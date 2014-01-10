
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.ArrayOfDocumentKey;


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
 *         &lt;element name="documentKeys" type="{http://dto.api.echosign}ArrayOfDocumentKey"/>
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
    "documentKeys"
})
@XmlRootElement(name = "sendDocumentResponse")
public class SendDocumentResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfDocumentKey documentKeys;

    /**
     * Gets the value of the documentKeys property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentKey }
     *     
     */
    public ArrayOfDocumentKey getDocumentKeys() {
        return documentKeys;
    }

    /**
     * Sets the value of the documentKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentKey }
     *     
     */
    public void setDocumentKeys(ArrayOfDocumentKey value) {
        this.documentKeys = value;
    }

}
