
package echosign.api.clientv15.service;

import javax.jws.WebService;
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

@WebService(serviceName = "EchoSignDocumentService15", targetNamespace = "http://api.echosign", endpointInterface = "echosign.api.clientv15.service.EchoSignDocumentService15PortType")
public class EchoSignDocumentService15Impl
    implements EchoSignDocumentService15PortType
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

    public Pong testPing(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public CreateGroupResult createGroup(String apiKey, String groupName) {
        throw new UnsupportedOperationException();
    }

    public InitiateInteractiveSendDocumentResult initiateInteractiveSendDocument(String apiKey, SenderInfo senderInfo, DocumentCreationInfo documentCreationInfo, boolean forceSendConfirmation, boolean authoringRequested) {
        throw new UnsupportedOperationException();
    }

    public RemoveDocumentResult removeDocument(String apiKey, String documentKey) {
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

    public MoveUsersToGroupResult moveUsersToGroup(String apiKey, String groupKey, UsersToMoveInfo usersToMoveInfo) {
        throw new UnsupportedOperationException();
    }

    public RenameGroupResult renameGroup(String apiKey, String groupKey, String newGroupName) {
        throw new UnsupportedOperationException();
    }

    public DocumentUrlResult getDocumentUrlByVersion(String apiKey, String versionKey) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentsForUserResult getMyDocuments(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public SigningUrlResult getSigningUrl(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public SendDocumentInteractiveResult sendDocumentInteractive(String apiKey, SenderInfo senderInfo, DocumentCreationInfo documentCreationInfo, SendDocumentInteractiveOptions sendDocumentInteractiveOptions) {
        throw new UnsupportedOperationException();
    }

    public DocumentInfoList getDocumentInfosByExternalId(String apiKey, String email, String password, ExternalId externalId) {
        throw new UnsupportedOperationException();
    }

    public CancelDocumentResult cancelDocument(String apiKey, String documentKey, String comment, boolean notifySigner) {
        throw new UnsupportedOperationException();
    }

    public DeliverDocumentResult deliverDocument(String apiKey, FileInfo fileInfo) {
        throw new UnsupportedOperationException();
    }

    public String createUser(String apiKey, UserCreationInfo userInfo) {
        throw new UnsupportedOperationException();
    }

    public GetMegaSignDocumentResult getMegaSignDocument(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public UrlWidgetCreationResult personalizeUrlWidget(String apiKey, String widgetUrl, WidgetPersonalizationInfo personalizationInfo) {
        throw new UnsupportedOperationException();
    }

    public GetUsersInAccountResult getUsersInAccount(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public CreateAccountResult createAccount(String apiKey, UserCreationInfo userCreationInfo, AccountCreationInfo accountCreationInfo) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentImageUrlsResult getDocumentImageUrls(String apiKey, String documentKey, GetDocumentImageUrlsOptions options) {
        throw new UnsupportedOperationException();
    }

    public GetLibraryDocumentsForUserResult getLibraryDocumentsForUser(String apiKey, UserCredentials userCredentials) {
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

    public SendReminderResult sendReminder(String apiKey, String documentKey, String comment) {
        throw new UnsupportedOperationException();
    }

    public byte[] testEchoFile(String apiKey, byte[] file) {
        throw new UnsupportedOperationException();
    }

    public GetWidgetsForUserResult getMyWidgets(String apiKey) {
        throw new UnsupportedOperationException();
    }

    public GetSupportingDocumentsResult getSupportingDocuments(String apiKey, String documentKey, ArrayOfString supportingDocumentKeys, GetSupportingDocumentsOptions options) {
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

    public GetFormDataResult getFormData(String apiKey, String documentKey) {
        throw new UnsupportedOperationException();
    }

    public GetDocumentsResult getDocuments(String apiKey, String documentKey, GetDocumentsOptions options) {
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
