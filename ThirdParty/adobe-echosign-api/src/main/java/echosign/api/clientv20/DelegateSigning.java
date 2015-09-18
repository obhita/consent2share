
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.DelegateSigningOptions;
import echosign.api.clientv20.dto9.UserCredentials;


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
 *         &lt;element name="apiKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userCredentials" type="{http://dto9.api.echosign}UserCredentials"/>
 *         &lt;element name="documentKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="delegateSigningOptions" type="{http://dto17.api.echosign}DelegateSigningOptions"/>
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
    "apiKey",
    "userCredentials",
    "documentKey",
    "delegateSigningOptions"
})
@XmlRootElement(name = "delegateSigning")
public class DelegateSigning {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected UserCredentials userCredentials;
    @XmlElement(required = true, nillable = true)
    protected String documentKey;
    @XmlElement(required = true, nillable = true)
    protected DelegateSigningOptions delegateSigningOptions;

    /**
     * Gets the value of the apiKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the value of the apiKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiKey(String value) {
        this.apiKey = value;
    }

    /**
     * Gets the value of the userCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link UserCredentials }
     *     
     */
    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    /**
     * Sets the value of the userCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserCredentials }
     *     
     */
    public void setUserCredentials(UserCredentials value) {
        this.userCredentials = value;
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
     * Gets the value of the delegateSigningOptions property.
     * 
     * @return
     *     possible object is
     *     {@link DelegateSigningOptions }
     *     
     */
    public DelegateSigningOptions getDelegateSigningOptions() {
        return delegateSigningOptions;
    }

    /**
     * Sets the value of the delegateSigningOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegateSigningOptions }
     *     
     */
    public void setDelegateSigningOptions(DelegateSigningOptions value) {
        this.delegateSigningOptions = value;
    }

}
