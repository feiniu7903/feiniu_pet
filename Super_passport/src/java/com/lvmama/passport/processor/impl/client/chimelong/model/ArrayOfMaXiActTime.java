
package com.lvmama.passport.processor.impl.client.chimelong.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfMaXiActTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMaXiActTime">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaXiActTime" type="{http://model.ws.agent.chimelong.cn}MaXiActTime" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMaXiActTime", propOrder = {
    "maXiActTime"
})
public class ArrayOfMaXiActTime {

    @XmlElement(name = "MaXiActTime", required = true, nillable = true)
    protected List<MaXiActTime> maXiActTime;

    /**
     * Gets the value of the maXiActTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maXiActTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaXiActTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaXiActTime }
     * 
     * 
     */
    public List<MaXiActTime> getMaXiActTime() {
        if (maXiActTime == null) {
            maXiActTime = new ArrayList<MaXiActTime>();
        }
        return this.maXiActTime;
    }

}
