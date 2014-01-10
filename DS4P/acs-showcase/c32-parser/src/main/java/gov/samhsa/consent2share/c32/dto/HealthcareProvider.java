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
 *         &lt;element name="role" type="{urn:hl7-org:v3}cd"/>
 *         &lt;element name="dateRange" type="{urn:hl7-org:v3}ivlTs"/>
 *         &lt;element name="providerEntity" type="{urn:hl7-org:v3}providerInformation"/>
 *         &lt;element name="patientID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
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
    "role",
    "dateRange",
    "providerEntity",
    "patientID",
    "comment"
})
public class HealthcareProvider {

    /** The role. */
    @XmlElement(required = true)
    protected Cd role;
    
    /** The date range. */
    @XmlElement(required = true)
    protected IvlTs dateRange;
    
    /** The provider entity. */
    @XmlElement(required = true)
    protected ProviderInformation providerEntity;
    
    /** The patient id. */
    protected Ii patientID;
    
    /** The comment. */
    protected List<Comment> comment;

    /**
     * Gets the value of the role property.
     *
     * @return the role
     * possible object is
     * {@link Cd }
     */
    public Cd getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setRole(Cd value) {
        this.role = value;
    }

    /**
     * Gets the value of the dateRange property.
     *
     * @return the date range
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getDateRange() {
        return dateRange;
    }

    /**
     * Sets the value of the dateRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setDateRange(IvlTs value) {
        this.dateRange = value;
    }

    /**
     * Gets the value of the providerEntity property.
     *
     * @return the provider entity
     * possible object is
     * {@link ProviderInformation }
     */
    public ProviderInformation getProviderEntity() {
        return providerEntity;
    }

    /**
     * Sets the value of the providerEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderInformation }
     *     
     */
    public void setProviderEntity(ProviderInformation value) {
        this.providerEntity = value;
    }

    /**
     * Gets the value of the patientID property.
     *
     * @return the patient id
     * possible object is
     * {@link Ii }
     */
    public Ii getPatientID() {
        return patientID;
    }

    /**
     * Sets the value of the patientID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setPatientID(Ii value) {
        this.patientID = value;
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
