
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto14.GetDocumentsForUserResultCode;


/**
 * <p>Java class for GetDocumentsForUserResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDocumentsForUserResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentListForUser" type="{http://dto17.api.echosign}ArrayOfDocumentListItem" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://dto14.api.echosign}GetDocumentsForUserResultCode" minOccurs="0"/>
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
@XmlType(name = "GetDocumentsForUserResult", propOrder = {
    "documentListForUser",
    "errorCode",
    "errorMessage",
    "success"
})
public class GetDocumentsForUserResult {

    @XmlElement(nillable = true)
    protected ArrayOfDocumentListItem documentListForUser;
    @XmlElement(nillable = true)
    protected GetDocumentsForUserResultCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    protected Boolean success;

    /**
     * Gets the value of the documentListForUser property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentListItem }
     *     
     */
    public ArrayOfDocumentListItem getDocumentListForUser() {
        return documentListForUser;
    }

    /**
     * Sets the value of the documentListForUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentListItem }
     *     
     */
    public void setDocumentListForUser(ArrayOfDocumentListItem value) {
        this.documentListForUser = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentsForUserResultCode }
     *     
     */
    public GetDocumentsForUserResultCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentsForUserResultCode }
     *     
     */
    public void setErrorCode(GetDocumentsForUserResultCode value) {
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
