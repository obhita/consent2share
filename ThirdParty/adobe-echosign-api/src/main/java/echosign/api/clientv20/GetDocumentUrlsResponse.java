
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto14.GetDocumentUrlsResult;


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
 *         &lt;element name="getDocumentUrlsResult" type="{http://dto14.api.echosign}GetDocumentUrlsResult"/>
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
    "getDocumentUrlsResult"
})
@XmlRootElement(name = "getDocumentUrlsResponse")
public class GetDocumentUrlsResponse {

    @XmlElement(required = true, nillable = true)
    protected GetDocumentUrlsResult getDocumentUrlsResult;

    /**
     * Gets the value of the getDocumentUrlsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentUrlsResult }
     *     
     */
    public GetDocumentUrlsResult getGetDocumentUrlsResult() {
        return getDocumentUrlsResult;
    }

    /**
     * Sets the value of the getDocumentUrlsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentUrlsResult }
     *     
     */
    public void setGetDocumentUrlsResult(GetDocumentUrlsResult value) {
        this.getDocumentUrlsResult = value;
    }

}
