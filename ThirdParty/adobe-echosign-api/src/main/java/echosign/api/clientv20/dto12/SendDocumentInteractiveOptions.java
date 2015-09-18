
package echosign.api.clientv20.dto12;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto20.SendThroughWebOptions;


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
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noChrome" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sendThroughWeb" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sendThroughWebOptions" type="{http://dto20.api.echosign}SendThroughWebOptions" minOccurs="0"/>
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
    "locale",
    "noChrome",
    "sendThroughWeb",
    "sendThroughWebOptions"
})
public class SendDocumentInteractiveOptions {

    protected Boolean authoringRequested;
    protected Boolean autoLoginUser;
    @XmlElement(nillable = true)
    protected String locale;
    protected Boolean noChrome;
    protected Boolean sendThroughWeb;
    @XmlElement(nillable = true)
    protected SendThroughWebOptions sendThroughWebOptions;

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
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
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

    /**
     * Gets the value of the sendThroughWeb property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSendThroughWeb() {
        return sendThroughWeb;
    }

    /**
     * Sets the value of the sendThroughWeb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSendThroughWeb(Boolean value) {
        this.sendThroughWeb = value;
    }

    /**
     * Gets the value of the sendThroughWebOptions property.
     * 
     * @return
     *     possible object is
     *     {@link SendThroughWebOptions }
     *     
     */
    public SendThroughWebOptions getSendThroughWebOptions() {
        return sendThroughWebOptions;
    }

    /**
     * Sets the value of the sendThroughWebOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendThroughWebOptions }
     *     
     */
    public void setSendThroughWebOptions(SendThroughWebOptions value) {
        this.sendThroughWebOptions = value;
    }

}
