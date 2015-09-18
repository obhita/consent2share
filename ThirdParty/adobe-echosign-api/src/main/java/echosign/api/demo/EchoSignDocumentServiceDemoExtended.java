package echosign.api.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import echosign.api.clientv20.ArrayOfString;
import echosign.api.clientv20.dto.AppliesTo;
import echosign.api.clientv20.dto.ArrayOfDocumentPageImages;
import echosign.api.clientv20.dto.ArrayOfFileInfo;
import echosign.api.clientv20.dto.CancelDocumentResult;
import echosign.api.clientv20.dto.DisplayUserInfo;
import echosign.api.clientv20.dto.DocumentCreationInfo;
import echosign.api.clientv20.dto.DocumentImageList;
import echosign.api.clientv20.dto.DocumentPageImages;
import echosign.api.clientv20.dto.DocumentUrlResult;
import echosign.api.clientv20.dto.ExternalId;
import echosign.api.clientv20.dto.FormCreationInfo;
import echosign.api.clientv20.dto.OptIn;
import echosign.api.clientv20.dto.RemoveDocumentResult;
import echosign.api.clientv20.dto.Result;
import echosign.api.clientv20.dto.SecurityOptions;
import echosign.api.clientv20.dto.SendReminderResult;
import echosign.api.clientv20.dto.SignatureFlow;
import echosign.api.clientv20.dto.SignatureType;
import echosign.api.clientv20.dto.UserCreationInfo;
import echosign.api.clientv20.dto.UserVerificationInfo;
import echosign.api.clientv20.dto12.SendDocumentInteractiveOptions;
import echosign.api.clientv20.dto14.ArrayOfRecipientInfo;
import echosign.api.clientv20.dto14.DocumentContent;
import echosign.api.clientv20.dto14.DocumentUrl;
import echosign.api.clientv20.dto14.GetDocumentImageUrlsOptions;
import echosign.api.clientv20.dto14.GetDocumentUrlsOptions;
import echosign.api.clientv20.dto14.GetDocumentUrlsResult;
import echosign.api.clientv20.dto14.GetDocumentsOptions;
import echosign.api.clientv20.dto14.GetSupportingDocumentsOptions;
import echosign.api.clientv20.dto14.SigningUrlResult;
import echosign.api.clientv20.dto14.SupportingDocumentContentFormat;
import echosign.api.clientv20.dto15.DeleteGroupResult;
import echosign.api.clientv20.dto15.MoveUsersToGroupResult;
import echosign.api.clientv20.dto15.RenameGroupResult;
import echosign.api.clientv20.dto15.UsersToMoveInfo;
import echosign.api.clientv20.dto16.ArrayOfDocumentKey;
import echosign.api.clientv20.dto16.ArrayOfUserInfo;
import echosign.api.clientv20.dto16.ArrayOfWidgetItem;
import echosign.api.clientv20.dto16.CreateGroupResult;
import echosign.api.clientv20.dto16.DisableWidgetOptions;
import echosign.api.clientv20.dto16.DisableWidgetResult;
import echosign.api.clientv20.dto16.DocumentImageUrls;
import echosign.api.clientv20.dto16.EmbeddedWidgetCreationResult;
import echosign.api.clientv20.dto16.EnableWidgetOptions;
import echosign.api.clientv20.dto16.EnableWidgetResult;
import echosign.api.clientv20.dto16.GetDocumentImageUrlsResult;
import echosign.api.clientv20.dto16.GetDocumentsResult;
import echosign.api.clientv20.dto16.GetGroupsInAccountResult;
import echosign.api.clientv20.dto16.GetSupportingDocumentsResult;
import echosign.api.clientv20.dto16.GetUsersInAccountResult;
import echosign.api.clientv20.dto16.GetUsersInGroupResult;
import echosign.api.clientv20.dto16.GetWidgetsForUserResult;
import echosign.api.clientv20.dto16.GroupInfo;
import echosign.api.clientv20.dto16.LibraryDocumentCreationResult;
import echosign.api.clientv20.dto16.PageImageUrl;
import echosign.api.clientv20.dto16.PageImageUrls;
import echosign.api.clientv20.dto16.SendDocumentInteractiveResult;
import echosign.api.clientv20.dto16.SendDocumentMegaSignResult;
import echosign.api.clientv20.dto16.SupportingDocument;
import echosign.api.clientv20.dto16.UrlWidgetCreationResult;
import echosign.api.clientv20.dto16.UserInfo;
import echosign.api.clientv20.dto16.WidgetItem;
import echosign.api.clientv20.dto17.ArrayOfDocumentLibraryItem;
import echosign.api.clientv20.dto17.ArrayOfDocumentListItem;
import echosign.api.clientv20.dto17.ArrayOfLibraryTemplateType;
import echosign.api.clientv20.dto17.DelegateSigningOptions;
import echosign.api.clientv20.dto17.DelegateSigningResult;
import echosign.api.clientv20.dto17.DocumentLibraryItem;
import echosign.api.clientv20.dto17.DocumentListItem;
import echosign.api.clientv20.dto17.GetDocumentsForUserResult;
import echosign.api.clientv20.dto17.GetLibraryDocumentsForUserResult;
import echosign.api.clientv20.dto17.GetMegaSignDocumentResult;
import echosign.api.clientv20.dto17.LibraryTemplateType;
import echosign.api.clientv20.dto17.NotifyDocumentVaultedResult;
import echosign.api.clientv20.dto17.RejectDocumentOptions;
import echosign.api.clientv20.dto17.RejectDocumentResult;
import echosign.api.clientv20.dto17.ReplaceSignerOptions;
import echosign.api.clientv20.dto17.ReplaceSignerResult;
import echosign.api.clientv20.dto17.VaultEventInfo;
import echosign.api.clientv20.dto18.ArrayOfRecipientSecurityOption;
import echosign.api.clientv20.dto18.AuthenticationMethod;
import echosign.api.clientv20.dto18.GetUserInfoOptions;
import echosign.api.clientv20.dto18.GetUserInfoResult;
import echosign.api.clientv20.dto18.RecipientSecurityOption;
import echosign.api.clientv20.dto18.SearchUserDocumentsOptions;
import echosign.api.clientv20.dto19.ArrayOfPhoneInfo;
import echosign.api.clientv20.dto19.EmbeddedViewOptions;
import echosign.api.clientv20.dto19.EmbeddedViewTarget;
import echosign.api.clientv20.dto19.GetEmbeddedViewResult;
import echosign.api.clientv20.dto19.PhoneInfo;
import echosign.api.clientv20.dto20.DocumentEventsForUserOptions;
import echosign.api.clientv20.dto20.DocumentHistoryEvent;
import echosign.api.clientv20.dto20.DocumentInfo;
import echosign.api.clientv20.dto20.DocumentInfoList;
import echosign.api.clientv20.dto20.GetDocumentEventsForUserResult;
import echosign.api.clientv20.dto7.AccountCreationInfo;
import echosign.api.clientv20.dto7.AccountType;
import echosign.api.clientv20.dto7.CreateAccountResult;
import echosign.api.clientv20.dto8.GetFormDataResult;
import echosign.api.clientv20.dto8.WidgetCompletionInfo;
import echosign.api.clientv20.dto8.WidgetCreationInfo;
import echosign.api.clientv20.dto8.WidgetPersonalizationInfo;
import echosign.api.clientv20.dto9.InitiateInteractiveSendDocumentResult;
import echosign.api.clientv20.dto9.LibraryDocumentCreationInfo;
import echosign.api.clientv20.dto9.LibrarySharingMode;
import echosign.api.clientv20.dto9.SigningUrl;
import echosign.api.clientv20.dto9.UserCredentials;

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
    userInfo.setCountryCode("US");
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
    userInfo.setCountryCode("US");
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
    return String.format("%tk:%tM:%tS %tm/%td/%ty", date, date, date, date, date, date);
  }

  public static String sendDocumentMegaSign(String url, String apiKey, String fileName, String formFieldLayerTemplateKey, String[] emails) throws Exception
  {
    ArrayOfRecipientInfo recipientInfos = createArrayOfRecipientInfosAllSigners(emails);
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipientInfos, fileName, formFieldLayerTemplateKey, testPrefix + "sendDocumentMegaSign: ", testMessage);
    SendDocumentMegaSignResult sendDocumentResult = getService(url).sendDocumentMegaSign(apiKey, null, documentInfo);

    if (!sendDocumentResult.isSuccess())
    {
      System.out.println("sendDocumentMegaSign failed. Error code " + sendDocumentResult.getErrorCode() +
          " Error Message " + sendDocumentResult.getErrorMessage());
      return null;
    }

    System.out.println("Mega doc key " + sendDocumentResult.getDocumentKey().getDocumentKey());
    ArrayOfDocumentKey documentKeys = sendDocumentResult.getDocumentKeyArray();
    for (int i = 0; i < documentKeys.getDocumentKey().size(); i++)
      System.out.println("Document key [" + i + "] " + documentKeys.getDocumentKey().get(i).getDocumentKey());
    return sendDocumentResult.getDocumentKey().getDocumentKey();
  }

  public static Map<String,String> getUsersInAccount(String url, String apiKey) throws Exception
  {
    GetUsersInAccountResult result = getService(url).getUsersInAccount(apiKey);

    System.out.println();
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
    ArrayOfUserInfo userInfos = result.getUserListForAccount();
    if(userInfos == null)
    {
      System.out.println("Num users in account: 0");
      return null;
    }

    Map<String,String> userMap = new HashMap<String,String>();
    System.out.format("%-14s %-35s %-30s%n", "userKey", "email", "fullNameOrEmail");
    System.out.format("-------------- ----------------------------------- ------------------------------  %n");
    for(UserInfo ui : userInfos.getUserInfo())
    {
      userMap.put(ui.getEmail(), ui.getUserKey());
      System.out.format("%-14s %-35s %-30s%n", ui.getUserKey(), ui.getEmail(), ui.getFullNameOrEmail());
    }
    System.out.println();
    System.out.format("Num users in account: %d%n", userInfos.getUserInfo().size());
    return userMap;
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
    System.out.format("%-77s %tm/%td/%tY %-24s %-2s %-2s %-20s %-20s %s%n",
                      item.getDocumentKey(), date, date, date, item.getUserDocumentStatus().toString(),
                      esign, megaSign, du.getFullNameOrEmail(), company, item.getName());
  }

  static void printDocumentListItemHeader()
  {
    System.out.format("%-77s %-10s %-24s %-2s %-2s %-20s %-20s %s%n",
                      "key", "date", "status", "e?", "m?", "user", "company", "name");
    System.out.format("----------------------------------------------------------------------------- ");
    System.out.format("---------- ");
    System.out.format("------------------------ ");
    System.out.format("-- ");
    System.out.format("-- ");
    System.out.format("-------------------- ");
    System.out.format("-------------------- ");
    System.out.format("-------------------- ");
    System.out.println();
  }

  public static void getDocument(String url, String apiKey, String documentKey, String versionKey, String fileName) throws Exception
  {
    byte[] data = (documentKey != null) ? getService(url).getLatestDocument(apiKey, documentKey) : getService(url).getDocumentByVersion(apiKey, versionKey);
    FileOutputStream stream = new FileOutputStream(new File(fileName));
    try {
      stream.write(data);
    } finally {
      stream.close();
    }
  }

  public static void cancelDocument(String url, String apiKey, String documentKey) throws Exception
  {
    CancelDocumentResult result = getService(url).cancelDocument(apiKey, documentKey, "Cancelled by the sender", true);
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
    widgetInfo.setFileInfos(createArrayOfFileInfos(new String[]{fileName}));

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
    widgetInfo.setFileInfos(createArrayOfFileInfos(new String[]{fileName}));

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

  public static String createUrlWidget(String url, String apiKey, String fileName) throws Exception
  {
    return createUrlWidget(url, apiKey, fileName, null, null, true, 0);
  }

  public static String createUrlWidget(String url, String apiKey, String fileName, String formFieldLayerTemplateKey, String completionUrl, boolean deframe, int delay) throws Exception
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
    widgetInfo.setFileInfos(createArrayOfFileInfos(new String[]{fileName}));
    widgetInfo.setWidgetCompletionInfo(completionInfo);

    ArrayOfFileInfo formFieldLayerTemplates = (formFieldLayerTemplateKey != null) ? createFormFieldLayerTemplates(new String[]{formFieldLayerTemplateKey}) : null;
    widgetInfo.setFormFieldLayerTemplates(formFieldLayerTemplates);

    UrlWidgetCreationResult result = getService(url).createUrlWidget(apiKey, null, widgetInfo);

    if (result.isSuccess())
      System.out.println("URL: " + result.getUrl());
    else
      System.out.println("Return code: " + result.getErrorCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));

    return result.getDocumentKey();
  }

  public static String createPersonalUrlWidget(String url, String apiKey, String fileName, String email) throws Exception
  {
    WidgetCreationInfo widgetInfo = new WidgetCreationInfo();
    widgetInfo.setName("Test url widget " + new Date());
    widgetInfo.setFileInfos(createArrayOfFileInfos(new String[]{fileName}));

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

  public static void disableWidget(String url, String apiKey, String documentKey, String redirectUrl, String message) throws Exception
  {
    DisableWidgetOptions options = new DisableWidgetOptions();
    options.setRedirectUrl(redirectUrl);
    options.setMessage(message);

    DisableWidgetResult result = getService(url).disableWidget(apiKey, documentKey, options);

    System.out.println("Return code: " + result.getResultCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));
  }

  public static void enableWidget(String url, String apiKey, String documentKey) throws Exception
  {
    EnableWidgetOptions options = new EnableWidgetOptions();

    EnableWidgetResult result = getService(url).enableWidget(apiKey, documentKey, options);

    System.out.println("Return code: " + result.getResultCode() + " " + ((result.getErrorMessage() != null) ? result.getErrorMessage() : ""));
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
    printWidgetListItemHeader();
    for(WidgetItem item : widgetItems.getWidgetItem())
    {
      printWidgetItem(item);
    }
    System.out.println();
    System.out.format("Num widgets for user: %d%n", widgetItems.getWidgetItem().size());
  }

  static void printWidgetListItemHeader()
  {
    System.out.format("%-77s %-10s %-8s %-2s %s%n",
                      "key", "date", "status", "e?", "name");
    System.out.format("----------------------------------------------------------------------------- ");
    System.out.format("---------- ");
    System.out.format("-------- ");
    System.out.format("-- ");
    System.out.format("-------------------- ");
    System.out.println();
  }

  private static void printWidgetItem(WidgetItem item)
  {
    Date date = item.getModifiedDate().toGregorianCalendar().getTime();
    String embedded = (item.getJavascript() != null) ? "Y" : "N";
    //we probably don't want to print out the js snippet
    System.out.format("%-77s %tm/%td/%tY %-8s %-2s %s%n", item.getDocumentKey(), date, date, date, item.getReusableDocumentStatus(), embedded, item.getName());
  }

  public static void initiateInteractiveSend(String url, String apiKey, String fileName, String recipient) throws Exception
  {
    ArrayOfRecipientInfo recipients = createArrayOfRecipientInfosAllSigners(new String[]{recipient});
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipients, fileName, null, "Test from SOAP:", testMessage);
    InitiateInteractiveSendDocumentResult result = getService(url).initiateInteractiveSendDocument(apiKey, null, documentInfo, false, false);
    System.out.println("Interactive send URL is: " + result.getSendDocumentURL());
  }

  public static void sendDocumentInteractive(String url, String apiKey, String fileName, String recipient) throws Exception
  {
    ArrayOfRecipientInfo recipients = createArrayOfRecipientInfosAllSigners(new String[]{recipient});
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipients, fileName, null, testPrefix, testMessage);
    SendDocumentInteractiveOptions sendDocumentInteractiveOptions = new SendDocumentInteractiveOptions();
    sendDocumentInteractiveOptions.setAutoLoginUser(true);
    sendDocumentInteractiveOptions.setNoChrome(false);
    sendDocumentInteractiveOptions.setAuthoringRequested(true);

    SendDocumentInteractiveResult result = getService(url).sendDocumentInteractive(apiKey, null, documentInfo, sendDocumentInteractiveOptions);
    System.out.println("Send Document Interactive URL is: " + result.getUrl());
  }

  public static void createLibraryDocumentInteractive(String url, String apiKey, String fileName) throws Exception
  {
    ArrayOfFileInfo fileInfos = createArrayOfFileInfos(new String[]{fileName});

    LibraryDocumentCreationInfo libraryDocument = new LibraryDocumentCreationInfo();
    libraryDocument.setName(testPrefix + fileName);
    libraryDocument.setFileInfos(fileInfos);
    libraryDocument.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);
    libraryDocument.setSignatureType(SignatureType.ESIGN);
    libraryDocument.setLibrarySharingMode(LibrarySharingMode.USER);

    SendDocumentInteractiveOptions sendDocumentInteractiveOptions = new SendDocumentInteractiveOptions();
    sendDocumentInteractiveOptions.setAutoLoginUser(true);
    sendDocumentInteractiveOptions.setNoChrome(false);
    sendDocumentInteractiveOptions.setAuthoringRequested(true);

    SendDocumentInteractiveResult result = getService(url).createLibraryDocumentInteractive(apiKey, null, libraryDocument, sendDocumentInteractiveOptions);
    System.out.println("Send Document Interactive URL is: " + result.getUrl());
  }

  public static String createLibraryDocument(String url, String apiKey, String fileName) throws Exception
  {
    ArrayOfFileInfo fileInfos = createArrayOfFileInfos(new String[]{fileName});

    LibraryDocumentCreationInfo libraryDocument = new LibraryDocumentCreationInfo();
    libraryDocument.setName(testPrefix + fileName);
    libraryDocument.setFileInfos(fileInfos);
    libraryDocument.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);
    libraryDocument.setSignatureType(SignatureType.ESIGN);
    libraryDocument.setLibrarySharingMode(LibrarySharingMode.USER);

    LibraryDocumentCreationResult result = getService(url).createLibraryDocument(apiKey, null, libraryDocument);
    System.out.println("DocumentKey is: " + result.getDocumentKey());
    return result.getDocumentKey();
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
    printDocumentLibraryListItemHeader();
    for(DocumentLibraryItem item : docItems.getDocumentLibraryItem())
    {
      printDocumentLibraryItem(item);
    }
    System.out.println();
    System.out.format("Num library documents for user: %d%n", docItems.getDocumentLibraryItem().size());
  }

  static void printDocumentLibraryListItemHeader()
  {
    System.out.format("%-77s %-10s %-8s %-7s %s%n",
                      "key", "date", "scope", "types", "name");
    System.out.format("----------------------------------------------------------------------------- ");
    System.out.format("---------- ");
    System.out.format("-------- ");
    System.out.format("------- ");
    System.out.format("-------------------- ");
    System.out.println();
  }

  private static void printDocumentLibraryItem(DocumentLibraryItem item)
  {
    Date date = item.getModifiedDate().toGregorianCalendar().getTime();
    String types = null;
    if (item.getLibraryTemplateTypes().getLibraryTemplateType().contains(LibraryTemplateType.DOCUMENT))
      types = "DOC";
    if (item.getLibraryTemplateTypes().getLibraryTemplateType().contains(LibraryTemplateType.FORM_FIELD_LAYER))
      types = (types == null) ? "FFL" : types + "/FFL";
    System.out.format("%-77s %tm/%td/%tY %-8s %-7s %s%n", item.getDocumentKey(), date, date, date, item.getScope(), types, item.getName());
  }

  public static void getAuditTrail(String url, String apiKey, String documentKey, String filename) throws Exception
  {
    byte[] data = getService(url).getAuditTrail(apiKey, documentKey).getAuditTrailPdf();
    FileOutputStream stream = getFileStream(filename);
    try {
      stream.write(data);
    } finally {
      stream.close();
    }
  }

  public static void getSigningUrl(String url, String apiKey, String documentKey) throws Exception
  {
    SigningUrlResult result = getService(url).getSigningUrl(apiKey, documentKey);
    for (SigningUrl signingUrl : result.getSigningUrls().getSigningUrl())
      System.out.println(signingUrl.getEmail() + ": " + signingUrl.getEsignUrl());
  }

  public static String sendDocumentWithExternalId(String url, String apiKey, String fileName, String recipient, String externalId) throws Exception
  {
    // If no External ID then just call the parent's sendDocument method
    if (externalId == null) {
      return EchoSignDocumentServiceDemo.sendDocument(url, apiKey, fileName, recipient);
    }

    ArrayOfRecipientInfo recipients = createArrayOfRecipientInfosAllSigners(new String[]{recipient});
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipients, fileName, null, "Test from SOAP with External ID: ", "This is externally neat.");

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

  public static void getDocuments(String url, String apiKey, String documentKey, String fileDirPath,
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
        FileOutputStream stream = new FileOutputStream(new File(createDocumentFileName(fileDirPath, docNdx, docContentList.size())));
        try {
          stream.write(docContent.getBytes());
        } finally {
          stream.close();
        }
      }
    } else
      System.out.println("getDocuments call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
  }

  private static String createDocumentFileName(String fileDirPath, int docNdx, int docNum)
  {
    String fileName = new File(new File(fileDirPath), "foo.pdf").getAbsolutePath();
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

  public static void getSupportingDocuments(String url, String apiKey, String documentKey, String baseFilePath) throws Exception
  {
    GetSupportingDocumentsOptions options = new GetSupportingDocumentsOptions();
    options.setDocumentFormatRequested(SupportingDocumentContentFormat.CONVERTED_PDF);
    GetSupportingDocumentsResult result = getService(url).getSupportingDocuments(apiKey, documentKey, null, options);
    if (!result.isSuccess()) {
      System.out.println("getSupportingDocuments call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
      return;
    }
    List<SupportingDocument> docList = result.getSupportingDocuments().getSupportingDocument();
    if (docList.isEmpty()) {
      return;
    }
    for (SupportingDocument doc : docList) {
      System.out.println("SupportingDocumentKey=" + doc.getSupportingDocumentKey() + ", fieldName=" + doc.getFieldName() + ", displayLabel=" + doc.getDisplayLabel() + ", mimeType=" + doc.getMimeType());
      File dest = new File(baseFilePath + "_" + doc.getFieldName() + ".pdf");
      FileOutputStream stream = new FileOutputStream(dest);
      System.out.println("Saved to: " + dest.getAbsolutePath());
      try {
        stream.write(doc.getContent());
      } finally {
        stream.close();
      }
    }
  }

  public static String createGroup(String url, String apiKey, String name) throws Exception
  {
    System.out.println("creating group : " + name);
    CreateGroupResult result = getService(url).createGroup(apiKey, name);
    if (result.isSuccess()) {
      String groupKey = result.getGroupKey();
      System.out.println("new group key = " + groupKey);
      return groupKey;
    } else {
      System.out.println("createGroup call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
      return null;
    }
  }

  public static void deleteGroup(String url, String apiKey, String groupKey) throws Exception
  {
    System.out.println("deleting group : " + groupKey);
    DeleteGroupResult result = getService(url).deleteGroup(apiKey, groupKey);
    if (result.isSuccess()) {
      System.out.println("deleted group " + groupKey);
    } else {
      System.out.println("deleteGroup call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
    }
  }

  public static void renameGroup(String url, String apiKey, String groupKey, String name) throws Exception
  {
    System.out.println("renaming group " + groupKey + ": " + name);
    RenameGroupResult result = getService(url).renameGroup(apiKey, groupKey, name);
    if (result.isSuccess()) {
      System.out.println("renamed group " + groupKey + " to " + name);
    } else {
      System.out.println("renameGroup call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
    }
  }

  public static String getGroupsInAccount(String url, String apiKey) throws Exception
  {
    System.out.println("retrieving goups in account");
    GetGroupsInAccountResult result = getService(url).getGroupsInAccount(apiKey);
    if (result.isSuccess()) {
      String defaultGroupKey = null;
      System.out.println("retrieved groups:");
      for (GroupInfo groupInfo : result.getGroupList().getGroupInfo()) {
        if (groupInfo.getGroupName().equals("Default Group"))
          defaultGroupKey = groupInfo.getGroupKey();
        System.out.println("   key: " + groupInfo.getGroupKey() + " name: " + groupInfo.getGroupName());
      }
      return defaultGroupKey;
    } else {
      System.out.println("getGroupsInAccount call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
      return null;
    }
  }

  public static List<String> getUsersInGroup(String url, String apiKey, String groupKey) throws Exception
  {
    System.out.println("retrieving users in group: " + groupKey);
    GetUsersInGroupResult result = getService(url).getUsersInGroup(apiKey, groupKey);
    if (result.isSuccess()) {
      List<String> userEmails = new ArrayList<String>();
      System.out.println("retrieved users:");
      for (UserInfo userInfo : result.getUserListForGroup().getUserInfo()) {
        userEmails.add(userInfo.getEmail());
        System.out.println("   key: " + userInfo.getUserKey() + " email: " + userInfo.getEmail());
      }
      return userEmails;
    } else {
      System.out.println("getUsersInGroup call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
      return null;
    }
  }

  public static void moveUsersToGroup(String url, String apiKey, String groupKey, String emails) throws Exception
  {
    System.out.println("retrieving users in group: " + groupKey);
    ArrayOfString emailArray = new ArrayOfString();
    for (String email : emails.split(","))
      emailArray.getString().add(email);
    UsersToMoveInfo usersToMoveInfo = new UsersToMoveInfo();
    usersToMoveInfo.setUserEmails(emailArray);
    MoveUsersToGroupResult result = getService(url).moveUsersToGroup(apiKey, groupKey, usersToMoveInfo);
    if (result.isSuccess()) {
      System.out.println("moved users");
    } else {
      System.out.println("moveUsersToGroup call failed with error code " + result.getErrorCode() +
                         ((result.getErrorMessage() != null) ? " (" + result.getErrorMessage() + ")" : ""));
    }
  }

  public static String createLibraryTemplate(String url, String apiKey, String filename, LibraryTemplateType type) throws Exception
  {
    File templateFile = (filename != null) ? new File(filename) : getTestPdfFile("testtemplate.pdf");

    ArrayOfFileInfo fileInfos = createArrayOfFileInfos(new String[]{templateFile.getAbsolutePath()});

    ArrayOfLibraryTemplateType libraryTemplateTypes = new ArrayOfLibraryTemplateType();
    libraryTemplateTypes.getLibraryTemplateType().add(type);

    LibraryDocumentCreationInfo libraryDocument = new LibraryDocumentCreationInfo();
    libraryDocument.setName(testPrefix + templateFile.getName());
    libraryDocument.setFileInfos(fileInfos);
    libraryDocument.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);
    libraryDocument.setSignatureType(SignatureType.ESIGN);
    libraryDocument.setLibrarySharingMode(LibrarySharingMode.USER);
    libraryDocument.setLibraryTemplateTypes(libraryTemplateTypes);

    LibraryDocumentCreationResult result = getService(url).createLibraryDocument(apiKey, null, libraryDocument);
    System.out.println("Library template key is: " + result.getDocumentKey());
    return result.getDocumentKey();
  }

  public static String sendDocumentWithFormFieldLayerTemplate(String url, String apiKey, String filename, String formFieldLayerTemplateKey, String recipient) throws Exception
  {
    ArrayOfRecipientInfo recipients = createArrayOfRecipientInfosAllSigners(new String[]{recipient});
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipients, filename, formFieldLayerTemplateKey, testPrefix, testMessage);
    ArrayOfDocumentKey documentKeys = getService(url).sendDocument(apiKey, null, documentInfo);
    System.out.println("Document key is: " + documentKeys.getDocumentKey().get(0).getDocumentKey());
    return documentKeys.getDocumentKey().get(0).getDocumentKey();
  }

  public static void rejectDocument(String url, String apiKey, String documentKey, String comment) throws Exception
  {
    RejectDocumentOptions options = new RejectDocumentOptions();
    options.setComment(comment);
    RejectDocumentResult result = getService(url).rejectDocument(apiKey, null, documentKey, options);
    System.out.format("errorCode=%s, errorMessage=%s%n", result.getErrorCode().toString(), result.getErrorMessage());
  }

  public static void replaceSigner(String url, String apiKey, String documentKey, String oldEmail, String newEmail, String comment) throws Exception
  {
    ReplaceSignerOptions options = new ReplaceSignerOptions();
    options.setOriginalSignerEmail(oldEmail);
    options.setNewSignerEmail(newEmail);
    options.setMessage(comment);
    ReplaceSignerResult result = getService(url).replaceSigner(apiKey, null, documentKey, options);
    System.out.println("replaceSigner result: " + result.getErrorCode());
  }

  public static void delegateSigning(String url, String apiKey, String documentKey, String newEmail, String comment) throws Exception
  {
    DelegateSigningOptions options = new DelegateSigningOptions();
    options.setNewSignerEmail(newEmail);
    options.setMessage(comment);
    DelegateSigningResult result = getService(url).delegateSigning(apiKey, null, documentKey, options);
    System.out.println("delegateSigning result: " + result.getErrorCode());
  }

  public static void getUserInfo(String url, String apiKey, String email) throws Exception
  {
    GetUserInfoOptions options = new GetUserInfoOptions();
    options.setEmail(email);
    GetUserInfoResult result = getService(url).getUserInfo(apiKey, options);
    System.out.println("getUserInfo result: " + result.getErrorCode());
  }

  public static void searchUserDocuments(String url, String apiKey, String query) throws Exception
  {
    SearchUserDocumentsOptions options = new SearchUserDocumentsOptions();
    options.setQuery(query);
    GetDocumentsForUserResult result = getService(url).searchUserDocuments(apiKey, null, options);
    System.out.println("searchUserDocuments result: " + result.getErrorCode());
  }

  public static void searchUserDocumentsByName(String url, String apiKey, String name) throws Exception
  {
    SearchUserDocumentsOptions options = new SearchUserDocumentsOptions();
    options.setName(name);
    GetDocumentsForUserResult result = getService(url).searchUserDocuments(apiKey, null, options);
    System.out.println("searchUserDocumentsByName result: " + result.getErrorCode());
  }

  public static void getDocumentEventsForUser(String url, String apiKey) throws Exception
  {
    DocumentEventsForUserOptions options = new DocumentEventsForUserOptions();
    Calendar cal = Calendar.getInstance();
    options.setEndDate(newXMLGregorianCalendar(cal.getTime()));
    cal.add(Calendar.WEEK_OF_YEAR, -1);
    options.setStartDate(newXMLGregorianCalendar(cal.getTime()));
    GetDocumentEventsForUserResult result = getService(url).getDocumentEventsForUser(apiKey, null, options);
    System.out.println("getDocumentEventsForUser result: " + result.getErrorCode());
  }

  private static DatatypeFactory datatypeFactory;
  private static DatatypeFactory getDatatypeFactory() throws Exception {
    if (datatypeFactory == null)
      datatypeFactory = DatatypeFactory.newInstance();
    return datatypeFactory;
  }

  private static XMLGregorianCalendar newXMLGregorianCalendar(Date date) throws Exception {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);
    return getDatatypeFactory().newXMLGregorianCalendar(cal);
  }

  public static void getEmbeddedView(String url, String accessToken) throws Exception
  {
    EmbeddedViewOptions options = new EmbeddedViewOptions();
    options.setEmbeddedViewTarget(EmbeddedViewTarget.USER_PROFILE);
    GetEmbeddedViewResult result = getService(url).getEmbeddedView(accessToken, null, options);
    System.out.println("getEmbeddedView URL: " + result.getUrl());
  }

  private static ArrayOfRecipientSecurityOption newArrayOfRecipientSecurityOption(String password) {
    return newArrayOfRecipientSecurityOption(newPasswordRecipientSecurityOption(password));
  }
  
  private static ArrayOfRecipientSecurityOption newArrayOfRecipientSecurityOption(String countryCode, String phone) {
    return newArrayOfRecipientSecurityOption(newPhoneRecipientSecurityOption(countryCode, phone));
  }
  
  private static RecipientSecurityOption newPasswordRecipientSecurityOption(String password) {
    RecipientSecurityOption securityOptions = new RecipientSecurityOption();
    securityOptions.setPassword(password);
    securityOptions.setAuthenticationMethod(AuthenticationMethod.PASSWORD);
    return securityOptions;
  }
  
  private static RecipientSecurityOption newPhoneRecipientSecurityOption(String countryCode, String phone) {
    PhoneInfo phoneInfo = new PhoneInfo();
    phoneInfo.setCountryCode(countryCode);
    phoneInfo.setPhone(phone);
    ArrayOfPhoneInfo phoneInfos = new ArrayOfPhoneInfo();
    phoneInfos.getPhoneInfo().add(phoneInfo);
    
    RecipientSecurityOption securityOptions = new RecipientSecurityOption();
    securityOptions.setPhoneInfos(phoneInfos);
    securityOptions.setAuthenticationMethod(AuthenticationMethod.PHONE);
    return securityOptions;
  }
  
  private static ArrayOfRecipientSecurityOption newArrayOfRecipientSecurityOption(RecipientSecurityOption securityOptions) {
    ArrayOfRecipientSecurityOption recipientSecurityOptions = new ArrayOfRecipientSecurityOption();
    recipientSecurityOptions.getRecipientSecurityOption().add(securityOptions);
    return recipientSecurityOptions;
  }
  
  public static String sendDocumentWithRecipientSecurityOptions(String url, String apiKey, String filename, String recipient1, String recipient1Password,
                                                                String recipient2, String recipient2Password) throws Exception
  {
    ArrayOfRecipientInfo recipients = createArrayOfRecipientInfosAllSigners(new String[]{recipient1, recipient2});
    
    ArrayOfRecipientSecurityOption recipient1SecurityOptions = newArrayOfRecipientSecurityOption(recipient1Password);
    ArrayOfRecipientSecurityOption recipient2SecurityOptions = newArrayOfRecipientSecurityOption(recipient2Password);
    recipients.getRecipientInfo().get(0).setSecurityOptions(recipient1SecurityOptions);
    recipients.getRecipientInfo().get(1).setSecurityOptions(recipient2SecurityOptions);
    
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipients, filename, null, testPrefix, testMessage);
    ArrayOfDocumentKey documentKeys = getService(url).sendDocument(apiKey, null, documentInfo);
    String docKey = documentKeys.getDocumentKey().get(0).getDocumentKey();
    System.out.println("Document key is: " + docKey);
    return docKey;
  }

  private static SecurityOptions newPasswordOptions(String externalPassword, String internalPassword, String openPassword, boolean protectOpen) {
    SecurityOptions securityOptions = new SecurityOptions();
    securityOptions.setExternalPassword(externalPassword);
    securityOptions.setInternalPassword(internalPassword);
    securityOptions.setOpenPassword(openPassword);
    securityOptions.setPasswordProtection(AppliesTo.ALL_USERS);
    securityOptions.setProtectOpen(protectOpen);
    return securityOptions;
  }
  
  public static String sendDocumentWithInternalAndExternalPassword(String url, String apiKey, String filename, String recipient1, String recipient2,
                                                                   String internalPassword, String externalPassword) throws Exception
  {
    ArrayOfRecipientInfo recipients = createArrayOfRecipientInfosAllSigners(new String[]{recipient1, recipient2});
       
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipients, filename, null, testPrefix, testMessage);
    SecurityOptions passwordOptions = newPasswordOptions(externalPassword, internalPassword, null, false);
    documentInfo.setSecurityOptions(passwordOptions);
    
    ArrayOfDocumentKey documentKeys = getService(url).sendDocument(apiKey, null, documentInfo);
    String docKey = documentKeys.getDocumentKey().get(0).getDocumentKey();
    System.out.println("Document key is: " + docKey);
    return docKey;
  }
  
  public static String sendDocumentWithPhoneAuth(String url, String apiKey, String filename, String recipient, String countryCode, String phone) throws Exception
  {
    ArrayOfRecipientInfo recipients = createArrayOfRecipientInfosAllSigners(new String[]{recipient});
    ArrayOfRecipientSecurityOption recipientSecurityOptions = newArrayOfRecipientSecurityOption(countryCode, phone);
    recipients.getRecipientInfo().get(0).setSecurityOptions(recipientSecurityOptions);
    
    DocumentCreationInfo documentInfo = createDocumentCreationInfoForEsign(recipients, filename, null, testPrefix, testMessage);
    
    ArrayOfDocumentKey documentKeys = getService(url).sendDocument(apiKey, null, documentInfo);
    String docKey = documentKeys.getDocumentKey().get(0).getDocumentKey();
    System.out.println("Document key is: " + docKey);
    return docKey;
  }
  
  public static void notifyDocumentVaulted(String url, String apiKey, String documentKey, String vaultEventId, String vaultEventComment) throws Exception
  {
    VaultEventInfo vaultEventInfo = new VaultEventInfo();
    vaultEventInfo.setVaultEventId(vaultEventId);
    vaultEventInfo.setVaultEventComment(vaultEventComment);
    NotifyDocumentVaultedResult result = getService(url).notifyDocumentVaulted(apiKey, documentKey, vaultEventInfo);
    System.out.println("notifyDocumentVaulted result: " + result.getErrorCode());
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
        String[] emails = new String[args.length - 4];
        final String filename = args[3];
        for(int i = 4; i< args.length; i++)
          emails[i - 4] = args[i];
        sendDocumentMegaSign(url, apiKey, filename, null, emails);
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
        createUrlWidget(url, apiKey, args[3], args[4], null, Boolean.parseBoolean(args[5]), Integer.parseInt(args[6]));
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
      else if (command.equals("sendDocumentInteractive"))
      {
        if (args.length != 5)
          return false;
        sendDocumentInteractive(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("createLibraryDocumentInteractive"))
      {
        if (args.length != 4)
          return false;
        createLibraryDocumentInteractive(url, apiKey, args[3]);
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
      else if (command.equals("getSupportingDocuments"))
      {
        if (args.length < 5)
          return false;
        getSupportingDocuments(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("latest"))
      {
        if (args.length != 5)
          return false;
        getDocument(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("createGroup"))
      {
        if (args.length != 4)
          return false;
        createGroup(url, apiKey, args[3]);
      }
      else if (command.equals("deleteGroup"))
      {
        if (args.length != 4)
          return false;
        deleteGroup(url, apiKey, args[3]);
      }
      else if (command.equals("renameGroup"))
      {
        if (args.length != 5)
          return false;
        renameGroup(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("getGroupsInAccount"))
      {
        if (args.length != 3)
          return false;
        getGroupsInAccount(url, apiKey);
      }
      else if (command.equals("getUsersInGroup"))
      {
        if (args.length != 4)
          return false;
        getUsersInGroup(url, apiKey, args[3]);
      }
      else if (command.equals("moveUsersToGroup"))
      {
        if (args.length != 5)
          return false;
        moveUsersToGroup(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("createFormFieldLayerTemplate"))
      {
        String filename = (args.length > 3 ? args[3] : null);
        createLibraryTemplate(url, apiKey, filename, LibraryTemplateType.FORM_FIELD_LAYER);
      }
      else if (command.equals("sendWithFormFieldLayerTemplate"))
      {
        if (args.length != 6)
          return false;
        sendDocumentWithFormFieldLayerTemplate(url, apiKey, args[3], args[4], args[5]);
      }
      else if (command.equals("sendDocumentMegaSignWithFormFieldLayerTemplate"))
      {
        if (args.length < 6)
          return false;
        String[] emails = new String[args.length - 5];
        final String filename = args[3];
        final String formFieldLayerTemplateKey = args[4];
        for(int i = 5; i< args.length; i++)
          emails[i - 5] = args[i];
        sendDocumentMegaSign(url, apiKey, filename, formFieldLayerTemplateKey, emails);
      }
      else if (command.equals("createUrlWidgetWithFormFieldLayerTemplate"))
      {
        if (args.length != 5)
          return false;
        createUrlWidget(url, apiKey, args[3], args[4], null, true, 0);
      }
      else if (command.equals("rejectDocument"))
      {
        if (args.length != 5)
          return false;
        rejectDocument(url, apiKey, args[3], args[4]);
      }
      else if (command.equals("replaceSigner"))
      {
        if (args.length != 7)
          return false;
        replaceSigner(url, apiKey, args[3], args[4], args[5], args[6]);
      }
      else if (command.equals("delegateSigning"))
      {
        if (args.length != 6)
          return false;
        delegateSigning(url, apiKey, args[3], args[4], args[5]);
      }
      else if (command.equals("getUserInfo"))
      {
        if (args.length != 4)
          return false;
        getUserInfo(url, apiKey, args[3]);
      }
      else if (command.equals("searchUserDocuments"))
      {
        if (args.length != 4)
          return false;
        searchUserDocuments(url, apiKey, args[3]);
      }
      else if (command.equals("searchUserDocumentsByName"))
      {
        if (args.length != 4)
          return false;
        searchUserDocumentsByName(url, apiKey, args[3]);
      }
      else if (command.equals("getDocumentEventsForUser"))
      {
        if (args.length != 3)
          return false;
        getDocumentEventsForUser(url, apiKey);
      }
      else if (command.equals("sendWithRecipientSecurity"))
      {
        if (args.length != 8)
          return false;
        sendDocumentWithRecipientSecurityOptions(url, apiKey, args[3], args[4], args[5], args[6], args[7]);
      }
      else if (command.equals("sendWithDifferentPasswords"))
      {
        if (args.length != 8)
          return false;
        sendDocumentWithInternalAndExternalPassword(url, apiKey, args[3], args[4], args[5], args[6], args[7]);
      }
      else if (command.equals("sendWithPhoneAuth"))
      {
        if (args.length != 7)
          return false;
        sendDocumentWithPhoneAuth(url, apiKey, args[3], args[4], args[5], args[6]);
      }
      else if (command.equals("getEmbeddedView"))
      {
        if (args.length != 3)
          return false;
        getEmbeddedView(url, apiKey);
      }
      else if (command.equals("notifyDocumentVaulted"))
      {
        if (args.length != 6)
          return false;
        notifyDocumentVaulted(url, apiKey, args[3], args[4], args[5]);
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
    usageSynopsis();

    usageBaseCommands();
    usageCommandsTillV16();
    usageCommandsV17();
    usageCommandsV18();
    usageCommandsV19();

    usageBaseDescriptions();
    usageDescriptionsTillV16();
    usageDescriptionsV17();
    usageDescriptionsV18();
    usageDescriptionsV19();
  }

  public static void usageCommandsTillV16()
  {
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
    System.err.println("  sendDocumentInteractive <filename> <recipient_email>");
    System.err.println("  createLibraryDocumentInteractive <filename>");
    System.err.println("  createLibraryDocument <filename>");
    System.err.println("  getLibraryDocuments <email>");
    System.err.println("  myLibraryDocuments");
    System.err.println("  audit <documentKey> <filename>");
    System.err.println("  signingUrl <documentKey>");
    System.err.println("  infosByExternalId <email> <password> <externalId>");
    System.err.println("  getDocuments <documentKey> <fileDirPath> [<versionKey> <userEmail> <combine>]");
    System.err.println("  getDocumentUrls <documentKey> [<versionKey> <userEmail> <combine>]");
    System.err.println("  getDocumentImageUrls <documentKey> [<versionKey> <userEmail> <combine>]");
    System.err.println("  getSupportingDocuments <documentKey> <baseFilePath>");
    System.err.println("  createGroup <name>");
    System.err.println("  deleteGroup <groupKey>");
    System.err.println("  renameGroup <groupKey> <name>");
    System.err.println("  getGroupsInAccount");
    System.err.println("  getUsersInGroup <groupKey>");
    System.err.println("  moveUsersToGroup <groupKey> <email[]>");
  }

  public static void usageDescriptionsTillV16()
  {
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
    System.err.println("createUser creates a new Adobe Document Cloud user in the system");
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
    System.err.println("sendDocumentInteractive returns a URL where the user can complete the process of sending the provided file(s)");
    System.err.println("");
    System.err.println("createLibraryDocumentInteractive returns a URL where the user can complete the process of adding the specified file to the user's template library");
    System.err.println("");
    System.err.println("createLibraryDocument will add the specified file to the user's template library");
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
    System.err.println("getSupportingDocuments retrieves the supporting files in PDF that are attached to the given document");
    System.err.println("");
    System.err.println("createGroup create a new group within an account");
    System.err.println("");
    System.err.println("deleteGroup removes a group within an accout");
    System.err.println("");
    System.err.println("renameGroup renames a group wthin an account");
    System.err.println("");
    System.err.println("getGroupsInAccount retrieves the groups within an account");
    System.err.println("");
    System.err.println("getUsersInGroup retrieves the users within a group");
    System.err.println("");
    System.err.println("moveUsersToGroup moves users to a group within an account");
    System.err.println("");
  }

  public static void usageCommandsV17()
  {
    System.err.println("  createFormFieldLayerTemplate [<filename>] ('testtemplate.pdf' will be used if <filename> is not provided)");
    System.err.println("  sendWithFormFieldLayerTemplate <filename> <libraryTemplateKey> <recipient_email>");
    System.err.println("  sendDocumentMegaSignWithFormFieldLayerTemplate <filename> <libraryTemplateKey> <signer_email1> <signer_email2> ...");
    System.err.println("  createUrlWidgetWithFormFieldLayerTemplate <filename> <libraryTemplateKey>");
    System.err.println("  rejectDocument <documentKey> <comment>");
    System.err.println("  replaceSigner <documentKey> <oldSigner> <newSigner> <comment>");
    System.err.println("  delegateSigning <documentKey> <newSigner> <comment>");
    System.err.println("  notifyDocumentVaulted <documentKey> <vaultEventId> <vaultEventComment>");
  }

  public static void usageDescriptionsV17()
  {
    System.err.println("createFormFieldLayerTemplate will add the specified file to the user's template library as form field layer template");
    System.err.println("");
    System.err.println("sendWithFormFieldLayerTemplate will create a new agreement with the specified form field layer template applied");
    System.err.println("");
    System.err.println("sendDocumentMegaSignWithFormFieldLayerTemplate will apply the specified form field layer template to a document and send it to multiple signers simultaneously");
    System.err.println("");
    System.err.println("createUrlWidgetWithFormFieldLayerTemplate will return the url for a signable widget of the provided file with the specified form field layer template applied");
    System.err.println("");
    System.err.println("rejectDocument will abort the signature workflow of the specified document and notify the sender of the rejection by email with the specified comment.");
    System.err.println("");
    System.err.println("replaceSigner will replace the old signer with a new signer and note the specified comment.");
    System.err.println("");
    System.err.println("delegateSigning will delegate the signer with a new signer and note the specified comment.");
    System.err.println("");
    System.err.println("  notifyDocumentVaulted notify the system that the document has been vaulted");
    System.err.println("");
  }

  public static void usageCommandsV18()
  {
    System.err.println("  getUserInfo  <email>");
    System.err.println("  searchUserDocuments  <query>");
    System.err.println("  getDocumentEventsForUser  ");
    System.err.println("  sendWithRecipientSecurity <filename> <signer1_email> <signer1_password> <signer2_email> <signer2_password>");
    System.err.println("  sendWithDifferentPasswords <filename> <signer1_email> <signer2_email> <internal_password> <external_password>");
  }

  public static void usageDescriptionsV18()
  {
    System.err.println("getUserInfo returns information about a user");
    System.err.println("");
    System.err.println("searchUserDocuments lists all the visible documents of the specified user matching a specified query");
    System.err.println("");
    System.err.println("getDocumentEventsForUser lists the events for the specified user for the past week");
    System.err.println("");
    System.err.println("sendWithRecipientSecurity will send an agreement with each signer having a different signing password");
    System.err.println("");
    System.err.println("sendWithDifferentPasswords will send an agreement where signers in the sender's account and signers outside the sender's account have different passwords");
    System.err.println("");
  }
  
  public static void usageCommandsV19()
  {
    System.err.println("  sendWithPhoneAuth <filename> <signer_email> <country_code> <phone_number>");
    System.err.println("  getEmbeddedView");
  }

  public static void usageDescriptionsV19()
  {
    System.err.println("sendWithPhoneAuth will send an agreement where the signer must request an access token be sent to their phone, and enter the access token to sign");
    System.err.println("getEmbeddedView will return a URL for an embedded view of the user's profile page");
    System.err.println("");
  }
}
