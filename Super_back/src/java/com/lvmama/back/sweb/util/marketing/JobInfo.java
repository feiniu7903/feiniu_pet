package com.lvmama.back.sweb.util.marketing;
/**
 * JobInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class JobInfo  implements java.io.Serializable {
    private JobStats jobStats;

    private FileInfo[] fileInfo;

    private JobInfoDetailedInfo detailedInfo;

    public JobInfo() {
    }

    public JobInfo(
           JobStats jobStats,
           FileInfo[] fileInfo,
           JobInfoDetailedInfo detailedInfo) {
           this.jobStats = jobStats;
           this.fileInfo = fileInfo;
           this.detailedInfo = detailedInfo;
    }


    /**
     * Gets the jobStats value for this JobInfo.
     * 
     * @return jobStats
     */
    public JobStats getJobStats() {
        return jobStats;
    }


    /**
     * Sets the jobStats value for this JobInfo.
     * 
     * @param jobStats
     */
    public void setJobStats(JobStats jobStats) {
        this.jobStats = jobStats;
    }


    /**
     * Gets the fileInfo value for this JobInfo.
     * 
     * @return fileInfo
     */
    public FileInfo[] getFileInfo() {
        return fileInfo;
    }


    /**
     * Sets the fileInfo value for this JobInfo.
     * 
     * @param fileInfo
     */
    public void setFileInfo(FileInfo[] fileInfo) {
        this.fileInfo = fileInfo;
    }

    public FileInfo getFileInfo(int i) {
        return this.fileInfo[i];
    }

    public void setFileInfo(int i, FileInfo _value) {
        this.fileInfo[i] = _value;
    }


    /**
     * Gets the detailedInfo value for this JobInfo.
     * 
     * @return detailedInfo
     */
    public JobInfoDetailedInfo getDetailedInfo() {
        return detailedInfo;
    }


    /**
     * Sets the detailedInfo value for this JobInfo.
     * 
     * @param detailedInfo
     */
    public void setDetailedInfo(JobInfoDetailedInfo detailedInfo) {
        this.detailedInfo = detailedInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JobInfo)) return false;
        JobInfo other = (JobInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.jobStats==null && other.getJobStats()==null) || 
             (this.jobStats!=null &&
              this.jobStats.equals(other.getJobStats()))) &&
            ((this.fileInfo==null && other.getFileInfo()==null) || 
             (this.fileInfo!=null &&
              java.util.Arrays.equals(this.fileInfo, other.getFileInfo()))) &&
            ((this.detailedInfo==null && other.getDetailedInfo()==null) || 
             (this.detailedInfo!=null &&
              this.detailedInfo.equals(other.getDetailedInfo())));
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
        if (getJobStats() != null) {
            _hashCode += getJobStats().hashCode();
        }
        if (getFileInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDetailedInfo() != null) {
            _hashCode += getDetailedInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JobInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobStats");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobStats"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobStats"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "fileInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "FileInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailedInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "detailedInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">JobInfo>detailedInfo"));
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
