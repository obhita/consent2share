
package echosign.api.clientv20.dto20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FileUploadOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FileUploadOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="libraryDocument" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="localFile" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="webConnectors" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileUploadOptions", propOrder = {
    "libraryDocument",
    "localFile",
    "webConnectors"
})
public class FileUploadOptions {

    protected Boolean libraryDocument;
    protected Boolean localFile;
    protected Boolean webConnectors;

    /**
     * Gets the value of the libraryDocument property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLibraryDocument() {
        return libraryDocument;
    }

    /**
     * Sets the value of the libraryDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLibraryDocument(Boolean value) {
        this.libraryDocument = value;
    }

    /**
     * Gets the value of the localFile property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLocalFile() {
        return localFile;
    }

    /**
     * Sets the value of the localFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLocalFile(Boolean value) {
        this.localFile = value;
    }

    /**
     * Gets the value of the webConnectors property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWebConnectors() {
        return webConnectors;
    }

    /**
     * Sets the value of the webConnectors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWebConnectors(Boolean value) {
        this.webConnectors = value;
    }

}
