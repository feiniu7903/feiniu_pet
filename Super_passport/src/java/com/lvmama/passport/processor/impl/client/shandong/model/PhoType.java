/**
 * PhoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class PhoType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PhoType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _结构示意图 = "结构示意图";
    public static final java.lang.String _地理位置示意图 = "地理位置示意图";
    public static final java.lang.String _代表图片 = "代表图片";
    public static final java.lang.String _DM = "DM";
    public static final java.lang.String _视频 = "视频";
    public static final java.lang.String _音频 = "音频";
    public static final PhoType 结构示意图 = new PhoType(_结构示意图);
    public static final PhoType 地理位置示意图 = new PhoType(_地理位置示意图);
    public static final PhoType 代表图片 = new PhoType(_代表图片);
    public static final PhoType DM = new PhoType(_DM);
    public static final PhoType 视频 = new PhoType(_视频);
    public static final PhoType 音频 = new PhoType(_音频);
    public java.lang.String getValue() { return _value_;}
    public static PhoType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        PhoType enumeration = (PhoType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static PhoType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(PhoType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "PhoType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
