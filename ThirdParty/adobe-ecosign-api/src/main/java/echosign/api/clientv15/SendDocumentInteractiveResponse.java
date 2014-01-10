
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto12.SendDocumentInteractiveResult;


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
 *         &lt;element name="sendDocumentInteractiveResult" type="{http://dto12.api.echosign}SendDocumentInteractiveResult"/>
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
    "sendDocumentInteractiveResult"
})
@XmlRootElement(name = "sendDocumentInteractiveResponse")
public class SendDocumentInteractiveResponse {

    @XmlElement(required = true, nillable = true)
    protected SendDocumentInteractiveResult sendDocumentInteractiveResult;

    /**
     * Gets the value of the sendDocumentInteractiveResult property.
     * 
     * @return
     *     possible object is
     *     {@link SendDocumentInteractiveResult }
     *     
     */
    public SendDocumentInteractiveResult getSendDocumentInteractiveResult() {
        return sendDocumentInteractiveResult;
    }

    /**
     * Sets the value of the sendDocumentInteractiveResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendDocumentInteractiveResult }
     *     
     */
    public void setSendDocumentInteractiveResult(SendDocumentInteractiveResult value) {
        this.sendDocumentInteractiveResult = value;
    }

}
