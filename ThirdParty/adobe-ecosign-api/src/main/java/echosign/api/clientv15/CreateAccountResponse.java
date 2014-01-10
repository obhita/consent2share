
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto7.CreateAccountResult;


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
 *         &lt;element name="createAccountResult" type="{http://dto7.api.echosign}CreateAccountResult"/>
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
    "createAccountResult"
})
@XmlRootElement(name = "createAccountResponse")
public class CreateAccountResponse {

    @XmlElement(required = true, nillable = true)
    protected CreateAccountResult createAccountResult;

    /**
     * Gets the value of the createAccountResult property.
     * 
     * @return
     *     possible object is
     *     {@link CreateAccountResult }
     *     
     */
    public CreateAccountResult getCreateAccountResult() {
        return createAccountResult;
    }

    /**
     * Sets the value of the createAccountResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateAccountResult }
     *     
     */
    public void setCreateAccountResult(CreateAccountResult value) {
        this.createAccountResult = value;
    }

}
