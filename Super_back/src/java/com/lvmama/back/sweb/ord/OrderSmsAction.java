package com.lvmama.back.sweb.ord;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

@ParentPackage("json-default")
@Results( {
		@Result(name = "ord_sms", location = "/WEB-INF/pages/back/ord/sms/ord_sms.jsp"),
		@Result(name = "ord_list_single", location = "/ord/order_monitor_list.do?pageType=single", type = "redirect"),
		@Result(name = "ord_list", location = "/ord/order_monitor_list.do", type = "redirect")})
/**
 * 订单发送短信操作类
 * 
 * @author huangl
 */
@SuppressWarnings("unchecked")
public class OrderSmsAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 发送手机号码.
	 */
	private String mobileNumber;
	/**
	 * 短信补充内容.
	 */
	private String ordContent;
	
	private String returnUrl="ord_list";
	
	/**
	 * 状态查询.
	 */
	private List orderApproveList;
	/**
	 *  群发选中的订单值.
	 */
	private String checkValue;
	/**
	 * 短信发送服务器.
	 */
	private TopicMessageProducer orderMessageProducer;
	/**
	 * 订单编号.
	 */
	private Long ordOrderId;
	/**
	 * 补发短信.
	 */
	private SmsService smsService;
	/**
	 * 短信发送类型.
	 */
	private String smsType;
	
	private OrdOrder orderDetail;
	private ComLogService comLogService;
	/**
	 * 执行发送方法
	 */
	@Action("/ordSms/sendOrdOrderSms")
	public String sendOrdOrderSms() {
		String redio_type=this.getRequest().getParameter("redio_type");
		//第一:用户单条发送短信凭证.
		if("OneSms".equals(smsType)){
			StringBuffer sb=new StringBuffer("重发短信：");
			sb.append("接收手机号:");
			sb.append(mobileNumber);
			sb.append(",类型");
			if("new".equals(redio_type)){				
				this.sendOrdOrderMsg(ordOrderId,this.mobileNumber);
				sb.append("发送短信凭证新内容");
			}else{
				//OrdOrder ordOrder=this.orderServiceProxy.queryOrdOrderByOrderId(ordOrderId);
				this.sendComSms(ordOrderId,mobileNumber);
				sb.append("补充条款,内容：ordContent");
			}
			
			comLogService.insert("ORD_ORDER", null, ordOrderId,
					getOperatorName(), "SEND_SMS", "发送短信凭证", sb.toString(),
					null);
		}
		//用户群发发送短信凭证.去掉群发功能
//		if("ManySms".equals(smsType)){
//			String []checkBoxName =checkValue.split(",");
//			if("new".equals(redio_type)){
//				for (int i = 0; i < checkBoxName.length; i++) {
//					if(checkBoxName[i]!=null){
//						OrdOrder ordOrder=this.orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(checkBoxName[i]));
//						this.sendOrdOrderMsg(Long.parseLong(checkBoxName[i]),ordOrder.getContact()!=null?ordOrder.getContact().getMobile():"");
//					}
//				}
//			}else{
//				for (int i = 0; i < checkBoxName.length; i++) {
//					if(checkBoxName[i]!=null){
//						OrdOrder ordOrder=this.orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(checkBoxName[i]));
//						this.sendComSms(Long.parseLong(checkBoxName[i]),ordOrder.getContact()!=null?ordOrder.getContact().getMobile():"");
//					}
//				}
//			}
//		}
		return returnUrl;
	}
	
	public void sendOrdOrderMsg(Long orderId,String mobileNumber){
		orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(orderId,mobileNumber));
	}
	
	public void sendComSms(Long orderId,String phone){
		ComSms sms = new ComSms();
		sms.setMobile(phone);
		sms.setContent(ordContent);
		sms.setObjectId(orderId);
		sms.setObjectType(Constant.SMS_TEMPLATE.REISSUE_SMS.name());
		sms.setMms("false");
		smsService.sendSms(sms);
	}
	
	/**
	 * 进入页面初始化时执行.
	 */
	@Action("/ordSms/jumpSendOrdOrderSms")
	public String jumpSendOrdOrderSms() {
		orderDetail=orderServiceProxy.queryOrdOrderByOrderId(ordOrderId);		
		return "ord_sms";
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOrdContent() {
		return ordContent;
	}

	public void setOrdContent(String ordContent) {
		this.ordContent = ordContent;
	}

	public List getOrderApproveList() {
		return orderApproveList;
	}

	public Long getOrdOrderId() {
		return ordOrderId;
	}

	public void setOrdOrderId(Long ordOrderId) {
		this.ordOrderId = ordOrderId;
	}

	public void setOrderApproveList(List orderApproveList) {
		this.orderApproveList = orderApproveList;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public OrdOrder getOrderDetail() {
		return orderDetail;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

}
