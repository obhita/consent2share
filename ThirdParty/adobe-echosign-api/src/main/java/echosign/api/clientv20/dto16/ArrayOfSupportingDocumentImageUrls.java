
package echosign.api.clientv20.dto16;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSupportingDocumentImageUrls complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSupportingDocumentImageUrls">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SupportingDocumentImageUrls" type="{http://dto16.api.echosign}SupportingDocumentImageUrls" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSupportingDocumentImageUrls", propOrder = {
    "supportingDocumentImageUrls"
})
public class ArrayOfSupportingDocumentImageUrls {

    @XmlElement(name = "SupportingDocumentImageUrls", nillable = true)
    protected List<SupportingDocumentImageUrls> supportingDocumentImageUrls;

    /**
     * Gets the value of the supportingDocumentImageUrls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportingDocumentImageUrls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportingDocumentImageUrls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupportingDocumentImageUrls }
     * 
     * 
     */
    public List<SupportingDocumentImageUrls> getSupportingDocumentImageUrls() {
        if (supportingDocumentImageUrls == null) {
            supportingDocumentImageUrls = new ArrayList<SupportingDocumentImageUrls>();
        }
        return this.supportingDocumentImageUrls;
    }

}
