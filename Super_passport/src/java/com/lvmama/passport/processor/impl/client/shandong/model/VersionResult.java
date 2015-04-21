/**
 * VersionResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class VersionResult  implements java.io.Serializable {
    private java.lang.String message;

    private java.lang.String stateCode;

    private java.lang.String updatetime;

    private java.lang.String versionNo;

    public VersionResult() {
    }

    public VersionResult(
           java.lang.String message,
           java.lang.String stateCode,
           java.lang.String updatetime,
           java.lang.String versionNo) {
           this.message = message;
           this.stateCode = stateCode;
           this.updatetime = updatetime;
           this.versionNo = versionNo;
    }


    /**
     * Gets the message value for this VersionResult.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this VersionResult.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the stateCode value for this VersionResult.
     * 
     * @return stateCode
     */
    public java.lang.String getStateCode() {
        return stateCode;
    }


    /**
     * Sets the stateCode value for this VersionResult.
     * 
     * @param stateCode
     */
    public void setStateCode(java.lang.String stateCode) {
        this.stateCode = stateCode;
    }


    /**
     * Gets the updatetime value for this VersionResult.
     * 
     * @return updatetime
     */
    public java.lang.String getUpdatetime() {
        return updatetime;
    }


    /**
     * Sets the updatetime value for this VersionResult.
     * 
     * @param updatetime
     */
    public void setUpdatetime(java.lang.String updatetime) {
        this.updatetime = updatetime;
    }


    /**
     * Gets the versionNo value for this VersionResult.
     * 
     * @return versionNo
     */
    public java.lang.String getVersionNo() {
        return versionNo;
    }


    /**
     * Sets the versionNo value for this VersionResult.
     * 
     * @param versionNo
     */
    public void setVersionNo(java.lang.String versionNo) {
        this.versionNo = versionNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VersionResult)) return false;
        VersionResult other = (VersionResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.stateCode==null && other.getStateCode()==null) || 
             (this.stateCode!=null &&
              this.stateCode.equals(other.getStateCode()))) &&
            ((this.updatetime==null && other.getUpdatetime()==null) || 
             (this.updatetime!=null &&
              this.updatetime.equals(other.getUpdatetime()))) &&
            ((this.versionNo==null && other.getVersionNo()==null) || 
             (this.versionNo!=null &&
              this.versionNo.equals(other.getVersionNo())));
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
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getStateCode() != null) {
            _hashCode += getStateCode().hashCode();
        }
        if (getUpdatetime() != null) {
            _hashCode += getUpdatetime().hashCode();
        }
        if (getVersionNo() != null) {
            _hashCode += getVersionNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VersionResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://entity.xz.com", "VersionResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "stateCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatetime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "updatetime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "versionNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
