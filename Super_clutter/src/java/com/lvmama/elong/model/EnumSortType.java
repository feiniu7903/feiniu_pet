//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.21 at 05:21:00 PM CST 
//


package com.lvmama.elong.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnumSortType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnumSortType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="StarRankDesc"/>
 *     &lt;enumeration value="RateAsc"/>
 *     &lt;enumeration value="DistanceAsc"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnumSortType")
@XmlEnum
public enum EnumSortType {

    @XmlEnumValue("Default")
    Default("Default"),
    @XmlEnumValue("StarRankDesc")
    StarRankDesc("StarRankDesc"),
    @XmlEnumValue("StarRankAsc")
    StarRankAsc("StarRankAsc"),
    @XmlEnumValue("RateAsc")
    RateAsc("RateAsc"),
    @XmlEnumValue("RateDesc")
    RateDesc("RateDesc"),
    @XmlEnumValue("DistanceAsc")
    DistanceAsc("DistanceAsc"),
    @XmlEnumValue("DistanceDesc")
    DistanceDesc("DistanceDesc");
    private final String value;

    EnumSortType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumSortType fromValue(String v) {
        for (EnumSortType c: EnumSortType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}