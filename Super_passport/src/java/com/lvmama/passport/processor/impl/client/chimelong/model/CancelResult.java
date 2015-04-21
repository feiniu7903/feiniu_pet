
package com.lvmama.passport.processor.impl.client.chimelong.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CancelResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancelResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chm_time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mer_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mer_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelResult", propOrder = {
    "chmTime",
    "merNo",
    "merOid",
    "result"
})
public class CancelResult {

    @XmlElementRef(name = "chm_time", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> chmTime;
    @XmlElementRef(name = "mer_no", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> merNo;
    @XmlElementRef(name = "mer_oid", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> merOid;
    @XmlElementRef(name = "result", namespace = "http://model.ws.agent.chimelong.cn", type = JAXBElement.class)
    protected JAXBElement<String> result;

    /**
     * Gets the value of the chmTime property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getChmTime() {
        return chmTime;
    }

    /**
     * Sets the value of the chmTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setChmTime(JAXBElement<String> value) {
        this.chmTime = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the merNo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMerNo() {
        return merNo;
    }

    /**
     * Sets the value of the merNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMerNo(JAXBElement<String> value) {
        this.merNo = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the merOid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMerOid() {
        return merOid;
    }

    /**
     * Sets the value of the merOid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMerOid(JAXBElement<String> value) {
        this.merOid = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResult(JAXBElement<String> value) {
        this.result = ((JAXBElement<String> ) value);
    }

}
