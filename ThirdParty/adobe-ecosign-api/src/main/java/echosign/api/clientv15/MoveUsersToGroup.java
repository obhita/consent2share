
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto15.UsersToMoveInfo;


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
 *         &lt;element name="apiKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="groupKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="usersToMoveInfo" type="{http://dto15.api.echosign}UsersToMoveInfo"/>
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
    "apiKey",
    "groupKey",
    "usersToMoveInfo"
})
@XmlRootElement(name = "moveUsersToGroup")
public class MoveUsersToGroup {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected String groupKey;
    @XmlElement(required = true, nillable = true)
    protected UsersToMoveInfo usersToMoveInfo;

    /**
     * Gets the value of the apiKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the value of the apiKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiKey(String value) {
        this.apiKey = value;
    }

    /**
     * Gets the value of the groupKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupKey() {
        return groupKey;
    }

    /**
     * Sets the value of the groupKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupKey(String value) {
        this.groupKey = value;
    }

    /**
     * Gets the value of the usersToMoveInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UsersToMoveInfo }
     *     
     */
    public UsersToMoveInfo getUsersToMoveInfo() {
        return usersToMoveInfo;
    }

    /**
     * Sets the value of the usersToMoveInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsersToMoveInfo }
     *     
     */
    public void setUsersToMoveInfo(UsersToMoveInfo value) {
        this.usersToMoveInfo = value;
    }

}
