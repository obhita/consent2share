
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto15.GetGroupsInAccountResult;


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
 *         &lt;element name="getGroupsInAccountResult" type="{http://dto15.api.echosign}GetGroupsInAccountResult"/>
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
    "getGroupsInAccountResult"
})
@XmlRootElement(name = "getGroupsInAccountResponse")
public class GetGroupsInAccountResponse {

    @XmlElement(required = true, nillable = true)
    protected GetGroupsInAccountResult getGroupsInAccountResult;

    /**
     * Gets the value of the getGroupsInAccountResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetGroupsInAccountResult }
     *     
     */
    public GetGroupsInAccountResult getGetGroupsInAccountResult() {
        return getGroupsInAccountResult;
    }

    /**
     * Sets the value of the getGroupsInAccountResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetGroupsInAccountResult }
     *     
     */
    public void setGetGroupsInAccountResult(GetGroupsInAccountResult value) {
        this.getGroupsInAccountResult = value;
    }

}
