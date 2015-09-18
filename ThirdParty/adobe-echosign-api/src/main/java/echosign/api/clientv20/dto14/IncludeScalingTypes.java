
package echosign.api.clientv20.dto14;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IncludeScalingTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IncludeScalingTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="FIXED_WIDTH"/>
 *     &lt;enumeration value="PERCENT_ZOOM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IncludeScalingTypes")
@XmlEnum
public enum IncludeScalingTypes {

    ALL,
    FIXED_WIDTH,
    PERCENT_ZOOM;

    public String value() {
        return name();
    }

    public static IncludeScalingTypes fromValue(String v) {
        return valueOf(v);
    }

}
