
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto10.GetWidgetsForUserResult;


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
 *         &lt;element name="getWidgetsForUserResult" type="{http://dto10.api.echosign}GetWidgetsForUserResult"/>
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
    "getWidgetsForUserResult"
})
@XmlRootElement(name = "getWidgetsForUserResponse")
public class GetWidgetsForUserResponse {

    @XmlElement(required = true, nillable = true)
    protected GetWidgetsForUserResult getWidgetsForUserResult;

    /**
     * Gets the value of the getWidgetsForUserResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetWidgetsForUserResult }
     *     
     */
    public GetWidgetsForUserResult getGetWidgetsForUserResult() {
        return getWidgetsForUserResult;
    }

    /**
     * Sets the value of the getWidgetsForUserResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetWidgetsForUserResult }
     *     
     */
    public void setGetWidgetsForUserResult(GetWidgetsForUserResult value) {
        this.getWidgetsForUserResult = value;
    }

}
