package echosign.api.demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

import echosign.api.clientv20.dto.ArrayOfFileInfo;
import echosign.api.clientv20.dto.DocumentCreationInfo;
import echosign.api.clientv20.dto.FileInfo;
import echosign.api.clientv20.dto.Pong;
import echosign.api.clientv20.dto.SignatureFlow;
import echosign.api.clientv20.dto.SignatureType;
import echosign.api.clientv20.dto14.ArrayOfRecipientInfo;
import echosign.api.clientv20.dto14.RecipientInfo;
import echosign.api.clientv20.dto14.RecipientRole;
import echosign.api.clientv20.dto16.ArrayOfDocumentKey;
import echosign.api.clientv20.dto19.ParticipantInfo;
import echosign.api.clientv20.dto20.DocumentHistoryEvent;
import echosign.api.clientv20.dto20.DocumentInfo;
import echosign.api.clientv20.service.EchoSignDocumentService20Client;
import echosign.api.clientv20.service.EchoSignDocumentService20PortType;

public class EchoSignDocumentServiceDemo
{
  public static final String testPrefix = "Test from SOAP: ";
  public static final String testMessage = "This is neat.";

  public static void test(String url, String apiKey) throws Exception
  {
    testPing(url, apiKey);
    testEchoFile(url, apiKey, getTestPdfFile().getPath());
  }

  public static File getTestPdfFile() throws Exception
  {
    return getTestPdfFile("test.pdf");
  }

  public static File getTestPdfFile(String filename) throws Exception
  {
    File testPdf = new File("./" + filename);
    if (testPdf.exists())
      return testPdf;
    return new File("../" + filename);
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
      System.err.println("ERROR: expecting to find file " + filename);
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
    ArrayOfRecipientInfo recipients = createArrayOfRecipientInfosAllSigners(new String[]{recipient});
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipients, fileName, null, testPrefix, testMessage);
    ArrayOfDocumentKey documentKeys = getService(url).sendDocument(apiKey, null, documentInfo);
    System.out.println("Document key is: " + documentKeys.getDocumentKey().get(0).getDocumentKey());
    return documentKeys.getDocumentKey().get(0).getDocumentKey();
  }

  protected static ArrayOfFileInfo createArrayOfFileInfos(String[] fileNames) throws IOException
  {
    ArrayOfFileInfo fileInfos = new ArrayOfFileInfo();
    for (String fileName : fileNames) {
      File file = new File(fileName);
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      IOUtils.copy(new FileInputStream(file), bytes);

      FileInfo fileInfo = new FileInfo();
      fileInfo.setFileName(file.getName());
      fileInfo.setFile(bytes.toByteArray());
      
      fileInfos.getFileInfo().add(fileInfo);
    }
    return fileInfos;
  }
  
  protected static ArrayOfFileInfo createFormFieldLayerTemplates(String[] formFieldLayerTemplateKeys) throws Exception
  {
    ArrayOfFileInfo fileInfos = new ArrayOfFileInfo();
    for (String formFieldLayerTemplateKey : formFieldLayerTemplateKeys) {
      FileInfo fileInfo = new FileInfo();
      fileInfo.setLibraryDocumentKey(formFieldLayerTemplateKey);
      fileInfos.getFileInfo().add(fileInfo);
    }
    return fileInfos;
  }

  protected static ArrayOfRecipientInfo createArrayOfRecipientInfosAllSigners(String[] recipients) throws Exception
  {
    ArrayOfRecipientInfo recipientInfos = new ArrayOfRecipientInfo();
    for (String recipient : recipients) {
      RecipientInfo recipientInfo = new RecipientInfo();
      recipientInfo.setEmail(recipient);
      recipientInfo.setFax(null);
      recipientInfo.setRole(RecipientRole.SIGNER);
      recipientInfo.setSecurityOptions(null);
      recipientInfos.getRecipientInfo().add(recipientInfo);
    }
    return recipientInfos;
  }

  protected static DocumentCreationInfo createDocumentCreationInfoForEsign(ArrayOfRecipientInfo recipientInfos, String fileName, String formFieldLayerTemplateKey, String name, String message) throws Exception
  {
    ArrayOfFileInfo fileInfos = createArrayOfFileInfos(new String[]{fileName});
    ArrayOfFileInfo formFieldLayerTemplates = (formFieldLayerTemplateKey != null) ? createFormFieldLayerTemplates(new String[]{formFieldLayerTemplateKey}) : null;

    DocumentCreationInfo documentCreationInfo = new DocumentCreationInfo();
    documentCreationInfo.setRecipients(recipientInfos);
    documentCreationInfo.setFileInfos(fileInfos);
    documentCreationInfo.setName(name + fileName);
    documentCreationInfo.setMessage(message);
    documentCreationInfo.setSignatureType(SignatureType.ESIGN);
    documentCreationInfo.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);
    documentCreationInfo.setFormFieldLayerTemplates(formFieldLayerTemplates);
    
    return documentCreationInfo;
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
        for (ParticipantInfo altPartInfo: partInfo.getAlternateParticipants().getParticipantInfo()) {
          System.out.println("Alternate Participant Email is :" + altPartInfo.getEmail() );
        }
      }
    }

    return info.getLatestDocumentKey();
  }

  public static void getDocument(String url, String apiKey, String documentKey, String filename) throws Exception
  {
    byte[] data = getService(url).getLatestDocument(apiKey, documentKey);
    FileOutputStream stream = getFileStream(filename);
    try {
      stream.write(data);
    } finally {
      stream.close();
    }
  }

  protected static FileOutputStream getFileStream(String filename) throws Exception {
    String fileName = new File(filename).getAbsolutePath();
    return new FileOutputStream(new File(fileName));
  }

  private static EchoSignDocumentService20PortType cachedService;

  protected static EchoSignDocumentService20PortType getService(String url) {
    if (cachedService == null) {
      EchoSignDocumentService20Client client = new EchoSignDocumentService20Client();
      cachedService = client.getEchoSignDocumentService20HttpPort(url);
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
    usageSynopsis();
    usageBaseCommands();
    usageBaseDescriptions();
  }

  public static void usageSynopsis()
  {
    System.err.println("Usage:");
    System.err.println("  demo.bat <URL> <API key> <function> [parameters]");
    System.err.println("");
    System.err.println("where the function is one of:");
  }

  public static void usageBaseCommands()
  {
    System.err.println("  test");
    System.err.println("  send <filename> <recipient_email>");
    System.err.println("  info <documentKey>");
    System.err.println("  latest <documentKey> <filename>");
  }

  public static void usageBaseDescriptions()
  {
    System.err.println("");
    System.err.println("test will run basic tests to make sure you can communicate with the web service");
    System.err.println("");
    System.err.println("send will create a new agreement in the Adobe Document Cloud system, and returns a documentKey");
    System.err.println("");
    System.err.println("info returns the current status and all the history events for a given documentKey");
    System.err.println("");
    System.err.println("latest saves the latest version of the document as a PDF with the given filename");
    System.err.println("");
  }

}
