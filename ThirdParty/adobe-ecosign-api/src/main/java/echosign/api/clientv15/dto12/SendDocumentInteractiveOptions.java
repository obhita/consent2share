
package echosign.api.clientv15.dto12;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendDocumentInteractiveOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SendDocumentInteractiveOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authoringRequested" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="autoLoginUser" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="noChrome" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendDocumentInteractiveOptions", propOrder = {
    "authoringRequested",
    "autoLoginUser",
    "noChrome"
})
public class SendDocumentInteractiveOptions {

    protected Boolean authoringRequested;
    protected Boolean autoLoginUser;
    protected Boolean noChrome;

    /**
     * Gets the value of the authoringRequested property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAuthoringRequested() {
        return authoringRequested;
    }

    /**
     * Sets the value of the authoringRequested property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAuthoringRequested(Boolean value) {
        this.authoringRequested = value;
    }

    /**
     * Gets the value of the autoLoginUser property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutoLoginUser() {
        return autoLoginUser;
    }

    /**
     * Sets the value of the autoLoginUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoLoginUser(Boolean value) {
        this.autoLoginUser = value;
    }

    /**
     * Gets the value of the noChrome property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoChrome() {
        return noChrome;
    }

    /**
     * Sets the value of the noChrome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoChrome(Boolean value) {
        this.noChrome = value;
    }

}
