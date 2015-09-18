
package echosign.api.clientv20.service;

import javax.jws.WebService;
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

@WebService(serviceName = "EchoSignDocumentService20", targetNamespace = "http://api.echosign", endpointInterface = "echosign.api.clientv20.service.EchoSignDocumentService20PortType")
public class EchoSignDocumentService20Impl
    implements EchoSignDocumentService20PortType
{


    public EmbeddedWidgetCreationResult createPersonalEmbeddedWidget(String apiKey, SenderInfo senderInfo, WidgetCreationInfo widgetInfo, WidgetPersonalizationInfo personalizationInfo) {
        throw new UnsupportedOperationException();
    }

    public UrlWidgetCreationResult createUrlWidget(String apiKey, SenderInfo senderInfo, WidgetCreationInfo widgetInfo) {
        throw new UnsupportedOperationException();
    }

    public GetUsersInGroupResult getUsersInGroup(String apiKey, String groupKey) {
        throw new UnsupportedOperationException();
    }

    public EmbeddedWidgetCreationResult personalizeEmbeddedWidget(String apiKey, String widgetJavascript, WidgetPersonalizationInfo personalizationInfo) {
        throw new UnsupportedOperationException();
    }

    public DocumentUrlResult getLatestDocumentUrl(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public UrlWidgetCreationResult createPersonalUrlWidget(String apiKey, SenderInfo senderInfo, WidgetCreationInfo widgetInfo, WidgetPersonalizationInfo personalizationInfo) {
        throw new UnsupportedOperationException();
    }

    public GetSignerFormFieldsResult getSignerFormFields(String apiKey, String documentKey, GetSignerFormFieldsOptions options) {
        throw new UnsupportedOperationException();
    }

    public Pong testPing(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public CreateGroupResult createGroup(String apiKey, String groupName) {
        throw new UnsupportedOperationException();
    }

    public RemoveDocumentResult removeDocument(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public InitiateInteractiveSendDocumentResult initiateInteractiveSendDocument(String apiKey, SenderInfo senderInfo, DocumentCreationInfo documentCreationInfo, boolean forceSendConfirmation, Boolean authoringRequested) {
        throw new UnsupportedOperationException();
    }

    public SendDocumentInteractiveResult createLibraryDocumentInteractive(String apiKey, SenderInfo senderInfo, LibraryDocumentCreationInfo libraryDocumentCreationInfo, SendDocumentInteractiveOptions sendDocumentInteractiveOptions) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentsForUserResult getDocumentsForUser(String apiKey, String userKey) {
        throw new UnsupportedOperationException();
    }

    public DocumentImageList getLatestImages(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public DocumentInfo getDocumentInfo(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentPagesInfoResult getDocumentPagesInfo(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public GetEmbeddedViewResult getEmbeddedView(String accessToken, OnBehalfOfUser onBehalfOfUser, EmbeddedViewOptions embeddedViewOptions) {
        throw new UnsupportedOperationException();
    }

    public RenameGroupResult renameGroup(String apiKey, String groupKey, String newGroupName) {
        throw new UnsupportedOperationException();
    }

    public MoveUsersToGroupResult moveUsersToGroup(String apiKey, String groupKey, UsersToMoveInfo usersToMoveInfo) {
        throw new UnsupportedOperationException();
    }

    public EnableWidgetResult enableWidget(String apiKey, String documentKey, EnableWidgetOptions options) {
        throw new UnsupportedOperationException();
    }

    public DocumentUrlResult getDocumentUrlByVersion(String apiKey, String versionKey) {
        throw new UnsupportedOperationException();
    }

    public DelegateSigningResult delegateSigning(String apiKey, UserCredentials userCredentials, String documentKey, DelegateSigningOptions delegateSigningOptions) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentsForUserResult getMyDocuments(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public SigningUrlResult getSigningUrl(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public DisableWidgetResult disableWidget(String apiKey, String documentKey, DisableWidgetOptions options) {
        throw new UnsupportedOperationException();
    }

    public SendDocumentInteractiveResult sendDocumentInteractive(String apiKey, SenderInfo senderInfo, DocumentCreationInfo documentCreationInfo, SendDocumentInteractiveOptions sendDocumentInteractiveOptions) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentEventsForUserResult getDocumentEventsForUser(String apiKey, UserCredentials userCredentials, DocumentEventsForUserOptions options) {
        throw new UnsupportedOperationException();
    }

    public DocumentInfoList getDocumentInfosByExternalId(String apiKey, String email, String password, ExternalId externalId) {
        throw new UnsupportedOperationException();
    }

    public NotifyDocumentVaultedResult notifyDocumentVaulted(String accessToken, String documentKey, VaultEventInfo vaultEventInfo) {
        throw new UnsupportedOperationException();
    }

    public CancelDocumentResult cancelDocument(String apiKey, String documentKey, String comment, boolean notifySigner) {
        throw new UnsupportedOperationException();
    }

    public ReplaceSignerResult replaceSigner(String apiKey, UserCredentials userCredentials, String documentKey, ReplaceSignerOptions replaceSignerOptions) {
        throw new UnsupportedOperationException();
    }

    public DeliverDocumentResult deliverDocument(String apiKey, FileInfo fileInfo) {
        throw new UnsupportedOperationException();
    }

    public UrlWidgetCreationResult personalizeUrlWidget(String apiKey, String widgetUrl, WidgetPersonalizationInfo personalizationInfo) {
        throw new UnsupportedOperationException();
    }

    public String createUser(String apiKey, UserCreationInfo userInfo) {
        throw new UnsupportedOperationException();
    }

    public GetMegaSignDocumentResult getMegaSignDocument(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public GetUsersInAccountResult getUsersInAccount(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public CreateAccountResult createAccount(String apiKey, UserCreationInfo userCreationInfo, AccountCreationInfo accountCreationInfo) {
        throw new UnsupportedOperationException();
    }

    public GetComposeDocumentUrlResult getComposeDocumentUrl(String accessToken, ComposeDocumentInfo composeDocumentInfo) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentImageUrlsResult getDocumentImageUrls(String apiKey, String documentKey, GetDocumentImageUrlsOptions options) {
        throw new UnsupportedOperationException();
    }

    public GetLibraryDocumentsForUserResult getLibraryDocumentsForUser(String apiKey, UserCredentials userCredentials) {
        throw new UnsupportedOperationException();
    }

    public RejectDocumentResult rejectDocument(String apiKey, UserCredentials userCredentials, String documentKey, RejectDocumentOptions rejectDocumentOptions) {
        throw new UnsupportedOperationException();
    }

    public byte[] getDocumentByVersion(String apiKey, String versionKey) {
        throw new UnsupportedOperationException();
    }

    public GetLibraryDocumentsForUserResult getMyLibraryDocuments(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public GetWidgetsForUserResult getWidgetsForUser(String apiKey, UserCredentials userCredentials) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentsForUserResult searchUserDocuments(String apiKey, UserCredentials userCredentials, SearchUserDocumentsOptions options) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentsForUserResult getUserDocuments(String apiKey, UserCredentials userCredentials) {
        throw new UnsupportedOperationException();
    }

    public AuditTrailResult getAuditTrail(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public GetGroupsInAccountResult getGroupsInAccount(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public DeleteGroupResult deleteGroup(String apiKey, String groupKey) {
        throw new UnsupportedOperationException();
    }

    public FormCreationResult createForm(String apiKey, SenderInfo senderInfo, FormCreationInfo formInfo) {
        throw new UnsupportedOperationException();
    }

    public UserVerificationInfo verifyUser(String apiKey, String email, String password) {
        throw new UnsupportedOperationException();
    }

    public byte[] testEchoFile(String apiKey, byte[] file) {
        throw new UnsupportedOperationException();
    }

    public SendReminderResult sendReminder(String apiKey, String documentKey, String comment) {
        throw new UnsupportedOperationException();
    }

    public GetWidgetsForUserResult getMyWidgets(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public GetSupportingDocumentsResult getSupportingDocuments(String apiKey, String documentKey, ArrayOfString supportingDocumentKeys, GetSupportingDocumentsOptions options) {
        throw new UnsupportedOperationException();
    }

    public GetUserInfoResult getUserInfo(String apiKey, GetUserInfoOptions options) {
        throw new UnsupportedOperationException();
    }

    public EmbeddedWidgetCreationResult createEmbeddedWidget(String apiKey, SenderInfo senderInfo, WidgetCreationInfo widgetInfo) {
        throw new UnsupportedOperationException();
    }

    public ArrayOfDocumentKey sendDocument(String apiKey, SenderInfo senderInfo, DocumentCreationInfo documentCreationInfo) {
        throw new UnsupportedOperationException();
    }

    public byte[] getLatestDocument(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public SendDocumentMegaSignResult sendDocumentMegaSign(String apiKey, SenderInfo senderInfo, DocumentCreationInfo documentCreationInfo) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentsResult getDocuments(String apiKey, String documentKey, GetDocumentsOptions options) {
        throw new UnsupportedOperationException();
    }

    public GetFormDataResult getFormData(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentUrlsResult getDocumentUrls(String apiKey, String documentKey, GetDocumentUrlsOptions options) {
        throw new UnsupportedOperationException();
    }

    public DocumentImageList getImagesByVersion(String apiKey, String versionKey) {
        throw new UnsupportedOperationException();
    }

    public LibraryDocumentCreationResult createLibraryDocument(String apiKey, SenderInfo senderInfo, LibraryDocumentCreationInfo libraryDocumentCreationInfo) {
        throw new UnsupportedOperationException();
    }

}
