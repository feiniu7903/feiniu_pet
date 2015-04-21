package com.lvmama.back.sweb.util.marketing;
/**
 * ReadObjects.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ReadObjects  implements java.io.Serializable {
    private java.lang.String[] paths;

    private boolean resolveLinks;

    private ErrorDetailsLevel errorMode;

    private ReadObjectsReturnOptions returnOptions;

    private java.lang.String sessionID;

    public ReadObjects() {
    }

    public ReadObjects(
           java.lang.String[] paths,
           boolean resolveLinks,
           ErrorDetailsLevel errorMode,
           ReadObjectsReturnOptions returnOptions,
           java.lang.String sessionID) {
           this.paths = paths;
           this.resolveLinks = resolveLinks;
           this.errorMode = errorMode;
           this.returnOptions = returnOptions;
           this.sessionID = sessionID;
    }


    /**
     * Gets the paths value for this ReadObjects.
     * 
     * @return paths
     */
    public java.lang.String[] getPaths() {
        return paths;
    }


    /**
     * Sets the paths value for this ReadObjects.
     * 
     * @param paths
     */
    public void setPaths(java.lang.String[] paths) {
        this.paths = paths;
    }

    public java.lang.String getPaths(int i) {
        return this.paths[i];
    }

    public void setPaths(int i, java.lang.String _value) {
        this.paths[i] = _value;
    }


    /**
     * Gets the resolveLinks value for this ReadObjects.
     * 
     * @return resolveLinks
     */
    public boolean isResolveLinks() {
        return resolveLinks;
    }


    /**
     * Sets the resolveLinks value for this ReadObjects.
     * 
     * @param resolveLinks
     */
    public void setResolveLinks(boolean resolveLinks) {
        this.resolveLinks = resolveLinks;
    }


    /**
     * Gets the errorMode value for this ReadObjects.
     * 
     * @return errorMode
     */
    public ErrorDetailsLevel getErrorMode() {
        return errorMode;
    }


    /**
     * Sets the errorMode value for this ReadObjects.
     * 
     * @param errorMode
     */
    public void setErrorMode(ErrorDetailsLevel errorMode) {
        this.errorMode = errorMode;
    }


    /**
     * Gets the returnOptions value for this ReadObjects.
     * 
     * @return returnOptions
     */
    public ReadObjectsReturnOptions getReturnOptions() {
        return returnOptions;
    }


    /**
     * Sets the returnOptions value for this ReadObjects.
     * 
     * @param returnOptions
     */
    public void setReturnOptions(ReadObjectsReturnOptions returnOptions) {
        this.returnOptions = returnOptions;
    }


    /**
     * Gets the sessionID value for this ReadObjects.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this ReadObjects.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReadObjects)) return false;
        ReadObjects other = (ReadObjects) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.paths==null && other.getPaths()==null) || 
             (this.paths!=null &&
              java.util.Arrays.equals(this.paths, other.getPaths()))) &&
            this.resolveLinks == other.isResolveLinks() &&
            ((this.errorMode==null && other.getErrorMode()==null) || 
             (this.errorMode!=null &&
              this.errorMode.equals(other.getErrorMode()))) &&
            ((this.returnOptions==null && other.getReturnOptions()==null) || 
             (this.returnOptions!=null &&
              this.returnOptions.equals(other.getReturnOptions()))) &&
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
        if (getPaths() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaths());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaths(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isResolveLinks() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getErrorMode() != null) {
            _hashCode += getErrorMode().hashCode();
        }
        if (getReturnOptions() != null) {
            _hashCode += getReturnOptions().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReadObjects.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">readObjects"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paths");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "paths"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resolveLinks");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "resolveLinks"));
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
        elemField.setFieldName("returnOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "returnOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReadObjectsReturnOptions"));
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
