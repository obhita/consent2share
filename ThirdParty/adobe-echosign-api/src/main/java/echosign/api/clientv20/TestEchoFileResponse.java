
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="outFile" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
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
    "outFile"
})
@XmlRootElement(name = "testEchoFileResponse")
public class TestEchoFileResponse {

    @XmlElement(required = true, nillable = true)
    protected byte[] outFile;

    /**
     * Gets the value of the outFile property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getOutFile() {
        return outFile;
    }

    /**
     * Sets the value of the outFile property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setOutFile(byte[] value) {
        this.outFile = ((byte[]) value);
    }

}
