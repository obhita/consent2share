
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto16.LibraryDocumentCreationResult;


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
 *         &lt;element name="libraryDocumentCreationResult" type="{http://dto16.api.echosign}LibraryDocumentCreationResult"/>
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
    "libraryDocumentCreationResult"
})
@XmlRootElement(name = "createLibraryDocumentResponse")
public class CreateLibraryDocumentResponse {

    @XmlElement(required = true, nillable = true)
    protected LibraryDocumentCreationResult libraryDocumentCreationResult;

    /**
     * Gets the value of the libraryDocumentCreationResult property.
     * 
     * @return
     *     possible object is
     *     {@link LibraryDocumentCreationResult }
     *     
     */
    public LibraryDocumentCreationResult getLibraryDocumentCreationResult() {
        return libraryDocumentCreationResult;
    }

    /**
     * Sets the value of the libraryDocumentCreationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link LibraryDocumentCreationResult }
     *     
     */
    public void setLibraryDocumentCreationResult(LibraryDocumentCreationResult value) {
        this.libraryDocumentCreationResult = value;
    }

}
