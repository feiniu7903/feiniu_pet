package com.lvmama.back.sweb.util.marketing;
/**
 * ExportFlags.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ExportFlags implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ExportFlags(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _processAll_ForMerge = "processAll_ForMerge";
    public static final java.lang.String _processAll_ForReplace = "processAll_ForReplace";
    public static final java.lang.String _processAll_ForWriteIfNotExists = "processAll_ForWriteIfNotExists";
    public static final java.lang.String _processLocalChanges = "processLocalChanges";
    public static final java.lang.String _processRemoteChanges = "processRemoteChanges";
    public static final java.lang.String _processAllChanges = "processAllChanges";
    public static final ExportFlags processAll_ForMerge = new ExportFlags(_processAll_ForMerge);
    public static final ExportFlags processAll_ForReplace = new ExportFlags(_processAll_ForReplace);
    public static final ExportFlags processAll_ForWriteIfNotExists = new ExportFlags(_processAll_ForWriteIfNotExists);
    public static final ExportFlags processLocalChanges = new ExportFlags(_processLocalChanges);
    public static final ExportFlags processRemoteChanges = new ExportFlags(_processRemoteChanges);
    public static final ExportFlags processAllChanges = new ExportFlags(_processAllChanges);
    public java.lang.String getValue() { return _value_;}
    public static ExportFlags fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ExportFlags enumeration = (ExportFlags)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ExportFlags fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ExportFlags.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ExportFlags"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
