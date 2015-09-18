
package echosign.api.clientv20.dto18;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRecipientSecurityOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRecipientSecurityOption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecipientSecurityOption" type="{http://dto18.api.echosign}RecipientSecurityOption" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRecipientSecurityOption", propOrder = {
    "recipientSecurityOption"
})
public class ArrayOfRecipientSecurityOption {

    @XmlElement(name = "RecipientSecurityOption", nillable = true)
    protected List<RecipientSecurityOption> recipientSecurityOption;

    /**
     * Gets the value of the recipientSecurityOption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recipientSecurityOption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecipientSecurityOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecipientSecurityOption }
     * 
     * 
     */
    public List<RecipientSecurityOption> getRecipientSecurityOption() {
        if (recipientSecurityOption == null) {
            recipientSecurityOption = new ArrayList<RecipientSecurityOption>();
        }
        return this.recipientSecurityOption;
    }

}
