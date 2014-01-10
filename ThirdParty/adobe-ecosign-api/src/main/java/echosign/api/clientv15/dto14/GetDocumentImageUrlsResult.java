
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDocumentImageUrlsResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDocumentImageUrlsResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://dto14.api.echosign}GetDocumentImageUrlsErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imageUrls" type="{http://dto14.api.echosign}ArrayOfDocumentImageUrls" minOccurs="0"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="supportingDocumentImageUrls" type="{http://dto14.api.echosign}ArrayOfSupportingDocumentImageUrls" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDocumentImageUrlsResult", propOrder = {
    "errorCode",
    "errorMessage",
    "imageUrls",
    "success",
    "supportingDocumentImageUrls"
})
public class GetDocumentImageUrlsResult {

    @XmlElement(nillable = true)
    protected GetDocumentImageUrlsErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    @XmlElement(nillable = true)
    protected ArrayOfDocumentImageUrls imageUrls;
    protected Boolean success;
    @XmlElement(nillable = true)
    protected ArrayOfSupportingDocumentImageUrls supportingDocumentImageUrls;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentImageUrlsErrorCode }
     *     
     */
    public GetDocumentImageUrlsErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentImageUrlsErrorCode }
     *     
     */
    public void setErrorCode(GetDocumentImageUrlsErrorCode value) {
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
     * Gets the value of the imageUrls property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentImageUrls }
     *     
     */
    public ArrayOfDocumentImageUrls getImageUrls() {
        return imageUrls;
    }

    /**
     * Sets the value of the imageUrls property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentImageUrls }
     *     
     */
    public void setImageUrls(ArrayOfDocumentImageUrls value) {
        this.imageUrls = value;
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

    /**
     * Gets the value of the supportingDocumentImageUrls property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSupportingDocumentImageUrls }
     *     
     */
    public ArrayOfSupportingDocumentImageUrls getSupportingDocumentImageUrls() {
        return supportingDocumentImageUrls;
    }

    /**
     * Sets the value of the supportingDocumentImageUrls property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSupportingDocumentImageUrls }
     *     
     */
    public void setSupportingDocumentImageUrls(ArrayOfSupportingDocumentImageUrls value) {
        this.supportingDocumentImageUrls = value;
    }

}
