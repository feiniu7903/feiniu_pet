package com.lvmama.back.sweb.util.marketing;
/**
 * CreateFolder.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class CreateFolder  implements java.io.Serializable {
    private java.lang.String path;

    private boolean createIfNotExists;

    private boolean createIntermediateDirs;

    private java.lang.String sessionID;

    public CreateFolder() {
    }

    public CreateFolder(
           java.lang.String path,
           boolean createIfNotExists,
           boolean createIntermediateDirs,
           java.lang.String sessionID) {
           this.path = path;
           this.createIfNotExists = createIfNotExists;
           this.createIntermediateDirs = createIntermediateDirs;
           this.sessionID = sessionID;
    }


    /**
     * Gets the path value for this CreateFolder.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this CreateFolder.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the createIfNotExists value for this CreateFolder.
     * 
     * @return createIfNotExists
     */
    public boolean isCreateIfNotExists() {
        return createIfNotExists;
    }


    /**
     * Sets the createIfNotExists value for this CreateFolder.
     * 
     * @param createIfNotExists
     */
    public void setCreateIfNotExists(boolean createIfNotExists) {
        this.createIfNotExists = createIfNotExists;
    }


    /**
     * Gets the createIntermediateDirs value for this CreateFolder.
     * 
     * @return createIntermediateDirs
     */
    public boolean isCreateIntermediateDirs() {
        return createIntermediateDirs;
    }


    /**
     * Sets the createIntermediateDirs value for this CreateFolder.
     * 
     * @param createIntermediateDirs
     */
    public void setCreateIntermediateDirs(boolean createIntermediateDirs) {
        this.createIntermediateDirs = createIntermediateDirs;
    }


    /**
     * Gets the sessionID value for this CreateFolder.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this CreateFolder.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateFolder)) return false;
        CreateFolder other = (CreateFolder) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.path==null && other.getPath()==null) || 
             (this.path!=null &&
              this.path.equals(other.getPath()))) &&
            this.createIfNotExists == other.isCreateIfNotExists() &&
            this.createIntermediateDirs == other.isCreateIntermediateDirs() &&
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
        if (getPath() != null) {
            _hashCode += getPath().hashCode();
        }
        _hashCode += (isCreateIfNotExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isCreateIntermediateDirs() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateFolder.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">createFolder"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("path");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "path"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createIfNotExists");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "createIfNotExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createIntermediateDirs");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "createIntermediateDirs"));
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
