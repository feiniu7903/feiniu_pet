/**
 * OrderInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.hengdianmc.client;

public class OrderInfo  implements java.io.Serializable {
    private java.lang.String timeStamp;

    private java.lang.String companyCode;

    private java.lang.String companyName;

    private java.lang.String companyOrderID;

    private java.lang.String orderTime;

    private java.lang.String arrivalDate;

    private java.lang.String payType;

    private java.lang.String visitorName;

    private java.lang.String visitorMobile;

    private int idCardNeed;

    private java.lang.String idCard;

    private java.lang.String products;

    public OrderInfo() {
    }

    public OrderInfo(
           java.lang.String timeStamp,
           java.lang.String companyCode,
           java.lang.String companyName,
           java.lang.String companyOrderID,
           java.lang.String orderTime,
           java.lang.String arrivalDate,
           java.lang.String payType,
           java.lang.String visitorName,
           java.lang.String visitorMobile,
           int idCardNeed,
           java.lang.String idCard,
           java.lang.String products) {
           this.timeStamp = timeStamp;
           this.companyCode = companyCode;
           this.companyName = companyName;
           this.companyOrderID = companyOrderID;
           this.orderTime = orderTime;
           this.arrivalDate = arrivalDate;
           this.payType = payType;
           this.visitorName = visitorName;
           this.visitorMobile = visitorMobile;
           this.idCardNeed = idCardNeed;
           this.idCard = idCard;
           this.products = products;
    }


    /**
     * Gets the timeStamp value for this OrderInfo.
     * 
     * @return timeStamp
     */
    public java.lang.String getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this OrderInfo.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.lang.String timeStamp) {
        this.timeStamp = timeStamp;
    }


    /**
     * Gets the companyCode value for this OrderInfo.
     * 
     * @return companyCode
     */
    public java.lang.String getCompanyCode() {
        return companyCode;
    }


    /**
     * Sets the companyCode value for this OrderInfo.
     * 
     * @param companyCode
     */
    public void setCompanyCode(java.lang.String companyCode) {
        this.companyCode = companyCode;
    }


    /**
     * Gets the companyName value for this OrderInfo.
     * 
     * @return companyName
     */
    public java.lang.String getCompanyName() {
        return companyName;
    }


    /**
     * Sets the companyName value for this OrderInfo.
     * 
     * @param companyName
     */
    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }


    /**
     * Gets the companyOrderID value for this OrderInfo.
     * 
     * @return companyOrderID
     */
    public java.lang.String getCompanyOrderID() {
        return companyOrderID;
    }


    /**
     * Sets the companyOrderID value for this OrderInfo.
     * 
     * @param companyOrderID
     */
    public void setCompanyOrderID(java.lang.String companyOrderID) {
        this.companyOrderID = companyOrderID;
    }


    /**
     * Gets the orderTime value for this OrderInfo.
     * 
     * @return orderTime
     */
    public java.lang.String getOrderTime() {
        return orderTime;
    }


    /**
     * Sets the orderTime value for this OrderInfo.
     * 
     * @param orderTime
     */
    public void setOrderTime(java.lang.String orderTime) {
        this.orderTime = orderTime;
    }


    /**
     * Gets the arrivalDate value for this OrderInfo.
     * 
     * @return arrivalDate
     */
    public java.lang.String getArrivalDate() {
        return arrivalDate;
    }


    /**
     * Sets the arrivalDate value for this OrderInfo.
     * 
     * @param arrivalDate
     */
    public void setArrivalDate(java.lang.String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }


    /**
     * Gets the payType value for this OrderInfo.
     * 
     * @return payType
     */
    public java.lang.String getPayType() {
        return payType;
    }


    /**
     * Sets the payType value for this OrderInfo.
     * 
     * @param payType
     */
    public void setPayType(java.lang.String payType) {
        this.payType = payType;
    }


    /**
     * Gets the visitorName value for this OrderInfo.
     * 
     * @return visitorName
     */
    public java.lang.String getVisitorName() {
        return visitorName;
    }


    /**
     * Sets the visitorName value for this OrderInfo.
     * 
     * @param visitorName
     */
    public void setVisitorName(java.lang.String visitorName) {
        this.visitorName = visitorName;
    }


    /**
     * Gets the visitorMobile value for this OrderInfo.
     * 
     * @return visitorMobile
     */
    public java.lang.String getVisitorMobile() {
        return visitorMobile;
    }


    /**
     * Sets the visitorMobile value for this OrderInfo.
     * 
     * @param visitorMobile
     */
    public void setVisitorMobile(java.lang.String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }


    /**
     * Gets the idCardNeed value for this OrderInfo.
     * 
     * @return idCardNeed
     */
    public int getIdCardNeed() {
        return idCardNeed;
    }


    /**
     * Sets the idCardNeed value for this OrderInfo.
     * 
     * @param idCardNeed
     */
    public void setIdCardNeed(int idCardNeed) {
        this.idCardNeed = idCardNeed;
    }


    /**
     * Gets the idCard value for this OrderInfo.
     * 
     * @return idCard
     */
    public java.lang.String getIdCard() {
        return idCard;
    }


    /**
     * Sets the idCard value for this OrderInfo.
     * 
     * @param idCard
     */
    public void setIdCard(java.lang.String idCard) {
        this.idCard = idCard;
    }


    /**
     * Gets the products value for this OrderInfo.
     * 
     * @return products
     */
    public java.lang.String getProducts() {
        return products;
    }


    /**
     * Sets the products value for this OrderInfo.
     * 
     * @param products
     */
    public void setProducts(java.lang.String products) {
        this.products = products;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderInfo)) return false;
        OrderInfo other = (OrderInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.timeStamp==null && other.getTimeStamp()==null) || 
             (this.timeStamp!=null &&
              this.timeStamp.equals(other.getTimeStamp()))) &&
            ((this.companyCode==null && other.getCompanyCode()==null) || 
             (this.companyCode!=null &&
              this.companyCode.equals(other.getCompanyCode()))) &&
            ((this.companyName==null && other.getCompanyName()==null) || 
             (this.companyName!=null &&
              this.companyName.equals(other.getCompanyName()))) &&
            ((this.companyOrderID==null && other.getCompanyOrderID()==null) || 
             (this.companyOrderID!=null &&
              this.companyOrderID.equals(other.getCompanyOrderID()))) &&
            ((this.orderTime==null && other.getOrderTime()==null) || 
             (this.orderTime!=null &&
              this.orderTime.equals(other.getOrderTime()))) &&
            ((this.arrivalDate==null && other.getArrivalDate()==null) || 
             (this.arrivalDate!=null &&
              this.arrivalDate.equals(other.getArrivalDate()))) &&
            ((this.payType==null && other.getPayType()==null) || 
             (this.payType!=null &&
              this.payType.equals(other.getPayType()))) &&
            ((this.visitorName==null && other.getVisitorName()==null) || 
             (this.visitorName!=null &&
              this.visitorName.equals(other.getVisitorName()))) &&
            ((this.visitorMobile==null && other.getVisitorMobile()==null) || 
             (this.visitorMobile!=null &&
              this.visitorMobile.equals(other.getVisitorMobile()))) &&
            this.idCardNeed == other.getIdCardNeed() &&
            ((this.idCard==null && other.getIdCard()==null) || 
             (this.idCard!=null &&
              this.idCard.equals(other.getIdCard()))) &&
            ((this.products==null && other.getProducts()==null) || 
             (this.products!=null &&
              this.products.equals(other.getProducts())));
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
        if (getTimeStamp() != null) {
            _hashCode += getTimeStamp().hashCode();
        }
        if (getCompanyCode() != null) {
            _hashCode += getCompanyCode().hashCode();
        }
        if (getCompanyName() != null) {
            _hashCode += getCompanyName().hashCode();
        }
        if (getCompanyOrderID() != null) {
            _hashCode += getCompanyOrderID().hashCode();
        }
        if (getOrderTime() != null) {
            _hashCode += getOrderTime().hashCode();
        }
        if (getArrivalDate() != null) {
            _hashCode += getArrivalDate().hashCode();
        }
        if (getPayType() != null) {
            _hashCode += getPayType().hashCode();
        }
        if (getVisitorName() != null) {
            _hashCode += getVisitorName().hashCode();
        }
        if (getVisitorMobile() != null) {
            _hashCode += getVisitorMobile().hashCode();
        }
        _hashCode += getIdCardNeed();
        if (getIdCard() != null) {
            _hashCode += getIdCard().hashCode();
        }
        if (getProducts() != null) {
            _hashCode += getProducts().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "OrderInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CompanyCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CompanyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyOrderID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CompanyOrderID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "OrderTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arrivalDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ArrivalDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PayType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visitorName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "VisitorName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visitorMobile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "VisitorMobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCardNeed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IdCardNeed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IdCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("products");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Products"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
