
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
 *         &lt;element name="GetOrdersStatusResult" type="{http://www.konglongcheng.com/}ArrayOfOrderStatus" minOccurs="0"/>
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
    "getOrdersStatusResult"
})
@XmlRootElement(name = "GetOrdersStatusResponse")
public class GetOrdersStatusResponse {

    @XmlElement(name = "GetOrdersStatusResult")
    protected ArrayOfOrderStatus getOrdersStatusResult;

    /**
     * Gets the value of the getOrdersStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderStatus }
     *     
     */
    public ArrayOfOrderStatus getGetOrdersStatusResult() {
        return getOrdersStatusResult;
    }

    /**
     * Sets the value of the getOrdersStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderStatus }
     *     
     */
    public void setGetOrdersStatusResult(ArrayOfOrderStatus value) {
        this.getOrdersStatusResult = value;
    }

}
