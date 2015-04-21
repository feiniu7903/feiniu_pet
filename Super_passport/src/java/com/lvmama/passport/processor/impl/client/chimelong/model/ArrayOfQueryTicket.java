
package com.lvmama.passport.processor.impl.client.chimelong.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfQueryTicket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfQueryTicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="QueryTicket" type="{http://model.ws.agent.chimelong.cn}QueryTicket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfQueryTicket", propOrder = {
    "queryTicket"
})
public class ArrayOfQueryTicket {

    @XmlElement(name = "QueryTicket", required = true, nillable = true)
    protected List<QueryTicket> queryTicket;

    /**
     * Gets the value of the queryTicket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the queryTicket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQueryTicket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryTicket }
     * 
     * 
     */
    public List<QueryTicket> getQueryTicket() {
        if (queryTicket == null) {
            queryTicket = new ArrayList<QueryTicket>();
        }
        return this.queryTicket;
    }

}
