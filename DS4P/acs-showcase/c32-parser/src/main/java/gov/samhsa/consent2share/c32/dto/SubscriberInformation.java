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
 *         &lt;element name="subscriberID" type="{urn:hl7-org:v3}ii"/>
 *         &lt;element name="subscriberAddress" type="{urn:hl7-org:v3}addr"/>
 *         &lt;element name="subscriberPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subscriberName" type="{urn:hl7-org:v3}pnm"/>
 *         &lt;element name="subscriberDateOfBirth" type="{urn:hl7-org:v3}ts"/>
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
    "subscriberID",
    "subscriberAddress",
    "subscriberPhone",
    "subscriberName",
    "subscriberDateOfBirth"
})
public class SubscriberInformation {

    /** The subscriber id. */
    @XmlElement(required = true)
    protected Ii subscriberID;
    
    /** The subscriber address. */
    @XmlElement(required = true)
    protected Addr subscriberAddress;
    
    /** The subscriber phone. */
    protected List<Tele> subscriberPhone;
    
    /** The subscriber name. */
    @XmlElement(required = true)
    protected Pnm subscriberName;
    
    /** The subscriber date of birth. */
    @XmlElement(required = true)
    protected Ts subscriberDateOfBirth;

    /**
     * Gets the value of the subscriberID property.
     *
     * @return the subscriber id
     * possible object is
     * {@link Ii }
     */
    public Ii getSubscriberID() {
        return subscriberID;
    }

    /**
     * Sets the value of the subscriberID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setSubscriberID(Ii value) {
        this.subscriberID = value;
    }

    /**
     * Gets the value of the subscriberAddress property.
     *
     * @return the subscriber address
     * possible object is
     * {@link Addr }
     */
    public Addr getSubscriberAddress() {
        return subscriberAddress;
    }

    /**
     * Sets the value of the subscriberAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Addr }
     *     
     */
    public void setSubscriberAddress(Addr value) {
        this.subscriberAddress = value;
    }

    /**
     * Gets the value of the subscriberPhone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subscriberPhone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getSubscriberPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the subscriber phone
     * {@link Tele }
     */
    public List<Tele> getSubscriberPhone() {
        if (subscriberPhone == null) {
            subscriberPhone = new ArrayList<Tele>();
        }
        return this.subscriberPhone;
    }

    /**
     * Gets the value of the subscriberName property.
     *
     * @return the subscriber name
     * possible object is
     * {@link Pnm }
     */
    public Pnm getSubscriberName() {
        return subscriberName;
    }

    /**
     * Sets the value of the subscriberName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pnm }
     *     
     */
    public void setSubscriberName(Pnm value) {
        this.subscriberName = value;
    }

    /**
     * Gets the value of the subscriberDateOfBirth property.
     *
     * @return the subscriber date of birth
     * possible object is
     * {@link Ts }
     */
    public Ts getSubscriberDateOfBirth() {
        return subscriberDateOfBirth;
    }

    /**
     * Sets the value of the subscriberDateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ts }
     *     
     */
    public void setSubscriberDateOfBirth(Ts value) {
        this.subscriberDateOfBirth = value;
    }

}
