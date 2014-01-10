
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto13.ArrayOfParticipantInfo;
import echosign.api.clientv15.dto13.ArrayOfParticipantSecurityOption;


/**
 * <p>Java class for ParticipantInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParticipantInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="roles" type="{http://dto14.api.echosign}ArrayOfParticipantRole" minOccurs="0"/>
 *         &lt;element name="status" type="{http://dto14.api.echosign}UserAgreementStatus" minOccurs="0"/>
 *         &lt;element name="alternateParticipants" type="{http://dto13.api.echosign}ArrayOfParticipantInfo" minOccurs="0"/>
 *         &lt;element name="securityOptions" type="{http://dto13.api.echosign}ArrayOfParticipantSecurityOption" minOccurs="0"/>
 *         &lt;element name="company" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParticipantInfo", propOrder = {
    "roles",
    "status",
    "alternateParticipants",
    "securityOptions",
    "company",
    "email",
    "name",
    "title"
})
public class ParticipantInfo {

    @XmlElement(nillable = true)
    protected ArrayOfParticipantRole roles;
    @XmlElement(nillable = true)
    protected UserAgreementStatus status;
    @XmlElement(nillable = true)
    protected ArrayOfParticipantInfo alternateParticipants;
    @XmlElement(nillable = true)
    protected ArrayOfParticipantSecurityOption securityOptions;
    @XmlElement(nillable = true)
    protected String company;
    @XmlElement(nillable = true)
    protected String email;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String title;

    /**
     * Gets the value of the roles property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParticipantRole }
     *     
     */
    public ArrayOfParticipantRole getRoles() {
        return roles;
    }

    /**
     * Sets the value of the roles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParticipantRole }
     *     
     */
    public void setRoles(ArrayOfParticipantRole value) {
        this.roles = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link UserAgreementStatus }
     *     
     */
    public UserAgreementStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAgreementStatus }
     *     
     */
    public void setStatus(UserAgreementStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the alternateParticipants property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParticipantInfo }
     *     
     */
    public ArrayOfParticipantInfo getAlternateParticipants() {
        return alternateParticipants;
    }

    /**
     * Sets the value of the alternateParticipants property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParticipantInfo }
     *     
     */
    public void setAlternateParticipants(ArrayOfParticipantInfo value) {
        this.alternateParticipants = value;
    }

    /**
     * Gets the value of the securityOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParticipantSecurityOption }
     *     
     */
    public ArrayOfParticipantSecurityOption getSecurityOptions() {
        return securityOptions;
    }

    /**
     * Sets the value of the securityOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParticipantSecurityOption }
     *     
     */
    public void setSecurityOptions(ArrayOfParticipantSecurityOption value) {
        this.securityOptions = value;
    }

    /**
     * Gets the value of the company property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the value of the company property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompany(String value) {
        this.company = value;
    }

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
