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
 *         &lt;element name="healthPlanCoverageDates" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *         &lt;element name="memberID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *         &lt;element name="patientRelationshipToSubscriber" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="patientAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="patientPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="patientName" type="{urn:hl7-org:v3}pnm"/>
 *         &lt;element name="patientDateOfBirth" type="{urn:hl7-org:v3}ts"/>
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
    "healthPlanCoverageDates",
    "memberID",
    "patientRelationshipToSubscriber",
    "patientAddress",
    "patientPhone",
    "patientName",
    "patientDateOfBirth"
})
public class MemberInformation {

    /** The health plan coverage dates. */
    protected IvlTs healthPlanCoverageDates;
    
    /** The member id. */
    protected Ii memberID;
    
    /** The patient relationship to subscriber. */
    protected Cd patientRelationshipToSubscriber;
    
    /** The patient address. */
    protected List<Addr> patientAddress;
    
    /** The patient phone. */
    protected List<Tele> patientPhone;
    
    /** The patient name. */
    @XmlElement(required = true)
    protected Pnm patientName;
    
    /** The patient date of birth. */
    @XmlElement(required = true)
    protected Ts patientDateOfBirth;

    /**
     * Gets the value of the healthPlanCoverageDates property.
     *
     * @return the health plan coverage dates
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getHealthPlanCoverageDates() {
        return healthPlanCoverageDates;
    }

    /**
     * Sets the value of the healthPlanCoverageDates property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setHealthPlanCoverageDates(IvlTs value) {
        this.healthPlanCoverageDates = value;
    }

    /**
     * Gets the value of the memberID property.
     *
     * @return the member id
     * possible object is
     * {@link Ii }
     */
    public Ii getMemberID() {
        return memberID;
    }

    /**
     * Sets the value of the memberID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setMemberID(Ii value) {
        this.memberID = value;
    }

    /**
     * Gets the value of the patientRelationshipToSubscriber property.
     *
     * @return the patient relationship to subscriber
     * possible object is
     * {@link Cd }
     */
    public Cd getPatientRelationshipToSubscriber() {
        return patientRelationshipToSubscriber;
    }

    /**
     * Sets the value of the patientRelationshipToSubscriber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setPatientRelationshipToSubscriber(Cd value) {
        this.patientRelationshipToSubscriber = value;
    }

    /**
     * Gets the value of the patientAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the patientAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPatientAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the patient address
     * {@link Addr }
     */
    public List<Addr> getPatientAddress() {
        if (patientAddress == null) {
            patientAddress = new ArrayList<Addr>();
        }
        return this.patientAddress;
    }

    /**
     * Gets the value of the patientPhone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the patientPhone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPatientPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the patient phone
     * {@link Tele }
     */
    public List<Tele> getPatientPhone() {
        if (patientPhone == null) {
            patientPhone = new ArrayList<Tele>();
        }
        return this.patientPhone;
    }

    /**
     * Gets the value of the patientName property.
     *
     * @return the patient name
     * possible object is
     * {@link Pnm }
     */
    public Pnm getPatientName() {
        return patientName;
    }

    /**
     * Sets the value of the patientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pnm }
     *     
     */
    public void setPatientName(Pnm value) {
        this.patientName = value;
    }

    /**
     * Gets the value of the patientDateOfBirth property.
     *
     * @return the patient date of birth
     * possible object is
     * {@link Ts }
     */
    public Ts getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    /**
     * Sets the value of the patientDateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ts }
     *     
     */
    public void setPatientDateOfBirth(Ts value) {
        this.patientDateOfBirth = value;
    }

}
