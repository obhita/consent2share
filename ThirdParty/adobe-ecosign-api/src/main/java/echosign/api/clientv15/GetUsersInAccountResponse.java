
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto15.GetUsersInAccountResult;


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
 *         &lt;element name="getUsersInAccountResult" type="{http://dto15.api.echosign}GetUsersInAccountResult"/>
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
    "getUsersInAccountResult"
})
@XmlRootElement(name = "getUsersInAccountResponse")
public class GetUsersInAccountResponse {

    @XmlElement(required = true, nillable = true)
    protected GetUsersInAccountResult getUsersInAccountResult;

    /**
     * Gets the value of the getUsersInAccountResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetUsersInAccountResult }
     *     
     */
    public GetUsersInAccountResult getGetUsersInAccountResult() {
        return getUsersInAccountResult;
    }

    /**
     * Sets the value of the getUsersInAccountResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetUsersInAccountResult }
     *     
     */
    public void setGetUsersInAccountResult(GetUsersInAccountResult value) {
        this.getUsersInAccountResult = value;
    }

}
