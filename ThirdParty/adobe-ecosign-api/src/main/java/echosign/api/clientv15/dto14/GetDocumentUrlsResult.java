
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDocumentUrlsResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDocumentUrlsResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://dto14.api.echosign}GetDocumentUrlsErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="supportingDocumentUrls" type="{http://dto14.api.echosign}ArrayOfSupportingDocumentUrl" minOccurs="0"/>
 *         &lt;element name="urls" type="{http://dto14.api.echosign}ArrayOfDocumentUrl" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDocumentUrlsResult", propOrder = {
    "errorCode",
    "errorMessage",
    "success",
    "supportingDocumentUrls",
    "urls"
})
public class GetDocumentUrlsResult {

    @XmlElement(nillable = true)
    protected GetDocumentUrlsErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    protected Boolean success;
    @XmlElement(nillable = true)
    protected ArrayOfSupportingDocumentUrl supportingDocumentUrls;
    @XmlElement(nillable = true)
    protected ArrayOfDocumentUrl urls;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentUrlsErrorCode }
     *     
     */
    public GetDocumentUrlsErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentUrlsErrorCode }
     *     
     */
    public void setErrorCode(GetDocumentUrlsErrorCode value) {
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

    /**
     * Gets the value of the supportingDocumentUrls property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSupportingDocumentUrl }
     *     
     */
    public ArrayOfSupportingDocumentUrl getSupportingDocumentUrls() {
        return supportingDocumentUrls;
    }

    /**
     * Sets the value of the supportingDocumentUrls property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSupportingDocumentUrl }
     *     
     */
    public void setSupportingDocumentUrls(ArrayOfSupportingDocumentUrl value) {
        this.supportingDocumentUrls = value;
    }

    /**
     * Gets the value of the urls property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentUrl }
     *     
     */
    public ArrayOfDocumentUrl getUrls() {
        return urls;
    }

    /**
     * Sets the value of the urls property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentUrl }
     *     
     */
    public void setUrls(ArrayOfDocumentUrl value) {
        this.urls = value;
    }

}
