
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto8.GetFormDataResult;


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
 *         &lt;element name="getFormDataResult" type="{http://dto8.api.echosign}GetFormDataResult"/>
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
    "getFormDataResult"
})
@XmlRootElement(name = "getFormDataResponse")
public class GetFormDataResponse {

    @XmlElement(required = true, nillable = true)
    protected GetFormDataResult getFormDataResult;

    /**
     * Gets the value of the getFormDataResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetFormDataResult }
     *     
     */
    public GetFormDataResult getGetFormDataResult() {
        return getFormDataResult;
    }

    /**
     * Sets the value of the getFormDataResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetFormDataResult }
     *     
     */
    public void setGetFormDataResult(GetFormDataResult value) {
        this.getFormDataResult = value;
    }

}
