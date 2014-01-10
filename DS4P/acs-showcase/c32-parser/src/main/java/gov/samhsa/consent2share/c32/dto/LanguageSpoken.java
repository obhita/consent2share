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
 *         &lt;element name="languageCode" type="{urn:hl7-org:v3}cs"/>
 *         &lt;element name="modeCode" type="{urn:hl7-org:v3}ce" minOccurs="0"/>
 *         &lt;element name="preferenceInd" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}nullFlavorType" />
 *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
    "languageCode",
    "modeCode",
    "preferenceInd"
})
public class LanguageSpoken {

    /** The language code. */
    @XmlElement(required = true)
    protected Cs languageCode;
    
    /** The mode code. */
    protected Ce modeCode;
    
    /** The preference ind. */
    protected PreferenceInd preferenceInd;

    /**
     * Gets the value of the languageCode property.
     *
     * @return the language code
     * possible object is
     * {@link Cs }
     */
    public Cs getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cs }
     *     
     */
    public void setLanguageCode(Cs value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the modeCode property.
     *
     * @return the mode code
     * possible object is
     * {@link Ce }
     */
    public Ce getModeCode() {
        return modeCode;
    }

    /**
     * Sets the value of the modeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ce }
     *     
     */
    public void setModeCode(Ce value) {
        this.modeCode = value;
    }

    /**
     * Gets the value of the preferenceInd property.
     *
     * @return the preference ind
     * possible object is
     * {@link PreferenceInd }
     */
    public PreferenceInd getPreferenceInd() {
        return preferenceInd;
    }

    /**
     * Sets the value of the preferenceInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenceInd }
     *     
     */
    public void setPreferenceInd(PreferenceInd value) {
        this.preferenceInd = value;
    }

}
