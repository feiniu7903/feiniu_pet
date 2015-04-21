package com.lvmama.back.sweb.util.marketing;
/**
 * _import.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class _import  implements java.io.Serializable {
    private java.lang.String filename;

    private ImportFlags flag;

    private java.util.Calendar lastPurgedLog;

    private boolean updateReplicationLog;

    private boolean returnErrors;

    private CatalogItemsFilter filter;

    private PathMapEntry[] pathMap;

    private java.lang.String sessionID;

    public _import() {
    }

    public _import(
           java.lang.String filename,
           ImportFlags flag,
           java.util.Calendar lastPurgedLog,
           boolean updateReplicationLog,
           boolean returnErrors,
           CatalogItemsFilter filter,
           PathMapEntry[] pathMap,
           java.lang.String sessionID) {
           this.filename = filename;
           this.flag = flag;
           this.lastPurgedLog = lastPurgedLog;
           this.updateReplicationLog = updateReplicationLog;
           this.returnErrors = returnErrors;
           this.filter = filter;
           this.pathMap = pathMap;
           this.sessionID = sessionID;
    }


    /**
     * Gets the filename value for this _import.
     * 
     * @return filename
     */
    public java.lang.String getFilename() {
        return filename;
    }


    /**
     * Sets the filename value for this _import.
     * 
     * @param filename
     */
    public void setFilename(java.lang.String filename) {
        this.filename = filename;
    }


    /**
     * Gets the flag value for this _import.
     * 
     * @return flag
     */
    public ImportFlags getFlag() {
        return flag;
    }


    /**
     * Sets the flag value for this _import.
     * 
     * @param flag
     */
    public void setFlag(ImportFlags flag) {
        this.flag = flag;
    }


    /**
     * Gets the lastPurgedLog value for this _import.
     * 
     * @return lastPurgedLog
     */
    public java.util.Calendar getLastPurgedLog() {
        return lastPurgedLog;
    }


    /**
     * Sets the lastPurgedLog value for this _import.
     * 
     * @param lastPurgedLog
     */
    public void setLastPurgedLog(java.util.Calendar lastPurgedLog) {
        this.lastPurgedLog = lastPurgedLog;
    }


    /**
     * Gets the updateReplicationLog value for this _import.
     * 
     * @return updateReplicationLog
     */
    public boolean isUpdateReplicationLog() {
        return updateReplicationLog;
    }


    /**
     * Sets the updateReplicationLog value for this _import.
     * 
     * @param updateReplicationLog
     */
    public void setUpdateReplicationLog(boolean updateReplicationLog) {
        this.updateReplicationLog = updateReplicationLog;
    }


    /**
     * Gets the returnErrors value for this _import.
     * 
     * @return returnErrors
     */
    public boolean isReturnErrors() {
        return returnErrors;
    }


    /**
     * Sets the returnErrors value for this _import.
     * 
     * @param returnErrors
     */
    public void setReturnErrors(boolean returnErrors) {
        this.returnErrors = returnErrors;
    }


    /**
     * Gets the filter value for this _import.
     * 
     * @return filter
     */
    public CatalogItemsFilter getFilter() {
        return filter;
    }


    /**
     * Sets the filter value for this _import.
     * 
     * @param filter
     */
    public void setFilter(CatalogItemsFilter filter) {
        this.filter = filter;
    }


    /**
     * Gets the pathMap value for this _import.
     * 
     * @return pathMap
     */
    public PathMapEntry[] getPathMap() {
        return pathMap;
    }


    /**
     * Sets the pathMap value for this _import.
     * 
     * @param pathMap
     */
    public void setPathMap(PathMapEntry[] pathMap) {
        this.pathMap = pathMap;
    }


    /**
     * Gets the sessionID value for this _import.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this _import.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _import)) return false;
        _import other = (_import) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.filename==null && other.getFilename()==null) || 
             (this.filename!=null &&
              this.filename.equals(other.getFilename()))) &&
            ((this.flag==null && other.getFlag()==null) || 
             (this.flag!=null &&
              this.flag.equals(other.getFlag()))) &&
            ((this.lastPurgedLog==null && other.getLastPurgedLog()==null) || 
             (this.lastPurgedLog!=null &&
              this.lastPurgedLog.equals(other.getLastPurgedLog()))) &&
            this.updateReplicationLog == other.isUpdateReplicationLog() &&
            this.returnErrors == other.isReturnErrors() &&
            ((this.filter==null && other.getFilter()==null) || 
             (this.filter!=null &&
              this.filter.equals(other.getFilter()))) &&
            ((this.pathMap==null && other.getPathMap()==null) || 
             (this.pathMap!=null &&
              java.util.Arrays.equals(this.pathMap, other.getPathMap()))) &&
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
        if (getFilename() != null) {
            _hashCode += getFilename().hashCode();
        }
        if (getFlag() != null) {
            _hashCode += getFlag().hashCode();
        }
        if (getLastPurgedLog() != null) {
            _hashCode += getLastPurgedLog().hashCode();
        }
        _hashCode += (isUpdateReplicationLog() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isReturnErrors() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getFilter() != null) {
            _hashCode += getFilter().hashCode();
        }
        if (getPathMap() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPathMap());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPathMap(), i);
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
        new org.apache.axis.description.TypeDesc(_import.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">import"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filename");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "filename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flag");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "flag"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ImportFlags"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastPurgedLog");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "lastPurgedLog"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateReplicationLog");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "updateReplicationLog"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "returnErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filter");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "filter"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "CatalogItemsFilter"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pathMap");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "pathMap"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "PathMapEntry"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "pathMapEntries"));
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
