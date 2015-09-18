
package echosign.api.clientv20.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import echosign.api.clientv20.ArrayOfString;
import echosign.api.clientv20.dto.CancelDocumentResult;
import echosign.api.clientv20.dto.DocumentCreationInfo;
import echosign.api.clientv20.dto.DocumentImageList;
import echosign.api.clientv20.dto.DocumentUrlResult;
import echosign.api.clientv20.dto.ExternalId;
import echosign.api.clientv20.dto.FileInfo;
import echosign.api.clientv20.dto.FormCreationInfo;
import echosign.api.clientv20.dto.Pong;
import echosign.api.clientv20.dto.RemoveDocumentResult;
import echosign.api.clientv20.dto.SendReminderResult;
import echosign.api.clientv20.dto.SenderInfo;
import echosign.api.clientv20.dto.UserCreationInfo;
import echosign.api.clientv20.dto.UserVerificationInfo;
import echosign.api.clientv20.dto12.SendDocumentInteractiveOptions;
import echosign.api.clientv20.dto13.DeliverDocumentResult;
import echosign.api.clientv20.dto14.GetDocumentImageUrlsOptions;
import echosign.api.clientv20.dto14.GetDocumentUrlsOptions;
import echosign.api.clientv20.dto14.GetDocumentUrlsResult;
import echosign.api.clientv20.dto14.GetDocumentsOptions;
import echosign.api.clientv20.dto14.GetSupportingDocumentsOptions;
import echosign.api.clientv20.dto14.SigningUrlResult;
import echosign.api.clientv20.dto15.DeleteGroupResult;
import echosign.api.clientv20.dto15.MoveUsersToGroupResult;
import echosign.api.clientv20.dto15.RenameGroupResult;
import echosign.api.clientv20.dto15.UsersToMoveInfo;
import echosign.api.clientv20.dto16.ArrayOfDocumentKey;
import echosign.api.clientv20.dto16.CreateGroupResult;
import echosign.api.clientv20.dto16.DisableWidgetOptions;
import echosign.api.clientv20.dto16.DisableWidgetResult;
import echosign.api.clientv20.dto16.EmbeddedWidgetCreationResult;
import echosign.api.clientv20.dto16.EnableWidgetOptions;
import echosign.api.clientv20.dto16.EnableWidgetResult;
import echosign.api.clientv20.dto16.FormCreationResult;
import echosign.api.clientv20.dto16.GetDocumentImageUrlsResult;
import echosign.api.clientv20.dto16.GetDocumentsResult;
import echosign.api.clientv20.dto16.GetGroupsInAccountResult;
import echosign.api.clientv20.dto16.GetSupportingDocumentsResult;
import echosign.api.clientv20.dto16.GetUsersInAccountResult;
import echosign.api.clientv20.dto16.GetUsersInGroupResult;
import echosign.api.clientv20.dto16.GetWidgetsForUserResult;
import echosign.api.clientv20.dto16.LibraryDocumentCreationResult;
import echosign.api.clientv20.dto16.SendDocumentInteractiveResult;
import echosign.api.clientv20.dto16.SendDocumentMegaSignResult;
import echosign.api.clientv20.dto16.UrlWidgetCreationResult;
import echosign.api.clientv20.dto17.ComposeDocumentInfo;
import echosign.api.clientv20.dto17.DelegateSigningOptions;
import echosign.api.clientv20.dto17.DelegateSigningResult;
import echosign.api.clientv20.dto17.GetComposeDocumentUrlResult;
import echosign.api.clientv20.dto17.GetDocumentsForUserResult;
import echosign.api.clientv20.dto17.GetLibraryDocumentsForUserResult;
import echosign.api.clientv20.dto17.GetMegaSignDocumentResult;
import echosign.api.clientv20.dto17.NotifyDocumentVaultedResult;
import echosign.api.clientv20.dto17.RejectDocumentOptions;
import echosign.api.clientv20.dto17.RejectDocumentResult;
import echosign.api.clientv20.dto17.ReplaceSignerOptions;
import echosign.api.clientv20.dto17.ReplaceSignerResult;
import echosign.api.clientv20.dto17.VaultEventInfo;
import echosign.api.clientv20.dto18.GetUserInfoOptions;
import echosign.api.clientv20.dto18.GetUserInfoResult;
import echosign.api.clientv20.dto18.SearchUserDocumentsOptions;
import echosign.api.clientv20.dto19.EmbeddedViewOptions;
import echosign.api.clientv20.dto19.GetDocumentPagesInfoResult;
import echosign.api.clientv20.dto19.GetEmbeddedViewResult;
import echosign.api.clientv20.dto19.GetSignerFormFieldsOptions;
import echosign.api.clientv20.dto19.GetSignerFormFieldsResult;
import echosign.api.clientv20.dto19.OnBehalfOfUser;
import echosign.api.clientv20.dto20.DocumentEventsForUserOptions;
import echosign.api.clientv20.dto20.DocumentInfo;
import echosign.api.clientv20.dto20.DocumentInfoList;
import echosign.api.clientv20.dto20.GetDocumentEventsForUserResult;
import echosign.api.clientv20.dto7.AccountCreationInfo;
import echosign.api.clientv20.dto7.CreateAccountResult;
import echosign.api.clientv20.dto8.GetFormDataResult;
import echosign.api.clientv20.dto8.WidgetCreationInfo;
import echosign.api.clientv20.dto8.WidgetPersonalizationInfo;
import echosign.api.clientv20.dto9.AuditTrailResult;
import echosign.api.clientv20.dto9.InitiateInteractiveSendDocumentResult;
import echosign.api.clientv20.dto9.LibraryDocumentCreationInfo;
import echosign.api.clientv20.dto9.UserCredentials;

@WebService(name = "EchoSignDocumentService20PortType", targetNamespace = "http://api.echosign")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EchoSignDocumentService20PortType {


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

    @WebMethod(operationName = "getSignerFormFields", action = "")
    @WebResult(name = "getSignerFormFieldsResult", targetNamespace = "http://api.echosign")
    public GetSignerFormFieldsResult getSignerFormFields(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        GetSignerFormFieldsOptions options);

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

    @WebMethod(operationName = "removeDocument", action = "")
    @WebResult(name = "removeDocumentResult", targetNamespace = "http://api.echosign")
    public RemoveDocumentResult removeDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

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
        Boolean authoringRequested);

    @WebMethod(operationName = "createLibraryDocumentInteractive", action = "")
    @WebResult(name = "sendDocumentInteractiveResult", targetNamespace = "http://api.echosign")
    public SendDocumentInteractiveResult createLibraryDocumentInteractive(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "senderInfo", targetNamespace = "http://api.echosign")
        SenderInfo senderInfo,
        @WebParam(name = "libraryDocumentCreationInfo", targetNamespace = "http://api.echosign")
        LibraryDocumentCreationInfo libraryDocumentCreationInfo,
        @WebParam(name = "sendDocumentInteractiveOptions", targetNamespace = "http://api.echosign")
        SendDocumentInteractiveOptions sendDocumentInteractiveOptions);

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

    @WebMethod(operationName = "getDocumentPagesInfo", action = "")
    @WebResult(name = "getDocumentPagesInfoResult", targetNamespace = "http://api.echosign")
    public GetDocumentPagesInfoResult getDocumentPagesInfo(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

    @WebMethod(operationName = "getEmbeddedView", action = "")
    @WebResult(name = "getEmbeddedViewResult", targetNamespace = "http://api.echosign")
    public GetEmbeddedViewResult getEmbeddedView(
        @WebParam(name = "accessToken", targetNamespace = "http://api.echosign")
        String accessToken,
        @WebParam(name = "onBehalfOfUser", targetNamespace = "http://api.echosign")
        OnBehalfOfUser onBehalfOfUser,
        @WebParam(name = "embeddedViewOptions", targetNamespace = "http://api.echosign")
        EmbeddedViewOptions embeddedViewOptions);

    @WebMethod(operationName = "renameGroup", action = "")
    @WebResult(name = "renameGroupResult", targetNamespace = "http://api.echosign")
    public RenameGroupResult renameGroup(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "groupKey", targetNamespace = "http://api.echosign")
        String groupKey,
        @WebParam(name = "newGroupName", targetNamespace = "http://api.echosign")
        String newGroupName);

    @WebMethod(operationName = "moveUsersToGroup", action = "")
    @WebResult(name = "moveUsersToGroupResult", targetNamespace = "http://api.echosign")
    public MoveUsersToGroupResult moveUsersToGroup(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "groupKey", targetNamespace = "http://api.echosign")
        String groupKey,
        @WebParam(name = "usersToMoveInfo", targetNamespace = "http://api.echosign")
        UsersToMoveInfo usersToMoveInfo);

    @WebMethod(operationName = "enableWidget", action = "")
    @WebResult(name = "out", targetNamespace = "http://api.echosign")
    public EnableWidgetResult enableWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        EnableWidgetOptions options);

    @WebMethod(operationName = "getDocumentUrlByVersion", action = "")
    @WebResult(name = "documentUrlResult", targetNamespace = "http://api.echosign")
    public DocumentUrlResult getDocumentUrlByVersion(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "versionKey", targetNamespace = "http://api.echosign")
        String versionKey);

    @WebMethod(operationName = "delegateSigning", action = "")
    @WebResult(name = "delegateSigningResult", targetNamespace = "http://api.echosign")
    public DelegateSigningResult delegateSigning(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCredentials", targetNamespace = "http://api.echosign")
        UserCredentials userCredentials,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "delegateSigningOptions", targetNamespace = "http://api.echosign")
        DelegateSigningOptions delegateSigningOptions);

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

    @WebMethod(operationName = "disableWidget", action = "")
    @WebResult(name = "out", targetNamespace = "http://api.echosign")
    public DisableWidgetResult disableWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        DisableWidgetOptions options);

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

    @WebMethod(operationName = "getDocumentEventsForUser", action = "")
    @WebResult(name = "getDocumentEventsForUserResult", targetNamespace = "http://api.echosign")
    public GetDocumentEventsForUserResult getDocumentEventsForUser(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCredentials", targetNamespace = "http://api.echosign")
        UserCredentials userCredentials,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        DocumentEventsForUserOptions options);

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

    @WebMethod(operationName = "notifyDocumentVaulted", action = "")
    @WebResult(name = "notifyDocumentVaultedResult", targetNamespace = "http://api.echosign")
    public NotifyDocumentVaultedResult notifyDocumentVaulted(
        @WebParam(name = "accessToken", targetNamespace = "http://api.echosign")
        String accessToken,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "vaultEventInfo", targetNamespace = "http://api.echosign")
        VaultEventInfo vaultEventInfo);

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

    @WebMethod(operationName = "replaceSigner", action = "")
    @WebResult(name = "replaceSignerResult", targetNamespace = "http://api.echosign")
    public ReplaceSignerResult replaceSigner(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCredentials", targetNamespace = "http://api.echosign")
        UserCredentials userCredentials,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "replaceSignerOptions", targetNamespace = "http://api.echosign")
        ReplaceSignerOptions replaceSignerOptions);

    @WebMethod(operationName = "deliverDocument", action = "")
    @WebResult(name = "DeliverDocumentResult", targetNamespace = "http://api.echosign")
    public DeliverDocumentResult deliverDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "fileInfo", targetNamespace = "http://api.echosign")
        FileInfo fileInfo);

    @WebMethod(operationName = "personalizeUrlWidget", action = "")
    @WebResult(name = "urlWidgetCreationResult", targetNamespace = "http://api.echosign")
    public UrlWidgetCreationResult personalizeUrlWidget(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "widgetUrl", targetNamespace = "http://api.echosign")
        String widgetUrl,
        @WebParam(name = "personalizationInfo", targetNamespace = "http://api.echosign")
        WidgetPersonalizationInfo personalizationInfo);

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

    @WebMethod(operationName = "getComposeDocumentUrl", action = "")
    @WebResult(name = "getComposeDocumentUrlResult", targetNamespace = "http://api.echosign")
    public GetComposeDocumentUrlResult getComposeDocumentUrl(
        @WebParam(name = "accessToken", targetNamespace = "http://api.echosign")
        String accessToken,
        @WebParam(name = "composeDocumentInfo", targetNamespace = "http://api.echosign")
        ComposeDocumentInfo composeDocumentInfo);

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

    @WebMethod(operationName = "rejectDocument", action = "")
    @WebResult(name = "rejectDocumentResult", targetNamespace = "http://api.echosign")
    public RejectDocumentResult rejectDocument(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCredentials", targetNamespace = "http://api.echosign")
        UserCredentials userCredentials,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "rejectDocumentOptions", targetNamespace = "http://api.echosign")
        RejectDocumentOptions rejectDocumentOptions);

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

    @WebMethod(operationName = "searchUserDocuments", action = "")
    @WebResult(name = "searchUserDocumentsResult", targetNamespace = "http://api.echosign")
    public GetDocumentsForUserResult searchUserDocuments(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "userCredentials", targetNamespace = "http://api.echosign")
        UserCredentials userCredentials,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        SearchUserDocumentsOptions options);

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

    @WebMethod(operationName = "testEchoFile", action = "")
    @WebResult(name = "outFile", targetNamespace = "http://api.echosign")
    public byte[] testEchoFile(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "file", targetNamespace = "http://api.echosign")
        byte[] file);

    @WebMethod(operationName = "sendReminder", action = "")
    @WebResult(name = "sendreminderResult", targetNamespace = "http://api.echosign")
    public SendReminderResult sendReminder(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "comment", targetNamespace = "http://api.echosign")
        String comment);

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

    @WebMethod(operationName = "getUserInfo", action = "")
    @WebResult(name = "getUserInfoResult", targetNamespace = "http://api.echosign")
    public GetUserInfoResult getUserInfo(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        GetUserInfoOptions options);

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

    @WebMethod(operationName = "getDocuments", action = "")
    @WebResult(name = "getDocumentsResult", targetNamespace = "http://api.echosign")
    public GetDocumentsResult getDocuments(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey,
        @WebParam(name = "options", targetNamespace = "http://api.echosign")
        GetDocumentsOptions options);

    @WebMethod(operationName = "getFormData", action = "")
    @WebResult(name = "getFormDataResult", targetNamespace = "http://api.echosign")
    public GetFormDataResult getFormData(
        @WebParam(name = "apiKey", targetNamespace = "http://api.echosign")
        String apiKey,
        @WebParam(name = "documentKey", targetNamespace = "http://api.echosign")
        String documentKey);

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
