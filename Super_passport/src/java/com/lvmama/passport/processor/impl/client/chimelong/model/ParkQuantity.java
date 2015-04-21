
package com.lvmama.passport.processor.impl.client.chimelong.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParkQuantity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParkQuantity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="avaiableQuantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="parkCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParkQuantity", propOrder = {
    "avaiableQuantity",
    "parkCode"
})
public class ParkQuantity {

    protected Integer avaiableQuantity;
    @XmlElementRef(name = "parkCode", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> parkCode;

    /**
     * Gets the value of the avaiableQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvaiableQuantity() {
        return avaiableQuantity;
    }

    /**
     * Sets the value of the avaiableQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvaiableQuantity(Integer value) {
        this.avaiableQuantity = value;
    }

    /**
     * Gets the value of the parkCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getParkCode() {
        return parkCode;
    }

    /**
     * Sets the value of the parkCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setParkCode(JAXBElement<String> value) {
        this.parkCode = ((JAXBElement<String> ) value);
    }

}
