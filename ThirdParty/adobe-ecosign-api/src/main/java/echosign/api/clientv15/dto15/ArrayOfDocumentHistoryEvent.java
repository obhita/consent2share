
package echosign.api.clientv15.dto15;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDocumentHistoryEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDocumentHistoryEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocumentHistoryEvent" type="{http://dto15.api.echosign}DocumentHistoryEvent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDocumentHistoryEvent", propOrder = {
    "documentHistoryEvent"
})
public class ArrayOfDocumentHistoryEvent {

    @XmlElement(name = "DocumentHistoryEvent", nillable = true)
    protected List<DocumentHistoryEvent> documentHistoryEvent;

    /**
     * Gets the value of the documentHistoryEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentHistoryEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentHistoryEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentHistoryEvent }
     * 
     * 
     */
    public List<DocumentHistoryEvent> getDocumentHistoryEvent() {
        if (documentHistoryEvent == null) {
            documentHistoryEvent = new ArrayList<DocumentHistoryEvent>();
        }
        return this.documentHistoryEvent;
    }

}
