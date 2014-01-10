
package echosign.api.clientv15.dto8;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmbeddedWidgetCreationResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmbeddedWidgetCreationResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://dto8.api.echosign}EmbeddedWidgetCreationResultErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="javascript" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "EmbeddedWidgetCreationResult", propOrder = {
    "documentKey",
    "errorCode",
    "errorMessage",
    "javascript",
    "success"
})
public class EmbeddedWidgetCreationResult {

    @XmlElement(nillable = true)
    protected String documentKey;
    @XmlElement(nillable = true)
    protected EmbeddedWidgetCreationResultErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    @XmlElement(nillable = true)
    protected String javascript;
    protected Boolean success;

    /**
     * Gets the value of the documentKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentKey() {
        return documentKey;
    }

    /**
     * Sets the value of the documentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentKey(String value) {
        this.documentKey = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link EmbeddedWidgetCreationResultErrorCode }
     *     
     */
    public EmbeddedWidgetCreationResultErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmbeddedWidgetCreationResultErrorCode }
     *     
     */
    public void setErrorCode(EmbeddedWidgetCreationResultErrorCode value) {
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
     * Gets the value of the javascript property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavascript() {
        return javascript;
    }

    /**
     * Sets the value of the javascript property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavascript(String value) {
        this.javascript = value;
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
