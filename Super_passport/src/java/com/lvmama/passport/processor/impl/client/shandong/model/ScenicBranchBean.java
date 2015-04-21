/**
 * ScenicBranchBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class ScenicBranchBean  implements java.io.Serializable {
    private java.lang.String branchDesc;

    private java.lang.String branchDescM;

    private java.lang.String branchName;

    private com.lvmama.passport.processor.impl.client.shandong.model.ScenicBranchPictureBean[] branchPictures;

    private java.util.Calendar modTime;

    private java.lang.String scenicBranchId;

    private java.lang.String scenicId;

    private com.lvmama.passport.processor.impl.client.shandong.model.State state;

    private java.lang.Integer type;

    private java.lang.String userId;

    public ScenicBranchBean() {
    }

    public ScenicBranchBean(
           java.lang.String branchDesc,
           java.lang.String branchDescM,
           java.lang.String branchName,
           com.lvmama.passport.processor.impl.client.shandong.model.ScenicBranchPictureBean[] branchPictures,
           java.util.Calendar modTime,
           java.lang.String scenicBranchId,
           java.lang.String scenicId,
           com.lvmama.passport.processor.impl.client.shandong.model.State state,
           java.lang.Integer type,
           java.lang.String userId) {
           this.branchDesc = branchDesc;
           this.branchDescM = branchDescM;
           this.branchName = branchName;
           this.branchPictures = branchPictures;
           this.modTime = modTime;
           this.scenicBranchId = scenicBranchId;
           this.scenicId = scenicId;
           this.state = state;
           this.type = type;
           this.userId = userId;
    }


    /**
     * Gets the branchDesc value for this ScenicBranchBean.
     * 
     * @return branchDesc
     */
    public java.lang.String getBranchDesc() {
        return branchDesc;
    }


    /**
     * Sets the branchDesc value for this ScenicBranchBean.
     * 
     * @param branchDesc
     */
    public void setBranchDesc(java.lang.String branchDesc) {
        this.branchDesc = branchDesc;
    }


    /**
     * Gets the branchDescM value for this ScenicBranchBean.
     * 
     * @return branchDescM
     */
    public java.lang.String getBranchDescM() {
        return branchDescM;
    }


    /**
     * Sets the branchDescM value for this ScenicBranchBean.
     * 
     * @param branchDescM
     */
    public void setBranchDescM(java.lang.String branchDescM) {
        this.branchDescM = branchDescM;
    }


    /**
     * Gets the branchName value for this ScenicBranchBean.
     * 
     * @return branchName
     */
    public java.lang.String getBranchName() {
        return branchName;
    }


    /**
     * Sets the branchName value for this ScenicBranchBean.
     * 
     * @param branchName
     */
    public void setBranchName(java.lang.String branchName) {
        this.branchName = branchName;
    }


    /**
     * Gets the branchPictures value for this ScenicBranchBean.
     * 
     * @return branchPictures
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.ScenicBranchPictureBean[] getBranchPictures() {
        return branchPictures;
    }


    /**
     * Sets the branchPictures value for this ScenicBranchBean.
     * 
     * @param branchPictures
     */
    public void setBranchPictures(com.lvmama.passport.processor.impl.client.shandong.model.ScenicBranchPictureBean[] branchPictures) {
        this.branchPictures = branchPictures;
    }


    /**
     * Gets the modTime value for this ScenicBranchBean.
     * 
     * @return modTime
     */
    public java.util.Calendar getModTime() {
        return modTime;
    }


    /**
     * Sets the modTime value for this ScenicBranchBean.
     * 
     * @param modTime
     */
    public void setModTime(java.util.Calendar modTime) {
        this.modTime = modTime;
    }


    /**
     * Gets the scenicBranchId value for this ScenicBranchBean.
     * 
     * @return scenicBranchId
     */
    public java.lang.String getScenicBranchId() {
        return scenicBranchId;
    }


    /**
     * Sets the scenicBranchId value for this ScenicBranchBean.
     * 
     * @param scenicBranchId
     */
    public void setScenicBranchId(java.lang.String scenicBranchId) {
        this.scenicBranchId = scenicBranchId;
    }


    /**
     * Gets the scenicId value for this ScenicBranchBean.
     * 
     * @return scenicId
     */
    public java.lang.String getScenicId() {
        return scenicId;
    }


    /**
     * Sets the scenicId value for this ScenicBranchBean.
     * 
     * @param scenicId
     */
    public void setScenicId(java.lang.String scenicId) {
        this.scenicId = scenicId;
    }


    /**
     * Gets the state value for this ScenicBranchBean.
     * 
     * @return state
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.State getState() {
        return state;
    }


    /**
     * Sets the state value for this ScenicBranchBean.
     * 
     * @param state
     */
    public void setState(com.lvmama.passport.processor.impl.client.shandong.model.State state) {
        this.state = state;
    }


    /**
     * Gets the type value for this ScenicBranchBean.
     * 
     * @return type
     */
    public java.lang.Integer getType() {
        return type;
    }


    /**
     * Sets the type value for this ScenicBranchBean.
     * 
     * @param type
     */
    public void setType(java.lang.Integer type) {
        this.type = type;
    }


    /**
     * Gets the userId value for this ScenicBranchBean.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ScenicBranchBean.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ScenicBranchBean)) return false;
        ScenicBranchBean other = (ScenicBranchBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.branchDesc==null && other.getBranchDesc()==null) || 
             (this.branchDesc!=null &&
              this.branchDesc.equals(other.getBranchDesc()))) &&
            ((this.branchDescM==null && other.getBranchDescM()==null) || 
             (this.branchDescM!=null &&
              this.branchDescM.equals(other.getBranchDescM()))) &&
            ((this.branchName==null && other.getBranchName()==null) || 
             (this.branchName!=null &&
              this.branchName.equals(other.getBranchName()))) &&
            ((this.branchPictures==null && other.getBranchPictures()==null) || 
             (this.branchPictures!=null &&
              java.util.Arrays.equals(this.branchPictures, other.getBranchPictures()))) &&
            ((this.modTime==null && other.getModTime()==null) || 
             (this.modTime!=null &&
              this.modTime.equals(other.getModTime()))) &&
            ((this.scenicBranchId==null && other.getScenicBranchId()==null) || 
             (this.scenicBranchId!=null &&
              this.scenicBranchId.equals(other.getScenicBranchId()))) &&
            ((this.scenicId==null && other.getScenicId()==null) || 
             (this.scenicId!=null &&
              this.scenicId.equals(other.getScenicId()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
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
        if (getBranchDesc() != null) {
            _hashCode += getBranchDesc().hashCode();
        }
        if (getBranchDescM() != null) {
            _hashCode += getBranchDescM().hashCode();
        }
        if (getBranchName() != null) {
            _hashCode += getBranchName().hashCode();
        }
        if (getBranchPictures() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBranchPictures());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBranchPictures(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getModTime() != null) {
            _hashCode += getModTime().hashCode();
        }
        if (getScenicBranchId() != null) {
            _hashCode += getScenicBranchId().hashCode();
        }
        if (getScenicId() != null) {
            _hashCode += getScenicId().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ScenicBranchBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicBranchBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "branchDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchDescM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "branchDescM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "branchName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchPictures");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "branchPictures"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicBranchPictureBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicBranchPictureBean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "modTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicBranchId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "scenicBranchId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "scenicId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "State"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
