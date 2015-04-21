package com.lvmama.back.sweb.util.marketing;
/**
 * IsMember.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class IsMember  implements java.io.Serializable {
    private Account[] group;

    private Account[] member;

    private java.lang.Boolean expandGroups;

    private java.lang.String sessionID;

    public IsMember() {
    }

    public IsMember(
           Account[] group,
           Account[] member,
           java.lang.Boolean expandGroups,
           java.lang.String sessionID) {
           this.group = group;
           this.member = member;
           this.expandGroups = expandGroups;
           this.sessionID = sessionID;
    }


    /**
     * Gets the group value for this IsMember.
     * 
     * @return group
     */
    public Account[] getGroup() {
        return group;
    }


    /**
     * Sets the group value for this IsMember.
     * 
     * @param group
     */
    public void setGroup(Account[] group) {
        this.group = group;
    }

    public Account getGroup(int i) {
        return this.group[i];
    }

    public void setGroup(int i, Account _value) {
        this.group[i] = _value;
    }


    /**
     * Gets the member value for this IsMember.
     * 
     * @return member
     */
    public Account[] getMember() {
        return member;
    }


    /**
     * Sets the member value for this IsMember.
     * 
     * @param member
     */
    public void setMember(Account[] member) {
        this.member = member;
    }

    public Account getMember(int i) {
        return this.member[i];
    }

    public void setMember(int i, Account _value) {
        this.member[i] = _value;
    }


    /**
     * Gets the expandGroups value for this IsMember.
     * 
     * @return expandGroups
     */
    public java.lang.Boolean getExpandGroups() {
        return expandGroups;
    }


    /**
     * Sets the expandGroups value for this IsMember.
     * 
     * @param expandGroups
     */
    public void setExpandGroups(java.lang.Boolean expandGroups) {
        this.expandGroups = expandGroups;
    }


    /**
     * Gets the sessionID value for this IsMember.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this IsMember.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IsMember)) return false;
        IsMember other = (IsMember) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.group==null && other.getGroup()==null) || 
             (this.group!=null &&
              java.util.Arrays.equals(this.group, other.getGroup()))) &&
            ((this.member==null && other.getMember()==null) || 
             (this.member!=null &&
              java.util.Arrays.equals(this.member, other.getMember()))) &&
            ((this.expandGroups==null && other.getExpandGroups()==null) || 
             (this.expandGroups!=null &&
              this.expandGroups.equals(other.getExpandGroups()))) &&
            ((this.sessionID==null && other.getSessionID()==null) || 
             (this.sessionID!=null &&
              this.sessionID.equals(other.getSessionID())));
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
        if (getGroup() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGroup());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGroup(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMember() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMember());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMember(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExpandGroups() != null) {
            _hashCode += getExpandGroups().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IsMember.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">isMember"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("group");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "group"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Account"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("member");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "member"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Account"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expandGroups");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "expandGroups"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
