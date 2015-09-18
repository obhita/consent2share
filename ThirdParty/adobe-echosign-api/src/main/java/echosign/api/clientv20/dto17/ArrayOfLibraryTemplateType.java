
package echosign.api.clientv20.dto17;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfLibraryTemplateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfLibraryTemplateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LibraryTemplateType" type="{http://dto17.api.echosign}LibraryTemplateType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfLibraryTemplateType", propOrder = {
    "libraryTemplateType"
})
public class ArrayOfLibraryTemplateType {

    @XmlElement(name = "LibraryTemplateType", nillable = true)
    protected List<LibraryTemplateType> libraryTemplateType;

    /**
     * Gets the value of the libraryTemplateType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the libraryTemplateType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLibraryTemplateType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LibraryTemplateType }
     * 
     * 
     */
    public List<LibraryTemplateType> getLibraryTemplateType() {
        if (libraryTemplateType == null) {
            libraryTemplateType = new ArrayList<LibraryTemplateType>();
        }
        return this.libraryTemplateType;
    }

}
