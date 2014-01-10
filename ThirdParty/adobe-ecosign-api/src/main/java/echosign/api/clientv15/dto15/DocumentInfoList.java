
package echosign.api.clientv15.dto15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto11.DocumentInfoListErrorCode;


/**
 * <p>Java class for DocumentInfoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentInfoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentInfos" type="{http://dto15.api.echosign}ArrayOfDocumentInfo" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://dto11.api.echosign}DocumentInfoListErrorCode" minOccurs="0"/>
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
@XmlType(name = "DocumentInfoList", propOrder = {
    "documentInfos",
    "errorCode",
    "errorMessage",
    "success"
})
public class DocumentInfoList {

    @XmlElement(nillable = true)
    protected ArrayOfDocumentInfo documentInfos;
    @XmlElement(nillable = true)
    protected DocumentInfoListErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    protected Boolean success;

    /**
     * Gets the value of the documentInfos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentInfo }
     *     
     */
    public ArrayOfDocumentInfo getDocumentInfos() {
        return documentInfos;
    }

    /**
     * Sets the value of the documentInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentInfo }
     *     
     */
    public void setDocumentInfos(ArrayOfDocumentInfo value) {
        this.documentInfos = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentInfoListErrorCode }
     *     
     */
    public DocumentInfoListErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentInfoListErrorCode }
     *     
     */
    public void setErrorCode(DocumentInfoListErrorCode value) {
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
