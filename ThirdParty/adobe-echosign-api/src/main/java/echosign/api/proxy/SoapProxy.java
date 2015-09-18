package echosign.api.proxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.io.IOUtils;

/**
 * A SoapProxy displays the requests and responses exchanged between the client and server. It can
 * also save the requests and responses and replay the requests later.
 * <p>
 * A SoapProxy maintains two streams, one stream from the client to the server and the other from
 * the server to the client and prints the data going through the streams. It's done with the help
 * of the classes <b>BufferedHttpStreamReader</b> and <b>CopyStream</b>.
 * <p>
 * When to stop streaming is controlled with the two global states, streaming and requesting. Both
 * streams will be stopped whenever the streaming state turns into 'stop' (normally when the end
 * of the stream is reached or an IO exception is thrown), or the requesting state turns into 'not
 * requesting', which will be done only by the client when there is no more requests to send.
 */
public class SoapProxy
{
  /**
   * Signify the stream direction, from the client to server or the server to the client.
   */
  public static enum Direction
  {
    CLIENT2SERVER, SERVER2CLIENT;
  }

  public static final String FROM_CLIENT_TO_SERVER = "From CLIENT to SERVER:";
  public static final String FROM_SERVER_TO_CLIENT = "From SERVER to CLIENT:";
  public static final String SOAP_ENVELOPE_START = "<soap:Envelope";  // no ">" here, there are arguments to follow
  public static final String SOAP_ENVELOPE_END = "</soap:Envelope>";
  public static final String PRINTABLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
  public static final String FILENAME_DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss.SSS";

  private static final String USAGE_MSG = "Usage: SoapProxy [-r] [-l] [-f] client_port|filename server_hostname server_port"
                                          + "\n-r no prettyprint\n-l logging in a created directory\n-f input from file";

  private static boolean prettyPrint = true;
  private static boolean logging = false;
  private static boolean inputIsFile = false;
  private static boolean streaming = true;  // false when stopping streaming
  private static boolean requesting = true; // false when client has no more requests to send

  private static final int DEFAULT_SOCKET_TIMEOUT_MSEC = 1000;

  /**
   * The main method
   * @param args url, apiKey, and a command must be specified at least
   */
  public static void main(String[] args)
  {
    int argStart = parseArguments(args);
    if (argStart < 0 || args.length - argStart != 3)
    {
      System.err.println(USAGE_MSG);
      return;
    }

    if (inputIsFile)
    {
      processInputFile(args[argStart], args[argStart + 1], Integer.parseInt(args[argStart + 2]));
      return;
    }

    // create log directory if logging
    File logDirectory = null;
    if (logging)
    {
      logDirectory = createLogDirectory();
    }

    // The terms "client" and "server" in this program apply to the user's view of the situation
    // rather than to this program's view, which is the other way around.
    int clientPort = Integer.parseInt(args[argStart]);
    String serverHost = args[argStart + 1];
    int serverPort = Integer.parseInt(args[argStart + 2]);

    try
    {
      // open a server socket that the client will connect to
      ServerSocket clientServerSocket = new ServerSocket(clientPort);

      int logFileNumber = 0;
      for (;;)
      {
        PrintStream clientLoggingStream = null;
        PrintStream serverLoggingStream = null;
        if (logging)
        {
          clientLoggingStream = createLogFile(logDirectory, String.format("%03d", logFileNumber) + "-client");
          serverLoggingStream = createLogFile(logDirectory, String.format("%03d", logFileNumber) + "-server");
          logFileNumber++;
        }
        proxyOneConnection(clientLoggingStream, serverLoggingStream, serverHost, serverPort, clientServerSocket, prettyPrint);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private static int parseArguments(String[] args)
  {
    int argStart = 0;
    for (;;)
    {
      if (argStart >= args.length)
      {
        return -1;
      }
      if (args[argStart].equals("-r"))
      {
        prettyPrint = false;
        argStart++;
      }
      else if (args[argStart].equals("-l"))
      {
        logging = true;
        argStart++;
      }
      else if (args[argStart].equals("-f"))
      {
        inputIsFile = true;
        argStart++;
      }
      else
      {
        return argStart;
      }
    }
  }

  /**
   * This method opens the socket streams for sending and receiving the data and then starts two
   * threads, one thread for sending the requests from the client to the server, and the other for
   * receiving the responses from the server and pass them back to the client, while displaying the
   * requests and responses.
   */
  public static void proxyOneConnection(PrintStream clientLoggingStream, PrintStream serverLoggingStream,
      String serverHost, int serverPort, ServerSocket clientServerSocket, boolean prettyPrint)
      throws IOException, InterruptedException
  {
    System.out.println("Waiting for client to connect (" + clientServerSocket.getInetAddress() + ":" + clientServerSocket.getLocalPort() + ")...");

    // accept socket connection from the client and get input and output streams to that socket
    Socket clientSocket = clientServerSocket.accept();
    clientSocket.setSoTimeout(DEFAULT_SOCKET_TIMEOUT_MSEC);
    InputStream clientInputStream = clientSocket.getInputStream();
    OutputStream clientOutputStream = clientSocket.getOutputStream();

    System.out.println("Connectiong to server (" + serverHost + ":" + serverPort + ")...");

    // get socket connection to the server and get input and output streams
	  SocketFactory socketFactory = SSLSocketFactory.getDefault();
    Socket serverSocket = socketFactory.createSocket(serverHost, serverPort);
    serverSocket.setSoTimeout(DEFAULT_SOCKET_TIMEOUT_MSEC);
    InputStream serverInputStream = serverSocket.getInputStream();
    OutputStream serverOutputStream = serverSocket.getOutputStream();

    // reset the states; start streaming and sending the requests
    startStreaming();
    startRequesting();

    // create the threads to read and write
    Thread serverToClientThread = new Thread(new CopyStream(Direction.SERVER2CLIENT, serverHost, serverInputStream, clientOutputStream, serverLoggingStream, prettyPrint));
    Thread clientToServerThread = new Thread(new CopyStream(Direction.CLIENT2SERVER, serverHost, clientInputStream, serverOutputStream, clientLoggingStream, prettyPrint));

    // start the threads
    serverToClientThread.start();
    clientToServerThread.start();

    // join and wait for the threads
    clientToServerThread.join();
    serverToClientThread.join();

    // close the sockets
    clientSocket.close();
    serverSocket.close();
  }

  /**
   * Reset the streaming state to 'streaming'.
   */
  public static void startStreaming()
  {
    streaming = true;
  }

  /**
   * Turns the streaming state to 'stop'. Either thread can call this method to signal the other to
   * stop.
   */
  public static void stopStreaming()
  {
    streaming = false;
  }

  /**
   * Returns true if the streaming state is 'streaming'.
   */
  public static boolean isStreaming()
  {
    return streaming;
  }

  /**
   * Reset the requesting state to 'requesting'.
   */
  public static void startRequesting()
  {
    requesting = true;
  }

  /**
   * Turns the requesting state to 'not requesting'. Only the client thread should call this method
   * to signal the server thread to stop.
   */
  public static void stopRequesting()
  {
    requesting = false;
  }

  /**
   * Returns true if the requesting state is 'requesting'.
   */
  public static boolean isRequesting()
  {
    return requesting;
  }

  private static File createLogDirectory()
  {
    String logDirName = (new SimpleDateFormat(FILENAME_DATE_FORMAT)).format(new Date());
    File file = new File(logDirName);
    if (!file.mkdir())
    {
      System.err.println("Cannot create log directory " + logDirName);
      System.exit(1);
    }
    return file;
  }

  public static PrintStream createLogFile(File logDirectory, String logFileName) throws IOException
  {
    File f = new File(logDirectory, logFileName);
    if (!f.canWrite() && !f.createNewFile())
    {
      System.out.println("Cannot create log file " + logFileName);
      System.exit(1);
    }
    return new PrintStream(new FileOutputStream(f), true, "UTF-8");
  }

  /**
   * If the input is a file (-f argument) process it here.
   */
  private static void processInputFile(String inputFileName, String serverHost, int serverPort)
  {
    try
    {
      String inputString = getInputString(inputFileName);
      if (inputString == null)
        return;

      // get server socket and input and output streams
      // also get logging stream if necessary
	    SocketFactory socketFactory = SSLSocketFactory.getDefault();
      Socket serverSocket = socketFactory.createSocket(serverHost, serverPort);
      serverSocket.setSoTimeout(DEFAULT_SOCKET_TIMEOUT_MSEC);
      InputStream serverInputStream = serverSocket.getInputStream();
      OutputStream serverOutputStream = serverSocket.getOutputStream();
      PrintStream serverLoggingStream = logging ? createLogFile(createLogDirectory(), "000-server") : null;

      // reset the states; start streaming and sending a request
      startStreaming();
      startRequesting();

      // run CopyStream to get the server input and output it appropriately
      Thread serverToClientThread = new Thread(new CopyStream(Direction.SERVER2CLIENT, serverHost, serverInputStream, null, serverLoggingStream, prettyPrint));
      serverToClientThread.start();

      // write the input to standard out and to the server
      System.out.println(">> " + (new SimpleDateFormat(PRINTABLE_DATE_FORMAT)).format(new Date()) + " " + FROM_CLIENT_TO_SERVER);
      System.out.println(inputString);
      serverOutputStream.write(inputString.getBytes("UTF-8"));

      // done sending the request
      stopRequesting();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private static String getInputString(String inputFileName) throws FileNotFoundException, IOException
  {
      // The input file, if we created it with pretty printing, will have an incorrect Content-Length
      // field. So we read the input file into a string and fix up the field.
      File inputFile = new File(inputFileName);
      if (!inputFile.exists() || inputFile.length() == 0)
      {
        System.out.println("Input file doesn't exist or is empty.");
        return null;
      }

      // read the input file into a string
      byte[] inputByteArray = new byte[(int)inputFile.length()];
      FileInputStream fileInputStream = new FileInputStream(inputFile);
      int n;
      try
      {
        n = fileInputStream.read(inputByteArray);
      }
      finally
      {
        IOUtils.closeQuietly(fileInputStream);
      }

      // find the beginning of the http body
      String inputString = new String(inputByteArray, 0, n, "UTF-8");
      int bodyStartPos = inputString.indexOf("\r\n\r\n") + 4;
      if (bodyStartPos <= 4)
      {
        System.out.println("Malformed input file: no blank line between header and content" + inputString);
        return null;
      }

      // get the actual content length and then place it in the header
      int contentLength = inputString.length() - bodyStartPos;
      inputString = inputString.replaceFirst("Content-Length: [0-9]+", "Content-Length: " + contentLength);

      return inputString;
  }
}
