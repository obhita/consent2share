
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto.CancelDocumentResult;


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
 *         &lt;element name="cancelDocumentResult" type="{http://dto.api.echosign}CancelDocumentResult"/>
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
    "cancelDocumentResult"
})
@XmlRootElement(name = "cancelDocumentResponse")
public class CancelDocumentResponse {

    @XmlElement(required = true, nillable = true)
    protected CancelDocumentResult cancelDocumentResult;

    /**
     * Gets the value of the cancelDocumentResult property.
     * 
     * @return
     *     possible object is
     *     {@link CancelDocumentResult }
     *     
     */
    public CancelDocumentResult getCancelDocumentResult() {
        return cancelDocumentResult;
    }

    /**
     * Sets the value of the cancelDocumentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CancelDocumentResult }
     *     
     */
    public void setCancelDocumentResult(CancelDocumentResult value) {
        this.cancelDocumentResult = value;
    }

}
