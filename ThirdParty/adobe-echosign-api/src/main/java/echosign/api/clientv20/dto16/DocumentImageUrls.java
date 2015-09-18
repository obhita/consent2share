
package echosign.api.clientv20.dto16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentImageUrls complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentImageUrls">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pages" type="{http://dto16.api.echosign}ArrayOfPageImageUrls" minOccurs="0"/>
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
@XmlType(name = "DocumentImageUrls", propOrder = {
    "pages",
    "name"
})
public class DocumentImageUrls {

    @XmlElement(nillable = true)
    protected ArrayOfPageImageUrls pages;
    @XmlElement(nillable = true)
    protected String name;

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
