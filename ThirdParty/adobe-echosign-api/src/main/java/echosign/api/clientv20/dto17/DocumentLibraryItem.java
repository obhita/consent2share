
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import echosign.api.clientv20.dto9.DocumentLibraryItemScope;


/**
 * <p>Java class for DocumentLibraryItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentLibraryItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="libraryTemplateTypes" type="{http://dto17.api.echosign}ArrayOfLibraryTemplateType" minOccurs="0"/>
 *         &lt;element name="documentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scope" type="{http://dto9.api.echosign}DocumentLibraryItemScope" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentLibraryItem", propOrder = {
    "libraryTemplateTypes",
    "documentKey",
    "modifiedDate",
    "name",
    "scope"
})
public class DocumentLibraryItem {

    @XmlElement(nillable = true)
    protected ArrayOfLibraryTemplateType libraryTemplateTypes;
    @XmlElement(nillable = true)
    protected String documentKey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected DocumentLibraryItemScope scope;

    /**
     * Gets the value of the libraryTemplateTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLibraryTemplateType }
     *     
     */
    public ArrayOfLibraryTemplateType getLibraryTemplateTypes() {
        return libraryTemplateTypes;
    }

    /**
     * Sets the value of the libraryTemplateTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLibraryTemplateType }
     *     
     */
    public void setLibraryTemplateTypes(ArrayOfLibraryTemplateType value) {
        this.libraryTemplateTypes = value;
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
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
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
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentLibraryItemScope }
     *     
     */
    public DocumentLibraryItemScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentLibraryItemScope }
     *     
     */
    public void setScope(DocumentLibraryItemScope value) {
        this.scope = value;
    }

}
