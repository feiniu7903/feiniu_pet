
package com.lvmama.passport.processor.impl.client.chimelong;

import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.datatype.XMLGregorianCalendar;

import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfBaseCategory;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfBaseTicket;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfMaXiActTime;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfPark;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfParkBaseTicket;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfParkQuantity;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfQueryTicket;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfTicket;
import com.lvmama.passport.processor.impl.client.chimelong.model.CancelResult;
import com.lvmama.passport.processor.impl.client.chimelong.model.OrderInfo;
import com.lvmama.passport.processor.impl.client.chimelong.model.OrderResult;
import com.lvmama.passport.processor.impl.client.chimelong.model.QueryResult;
import com.lvmama.passport.processor.impl.client.chimelong.ticket.ArrayOfEspecialTicket;
import com.lvmama.passport.processor.impl.client.chimelong.util.FaultException;


@WebService(name = "TicketServicePortType", targetNamespace = "http://ws.agent.chimelong.cn")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TicketServicePortType {


    @WebMethod(operationName = "QueryParks", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfPark queryParks()
        throws FaultException
    ;

    @WebMethod(operationName = "BuyTicket", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public OrderResult buyTicket(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "DetainOrder", action = "")
    public void detainOrder(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryOrder", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public QueryResult queryOrder(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "CancelOrder", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public CancelResult cancelOrder(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryAllEspecialTicket", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfEspecialTicket queryAllEspecialTicket()
        throws FaultException
    ;

    @WebMethod(operationName = "QueryParkBaseTicket", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfParkBaseTicket queryParkBaseTicket()
        throws FaultException
    ;

    @WebMethod(operationName = "QueryAllTickets", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfTicket queryAllTickets(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "findMaXiActTime", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfMaXiActTime findMaXiActTime(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryTicketQuantity", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfQueryTicket queryTicketQuantity(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        ArrayOfQueryTicket in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        XMLGregorianCalendar in1)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryAgentInfo", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public String queryAgentInfo(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryParkTicketAvaiableQuantity", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfParkQuantity queryParkTicketAvaiableQuantity(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        XMLGregorianCalendar in0)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryBaseTickets", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfBaseTicket queryBaseTickets()
        throws FaultException
    ;

    @WebMethod(operationName = "BuyETicket", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public OrderResult buyETicket(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "fetchTicket", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public OrderResult fetchTicket(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryMoney", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public BigDecimal queryMoney(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryTickets", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfTicket queryTickets(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryOrderBySSN", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public OrderInfo queryOrderBySSN(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://ws.agent.chimelong.cn")
        String in1,
        @WebParam(name = "in2", targetNamespace = "http://ws.agent.chimelong.cn")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://ws.agent.chimelong.cn")
        String in3)
        throws FaultException
    ;

    @WebMethod(operationName = "QueryBaseCategory", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public ArrayOfBaseCategory queryBaseCategory()
        throws FaultException
    ;

    @WebMethod(operationName = "checkHasMaXi", action = "")
    @WebResult(name = "out", targetNamespace = "http://ws.agent.chimelong.cn")
    public String checkHasMaXi(
        @WebParam(name = "in0", targetNamespace = "http://ws.agent.chimelong.cn")
        String in0)
        throws FaultException
    ;

}
