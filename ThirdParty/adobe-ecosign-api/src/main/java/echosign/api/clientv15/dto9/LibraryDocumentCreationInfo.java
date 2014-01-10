
package echosign.api.clientv15.dto9;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.ArrayOfFileInfo;
import echosign.api.clientv15.dto.SignatureFlow;
import echosign.api.clientv15.dto.SignatureType;


/**
 * <p>Java class for LibraryDocumentCreationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LibraryDocumentCreationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileInfos" type="{http://dto.api.echosign}ArrayOfFileInfo" minOccurs="0"/>
 *         &lt;element name="librarySharingMode" type="{http://dto9.api.echosign}LibrarySharingMode" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signatureFlow" type="{http://dto.api.echosign}SignatureFlow" minOccurs="0"/>
 *         &lt;element name="signatureType" type="{http://dto.api.echosign}SignatureType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LibraryDocumentCreationInfo", propOrder = {
    "fileInfos",
    "librarySharingMode",
    "name",
    "signatureFlow",
    "signatureType"
})
public class LibraryDocumentCreationInfo {

    @XmlElement(nillable = true)
    protected ArrayOfFileInfo fileInfos;
    @XmlElement(nillable = true)
    protected LibrarySharingMode librarySharingMode;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected SignatureFlow signatureFlow;
    @XmlElement(nillable = true)
    protected SignatureType signatureType;

    /**
     * Gets the value of the fileInfos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFileInfo }
     *     
     */
    public ArrayOfFileInfo getFileInfos() {
        return fileInfos;
    }

    /**
     * Sets the value of the fileInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFileInfo }
     *     
     */
    public void setFileInfos(ArrayOfFileInfo value) {
        this.fileInfos = value;
    }

    /**
     * Gets the value of the librarySharingMode property.
     * 
     * @return
     *     possible object is
     *     {@link LibrarySharingMode }
     *     
     */
    public LibrarySharingMode getLibrarySharingMode() {
        return librarySharingMode;
    }

    /**
     * Sets the value of the librarySharingMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link LibrarySharingMode }
     *     
     */
    public void setLibrarySharingMode(LibrarySharingMode value) {
        this.librarySharingMode = value;
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
     * Gets the value of the signatureFlow property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureFlow }
     *     
     */
    public SignatureFlow getSignatureFlow() {
        return signatureFlow;
    }

    /**
     * Sets the value of the signatureFlow property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureFlow }
     *     
     */
    public void setSignatureFlow(SignatureFlow value) {
        this.signatureFlow = value;
    }

    /**
     * Gets the value of the signatureType property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignatureType() {
        return signatureType;
    }

    /**
     * Sets the value of the signatureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignatureType(SignatureType value) {
        this.signatureType = value;
    }

}
