/**
 * ScenicTypeBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class ScenicTypeBean  implements java.io.Serializable {
    private java.lang.Integer grade;

    private java.lang.String id;

    private java.lang.Integer modifier;

    private java.util.Calendar modtime;

    private java.lang.String name;

    private java.lang.String sceniTypeName;

    private java.lang.String scenicTypeId;

    private java.lang.String scenicTypeIdP;

    private java.lang.String supperId;

    private java.lang.String supperName;

    private java.lang.String userId;

    public ScenicTypeBean() {
    }

    public ScenicTypeBean(
           java.lang.Integer grade,
           java.lang.String id,
           java.lang.Integer modifier,
           java.util.Calendar modtime,
           java.lang.String name,
           java.lang.String sceniTypeName,
           java.lang.String scenicTypeId,
           java.lang.String scenicTypeIdP,
           java.lang.String supperId,
           java.lang.String supperName,
           java.lang.String userId) {
           this.grade = grade;
           this.id = id;
           this.modifier = modifier;
           this.modtime = modtime;
           this.name = name;
           this.sceniTypeName = sceniTypeName;
           this.scenicTypeId = scenicTypeId;
           this.scenicTypeIdP = scenicTypeIdP;
           this.supperId = supperId;
           this.supperName = supperName;
           this.userId = userId;
    }


    /**
     * Gets the grade value for this ScenicTypeBean.
     * 
     * @return grade
     */
    public java.lang.Integer getGrade() {
        return grade;
    }


    /**
     * Sets the grade value for this ScenicTypeBean.
     * 
     * @param grade
     */
    public void setGrade(java.lang.Integer grade) {
        this.grade = grade;
    }


    /**
     * Gets the id value for this ScenicTypeBean.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this ScenicTypeBean.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the modifier value for this ScenicTypeBean.
     * 
     * @return modifier
     */
    public java.lang.Integer getModifier() {
        return modifier;
    }


    /**
     * Sets the modifier value for this ScenicTypeBean.
     * 
     * @param modifier
     */
    public void setModifier(java.lang.Integer modifier) {
        this.modifier = modifier;
    }


    /**
     * Gets the modtime value for this ScenicTypeBean.
     * 
     * @return modtime
     */
    public java.util.Calendar getModtime() {
        return modtime;
    }


    /**
     * Sets the modtime value for this ScenicTypeBean.
     * 
     * @param modtime
     */
    public void setModtime(java.util.Calendar modtime) {
        this.modtime = modtime;
    }


    /**
     * Gets the name value for this ScenicTypeBean.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ScenicTypeBean.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the sceniTypeName value for this ScenicTypeBean.
     * 
     * @return sceniTypeName
     */
    public java.lang.String getSceniTypeName() {
        return sceniTypeName;
    }


    /**
     * Sets the sceniTypeName value for this ScenicTypeBean.
     * 
     * @param sceniTypeName
     */
    public void setSceniTypeName(java.lang.String sceniTypeName) {
        this.sceniTypeName = sceniTypeName;
    }


    /**
     * Gets the scenicTypeId value for this ScenicTypeBean.
     * 
     * @return scenicTypeId
     */
    public java.lang.String getScenicTypeId() {
        return scenicTypeId;
    }


    /**
     * Sets the scenicTypeId value for this ScenicTypeBean.
     * 
     * @param scenicTypeId
     */
    public void setScenicTypeId(java.lang.String scenicTypeId) {
        this.scenicTypeId = scenicTypeId;
    }


    /**
     * Gets the scenicTypeIdP value for this ScenicTypeBean.
     * 
     * @return scenicTypeIdP
     */
    public java.lang.String getScenicTypeIdP() {
        return scenicTypeIdP;
    }


    /**
     * Sets the scenicTypeIdP value for this ScenicTypeBean.
     * 
     * @param scenicTypeIdP
     */
    public void setScenicTypeIdP(java.lang.String scenicTypeIdP) {
        this.scenicTypeIdP = scenicTypeIdP;
    }


    /**
     * Gets the supperId value for this ScenicTypeBean.
     * 
     * @return supperId
     */
    public java.lang.String getSupperId() {
        return supperId;
    }


    /**
     * Sets the supperId value for this ScenicTypeBean.
     * 
     * @param supperId
     */
    public void setSupperId(java.lang.String supperId) {
        this.supperId = supperId;
    }


    /**
     * Gets the supperName value for this ScenicTypeBean.
     * 
     * @return supperName
     */
    public java.lang.String getSupperName() {
        return supperName;
    }


    /**
     * Sets the supperName value for this ScenicTypeBean.
     * 
     * @param supperName
     */
    public void setSupperName(java.lang.String supperName) {
        this.supperName = supperName;
    }


    /**
     * Gets the userId value for this ScenicTypeBean.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ScenicTypeBean.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ScenicTypeBean)) return false;
        ScenicTypeBean other = (ScenicTypeBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.grade==null && other.getGrade()==null) || 
             (this.grade!=null &&
              this.grade.equals(other.getGrade()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.modifier==null && other.getModifier()==null) || 
             (this.modifier!=null &&
              this.modifier.equals(other.getModifier()))) &&
            ((this.modtime==null && other.getModtime()==null) || 
             (this.modtime!=null &&
              this.modtime.equals(other.getModtime()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.sceniTypeName==null && other.getSceniTypeName()==null) || 
             (this.sceniTypeName!=null &&
              this.sceniTypeName.equals(other.getSceniTypeName()))) &&
            ((this.scenicTypeId==null && other.getScenicTypeId()==null) || 
             (this.scenicTypeId!=null &&
              this.scenicTypeId.equals(other.getScenicTypeId()))) &&
            ((this.scenicTypeIdP==null && other.getScenicTypeIdP()==null) || 
             (this.scenicTypeIdP!=null &&
              this.scenicTypeIdP.equals(other.getScenicTypeIdP()))) &&
            ((this.supperId==null && other.getSupperId()==null) || 
             (this.supperId!=null &&
              this.supperId.equals(other.getSupperId()))) &&
            ((this.supperName==null && other.getSupperName()==null) || 
             (this.supperName!=null &&
              this.supperName.equals(other.getSupperName()))) &&
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
        if (getGrade() != null) {
            _hashCode += getGrade().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getModifier() != null) {
            _hashCode += getModifier().hashCode();
        }
        if (getModtime() != null) {
            _hashCode += getModtime().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSceniTypeName() != null) {
            _hashCode += getSceniTypeName().hashCode();
        }
        if (getScenicTypeId() != null) {
            _hashCode += getScenicTypeId().hashCode();
        }
        if (getScenicTypeIdP() != null) {
            _hashCode += getScenicTypeIdP().hashCode();
        }
        if (getSupperId() != null) {
            _hashCode += getSupperId().hashCode();
        }
        if (getSupperName() != null) {
            _hashCode += getSupperName().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ScenicTypeBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicTypeBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grade");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "grade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "modifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modtime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "modtime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sceniTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "sceniTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "scenicTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicTypeIdP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "scenicTypeIdP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supperId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "supperId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supperName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "supperName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
