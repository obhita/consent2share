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
 *         &lt;element name="documentID" type="{urn:hl7-org:v3}ii"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="version" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="setID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="confidentiality" type="{urn:hl7-org:v3}cd"/>
 *         &lt;element name="documentTimestamp" type="{urn:hl7-org:v3}ts"/>
 *         &lt;element name="personalInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="patientInformation">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="personID" type="{urn:hl7-org:v3}ii"/>
 *                             &lt;element name="personAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded"/>
 *                             &lt;element name="personPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded"/>
 *                             &lt;element name="personInformation" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="personName" type="{urn:hl7-org:v3}pnm" maxOccurs="unbounded"/>
 *                                       &lt;element name="gender" type="{urn:hl7-org:v3}cd"/>
 *                                       &lt;element name="personDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *                                       &lt;element name="maritalStatus" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="religiousAffiliation" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                                       &lt;element name="race" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="ethnicity" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                                       &lt;element name="birthPlace" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;extension base="{urn:hl7-org:v3}addr">
 *                                               &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                                             &lt;/extension>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="languagesSpoken" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="languageSpoken" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="languageCode" type="{urn:hl7-org:v3}cs"/>
 *                             &lt;element name="modeCode" type="{urn:hl7-org:v3}ce" minOccurs="0"/>
 *                             &lt;element name="preferenceInd" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}nullFlavorType" />
 *                                     &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="supports" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="support" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="date" type="{urn:hl7-org:v3}ivlTs"/>
 *                             &lt;choice>
 *                               &lt;element name="guardian" type="{urn:hl7-org:v3}contactType"/>
 *                               &lt;element name="contact" type="{urn:hl7-org:v3}contactType" maxOccurs="unbounded"/>
 *                             &lt;/choice>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="custodian">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="custodianID" type="{urn:hl7-org:v3}ii" maxOccurs="unbounded"/>
 *                   &lt;element name="custodianName" type="{urn:hl7-org:v3}onm" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="healthcareProviders" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="careProvisionDateRange" type="{urn:hl7-org:v3}ivlTs"/>
 *                   &lt;element name="healthcareProvider" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="role" type="{urn:hl7-org:v3}cd"/>
 *                             &lt;element name="dateRange" type="{urn:hl7-org:v3}ivlTs"/>
 *                             &lt;element name="providerEntity" type="{urn:hl7-org:v3}providerInformation"/>
 *                             &lt;element name="patientID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                             &lt;element ref="{urn:hl7-org:v3}comment" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{urn:hl7-org:v3}informationSource"/>
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
    "documentID",
    "title",
    "version",
    "confidentiality",
    "documentTimestamp",
    "personalInformation",
    "languagesSpoken",
    "supports",
    "custodian",
    "healthcareProviders",
    "informationSource"
})
public class Header {

    /** The document id. */
    @XmlElement(required = true)
    protected Ii documentID;
    
    /** The title. */
    @XmlElement(required = true)
    protected String title;
    
    /** The version. */
    protected Version version;
    
    /** The confidentiality. */
    @XmlElement(required = true)
    protected Cd confidentiality;
    
    /** The document timestamp. */
    @XmlElement(required = true)
    protected Ts documentTimestamp;
    
    /** The personal information. */
    @XmlElement(required = true)
    protected PersonalInformation personalInformation;
    
    /** The languages spoken. */
    protected LanguagesSpoken languagesSpoken;
    
    /** The supports. */
    protected Supports supports;
    
    /** The custodian. */
    @XmlElement(required = true)
    protected Custodian custodian;
    
    /** The healthcare providers. */
    protected HealthcareProviders healthcareProviders;
    
    /** The information source. */
    @XmlElement(required = true)
    protected InformationSource informationSource;

    /**
     * Gets the value of the documentID property.
     *
     * @return the document id
     * possible object is
     * {@link Ii }
     */
    public Ii getDocumentID() {
        return documentID;
    }

    /**
     * Sets the value of the documentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setDocumentID(Ii value) {
        this.documentID = value;
    }

    /**
     * Gets the value of the title property.
     *
     * @return the title
     * possible object is
     * {@link String }
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

    /**
     * Gets the value of the version property.
     *
     * @return the version
     * possible object is
     * {@link Version }
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Version }
     *     
     */
    public void setVersion(Version value) {
        this.version = value;
    }

    /**
     * Gets the value of the confidentiality property.
     *
     * @return the confidentiality
     * possible object is
     * {@link Cd }
     */
    public Cd getConfidentiality() {
        return confidentiality;
    }

    /**
     * Sets the value of the confidentiality property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setConfidentiality(Cd value) {
        this.confidentiality = value;
    }

    /**
     * Gets the value of the documentTimestamp property.
     *
     * @return the document timestamp
     * possible object is
     * {@link Ts }
     */
    public Ts getDocumentTimestamp() {
        return documentTimestamp;
    }

    /**
     * Sets the value of the documentTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ts }
     *     
     */
    public void setDocumentTimestamp(Ts value) {
        this.documentTimestamp = value;
    }

    /**
     * Gets the value of the personalInformation property.
     *
     * @return the personal information
     * possible object is
     * {@link PersonalInformation }
     */
    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    /**
     * Sets the value of the personalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonalInformation }
     *     
     */
    public void setPersonalInformation(PersonalInformation value) {
        this.personalInformation = value;
    }

    /**
     * Gets the value of the languagesSpoken property.
     *
     * @return the languages spoken
     * possible object is
     * {@link LanguagesSpoken }
     */
    public LanguagesSpoken getLanguagesSpoken() {
        return languagesSpoken;
    }

    /**
     * Sets the value of the languagesSpoken property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguagesSpoken }
     *     
     */
    public void setLanguagesSpoken(LanguagesSpoken value) {
        this.languagesSpoken = value;
    }

    /**
     * Gets the value of the supports property.
     *
     * @return the supports
     * possible object is
     * {@link Supports }
     */
    public Supports getSupports() {
        return supports;
    }

    /**
     * Sets the value of the supports property.
     * 
     * @param value
     *     allowed object is
     *     {@link Supports }
     *     
     */
    public void setSupports(Supports value) {
        this.supports = value;
    }

    /**
     * Gets the value of the custodian property.
     *
     * @return the custodian
     * possible object is
     * {@link Custodian }
     */
    public Custodian getCustodian() {
        return custodian;
    }

    /**
     * Sets the value of the custodian property.
     * 
     * @param value
     *     allowed object is
     *     {@link Custodian }
     *     
     */
    public void setCustodian(Custodian value) {
        this.custodian = value;
    }

    /**
     * Gets the value of the healthcareProviders property.
     *
     * @return the healthcare providers
     * possible object is
     * {@link HealthcareProviders }
     */
    public HealthcareProviders getHealthcareProviders() {
        return healthcareProviders;
    }

    /**
     * Sets the value of the healthcareProviders property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthcareProviders }
     *     
     */
    public void setHealthcareProviders(HealthcareProviders value) {
        this.healthcareProviders = value;
    }

    /**
     * Gets the value of the informationSource property.
     *
     * @return the information source
     * possible object is
     * {@link InformationSource }
     */
    public InformationSource getInformationSource() {
        return informationSource;
    }

    /**
     * Sets the value of the informationSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformationSource }
     *     
     */
    public void setInformationSource(InformationSource value) {
        this.informationSource = value;
    }

}
