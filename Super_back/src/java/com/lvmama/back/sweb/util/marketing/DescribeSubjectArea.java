package com.lvmama.back.sweb.util.marketing;
/**
 * DescribeSubjectArea.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class DescribeSubjectArea  implements java.io.Serializable {
    private java.lang.String subjectAreaName;

    private SASubjectAreaDetails detailsLevel;

    private java.lang.String sessionID;

    public DescribeSubjectArea() {
    }

    public DescribeSubjectArea(
           java.lang.String subjectAreaName,
           SASubjectAreaDetails detailsLevel,
           java.lang.String sessionID) {
           this.subjectAreaName = subjectAreaName;
           this.detailsLevel = detailsLevel;
           this.sessionID = sessionID;
    }


    /**
     * Gets the subjectAreaName value for this DescribeSubjectArea.
     * 
     * @return subjectAreaName
     */
    public java.lang.String getSubjectAreaName() {
        return subjectAreaName;
    }


    /**
     * Sets the subjectAreaName value for this DescribeSubjectArea.
     * 
     * @param subjectAreaName
     */
    public void setSubjectAreaName(java.lang.String subjectAreaName) {
        this.subjectAreaName = subjectAreaName;
    }


    /**
     * Gets the detailsLevel value for this DescribeSubjectArea.
     * 
     * @return detailsLevel
     */
    public SASubjectAreaDetails getDetailsLevel() {
        return detailsLevel;
    }


    /**
     * Sets the detailsLevel value for this DescribeSubjectArea.
     * 
     * @param detailsLevel
     */
    public void setDetailsLevel(SASubjectAreaDetails detailsLevel) {
        this.detailsLevel = detailsLevel;
    }


    /**
     * Gets the sessionID value for this DescribeSubjectArea.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this DescribeSubjectArea.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DescribeSubjectArea)) return false;
        DescribeSubjectArea other = (DescribeSubjectArea) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.subjectAreaName==null && other.getSubjectAreaName()==null) || 
             (this.subjectAreaName!=null &&
              this.subjectAreaName.equals(other.getSubjectAreaName()))) &&
            ((this.detailsLevel==null && other.getDetailsLevel()==null) || 
             (this.detailsLevel!=null &&
              this.detailsLevel.equals(other.getDetailsLevel()))) &&
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
        if (getSubjectAreaName() != null) {
            _hashCode += getSubjectAreaName().hashCode();
        }
        if (getDetailsLevel() != null) {
            _hashCode += getDetailsLevel().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DescribeSubjectArea.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">describeSubjectArea"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subjectAreaName");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "subjectAreaName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailsLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "detailsLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SASubjectAreaDetails"));
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
