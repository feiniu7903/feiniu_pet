
package com.lvmama.passport.processor.impl.client.chimelong.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfBaseTicket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfBaseTicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BaseTicket" type="{http://model.ws.agent.chimelong.cn}BaseTicket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfBaseTicket", propOrder = {
    "baseTicket"
})
public class ArrayOfBaseTicket {

    @XmlElement(name = "BaseTicket", required = true, nillable = true)
    protected List<BaseTicket> baseTicket;

    /**
     * Gets the value of the baseTicket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseTicket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseTicket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BaseTicket }
     * 
     * 
     */
    public List<BaseTicket> getBaseTicket() {
        if (baseTicket == null) {
            baseTicket = new ArrayList<BaseTicket>();
        }
        return this.baseTicket;
    }

}
