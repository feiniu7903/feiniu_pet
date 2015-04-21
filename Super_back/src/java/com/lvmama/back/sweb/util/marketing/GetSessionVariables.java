package com.lvmama.back.sweb.util.marketing;
/**
 * GetSessionVariables.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */


/**
 * Predefined variable names:
 *                    NQ_SESSION.USER,
 *                    NQ_SESSION.USERGUID,
 *                    NQ_SESSION.GROUP,
 *                    NQ_SESSION.GROUPGUIDS,
 *                    NQ_SESSION.WEBGROUPS,
 *                    NQ_SESSION.REALM,
 *                    NQ_SESSION.REALMGUID,
 *                    NQ_SESSION.TOKENS,
 *                    NQ_SESSION.REQUESTKEY,
 *                    NQ_SESSION.PORTALPATH,
 *                    NQ_SESSION.DISPLAYNAME,
 *                    NQ_SESSION.SKIN,
 *                    NQ_SESSION.STYLE,
 *                    NQ_SESSION.EMAIL,
 *                    NQ_SESSION.CURRENCYTAG,
 *                    NQ_SESSION.ACTUATEUSERID,
 *                    NQ_SESSION.TIMEZONE,
 *                    NQ_SESSION.DATA_TZ,
 *                    NQ_SESSION.DATA_DISPLAY_TZ,
 *                    NQ_SESSION.PROXYLEVEL,
 *                    NQ_SESSION.USERLOCALE,
 *                    NQ_SESSION.USERLANG,
 *                    NQ_SESSION.PREFERRED_CURRENCY
 */
public class GetSessionVariables  implements java.io.Serializable {
    private java.lang.String[] names;

    private java.lang.String sessionID;

    public GetSessionVariables() {
    }

    public GetSessionVariables(
           java.lang.String[] names,
           java.lang.String sessionID) {
           this.names = names;
           this.sessionID = sessionID;
    }


    /**
     * Gets the names value for this GetSessionVariables.
     * 
     * @return names
     */
    public java.lang.String[] getNames() {
        return names;
    }


    /**
     * Sets the names value for this GetSessionVariables.
     * 
     * @param names
     */
    public void setNames(java.lang.String[] names) {
        this.names = names;
    }

    public java.lang.String getNames(int i) {
        return this.names[i];
    }

    public void setNames(int i, java.lang.String _value) {
        this.names[i] = _value;
    }


    /**
     * Gets the sessionID value for this GetSessionVariables.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this GetSessionVariables.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetSessionVariables)) return false;
        GetSessionVariables other = (GetSessionVariables) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.names==null && other.getNames()==null) || 
             (this.names!=null &&
              java.util.Arrays.equals(this.names, other.getNames()))) &&
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
        if (getNames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNames(), i);
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
        new org.apache.axis.description.TypeDesc(GetSessionVariables.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSessionVariables"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("names");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "names"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
