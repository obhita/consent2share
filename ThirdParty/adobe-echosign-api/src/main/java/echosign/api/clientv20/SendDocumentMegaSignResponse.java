
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto16.SendDocumentMegaSignResult;


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
 *         &lt;element name="sendMegaSignDocumentResult" type="{http://dto16.api.echosign}SendDocumentMegaSignResult"/>
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
    "sendMegaSignDocumentResult"
})
@XmlRootElement(name = "sendDocumentMegaSignResponse")
public class SendDocumentMegaSignResponse {

    @XmlElement(required = true, nillable = true)
    protected SendDocumentMegaSignResult sendMegaSignDocumentResult;

    /**
     * Gets the value of the sendMegaSignDocumentResult property.
     * 
     * @return
     *     possible object is
     *     {@link SendDocumentMegaSignResult }
     *     
     */
    public SendDocumentMegaSignResult getSendMegaSignDocumentResult() {
        return sendMegaSignDocumentResult;
    }

    /**
     * Sets the value of the sendMegaSignDocumentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendDocumentMegaSignResult }
     *     
     */
    public void setSendMegaSignDocumentResult(SendDocumentMegaSignResult value) {
        this.sendMegaSignDocumentResult = value;
    }

}
