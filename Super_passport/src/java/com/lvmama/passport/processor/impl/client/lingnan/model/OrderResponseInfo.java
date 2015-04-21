/**
 * OrderResponseInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.lingnan.model;

public class OrderResponseInfo  implements java.io.Serializable {
    private java.math.BigDecimal orderId;

    private java.lang.String checkinCode;

    private int sceneId;

    private java.lang.String sceneName;

    private int ticketId;

    private java.lang.String ticketName;

    private java.math.BigDecimal salePrice;

    private java.math.BigDecimal agentPrice;

    private java.math.BigDecimal saleAmount;

    private java.math.BigDecimal paidAmount;

    private java.math.BigDecimal agentAmount;

    private java.math.BigDecimal cancelPoundage;

    private java.math.BigDecimal commissionAmount;

    private java.lang.String currency;

    private org.apache.axis.types.UnsignedByte status;

    private java.lang.String contactName;

    private int ticketNum;

    private java.util.Calendar planGoDate;

    private java.lang.String mobile;

    private java.lang.String memberOrderId;

    private java.util.Calendar createTime;

    private org.apache.axis.types.UnsignedByte validDay;

    private org.apache.axis.types.UnsignedByte payType;

    private com.lvmama.passport.processor.impl.client.lingnan.model.TicketCheckinInfo ticketCheckinInfo;

    private com.lvmama.passport.processor.impl.client.lingnan.model.SmsSendLogInfo[] smsSendLogInfo;

    public OrderResponseInfo() {
    }

    public OrderResponseInfo(
           java.math.BigDecimal orderId,
           java.lang.String checkinCode,
           int sceneId,
           java.lang.String sceneName,
           int ticketId,
           java.lang.String ticketName,
           java.math.BigDecimal salePrice,
           java.math.BigDecimal agentPrice,
           java.math.BigDecimal saleAmount,
           java.math.BigDecimal paidAmount,
           java.math.BigDecimal agentAmount,
           java.math.BigDecimal cancelPoundage,
           java.math.BigDecimal commissionAmount,
           java.lang.String currency,
           org.apache.axis.types.UnsignedByte status,
           java.lang.String contactName,
           int ticketNum,
           java.util.Calendar planGoDate,
           java.lang.String mobile,
           java.lang.String memberOrderId,
           java.util.Calendar createTime,
           org.apache.axis.types.UnsignedByte validDay,
           org.apache.axis.types.UnsignedByte payType,
           TicketCheckinInfo ticketCheckinInfo,
           SmsSendLogInfo[] smsSendLogInfo) {
           this.orderId = orderId;
           this.checkinCode = checkinCode;
           this.sceneId = sceneId;
           this.sceneName = sceneName;
           this.ticketId = ticketId;
           this.ticketName = ticketName;
           this.salePrice = salePrice;
           this.agentPrice = agentPrice;
           this.saleAmount = saleAmount;
           this.paidAmount = paidAmount;
           this.agentAmount = agentAmount;
           this.cancelPoundage = cancelPoundage;
           this.commissionAmount = commissionAmount;
           this.currency = currency;
           this.status = status;
           this.contactName = contactName;
           this.ticketNum = ticketNum;
           this.planGoDate = planGoDate;
           this.mobile = mobile;
           this.memberOrderId = memberOrderId;
           this.createTime = createTime;
           this.validDay = validDay;
           this.payType = payType;
           this.ticketCheckinInfo = ticketCheckinInfo;
           this.smsSendLogInfo = smsSendLogInfo;
    }


    /**
     * Gets the orderId value for this OrderResponseInfo.
     * 
     * @return orderId
     */
    public java.math.BigDecimal getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this OrderResponseInfo.
     * 
     * @param orderId
     */
    public void setOrderId(java.math.BigDecimal orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the checkinCode value for this OrderResponseInfo.
     * 
     * @return checkinCode
     */
    public java.lang.String getCheckinCode() {
        return checkinCode;
    }


    /**
     * Sets the checkinCode value for this OrderResponseInfo.
     * 
     * @param checkinCode
     */
    public void setCheckinCode(java.lang.String checkinCode) {
        this.checkinCode = checkinCode;
    }


    /**
     * Gets the sceneId value for this OrderResponseInfo.
     * 
     * @return sceneId
     */
    public int getSceneId() {
        return sceneId;
    }


    /**
     * Sets the sceneId value for this OrderResponseInfo.
     * 
     * @param sceneId
     */
    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }


    /**
     * Gets the sceneName value for this OrderResponseInfo.
     * 
     * @return sceneName
     */
    public java.lang.String getSceneName() {
        return sceneName;
    }


    /**
     * Sets the sceneName value for this OrderResponseInfo.
     * 
     * @param sceneName
     */
    public void setSceneName(java.lang.String sceneName) {
        this.sceneName = sceneName;
    }


    /**
     * Gets the ticketId value for this OrderResponseInfo.
     * 
     * @return ticketId
     */
    public int getTicketId() {
        return ticketId;
    }


    /**
     * Sets the ticketId value for this OrderResponseInfo.
     * 
     * @param ticketId
     */
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }


    /**
     * Gets the ticketName value for this OrderResponseInfo.
     * 
     * @return ticketName
     */
    public java.lang.String getTicketName() {
        return ticketName;
    }


    /**
     * Sets the ticketName value for this OrderResponseInfo.
     * 
     * @param ticketName
     */
    public void setTicketName(java.lang.String ticketName) {
        this.ticketName = ticketName;
    }


    /**
     * Gets the salePrice value for this OrderResponseInfo.
     * 
     * @return salePrice
     */
    public java.math.BigDecimal getSalePrice() {
        return salePrice;
    }


    /**
     * Sets the salePrice value for this OrderResponseInfo.
     * 
     * @param salePrice
     */
    public void setSalePrice(java.math.BigDecimal salePrice) {
        this.salePrice = salePrice;
    }


    /**
     * Gets the agentPrice value for this OrderResponseInfo.
     * 
     * @return agentPrice
     */
    public java.math.BigDecimal getAgentPrice() {
        return agentPrice;
    }


    /**
     * Sets the agentPrice value for this OrderResponseInfo.
     * 
     * @param agentPrice
     */
    public void setAgentPrice(java.math.BigDecimal agentPrice) {
        this.agentPrice = agentPrice;
    }


    /**
     * Gets the saleAmount value for this OrderResponseInfo.
     * 
     * @return saleAmount
     */
    public java.math.BigDecimal getSaleAmount() {
        return saleAmount;
    }


    /**
     * Sets the saleAmount value for this OrderResponseInfo.
     * 
     * @param saleAmount
     */
    public void setSaleAmount(java.math.BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }


    /**
     * Gets the paidAmount value for this OrderResponseInfo.
     * 
     * @return paidAmount
     */
    public java.math.BigDecimal getPaidAmount() {
        return paidAmount;
    }


    /**
     * Sets the paidAmount value for this OrderResponseInfo.
     * 
     * @param paidAmount
     */
    public void setPaidAmount(java.math.BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }


    /**
     * Gets the agentAmount value for this OrderResponseInfo.
     * 
     * @return agentAmount
     */
    public java.math.BigDecimal getAgentAmount() {
        return agentAmount;
    }


    /**
     * Sets the agentAmount value for this OrderResponseInfo.
     * 
     * @param agentAmount
     */
    public void setAgentAmount(java.math.BigDecimal agentAmount) {
        this.agentAmount = agentAmount;
    }


    /**
     * Gets the cancelPoundage value for this OrderResponseInfo.
     * 
     * @return cancelPoundage
     */
    public java.math.BigDecimal getCancelPoundage() {
        return cancelPoundage;
    }


    /**
     * Sets the cancelPoundage value for this OrderResponseInfo.
     * 
     * @param cancelPoundage
     */
    public void setCancelPoundage(java.math.BigDecimal cancelPoundage) {
        this.cancelPoundage = cancelPoundage;
    }


    /**
     * Gets the commissionAmount value for this OrderResponseInfo.
     * 
     * @return commissionAmount
     */
    public java.math.BigDecimal getCommissionAmount() {
        return commissionAmount;
    }


    /**
     * Sets the commissionAmount value for this OrderResponseInfo.
     * 
     * @param commissionAmount
     */
    public void setCommissionAmount(java.math.BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }


    /**
     * Gets the currency value for this OrderResponseInfo.
     * 
     * @return currency
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this OrderResponseInfo.
     * 
     * @param currency
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the status value for this OrderResponseInfo.
     * 
     * @return status
     */
    public org.apache.axis.types.UnsignedByte getStatus() {
        return status;
    }


    /**
     * Sets the status value for this OrderResponseInfo.
     * 
     * @param status
     */
    public void setStatus(org.apache.axis.types.UnsignedByte status) {
        this.status = status;
    }


    /**
     * Gets the contactName value for this OrderResponseInfo.
     * 
     * @return contactName
     */
    public java.lang.String getContactName() {
        return contactName;
    }


    /**
     * Sets the contactName value for this OrderResponseInfo.
     * 
     * @param contactName
     */
    public void setContactName(java.lang.String contactName) {
        this.contactName = contactName;
    }


    /**
     * Gets the ticketNum value for this OrderResponseInfo.
     * 
     * @return ticketNum
     */
    public int getTicketNum() {
        return ticketNum;
    }


    /**
     * Sets the ticketNum value for this OrderResponseInfo.
     * 
     * @param ticketNum
     */
    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }


    /**
     * Gets the planGoDate value for this OrderResponseInfo.
     * 
     * @return planGoDate
     */
    public java.util.Calendar getPlanGoDate() {
        return planGoDate;
    }


    /**
     * Sets the planGoDate value for this OrderResponseInfo.
     * 
     * @param planGoDate
     */
    public void setPlanGoDate(java.util.Calendar planGoDate) {
        this.planGoDate = planGoDate;
    }


    /**
     * Gets the mobile value for this OrderResponseInfo.
     * 
     * @return mobile
     */
    public java.lang.String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this OrderResponseInfo.
     * 
     * @param mobile
     */
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the memberOrderId value for this OrderResponseInfo.
     * 
     * @return memberOrderId
     */
    public java.lang.String getMemberOrderId() {
        return memberOrderId;
    }


    /**
     * Sets the memberOrderId value for this OrderResponseInfo.
     * 
     * @param memberOrderId
     */
    public void setMemberOrderId(java.lang.String memberOrderId) {
        this.memberOrderId = memberOrderId;
    }


    /**
     * Gets the createTime value for this OrderResponseInfo.
     * 
     * @return createTime
     */
    public java.util.Calendar getCreateTime() {
        return createTime;
    }


    /**
     * Sets the createTime value for this OrderResponseInfo.
     * 
     * @param createTime
     */
    public void setCreateTime(java.util.Calendar createTime) {
        this.createTime = createTime;
    }


    /**
     * Gets the validDay value for this OrderResponseInfo.
     * 
     * @return validDay
     */
    public org.apache.axis.types.UnsignedByte getValidDay() {
        return validDay;
    }


    /**
     * Sets the validDay value for this OrderResponseInfo.
     * 
     * @param validDay
     */
    public void setValidDay(org.apache.axis.types.UnsignedByte validDay) {
        this.validDay = validDay;
    }


    /**
     * Gets the payType value for this OrderResponseInfo.
     * 
     * @return payType
     */
    public org.apache.axis.types.UnsignedByte getPayType() {
        return payType;
    }


    /**
     * Sets the payType value for this OrderResponseInfo.
     * 
     * @param payType
     */
    public void setPayType(org.apache.axis.types.UnsignedByte payType) {
        this.payType = payType;
    }


    /**
     * Gets the ticketCheckinInfo value for this OrderResponseInfo.
     * 
     * @return ticketCheckinInfo
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.TicketCheckinInfo getTicketCheckinInfo() {
        return ticketCheckinInfo;
    }


    /**
     * Sets the ticketCheckinInfo value for this OrderResponseInfo.
     * 
     * @param ticketCheckinInfo
     */
    public void setTicketCheckinInfo(TicketCheckinInfo ticketCheckinInfo) {
        this.ticketCheckinInfo = ticketCheckinInfo;
    }


    /**
     * Gets the smsSendLogInfo value for this OrderResponseInfo.
     * 
     * @return smsSendLogInfo
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.SmsSendLogInfo[] getSmsSendLogInfo() {
        return smsSendLogInfo;
    }


    /**
     * Sets the smsSendLogInfo value for this OrderResponseInfo.
     * 
     * @param smsSendLogInfo
     */
    public void setSmsSendLogInfo(SmsSendLogInfo[] smsSendLogInfo) {
        this.smsSendLogInfo = smsSendLogInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderResponseInfo)) return false;
        OrderResponseInfo other = (OrderResponseInfo) obj;
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
            ((this.checkinCode==null && other.getCheckinCode()==null) || 
             (this.checkinCode!=null &&
              this.checkinCode.equals(other.getCheckinCode()))) &&
            this.sceneId == other.getSceneId() &&
            ((this.sceneName==null && other.getSceneName()==null) || 
             (this.sceneName!=null &&
              this.sceneName.equals(other.getSceneName()))) &&
            this.ticketId == other.getTicketId() &&
            ((this.ticketName==null && other.getTicketName()==null) || 
             (this.ticketName!=null &&
              this.ticketName.equals(other.getTicketName()))) &&
            ((this.salePrice==null && other.getSalePrice()==null) || 
             (this.salePrice!=null &&
              this.salePrice.equals(other.getSalePrice()))) &&
            ((this.agentPrice==null && other.getAgentPrice()==null) || 
             (this.agentPrice!=null &&
              this.agentPrice.equals(other.getAgentPrice()))) &&
            ((this.saleAmount==null && other.getSaleAmount()==null) || 
             (this.saleAmount!=null &&
              this.saleAmount.equals(other.getSaleAmount()))) &&
            ((this.paidAmount==null && other.getPaidAmount()==null) || 
             (this.paidAmount!=null &&
              this.paidAmount.equals(other.getPaidAmount()))) &&
            ((this.agentAmount==null && other.getAgentAmount()==null) || 
             (this.agentAmount!=null &&
              this.agentAmount.equals(other.getAgentAmount()))) &&
            ((this.cancelPoundage==null && other.getCancelPoundage()==null) || 
             (this.cancelPoundage!=null &&
              this.cancelPoundage.equals(other.getCancelPoundage()))) &&
            ((this.commissionAmount==null && other.getCommissionAmount()==null) || 
             (this.commissionAmount!=null &&
              this.commissionAmount.equals(other.getCommissionAmount()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.contactName==null && other.getContactName()==null) || 
             (this.contactName!=null &&
              this.contactName.equals(other.getContactName()))) &&
            this.ticketNum == other.getTicketNum() &&
            ((this.planGoDate==null && other.getPlanGoDate()==null) || 
             (this.planGoDate!=null &&
              this.planGoDate.equals(other.getPlanGoDate()))) &&
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            ((this.memberOrderId==null && other.getMemberOrderId()==null) || 
             (this.memberOrderId!=null &&
              this.memberOrderId.equals(other.getMemberOrderId()))) &&
            ((this.createTime==null && other.getCreateTime()==null) || 
             (this.createTime!=null &&
              this.createTime.equals(other.getCreateTime()))) &&
            ((this.validDay==null && other.getValidDay()==null) || 
             (this.validDay!=null &&
              this.validDay.equals(other.getValidDay()))) &&
            ((this.payType==null && other.getPayType()==null) || 
             (this.payType!=null &&
              this.payType.equals(other.getPayType()))) &&
            ((this.ticketCheckinInfo==null && other.getTicketCheckinInfo()==null) || 
             (this.ticketCheckinInfo!=null &&
              this.ticketCheckinInfo.equals(other.getTicketCheckinInfo()))) &&
            ((this.smsSendLogInfo==null && other.getSmsSendLogInfo()==null) || 
             (this.smsSendLogInfo!=null &&
              java.util.Arrays.equals(this.smsSendLogInfo, other.getSmsSendLogInfo())));
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
        if (getCheckinCode() != null) {
            _hashCode += getCheckinCode().hashCode();
        }
        _hashCode += getSceneId();
        if (getSceneName() != null) {
            _hashCode += getSceneName().hashCode();
        }
        _hashCode += getTicketId();
        if (getTicketName() != null) {
            _hashCode += getTicketName().hashCode();
        }
        if (getSalePrice() != null) {
            _hashCode += getSalePrice().hashCode();
        }
        if (getAgentPrice() != null) {
            _hashCode += getAgentPrice().hashCode();
        }
        if (getSaleAmount() != null) {
            _hashCode += getSaleAmount().hashCode();
        }
        if (getPaidAmount() != null) {
            _hashCode += getPaidAmount().hashCode();
        }
        if (getAgentAmount() != null) {
            _hashCode += getAgentAmount().hashCode();
        }
        if (getCancelPoundage() != null) {
            _hashCode += getCancelPoundage().hashCode();
        }
        if (getCommissionAmount() != null) {
            _hashCode += getCommissionAmount().hashCode();
        }
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getContactName() != null) {
            _hashCode += getContactName().hashCode();
        }
        _hashCode += getTicketNum();
        if (getPlanGoDate() != null) {
            _hashCode += getPlanGoDate().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        if (getMemberOrderId() != null) {
            _hashCode += getMemberOrderId().hashCode();
        }
        if (getCreateTime() != null) {
            _hashCode += getCreateTime().hashCode();
        }
        if (getValidDay() != null) {
            _hashCode += getValidDay().hashCode();
        }
        if (getPayType() != null) {
            _hashCode += getPayType().hashCode();
        }
        if (getTicketCheckinInfo() != null) {
            _hashCode += getTicketCheckinInfo().hashCode();
        }
        if (getSmsSendLogInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSmsSendLogInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSmsSendLogInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderResponseInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "OrderResponseInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "OrderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkinCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "CheckinCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sceneId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SceneId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sceneName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SceneName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "TicketId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "TicketName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SalePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "AgentPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SaleAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paidAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "PaidAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "AgentAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cancelPoundage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "CancelPoundage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commissionAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "CommissionAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "ContactName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "TicketNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("planGoDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "PlanGoDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("memberOrderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "MemberOrderId"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validDay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "ValidDay"));
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
        elemField.setFieldName("ticketCheckinInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "TicketCheckinInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "TicketCheckinInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smsSendLogInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SmsSendLogInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SmsSendLogInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "SmsSendLogInfo"));
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
