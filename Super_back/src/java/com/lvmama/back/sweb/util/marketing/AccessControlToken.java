package com.lvmama.back.sweb.util.marketing;
/**
 * AccessControlToken.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */


/**
 * permissionMask field value is  combination of the following flags:
 * 1 permission to read items content
 *             2 permission to traverse directory
 *             4 permission to change items content
 *             8 permission to delete an item
 *             16 permission to assign permissions to others
 *             32 can take ownership of the item
 *             2048 permission to run a publisher report live
 *             4096 permission to schedule a publisher report
 *             8192 permission to view output of a publisher report
 */
public class AccessControlToken  implements java.io.Serializable {
    private Account account;

    private int permissionMask;

    public AccessControlToken() {
    }

    public AccessControlToken(
           Account account,
           int permissionMask) {
           this.account = account;
           this.permissionMask = permissionMask;
    }


    /**
     * Gets the account value for this AccessControlToken.
     * 
     * @return account
     */
    public Account getAccount() {
        return account;
    }


    /**
     * Sets the account value for this AccessControlToken.
     * 
     * @param account
     */
    public void setAccount(Account account) {
        this.account = account;
    }


    /**
     * Gets the permissionMask value for this AccessControlToken.
     * 
     * @return permissionMask
     */
    public int getPermissionMask() {
        return permissionMask;
    }


    /**
     * Sets the permissionMask value for this AccessControlToken.
     * 
     * @param permissionMask
     */
    public void setPermissionMask(int permissionMask) {
        this.permissionMask = permissionMask;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccessControlToken)) return false;
        AccessControlToken other = (AccessControlToken) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            this.permissionMask == other.getPermissionMask();
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
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        _hashCode += getPermissionMask();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccessControlToken.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "AccessControlToken"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "account"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Account"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permissionMask");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "permissionMask"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
