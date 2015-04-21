package com.lvmama.back.sweb.util.marketing;
/**
 * DeleteResultSet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class DeleteResultSet  implements java.io.Serializable {
    private java.lang.String targetLevel;

    private java.lang.String[] GUIDs;

    private java.lang.String segmentPath;

    private java.lang.String sessionID;

    public DeleteResultSet() {
    }

    public DeleteResultSet(
           java.lang.String targetLevel,
           java.lang.String[] GUIDs,
           java.lang.String segmentPath,
           java.lang.String sessionID) {
           this.targetLevel = targetLevel;
           this.GUIDs = GUIDs;
           this.segmentPath = segmentPath;
           this.sessionID = sessionID;
    }


    /**
     * Gets the targetLevel value for this DeleteResultSet.
     * 
     * @return targetLevel
     */
    public java.lang.String getTargetLevel() {
        return targetLevel;
    }


    /**
     * Sets the targetLevel value for this DeleteResultSet.
     * 
     * @param targetLevel
     */
    public void setTargetLevel(java.lang.String targetLevel) {
        this.targetLevel = targetLevel;
    }


    /**
     * Gets the GUIDs value for this DeleteResultSet.
     * 
     * @return GUIDs
     */
    public java.lang.String[] getGUIDs() {
        return GUIDs;
    }


    /**
     * Sets the GUIDs value for this DeleteResultSet.
     * 
     * @param GUIDs
     */
    public void setGUIDs(java.lang.String[] GUIDs) {
        this.GUIDs = GUIDs;
    }


    /**
     * Gets the segmentPath value for this DeleteResultSet.
     * 
     * @return segmentPath
     */
    public java.lang.String getSegmentPath() {
        return segmentPath;
    }


    /**
     * Sets the segmentPath value for this DeleteResultSet.
     * 
     * @param segmentPath
     */
    public void setSegmentPath(java.lang.String segmentPath) {
        this.segmentPath = segmentPath;
    }


    /**
     * Gets the sessionID value for this DeleteResultSet.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this DeleteResultSet.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeleteResultSet)) return false;
        DeleteResultSet other = (DeleteResultSet) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.targetLevel==null && other.getTargetLevel()==null) || 
             (this.targetLevel!=null &&
              this.targetLevel.equals(other.getTargetLevel()))) &&
            ((this.GUIDs==null && other.getGUIDs()==null) || 
             (this.GUIDs!=null &&
              java.util.Arrays.equals(this.GUIDs, other.getGUIDs()))) &&
            ((this.segmentPath==null && other.getSegmentPath()==null) || 
             (this.segmentPath!=null &&
              this.segmentPath.equals(other.getSegmentPath()))) &&
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
        if (getTargetLevel() != null) {
            _hashCode += getTargetLevel().hashCode();
        }
        if (getGUIDs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGUIDs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGUIDs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSegmentPath() != null) {
            _hashCode += getSegmentPath().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeleteResultSet.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">deleteResultSet"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "targetLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GUIDs");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GUIDs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GUID"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segmentPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
