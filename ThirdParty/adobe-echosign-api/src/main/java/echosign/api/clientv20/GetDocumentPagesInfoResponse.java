
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto19.GetDocumentPagesInfoResult;


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
 *         &lt;element name="getDocumentPagesInfoResult" type="{http://dto19.api.echosign}GetDocumentPagesInfoResult"/>
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
    "getDocumentPagesInfoResult"
})
@XmlRootElement(name = "getDocumentPagesInfoResponse")
public class GetDocumentPagesInfoResponse {

    @XmlElement(required = true, nillable = true)
    protected GetDocumentPagesInfoResult getDocumentPagesInfoResult;

    /**
     * Gets the value of the getDocumentPagesInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentPagesInfoResult }
     *     
     */
    public GetDocumentPagesInfoResult getGetDocumentPagesInfoResult() {
        return getDocumentPagesInfoResult;
    }

    /**
     * Sets the value of the getDocumentPagesInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentPagesInfoResult }
     *     
     */
    public void setGetDocumentPagesInfoResult(GetDocumentPagesInfoResult value) {
        this.getDocumentPagesInfoResult = value;
    }

}
