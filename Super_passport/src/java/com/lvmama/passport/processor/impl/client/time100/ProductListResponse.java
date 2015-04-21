package com.lvmama.passport.processor.impl.client.time100;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productListResult" type="{http://www.time100.net/}ArrayOfProductList" minOccurs="0"/>
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
    "productListResult"
})
@XmlRootElement(name = "productListResponse")
public class ProductListResponse {

    protected ArrayOfProductList productListResult;

    /**
     * 获取productListResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProductList }
     *     
     */
    public ArrayOfProductList getProductListResult() {
        return productListResult;
    }

    /**
     * 设置productListResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProductList }
     *     
     */
    public void setProductListResult(ArrayOfProductList value) {
        this.productListResult = value;
    }

}
