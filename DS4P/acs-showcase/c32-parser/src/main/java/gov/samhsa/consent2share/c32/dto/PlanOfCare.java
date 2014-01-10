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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
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
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="plannedObservation" type="{urn:hl7-org:v3}plannedEvent"/>
 *           &lt;element name="plannedProcedure" type="{urn:hl7-org:v3}plannedEvent"/>
 *           &lt;element name="plannedAct" type="{urn:hl7-org:v3}plannedEvent"/>
 *         &lt;/choice>
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
    "plannedObservationOrPlannedProcedureOrPlannedAct"
})
public class PlanOfCare {

    /** The text. */
    protected TextType text;
    
    /** The planned observation or planned procedure or planned act. */
    @XmlElementRefs({
        @XmlElementRef(name = "plannedObservation", namespace = "urn:hl7-org:v3", type = JAXBElement.class),
        @XmlElementRef(name = "plannedAct", namespace = "urn:hl7-org:v3", type = JAXBElement.class),
        @XmlElementRef(name = "plannedProcedure", namespace = "urn:hl7-org:v3", type = JAXBElement.class)
    })
    protected List<JAXBElement<PlannedEvent>> plannedObservationOrPlannedProcedureOrPlannedAct;

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
     * Gets the value of the plannedObservationOrPlannedProcedureOrPlannedAct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the plannedObservationOrPlannedProcedureOrPlannedAct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getPlannedObservationOrPlannedProcedureOrPlannedAct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the planned observation or planned procedure or planned act
     * {@link JAXBElement }{@code <}{@link PlannedEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link PlannedEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link PlannedEvent }{@code >}
     */
    public List<JAXBElement<PlannedEvent>> getPlannedObservationOrPlannedProcedureOrPlannedAct() {
        if (plannedObservationOrPlannedProcedureOrPlannedAct == null) {
            plannedObservationOrPlannedProcedureOrPlannedAct = new ArrayList<JAXBElement<PlannedEvent>>();
        }
        return this.plannedObservationOrPlannedProcedureOrPlannedAct;
    }

}
