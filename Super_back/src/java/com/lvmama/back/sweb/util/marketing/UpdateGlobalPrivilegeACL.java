package com.lvmama.back.sweb.util.marketing;
/**
 * UpdateGlobalPrivilegeACL.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class UpdateGlobalPrivilegeACL  implements java.io.Serializable {
    private java.lang.String privilegeName;

    private ACL acl;

    private UpdateACLParams updateACLParams;

    private java.lang.String sessionID;

    public UpdateGlobalPrivilegeACL() {
    }

    public UpdateGlobalPrivilegeACL(
           java.lang.String privilegeName,
           ACL acl,
           UpdateACLParams updateACLParams,
           java.lang.String sessionID) {
           this.privilegeName = privilegeName;
           this.acl = acl;
           this.updateACLParams = updateACLParams;
           this.sessionID = sessionID;
    }


    /**
     * Gets the privilegeName value for this UpdateGlobalPrivilegeACL.
     * 
     * @return privilegeName
     */
    public java.lang.String getPrivilegeName() {
        return privilegeName;
    }


    /**
     * Sets the privilegeName value for this UpdateGlobalPrivilegeACL.
     * 
     * @param privilegeName
     */
    public void setPrivilegeName(java.lang.String privilegeName) {
        this.privilegeName = privilegeName;
    }


    /**
     * Gets the acl value for this UpdateGlobalPrivilegeACL.
     * 
     * @return acl
     */
    public ACL getAcl() {
        return acl;
    }


    /**
     * Sets the acl value for this UpdateGlobalPrivilegeACL.
     * 
     * @param acl
     */
    public void setAcl(ACL acl) {
        this.acl = acl;
    }


    /**
     * Gets the updateACLParams value for this UpdateGlobalPrivilegeACL.
     * 
     * @return updateACLParams
     */
    public UpdateACLParams getUpdateACLParams() {
        return updateACLParams;
    }


    /**
     * Sets the updateACLParams value for this UpdateGlobalPrivilegeACL.
     * 
     * @param updateACLParams
     */
    public void setUpdateACLParams(UpdateACLParams updateACLParams) {
        this.updateACLParams = updateACLParams;
    }


    /**
     * Gets the sessionID value for this UpdateGlobalPrivilegeACL.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this UpdateGlobalPrivilegeACL.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateGlobalPrivilegeACL)) return false;
        UpdateGlobalPrivilegeACL other = (UpdateGlobalPrivilegeACL) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.privilegeName==null && other.getPrivilegeName()==null) || 
             (this.privilegeName!=null &&
              this.privilegeName.equals(other.getPrivilegeName()))) &&
            ((this.acl==null && other.getAcl()==null) || 
             (this.acl!=null &&
              this.acl.equals(other.getAcl()))) &&
            ((this.updateACLParams==null && other.getUpdateACLParams()==null) || 
             (this.updateACLParams!=null &&
              this.updateACLParams.equals(other.getUpdateACLParams()))) &&
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
        if (getPrivilegeName() != null) {
            _hashCode += getPrivilegeName().hashCode();
        }
        if (getAcl() != null) {
            _hashCode += getAcl().hashCode();
        }
        if (getUpdateACLParams() != null) {
            _hashCode += getUpdateACLParams().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateGlobalPrivilegeACL.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">updateGlobalPrivilegeACL"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("privilegeName");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "privilegeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acl");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "acl"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ACL"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateACLParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "updateACLParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "UpdateACLParams"));
        elemField.setNillable(true);
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
