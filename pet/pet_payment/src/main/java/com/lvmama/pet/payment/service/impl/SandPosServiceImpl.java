package com.lvmama.pet.payment.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.po.pay.PayPosUser;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.pay.PayPaymentDetailService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pay.PayPosCommercialService;
import com.lvmama.comm.pet.service.pay.PayPosUserService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.service.SandPosService;

public class SandPosServiceImpl implements SandPosService {
	
	private static final Logger log = Logger.getLogger(SandPosServiceImpl.class);
	
	/**
	 * 支付信息接口
	 */
	private PayPaymentService payPaymentService;
	
	/**
	 * 支付扩展信息接口
	 */
	private PayPaymentDetailService payPaymentDetailService;
	
	/**
	 * POS机商户接口
	 */
	private PayPosCommercialService payPosCommercialService;
	/**
	 * POS机用户接口
	 */
	private PayPosUserService payPosUserService;
	
	/**
	 * JMS消息接口
	 */
	protected TopicMessageProducer resourceMessageProducer;
	
	/**
	 * super后台用户接口
	 */
	private PermUserService permUserService;
	
	/**
	 * 订单联系人接口
	 */
	private OrderService orderServiceProxy;
	
	
	
	/**
	 * POS机的用户的登录验证.
	 * @param message
	 * @return
	 */
	@Override
	public String posUserLogin(Map<String, String> posMap) {
		String commercialNo = posMap.get("requester");// 商户号
		String terminalNo = posMap.get("terminal_id");//终端号
		String empNo = posMap.get("delivery_man");//员工号
		String empPasswd = posMap.get("password");//员工登录密码
		String returnMessage = payPosCommercialService.isSandLogin(commercialNo, terminalNo, empNo, empPasswd);
		
		log.info("returnMessage="+returnMessage+",code="+Constant.SAND_PAY_POS_STATUS.getCode(returnMessage)+",msg="+Constant.SAND_PAY_POS_STATUS.getCnName(returnMessage));
		String returnBodyXml = "<Transaction_Body>"+
				"<user_state>"+Constant.SAND_PAY_POS_STATUS.getCode(returnMessage)+"</user_state>"+
				"<function>10000000</function>"+
				"<info></info>"+
				"<check_value>"+posMap.get("check_value")+"</check_value>"+
				"</Transaction_Body>";
		return sortReturnMessage(posMap, returnBodyXml, returnMessage);
		
	}
	
	
	/**
	 * POS机订单查询.
	 * @param message
	 * @return
	 */
	@Override
	public String queryOrderAmountByOrderId(Map<String, String> posMap) {
		String commercialNo = posMap.get("requester");// 商户号
		String terminalNo = posMap.get("terminal_id");//终端号
		String paymentTradeNo = posMap.get("order_no");// 订单号
		String deliveryMan=posMap.get("delivery_man");//POS机员工号
		String isSuccess = payPosCommercialService.isPosSuccess(commercialNo, terminalNo);
		Long payAmount = 0L;
		Long orderId=0L;
		if(Constant.SAND_PAY_POS_STATUS.SUCCESS.name().equals(isSuccess) && StringUtils.isNotEmpty(paymentTradeNo)){
			List<PayPayment> paymentList = payPaymentService.selectByPaymentTradeNo(paymentTradeNo);
			for (PayPayment payment : paymentList) {
				if (payment != null
						&& !payment.isSuccess()
						&& (Constant.PAYMENT_GATEWAY.SAND_POS.name().equals(payment.getPaymentGateway()) || Constant.PAYMENT_GATEWAY.SAND_POS_CASH.name()
								.equals(payment.getPaymentGateway()))) {
					payAmount += payment.getAmount();
					orderId=payment.getObjectId();
				}
			}
		}
		
		String value[]=getOrderContactPersonByOrderId(orderId);
		String dealInfo=getRealNameByEmpNo(deliveryMan);
		String returnBodyXml = "<Transaction_Body>"+
				"<ID>"+value[0]+"</ID>"+
				"<amount>"+PriceUtil.trans2YuanStr(payAmount)+"</amount>"+
				"<comm_info>"+value[1]+"</comm_info>"+
				"<deal_info>"+dealInfo+"</deal_info>"+
				"<check_value>"+posMap.get("check_value")+"</check_value>"+
				"</Transaction_Body>";
		return sortReturnMessage(posMap,returnBodyXml,isSuccess);
	}
	
	/**
	 * 通过订单号获取下单时订单产品ID及下单联系人名称
	 * @author ZHANG Nan
	 * @param orderId 订单号
	 * @return [0]=产品ID [1]下单联系人
	 */
	private String[] getOrderContactPersonByOrderId(Long orderId){
		String value[]=new String[2];
		OrdOrder ordOrder=orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(ordOrder!=null){
			OrdOrderItemProd ordOrderItemProd=ordOrder.getMainProduct();
			if(ordOrderItemProd!=null){
				value[0]=ordOrderItemProd.getProductId()+"";
			}
			OrdPerson ordPerson=ordOrder.getContact();
			if(ordPerson!=null){
				value[1]=ordPerson.getName();
			}
		}
		return value;
	}
	/**
	 * 获取POS机员工对应super系统登录用户的真实名
	 * @author ZHANG Nan
	 * @param empNo POS机员工号
	 * @return super系统登录用户的真实名
	 */
	private String getRealNameByEmpNo(String empNo){
		PayPosUser payPosUser=payPosUserService.selectByEmpNo(empNo);
		if(payPosUser!=null){
			PermUser permUser=permUserService.getPermUserByUserName(payPosUser.getEmpName());
			if(permUser!=null){
				return permUser.getRealName();
			}
		}
		return "";
	}
	/**
	 * POS机支付通知.
	 * @param message
	 * @return
	 */
	@Override
	public String orderPayNotice(Map<String, String> posMap) {
		String isSuccess = Constant.SAND_PAY_POS_STATUS.PAYED_FAILED.name();
		
		
		String requestTime = posMap.get("request_time");
		//String commercialNo = posMap.get("mid");// 商户号
		String terminalNo = posMap.get("tid");//终端号
		String deliveryMan=posMap.get("delivery_man");;//POS机员工号
		String paymentTradeNo = posMap.get("order_no");//支付 订单号
		String pay_type = posMap.get("pay_type");//支付方式（01，银联卡，02，杉德卡，03，现金，04，斯马特卡，05， 其他）
		String transType=posMap.get("trans_type");//交易类型（01消费，02撤销，03退货，04预授权，05预授权完成，06预授权撤销，07预授权完成撤销）
		String cardacc_s = posMap.get("cardacc_s");//交易卡号
		String pos_serial = posMap.get("pos_serial");//支付凭证号
		String pos_setbat=posMap.get("pos_setbat");//交易批次号
		String amount = posMap.get("amount");//订单支付金额
		Long payAmount = 0L;
        if(StringUtils.isNotEmpty(amount)){
        	payAmount = PriceUtil.convertToFen(amount);
        }
		if(StringUtils.isNotEmpty(paymentTradeNo) && payAmount > 0){
			List<PayPayment> paymentList = payPaymentService.selectByPaymentTradeNoAndGateways(paymentTradeNo,"'"+Constant.PAYMENT_GATEWAY.SAND_POS.name()+"','"+Constant.PAYMENT_GATEWAY.SAND_POS_CASH.name()+"'");
			for (PayPayment payment : paymentList) {
				if (payment != null && "01".equals(transType) && payAmount > 0) {
					
					if("03".equalsIgnoreCase(pay_type)){
						//如果使用杉德POS机为现金支付,但网关为杉德POS机支付则交易失败
						if(Constant.PAYMENT_GATEWAY.SAND_POS.name().equals(payment.getPaymentGateway())){
							String returnBodyXml = "<Transaction_Body><pay_msg>"+Constant.SAND_PAY_POS_STATUS.getCnName(isSuccess)+"</pay_msg><check_value>"+posMap.get("check_value")+"</check_value></Transaction_Body>";
							return sortReturnMessage(posMap,returnBodyXml,isSuccess);	
						}
					}
					else{
						//如果使用杉德POS机支付,但网关为杉德POS机现金支付则交易失败
						if(Constant.PAYMENT_GATEWAY.SAND_POS_CASH.name().equals(payment.getPaymentGateway())){
							String returnBodyXml = "<Transaction_Body><pay_msg>"+Constant.SAND_PAY_POS_STATUS.getCnName(isSuccess)+"</pay_msg><check_value>"+posMap.get("check_value")+"</check_value></Transaction_Body>";
							return sortReturnMessage(posMap,returnBodyXml,isSuccess);	
						}
					}
					
					payment.setCallbackTime(DateUtil.stringToDate(requestTime,"yyyyMMddHHmmss"));
		        	payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
		        	payment.setCallbackInfo("支付成功");
		        	payment.setGatewayTradeNo(pos_serial);
					payment.setRefundSerial(pos_setbat);
					
		        	if(payAmount-payment.getAmount() >= 0 ){
						payment.setAmount(payment.getAmount());
						payAmount=payAmount-payment.getAmount();
						boolean isKey = payPaymentService.updatePayment(payment);
						if(isKey){
							isSuccess = Constant.SAND_PAY_POS_STATUS.SUCCESS.name();
							//如果支付信息保存成功并且是POS机支付则记录扩展支付信息
							savePaymentDetail(payment, deliveryMan,terminalNo,cardacc_s);
						}
					}
				}
			}
			String key = "PAYMENT_CALL_BACK_BASE_ACTION_"+ paymentTradeNo;
			try {
				if (!SynchronizedLock.isOnDoingMemCached(key) && Constant.SAND_PAY_POS_STATUS.SUCCESS.name().equals(isSuccess) && "01".equals(transType)) {
					for (PayPayment payment : paymentList) {
						if(payment!=null && Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equals(payment.getStatus()) && !payment.isNotified()){
							resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(payment.getPaymentId()));	
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				SynchronizedLock.releaseMemCached(key);
			}
		}
		String returnBodyXml = "<Transaction_Body><pay_msg>"+Constant.SAND_PAY_POS_STATUS.getCnName(isSuccess)+"</pay_msg><check_value>"+posMap.get("check_value")+"</check_value></Transaction_Body>";
		return sortReturnMessage(posMap,returnBodyXml,isSuccess);
		
	}
	/**
	 * 记录POS的扩展支付信息
	 * @author ZHANG Nan
	 * @param payPayment 支付对象
	 * @param posEmpNo POS机员工号
	 * @param terminalNo POS机终端号
	 * @param cardacc_s 交易卡号
	 */
	private void savePaymentDetail(PayPayment payPayment,String posEmpNo,String terminalNo,String cardacc_s){
		PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPaymentId(String.valueOf(payPayment.getPaymentId()));
		if(payPaymentDetail==null){
			payPaymentDetail=new PayPaymentDetail();
			payPaymentDetail.setPaymentId(payPayment.getPaymentId());
			payPaymentDetail.setPosTerminalNo(terminalNo);
			payPaymentDetail.setPaymentBankCardNo(cardacc_s);
			if(StringUtils.isNotBlank(posEmpNo)){
				PayPosUser payPosUser=payPosUserService.selectByEmpNo(posEmpNo);
				if(payPosUser!=null){
					payPaymentDetail.setReceivingPerson(payPosUser.getEmpName());
				}
			}
			if(Constant.PAYMENT_GATEWAY.SAND_POS_CASH.name().equals(payPayment.getPaymentGateway())){
				payPaymentDetail.setCashAuditStatus(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.UNLIBERATED.name());
			}
			payPaymentDetailService.savePayPaymentDetail(payPaymentDetail);
		}
	}
	/**
	 * 
	 * @param posMap
	 * @param returnBodyXml
	 * @param resp_code
	 * @param resp_msg
	 * @return
	 */
	private String sortReturnMessage(Map<String, String> posMap,String returnBodyXml,String respName){
		String sendMess = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<Transaction>"+
						"<Transaction_Header>"+
						"<transaction_id>"+posMap.get("transaction_id")+"</transaction_id>"+
						"<requester>"+posMap.get("requester")+"</requester> "+
						"<target>"+posMap.get("target")+"</target> "+
						"<request_time>"+posMap.get("request_time")+"</request_time> "+
						"<system_serial>"+posMap.get("system_serial")+"</system_serial>"+
						"<resp_code>"+Constant.SAND_PAY_POS_STATUS.getCode(respName)+"</resp_code>"+
						"<resp_msg>"+Constant.SAND_PAY_POS_STATUS.getCnName(respName)+"</resp_msg>"+
						"<ext_attributes>"+
							"<delivery_man>"+posMap.get("delivery_man")+"</delivery_man>"+
							"<settle_account>"+posMap.get("settle_account")+"</settle_account>"+
						"</ext_attributes>"+
						"</Transaction_Header>"+returnBodyXml+"</Transaction>";
		return sendMess;
	}
	
	
	
	@Override
	public void setPayPosCommercialService(
			PayPosCommercialService payPosCommercialService) {
		this.payPosCommercialService = payPosCommercialService;
	}


	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}


	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}


	public PayPaymentDetailService getPayPaymentDetailService() {
		return payPaymentDetailService;
	}


	public void setPayPaymentDetailService(PayPaymentDetailService payPaymentDetailService) {
		this.payPaymentDetailService = payPaymentDetailService;
	}


	public PayPosUserService getPayPosUserService() {
		return payPosUserService;
	}


	public void setPayPosUserService(PayPosUserService payPosUserService) {
		this.payPosUserService = payPosUserService;
	}


	public PermUserService getPermUserService() {
		return permUserService;
	}


	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}


	public PayPosCommercialService getPayPosCommercialService() {
		return payPosCommercialService;
	}


	public TopicMessageProducer getResourceMessageProducer() {
		return resourceMessageProducer;
	}
}
