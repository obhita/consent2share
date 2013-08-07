
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.SendReminderResult;


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
 *         &lt;element name="sendreminderResult" type="{http://dto.api.echosign}SendReminderResult"/>
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
    "sendreminderResult"
})
@XmlRootElement(name = "sendReminderResponse")
public class SendReminderResponse {

    @XmlElement(required = true, nillable = true)
    protected SendReminderResult sendreminderResult;

    /**
     * Gets the value of the sendreminderResult property.
     * 
     * @return
     *     possible object is
     *     {@link SendReminderResult }
     *     
     */
    public SendReminderResult getSendreminderResult() {
        return sendreminderResult;
    }

    /**
     * Sets the value of the sendreminderResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendReminderResult }
     *     
     */
    public void setSendreminderResult(SendReminderResult value) {
        this.sendreminderResult = value;
    }

}
