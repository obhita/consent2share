
package gov.samhsa.ds4ppilot.schema.pep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="packageAsXdm" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="senderEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="recipientEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "patientId",
    "packageAsXdm",
    "senderEmailAddress",
    "recipientEmailAddress"
})
@XmlRootElement(name = "FilterC32Request")
public class FilterC32Request {

    @XmlElement(required = true)
    protected String patientId;
    protected boolean packageAsXdm;
    @XmlElement(required = true)
    protected String senderEmailAddress;
    @XmlElement(required = true)
    protected String recipientEmailAddress;

    /**
     * Gets the value of the patientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the value of the patientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientId(String value) {
        this.patientId = value;
    }

    /**
     * Gets the value of the packageAsXdm property.
     * 
     */
    public boolean isPackageAsXdm() {
        return packageAsXdm;
    }

    /**
     * Sets the value of the packageAsXdm property.
     * 
     */
    public void setPackageAsXdm(boolean value) {
        this.packageAsXdm = value;
    }

    /**
     * Gets the value of the senderEmailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderEmailAddress() {
        return senderEmailAddress;
    }

    /**
     * Sets the value of the senderEmailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderEmailAddress(String value) {
        this.senderEmailAddress = value;
    }

    /**
     * Gets the value of the recipientEmailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipientEmailAddress() {
        return recipientEmailAddress;
    }

    /**
     * Sets the value of the recipientEmailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipientEmailAddress(String value) {
        this.recipientEmailAddress = value;
    }

}
