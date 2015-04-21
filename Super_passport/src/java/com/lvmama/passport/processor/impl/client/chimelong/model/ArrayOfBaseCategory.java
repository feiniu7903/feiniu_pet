
package com.lvmama.passport.processor.impl.client.chimelong.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfBaseCategory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfBaseCategory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BaseCategory" type="{http://model.ws.agent.chimelong.cn}BaseCategory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfBaseCategory", propOrder = {
    "baseCategory"
})
public class ArrayOfBaseCategory {

    @XmlElement(name = "BaseCategory", required = true, nillable = true)
    protected List<BaseCategory> baseCategory;

    /**
     * Gets the value of the baseCategory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseCategory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BaseCategory }
     * 
     * 
     */
    public List<BaseCategory> getBaseCategory() {
        if (baseCategory == null) {
            baseCategory = new ArrayList<BaseCategory>();
        }
        return this.baseCategory;
    }

}
