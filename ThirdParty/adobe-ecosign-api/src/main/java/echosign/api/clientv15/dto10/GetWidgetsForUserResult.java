
package echosign.api.clientv15.dto10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetWidgetsForUserResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetWidgetsForUserResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://dto10.api.echosign}GetWidgetsForUserErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="widgets" type="{http://dto10.api.echosign}ArrayOfWidgetItem" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetWidgetsForUserResult", propOrder = {
    "errorCode",
    "errorMessage",
    "success",
    "widgets"
})
public class GetWidgetsForUserResult {

    @XmlElement(nillable = true)
    protected GetWidgetsForUserErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    protected Boolean success;
    @XmlElement(nillable = true)
    protected ArrayOfWidgetItem widgets;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetWidgetsForUserErrorCode }
     *     
     */
    public GetWidgetsForUserErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetWidgetsForUserErrorCode }
     *     
     */
    public void setErrorCode(GetWidgetsForUserErrorCode value) {
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
     * Gets the value of the widgets property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWidgetItem }
     *     
     */
    public ArrayOfWidgetItem getWidgets() {
        return widgets;
    }

    /**
     * Sets the value of the widgets property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWidgetItem }
     *     
     */
    public void setWidgets(ArrayOfWidgetItem value) {
        this.widgets = value;
    }

}
