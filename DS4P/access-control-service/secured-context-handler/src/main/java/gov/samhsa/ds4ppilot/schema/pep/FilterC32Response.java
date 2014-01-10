
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
 *         &lt;element name="pdpDecision" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filteredStreamBody" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="maskedDocument" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "pdpDecision",
    "filteredStreamBody",
    "maskedDocument"
})
@XmlRootElement(name = "FilterC32Response")
public class FilterC32Response {

    @XmlElement(required = true)
    protected String patientId;
    @XmlElement(required = true)
    protected String pdpDecision;
    @XmlElement(required = true)
    protected byte[] filteredStreamBody;
    @XmlElement(required = true)
    protected String maskedDocument;

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
     * Gets the value of the pdpDecision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPdpDecision() {
        return pdpDecision;
    }

    /**
     * Sets the value of the pdpDecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPdpDecision(String value) {
        this.pdpDecision = value;
    }

    /**
     * Gets the value of the filteredStreamBody property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFilteredStreamBody() {
        return filteredStreamBody;
    }

    /**
     * Sets the value of the filteredStreamBody property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFilteredStreamBody(byte[] value) {
        this.filteredStreamBody = ((byte[]) value);
    }

    /**
     * Gets the value of the maskedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaskedDocument() {
        return maskedDocument;
    }

    /**
     * Sets the value of the maskedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaskedDocument(String value) {
        this.maskedDocument = value;
    }

}
