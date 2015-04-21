package com.lvmama.back.sweb.util.marketing;
/**
 * ItemInfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ItemInfoType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ItemInfoType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Folder = "Folder";
    public static final java.lang.String _Link = "Link";
    public static final java.lang.String _Object = "Object";
    public static final java.lang.String _Missing = "Missing";
    public static final java.lang.String _NoAccess = "NoAccess";
    public static final ItemInfoType Folder = new ItemInfoType(_Folder);
    public static final ItemInfoType Link = new ItemInfoType(_Link);
    public static final ItemInfoType Object = new ItemInfoType(_Object);
    public static final ItemInfoType Missing = new ItemInfoType(_Missing);
    public static final ItemInfoType NoAccess = new ItemInfoType(_NoAccess);
    public java.lang.String getValue() { return _value_;}
    public static ItemInfoType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ItemInfoType enumeration = (ItemInfoType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ItemInfoType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ItemInfoType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ItemInfoType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
