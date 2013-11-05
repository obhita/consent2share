
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendDocumentMegaSignResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SendDocumentMegaSignResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentKey" type="{http://dto.api.echosign}DocumentKey" minOccurs="0"/>
 *         &lt;element name="documentKeyArray" type="{http://dto.api.echosign}ArrayOfDocumentKey" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://dto.api.echosign}SendDocumentMegaSignResultErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "SendDocumentMegaSignResult", propOrder = {
    "documentKey",
    "documentKeyArray",
    "errorCode",
    "errorMessage",
    "success"
})
public class SendDocumentMegaSignResult {

    @XmlElement(nillable = true)
    protected DocumentKey documentKey;
    @XmlElement(nillable = true)
    protected ArrayOfDocumentKey documentKeyArray;
    @XmlElement(nillable = true)
    protected SendDocumentMegaSignResultErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
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
     * Gets the value of the documentKeyArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentKey }
     *     
     */
    public ArrayOfDocumentKey getDocumentKeyArray() {
        return documentKeyArray;
    }

    /**
     * Sets the value of the documentKeyArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentKey }
     *     
     */
    public void setDocumentKeyArray(ArrayOfDocumentKey value) {
        this.documentKeyArray = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link SendDocumentMegaSignResultErrorCode }
     *     
     */
    public SendDocumentMegaSignResultErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendDocumentMegaSignResultErrorCode }
     *     
     */
    public void setErrorCode(SendDocumentMegaSignResultErrorCode value) {
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
