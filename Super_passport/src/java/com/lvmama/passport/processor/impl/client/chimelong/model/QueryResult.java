
package com.lvmama.passport.processor.impl.client.chimelong.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date_end" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_start" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mer_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderList" type="{http://model.ws.agent.chimelong.cn}ArrayOfOrderInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryResult", propOrder = {
    "dateEnd",
    "dateStart",
    "merNo",
    "orderList"
})
public class QueryResult {

    @XmlElementRef(name = "date_end", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> dateEnd;
    @XmlElementRef(name = "date_start", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> dateStart;
    @XmlElementRef(name = "mer_no", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> merNo;
    @XmlElementRef(name = "orderList", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<ArrayOfOrderInfo> orderList;

    /**
     * Gets the value of the dateEnd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDateEnd() {
        return dateEnd;
    }

    /**
     * Sets the value of the dateEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDateEnd(JAXBElement<String> value) {
        this.dateEnd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dateStart property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDateStart() {
        return dateStart;
    }

    /**
     * Sets the value of the dateStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDateStart(JAXBElement<String> value) {
        this.dateStart = ((JAXBElement<String> ) value);
    }

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
     * Gets the value of the orderList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfOrderInfo }{@code >}
     *     
     */
    public JAXBElement<ArrayOfOrderInfo> getOrderList() {
        return orderList;
    }

    /**
     * Sets the value of the orderList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfOrderInfo }{@code >}
     *     
     */
    public void setOrderList(JAXBElement<ArrayOfOrderInfo> value) {
        this.orderList = ((JAXBElement<ArrayOfOrderInfo> ) value);
    }

}
