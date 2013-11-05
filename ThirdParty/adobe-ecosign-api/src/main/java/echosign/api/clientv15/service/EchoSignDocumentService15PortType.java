
package echosign.api.clientv15.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import echosign.api.clientv15.ArrayOfString;
import echosign.api.clientv15.dto.ArrayOfDocumentKey;
import echosign.api.clientv15.dto.CancelDocumentResult;
import echosign.api.clientv15.dto.DocumentCreationInfo;
import echosign.api.clientv15.dto.DocumentImageList;
import echosign.api.clientv15.dto.DocumentUrlResult;
import echosign.api.clientv15.dto.ExternalId;
import echosign.api.clientv15.dto.FileInfo;
import echosign.api.clientv15.dto.FormCreationInfo;
import echosign.api.clientv15.dto.FormCreationResult;
import echosign.api.clientv15.dto.Pong;
import echosign.api.clientv15.dto.RemoveDocumentResult;
import echosign.api.clientv15.dto.SendDocumentMegaSignResult;
import echosign.api.clientv15.dto.SendReminderResult;
import echosign.api.clientv15.dto.SenderInfo;
import echosign.api.clientv15.dto.UserCreationInfo;
import echosign.api.clientv15.dto.UserVerificationInfo;
import echosign.api.clientv15.dto10.GetWidgetsForUserResult;
import echosign.api.clientv15.dto12.SendDocumentInteractiveOptions;
import echosign.api.clientv15.dto12.SendDocumentInteractiveResult;
import echosign.api.clientv15.dto13.DeliverDocumentResult;
import echosign.api.clientv15.dto14.GetDocumentImageUrlsOptions;
import echosign.api.clientv15.dto14.GetDocumentImageUrlsResult;
import echosign.api.clientv15.dto14.GetDocumentUrlsOptions;
import echosign.api.clientv15.dto14.GetDocumentUrlsResult;
import echosign.api.clientv15.dto14.GetDocumentsForUserResult;
import echosign.api.clientv15.dto14.GetDocumentsOptions;
import echosign.api.clientv15.dto14.GetDocumentsResult;
import echosign.api.clientv15.dto14.GetMegaSignDocumentResult;
import echosign.api.clientv15.dto14.GetSupportingDocumentsOptions;
import echosign.api.clientv15.dto14.GetSupportingDocumentsResult;
import echosign.api.clientv15.dto14.SigningUrlResult;
import echosign.api.clientv15.dto15.CreateGroupResult;
import echosign.api.clientv15.dto15.DeleteGroupResult;
import echosign.api.clientv15.dto15.DocumentInfo;
import echosign.api.clientv15.dto15.DocumentInfoList;
import echosign.api.clientv15.dto15.GetGroupsInAccountResult;
import echosign.api.clientv15.dto15.GetUsersInAccountResult;
import echosign.api.clientv15.dto15.GetUsersInGroupResult;
import echosign.api.clientv15.dto15.MoveUsersToGroupResult;
import echosign.api.clientv15.dto15.RenameGroupResult;
import echosign.api.clientv15.dto15.UsersToMoveInfo;
import echosign.api.clientv15.dto7.AccountCreationInfo;
import echosign.api.clientv15.dto7.CreateAccountResult;
import echosign.api.clientv15.dto8.EmbeddedWidgetCreationResult;
import echosign.api.clientv15.dto8.GetFormDataResult;
import echosign.api.clientv15.dto8.UrlWidgetCreationResult;
import echosign.api.clientv15.dto8.WidgetCreationInfo;
import echosign.api.clientv15.dto8.WidgetPersonalizationInfo;
import echosign.api.clientv15.dto9.AuditTrailResult;
import echosign.api.clientv15.dto9.GetLibraryDocumentsForUserResult;
import echosign.api.clientv15.dto9.InitiateInteractiveSendDocumentResult;
import echosign.api.clientv15.dto9.LibraryDocumentCreationInfo;
import echosign.api.clientv15.dto9.LibraryDocumentCreationResult;
import echosign.api.clientv15.dto9.UserCredentials;

@WebService(name = "EchoSignDocumentService15PortType", targetNamespace = "http://api.echosign")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EchoSignDocumentService15PortType {


    @WebMethod(operationName = "createPersonalEmbeddedWidget", action = "")
    @WebResult(name = "embeddedWidgetCreationResult", targetNamespace = "http://api.echosign")
    public EmbeddedWidgetCreationResult createPersonalEmbeddedWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "widgetInfo", targetNamespace = "http://api.echosign")
        WidgetCreationInfo widgetInfo,
        @WebParam(name = "personalizationInfo", targetNamespace = "http://api.echosign")
        WidgetPersonalizationInfo personalizationInfo);

    @WebMethod(operationName = "createUrlWidget", action = "")
    @WebResult(name = "urlWidgetCreationResult", targetNamespace = "http://api.echosign")
    public UrlWidgetCreationResult createUrlWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "widgetInfo", targetNamespace = "http://api.echosign")
        WidgetCreationInfo widgetInfo);

    @WebMethod(operationName = "getUsersInGroup", action = "")
    @WebResult(name = "getUsersInGroupResult", targetNamespace = "http://api.echosign")
    public GetUsersInGroupResult getUsersInGroup(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "groupKey", targetNamespace = "http://api.echosign")
        String groupKey);

    @WebMethod(operationName = "personalizeEmbeddedWidget", action = "")
    @WebResult(name = "embeddedWidgetCreationResult", targetNamespace = "http://api.echosign")
    public EmbeddedWidgetCreationResult personalizeEmbeddedWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "widgetJavascript", targetNamespace = "http://api.echosign")
        String widgetJavascript,
        @WebParam(name = "personalizationInfo", targetNamespace = "http://api.echosign")
        WidgetPersonalizationInfo personalizationInfo);

    @WebMethod(operationName = "getLatestDocumentUrl", action = "")
    @WebResult(name = "documentUrlResult", targetNamespace = "http://api.echosign")
    public DocumentUrlResult getLatestDocumentUrl(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "createPersonalUrlWidget", action = "")
    @WebResult(name = "urlWidgetCreationResult", targetNamespace = "http://api.echosign")
    public UrlWidgetCreationResult createPersonalUrlWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "widgetInfo", targetNamespace = "http://api.echosign")
        WidgetCreationInfo widgetInfo,
        @WebParam(name = "personalizationInfo", targetNamespace = "http://api.echosign")
        WidgetPersonalizationInfo personalizationInfo);

    @WebMethod(operationName = "testPing", action = "")
    @WebResult(name = "pong", targetNamespace = "http://api.echosign")
    public Pong testPing(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey);

    @WebMethod(operationName = "createGroup", action = "")
    @WebResult(name = "createGroupResult", targetNamespace = "http://api.echosign")
    public CreateGroupResult createGroup(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "groupName", targetNamespace = "http://api.echosign")
        String groupName);

    @WebMethod(operationName = "initiateInteractiveSendDocument", action = "")
    @WebResult(name = "initiateInteractiveSendDocumentResult", targetNamespace = "http://api.echosign")
    public InitiateInteractiveSendDocumentResult initiateInteractiveSendDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "documentCreationInfo", targetNamespace = "http://api.echosign")
        DocumentCreationInfo documentCreationInfo,
        @WebParam(name = "forceSendConfirmation", targetNamespace = "http://api.echosign")
        boolean forceSendConfirmation,
        @WebParam(name = "authoringRequested", targetNamespace = "http://api.echosign")
        boolean authoringRequested);

    @WebMethod(operationName = "removeDocument", action = "")
    @WebResult(name = "removeDocumentResult", targetNamespace = "http://api.echosign")
    public RemoveDocumentResult removeDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "getDocumentsForUser", action = "")
    @WebResult(name = "getDocumentsForUserResult", targetNamespace = "http://api.echosign")
    public GetDocumentsForUserResult getDocumentsForUser(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userKey", targetNamespace = "http://api.echosign")
        String userKey);

    @WebMethod(operationName = "getLatestImages", action = "")
    @WebResult(name = "documentImageList", targetNamespace = "http://api.echosign")
    public DocumentImageList getLatestImages(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "getDocumentInfo", action = "")
    @WebResult(name = "documentInfo", targetNamespace = "http://api.echosign")
    public DocumentInfo getDocumentInfo(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "moveUsersToGroup", action = "")
    @WebResult(name = "moveUsersToGroupResult", targetNamespace = "http://api.echosign")
    public MoveUsersToGroupResult moveUsersToGroup(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "groupKey", targetNamespace = "http://api.echosign")
        String groupKey,
        @WebParam(name = "usersToMoveInfo", targetNamespace = "http://api.echosign")
        UsersToMoveInfo usersToMoveInfo);

    @WebMethod(operationName = "renameGroup", action = "")
    @WebResult(name = "renameGroupResult", targetNamespace = "http://api.echosign")
    public RenameGroupResult renameGroup(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "groupKey", targetNamespace = "http://api.echosign")
        String groupKey,
        @WebParam(name = "newGroupName", targetNamespace = "http://api.echosign")
        String newGroupName);

    @WebMethod(operationName = "getDocumentUrlByVersion", action = "")
    @WebResult(name = "documentUrlResult", targetNamespace = "http://api.echosign")
    public DocumentUrlResult getDocumentUrlByVersion(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "versionKey", targetNamespace = "http://api.echosign")
        String versionKey);

    @WebMethod(operationName = "getMyDocuments", action = "")
    @WebResult(name = "getMyDocumentsResult", targetNamespace = "http://api.echosign")
    public GetDocumentsForUserResult getMyDocuments(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey);

    @WebMethod(operationName = "getSigningUrl", action = "")
    @WebResult(name = "getSigningUrlResult", targetNamespace = "http://api.echosign")
    public SigningUrlResult getSigningUrl(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "sendDocumentInteractive", action = "")
    @WebResult(name = "sendDocumentInteractiveResult", targetNamespace = "http://api.echosign")
    public SendDocumentInteractiveResult sendDocumentInteractive(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "documentCreationInfo", targetNamespace = "http://api.echosign")
        DocumentCreationInfo documentCreationInfo,
        @WebParam(name = "sendDocumentInteractiveOptions", targetNamespace = "http://api.echosign")
        SendDocumentInteractiveOptions sendDocumentInteractiveOptions);

    @WebMethod(operationName = "getDocumentInfosByExternalId", action = "")
    @WebResult(name = "documentInfoList", targetNamespace = "http://api.echosign")
    public DocumentInfoList getDocumentInfosByExternalId(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "email", targetNamespace = "http://api.echosign")
        String email,
        @WebParam(name = "password", targetNamespace = "http://api.echosign")
        String password,
        @WebParam(name = "externalId", targetNamespace = "http://api.echosign")
        ExternalId externalId);

    @WebMethod(operationName = "cancelDocument", action = "")
    @WebResult(name = "cancelDocumentResult", targetNamespace = "http://api.echosign")
    public CancelDocumentResult cancelDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "comment", targetNamespace = "http://api.echosign")
        String comment,
        @WebParam(name = "notifySigner", targetNamespace = "http://api.echosign")
        boolean notifySigner);

    @WebMethod(operationName = "deliverDocument", action = "")
    @WebResult(name = "DeliverDocumentResult", targetNamespace = "http://api.echosign")
    public DeliverDocumentResult deliverDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "fileInfo", targetNamespace = "http://api.echosign")
        FileInfo fileInfo);

    @WebMethod(operationName = "createUser", action = "")
    @WebResult(name = "userKey", targetNamespace = "http://api.echosign")
    public String createUser(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userInfo", targetNamespace = "http://api.echosign")
        UserCreationInfo userInfo);

    @WebMethod(operationName = "getMegaSignDocument", action = "")
    @WebResult(name = "getMegaSignDocumentResult", targetNamespace = "http://api.echosign")
    public GetMegaSignDocumentResult getMegaSignDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "personalizeUrlWidget", action = "")
    @WebResult(name = "urlWidgetCreationResult", targetNamespace = "http://api.echosign")
    public UrlWidgetCreationResult personalizeUrlWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "widgetUrl", targetNamespace = "http://api.echosign")
        String widgetUrl,
        @WebParam(name = "personalizationInfo", targetNamespace = "http://api.echosign")
        WidgetPersonalizationInfo personalizationInfo);

    @WebMethod(operationName = "getUsersInAccount", action = "")
    @WebResult(name = "getUsersInAccountResult", targetNamespace = "http://api.echosign")
    public GetUsersInAccountResult getUsersInAccount(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey);

    @WebMethod(operationName = "createAccount", action = "")
    @WebResult(name = "createAccountResult", targetNamespace = "http://api.echosign")
    public CreateAccountResult createAccount(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCreationInfo", targetNamespace = "http://api.echosign")
        UserCreationInfo userCreationInfo,
        @WebParam(name = "accountCreationInfo", targetNamespace = "http://api.echosign")
        AccountCreationInfo accountCreationInfo);

    @WebMethod(operationName = "getDocumentImageUrls", action = "")
    @WebResult(name = "getDocumentImageUrlsResult", targetNamespace = "http://api.echosign")
    public GetDocumentImageUrlsResult getDocumentImageUrls(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        GetDocumentImageUrlsOptions options);

    @WebMethod(operationName = "getLibraryDocumentsForUser", action = "")
    @WebResult(name = "getLibraryDocumentsForUserResult", targetNamespace = "http://api.echosign")
    public GetLibraryDocumentsForUserResult getLibraryDocumentsForUser(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCredentials", targetNamespace = "http://api.echosign")
        UserCredentials userCredentials);

    @WebMethod(operationName = "getDocumentByVersion", action = "")
    @WebResult(name = "pdf", targetNamespace = "http://api.echosign")
    public byte[] getDocumentByVersion(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "versionKey", targetNamespace = "http://api.echosign")
        String versionKey);

    @WebMethod(operationName = "getMyLibraryDocuments", action = "")
    @WebResult(name = "getMyLibraryDocumentsResult", targetNamespace = "http://api.echosign")
    public GetLibraryDocumentsForUserResult getMyLibraryDocuments(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey);

    @WebMethod(operationName = "getWidgetsForUser", action = "")
    @WebResult(name = "getWidgetsForUserResult", targetNamespace = "http://api.echosign")
    public GetWidgetsForUserResult getWidgetsForUser(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCredentials", targetNamespace = "http://api.echosign")
        UserCredentials userCredentials);

    @WebMethod(operationName = "getUserDocuments", action = "")
    @WebResult(name = "getDocumentsForUserResult", targetNamespace = "http://api.echosign")
    public GetDocumentsForUserResult getUserDocuments(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCredentials", targetNamespace = "http://api.echosign")
        UserCredentials userCredentials);

    @WebMethod(operationName = "getAuditTrail", action = "")
    @WebResult(name = "getAuditTrailResult", targetNamespace = "http://api.echosign")
    public AuditTrailResult getAuditTrail(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "getGroupsInAccount", action = "")
    @WebResult(name = "getGroupsInAccountResult", targetNamespace = "http://api.echosign")
    public GetGroupsInAccountResult getGroupsInAccount(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey);

    @WebMethod(operationName = "deleteGroup", action = "")
    @WebResult(name = "deleteGroupResult", targetNamespace = "http://api.echosign")
    public DeleteGroupResult deleteGroup(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "groupKey", targetNamespace = "http://api.echosign")
        String groupKey);

    @WebMethod(operationName = "createForm", action = "")
    @WebResult(name = "formCreationResult", targetNamespace = "http://api.echosign")
    public FormCreationResult createForm(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "formInfo", targetNamespace = "http://api.echosign")
        FormCreationInfo formInfo);

    @WebMethod(operationName = "verifyUser", action = "")
    @WebResult(name = "userVerificationInfo", targetNamespace = "http://api.echosign")
    public UserVerificationInfo verifyUser(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "email", targetNamespace = "http://api.echosign")
        String email,
        @WebParam(name = "password", targetNamespace = "http://api.echosign")
        String password);

    @WebMethod(operationName = "sendReminder", action = "")
    @WebResult(name = "sendreminderResult", targetNamespace = "http://api.echosign")
    public SendReminderResult sendReminder(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "comment", targetNamespace = "http://api.echosign")
        String comment);

    @WebMethod(operationName = "testEchoFile", action = "")
    @WebResult(name = "outFile", targetNamespace = "http://api.echosign")
    public byte[] testEchoFile(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "file", targetNamespace = "http://api.echosign")
        byte[] file);

    @WebMethod(operationName = "getMyWidgets", action = "")
    @WebResult(name = "getMyWidgetsResult", targetNamespace = "http://api.echosign")
    public GetWidgetsForUserResult getMyWidgets(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey);

    @WebMethod(operationName = "getSupportingDocuments", action = "")
    @WebResult(name = "getSupportingDocumentsResult", targetNamespace = "http://api.echosign")
    public GetSupportingDocumentsResult getSupportingDocuments(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "supportingDocumentKeys", targetNamespace = "http://api.echosign")
        ArrayOfString supportingDocumentKeys,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        GetSupportingDocumentsOptions options);

    @WebMethod(operationName = "createEmbeddedWidget", action = "")
    @WebResult(name = "embeddedWidgetCreationResult", targetNamespace = "http://api.echosign")
    public EmbeddedWidgetCreationResult createEmbeddedWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "widgetInfo", targetNamespace = "http://api.echosign")
        WidgetCreationInfo widgetInfo);

    @WebMethod(operationName = "sendDocument", action = "")
    @WebResult(name = "documentKeys", targetNamespace = "http://api.echosign")
    public ArrayOfDocumentKey sendDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "documentCreationInfo", targetNamespace = "http://api.echosign")
        DocumentCreationInfo documentCreationInfo);

    @WebMethod(operationName = "getLatestDocument", action = "")
    @WebResult(name = "pdf", targetNamespace = "http://api.echosign")
    public byte[] getLatestDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "sendDocumentMegaSign", action = "")
    @WebResult(name = "sendMegaSignDocumentResult", targetNamespace = "http://api.echosign")
    public SendDocumentMegaSignResult sendDocumentMegaSign(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "documentCreationInfo", targetNamespace = "http://api.echosign")
        DocumentCreationInfo documentCreationInfo);

    @WebMethod(operationName = "getFormData", action = "")
    @WebResult(name = "getFormDataResult", targetNamespace = "http://api.echosign")
    public GetFormDataResult getFormData(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "getDocuments", action = "")
    @WebResult(name = "getDocumentsResult", targetNamespace = "http://api.echosign")
    public GetDocumentsResult getDocuments(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        GetDocumentsOptions options);

    @WebMethod(operationName = "getDocumentUrls", action = "")
    @WebResult(name = "getDocumentUrlsResult", targetNamespace = "http://api.echosign")
    public GetDocumentUrlsResult getDocumentUrls(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        GetDocumentUrlsOptions options);

    @WebMethod(operationName = "getImagesByVersion", action = "")
    @WebResult(name = "documentImageList", targetNamespace = "http://api.echosign")
    public DocumentImageList getImagesByVersion(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "versionKey", targetNamespace = "http://api.echosign")
        String versionKey);

    @WebMethod(operationName = "createLibraryDocument", action = "")
    @WebResult(name = "libraryDocumentCreationResult", targetNamespace = "http://api.echosign")
    public LibraryDocumentCreationResult createLibraryDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "libraryDocumentCreationInfo", targetNamespace = "http://api.echosign")
        LibraryDocumentCreationInfo libraryDocumentCreationInfo);

}
