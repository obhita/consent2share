
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SecurityOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecurityOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kbaProtection" type="{http://dto.api.echosign}AppliesTo" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passwordProtection" type="{http://dto.api.echosign}AppliesTo" minOccurs="0"/>
 *         &lt;element name="protectOpen" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="webIdentityProtection" type="{http://dto.api.echosign}AppliesTo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityOptions", propOrder = {
    "kbaProtection",
    "password",
    "passwordProtection",
    "protectOpen",
    "webIdentityProtection"
})
public class SecurityOptions {

    @XmlElement(nillable = true)
    protected AppliesTo kbaProtection;
    @XmlElement(nillable = true)
    protected String password;
    @XmlElement(nillable = true)
    protected AppliesTo passwordProtection;
    protected Boolean protectOpen;
    @XmlElement(nillable = true)
    protected AppliesTo webIdentityProtection;

    /**
     * Gets the value of the kbaProtection property.
     * 
     * @return
     *     possible object is
     *     {@link AppliesTo }
     *     
     */
    public AppliesTo getKbaProtection() {
        return kbaProtection;
    }

    /**
     * Sets the value of the kbaProtection property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliesTo }
     *     
     */
    public void setKbaProtection(AppliesTo value) {
        this.kbaProtection = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the passwordProtection property.
     * 
     * @return
     *     possible object is
     *     {@link AppliesTo }
     *     
     */
    public AppliesTo getPasswordProtection() {
        return passwordProtection;
    }

    /**
     * Sets the value of the passwordProtection property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliesTo }
     *     
     */
    public void setPasswordProtection(AppliesTo value) {
        this.passwordProtection = value;
    }

    /**
     * Gets the value of the protectOpen property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProtectOpen() {
        return protectOpen;
    }

    /**
     * Sets the value of the protectOpen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProtectOpen(Boolean value) {
        this.protectOpen = value;
    }

    /**
     * Gets the value of the webIdentityProtection property.
     * 
     * @return
     *     possible object is
     *     {@link AppliesTo }
     *     
     */
    public AppliesTo getWebIdentityProtection() {
        return webIdentityProtection;
    }

    /**
     * Sets the value of the webIdentityProtection property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliesTo }
     *     
     */
    public void setWebIdentityProtection(AppliesTo value) {
        this.webIdentityProtection = value;
    }

}
