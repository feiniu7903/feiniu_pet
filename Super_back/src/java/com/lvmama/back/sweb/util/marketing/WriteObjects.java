package com.lvmama.back.sweb.util.marketing;
/**
 * WriteObjects.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class WriteObjects  implements java.io.Serializable {
    private CatalogObject[] catalogObjects;

    private boolean allowOverwrite;

    private boolean createIntermediateDirs;

    private ErrorDetailsLevel errorMode;

    private java.lang.String sessionID;

    public WriteObjects() {
    }

    public WriteObjects(
           CatalogObject[] catalogObjects,
           boolean allowOverwrite,
           boolean createIntermediateDirs,
           ErrorDetailsLevel errorMode,
           java.lang.String sessionID) {
           this.catalogObjects = catalogObjects;
           this.allowOverwrite = allowOverwrite;
           this.createIntermediateDirs = createIntermediateDirs;
           this.errorMode = errorMode;
           this.sessionID = sessionID;
    }


    /**
     * Gets the catalogObjects value for this WriteObjects.
     * 
     * @return catalogObjects
     */
    public CatalogObject[] getCatalogObjects() {
        return catalogObjects;
    }


    /**
     * Sets the catalogObjects value for this WriteObjects.
     * 
     * @param catalogObjects
     */
    public void setCatalogObjects(CatalogObject[] catalogObjects) {
        this.catalogObjects = catalogObjects;
    }

    public CatalogObject getCatalogObjects(int i) {
        return this.catalogObjects[i];
    }

    public void setCatalogObjects(int i, CatalogObject _value) {
        this.catalogObjects[i] = _value;
    }


    /**
     * Gets the allowOverwrite value for this WriteObjects.
     * 
     * @return allowOverwrite
     */
    public boolean isAllowOverwrite() {
        return allowOverwrite;
    }


    /**
     * Sets the allowOverwrite value for this WriteObjects.
     * 
     * @param allowOverwrite
     */
    public void setAllowOverwrite(boolean allowOverwrite) {
        this.allowOverwrite = allowOverwrite;
    }


    /**
     * Gets the createIntermediateDirs value for this WriteObjects.
     * 
     * @return createIntermediateDirs
     */
    public boolean isCreateIntermediateDirs() {
        return createIntermediateDirs;
    }


    /**
     * Sets the createIntermediateDirs value for this WriteObjects.
     * 
     * @param createIntermediateDirs
     */
    public void setCreateIntermediateDirs(boolean createIntermediateDirs) {
        this.createIntermediateDirs = createIntermediateDirs;
    }


    /**
     * Gets the errorMode value for this WriteObjects.
     * 
     * @return errorMode
     */
    public ErrorDetailsLevel getErrorMode() {
        return errorMode;
    }


    /**
     * Sets the errorMode value for this WriteObjects.
     * 
     * @param errorMode
     */
    public void setErrorMode(ErrorDetailsLevel errorMode) {
        this.errorMode = errorMode;
    }


    /**
     * Gets the sessionID value for this WriteObjects.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this WriteObjects.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WriteObjects)) return false;
        WriteObjects other = (WriteObjects) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.catalogObjects==null && other.getCatalogObjects()==null) || 
             (this.catalogObjects!=null &&
              java.util.Arrays.equals(this.catalogObjects, other.getCatalogObjects()))) &&
            this.allowOverwrite == other.isAllowOverwrite() &&
            this.createIntermediateDirs == other.isCreateIntermediateDirs() &&
            ((this.errorMode==null && other.getErrorMode()==null) || 
             (this.errorMode!=null &&
              this.errorMode.equals(other.getErrorMode()))) &&
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
        if (getCatalogObjects() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCatalogObjects());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCatalogObjects(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isAllowOverwrite() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isCreateIntermediateDirs() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getErrorMode() != null) {
            _hashCode += getErrorMode().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WriteObjects.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">writeObjects"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("catalogObjects");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "catalogObjects"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "CatalogObject"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowOverwrite");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "allowOverwrite"));
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
        elemField.setFieldName("errorMode");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "errorMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ErrorDetailsLevel"));
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
