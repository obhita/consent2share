
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.ArrayOfString;
import echosign.api.clientv15.dto14.ArrayOfRecipientInfo;
import echosign.api.clientv15.dto9.MergeFieldInfo;


/**
 * <p>Java class for DocumentCreationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentCreationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callbackInfo" type="{http://dto.api.echosign}CallbackInfo" minOccurs="0"/>
 *         &lt;element name="ccs" type="{http://api.echosign}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="daysUntilSigningDeadline" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="externalId" type="{http://dto.api.echosign}ExternalId" minOccurs="0"/>
 *         &lt;element name="fileInfos" type="{http://dto.api.echosign}ArrayOfFileInfo" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mergeFieldInfo" type="{http://dto9.api.echosign}MergeFieldInfo" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recipients" type="{http://dto14.api.echosign}ArrayOfRecipientInfo" minOccurs="0"/>
 *         &lt;element name="reminderFrequency" type="{http://dto.api.echosign}ReminderFrequency" minOccurs="0"/>
 *         &lt;element name="securityOptions" type="{http://dto.api.echosign}SecurityOptions" minOccurs="0"/>
 *         &lt;element name="signatureFlow" type="{http://dto.api.echosign}SignatureFlow" minOccurs="0"/>
 *         &lt;element name="signatureType" type="{http://dto.api.echosign}SignatureType" minOccurs="0"/>
 *         &lt;element name="tos" type="{http://api.echosign}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentCreationInfo", propOrder = {
    "callbackInfo",
    "ccs",
    "daysUntilSigningDeadline",
    "externalId",
    "fileInfos",
    "locale",
    "mergeFieldInfo",
    "message",
    "name",
    "recipients",
    "reminderFrequency",
    "securityOptions",
    "signatureFlow",
    "signatureType",
    "tos"
})
public class DocumentCreationInfo {

    @XmlElement(nillable = true)
    protected CallbackInfo callbackInfo;
    @XmlElement(nillable = true)
    protected ArrayOfString ccs;
    @XmlElement(nillable = true)
    protected Integer daysUntilSigningDeadline;
    @XmlElement(nillable = true)
    protected ExternalId externalId;
    @XmlElement(nillable = true)
    protected ArrayOfFileInfo fileInfos;
    @XmlElement(nillable = true)
    protected String locale;
    @XmlElement(nillable = true)
    protected MergeFieldInfo mergeFieldInfo;
    @XmlElement(nillable = true)
    protected String message;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected ArrayOfRecipientInfo recipients;
    @XmlElement(nillable = true)
    protected ReminderFrequency reminderFrequency;
    @XmlElement(nillable = true)
    protected SecurityOptions securityOptions;
    @XmlElement(nillable = true)
    protected SignatureFlow signatureFlow;
    @XmlElement(nillable = true)
    protected SignatureType signatureType;
    @XmlElement(nillable = true)
    protected ArrayOfString tos;

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
     * Gets the value of the ccs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getCcs() {
        return ccs;
    }

    /**
     * Sets the value of the ccs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setCcs(ArrayOfString value) {
        this.ccs = value;
    }

    /**
     * Gets the value of the daysUntilSigningDeadline property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDaysUntilSigningDeadline() {
        return daysUntilSigningDeadline;
    }

    /**
     * Sets the value of the daysUntilSigningDeadline property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDaysUntilSigningDeadline(Integer value) {
        this.daysUntilSigningDeadline = value;
    }

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalId }
     *     
     */
    public ExternalId getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalId }
     *     
     */
    public void setExternalId(ExternalId value) {
        this.externalId = value;
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
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
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
     * Gets the value of the recipients property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRecipientInfo }
     *     
     */
    public ArrayOfRecipientInfo getRecipients() {
        return recipients;
    }

    /**
     * Sets the value of the recipients property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRecipientInfo }
     *     
     */
    public void setRecipients(ArrayOfRecipientInfo value) {
        this.recipients = value;
    }

    /**
     * Gets the value of the reminderFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link ReminderFrequency }
     *     
     */
    public ReminderFrequency getReminderFrequency() {
        return reminderFrequency;
    }

    /**
     * Sets the value of the reminderFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReminderFrequency }
     *     
     */
    public void setReminderFrequency(ReminderFrequency value) {
        this.reminderFrequency = value;
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
     * Gets the value of the signatureType property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignatureType() {
        return signatureType;
    }

    /**
     * Sets the value of the signatureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignatureType(SignatureType value) {
        this.signatureType = value;
    }

    /**
     * Gets the value of the tos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getTos() {
        return tos;
    }

    /**
     * Sets the value of the tos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setTos(ArrayOfString value) {
        this.tos = value;
    }

}
