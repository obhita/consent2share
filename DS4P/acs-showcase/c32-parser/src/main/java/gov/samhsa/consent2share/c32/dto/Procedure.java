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
 *         &lt;element name="procedureID" type="{urn:hl7-org:v3}ii" maxOccurs="unbounded"/>
 *         &lt;element name="procedureType" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{urn:hl7-org:v3}cd">
 *                 &lt;sequence>
 *                   &lt;element name="originalText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="0" minOccurs="0"/>
 *                   &lt;element ref="{urn:hl7-org:v3}qualifier" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="procedureFreeTextType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="procedureDateTime" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *         &lt;element name="bodySite" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="procedureProvider" type="{urn:hl7-org:v3}providerInformation" maxOccurs="unbounded" minOccurs="0"/>
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
    "procedureID",
    "procedureType",
    "procedureFreeTextType",
    "procedureDateTime",
    "bodySite",
    "procedureProvider",
    "comment"
})
public class Procedure {

    /** The procedure id. */
    @XmlElement(required = true)
    protected List<Ii> procedureID;
    
    /** The procedure type. */
    protected ProcedureType procedureType;
    
    /** The procedure free text type. */
    @XmlElement(required = true)
    protected String procedureFreeTextType;
    
    /** The procedure date time. */
    protected IvlTs procedureDateTime;
    
    /** The body site. */
    protected Cd bodySite;
    
    /** The procedure provider. */
    protected List<ProviderInformation> procedureProvider;
    
    /** The comment. */
    protected List<Comment> comment;

    /**
     * Gets the value of the procedureID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the procedureID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getProcedureID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the procedure id
     * {@link Ii }
     */
    public List<Ii> getProcedureID() {
        if (procedureID == null) {
            procedureID = new ArrayList<Ii>();
        }
        return this.procedureID;
    }

    /**
     * Gets the value of the procedureType property.
     *
     * @return the procedure type
     * possible object is
     * {@link ProcedureType }
     */
    public ProcedureType getProcedureType() {
        return procedureType;
    }

    /**
     * Sets the value of the procedureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcedureType }
     *     
     */
    public void setProcedureType(ProcedureType value) {
        this.procedureType = value;
    }

    /**
     * Gets the value of the procedureFreeTextType property.
     *
     * @return the procedure free text type
     * possible object is
     * {@link String }
     */
    public String getProcedureFreeTextType() {
        return procedureFreeTextType;
    }

    /**
     * Sets the value of the procedureFreeTextType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcedureFreeTextType(String value) {
        this.procedureFreeTextType = value;
    }

    /**
     * Gets the value of the procedureDateTime property.
     *
     * @return the procedure date time
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getProcedureDateTime() {
        return procedureDateTime;
    }

    /**
     * Sets the value of the procedureDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setProcedureDateTime(IvlTs value) {
        this.procedureDateTime = value;
    }

    /**
     * Gets the value of the bodySite property.
     *
     * @return the body site
     * possible object is
     * {@link Cd }
     */
    public Cd getBodySite() {
        return bodySite;
    }

    /**
     * Sets the value of the bodySite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setBodySite(Cd value) {
        this.bodySite = value;
    }

    /**
     * Gets the value of the procedureProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the procedureProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getProcedureProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the procedure provider
     * {@link ProviderInformation }
     */
    public List<ProviderInformation> getProcedureProvider() {
        if (procedureProvider == null) {
            procedureProvider = new ArrayList<ProviderInformation>();
        }
        return this.procedureProvider;
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
