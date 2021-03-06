//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.05.21 at 09:59:49 PM BST 
//


package mismo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AUS_SpecialBorrowerEmployerRelationshipTypeEnumerated.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AUS_SpecialBorrowerEmployerRelationshipTypeEnumerated">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PropertySeller"/>
 *     &lt;enumeration value="RealEstateBroker"/>
 *     &lt;enumeration value="EmployedByRelative"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AUS_SpecialBorrowerEmployerRelationshipTypeEnumerated")
@XmlEnum
public enum AUSSpecialBorrowerEmployerRelationshipTypeEnumerated {

    @XmlEnumValue("PropertySeller")
    PROPERTY_SELLER("PropertySeller"),
    @XmlEnumValue("RealEstateBroker")
    REAL_ESTATE_BROKER("RealEstateBroker"),
    @XmlEnumValue("EmployedByRelative")
    EMPLOYED_BY_RELATIVE("EmployedByRelative"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    AUSSpecialBorrowerEmployerRelationshipTypeEnumerated(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AUSSpecialBorrowerEmployerRelationshipTypeEnumerated fromValue(String v) {
        for (AUSSpecialBorrowerEmployerRelationshipTypeEnumerated c: AUSSpecialBorrowerEmployerRelationshipTypeEnumerated.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
