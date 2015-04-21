package com.lvmama.back.sweb.util.marketing;
/**
 * CopyItem2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class CopyItem2  implements java.io.Serializable {
    private java.lang.String[] path;

    private boolean recursive;

    private boolean permissions;

    private boolean timestamps;

    private boolean useMtom;

    private java.lang.String sessionID;

    public CopyItem2() {
    }

    public CopyItem2(
           java.lang.String[] path,
           boolean recursive,
           boolean permissions,
           boolean timestamps,
           boolean useMtom,
           java.lang.String sessionID) {
           this.path = path;
           this.recursive = recursive;
           this.permissions = permissions;
           this.timestamps = timestamps;
           this.useMtom = useMtom;
           this.sessionID = sessionID;
    }


    /**
     * Gets the path value for this CopyItem2.
     * 
     * @return path
     */
    public java.lang.String[] getPath() {
        return path;
    }


    /**
     * Sets the path value for this CopyItem2.
     * 
     * @param path
     */
    public void setPath(java.lang.String[] path) {
        this.path = path;
    }

    public java.lang.String getPath(int i) {
        return this.path[i];
    }

    public void setPath(int i, java.lang.String _value) {
        this.path[i] = _value;
    }


    /**
     * Gets the recursive value for this CopyItem2.
     * 
     * @return recursive
     */
    public boolean isRecursive() {
        return recursive;
    }


    /**
     * Sets the recursive value for this CopyItem2.
     * 
     * @param recursive
     */
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }


    /**
     * Gets the permissions value for this CopyItem2.
     * 
     * @return permissions
     */
    public boolean isPermissions() {
        return permissions;
    }


    /**
     * Sets the permissions value for this CopyItem2.
     * 
     * @param permissions
     */
    public void setPermissions(boolean permissions) {
        this.permissions = permissions;
    }


    /**
     * Gets the timestamps value for this CopyItem2.
     * 
     * @return timestamps
     */
    public boolean isTimestamps() {
        return timestamps;
    }


    /**
     * Sets the timestamps value for this CopyItem2.
     * 
     * @param timestamps
     */
    public void setTimestamps(boolean timestamps) {
        this.timestamps = timestamps;
    }


    /**
     * Gets the useMtom value for this CopyItem2.
     * 
     * @return useMtom
     */
    public boolean isUseMtom() {
        return useMtom;
    }


    /**
     * Sets the useMtom value for this CopyItem2.
     * 
     * @param useMtom
     */
    public void setUseMtom(boolean useMtom) {
        this.useMtom = useMtom;
    }


    /**
     * Gets the sessionID value for this CopyItem2.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this CopyItem2.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CopyItem2)) return false;
        CopyItem2 other = (CopyItem2) obj;
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
              java.util.Arrays.equals(this.path, other.getPath()))) &&
            this.recursive == other.isRecursive() &&
            this.permissions == other.isPermissions() &&
            this.timestamps == other.isTimestamps() &&
            this.useMtom == other.isUseMtom() &&
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
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPath());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPath(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isRecursive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isPermissions() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isTimestamps() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isUseMtom() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CopyItem2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">copyItem2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("path");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "path"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recursive");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "recursive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permissions");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "permissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamps");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "timestamps"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("useMtom");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "useMtom"));
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
