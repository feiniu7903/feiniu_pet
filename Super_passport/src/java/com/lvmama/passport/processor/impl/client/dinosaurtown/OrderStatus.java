
package com.lvmama.passport.processor.impl.client.dinosaurtown;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RefNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="InTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderStatus", propOrder = {
    "refNo",
    "productNo",
    "inQuantity",
    "inTime",
    "orderNo"
})
public class OrderStatus {

    @XmlElement(name = "RefNo")
    protected String refNo;
    @XmlElement(name = "ProductNo")
    protected String productNo;
    @XmlElement(name = "InQuantity")
    protected int inQuantity;
    @XmlElement(name = "InTime")
    protected String inTime;
    @XmlElement(name = "OrderNo")
    protected String orderNo;

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
     * Gets the value of the productNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * Sets the value of the productNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductNo(String value) {
        this.productNo = value;
    }

    /**
     * Gets the value of the inQuantity property.
     * 
     */
    public int getInQuantity() {
        return inQuantity;
    }

    /**
     * Sets the value of the inQuantity property.
     * 
     */
    public void setInQuantity(int value) {
        this.inQuantity = value;
    }

    /**
     * Gets the value of the inTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInTime() {
        return inTime;
    }

    /**
     * Sets the value of the inTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInTime(String value) {
        this.inTime = value;
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

}
