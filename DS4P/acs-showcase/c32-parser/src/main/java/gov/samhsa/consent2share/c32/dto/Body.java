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
 *         &lt;element ref="{urn:hl7-org:v3}results" minOccurs="0"/>
 *         &lt;element name="encounters" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="encounter" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="encounterID" type="{urn:hl7-org:v3}ii" maxOccurs="unbounded"/>
 *                             &lt;element name="encounterType" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{urn:hl7-org:v3}cd">
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="encounterDateTime" type="{urn:hl7-org:v3}ivlTs"/>
 *                             &lt;element name="dischargeDisposition" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                             &lt;element name="admissionType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="encounterProvider" type="{urn:hl7-org:v3}providerInformation" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="facilityLocation" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="locationDuration" type="{urn:hl7-org:v3}ivlTs"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="reasonForVisit" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                                       &lt;element name="reason" type="{urn:hl7-org:v3}cd"/>
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
 *         &lt;element name="medications" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="medication" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="indicateMedicationStopped" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *                             &lt;element name="medicationDateRange" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                             &lt;element name="admissionTiming" type="{urn:hl7-org:v3}pivlTs" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="route" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="dose" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *                             &lt;element name="site" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="doseRestriction" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="numerator" type="{urn:hl7-org:v3}pq"/>
 *                                       &lt;element name="denominator" type="{urn:hl7-org:v3}pq"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="productForm" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="deliveryMethod" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="medicationInformation" type="{urn:hl7-org:v3}medicationInformation" maxOccurs="unbounded"/>
 *                             &lt;element name="typeOfMedication" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{urn:hl7-org:v3}cd">
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="statusOfMedication" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{urn:hl7-org:v3}cd">
 *                                     &lt;attribute name="valueType" type="{urn:hl7-org:v3}valueType" />
 *                                     &lt;attribute name="status" type="{urn:hl7-org:v3}statusType" />
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="indication" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{urn:hl7-org:v3}cd">
 *                                     &lt;attribute name="status" type="{urn:hl7-org:v3}statusType" />
 *                                     &lt;attribute name="freeTextRef" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="frequency" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                             &lt;element name="patientInstructions" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                             &lt;element name="reaction" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="vehicle" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{urn:hl7-org:v3}cd">
 *                                     &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="doseIndicator" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="orderInformation" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="orderNumber" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                                       &lt;element name="fills" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="quantityOrdered" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *                                       &lt;element name="orderExpirationDateTime" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *                                       &lt;element name="orderDateTime" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="fulfillmentInstructions" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                             &lt;element name="fulfillmentHistory" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="prescriptionNumber" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                                       &lt;element ref="{urn:hl7-org:v3}provider" minOccurs="0"/>
 *                                       &lt;element name="dispensingPharmacyLocation" type="{urn:hl7-org:v3}addr" minOccurs="0"/>
 *                                       &lt;element name="dispenseDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                                       &lt;element name="quantityDispensed" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *                                       &lt;element name="fillNumber" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                                               &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}nullFlavorType" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="fillStatus" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attribute name="value" type="{urn:hl7-org:v3}statusType" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
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
 *         &lt;element name="immunizations" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="immunization" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="administeredDate" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *                             &lt;element name="medicationSeriesNumber" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *                             &lt;element name="route" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="reaction" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="immunizationProvider" type="{urn:hl7-org:v3}providerInformation" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="medicationInformation" type="{urn:hl7-org:v3}medicationInformation" maxOccurs="unbounded"/>
 *                             &lt;element name="refusalReason" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="statusOfImmunization" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element ref="{urn:hl7-org:v3}comment" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="refusalInd" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="allergies" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="allergy" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="adverseEventType" type="{urn:hl7-org:v3}cd"/>
 *                             &lt;element name="adverseEventDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                             &lt;element name="product" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="reaction" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="severity" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="allergyStatus" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
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
 *         &lt;element name="vitalSigns" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="vitalSign" type="{urn:hl7-org:v3}result" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="advanceDirectives" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="advanceDirective" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="type">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{urn:hl7-org:v3}cd">
 *                                     &lt;sequence>
 *                                       &lt;element name="originalText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="0" minOccurs="0"/>
 *                                       &lt;element ref="{urn:hl7-org:v3}qualifier" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="value" type="{urn:hl7-org:v3}cd"/>
 *                             &lt;element name="effectiveDate" type="{urn:hl7-org:v3}ivlTs"/>
 *                             &lt;element name="status" type="{urn:hl7-org:v3}ce"/>
 *                             &lt;element name="documentCustodian" type="{urn:hl7-org:v3}organizationInformation"/>
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
 *         &lt;element name="procedures" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="procedure" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="procedureID" type="{urn:hl7-org:v3}ii" maxOccurs="unbounded"/>
 *                             &lt;element name="procedureType" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{urn:hl7-org:v3}cd">
 *                                     &lt;sequence>
 *                                       &lt;element name="originalText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="0" minOccurs="0"/>
 *                                       &lt;element ref="{urn:hl7-org:v3}qualifier" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="procedureFreeTextType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="procedureDateTime" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                             &lt;element name="bodySite" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="procedureProvider" type="{urn:hl7-org:v3}providerInformation" maxOccurs="unbounded" minOccurs="0"/>
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
 *         &lt;element name="insuranceProviders" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="insuranceProvider" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="resultID" type="{urn:hl7-org:v3}ii"/>
 *                             &lt;element name="healthInsuranceType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="payer" type="{urn:hl7-org:v3}organizationInformation"/>
 *                             &lt;element name="memberInformation">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="healthPlanCoverageDates" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                                       &lt;element name="memberID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                                       &lt;element name="patientRelationshipToSubscriber" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                                       &lt;element name="patientAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="patientPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="patientName" type="{urn:hl7-org:v3}pnm"/>
 *                                       &lt;element name="patientDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="financialResponsibilityPartyType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="subscriberInformation" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="subscriberID" type="{urn:hl7-org:v3}ii"/>
 *                                       &lt;element name="subscriberAddress" type="{urn:hl7-org:v3}addr"/>
 *                                       &lt;element name="subscriberPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="subscriberName" type="{urn:hl7-org:v3}pnm"/>
 *                                       &lt;element name="subscriberDateOfBirth" type="{urn:hl7-org:v3}ts"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="guarantorInformation" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="responsibilityEffectiveDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                                       &lt;element name="partyAddress" type="{urn:hl7-org:v3}addr" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="partyPhone" type="{urn:hl7-org:v3}tele" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="partyName" type="{urn:hl7-org:v3}pnm" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="healthPlanName" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
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
 *         &lt;element name="pregnancies" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="pregnancy" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{urn:hl7-org:v3}cd">
 *                           &lt;sequence>
 *                             &lt;element ref="{urn:hl7-org:v3}comment" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="conditions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="condition" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="diagnosisPriority" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="problemDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *                             &lt;element name="problemType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="problemName" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="problemCode" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                             &lt;element name="treatingProvider" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="treatingProviderID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ageAtOnset" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="causeOfDeath" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="problemCode" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                                       &lt;element name="timeOfDeath" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *                                       &lt;element name="ageAtDeath" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="problemStatus" type="{urn:hl7-org:v3}ce" minOccurs="0"/>
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
 *         &lt;element name="planOfCare" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;choice maxOccurs="unbounded">
 *                     &lt;element name="plannedObservation" type="{urn:hl7-org:v3}plannedEvent"/>
 *                     &lt;element name="plannedProcedure" type="{urn:hl7-org:v3}plannedEvent"/>
 *                     &lt;element name="plannedAct" type="{urn:hl7-org:v3}plannedEvent"/>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="socialHistory" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{urn:hl7-org:v3}textType" minOccurs="0"/>
 *                   &lt;element name="socialHistoryEntry" type="{urn:hl7-org:v3}socialHistoryEntry" maxOccurs="unbounded"/>
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
    "results",
    "encounters",
    "medications",
    "immunizations",
    "allergies",
    "vitalSigns",
    "advanceDirectives",
    "procedures",
    "insuranceProviders",
    "pregnancies",
    "conditions",
    "planOfCare",
    "socialHistory"
})
public class Body {

    /** The results. */
    protected Results results;
    
    /** The encounters. */
    protected Encounters encounters;
    
    /** The medications. */
    protected Medications medications;
    
    /** The immunizations. */
    protected Immunizations immunizations;
    
    /** The allergies. */
    protected Allergies allergies;
    
    /** The vital signs. */
    protected VitalSigns vitalSigns;
    
    /** The advance directives. */
    protected AdvanceDirectives advanceDirectives;
    
    /** The procedures. */
    protected Procedures procedures;
    
    /** The insurance providers. */
    protected InsuranceProviders insuranceProviders;
    
    /** The pregnancies. */
    protected Pregnancies pregnancies;
    
    /** The conditions. */
    protected Conditions conditions;
    
    /** The plan of care. */
    protected PlanOfCare planOfCare;
    
    /** The social history. */
    protected SocialHistory socialHistory;

    /**
     * Gets the results.
     *
     * @return the results
     * possible object is
     * {@link Results }
     */
    public Results getResults() {
        return results;
    }

    /**
     * Sets the value of the results property.
     * 
     * @param value
     *     allowed object is
     *     {@link Results }
     *     
     */
    public void setResults(Results value) {
        this.results = value;
    }

    /**
     * Gets the value of the encounters property.
     *
     * @return the encounters
     * possible object is
     * {@link Encounters }
     */
    public Encounters getEncounters() {
        return encounters;
    }

    /**
     * Sets the value of the encounters property.
     * 
     * @param value
     *     allowed object is
     *     {@link Encounters }
     *     
     */
    public void setEncounters(Encounters value) {
        this.encounters = value;
    }

    /**
     * Gets the value of the medications property.
     *
     * @return the medications
     * possible object is
     * {@link Medications }
     */
    public Medications getMedications() {
        return medications;
    }

    /**
     * Sets the value of the medications property.
     * 
     * @param value
     *     allowed object is
     *     {@link Medications }
     *     
     */
    public void setMedications(Medications value) {
        this.medications = value;
    }

    /**
     * Gets the value of the immunizations property.
     *
     * @return the immunizations
     * possible object is
     * {@link Immunizations }
     */
    public Immunizations getImmunizations() {
        return immunizations;
    }

    /**
     * Sets the value of the immunizations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Immunizations }
     *     
     */
    public void setImmunizations(Immunizations value) {
        this.immunizations = value;
    }

    /**
     * Gets the value of the allergies property.
     *
     * @return the allergies
     * possible object is
     * {@link Allergies }
     */
    public Allergies getAllergies() {
        return allergies;
    }

    /**
     * Sets the value of the allergies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Allergies }
     *     
     */
    public void setAllergies(Allergies value) {
        this.allergies = value;
    }

    /**
     * Gets the value of the vitalSigns property.
     *
     * @return the vital signs
     * possible object is
     * {@link VitalSigns }
     */
    public VitalSigns getVitalSigns() {
        return vitalSigns;
    }

    /**
     * Sets the value of the vitalSigns property.
     * 
     * @param value
     *     allowed object is
     *     {@link VitalSigns }
     *     
     */
    public void setVitalSigns(VitalSigns value) {
        this.vitalSigns = value;
    }

    /**
     * Gets the value of the advanceDirectives property.
     *
     * @return the advance directives
     * possible object is
     * {@link AdvanceDirectives }
     */
    public AdvanceDirectives getAdvanceDirectives() {
        return advanceDirectives;
    }

    /**
     * Sets the value of the advanceDirectives property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdvanceDirectives }
     *     
     */
    public void setAdvanceDirectives(AdvanceDirectives value) {
        this.advanceDirectives = value;
    }

    /**
     * Gets the value of the procedures property.
     *
     * @return the procedures
     * possible object is
     * {@link Procedures }
     */
    public Procedures getProcedures() {
        return procedures;
    }

    /**
     * Sets the value of the procedures property.
     * 
     * @param value
     *     allowed object is
     *     {@link Procedures }
     *     
     */
    public void setProcedures(Procedures value) {
        this.procedures = value;
    }

    /**
     * Gets the value of the insuranceProviders property.
     *
     * @return the insurance providers
     * possible object is
     * {@link InsuranceProviders }
     */
    public InsuranceProviders getInsuranceProviders() {
        return insuranceProviders;
    }

    /**
     * Sets the value of the insuranceProviders property.
     * 
     * @param value
     *     allowed object is
     *     {@link InsuranceProviders }
     *     
     */
    public void setInsuranceProviders(InsuranceProviders value) {
        this.insuranceProviders = value;
    }

    /**
     * Gets the value of the pregnancies property.
     *
     * @return the pregnancies
     * possible object is
     * {@link Pregnancies }
     */
    public Pregnancies getPregnancies() {
        return pregnancies;
    }

    /**
     * Sets the value of the pregnancies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pregnancies }
     *     
     */
    public void setPregnancies(Pregnancies value) {
        this.pregnancies = value;
    }

    /**
     * Gets the value of the conditions property.
     *
     * @return the conditions
     * possible object is
     * {@link Conditions }
     */
    public Conditions getConditions() {
        return conditions;
    }

    /**
     * Sets the value of the conditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Conditions }
     *     
     */
    public void setConditions(Conditions value) {
        this.conditions = value;
    }

    /**
     * Gets the value of the planOfCare property.
     *
     * @return the plan of care
     * possible object is
     * {@link PlanOfCare }
     */
    public PlanOfCare getPlanOfCare() {
        return planOfCare;
    }

    /**
     * Sets the value of the planOfCare property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanOfCare }
     *     
     */
    public void setPlanOfCare(PlanOfCare value) {
        this.planOfCare = value;
    }

    /**
     * Gets the value of the socialHistory property.
     *
     * @return the social history
     * possible object is
     * {@link SocialHistory }
     */
    public SocialHistory getSocialHistory() {
        return socialHistory;
    }

    /**
     * Sets the value of the socialHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link SocialHistory }
     *     
     */
    public void setSocialHistory(SocialHistory value) {
        this.socialHistory = value;
    }

}
