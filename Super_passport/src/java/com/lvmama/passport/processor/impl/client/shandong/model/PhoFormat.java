/**
 * PhoFormat.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class PhoFormat implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PhoFormat(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _图片 = "图片";
    public static final java.lang.String _视频 = "视频";
    public static final java.lang.String _flash = "flash";
    public static final java.lang.String _DM = "DM";
    public static final java.lang.String _示意图 = "示意图";
    public static final java.lang.String _音频 = "音频";
    public static final PhoFormat 图片 = new PhoFormat(_图片);
    public static final PhoFormat 视频 = new PhoFormat(_视频);
    public static final PhoFormat flash = new PhoFormat(_flash);
    public static final PhoFormat DM = new PhoFormat(_DM);
    public static final PhoFormat 示意图 = new PhoFormat(_示意图);
    public static final PhoFormat 音频 = new PhoFormat(_音频);
    public java.lang.String getValue() { return _value_;}
    public static PhoFormat fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        PhoFormat enumeration = (PhoFormat)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static PhoFormat fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(PhoFormat.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "PhoFormat"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
