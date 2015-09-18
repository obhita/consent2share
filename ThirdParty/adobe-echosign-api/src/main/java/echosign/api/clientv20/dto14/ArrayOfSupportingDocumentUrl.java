
package echosign.api.clientv20.dto14;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSupportingDocumentUrl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSupportingDocumentUrl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SupportingDocumentUrl" type="{http://dto14.api.echosign}SupportingDocumentUrl" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSupportingDocumentUrl", propOrder = {
    "supportingDocumentUrl"
})
public class ArrayOfSupportingDocumentUrl {

    @XmlElement(name = "SupportingDocumentUrl", nillable = true)
    protected List<SupportingDocumentUrl> supportingDocumentUrl;

    /**
     * Gets the value of the supportingDocumentUrl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportingDocumentUrl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportingDocumentUrl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupportingDocumentUrl }
     * 
     * 
     */
    public List<SupportingDocumentUrl> getSupportingDocumentUrl() {
        if (supportingDocumentUrl == null) {
            supportingDocumentUrl = new ArrayList<SupportingDocumentUrl>();
        }
        return this.supportingDocumentUrl;
    }

}
