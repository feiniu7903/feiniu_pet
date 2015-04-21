package com.lvmama.back.sweb.util.marketing;
/**
 * SADataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class SADataType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SADataType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Invalid = "Invalid";
    public static final java.lang.String _Unknown = "Unknown";
    public static final java.lang.String _VarBinary = "VarBinary";
    public static final java.lang.String _LongVarBinary = "LongVarBinary";
    public static final java.lang.String _Binary = "Binary";
    public static final java.lang.String _Char = "Char";
    public static final java.lang.String _VarChar = "VarChar";
    public static final java.lang.String _LongVarChar = "LongVarChar";
    public static final java.lang.String _Date = "Date";
    public static final java.lang.String _Time = "Time";
    public static final java.lang.String _TimeStamp = "TimeStamp";
    public static final java.lang.String _Numeric = "Numeric";
    public static final java.lang.String _Decimal = "Decimal";
    public static final java.lang.String _Integer = "Integer";
    public static final java.lang.String _SmallInt = "SmallInt";
    public static final java.lang.String _BigInt = "BigInt";
    public static final java.lang.String _TinyInt = "TinyInt";
    public static final java.lang.String _Bit = "Bit";
    public static final java.lang.String _Float = "Float";
    public static final java.lang.String _Real = "Real";
    public static final java.lang.String _Double = "Double";
    public static final java.lang.String _Coordinate = "Coordinate";
    public static final SADataType Invalid = new SADataType(_Invalid);
    public static final SADataType Unknown = new SADataType(_Unknown);
    public static final SADataType VarBinary = new SADataType(_VarBinary);
    public static final SADataType LongVarBinary = new SADataType(_LongVarBinary);
    public static final SADataType Binary = new SADataType(_Binary);
    public static final SADataType Char = new SADataType(_Char);
    public static final SADataType VarChar = new SADataType(_VarChar);
    public static final SADataType LongVarChar = new SADataType(_LongVarChar);
    public static final SADataType Date = new SADataType(_Date);
    public static final SADataType Time = new SADataType(_Time);
    public static final SADataType TimeStamp = new SADataType(_TimeStamp);
    public static final SADataType Numeric = new SADataType(_Numeric);
    public static final SADataType Decimal = new SADataType(_Decimal);
    public static final SADataType Integer = new SADataType(_Integer);
    public static final SADataType SmallInt = new SADataType(_SmallInt);
    public static final SADataType BigInt = new SADataType(_BigInt);
    public static final SADataType TinyInt = new SADataType(_TinyInt);
    public static final SADataType Bit = new SADataType(_Bit);
    public static final SADataType Float = new SADataType(_Float);
    public static final SADataType Real = new SADataType(_Real);
    public static final SADataType Double = new SADataType(_Double);
    public static final SADataType Coordinate = new SADataType(_Coordinate);
    public java.lang.String getValue() { return _value_;}
    public static SADataType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SADataType enumeration = (SADataType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SADataType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SADataType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SADataType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
