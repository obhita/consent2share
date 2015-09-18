
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.ComposeDocumentInfo;


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
 *         &lt;element name="composeDocumentInfo" type="{http://dto17.api.echosign}ComposeDocumentInfo"/>
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
    "composeDocumentInfo"
})
@XmlRootElement(name = "getComposeDocumentUrl")
public class GetComposeDocumentUrl {

    @XmlElement(required = true, nillable = true)
    protected String accessToken;
    @XmlElement(required = true, nillable = true)
    protected ComposeDocumentInfo composeDocumentInfo;

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
     * Gets the value of the composeDocumentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ComposeDocumentInfo }
     *     
     */
    public ComposeDocumentInfo getComposeDocumentInfo() {
        return composeDocumentInfo;
    }

    /**
     * Sets the value of the composeDocumentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComposeDocumentInfo }
     *     
     */
    public void setComposeDocumentInfo(ComposeDocumentInfo value) {
        this.composeDocumentInfo = value;
    }

}
