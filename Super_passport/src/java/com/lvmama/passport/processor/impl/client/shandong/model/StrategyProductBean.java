/**
 * StrategyProductBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class StrategyProductBean  implements java.io.Serializable {
    private java.lang.String OTAStrategy_Name;

    private java.lang.Double markerPrice;

    private java.lang.String peopleStrategy_Name;

    private java.lang.String price_type;

    private java.lang.Double price_value;

    private java.lang.String productInfo_id;

    private java.lang.String seasonStrategy_Name;

    private java.lang.String strategy_id;

    public StrategyProductBean() {
    }

    public StrategyProductBean(
           java.lang.String OTAStrategy_Name,
           java.lang.Double markerPrice,
           java.lang.String peopleStrategy_Name,
           java.lang.String price_type,
           java.lang.Double price_value,
           java.lang.String productInfo_id,
           java.lang.String seasonStrategy_Name,
           java.lang.String strategy_id) {
           this.OTAStrategy_Name = OTAStrategy_Name;
           this.markerPrice = markerPrice;
           this.peopleStrategy_Name = peopleStrategy_Name;
           this.price_type = price_type;
           this.price_value = price_value;
           this.productInfo_id = productInfo_id;
           this.seasonStrategy_Name = seasonStrategy_Name;
           this.strategy_id = strategy_id;
    }


    /**
     * Gets the OTAStrategy_Name value for this StrategyProductBean.
     * 
     * @return OTAStrategy_Name
     */
    public java.lang.String getOTAStrategy_Name() {
        return OTAStrategy_Name;
    }


    /**
     * Sets the OTAStrategy_Name value for this StrategyProductBean.
     * 
     * @param OTAStrategy_Name
     */
    public void setOTAStrategy_Name(java.lang.String OTAStrategy_Name) {
        this.OTAStrategy_Name = OTAStrategy_Name;
    }


    /**
     * Gets the markerPrice value for this StrategyProductBean.
     * 
     * @return markerPrice
     */
    public java.lang.Double getMarkerPrice() {
        return markerPrice;
    }


    /**
     * Sets the markerPrice value for this StrategyProductBean.
     * 
     * @param markerPrice
     */
    public void setMarkerPrice(java.lang.Double markerPrice) {
        this.markerPrice = markerPrice;
    }


    /**
     * Gets the peopleStrategy_Name value for this StrategyProductBean.
     * 
     * @return peopleStrategy_Name
     */
    public java.lang.String getPeopleStrategy_Name() {
        return peopleStrategy_Name;
    }


    /**
     * Sets the peopleStrategy_Name value for this StrategyProductBean.
     * 
     * @param peopleStrategy_Name
     */
    public void setPeopleStrategy_Name(java.lang.String peopleStrategy_Name) {
        this.peopleStrategy_Name = peopleStrategy_Name;
    }


    /**
     * Gets the price_type value for this StrategyProductBean.
     * 
     * @return price_type
     */
    public java.lang.String getPrice_type() {
        return price_type;
    }


    /**
     * Sets the price_type value for this StrategyProductBean.
     * 
     * @param price_type
     */
    public void setPrice_type(java.lang.String price_type) {
        this.price_type = price_type;
    }


    /**
     * Gets the price_value value for this StrategyProductBean.
     * 
     * @return price_value
     */
    public java.lang.Double getPrice_value() {
        return price_value;
    }


    /**
     * Sets the price_value value for this StrategyProductBean.
     * 
     * @param price_value
     */
    public void setPrice_value(java.lang.Double price_value) {
        this.price_value = price_value;
    }


    /**
     * Gets the productInfo_id value for this StrategyProductBean.
     * 
     * @return productInfo_id
     */
    public java.lang.String getProductInfo_id() {
        return productInfo_id;
    }


    /**
     * Sets the productInfo_id value for this StrategyProductBean.
     * 
     * @param productInfo_id
     */
    public void setProductInfo_id(java.lang.String productInfo_id) {
        this.productInfo_id = productInfo_id;
    }


    /**
     * Gets the seasonStrategy_Name value for this StrategyProductBean.
     * 
     * @return seasonStrategy_Name
     */
    public java.lang.String getSeasonStrategy_Name() {
        return seasonStrategy_Name;
    }


    /**
     * Sets the seasonStrategy_Name value for this StrategyProductBean.
     * 
     * @param seasonStrategy_Name
     */
    public void setSeasonStrategy_Name(java.lang.String seasonStrategy_Name) {
        this.seasonStrategy_Name = seasonStrategy_Name;
    }


    /**
     * Gets the strategy_id value for this StrategyProductBean.
     * 
     * @return strategy_id
     */
    public java.lang.String getStrategy_id() {
        return strategy_id;
    }


    /**
     * Sets the strategy_id value for this StrategyProductBean.
     * 
     * @param strategy_id
     */
    public void setStrategy_id(java.lang.String strategy_id) {
        this.strategy_id = strategy_id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StrategyProductBean)) return false;
        StrategyProductBean other = (StrategyProductBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.OTAStrategy_Name==null && other.getOTAStrategy_Name()==null) || 
             (this.OTAStrategy_Name!=null &&
              this.OTAStrategy_Name.equals(other.getOTAStrategy_Name()))) &&
            ((this.markerPrice==null && other.getMarkerPrice()==null) || 
             (this.markerPrice!=null &&
              this.markerPrice.equals(other.getMarkerPrice()))) &&
            ((this.peopleStrategy_Name==null && other.getPeopleStrategy_Name()==null) || 
             (this.peopleStrategy_Name!=null &&
              this.peopleStrategy_Name.equals(other.getPeopleStrategy_Name()))) &&
            ((this.price_type==null && other.getPrice_type()==null) || 
             (this.price_type!=null &&
              this.price_type.equals(other.getPrice_type()))) &&
            ((this.price_value==null && other.getPrice_value()==null) || 
             (this.price_value!=null &&
              this.price_value.equals(other.getPrice_value()))) &&
            ((this.productInfo_id==null && other.getProductInfo_id()==null) || 
             (this.productInfo_id!=null &&
              this.productInfo_id.equals(other.getProductInfo_id()))) &&
            ((this.seasonStrategy_Name==null && other.getSeasonStrategy_Name()==null) || 
             (this.seasonStrategy_Name!=null &&
              this.seasonStrategy_Name.equals(other.getSeasonStrategy_Name()))) &&
            ((this.strategy_id==null && other.getStrategy_id()==null) || 
             (this.strategy_id!=null &&
              this.strategy_id.equals(other.getStrategy_id())));
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
        if (getOTAStrategy_Name() != null) {
            _hashCode += getOTAStrategy_Name().hashCode();
        }
        if (getMarkerPrice() != null) {
            _hashCode += getMarkerPrice().hashCode();
        }
        if (getPeopleStrategy_Name() != null) {
            _hashCode += getPeopleStrategy_Name().hashCode();
        }
        if (getPrice_type() != null) {
            _hashCode += getPrice_type().hashCode();
        }
        if (getPrice_value() != null) {
            _hashCode += getPrice_value().hashCode();
        }
        if (getProductInfo_id() != null) {
            _hashCode += getProductInfo_id().hashCode();
        }
        if (getSeasonStrategy_Name() != null) {
            _hashCode += getSeasonStrategy_Name().hashCode();
        }
        if (getStrategy_id() != null) {
            _hashCode += getStrategy_id().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StrategyProductBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://product.bean.xz.com", "StrategyProductBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OTAStrategy_Name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "OTAStrategy_Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("markerPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "markerPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("peopleStrategy_Name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "peopleStrategy_Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price_type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "price_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price_value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "price_value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productInfo_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "productInfo_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seasonStrategy_Name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "seasonStrategy_Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strategy_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "strategy_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
