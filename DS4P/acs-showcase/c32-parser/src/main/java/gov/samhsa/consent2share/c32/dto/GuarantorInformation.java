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
 *         &lt;element name="responsibilityEffectiveDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *         &lt;element name="partyAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="partyPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="partyName" type="{urn:hl7-org:v3}pnm" minOccurs="0"/>
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
    "responsibilityEffectiveDate",
    "partyAddress",
    "partyPhone",
    "partyName"
})
public class GuarantorInformation {

    /** The responsibility effective date. */
    protected IvlTs responsibilityEffectiveDate;
    
    /** The party address. */
    protected List<Addr> partyAddress;
    
    /** The party phone. */
    protected List<Tele> partyPhone;
    
    /** The party name. */
    protected Pnm partyName;

    /**
     * Gets the value of the responsibilityEffectiveDate property.
     *
     * @return the responsibility effective date
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getResponsibilityEffectiveDate() {
        return responsibilityEffectiveDate;
    }

    /**
     * Sets the value of the responsibilityEffectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setResponsibilityEffectiveDate(IvlTs value) {
        this.responsibilityEffectiveDate = value;
    }

    /**
     * Gets the value of the partyAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partyAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPartyAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the party address
     * {@link Addr }
     */
    public List<Addr> getPartyAddress() {
        if (partyAddress == null) {
            partyAddress = new ArrayList<Addr>();
        }
        return this.partyAddress;
    }

    /**
     * Gets the value of the partyPhone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partyPhone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPartyPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the party phone
     * {@link Tele }
     */
    public List<Tele> getPartyPhone() {
        if (partyPhone == null) {
            partyPhone = new ArrayList<Tele>();
        }
        return this.partyPhone;
    }

    /**
     * Gets the value of the partyName property.
     *
     * @return the party name
     * possible object is
     * {@link Pnm }
     */
    public Pnm getPartyName() {
        return partyName;
    }

    /**
     * Sets the value of the partyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pnm }
     *     
     */
    public void setPartyName(Pnm value) {
        this.partyName = value;
    }

}
