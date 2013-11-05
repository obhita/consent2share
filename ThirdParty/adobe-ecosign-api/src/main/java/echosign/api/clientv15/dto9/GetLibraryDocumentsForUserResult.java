
package echosign.api.clientv15.dto9;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetLibraryDocumentsForUserResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetLibraryDocumentsForUserResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://dto9.api.echosign}GetLibraryDocumentsForUserErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="libraryDocuments" type="{http://dto9.api.echosign}ArrayOfDocumentLibraryItem" minOccurs="0"/>
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
@XmlType(name = "GetLibraryDocumentsForUserResult", propOrder = {
    "errorCode",
    "errorMessage",
    "libraryDocuments",
    "success"
})
public class GetLibraryDocumentsForUserResult {

    @XmlElement(nillable = true)
    protected GetLibraryDocumentsForUserErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    @XmlElement(nillable = true)
    protected ArrayOfDocumentLibraryItem libraryDocuments;
    protected Boolean success;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetLibraryDocumentsForUserErrorCode }
     *     
     */
    public GetLibraryDocumentsForUserErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetLibraryDocumentsForUserErrorCode }
     *     
     */
    public void setErrorCode(GetLibraryDocumentsForUserErrorCode value) {
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
     * Gets the value of the libraryDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentLibraryItem }
     *     
     */
    public ArrayOfDocumentLibraryItem getLibraryDocuments() {
        return libraryDocuments;
    }

    /**
     * Sets the value of the libraryDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentLibraryItem }
     *     
     */
    public void setLibraryDocuments(ArrayOfDocumentLibraryItem value) {
        this.libraryDocuments = value;
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
