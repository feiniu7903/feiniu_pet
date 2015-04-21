package com.lvmama.back.sweb.util.marketing;
/**
 * SegmentationOptions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class SegmentationOptions  implements java.io.Serializable {
    private java.lang.Boolean removeCacheHits;

    private OverrideType countOverride;

    private OverrideType cacheOverride;

    private java.math.BigDecimal samplingFactor;

    private NameValuePair[] govRules;

    private NameValuePair[] prompts;

    public SegmentationOptions() {
    }

    public SegmentationOptions(
           java.lang.Boolean removeCacheHits,
           OverrideType countOverride,
           OverrideType cacheOverride,
           java.math.BigDecimal samplingFactor,
           NameValuePair[] govRules,
           NameValuePair[] prompts) {
           this.removeCacheHits = removeCacheHits;
           this.countOverride = countOverride;
           this.cacheOverride = cacheOverride;
           this.samplingFactor = samplingFactor;
           this.govRules = govRules;
           this.prompts = prompts;
    }


    /**
     * Gets the removeCacheHits value for this SegmentationOptions.
     * 
     * @return removeCacheHits
     */
    public java.lang.Boolean getRemoveCacheHits() {
        return removeCacheHits;
    }


    /**
     * Sets the removeCacheHits value for this SegmentationOptions.
     * 
     * @param removeCacheHits
     */
    public void setRemoveCacheHits(java.lang.Boolean removeCacheHits) {
        this.removeCacheHits = removeCacheHits;
    }


    /**
     * Gets the countOverride value for this SegmentationOptions.
     * 
     * @return countOverride
     */
    public OverrideType getCountOverride() {
        return countOverride;
    }


    /**
     * Sets the countOverride value for this SegmentationOptions.
     * 
     * @param countOverride
     */
    public void setCountOverride(OverrideType countOverride) {
        this.countOverride = countOverride;
    }


    /**
     * Gets the cacheOverride value for this SegmentationOptions.
     * 
     * @return cacheOverride
     */
    public OverrideType getCacheOverride() {
        return cacheOverride;
    }


    /**
     * Sets the cacheOverride value for this SegmentationOptions.
     * 
     * @param cacheOverride
     */
    public void setCacheOverride(OverrideType cacheOverride) {
        this.cacheOverride = cacheOverride;
    }


    /**
     * Gets the samplingFactor value for this SegmentationOptions.
     * 
     * @return samplingFactor
     */
    public java.math.BigDecimal getSamplingFactor() {
        return samplingFactor;
    }


    /**
     * Sets the samplingFactor value for this SegmentationOptions.
     * 
     * @param samplingFactor
     */
    public void setSamplingFactor(java.math.BigDecimal samplingFactor) {
        this.samplingFactor = samplingFactor;
    }


    /**
     * Gets the govRules value for this SegmentationOptions.
     * 
     * @return govRules
     */
    public NameValuePair[] getGovRules() {
        return govRules;
    }


    /**
     * Sets the govRules value for this SegmentationOptions.
     * 
     * @param govRules
     */
    public void setGovRules(NameValuePair[] govRules) {
        this.govRules = govRules;
    }

    public NameValuePair getGovRules(int i) {
        return this.govRules[i];
    }

    public void setGovRules(int i, NameValuePair _value) {
        this.govRules[i] = _value;
    }


    /**
     * Gets the prompts value for this SegmentationOptions.
     * 
     * @return prompts
     */
    public NameValuePair[] getPrompts() {
        return prompts;
    }


    /**
     * Sets the prompts value for this SegmentationOptions.
     * 
     * @param prompts
     */
    public void setPrompts(NameValuePair[] prompts) {
        this.prompts = prompts;
    }

    public NameValuePair getPrompts(int i) {
        return this.prompts[i];
    }

    public void setPrompts(int i, NameValuePair _value) {
        this.prompts[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SegmentationOptions)) return false;
        SegmentationOptions other = (SegmentationOptions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.removeCacheHits==null && other.getRemoveCacheHits()==null) || 
             (this.removeCacheHits!=null &&
              this.removeCacheHits.equals(other.getRemoveCacheHits()))) &&
            ((this.countOverride==null && other.getCountOverride()==null) || 
             (this.countOverride!=null &&
              this.countOverride.equals(other.getCountOverride()))) &&
            ((this.cacheOverride==null && other.getCacheOverride()==null) || 
             (this.cacheOverride!=null &&
              this.cacheOverride.equals(other.getCacheOverride()))) &&
            ((this.samplingFactor==null && other.getSamplingFactor()==null) || 
             (this.samplingFactor!=null &&
              this.samplingFactor.equals(other.getSamplingFactor()))) &&
            ((this.govRules==null && other.getGovRules()==null) || 
             (this.govRules!=null &&
              java.util.Arrays.equals(this.govRules, other.getGovRules()))) &&
            ((this.prompts==null && other.getPrompts()==null) || 
             (this.prompts!=null &&
              java.util.Arrays.equals(this.prompts, other.getPrompts())));
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
        if (getRemoveCacheHits() != null) {
            _hashCode += getRemoveCacheHits().hashCode();
        }
        if (getCountOverride() != null) {
            _hashCode += getCountOverride().hashCode();
        }
        if (getCacheOverride() != null) {
            _hashCode += getCacheOverride().hashCode();
        }
        if (getSamplingFactor() != null) {
            _hashCode += getSamplingFactor().hashCode();
        }
        if (getGovRules() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGovRules());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGovRules(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrompts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrompts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrompts(), i);
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
        new org.apache.axis.description.TypeDesc(SegmentationOptions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SegmentationOptions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("removeCacheHits");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "removeCacheHits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countOverride");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "countOverride"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "OverrideType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cacheOverride");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "cacheOverride"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "OverrideType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("samplingFactor");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "samplingFactor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("govRules");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "govRules"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prompts");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "prompts"));
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
