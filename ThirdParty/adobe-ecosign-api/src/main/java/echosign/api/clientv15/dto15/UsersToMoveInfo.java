
package echosign.api.clientv15.dto15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.ArrayOfString;


/**
 * <p>Java class for UsersToMoveInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UsersToMoveInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="preserveGroupAdmins" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="userEmails" type="{http://api.echosign}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsersToMoveInfo", propOrder = {
    "preserveGroupAdmins",
    "userEmails"
})
public class UsersToMoveInfo {

    protected Boolean preserveGroupAdmins;
    @XmlElement(nillable = true)
    protected ArrayOfString userEmails;

    /**
     * Gets the value of the preserveGroupAdmins property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreserveGroupAdmins() {
        return preserveGroupAdmins;
    }

    /**
     * Sets the value of the preserveGroupAdmins property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreserveGroupAdmins(Boolean value) {
        this.preserveGroupAdmins = value;
    }

    /**
     * Gets the value of the userEmails property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getUserEmails() {
        return userEmails;
    }

    /**
     * Sets the value of the userEmails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setUserEmails(ArrayOfString value) {
        this.userEmails = value;
    }

}
