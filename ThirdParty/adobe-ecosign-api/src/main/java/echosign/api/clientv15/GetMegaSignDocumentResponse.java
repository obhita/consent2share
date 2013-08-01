
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto14.GetMegaSignDocumentResult;


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
 *         &lt;element name="getMegaSignDocumentResult" type="{http://dto14.api.echosign}GetMegaSignDocumentResult"/>
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
    "getMegaSignDocumentResult"
})
@XmlRootElement(name = "getMegaSignDocumentResponse")
public class GetMegaSignDocumentResponse {

    @XmlElement(required = true, nillable = true)
    protected GetMegaSignDocumentResult getMegaSignDocumentResult;

    /**
     * Gets the value of the getMegaSignDocumentResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetMegaSignDocumentResult }
     *     
     */
    public GetMegaSignDocumentResult getGetMegaSignDocumentResult() {
        return getMegaSignDocumentResult;
    }

    /**
     * Sets the value of the getMegaSignDocumentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetMegaSignDocumentResult }
     *     
     */
    public void setGetMegaSignDocumentResult(GetMegaSignDocumentResult value) {
        this.getMegaSignDocumentResult = value;
    }

}
