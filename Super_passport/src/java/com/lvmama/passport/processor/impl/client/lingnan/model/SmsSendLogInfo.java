/**
 * SmsSendLogInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.lingnan.model;

public class SmsSendLogInfo  implements java.io.Serializable {
    private java.math.BigDecimal orderId;

    private boolean success;

    private java.lang.String errMsg;

    private java.lang.String mobile;

    private org.apache.axis.types.UnsignedByte messageType;

    private java.lang.String smsContent;

    private java.lang.String mmsContent;

    private java.util.Calendar sendTime;

    private org.apache.axis.types.UnsignedByte channel;

    private java.lang.String mmsId;

    private java.lang.String secondCode;

    private boolean isResend;

    private int smsLogId;

    public SmsSendLogInfo() {
    }

    public SmsSendLogInfo(
           java.math.BigDecimal orderId,
           boolean success,
           java.lang.String errMsg,
           java.lang.String mobile,
           org.apache.axis.types.UnsignedByte messageType,
           java.lang.String smsContent,
           java.lang.String mmsContent,
           java.util.Calendar sendTime,
           org.apache.axis.types.UnsignedByte channel,
           java.lang.String mmsId,
           java.lang.String secondCode,
           boolean isResend,
           int smsLogId) {
           this.orderId = orderId;
           this.success = success;
           this.errMsg = errMsg;
           this.mobile = mobile;
           this.messageType = messageType;
           this.smsContent = smsContent;
           this.mmsContent = mmsContent;
           this.sendTime = sendTime;
           this.channel = channel;
           this.mmsId = mmsId;
           this.secondCode = secondCode;
           this.isResend = isResend;
           this.smsLogId = smsLogId;
    }


    /**
     * Gets the orderId value for this SmsSendLogInfo.
     * 
     * @return orderId
     */
    public java.math.BigDecimal getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this SmsSendLogInfo.
     * 
     * @param orderId
     */
    public void setOrderId(java.math.BigDecimal orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the success value for this SmsSendLogInfo.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this SmsSendLogInfo.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the errMsg value for this SmsSendLogInfo.
     * 
     * @return errMsg
     */
    public java.lang.String getErrMsg() {
        return errMsg;
    }


    /**
     * Sets the errMsg value for this SmsSendLogInfo.
     * 
     * @param errMsg
     */
    public void setErrMsg(java.lang.String errMsg) {
        this.errMsg = errMsg;
    }


    /**
     * Gets the mobile value for this SmsSendLogInfo.
     * 
     * @return mobile
     */
    public java.lang.String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this SmsSendLogInfo.
     * 
     * @param mobile
     */
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the messageType value for this SmsSendLogInfo.
     * 
     * @return messageType
     */
    public org.apache.axis.types.UnsignedByte getMessageType() {
        return messageType;
    }


    /**
     * Sets the messageType value for this SmsSendLogInfo.
     * 
     * @param messageType
     */
    public void setMessageType(org.apache.axis.types.UnsignedByte messageType) {
        this.messageType = messageType;
    }


    /**
     * Gets the smsContent value for this SmsSendLogInfo.
     * 
     * @return smsContent
     */
    public java.lang.String getSmsContent() {
        return smsContent;
    }


    /**
     * Sets the smsContent value for this SmsSendLogInfo.
     * 
     * @param smsContent
     */
    public void setSmsContent(java.lang.String smsContent) {
        this.smsContent = smsContent;
    }


    /**
     * Gets the mmsContent value for this SmsSendLogInfo.
     * 
     * @return mmsContent
     */
    public java.lang.String getMmsContent() {
        return mmsContent;
    }


    /**
     * Sets the mmsContent value for this SmsSendLogInfo.
     * 
     * @param mmsContent
     */
    public void setMmsContent(java.lang.String mmsContent) {
        this.mmsContent = mmsContent;
    }


    /**
     * Gets the sendTime value for this SmsSendLogInfo.
     * 
     * @return sendTime
     */
    public java.util.Calendar getSendTime() {
        return sendTime;
    }


    /**
     * Sets the sendTime value for this SmsSendLogInfo.
     * 
     * @param sendTime
     */
    public void setSendTime(java.util.Calendar sendTime) {
        this.sendTime = sendTime;
    }


    /**
     * Gets the channel value for this SmsSendLogInfo.
     * 
     * @return channel
     */
    public org.apache.axis.types.UnsignedByte getChannel() {
        return channel;
    }


    /**
     * Sets the channel value for this SmsSendLogInfo.
     * 
     * @param channel
     */
    public void setChannel(org.apache.axis.types.UnsignedByte channel) {
        this.channel = channel;
    }


    /**
     * Gets the mmsId value for this SmsSendLogInfo.
     * 
     * @return mmsId
     */
    public java.lang.String getMmsId() {
        return mmsId;
    }


    /**
     * Sets the mmsId value for this SmsSendLogInfo.
     * 
     * @param mmsId
     */
    public void setMmsId(java.lang.String mmsId) {
        this.mmsId = mmsId;
    }


    /**
     * Gets the secondCode value for this SmsSendLogInfo.
     * 
     * @return secondCode
     */
    public java.lang.String getSecondCode() {
        return secondCode;
    }


    /**
     * Sets the secondCode value for this SmsSendLogInfo.
     * 
     * @param secondCode
     */
    public void setSecondCode(java.lang.String secondCode) {
        this.secondCode = secondCode;
    }


    /**
     * Gets the isResend value for this SmsSendLogInfo.
     * 
     * @return isResend
     */
    public boolean isIsResend() {
        return isResend;
    }


    /**
     * Sets the isResend value for this SmsSendLogInfo.
     * 
     * @param isResend
     */
    public void setIsResend(boolean isResend) {
        this.isResend = isResend;
    }


    /**
     * Gets the smsLogId value for this SmsSendLogInfo.
     * 
     * @return smsLogId
     */
    public int getSmsLogId() {
        return smsLogId;
    }


    /**
     * Sets the smsLogId value for this SmsSendLogInfo.
     * 
     * @param smsLogId
     */
    public void setSmsLogId(int smsLogId) {
        this.smsLogId = smsLogId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SmsSendLogInfo)) return false;
        SmsSendLogInfo other = (SmsSendLogInfo) obj;
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
            this.success == other.isSuccess() &&
            ((this.errMsg==null && other.getErrMsg()==null) || 
             (this.errMsg!=null &&
              this.errMsg.equals(other.getErrMsg()))) &&
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            ((this.messageType==null && other.getMessageType()==null) || 
             (this.messageType!=null &&
              this.messageType.equals(other.getMessageType()))) &&
            ((this.smsContent==null && other.getSmsContent()==null) || 
             (this.smsContent!=null &&
              this.smsContent.equals(other.getSmsContent()))) &&
            ((this.mmsContent==null && other.getMmsContent()==null) || 
             (this.mmsContent!=null &&
              this.mmsContent.equals(other.getMmsContent()))) &&
            ((this.sendTime==null && other.getSendTime()==null) || 
             (this.sendTime!=null &&
              this.sendTime.equals(other.getSendTime()))) &&
            ((this.channel==null && other.getChannel()==null) || 
             (this.channel!=null &&
              this.channel.equals(other.getChannel()))) &&
            ((this.mmsId==null && other.getMmsId()==null) || 
             (this.mmsId!=null &&
              this.mmsId.equals(other.getMmsId()))) &&
            ((this.secondCode==null && other.getSecondCode()==null) || 
             (this.secondCode!=null &&
              this.secondCode.equals(other.getSecondCode()))) &&
            this.isResend == other.isIsResend() &&
            this.smsLogId == other.getSmsLogId();
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
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getErrMsg() != null) {
            _hashCode += getErrMsg().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        if (getMessageType() != null) {
            _hashCode += getMessageType().hashCode();
        }
        if (getSmsContent() != null) {
            _hashCode += getSmsContent().hashCode();
        }
        if (getMmsContent() != null) {
            _hashCode += getMmsContent().hashCode();
        }
        if (getSendTime() != null) {
            _hashCode += getSendTime().hashCode();
        }
        if (getChannel() != null) {
            _hashCode += getChannel().hashCode();
        }
        if (getMmsId() != null) {
            _hashCode += getMmsId().hashCode();
        }
        if (getSecondCode() != null) {
            _hashCode += getSecondCode().hashCode();
        }
        _hashCode += (isIsResend() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getSmsLogId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SmsSendLogInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SmsSendLogInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "OrderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "Success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "ErrMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "Mobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "MessageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smsContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SmsContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mmsContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "MmsContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SendTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "Channel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mmsId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "MmsId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SecondCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isResend");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "IsResend"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smsLogId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SmsLogId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
