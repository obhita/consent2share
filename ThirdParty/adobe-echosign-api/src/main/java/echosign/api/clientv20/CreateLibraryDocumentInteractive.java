
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto.SenderInfo;
import echosign.api.clientv20.dto12.SendDocumentInteractiveOptions;
import echosign.api.clientv20.dto9.LibraryDocumentCreationInfo;


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
 *         &lt;element name="libraryDocumentCreationInfo" type="{http://dto9.api.echosign}LibraryDocumentCreationInfo"/>
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
    "libraryDocumentCreationInfo",
    "sendDocumentInteractiveOptions"
})
@XmlRootElement(name = "createLibraryDocumentInteractive")
public class CreateLibraryDocumentInteractive {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected SenderInfo senderInfo;
    @XmlElement(required = true, nillable = true)
    protected LibraryDocumentCreationInfo libraryDocumentCreationInfo;
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
     * Gets the value of the libraryDocumentCreationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link LibraryDocumentCreationInfo }
     *     
     */
    public LibraryDocumentCreationInfo getLibraryDocumentCreationInfo() {
        return libraryDocumentCreationInfo;
    }

    /**
     * Sets the value of the libraryDocumentCreationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LibraryDocumentCreationInfo }
     *     
     */
    public void setLibraryDocumentCreationInfo(LibraryDocumentCreationInfo value) {
        this.libraryDocumentCreationInfo = value;
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
