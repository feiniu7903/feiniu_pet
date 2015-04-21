package com.lvmama.back.sweb.util.marketing;
/**
 * PasteItem2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class PasteItem2  implements java.io.Serializable {
    private byte[] archive;

    private java.lang.String replacePath;

    private int flagACL;

    private int flagOverwrite;

    private java.lang.String sessionID;

    public PasteItem2() {
    }

    public PasteItem2(
           byte[] archive,
           java.lang.String replacePath,
           int flagACL,
           int flagOverwrite,
           java.lang.String sessionID) {
           this.archive = archive;
           this.replacePath = replacePath;
           this.flagACL = flagACL;
           this.flagOverwrite = flagOverwrite;
           this.sessionID = sessionID;
    }


    /**
     * Gets the archive value for this PasteItem2.
     * 
     * @return archive
     */
    public byte[] getArchive() {
        return archive;
    }


    /**
     * Sets the archive value for this PasteItem2.
     * 
     * @param archive
     */
    public void setArchive(byte[] archive) {
        this.archive = archive;
    }


    /**
     * Gets the replacePath value for this PasteItem2.
     * 
     * @return replacePath
     */
    public java.lang.String getReplacePath() {
        return replacePath;
    }


    /**
     * Sets the replacePath value for this PasteItem2.
     * 
     * @param replacePath
     */
    public void setReplacePath(java.lang.String replacePath) {
        this.replacePath = replacePath;
    }


    /**
     * Gets the flagACL value for this PasteItem2.
     * 
     * @return flagACL
     */
    public int getFlagACL() {
        return flagACL;
    }


    /**
     * Sets the flagACL value for this PasteItem2.
     * 
     * @param flagACL
     */
    public void setFlagACL(int flagACL) {
        this.flagACL = flagACL;
    }


    /**
     * Gets the flagOverwrite value for this PasteItem2.
     * 
     * @return flagOverwrite
     */
    public int getFlagOverwrite() {
        return flagOverwrite;
    }


    /**
     * Sets the flagOverwrite value for this PasteItem2.
     * 
     * @param flagOverwrite
     */
    public void setFlagOverwrite(int flagOverwrite) {
        this.flagOverwrite = flagOverwrite;
    }


    /**
     * Gets the sessionID value for this PasteItem2.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this PasteItem2.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PasteItem2)) return false;
        PasteItem2 other = (PasteItem2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.archive==null && other.getArchive()==null) || 
             (this.archive!=null &&
              java.util.Arrays.equals(this.archive, other.getArchive()))) &&
            ((this.replacePath==null && other.getReplacePath()==null) || 
             (this.replacePath!=null &&
              this.replacePath.equals(other.getReplacePath()))) &&
            this.flagACL == other.getFlagACL() &&
            this.flagOverwrite == other.getFlagOverwrite() &&
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
        if (getArchive() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArchive());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArchive(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getReplacePath() != null) {
            _hashCode += getReplacePath().hashCode();
        }
        _hashCode += getFlagACL();
        _hashCode += getFlagOverwrite();
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PasteItem2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">pasteItem2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("archive");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "archive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replacePath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "replacePath"));
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
        elemField.setFieldName("flagOverwrite");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "flagOverwrite"));
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
