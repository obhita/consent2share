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
 *         &lt;element name="personID" type="{urn:hl7-org:v3}ii"/>
 *         &lt;element name="personAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded"/>
 *         &lt;element name="personPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded"/>
 *         &lt;element name="personInformation" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="personName" type="{urn:hl7-org:v3}pnm" maxOccurs="unbounded"/>
 *                   &lt;element name="gender" type="{urn:hl7-org:v3}cd"/>
 *                   &lt;element name="personDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *                   &lt;element name="maritalStatus" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="religiousAffiliation" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                   &lt;element name="race" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="ethnicity" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                   &lt;element name="birthPlace" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{urn:hl7-org:v3}addr">
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
    "personID",
    "personAddress",
    "personPhone",
    "personInformation"
})
public class PatientInformation {

    /** The person id. */
    @XmlElement(required = true)
    protected Ii personID;
    
    /** The person address. */
    @XmlElement(required = true)
    protected List<Addr> personAddress;
    
    /** The person phone. */
    @XmlElement(required = true)
    protected List<Tele> personPhone;
    
    /** The person information. */
    @XmlElement(required = true)
    protected List<PersonInformation> personInformation;

    /**
     * Gets the value of the personID property.
     *
     * @return the person id
     * possible object is
     * {@link Ii }
     */
    public Ii getPersonID() {
        return personID;
    }

    /**
     * Sets the value of the personID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setPersonID(Ii value) {
        this.personID = value;
    }

    /**
     * Gets the value of the personAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPersonAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the person address
     * {@link Addr }
     */
    public List<Addr> getPersonAddress() {
        if (personAddress == null) {
            personAddress = new ArrayList<Addr>();
        }
        return this.personAddress;
    }

    /**
     * Gets the value of the personPhone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personPhone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPersonPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the person phone
     * {@link Tele }
     */
    public List<Tele> getPersonPhone() {
        if (personPhone == null) {
            personPhone = new ArrayList<Tele>();
        }
        return this.personPhone;
    }

    /**
     * Gets the value of the personInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPersonInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the person information
     * {@link PersonInformation }
     */
    public List<PersonInformation> getPersonInformation() {
        if (personInformation == null) {
            personInformation = new ArrayList<PersonInformation>();
        }
        return this.personInformation;
    }

}
