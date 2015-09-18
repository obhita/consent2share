
package echosign.api.clientv20.dto19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSignerFormField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignerFormField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignerFormField" type="{http://dto19.api.echosign}SignerFormField" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignerFormField", propOrder = {
    "signerFormField"
})
public class ArrayOfSignerFormField {

    @XmlElement(name = "SignerFormField", nillable = true)
    protected List<SignerFormField> signerFormField;

    /**
     * Gets the value of the signerFormField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signerFormField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignerFormField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignerFormField }
     * 
     * 
     */
    public List<SignerFormField> getSignerFormField() {
        if (signerFormField == null) {
            signerFormField = new ArrayList<SignerFormField>();
        }
        return this.signerFormField;
    }

}
