
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto8.WidgetPersonalizationInfo;


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
 *         &lt;element name="widgetJavascript" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="personalizationInfo" type="{http://dto8.api.echosign}WidgetPersonalizationInfo"/>
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
    "widgetJavascript",
    "personalizationInfo"
})
@XmlRootElement(name = "personalizeEmbeddedWidget")
public class PersonalizeEmbeddedWidget {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected String widgetJavascript;
    @XmlElement(required = true, nillable = true)
    protected WidgetPersonalizationInfo personalizationInfo;

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
     * Gets the value of the widgetJavascript property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidgetJavascript() {
        return widgetJavascript;
    }

    /**
     * Sets the value of the widgetJavascript property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidgetJavascript(String value) {
        this.widgetJavascript = value;
    }

    /**
     * Gets the value of the personalizationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link WidgetPersonalizationInfo }
     *     
     */
    public WidgetPersonalizationInfo getPersonalizationInfo() {
        return personalizationInfo;
    }

    /**
     * Sets the value of the personalizationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link WidgetPersonalizationInfo }
     *     
     */
    public void setPersonalizationInfo(WidgetPersonalizationInfo value) {
        this.personalizationInfo = value;
    }

}
