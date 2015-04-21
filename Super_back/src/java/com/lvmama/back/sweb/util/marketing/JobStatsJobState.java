package com.lvmama.back.sweb.util.marketing;
/**
 * JobStatsJobState.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class JobStatsJobState implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected JobStatsJobState(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Running = "Running";
    public static final java.lang.String _Finished = "Finished";
    public static final java.lang.String _Error = "Error";
    public static final java.lang.String _Queued = "Queued";
    public static final java.lang.String _Cancelled = "Cancelled";
    public static final JobStatsJobState Running = new JobStatsJobState(_Running);
    public static final JobStatsJobState Finished = new JobStatsJobState(_Finished);
    public static final JobStatsJobState Error = new JobStatsJobState(_Error);
    public static final JobStatsJobState Queued = new JobStatsJobState(_Queued);
    public static final JobStatsJobState Cancelled = new JobStatsJobState(_Cancelled);
    public java.lang.String getValue() { return _value_;}
    public static JobStatsJobState fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        JobStatsJobState enumeration = (JobStatsJobState)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static JobStatsJobState fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JobStatsJobState.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">JobStats>jobState"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
