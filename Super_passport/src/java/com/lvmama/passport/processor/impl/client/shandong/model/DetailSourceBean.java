/**
 * DetailSourceBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class DetailSourceBean  implements java.io.Serializable {
    private java.util.Calendar activityEndDate;

    private java.lang.String activityName;

    private java.util.Calendar activityStartDate;

    private java.lang.String describe;

    private java.lang.String detailSourceId;

    private com.lvmama.passport.processor.impl.client.shandong.model.ScenicChannelName scenicChannelName;

    public DetailSourceBean() {
    }

    public DetailSourceBean(
           java.util.Calendar activityEndDate,
           java.lang.String activityName,
           java.util.Calendar activityStartDate,
           java.lang.String describe,
           java.lang.String detailSourceId,
           com.lvmama.passport.processor.impl.client.shandong.model.ScenicChannelName scenicChannelName) {
           this.activityEndDate = activityEndDate;
           this.activityName = activityName;
           this.activityStartDate = activityStartDate;
           this.describe = describe;
           this.detailSourceId = detailSourceId;
           this.scenicChannelName = scenicChannelName;
    }


    /**
     * Gets the activityEndDate value for this DetailSourceBean.
     * 
     * @return activityEndDate
     */
    public java.util.Calendar getActivityEndDate() {
        return activityEndDate;
    }


    /**
     * Sets the activityEndDate value for this DetailSourceBean.
     * 
     * @param activityEndDate
     */
    public void setActivityEndDate(java.util.Calendar activityEndDate) {
        this.activityEndDate = activityEndDate;
    }


    /**
     * Gets the activityName value for this DetailSourceBean.
     * 
     * @return activityName
     */
    public java.lang.String getActivityName() {
        return activityName;
    }


    /**
     * Sets the activityName value for this DetailSourceBean.
     * 
     * @param activityName
     */
    public void setActivityName(java.lang.String activityName) {
        this.activityName = activityName;
    }


    /**
     * Gets the activityStartDate value for this DetailSourceBean.
     * 
     * @return activityStartDate
     */
    public java.util.Calendar getActivityStartDate() {
        return activityStartDate;
    }


    /**
     * Sets the activityStartDate value for this DetailSourceBean.
     * 
     * @param activityStartDate
     */
    public void setActivityStartDate(java.util.Calendar activityStartDate) {
        this.activityStartDate = activityStartDate;
    }


    /**
     * Gets the describe value for this DetailSourceBean.
     * 
     * @return describe
     */
    public java.lang.String getDescribe() {
        return describe;
    }


    /**
     * Sets the describe value for this DetailSourceBean.
     * 
     * @param describe
     */
    public void setDescribe(java.lang.String describe) {
        this.describe = describe;
    }


    /**
     * Gets the detailSourceId value for this DetailSourceBean.
     * 
     * @return detailSourceId
     */
    public java.lang.String getDetailSourceId() {
        return detailSourceId;
    }


    /**
     * Sets the detailSourceId value for this DetailSourceBean.
     * 
     * @param detailSourceId
     */
    public void setDetailSourceId(java.lang.String detailSourceId) {
        this.detailSourceId = detailSourceId;
    }


    /**
     * Gets the scenicChannelName value for this DetailSourceBean.
     * 
     * @return scenicChannelName
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.ScenicChannelName getScenicChannelName() {
        return scenicChannelName;
    }


    /**
     * Sets the scenicChannelName value for this DetailSourceBean.
     * 
     * @param scenicChannelName
     */
    public void setScenicChannelName(com.lvmama.passport.processor.impl.client.shandong.model.ScenicChannelName scenicChannelName) {
        this.scenicChannelName = scenicChannelName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DetailSourceBean)) return false;
        DetailSourceBean other = (DetailSourceBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.activityEndDate==null && other.getActivityEndDate()==null) || 
             (this.activityEndDate!=null &&
              this.activityEndDate.equals(other.getActivityEndDate()))) &&
            ((this.activityName==null && other.getActivityName()==null) || 
             (this.activityName!=null &&
              this.activityName.equals(other.getActivityName()))) &&
            ((this.activityStartDate==null && other.getActivityStartDate()==null) || 
             (this.activityStartDate!=null &&
              this.activityStartDate.equals(other.getActivityStartDate()))) &&
            ((this.describe==null && other.getDescribe()==null) || 
             (this.describe!=null &&
              this.describe.equals(other.getDescribe()))) &&
            ((this.detailSourceId==null && other.getDetailSourceId()==null) || 
             (this.detailSourceId!=null &&
              this.detailSourceId.equals(other.getDetailSourceId()))) &&
            ((this.scenicChannelName==null && other.getScenicChannelName()==null) || 
             (this.scenicChannelName!=null &&
              this.scenicChannelName.equals(other.getScenicChannelName())));
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
        if (getActivityEndDate() != null) {
            _hashCode += getActivityEndDate().hashCode();
        }
        if (getActivityName() != null) {
            _hashCode += getActivityName().hashCode();
        }
        if (getActivityStartDate() != null) {
            _hashCode += getActivityStartDate().hashCode();
        }
        if (getDescribe() != null) {
            _hashCode += getDescribe().hashCode();
        }
        if (getDetailSourceId() != null) {
            _hashCode += getDetailSourceId().hashCode();
        }
        if (getScenicChannelName() != null) {
            _hashCode += getScenicChannelName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DetailSourceBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://product.bean.xz.com", "DetailSourceBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activityEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "activityEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activityName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "activityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activityStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "activityStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("describe");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "describe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailSourceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "detailSourceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicChannelName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "scenicChannelName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "ScenicChannelName"));
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
