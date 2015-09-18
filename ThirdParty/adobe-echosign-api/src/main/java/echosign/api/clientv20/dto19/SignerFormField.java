
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.ArrayOfString;


/**
 * <p>Java class for SignerFormField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SignerFormField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alignment" type="{http://dto19.api.echosign}TextAlignment" minOccurs="0"/>
 *         &lt;element name="anyOrAll" type="{http://dto19.api.echosign}AnyAll" minOccurs="0"/>
 *         &lt;element name="backgroundColor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="borderColor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="borderStyle" type="{http://dto19.api.echosign}BorderStyle" minOccurs="0"/>
 *         &lt;element name="borderWidth" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="calculatedExpression" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conditions" type="{http://dto19.api.echosign}ArrayOfSignerFormFieldCondition" minOccurs="0"/>
 *         &lt;element name="contentType" type="{http://dto19.api.echosign}ContentType" minOccurs="0"/>
 *         &lt;element name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayFormatType" type="{http://dto19.api.echosign}DisplayFormatType" minOccurs="0"/>
 *         &lt;element name="displayLabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fontColor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fontName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fontSize" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="hidden" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hiddenOptions" type="{http://api.echosign}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="inputType" type="{http://dto19.api.echosign}InputType" minOccurs="0"/>
 *         &lt;element name="locations" type="{http://dto19.api.echosign}ArrayOfSignerFormFieldLocation" minOccurs="0"/>
 *         &lt;element name="masked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="maxLength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxNumberValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="minLength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="minNumberValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="radioCheckType" type="{http://dto19.api.echosign}RadioCheckType" minOccurs="0"/>
 *         &lt;element name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="regularExpression" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="showOrHide" type="{http://dto19.api.echosign}ShowHide" minOccurs="0"/>
 *         &lt;element name="specialErrMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialFormula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tooltip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validationRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="visibleOptions" type="{http://api.echosign}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignerFormField", propOrder = {
    "alignment",
    "anyOrAll",
    "backgroundColor",
    "borderColor",
    "borderStyle",
    "borderWidth",
    "calculatedExpression",
    "conditions",
    "contentType",
    "defaultValue",
    "displayFormat",
    "displayFormatType",
    "displayLabel",
    "fontColor",
    "fontName",
    "fontSize",
    "hidden",
    "hiddenOptions",
    "inputType",
    "locations",
    "masked",
    "maxLength",
    "maxNumberValue",
    "minLength",
    "minNumberValue",
    "name",
    "radioCheckType",
    "readOnly",
    "regularExpression",
    "required",
    "showOrHide",
    "specialErrMsg",
    "specialFormula",
    "tooltip",
    "validationRule",
    "visibleOptions"
})
public class SignerFormField {

    @XmlElement(nillable = true)
    protected TextAlignment alignment;
    @XmlElement(nillable = true)
    protected AnyAll anyOrAll;
    @XmlElement(nillable = true)
    protected String backgroundColor;
    @XmlElement(nillable = true)
    protected String borderColor;
    @XmlElement(nillable = true)
    protected BorderStyle borderStyle;
    protected Float borderWidth;
    @XmlElement(nillable = true)
    protected String calculatedExpression;
    @XmlElement(nillable = true)
    protected ArrayOfSignerFormFieldCondition conditions;
    @XmlElement(nillable = true)
    protected ContentType contentType;
    @XmlElement(nillable = true)
    protected String defaultValue;
    @XmlElement(nillable = true)
    protected String displayFormat;
    @XmlElement(nillable = true)
    protected DisplayFormatType displayFormatType;
    @XmlElement(nillable = true)
    protected String displayLabel;
    @XmlElement(nillable = true)
    protected String fontColor;
    @XmlElement(nillable = true)
    protected String fontName;
    protected Float fontSize;
    protected Boolean hidden;
    @XmlElement(nillable = true)
    protected ArrayOfString hiddenOptions;
    @XmlElement(nillable = true)
    protected InputType inputType;
    @XmlElement(nillable = true)
    protected ArrayOfSignerFormFieldLocation locations;
    protected Boolean masked;
    protected Integer maxLength;
    protected Double maxNumberValue;
    protected Integer minLength;
    protected Double minNumberValue;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected RadioCheckType radioCheckType;
    protected Boolean readOnly;
    @XmlElement(nillable = true)
    protected String regularExpression;
    protected Boolean required;
    @XmlElement(nillable = true)
    protected ShowHide showOrHide;
    @XmlElement(nillable = true)
    protected String specialErrMsg;
    @XmlElement(nillable = true)
    protected String specialFormula;
    @XmlElement(nillable = true)
    protected String tooltip;
    @XmlElement(nillable = true)
    protected String validationRule;
    @XmlElement(nillable = true)
    protected ArrayOfString visibleOptions;

    /**
     * Gets the value of the alignment property.
     * 
     * @return
     *     possible object is
     *     {@link TextAlignment }
     *     
     */
    public TextAlignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the value of the alignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextAlignment }
     *     
     */
    public void setAlignment(TextAlignment value) {
        this.alignment = value;
    }

    /**
     * Gets the value of the anyOrAll property.
     * 
     * @return
     *     possible object is
     *     {@link AnyAll }
     *     
     */
    public AnyAll getAnyOrAll() {
        return anyOrAll;
    }

    /**
     * Sets the value of the anyOrAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnyAll }
     *     
     */
    public void setAnyOrAll(AnyAll value) {
        this.anyOrAll = value;
    }

    /**
     * Gets the value of the backgroundColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the value of the backgroundColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackgroundColor(String value) {
        this.backgroundColor = value;
    }

    /**
     * Gets the value of the borderColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the value of the borderColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorderColor(String value) {
        this.borderColor = value;
    }

    /**
     * Gets the value of the borderStyle property.
     * 
     * @return
     *     possible object is
     *     {@link BorderStyle }
     *     
     */
    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

    /**
     * Sets the value of the borderStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BorderStyle }
     *     
     */
    public void setBorderStyle(BorderStyle value) {
        this.borderStyle = value;
    }

    /**
     * Gets the value of the borderWidth property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getBorderWidth() {
        return borderWidth;
    }

    /**
     * Sets the value of the borderWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setBorderWidth(Float value) {
        this.borderWidth = value;
    }

    /**
     * Gets the value of the calculatedExpression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalculatedExpression() {
        return calculatedExpression;
    }

    /**
     * Sets the value of the calculatedExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalculatedExpression(String value) {
        this.calculatedExpression = value;
    }

    /**
     * Gets the value of the conditions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignerFormFieldCondition }
     *     
     */
    public ArrayOfSignerFormFieldCondition getConditions() {
        return conditions;
    }

    /**
     * Sets the value of the conditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignerFormFieldCondition }
     *     
     */
    public void setConditions(ArrayOfSignerFormFieldCondition value) {
        this.conditions = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link ContentType }
     *     
     */
    public ContentType getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentType }
     *     
     */
    public void setContentType(ContentType value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the displayFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayFormat() {
        return displayFormat;
    }

    /**
     * Sets the value of the displayFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayFormat(String value) {
        this.displayFormat = value;
    }

    /**
     * Gets the value of the displayFormatType property.
     * 
     * @return
     *     possible object is
     *     {@link DisplayFormatType }
     *     
     */
    public DisplayFormatType getDisplayFormatType() {
        return displayFormatType;
    }

    /**
     * Sets the value of the displayFormatType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DisplayFormatType }
     *     
     */
    public void setDisplayFormatType(DisplayFormatType value) {
        this.displayFormatType = value;
    }

    /**
     * Gets the value of the displayLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayLabel() {
        return displayLabel;
    }

    /**
     * Sets the value of the displayLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayLabel(String value) {
        this.displayLabel = value;
    }

    /**
     * Gets the value of the fontColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontColor() {
        return fontColor;
    }

    /**
     * Sets the value of the fontColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontColor(String value) {
        this.fontColor = value;
    }

    /**
     * Gets the value of the fontName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * Sets the value of the fontName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontName(String value) {
        this.fontName = value;
    }

    /**
     * Gets the value of the fontSize property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFontSize() {
        return fontSize;
    }

    /**
     * Sets the value of the fontSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFontSize(Float value) {
        this.fontSize = value;
    }

    /**
     * Gets the value of the hidden property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the value of the hidden property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHidden(Boolean value) {
        this.hidden = value;
    }

    /**
     * Gets the value of the hiddenOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getHiddenOptions() {
        return hiddenOptions;
    }

    /**
     * Sets the value of the hiddenOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setHiddenOptions(ArrayOfString value) {
        this.hiddenOptions = value;
    }

    /**
     * Gets the value of the inputType property.
     * 
     * @return
     *     possible object is
     *     {@link InputType }
     *     
     */
    public InputType getInputType() {
        return inputType;
    }

    /**
     * Sets the value of the inputType property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputType }
     *     
     */
    public void setInputType(InputType value) {
        this.inputType = value;
    }

    /**
     * Gets the value of the locations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignerFormFieldLocation }
     *     
     */
    public ArrayOfSignerFormFieldLocation getLocations() {
        return locations;
    }

    /**
     * Sets the value of the locations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignerFormFieldLocation }
     *     
     */
    public void setLocations(ArrayOfSignerFormFieldLocation value) {
        this.locations = value;
    }

    /**
     * Gets the value of the masked property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMasked() {
        return masked;
    }

    /**
     * Sets the value of the masked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMasked(Boolean value) {
        this.masked = value;
    }

    /**
     * Gets the value of the maxLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxLength(Integer value) {
        this.maxLength = value;
    }

    /**
     * Gets the value of the maxNumberValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaxNumberValue() {
        return maxNumberValue;
    }

    /**
     * Sets the value of the maxNumberValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaxNumberValue(Double value) {
        this.maxNumberValue = value;
    }

    /**
     * Gets the value of the minLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinLength() {
        return minLength;
    }

    /**
     * Sets the value of the minLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinLength(Integer value) {
        this.minLength = value;
    }

    /**
     * Gets the value of the minNumberValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMinNumberValue() {
        return minNumberValue;
    }

    /**
     * Sets the value of the minNumberValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMinNumberValue(Double value) {
        this.minNumberValue = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the radioCheckType property.
     * 
     * @return
     *     possible object is
     *     {@link RadioCheckType }
     *     
     */
    public RadioCheckType getRadioCheckType() {
        return radioCheckType;
    }

    /**
     * Sets the value of the radioCheckType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RadioCheckType }
     *     
     */
    public void setRadioCheckType(RadioCheckType value) {
        this.radioCheckType = value;
    }

    /**
     * Gets the value of the readOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Sets the value of the readOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReadOnly(Boolean value) {
        this.readOnly = value;
    }

    /**
     * Gets the value of the regularExpression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegularExpression() {
        return regularExpression;
    }

    /**
     * Sets the value of the regularExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegularExpression(String value) {
        this.regularExpression = value;
    }

    /**
     * Gets the value of the required property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequired(Boolean value) {
        this.required = value;
    }

    /**
     * Gets the value of the showOrHide property.
     * 
     * @return
     *     possible object is
     *     {@link ShowHide }
     *     
     */
    public ShowHide getShowOrHide() {
        return showOrHide;
    }

    /**
     * Sets the value of the showOrHide property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowHide }
     *     
     */
    public void setShowOrHide(ShowHide value) {
        this.showOrHide = value;
    }

    /**
     * Gets the value of the specialErrMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialErrMsg() {
        return specialErrMsg;
    }

    /**
     * Sets the value of the specialErrMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialErrMsg(String value) {
        this.specialErrMsg = value;
    }

    /**
     * Gets the value of the specialFormula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialFormula() {
        return specialFormula;
    }

    /**
     * Sets the value of the specialFormula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialFormula(String value) {
        this.specialFormula = value;
    }

    /**
     * Gets the value of the tooltip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Sets the value of the tooltip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTooltip(String value) {
        this.tooltip = value;
    }

    /**
     * Gets the value of the validationRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationRule() {
        return validationRule;
    }

    /**
     * Sets the value of the validationRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationRule(String value) {
        this.validationRule = value;
    }

    /**
     * Gets the value of the visibleOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getVisibleOptions() {
        return visibleOptions;
    }

    /**
     * Sets the value of the visibleOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setVisibleOptions(ArrayOfString value) {
        this.visibleOptions = value;
    }

}
