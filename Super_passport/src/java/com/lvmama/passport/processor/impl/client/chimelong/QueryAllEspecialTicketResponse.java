
package com.lvmama.passport.processor.impl.client.chimelong;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.lvmama.passport.processor.impl.client.chimelong.ticket.ArrayOfEspecialTicket;



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
 *         &lt;element name="out" type="{http://ticket.pojos.apt.grgbanking.cn}ArrayOfEspecialTicket"/>
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
@XmlRootElement(name = "QueryAllEspecialTicketResponse")
public class QueryAllEspecialTicketResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfEspecialTicket out;

    /**
     * Gets the value of the out property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEspecialTicket }
     *     
     */
    public ArrayOfEspecialTicket getOut() {
        return out;
    }

    /**
     * Sets the value of the out property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEspecialTicket }
     *     
     */
    public void setOut(ArrayOfEspecialTicket value) {
        this.out = value;
    }

}
