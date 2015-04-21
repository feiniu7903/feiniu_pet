package com.lvmama.passport.processor.impl.client.time100;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="API_KEY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="API_PSW" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "apikey",
    "apipsw",
    "key",
    "start",
    "end"
})
@XmlRootElement(name = "productList")
public class ProductList {

    @XmlElement(name = "API_KEY")
    protected String apikey;
    @XmlElement(name = "API_PSW")
    protected String apipsw;
    @XmlElement(name = "Key")
    protected String key;
    protected int start;
    protected int end;

    /**
     * 获取apikey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIKEY() {
        return apikey;
    }

    /**
     * 设置apikey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIKEY(String value) {
        this.apikey = value;
    }

    /**
     * 获取apipsw属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIPSW() {
        return apipsw;
    }

    /**
     * 设置apipsw属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIPSW(String value) {
        this.apipsw = value;
    }

    /**
     * 获取key属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置key属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * 获取start属性的值。
     * 
     */
    public int getStart() {
        return start;
    }

    /**
     * 设置start属性的值。
     * 
     */
    public void setStart(int value) {
        this.start = value;
    }

    /**
     * 获取end属性的值。
     * 
     */
    public int getEnd() {
        return end;
    }

    /**
     * 设置end属性的值。
     * 
     */
    public void setEnd(int value) {
        this.end = value;
    }

}
