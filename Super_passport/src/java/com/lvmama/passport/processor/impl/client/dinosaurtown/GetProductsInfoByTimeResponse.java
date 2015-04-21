
package com.lvmama.passport.processor.impl.client.dinosaurtown;

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
 *         &lt;element name="GetProductsInfoByTimeResult" type="{http://www.konglongcheng.com/}ArrayOfProductsInfo" minOccurs="0"/>
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
    "getProductsInfoByTimeResult"
})
@XmlRootElement(name = "GetProductsInfoByTimeResponse")
public class GetProductsInfoByTimeResponse {

    @XmlElement(name = "GetProductsInfoByTimeResult")
    protected ArrayOfProductsInfo getProductsInfoByTimeResult;

    /**
     * Gets the value of the getProductsInfoByTimeResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProductsInfo }
     *     
     */
    public ArrayOfProductsInfo getGetProductsInfoByTimeResult() {
        return getProductsInfoByTimeResult;
    }

    /**
     * Sets the value of the getProductsInfoByTimeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProductsInfo }
     *     
     */
    public void setGetProductsInfoByTimeResult(ArrayOfProductsInfo value) {
        this.getProductsInfoByTimeResult = value;
    }

}
