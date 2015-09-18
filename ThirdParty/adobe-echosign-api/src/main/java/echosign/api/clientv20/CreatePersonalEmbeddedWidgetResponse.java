
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto16.EmbeddedWidgetCreationResult;


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
 *         &lt;element name="embeddedWidgetCreationResult" type="{http://dto16.api.echosign}EmbeddedWidgetCreationResult"/>
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
    "embeddedWidgetCreationResult"
})
@XmlRootElement(name = "createPersonalEmbeddedWidgetResponse")
public class CreatePersonalEmbeddedWidgetResponse {

    @XmlElement(required = true, nillable = true)
    protected EmbeddedWidgetCreationResult embeddedWidgetCreationResult;

    /**
     * Gets the value of the embeddedWidgetCreationResult property.
     * 
     * @return
     *     possible object is
     *     {@link EmbeddedWidgetCreationResult }
     *     
     */
    public EmbeddedWidgetCreationResult getEmbeddedWidgetCreationResult() {
        return embeddedWidgetCreationResult;
    }

    /**
     * Sets the value of the embeddedWidgetCreationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmbeddedWidgetCreationResult }
     *     
     */
    public void setEmbeddedWidgetCreationResult(EmbeddedWidgetCreationResult value) {
        this.embeddedWidgetCreationResult = value;
    }

}
