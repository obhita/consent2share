
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.ReplaceSignerOptions;
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
 *         &lt;element name="replaceSignerOptions" type="{http://dto17.api.echosign}ReplaceSignerOptions"/>
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
    "replaceSignerOptions"
})
@XmlRootElement(name = "replaceSigner")
public class ReplaceSigner {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected UserCredentials userCredentials;
    @XmlElement(required = true, nillable = true)
    protected String documentKey;
    @XmlElement(required = true, nillable = true)
    protected ReplaceSignerOptions replaceSignerOptions;

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
     * Gets the value of the replaceSignerOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ReplaceSignerOptions }
     *     
     */
    public ReplaceSignerOptions getReplaceSignerOptions() {
        return replaceSignerOptions;
    }

    /**
     * Sets the value of the replaceSignerOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplaceSignerOptions }
     *     
     */
    public void setReplaceSignerOptions(ReplaceSignerOptions value) {
        this.replaceSignerOptions = value;
    }

}
