
package echosign.api.clientv15.dto14;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDocumentImageUrls complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDocumentImageUrls">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocumentImageUrls" type="{http://dto14.api.echosign}DocumentImageUrls" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDocumentImageUrls", propOrder = {
    "documentImageUrls"
})
public class ArrayOfDocumentImageUrls {

    @XmlElement(name = "DocumentImageUrls", nillable = true)
    protected List<DocumentImageUrls> documentImageUrls;

    /**
     * Gets the value of the documentImageUrls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentImageUrls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentImageUrls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentImageUrls }
     * 
     * 
     */
    public List<DocumentImageUrls> getDocumentImageUrls() {
        if (documentImageUrls == null) {
            documentImageUrls = new ArrayList<DocumentImageUrls>();
        }
        return this.documentImageUrls;
    }

}
