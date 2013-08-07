
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.DocumentCreationInfo;
import echosign.api.clientv15.dto.SenderInfo;
import echosign.api.clientv15.dto12.SendDocumentInteractiveOptions;


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
 *         &lt;element name="senderInfo" type="{http://dto.api.echosign}SenderInfo"/>
 *         &lt;element name="documentCreationInfo" type="{http://dto.api.echosign}DocumentCreationInfo"/>
 *         &lt;element name="sendDocumentInteractiveOptions" type="{http://dto12.api.echosign}SendDocumentInteractiveOptions"/>
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
    "senderInfo",
    "documentCreationInfo",
    "sendDocumentInteractiveOptions"
})
@XmlRootElement(name = "sendDocumentInteractive")
public class SendDocumentInteractive {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected SenderInfo senderInfo;
    @XmlElement(required = true, nillable = true)
    protected DocumentCreationInfo documentCreationInfo;
    @XmlElement(required = true, nillable = true)
    protected SendDocumentInteractiveOptions sendDocumentInteractiveOptions;

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
     * Gets the value of the senderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SenderInfo }
     *     
     */
    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    /**
     * Sets the value of the senderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SenderInfo }
     *     
     */
    public void setSenderInfo(SenderInfo value) {
        this.senderInfo = value;
    }

    /**
     * Gets the value of the documentCreationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentCreationInfo }
     *     
     */
    public DocumentCreationInfo getDocumentCreationInfo() {
        return documentCreationInfo;
    }

    /**
     * Sets the value of the documentCreationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentCreationInfo }
     *     
     */
    public void setDocumentCreationInfo(DocumentCreationInfo value) {
        this.documentCreationInfo = value;
    }

    /**
     * Gets the value of the sendDocumentInteractiveOptions property.
     * 
     * @return
     *     possible object is
     *     {@link SendDocumentInteractiveOptions }
     *     
     */
    public SendDocumentInteractiveOptions getSendDocumentInteractiveOptions() {
        return sendDocumentInteractiveOptions;
    }

    /**
     * Sets the value of the sendDocumentInteractiveOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendDocumentInteractiveOptions }
     *     
     */
    public void setSendDocumentInteractiveOptions(SendDocumentInteractiveOptions value) {
        this.sendDocumentInteractiveOptions = value;
    }

}
