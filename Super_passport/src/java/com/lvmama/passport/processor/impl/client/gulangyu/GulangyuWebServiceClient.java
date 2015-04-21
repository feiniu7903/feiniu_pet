package com.lvmama.passport.processor.impl.client.gulangyu;

import com.lvmama.passport.processor.impl.client.gulangyu.model.OrderSubmitRequest;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 鼓浪屿webservice客户端
 * 
 * @author lipengcheng
 * 
 */
public class GulangyuWebServiceClient{
	
	/**
	 * 取到全部鼓浪屿景区
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAllScenicSpotResult() throws Exception {
		Object[] objs = WebServiceClient.getClientInstance().execute(WebServiceConstant.getProperties("gulangyu.wsdl"), new Object[] { WebServiceConstant.getProperties("gulangyu.user"), WebServiceConstant.getProperties("gulangyu.password"), "" }, "Get_ScenicSpot_List");
		return (String) objs[0];
	}
	
	/**
	 * 取到鼓浪屿的普通门票
	 * @param scenicSpotId
	 * @return
	 * @throws Exception
	 */
	public static String getTicketResult(String scenicSpotId) throws Exception {
		Object[] objs = WebServiceClient.getClientInstance().execute(WebServiceConstant.getProperties("gulangyu.wsdl"), new Object[] { WebServiceConstant.getProperties("gulangyu.user"), WebServiceConstant.getProperties("gulangyu.password"), scenicSpotId ,"" }, "Get_Ticket_List");
		return (String) objs[0];
	}

	/**
	 * 取到鼓浪屿的特殊门票
	 * @param scenicSpotId
	 * @return
	 * @throws Exception
	 */
	public static String getSpecialTicketResult(String scenicSpotId,String uuId) throws Exception {
		Object[] objs = WebServiceClient.getClientInstance().execute(WebServiceConstant.getProperties("gulangyu.wsdl"), new Object[] { WebServiceConstant.getProperties("gulangyu.user"), WebServiceConstant.getProperties("gulangyu.password"), scenicSpotId ,uuId }, "Get_Special_Ticket_Info");
		return (String) objs[0];
	}
	
	/**
	 * 提交订单
	 * @param orderSubmitInfo
	 * @return
	 * @throws Exception
	 */
	public static String getOrderSubmitResult(OrderSubmitRequest orderSubmitInfo) throws Exception{
		Object[] objs = WebServiceClient.getClientInstance().execute(WebServiceConstant.getProperties("gulangyu.wsdl"), new Object[] { 	WebServiceConstant.getProperties("gulangyu.user"),
			WebServiceConstant.getProperties("gulangyu.password"), 
															orderSubmitInfo.getUuLid(),
															orderSubmitInfo.getUuTid(),
															orderSubmitInfo.getOrderId(),
															orderSubmitInfo.getVisitTime(),
															orderSubmitInfo.getTicketNum(),
															orderSubmitInfo.getTicketPrice(),
															orderSubmitInfo.getCustomerName(),
															orderSubmitInfo.getMobile(),
															orderSubmitInfo.getCreateTime(),
															orderSubmitInfo.getPaymentType(),
															orderSubmitInfo.getPaymentStatus(),
															orderSubmitInfo.getTotalAmount(),
															orderSubmitInfo.getUnionType(),
															orderSubmitInfo.getUnionId(),
															orderSubmitInfo.getMemberId()
														},
				"Ticket_Order_Submit");
		return (String)objs[0];
	}
	
	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public static String getOrderCancelResult(String uu16uorder) throws Exception{
		Object[] objs = WebServiceClient.getClientInstance().execute(WebServiceConstant.getProperties("gulangyu.wsdl"), new Object[] { WebServiceConstant.getProperties("gulangyu.user"), WebServiceConstant.getProperties("gulangyu.password"), uu16uorder}, "Cancel_Order");
		return (String) objs[0];
	}
	
	public static String getOrderListResult(String uuLid,String uuTid,String uuBid, String startTime,String endTime,String uuOrdernum,String memberId) throws Exception{
		Object[] objs = WebServiceClient.getClientInstance().execute(WebServiceConstant.getProperties("gulangyu.wsdl"), new Object[]{WebServiceConstant.getProperties("gulangyu.user"), 
			WebServiceConstant.getProperties("gulangyu.password"),
														uuLid,
														uuTid,
														uuBid,
														startTime,
														endTime,
														uuOrdernum,
														memberId
														},
									"Get_Ticket_Order_List");
		return (String)objs[0];
	}
	
	public static String getReSendMsgResult(String uu16uorder) throws Exception{
		Object[] objs = WebServiceClient.getClientInstance().execute(WebServiceConstant.getProperties("gulangyu.wsdl"), new Object[] { WebServiceConstant.getProperties("gulangyu.user"), WebServiceConstant.getProperties("gulangyu.password"), uu16uorder}, "reSend_MSG");
		return (String)objs[0];
	}
}
