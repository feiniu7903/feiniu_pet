
package com.lvmama.passport.processor.impl.client.yanchen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="DataList" type="{http://my0519.com/}reserve" minOccurs="0"/>
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
    "dataList"
})
@XmlRootElement(name = "AddData")
public class AddData {

    @XmlElement(name = "DataList")
    protected Reserve dataList;

    /**
     * Gets the value of the dataList property.
     * 
     * @return
     *     possible object is
     *     {@link Reserve }
     *     
     */
    public Reserve getDataList() {
        return dataList;
    }

    /**
     * Sets the value of the dataList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reserve }
     *     
     */
    public void setDataList(Reserve value) {
        this.dataList = value;
    }

}
