
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.DocumentImageList;


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
 *         &lt;element name="documentImageList" type="{http://dto.api.echosign}DocumentImageList"/>
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
    "documentImageList"
})
@XmlRootElement(name = "getImagesByVersionResponse")
public class GetImagesByVersionResponse {

    @XmlElement(required = true, nillable = true)
    protected DocumentImageList documentImageList;

    /**
     * Gets the value of the documentImageList property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentImageList }
     *     
     */
    public DocumentImageList getDocumentImageList() {
        return documentImageList;
    }

    /**
     * Sets the value of the documentImageList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentImageList }
     *     
     */
    public void setDocumentImageList(DocumentImageList value) {
        this.documentImageList = value;
    }

}
