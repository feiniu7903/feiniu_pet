package com.lvmama.pet.payment.callback.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.UnionpayPreCallbackData;
import com.lvmama.pet.utils.UnionUtil;



/**
 * 银联预授权支付回调.
 * 
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期
 * @since Super一期
 */
@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/unionpay_callback_async.ftl", type = "freemarker")
	})
public class UnionpayPreCallBackAction extends CallbackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7402387372007977509L;
	
	/**
	 * 商户密钥.
	 */
	private  String key = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_KEY");
	
	private UnionpayPreCallbackData unionpayPreCallbackData;
	
	/**
	 * 交通银行支付回调.
	 * 
	 * @return /WEB-INF/pages/pay/order_pay_detail.ftl
	 */
	@Action("/pay/chinaPayPreCallback")
	public String execute() {
		String result = "order_success";
		Map<String, String> pureParaPair=getPureParaPair();
		log.info("UnionpayPreCallBackAction.execute() pureParaPair="+pureParaPair);
		//是不是预授权成功
		if (PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_TRANSTYPE_PAY").equalsIgnoreCase(pureParaPair.get("transType"))) {
			result = callback(true);
		//是不是预授权完成
		}else if (PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_TRANSTYPE_PAYSUCC").equalsIgnoreCase(pureParaPair.get("transType"))){
			List<PayPayment> paymentList=getPayPaymentService().selectByPaymentTradeNo(pureParaPair.get("orderNumber"));
			for (PayPayment payPayment : paymentList) {
				PayPrePayment prePayment=getPayPaymentService().selectPrePaymentByPaymentId(payPayment.getPaymentId());
				//预授权完成(扣款)后 会重新生成新的对账流水号和网关交易号,故需要将老的流水号更新成新的
				LOG.info("newPaymentTradeNo:"+pureParaPair.get("orderNumber"));
				LOG.info("newGatewayTradeNo:"+pureParaPair.get("qid"));
				LOG.info("newCallbackTime:"+pureParaPair.get("respTime"));
				
				payPayment.setPaymentTradeNo(pureParaPair.get("orderNumber"));
				payPayment.setGatewayTradeNo(pureParaPair.get("qid"));
				payPayment.setCallbackTime(DateUtil.toDate(pureParaPair.get("respTime"), "yyyyMMddHHmmss"));
				payPayment.setCallbackInfo(pureParaPair.get("respMsg"));
				
				if (!"00".equals(pureParaPair.get("respCode"))) {
					payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
					
				}
				else if(!checkSecurity(pureParaPair)){
					payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
					payPayment.setCallbackInfo("验签错误");
				}
				else{
					payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
					
				}
				getPayPaymentService().updatePayment(payPayment);
				
				prePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name());
				prePayment.setCompleteTime(new Date());
				getPayPaymentService().updatePayPrePayment(prePayment);
				
			}
			result = "order_success";
		}
		return result;
	}

	@Action("/pay/chinaPayPreCallback/asyn")
	public String asyn(){
		String result = "order_success";
		Map<String, String> pureParaPair=getPureParaPair();
		log.info("UnionpayPreCallBackAction.asyn() pureParaPair="+pureParaPair);
		//是不是预授权成功
		if (PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_TRANSTYPE_PAY").equalsIgnoreCase(pureParaPair.get("transType"))) {
			result = callback(false);
		//是不是预授权完成
		}else if (PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_TRANSTYPE_PAYSUCC").equalsIgnoreCase(pureParaPair.get("transType"))){	
			List<PayPayment> paymentList=getPayPaymentService().selectByPaymentTradeNo(pureParaPair.get("orderNumber"));
			for (PayPayment payPayment : paymentList) {
				PayPrePayment prePayment=getPayPaymentService().selectPrePaymentByPaymentId(payPayment.getPaymentId());
				//预授权完成(扣款)后 会重新生成新的对账流水号和网关交易号,故需要将老的流水号更新成新的
				LOG.info("newPaymentTradeNo:"+pureParaPair.get("orderNumber"));
				LOG.info("newGatewayTradeNo:"+pureParaPair.get("qid"));
				LOG.info("newCallbackTime:"+pureParaPair.get("respTime"));
				
				payPayment.setPaymentTradeNo(pureParaPair.get("orderNumber"));
				payPayment.setGatewayTradeNo(pureParaPair.get("qid"));
				payPayment.setCallbackTime(DateUtil.toDate(pureParaPair.get("respTime"), "yyyyMMddHHmmss"));
				payPayment.setCallbackInfo(pureParaPair.get("respMsg"));
				
				if (!"00".equals(pureParaPair.get("respCode"))) {
					payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
					
				}
				else if(!checkSecurity(pureParaPair)){
					payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
					payPayment.setCallbackInfo("验签错误");
				}
				else{
					payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
					
				}
				
				getPayPaymentService().updatePayment(payPayment);
				
				prePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name());
				prePayment.setCompleteTime(new Date());
				getPayPaymentService().updatePayPrePayment(prePayment);
				
			}
			result = "asyn";
		}
		return result;
	}
	@Override
	CallbackData getCallbackData() {
		unionpayPreCallbackData=new UnionpayPreCallbackData(getPureParaPair());
		return unionpayPreCallbackData;
	}
	/**
	 * 银联预授权完成回调验签
	 * @author ZHANG Nan
	 * @param pureParaPair
	 * @return
	 */
	private boolean checkSecurity(Map<String, String> pureParaPair){
		Set<Entry<String, String>> set=pureParaPair.entrySet();
		String value[]=new String[pureParaPair.size()];
		int i=0;
		for (Entry<String, String> entry : set) {
			value[i]=entry.getKey()+"="+entry.getValue();
			i++;
		}
		return UnionUtil.checkSecurity(key, value);
	}

}
