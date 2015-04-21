package com.lvmama.back.sweb.util.marketing;
/**
 * AddReportToPage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class AddReportToPage  implements java.io.Serializable {
    private java.lang.String pageID;

    private java.lang.String reportID;

    private ReportRef report;

    private java.lang.String reportViewName;

    private ReportParams reportParams;

    private ReportHTMLOptions options;

    private java.lang.String sessionID;

    public AddReportToPage() {
    }

    public AddReportToPage(
           java.lang.String pageID,
           java.lang.String reportID,
           ReportRef report,
           java.lang.String reportViewName,
           ReportParams reportParams,
           ReportHTMLOptions options,
           java.lang.String sessionID) {
           this.pageID = pageID;
           this.reportID = reportID;
           this.report = report;
           this.reportViewName = reportViewName;
           this.reportParams = reportParams;
           this.options = options;
           this.sessionID = sessionID;
    }


    /**
     * Gets the pageID value for this AddReportToPage.
     * 
     * @return pageID
     */
    public java.lang.String getPageID() {
        return pageID;
    }


    /**
     * Sets the pageID value for this AddReportToPage.
     * 
     * @param pageID
     */
    public void setPageID(java.lang.String pageID) {
        this.pageID = pageID;
    }


    /**
     * Gets the reportID value for this AddReportToPage.
     * 
     * @return reportID
     */
    public java.lang.String getReportID() {
        return reportID;
    }


    /**
     * Sets the reportID value for this AddReportToPage.
     * 
     * @param reportID
     */
    public void setReportID(java.lang.String reportID) {
        this.reportID = reportID;
    }


    /**
     * Gets the report value for this AddReportToPage.
     * 
     * @return report
     */
    public ReportRef getReport() {
        return report;
    }


    /**
     * Sets the report value for this AddReportToPage.
     * 
     * @param report
     */
    public void setReport(ReportRef report) {
        this.report = report;
    }


    /**
     * Gets the reportViewName value for this AddReportToPage.
     * 
     * @return reportViewName
     */
    public java.lang.String getReportViewName() {
        return reportViewName;
    }


    /**
     * Sets the reportViewName value for this AddReportToPage.
     * 
     * @param reportViewName
     */
    public void setReportViewName(java.lang.String reportViewName) {
        this.reportViewName = reportViewName;
    }


    /**
     * Gets the reportParams value for this AddReportToPage.
     * 
     * @return reportParams
     */
    public ReportParams getReportParams() {
        return reportParams;
    }


    /**
     * Sets the reportParams value for this AddReportToPage.
     * 
     * @param reportParams
     */
    public void setReportParams(ReportParams reportParams) {
        this.reportParams = reportParams;
    }


    /**
     * Gets the options value for this AddReportToPage.
     * 
     * @return options
     */
    public ReportHTMLOptions getOptions() {
        return options;
    }


    /**
     * Sets the options value for this AddReportToPage.
     * 
     * @param options
     */
    public void setOptions(ReportHTMLOptions options) {
        this.options = options;
    }


    /**
     * Gets the sessionID value for this AddReportToPage.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this AddReportToPage.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddReportToPage)) return false;
        AddReportToPage other = (AddReportToPage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pageID==null && other.getPageID()==null) || 
             (this.pageID!=null &&
              this.pageID.equals(other.getPageID()))) &&
            ((this.reportID==null && other.getReportID()==null) || 
             (this.reportID!=null &&
              this.reportID.equals(other.getReportID()))) &&
            ((this.report==null && other.getReport()==null) || 
             (this.report!=null &&
              this.report.equals(other.getReport()))) &&
            ((this.reportViewName==null && other.getReportViewName()==null) || 
             (this.reportViewName!=null &&
              this.reportViewName.equals(other.getReportViewName()))) &&
            ((this.reportParams==null && other.getReportParams()==null) || 
             (this.reportParams!=null &&
              this.reportParams.equals(other.getReportParams()))) &&
            ((this.options==null && other.getOptions()==null) || 
             (this.options!=null &&
              this.options.equals(other.getOptions()))) &&
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
        if (getPageID() != null) {
            _hashCode += getPageID().hashCode();
        }
        if (getReportID() != null) {
            _hashCode += getReportID().hashCode();
        }
        if (getReport() != null) {
            _hashCode += getReport().hashCode();
        }
        if (getReportViewName() != null) {
            _hashCode += getReportViewName().hashCode();
        }
        if (getReportParams() != null) {
            _hashCode += getReportParams().hashCode();
        }
        if (getOptions() != null) {
            _hashCode += getOptions().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddReportToPage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">addReportToPage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "pageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("report");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "report"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportRef"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportViewName");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportViewName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportParams"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("options");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "options"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportHTMLOptions"));
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
