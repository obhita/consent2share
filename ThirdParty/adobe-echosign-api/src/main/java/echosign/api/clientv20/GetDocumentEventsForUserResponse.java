
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto20.GetDocumentEventsForUserResult;


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
 *         &lt;element name="getDocumentEventsForUserResult" type="{http://dto20.api.echosign}GetDocumentEventsForUserResult"/>
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
    "getDocumentEventsForUserResult"
})
@XmlRootElement(name = "getDocumentEventsForUserResponse")
public class GetDocumentEventsForUserResponse {

    @XmlElement(required = true, nillable = true)
    protected GetDocumentEventsForUserResult getDocumentEventsForUserResult;

    /**
     * Gets the value of the getDocumentEventsForUserResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentEventsForUserResult }
     *     
     */
    public GetDocumentEventsForUserResult getGetDocumentEventsForUserResult() {
        return getDocumentEventsForUserResult;
    }

    /**
     * Sets the value of the getDocumentEventsForUserResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentEventsForUserResult }
     *     
     */
    public void setGetDocumentEventsForUserResult(GetDocumentEventsForUserResult value) {
        this.getDocumentEventsForUserResult = value;
    }

}
