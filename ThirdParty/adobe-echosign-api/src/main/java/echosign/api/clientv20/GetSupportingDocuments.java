
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto14.GetSupportingDocumentsOptions;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apiKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="documentKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supportingDocumentKeys" type="{http://api.echosign}ArrayOfString"/>
 *         &lt;element name="options" type="{http://dto14.api.echosign}GetSupportingDocumentsOptions"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "apiKey",
    "documentKey",
    "supportingDocumentKeys",
    "options"
})
@XmlRootElement(name = "getSupportingDocuments")
public class GetSupportingDocuments {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected String documentKey;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfString supportingDocumentKeys;
    @XmlElement(required = true, nillable = true)
    protected GetSupportingDocumentsOptions options;

    /**
     * Gets the value of the apiKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the value of the apiKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiKey(String value) {
        this.apiKey = value;
    }

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
     * Gets the value of the supportingDocumentKeys property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getSupportingDocumentKeys() {
        return supportingDocumentKeys;
    }

    /**
     * Sets the value of the supportingDocumentKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setSupportingDocumentKeys(ArrayOfString value) {
        this.supportingDocumentKeys = value;
    }

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link GetSupportingDocumentsOptions }
     *     
     */
    public GetSupportingDocumentsOptions getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSupportingDocumentsOptions }
     *     
     */
    public void setOptions(GetSupportingDocumentsOptions value) {
        this.options = value;
    }

}
