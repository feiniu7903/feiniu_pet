package com.lvmama.back.sweb.util.marketing;
/**
 * ReportParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ReportParams  implements java.io.Serializable {
    private java.lang.String[] filterExpressions;

    private Variable[] variables;

    private NameValuePair[] nameValues;

    private TemplateInfo[] templateInfos;

    private java.lang.String viewName;

    public ReportParams() {
    }

    public ReportParams(
           java.lang.String[] filterExpressions,
           Variable[] variables,
           NameValuePair[] nameValues,
           TemplateInfo[] templateInfos,
           java.lang.String viewName) {
           this.filterExpressions = filterExpressions;
           this.variables = variables;
           this.nameValues = nameValues;
           this.templateInfos = templateInfos;
           this.viewName = viewName;
    }


    /**
     * Gets the filterExpressions value for this ReportParams.
     * 
     * @return filterExpressions
     */
    public java.lang.String[] getFilterExpressions() {
        return filterExpressions;
    }


    /**
     * Sets the filterExpressions value for this ReportParams.
     * 
     * @param filterExpressions
     */
    public void setFilterExpressions(java.lang.String[] filterExpressions) {
        this.filterExpressions = filterExpressions;
    }

    public java.lang.String getFilterExpressions(int i) {
        return this.filterExpressions[i];
    }

    public void setFilterExpressions(int i, java.lang.String _value) {
        this.filterExpressions[i] = _value;
    }


    /**
     * Gets the variables value for this ReportParams.
     * 
     * @return variables
     */
    public Variable[] getVariables() {
        return variables;
    }


    /**
     * Sets the variables value for this ReportParams.
     * 
     * @param variables
     */
    public void setVariables(Variable[] variables) {
        this.variables = variables;
    }

    public Variable getVariables(int i) {
        return this.variables[i];
    }

    public void setVariables(int i, Variable _value) {
        this.variables[i] = _value;
    }


    /**
     * Gets the nameValues value for this ReportParams.
     * 
     * @return nameValues
     */
    public NameValuePair[] getNameValues() {
        return nameValues;
    }


    /**
     * Sets the nameValues value for this ReportParams.
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


    /**
     * Gets the templateInfos value for this ReportParams.
     * 
     * @return templateInfos
     */
    public TemplateInfo[] getTemplateInfos() {
        return templateInfos;
    }


    /**
     * Sets the templateInfos value for this ReportParams.
     * 
     * @param templateInfos
     */
    public void setTemplateInfos(TemplateInfo[] templateInfos) {
        this.templateInfos = templateInfos;
    }

    public TemplateInfo getTemplateInfos(int i) {
        return this.templateInfos[i];
    }

    public void setTemplateInfos(int i, TemplateInfo _value) {
        this.templateInfos[i] = _value;
    }


    /**
     * Gets the viewName value for this ReportParams.
     * 
     * @return viewName
     */
    public java.lang.String getViewName() {
        return viewName;
    }


    /**
     * Sets the viewName value for this ReportParams.
     * 
     * @param viewName
     */
    public void setViewName(java.lang.String viewName) {
        this.viewName = viewName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReportParams)) return false;
        ReportParams other = (ReportParams) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.filterExpressions==null && other.getFilterExpressions()==null) || 
             (this.filterExpressions!=null &&
              java.util.Arrays.equals(this.filterExpressions, other.getFilterExpressions()))) &&
            ((this.variables==null && other.getVariables()==null) || 
             (this.variables!=null &&
              java.util.Arrays.equals(this.variables, other.getVariables()))) &&
            ((this.nameValues==null && other.getNameValues()==null) || 
             (this.nameValues!=null &&
              java.util.Arrays.equals(this.nameValues, other.getNameValues()))) &&
            ((this.templateInfos==null && other.getTemplateInfos()==null) || 
             (this.templateInfos!=null &&
              java.util.Arrays.equals(this.templateInfos, other.getTemplateInfos()))) &&
            ((this.viewName==null && other.getViewName()==null) || 
             (this.viewName!=null &&
              this.viewName.equals(other.getViewName())));
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
        if (getFilterExpressions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFilterExpressions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFilterExpressions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVariables() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVariables());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVariables(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        if (getTemplateInfos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTemplateInfos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTemplateInfos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getViewName() != null) {
            _hashCode += getViewName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReportParams.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportParams"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filterExpressions");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "filterExpressions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variables");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "variables"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Variable"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameValues");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "nameValues"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateInfos");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "templateInfos"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TemplateInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("viewName");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "viewName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
