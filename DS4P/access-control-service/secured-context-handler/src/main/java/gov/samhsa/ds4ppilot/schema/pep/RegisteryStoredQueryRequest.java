
package gov.samhsa.ds4ppilot.schema.pep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import gov.va.ehtac.ds4p.ws.EnforcePolicy;


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
 *         &lt;element name="enforcePolicy" type="{http://ws.ds4p.ehtac.va.gov/}enforcePolicy"/>
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
    "enforcePolicy"
})
@XmlRootElement(name = "RegisteryStoredQueryRequest")
public class RegisteryStoredQueryRequest {

    @XmlElement(required = true)
    protected String patientId;
    @XmlElement(required = true)
    protected EnforcePolicy enforcePolicy;

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
     * Gets the value of the enforcePolicy property.
     * 
     * @return
     *     possible object is
     *     {@link EnforcePolicy }
     *     
     */
    public EnforcePolicy getEnforcePolicy() {
        return enforcePolicy;
    }

    /**
     * Sets the value of the enforcePolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnforcePolicy }
     *     
     */
    public void setEnforcePolicy(EnforcePolicy value) {
        this.enforcePolicy = value;
    }

}
