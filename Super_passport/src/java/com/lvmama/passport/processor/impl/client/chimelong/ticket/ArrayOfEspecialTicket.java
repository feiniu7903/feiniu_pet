
package com.lvmama.passport.processor.impl.client.chimelong.ticket;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfEspecialTicket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfEspecialTicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EspecialTicket" type="{http://ticket.pojos.apt.grgbanking.cn}EspecialTicket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfEspecialTicket", propOrder = {
    "especialTicket"
})
public class ArrayOfEspecialTicket {

    @XmlElement(name = "EspecialTicket", required = true, nillable = true)
    protected List<EspecialTicket> especialTicket;

    /**
     * Gets the value of the especialTicket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the especialTicket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEspecialTicket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EspecialTicket }
     * 
     * 
     */
    public List<EspecialTicket> getEspecialTicket() {
        if (especialTicket == null) {
            especialTicket = new ArrayList<EspecialTicket>();
        }
        return this.especialTicket;
    }

}
