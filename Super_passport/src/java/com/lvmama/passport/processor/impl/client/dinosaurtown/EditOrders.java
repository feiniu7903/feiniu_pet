
package com.lvmama.passport.processor.impl.client.dinosaurtown;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MerchantCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RefNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Customer" type="{http://www.konglongcheng.com/}CustomerInfo" minOccurs="0"/>
 *         &lt;element name="OrderProducts" type="{http://www.konglongcheng.com/}ArrayOfOrderProduct" minOccurs="0"/>
 *         &lt;element name="SetFlag" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Signature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "merchantCode",
    "refNo",
    "orderNo",
    "customer",
    "orderProducts",
    "setFlag",
    "signature"
})
@XmlRootElement(name = "EditOrders")
public class EditOrders {

    @XmlElement(name = "MerchantCode")
    protected String merchantCode;
    @XmlElement(name = "RefNo")
    protected String refNo;
    @XmlElement(name = "OrderNo")
    protected String orderNo;
    @XmlElement(name = "Customer")
    protected CustomerInfo customer;
    @XmlElement(name = "OrderProducts")
    protected ArrayOfOrderProduct orderProducts;
    @XmlElement(name = "SetFlag")
    protected int setFlag;
    @XmlElement(name = "Signature")
    protected String signature;

    /**
     * Gets the value of the merchantCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     * Sets the value of the merchantCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantCode(String value) {
        this.merchantCode = value;
    }

    /**
     * Gets the value of the refNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefNo() {
        return refNo;
    }

    /**
     * Sets the value of the refNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefNo(String value) {
        this.refNo = value;
    }

    /**
     * Gets the value of the orderNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Sets the value of the orderNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerInfo }
     *     
     */
    public CustomerInfo getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerInfo }
     *     
     */
    public void setCustomer(CustomerInfo value) {
        this.customer = value;
    }

    /**
     * Gets the value of the orderProducts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderProduct }
     *     
     */
    public ArrayOfOrderProduct getOrderProducts() {
        return orderProducts;
    }

    /**
     * Sets the value of the orderProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderProduct }
     *     
     */
    public void setOrderProducts(ArrayOfOrderProduct value) {
        this.orderProducts = value;
    }

    /**
     * Gets the value of the setFlag property.
     * 
     */
    public int getSetFlag() {
        return setFlag;
    }

    /**
     * Sets the value of the setFlag property.
     * 
     */
    public void setSetFlag(int value) {
        this.setFlag = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignature(String value) {
        this.signature = value;
    }

}
