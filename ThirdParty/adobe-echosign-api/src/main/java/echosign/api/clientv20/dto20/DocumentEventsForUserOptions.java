
package echosign.api.clientv20.dto20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DocumentEventsForUserOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentEventsForUserOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="filterEvents" type="{http://dto20.api.echosign}ArrayOfAgreementEventType" minOccurs="0"/>
 *         &lt;element name="onlyShowLatestEvent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentEventsForUserOptions", propOrder = {
    "endDate",
    "filterEvents",
    "onlyShowLatestEvent",
    "startDate"
})
public class DocumentEventsForUserOptions {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(nillable = true)
    protected ArrayOfAgreementEventType filterEvents;
    protected Boolean onlyShowLatestEvent;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the filterEvents property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAgreementEventType }
     *     
     */
    public ArrayOfAgreementEventType getFilterEvents() {
        return filterEvents;
    }

    /**
     * Sets the value of the filterEvents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAgreementEventType }
     *     
     */
    public void setFilterEvents(ArrayOfAgreementEventType value) {
        this.filterEvents = value;
    }

    /**
     * Gets the value of the onlyShowLatestEvent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOnlyShowLatestEvent() {
        return onlyShowLatestEvent;
    }

    /**
     * Sets the value of the onlyShowLatestEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnlyShowLatestEvent(Boolean value) {
        this.onlyShowLatestEvent = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

}
