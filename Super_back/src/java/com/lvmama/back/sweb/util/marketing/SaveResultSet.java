package com.lvmama.back.sweb.util.marketing;
/**
 * SaveResultSet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class SaveResultSet  implements java.io.Serializable {
    private java.lang.String segmentPath;

    private TreeNodePath treeNodePath;

    private java.lang.String savedSegmentPath;

    private SegmentationOptions segmentationOptions;

    private java.lang.String SRCustomLabel;

    private java.lang.Boolean appendStaticSegment;

    private java.lang.String sessionID;

    public SaveResultSet() {
    }

    public SaveResultSet(
           java.lang.String segmentPath,
           TreeNodePath treeNodePath,
           java.lang.String savedSegmentPath,
           SegmentationOptions segmentationOptions,
           java.lang.String SRCustomLabel,
           java.lang.Boolean appendStaticSegment,
           java.lang.String sessionID) {
           this.segmentPath = segmentPath;
           this.treeNodePath = treeNodePath;
           this.savedSegmentPath = savedSegmentPath;
           this.segmentationOptions = segmentationOptions;
           this.SRCustomLabel = SRCustomLabel;
           this.appendStaticSegment = appendStaticSegment;
           this.sessionID = sessionID;
    }


    /**
     * Gets the segmentPath value for this SaveResultSet.
     * 
     * @return segmentPath
     */
    public java.lang.String getSegmentPath() {
        return segmentPath;
    }


    /**
     * Sets the segmentPath value for this SaveResultSet.
     * 
     * @param segmentPath
     */
    public void setSegmentPath(java.lang.String segmentPath) {
        this.segmentPath = segmentPath;
    }


    /**
     * Gets the treeNodePath value for this SaveResultSet.
     * 
     * @return treeNodePath
     */
    public TreeNodePath getTreeNodePath() {
        return treeNodePath;
    }


    /**
     * Sets the treeNodePath value for this SaveResultSet.
     * 
     * @param treeNodePath
     */
    public void setTreeNodePath(TreeNodePath treeNodePath) {
        this.treeNodePath = treeNodePath;
    }


    /**
     * Gets the savedSegmentPath value for this SaveResultSet.
     * 
     * @return savedSegmentPath
     */
    public java.lang.String getSavedSegmentPath() {
        return savedSegmentPath;
    }


    /**
     * Sets the savedSegmentPath value for this SaveResultSet.
     * 
     * @param savedSegmentPath
     */
    public void setSavedSegmentPath(java.lang.String savedSegmentPath) {
        this.savedSegmentPath = savedSegmentPath;
    }


    /**
     * Gets the segmentationOptions value for this SaveResultSet.
     * 
     * @return segmentationOptions
     */
    public SegmentationOptions getSegmentationOptions() {
        return segmentationOptions;
    }


    /**
     * Sets the segmentationOptions value for this SaveResultSet.
     * 
     * @param segmentationOptions
     */
    public void setSegmentationOptions(SegmentationOptions segmentationOptions) {
        this.segmentationOptions = segmentationOptions;
    }


    /**
     * Gets the SRCustomLabel value for this SaveResultSet.
     * 
     * @return SRCustomLabel
     */
    public java.lang.String getSRCustomLabel() {
        return SRCustomLabel;
    }


    /**
     * Sets the SRCustomLabel value for this SaveResultSet.
     * 
     * @param SRCustomLabel
     */
    public void setSRCustomLabel(java.lang.String SRCustomLabel) {
        this.SRCustomLabel = SRCustomLabel;
    }


    /**
     * Gets the appendStaticSegment value for this SaveResultSet.
     * 
     * @return appendStaticSegment
     */
    public java.lang.Boolean getAppendStaticSegment() {
        return appendStaticSegment;
    }


    /**
     * Sets the appendStaticSegment value for this SaveResultSet.
     * 
     * @param appendStaticSegment
     */
    public void setAppendStaticSegment(java.lang.Boolean appendStaticSegment) {
        this.appendStaticSegment = appendStaticSegment;
    }


    /**
     * Gets the sessionID value for this SaveResultSet.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this SaveResultSet.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SaveResultSet)) return false;
        SaveResultSet other = (SaveResultSet) obj;
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
            ((this.treeNodePath==null && other.getTreeNodePath()==null) || 
             (this.treeNodePath!=null &&
              this.treeNodePath.equals(other.getTreeNodePath()))) &&
            ((this.savedSegmentPath==null && other.getSavedSegmentPath()==null) || 
             (this.savedSegmentPath!=null &&
              this.savedSegmentPath.equals(other.getSavedSegmentPath()))) &&
            ((this.segmentationOptions==null && other.getSegmentationOptions()==null) || 
             (this.segmentationOptions!=null &&
              this.segmentationOptions.equals(other.getSegmentationOptions()))) &&
            ((this.SRCustomLabel==null && other.getSRCustomLabel()==null) || 
             (this.SRCustomLabel!=null &&
              this.SRCustomLabel.equals(other.getSRCustomLabel()))) &&
            ((this.appendStaticSegment==null && other.getAppendStaticSegment()==null) || 
             (this.appendStaticSegment!=null &&
              this.appendStaticSegment.equals(other.getAppendStaticSegment()))) &&
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
        if (getTreeNodePath() != null) {
            _hashCode += getTreeNodePath().hashCode();
        }
        if (getSavedSegmentPath() != null) {
            _hashCode += getSavedSegmentPath().hashCode();
        }
        if (getSegmentationOptions() != null) {
            _hashCode += getSegmentationOptions().hashCode();
        }
        if (getSRCustomLabel() != null) {
            _hashCode += getSRCustomLabel().hashCode();
        }
        if (getAppendStaticSegment() != null) {
            _hashCode += getAppendStaticSegment().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SaveResultSet.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">saveResultSet"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("savedSegmentPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "savedSegmentPath"));
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
        elemField.setFieldName("SRCustomLabel");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SRCustomLabel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appendStaticSegment");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "appendStaticSegment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
