
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDocumentPagesInfoResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDocumentPagesInfoResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentPagesInfo" type="{http://dto19.api.echosign}ArrayOfDocumentPageInfo" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://dto19.api.echosign}GetDocumentPagesInfoResultErrorCode" minOccurs="0"/>
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
@XmlType(name = "GetDocumentPagesInfoResult", propOrder = {
    "documentPagesInfo",
    "errorCode",
    "errorMessage",
    "success"
})
public class GetDocumentPagesInfoResult {

    @XmlElement(nillable = true)
    protected ArrayOfDocumentPageInfo documentPagesInfo;
    @XmlElement(nillable = true)
    protected GetDocumentPagesInfoResultErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    protected Boolean success;

    /**
     * Gets the value of the documentPagesInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentPageInfo }
     *     
     */
    public ArrayOfDocumentPageInfo getDocumentPagesInfo() {
        return documentPagesInfo;
    }

    /**
     * Sets the value of the documentPagesInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentPageInfo }
     *     
     */
    public void setDocumentPagesInfo(ArrayOfDocumentPageInfo value) {
        this.documentPagesInfo = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentPagesInfoResultErrorCode }
     *     
     */
    public GetDocumentPagesInfoResultErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentPagesInfoResultErrorCode }
     *     
     */
    public void setErrorCode(GetDocumentPagesInfoResultErrorCode value) {
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
