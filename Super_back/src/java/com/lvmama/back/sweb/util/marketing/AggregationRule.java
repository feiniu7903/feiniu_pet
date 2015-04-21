package com.lvmama.back.sweb.util.marketing;
/**
 * AggregationRule.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class AggregationRule implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AggregationRule(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _None = "None";
    public static final java.lang.String _Sum = "Sum";
    public static final java.lang.String _Count = "Count";
    public static final java.lang.String _Rank = "Rank";
    public static final java.lang.String _Avg = "Avg";
    public static final java.lang.String _Min = "Min";
    public static final java.lang.String _Max = "Max";
    public static final java.lang.String _Last = "Last";
    public static final java.lang.String _Percentile = "Percentile";
    public static final java.lang.String _First = "First";
    public static final java.lang.String _ServerDefault = "ServerDefault";
    public static final java.lang.String _CountStar = "CountStar";
    public static final java.lang.String _Complex = "Complex";
    public static final java.lang.String _TopN = "TopN";
    public static final java.lang.String _BottomN = "BottomN";
    public static final java.lang.String _SubTotal = "SubTotal";
    public static final java.lang.String _DimensionAggr = "DimensionAggr";
    public static final java.lang.String _CountDistinct = "CountDistinct";
    public static final AggregationRule None = new AggregationRule(_None);
    public static final AggregationRule Sum = new AggregationRule(_Sum);
    public static final AggregationRule Count = new AggregationRule(_Count);
    public static final AggregationRule Rank = new AggregationRule(_Rank);
    public static final AggregationRule Avg = new AggregationRule(_Avg);
    public static final AggregationRule Min = new AggregationRule(_Min);
    public static final AggregationRule Max = new AggregationRule(_Max);
    public static final AggregationRule Last = new AggregationRule(_Last);
    public static final AggregationRule Percentile = new AggregationRule(_Percentile);
    public static final AggregationRule First = new AggregationRule(_First);
    public static final AggregationRule ServerDefault = new AggregationRule(_ServerDefault);
    public static final AggregationRule CountStar = new AggregationRule(_CountStar);
    public static final AggregationRule Complex = new AggregationRule(_Complex);
    public static final AggregationRule TopN = new AggregationRule(_TopN);
    public static final AggregationRule BottomN = new AggregationRule(_BottomN);
    public static final AggregationRule SubTotal = new AggregationRule(_SubTotal);
    public static final AggregationRule DimensionAggr = new AggregationRule(_DimensionAggr);
    public static final AggregationRule CountDistinct = new AggregationRule(_CountDistinct);
    public java.lang.String getValue() { return _value_;}
    public static AggregationRule fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AggregationRule enumeration = (AggregationRule)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AggregationRule fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AggregationRule.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "AggregationRule"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
