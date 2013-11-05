
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CallbackInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CallbackInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signedDocumentUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallbackInfo", propOrder = {
    "signedDocumentUrl"
})
public class CallbackInfo {

    @XmlElement(nillable = true)
    protected String signedDocumentUrl;

    /**
     * Gets the value of the signedDocumentUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignedDocumentUrl() {
        return signedDocumentUrl;
    }

    /**
     * Sets the value of the signedDocumentUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignedDocumentUrl(String value) {
        this.signedDocumentUrl = value;
    }

}
