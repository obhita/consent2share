
package echosign.api.clientv20.dto19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSignerFormFieldCondition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignerFormFieldCondition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignerFormFieldCondition" type="{http://dto19.api.echosign}SignerFormFieldCondition" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignerFormFieldCondition", propOrder = {
    "signerFormFieldCondition"
})
public class ArrayOfSignerFormFieldCondition {

    @XmlElement(name = "SignerFormFieldCondition", nillable = true)
    protected List<SignerFormFieldCondition> signerFormFieldCondition;

    /**
     * Gets the value of the signerFormFieldCondition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signerFormFieldCondition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignerFormFieldCondition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignerFormFieldCondition }
     * 
     * 
     */
    public List<SignerFormFieldCondition> getSignerFormFieldCondition() {
        if (signerFormFieldCondition == null) {
            signerFormFieldCondition = new ArrayList<SignerFormFieldCondition>();
        }
        return this.signerFormFieldCondition;
    }

}
