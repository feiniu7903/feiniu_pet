package com.lvmama.back.sweb.util.marketing;
/**
 * EvaluateCondition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class EvaluateCondition  implements java.io.Serializable {
    private java.lang.String path;

    private java.lang.String[] reportCustomizationParameters;

    private java.lang.String sessionID;

    public EvaluateCondition() {
    }

    public EvaluateCondition(
           java.lang.String path,
           java.lang.String[] reportCustomizationParameters,
           java.lang.String sessionID) {
           this.path = path;
           this.reportCustomizationParameters = reportCustomizationParameters;
           this.sessionID = sessionID;
    }


    /**
     * Gets the path value for this EvaluateCondition.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this EvaluateCondition.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the reportCustomizationParameters value for this EvaluateCondition.
     * 
     * @return reportCustomizationParameters
     */
    public java.lang.String[] getReportCustomizationParameters() {
        return reportCustomizationParameters;
    }


    /**
     * Sets the reportCustomizationParameters value for this EvaluateCondition.
     * 
     * @param reportCustomizationParameters
     */
    public void setReportCustomizationParameters(java.lang.String[] reportCustomizationParameters) {
        this.reportCustomizationParameters = reportCustomizationParameters;
    }

    public java.lang.String getReportCustomizationParameters(int i) {
        return this.reportCustomizationParameters[i];
    }

    public void setReportCustomizationParameters(int i, java.lang.String _value) {
        this.reportCustomizationParameters[i] = _value;
    }


    /**
     * Gets the sessionID value for this EvaluateCondition.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this EvaluateCondition.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EvaluateCondition)) return false;
        EvaluateCondition other = (EvaluateCondition) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.path==null && other.getPath()==null) || 
             (this.path!=null &&
              this.path.equals(other.getPath()))) &&
            ((this.reportCustomizationParameters==null && other.getReportCustomizationParameters()==null) || 
             (this.reportCustomizationParameters!=null &&
              java.util.Arrays.equals(this.reportCustomizationParameters, other.getReportCustomizationParameters()))) &&
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
        if (getPath() != null) {
            _hashCode += getPath().hashCode();
        }
        if (getReportCustomizationParameters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReportCustomizationParameters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReportCustomizationParameters(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EvaluateCondition.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">evaluateCondition"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("path");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "path"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportCustomizationParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportCustomizationParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
