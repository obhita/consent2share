
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto9.AuditTrailResult;


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
 *         &lt;element name="getAuditTrailResult" type="{http://dto9.api.echosign}AuditTrailResult"/>
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
    "getAuditTrailResult"
})
@XmlRootElement(name = "getAuditTrailResponse")
public class GetAuditTrailResponse {

    @XmlElement(required = true, nillable = true)
    protected AuditTrailResult getAuditTrailResult;

    /**
     * Gets the value of the getAuditTrailResult property.
     * 
     * @return
     *     possible object is
     *     {@link AuditTrailResult }
     *     
     */
    public AuditTrailResult getGetAuditTrailResult() {
        return getAuditTrailResult;
    }

    /**
     * Sets the value of the getAuditTrailResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditTrailResult }
     *     
     */
    public void setGetAuditTrailResult(AuditTrailResult value) {
        this.getAuditTrailResult = value;
    }

}
