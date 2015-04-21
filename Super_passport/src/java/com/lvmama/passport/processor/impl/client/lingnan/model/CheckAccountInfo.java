/**
 * CheckAccountInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.lingnan.model;

public class CheckAccountInfo  implements java.io.Serializable {
    private java.math.BigDecimal orderId;

    private org.apache.axis.types.UnsignedByte status;

    private org.apache.axis.types.UnsignedByte payType;

    private java.lang.String memberOrderId;

    private int checkinNum;

    private java.util.Calendar checkinTime;

    private java.lang.String checkinType;

    private java.util.Calendar createTime;

    public CheckAccountInfo() {
    }

    public CheckAccountInfo(
           java.math.BigDecimal orderId,
           org.apache.axis.types.UnsignedByte status,
           org.apache.axis.types.UnsignedByte payType,
           java.lang.String memberOrderId,
           int checkinNum,
           java.util.Calendar checkinTime,
           java.lang.String checkinType,
           java.util.Calendar createTime) {
           this.orderId = orderId;
           this.status = status;
           this.payType = payType;
           this.memberOrderId = memberOrderId;
           this.checkinNum = checkinNum;
           this.checkinTime = checkinTime;
           this.checkinType = checkinType;
           this.createTime = createTime;
    }


    /**
     * Gets the orderId value for this CheckAccountInfo.
     * 
     * @return orderId
     */
    public java.math.BigDecimal getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this CheckAccountInfo.
     * 
     * @param orderId
     */
    public void setOrderId(java.math.BigDecimal orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the status value for this CheckAccountInfo.
     * 
     * @return status
     */
    public org.apache.axis.types.UnsignedByte getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CheckAccountInfo.
     * 
     * @param status
     */
    public void setStatus(org.apache.axis.types.UnsignedByte status) {
        this.status = status;
    }


    /**
     * Gets the payType value for this CheckAccountInfo.
     * 
     * @return payType
     */
    public org.apache.axis.types.UnsignedByte getPayType() {
        return payType;
    }


    /**
     * Sets the payType value for this CheckAccountInfo.
     * 
     * @param payType
     */
    public void setPayType(org.apache.axis.types.UnsignedByte payType) {
        this.payType = payType;
    }


    /**
     * Gets the memberOrderId value for this CheckAccountInfo.
     * 
     * @return memberOrderId
     */
    public java.lang.String getMemberOrderId() {
        return memberOrderId;
    }


    /**
     * Sets the memberOrderId value for this CheckAccountInfo.
     * 
     * @param memberOrderId
     */
    public void setMemberOrderId(java.lang.String memberOrderId) {
        this.memberOrderId = memberOrderId;
    }


    /**
     * Gets the checkinNum value for this CheckAccountInfo.
     * 
     * @return checkinNum
     */
    public int getCheckinNum() {
        return checkinNum;
    }


    /**
     * Sets the checkinNum value for this CheckAccountInfo.
     * 
     * @param checkinNum
     */
    public void setCheckinNum(int checkinNum) {
        this.checkinNum = checkinNum;
    }


    /**
     * Gets the checkinTime value for this CheckAccountInfo.
     * 
     * @return checkinTime
     */
    public java.util.Calendar getCheckinTime() {
        return checkinTime;
    }


    /**
     * Sets the checkinTime value for this CheckAccountInfo.
     * 
     * @param checkinTime
     */
    public void setCheckinTime(java.util.Calendar checkinTime) {
        this.checkinTime = checkinTime;
    }


    /**
     * Gets the checkinType value for this CheckAccountInfo.
     * 
     * @return checkinType
     */
    public java.lang.String getCheckinType() {
        return checkinType;
    }


    /**
     * Sets the checkinType value for this CheckAccountInfo.
     * 
     * @param checkinType
     */
    public void setCheckinType(java.lang.String checkinType) {
        this.checkinType = checkinType;
    }


    /**
     * Gets the createTime value for this CheckAccountInfo.
     * 
     * @return createTime
     */
    public java.util.Calendar getCreateTime() {
        return createTime;
    }


    /**
     * Sets the createTime value for this CheckAccountInfo.
     * 
     * @param createTime
     */
    public void setCreateTime(java.util.Calendar createTime) {
        this.createTime = createTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CheckAccountInfo)) return false;
        CheckAccountInfo other = (CheckAccountInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.orderId==null && other.getOrderId()==null) || 
             (this.orderId!=null &&
              this.orderId.equals(other.getOrderId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.payType==null && other.getPayType()==null) || 
             (this.payType!=null &&
              this.payType.equals(other.getPayType()))) &&
            ((this.memberOrderId==null && other.getMemberOrderId()==null) || 
             (this.memberOrderId!=null &&
              this.memberOrderId.equals(other.getMemberOrderId()))) &&
            this.checkinNum == other.getCheckinNum() &&
            ((this.checkinTime==null && other.getCheckinTime()==null) || 
             (this.checkinTime!=null &&
              this.checkinTime.equals(other.getCheckinTime()))) &&
            ((this.checkinType==null && other.getCheckinType()==null) || 
             (this.checkinType!=null &&
              this.checkinType.equals(other.getCheckinType()))) &&
            ((this.createTime==null && other.getCreateTime()==null) || 
             (this.createTime!=null &&
              this.createTime.equals(other.getCreateTime())));
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
        if (getOrderId() != null) {
            _hashCode += getOrderId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getPayType() != null) {
            _hashCode += getPayType().hashCode();
        }
        if (getMemberOrderId() != null) {
            _hashCode += getMemberOrderId().hashCode();
        }
        _hashCode += getCheckinNum();
        if (getCheckinTime() != null) {
            _hashCode += getCheckinTime().hashCode();
        }
        if (getCheckinType() != null) {
            _hashCode += getCheckinType().hashCode();
        }
        if (getCreateTime() != null) {
            _hashCode += getCreateTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CheckAccountInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "CheckAccountInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "OrderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "PayType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memberOrderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "MemberOrderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkinNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "CheckinNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkinTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "CheckinTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkinType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "CheckinType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "CreateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
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
