package com.lvmama.front.web.ajax;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.vo.Constant;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 针对订单操作的Ajax类
 * @author Ganyingwen
 *
 */
public class AjaxOrderAction extends ActionSupport {
	private static final long serialVersionUID = 1545454454545445L;
	
	private Long orderId; 
	private OrderService orderServiceProxy;
	private OrdOrderSHHolidayService ordOrderSHHolidayService;
	private transient TopicMessageProducer orderMessageProducer;
	/**
	 * 合并支付订单号(逗号隔开)
	 */
	private String orderIds;
	/**
	 * 订单是否已成功付款
	 */
	@Action("/ajax/isOrderSuccessPay")	
	public void isSuccessPayOrder()  throws Exception{
		
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		
		if(null != order){
			if(order.isPaymentSucc()){
				printRtn(new AjaxRtnBean(true,"订单已支付"));
//			}else if(order.isPrePaymentSucc()){
//				printRtn(new AjaxRtnBean(true,"订单预授权成功"));
			}else{
				printRtn(new AjaxRtnBean(false,"订单没支付"));
			}
			return;
		}
		
		printRtn(new AjaxRtnBean(false,"订单没支付"));
	}
	/**
	 * 合并支付订单是否已成功付款
	 */
	@Action("/ajax/isOrderSuccessMergePay")	
	public void isSuccessMergePayOrder()  throws Exception{
		if(StringUtils.isBlank(orderIds)){
			printRtn(new AjaxRtnBean(false,"订单未支付"));
			return ;
		}
		
		String [] orderIdArray=orderIds.split(",");
		for (String tempOrderId : orderIdArray) {
			Long orderId=null;
			try {
				orderId=Long.valueOf(tempOrderId);
			} catch (Exception e) {
				e.printStackTrace();
				printRtn(new AjaxRtnBean(false,"订单未支付"));
				return ;
			}
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(order == null || !order.isPaymentSucc()){
				printRtn(new AjaxRtnBean(false,"订单没支付"));
				return;
			}
		}
		
		printRtn(new AjaxRtnBean(true,"订单已支付"));
		return ;	
	}
	/**
	 * 重新发送电子合同
	 * @throws Exception
	 */
	@Action("/ajax/sendOrderEContract")
	public void sendEContract() throws Exception {
		if (null == orderId) {
			printRtn(new AjaxRtnBean(false,"订单号为空"));
			return;
		}
		
		orderMessageProducer.sendMsg(MessageFactory.newOrderSendEContract(orderId));	
		printRtn(new AjaxRtnBean(true, "合同已发送"));

	}
	
	/**
	 * 渠道下单第三方验证结果
	 */
	@Action("/ajax/supplierBookingResult")	
	public void supplierBookingResult()  throws Exception{
		if (null == orderId) {
			printRtn(new AjaxRtnBean(false,"cancel"));
			return;
		}
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(null != order){
			if(StringUtils.equalsIgnoreCase(order.getOrderStatus(), Constant.ORDER_STATUS.NORMAL.getCode())&&
					StringUtils.equalsIgnoreCase(order.getApproveStatus(), Constant.ORDER_APPROVE_STATUS.VERIFIED.getCode())	){
				printRtn(new AjaxRtnBean(true,""));
				return;
			}else if(StringUtils.equalsIgnoreCase(order.getOrderStatus(), Constant.ORDER_STATUS.CANCEL.getCode())){
				printRtn(new AjaxRtnBean(false,"您的订单审核失败,请重新下单或看下其他出发日期或产品!"));
				return;
			}else if(StringUtils.equalsIgnoreCase(order.getOrderStatus(), Constant.ORDER_STATUS.NORMAL.getCode())){
				OrdOrderSHHoliday sh = ordOrderSHHolidayService.selectByObjectIdAndObjectType(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_APPROVE.getCode(),"TRUE"));
				if(sh!=null && "true".equalsIgnoreCase(sh.getContent())){
					printRtn(new AjaxRtnBean(false,"您的订单还未确认，我们将尽快确认资源，以手机短信的形式通知到您，请耐心等待。"));
					return;
				}
			}
		}
		printRtn(new AjaxRtnBean(false,"您的订单正在提交确认中，需要大约1分钟时间,订单确认后页面会自动跳转至支付页面"));
	}
	
	/**
	 * 输出返回码
	 * @param bean
	 * @throws IOException
	 */
	private void printRtn(final AjaxRtnBean bean) throws IOException {
		ServletActionContext.getResponse().setContentType("text/json; charset=gb2312");
		if (ServletActionContext.getRequest().getParameter("jsoncallback") == null) {
			ServletActionContext.getResponse().getWriter().print(JSONObject.fromObject(bean));
		} else {
			ServletActionContext.getResponse().getWriter().print(ServletActionContext.getRequest().getParameter("jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
		}
	}	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	public void setOrdOrderSHHolidayService(
			OrdOrderSHHolidayService ordOrderSHHolidayService) {
		this.ordOrderSHHolidayService = ordOrderSHHolidayService;
	}
	
}
