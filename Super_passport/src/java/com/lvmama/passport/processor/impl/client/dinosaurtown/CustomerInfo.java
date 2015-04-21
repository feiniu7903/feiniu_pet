
package com.lvmama.passport.processor.impl.client.dinosaurtown;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LinkMan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LinkSex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LinkMobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LinkCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LinkAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerInfo", propOrder = {
    "linkMan",
    "linkSex",
    "linkMobile",
    "linkCard",
    "linkAddr"
})
public class CustomerInfo {

    @XmlElement(name = "LinkMan")
    protected String linkMan;
    @XmlElement(name = "LinkSex")
    protected String linkSex;
    @XmlElement(name = "LinkMobile")
    protected String linkMobile;
    @XmlElement(name = "LinkCard")
    protected String linkCard;
    @XmlElement(name = "LinkAddr")
    protected String linkAddr;

    /**
     * Gets the value of the linkMan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * Sets the value of the linkMan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkMan(String value) {
        this.linkMan = value;
    }

    /**
     * Gets the value of the linkSex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkSex() {
        return linkSex;
    }

    /**
     * Sets the value of the linkSex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkSex(String value) {
        this.linkSex = value;
    }

    /**
     * Gets the value of the linkMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkMobile() {
        return linkMobile;
    }

    /**
     * Sets the value of the linkMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkMobile(String value) {
        this.linkMobile = value;
    }

    /**
     * Gets the value of the linkCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkCard() {
        return linkCard;
    }

    /**
     * Sets the value of the linkCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkCard(String value) {
        this.linkCard = value;
    }

    /**
     * Gets the value of the linkAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkAddr() {
        return linkAddr;
    }

    /**
     * Sets the value of the linkAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkAddr(String value) {
        this.linkAddr = value;
    }

}
