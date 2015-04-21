/**
 * GeneralState.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class GeneralState implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected GeneralState(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _已删除 = "已删除";
    public static final java.lang.String _未审核 = "未审核";
    public static final java.lang.String _审核通过 = "审核通过";
    public static final java.lang.String _审核未通过 = "审核未通过";
    public static final GeneralState 已删除 = new GeneralState(_已删除);
    public static final GeneralState 未审核 = new GeneralState(_未审核);
    public static final GeneralState 审核通过 = new GeneralState(_审核通过);
    public static final GeneralState 审核未通过 = new GeneralState(_审核未通过);
    public java.lang.String getValue() { return _value_;}
    public static GeneralState fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        GeneralState enumeration = (GeneralState)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static GeneralState fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(GeneralState.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "GeneralState"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
