
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto19.EmbeddedViewOptions;
import echosign.api.clientv20.dto19.OnBehalfOfUser;


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
 *         &lt;element name="onBehalfOfUser" type="{http://dto19.api.echosign}OnBehalfOfUser"/>
 *         &lt;element name="embeddedViewOptions" type="{http://dto19.api.echosign}EmbeddedViewOptions"/>
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
    "onBehalfOfUser",
    "embeddedViewOptions"
})
@XmlRootElement(name = "getEmbeddedView")
public class GetEmbeddedView {

    @XmlElement(required = true, nillable = true)
    protected String accessToken;
    @XmlElement(required = true, nillable = true)
    protected OnBehalfOfUser onBehalfOfUser;
    @XmlElement(required = true, nillable = true)
    protected EmbeddedViewOptions embeddedViewOptions;

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
     * Gets the value of the onBehalfOfUser property.
     * 
     * @return
     *     possible object is
     *     {@link OnBehalfOfUser }
     *     
     */
    public OnBehalfOfUser getOnBehalfOfUser() {
        return onBehalfOfUser;
    }

    /**
     * Sets the value of the onBehalfOfUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link OnBehalfOfUser }
     *     
     */
    public void setOnBehalfOfUser(OnBehalfOfUser value) {
        this.onBehalfOfUser = value;
    }

    /**
     * Gets the value of the embeddedViewOptions property.
     * 
     * @return
     *     possible object is
     *     {@link EmbeddedViewOptions }
     *     
     */
    public EmbeddedViewOptions getEmbeddedViewOptions() {
        return embeddedViewOptions;
    }

    /**
     * Sets the value of the embeddedViewOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmbeddedViewOptions }
     *     
     */
    public void setEmbeddedViewOptions(EmbeddedViewOptions value) {
        this.embeddedViewOptions = value;
    }

}
