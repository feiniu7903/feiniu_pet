
package com.lvmama.passport.processor.impl.client.chimelong.model;

import java.math.BigDecimal;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.chimelong.agent.ws.model package. 
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

    private final static QName _ParkName_QNAME = new QName("http://model.ws.agent.chimelong.cn", "name");
    private final static QName _ParkCode_QNAME = new QName("http://model.ws.agent.chimelong.cn", "code");
    private final static QName _OrderResultVerNo_QNAME = new QName("http://model.ws.agent.chimelong.cn", "ver_no");
    private final static QName _OrderResultSign_QNAME = new QName("http://model.ws.agent.chimelong.cn", "sign");
    private final static QName _OrderResultOrderInfo_QNAME = new QName("http://model.ws.agent.chimelong.cn", "orderInfo");
    private final static QName _OrderResultMerNo_QNAME = new QName("http://model.ws.agent.chimelong.cn", "mer_no");
    private final static QName _ParkQuantityParkCode_QNAME = new QName("http://model.ws.agent.chimelong.cn", "parkCode");
    private final static QName _MaXiActTimeActId_QNAME = new QName("http://model.ws.agent.chimelong.cn", "actId");
    private final static QName _QueryResultDateEnd_QNAME = new QName("http://model.ws.agent.chimelong.cn", "date_end");
    private final static QName _QueryResultDateStart_QNAME = new QName("http://model.ws.agent.chimelong.cn", "date_start");
    private final static QName _QueryResultOrderList_QNAME = new QName("http://model.ws.agent.chimelong.cn", "orderList");
    private final static QName _CancelResultResult_QNAME = new QName("http://model.ws.agent.chimelong.cn", "result");
    private final static QName _CancelResultChmTime_QNAME = new QName("http://model.ws.agent.chimelong.cn", "chm_time");
    private final static QName _CancelResultMerOid_QNAME = new QName("http://model.ws.agent.chimelong.cn", "mer_oid");
    private final static QName _TicketBaseCode_QNAME = new QName("http://model.ws.agent.chimelong.cn", "baseCode");
    private final static QName _TicketOrigPrice_QNAME = new QName("http://model.ws.agent.chimelong.cn", "origPrice");
    private final static QName _TicketBaseName_QNAME = new QName("http://model.ws.agent.chimelong.cn", "baseName");
    private final static QName _TicketBaseAndCateCode_QNAME = new QName("http://model.ws.agent.chimelong.cn", "baseAndCateCode");
    private final static QName _TicketPriceCode_QNAME = new QName("http://model.ws.agent.chimelong.cn", "priceCode");
    private final static QName _TicketPrice1_QNAME = new QName("http://model.ws.agent.chimelong.cn", "price1");
    private final static QName _TicketCateName_QNAME = new QName("http://model.ws.agent.chimelong.cn", "cateName");
    private final static QName _TicketPrice2_QNAME = new QName("http://model.ws.agent.chimelong.cn", "price2");
    private final static QName _TicketCateCode_QNAME = new QName("http://model.ws.agent.chimelong.cn", "cateCode");
    private final static QName _TicketPromoName_QNAME = new QName("http://model.ws.agent.chimelong.cn", "promoName");
    private final static QName _ParkBaseTicketBaseTicketCode_QNAME = new QName("http://model.ws.agent.chimelong.cn", "baseTicketCode");
    private final static QName _QueryTicketPrice_QNAME = new QName("http://model.ws.agent.chimelong.cn", "price");
    private final static QName _QueryTicketTkType_QNAME = new QName("http://model.ws.agent.chimelong.cn", "tk_type");
    private final static QName _OrderInfoTime_QNAME = new QName("http://model.ws.agent.chimelong.cn", "time");
    private final static QName _OrderInfoStatus_QNAME = new QName("http://model.ws.agent.chimelong.cn", "status");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.chimelong.agent.ws.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfParkQuantity }
     * 
     */
    public ArrayOfParkQuantity createArrayOfParkQuantity() {
        return new ArrayOfParkQuantity();
    }

    /**
     * Create an instance of {@link ArrayOfPark }
     * 
     */
    public ArrayOfPark createArrayOfPark() {
        return new ArrayOfPark();
    }

    /**
     * Create an instance of {@link QueryResult }
     * 
     */
    public QueryResult createQueryResult() {
        return new QueryResult();
    }

    /**
     * Create an instance of {@link ArrayOfMaXiActTime }
     * 
     */
    public ArrayOfMaXiActTime createArrayOfMaXiActTime() {
        return new ArrayOfMaXiActTime();
    }

    /**
     * Create an instance of {@link ArrayOfTicket }
     * 
     */
    public ArrayOfTicket createArrayOfTicket() {
        return new ArrayOfTicket();
    }

    /**
     * Create an instance of {@link BaseCategory }
     * 
     */
    public BaseCategory createBaseCategory() {
        return new BaseCategory();
    }

    /**
     * Create an instance of {@link Park }
     * 
     */
    public Park createPark() {
        return new Park();
    }

    /**
     * Create an instance of {@link MaXiActTime }
     * 
     */
    public MaXiActTime createMaXiActTime() {
        return new MaXiActTime();
    }

    /**
     * Create an instance of {@link ParkBaseTicket }
     * 
     */
    public ParkBaseTicket createParkBaseTicket() {
        return new ParkBaseTicket();
    }

    /**
     * Create an instance of {@link ArrayOfOrderInfo }
     * 
     */
    public ArrayOfOrderInfo createArrayOfOrderInfo() {
        return new ArrayOfOrderInfo();
    }

    /**
     * Create an instance of {@link ParkQuantity }
     * 
     */
    public ParkQuantity createParkQuantity() {
        return new ParkQuantity();
    }

    /**
     * Create an instance of {@link BaseTicket }
     * 
     */
    public BaseTicket createBaseTicket() {
        return new BaseTicket();
    }

    /**
     * Create an instance of {@link CancelResult }
     * 
     */
    public CancelResult createCancelResult() {
        return new CancelResult();
    }

    /**
     * Create an instance of {@link ArrayOfParkBaseTicket }
     * 
     */
    public ArrayOfParkBaseTicket createArrayOfParkBaseTicket() {
        return new ArrayOfParkBaseTicket();
    }

    /**
     * Create an instance of {@link OrderInfo }
     * 
     */
    public OrderInfo createOrderInfo() {
        return new OrderInfo();
    }

    /**
     * Create an instance of {@link ArrayOfBaseTicket }
     * 
     */
    public ArrayOfBaseTicket createArrayOfBaseTicket() {
        return new ArrayOfBaseTicket();
    }

    /**
     * Create an instance of {@link OrderResult }
     * 
     */
    public OrderResult createOrderResult() {
        return new OrderResult();
    }

    /**
     * Create an instance of {@link ArrayOfBaseCategory }
     * 
     */
    public ArrayOfBaseCategory createArrayOfBaseCategory() {
        return new ArrayOfBaseCategory();
    }

    /**
     * Create an instance of {@link Ticket }
     * 
     */
    public Ticket createTicket() {
        return new Ticket();
    }

    /**
     * Create an instance of {@link ArrayOfQueryTicket }
     * 
     */
    public ArrayOfQueryTicket createArrayOfQueryTicket() {
        return new ArrayOfQueryTicket();
    }

    /**
     * Create an instance of {@link QueryTicket }
     * 
     */
    public QueryTicket createQueryTicket() {
        return new QueryTicket();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "name", scope = Park.class)
    public JAXBElement<String> createParkName(String value) {
        return new JAXBElement<String>(_ParkName_QNAME, String.class, Park.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "code", scope = Park.class)
    public JAXBElement<String> createParkCode(String value) {
        return new JAXBElement<String>(_ParkCode_QNAME, String.class, Park.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "name", scope = BaseCategory.class)
    public JAXBElement<String> createBaseCategoryName(String value) {
        return new JAXBElement<String>(_ParkName_QNAME, String.class, BaseCategory.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "code", scope = BaseCategory.class)
    public JAXBElement<String> createBaseCategoryCode(String value) {
        return new JAXBElement<String>(_ParkCode_QNAME, String.class, BaseCategory.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "ver_no", scope = OrderResult.class)
    public JAXBElement<String> createOrderResultVerNo(String value) {
        return new JAXBElement<String>(_OrderResultVerNo_QNAME, String.class, OrderResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "sign", scope = OrderResult.class)
    public JAXBElement<String> createOrderResultSign(String value) {
        return new JAXBElement<String>(_OrderResultSign_QNAME, String.class, OrderResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "orderInfo", scope = OrderResult.class)
    public JAXBElement<String> createOrderResultOrderInfo(String value) {
        return new JAXBElement<String>(_OrderResultOrderInfo_QNAME, String.class, OrderResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "mer_no", scope = OrderResult.class)
    public JAXBElement<String> createOrderResultMerNo(String value) {
        return new JAXBElement<String>(_OrderResultMerNo_QNAME, String.class, OrderResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "parkCode", scope = ParkQuantity.class)
    public JAXBElement<String> createParkQuantityParkCode(String value) {
        return new JAXBElement<String>(_ParkQuantityParkCode_QNAME, String.class, ParkQuantity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "name", scope = BaseTicket.class)
    public JAXBElement<String> createBaseTicketName(String value) {
        return new JAXBElement<String>(_ParkName_QNAME, String.class, BaseTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "code", scope = BaseTicket.class)
    public JAXBElement<String> createBaseTicketCode(String value) {
        return new JAXBElement<String>(_ParkCode_QNAME, String.class, BaseTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "actId", scope = MaXiActTime.class)
    public JAXBElement<String> createMaXiActTimeActId(String value) {
        return new JAXBElement<String>(_MaXiActTimeActId_QNAME, String.class, MaXiActTime.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "date_end", scope = QueryResult.class)
    public JAXBElement<String> createQueryResultDateEnd(String value) {
        return new JAXBElement<String>(_QueryResultDateEnd_QNAME, String.class, QueryResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "date_start", scope = QueryResult.class)
    public JAXBElement<String> createQueryResultDateStart(String value) {
        return new JAXBElement<String>(_QueryResultDateStart_QNAME, String.class, QueryResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "mer_no", scope = QueryResult.class)
    public JAXBElement<String> createQueryResultMerNo(String value) {
        return new JAXBElement<String>(_OrderResultMerNo_QNAME, String.class, QueryResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOrderInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "orderList", scope = QueryResult.class)
    public JAXBElement<ArrayOfOrderInfo> createQueryResultOrderList(ArrayOfOrderInfo value) {
        return new JAXBElement<ArrayOfOrderInfo>(_QueryResultOrderList_QNAME, ArrayOfOrderInfo.class, QueryResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "result", scope = CancelResult.class)
    public JAXBElement<String> createCancelResultResult(String value) {
        return new JAXBElement<String>(_CancelResultResult_QNAME, String.class, CancelResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "mer_no", scope = CancelResult.class)
    public JAXBElement<String> createCancelResultMerNo(String value) {
        return new JAXBElement<String>(_OrderResultMerNo_QNAME, String.class, CancelResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "chm_time", scope = CancelResult.class)
    public JAXBElement<String> createCancelResultChmTime(String value) {
        return new JAXBElement<String>(_CancelResultChmTime_QNAME, String.class, CancelResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "mer_oid", scope = CancelResult.class)
    public JAXBElement<String> createCancelResultMerOid(String value) {
        return new JAXBElement<String>(_CancelResultMerOid_QNAME, String.class, CancelResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "baseCode", scope = Ticket.class)
    public JAXBElement<String> createTicketBaseCode(String value) {
        return new JAXBElement<String>(_TicketBaseCode_QNAME, String.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "origPrice", scope = Ticket.class)
    public JAXBElement<BigDecimal> createTicketOrigPrice(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_TicketOrigPrice_QNAME, BigDecimal.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "baseName", scope = Ticket.class)
    public JAXBElement<String> createTicketBaseName(String value) {
        return new JAXBElement<String>(_TicketBaseName_QNAME, String.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "baseAndCateCode", scope = Ticket.class)
    public JAXBElement<String> createTicketBaseAndCateCode(String value) {
        return new JAXBElement<String>(_TicketBaseAndCateCode_QNAME, String.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "priceCode", scope = Ticket.class)
    public JAXBElement<String> createTicketPriceCode(String value) {
        return new JAXBElement<String>(_TicketPriceCode_QNAME, String.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "price1", scope = Ticket.class)
    public JAXBElement<BigDecimal> createTicketPrice1(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_TicketPrice1_QNAME, BigDecimal.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "cateName", scope = Ticket.class)
    public JAXBElement<String> createTicketCateName(String value) {
        return new JAXBElement<String>(_TicketCateName_QNAME, String.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "price2", scope = Ticket.class)
    public JAXBElement<BigDecimal> createTicketPrice2(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_TicketPrice2_QNAME, BigDecimal.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "cateCode", scope = Ticket.class)
    public JAXBElement<String> createTicketCateCode(String value) {
        return new JAXBElement<String>(_TicketCateCode_QNAME, String.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "promoName", scope = Ticket.class)
    public JAXBElement<String> createTicketPromoName(String value) {
        return new JAXBElement<String>(_TicketPromoName_QNAME, String.class, Ticket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "baseTicketCode", scope = ParkBaseTicket.class)
    public JAXBElement<String> createParkBaseTicketBaseTicketCode(String value) {
        return new JAXBElement<String>(_ParkBaseTicketBaseTicketCode_QNAME, String.class, ParkBaseTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "parkCode", scope = ParkBaseTicket.class)
    public JAXBElement<String> createParkBaseTicketParkCode(String value) {
        return new JAXBElement<String>(_ParkQuantityParkCode_QNAME, String.class, ParkBaseTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "price", scope = QueryTicket.class)
    public JAXBElement<BigDecimal> createQueryTicketPrice(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_QueryTicketPrice_QNAME, BigDecimal.class, QueryTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "tk_type", scope = QueryTicket.class)
    public JAXBElement<String> createQueryTicketTkType(String value) {
        return new JAXBElement<String>(_QueryTicketTkType_QNAME, String.class, QueryTicket.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "time", scope = OrderInfo.class)
    public JAXBElement<String> createOrderInfoTime(String value) {
        return new JAXBElement<String>(_OrderInfoTime_QNAME, String.class, OrderInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "status", scope = OrderInfo.class)
    public JAXBElement<String> createOrderInfoStatus(String value) {
        return new JAXBElement<String>(_OrderInfoStatus_QNAME, String.class, OrderInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://model.ws.agent.chimelong.cn", name = "mer_oid", scope = OrderInfo.class)
    public JAXBElement<String> createOrderInfoMerOid(String value) {
        return new JAXBElement<String>(_CancelResultMerOid_QNAME, String.class, OrderInfo.class, value);
    }

}
