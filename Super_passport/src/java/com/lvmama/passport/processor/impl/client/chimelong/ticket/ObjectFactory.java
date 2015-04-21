
package com.lvmama.passport.processor.impl.client.chimelong.ticket;

import java.math.BigDecimal;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.grgbanking.apt.pojos.ticket package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EspecialTicketQuantity_QNAME = new QName("http://ticket.pojos.apt.grgbanking.cn", "quantity");
    private final static QName _EspecialTicketPrice2_QNAME = new QName("http://ticket.pojos.apt.grgbanking.cn", "price2");
    private final static QName _EspecialTicketIssale_QNAME = new QName("http://ticket.pojos.apt.grgbanking.cn", "issale");
    private final static QName _EspecialTicketId_QNAME = new QName("http://ticket.pojos.apt.grgbanking.cn", "id");
    private final static QName _EspecialTicketPrice_QNAME = new QName("http://ticket.pojos.apt.grgbanking.cn", "price");
    private final static QName _EspecialTicketDesc_QNAME = new QName("http://ticket.pojos.apt.grgbanking.cn", "desc");
    private final static QName _EspecialTicketName_QNAME = new QName("http://ticket.pojos.apt.grgbanking.cn", "name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.grgbanking.apt.pojos.ticket
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EspecialTicket }
     * 
     */
    public EspecialTicket createEspecialTicket() {
        return new EspecialTicket();
    }

    /**
     * Create an instance of {@link ArrayOfEspecialTicket }
     * 
     */
    public ArrayOfEspecialTicket createArrayOfEspecialTicket() {
        return new ArrayOfEspecialTicket();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ticket.pojos.apt.grgbanking.cn", name = "quantity", scope = EspecialTicket.class)
    public JAXBElement<Integer> createEspecialTicketQuantity(Integer value) {
        return new JAXBElement<Integer>(_EspecialTicketQuantity_QNAME, Integer.class, EspecialTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ticket.pojos.apt.grgbanking.cn", name = "price2", scope = EspecialTicket.class)
    public JAXBElement<BigDecimal> createEspecialTicketPrice2(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_EspecialTicketPrice2_QNAME, BigDecimal.class, EspecialTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ticket.pojos.apt.grgbanking.cn", name = "issale", scope = EspecialTicket.class)
    public JAXBElement<String> createEspecialTicketIssale(String value) {
        return new JAXBElement<String>(_EspecialTicketIssale_QNAME, String.class, EspecialTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ticket.pojos.apt.grgbanking.cn", name = "id", scope = EspecialTicket.class)
    public JAXBElement<String> createEspecialTicketId(String value) {
        return new JAXBElement<String>(_EspecialTicketId_QNAME, String.class, EspecialTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ticket.pojos.apt.grgbanking.cn", name = "price", scope = EspecialTicket.class)
    public JAXBElement<BigDecimal> createEspecialTicketPrice(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_EspecialTicketPrice_QNAME, BigDecimal.class, EspecialTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ticket.pojos.apt.grgbanking.cn", name = "desc", scope = EspecialTicket.class)
    public JAXBElement<String> createEspecialTicketDesc(String value) {
        return new JAXBElement<String>(_EspecialTicketDesc_QNAME, String.class, EspecialTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ticket.pojos.apt.grgbanking.cn", name = "name", scope = EspecialTicket.class)
    public JAXBElement<String> createEspecialTicketName(String value) {
        return new JAXBElement<String>(_EspecialTicketName_QNAME, String.class, EspecialTicket.class, value);
    }

}
