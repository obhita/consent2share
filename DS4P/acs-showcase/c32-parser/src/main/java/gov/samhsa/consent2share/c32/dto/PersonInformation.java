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
 *         &lt;element name="personName" type="{urn:hl7-org:v3}pnm" maxOccurs="unbounded"/>
 *         &lt;element name="gender" type="{urn:hl7-org:v3}cd"/>
 *         &lt;element name="personDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *         &lt;element name="maritalStatus" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="religiousAffiliation" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="race" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ethnicity" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="birthPlace" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{urn:hl7-org:v3}addr">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/extension>
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
    "personName",
    "gender",
    "personDateOfBirth",
    "maritalStatus",
    "religiousAffiliation",
    "race",
    "ethnicity",
    "birthPlace"
})
public class PersonInformation {

    /** The person name. */
    @XmlElement(required = true)
    protected List<Pnm> personName;
    
    /** The gender. */
    @XmlElement(required = true)
    protected Cd gender;
    
    /** The person date of birth. */
    @XmlElement(required = true)
    protected Ts personDateOfBirth;
    
    /** The marital status. */
    protected List<Cd> maritalStatus;
    
    /** The religious affiliation. */
    protected Cd religiousAffiliation;
    
    /** The race. */
    protected List<Cd> race;
    
    /** The ethnicity. */
    protected Cd ethnicity;
    
    /** The birth place. */
    protected BirthPlace birthPlace;

    /**
     * Gets the value of the personName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPersonName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the person name
     * {@link Pnm }
     */
    public List<Pnm> getPersonName() {
        if (personName == null) {
            personName = new ArrayList<Pnm>();
        }
        return this.personName;
    }

    /**
     * Gets the value of the gender property.
     *
     * @return the gender
     * possible object is
     * {@link Cd }
     */
    public Cd getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setGender(Cd value) {
        this.gender = value;
    }

    /**
     * Gets the value of the personDateOfBirth property.
     *
     * @return the person date of birth
     * possible object is
     * {@link Ts }
     */
    public Ts getPersonDateOfBirth() {
        return personDateOfBirth;
    }

    /**
     * Sets the value of the personDateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ts }
     *     
     */
    public void setPersonDateOfBirth(Ts value) {
        this.personDateOfBirth = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maritalStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getMaritalStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the marital status
     * {@link Cd }
     */
    public List<Cd> getMaritalStatus() {
        if (maritalStatus == null) {
            maritalStatus = new ArrayList<Cd>();
        }
        return this.maritalStatus;
    }

    /**
     * Gets the value of the religiousAffiliation property.
     *
     * @return the religious affiliation
     * possible object is
     * {@link Cd }
     */
    public Cd getReligiousAffiliation() {
        return religiousAffiliation;
    }

    /**
     * Sets the value of the religiousAffiliation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setReligiousAffiliation(Cd value) {
        this.religiousAffiliation = value;
    }

    /**
     * Gets the value of the race property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the race property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getRace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the race
     * {@link Cd }
     */
    public List<Cd> getRace() {
        if (race == null) {
            race = new ArrayList<Cd>();
        }
        return this.race;
    }

    /**
     * Gets the value of the ethnicity property.
     *
     * @return the ethnicity
     * possible object is
     * {@link Cd }
     */
    public Cd getEthnicity() {
        return ethnicity;
    }

    /**
     * Sets the value of the ethnicity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setEthnicity(Cd value) {
        this.ethnicity = value;
    }

    /**
     * Gets the value of the birthPlace property.
     *
     * @return the birth place
     * possible object is
     * {@link BirthPlace }
     */
    public BirthPlace getBirthPlace() {
        return birthPlace;
    }

    /**
     * Sets the value of the birthPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link BirthPlace }
     *     
     */
    public void setBirthPlace(BirthPlace value) {
        this.birthPlace = value;
    }

}
