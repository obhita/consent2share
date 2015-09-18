package echosign.api.proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * A BufferedHttpStreamReader reads an HTTP stream and can take special care for the Host header
 * and the content length tracking.
 */
public class BufferedHttpStreamReader extends InputStreamReader
{
  public static final int DEFAULT_BUFFER_SIZE = 8192;

  /**
   * This is the official EchoSign domain name for the secure API calls.
   */
  public static final String ECHOSIGN_DOMAIN_NAME = "secure.echosign.com";
  /**
   * Use this environment variable if you want to make the secure API calls other than the
   * offical domain name 'secure.echogin.com'.
   */
  public static final String ECHOSIGN_DOMAIN_NAME_ENV_NAME = "ECHOSIGN_DOMAIN_NAME";

  private static final String HOST_HEADER = "Host: ";
  private static final String HOST_HEADER_REGEXP = HOST_HEADER + "\\s*[^\\r\\n]+";
  private static final String CONTENT_LENGTH_HEADER = "Content-Length: ";

  private char[] buffer;
  private int offset;
  private int remainder;
  private int numRead;
  private boolean eof;

  private boolean readingContent;
  private int contentLength;
  private int blankLinePos;

  private String bufferString;
  private String hostHeaderEchoSign;

  public BufferedHttpStreamReader(String serverHost, InputStream in) throws UnsupportedEncodingException
  {
    this(serverHost, in, "UTF-8", DEFAULT_BUFFER_SIZE);
  }

  public BufferedHttpStreamReader(String serverHost, InputStream in, String charsetName, int bufferSize) throws UnsupportedEncodingException
  {
    super(in, charsetName);
    buffer = new char[bufferSize];
    eof = false;
    readingContent = false;
    contentLength = 0;

    // Use the specified domain name for the Host header. Look for it in the environment variable,
    // the argument serverHost, or the default one in this order.
    String echoSignDomainName = System.getenv(ECHOSIGN_DOMAIN_NAME_ENV_NAME);
    hostHeaderEchoSign = HOST_HEADER + ((echoSignDomainName != null) ? echoSignDomainName : ((serverHost != null) ? serverHost : ECHOSIGN_DOMAIN_NAME));

    reset();
  }

  public void reset()
  {
    offset = 0;
    remainder = buffer.length;
    numRead = 0;
    blankLinePos = -1;
    bufferString = null;
  }

  public int read() throws IOException
  {
    if (eof())
      return -1;

    if (remainder <= 0)
    {
      System.err.println("WARNING: BufferedHttpStreamReader: reset due to out of buffer space");
      reset();
    }

    int n = super.read(buffer, offset, remainder);

    if (n < 0)
      eof = true;
    else if (0 < n)
    {
      numRead = n;
      offset += numRead;
      remainder -= numRead;

      // the order is important here
      readingContent = (0 < contentLength);
      contentLength -= numRead;
    }

    return n;
  }

  public boolean eof()
  {
    return eof;
  }

  public boolean empty()
  {
    return (offset == 0);
  }

  public boolean isReadingContent()
  {
    return readingContent;
  }

  public boolean hasBlankLine()
  {
    // look for CRLFCRLF from the last data position
    for (int i = offset - numRead; i < offset; i++)
    {
      if (buffer[i] == '\r'
          && i + 4 <= offset
          && buffer[i + 1] == '\n'
          && buffer[i + 2] == '\r'
          && buffer[i + 3] == '\n')
      {
        // remember the position for later use
        blankLinePos = i + 2;
        return true;
      }
    }
    return false;
  }

  private void convertBytesToString()
  {
    if (bufferString == null)
      bufferString = new String(buffer, 0, offset);
  }

  public String getBufferString()
  {
    convertBytesToString();
    return bufferString;
  }

  // start tracking the content length
  public void trackContentLength()
  {
    if (0 < blankLinePos)
    {
      convertBytesToString();

      // find the location of the content length string
      int beg = bufferString.indexOf(CONTENT_LENGTH_HEADER);
      if (0 <= beg)
      {
        beg += CONTENT_LENGTH_HEADER.length();

        // skip whitespace and find the end of the string
        char c = bufferString.charAt(beg);
        while (c == ' ' || c == '\t')
          c = bufferString.charAt(++beg);

        int end = beg;
        while ('0' <= c && c <= '9')
          c = bufferString.charAt(++end);

        if (beg < end)
        {
          contentLength = Integer.parseInt(bufferString.substring(beg, end));

          // some part of the content may be read already
          contentLength -= bufferString.length() - (blankLinePos + 2); // 2 := CRLF
        }
      }
    }
  }

  // coerce the Host header to have the EchoSign domain name
  public void coerceHostHeader()
  {
    if (0 < blankLinePos)
    {
      convertBytesToString();
      bufferString = bufferString.replaceFirst(HOST_HEADER_REGEXP, hostHeaderEchoSign);
    }
  }

  public byte[] toByteArray() throws IOException, UnsupportedEncodingException
  {
    ByteArrayOutputStream baostrm = new ByteArrayOutputStream();
    OutputStreamWriter oswtr = new OutputStreamWriter(baostrm, getEncoding());
    if (bufferString != null)
      oswtr.write(bufferString, 0, bufferString.length());
    else
      oswtr.write(buffer, 0, offset);
    oswtr.close();
    return baostrm.toByteArray();
  }
}
