
package echosign.api.clientv20.dto16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupportingDocumentImageUrls complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupportingDocumentImageUrls">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pages" type="{http://dto16.api.echosign}ArrayOfPageImageUrls" minOccurs="0"/>
 *         &lt;element name="fieldName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupportingDocumentImageUrls", propOrder = {
    "pages",
    "fieldName"
})
public class SupportingDocumentImageUrls {

    @XmlElement(nillable = true)
    protected ArrayOfPageImageUrls pages;
    @XmlElement(nillable = true)
    protected String fieldName;

    /**
     * Gets the value of the pages property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPageImageUrls }
     *     
     */
    public ArrayOfPageImageUrls getPages() {
        return pages;
    }

    /**
     * Sets the value of the pages property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPageImageUrls }
     *     
     */
    public void setPages(ArrayOfPageImageUrls value) {
        this.pages = value;
    }

    /**
     * Gets the value of the fieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the value of the fieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

}
