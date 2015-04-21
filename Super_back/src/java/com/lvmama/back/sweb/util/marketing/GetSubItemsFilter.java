package com.lvmama.back.sweb.util.marketing;
/**
 * GetSubItemsFilter.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class GetSubItemsFilter  implements java.io.Serializable {
    private NameValuePair[] itemInfoFilters;

    private java.lang.String dummy;

    public GetSubItemsFilter() {
    }

    public GetSubItemsFilter(
           NameValuePair[] itemInfoFilters,
           java.lang.String dummy) {
           this.itemInfoFilters = itemInfoFilters;
           this.dummy = dummy;
    }


    /**
     * Gets the itemInfoFilters value for this GetSubItemsFilter.
     * 
     * @return itemInfoFilters
     */
    public NameValuePair[] getItemInfoFilters() {
        return itemInfoFilters;
    }


    /**
     * Sets the itemInfoFilters value for this GetSubItemsFilter.
     * 
     * @param itemInfoFilters
     */
    public void setItemInfoFilters(NameValuePair[] itemInfoFilters) {
        this.itemInfoFilters = itemInfoFilters;
    }

    public NameValuePair getItemInfoFilters(int i) {
        return this.itemInfoFilters[i];
    }

    public void setItemInfoFilters(int i, NameValuePair _value) {
        this.itemInfoFilters[i] = _value;
    }


    /**
     * Gets the dummy value for this GetSubItemsFilter.
     * 
     * @return dummy
     */
    public java.lang.String getDummy() {
        return dummy;
    }


    /**
     * Sets the dummy value for this GetSubItemsFilter.
     * 
     * @param dummy
     */
    public void setDummy(java.lang.String dummy) {
        this.dummy = dummy;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetSubItemsFilter)) return false;
        GetSubItemsFilter other = (GetSubItemsFilter) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.itemInfoFilters==null && other.getItemInfoFilters()==null) || 
             (this.itemInfoFilters!=null &&
              java.util.Arrays.equals(this.itemInfoFilters, other.getItemInfoFilters()))) &&
            ((this.dummy==null && other.getDummy()==null) || 
             (this.dummy!=null &&
              this.dummy.equals(other.getDummy())));
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
        if (getItemInfoFilters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItemInfoFilters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItemInfoFilters(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDummy() != null) {
            _hashCode += getDummy().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSubItemsFilter.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GetSubItemsFilter"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemInfoFilters");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "itemInfoFilters"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dummy");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "dummy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
