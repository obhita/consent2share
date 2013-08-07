
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.RemoveDocumentResult;


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
 *         &lt;element name="removeDocumentResult" type="{http://dto.api.echosign}RemoveDocumentResult"/>
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
    "removeDocumentResult"
})
@XmlRootElement(name = "removeDocumentResponse")
public class RemoveDocumentResponse {

    @XmlElement(required = true, nillable = true)
    protected RemoveDocumentResult removeDocumentResult;

    /**
     * Gets the value of the removeDocumentResult property.
     * 
     * @return
     *     possible object is
     *     {@link RemoveDocumentResult }
     *     
     */
    public RemoveDocumentResult getRemoveDocumentResult() {
        return removeDocumentResult;
    }

    /**
     * Sets the value of the removeDocumentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoveDocumentResult }
     *     
     */
    public void setRemoveDocumentResult(RemoveDocumentResult value) {
        this.removeDocumentResult = value;
    }

}
