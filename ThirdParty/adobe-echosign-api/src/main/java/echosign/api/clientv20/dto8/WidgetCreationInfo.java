
package echosign.api.clientv20.dto8;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto.ArrayOfFileInfo;
import echosign.api.clientv20.dto.CallbackInfo;
import echosign.api.clientv20.dto.SecurityOptions;
import echosign.api.clientv20.dto.SignatureFlow;
import echosign.api.clientv20.dto.VaultingInfo;
import echosign.api.clientv20.dto14.ArrayOfRecipientInfo;
import echosign.api.clientv20.dto18.RecipientSecurityOption;
import echosign.api.clientv20.dto9.MergeFieldInfo;


/**
 * <p>Java class for WidgetCreationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WidgetCreationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callbackInfo" type="{http://dto.api.echosign}CallbackInfo" minOccurs="0"/>
 *         &lt;element name="counterSigners" type="{http://dto14.api.echosign}ArrayOfRecipientInfo" minOccurs="0"/>
 *         &lt;element name="fileInfos" type="{http://dto.api.echosign}ArrayOfFileInfo" minOccurs="0"/>
 *         &lt;element name="formFieldLayerTemplates" type="{http://dto.api.echosign}ArrayOfFileInfo" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mergeFieldInfo" type="{http://dto9.api.echosign}MergeFieldInfo" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityOptions" type="{http://dto.api.echosign}SecurityOptions" minOccurs="0"/>
 *         &lt;element name="signatureFlow" type="{http://dto.api.echosign}SignatureFlow" minOccurs="0"/>
 *         &lt;element name="vaultingInfo" type="{http://dto.api.echosign}VaultingInfo" minOccurs="0"/>
 *         &lt;element name="widgetAuthFailureInfo" type="{http://dto8.api.echosign}WidgetCompletionInfo" minOccurs="0"/>
 *         &lt;element name="widgetCompletionInfo" type="{http://dto8.api.echosign}WidgetCompletionInfo" minOccurs="0"/>
 *         &lt;element name="widgetSignerSecurityOptions" type="{http://dto18.api.echosign}RecipientSecurityOption" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WidgetCreationInfo", propOrder = {
    "callbackInfo",
    "counterSigners",
    "fileInfos",
    "formFieldLayerTemplates",
    "locale",
    "mergeFieldInfo",
    "name",
    "securityOptions",
    "signatureFlow",
    "vaultingInfo",
    "widgetAuthFailureInfo",
    "widgetCompletionInfo",
    "widgetSignerSecurityOptions"
})
public class WidgetCreationInfo {

    @XmlElement(nillable = true)
    protected CallbackInfo callbackInfo;
    @XmlElement(nillable = true)
    protected ArrayOfRecipientInfo counterSigners;
    @XmlElement(nillable = true)
    protected ArrayOfFileInfo fileInfos;
    @XmlElement(nillable = true)
    protected ArrayOfFileInfo formFieldLayerTemplates;
    @XmlElement(nillable = true)
    protected String locale;
    @XmlElement(nillable = true)
    protected MergeFieldInfo mergeFieldInfo;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected SecurityOptions securityOptions;
    @XmlElement(nillable = true)
    protected SignatureFlow signatureFlow;
    @XmlElement(nillable = true)
    protected VaultingInfo vaultingInfo;
    @XmlElement(nillable = true)
    protected WidgetCompletionInfo widgetAuthFailureInfo;
    @XmlElement(nillable = true)
    protected WidgetCompletionInfo widgetCompletionInfo;
    @XmlElement(nillable = true)
    protected RecipientSecurityOption widgetSignerSecurityOptions;

    /**
     * Gets the value of the callbackInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CallbackInfo }
     *     
     */
    public CallbackInfo getCallbackInfo() {
        return callbackInfo;
    }

    /**
     * Sets the value of the callbackInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallbackInfo }
     *     
     */
    public void setCallbackInfo(CallbackInfo value) {
        this.callbackInfo = value;
    }

    /**
     * Gets the value of the counterSigners property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRecipientInfo }
     *     
     */
    public ArrayOfRecipientInfo getCounterSigners() {
        return counterSigners;
    }

    /**
     * Sets the value of the counterSigners property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRecipientInfo }
     *     
     */
    public void setCounterSigners(ArrayOfRecipientInfo value) {
        this.counterSigners = value;
    }

    /**
     * Gets the value of the fileInfos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFileInfo }
     *     
     */
    public ArrayOfFileInfo getFileInfos() {
        return fileInfos;
    }

    /**
     * Sets the value of the fileInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFileInfo }
     *     
     */
    public void setFileInfos(ArrayOfFileInfo value) {
        this.fileInfos = value;
    }

    /**
     * Gets the value of the formFieldLayerTemplates property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFileInfo }
     *     
     */
    public ArrayOfFileInfo getFormFieldLayerTemplates() {
        return formFieldLayerTemplates;
    }

    /**
     * Sets the value of the formFieldLayerTemplates property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFileInfo }
     *     
     */
    public void setFormFieldLayerTemplates(ArrayOfFileInfo value) {
        this.formFieldLayerTemplates = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the mergeFieldInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MergeFieldInfo }
     *     
     */
    public MergeFieldInfo getMergeFieldInfo() {
        return mergeFieldInfo;
    }

    /**
     * Sets the value of the mergeFieldInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MergeFieldInfo }
     *     
     */
    public void setMergeFieldInfo(MergeFieldInfo value) {
        this.mergeFieldInfo = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the securityOptions property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityOptions }
     *     
     */
    public SecurityOptions getSecurityOptions() {
        return securityOptions;
    }

    /**
     * Sets the value of the securityOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityOptions }
     *     
     */
    public void setSecurityOptions(SecurityOptions value) {
        this.securityOptions = value;
    }

    /**
     * Gets the value of the signatureFlow property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureFlow }
     *     
     */
    public SignatureFlow getSignatureFlow() {
        return signatureFlow;
    }

    /**
     * Sets the value of the signatureFlow property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureFlow }
     *     
     */
    public void setSignatureFlow(SignatureFlow value) {
        this.signatureFlow = value;
    }

    /**
     * Gets the value of the vaultingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VaultingInfo }
     *     
     */
    public VaultingInfo getVaultingInfo() {
        return vaultingInfo;
    }

    /**
     * Sets the value of the vaultingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaultingInfo }
     *     
     */
    public void setVaultingInfo(VaultingInfo value) {
        this.vaultingInfo = value;
    }

    /**
     * Gets the value of the widgetAuthFailureInfo property.
     * 
     * @return
     *     possible object is
     *     {@link WidgetCompletionInfo }
     *     
     */
    public WidgetCompletionInfo getWidgetAuthFailureInfo() {
        return widgetAuthFailureInfo;
    }

    /**
     * Sets the value of the widgetAuthFailureInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link WidgetCompletionInfo }
     *     
     */
    public void setWidgetAuthFailureInfo(WidgetCompletionInfo value) {
        this.widgetAuthFailureInfo = value;
    }

    /**
     * Gets the value of the widgetCompletionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link WidgetCompletionInfo }
     *     
     */
    public WidgetCompletionInfo getWidgetCompletionInfo() {
        return widgetCompletionInfo;
    }

    /**
     * Sets the value of the widgetCompletionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link WidgetCompletionInfo }
     *     
     */
    public void setWidgetCompletionInfo(WidgetCompletionInfo value) {
        this.widgetCompletionInfo = value;
    }

    /**
     * Gets the value of the widgetSignerSecurityOptions property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientSecurityOption }
     *     
     */
    public RecipientSecurityOption getWidgetSignerSecurityOptions() {
        return widgetSignerSecurityOptions;
    }

    /**
     * Sets the value of the widgetSignerSecurityOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientSecurityOption }
     *     
     */
    public void setWidgetSignerSecurityOptions(RecipientSecurityOption value) {
        this.widgetSignerSecurityOptions = value;
    }

}
