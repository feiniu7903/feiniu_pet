
package com.lvmama.passport.processor.impl.client.dinosaurtown;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="testSignatureResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "testSignatureResult"
})
@XmlRootElement(name = "testSignatureResponse")
public class TestSignatureResponse {

    protected String testSignatureResult;

    /**
     * Gets the value of the testSignatureResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestSignatureResult() {
        return testSignatureResult;
    }

    /**
     * Sets the value of the testSignatureResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestSignatureResult(String value) {
        this.testSignatureResult = value;
    }

}
