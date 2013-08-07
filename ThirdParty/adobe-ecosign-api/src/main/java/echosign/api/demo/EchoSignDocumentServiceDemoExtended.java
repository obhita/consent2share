package echosign.api.demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import echosign.api.clientv15.ArrayOfString;
import echosign.api.clientv15.dto.ArrayOfDocumentKey;
import echosign.api.clientv15.dto.ArrayOfDocumentPageImages;
import echosign.api.clientv15.dto.ArrayOfFileInfo;
import echosign.api.clientv15.dto15.ArrayOfUserInfo;
import echosign.api.clientv15.dto.CancelDocumentResult;
import echosign.api.clientv15.dto.DisplayUserInfo;
import echosign.api.clientv15.dto.DocumentCreationInfo;
import echosign.api.clientv15.dto.DocumentImageList;
import echosign.api.clientv15.dto.DocumentPageImages;
import echosign.api.clientv15.dto.DocumentUrlResult;
import echosign.api.clientv15.dto.ExternalId;
import echosign.api.clientv15.dto.FormCreationInfo;
import echosign.api.clientv15.dto15.GetUsersInAccountResult;
import echosign.api.clientv15.dto.OptIn;
import echosign.api.clientv15.dto.RemoveDocumentResult;
import echosign.api.clientv15.dto.Result;
import echosign.api.clientv15.dto.SendDocumentMegaSignResult;
import echosign.api.clientv15.dto.SendReminderResult;
import echosign.api.clientv15.dto.SignatureFlow;
import echosign.api.clientv15.dto.SignatureType;
import echosign.api.clientv15.dto.UserCreationInfo;
import echosign.api.clientv15.dto15.UserInfo;
import echosign.api.clientv15.dto.UserVerificationInfo;
import echosign.api.clientv15.dto10.ArrayOfWidgetItem;
import echosign.api.clientv15.dto10.GetWidgetsForUserResult;
import echosign.api.clientv15.dto10.WidgetItem;
import echosign.api.clientv15.dto12.SendDocumentInteractiveOptions;
import echosign.api.clientv15.dto12.SendDocumentInteractiveResult;
import echosign.api.clientv15.dto14.ArrayOfDocumentListItem;
import echosign.api.clientv15.dto14.DocumentContent;
import echosign.api.clientv15.dto14.DocumentImageUrls;
import echosign.api.clientv15.dto14.DocumentListItem;
import echosign.api.clientv15.dto14.DocumentUrl;
import echosign.api.clientv15.dto14.GetDocumentImageUrlsOptions;
import echosign.api.clientv15.dto14.GetDocumentImageUrlsResult;
import echosign.api.clientv15.dto14.GetDocumentUrlsOptions;
import echosign.api.clientv15.dto14.GetDocumentUrlsResult;
import echosign.api.clientv15.dto14.GetDocumentsForUserResult;
import echosign.api.clientv15.dto14.GetDocumentsOptions;
import echosign.api.clientv15.dto14.GetDocumentsResult;
import echosign.api.clientv15.dto14.GetMegaSignDocumentResult;
import echosign.api.clientv15.dto14.PageImageUrl;
import echosign.api.clientv15.dto14.PageImageUrls;
import echosign.api.clientv15.dto14.SigningUrlResult;
import echosign.api.clientv15.dto15.DocumentHistoryEvent;
import echosign.api.clientv15.dto15.DocumentInfo;
import echosign.api.clientv15.dto15.DocumentInfoList;
import echosign.api.clientv15.dto7.AccountCreationInfo;
import echosign.api.clientv15.dto7.AccountType;
import echosign.api.clientv15.dto7.CreateAccountResult;
import echosign.api.clientv15.dto8.EmbeddedWidgetCreationResult;
import echosign.api.clientv15.dto8.GetFormDataResult;
import echosign.api.clientv15.dto8.UrlWidgetCreationResult;
import echosign.api.clientv15.dto8.WidgetCompletionInfo;
import echosign.api.clientv15.dto8.WidgetCreationInfo;
import echosign.api.clientv15.dto8.WidgetPersonalizationInfo;
import echosign.api.clientv15.dto9.ArrayOfDocumentLibraryItem;
import echosign.api.clientv15.dto9.DocumentLibraryItem;
import echosign.api.clientv15.dto9.GetLibraryDocumentsForUserResult;
import echosign.api.clientv15.dto9.InitiateInteractiveSendDocumentResult;
import echosign.api.clientv15.dto9.LibraryDocumentCreationInfo;
import echosign.api.clientv15.dto9.LibraryDocumentCreationResult;
import echosign.api.clientv15.dto9.LibrarySharingMode;
import echosign.api.clientv15.dto9.SigningUrl;
import echosign.api.clientv15.dto9.UserCredentials;

/**
 * Author: dan
 * Time: Aug 15, 2006 10:02:39 PM
 */
public class EchoSignDocumentServiceDemoExtended extends EchoSignDocumentServiceDemo
{

  public static String createUser(String url, String apiKey, String email, String password) throws Exception
  {
    System.out.println("creating user for : " + email);
    UserCreationInfo userInfo = new UserCreationInfo();
    userInfo.setEmail(email);
    userInfo.setCompany("WalrusTech");
    userInfo.setFirstName("Foo");
    userInfo.setLastName("Bar");
    userInfo.setOptIn(OptIn.NO);
    userInfo.setPassword(password);
    userInfo.setCustomField1("custom1");
    userInfo.setCustomField2("custom2");
    userInfo.setCustomField3("custom3");

    String userKey = getService(url).createUser(apiKey, userInfo);

    System.out.println("new user key = " + userKey);
    return userKey;
  }

  public static void createAccount(String url, String apiKey, String email, String password) throws Exception
  {
    System.out.println("creating account for : " + email);
    UserCreationInfo userInfo = new UserCreationInfo();
    userInfo.setEmail(email);
    userInfo.setCompany("WalrusTech");
    userInfo.setFirstName("Foo");
    userInfo.setLastName("Bar");
    userInfo.setOptIn(OptIn.NO);
    userInfo.setPassword(password);
    userInfo.setCustomField1("custom1");
    userInfo.setCustomField2("custom2");
    userInfo.setCustomField3("custom3");

    AccountCreationInfo accountInfo = new AccountCreationInfo();
    accountInfo.setCompanyName("WalrusTech");
    accountInfo.setAccountType(AccountType.ENTERPRISE);
    accountInfo.setNumSeats(25);

    CreateAccountResult result = getService(url).createAccount(apiKey, userInfo, accountInfo);

    System.out.println("Result: " + result.getErrorCode());
  }

  static void addMarkers(DocumentCreationInfo info)
  {
    Date now = new Date();
    StringBuilder name = info.getName() == null ? new StringBuilder() : new StringBuilder(info.getName() +", ");
    String dateMarker = makeDateMarker(now);
    name.append(dateMarker);
    info.setName(name.toString());

    StringBuilder msg = info.getMessage() == null ? new StringBuilder() : new StringBuilder(info.getMessage());
    msg.append('(');
    msg.append(dateMarker);
    msg.append(')');
    info.setMessage(msg.toString());
  }

  static void addMarkers(FormCreationInfo info)
  {
    Date now = new Date();
    StringBuilder name = info.getName() == null ? new StringBuilder() : new StringBuilder(info.getName() +", ");
    name.append(makeDateMarker(now));
    info.setName(name.toString());
  }

  static String makeDateMarker(Date date)
  {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bos);
    out.format("%tk:%tM:%tS %tm/%td/%ty", date, date, date, date, date, date);
    out.close();
    return bos.toString();
  }

  public static String sendDocumentMegaSign(String url, String apiKey, String fileName, String[] tos) throws Exception
  {
    ArrayOfString arrayOfTos = new ArrayOfString();
    for (String to : tos)
      arrayOfTos.getString().add(to);

    DocumentCreationInfo documentInfo = new DocumentCreationInfo();
    documentInfo.setTos(arrayOfTos);
    documentInfo.setName("Test from SOAP: sendDocumentMegaSign");
    documentInfo.setMessage("This is neat.");
    documentInfo.setFileInfos(getArrayOfFileInfos(fileName));
    documentInfo.setSignatureType(SignatureType.ESIGN);
    documentInfo.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);

    SendDocumentMegaSignResult sendDocumentResult = getService(url).sendDocumentMegaSign(apiKey, null, documentInfo);

    if (!sendDocumentResult.isSuccess())
    {
      System.out.println("sendDocumentMegaSign failed. Error code " + sendDocumentResult.getErrorCode() +
          " Error Message " + sendDocumentResult.getErrorMessage());
      return null;
    }
    ArrayOfDocumentKey documentKeys = sendDocumentResult.getDocumentKeyArray();

    System.out.println("Mega doc key " + sendDocumentResult.getDocumentKey().getDocumentKey());
    for (int i = 0; i < documentKeys.getDocumentKey().size(); i++)
      System.out.println("Document key [" + i + "] " + documentKeys.getDocumentKey().get(i).getDocumentKey());
    return sendDocumentResult.getDocumentKey().getDocumentKey();
  }

  public static void getUsersInAccount(String url, String apiKey) throws Exception
  {
    GetUsersInAccountResult result = getService(url).getUsersInAccount(apiKey);

    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfUserInfo userInfos = result.getUserListForAccount();
    if(userInfos == null)
    {
      System.out.println("Num users in account: 0");
      return;
    }

    System.out.format("%-14s %-35s %-30s%n", "userKey", "email", "fullNameOrEmail");
    System.out.format("-------------- ----------------------------------- ------------------------------  %n");
    for(UserInfo ui : userInfos.getUserInfo())
    {
      System.out.format("%-14s %-35s %-30s%n", ui.getUserKey(), ui.getEmail(), ui.getFullNameOrEmail());
    }
    System.out.println();
    System.out.format("Num users in account: %d%n", userInfos.getUserInfo().size());
  }

  public static void getUserDocuments(String url, String apiKey, String email) throws Exception
  {
    UserCredentials userCredentials = new UserCredentials();
    userCredentials.setEmail(email);
    GetDocumentsForUserResult result = getService(url).getUserDocuments(apiKey, userCredentials);
    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfDocumentListItem docItems = result.getDocumentListForUser();
    if(docItems == null)
    {
      System.out.println("Num documents for user: 0");
      return;
    }
    printDocumentListItems(docItems);
  }

  public static void getDocumentsForUser(String url, String apiKey, String userKey) throws Exception
  {
    GetDocumentsForUserResult result = getService(url).getDocumentsForUser(apiKey, userKey);
    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfDocumentListItem docItems = result.getDocumentListForUser();
    if(docItems == null)
    {
      System.out.println("Num documents for user: 0");
      return;
    }
    printDocumentListItems(docItems);
  }

  public static void getMyDocuments(String url, String apiKey) throws Exception {
    GetDocumentsForUserResult result = getService(url).getMyDocuments(apiKey);
    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfDocumentListItem docItems = result.getDocumentListForUser();
    if(docItems == null)
    {
      System.out.println("Num documents for me: 0");
      return;
    }
    printDocumentListItems(docItems);
  }

   public static void getMegaSignDocument(String url, String apiKey, String documentKey) throws Exception
  {
    GetMegaSignDocumentResult result = getService(url).getMegaSignDocument(apiKey, documentKey);
    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfDocumentListItem docItems = result.getDocuments();
    if(docItems == null)
    {
      System.out.println("Num documents for user: 0");
      return;
    }
    printDocumentListItems(docItems);
  }

  private static void printDocumentListItems(ArrayOfDocumentListItem docItems)
  {
    printDocumentListItemHeader();
    for(DocumentListItem item : docItems.getDocumentListItem())
    {
      printDocumentListItem(item);
    }
    System.out.println();
    System.out.format("Num documents for user: %d%n", docItems.getDocumentListItem().size());
  }

  private static void printDocumentListItem(DocumentListItem item)
  {
    DisplayUserInfo du = item.getDisplayUserInfo();
    final String esign = item.isEsign() ? "Y" : "n";
    final String megaSign = item.isMegaSign() ? "Y" : "n";
    final String company = du.getCompany() == null ? "" : du.getCompany();

    Date date = item.getDisplayDate().toGregorianCalendar().getTime();
    System.out.format("%-14s %-24s %-2s %-2s %tm/%td/%tY %-20s %-20s %-20s%n", item.getDocumentKey(), item.getUserDocumentStatus().toString(),
        esign, megaSign, date, date, date, du.getFullNameOrEmail(), company, item.getName());
  }

  static void printDocumentListItemHeader()
  {
    System.out.format("%-14s%n", "document");
    System.out.format("%-14s %-24s %2s %2s %-10s %-20s %-20s %-20s%n", "key", "status", "e?", "m?", "date", "user", "company", "name");
    System.out.format("-------------- ------------------------ -- -- ---------- ");
    for(int i=0; i<2;i++)
      System.out.format("-------------------- ");
    System.out.format("----------------------------------------");
    System.out.println();
  }

  public static void getDocument(String url, String apiKey, String documentKey, String versionKey, String fileName) throws Exception
  {
    byte[] data = (documentKey != null) ? getService(url).getLatestDocument(apiKey, documentKey) : getService(url).getDocumentByVersion(apiKey, versionKey);
    FileOutputStream stream = new FileOutputStream(new File(fileName));
    stream.write(data);
    stream.close();
  }

  public static void cancelDocument(String url, String apiKey, String documentKey) throws Exception
  {
    CancelDocumentResult result = getService(url).cancelDocument(apiKey, documentKey, "Cancelled by sample code", true);
    System.out.println("cancelDocument result: " + result.getResult());
  }

  public static void sendReminder(String url, String apiKey, String documentKey) throws Exception
  {
    SendReminderResult result = getService(url).sendReminder(apiKey, documentKey, "Reminded by sample code");
    System.out.println("sendReminder result: " + result.getResult());
    if (result.getResult() == Result.REMINDER_SENT)
      System.out.println("Reminder has been sent to: " + result.getRecipientEmail());
  }

  public static void documentUrl(String url, String apiKey, String key, boolean document) throws Exception
  {
    DocumentUrlResult documentUrlResult;
    if (document)
    {
      documentUrlResult = getService(url).getLatestDocumentUrl(apiKey, key);
    }
    else
    {
      documentUrlResult = getService(url).getDocumentUrlByVersion(apiKey, key);
    }
    if (!documentUrlResult.isSuccess())
    {
      System.out.println("Document Url call failed with error code " + documentUrlResult.getErrorCode());
      return;
    }
    System.out.println("Document URL: " + documentUrlResult.getUrl());
  }

  public static void imageList(String url, String apiKey, String key, boolean document) throws Exception
  {
    DocumentImageList documentImageList;
    if (document)
    {
      documentImageList = getService(url).getLatestImages(apiKey, key);
    }
    else
    {
      documentImageList = getService(url).getImagesByVersion(apiKey, key);
    }
    if (!documentImageList.isSuccess())
    {
      System.out.println("Image List call failed with error code " + documentImageList.getErrorCode());
      return;
    }
    ArrayOfDocumentPageImages documentPageImages = documentImageList.getPageImages();
    if (documentPageImages.getDocumentPageImages().size() == 0)
    {
      System.out.println("No images");
      return;
    }
    for (int i = 0; i < documentPageImages.getDocumentPageImages().size(); i++)
    {
      DocumentPageImages documentPageImage = documentPageImages.getDocumentPageImages().get(i);
      System.out.println("Document page " + (i + 1));
      System.out.println("\tsmall image URL: " + documentPageImage.getSmallImageUrl());
      System.out.println("\tmedium image URL: " + documentPageImage.getMediumImageUrl());
      System.out.println("\tlarge image URL: " + documentPageImage.getLargeImageUrl());
    }
  }

  public static void remove(String url, String apiKey, String key) throws Exception
  {
    RemoveDocumentResult removeDocumentResult = getService(url).removeDocument(apiKey, key);
    if (removeDocumentResult.isSuccess())
    {
      System.out.println("Document Successfully removed");
    }
    else
    {
      System.out.print("Error in deleting document: " + removeDocumentResult.getErrorCode());
      if (removeDocumentResult.getErrorMessage() != null)
        System.out.println(" " + removeDocumentResult.getErrorMessage());
      else
        System.out.println();
    }
  }

  public static void getFormData(String url, String apiKey, String key) throws Exception
  {
    GetFormDataResult getFormDataResult = getService(url).getFormData(apiKey, key);
    if (getFormDataResult.isSuccess())
    {
      System.out.println("Form Data CSV Values:");
      System.out.println(getFormDataResult.getFormDataCsv());
    }
    else
    {
      System.out.print("Error getting form data: " + getFormDataResult.getErrorCode());
      if (getFormDataResult.getErrorMessage() != null)
        System.out.println(" " + getFormDataResult.getErrorMessage());
      else
        System.out.println();
    }
  }

  public static void verifyUser(String url, String apiKey, String email, String password) throws Exception
  {
    UserVerificationInfo userVerificationInfo = getService(url).verifyUser(apiKey, email, password);
    System.out.println("User status is " + userVerificationInfo.getUserVerificationStatus());
  }

  public static void createEmbeddedWidget(String url, String apiKey, String fileName) throws Exception
  {
    WidgetCreationInfo widgetInfo = new WidgetCreationInfo();
    widgetInfo.setName("Test embedded widget " + new Date());
    widgetInfo.setFileInfos(getArrayOfFileInfos(fileName));

    EmbeddedWidgetCreationResult result = getService(url).createEmbeddedWidget(apiKey, null, widgetInfo);

    if (result.isSuccess())
      System.out.println("Embeddable Javascript: " + result.getJavascript());
    else
      System.out.println("Return code: " + result.getErrorCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));
  }

  public static String createPersonalEmbeddedWidget(String url, String apiKey, String fileName, String email) throws Exception
  {
    WidgetCreationInfo widgetInfo = new WidgetCreationInfo();
    widgetInfo.setName("Test embedded widget " + new Date());
    widgetInfo.setFileInfos(getArrayOfFileInfos(fileName));

    WidgetPersonalizationInfo personalInfo = new WidgetPersonalizationInfo();
    personalInfo.setEmail(email);
    personalInfo.setComment("API testing");

    EmbeddedWidgetCreationResult result = getService(url).createPersonalEmbeddedWidget(apiKey, null, widgetInfo, personalInfo);

    if (result.isSuccess()) {
      System.out.println("Embeddable Javascript: " + result.getJavascript());
      return result.getJavascript();
    }
    else {
      System.out.println("Return code: " + result.getErrorCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));
      return null;
    }
  }

  public static void personalizeEmbeddedWidget(String url, String apiKey, String javascript, String email) throws Exception
  {
    WidgetPersonalizationInfo personalInfo = new WidgetPersonalizationInfo();
    personalInfo.setEmail(email);
    personalInfo.setComment("API testing");

    EmbeddedWidgetCreationResult result = getService(url).personalizeEmbeddedWidget(apiKey, javascript, personalInfo);

    if (result.isSuccess())
      System.out.println("Embeddable Javascript: " + result.getJavascript());
    else
      System.out.println("Return code: " + result.getErrorCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));
  }

  public static void createUrlWidget(String url, String apiKey, String fileName) throws Exception
  {
    createUrlWidget(url, apiKey, fileName, null, true, 0);
  }

  public static void createUrlWidget(String url, String apiKey, String fileName, String completionUrl, boolean deframe, int delay) throws Exception
  {
    WidgetCompletionInfo completionInfo = null;
    if (completionUrl != null) {
      completionInfo = new WidgetCompletionInfo();
      completionInfo.setUrl(completionUrl);
      completionInfo.setDeframe(deframe);
      completionInfo.setDelay(delay);
    }

    WidgetCreationInfo widgetInfo = new WidgetCreationInfo();
    widgetInfo.setName("Test url widget " + new Date());
    widgetInfo.setFileInfos(getArrayOfFileInfos(fileName));
    widgetInfo.setWidgetCompletionInfo(completionInfo);

    UrlWidgetCreationResult result = getService(url).createUrlWidget(apiKey, null, widgetInfo);

    if (result.isSuccess())
      System.out.println("URL: " + result.getUrl());
    else
      System.out.println("Return code: " + result.getErrorCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));
  }

  public static String createPersonalUrlWidget(String url, String apiKey, String fileName, String email) throws Exception
  {
    WidgetCreationInfo widgetInfo = new WidgetCreationInfo();
    widgetInfo.setName("Test url widget " + new Date());
    widgetInfo.setFileInfos(getArrayOfFileInfos(fileName));

    WidgetPersonalizationInfo personalInfo = new WidgetPersonalizationInfo();
    personalInfo.setEmail(email);
    personalInfo.setComment("API testing");

    UrlWidgetCreationResult result = getService(url).createPersonalUrlWidget(apiKey, null, widgetInfo, personalInfo);

    if (result.isSuccess()) {
      System.out.println("URL: " + result.getUrl());
      return result.getUrl();
    }
    else {
      System.out.println("Return code: " + result.getErrorCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));
      return null;
    }
  }

  public static void personalizeUrlWidget(String url, String apiKey, String widgetUrl, String email) throws Exception
  {
    WidgetPersonalizationInfo personalInfo = new WidgetPersonalizationInfo();
    personalInfo.setEmail(email);
    personalInfo.setComment("API testing");

    UrlWidgetCreationResult result = getService(url).personalizeUrlWidget(apiKey, widgetUrl, personalInfo);

    if (result.isSuccess())
      System.out.println("URL: " + result.getUrl());
    else
      System.out.println("Return code: " + result.getErrorCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));
  }

  public static void getWidgets(String url, String apiKey, String email) throws Exception
  {
    UserCredentials userCredentials = new UserCredentials();
    userCredentials.setEmail(email);
    GetWidgetsForUserResult result = getService(url).getWidgetsForUser(apiKey, userCredentials);
    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfWidgetItem widgetItems = result.getWidgets();
    if(widgetItems == null)
    {
      System.out.println("Num widgets for user: 0");
      return;
    }
    printWidgetListItems(widgetItems);
  }

  public static void getMyWidgets(String url, String apiKey) throws Exception
  {
    GetWidgetsForUserResult result = getService(url).getMyWidgets(apiKey);
    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfWidgetItem widgetItems = result.getWidgets();
    if(widgetItems == null)
    {
      System.out.println("Num widgets for me: 0");
      return;
    }
    printWidgetListItems(widgetItems);
  }

  private static void printWidgetListItems(ArrayOfWidgetItem widgetItems)
  {
    printDocumentListItemHeader();
    for(WidgetItem item : widgetItems.getWidgetItem())
    {
      printWidgetItem(item);
    }
    System.out.println();
    System.out.format("Num widgets for user: %d%n", widgetItems.getWidgetItem().size());
  }

  private static void printWidgetItem(WidgetItem item)
  {
    Date date = item.getModifiedDate().toGregorianCalendar().getTime();
    //we probably don't want to print out the js snippet
    System.out.format("%-14s %-24s %tm/%td/%tY%n", item.getDocumentKey(), item.getName(), date, date, date);
  }

  public static void initiateInteractiveSend(String url, String apiKey, String fileName, String recipient) throws Exception
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

    InitiateInteractiveSendDocumentResult result = getService(url).initiateInteractiveSendDocument(apiKey, null, documentInfo, false, false);
    System.out.println("Interactive send URL is: " + result.getSendDocumentURL());
  }

  public static void sendDocumentInteractive(String url, String apiKey, String fileName, String recipient) throws Exception
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

    SendDocumentInteractiveOptions sendDocumentInteractiveOptions = new SendDocumentInteractiveOptions();
    sendDocumentInteractiveOptions.setAutoLoginUser(true);
    sendDocumentInteractiveOptions.setNoChrome(false);
    sendDocumentInteractiveOptions.setAuthoringRequested(true);

    SendDocumentInteractiveResult result = getService(url).sendDocumentInteractive(apiKey, null, documentInfo, sendDocumentInteractiveOptions);
    System.out.println("Send Document Interactive URL is: " + result.getUrl());
  }

  public static void createLibraryDocument(String url, String apiKey, String fileName) throws Exception
  {
    ArrayOfFileInfo fileInfos = getArrayOfFileInfos(fileName);

    LibraryDocumentCreationInfo libraryDocument = new LibraryDocumentCreationInfo();
    libraryDocument.setName("Test from SOAP: " + fileName);
    libraryDocument.setFileInfos(fileInfos);
    libraryDocument.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);
    libraryDocument.setSignatureType(SignatureType.ESIGN);
    libraryDocument.setLibrarySharingMode(LibrarySharingMode.USER);

    LibraryDocumentCreationResult result = getService(url).createLibraryDocument(apiKey, null, libraryDocument);
    System.out.println("DocumentKey is: " + result.getDocumentKey());
  }

  public static void getLibraryDocuments(String url, String apiKey, String email) throws Exception
  {
    UserCredentials userCredentials = new UserCredentials();
    userCredentials.setEmail(email);
    GetLibraryDocumentsForUserResult result = getService(url).getLibraryDocumentsForUser(apiKey, userCredentials);
    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfDocumentLibraryItem docItems = result.getLibraryDocuments();
    if(docItems == null)
    {
      System.out.println("Num library documents for user: 0");
      return;
    }
    printDocumentLibraryListItems(docItems);
  }

  public static void getMyLibraryDocuments(String url, String apiKey) throws Exception
  {
    GetLibraryDocumentsForUserResult result = getService(url).getMyLibraryDocuments(apiKey);
    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfDocumentLibraryItem docItems = result.getLibraryDocuments();
    if(docItems == null)
    {
      System.out.println("Num library documents for me: 0");
      return;
    }
    printDocumentLibraryListItems(docItems);
  }

  private static void printDocumentLibraryListItems(ArrayOfDocumentLibraryItem docItems)
  {
    printDocumentListItemHeader();
    for(DocumentLibraryItem item : docItems.getDocumentLibraryItem())
    {
      printDocumentLibraryItem(item);
    }
    System.out.println();
    System.out.format("Num library documents for user: %d%n", docItems.getDocumentLibraryItem().size());
  }

  private static void printDocumentLibraryItem(DocumentLibraryItem item)
  {
    Date date = item.getModifiedDate().toGregorianCalendar().getTime();
    System.out.format("%-14s %-24s %-10s %tm/%td/%tY%n", item.getDocumentKey(), item.getName(), item.getScope(), date, date, date);
  }

  public static void getAuditTrail(String url, String apiKey, String documentKey, String fileName) throws Exception
  {
    byte[] data = getService(url).getAuditTrail(apiKey, documentKey).getAuditTrailPdf();
    FileOutputStream stream = new FileOutputStream(new File(fileName));
    stream.write(data);
    stream.close();
  }

  public static void getSigningUrl(String url, String apiKey, String documentKey) throws Exception
  {
    SigningUrlResult result = getService(url).getSigningUrl(apiKey, documentKey);
    for (SigningUrl signingUrl : result.getSigningUrls().getSigningUrl())
      System.out.println(signingUrl.getEmail() + ": " + signingUrl.getEsignUrl() + " & " + signingUrl.getSimpleEsignUrl());
  }

  public static String sendDocumentWithExternalId(String url, String apiKey, String fileName, String recipient,
      String externalId) throws Exception {
    // If no External ID then just call the parent's sendDocument method
    if (externalId == null) {
      return EchoSignDocumentServiceDemo.sendDocument(url, apiKey, fileName, recipient);
    }

    ArrayOfFileInfo fileInfos = getArrayOfFileInfos(fileName);

    ArrayOfString tos = new ArrayOfString();
    tos.getString().add(recipient);

    DocumentCreationInfo documentInfo = new DocumentCreationInfo();
    documentInfo.setTos(tos);
    documentInfo.setName("Test from SOAP with External ID: " + fileName);
    documentInfo.setMessage("This is externally neat.");
    documentInfo.setFileInfos(fileInfos);
    documentInfo.setSignatureType(SignatureType.ESIGN);
    documentInfo.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);

    ExternalId extId = new ExternalId();
    extId.setId(externalId);
    extId.setNamespace("API_OTHER");
    documentInfo.setExternalId(extId);

    ArrayOfDocumentKey documentKeys = getService(url).sendDocument(apiKey, null, documentInfo);
    System.out.println("Document key is: " + documentKeys.getDocumentKey().get(0).getDocumentKey());
    return documentKeys.getDocumentKey().get(0).getDocumentKey();
  }

  public static void getDocumentInfosByExternalId(String url, String apiKey, String email, String password,
      String externalId) throws Exception {
    ExternalId extId = null;
    if (externalId != null) {
      extId = new ExternalId();
      extId.setId(externalId);
      extId.setNamespace("API_OTHER");
    }
    DocumentInfoList infos = getService(url).getDocumentInfosByExternalId(apiKey, email, password, extId);

    if (!infos.isSuccess()) {
      System.out.println("Return code: " + infos.getErrorCode() + " " + ((infos.getErrorMessage() != null) ? infos.getErrorMessage() : ""));
      return;
    }

    System.out.println(infos.getDocumentInfos().getDocumentInfo().size() + " documents match the given External ID");
    String versionKey;
    for (DocumentInfo info : infos.getDocumentInfos().getDocumentInfo()) {
      System.out.println("Document is in status: " + info.getStatus());
      System.out.println("Document history: ");

      for (DocumentHistoryEvent event: info.getEvents().getDocumentHistoryEvent()) {
        versionKey = event.getDocumentVersionKey();
        System.out.println("\t" + event.getDescription() + " on " + event.getDate() +
            (versionKey == null ? "" : " (versionKey: " + versionKey + ")"));
      }
      System.out.println("Latest versionKey: " + info.getLatestDocumentKey());
    }
  }

  public static void getDocuments(String url, String apiKey, String documentKey, String fileName,
                                  String versionKey, String userEmail, boolean combine) throws Exception
  {
    GetDocumentsOptions options = new GetDocumentsOptions();
    options.setVersionKey(versionKey);
    options.setParticipantEmail(userEmail);
    options.setCombine(combine);
    GetDocumentsResult result = getService(url).getDocuments(apiKey, documentKey, options);
    if (result.isSuccess()) {
      List<DocumentContent> docContentList = result.getDocuments().getDocumentContent();
      for (int docNdx=0; docNdx<docContentList.size(); ++docNdx) {
        DocumentContent docContent = docContentList.get(docNdx);
        System.out.println("Document Name=" + docContent.getName() + " Type=" + docContent.getMimetype() + " Size=" + docContent.getBytes().length);
        FileOutputStream stream = new FileOutputStream(new File(createDocumentFileName(fileName, docNdx, docContentList.size())));
        stream.write(docContent.getBytes());
        stream.close();
      }
    } else
      System.out.println("getDocuments call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
  }

  private static String createDocumentFileName(String fileName, int docNdx, int docNum)
  {
    if (docNum > 1 )
      return fileName.replaceFirst(Matcher.quoteReplacement(".pdf"), "_" + docNdx + ".pdf");
    else
      return fileName;
  }

  public static void getDocumentUrls(String url, String apiKey, String documentKey,
                                     String versionKey, String userEmail, boolean combine) throws Exception
  {
    GetDocumentUrlsOptions options = new GetDocumentUrlsOptions();
    options.setVersionKey(versionKey);
    options.setParticipantEmail(userEmail);
    options.setCombine(combine);
    options.setVersionKey(versionKey);
    GetDocumentUrlsResult result = getService(url).getDocumentUrls(apiKey, documentKey, options);
    if (result.isSuccess()) {
      for (DocumentUrl docUrl : result.getUrls().getDocumentUrl() )
        System.out.println("Document Name= " + docUrl.getName() + " URL=" + docUrl.getUrl());
    } else
      System.out.println("getDocumentUrls call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
  }

  public static void getDocumentImageUrls(String url, String apiKey, String documentKey,
                                          String versionKey, String userEmail, boolean combine) throws Exception
  {
    GetDocumentImageUrlsOptions options = new GetDocumentImageUrlsOptions();
    options.setVersionKey(versionKey);
    options.setParticipantEmail(userEmail);
    options.setVersionKey(versionKey);
    GetDocumentImageUrlsResult result = getService(url).getDocumentImageUrls(apiKey, documentKey, options);
    if (result.isSuccess()) {
      for (DocumentImageUrls docImageUrls : result.getImageUrls().getDocumentImageUrls()) {
        List<PageImageUrls> pageImageUrlsList = docImageUrls.getPages().getPageImageUrls();
        System.out.println("Document Name= " + docImageUrls.getName() + " Pages=" + pageImageUrlsList.size());
        for (int pageNdx=0; pageNdx<pageImageUrlsList.size(); ++pageNdx) {
          List<PageImageUrl> imageUrlList = pageImageUrlsList.get(pageNdx).getImages().getPageImageUrl();
          System.out.println("  Page= " + (++pageNdx) + " Images=" + imageUrlList.size());
          for (PageImageUrl imageUrl : imageUrlList)
           System.out.println("  Width= " + imageUrl.getWidth() + " Url=" + imageUrl.getUrl());
        }
      }
    } else
      System.out.println("getDocumentUrls call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
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

      if (command.equals("sendDocumentMegaSign"))
      {
        if (args.length < 5)
          return false;
        String[] tos = new String[args.length - 4];
        final String filename = args[3];
        for(int i = 4; i< args.length; i++)
          tos[i - 4] = args[i];
        sendDocumentMegaSign(url, apiKey, filename, tos);
      }
      else if (command.equals("users"))
      {
        getUsersInAccount(url, apiKey);
      }
      else if (command.equals("docs"))
      {
         if (args.length != 4)
          return false;
        getDocumentsForUser(url, apiKey, args[3]);
      }
      else if (command.equals("userDocs"))
      {
         if (args.length != 4)
          return false;
        getUserDocuments(url, apiKey, args[3]);
      }
      else if (command.equals("myDocs"))
      {
        getMyDocuments(url, apiKey);
      }

      else if (command.equals("megadoc"))
      {
         if (args.length != 4)
           return false;
        getMegaSignDocument(url, apiKey, args[3]);
      }

      else if (command.equals("createUser"))
      {
        if (args.length != 5)
          return false;
        createUser(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("createAccount"))
      {
        if (args.length != 5)
          return false;
        createAccount(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("version"))
      {
        if (args.length != 5)
          return false;
        getDocument(url, apiKey, null, args[3], args[4]);
      }
      else if (command.equals("cancel"))
      {
        if (args.length != 4)
          return false;
        cancelDocument(url, apiKey, args[3]);
      }
      else if (command.equals("remind"))
      {
        if (args.length != 4)
          return false;
        sendReminder(url, apiKey, args[3]);
      }
      else if (command.equals("getLatestDocumentUrl"))
      {
        if (args.length != 4)
          return false;
        documentUrl(url, apiKey, args[3], true);
      }
      else if (command.equals("getDocumentUrlByVersion"))
      {
        if (args.length != 4)
          return false;
        documentUrl(url, apiKey, args[3], false);
      }
      else if (command.equals("getLatestImages"))
      {
        if (args.length != 4)
          return false;
        imageList(url, apiKey, args[3], true);
      }
      else if (command.equals("getImagesByVersion"))
      {
        if (args.length != 4)
          return false;
        imageList(url, apiKey, args[3], false);
      }
      else if (command.equals("removeDocument"))
      {
        if (args.length != 4)
          return false;
        remove(url, apiKey, args[3]);
      }
      else if (command.equals("getFormData"))
      {
        if (args.length != 4)
          return false;
        getFormData(url, apiKey, args[3]);
      }
      else if (command.equals("verifyUser"))
      {
        if (args.length != 5)
          return false;
        verifyUser(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("createEmbeddedWidget"))
      {
        if (args.length != 4)
          return false;
        createEmbeddedWidget(url, apiKey, args[3]);
      }
      else if (command.equals("createPersonalEmbeddedWidget"))
      {
        if (args.length != 5)
          return false;
        createPersonalEmbeddedWidget(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("personalizeEmbeddedWidget"))
      {
        if (args.length != 5)
          return false;
        personalizeEmbeddedWidget(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("createUrlWidget"))
      {
        if (args.length != 4)
          return false;
        createUrlWidget(url, apiKey, args[3]);
      }
      else if (command.equals("createUrlWidgetWithRedirect"))
      {
        if (args.length != 7)
          return false;
        createUrlWidget(url, apiKey, args[3], args[4], Boolean.parseBoolean(args[5]), Integer.parseInt(args[6]));
      }
      else if (command.equals("createPersonalUrlWidget"))
      {
        if (args.length != 5)
          return false;
        createPersonalUrlWidget(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("personalizeUrlWidget"))
      {
        if (args.length != 5)
          return false;
        personalizeUrlWidget(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("interactiveSend"))
      {
        if (args.length != 5)
          return false;
        initiateInteractiveSend(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("widgets"))
      {
        if (args.length != 4)
          return false;
        getWidgets(url, apiKey, args[3]);
      }
      else if (command.equals("myWidgets"))
      {
        getMyWidgets(url, apiKey);
      }
      else if (command.equals("createLibraryDocument"))
      {
        if (args.length != 4)
          return false;
        createLibraryDocument(url, apiKey, args[3]);
      }
      else if (command.equals("getLibraryDocuments"))
      {
        if (args.length != 4)
          return false;
        getLibraryDocuments(url, apiKey, args[3]);
      }
      else if (command.equals("myLibraryDocuments"))
      {
        getMyLibraryDocuments(url, apiKey);
      }
      else if (command.equals("audit"))
      {
        if (args.length != 5)
          return false;
        getAuditTrail(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("signingUrl"))
      {
        if (args.length != 4)
          return false;
        getSigningUrl(url, apiKey, args[3]);
      }
      else if (command.equals("infosByExternalId"))
      {
        if (args.length < 4)
          return false;
        String email = (args.length > 4 ? args[4] : null);
        String password = (args.length > 5 ? args[5] : null);
        getDocumentInfosByExternalId(url, apiKey, email, password, args[3]);
      }
      else if (command.equals("getDocuments"))
      {
        if (args.length < 5)
          return false;
        String versionKey = (args.length > 5 ? args[5] : null);
        String userEmail = (args.length > 6 ? args[6] : null);
        boolean combine = (args.length > 7 ? args[7].equals("true") : true);
        getDocuments(url, apiKey, args[3], args[4], versionKey, userEmail, combine);
      }
      else if (command.equals("getDocumentUrls"))
      {
        if (args.length < 4)
          return false;
        String versionKey = (args.length > 4 ? args[4] : null);
        String userEmail = (args.length > 5 ? args[5] : null);
        boolean combine = (args.length > 6 ? args[6].equals("true") : true);
        getDocumentUrls(url, apiKey, args[3], versionKey, userEmail, combine);
      }
      else if (command.equals("getDocumentImageUrls"))
      {
        if (args.length < 4)
          return false;
        String versionKey = (args.length > 4 ? args[4] : null);
        String userEmail = (args.length > 5 ? args[5] : null);
        boolean combine = (args.length > 6 ? args[6].equals("true") : true);
        getDocumentImageUrls(url, apiKey, args[3], versionKey, userEmail, combine);
      }
      else if (command.equals("latest"))
      {
        if (args.length != 5)
          return false;
        getDocument(url, apiKey, args[3], args[4]);
      }


      else
      {
        return EchoSignDocumentServiceDemo.process(args);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return true;
  }

  public static void usage()
  {
    usage1();
    usage2();
    System.err.println("  version <versionKey> <filename>");
    System.err.println("  cancel <documentKey>");
    System.err.println("  remind <documentKey>");
    System.err.println("  getLatestDocumentUrl <documentKey>");
    System.err.println("  getDocumentUrlByVersion <versionKey>");
    System.err.println("  getLatestImages <documentKey>");
    System.err.println("  getImagesByVersion <versionKey>");
    System.err.println("  users");
    System.err.println("  docs <userKey>");
    System.err.println("  userDocs <email>");
    System.err.println("  myDocs");
    System.err.println("  megadoc <documentKey>");
    System.err.println("  removeDocument <documentKey>");
    System.err.println("  getFormData <documentKey>");
    System.err.println("  sendDocumentMegaSign <filename> <signer_email1> <signer_email2> ...");
    System.err.println("  verifyUser <email> <password>");
    System.err.println("  createUser <email> <password>");
    System.err.println("  createAccount <email> <password>");
    System.err.println("  createEmbeddedWidget <filename>");
    System.err.println("  createPersonalEmbeddedWidget <filename> <signer_email>");
    System.err.println("  personalizeEmbeddedWidget <javascript> <signer_email>");
    System.err.println("  createUrlWidget <filename>");
    System.err.println("  createUrlWidgetWithRedirect <filename> <completion_url> <deframe> <delay_in_seconds>");
    System.err.println("  createPersonalUrlWidget <filename> <signer_email>");
    System.err.println("  personalizeUrlWidget <url> <signer_email>");
    System.err.println("  interactiveSend <filename> <recipient_email>");
    System.err.println("  widgets <email>");
    System.err.println("  myWidgets");
    System.err.println("  createLibraryDocument <filename>");
    System.err.println("  getLibraryDocuments <email>");
    System.err.println("  myLibraryDocuments");
    System.err.println("  audit <documentKey> <filename>");
    System.err.println("  signingUrl <documentKey>");
    System.err.println("  infosByExternalId <email> <password> <externalId>");
    System.err.println("  getDocuments <documentKey> <fileName> [<versionKey> <userEmail> <combine>]");
    System.err.println("  getDocumentUrls <documentKey> [<versionKey> <userEmail> <combine>]");
    System.err.println("  getDocumentImageUrls <documentKey> [<versionKey> <userEmail> <combine>]");
    usage3();
    System.err.println("version saves a specific version of the document (referenced by the history events)");
    System.err.println("");
    System.err.println("cancel terminates a transaction and notified all parties that it's been cancelled");
    System.err.println("");
    System.err.println("remind sends another email to the person whose turn it is to sign that document");
    System.err.println("");
    System.err.println("getLatestDocumentUrl returns the URL which points to the PDF of the latest version of the document");
    System.err.println("");
    System.err.println("getDocumentUrlByVersion returns the URL which point to the PDF of the specified version of a document");
    System.err.println("");
    System.err.println("getLatestImages returns URLs which point to the images of the pages of a document");
    System.err.println("");
    System.err.println("getImagesByVersion returns URLs which point to the images of the pages of the specified version of a document");
    System.err.println("");
    System.err.println("users lists all users in the account of the API key holder");
    System.err.println("");
    System.err.println("docs lists all the visible documents of the specified user");
    System.err.println("");
    System.err.println("userDocs lists all the visible documents of the specified user");
    System.err.println("");
    System.err.println("myDocs lists all the visible documents of the current API user");
    System.err.println("");
    System.err.println("megadoc lists all the child documents of the specified documentKey");
    System.err.println("");
    System.err.println("removeDocument removes the document from the system");
    System.err.println("");
    System.err.println("getFormData returns a CSV data structure with the form data for the specified document or form");
    System.err.println("");
    System.err.println("sendDocumentMegaSign sends a document to multiple signers simultaneously");
    System.err.println("");
    System.err.println("verifyUser returns the status of the user in the system");
    System.err.println("");
    System.err.println("createUser creates a new EchoSign user in the system");
    System.err.println("");
    System.err.println("createAccount creates a new paying account in the system");
    System.err.println("");
    System.err.println("createEmbeddedWidget will return the embeddable javascript for a signable widget of the provided file");
    System.err.println("");
    System.err.println("createPersonalEmbeddedWidget will return the embeddable javascript widget intended for a specific signer");
    System.err.println("");
    System.err.println("personalizeEmbeddedWidget will return transform the widget javascript to be intended for a specific signer");
    System.err.println("");
    System.err.println("createUrlWidget will return the url for a signable widget of the provided file");
    System.err.println("");
    System.err.println("createPersonalUrlWidget will return the url widget intended for a specific signer");
    System.err.println("");
    System.err.println("personalizeUrlWidget will return transform the widget url to be intended for a specific signer");
    System.err.println("");
    System.err.println("interactiveSend returns a URL where the user can complete the process of sending the provided file(s)");
    System.err.println("");
    System.err.println("widgets will return the list of all the widgets available to a given user");
    System.err.println("");
    System.err.println("myWidgets will return the list of all the widgets available to the current API user");
    System.err.println("");
    System.err.println("createLibraryDocument will add the specified file to the user's document library");
    System.err.println("");
    System.err.println("getLibraryDocuments will return the list of all the library documents available to a given user");
    System.err.println("");
    System.err.println("myLibraryDocuments will return the list of all the library documents available to the current API user");
    System.err.println("");
    System.err.println("audit saves the document audit trail as a PDF with the given filename");
    System.err.println("");
    System.err.println("signingUrl retrieves the authenticated url for the esign page for the current signer");
    System.err.println("");
    System.err.println("infosByExternalId retrieves the list of documents that match the given external ID");
    System.err.println("");
    System.err.println("getDocuments retrieves the PDF files that match the given document");
    System.err.println("");
    System.err.println("getDocumentUrls retrieves the URLs that match the given document");
    System.err.println("");
    System.err.println("getDocumentImageUrls retrieves the page image URLs that match the given document");
    System.err.println("");
  }
}
