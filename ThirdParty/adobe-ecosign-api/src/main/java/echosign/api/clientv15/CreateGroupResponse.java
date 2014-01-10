
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto15.CreateGroupResult;


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
 *         &lt;element name="createGroupResult" type="{http://dto15.api.echosign}CreateGroupResult"/>
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
    "createGroupResult"
})
@XmlRootElement(name = "createGroupResponse")
public class CreateGroupResponse {

    @XmlElement(required = true, nillable = true)
    protected CreateGroupResult createGroupResult;

    /**
     * Gets the value of the createGroupResult property.
     * 
     * @return
     *     possible object is
     *     {@link CreateGroupResult }
     *     
     */
    public CreateGroupResult getCreateGroupResult() {
        return createGroupResult;
    }

    /**
     * Sets the value of the createGroupResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateGroupResult }
     *     
     */
    public void setCreateGroupResult(CreateGroupResult value) {
        this.createGroupResult = value;
    }

}
