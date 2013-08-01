
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.SenderInfo;
import echosign.api.clientv15.dto9.LibraryDocumentCreationInfo;


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
    "libraryDocumentCreationInfo"
})
@XmlRootElement(name = "createLibraryDocument")
public class CreateLibraryDocument {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected SenderInfo senderInfo;
    @XmlElement(required = true, nillable = true)
    protected LibraryDocumentCreationInfo libraryDocumentCreationInfo;

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

}
