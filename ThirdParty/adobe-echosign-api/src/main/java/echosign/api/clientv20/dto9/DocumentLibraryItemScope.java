
package echosign.api.clientv20.dto9;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentLibraryItemScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DocumentLibraryItemScope">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PERSONAL"/>
 *     &lt;enumeration value="SHARED"/>
 *     &lt;enumeration value="GLOBAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DocumentLibraryItemScope")
@XmlEnum
public enum DocumentLibraryItemScope {

    PERSONAL,
    SHARED,
    GLOBAL;

    public String value() {
        return name();
    }

    public static DocumentLibraryItemScope fromValue(String v) {
        return valueOf(v);
    }

}
