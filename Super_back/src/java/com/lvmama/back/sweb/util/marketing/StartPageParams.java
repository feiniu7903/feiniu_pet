package com.lvmama.back.sweb.util.marketing;
/**
 * StartPageParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class StartPageParams  implements java.io.Serializable {
    private java.lang.String idsPrefix;

    private boolean dontUseHttpCookies;

    public StartPageParams() {
    }

    public StartPageParams(
           java.lang.String idsPrefix,
           boolean dontUseHttpCookies) {
           this.idsPrefix = idsPrefix;
           this.dontUseHttpCookies = dontUseHttpCookies;
    }


    /**
     * Gets the idsPrefix value for this StartPageParams.
     * 
     * @return idsPrefix
     */
    public java.lang.String getIdsPrefix() {
        return idsPrefix;
    }


    /**
     * Sets the idsPrefix value for this StartPageParams.
     * 
     * @param idsPrefix
     */
    public void setIdsPrefix(java.lang.String idsPrefix) {
        this.idsPrefix = idsPrefix;
    }


    /**
     * Gets the dontUseHttpCookies value for this StartPageParams.
     * 
     * @return dontUseHttpCookies
     */
    public boolean isDontUseHttpCookies() {
        return dontUseHttpCookies;
    }


    /**
     * Sets the dontUseHttpCookies value for this StartPageParams.
     * 
     * @param dontUseHttpCookies
     */
    public void setDontUseHttpCookies(boolean dontUseHttpCookies) {
        this.dontUseHttpCookies = dontUseHttpCookies;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StartPageParams)) return false;
        StartPageParams other = (StartPageParams) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idsPrefix==null && other.getIdsPrefix()==null) || 
             (this.idsPrefix!=null &&
              this.idsPrefix.equals(other.getIdsPrefix()))) &&
            this.dontUseHttpCookies == other.isDontUseHttpCookies();
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
        if (getIdsPrefix() != null) {
            _hashCode += getIdsPrefix().hashCode();
        }
        _hashCode += (isDontUseHttpCookies() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StartPageParams.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "StartPageParams"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idsPrefix");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "idsPrefix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dontUseHttpCookies");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "dontUseHttpCookies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
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
