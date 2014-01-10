
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.UserCreationInfo;
import echosign.api.clientv15.dto7.AccountCreationInfo;


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
 *         &lt;element name="apiKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userCreationInfo" type="{http://dto.api.echosign}UserCreationInfo"/>
 *         &lt;element name="accountCreationInfo" type="{http://dto7.api.echosign}AccountCreationInfo"/>
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
    "apiKey",
    "userCreationInfo",
    "accountCreationInfo"
})
@XmlRootElement(name = "createAccount")
public class CreateAccount {

    @XmlElement(required = true, nillable = true)
    protected String apiKey;
    @XmlElement(required = true, nillable = true)
    protected UserCreationInfo userCreationInfo;
    @XmlElement(required = true, nillable = true)
    protected AccountCreationInfo accountCreationInfo;

    /**
     * Gets the value of the apiKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the value of the apiKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiKey(String value) {
        this.apiKey = value;
    }

    /**
     * Gets the value of the userCreationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UserCreationInfo }
     *     
     */
    public UserCreationInfo getUserCreationInfo() {
        return userCreationInfo;
    }

    /**
     * Sets the value of the userCreationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserCreationInfo }
     *     
     */
    public void setUserCreationInfo(UserCreationInfo value) {
        this.userCreationInfo = value;
    }

    /**
     * Gets the value of the accountCreationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AccountCreationInfo }
     *     
     */
    public AccountCreationInfo getAccountCreationInfo() {
        return accountCreationInfo;
    }

    /**
     * Sets the value of the accountCreationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountCreationInfo }
     *     
     */
    public void setAccountCreationInfo(AccountCreationInfo value) {
        this.accountCreationInfo = value;
    }

}
