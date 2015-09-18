
package echosign.api.clientv20.dto16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FormCreationResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormCreationResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentKey" type="{http://dto16.api.echosign}DocumentKey" minOccurs="0"/>
 *         &lt;element name="widgetCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="widgetUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormCreationResult", propOrder = {
    "documentKey",
    "widgetCode",
    "widgetUrl"
})
public class FormCreationResult {

    @XmlElement(nillable = true)
    protected DocumentKey documentKey;
    @XmlElement(nillable = true)
    protected String widgetCode;
    @XmlElement(nillable = true)
    protected String widgetUrl;

    /**
     * Gets the value of the documentKey property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentKey }
     *     
     */
    public DocumentKey getDocumentKey() {
        return documentKey;
    }

    /**
     * Sets the value of the documentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentKey }
     *     
     */
    public void setDocumentKey(DocumentKey value) {
        this.documentKey = value;
    }

    /**
     * Gets the value of the widgetCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidgetCode() {
        return widgetCode;
    }

    /**
     * Sets the value of the widgetCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidgetCode(String value) {
        this.widgetCode = value;
    }

    /**
     * Gets the value of the widgetUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidgetUrl() {
        return widgetUrl;
    }

    /**
     * Sets the value of the widgetUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidgetUrl(String value) {
        this.widgetUrl = value;
    }

}
