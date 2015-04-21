/**
 * ContactBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class ContactBean  implements java.io.Serializable {
    private com.lvmama.passport.processor.impl.client.shandong.model.GeneralState auditState;

    private com.lvmama.passport.processor.impl.client.shandong.model.BusType busType;

    private java.lang.String contact;

    private java.lang.String contactId;

    private com.lvmama.passport.processor.impl.client.shandong.model.ContactType contactType;

    private java.util.Calendar modTime;

    private java.lang.String scenicId;

    private java.lang.String userId;

    public ContactBean() {
    }

    public ContactBean(
           com.lvmama.passport.processor.impl.client.shandong.model.GeneralState auditState,
           com.lvmama.passport.processor.impl.client.shandong.model.BusType busType,
           java.lang.String contact,
           java.lang.String contactId,
           com.lvmama.passport.processor.impl.client.shandong.model.ContactType contactType,
           java.util.Calendar modTime,
           java.lang.String scenicId,
           java.lang.String userId) {
           this.auditState = auditState;
           this.busType = busType;
           this.contact = contact;
           this.contactId = contactId;
           this.contactType = contactType;
           this.modTime = modTime;
           this.scenicId = scenicId;
           this.userId = userId;
    }


    /**
     * Gets the auditState value for this ContactBean.
     * 
     * @return auditState
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.GeneralState getAuditState() {
        return auditState;
    }


    /**
     * Sets the auditState value for this ContactBean.
     * 
     * @param auditState
     */
    public void setAuditState(com.lvmama.passport.processor.impl.client.shandong.model.GeneralState auditState) {
        this.auditState = auditState;
    }


    /**
     * Gets the busType value for this ContactBean.
     * 
     * @return busType
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.BusType getBusType() {
        return busType;
    }


    /**
     * Sets the busType value for this ContactBean.
     * 
     * @param busType
     */
    public void setBusType(com.lvmama.passport.processor.impl.client.shandong.model.BusType busType) {
        this.busType = busType;
    }


    /**
     * Gets the contact value for this ContactBean.
     * 
     * @return contact
     */
    public java.lang.String getContact() {
        return contact;
    }


    /**
     * Sets the contact value for this ContactBean.
     * 
     * @param contact
     */
    public void setContact(java.lang.String contact) {
        this.contact = contact;
    }


    /**
     * Gets the contactId value for this ContactBean.
     * 
     * @return contactId
     */
    public java.lang.String getContactId() {
        return contactId;
    }


    /**
     * Sets the contactId value for this ContactBean.
     * 
     * @param contactId
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
    }


    /**
     * Gets the contactType value for this ContactBean.
     * 
     * @return contactType
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.ContactType getContactType() {
        return contactType;
    }


    /**
     * Sets the contactType value for this ContactBean.
     * 
     * @param contactType
     */
    public void setContactType(com.lvmama.passport.processor.impl.client.shandong.model.ContactType contactType) {
        this.contactType = contactType;
    }


    /**
     * Gets the modTime value for this ContactBean.
     * 
     * @return modTime
     */
    public java.util.Calendar getModTime() {
        return modTime;
    }


    /**
     * Sets the modTime value for this ContactBean.
     * 
     * @param modTime
     */
    public void setModTime(java.util.Calendar modTime) {
        this.modTime = modTime;
    }


    /**
     * Gets the scenicId value for this ContactBean.
     * 
     * @return scenicId
     */
    public java.lang.String getScenicId() {
        return scenicId;
    }


    /**
     * Sets the scenicId value for this ContactBean.
     * 
     * @param scenicId
     */
    public void setScenicId(java.lang.String scenicId) {
        this.scenicId = scenicId;
    }


    /**
     * Gets the userId value for this ContactBean.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ContactBean.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContactBean)) return false;
        ContactBean other = (ContactBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.auditState==null && other.getAuditState()==null) || 
             (this.auditState!=null &&
              this.auditState.equals(other.getAuditState()))) &&
            ((this.busType==null && other.getBusType()==null) || 
             (this.busType!=null &&
              this.busType.equals(other.getBusType()))) &&
            ((this.contact==null && other.getContact()==null) || 
             (this.contact!=null &&
              this.contact.equals(other.getContact()))) &&
            ((this.contactId==null && other.getContactId()==null) || 
             (this.contactId!=null &&
              this.contactId.equals(other.getContactId()))) &&
            ((this.contactType==null && other.getContactType()==null) || 
             (this.contactType!=null &&
              this.contactType.equals(other.getContactType()))) &&
            ((this.modTime==null && other.getModTime()==null) || 
             (this.modTime!=null &&
              this.modTime.equals(other.getModTime()))) &&
            ((this.scenicId==null && other.getScenicId()==null) || 
             (this.scenicId!=null &&
              this.scenicId.equals(other.getScenicId()))) &&
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
        if (getAuditState() != null) {
            _hashCode += getAuditState().hashCode();
        }
        if (getBusType() != null) {
            _hashCode += getBusType().hashCode();
        }
        if (getContact() != null) {
            _hashCode += getContact().hashCode();
        }
        if (getContactId() != null) {
            _hashCode += getContactId().hashCode();
        }
        if (getContactType() != null) {
            _hashCode += getContactType().hashCode();
        }
        if (getModTime() != null) {
            _hashCode += getModTime().hashCode();
        }
        if (getScenicId() != null) {
            _hashCode += getScenicId().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContactBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ContactBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auditState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "auditState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "GeneralState"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("busType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "busType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "BusType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "contact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "contactId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "contactType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "ContactType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "modTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "scenicId"));
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
