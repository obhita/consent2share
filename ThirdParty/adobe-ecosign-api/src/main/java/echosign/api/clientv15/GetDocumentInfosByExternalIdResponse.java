
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto15.DocumentInfoList;


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
 *         &lt;element name="documentInfoList" type="{http://dto15.api.echosign}DocumentInfoList"/>
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
    "documentInfoList"
})
@XmlRootElement(name = "getDocumentInfosByExternalIdResponse")
public class GetDocumentInfosByExternalIdResponse {

    @XmlElement(required = true, nillable = true)
    protected DocumentInfoList documentInfoList;

    /**
     * Gets the value of the documentInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentInfoList }
     *     
     */
    public DocumentInfoList getDocumentInfoList() {
        return documentInfoList;
    }

    /**
     * Sets the value of the documentInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentInfoList }
     *     
     */
    public void setDocumentInfoList(DocumentInfoList value) {
        this.documentInfoList = value;
    }

}
