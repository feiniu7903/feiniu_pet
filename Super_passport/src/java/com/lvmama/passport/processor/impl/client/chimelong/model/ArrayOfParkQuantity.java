
package com.lvmama.passport.processor.impl.client.chimelong.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfParkQuantity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfParkQuantity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParkQuantity" type="{http://model.ws.agent.chimelong.cn}ParkQuantity" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfParkQuantity", propOrder = {
    "parkQuantity"
})
public class ArrayOfParkQuantity {

    @XmlElement(name = "ParkQuantity", required = true, nillable = true)
    protected List<ParkQuantity> parkQuantity;

    /**
     * Gets the value of the parkQuantity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parkQuantity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParkQuantity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParkQuantity }
     * 
     * 
     */
    public List<ParkQuantity> getParkQuantity() {
        if (parkQuantity == null) {
            parkQuantity = new ArrayList<ParkQuantity>();
        }
        return this.parkQuantity;
    }

}
