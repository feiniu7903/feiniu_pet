package com.lvmama.back.sweb.util.marketing;
/**
 * SAWSessionParameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class SAWSessionParameters  implements java.io.Serializable {
    private SAWLocale locale;

    private SAWLocale language;

    private java.lang.String userAgent;

    private boolean asyncLogon;

    private LogonParameter[] logonParams;

    private java.lang.String sessionID;

    private java.lang.String syndicate;

    public SAWSessionParameters() {
    }

    public SAWSessionParameters(
           SAWLocale locale,
           SAWLocale language,
           java.lang.String userAgent,
           boolean asyncLogon,
           LogonParameter[] logonParams,
           java.lang.String sessionID,
           java.lang.String syndicate) {
           this.locale = locale;
           this.language = language;
           this.userAgent = userAgent;
           this.asyncLogon = asyncLogon;
           this.logonParams = logonParams;
           this.sessionID = sessionID;
           this.syndicate = syndicate;
    }


    /**
     * Gets the locale value for this SAWSessionParameters.
     * 
     * @return locale
     */
    public SAWLocale getLocale() {
        return locale;
    }


    /**
     * Sets the locale value for this SAWSessionParameters.
     * 
     * @param locale
     */
    public void setLocale(SAWLocale locale) {
        this.locale = locale;
    }


    /**
     * Gets the language value for this SAWSessionParameters.
     * 
     * @return language
     */
    public SAWLocale getLanguage() {
        return language;
    }


    /**
     * Sets the language value for this SAWSessionParameters.
     * 
     * @param language
     */
    public void setLanguage(SAWLocale language) {
        this.language = language;
    }


    /**
     * Gets the userAgent value for this SAWSessionParameters.
     * 
     * @return userAgent
     */
    public java.lang.String getUserAgent() {
        return userAgent;
    }


    /**
     * Sets the userAgent value for this SAWSessionParameters.
     * 
     * @param userAgent
     */
    public void setUserAgent(java.lang.String userAgent) {
        this.userAgent = userAgent;
    }


    /**
     * Gets the asyncLogon value for this SAWSessionParameters.
     * 
     * @return asyncLogon
     */
    public boolean isAsyncLogon() {
        return asyncLogon;
    }


    /**
     * Sets the asyncLogon value for this SAWSessionParameters.
     * 
     * @param asyncLogon
     */
    public void setAsyncLogon(boolean asyncLogon) {
        this.asyncLogon = asyncLogon;
    }


    /**
     * Gets the logonParams value for this SAWSessionParameters.
     * 
     * @return logonParams
     */
    public LogonParameter[] getLogonParams() {
        return logonParams;
    }


    /**
     * Sets the logonParams value for this SAWSessionParameters.
     * 
     * @param logonParams
     */
    public void setLogonParams(LogonParameter[] logonParams) {
        this.logonParams = logonParams;
    }

    public LogonParameter getLogonParams(int i) {
        return this.logonParams[i];
    }

    public void setLogonParams(int i, LogonParameter _value) {
        this.logonParams[i] = _value;
    }


    /**
     * Gets the sessionID value for this SAWSessionParameters.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this SAWSessionParameters.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }


    /**
     * Gets the syndicate value for this SAWSessionParameters.
     * 
     * @return syndicate
     */
    public java.lang.String getSyndicate() {
        return syndicate;
    }


    /**
     * Sets the syndicate value for this SAWSessionParameters.
     * 
     * @param syndicate
     */
    public void setSyndicate(java.lang.String syndicate) {
        this.syndicate = syndicate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SAWSessionParameters)) return false;
        SAWSessionParameters other = (SAWSessionParameters) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.locale==null && other.getLocale()==null) || 
             (this.locale!=null &&
              this.locale.equals(other.getLocale()))) &&
            ((this.language==null && other.getLanguage()==null) || 
             (this.language!=null &&
              this.language.equals(other.getLanguage()))) &&
            ((this.userAgent==null && other.getUserAgent()==null) || 
             (this.userAgent!=null &&
              this.userAgent.equals(other.getUserAgent()))) &&
            this.asyncLogon == other.isAsyncLogon() &&
            ((this.logonParams==null && other.getLogonParams()==null) || 
             (this.logonParams!=null &&
              java.util.Arrays.equals(this.logonParams, other.getLogonParams()))) &&
            ((this.sessionID==null && other.getSessionID()==null) || 
             (this.sessionID!=null &&
              this.sessionID.equals(other.getSessionID()))) &&
            ((this.syndicate==null && other.getSyndicate()==null) || 
             (this.syndicate!=null &&
              this.syndicate.equals(other.getSyndicate())));
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
        if (getLocale() != null) {
            _hashCode += getLocale().hashCode();
        }
        if (getLanguage() != null) {
            _hashCode += getLanguage().hashCode();
        }
        if (getUserAgent() != null) {
            _hashCode += getUserAgent().hashCode();
        }
        _hashCode += (isAsyncLogon() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getLogonParams() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLogonParams());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLogonParams(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        if (getSyndicate() != null) {
            _hashCode += getSyndicate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SAWSessionParameters.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAWSessionParameters"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locale");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "locale"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAWLocale"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("language");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "language"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAWLocale"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userAgent");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "userAgent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asyncLogon");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "asyncLogon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logonParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "logonParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "LogonParameter"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syndicate");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "syndicate"));
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
