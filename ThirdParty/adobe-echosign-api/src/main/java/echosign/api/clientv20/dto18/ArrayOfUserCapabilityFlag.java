
package echosign.api.clientv20.dto18;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfUserCapabilityFlag complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfUserCapabilityFlag">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserCapabilityFlag" type="{http://dto18.api.echosign}UserCapabilityFlag" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfUserCapabilityFlag", propOrder = {
    "userCapabilityFlag"
})
public class ArrayOfUserCapabilityFlag {

    @XmlElement(name = "UserCapabilityFlag", nillable = true)
    protected List<UserCapabilityFlag> userCapabilityFlag;

    /**
     * Gets the value of the userCapabilityFlag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userCapabilityFlag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserCapabilityFlag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserCapabilityFlag }
     * 
     * 
     */
    public List<UserCapabilityFlag> getUserCapabilityFlag() {
        if (userCapabilityFlag == null) {
            userCapabilityFlag = new ArrayList<UserCapabilityFlag>();
        }
        return this.userCapabilityFlag;
    }

}
