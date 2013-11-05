
package echosign.api.clientv15.dto9;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfMergeField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMergeField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MergeField" type="{http://dto9.api.echosign}MergeField" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMergeField", propOrder = {
    "mergeField"
})
public class ArrayOfMergeField {

    @XmlElement(name = "MergeField", nillable = true)
    protected List<MergeField> mergeField;

    /**
     * Gets the value of the mergeField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mergeField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMergeField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MergeField }
     * 
     * 
     */
    public List<MergeField> getMergeField() {
        if (mergeField == null) {
            mergeField = new ArrayList<MergeField>();
        }
        return this.mergeField;
    }

}
