/**
 * 
 */
package com.lvmama.back.sweb.offlinepay;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;


/**
 * vst系统订单付款操作，当前操作目前只做测试使用
 * @author lancey
 *
 */
@Results({@Result(name="vstPayIndex",location="/WEB-INF/pages/back/offlinePay/vstPayIndex.jsp")})
public class VstOrderOfflinepayAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1601000840675211662L;

	@Action("/offlinePay/vstPayIndex")
	public String index(){
		return "vstPayIndex";
	}
	
	private Long orderId;
	private Long amount;//分
	private VstOrdOrderService vstOrdOrderService;
	private PayPaymentService payPaymentService;
	private TopicMessageProducer resourceMessageProducer; 
	
	@Action("/offlinePay/vstPaySubmit")
	public void submit(){
		JSONResult result = new JSONResult();
		try{
			String str=Constant.getInstance().getValue("vst_offline_pay_able");
			if(!StringUtils.equals("true", str)){
				throw new IllegalArgumentException("系统不支持线下支付");
			}
			Assert.notNull(orderId);
			Assert.notNull(amount);
			VstOrdOrderVo order = vstOrdOrderService.getVstOrdOrderVo(orderId);
			if(order==null){
				throw new IllegalArgumentException("订单不存在");
			}
			if(!"PREPAID".equalsIgnoreCase(order.getPaymentTarget())){
				throw new IllegalArgumentException("当前订单不是在线支付订单");
			}
			long paymentAmount=amount;
			if(amount==-1000){
				paymentAmount = order.getOughtAmount()-order.getActualAmount();
			}else if(amount<0||order.getActualAmount()+amount>order.getOughtAmount()){
				throw new IllegalArgumentException("支付金额大于订单金额");
			}
			PayPayment payPayment = new PayPayment();
			payPayment.setObjectId(order.getOrderId());
			payPayment.setSerial(payPayment.geneSerialNo());
			String key = "PAYMENT_VST_ACTION" + payPayment.getSerial();
			if (SynchronizedLock.isOnDoingMemCached(key)) {
				throw new Exception("重复操作同一个订单");
			}
			try {
				Date clllbackTime = new Date();
				
				payPayment.setCallbackInfo("VST测试支付");
				payPayment.setGatewayTradeNo(DateUtil.formatDate(clllbackTime, "yyyyMMddHHmmssSSS")+order.getOrderId());
				payPayment.setPaymentTradeNo(payPayment.getGatewayTradeNo());
				payPayment.setCallbackTime(clllbackTime);
				payPayment.setCreateTime(clllbackTime);
				payPayment.setPaymentGateway("VST_PAYMENT_CHANNEL");
				payPayment.setAmount(paymentAmount);
				payPayment.setOperator(getOperatorNameAndCheck());
				payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.getCode());
				payPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
				payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
				payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
				
	
				Long paymentId = payPaymentService.savePayment(payPayment);
				resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
			} finally {
				SynchronizedLock.releaseMemCached(key);
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
