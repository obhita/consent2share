
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetSupportingDocumentsOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetSupportingDocumentsOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentFormatRequested" type="{http://dto14.api.echosign}SupportingDocumentContentFormat" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetSupportingDocumentsOptions", propOrder = {
    "documentFormatRequested"
})
public class GetSupportingDocumentsOptions {

    @XmlElement(nillable = true)
    protected SupportingDocumentContentFormat documentFormatRequested;

    /**
     * Gets the value of the documentFormatRequested property.
     * 
     * @return
     *     possible object is
     *     {@link SupportingDocumentContentFormat }
     *     
     */
    public SupportingDocumentContentFormat getDocumentFormatRequested() {
        return documentFormatRequested;
    }

    /**
     * Sets the value of the documentFormatRequested property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupportingDocumentContentFormat }
     *     
     */
    public void setDocumentFormatRequested(SupportingDocumentContentFormat value) {
        this.documentFormatRequested = value;
    }

}
