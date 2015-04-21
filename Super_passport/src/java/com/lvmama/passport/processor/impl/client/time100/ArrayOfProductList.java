
package com.lvmama.passport.processor.impl.client.time100;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfProductList complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProductList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetailProductList" type="{http://www.time100.net/}DetailProductList" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProductList", propOrder = {
    "detailProductList"
})
public class ArrayOfProductList {

    @XmlElement(name = "DetailProductList", nillable = true)
    protected List<DetailProductList> detailProductList;

    /**
     * Gets the value of the detailProductList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detailProductList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetailProductList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetailProductList }
     * 
     * 
     */
    public List<DetailProductList> getDetailProductList() {
        if (detailProductList == null) {
            detailProductList = new ArrayList<DetailProductList>();
        }
        return this.detailProductList;
    }

}
