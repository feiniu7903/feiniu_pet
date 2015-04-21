package com.lvmama.back.sweb.util.marketing;
/**
 * MoveItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class MoveItem  implements java.io.Serializable {
    private java.lang.String pathSrc;

    private java.lang.String pathDest;

    private int flagACL;

    private java.lang.String sessionID;

    public MoveItem() {
    }

    public MoveItem(
           java.lang.String pathSrc,
           java.lang.String pathDest,
           int flagACL,
           java.lang.String sessionID) {
           this.pathSrc = pathSrc;
           this.pathDest = pathDest;
           this.flagACL = flagACL;
           this.sessionID = sessionID;
    }


    /**
     * Gets the pathSrc value for this MoveItem.
     * 
     * @return pathSrc
     */
    public java.lang.String getPathSrc() {
        return pathSrc;
    }


    /**
     * Sets the pathSrc value for this MoveItem.
     * 
     * @param pathSrc
     */
    public void setPathSrc(java.lang.String pathSrc) {
        this.pathSrc = pathSrc;
    }


    /**
     * Gets the pathDest value for this MoveItem.
     * 
     * @return pathDest
     */
    public java.lang.String getPathDest() {
        return pathDest;
    }


    /**
     * Sets the pathDest value for this MoveItem.
     * 
     * @param pathDest
     */
    public void setPathDest(java.lang.String pathDest) {
        this.pathDest = pathDest;
    }


    /**
     * Gets the flagACL value for this MoveItem.
     * 
     * @return flagACL
     */
    public int getFlagACL() {
        return flagACL;
    }


    /**
     * Sets the flagACL value for this MoveItem.
     * 
     * @param flagACL
     */
    public void setFlagACL(int flagACL) {
        this.flagACL = flagACL;
    }


    /**
     * Gets the sessionID value for this MoveItem.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this MoveItem.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MoveItem)) return false;
        MoveItem other = (MoveItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pathSrc==null && other.getPathSrc()==null) || 
             (this.pathSrc!=null &&
              this.pathSrc.equals(other.getPathSrc()))) &&
            ((this.pathDest==null && other.getPathDest()==null) || 
             (this.pathDest!=null &&
              this.pathDest.equals(other.getPathDest()))) &&
            this.flagACL == other.getFlagACL() &&
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
        if (getPathSrc() != null) {
            _hashCode += getPathSrc().hashCode();
        }
        if (getPathDest() != null) {
            _hashCode += getPathDest().hashCode();
        }
        _hashCode += getFlagACL();
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MoveItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">moveItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pathSrc");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "pathSrc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pathDest");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "pathDest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagACL");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "flagACL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
