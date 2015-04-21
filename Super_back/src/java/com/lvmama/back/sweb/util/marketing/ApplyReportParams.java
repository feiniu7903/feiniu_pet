package com.lvmama.back.sweb.util.marketing;
/**
 * ApplyReportParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ApplyReportParams  implements java.io.Serializable {
    private ReportRef reportRef;

    private ReportParams reportParams;

    private boolean encodeInString;

    private java.lang.String sessionID;

    public ApplyReportParams() {
    }

    public ApplyReportParams(
           ReportRef reportRef,
           ReportParams reportParams,
           boolean encodeInString,
           java.lang.String sessionID) {
           this.reportRef = reportRef;
           this.reportParams = reportParams;
           this.encodeInString = encodeInString;
           this.sessionID = sessionID;
    }


    /**
     * Gets the reportRef value for this ApplyReportParams.
     * 
     * @return reportRef
     */
    public ReportRef getReportRef() {
        return reportRef;
    }


    /**
     * Sets the reportRef value for this ApplyReportParams.
     * 
     * @param reportRef
     */
    public void setReportRef(ReportRef reportRef) {
        this.reportRef = reportRef;
    }


    /**
     * Gets the reportParams value for this ApplyReportParams.
     * 
     * @return reportParams
     */
    public ReportParams getReportParams() {
        return reportParams;
    }


    /**
     * Sets the reportParams value for this ApplyReportParams.
     * 
     * @param reportParams
     */
    public void setReportParams(ReportParams reportParams) {
        this.reportParams = reportParams;
    }


    /**
     * Gets the encodeInString value for this ApplyReportParams.
     * 
     * @return encodeInString
     */
    public boolean isEncodeInString() {
        return encodeInString;
    }


    /**
     * Sets the encodeInString value for this ApplyReportParams.
     * 
     * @param encodeInString
     */
    public void setEncodeInString(boolean encodeInString) {
        this.encodeInString = encodeInString;
    }


    /**
     * Gets the sessionID value for this ApplyReportParams.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this ApplyReportParams.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApplyReportParams)) return false;
        ApplyReportParams other = (ApplyReportParams) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reportRef==null && other.getReportRef()==null) || 
             (this.reportRef!=null &&
              this.reportRef.equals(other.getReportRef()))) &&
            ((this.reportParams==null && other.getReportParams()==null) || 
             (this.reportParams!=null &&
              this.reportParams.equals(other.getReportParams()))) &&
            this.encodeInString == other.isEncodeInString() &&
            ((this.sessionID==null && other.getSessionID()==null) || 
             (this.sessionID!=null &&
              this.sessionID.equals(other.getSessionID())));
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
        if (getReportRef() != null) {
            _hashCode += getReportRef().hashCode();
        }
        if (getReportParams() != null) {
            _hashCode += getReportParams().hashCode();
        }
        _hashCode += (isEncodeInString() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ApplyReportParams.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">applyReportParams"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportRef");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportRef"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportParams"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("encodeInString");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "encodeInString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"));
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
