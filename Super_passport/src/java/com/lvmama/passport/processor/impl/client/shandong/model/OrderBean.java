/**
 * OrderBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class OrderBean  implements java.io.Serializable {
    private java.util.Calendar begin_time;

    private java.util.Calendar buy_time;

    private java.lang.Integer counts;

    private java.util.Calendar create_time;
    
    private java.lang.String detailSourceId;

    private java.util.Calendar end_time;

    private java.util.Calendar lm_time;

    private java.lang.String lm_user;

    private java.lang.String massege;

    private java.lang.Integer msgInt;

    private java.lang.Double orderPrice;

    private java.lang.String orderSourceName;

    private com.lvmama.passport.processor.impl.client.shandong.model.OrderStatus orderStatus;

    private java.lang.String order_id;

    private java.lang.String order_source;

    private java.lang.Integer order_status;
    
    private java.lang.String ota_order_id;

    private java.lang.String product_info_id;

    private java.lang.String product_name;

    private java.lang.String purchaser_card;

    private java.lang.String purchaser_mail;

    private java.lang.String purchaser_name;

    private java.lang.String purchaser_phone;

    private java.lang.String purchaser_photo;

    private java.lang.String purchaser_sex;

    private java.lang.String source_detail;

    private java.lang.String strategy_id;

    private java.lang.String traveler_source;

    public OrderBean() {
    }

    public OrderBean(
           java.util.Calendar begin_time,
           java.util.Calendar buy_time,
           java.lang.Integer counts,
           java.util.Calendar create_time,
           java.lang.String detailSourceId,
           java.util.Calendar end_time,
           java.util.Calendar lm_time,
           java.lang.String lm_user,
           java.lang.String massege,
           java.lang.Integer msgInt,
           java.lang.Double orderPrice,
           java.lang.String orderSourceName,
           com.lvmama.passport.processor.impl.client.shandong.model.OrderStatus orderStatus,
           java.lang.String order_id,
           java.lang.String order_source,
           java.lang.String ota_order_id,
           java.lang.Integer order_status,
           java.lang.String product_info_id,
           java.lang.String product_name,
           java.lang.String purchaser_card,
           java.lang.String purchaser_mail,
           java.lang.String purchaser_name,
           java.lang.String purchaser_phone,
           java.lang.String purchaser_photo,
           java.lang.String purchaser_sex,
           java.lang.String source_detail,
           java.lang.String strategy_id,
           java.lang.String traveler_source) {
           this.begin_time = begin_time;
           this.buy_time = buy_time;
           this.counts = counts;
           this.create_time = create_time;
           this.detailSourceId = detailSourceId;
           this.end_time = end_time;
           this.lm_time = lm_time;
           this.lm_user = lm_user;
           this.massege = massege;
           this.msgInt = msgInt;
           this.orderPrice = orderPrice;
           this.orderSourceName = orderSourceName;
           this.orderStatus = orderStatus;
           this.ota_order_id=ota_order_id;
           this.order_id = order_id;
           this.order_source = order_source;
           this.order_status = order_status;
           this.product_info_id = product_info_id;
           this.product_name = product_name;
           this.purchaser_card = purchaser_card;
           this.purchaser_mail = purchaser_mail;
           this.purchaser_name = purchaser_name;
           this.purchaser_phone = purchaser_phone;
           this.purchaser_photo = purchaser_photo;
           this.purchaser_sex = purchaser_sex;
           this.source_detail = source_detail;
           this.strategy_id = strategy_id;
           this.traveler_source = traveler_source;
    }


    /**
     * Gets the begin_time value for this OrderBean.
     * 
     * @return begin_time
     */
    public java.util.Calendar getBegin_time() {
        return begin_time;
    }


    /**
     * Sets the begin_time value for this OrderBean.
     * 
     * @param begin_time
     */
    public void setBegin_time(java.util.Calendar begin_time) {
        this.begin_time = begin_time;
    }


    /**
     * Gets the buy_time value for this OrderBean.
     * 
     * @return buy_time
     */
    public java.util.Calendar getBuy_time() {
        return buy_time;
    }


    /**
     * Sets the buy_time value for this OrderBean.
     * 
     * @param buy_time
     */
    public void setBuy_time(java.util.Calendar buy_time) {
        this.buy_time = buy_time;
    }


    /**
     * Gets the counts value for this OrderBean.
     * 
     * @return counts
     */
    public java.lang.Integer getCounts() {
        return counts;
    }


    /**
     * Sets the counts value for this OrderBean.
     * 
     * @param counts
     */
    public void setCounts(java.lang.Integer counts) {
        this.counts = counts;
    }


    /**
     * Gets the create_time value for this OrderBean.
     * 
     * @return create_time
     */
    public java.util.Calendar getCreate_time() {
        return create_time;
    }


    /**
     * Sets the create_time value for this OrderBean.
     * 
     * @param create_time
     */
    public void setCreate_time(java.util.Calendar create_time) {
        this.create_time = create_time;
    }


    /**
     * Gets the detailSourceId value for this OrderBean.
     * 
     * @return detailSourceId
     */
    public java.lang.String getDetailSourceId() {
        return detailSourceId;
    }


    /**
     * Sets the detailSourceId value for this OrderBean.
     * 
     * @param detailSourceId
     */
    public void setDetailSourceId(java.lang.String detailSourceId) {
        this.detailSourceId = detailSourceId;
    }


    /**
     * Gets the end_time value for this OrderBean.
     * 
     * @return end_time
     */
    public java.util.Calendar getEnd_time() {
        return end_time;
    }


    /**
     * Sets the end_time value for this OrderBean.
     * 
     * @param end_time
     */
    public void setEnd_time(java.util.Calendar end_time) {
        this.end_time = end_time;
    }


    /**
     * Gets the lm_time value for this OrderBean.
     * 
     * @return lm_time
     */
    public java.util.Calendar getLm_time() {
        return lm_time;
    }


    /**
     * Sets the lm_time value for this OrderBean.
     * 
     * @param lm_time
     */
    public void setLm_time(java.util.Calendar lm_time) {
        this.lm_time = lm_time;
    }


    /**
     * Gets the lm_user value for this OrderBean.
     * 
     * @return lm_user
     */
    public java.lang.String getLm_user() {
        return lm_user;
    }


    /**
     * Sets the lm_user value for this OrderBean.
     * 
     * @param lm_user
     */
    public void setLm_user(java.lang.String lm_user) {
        this.lm_user = lm_user;
    }


    /**
     * Gets the massege value for this OrderBean.
     * 
     * @return massege
     */
    public java.lang.String getMassege() {
        return massege;
    }


    /**
     * Sets the massege value for this OrderBean.
     * 
     * @param massege
     */
    public void setMassege(java.lang.String massege) {
        this.massege = massege;
    }


    /**
     * Gets the msgInt value for this OrderBean.
     * 
     * @return msgInt
     */
    public java.lang.Integer getMsgInt() {
        return msgInt;
    }


    /**
     * Sets the msgInt value for this OrderBean.
     * 
     * @param msgInt
     */
    public void setMsgInt(java.lang.Integer msgInt) {
        this.msgInt = msgInt;
    }


    /**
     * Gets the orderPrice value for this OrderBean.
     * 
     * @return orderPrice
     */
    public java.lang.Double getOrderPrice() {
        return orderPrice;
    }


    /**
     * Sets the orderPrice value for this OrderBean.
     * 
     * @param orderPrice
     */
    public void setOrderPrice(java.lang.Double orderPrice) {
        this.orderPrice = orderPrice;
    }


    /**
     * Gets the orderSourceName value for this OrderBean.
     * 
     * @return orderSourceName
     */
    public java.lang.String getOrderSourceName() {
        return orderSourceName;
    }


    /**
     * Sets the orderSourceName value for this OrderBean.
     * 
     * @param orderSourceName
     */
    public void setOrderSourceName(java.lang.String orderSourceName) {
        this.orderSourceName = orderSourceName;
    }


    /**
     * Gets the orderStatus value for this OrderBean.
     * 
     * @return orderStatus
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.OrderStatus getOrderStatus() {
        return orderStatus;
    }


    /**
     * Sets the orderStatus value for this OrderBean.
     * 
     * @param orderStatus
     */
    public void setOrderStatus(com.lvmama.passport.processor.impl.client.shandong.model.OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    /**
     * Gets the order_id value for this OrderBean.
     * 
     * @return order_id
     */
    public java.lang.String getOrder_id() {
        return order_id;
    }


    /**
     * Sets the order_id value for this OrderBean.
     * 
     * @param order_id
     */
    public void setOrder_id(java.lang.String order_id) {
        this.order_id = order_id;
    }


    /**
     * Gets the order_source value for this OrderBean.
     * 
     * @return order_source
     */
    public java.lang.String getOrder_source() {
        return order_source;
    }


    /**
     * Sets the order_source value for this OrderBean.
     * 
     * @param order_source
     */
    public void setOrder_source(java.lang.String order_source) {
        this.order_source = order_source;
    }


    /**
     * Gets the order_status value for this OrderBean.
     * 
     * @return order_status
     */
    public java.lang.Integer getOrder_status() {
        return order_status;
    }


    /**
     * Sets the order_status value for this OrderBean.
     * 
     * @param order_status
     */
    public void setOrder_status(java.lang.Integer order_status) {
        this.order_status = order_status;
    }


    public java.lang.String getOta_order_id() {
		return ota_order_id;
	}

	public void setOta_order_id(java.lang.String ota_order_id) {
		this.ota_order_id = ota_order_id;
	}

	/**
     * Gets the product_info_id value for this OrderBean.
     * 
     * @return product_info_id
     */
    public java.lang.String getProduct_info_id() {
        return product_info_id;
    }


    /**
     * Sets the product_info_id value for this OrderBean.
     * 
     * @param product_info_id
     */
    public void setProduct_info_id(java.lang.String product_info_id) {
        this.product_info_id = product_info_id;
    }


    /**
     * Gets the product_name value for this OrderBean.
     * 
     * @return product_name
     */
    public java.lang.String getProduct_name() {
        return product_name;
    }


    /**
     * Sets the product_name value for this OrderBean.
     * 
     * @param product_name
     */
    public void setProduct_name(java.lang.String product_name) {
        this.product_name = product_name;
    }


    /**
     * Gets the purchaser_card value for this OrderBean.
     * 
     * @return purchaser_card
     */
    public java.lang.String getPurchaser_card() {
        return purchaser_card;
    }


    /**
     * Sets the purchaser_card value for this OrderBean.
     * 
     * @param purchaser_card
     */
    public void setPurchaser_card(java.lang.String purchaser_card) {
        this.purchaser_card = purchaser_card;
    }


    /**
     * Gets the purchaser_mail value for this OrderBean.
     * 
     * @return purchaser_mail
     */
    public java.lang.String getPurchaser_mail() {
        return purchaser_mail;
    }


    /**
     * Sets the purchaser_mail value for this OrderBean.
     * 
     * @param purchaser_mail
     */
    public void setPurchaser_mail(java.lang.String purchaser_mail) {
        this.purchaser_mail = purchaser_mail;
    }


    /**
     * Gets the purchaser_name value for this OrderBean.
     * 
     * @return purchaser_name
     */
    public java.lang.String getPurchaser_name() {
        return purchaser_name;
    }


    /**
     * Sets the purchaser_name value for this OrderBean.
     * 
     * @param purchaser_name
     */
    public void setPurchaser_name(java.lang.String purchaser_name) {
        this.purchaser_name = purchaser_name;
    }


    /**
     * Gets the purchaser_phone value for this OrderBean.
     * 
     * @return purchaser_phone
     */
    public java.lang.String getPurchaser_phone() {
        return purchaser_phone;
    }


    /**
     * Sets the purchaser_phone value for this OrderBean.
     * 
     * @param purchaser_phone
     */
    public void setPurchaser_phone(java.lang.String purchaser_phone) {
        this.purchaser_phone = purchaser_phone;
    }


    /**
     * Gets the purchaser_photo value for this OrderBean.
     * 
     * @return purchaser_photo
     */
    public java.lang.String getPurchaser_photo() {
        return purchaser_photo;
    }


    /**
     * Sets the purchaser_photo value for this OrderBean.
     * 
     * @param purchaser_photo
     */
    public void setPurchaser_photo(java.lang.String purchaser_photo) {
        this.purchaser_photo = purchaser_photo;
    }


    /**
     * Gets the purchaser_sex value for this OrderBean.
     * 
     * @return purchaser_sex
     */
    public java.lang.String getPurchaser_sex() {
        return purchaser_sex;
    }


    /**
     * Sets the purchaser_sex value for this OrderBean.
     * 
     * @param purchaser_sex
     */
    public void setPurchaser_sex(java.lang.String purchaser_sex) {
        this.purchaser_sex = purchaser_sex;
    }


    /**
     * Gets the source_detail value for this OrderBean.
     * 
     * @return source_detail
     */
    public java.lang.String getSource_detail() {
        return source_detail;
    }


    /**
     * Sets the source_detail value for this OrderBean.
     * 
     * @param source_detail
     */
    public void setSource_detail(java.lang.String source_detail) {
        this.source_detail = source_detail;
    }


    /**
     * Gets the strategy_id value for this OrderBean.
     * 
     * @return strategy_id
     */
    public java.lang.String getStrategy_id() {
        return strategy_id;
    }


    /**
     * Sets the strategy_id value for this OrderBean.
     * 
     * @param strategy_id
     */
    public void setStrategy_id(java.lang.String strategy_id) {
        this.strategy_id = strategy_id;
    }


    /**
     * Gets the traveler_source value for this OrderBean.
     * 
     * @return traveler_source
     */
    public java.lang.String getTraveler_source() {
        return traveler_source;
    }


    /**
     * Sets the traveler_source value for this OrderBean.
     * 
     * @param traveler_source
     */
    public void setTraveler_source(java.lang.String traveler_source) {
        this.traveler_source = traveler_source;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderBean)) return false;
        OrderBean other = (OrderBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.begin_time==null && other.getBegin_time()==null) || 
             (this.begin_time!=null &&
              this.begin_time.equals(other.getBegin_time()))) &&
            ((this.buy_time==null && other.getBuy_time()==null) || 
             (this.buy_time!=null &&
              this.buy_time.equals(other.getBuy_time()))) &&
            ((this.counts==null && other.getCounts()==null) || 
             (this.counts!=null &&
              this.counts.equals(other.getCounts()))) &&
            ((this.create_time==null && other.getCreate_time()==null) || 
             (this.create_time!=null &&
              this.create_time.equals(other.getCreate_time()))) &&
            ((this.detailSourceId==null && other.getDetailSourceId()==null) || 
             (this.detailSourceId!=null &&
              this.detailSourceId.equals(other.getDetailSourceId()))) &&
            ((this.end_time==null && other.getEnd_time()==null) || 
             (this.end_time!=null &&
              this.end_time.equals(other.getEnd_time()))) &&
            ((this.lm_time==null && other.getLm_time()==null) || 
             (this.lm_time!=null &&
              this.lm_time.equals(other.getLm_time()))) &&
            ((this.lm_user==null && other.getLm_user()==null) || 
             (this.lm_user!=null &&
              this.lm_user.equals(other.getLm_user()))) &&
            ((this.massege==null && other.getMassege()==null) || 
             (this.massege!=null &&
              this.massege.equals(other.getMassege()))) &&
            ((this.msgInt==null && other.getMsgInt()==null) || 
             (this.msgInt!=null &&
              this.msgInt.equals(other.getMsgInt()))) &&
            ((this.orderPrice==null && other.getOrderPrice()==null) || 
             (this.orderPrice!=null &&
              this.orderPrice.equals(other.getOrderPrice()))) &&
            ((this.orderSourceName==null && other.getOrderSourceName()==null) || 
             (this.orderSourceName!=null &&
              this.orderSourceName.equals(other.getOrderSourceName()))) &&
            ((this.orderStatus==null && other.getOrderStatus()==null) || 
             (this.orderStatus!=null &&
              this.orderStatus.equals(other.getOrderStatus()))) &&
            ((this.order_id==null && other.getOrder_id()==null) || 
             (this.order_id!=null &&
              this.order_id.equals(other.getOrder_id()))) &&
            ((this.order_source==null && other.getOrder_source()==null) || 
             (this.order_source!=null &&
              this.order_source.equals(other.getOrder_source()))) &&
            ((this.order_status==null && other.getOrder_status()==null) || 
             (this.order_status!=null &&
              this.order_status.equals(other.getOrder_status()))) &&
            ((this.product_info_id==null && other.getProduct_info_id()==null) || 
             (this.product_info_id!=null &&
              this.product_info_id.equals(other.getProduct_info_id()))) &&
            ((this.product_name==null && other.getProduct_name()==null) || 
             (this.product_name!=null &&
              this.product_name.equals(other.getProduct_name()))) &&
            ((this.purchaser_card==null && other.getPurchaser_card()==null) || 
             (this.purchaser_card!=null &&
              this.purchaser_card.equals(other.getPurchaser_card()))) &&
            ((this.purchaser_mail==null && other.getPurchaser_mail()==null) || 
             (this.purchaser_mail!=null &&
              this.purchaser_mail.equals(other.getPurchaser_mail()))) &&
            ((this.purchaser_name==null && other.getPurchaser_name()==null) || 
             (this.purchaser_name!=null &&
              this.purchaser_name.equals(other.getPurchaser_name()))) &&
            ((this.purchaser_phone==null && other.getPurchaser_phone()==null) || 
             (this.purchaser_phone!=null &&
              this.purchaser_phone.equals(other.getPurchaser_phone()))) &&
            ((this.purchaser_photo==null && other.getPurchaser_photo()==null) || 
             (this.purchaser_photo!=null &&
              this.purchaser_photo.equals(other.getPurchaser_photo()))) &&
            ((this.purchaser_sex==null && other.getPurchaser_sex()==null) || 
             (this.purchaser_sex!=null &&
              this.purchaser_sex.equals(other.getPurchaser_sex()))) &&
            ((this.source_detail==null && other.getSource_detail()==null) || 
             (this.source_detail!=null &&
              this.source_detail.equals(other.getSource_detail()))) &&
            ((this.strategy_id==null && other.getStrategy_id()==null) || 
             (this.strategy_id!=null &&
              this.strategy_id.equals(other.getStrategy_id()))) &&
            ((this.traveler_source==null && other.getTraveler_source()==null) || 
             (this.traveler_source!=null &&
              this.traveler_source.equals(other.getTraveler_source())));
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
        if (getBegin_time() != null) {
            _hashCode += getBegin_time().hashCode();
        }
        if (getBuy_time() != null) {
            _hashCode += getBuy_time().hashCode();
        }
        if (getCounts() != null) {
            _hashCode += getCounts().hashCode();
        }
        if (getCreate_time() != null) {
            _hashCode += getCreate_time().hashCode();
        }
        if (getDetailSourceId() != null) {
            _hashCode += getDetailSourceId().hashCode();
        }
        if (getEnd_time() != null) {
            _hashCode += getEnd_time().hashCode();
        }
        if (getLm_time() != null) {
            _hashCode += getLm_time().hashCode();
        }
        if (getLm_user() != null) {
            _hashCode += getLm_user().hashCode();
        }
        if (getMassege() != null) {
            _hashCode += getMassege().hashCode();
        }
        if (getMsgInt() != null) {
            _hashCode += getMsgInt().hashCode();
        }
        if (getOrderPrice() != null) {
            _hashCode += getOrderPrice().hashCode();
        }
        if (getOrderSourceName() != null) {
            _hashCode += getOrderSourceName().hashCode();
        }
        if (getOrderStatus() != null) {
            _hashCode += getOrderStatus().hashCode();
        }
        if (getOrder_id() != null) {
            _hashCode += getOrder_id().hashCode();
        }
        if (getOrder_source() != null) {
            _hashCode += getOrder_source().hashCode();
        }
        if (getOrder_status() != null) {
            _hashCode += getOrder_status().hashCode();
        }
        if (getProduct_info_id() != null) {
            _hashCode += getProduct_info_id().hashCode();
        }
        if (getProduct_name() != null) {
            _hashCode += getProduct_name().hashCode();
        }
        if (getPurchaser_card() != null) {
            _hashCode += getPurchaser_card().hashCode();
        }
        if (getPurchaser_mail() != null) {
            _hashCode += getPurchaser_mail().hashCode();
        }
        if (getPurchaser_name() != null) {
            _hashCode += getPurchaser_name().hashCode();
        }
        if (getPurchaser_phone() != null) {
            _hashCode += getPurchaser_phone().hashCode();
        }
        if (getPurchaser_photo() != null) {
            _hashCode += getPurchaser_photo().hashCode();
        }
        if (getPurchaser_sex() != null) {
            _hashCode += getPurchaser_sex().hashCode();
        }
        if (getSource_detail() != null) {
            _hashCode += getSource_detail().hashCode();
        }
        if (getStrategy_id() != null) {
            _hashCode += getStrategy_id().hashCode();
        }
        if (getTraveler_source() != null) {
            _hashCode += getTraveler_source().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://product.bean.xz.com", "OrderBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("begin_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "begin_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buy_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "buy_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("counts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "counts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("create_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "create_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailSourceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "detailSourceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "end_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lm_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "lm_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lm_user");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "lm_user"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("massege");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "massege"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msgInt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "msgInt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "orderPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderSourceName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "orderSourceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "orderStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "OrderStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("order_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "order_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("order_source");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "order_source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("order_status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "order_status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product_info_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "product_info_id"));
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
        elemField.setFieldName("purchaser_card");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "purchaser_card"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchaser_mail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "purchaser_mail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchaser_name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "purchaser_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchaser_phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "purchaser_phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchaser_photo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "purchaser_photo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchaser_sex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "purchaser_sex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source_detail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "source_detail"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("traveler_source");
        elemField.setXmlName(new javax.xml.namespace.QName("http://product.bean.xz.com", "traveler_source"));
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
