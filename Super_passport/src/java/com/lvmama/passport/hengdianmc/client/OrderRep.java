/**
 * OrderRep.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.hengdianmc.client;

public class OrderRep  implements java.io.Serializable {
    private java.lang.String companyOrderID;

    private boolean result;

    private java.lang.String errorMsg;

    private java.lang.String dealTime;

    public OrderRep() {
    }

    public OrderRep(
           java.lang.String companyOrderID,
           boolean result,
           java.lang.String errorMsg,
           java.lang.String dealTime) {
           this.companyOrderID = companyOrderID;
           this.result = result;
           this.errorMsg = errorMsg;
           this.dealTime = dealTime;
    }


    /**
     * Gets the companyOrderID value for this OrderRep.
     * 
     * @return companyOrderID
     */
    public java.lang.String getCompanyOrderID() {
        return companyOrderID;
    }


    /**
     * Sets the companyOrderID value for this OrderRep.
     * 
     * @param companyOrderID
     */
    public void setCompanyOrderID(java.lang.String companyOrderID) {
        this.companyOrderID = companyOrderID;
    }


    /**
     * Gets the result value for this OrderRep.
     * 
     * @return result
     */
    public boolean isResult() {
        return result;
    }


    /**
     * Sets the result value for this OrderRep.
     * 
     * @param result
     */
    public void setResult(boolean result) {
        this.result = result;
    }


    /**
     * Gets the errorMsg value for this OrderRep.
     * 
     * @return errorMsg
     */
    public java.lang.String getErrorMsg() {
        return errorMsg;
    }


    /**
     * Sets the errorMsg value for this OrderRep.
     * 
     * @param errorMsg
     */
    public void setErrorMsg(java.lang.String errorMsg) {
        this.errorMsg = errorMsg;
    }


    /**
     * Gets the dealTime value for this OrderRep.
     * 
     * @return dealTime
     */
    public java.lang.String getDealTime() {
        return dealTime;
    }


    /**
     * Sets the dealTime value for this OrderRep.
     * 
     * @param dealTime
     */
    public void setDealTime(java.lang.String dealTime) {
        this.dealTime = dealTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderRep)) return false;
        OrderRep other = (OrderRep) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.companyOrderID==null && other.getCompanyOrderID()==null) || 
             (this.companyOrderID!=null &&
              this.companyOrderID.equals(other.getCompanyOrderID()))) &&
            this.result == other.isResult() &&
            ((this.errorMsg==null && other.getErrorMsg()==null) || 
             (this.errorMsg!=null &&
              this.errorMsg.equals(other.getErrorMsg()))) &&
            ((this.dealTime==null && other.getDealTime()==null) || 
             (this.dealTime!=null &&
              this.dealTime.equals(other.getDealTime())));
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
        if (getCompanyOrderID() != null) {
            _hashCode += getCompanyOrderID().hashCode();
        }
        _hashCode += (isResult() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getErrorMsg() != null) {
            _hashCode += getErrorMsg().hashCode();
        }
        if (getDealTime() != null) {
            _hashCode += getDealTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderRep.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "OrderRep"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyOrderID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CompanyOrderID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ErrorMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DealTime"));
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
