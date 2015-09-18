
package echosign.api.clientv20.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentPageImages complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentPageImages">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="largeImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediumImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smallImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentPageImages", propOrder = {
    "largeImageUrl",
    "mediumImageUrl",
    "smallImageUrl"
})
public class DocumentPageImages {

    @XmlElement(nillable = true)
    protected String largeImageUrl;
    @XmlElement(nillable = true)
    protected String mediumImageUrl;
    @XmlElement(nillable = true)
    protected String smallImageUrl;

    /**
     * Gets the value of the largeImageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    /**
     * Sets the value of the largeImageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLargeImageUrl(String value) {
        this.largeImageUrl = value;
    }

    /**
     * Gets the value of the mediumImageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    /**
     * Sets the value of the mediumImageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediumImageUrl(String value) {
        this.mediumImageUrl = value;
    }

    /**
     * Gets the value of the smallImageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    /**
     * Sets the value of the smallImageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmallImageUrl(String value) {
        this.smallImageUrl = value;
    }

}
