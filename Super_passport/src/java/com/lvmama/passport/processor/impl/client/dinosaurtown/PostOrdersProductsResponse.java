
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
 *         &lt;element name="PostOrdersProductsResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "postOrdersProductsResult"
})
@XmlRootElement(name = "PostOrdersProductsResponse")
public class PostOrdersProductsResponse {

    @XmlElement(name = "PostOrdersProductsResult")
    protected String postOrdersProductsResult;

    /**
     * Gets the value of the postOrdersProductsResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostOrdersProductsResult() {
        return postOrdersProductsResult;
    }

    /**
     * Sets the value of the postOrdersProductsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostOrdersProductsResult(String value) {
        this.postOrdersProductsResult = value;
    }

}
