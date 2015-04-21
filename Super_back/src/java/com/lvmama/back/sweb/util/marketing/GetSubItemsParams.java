package com.lvmama.back.sweb.util.marketing;
/**
 * GetSubItemsParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class GetSubItemsParams  implements java.io.Serializable {
    private GetSubItemsFilter filter;

    private boolean includeACL;

    private int withPermission;

    private int withPermissionMask;

    private int withAttributes;

    private int withAttributesMask;

    private boolean preserveOriginalLinkPath;

    public GetSubItemsParams() {
    }

    public GetSubItemsParams(
           GetSubItemsFilter filter,
           boolean includeACL,
           int withPermission,
           int withPermissionMask,
           int withAttributes,
           int withAttributesMask,
           boolean preserveOriginalLinkPath) {
           this.filter = filter;
           this.includeACL = includeACL;
           this.withPermission = withPermission;
           this.withPermissionMask = withPermissionMask;
           this.withAttributes = withAttributes;
           this.withAttributesMask = withAttributesMask;
           this.preserveOriginalLinkPath = preserveOriginalLinkPath;
    }


    /**
     * Gets the filter value for this GetSubItemsParams.
     * 
     * @return filter
     */
    public GetSubItemsFilter getFilter() {
        return filter;
    }


    /**
     * Sets the filter value for this GetSubItemsParams.
     * 
     * @param filter
     */
    public void setFilter(GetSubItemsFilter filter) {
        this.filter = filter;
    }


    /**
     * Gets the includeACL value for this GetSubItemsParams.
     * 
     * @return includeACL
     */
    public boolean isIncludeACL() {
        return includeACL;
    }


    /**
     * Sets the includeACL value for this GetSubItemsParams.
     * 
     * @param includeACL
     */
    public void setIncludeACL(boolean includeACL) {
        this.includeACL = includeACL;
    }


    /**
     * Gets the withPermission value for this GetSubItemsParams.
     * 
     * @return withPermission
     */
    public int getWithPermission() {
        return withPermission;
    }


    /**
     * Sets the withPermission value for this GetSubItemsParams.
     * 
     * @param withPermission
     */
    public void setWithPermission(int withPermission) {
        this.withPermission = withPermission;
    }


    /**
     * Gets the withPermissionMask value for this GetSubItemsParams.
     * 
     * @return withPermissionMask
     */
    public int getWithPermissionMask() {
        return withPermissionMask;
    }


    /**
     * Sets the withPermissionMask value for this GetSubItemsParams.
     * 
     * @param withPermissionMask
     */
    public void setWithPermissionMask(int withPermissionMask) {
        this.withPermissionMask = withPermissionMask;
    }


    /**
     * Gets the withAttributes value for this GetSubItemsParams.
     * 
     * @return withAttributes
     */
    public int getWithAttributes() {
        return withAttributes;
    }


    /**
     * Sets the withAttributes value for this GetSubItemsParams.
     * 
     * @param withAttributes
     */
    public void setWithAttributes(int withAttributes) {
        this.withAttributes = withAttributes;
    }


    /**
     * Gets the withAttributesMask value for this GetSubItemsParams.
     * 
     * @return withAttributesMask
     */
    public int getWithAttributesMask() {
        return withAttributesMask;
    }


    /**
     * Sets the withAttributesMask value for this GetSubItemsParams.
     * 
     * @param withAttributesMask
     */
    public void setWithAttributesMask(int withAttributesMask) {
        this.withAttributesMask = withAttributesMask;
    }


    /**
     * Gets the preserveOriginalLinkPath value for this GetSubItemsParams.
     * 
     * @return preserveOriginalLinkPath
     */
    public boolean isPreserveOriginalLinkPath() {
        return preserveOriginalLinkPath;
    }


    /**
     * Sets the preserveOriginalLinkPath value for this GetSubItemsParams.
     * 
     * @param preserveOriginalLinkPath
     */
    public void setPreserveOriginalLinkPath(boolean preserveOriginalLinkPath) {
        this.preserveOriginalLinkPath = preserveOriginalLinkPath;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetSubItemsParams)) return false;
        GetSubItemsParams other = (GetSubItemsParams) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.filter==null && other.getFilter()==null) || 
             (this.filter!=null &&
              this.filter.equals(other.getFilter()))) &&
            this.includeACL == other.isIncludeACL() &&
            this.withPermission == other.getWithPermission() &&
            this.withPermissionMask == other.getWithPermissionMask() &&
            this.withAttributes == other.getWithAttributes() &&
            this.withAttributesMask == other.getWithAttributesMask() &&
            this.preserveOriginalLinkPath == other.isPreserveOriginalLinkPath();
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
        if (getFilter() != null) {
            _hashCode += getFilter().hashCode();
        }
        _hashCode += (isIncludeACL() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getWithPermission();
        _hashCode += getWithPermissionMask();
        _hashCode += getWithAttributes();
        _hashCode += getWithAttributesMask();
        _hashCode += (isPreserveOriginalLinkPath() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSubItemsParams.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GetSubItemsParams"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filter");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "filter"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GetSubItemsFilter"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("includeACL");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "includeACL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("withPermission");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "withPermission"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("withPermissionMask");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "withPermissionMask"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("withAttributes");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "withAttributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("withAttributesMask");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "withAttributesMask"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preserveOriginalLinkPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "preserveOriginalLinkPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
