package com.lvmama.back.sweb.util.marketing;
/**
 * UpdateACLMode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class UpdateACLMode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected UpdateACLMode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ReplaceAcl = "ReplaceAcl";
    public static final java.lang.String _ReplaceForSpecifiedAccounts = "ReplaceForSpecifiedAccounts";
    public static final java.lang.String _DeleteAccountsFromAcl = "DeleteAccountsFromAcl";
    public static final UpdateACLMode ReplaceAcl = new UpdateACLMode(_ReplaceAcl);
    public static final UpdateACLMode ReplaceForSpecifiedAccounts = new UpdateACLMode(_ReplaceForSpecifiedAccounts);
    public static final UpdateACLMode DeleteAccountsFromAcl = new UpdateACLMode(_DeleteAccountsFromAcl);
    public java.lang.String getValue() { return _value_;}
    public static UpdateACLMode fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        UpdateACLMode enumeration = (UpdateACLMode)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static UpdateACLMode fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(UpdateACLMode.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "UpdateACLMode"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
