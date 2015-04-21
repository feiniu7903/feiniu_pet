
package com.lvmama.passport.processor.impl.client.yanchen;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for reserve complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reserve">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="djno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="linkman" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telphone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cdate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="paymode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payflag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="agentno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ipaddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rtypeid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rnum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rprice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="rdate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="agentkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reserve", propOrder = {
    "djno",
    "linkman",
    "telphone",
    "content",
    "cdate",
    "paymode",
    "payflag",
    "agentno",
    "ipaddress",
    "rtypeid",
    "rnum",
    "rprice",
    "rdate",
    "agentkey"
})
public class Reserve {

    protected String djno;
    protected String linkman;
    protected String telphone;
    protected String content;
    @XmlElement(required = true)
    protected XMLGregorianCalendar cdate;
    protected String paymode;
    protected boolean payflag;
    protected String agentno;
    protected String ipaddress;
    protected String rtypeid;
    protected int rnum;
    @XmlElement(required = true)
    protected BigDecimal rprice;
    @XmlElement(required = true)
    protected XMLGregorianCalendar rdate;
    protected String agentkey;

    /**
     * Gets the value of the djno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDjno() {
        return djno;
    }

    /**
     * Sets the value of the djno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDjno(String value) {
        this.djno = value;
    }

    /**
     * Gets the value of the linkman property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * Sets the value of the linkman property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkman(String value) {
        this.linkman = value;
    }

    /**
     * Gets the value of the telphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * Sets the value of the telphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelphone(String value) {
        this.telphone = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the cdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCdate() {
        return cdate;
    }

    /**
     * Sets the value of the cdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCdate(XMLGregorianCalendar value) {
        this.cdate = value;
    }

    /**
     * Gets the value of the paymode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymode() {
        return paymode;
    }

    /**
     * Sets the value of the paymode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymode(String value) {
        this.paymode = value;
    }

    /**
     * Gets the value of the payflag property.
     * 
     */
    public boolean isPayflag() {
        return payflag;
    }

    /**
     * Sets the value of the payflag property.
     * 
     */
    public void setPayflag(boolean value) {
        this.payflag = value;
    }

    /**
     * Gets the value of the agentno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentno() {
        return agentno;
    }

    /**
     * Sets the value of the agentno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentno(String value) {
        this.agentno = value;
    }

    /**
     * Gets the value of the ipaddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpaddress() {
        return ipaddress;
    }

    /**
     * Sets the value of the ipaddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpaddress(String value) {
        this.ipaddress = value;
    }

    /**
     * Gets the value of the rtypeid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtypeid() {
        return rtypeid;
    }

    /**
     * Sets the value of the rtypeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtypeid(String value) {
        this.rtypeid = value;
    }

    /**
     * Gets the value of the rnum property.
     * 
     */
    public int getRnum() {
        return rnum;
    }

    /**
     * Sets the value of the rnum property.
     * 
     */
    public void setRnum(int value) {
        this.rnum = value;
    }

    /**
     * Gets the value of the rprice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRprice() {
        return rprice;
    }

    /**
     * Sets the value of the rprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRprice(BigDecimal value) {
        this.rprice = value;
    }

    /**
     * Gets the value of the rdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRdate() {
        return rdate;
    }

    /**
     * Sets the value of the rdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRdate(XMLGregorianCalendar value) {
        this.rdate = value;
    }

    /**
     * Gets the value of the agentkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentkey() {
        return agentkey;
    }

    /**
     * Sets the value of the agentkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentkey(String value) {
        this.agentkey = value;
    }

}
