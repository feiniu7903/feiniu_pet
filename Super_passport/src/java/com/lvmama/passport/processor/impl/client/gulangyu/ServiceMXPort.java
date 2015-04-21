
package com.lvmama.passport.processor.impl.client.gulangyu;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "serviceMXPort", targetNamespace = "urn:serviceMX")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ServiceMXPort {

	@WebMethod(operationName = "Get_ScenicSpot_Info", action = "urn:serviceMX#service#Get_ScenicSpot_Info")
	@WebResult(name = "Get_ScenicSpot_Info", targetNamespace = "urn:serviceMX")
	public String get_ScenicSpot_Info(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw, @WebParam(name = "n", targetNamespace = "urn:serviceMX")
			String n);

	@WebMethod(operationName = "Get_ScenicSpot_List", action = "urn:serviceMX#service#Get_ScenicSpot_List")
	@WebResult(name = "Get_ScenicSpot_List", targetNamespace = "urn:serviceMX")
	public String get_ScenicSpot_List(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw, @WebParam(name = "n", targetNamespace = "urn:serviceMX")
			String n);

	@WebMethod(operationName = "reSend_MSG", action = "urn:serviceMX#service#reSend_MSG")
	@WebResult(name = "reSend_MSG", targetNamespace = "urn:serviceMX")
	public String reSend_MSG(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw,
			@WebParam(name = "ordern", targetNamespace = "urn:serviceMX")
			String ordern);

	@WebMethod(operationName = "Get_Ticket_Order_List", action = "urn:serviceMX#service#Get_Ticket_Order_List")
	@WebResult(name = "Get_Ticket_Order_List", targetNamespace = "urn:serviceMX")
	public String get_Ticket_Order_List(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw,
			@WebParam(name = "lid", targetNamespace = "urn:serviceMX")
			String lid,
			@WebParam(name = "tid", targetNamespace = "urn:serviceMX")
			String tid,
			@WebParam(name = "sid", targetNamespace = "urn:serviceMX")
			String sid,
			@WebParam(name = "stime", targetNamespace = "urn:serviceMX")
			String stime,
			@WebParam(name = "etime", targetNamespace = "urn:serviceMX")
			String etime,
			@WebParam(name = "ordernum", targetNamespace = "urn:serviceMX")
			String ordernum,
			@WebParam(name = "memid", targetNamespace = "urn:serviceMX")
			String memid);

	@WebMethod(operationName = "Get_Special_Ticket_Info", action = "urn:serviceMX#service#Get_Special_Ticket_Info")
	@WebResult(name = "Get_Special_Ticket_Info", targetNamespace = "urn:serviceMX")
	public String get_Special_Ticket_Info(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw, @WebParam(name = "l", targetNamespace = "urn:serviceMX")
			String l, @WebParam(name = "t", targetNamespace = "urn:serviceMX")
			String t);

	@WebMethod(operationName = "Get_Ticket_List", action = "urn:serviceMX#service#Get_Ticket_List")
	@WebResult(name = "Get_Ticket_List", targetNamespace = "urn:serviceMX")
	public String get_Ticket_List(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw, @WebParam(name = "n", targetNamespace = "urn:serviceMX")
			String n, @WebParam(name = "m", targetNamespace = "urn:serviceMX")
			String m);

	@WebMethod(operationName = "Ticket_Order_Submit", action = "urn:serviceMX#service#Ticket_Order_Submit")
	@WebResult(name = "Ticket_Order_Submit", targetNamespace = "urn:serviceMX")
	public String ticket_Order_Submit(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw,
			@WebParam(name = "lid", targetNamespace = "urn:serviceMX")
			String lid,
			@WebParam(name = "tid", targetNamespace = "urn:serviceMX")
			String tid,
			@WebParam(name = "rnum", targetNamespace = "urn:serviceMX")
			String rnum,
			@WebParam(name = "playtime", targetNamespace = "urn:serviceMX")
			String playtime,
			@WebParam(name = "tnum", targetNamespace = "urn:serviceMX")
			String tnum,
			@WebParam(name = "tprice", targetNamespace = "urn:serviceMX")
			String tprice,
			@WebParam(name = "ordername", targetNamespace = "urn:serviceMX")
			String ordername,
			@WebParam(name = "ordertel", targetNamespace = "urn:serviceMX")
			String ordertel,
			@WebParam(name = "ordertime", targetNamespace = "urn:serviceMX")
			String ordertime,
			@WebParam(name = "payinfo", targetNamespace = "urn:serviceMX")
			String payinfo,
			@WebParam(name = "paystatus", targetNamespace = "urn:serviceMX")
			String paystatus,
			@WebParam(name = "totalmoney", targetNamespace = "urn:serviceMX")
			String totalmoney,
			@WebParam(name = "type", targetNamespace = "urn:serviceMX")
			String type,
			@WebParam(name = "linktStr", targetNamespace = "urn:serviceMX")
			String linktStr,
			@WebParam(name = "member", targetNamespace = "urn:serviceMX")
			String member);

	@WebMethod(operationName = "Cancel_Order", action = "urn:serviceMX#service#Cancel_Order")
	@WebResult(name = "Cancel_Order", targetNamespace = "urn:serviceMX")
	public String cancel_Order(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw,
			@WebParam(name = "ordern", targetNamespace = "urn:serviceMX")
			String ordern);

	@WebMethod(operationName = "authrize", action = "urn:serviceMX#service#authrize")
	@WebResult(name = "authrize", targetNamespace = "urn:serviceMX")
	public String authrize(
			@WebParam(name = "ac", targetNamespace = "urn:serviceMX")
			String ac,
			@WebParam(name = "pw", targetNamespace = "urn:serviceMX")
			String pw);

}
