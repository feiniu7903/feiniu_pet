package com.lvmama.back.sweb.util.marketing;
/**
 * GetPromptedColumnsResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class GetPromptedColumnsResult  implements java.io.Serializable {
    private java.lang.String[] columnInfo;

    public GetPromptedColumnsResult() {
    }

    public GetPromptedColumnsResult(
           java.lang.String[] columnInfo) {
           this.columnInfo = columnInfo;
    }


    /**
     * Gets the columnInfo value for this GetPromptedColumnsResult.
     * 
     * @return columnInfo
     */
    public java.lang.String[] getColumnInfo() {
        return columnInfo;
    }


    /**
     * Sets the columnInfo value for this GetPromptedColumnsResult.
     * 
     * @param columnInfo
     */
    public void setColumnInfo(java.lang.String[] columnInfo) {
        this.columnInfo = columnInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetPromptedColumnsResult)) return false;
        GetPromptedColumnsResult other = (GetPromptedColumnsResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.columnInfo==null && other.getColumnInfo()==null) || 
             (this.columnInfo!=null &&
              java.util.Arrays.equals(this.columnInfo, other.getColumnInfo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getColumnInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getColumnInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getColumnInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetPromptedColumnsResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPromptedColumnsResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("columnInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ColumnInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Columns"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
