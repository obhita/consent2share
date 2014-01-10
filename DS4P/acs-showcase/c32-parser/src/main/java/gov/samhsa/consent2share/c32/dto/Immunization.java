/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.c32.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
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
 *         &lt;element name="administeredDate" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *         &lt;element name="medicationSeriesNumber" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *         &lt;element name="route" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="reaction" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="immunizationProvider" type="{urn:hl7-org:v3}providerInformation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="medicationInformation" type="{urn:hl7-org:v3}medicationInformation" maxOccurs="unbounded"/>
 *         &lt;element name="refusalReason" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="statusOfImmunization" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:hl7-org:v3}comment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="refusalInd" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "administeredDate",
    "medicationSeriesNumber",
    "route",
    "reaction",
    "immunizationProvider",
    "medicationInformation",
    "refusalReason",
    "statusOfImmunization",
    "comment"
})
public class Immunization {

    /** The administered date. */
    protected Ts administeredDate;
    
    /** The medication series number. */
    protected Pq medicationSeriesNumber;
    
    /** The route. */
    protected Cd route;
    
    /** The reaction. */
    protected List<Cd> reaction;
    
    /** The immunization provider. */
    protected List<ProviderInformation> immunizationProvider;
    
    /** The medication information. */
    @XmlElement(required = true)
    protected List<MedicationInformation> medicationInformation;
    
    /** The refusal reason. */
    protected Cd refusalReason;
    
    /** The status of immunization. */
    protected List<Cd> statusOfImmunization;
    
    /** The comment. */
    protected List<Comment> comment;
    
    /** The refusal ind. */
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String refusalInd;

    /**
     * Gets the value of the administeredDate property.
     *
     * @return the administered date
     * possible object is
     * {@link Ts }
     */
    public Ts getAdministeredDate() {
        return administeredDate;
    }

    /**
     * Sets the value of the administeredDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ts }
     *     
     */
    public void setAdministeredDate(Ts value) {
        this.administeredDate = value;
    }

    /**
     * Gets the value of the medicationSeriesNumber property.
     *
     * @return the medication series number
     * possible object is
     * {@link Pq }
     */
    public Pq getMedicationSeriesNumber() {
        return medicationSeriesNumber;
    }

    /**
     * Sets the value of the medicationSeriesNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pq }
     *     
     */
    public void setMedicationSeriesNumber(Pq value) {
        this.medicationSeriesNumber = value;
    }

    /**
     * Gets the value of the route property.
     *
     * @return the route
     * possible object is
     * {@link Cd }
     */
    public Cd getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setRoute(Cd value) {
        this.route = value;
    }

    /**
     * Gets the value of the reaction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reaction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getReaction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the reaction
     * {@link Cd }
     */
    public List<Cd> getReaction() {
        if (reaction == null) {
            reaction = new ArrayList<Cd>();
        }
        return this.reaction;
    }

    /**
     * Gets the value of the immunizationProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the immunizationProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getImmunizationProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the immunization provider
     * {@link ProviderInformation }
     */
    public List<ProviderInformation> getImmunizationProvider() {
        if (immunizationProvider == null) {
            immunizationProvider = new ArrayList<ProviderInformation>();
        }
        return this.immunizationProvider;
    }

    /**
     * Gets the value of the medicationInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the medicationInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getMedicationInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the medication information
     * {@link MedicationInformation }
     */
    public List<MedicationInformation> getMedicationInformation() {
        if (medicationInformation == null) {
            medicationInformation = new ArrayList<MedicationInformation>();
        }
        return this.medicationInformation;
    }

    /**
     * Gets the value of the refusalReason property.
     *
     * @return the refusal reason
     * possible object is
     * {@link Cd }
     */
    public Cd getRefusalReason() {
        return refusalReason;
    }

    /**
     * Sets the value of the refusalReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setRefusalReason(Cd value) {
        this.refusalReason = value;
    }

    /**
     * Gets the value of the statusOfImmunization property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statusOfImmunization property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getStatusOfImmunization().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the status of immunization
     * {@link Cd }
     */
    public List<Cd> getStatusOfImmunization() {
        if (statusOfImmunization == null) {
            statusOfImmunization = new ArrayList<Cd>();
        }
        return this.statusOfImmunization;
    }

    /**
     * Gets the value of the comment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getComment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the comment
     * {@link Comment }
     */
    public List<Comment> getComment() {
        if (comment == null) {
            comment = new ArrayList<Comment>();
        }
        return this.comment;
    }

    /**
     * Gets the value of the refusalInd property.
     *
     * @return the refusal ind
     * possible object is
     * {@link String }
     */
    public String getRefusalInd() {
        return refusalInd;
    }

    /**
     * Sets the value of the refusalInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefusalInd(String value) {
        this.refusalInd = value;
    }

}
