
package echosign.api.clientv15.dto9;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MergeFieldInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MergeFieldInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mergeFields" type="{http://dto9.api.echosign}ArrayOfMergeField" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MergeFieldInfo", propOrder = {
    "mergeFields"
})
public class MergeFieldInfo {

    @XmlElement(nillable = true)
    protected ArrayOfMergeField mergeFields;

    /**
     * Gets the value of the mergeFields property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMergeField }
     *     
     */
    public ArrayOfMergeField getMergeFields() {
        return mergeFields;
    }

    /**
     * Sets the value of the mergeFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMergeField }
     *     
     */
    public void setMergeFields(ArrayOfMergeField value) {
        this.mergeFields = value;
    }

}
