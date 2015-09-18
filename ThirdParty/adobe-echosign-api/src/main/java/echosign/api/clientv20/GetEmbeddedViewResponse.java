
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto19.GetEmbeddedViewResult;


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
 *         &lt;element name="getEmbeddedViewResult" type="{http://dto19.api.echosign}GetEmbeddedViewResult"/>
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
    "getEmbeddedViewResult"
})
@XmlRootElement(name = "getEmbeddedViewResponse")
public class GetEmbeddedViewResponse {

    @XmlElement(required = true, nillable = true)
    protected GetEmbeddedViewResult getEmbeddedViewResult;

    /**
     * Gets the value of the getEmbeddedViewResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetEmbeddedViewResult }
     *     
     */
    public GetEmbeddedViewResult getGetEmbeddedViewResult() {
        return getEmbeddedViewResult;
    }

    /**
     * Sets the value of the getEmbeddedViewResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetEmbeddedViewResult }
     *     
     */
    public void setGetEmbeddedViewResult(GetEmbeddedViewResult value) {
        this.getEmbeddedViewResult = value;
    }

}
