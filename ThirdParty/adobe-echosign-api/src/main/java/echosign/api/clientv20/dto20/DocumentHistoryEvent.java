
package echosign.api.clientv20.dto20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import echosign.api.clientv20.dto16.DeviceLocation;


/**
 * <p>Java class for DocumentHistoryEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentHistoryEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://dto20.api.echosign}AgreementEventType" minOccurs="0"/>
 *         &lt;element name="vaultEventId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceLocation" type="{http://dto16.api.echosign}DeviceLocation" minOccurs="0"/>
 *         &lt;element name="documentVersionKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="synchronizationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actingUserIpAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actingUserEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentHistoryEvent", propOrder = {
    "type",
    "vaultEventId",
    "deviceLocation",
    "documentVersionKey",
    "synchronizationKey",
    "actingUserIpAddress",
    "actingUserEmail",
    "comment",
    "participantEmail",
    "date",
    "description"
})
public class DocumentHistoryEvent {

    @XmlElement(nillable = true)
    protected AgreementEventType type;
    @XmlElement(nillable = true)
    protected String vaultEventId;
    @XmlElement(nillable = true)
    protected DeviceLocation deviceLocation;
    @XmlElement(nillable = true)
    protected String documentVersionKey;
    @XmlElement(nillable = true)
    protected String synchronizationKey;
    @XmlElement(nillable = true)
    protected String actingUserIpAddress;
    @XmlElement(nillable = true)
    protected String actingUserEmail;
    @XmlElement(nillable = true)
    protected String comment;
    @XmlElement(nillable = true)
    protected String participantEmail;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(nillable = true)
    protected String description;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link AgreementEventType }
     *     
     */
    public AgreementEventType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgreementEventType }
     *     
     */
    public void setType(AgreementEventType value) {
        this.type = value;
    }

    /**
     * Gets the value of the vaultEventId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaultEventId() {
        return vaultEventId;
    }

    /**
     * Sets the value of the vaultEventId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaultEventId(String value) {
        this.vaultEventId = value;
    }

    /**
     * Gets the value of the deviceLocation property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceLocation }
     *     
     */
    public DeviceLocation getDeviceLocation() {
        return deviceLocation;
    }

    /**
     * Sets the value of the deviceLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceLocation }
     *     
     */
    public void setDeviceLocation(DeviceLocation value) {
        this.deviceLocation = value;
    }

    /**
     * Gets the value of the documentVersionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentVersionKey() {
        return documentVersionKey;
    }

    /**
     * Sets the value of the documentVersionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentVersionKey(String value) {
        this.documentVersionKey = value;
    }

    /**
     * Gets the value of the synchronizationKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynchronizationKey() {
        return synchronizationKey;
    }

    /**
     * Sets the value of the synchronizationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynchronizationKey(String value) {
        this.synchronizationKey = value;
    }

    /**
     * Gets the value of the actingUserIpAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActingUserIpAddress() {
        return actingUserIpAddress;
    }

    /**
     * Sets the value of the actingUserIpAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActingUserIpAddress(String value) {
        this.actingUserIpAddress = value;
    }

    /**
     * Gets the value of the actingUserEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActingUserEmail() {
        return actingUserEmail;
    }

    /**
     * Sets the value of the actingUserEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActingUserEmail(String value) {
        this.actingUserEmail = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
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
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
