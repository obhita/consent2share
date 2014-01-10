
package gov.samhsa.ds4ppilot.schema.pep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="return" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kekMaskingKey" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="kekEncryptionKey" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="metadata" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "_return",
    "kekMaskingKey",
    "kekEncryptionKey",
    "metadata"
})
@XmlRootElement(name = "RetrieveDocumentSetResponse")
public class RetrieveDocumentSetResponse {

    @XmlElement(name = "return", required = true)
    protected String _return;
    @XmlElement(required = true)
    protected byte[] kekMaskingKey;
    @XmlElement(required = true)
    protected byte[] kekEncryptionKey;
    @XmlElement(required = true)
    protected String metadata;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturn(String value) {
        this._return = value;
    }

    /**
     * Gets the value of the kekMaskingKey property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getKekMaskingKey() {
        return kekMaskingKey;
    }

    /**
     * Sets the value of the kekMaskingKey property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setKekMaskingKey(byte[] value) {
        this.kekMaskingKey = ((byte[]) value);
    }

    /**
     * Gets the value of the kekEncryptionKey property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getKekEncryptionKey() {
        return kekEncryptionKey;
    }

    /**
     * Sets the value of the kekEncryptionKey property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setKekEncryptionKey(byte[] value) {
        this.kekEncryptionKey = ((byte[]) value);
    }

    /**
     * Gets the value of the metadata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetadata(String value) {
        this.metadata = value;
    }

}
