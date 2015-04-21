
package com.lvmama.passport.processor.impl.client.chimelong.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mer_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ver_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderResult", propOrder = {
    "merNo",
    "orderInfo",
    "sign",
    "verNo"
})
public class OrderResult {

    @XmlElementRef(name = "mer_no", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> merNo;
    @XmlElementRef(name = "orderInfo", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> orderInfo;
    @XmlElementRef(name = "sign", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> sign;
    @XmlElementRef(name = "ver_no", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> verNo;

    /**
     * Gets the value of the merNo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMerNo() {
        return merNo;
    }

    /**
     * Sets the value of the merNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMerNo(JAXBElement<String> value) {
        this.merNo = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the orderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderInfo() {
        return orderInfo;
    }

    /**
     * Sets the value of the orderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderInfo(JAXBElement<String> value) {
        this.orderInfo = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sign property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSign() {
        return sign;
    }

    /**
     * Sets the value of the sign property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSign(JAXBElement<String> value) {
        this.sign = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the verNo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVerNo() {
        return verNo;
    }

    /**
     * Sets the value of the verNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVerNo(JAXBElement<String> value) {
        this.verNo = ((JAXBElement<String> ) value);
    }

}
