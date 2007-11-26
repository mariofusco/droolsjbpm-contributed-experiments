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
 * <p>Java class for AUS_MortgageScoreTypeEnumerated.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AUS_MortgageScoreTypeEnumerated">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FraudFilterScore"/>
 *     &lt;enumeration value="GE_IQScore"/>
 *     &lt;enumeration value="Other"/>
 *     &lt;enumeration value="PMIAuraAQIScore"/>
 *     &lt;enumeration value="UGIAccuscore"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AUS_MortgageScoreTypeEnumerated")
@XmlEnum
public enum AUSMortgageScoreTypeEnumerated {

    @XmlEnumValue("FraudFilterScore")
    FRAUD_FILTER_SCORE("FraudFilterScore"),
    @XmlEnumValue("GE_IQScore")
    GE_IQ_SCORE("GE_IQScore"),
    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("PMIAuraAQIScore")
    PMI_AURA_AQI_SCORE("PMIAuraAQIScore"),
    @XmlEnumValue("UGIAccuscore")
    UGI_ACCUSCORE("UGIAccuscore");
    private final String value;

    AUSMortgageScoreTypeEnumerated(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AUSMortgageScoreTypeEnumerated fromValue(String v) {
        for (AUSMortgageScoreTypeEnumerated c: AUSMortgageScoreTypeEnumerated.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}