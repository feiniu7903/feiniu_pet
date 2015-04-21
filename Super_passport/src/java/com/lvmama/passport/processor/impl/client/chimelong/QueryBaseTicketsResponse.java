
package com.lvmama.passport.processor.impl.client.chimelong;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfBaseTicket;



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
 *         &lt;element name="out" type="{http://model.ws.agent.chimelong.cn}ArrayOfBaseTicket"/>
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
    "out"
})
@XmlRootElement(name = "QueryBaseTicketsResponse")
public class QueryBaseTicketsResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfBaseTicket out;

    /**
     * Gets the value of the out property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBaseTicket }
     *     
     */
    public ArrayOfBaseTicket getOut() {
        return out;
    }

    /**
     * Sets the value of the out property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBaseTicket }
     *     
     */
    public void setOut(ArrayOfBaseTicket value) {
        this.out = value;
    }

}
