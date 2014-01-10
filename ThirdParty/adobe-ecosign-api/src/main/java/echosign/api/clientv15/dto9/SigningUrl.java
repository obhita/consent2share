
package echosign.api.clientv15.dto9;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SigningUrl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SigningUrl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esignUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="simpleEsignUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SigningUrl", propOrder = {
    "email",
    "esignUrl",
    "simpleEsignUrl"
})
public class SigningUrl {

    @XmlElement(nillable = true)
    protected String email;
    @XmlElement(nillable = true)
    protected String esignUrl;
    @XmlElement(nillable = true)
    protected String simpleEsignUrl;

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the esignUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsignUrl() {
        return esignUrl;
    }

    /**
     * Sets the value of the esignUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsignUrl(String value) {
        this.esignUrl = value;
    }

    /**
     * Gets the value of the simpleEsignUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimpleEsignUrl() {
        return simpleEsignUrl;
    }

    /**
     * Sets the value of the simpleEsignUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimpleEsignUrl(String value) {
        this.simpleEsignUrl = value;
    }

}
