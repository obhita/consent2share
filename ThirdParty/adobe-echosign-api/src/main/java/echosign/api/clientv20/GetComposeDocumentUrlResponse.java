
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.GetComposeDocumentUrlResult;


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
 *         &lt;element name="getComposeDocumentUrlResult" type="{http://dto17.api.echosign}GetComposeDocumentUrlResult"/>
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
    "getComposeDocumentUrlResult"
})
@XmlRootElement(name = "getComposeDocumentUrlResponse")
public class GetComposeDocumentUrlResponse {

    @XmlElement(required = true, nillable = true)
    protected GetComposeDocumentUrlResult getComposeDocumentUrlResult;

    /**
     * Gets the value of the getComposeDocumentUrlResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetComposeDocumentUrlResult }
     *     
     */
    public GetComposeDocumentUrlResult getGetComposeDocumentUrlResult() {
        return getComposeDocumentUrlResult;
    }

    /**
     * Sets the value of the getComposeDocumentUrlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetComposeDocumentUrlResult }
     *     
     */
    public void setGetComposeDocumentUrlResult(GetComposeDocumentUrlResult value) {
        this.getComposeDocumentUrlResult = value;
    }

}
