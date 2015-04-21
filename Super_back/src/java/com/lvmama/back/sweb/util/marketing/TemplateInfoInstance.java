package com.lvmama.back.sweb.util.marketing;
/**
 * TemplateInfoInstance.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class TemplateInfoInstance  implements java.io.Serializable {
    private java.lang.String instanceName;

    private NameValuePair[] nameValues;

    public TemplateInfoInstance() {
    }

    public TemplateInfoInstance(
           java.lang.String instanceName,
           NameValuePair[] nameValues) {
           this.instanceName = instanceName;
           this.nameValues = nameValues;
    }


    /**
     * Gets the instanceName value for this TemplateInfoInstance.
     * 
     * @return instanceName
     */
    public java.lang.String getInstanceName() {
        return instanceName;
    }


    /**
     * Sets the instanceName value for this TemplateInfoInstance.
     * 
     * @param instanceName
     */
    public void setInstanceName(java.lang.String instanceName) {
        this.instanceName = instanceName;
    }


    /**
     * Gets the nameValues value for this TemplateInfoInstance.
     * 
     * @return nameValues
     */
    public NameValuePair[] getNameValues() {
        return nameValues;
    }


    /**
     * Sets the nameValues value for this TemplateInfoInstance.
     * 
     * @param nameValues
     */
    public void setNameValues(NameValuePair[] nameValues) {
        this.nameValues = nameValues;
    }

    public NameValuePair getNameValues(int i) {
        return this.nameValues[i];
    }

    public void setNameValues(int i, NameValuePair _value) {
        this.nameValues[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TemplateInfoInstance)) return false;
        TemplateInfoInstance other = (TemplateInfoInstance) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.instanceName==null && other.getInstanceName()==null) || 
             (this.instanceName!=null &&
              this.instanceName.equals(other.getInstanceName()))) &&
            ((this.nameValues==null && other.getNameValues()==null) || 
             (this.nameValues!=null &&
              java.util.Arrays.equals(this.nameValues, other.getNameValues())));
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
        if (getInstanceName() != null) {
            _hashCode += getInstanceName().hashCode();
        }
        if (getNameValues() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNameValues());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNameValues(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TemplateInfoInstance.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TemplateInfoInstance"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instanceName");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "instanceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameValues");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "nameValues"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
