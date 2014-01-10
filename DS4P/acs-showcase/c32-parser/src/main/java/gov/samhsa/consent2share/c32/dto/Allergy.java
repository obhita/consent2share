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
 *         &lt;element name="adverseEventType" type="{urn:hl7-org:v3}cd"/>
 *         &lt;element name="adverseEventDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *         &lt;element name="product" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reaction" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="severity" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="allergyStatus" type="{urn:hl7-org:v3}cd" maxOccurs="unbounded" minOccurs="0"/>
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
    "adverseEventType",
    "adverseEventDate",
    "product",
    "reaction",
    "severity",
    "allergyStatus",
    "comment"
})
public class Allergy {

    /** The adverse event type. */
    @XmlElement(required = true)
    protected Cd adverseEventType;
    
    /** The adverse event date. */
    protected IvlTs adverseEventDate;
    
    /** The product. */
    protected List<Cd> product;
    
    /** The reaction. */
    protected List<Cd> reaction;
    
    /** The severity. */
    protected List<Cd> severity;
    
    /** The allergy status. */
    protected List<Cd> allergyStatus;
    
    /** The comment. */
    protected List<Comment> comment;

    /**
     * Gets the value of the adverseEventType property.
     *
     * @return the adverse event type
     * possible object is
     * {@link Cd }
     */
    public Cd getAdverseEventType() {
        return adverseEventType;
    }

    /**
     * Sets the value of the adverseEventType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setAdverseEventType(Cd value) {
        this.adverseEventType = value;
    }

    /**
     * Gets the value of the adverseEventDate property.
     *
     * @return the adverse event date
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getAdverseEventDate() {
        return adverseEventDate;
    }

    /**
     * Sets the value of the adverseEventDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setAdverseEventDate(IvlTs value) {
        this.adverseEventDate = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the product property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the product
     * {@link Cd }
     */
    public List<Cd> getProduct() {
        if (product == null) {
            product = new ArrayList<Cd>();
        }
        return this.product;
    }

    /**
     * Gets the value of the reaction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reaction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getReaction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the reaction
     * {@link Cd }
     */
    public List<Cd> getReaction() {
        if (reaction == null) {
            reaction = new ArrayList<Cd>();
        }
        return this.reaction;
    }

    /**
     * Gets the value of the severity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the severity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getSeverity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the severity
     * {@link Cd }
     */
    public List<Cd> getSeverity() {
        if (severity == null) {
            severity = new ArrayList<Cd>();
        }
        return this.severity;
    }

    /**
     * Gets the value of the allergyStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allergyStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getAllergyStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the allergy status
     * {@link Cd }
     */
    public List<Cd> getAllergyStatus() {
        if (allergyStatus == null) {
            allergyStatus = new ArrayList<Cd>();
        }
        return this.allergyStatus;
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
