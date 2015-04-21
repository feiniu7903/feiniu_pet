package com.lvmama.back.sweb.util.marketing;
/**
 * ExecuteSQLQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ExecuteSQLQuery  implements java.io.Serializable {
    private java.lang.String sql;

    private XMLQueryOutputFormat outputFormat;

    private XMLQueryExecutionOptions executionOptions;

    private java.lang.String sessionID;

    public ExecuteSQLQuery() {
    }

    public ExecuteSQLQuery(
           java.lang.String sql,
           XMLQueryOutputFormat outputFormat,
           XMLQueryExecutionOptions executionOptions,
           java.lang.String sessionID) {
           this.sql = sql;
           this.outputFormat = outputFormat;
           this.executionOptions = executionOptions;
           this.sessionID = sessionID;
    }


    /**
     * Gets the sql value for this ExecuteSQLQuery.
     * 
     * @return sql
     */
    public java.lang.String getSql() {
        return sql;
    }


    /**
     * Sets the sql value for this ExecuteSQLQuery.
     * 
     * @param sql
     */
    public void setSql(java.lang.String sql) {
        this.sql = sql;
    }


    /**
     * Gets the outputFormat value for this ExecuteSQLQuery.
     * 
     * @return outputFormat
     */
    public XMLQueryOutputFormat getOutputFormat() {
        return outputFormat;
    }


    /**
     * Sets the outputFormat value for this ExecuteSQLQuery.
     * 
     * @param outputFormat
     */
    public void setOutputFormat(XMLQueryOutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }


    /**
     * Gets the executionOptions value for this ExecuteSQLQuery.
     * 
     * @return executionOptions
     */
    public XMLQueryExecutionOptions getExecutionOptions() {
        return executionOptions;
    }


    /**
     * Sets the executionOptions value for this ExecuteSQLQuery.
     * 
     * @param executionOptions
     */
    public void setExecutionOptions(XMLQueryExecutionOptions executionOptions) {
        this.executionOptions = executionOptions;
    }


    /**
     * Gets the sessionID value for this ExecuteSQLQuery.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this ExecuteSQLQuery.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExecuteSQLQuery)) return false;
        ExecuteSQLQuery other = (ExecuteSQLQuery) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sql==null && other.getSql()==null) || 
             (this.sql!=null &&
              this.sql.equals(other.getSql()))) &&
            ((this.outputFormat==null && other.getOutputFormat()==null) || 
             (this.outputFormat!=null &&
              this.outputFormat.equals(other.getOutputFormat()))) &&
            ((this.executionOptions==null && other.getExecutionOptions()==null) || 
             (this.executionOptions!=null &&
              this.executionOptions.equals(other.getExecutionOptions()))) &&
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
        if (getSql() != null) {
            _hashCode += getSql().hashCode();
        }
        if (getOutputFormat() != null) {
            _hashCode += getOutputFormat().hashCode();
        }
        if (getExecutionOptions() != null) {
            _hashCode += getExecutionOptions().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExecuteSQLQuery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">executeSQLQuery"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sql");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sql"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputFormat");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "outputFormat"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "XMLQueryOutputFormat"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("executionOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "executionOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "XMLQueryExecutionOptions"));
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
