package com.lvmama.back.sweb.util.marketing;
/**
 * FileInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class FileInfo  implements java.io.Serializable {
    private java.lang.String filepath;

    private java.util.Calendar startTime;

    private java.util.Calendar endTime;

    private long filesize;

    private NameValueArrayPair[] fileAttribs;

    public FileInfo() {
    }

    public FileInfo(
           java.lang.String filepath,
           java.util.Calendar startTime,
           java.util.Calendar endTime,
           long filesize,
           NameValueArrayPair[] fileAttribs) {
           this.filepath = filepath;
           this.startTime = startTime;
           this.endTime = endTime;
           this.filesize = filesize;
           this.fileAttribs = fileAttribs;
    }


    /**
     * Gets the filepath value for this FileInfo.
     * 
     * @return filepath
     */
    public java.lang.String getFilepath() {
        return filepath;
    }


    /**
     * Sets the filepath value for this FileInfo.
     * 
     * @param filepath
     */
    public void setFilepath(java.lang.String filepath) {
        this.filepath = filepath;
    }


    /**
     * Gets the startTime value for this FileInfo.
     * 
     * @return startTime
     */
    public java.util.Calendar getStartTime() {
        return startTime;
    }


    /**
     * Sets the startTime value for this FileInfo.
     * 
     * @param startTime
     */
    public void setStartTime(java.util.Calendar startTime) {
        this.startTime = startTime;
    }


    /**
     * Gets the endTime value for this FileInfo.
     * 
     * @return endTime
     */
    public java.util.Calendar getEndTime() {
        return endTime;
    }


    /**
     * Sets the endTime value for this FileInfo.
     * 
     * @param endTime
     */
    public void setEndTime(java.util.Calendar endTime) {
        this.endTime = endTime;
    }


    /**
     * Gets the filesize value for this FileInfo.
     * 
     * @return filesize
     */
    public long getFilesize() {
        return filesize;
    }


    /**
     * Sets the filesize value for this FileInfo.
     * 
     * @param filesize
     */
    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }


    /**
     * Gets the fileAttribs value for this FileInfo.
     * 
     * @return fileAttribs
     */
    public NameValueArrayPair[] getFileAttribs() {
        return fileAttribs;
    }


    /**
     * Sets the fileAttribs value for this FileInfo.
     * 
     * @param fileAttribs
     */
    public void setFileAttribs(NameValueArrayPair[] fileAttribs) {
        this.fileAttribs = fileAttribs;
    }

    public NameValueArrayPair getFileAttribs(int i) {
        return this.fileAttribs[i];
    }

    public void setFileAttribs(int i, NameValueArrayPair _value) {
        this.fileAttribs[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FileInfo)) return false;
        FileInfo other = (FileInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.filepath==null && other.getFilepath()==null) || 
             (this.filepath!=null &&
              this.filepath.equals(other.getFilepath()))) &&
            ((this.startTime==null && other.getStartTime()==null) || 
             (this.startTime!=null &&
              this.startTime.equals(other.getStartTime()))) &&
            ((this.endTime==null && other.getEndTime()==null) || 
             (this.endTime!=null &&
              this.endTime.equals(other.getEndTime()))) &&
            this.filesize == other.getFilesize() &&
            ((this.fileAttribs==null && other.getFileAttribs()==null) || 
             (this.fileAttribs!=null &&
              java.util.Arrays.equals(this.fileAttribs, other.getFileAttribs())));
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
        if (getFilepath() != null) {
            _hashCode += getFilepath().hashCode();
        }
        if (getStartTime() != null) {
            _hashCode += getStartTime().hashCode();
        }
        if (getEndTime() != null) {
            _hashCode += getEndTime().hashCode();
        }
        _hashCode += new Long(getFilesize()).hashCode();
        if (getFileAttribs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileAttribs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileAttribs(), i);
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
        new org.apache.axis.description.TypeDesc(FileInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "FileInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filepath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "filepath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTime");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "startTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endTime");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "endTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filesize");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "filesize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileAttribs");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "fileAttribs"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "NameValueArrayPair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
