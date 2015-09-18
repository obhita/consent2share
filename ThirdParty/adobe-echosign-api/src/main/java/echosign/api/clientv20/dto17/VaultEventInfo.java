
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaultEventInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaultEventInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="vaultEventComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vaultEventId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaultEventInfo", propOrder = {
    "vaultEventComment",
    "vaultEventId"
})
public class VaultEventInfo {

    @XmlElement(nillable = true)
    protected String vaultEventComment;
    @XmlElement(nillable = true)
    protected String vaultEventId;

    /**
     * Gets the value of the vaultEventComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaultEventComment() {
        return vaultEventComment;
    }

    /**
     * Sets the value of the vaultEventComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaultEventComment(String value) {
        this.vaultEventComment = value;
    }

    /**
     * Gets the value of the vaultEventId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaultEventId() {
        return vaultEventId;
    }

    /**
     * Sets the value of the vaultEventId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaultEventId(String value) {
        this.vaultEventId = value;
    }

}
