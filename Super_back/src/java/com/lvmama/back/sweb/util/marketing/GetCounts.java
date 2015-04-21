package com.lvmama.back.sweb.util.marketing;
/**
 * GetCounts.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class GetCounts  implements java.io.Serializable {
    private java.lang.String segmentPath;

    private java.lang.String treePath;

    private SegmentationOptions segmentationOptions;

    private java.lang.String sessionID;

    public GetCounts() {
    }

    public GetCounts(
           java.lang.String segmentPath,
           java.lang.String treePath,
           SegmentationOptions segmentationOptions,
           java.lang.String sessionID) {
           this.segmentPath = segmentPath;
           this.treePath = treePath;
           this.segmentationOptions = segmentationOptions;
           this.sessionID = sessionID;
    }


    /**
     * Gets the segmentPath value for this GetCounts.
     * 
     * @return segmentPath
     */
    public java.lang.String getSegmentPath() {
        return segmentPath;
    }


    /**
     * Sets the segmentPath value for this GetCounts.
     * 
     * @param segmentPath
     */
    public void setSegmentPath(java.lang.String segmentPath) {
        this.segmentPath = segmentPath;
    }


    /**
     * Gets the treePath value for this GetCounts.
     * 
     * @return treePath
     */
    public java.lang.String getTreePath() {
        return treePath;
    }


    /**
     * Sets the treePath value for this GetCounts.
     * 
     * @param treePath
     */
    public void setTreePath(java.lang.String treePath) {
        this.treePath = treePath;
    }


    /**
     * Gets the segmentationOptions value for this GetCounts.
     * 
     * @return segmentationOptions
     */
    public SegmentationOptions getSegmentationOptions() {
        return segmentationOptions;
    }


    /**
     * Sets the segmentationOptions value for this GetCounts.
     * 
     * @param segmentationOptions
     */
    public void setSegmentationOptions(SegmentationOptions segmentationOptions) {
        this.segmentationOptions = segmentationOptions;
    }


    /**
     * Gets the sessionID value for this GetCounts.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this GetCounts.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCounts)) return false;
        GetCounts other = (GetCounts) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.segmentPath==null && other.getSegmentPath()==null) || 
             (this.segmentPath!=null &&
              this.segmentPath.equals(other.getSegmentPath()))) &&
            ((this.treePath==null && other.getTreePath()==null) || 
             (this.treePath!=null &&
              this.treePath.equals(other.getTreePath()))) &&
            ((this.segmentationOptions==null && other.getSegmentationOptions()==null) || 
             (this.segmentationOptions!=null &&
              this.segmentationOptions.equals(other.getSegmentationOptions()))) &&
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
        if (getSegmentPath() != null) {
            _hashCode += getSegmentPath().hashCode();
        }
        if (getTreePath() != null) {
            _hashCode += getTreePath().hashCode();
        }
        if (getSegmentationOptions() != null) {
            _hashCode += getSegmentationOptions().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCounts.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getCounts"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segmentPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("treePath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treePath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
