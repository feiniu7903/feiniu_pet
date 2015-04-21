/**
 * TicketCheckinInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.lingnan.model;

public class TicketCheckinInfo  implements java.io.Serializable {
    private java.math.BigDecimal orderId;

    private int checkinNum;

    private java.util.Calendar checkinTime;

    private org.apache.axis.types.UnsignedByte checkinType;

    public TicketCheckinInfo() {
    }

    public TicketCheckinInfo(
           java.math.BigDecimal orderId,
           int checkinNum,
           java.util.Calendar checkinTime,
           org.apache.axis.types.UnsignedByte checkinType) {
           this.orderId = orderId;
           this.checkinNum = checkinNum;
           this.checkinTime = checkinTime;
           this.checkinType = checkinType;
    }


    /**
     * Gets the orderId value for this TicketCheckinInfo.
     * 
     * @return orderId
     */
    public java.math.BigDecimal getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this TicketCheckinInfo.
     * 
     * @param orderId
     */
    public void setOrderId(java.math.BigDecimal orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the checkinNum value for this TicketCheckinInfo.
     * 
     * @return checkinNum
     */
    public int getCheckinNum() {
        return checkinNum;
    }


    /**
     * Sets the checkinNum value for this TicketCheckinInfo.
     * 
     * @param checkinNum
     */
    public void setCheckinNum(int checkinNum) {
        this.checkinNum = checkinNum;
    }


    /**
     * Gets the checkinTime value for this TicketCheckinInfo.
     * 
     * @return checkinTime
     */
    public java.util.Calendar getCheckinTime() {
        return checkinTime;
    }


    /**
     * Sets the checkinTime value for this TicketCheckinInfo.
     * 
     * @param checkinTime
     */
    public void setCheckinTime(java.util.Calendar checkinTime) {
        this.checkinTime = checkinTime;
    }


    /**
     * Gets the checkinType value for this TicketCheckinInfo.
     * 
     * @return checkinType
     */
    public org.apache.axis.types.UnsignedByte getCheckinType() {
        return checkinType;
    }


    /**
     * Sets the checkinType value for this TicketCheckinInfo.
     * 
     * @param checkinType
     */
    public void setCheckinType(org.apache.axis.types.UnsignedByte checkinType) {
        this.checkinType = checkinType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TicketCheckinInfo)) return false;
        TicketCheckinInfo other = (TicketCheckinInfo) obj;
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
            this.checkinNum == other.getCheckinNum() &&
            ((this.checkinTime==null && other.getCheckinTime()==null) || 
             (this.checkinTime!=null &&
              this.checkinTime.equals(other.getCheckinTime()))) &&
            ((this.checkinType==null && other.getCheckinType()==null) || 
             (this.checkinType!=null &&
              this.checkinType.equals(other.getCheckinType())));
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
        _hashCode += getCheckinNum();
        if (getCheckinTime() != null) {
            _hashCode += getCheckinTime().hashCode();
        }
        if (getCheckinType() != null) {
            _hashCode += getCheckinType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TicketCheckinInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "TicketCheckinInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "OrderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
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
