package com.lvmama.back.sweb.util.marketing;
/**
 * ReportHTMLOptions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ReportHTMLOptions  implements java.io.Serializable {
    private boolean enableDelayLoading;

    private java.lang.String linkMode;

    public ReportHTMLOptions() {
    }

    public ReportHTMLOptions(
           boolean enableDelayLoading,
           java.lang.String linkMode) {
           this.enableDelayLoading = enableDelayLoading;
           this.linkMode = linkMode;
    }


    /**
     * Gets the enableDelayLoading value for this ReportHTMLOptions.
     * 
     * @return enableDelayLoading
     */
    public boolean isEnableDelayLoading() {
        return enableDelayLoading;
    }


    /**
     * Sets the enableDelayLoading value for this ReportHTMLOptions.
     * 
     * @param enableDelayLoading
     */
    public void setEnableDelayLoading(boolean enableDelayLoading) {
        this.enableDelayLoading = enableDelayLoading;
    }


    /**
     * Gets the linkMode value for this ReportHTMLOptions.
     * 
     * @return linkMode
     */
    public java.lang.String getLinkMode() {
        return linkMode;
    }


    /**
     * Sets the linkMode value for this ReportHTMLOptions.
     * 
     * @param linkMode
     */
    public void setLinkMode(java.lang.String linkMode) {
        this.linkMode = linkMode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReportHTMLOptions)) return false;
        ReportHTMLOptions other = (ReportHTMLOptions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.enableDelayLoading == other.isEnableDelayLoading() &&
            ((this.linkMode==null && other.getLinkMode()==null) || 
             (this.linkMode!=null &&
              this.linkMode.equals(other.getLinkMode())));
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
        _hashCode += (isEnableDelayLoading() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getLinkMode() != null) {
            _hashCode += getLinkMode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReportHTMLOptions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportHTMLOptions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enableDelayLoading");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "enableDelayLoading"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkMode");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "linkMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
