
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDocumentImageUrlsOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDocumentImageUrlsOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachSupportingDocuments" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="participantEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="versionKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDocumentImageUrlsOptions", propOrder = {
    "attachSupportingDocuments",
    "participantEmail",
    "versionKey"
})
public class GetDocumentImageUrlsOptions {

    protected Boolean attachSupportingDocuments;
    @XmlElement(nillable = true)
    protected String participantEmail;
    @XmlElement(nillable = true)
    protected String versionKey;

    /**
     * Gets the value of the attachSupportingDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAttachSupportingDocuments() {
        return attachSupportingDocuments;
    }

    /**
     * Sets the value of the attachSupportingDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAttachSupportingDocuments(Boolean value) {
        this.attachSupportingDocuments = value;
    }

    /**
     * Gets the value of the participantEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantEmail() {
        return participantEmail;
    }

    /**
     * Sets the value of the participantEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantEmail(String value) {
        this.participantEmail = value;
    }

    /**
     * Gets the value of the versionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionKey() {
        return versionKey;
    }

    /**
     * Sets the value of the versionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionKey(String value) {
        this.versionKey = value;
    }

}
