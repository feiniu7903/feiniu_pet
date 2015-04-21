/**
 * OrderRequestInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.lingnan.model;

public class OrderRequestInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1712631168401358183L;

	private int ticketId;

    private java.lang.String contactName;

    private int ticketNum;

    private String planGoDate;

    private java.lang.String mobile;

    private java.lang.String memberOrderId;

    private boolean mmsReceivable;

    public OrderRequestInfo() {
    }

    public OrderRequestInfo(
           int ticketId,
           java.lang.String contactName,
           int ticketNum,
           String planGoDate,
           java.lang.String mobile,
           java.lang.String memberOrderId,
           boolean mmsReceivable) {
           this.ticketId = ticketId;
           this.contactName = contactName;
           this.ticketNum = ticketNum;
           this.planGoDate = planGoDate;
           this.mobile = mobile;
           this.memberOrderId = memberOrderId;
           this.mmsReceivable = mmsReceivable;
    }


    /**
     * Gets the ticketId value for this OrderRequestInfo.
     * 
     * @return ticketId
     */
    public int getTicketId() {
        return ticketId;
    }


    /**
     * Sets the ticketId value for this OrderRequestInfo.
     * 
     * @param ticketId
     */
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }


    /**
     * Gets the contactName value for this OrderRequestInfo.
     * 
     * @return contactName
     */
    public java.lang.String getContactName() {
        return contactName;
    }


    /**
     * Sets the contactName value for this OrderRequestInfo.
     * 
     * @param contactName
     */
    public void setContactName(java.lang.String contactName) {
        this.contactName = contactName;
    }


    /**
     * Gets the ticketNum value for this OrderRequestInfo.
     * 
     * @return ticketNum
     */
    public int getTicketNum() {
        return ticketNum;
    }


    /**
     * Sets the ticketNum value for this OrderRequestInfo.
     * 
     * @param ticketNum
     */
    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }


    /**
     * Gets the planGoDate value for this OrderRequestInfo.
     * 
     * @return planGoDate
     */
    public String getPlanGoDate() {
        return planGoDate;
    }


    /**
     * Sets the planGoDate value for this OrderRequestInfo.
     * 
     * @param planGoDate
     */
    public void setPlanGoDate(String planGoDate) {
        this.planGoDate = planGoDate;
    }


    /**
     * Gets the mobile value for this OrderRequestInfo.
     * 
     * @return mobile
     */
    public java.lang.String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this OrderRequestInfo.
     * 
     * @param mobile
     */
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the memberOrderId value for this OrderRequestInfo.
     * 
     * @return memberOrderId
     */
    public java.lang.String getMemberOrderId() {
        return memberOrderId;
    }


    /**
     * Sets the memberOrderId value for this OrderRequestInfo.
     * 
     * @param memberOrderId
     */
    public void setMemberOrderId(java.lang.String memberOrderId) {
        this.memberOrderId = memberOrderId;
    }


    /**
     * Gets the mmsReceivable value for this OrderRequestInfo.
     * 
     * @return mmsReceivable
     */
    public boolean isMmsReceivable() {
        return mmsReceivable;
    }


    /**
     * Sets the mmsReceivable value for this OrderRequestInfo.
     * 
     * @param mmsReceivable
     */
    public void setMmsReceivable(boolean mmsReceivable) {
        this.mmsReceivable = mmsReceivable;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderRequestInfo)) return false;
        OrderRequestInfo other = (OrderRequestInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ticketId == other.getTicketId() &&
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
            this.mmsReceivable == other.isMmsReceivable();
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
        _hashCode += getTicketId();
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
        _hashCode += (isMmsReceivable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderRequestInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "OrderRequestInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "TicketId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("mmsReceivable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.cnto.cn/ws/scene/v1.0/", "MmsReceivable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
