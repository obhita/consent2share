package echosign.api.demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

import echosign.api.clientv15.ArrayOfString;
import echosign.api.clientv15.dto.ArrayOfDocumentKey;
import echosign.api.clientv15.dto.ArrayOfFileInfo;
import echosign.api.clientv15.dto.DocumentCreationInfo;
import echosign.api.clientv15.dto.FileInfo;
import echosign.api.clientv15.dto.Pong;
import echosign.api.clientv15.dto.SignatureFlow;
import echosign.api.clientv15.dto.SignatureType;
import echosign.api.clientv15.dto14.ParticipantInfo;
import echosign.api.clientv15.dto15.DocumentHistoryEvent;
import echosign.api.clientv15.dto15.DocumentInfo;
import echosign.api.clientv15.service.EchoSignDocumentService15Client;
import echosign.api.clientv15.service.EchoSignDocumentService15PortType;

public class EchoSignDocumentServiceDemo
{
  public static void test(String url, String apiKey) throws Exception
  {
    testPing(url, apiKey);
    testEchoFile(url, apiKey, "../test.pdf");
  }

  public static void testPing(String url, String apiKey) throws Exception
  {
    System.out.println("Testing basic connectivity...");
    Pong pong = getService(url).testPing(apiKey);
    System.out.println("Message from server: " + pong.getMessage());
  }

  public static boolean testEchoFile(String url, String apiKey, String filename) throws Exception
  {
    File file = new File(filename);
    if (!file.exists())
    {
      System.err.println("ERROR: expecting to find file ../test.pdf");
      return false;
    }

    System.out.println("Testing file transfer...");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    IOUtils.copy(new FileInputStream(file), byteArrayOutputStream);
    byte[] in = byteArrayOutputStream.toByteArray();
    byte[] out = getService(url).testEchoFile(apiKey, in);

    if (Arrays.equals(in, out))
    {
      System.out.println("Woohoo!  Everything seems to work.");
      return true;
    }
    else
    {
      System.err.println("ERROR:  Some kind of problem with file transfer, it seems.");
      return false;
    }
  }

  public static String sendDocument(String url, String apiKey, String fileName, String recipient) throws Exception
  {
    ArrayOfFileInfo fileInfos = getArrayOfFileInfos(fileName);

    ArrayOfString tos = new ArrayOfString();
    tos.getString().add(recipient);

    DocumentCreationInfo documentInfo = new DocumentCreationInfo();
    documentInfo.setTos(tos);
    documentInfo.setName("Test from SOAP: " + fileName);
    documentInfo.setMessage("This is neat.");
    documentInfo.setFileInfos(fileInfos);
    documentInfo.setSignatureType(SignatureType.ESIGN);
    documentInfo.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);

    ArrayOfDocumentKey documentKeys = getService(url).sendDocument(apiKey, null, documentInfo);
    System.out.println("Document key is: " + documentKeys.getDocumentKey().get(0).getDocumentKey());
    return documentKeys.getDocumentKey().get(0).getDocumentKey();
  }

  protected static ArrayOfFileInfo getArrayOfFileInfos(String fileName) throws IOException {
    File file = new File(fileName);
    FileInfo fileInfo = new FileInfo();
    fileInfo.setFileName(file.getName());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    IOUtils.copy(new FileInputStream(file), bytes);
    fileInfo.setFile(bytes.toByteArray());
    ArrayOfFileInfo fileInfos = new ArrayOfFileInfo();
    fileInfos.getFileInfo().add(fileInfo);
    return fileInfos;
  }

  public static String getDocumentInfo(String url, String apiKey, String documentKey) throws Exception
  {
    DocumentInfo info = getService(url).getDocumentInfo(apiKey, documentKey);

    System.out.println("Document is in status: " + info.getStatus());
    System.out.println("Document history: ");
    String versionKey;
    for (DocumentHistoryEvent event: info.getEvents().getDocumentHistoryEvent())
    {
      versionKey = event.getDocumentVersionKey();
      System.out.println("\t" + event.getDescription() + " on " + event.getDate() +
        (versionKey == null ? "" : " (versionKey: " + versionKey + ")"));
    }
    System.out.println("Latest versionKey: " + info.getLatestDocumentKey());

    for (ParticipantInfo partInfo:  info.getParticipants().getParticipantInfo()) {
      System.out.println("Participant Email is :" + partInfo.getEmail() );
      if ( partInfo.getAlternateParticipants() != null) {
        for (echosign.api.clientv15.dto13.ParticipantInfo altPartInfo: partInfo.getAlternateParticipants().getParticipantInfo()) {
          System.out.println("Alternate Participant Email is :" + altPartInfo.getEmail() );
        }
      }
    }

    return info.getLatestDocumentKey();
  }

  public static void getDocument(String url, String apiKey, String documentKey, String fileName) throws Exception
  {
    byte[] data = getService(url).getLatestDocument(apiKey, documentKey);
    FileOutputStream stream = new FileOutputStream(new File(fileName));
    stream.write(data);
    stream.close();
  }

  private static EchoSignDocumentService15PortType cachedService;

  protected static EchoSignDocumentService15PortType getService(String url) {
    if (cachedService == null) {
      EchoSignDocumentService15Client client = new EchoSignDocumentService15Client();
      cachedService = client.getEchoSignDocumentService15HttpPort(url);
    }
    return cachedService;
  }

  public static void main(String[] args)
  {
    if (!process(args))
      usage();
  }

  public static boolean process(String[] args)
  {
    try
    {
      if (args.length < 3)
        return false;

      String url = args[0];
      String apiKey = args[1];
      String command = args[2];

      if (command.equals("test"))
      {
        if (args.length != 3)
          return false;
        test(url, apiKey);
      }
      else if (command.equals("send"))
      {
        if (args.length != 5)
          return false;
        sendDocument(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("info"))
      {
        if (args.length != 4)
          return false;
        getDocumentInfo(url, apiKey, args[3]);
      }
      else if (command.equals("latest"))
      {
        if (args.length != 5)
          return false;
        getDocument(url, apiKey, args[3], args[4]);
      }
      else
      {
        return false;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static void usage()
  {
    usage1();
    usage2();
    usage3();
  }

  public static void usage1()
  {
    System.err.println("Usage:");
    System.err.println("  demo.bat <URL> <API key> <function> [parameters]");
    System.err.println("");
    System.err.println("where the function is one of:");
  }

  public static void usage2()
  {
    System.err.println("  test");
    System.err.println("  send <filename> <recipient_email>");
    System.err.println("  info <documentKey>");
    System.err.println("  latest <documentKey> <filename>");
  }

  public static void usage3()
  {
    System.err.println("");
    System.err.println("test will run basic tests to make sure you can communicate with the web service");
    System.err.println("");
    System.err.println("send will create a new agreement in the EchoSign system, and returns a documentKey");
    System.err.println("");
    System.err.println("info returns the current status and all the history events for a given documentKey");
    System.err.println("");
    System.err.println("latest saves the latest version of the document as a PDF with the given filename");
    System.err.println("");
  }

}
