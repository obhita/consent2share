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
 *         &lt;element name="prescriptionNumber" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *         &lt;element ref="{urn:hl7-org:v3}provider" minOccurs="0"/>
 *         &lt;element name="dispensingPharmacyLocation" type="{urn:hl7-org:v3}addr" minOccurs="0"/>
 *         &lt;element name="dispenseDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *         &lt;element name="quantityDispensed" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *         &lt;element name="fillNumber" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}nullFlavorType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="fillStatus" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="value" type="{urn:hl7-org:v3}statusType" />
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
    "prescriptionNumber",
    "provider",
    "dispensingPharmacyLocation",
    "dispenseDate",
    "quantityDispensed",
    "fillNumber",
    "fillStatus"
})
public class FulfillmentHistory {

    /** The prescription number. */
    protected Ii prescriptionNumber;
    
    /** The provider. */
    protected OrganizationInformation provider;
    
    /** The dispensing pharmacy location. */
    protected Addr dispensingPharmacyLocation;
    
    /** The dispense date. */
    protected IvlTs dispenseDate;
    
    /** The quantity dispensed. */
    protected Pq quantityDispensed;
    
    /** The fill number. */
    protected FillNumber fillNumber;
    
    /** The fill status. */
    protected FillStatus fillStatus;

    /**
     * Gets the value of the prescriptionNumber property.
     *
     * @return the prescription number
     * possible object is
     * {@link Ii }
     */
    public Ii getPrescriptionNumber() {
        return prescriptionNumber;
    }

    /**
     * Sets the value of the prescriptionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setPrescriptionNumber(Ii value) {
        this.prescriptionNumber = value;
    }

    /**
     * Gets the value of the provider property.
     *
     * @return the provider
     * possible object is
     * {@link OrganizationInformation }
     */
    public OrganizationInformation getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationInformation }
     *     
     */
    public void setProvider(OrganizationInformation value) {
        this.provider = value;
    }

    /**
     * Gets the value of the dispensingPharmacyLocation property.
     *
     * @return the dispensing pharmacy location
     * possible object is
     * {@link Addr }
     */
    public Addr getDispensingPharmacyLocation() {
        return dispensingPharmacyLocation;
    }

    /**
     * Sets the value of the dispensingPharmacyLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Addr }
     *     
     */
    public void setDispensingPharmacyLocation(Addr value) {
        this.dispensingPharmacyLocation = value;
    }

    /**
     * Gets the value of the dispenseDate property.
     *
     * @return the dispense date
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getDispenseDate() {
        return dispenseDate;
    }

    /**
     * Sets the value of the dispenseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setDispenseDate(IvlTs value) {
        this.dispenseDate = value;
    }

    /**
     * Gets the value of the quantityDispensed property.
     *
     * @return the quantity dispensed
     * possible object is
     * {@link Pq }
     */
    public Pq getQuantityDispensed() {
        return quantityDispensed;
    }

    /**
     * Sets the value of the quantityDispensed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pq }
     *     
     */
    public void setQuantityDispensed(Pq value) {
        this.quantityDispensed = value;
    }

    /**
     * Gets the value of the fillNumber property.
     *
     * @return the fill number
     * possible object is
     * {@link FillNumber }
     */
    public FillNumber getFillNumber() {
        return fillNumber;
    }

    /**
     * Sets the value of the fillNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link FillNumber }
     *     
     */
    public void setFillNumber(FillNumber value) {
        this.fillNumber = value;
    }

    /**
     * Gets the value of the fillStatus property.
     *
     * @return the fill status
     * possible object is
     * {@link FillStatus }
     */
    public FillStatus getFillStatus() {
        return fillStatus;
    }

    /**
     * Sets the value of the fillStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link FillStatus }
     *     
     */
    public void setFillStatus(FillStatus value) {
        this.fillStatus = value;
    }

}
