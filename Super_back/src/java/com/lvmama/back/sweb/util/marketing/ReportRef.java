package com.lvmama.back.sweb.util.marketing;
/**
 * ReportRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ReportRef  implements java.io.Serializable {
    private java.lang.String reportPath;

    private java.lang.String reportXml;

    public ReportRef() {
    }

    public ReportRef(
           java.lang.String reportPath,
           java.lang.String reportXml) {
           this.reportPath = reportPath;
           this.reportXml = reportXml;
    }


    /**
     * Gets the reportPath value for this ReportRef.
     * 
     * @return reportPath
     */
    public java.lang.String getReportPath() {
        return reportPath;
    }


    /**
     * Sets the reportPath value for this ReportRef.
     * 
     * @param reportPath
     */
    public void setReportPath(java.lang.String reportPath) {
        this.reportPath = reportPath;
    }


    /**
     * Gets the reportXml value for this ReportRef.
     * 
     * @return reportXml
     */
    public java.lang.String getReportXml() {
        return reportXml;
    }


    /**
     * Sets the reportXml value for this ReportRef.
     * 
     * @param reportXml
     */
    public void setReportXml(java.lang.String reportXml) {
        this.reportXml = reportXml;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReportRef)) return false;
        ReportRef other = (ReportRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reportPath==null && other.getReportPath()==null) || 
             (this.reportPath!=null &&
              this.reportPath.equals(other.getReportPath()))) &&
            ((this.reportXml==null && other.getReportXml()==null) || 
             (this.reportXml!=null &&
              this.reportXml.equals(other.getReportXml())));
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
        if (getReportPath() != null) {
            _hashCode += getReportPath().hashCode();
        }
        if (getReportXml() != null) {
            _hashCode += getReportXml().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReportRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportXml");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportXml"));
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
