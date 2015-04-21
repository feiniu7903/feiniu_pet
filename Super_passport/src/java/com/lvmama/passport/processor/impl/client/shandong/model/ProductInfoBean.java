/**
 * ProductInfoBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class ProductInfoBean  implements java.io.Serializable {
    private java.lang.Integer people_count;

    private java.lang.Double price;

    private java.lang.String productInfo_id;

    private java.lang.String product_description;

    private java.lang.String product_name;

    private java.lang.String providersName;

    private java.lang.String scenicName;

    private java.lang.String scenic_id;

    private com.lvmama.passport.processor.impl.client.shandong.model.StrategyProductBean[] strategyProductList;

    private java.util.Calendar valid_end;

    private java.util.Calendar valid_start;

    public ProductInfoBean() {
    }

    public ProductInfoBean(
           java.lang.Integer people_count,
           java.lang.Double price,
           java.lang.String productInfo_id,
           java.lang.String product_description,
           java.lang.String product_name,
           java.lang.String providersName,
           java.lang.String scenicName,
           java.lang.String scenic_id,
           com.lvmama.passport.processor.impl.client.shandong.model.StrategyProductBean[] strategyProductList,
           java.util.Calendar valid_end,
           java.util.Calendar valid_start) {
           this.people_count = people_count;
           this.price = price;
           this.productInfo_id = productInfo_id;
           this.product_description = product_description;
           this.product_name = product_name;
           this.providersName = providersName;
           this.scenicName = scenicName;
           this.scenic_id = scenic_id;
           this.strategyProductList = strategyProductList;
           this.valid_end = valid_end;
           this.valid_start = valid_start;
    }


    /**
     * Gets the people_count value for this ProductInfoBean.
     * 
     * @return people_count
     */
    public java.lang.Integer getPeople_count() {
        return people_count;
    }


    /**
     * Sets the people_count value for this ProductInfoBean.
     * 
     * @param people_count
     */
    public void setPeople_count(java.lang.Integer people_count) {
        this.people_count = people_count;
    }


    /**
     * Gets the price value for this ProductInfoBean.
     * 
     * @return price
     */
    public java.lang.Double getPrice() {
        return price;
    }


    /**
     * Sets the price value for this ProductInfoBean.
     * 
     * @param price
     */
    public void setPrice(java.lang.Double price) {
        this.price = price;
    }


    /**
     * Gets the productInfo_id value for this ProductInfoBean.
     * 
     * @return productInfo_id
     */
    public java.lang.String getProductInfo_id() {
        return productInfo_id;
    }


    /**
     * Sets the productInfo_id value for this ProductInfoBean.
     * 
     * @param productInfo_id
     */
    public void setProductInfo_id(java.lang.String productInfo_id) {
        this.productInfo_id = productInfo_id;
    }


    /**
     * Gets the product_description value for this ProductInfoBean.
     * 
     * @return product_description
     */
    public java.lang.String getProduct_description() {
        return product_description;
    }


    /**
     * Sets the product_description value for this ProductInfoBean.
     * 
     * @param product_description
     */
    public void setProduct_description(java.lang.String product_description) {
        this.product_description = product_description;
    }


    /**
     * Gets the product_name value for this ProductInfoBean.
     * 
     * @return product_name
     */
    public java.lang.String getProduct_name() {
        return product_name;
    }


    /**
     * Sets the product_name value for this ProductInfoBean.
     * 
     * @param product_name
     */
    public void setProduct_name(java.lang.String product_name) {
        this.product_name = product_name;
    }


    /**
     * Gets the providersName value for this ProductInfoBean.
     * 
     * @return providersName
     */
    public java.lang.String getProvidersName() {
        return providersName;
    }


    /**
     * Sets the providersName value for this ProductInfoBean.
     * 
     * @param providersName
     */
    public void setProvidersName(java.lang.String providersName) {
        this.providersName = providersName;
    }


    /**
     * Gets the scenicName value for this ProductInfoBean.
     * 
     * @return scenicName
     */
    public java.lang.String getScenicName() {
        return scenicName;
    }


    /**
     * Sets the scenicName value for this ProductInfoBean.
     * 
     * @param scenicName
     */
    public void setScenicName(java.lang.String scenicName) {
        this.scenicName = scenicName;
    }


    /**
     * Gets the scenic_id value for this ProductInfoBean.
     * 
     * @return scenic_id
     */
    public java.lang.String getScenic_id() {
        return scenic_id;
    }


    /**
     * Sets the scenic_id value for this ProductInfoBean.
     * 
     * @param scenic_id
     */
    public void setScenic_id(java.lang.String scenic_id) {
        this.scenic_id = scenic_id;
    }


    /**
     * Gets the strategyProductList value for this ProductInfoBean.
     * 
     * @return strategyProductList
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.StrategyProductBean[] getStrategyProductList() {
        return strategyProductList;
    }


    /**
     * Sets the strategyProductList value for this ProductInfoBean.
     * 
     * @param strategyProductList
     */
    public void setStrategyProductList(com.lvmama.passport.processor.impl.client.shandong.model.StrategyProductBean[] strategyProductList) {
        this.strategyProductList = strategyProductList;
    }


    /**
     * Gets the valid_end value for this ProductInfoBean.
     * 
     * @return valid_end
     */
    public java.util.Calendar getValid_end() {
        return valid_end;
    }


    /**
     * Sets the valid_end value for this ProductInfoBean.
     * 
     * @param valid_end
     */
    public void setValid_end(java.util.Calendar valid_end) {
        this.valid_end = valid_end;
    }


    /**
     * Gets the valid_start value for this ProductInfoBean.
     * 
     * @return valid_start
     */
    public java.util.Calendar getValid_start() {
        return valid_start;
    }


    /**
     * Sets the valid_start value for this ProductInfoBean.
     * 
     * @param valid_start
     */
    public void setValid_start(java.util.Calendar valid_start) {
        this.valid_start = valid_start;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProductInfoBean)) return false;
        ProductInfoBean other = (ProductInfoBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.people_count==null && other.getPeople_count()==null) || 
             (this.people_count!=null &&
              this.people_count.equals(other.getPeople_count()))) &&
            ((this.price==null && other.getPrice()==null) || 
             (this.price!=null &&
              this.price.equals(other.getPrice()))) &&
            ((this.productInfo_id==null && other.getProductInfo_id()==null) || 
             (this.productInfo_id!=null &&
              this.productInfo_id.equals(other.getProductInfo_id()))) &&
            ((this.product_description==null && other.getProduct_description()==null) || 
             (this.product_description!=null &&
              this.product_description.equals(other.getProduct_description()))) &&
            ((this.product_name==null && other.getProduct_name()==null) || 
             (this.product_name!=null &&
              this.product_name.equals(other.getProduct_name()))) &&
            ((this.providersName==null && other.getProvidersName()==null) || 
             (this.providersName!=null &&
              this.providersName.equals(other.getProvidersName()))) &&
            ((this.scenicName==null && other.getScenicName()==null) || 
             (this.scenicName!=null &&
              this.scenicName.equals(other.getScenicName()))) &&
            ((this.scenic_id==null && other.getScenic_id()==null) || 
             (this.scenic_id!=null &&
              this.scenic_id.equals(other.getScenic_id()))) &&
            ((this.strategyProductList==null && other.getStrategyProductList()==null) || 
             (this.strategyProductList!=null &&
              java.util.Arrays.equals(this.strategyProductList, other.getStrategyProductList()))) &&
            ((this.valid_end==null && other.getValid_end()==null) || 
             (this.valid_end!=null &&
              this.valid_end.equals(other.getValid_end()))) &&
            ((this.valid_start==null && other.getValid_start()==null) || 
             (this.valid_start!=null &&
              this.valid_start.equals(other.getValid_start())));
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
        if (getPeople_count() != null) {
            _hashCode += getPeople_count().hashCode();
        }
        if (getPrice() != null) {
            _hashCode += getPrice().hashCode();
        }
        if (getProductInfo_id() != null) {
            _hashCode += getProductInfo_id().hashCode();
        }
        if (getProduct_description() != null) {
            _hashCode += getProduct_description().hashCode();
        }
        if (getProduct_name() != null) {
            _hashCode += getProduct_name().hashCode();
        }
        if (getProvidersName() != null) {
            _hashCode += getProvidersName().hashCode();
        }
        if (getScenicName() != null) {
            _hashCode += getScenicName().hashCode();
        }
        if (getScenic_id() != null) {
            _hashCode += getScenic_id().hashCode();
        }
        if (getStrategyProductList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStrategyProductList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStrategyProductList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getValid_end() != null) {
            _hashCode += getValid_end().hashCode();
        }
        if (getValid_start() != null) {
            _hashCode += getValid_start().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProductInfoBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://product.bean.xz.com", "ProductInfoBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("people_count");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "people_count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productInfo_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "productInfo_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product_description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "product_description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product_name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "product_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("providersName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "providersName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "scenicName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenic_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "scenic_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strategyProductList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "strategyProductList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://product.bean.xz.com", "StrategyProductBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://product.bean.xz.com", "StrategyProductBean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid_end");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "valid_end"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid_start");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "valid_start"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
