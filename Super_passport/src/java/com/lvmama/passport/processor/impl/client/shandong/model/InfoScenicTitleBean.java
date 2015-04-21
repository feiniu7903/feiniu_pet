/**
 * InfoScenicTitleBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class InfoScenicTitleBean  implements java.io.Serializable {
    private java.util.Calendar modTime;

    private java.lang.String otherTitles;

    private java.lang.String scenicId;

    private java.lang.String scenicTitleId;

    private java.lang.String scenicTitleName;

    private java.lang.String scenicTitleRelaId;

    private java.lang.String userId;

    public InfoScenicTitleBean() {
    }

    public InfoScenicTitleBean(
           java.util.Calendar modTime,
           java.lang.String otherTitles,
           java.lang.String scenicId,
           java.lang.String scenicTitleId,
           java.lang.String scenicTitleName,
           java.lang.String scenicTitleRelaId,
           java.lang.String userId) {
           this.modTime = modTime;
           this.otherTitles = otherTitles;
           this.scenicId = scenicId;
           this.scenicTitleId = scenicTitleId;
           this.scenicTitleName = scenicTitleName;
           this.scenicTitleRelaId = scenicTitleRelaId;
           this.userId = userId;
    }


    /**
     * Gets the modTime value for this InfoScenicTitleBean.
     * 
     * @return modTime
     */
    public java.util.Calendar getModTime() {
        return modTime;
    }


    /**
     * Sets the modTime value for this InfoScenicTitleBean.
     * 
     * @param modTime
     */
    public void setModTime(java.util.Calendar modTime) {
        this.modTime = modTime;
    }


    /**
     * Gets the otherTitles value for this InfoScenicTitleBean.
     * 
     * @return otherTitles
     */
    public java.lang.String getOtherTitles() {
        return otherTitles;
    }


    /**
     * Sets the otherTitles value for this InfoScenicTitleBean.
     * 
     * @param otherTitles
     */
    public void setOtherTitles(java.lang.String otherTitles) {
        this.otherTitles = otherTitles;
    }


    /**
     * Gets the scenicId value for this InfoScenicTitleBean.
     * 
     * @return scenicId
     */
    public java.lang.String getScenicId() {
        return scenicId;
    }


    /**
     * Sets the scenicId value for this InfoScenicTitleBean.
     * 
     * @param scenicId
     */
    public void setScenicId(java.lang.String scenicId) {
        this.scenicId = scenicId;
    }


    /**
     * Gets the scenicTitleId value for this InfoScenicTitleBean.
     * 
     * @return scenicTitleId
     */
    public java.lang.String getScenicTitleId() {
        return scenicTitleId;
    }


    /**
     * Sets the scenicTitleId value for this InfoScenicTitleBean.
     * 
     * @param scenicTitleId
     */
    public void setScenicTitleId(java.lang.String scenicTitleId) {
        this.scenicTitleId = scenicTitleId;
    }


    /**
     * Gets the scenicTitleName value for this InfoScenicTitleBean.
     * 
     * @return scenicTitleName
     */
    public java.lang.String getScenicTitleName() {
        return scenicTitleName;
    }


    /**
     * Sets the scenicTitleName value for this InfoScenicTitleBean.
     * 
     * @param scenicTitleName
     */
    public void setScenicTitleName(java.lang.String scenicTitleName) {
        this.scenicTitleName = scenicTitleName;
    }


    /**
     * Gets the scenicTitleRelaId value for this InfoScenicTitleBean.
     * 
     * @return scenicTitleRelaId
     */
    public java.lang.String getScenicTitleRelaId() {
        return scenicTitleRelaId;
    }


    /**
     * Sets the scenicTitleRelaId value for this InfoScenicTitleBean.
     * 
     * @param scenicTitleRelaId
     */
    public void setScenicTitleRelaId(java.lang.String scenicTitleRelaId) {
        this.scenicTitleRelaId = scenicTitleRelaId;
    }


    /**
     * Gets the userId value for this InfoScenicTitleBean.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this InfoScenicTitleBean.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoScenicTitleBean)) return false;
        InfoScenicTitleBean other = (InfoScenicTitleBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.modTime==null && other.getModTime()==null) || 
             (this.modTime!=null &&
              this.modTime.equals(other.getModTime()))) &&
            ((this.otherTitles==null && other.getOtherTitles()==null) || 
             (this.otherTitles!=null &&
              this.otherTitles.equals(other.getOtherTitles()))) &&
            ((this.scenicId==null && other.getScenicId()==null) || 
             (this.scenicId!=null &&
              this.scenicId.equals(other.getScenicId()))) &&
            ((this.scenicTitleId==null && other.getScenicTitleId()==null) || 
             (this.scenicTitleId!=null &&
              this.scenicTitleId.equals(other.getScenicTitleId()))) &&
            ((this.scenicTitleName==null && other.getScenicTitleName()==null) || 
             (this.scenicTitleName!=null &&
              this.scenicTitleName.equals(other.getScenicTitleName()))) &&
            ((this.scenicTitleRelaId==null && other.getScenicTitleRelaId()==null) || 
             (this.scenicTitleRelaId!=null &&
              this.scenicTitleRelaId.equals(other.getScenicTitleRelaId()))) &&
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
        if (getModTime() != null) {
            _hashCode += getModTime().hashCode();
        }
        if (getOtherTitles() != null) {
            _hashCode += getOtherTitles().hashCode();
        }
        if (getScenicId() != null) {
            _hashCode += getScenicId().hashCode();
        }
        if (getScenicTitleId() != null) {
            _hashCode += getScenicTitleId().hashCode();
        }
        if (getScenicTitleName() != null) {
            _hashCode += getScenicTitleName().hashCode();
        }
        if (getScenicTitleRelaId() != null) {
            _hashCode += getScenicTitleRelaId().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoScenicTitleBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://entity.xz.com", "InfoScenicTitleBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "modTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherTitles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "otherTitles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicTitleId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicTitleId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicTitleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicTitleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicTitleRelaId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicTitleRelaId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "userId"));
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
