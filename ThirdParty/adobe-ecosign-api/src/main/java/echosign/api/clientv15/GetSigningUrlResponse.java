
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto14.SigningUrlResult;


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
 *         &lt;element name="getSigningUrlResult" type="{http://dto14.api.echosign}SigningUrlResult"/>
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
    "getSigningUrlResult"
})
@XmlRootElement(name = "getSigningUrlResponse")
public class GetSigningUrlResponse {

    @XmlElement(required = true, nillable = true)
    protected SigningUrlResult getSigningUrlResult;

    /**
     * Gets the value of the getSigningUrlResult property.
     * 
     * @return
     *     possible object is
     *     {@link SigningUrlResult }
     *     
     */
    public SigningUrlResult getGetSigningUrlResult() {
        return getSigningUrlResult;
    }

    /**
     * Sets the value of the getSigningUrlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SigningUrlResult }
     *     
     */
    public void setGetSigningUrlResult(SigningUrlResult value) {
        this.getSigningUrlResult = value;
    }

}
