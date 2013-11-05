
package echosign.api.clientv15.dto9;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.DocumentKey;


/**
 * <p>Java class for InitiateInteractiveSendDocumentResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitiateInteractiveSendDocumentResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentKey" type="{http://dto.api.echosign}DocumentKey" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://dto9.api.echosign}InitiateInteractiveSendDocumentResultErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="needsInteraction" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sendDocumentURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitiateInteractiveSendDocumentResult", propOrder = {
    "documentKey",
    "errorCode",
    "errorMessage",
    "needsInteraction",
    "sendDocumentURL",
    "success"
})
public class InitiateInteractiveSendDocumentResult {

    @XmlElement(nillable = true)
    protected DocumentKey documentKey;
    @XmlElement(nillable = true)
    protected InitiateInteractiveSendDocumentResultErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    protected Boolean needsInteraction;
    @XmlElement(nillable = true)
    protected String sendDocumentURL;
    protected Boolean success;

    /**
     * Gets the value of the documentKey property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentKey }
     *     
     */
    public DocumentKey getDocumentKey() {
        return documentKey;
    }

    /**
     * Sets the value of the documentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentKey }
     *     
     */
    public void setDocumentKey(DocumentKey value) {
        this.documentKey = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link InitiateInteractiveSendDocumentResultErrorCode }
     *     
     */
    public InitiateInteractiveSendDocumentResultErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitiateInteractiveSendDocumentResultErrorCode }
     *     
     */
    public void setErrorCode(InitiateInteractiveSendDocumentResultErrorCode value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the needsInteraction property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNeedsInteraction() {
        return needsInteraction;
    }

    /**
     * Sets the value of the needsInteraction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedsInteraction(Boolean value) {
        this.needsInteraction = value;
    }

    /**
     * Gets the value of the sendDocumentURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendDocumentURL() {
        return sendDocumentURL;
    }

    /**
     * Sets the value of the sendDocumentURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendDocumentURL(String value) {
        this.sendDocumentURL = value;
    }

    /**
     * Gets the value of the success property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuccess(Boolean value) {
        this.success = value;
    }

}
