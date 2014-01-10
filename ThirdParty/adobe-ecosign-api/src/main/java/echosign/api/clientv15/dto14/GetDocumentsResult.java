
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDocumentsResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDocumentsResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documents" type="{http://dto14.api.echosign}ArrayOfDocumentContent" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://dto14.api.echosign}GetDocumentsErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="supportingDocuments" type="{http://dto14.api.echosign}ArrayOfSupportingDocument" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDocumentsResult", propOrder = {
    "documents",
    "errorCode",
    "errorMessage",
    "success",
    "supportingDocuments"
})
public class GetDocumentsResult {

    @XmlElement(nillable = true)
    protected ArrayOfDocumentContent documents;
    @XmlElement(nillable = true)
    protected GetDocumentsErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    protected Boolean success;
    @XmlElement(nillable = true)
    protected ArrayOfSupportingDocument supportingDocuments;

    /**
     * Gets the value of the documents property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentContent }
     *     
     */
    public ArrayOfDocumentContent getDocuments() {
        return documents;
    }

    /**
     * Sets the value of the documents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentContent }
     *     
     */
    public void setDocuments(ArrayOfDocumentContent value) {
        this.documents = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentsErrorCode }
     *     
     */
    public GetDocumentsErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentsErrorCode }
     *     
     */
    public void setErrorCode(GetDocumentsErrorCode value) {
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
     * Gets the value of the supportingDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSupportingDocument }
     *     
     */
    public ArrayOfSupportingDocument getSupportingDocuments() {
        return supportingDocuments;
    }

    /**
     * Sets the value of the supportingDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSupportingDocument }
     *     
     */
    public void setSupportingDocuments(ArrayOfSupportingDocument value) {
        this.supportingDocuments = value;
    }

}
