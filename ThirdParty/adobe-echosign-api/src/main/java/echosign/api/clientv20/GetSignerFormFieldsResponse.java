
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto19.GetSignerFormFieldsResult;


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
 *         &lt;element name="getSignerFormFieldsResult" type="{http://dto19.api.echosign}GetSignerFormFieldsResult"/>
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
    "getSignerFormFieldsResult"
})
@XmlRootElement(name = "getSignerFormFieldsResponse")
public class GetSignerFormFieldsResponse {

    @XmlElement(required = true, nillable = true)
    protected GetSignerFormFieldsResult getSignerFormFieldsResult;

    /**
     * Gets the value of the getSignerFormFieldsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetSignerFormFieldsResult }
     *     
     */
    public GetSignerFormFieldsResult getGetSignerFormFieldsResult() {
        return getSignerFormFieldsResult;
    }

    /**
     * Sets the value of the getSignerFormFieldsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSignerFormFieldsResult }
     *     
     */
    public void setGetSignerFormFieldsResult(GetSignerFormFieldsResult value) {
        this.getSignerFormFieldsResult = value;
    }

}
