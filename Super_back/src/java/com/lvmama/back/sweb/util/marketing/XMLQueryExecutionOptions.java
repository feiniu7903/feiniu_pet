package com.lvmama.back.sweb.util.marketing;
/**
 * XMLQueryExecutionOptions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class XMLQueryExecutionOptions  implements java.io.Serializable {
    private boolean async;

    private int maxRowsPerPage;

    private boolean refresh;

    private boolean presentationInfo;

    private java.lang.String type;

    public XMLQueryExecutionOptions() {
    }

    public XMLQueryExecutionOptions(
           boolean async,
           int maxRowsPerPage,
           boolean refresh,
           boolean presentationInfo,
           java.lang.String type) {
           this.async = async;
           this.maxRowsPerPage = maxRowsPerPage;
           this.refresh = refresh;
           this.presentationInfo = presentationInfo;
           this.type = type;
    }


    /**
     * Gets the async value for this XMLQueryExecutionOptions.
     * 
     * @return async
     */
    public boolean isAsync() {
        return async;
    }


    /**
     * Sets the async value for this XMLQueryExecutionOptions.
     * 
     * @param async
     */
    public void setAsync(boolean async) {
        this.async = async;
    }


    /**
     * Gets the maxRowsPerPage value for this XMLQueryExecutionOptions.
     * 
     * @return maxRowsPerPage
     */
    public int getMaxRowsPerPage() {
        return maxRowsPerPage;
    }


    /**
     * Sets the maxRowsPerPage value for this XMLQueryExecutionOptions.
     * 
     * @param maxRowsPerPage
     */
    public void setMaxRowsPerPage(int maxRowsPerPage) {
        this.maxRowsPerPage = maxRowsPerPage;
    }


    /**
     * Gets the refresh value for this XMLQueryExecutionOptions.
     * 
     * @return refresh
     */
    public boolean isRefresh() {
        return refresh;
    }


    /**
     * Sets the refresh value for this XMLQueryExecutionOptions.
     * 
     * @param refresh
     */
    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }


    /**
     * Gets the presentationInfo value for this XMLQueryExecutionOptions.
     * 
     * @return presentationInfo
     */
    public boolean isPresentationInfo() {
        return presentationInfo;
    }


    /**
     * Sets the presentationInfo value for this XMLQueryExecutionOptions.
     * 
     * @param presentationInfo
     */
    public void setPresentationInfo(boolean presentationInfo) {
        this.presentationInfo = presentationInfo;
    }


    /**
     * Gets the type value for this XMLQueryExecutionOptions.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this XMLQueryExecutionOptions.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XMLQueryExecutionOptions)) return false;
        XMLQueryExecutionOptions other = (XMLQueryExecutionOptions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.async == other.isAsync() &&
            this.maxRowsPerPage == other.getMaxRowsPerPage() &&
            this.refresh == other.isRefresh() &&
            this.presentationInfo == other.isPresentationInfo() &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        _hashCode += (isAsync() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getMaxRowsPerPage();
        _hashCode += (isRefresh() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isPresentationInfo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XMLQueryExecutionOptions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "XMLQueryExecutionOptions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("async");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "async"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxRowsPerPage");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "maxRowsPerPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refresh");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "refresh"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presentationInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "presentationInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "type"));
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
