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
 *         &lt;element name="orderNumber" type="{urn:hl7-org:v3}ii" minOccurs="0"/>
 *         &lt;element name="fills" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="quantityOrdered" type="{urn:hl7-org:v3}pq" minOccurs="0"/>
 *         &lt;element name="orderExpirationDateTime" type="{urn:hl7-org:v3}ts" minOccurs="0"/>
 *         &lt;element name="orderDateTime" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
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
    "orderNumber",
    "fills",
    "quantityOrdered",
    "orderExpirationDateTime",
    "orderDateTime"
})
public class OrderInformation {

    /** The order number. */
    protected Ii orderNumber;
    
    /** The fills. */
    protected Fills fills;
    
    /** The quantity ordered. */
    protected Pq quantityOrdered;
    
    /** The order expiration date time. */
    protected Ts orderExpirationDateTime;
    
    /** The order date time. */
    protected IvlTs orderDateTime;

    /**
     * Gets the value of the orderNumber property.
     *
     * @return the order number
     * possible object is
     * {@link Ii }
     */
    public Ii getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setOrderNumber(Ii value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the fills property.
     *
     * @return the fills
     * possible object is
     * {@link Fills }
     */
    public Fills getFills() {
        return fills;
    }

    /**
     * Sets the value of the fills property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fills }
     *     
     */
    public void setFills(Fills value) {
        this.fills = value;
    }

    /**
     * Gets the value of the quantityOrdered property.
     *
     * @return the quantity ordered
     * possible object is
     * {@link Pq }
     */
    public Pq getQuantityOrdered() {
        return quantityOrdered;
    }

    /**
     * Sets the value of the quantityOrdered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pq }
     *     
     */
    public void setQuantityOrdered(Pq value) {
        this.quantityOrdered = value;
    }

    /**
     * Gets the value of the orderExpirationDateTime property.
     *
     * @return the order expiration date time
     * possible object is
     * {@link Ts }
     */
    public Ts getOrderExpirationDateTime() {
        return orderExpirationDateTime;
    }

    /**
     * Sets the value of the orderExpirationDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ts }
     *     
     */
    public void setOrderExpirationDateTime(Ts value) {
        this.orderExpirationDateTime = value;
    }

    /**
     * Gets the value of the orderDateTime property.
     *
     * @return the order date time
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getOrderDateTime() {
        return orderDateTime;
    }

    /**
     * Sets the value of the orderDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setOrderDateTime(IvlTs value) {
        this.orderDateTime = value;
    }

}
