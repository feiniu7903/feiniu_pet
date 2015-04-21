/**
 * RecDegree.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class RecDegree implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RecDegree(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _一 = "一";
    public static final java.lang.String _二 = "二";
    public static final java.lang.String _三 = "三";
    public static final java.lang.String _四 = "四";
    public static final java.lang.String _五 = "五";
    public static final RecDegree 一 = new RecDegree(_一);
    public static final RecDegree 二 = new RecDegree(_二);
    public static final RecDegree 三 = new RecDegree(_三);
    public static final RecDegree 四 = new RecDegree(_四);
    public static final RecDegree 五 = new RecDegree(_五);
    public java.lang.String getValue() { return _value_;}
    public static RecDegree fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RecDegree enumeration = (RecDegree)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RecDegree fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RecDegree.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "RecDegree"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
