
package echosign.api.clientv20.dto14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto18.ArrayOfRecipientSecurityOption;


/**
 * <p>Java class for RecipientInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecipientInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="role" type="{http://dto14.api.echosign}RecipientRole" minOccurs="0"/>
 *         &lt;element name="securityOptions" type="{http://dto18.api.echosign}ArrayOfRecipientSecurityOption" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecipientInfo", propOrder = {
    "email",
    "fax",
    "role",
    "securityOptions"
})
public class RecipientInfo {

    @XmlElement(nillable = true)
    protected String email;
    @XmlElement(nillable = true)
    protected String fax;
    @XmlElement(nillable = true)
    protected RecipientRole role;
    @XmlElement(nillable = true)
    protected ArrayOfRecipientSecurityOption securityOptions;

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
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientRole }
     *     
     */
    public RecipientRole getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientRole }
     *     
     */
    public void setRole(RecipientRole value) {
        this.role = value;
    }

    /**
     * Gets the value of the securityOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRecipientSecurityOption }
     *     
     */
    public ArrayOfRecipientSecurityOption getSecurityOptions() {
        return securityOptions;
    }

    /**
     * Sets the value of the securityOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRecipientSecurityOption }
     *     
     */
    public void setSecurityOptions(ArrayOfRecipientSecurityOption value) {
        this.securityOptions = value;
    }

}
