
package echosign.api.clientv15.dto13;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfParticipantSecurityOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfParticipantSecurityOption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParticipantSecurityOption" type="{http://dto13.api.echosign}ParticipantSecurityOption" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfParticipantSecurityOption", propOrder = {
    "participantSecurityOption"
})
public class ArrayOfParticipantSecurityOption {

    @XmlElement(name = "ParticipantSecurityOption", nillable = true)
    protected List<ParticipantSecurityOption> participantSecurityOption;

    /**
     * Gets the value of the participantSecurityOption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participantSecurityOption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipantSecurityOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParticipantSecurityOption }
     * 
     * 
     */
    public List<ParticipantSecurityOption> getParticipantSecurityOption() {
        if (participantSecurityOption == null) {
            participantSecurityOption = new ArrayList<ParticipantSecurityOption>();
        }
        return this.participantSecurityOption;
    }

}
