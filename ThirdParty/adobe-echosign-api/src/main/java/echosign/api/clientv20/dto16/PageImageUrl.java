
package echosign.api.clientv20.dto16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageImageUrl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageImageUrl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scalingType" type="{http://dto16.api.echosign}ScalingType" minOccurs="0"/>
 *         &lt;element name="zoom" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageImageUrl", propOrder = {
    "scalingType",
    "zoom",
    "url",
    "width"
})
public class PageImageUrl {

    @XmlElement(nillable = true)
    protected ScalingType scalingType;
    @XmlElement(nillable = true)
    protected Integer zoom;
    @XmlElement(nillable = true)
    protected String url;
    @XmlElement(nillable = true)
    protected Integer width;

    /**
     * Gets the value of the scalingType property.
     * 
     * @return
     *     possible object is
     *     {@link ScalingType }
     *     
     */
    public ScalingType getScalingType() {
        return scalingType;
    }

    /**
     * Sets the value of the scalingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScalingType }
     *     
     */
    public void setScalingType(ScalingType value) {
        this.scalingType = value;
    }

    /**
     * Gets the value of the zoom property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getZoom() {
        return zoom;
    }

    /**
     * Sets the value of the zoom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setZoom(Integer value) {
        this.zoom = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth(Integer value) {
        this.width = value;
    }

}
