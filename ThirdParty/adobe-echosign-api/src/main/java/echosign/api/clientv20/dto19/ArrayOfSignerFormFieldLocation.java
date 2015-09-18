
package echosign.api.clientv20.dto19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSignerFormFieldLocation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignerFormFieldLocation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignerFormFieldLocation" type="{http://dto19.api.echosign}SignerFormFieldLocation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignerFormFieldLocation", propOrder = {
    "signerFormFieldLocation"
})
public class ArrayOfSignerFormFieldLocation {

    @XmlElement(name = "SignerFormFieldLocation", nillable = true)
    protected List<SignerFormFieldLocation> signerFormFieldLocation;

    /**
     * Gets the value of the signerFormFieldLocation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signerFormFieldLocation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignerFormFieldLocation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignerFormFieldLocation }
     * 
     * 
     */
    public List<SignerFormFieldLocation> getSignerFormFieldLocation() {
        if (signerFormFieldLocation == null) {
            signerFormFieldLocation = new ArrayList<SignerFormFieldLocation>();
        }
        return this.signerFormFieldLocation;
    }

}
