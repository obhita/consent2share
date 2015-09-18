
package echosign.api.clientv20.dto20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto18.GetDocumentEventsForUserResultCode;


/**
 * <p>Java class for GetDocumentEventsForUserResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDocumentEventsForUserResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentEventsForUser" type="{http://dto20.api.echosign}ArrayOfDocumentEventForUser" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://dto18.api.echosign}GetDocumentEventsForUserResultCode" minOccurs="0"/>
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
@XmlType(name = "GetDocumentEventsForUserResult", propOrder = {
    "documentEventsForUser",
    "errorCode",
    "errorMessage",
    "success"
})
public class GetDocumentEventsForUserResult {

    @XmlElement(nillable = true)
    protected ArrayOfDocumentEventForUser documentEventsForUser;
    @XmlElement(nillable = true)
    protected GetDocumentEventsForUserResultCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    protected Boolean success;

    /**
     * Gets the value of the documentEventsForUser property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentEventForUser }
     *     
     */
    public ArrayOfDocumentEventForUser getDocumentEventsForUser() {
        return documentEventsForUser;
    }

    /**
     * Sets the value of the documentEventsForUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentEventForUser }
     *     
     */
    public void setDocumentEventsForUser(ArrayOfDocumentEventForUser value) {
        this.documentEventsForUser = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentEventsForUserResultCode }
     *     
     */
    public GetDocumentEventsForUserResultCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentEventsForUserResultCode }
     *     
     */
    public void setErrorCode(GetDocumentEventsForUserResultCode value) {
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
