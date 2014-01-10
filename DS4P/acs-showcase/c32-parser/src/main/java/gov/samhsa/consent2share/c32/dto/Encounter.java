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
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="encounterID" type="{urn:hl7-org:v3}ii" maxOccurs="unbounded"/>
 *         &lt;element name="encounterType" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{urn:hl7-org:v3}cd">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="encounterDateTime" type="{urn:hl7-org:v3}ivlTs"/>
 *         &lt;element name="dischargeDisposition" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="admissionType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="encounterProvider" type="{urn:hl7-org:v3}providerInformation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="facilityLocation" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="locationDuration" type="{urn:hl7-org:v3}ivlTs"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="reasonForVisit" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="reason" type="{urn:hl7-org:v3}cd"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "encounterID",
    "encounterType",
    "encounterDateTime",
    "dischargeDisposition",
    "admissionType",
    "encounterProvider",
    "facilityLocation",
    "reasonForVisit"
})
public class Encounter {

    /** The encounter id. */
    @XmlElement(required = true)
    protected List<Ii> encounterID;
    
    /** The encounter type. */
    protected EncounterType encounterType;
    
    /** The encounter date time. */
    @XmlElement(required = true)
    protected IvlTs encounterDateTime;
    
    /** The discharge disposition. */
    protected String dischargeDisposition;
    
    /** The admission type. */
    protected Cd admissionType;
    
    /** The encounter provider. */
    protected List<ProviderInformation> encounterProvider;
    
    /** The facility location. */
    protected FacilityLocation facilityLocation;
    
    /** The reason for visit. */
    protected ReasonForVisit reasonForVisit;

    /**
     * Gets the value of the encounterID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the encounterID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getEncounterID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the encounter id
     * {@link Ii }
     */
    public List<Ii> getEncounterID() {
        if (encounterID == null) {
            encounterID = new ArrayList<Ii>();
        }
        return this.encounterID;
    }

    /**
     * Gets the value of the encounterType property.
     *
     * @return the encounter type
     * possible object is
     * {@link EncounterType }
     */
    public EncounterType getEncounterType() {
        return encounterType;
    }

    /**
     * Sets the value of the encounterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncounterType }
     *     
     */
    public void setEncounterType(EncounterType value) {
        this.encounterType = value;
    }

    /**
     * Gets the value of the encounterDateTime property.
     *
     * @return the encounter date time
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getEncounterDateTime() {
        return encounterDateTime;
    }

    /**
     * Sets the value of the encounterDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setEncounterDateTime(IvlTs value) {
        this.encounterDateTime = value;
    }

    /**
     * Gets the value of the dischargeDisposition property.
     *
     * @return the discharge disposition
     * possible object is
     * {@link String }
     */
    public String getDischargeDisposition() {
        return dischargeDisposition;
    }

    /**
     * Sets the value of the dischargeDisposition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDischargeDisposition(String value) {
        this.dischargeDisposition = value;
    }

    /**
     * Gets the value of the admissionType property.
     *
     * @return the admission type
     * possible object is
     * {@link Cd }
     */
    public Cd getAdmissionType() {
        return admissionType;
    }

    /**
     * Sets the value of the admissionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setAdmissionType(Cd value) {
        this.admissionType = value;
    }

    /**
     * Gets the value of the encounterProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the encounterProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getEncounterProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the encounter provider
     * {@link ProviderInformation }
     */
    public List<ProviderInformation> getEncounterProvider() {
        if (encounterProvider == null) {
            encounterProvider = new ArrayList<ProviderInformation>();
        }
        return this.encounterProvider;
    }

    /**
     * Gets the value of the facilityLocation property.
     *
     * @return the facility location
     * possible object is
     * {@link FacilityLocation }
     */
    public FacilityLocation getFacilityLocation() {
        return facilityLocation;
    }

    /**
     * Sets the value of the facilityLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityLocation }
     *     
     */
    public void setFacilityLocation(FacilityLocation value) {
        this.facilityLocation = value;
    }

    /**
     * Gets the value of the reasonForVisit property.
     *
     * @return the reason for visit
     * possible object is
     * {@link ReasonForVisit }
     */
    public ReasonForVisit getReasonForVisit() {
        return reasonForVisit;
    }

    /**
     * Sets the value of the reasonForVisit property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonForVisit }
     *     
     */
    public void setReasonForVisit(ReasonForVisit value) {
        this.reasonForVisit = value;
    }

}
