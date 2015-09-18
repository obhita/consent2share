
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto.DocumentUrlResult;


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
 *         &lt;element name="documentUrlResult" type="{http://dto.api.echosign}DocumentUrlResult"/>
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
    "documentUrlResult"
})
@XmlRootElement(name = "getDocumentUrlByVersionResponse")
public class GetDocumentUrlByVersionResponse {

    @XmlElement(required = true, nillable = true)
    protected DocumentUrlResult documentUrlResult;

    /**
     * Gets the value of the documentUrlResult property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentUrlResult }
     *     
     */
    public DocumentUrlResult getDocumentUrlResult() {
        return documentUrlResult;
    }

    /**
     * Sets the value of the documentUrlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentUrlResult }
     *     
     */
    public void setDocumentUrlResult(DocumentUrlResult value) {
        this.documentUrlResult = value;
    }

}
