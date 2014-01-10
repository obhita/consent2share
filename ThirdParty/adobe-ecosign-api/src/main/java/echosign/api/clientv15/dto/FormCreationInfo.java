
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FormCreationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormCreationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://dto.api.echosign}ExternalId" minOccurs="0"/>
 *         &lt;element name="fileInfos" type="{http://dto.api.echosign}ArrayOfFileInfo" minOccurs="0"/>
 *         &lt;element name="formType" type="{http://dto.api.echosign}FormType" minOccurs="0"/>
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
@XmlType(name = "FormCreationInfo", propOrder = {
    "externalId",
    "fileInfos",
    "formType",
    "name"
})
public class FormCreationInfo {

    @XmlElement(nillable = true)
    protected ExternalId externalId;
    @XmlElement(nillable = true)
    protected ArrayOfFileInfo fileInfos;
    @XmlElement(nillable = true)
    protected FormType formType;
    @XmlElement(nillable = true)
    protected String name;

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalId }
     *     
     */
    public ExternalId getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalId }
     *     
     */
    public void setExternalId(ExternalId value) {
        this.externalId = value;
    }

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
     * Gets the value of the formType property.
     * 
     * @return
     *     possible object is
     *     {@link FormType }
     *     
     */
    public FormType getFormType() {
        return formType;
    }

    /**
     * Sets the value of the formType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormType }
     *     
     */
    public void setFormType(FormType value) {
        this.formType = value;
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
