
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto13.DeliverDocumentResult;


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
 *         &lt;element name="DeliverDocumentResult" type="{http://dto13.api.echosign}DeliverDocumentResult"/>
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
    "deliverDocumentResult"
})
@XmlRootElement(name = "deliverDocumentResponse")
public class DeliverDocumentResponse {

    @XmlElement(name = "DeliverDocumentResult", required = true, nillable = true)
    protected DeliverDocumentResult deliverDocumentResult;

    /**
     * Gets the value of the deliverDocumentResult property.
     * 
     * @return
     *     possible object is
     *     {@link DeliverDocumentResult }
     *     
     */
    public DeliverDocumentResult getDeliverDocumentResult() {
        return deliverDocumentResult;
    }

    /**
     * Sets the value of the deliverDocumentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliverDocumentResult }
     *     
     */
    public void setDeliverDocumentResult(DeliverDocumentResult value) {
        this.deliverDocumentResult = value;
    }

}
