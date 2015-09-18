
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import echosign.api.clientv20.dto.DisplayUserInfo;


/**
 * <p>Java class for DocumentListItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentListItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userDocumentStatus" type="{http://dto17.api.echosign}DocumentListItemUserDocumentStatus" minOccurs="0"/>
 *         &lt;element name="documentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latestDocumentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="displayUserInfo" type="{http://dto.api.echosign}DisplayUserInfo" minOccurs="0"/>
 *         &lt;element name="esign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="megaSign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentListItem", propOrder = {
    "userDocumentStatus",
    "documentKey",
    "latestDocumentKey",
    "displayDate",
    "displayUserInfo",
    "esign",
    "megaSign",
    "name"
})
public class DocumentListItem {

    @XmlElement(nillable = true)
    protected DocumentListItemUserDocumentStatus userDocumentStatus;
    @XmlElement(nillable = true)
    protected String documentKey;
    @XmlElement(nillable = true)
    protected String latestDocumentKey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar displayDate;
    @XmlElement(nillable = true)
    protected DisplayUserInfo displayUserInfo;
    protected Boolean esign;
    protected Boolean megaSign;
    @XmlElement(nillable = true)
    protected String name;

    /**
     * Gets the value of the userDocumentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentListItemUserDocumentStatus }
     *     
     */
    public DocumentListItemUserDocumentStatus getUserDocumentStatus() {
        return userDocumentStatus;
    }

    /**
     * Sets the value of the userDocumentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentListItemUserDocumentStatus }
     *     
     */
    public void setUserDocumentStatus(DocumentListItemUserDocumentStatus value) {
        this.userDocumentStatus = value;
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
     * Gets the value of the displayDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDisplayDate() {
        return displayDate;
    }

    /**
     * Sets the value of the displayDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDisplayDate(XMLGregorianCalendar value) {
        this.displayDate = value;
    }

    /**
     * Gets the value of the displayUserInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DisplayUserInfo }
     *     
     */
    public DisplayUserInfo getDisplayUserInfo() {
        return displayUserInfo;
    }

    /**
     * Sets the value of the displayUserInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DisplayUserInfo }
     *     
     */
    public void setDisplayUserInfo(DisplayUserInfo value) {
        this.displayUserInfo = value;
    }

    /**
     * Gets the value of the esign property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsign() {
        return esign;
    }

    /**
     * Sets the value of the esign property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsign(Boolean value) {
        this.esign = value;
    }

    /**
     * Gets the value of the megaSign property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMegaSign() {
        return megaSign;
    }

    /**
     * Sets the value of the megaSign property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMegaSign(Boolean value) {
        this.megaSign = value;
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

}
