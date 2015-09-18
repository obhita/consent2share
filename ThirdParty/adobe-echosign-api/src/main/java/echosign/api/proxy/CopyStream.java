package echosign.api.proxy;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import echosign.api.proxy.SoapProxy.Direction;

/**
 * A CopyStream is a runnable that copies an input stream to an output stream and a logging
 * stream.
 */
public class CopyStream implements Runnable
{
  private Direction direction;
  private String message;
  private PrintStream logPrintStream;
  private BufferedHttpStreamReader inputReader;
  private OutputStream outputStream;
  private boolean prettyPrint;
  private DateFormat printableDateFormat;

  public CopyStream(Direction direction, String serverHost, InputStream inputStream, OutputStream outputStream, PrintStream logPrintStream, boolean prettyPrint) throws UnsupportedEncodingException
  {
    this.direction = direction;
    this.message = (direction == Direction.CLIENT2SERVER) ? SoapProxy.FROM_CLIENT_TO_SERVER : SoapProxy.FROM_SERVER_TO_CLIENT;
    this.inputReader = new BufferedHttpStreamReader(serverHost, inputStream);
    this.outputStream = outputStream;
    this.logPrintStream = logPrintStream;
    this.prettyPrint = prettyPrint;
    this.printableDateFormat = new SimpleDateFormat(SoapProxy.PRINTABLE_DATE_FORMAT);
  }

  /**
   * Read data from an input stream and write it to an output stream and a logging stream until
   * any reason to stop reading occurs.
   */
  public void run()
  {
    try
    {
      while (true)
      {
        if (!readDataFromInput())
          return;
        writeDataToOutput();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    SoapProxy.stopStreaming();
  }

  private boolean readDataFromInput() throws IOException
  {
    inputReader.reset();
    while (true)
    {
      if (!readData())
        return false; // stop streaming

      // return if reading from the server or the request content
      if (direction == Direction.SERVER2CLIENT || inputReader.isReadingContent())
        return true;

      // return if the blank line is found
      if (inputReader.hasBlankLine())
      {
        // start tracking the content length
        inputReader.trackContentLength();
        // coerce the Host header to have the EchoSign domain name
        inputReader.coerceHostHeader();
        return true;
      }
    }
  }

  /**
   * Monitoring the two global states, straming and requesting, and making a decision to stop
   * streaming is centralized in this method.
   */
  private boolean readData() throws IOException
  {
    do
    {
      try
      {
        if (!SoapProxy.isStreaming())
          return false;

        inputReader.read();
        if (inputReader.eof())
        {
          SoapProxy.stopStreaming();
          return false;
        }
      }
      catch (SocketTimeoutException e)
      {
        // continue reading unless the client says no more requests
        if (!SoapProxy.isRequesting())
        {
          SoapProxy.stopStreaming();
          return false;
        }
      }
      catch (SocketException e)
      {
        e.printStackTrace();
        SoapProxy.stopStreaming();
        return false;
      }
    }
    while (inputReader.empty());

    return true;
  }

  private void writeDataToOutput() throws IOException, JDOMException
  {
    // print data
    System.out.println(">> " + printableDateFormat.format(new Date()) + " " + message);
    String data = inputReader.getBufferString();
    if (prettyPrint)
      prettyPrint(data);
    else
      println(data);

    // write data to the output stream
    if (outputStream != null)
    {
      byte[] bytes = inputReader.toByteArray();
      outputStream.write(bytes, 0, bytes.length);
    }
  }

  /**
   * Prints to both system.out and to the log if we are logging.
   */
  private void print(String s)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0 ; i < s.length(); i++)
    {
      char c = s.charAt(i);
      if (c != '\n' && c != '\r' && c != '\t' && (c < 32 || c > 126))
      {
        sb.append("\\" + (int) c);
      }
      else
      {
        sb.append(c);
      }
    }
    System.out.print(sb);
    if (logPrintStream != null)
      logPrintStream.print(s);
  }

  private void println(String s)
  {
    print(s);
    System.out.println();
  }

  /**
   * Pretty-prints the soap stuff using jdom.
   */
  private void prettyPrint(String string) throws JDOMException, IOException
  {
    int start = string.indexOf(SoapProxy.SOAP_ENVELOPE_START);
    int end = string.indexOf(SoapProxy.SOAP_ENVELOPE_END, start);
    if (start >= 0 && end >= 0)
    {
      end += SoapProxy.SOAP_ENVELOPE_END.length();
      if (start != 0)
        print(string.substring(0, start));
      StringReader stringReader = new StringReader(string.substring(start, end));
      SAXBuilder saxBuilder = new SAXBuilder();
      Document document = saxBuilder.build(stringReader);
      XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
      CharArrayWriter charArrayWriter = new CharArrayWriter();
      xmlOutputter.output(document, charArrayWriter);
      String result = charArrayWriter.toString();

      // the XMLOutputter adds an additional first line saying
      // <?xml version="1.0" encoding="UTF-8"?>
      // as this line is not in the input, we don't want it, so we remove it
      result = result.substring(result.indexOf("\n") + 1);
      println(result);
      if (end != string.length())
        println(string.substring(end));
    }
    else
    {
      println(string);
    }
  }
}
