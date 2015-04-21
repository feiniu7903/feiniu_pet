
package com.lvmama.passport.processor.impl.client.time100;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DetailProductList complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DetailProductList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Price" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Scid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Scusname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailProductList", propOrder = {
    "tid",
    "price",
    "title",
    "scid",
    "scusname"
})
public class DetailProductList {

    @XmlElement(name = "Tid")
    protected int tid;
    @XmlElement(name = "Price")
    protected float price;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "Scid")
    protected int scid;
    @XmlElement(name = "Scusname")
    protected String scusname;

    /**
     * 获取tid属性的值。
     * 
     */
    public int getTid() {
        return tid;
    }

    /**
     * 设置tid属性的值。
     * 
     */
    public void setTid(int value) {
        this.tid = value;
    }

    /**
     * 获取price属性的值。
     * 
     */
    public float getPrice() {
        return price;
    }

    /**
     * 设置price属性的值。
     * 
     */
    public void setPrice(float value) {
        this.price = value;
    }

    /**
     * 获取title属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * 获取scid属性的值。
     * 
     */
    public int getScid() {
        return scid;
    }

    /**
     * 设置scid属性的值。
     * 
     */
    public void setScid(int value) {
        this.scid = value;
    }

    /**
     * 获取scusname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScusname() {
        return scusname;
    }

    /**
     * 设置scusname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScusname(String value) {
        this.scusname = value;
    }

}
