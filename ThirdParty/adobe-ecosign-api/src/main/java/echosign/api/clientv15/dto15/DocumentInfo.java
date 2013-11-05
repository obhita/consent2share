
package echosign.api.clientv15.dto15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import echosign.api.clientv15.dto13.ArrayOfSecurityOption;
import echosign.api.clientv15.dto14.AgreementStatus;
import echosign.api.clientv15.dto14.ArrayOfParticipantInfo;
import echosign.api.clientv15.dto9.ArrayOfNextParticipantInfo;


/**
 * <p>Java class for DocumentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="events" type="{http://dto15.api.echosign}ArrayOfDocumentHistoryEvent" minOccurs="0"/>
 *         &lt;element name="latestDocumentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expiration" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nextParticipantInfos" type="{http://dto9.api.echosign}ArrayOfNextParticipantInfo" minOccurs="0"/>
 *         &lt;element name="documentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityOptions" type="{http://dto13.api.echosign}ArrayOfSecurityOption" minOccurs="0"/>
 *         &lt;element name="participants" type="{http://dto14.api.echosign}ArrayOfParticipantInfo" minOccurs="0"/>
 *         &lt;element name="status" type="{http://dto14.api.echosign}AgreementStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentInfo", propOrder = {
    "events",
    "latestDocumentKey",
    "expiration",
    "locale",
    "message",
    "name",
    "nextParticipantInfos",
    "documentKey",
    "securityOptions",
    "participants",
    "status"
})
public class DocumentInfo {

    @XmlElement(nillable = true)
    protected ArrayOfDocumentHistoryEvent events;
    @XmlElement(nillable = true)
    protected String latestDocumentKey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiration;
    @XmlElement(nillable = true)
    protected String locale;
    @XmlElement(nillable = true)
    protected String message;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected ArrayOfNextParticipantInfo nextParticipantInfos;
    @XmlElement(nillable = true)
    protected String documentKey;
    @XmlElement(nillable = true)
    protected ArrayOfSecurityOption securityOptions;
    @XmlElement(nillable = true)
    protected ArrayOfParticipantInfo participants;
    @XmlElement(nillable = true)
    protected AgreementStatus status;

    /**
     * Gets the value of the events property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentHistoryEvent }
     *     
     */
    public ArrayOfDocumentHistoryEvent getEvents() {
        return events;
    }

    /**
     * Sets the value of the events property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentHistoryEvent }
     *     
     */
    public void setEvents(ArrayOfDocumentHistoryEvent value) {
        this.events = value;
    }

    /**
     * Gets the value of the latestDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatestDocumentKey() {
        return latestDocumentKey;
    }

    /**
     * Sets the value of the latestDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatestDocumentKey(String value) {
        this.latestDocumentKey = value;
    }

    /**
     * Gets the value of the expiration property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiration() {
        return expiration;
    }

    /**
     * Sets the value of the expiration property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiration(XMLGregorianCalendar value) {
        this.expiration = value;
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
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the nextParticipantInfos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfNextParticipantInfo }
     *     
     */
    public ArrayOfNextParticipantInfo getNextParticipantInfos() {
        return nextParticipantInfos;
    }

    /**
     * Sets the value of the nextParticipantInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfNextParticipantInfo }
     *     
     */
    public void setNextParticipantInfos(ArrayOfNextParticipantInfo value) {
        this.nextParticipantInfos = value;
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
     * Gets the value of the securityOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSecurityOption }
     *     
     */
    public ArrayOfSecurityOption getSecurityOptions() {
        return securityOptions;
    }

    /**
     * Sets the value of the securityOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSecurityOption }
     *     
     */
    public void setSecurityOptions(ArrayOfSecurityOption value) {
        this.securityOptions = value;
    }

    /**
     * Gets the value of the participants property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParticipantInfo }
     *     
     */
    public ArrayOfParticipantInfo getParticipants() {
        return participants;
    }

    /**
     * Sets the value of the participants property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParticipantInfo }
     *     
     */
    public void setParticipants(ArrayOfParticipantInfo value) {
        this.participants = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AgreementStatus }
     *     
     */
    public AgreementStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgreementStatus }
     *     
     */
    public void setStatus(AgreementStatus value) {
        this.status = value;
    }

}
