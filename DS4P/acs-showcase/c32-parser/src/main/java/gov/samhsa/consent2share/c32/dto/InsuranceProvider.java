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
 *         &lt;element name="resultID" type="{urn:hl7-org:v3}ii"/>
 *         &lt;element name="healthInsuranceType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="payer" type="{urn:hl7-org:v3}organizationInformation"/>
 *         &lt;element name="memberInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="healthPlanCoverageDates" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                   &lt;element name="memberID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                   &lt;element name="patientRelationshipToSubscriber" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                   &lt;element name="patientAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="patientPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="patientName" type="{urn:hl7-org:v3}pnm"/>
 *                   &lt;element name="patientDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="financialResponsibilityPartyType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="subscriberInformation" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="subscriberID" type="{urn:hl7-org:v3}ii"/>
 *                   &lt;element name="subscriberAddress" type="{urn:hl7-org:v3}addr"/>
 *                   &lt;element name="subscriberPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="subscriberName" type="{urn:hl7-org:v3}pnm"/>
 *                   &lt;element name="subscriberDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="guarantorInformation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="responsibilityEffectiveDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                   &lt;element name="partyAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="partyPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="partyName" type="{urn:hl7-org:v3}pnm" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="healthPlanName" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element ref="{urn:hl7-org:v3}comment" maxOccurs="unbounded" minOccurs="0"/>
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
    "resultID",
    "healthInsuranceType",
    "payer",
    "memberInformation",
    "financialResponsibilityPartyType",
    "subscriberInformation",
    "guarantorInformation",
    "healthPlanName",
    "comment"
})
public class InsuranceProvider {

    /** The result id. */
    @XmlElement(required = true)
    protected Ii resultID;
    
    /** The health insurance type. */
    protected Cd healthInsuranceType;
    
    /** The payer. */
    @XmlElement(required = true)
    protected OrganizationInformation payer;
    
    /** The member information. */
    @XmlElement(required = true)
    protected MemberInformation memberInformation;
    
    /** The financial responsibility party type. */
    protected Cd financialResponsibilityPartyType;
    
    /** The subscriber information. */
    protected SubscriberInformation subscriberInformation;
    
    /** The guarantor information. */
    protected List<GuarantorInformation> guarantorInformation;
    
    /** The health plan name. */
    protected String healthPlanName;
    
    /** The comment. */
    protected List<Comment> comment;

    /**
     * Gets the value of the resultID property.
     *
     * @return the result id
     * possible object is
     * {@link Ii }
     */
    public Ii getResultID() {
        return resultID;
    }

    /**
     * Sets the value of the resultID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setResultID(Ii value) {
        this.resultID = value;
    }

    /**
     * Gets the value of the healthInsuranceType property.
     *
     * @return the health insurance type
     * possible object is
     * {@link Cd }
     */
    public Cd getHealthInsuranceType() {
        return healthInsuranceType;
    }

    /**
     * Sets the value of the healthInsuranceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setHealthInsuranceType(Cd value) {
        this.healthInsuranceType = value;
    }

    /**
     * Gets the value of the payer property.
     *
     * @return the payer
     * possible object is
     * {@link OrganizationInformation }
     */
    public OrganizationInformation getPayer() {
        return payer;
    }

    /**
     * Sets the value of the payer property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationInformation }
     *     
     */
    public void setPayer(OrganizationInformation value) {
        this.payer = value;
    }

    /**
     * Gets the value of the memberInformation property.
     *
     * @return the member information
     * possible object is
     * {@link MemberInformation }
     */
    public MemberInformation getMemberInformation() {
        return memberInformation;
    }

    /**
     * Sets the value of the memberInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberInformation }
     *     
     */
    public void setMemberInformation(MemberInformation value) {
        this.memberInformation = value;
    }

    /**
     * Gets the value of the financialResponsibilityPartyType property.
     *
     * @return the financial responsibility party type
     * possible object is
     * {@link Cd }
     */
    public Cd getFinancialResponsibilityPartyType() {
        return financialResponsibilityPartyType;
    }

    /**
     * Sets the value of the financialResponsibilityPartyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setFinancialResponsibilityPartyType(Cd value) {
        this.financialResponsibilityPartyType = value;
    }

    /**
     * Gets the value of the subscriberInformation property.
     *
     * @return the subscriber information
     * possible object is
     * {@link SubscriberInformation }
     */
    public SubscriberInformation getSubscriberInformation() {
        return subscriberInformation;
    }

    /**
     * Sets the value of the subscriberInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriberInformation }
     *     
     */
    public void setSubscriberInformation(SubscriberInformation value) {
        this.subscriberInformation = value;
    }

    /**
     * Gets the value of the guarantorInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guarantorInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getGuarantorInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the guarantor information
     * {@link GuarantorInformation }
     */
    public List<GuarantorInformation> getGuarantorInformation() {
        if (guarantorInformation == null) {
            guarantorInformation = new ArrayList<GuarantorInformation>();
        }
        return this.guarantorInformation;
    }

    /**
     * Gets the value of the healthPlanName property.
     *
     * @return the health plan name
     * possible object is
     * {@link String }
     */
    public String getHealthPlanName() {
        return healthPlanName;
    }

    /**
     * Sets the value of the healthPlanName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealthPlanName(String value) {
        this.healthPlanName = value;
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

}
