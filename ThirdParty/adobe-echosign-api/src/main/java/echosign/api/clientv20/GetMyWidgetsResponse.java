
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto16.GetWidgetsForUserResult;


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
 *         &lt;element name="getMyWidgetsResult" type="{http://dto16.api.echosign}GetWidgetsForUserResult"/>
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
    "getMyWidgetsResult"
})
@XmlRootElement(name = "getMyWidgetsResponse")
public class GetMyWidgetsResponse {

    @XmlElement(required = true, nillable = true)
    protected GetWidgetsForUserResult getMyWidgetsResult;

    /**
     * Gets the value of the getMyWidgetsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetWidgetsForUserResult }
     *     
     */
    public GetWidgetsForUserResult getGetMyWidgetsResult() {
        return getMyWidgetsResult;
    }

    /**
     * Sets the value of the getMyWidgetsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetWidgetsForUserResult }
     *     
     */
    public void setGetMyWidgetsResult(GetWidgetsForUserResult value) {
        this.getMyWidgetsResult = value;
    }

}
