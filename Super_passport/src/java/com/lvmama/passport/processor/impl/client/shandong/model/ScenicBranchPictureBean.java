/**
 * ScenicBranchPictureBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class ScenicBranchPictureBean  implements java.io.Serializable {
    private java.lang.String branchPicture;

    private java.lang.String branchPictureId;

    private java.lang.String scenicBranchId;

    private java.lang.String userId;

    public ScenicBranchPictureBean() {
    }

    public ScenicBranchPictureBean(
           java.lang.String branchPicture,
           java.lang.String branchPictureId,
           java.lang.String scenicBranchId,
           java.lang.String userId) {
           this.branchPicture = branchPicture;
           this.branchPictureId = branchPictureId;
           this.scenicBranchId = scenicBranchId;
           this.userId = userId;
    }


    /**
     * Gets the branchPicture value for this ScenicBranchPictureBean.
     * 
     * @return branchPicture
     */
    public java.lang.String getBranchPicture() {
        return branchPicture;
    }


    /**
     * Sets the branchPicture value for this ScenicBranchPictureBean.
     * 
     * @param branchPicture
     */
    public void setBranchPicture(java.lang.String branchPicture) {
        this.branchPicture = branchPicture;
    }


    /**
     * Gets the branchPictureId value for this ScenicBranchPictureBean.
     * 
     * @return branchPictureId
     */
    public java.lang.String getBranchPictureId() {
        return branchPictureId;
    }


    /**
     * Sets the branchPictureId value for this ScenicBranchPictureBean.
     * 
     * @param branchPictureId
     */
    public void setBranchPictureId(java.lang.String branchPictureId) {
        this.branchPictureId = branchPictureId;
    }


    /**
     * Gets the scenicBranchId value for this ScenicBranchPictureBean.
     * 
     * @return scenicBranchId
     */
    public java.lang.String getScenicBranchId() {
        return scenicBranchId;
    }


    /**
     * Sets the scenicBranchId value for this ScenicBranchPictureBean.
     * 
     * @param scenicBranchId
     */
    public void setScenicBranchId(java.lang.String scenicBranchId) {
        this.scenicBranchId = scenicBranchId;
    }


    /**
     * Gets the userId value for this ScenicBranchPictureBean.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ScenicBranchPictureBean.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ScenicBranchPictureBean)) return false;
        ScenicBranchPictureBean other = (ScenicBranchPictureBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.branchPicture==null && other.getBranchPicture()==null) || 
             (this.branchPicture!=null &&
              this.branchPicture.equals(other.getBranchPicture()))) &&
            ((this.branchPictureId==null && other.getBranchPictureId()==null) || 
             (this.branchPictureId!=null &&
              this.branchPictureId.equals(other.getBranchPictureId()))) &&
            ((this.scenicBranchId==null && other.getScenicBranchId()==null) || 
             (this.scenicBranchId!=null &&
              this.scenicBranchId.equals(other.getScenicBranchId()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId())));
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
        if (getBranchPicture() != null) {
            _hashCode += getBranchPicture().hashCode();
        }
        if (getBranchPictureId() != null) {
            _hashCode += getBranchPictureId().hashCode();
        }
        if (getScenicBranchId() != null) {
            _hashCode += getScenicBranchId().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ScenicBranchPictureBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicBranchPictureBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchPicture");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "branchPicture"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchPictureId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "branchPictureId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicBranchId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "scenicBranchId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "userId"));
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
