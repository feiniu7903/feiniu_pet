
package com.lvmama.passport.processor.impl.client.chimelong.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfParkBaseTicket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfParkBaseTicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParkBaseTicket" type="{http://model.ws.agent.chimelong.cn}ParkBaseTicket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfParkBaseTicket", propOrder = {
    "parkBaseTicket"
})
public class ArrayOfParkBaseTicket {

    @XmlElement(name = "ParkBaseTicket", required = true, nillable = true)
    protected List<ParkBaseTicket> parkBaseTicket;

    /**
     * Gets the value of the parkBaseTicket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parkBaseTicket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParkBaseTicket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParkBaseTicket }
     * 
     * 
     */
    public List<ParkBaseTicket> getParkBaseTicket() {
        if (parkBaseTicket == null) {
            parkBaseTicket = new ArrayList<ParkBaseTicket>();
        }
        return this.parkBaseTicket;
    }

}
