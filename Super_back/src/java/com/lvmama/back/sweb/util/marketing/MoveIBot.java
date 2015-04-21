package com.lvmama.back.sweb.util.marketing;
/**
 * MoveIBot.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class MoveIBot  implements java.io.Serializable {
    private java.lang.String fromPath;

    private java.lang.String toPath;

    private boolean resolveLinks;

    private boolean allowOverwrite;

    private java.lang.String sessionID;

    public MoveIBot() {
    }

    public MoveIBot(
           java.lang.String fromPath,
           java.lang.String toPath,
           boolean resolveLinks,
           boolean allowOverwrite,
           java.lang.String sessionID) {
           this.fromPath = fromPath;
           this.toPath = toPath;
           this.resolveLinks = resolveLinks;
           this.allowOverwrite = allowOverwrite;
           this.sessionID = sessionID;
    }


    /**
     * Gets the fromPath value for this MoveIBot.
     * 
     * @return fromPath
     */
    public java.lang.String getFromPath() {
        return fromPath;
    }


    /**
     * Sets the fromPath value for this MoveIBot.
     * 
     * @param fromPath
     */
    public void setFromPath(java.lang.String fromPath) {
        this.fromPath = fromPath;
    }


    /**
     * Gets the toPath value for this MoveIBot.
     * 
     * @return toPath
     */
    public java.lang.String getToPath() {
        return toPath;
    }


    /**
     * Sets the toPath value for this MoveIBot.
     * 
     * @param toPath
     */
    public void setToPath(java.lang.String toPath) {
        this.toPath = toPath;
    }


    /**
     * Gets the resolveLinks value for this MoveIBot.
     * 
     * @return resolveLinks
     */
    public boolean isResolveLinks() {
        return resolveLinks;
    }


    /**
     * Sets the resolveLinks value for this MoveIBot.
     * 
     * @param resolveLinks
     */
    public void setResolveLinks(boolean resolveLinks) {
        this.resolveLinks = resolveLinks;
    }


    /**
     * Gets the allowOverwrite value for this MoveIBot.
     * 
     * @return allowOverwrite
     */
    public boolean isAllowOverwrite() {
        return allowOverwrite;
    }


    /**
     * Sets the allowOverwrite value for this MoveIBot.
     * 
     * @param allowOverwrite
     */
    public void setAllowOverwrite(boolean allowOverwrite) {
        this.allowOverwrite = allowOverwrite;
    }


    /**
     * Gets the sessionID value for this MoveIBot.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this MoveIBot.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MoveIBot)) return false;
        MoveIBot other = (MoveIBot) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fromPath==null && other.getFromPath()==null) || 
             (this.fromPath!=null &&
              this.fromPath.equals(other.getFromPath()))) &&
            ((this.toPath==null && other.getToPath()==null) || 
             (this.toPath!=null &&
              this.toPath.equals(other.getToPath()))) &&
            this.resolveLinks == other.isResolveLinks() &&
            this.allowOverwrite == other.isAllowOverwrite() &&
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
        if (getFromPath() != null) {
            _hashCode += getFromPath().hashCode();
        }
        if (getToPath() != null) {
            _hashCode += getToPath().hashCode();
        }
        _hashCode += (isResolveLinks() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isAllowOverwrite() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MoveIBot.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">moveIBot"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "fromPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "toPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resolveLinks");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "resolveLinks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowOverwrite");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "allowOverwrite"));
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
