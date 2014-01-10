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
 *         &lt;element name="indicateMedicationStopped" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *         &lt;element name="medicationDateRange" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *         &lt;element name="admissionTiming" type="{urn:hl7-org:v3}pivlTs" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="route" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="dose" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *         &lt;element name="site" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="doseRestriction" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="numerator" type="{urn:hl7-org:v3}pq"/>
 *                   &lt;element name="denominator" type="{urn:hl7-org:v3}pq"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="productForm" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="deliveryMethod" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="medicationInformation" type="{urn:hl7-org:v3}medicationInformation" maxOccurs="unbounded"/>
 *         &lt;element name="typeOfMedication" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{urn:hl7-org:v3}cd">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="statusOfMedication" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{urn:hl7-org:v3}cd">
 *                 &lt;attribute name="valueType" type="{urn:hl7-org:v3}valueType" />
 *                 &lt;attribute name="status" type="{urn:hl7-org:v3}statusType" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="indication" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{urn:hl7-org:v3}cd">
 *                 &lt;attribute name="status" type="{urn:hl7-org:v3}statusType" />
 *                 &lt;attribute name="freeTextRef" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="frequency" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="patientInstructions" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="reaction" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="vehicle" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{urn:hl7-org:v3}cd">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="doseIndicator" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="orderInformation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="orderNumber" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                   &lt;element name="fills" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="quantityOrdered" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *                   &lt;element name="orderExpirationDateTime" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *                   &lt;element name="orderDateTime" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="fulfillmentInstructions" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="fulfillmentHistory" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="prescriptionNumber" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                   &lt;element ref="{urn:hl7-org:v3}provider" minOccurs="0"/>
 *                   &lt;element name="dispensingPharmacyLocation" type="{urn:hl7-org:v3}addr" minOccurs="0"/>
 *                   &lt;element name="dispenseDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                   &lt;element name="quantityDispensed" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *                   &lt;element name="fillNumber" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                           &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}nullFlavorType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="fillStatus" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="value" type="{urn:hl7-org:v3}statusType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "indicateMedicationStopped",
    "medicationDateRange",
    "admissionTiming",
    "route",
    "dose",
    "site",
    "doseRestriction",
    "productForm",
    "deliveryMethod",
    "medicationInformation",
    "typeOfMedication",
    "statusOfMedication",
    "indication",
    "frequency",
    "patientInstructions",
    "reaction",
    "vehicle",
    "doseIndicator",
    "orderInformation",
    "fulfillmentInstructions",
    "fulfillmentHistory",
    "comment"
})
public class Medication {

    /** The indicate medication stopped. */
    protected Ts indicateMedicationStopped;
    
    /** The medication date range. */
    protected IvlTs medicationDateRange;
    
    /** The admission timing. */
    protected List<PivlTs> admissionTiming;
    
    /** The route. */
    protected Cd route;
    
    /** The dose. */
    protected Pq dose;
    
    /** The site. */
    protected List<Cd> site;
    
    /** The dose restriction. */
    protected DoseRestriction doseRestriction;
    
    /** The product form. */
    protected Cd productForm;
    
    /** The delivery method. */
    protected List<Cd> deliveryMethod;
    
    /** The medication information. */
    @XmlElement(required = true)
    protected List<MedicationInformation> medicationInformation;
    
    /** The type of medication. */
    protected TypeOfMedication typeOfMedication;
    
    /** The status of medication. */
    protected StatusOfMedication statusOfMedication;
    
    /** The indication. */
    protected List<Indication> indication;
    
    /** The frequency. */
    protected String frequency;
    
    /** The patient instructions. */
    protected String patientInstructions;
    
    /** The reaction. */
    protected Cd reaction;
    
    /** The vehicle. */
    protected List<Vehicle> vehicle;
    
    /** The dose indicator. */
    protected List<String> doseIndicator;
    
    /** The order information. */
    protected List<OrderInformation> orderInformation;
    
    /** The fulfillment instructions. */
    protected String fulfillmentInstructions;
    
    /** The fulfillment history. */
    protected List<FulfillmentHistory> fulfillmentHistory;
    
    /** The comment. */
    protected List<Comment> comment;

    /**
     * Gets the value of the indicateMedicationStopped property.
     *
     * @return the indicate medication stopped
     * possible object is
     * {@link Ts }
     */
    public Ts getIndicateMedicationStopped() {
        return indicateMedicationStopped;
    }

    /**
     * Sets the value of the indicateMedicationStopped property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ts }
     *     
     */
    public void setIndicateMedicationStopped(Ts value) {
        this.indicateMedicationStopped = value;
    }

    /**
     * Gets the value of the medicationDateRange property.
     *
     * @return the medication date range
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getMedicationDateRange() {
        return medicationDateRange;
    }

    /**
     * Sets the value of the medicationDateRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setMedicationDateRange(IvlTs value) {
        this.medicationDateRange = value;
    }

    /**
     * Gets the value of the admissionTiming property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the admissionTiming property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getAdmissionTiming().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the admission timing
     * {@link PivlTs }
     */
    public List<PivlTs> getAdmissionTiming() {
        if (admissionTiming == null) {
            admissionTiming = new ArrayList<PivlTs>();
        }
        return this.admissionTiming;
    }

    /**
     * Gets the value of the route property.
     *
     * @return the route
     * possible object is
     * {@link Cd }
     */
    public Cd getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setRoute(Cd value) {
        this.route = value;
    }

    /**
     * Gets the value of the dose property.
     *
     * @return the dose
     * possible object is
     * {@link Pq }
     */
    public Pq getDose() {
        return dose;
    }

    /**
     * Sets the value of the dose property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pq }
     *     
     */
    public void setDose(Pq value) {
        this.dose = value;
    }

    /**
     * Gets the value of the site property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the site property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getSite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the site
     * {@link Cd }
     */
    public List<Cd> getSite() {
        if (site == null) {
            site = new ArrayList<Cd>();
        }
        return this.site;
    }

    /**
     * Gets the value of the doseRestriction property.
     *
     * @return the dose restriction
     * possible object is
     * {@link DoseRestriction }
     */
    public DoseRestriction getDoseRestriction() {
        return doseRestriction;
    }

    /**
     * Sets the value of the doseRestriction property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoseRestriction }
     *     
     */
    public void setDoseRestriction(DoseRestriction value) {
        this.doseRestriction = value;
    }

    /**
     * Gets the value of the productForm property.
     *
     * @return the product form
     * possible object is
     * {@link Cd }
     */
    public Cd getProductForm() {
        return productForm;
    }

    /**
     * Sets the value of the productForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setProductForm(Cd value) {
        this.productForm = value;
    }

    /**
     * Gets the value of the deliveryMethod property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deliveryMethod property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getDeliveryMethod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the delivery method
     * {@link Cd }
     */
    public List<Cd> getDeliveryMethod() {
        if (deliveryMethod == null) {
            deliveryMethod = new ArrayList<Cd>();
        }
        return this.deliveryMethod;
    }

    /**
     * Gets the value of the medicationInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the medicationInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getMedicationInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the medication information
     * {@link MedicationInformation }
     */
    public List<MedicationInformation> getMedicationInformation() {
        if (medicationInformation == null) {
            medicationInformation = new ArrayList<MedicationInformation>();
        }
        return this.medicationInformation;
    }

    /**
     * Gets the value of the typeOfMedication property.
     *
     * @return the type of medication
     * possible object is
     * {@link TypeOfMedication }
     */
    public TypeOfMedication getTypeOfMedication() {
        return typeOfMedication;
    }

    /**
     * Sets the value of the typeOfMedication property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfMedication }
     *     
     */
    public void setTypeOfMedication(TypeOfMedication value) {
        this.typeOfMedication = value;
    }

    /**
     * Gets the value of the statusOfMedication property.
     *
     * @return the status of medication
     * possible object is
     * {@link StatusOfMedication }
     */
    public StatusOfMedication getStatusOfMedication() {
        return statusOfMedication;
    }

    /**
     * Sets the value of the statusOfMedication property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusOfMedication }
     *     
     */
    public void setStatusOfMedication(StatusOfMedication value) {
        this.statusOfMedication = value;
    }

    /**
     * Gets the value of the indication property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indication property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getIndication().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the indication
     * {@link Indication }
     */
    public List<Indication> getIndication() {
        if (indication == null) {
            indication = new ArrayList<Indication>();
        }
        return this.indication;
    }

    /**
     * Gets the value of the frequency property.
     *
     * @return the frequency
     * possible object is
     * {@link String }
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrequency(String value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the patientInstructions property.
     *
     * @return the patient instructions
     * possible object is
     * {@link String }
     */
    public String getPatientInstructions() {
        return patientInstructions;
    }

    /**
     * Sets the value of the patientInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientInstructions(String value) {
        this.patientInstructions = value;
    }

    /**
     * Gets the value of the reaction property.
     *
     * @return the reaction
     * possible object is
     * {@link Cd }
     */
    public Cd getReaction() {
        return reaction;
    }

    /**
     * Sets the value of the reaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setReaction(Cd value) {
        this.reaction = value;
    }

    /**
     * Gets the value of the vehicle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vehicle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getVehicle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the vehicle
     * {@link Vehicle }
     */
    public List<Vehicle> getVehicle() {
        if (vehicle == null) {
            vehicle = new ArrayList<Vehicle>();
        }
        return this.vehicle;
    }

    /**
     * Gets the value of the doseIndicator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the doseIndicator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getDoseIndicator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the dose indicator
     * {@link String }
     */
    public List<String> getDoseIndicator() {
        if (doseIndicator == null) {
            doseIndicator = new ArrayList<String>();
        }
        return this.doseIndicator;
    }

    /**
     * Gets the value of the orderInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getOrderInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the order information
     * {@link OrderInformation }
     */
    public List<OrderInformation> getOrderInformation() {
        if (orderInformation == null) {
            orderInformation = new ArrayList<OrderInformation>();
        }
        return this.orderInformation;
    }

    /**
     * Gets the value of the fulfillmentInstructions property.
     *
     * @return the fulfillment instructions
     * possible object is
     * {@link String }
     */
    public String getFulfillmentInstructions() {
        return fulfillmentInstructions;
    }

    /**
     * Sets the value of the fulfillmentInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFulfillmentInstructions(String value) {
        this.fulfillmentInstructions = value;
    }

    /**
     * Gets the value of the fulfillmentHistory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fulfillmentHistory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getFulfillmentHistory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the fulfillment history
     * {@link FulfillmentHistory }
     */
    public List<FulfillmentHistory> getFulfillmentHistory() {
        if (fulfillmentHistory == null) {
            fulfillmentHistory = new ArrayList<FulfillmentHistory>();
        }
        return this.fulfillmentHistory;
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
