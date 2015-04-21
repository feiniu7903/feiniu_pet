package com.lvmama.back.sweb.util.marketing;
/**
 * ExecuteXMLQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ExecuteXMLQuery  implements java.io.Serializable {
    private ReportRef report;

    private XMLQueryOutputFormat outputFormat;

    private XMLQueryExecutionOptions executionOptions;

    private ReportParams reportParams;

    private java.lang.String sessionID;

    public ExecuteXMLQuery() {
    }

    public ExecuteXMLQuery(
           ReportRef report,
           XMLQueryOutputFormat outputFormat,
           XMLQueryExecutionOptions executionOptions,
           ReportParams reportParams,
           java.lang.String sessionID) {
           this.report = report;
           this.outputFormat = outputFormat;
           this.executionOptions = executionOptions;
           this.reportParams = reportParams;
           this.sessionID = sessionID;
    }


    /**
     * Gets the report value for this ExecuteXMLQuery.
     * 
     * @return report
     */
    public ReportRef getReport() {
        return report;
    }


    /**
     * Sets the report value for this ExecuteXMLQuery.
     * 
     * @param report
     */
    public void setReport(ReportRef report) {
        this.report = report;
    }


    /**
     * Gets the outputFormat value for this ExecuteXMLQuery.
     * 
     * @return outputFormat
     */
    public XMLQueryOutputFormat getOutputFormat() {
        return outputFormat;
    }


    /**
     * Sets the outputFormat value for this ExecuteXMLQuery.
     * 
     * @param outputFormat
     */
    public void setOutputFormat(XMLQueryOutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }


    /**
     * Gets the executionOptions value for this ExecuteXMLQuery.
     * 
     * @return executionOptions
     */
    public XMLQueryExecutionOptions getExecutionOptions() {
        return executionOptions;
    }


    /**
     * Sets the executionOptions value for this ExecuteXMLQuery.
     * 
     * @param executionOptions
     */
    public void setExecutionOptions(XMLQueryExecutionOptions executionOptions) {
        this.executionOptions = executionOptions;
    }


    /**
     * Gets the reportParams value for this ExecuteXMLQuery.
     * 
     * @return reportParams
     */
    public ReportParams getReportParams() {
        return reportParams;
    }


    /**
     * Sets the reportParams value for this ExecuteXMLQuery.
     * 
     * @param reportParams
     */
    public void setReportParams(ReportParams reportParams) {
        this.reportParams = reportParams;
    }


    /**
     * Gets the sessionID value for this ExecuteXMLQuery.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this ExecuteXMLQuery.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExecuteXMLQuery)) return false;
        ExecuteXMLQuery other = (ExecuteXMLQuery) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.report==null && other.getReport()==null) || 
             (this.report!=null &&
              this.report.equals(other.getReport()))) &&
            ((this.outputFormat==null && other.getOutputFormat()==null) || 
             (this.outputFormat!=null &&
              this.outputFormat.equals(other.getOutputFormat()))) &&
            ((this.executionOptions==null && other.getExecutionOptions()==null) || 
             (this.executionOptions!=null &&
              this.executionOptions.equals(other.getExecutionOptions()))) &&
            ((this.reportParams==null && other.getReportParams()==null) || 
             (this.reportParams!=null &&
              this.reportParams.equals(other.getReportParams()))) &&
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
        if (getReport() != null) {
            _hashCode += getReport().hashCode();
        }
        if (getOutputFormat() != null) {
            _hashCode += getOutputFormat().hashCode();
        }
        if (getExecutionOptions() != null) {
            _hashCode += getExecutionOptions().hashCode();
        }
        if (getReportParams() != null) {
            _hashCode += getReportParams().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExecuteXMLQuery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">executeXMLQuery"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("report");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "report"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportRef"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputFormat");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "outputFormat"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "XMLQueryOutputFormat"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("executionOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "executionOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "XMLQueryExecutionOptions"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportParams"));
        elemField.setNillable(true);
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
