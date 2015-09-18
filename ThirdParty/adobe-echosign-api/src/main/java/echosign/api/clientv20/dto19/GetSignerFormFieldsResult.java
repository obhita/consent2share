
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.ArrayOfString;


/**
 * <p>Java class for GetSignerFormFieldsResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetSignerFormFieldsResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://dto19.api.echosign}GetSignerFormFieldsResultErrorCode" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="formValuesForConditionalsAndCalculations" type="{http://dto19.api.echosign}ArrayOfFormValue" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderedCalculatedFieldNames" type="{http://api.echosign}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="orderedConditionalFieldNames" type="{http://api.echosign}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="signer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signerFormFields" type="{http://dto19.api.echosign}ArrayOfSignerFormField" minOccurs="0"/>
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
@XmlType(name = "GetSignerFormFieldsResult", propOrder = {
    "errorCode",
    "errorMessage",
    "formValuesForConditionalsAndCalculations",
    "locale",
    "orderedCalculatedFieldNames",
    "orderedConditionalFieldNames",
    "signer",
    "signerFormFields",
    "success"
})
public class GetSignerFormFieldsResult {

    @XmlElement(nillable = true)
    protected GetSignerFormFieldsResultErrorCode errorCode;
    @XmlElement(nillable = true)
    protected String errorMessage;
    @XmlElement(nillable = true)
    protected ArrayOfFormValue formValuesForConditionalsAndCalculations;
    @XmlElement(nillable = true)
    protected String locale;
    @XmlElement(nillable = true)
    protected ArrayOfString orderedCalculatedFieldNames;
    @XmlElement(nillable = true)
    protected ArrayOfString orderedConditionalFieldNames;
    @XmlElement(nillable = true)
    protected String signer;
    @XmlElement(nillable = true)
    protected ArrayOfSignerFormField signerFormFields;
    protected Boolean success;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link GetSignerFormFieldsResultErrorCode }
     *     
     */
    public GetSignerFormFieldsResultErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSignerFormFieldsResultErrorCode }
     *     
     */
    public void setErrorCode(GetSignerFormFieldsResultErrorCode value) {
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
     * Gets the value of the formValuesForConditionalsAndCalculations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFormValue }
     *     
     */
    public ArrayOfFormValue getFormValuesForConditionalsAndCalculations() {
        return formValuesForConditionalsAndCalculations;
    }

    /**
     * Sets the value of the formValuesForConditionalsAndCalculations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFormValue }
     *     
     */
    public void setFormValuesForConditionalsAndCalculations(ArrayOfFormValue value) {
        this.formValuesForConditionalsAndCalculations = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the orderedCalculatedFieldNames property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getOrderedCalculatedFieldNames() {
        return orderedCalculatedFieldNames;
    }

    /**
     * Sets the value of the orderedCalculatedFieldNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setOrderedCalculatedFieldNames(ArrayOfString value) {
        this.orderedCalculatedFieldNames = value;
    }

    /**
     * Gets the value of the orderedConditionalFieldNames property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getOrderedConditionalFieldNames() {
        return orderedConditionalFieldNames;
    }

    /**
     * Sets the value of the orderedConditionalFieldNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setOrderedConditionalFieldNames(ArrayOfString value) {
        this.orderedConditionalFieldNames = value;
    }

    /**
     * Gets the value of the signer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigner() {
        return signer;
    }

    /**
     * Sets the value of the signer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigner(String value) {
        this.signer = value;
    }

    /**
     * Gets the value of the signerFormFields property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignerFormField }
     *     
     */
    public ArrayOfSignerFormField getSignerFormFields() {
        return signerFormFields;
    }

    /**
     * Sets the value of the signerFormFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignerFormField }
     *     
     */
    public void setSignerFormFields(ArrayOfSignerFormField value) {
        this.signerFormFields = value;
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
