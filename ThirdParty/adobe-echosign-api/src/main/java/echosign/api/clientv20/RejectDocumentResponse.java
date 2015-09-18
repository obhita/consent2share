
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.RejectDocumentResult;


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
 *         &lt;element name="rejectDocumentResult" type="{http://dto17.api.echosign}RejectDocumentResult"/>
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
    "rejectDocumentResult"
})
@XmlRootElement(name = "rejectDocumentResponse")
public class RejectDocumentResponse {

    @XmlElement(required = true, nillable = true)
    protected RejectDocumentResult rejectDocumentResult;

    /**
     * Gets the value of the rejectDocumentResult property.
     * 
     * @return
     *     possible object is
     *     {@link RejectDocumentResult }
     *     
     */
    public RejectDocumentResult getRejectDocumentResult() {
        return rejectDocumentResult;
    }

    /**
     * Sets the value of the rejectDocumentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RejectDocumentResult }
     *     
     */
    public void setRejectDocumentResult(RejectDocumentResult value) {
        this.rejectDocumentResult = value;
    }

}
