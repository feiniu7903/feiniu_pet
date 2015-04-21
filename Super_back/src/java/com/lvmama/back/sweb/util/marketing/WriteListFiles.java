package com.lvmama.back.sweb.util.marketing;
/**
 * WriteListFiles.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class WriteListFiles  implements java.io.Serializable {
    private ReportRef report;

    private ReportParams reportParams;

    private java.lang.String segmentPath;

    private TreeNodePath treeNodePath;

    private SegmentationOptions segmentationOptions;

    private java.lang.String filesystem;

    private java.math.BigInteger timeout;

    private java.lang.String sessionID;

    public WriteListFiles() {
    }

    public WriteListFiles(
           ReportRef report,
           ReportParams reportParams,
           java.lang.String segmentPath,
           TreeNodePath treeNodePath,
           SegmentationOptions segmentationOptions,
           java.lang.String filesystem,
           java.math.BigInteger timeout,
           java.lang.String sessionID) {
           this.report = report;
           this.reportParams = reportParams;
           this.segmentPath = segmentPath;
           this.treeNodePath = treeNodePath;
           this.segmentationOptions = segmentationOptions;
           this.filesystem = filesystem;
           this.timeout = timeout;
           this.sessionID = sessionID;
    }


    /**
     * Gets the report value for this WriteListFiles.
     * 
     * @return report
     */
    public ReportRef getReport() {
        return report;
    }


    /**
     * Sets the report value for this WriteListFiles.
     * 
     * @param report
     */
    public void setReport(ReportRef report) {
        this.report = report;
    }


    /**
     * Gets the reportParams value for this WriteListFiles.
     * 
     * @return reportParams
     */
    public ReportParams getReportParams() {
        return reportParams;
    }


    /**
     * Sets the reportParams value for this WriteListFiles.
     * 
     * @param reportParams
     */
    public void setReportParams(ReportParams reportParams) {
        this.reportParams = reportParams;
    }


    /**
     * Gets the segmentPath value for this WriteListFiles.
     * 
     * @return segmentPath
     */
    public java.lang.String getSegmentPath() {
        return segmentPath;
    }


    /**
     * Sets the segmentPath value for this WriteListFiles.
     * 
     * @param segmentPath
     */
    public void setSegmentPath(java.lang.String segmentPath) {
        this.segmentPath = segmentPath;
    }


    /**
     * Gets the treeNodePath value for this WriteListFiles.
     * 
     * @return treeNodePath
     */
    public TreeNodePath getTreeNodePath() {
        return treeNodePath;
    }


    /**
     * Sets the treeNodePath value for this WriteListFiles.
     * 
     * @param treeNodePath
     */
    public void setTreeNodePath(TreeNodePath treeNodePath) {
        this.treeNodePath = treeNodePath;
    }


    /**
     * Gets the segmentationOptions value for this WriteListFiles.
     * 
     * @return segmentationOptions
     */
    public SegmentationOptions getSegmentationOptions() {
        return segmentationOptions;
    }


    /**
     * Sets the segmentationOptions value for this WriteListFiles.
     * 
     * @param segmentationOptions
     */
    public void setSegmentationOptions(SegmentationOptions segmentationOptions) {
        this.segmentationOptions = segmentationOptions;
    }


    /**
     * Gets the filesystem value for this WriteListFiles.
     * 
     * @return filesystem
     */
    public java.lang.String getFilesystem() {
        return filesystem;
    }


    /**
     * Sets the filesystem value for this WriteListFiles.
     * 
     * @param filesystem
     */
    public void setFilesystem(java.lang.String filesystem) {
        this.filesystem = filesystem;
    }


    /**
     * Gets the timeout value for this WriteListFiles.
     * 
     * @return timeout
     */
    public java.math.BigInteger getTimeout() {
        return timeout;
    }


    /**
     * Sets the timeout value for this WriteListFiles.
     * 
     * @param timeout
     */
    public void setTimeout(java.math.BigInteger timeout) {
        this.timeout = timeout;
    }


    /**
     * Gets the sessionID value for this WriteListFiles.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this WriteListFiles.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WriteListFiles)) return false;
        WriteListFiles other = (WriteListFiles) obj;
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
            ((this.reportParams==null && other.getReportParams()==null) || 
             (this.reportParams!=null &&
              this.reportParams.equals(other.getReportParams()))) &&
            ((this.segmentPath==null && other.getSegmentPath()==null) || 
             (this.segmentPath!=null &&
              this.segmentPath.equals(other.getSegmentPath()))) &&
            ((this.treeNodePath==null && other.getTreeNodePath()==null) || 
             (this.treeNodePath!=null &&
              this.treeNodePath.equals(other.getTreeNodePath()))) &&
            ((this.segmentationOptions==null && other.getSegmentationOptions()==null) || 
             (this.segmentationOptions!=null &&
              this.segmentationOptions.equals(other.getSegmentationOptions()))) &&
            ((this.filesystem==null && other.getFilesystem()==null) || 
             (this.filesystem!=null &&
              this.filesystem.equals(other.getFilesystem()))) &&
            ((this.timeout==null && other.getTimeout()==null) || 
             (this.timeout!=null &&
              this.timeout.equals(other.getTimeout()))) &&
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
        if (getReportParams() != null) {
            _hashCode += getReportParams().hashCode();
        }
        if (getSegmentPath() != null) {
            _hashCode += getSegmentPath().hashCode();
        }
        if (getTreeNodePath() != null) {
            _hashCode += getTreeNodePath().hashCode();
        }
        if (getSegmentationOptions() != null) {
            _hashCode += getSegmentationOptions().hashCode();
        }
        if (getFilesystem() != null) {
            _hashCode += getFilesystem().hashCode();
        }
        if (getTimeout() != null) {
            _hashCode += getTimeout().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WriteListFiles.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">writeListFiles"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("report");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "report"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportParams"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segmentPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("treeNodePath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treeNodePath"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TreeNodePath"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segmentationOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentationOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SegmentationOptions"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filesystem");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "filesystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeout");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "timeout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
