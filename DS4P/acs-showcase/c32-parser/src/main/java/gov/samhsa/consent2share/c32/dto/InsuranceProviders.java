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
 *         &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *         &lt;element name="insuranceProvider" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="resultID" type="{urn:hl7-org:v3}ii"/>
 *                   &lt;element name="healthInsuranceType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                   &lt;element name="payer" type="{urn:hl7-org:v3}organizationInformation"/>
 *                   &lt;element name="memberInformation">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="healthPlanCoverageDates" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                             &lt;element name="memberID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                             &lt;element name="patientRelationshipToSubscriber" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="patientAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="patientPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="patientName" type="{urn:hl7-org:v3}pnm"/>
 *                             &lt;element name="patientDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="financialResponsibilityPartyType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                   &lt;element name="subscriberInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="subscriberID" type="{urn:hl7-org:v3}ii"/>
 *                             &lt;element name="subscriberAddress" type="{urn:hl7-org:v3}addr"/>
 *                             &lt;element name="subscriberPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="subscriberName" type="{urn:hl7-org:v3}pnm"/>
 *                             &lt;element name="subscriberDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="guarantorInformation" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="responsibilityEffectiveDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                             &lt;element name="partyAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="partyPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="partyName" type="{urn:hl7-org:v3}pnm" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="healthPlanName" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                   &lt;element ref="{urn:hl7-org:v3}comment" maxOccurs="unbounded" minOccurs="0"/>
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
    "text",
    "insuranceProvider"
})
public class InsuranceProviders {

    /** The text. */
    protected TextType text;
    
    /** The insurance provider. */
    @XmlElement(required = true)
    protected List<InsuranceProvider> insuranceProvider;

    /**
     * Gets the value of the text property.
     *
     * @return the text
     * possible object is
     * {@link TextType }
     */
    public TextType getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setText(TextType value) {
        this.text = value;
    }

    /**
     * Gets the value of the insuranceProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the insuranceProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getInsuranceProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the insurance provider
     * {@link InsuranceProvider }
     */
    public List<InsuranceProvider> getInsuranceProvider() {
        if (insuranceProvider == null) {
            insuranceProvider = new ArrayList<InsuranceProvider>();
        }
        return this.insuranceProvider;
    }

}
