
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.ReplaceSignerResult;


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
 *         &lt;element name="replaceSignerResult" type="{http://dto17.api.echosign}ReplaceSignerResult"/>
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
    "replaceSignerResult"
})
@XmlRootElement(name = "replaceSignerResponse")
public class ReplaceSignerResponse {

    @XmlElement(required = true, nillable = true)
    protected ReplaceSignerResult replaceSignerResult;

    /**
     * Gets the value of the replaceSignerResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReplaceSignerResult }
     *     
     */
    public ReplaceSignerResult getReplaceSignerResult() {
        return replaceSignerResult;
    }

    /**
     * Sets the value of the replaceSignerResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplaceSignerResult }
     *     
     */
    public void setReplaceSignerResult(ReplaceSignerResult value) {
        this.replaceSignerResult = value;
    }

}
