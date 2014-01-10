
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto15.RenameGroupResult;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="renameGroupResult" type="{http://dto15.api.echosign}RenameGroupResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "renameGroupResult"
})
@XmlRootElement(name = "renameGroupResponse")
public class RenameGroupResponse {

    @XmlElement(required = true, nillable = true)
    protected RenameGroupResult renameGroupResult;

    /**
     * Gets the value of the renameGroupResult property.
     * 
     * @return
     *     possible object is
     *     {@link RenameGroupResult }
     *     
     */
    public RenameGroupResult getRenameGroupResult() {
        return renameGroupResult;
    }

    /**
     * Sets the value of the renameGroupResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RenameGroupResult }
     *     
     */
    public void setRenameGroupResult(RenameGroupResult value) {
        this.renameGroupResult = value;
    }

}
