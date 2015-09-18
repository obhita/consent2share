
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto14.ArrayOfRecipientInfo;


/**
 * <p>Java class for ComposeDocumentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComposeDocumentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="composeDocumentType" type="{http://dto17.api.echosign}ComposeDocumentType" minOccurs="0"/>
 *         &lt;element name="deviceInfo" type="{http://dto17.api.echosign}DeviceInfo" minOccurs="0"/>
 *         &lt;element name="file" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recipients" type="{http://dto14.api.echosign}ArrayOfRecipientInfo" minOccurs="0"/>
 *         &lt;element name="signatureLocation" type="{http://dto17.api.echosign}SignatureLocation" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComposeDocumentInfo", propOrder = {
    "composeDocumentType",
    "deviceInfo",
    "file",
    "fileName",
    "locale",
    "message",
    "recipients",
    "signatureLocation",
    "title"
})
public class ComposeDocumentInfo {

    @XmlElement(nillable = true)
    protected ComposeDocumentType composeDocumentType;
    @XmlElement(nillable = true)
    protected DeviceInfo deviceInfo;
    @XmlElement(nillable = true)
    protected byte[] file;
    @XmlElement(nillable = true)
    protected String fileName;
    @XmlElement(nillable = true)
    protected String locale;
    @XmlElement(nillable = true)
    protected String message;
    @XmlElement(nillable = true)
    protected ArrayOfRecipientInfo recipients;
    @XmlElement(nillable = true)
    protected SignatureLocation signatureLocation;
    @XmlElement(nillable = true)
    protected String title;

    /**
     * Gets the value of the composeDocumentType property.
     * 
     * @return
     *     possible object is
     *     {@link ComposeDocumentType }
     *     
     */
    public ComposeDocumentType getComposeDocumentType() {
        return composeDocumentType;
    }

    /**
     * Sets the value of the composeDocumentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComposeDocumentType }
     *     
     */
    public void setComposeDocumentType(ComposeDocumentType value) {
        this.composeDocumentType = value;
    }

    /**
     * Gets the value of the deviceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceInfo }
     *     
     */
    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    /**
     * Sets the value of the deviceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceInfo }
     *     
     */
    public void setDeviceInfo(DeviceInfo value) {
        this.deviceInfo = value;
    }

    /**
     * Gets the value of the file property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFile() {
        return file;
    }

    /**
     * Sets the value of the file property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFile(byte[] value) {
        this.file = ((byte[]) value);
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
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
     * Gets the value of the signatureLocation property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureLocation }
     *     
     */
    public SignatureLocation getSignatureLocation() {
        return signatureLocation;
    }

    /**
     * Sets the value of the signatureLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureLocation }
     *     
     */
    public void setSignatureLocation(SignatureLocation value) {
        this.signatureLocation = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
