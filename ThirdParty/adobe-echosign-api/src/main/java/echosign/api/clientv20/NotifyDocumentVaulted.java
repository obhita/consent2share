
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.VaultEventInfo;


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
 *         &lt;element name="accessToken" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="documentKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vaultEventInfo" type="{http://dto17.api.echosign}VaultEventInfo"/>
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
    "accessToken",
    "documentKey",
    "vaultEventInfo"
})
@XmlRootElement(name = "notifyDocumentVaulted")
public class NotifyDocumentVaulted {

    @XmlElement(required = true, nillable = true)
    protected String accessToken;
    @XmlElement(required = true, nillable = true)
    protected String documentKey;
    @XmlElement(required = true, nillable = true)
    protected VaultEventInfo vaultEventInfo;

    /**
     * Gets the value of the accessToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the value of the accessToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessToken(String value) {
        this.accessToken = value;
    }

    /**
     * Gets the value of the documentKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentKey() {
        return documentKey;
    }

    /**
     * Sets the value of the documentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentKey(String value) {
        this.documentKey = value;
    }

    /**
     * Gets the value of the vaultEventInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VaultEventInfo }
     *     
     */
    public VaultEventInfo getVaultEventInfo() {
        return vaultEventInfo;
    }

    /**
     * Sets the value of the vaultEventInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaultEventInfo }
     *     
     */
    public void setVaultEventInfo(VaultEventInfo value) {
        this.vaultEventInfo = value;
    }

}
