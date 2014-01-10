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

import java.math.BigInteger;
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
 *         &lt;element name="diagnosisPriority" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="problemDate" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *         &lt;element name="problemType" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="problemName" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="problemCode" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="treatingProvider" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="treatingProviderID" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ageAtOnset" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="causeOfDeath" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="problemCode" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *                   &lt;element name="timeOfDeath" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *                   &lt;element name="ageAtDeath" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="problemStatus" type="{urn:hl7-org:v3}ce" minOccurs="0"/>
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
    "diagnosisPriority",
    "problemDate",
    "problemType",
    "problemName",
    "problemCode",
    "treatingProvider",
    "ageAtOnset",
    "causeOfDeath",
    "problemStatus",
    "comment"
})
public class Condition {

    /** The diagnosis priority. */
    protected BigInteger diagnosisPriority;
    
    /** The problem date. */
    protected IvlTs problemDate;
    
    /** The problem type. */
    protected Cd problemType;
    
    /** The problem name. */
    @XmlElement(required = true)
    protected String problemName;
    
    /** The problem code. */
    protected Cd problemCode;
    
    /** The treating provider. */
    protected List<TreatingProvider> treatingProvider;
    
    /** The age at onset. */
    protected BigInteger ageAtOnset;
    
    /** The cause of death. */
    protected CauseOfDeath causeOfDeath;
    
    /** The problem status. */
    protected Ce problemStatus;
    
    /** The comment. */
    protected List<Comment> comment;

    /**
     * Gets the value of the diagnosisPriority property.
     *
     * @return the diagnosis priority
     * possible object is
     * {@link BigInteger }
     */
    public BigInteger getDiagnosisPriority() {
        return diagnosisPriority;
    }

    /**
     * Sets the value of the diagnosisPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDiagnosisPriority(BigInteger value) {
        this.diagnosisPriority = value;
    }

    /**
     * Gets the value of the problemDate property.
     *
     * @return the problem date
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getProblemDate() {
        return problemDate;
    }

    /**
     * Sets the value of the problemDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setProblemDate(IvlTs value) {
        this.problemDate = value;
    }

    /**
     * Gets the value of the problemType property.
     *
     * @return the problem type
     * possible object is
     * {@link Cd }
     */
    public Cd getProblemType() {
        return problemType;
    }

    /**
     * Sets the value of the problemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setProblemType(Cd value) {
        this.problemType = value;
    }

    /**
     * Gets the value of the problemName property.
     *
     * @return the problem name
     * possible object is
     * {@link String }
     */
    public String getProblemName() {
        return problemName;
    }

    /**
     * Sets the value of the problemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProblemName(String value) {
        this.problemName = value;
    }

    /**
     * Gets the value of the problemCode property.
     *
     * @return the problem code
     * possible object is
     * {@link Cd }
     */
    public Cd getProblemCode() {
        return problemCode;
    }

    /**
     * Sets the value of the problemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setProblemCode(Cd value) {
        this.problemCode = value;
    }

    /**
     * Gets the value of the treatingProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treatingProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getTreatingProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the treating provider
     * {@link TreatingProvider }
     */
    public List<TreatingProvider> getTreatingProvider() {
        if (treatingProvider == null) {
            treatingProvider = new ArrayList<TreatingProvider>();
        }
        return this.treatingProvider;
    }

    /**
     * Gets the value of the ageAtOnset property.
     *
     * @return the age at onset
     * possible object is
     * {@link BigInteger }
     */
    public BigInteger getAgeAtOnset() {
        return ageAtOnset;
    }

    /**
     * Sets the value of the ageAtOnset property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAgeAtOnset(BigInteger value) {
        this.ageAtOnset = value;
    }

    /**
     * Gets the value of the causeOfDeath property.
     *
     * @return the cause of death
     * possible object is
     * {@link CauseOfDeath }
     */
    public CauseOfDeath getCauseOfDeath() {
        return causeOfDeath;
    }

    /**
     * Sets the value of the causeOfDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link CauseOfDeath }
     *     
     */
    public void setCauseOfDeath(CauseOfDeath value) {
        this.causeOfDeath = value;
    }

    /**
     * Gets the value of the problemStatus property.
     *
     * @return the problem status
     * possible object is
     * {@link Ce }
     */
    public Ce getProblemStatus() {
        return problemStatus;
    }

    /**
     * Sets the value of the problemStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ce }
     *     
     */
    public void setProblemStatus(Ce value) {
        this.problemStatus = value;
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
