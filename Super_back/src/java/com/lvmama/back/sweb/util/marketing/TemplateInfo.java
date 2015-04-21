package com.lvmama.back.sweb.util.marketing;
/**
 * TemplateInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class TemplateInfo  implements java.io.Serializable {
    private java.lang.String templateForEach;

    private java.lang.String templateIterator;

    private TemplateInfoInstance[] instance;

    public TemplateInfo() {
    }

    public TemplateInfo(
           java.lang.String templateForEach,
           java.lang.String templateIterator,
           TemplateInfoInstance[] instance) {
           this.templateForEach = templateForEach;
           this.templateIterator = templateIterator;
           this.instance = instance;
    }


    /**
     * Gets the templateForEach value for this TemplateInfo.
     * 
     * @return templateForEach
     */
    public java.lang.String getTemplateForEach() {
        return templateForEach;
    }


    /**
     * Sets the templateForEach value for this TemplateInfo.
     * 
     * @param templateForEach
     */
    public void setTemplateForEach(java.lang.String templateForEach) {
        this.templateForEach = templateForEach;
    }


    /**
     * Gets the templateIterator value for this TemplateInfo.
     * 
     * @return templateIterator
     */
    public java.lang.String getTemplateIterator() {
        return templateIterator;
    }


    /**
     * Sets the templateIterator value for this TemplateInfo.
     * 
     * @param templateIterator
     */
    public void setTemplateIterator(java.lang.String templateIterator) {
        this.templateIterator = templateIterator;
    }


    /**
     * Gets the instance value for this TemplateInfo.
     * 
     * @return instance
     */
    public TemplateInfoInstance[] getInstance() {
        return instance;
    }


    /**
     * Sets the instance value for this TemplateInfo.
     * 
     * @param instance
     */
    public void setInstance(TemplateInfoInstance[] instance) {
        this.instance = instance;
    }

    public TemplateInfoInstance getInstance(int i) {
        return this.instance[i];
    }

    public void setInstance(int i, TemplateInfoInstance _value) {
        this.instance[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TemplateInfo)) return false;
        TemplateInfo other = (TemplateInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.templateForEach==null && other.getTemplateForEach()==null) || 
             (this.templateForEach!=null &&
              this.templateForEach.equals(other.getTemplateForEach()))) &&
            ((this.templateIterator==null && other.getTemplateIterator()==null) || 
             (this.templateIterator!=null &&
              this.templateIterator.equals(other.getTemplateIterator()))) &&
            ((this.instance==null && other.getInstance()==null) || 
             (this.instance!=null &&
              java.util.Arrays.equals(this.instance, other.getInstance())));
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
        if (getTemplateForEach() != null) {
            _hashCode += getTemplateForEach().hashCode();
        }
        if (getTemplateIterator() != null) {
            _hashCode += getTemplateIterator().hashCode();
        }
        if (getInstance() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInstance());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInstance(), i);
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
        new org.apache.axis.description.TypeDesc(TemplateInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TemplateInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateForEach");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "templateForEach"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateIterator");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "templateIterator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instance");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "instance"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TemplateInfoInstance"));
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
