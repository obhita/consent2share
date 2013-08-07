
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.FormCreationResult;


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
 *         &lt;element name="formCreationResult" type="{http://dto.api.echosign}FormCreationResult"/>
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
    "formCreationResult"
})
@XmlRootElement(name = "createFormResponse")
public class CreateFormResponse {

    @XmlElement(required = true, nillable = true)
    protected FormCreationResult formCreationResult;

    /**
     * Gets the value of the formCreationResult property.
     * 
     * @return
     *     possible object is
     *     {@link FormCreationResult }
     *     
     */
    public FormCreationResult getFormCreationResult() {
        return formCreationResult;
    }

    /**
     * Sets the value of the formCreationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormCreationResult }
     *     
     */
    public void setFormCreationResult(FormCreationResult value) {
        this.formCreationResult = value;
    }

}
