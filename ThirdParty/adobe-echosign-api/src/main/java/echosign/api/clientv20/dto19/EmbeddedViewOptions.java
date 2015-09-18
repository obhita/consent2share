
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmbeddedViewOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmbeddedViewOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="embeddedViewObject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="embeddedViewTarget" type="{http://dto19.api.echosign}EmbeddedViewTarget" minOccurs="0"/>
 *         &lt;element name="noChrome" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmbeddedViewOptions", propOrder = {
    "embeddedViewObject",
    "embeddedViewTarget",
    "noChrome"
})
public class EmbeddedViewOptions {

    @XmlElement(nillable = true)
    protected String embeddedViewObject;
    @XmlElement(nillable = true)
    protected EmbeddedViewTarget embeddedViewTarget;
    protected Boolean noChrome;

    /**
     * Gets the value of the embeddedViewObject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmbeddedViewObject() {
        return embeddedViewObject;
    }

    /**
     * Sets the value of the embeddedViewObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmbeddedViewObject(String value) {
        this.embeddedViewObject = value;
    }

    /**
     * Gets the value of the embeddedViewTarget property.
     * 
     * @return
     *     possible object is
     *     {@link EmbeddedViewTarget }
     *     
     */
    public EmbeddedViewTarget getEmbeddedViewTarget() {
        return embeddedViewTarget;
    }

    /**
     * Sets the value of the embeddedViewTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmbeddedViewTarget }
     *     
     */
    public void setEmbeddedViewTarget(EmbeddedViewTarget value) {
        this.embeddedViewTarget = value;
    }

    /**
     * Gets the value of the noChrome property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoChrome() {
        return noChrome;
    }

    /**
     * Sets the value of the noChrome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoChrome(Boolean value) {
        this.noChrome = value;
    }

}
