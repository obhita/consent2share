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
 *         &lt;element name="problemCode" type="{urn:hl7-org:v3}cd" minOccurs="0"/>
 *         &lt;element name="timeOfDeath" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *         &lt;element name="ageAtDeath" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
    "problemCode",
    "timeOfDeath",
    "ageAtDeath"
})
public class CauseOfDeath {

    /** The problem code. */
    protected Cd problemCode;
    
    /** The time of death. */
    protected Ts timeOfDeath;
    
    /** The age at death. */
    protected BigInteger ageAtDeath;

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
     * Gets the value of the timeOfDeath property.
     *
     * @return the time of death
     * possible object is
     * {@link Ts }
     */
    public Ts getTimeOfDeath() {
        return timeOfDeath;
    }

    /**
     * Sets the value of the timeOfDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ts }
     *     
     */
    public void setTimeOfDeath(Ts value) {
        this.timeOfDeath = value;
    }

    /**
     * Gets the value of the ageAtDeath property.
     *
     * @return the age at death
     * possible object is
     * {@link BigInteger }
     */
    public BigInteger getAgeAtDeath() {
        return ageAtDeath;
    }

    /**
     * Sets the value of the ageAtDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAgeAtDeath(BigInteger value) {
        this.ageAtDeath = value;
    }

}
