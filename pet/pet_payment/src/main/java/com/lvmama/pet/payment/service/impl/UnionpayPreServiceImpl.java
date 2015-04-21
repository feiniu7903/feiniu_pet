package com.lvmama.pet.payment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.po.pay.PaymentToBankInfo;
import com.lvmama.comm.pet.service.pay.BankPaymentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.UnionUtil;
import com.lvmama.pet.vo.PaymentErrorData;
  

/**
 * 此Service做订单资源审核通过后 通知银联进行预授权完成
 * @author Alex Wang
 *
 */
public class UnionpayPreServiceImpl implements BankPaymentService{
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(UnionpayPreServiceImpl.class);
	/**
	 * 版本号.
	 */
	private  String version = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_VERSION");

	/**
	 * 字符集.
	 */
	private static String charset = "UTF-8";
	/**
	 * 商户号.
	 */
	private  String merId = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_MERID");
	/**
	 * 交易币种.
	 */
	private  String orderCurrency = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_CURRENCY");
	/**
	 * 商户密钥.
	 */
	private  String key = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_KEY");
  
	/**
	 * 后台交易.
	 */
	private String backStagegateWay = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_BACK_URL");
	
	private PayPaymentService payPaymentService;
	
	/**
	 * 资源审核通过-进行预授权完成
	 * @param message
	 */
	public void prePayComplete(Long objectId, String bizType) {

	}
	
	/**
	 * 支付的处理.
	 * @param info
	 * @return
	 */
	@Override
	public BankReturnInfo pay(PaymentToBankInfo info) {
		 
		List<PayPayment> list = payPaymentService.createPrePaymentCompleteData(info.getObjectId(), info.getBizType());
		LOG.info("create list size()="+list.size());
		for (PayPayment payment : list) {
			if (payment.isPrePayment()) {
				PayPrePayment prePayment = payment.getPayPrePayment();
				if (prePayment.isPrePaySuccess()) {
					LOG.info("prePayment, paymentId: "+prePayment.getPaymentId());
					if (payment.isUnionPayPre()) {
						String newPaymentTradeNo=SerialUtil.generate24ByteSerialAttaObjectId(info.getObjectId());
						info.setPaySerial(newPaymentTradeNo);
						info.setCustomerIp("127.0.0.1");
						info.setPayAmount(payment.getAmount());
						info.setPreRefundType(Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name());
						info.setPaymentGateway(payment.getPaymentGateway());
						info.setCreateTime(new Date());
						info.setGatewayTradeNo(payment.getGatewayTradeNo());
						
						LOG.info("newPaymentTradeNo:"+newPaymentTradeNo);
						payment.setPaymentTradeNo(newPaymentTradeNo);
						//对于预授权扣款(完成)来说 新发起的扣款请求需要将状态设置为新建，待异步回调后返回扣款是否成功结果
						payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
						payPaymentService.updatePayment(payment);
						
						Map<String,String> resultMap =  doPayPreXml(info);
						
						if (Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(resultMap.get("successFlag")) && prePayment.isPrePaySuccess()) {
							//预授权完成(扣款)后 会重新生成新的对账流水号和网关交易号,故需要将老的流水号更新成新的
							LOG.info("newPaymentTradeNo:"+newPaymentTradeNo);
							LOG.info("newGatewayTradeNo:"+resultMap.get("gatewayTradeNo"));
							LOG.info("newCallbackTime:"+resultMap.get("respTime"));
							
							payment.setPaymentTradeNo(newPaymentTradeNo);
							payment.setGatewayTradeNo(resultMap.get("gatewayTradeNo"));
							payment.setCallbackTime(DateUtil.toDate(resultMap.get("respTime"), "yyyyMMddHHmmss"));
							payment.setStatus(null);
							payment.setCallbackInfo("发起预授权完成(扣款)动作完毕,等待银联回应!");
							payPaymentService.updatePayment(payment);
							
							prePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name());
							prePayment.setCompleteTime(new Date());
							payPaymentService.updatePayPrePayment(prePayment);
							LOG.info("post complete request success");	
						}
					}
				}
			}
		}
		return  new BankReturnInfo();  
	}

	/**
	 * 在线预授权.
	 * @param info
	 * @return
	 */
	private Map<String,String> doPayPreXml(PaymentToBankInfo info){
		String[] valueVo = new String[]{
				"",//acqCode   c
				PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_BACKENDURL"),//backEndUrl   m
				charset,//charset  m
				"",//commodityDiscount  o
				"",//commodityName    o
				"",//commodityQuantity   o
				"",//commodityUnitPrice   o
				"",//commodityUrl       o
				"127.0.0.1",//customerIp   m
				"",//customerName    o
				"",//defaultBankNumber    o
				"",//defaultPayType   o
				PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_FRONTENDURL"),//frontEndUrl    m
				PaymentConstant.MERABBR,//merAbbr   m
				"",//merCode    c
				merId,//merId     m
				"",//merReserved   o
				info.getPayAmount().toString(),//orderAmount   m
				orderCurrency,//orderCurrency   m
				info.getPaySerial(),//orderNumber  m
				DateUtil.getFormatDate(info.getCreateTime(), "yyyyMMddHHmmss"),//orderTime   m
				info.getGatewayTradeNo(),//origQid   c
				"",//transTimeout  o
				PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_TRANSTYPE_PAYSUCC"),//transType   m
				"",//transferFee   0
				version//version  m
		};
		Map<String,String> returnMap=new HashMap<String,String>();
		
		String res = UnionUtil.getUnionPaySyncRes(key, backStagegateWay, valueVo);
		LOG.info("response:" + res );
		
		if (res != null && !"".equals(res)) {
			String[] arr = UnionUtil.getResArr(res);
			if(UnionUtil.checkSecurity(key, arr)){//验证签名
				String result=UnionUtil.getRespCode(arr);//商户业务逻辑
				returnMap.put("code", result);
				returnMap.put("codeInfo", PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name(), result));
				//这里返回00只代表发起预授权扣款请求成功,不代表扣款成功
				if("00".equals(result)){
					returnMap.put("successFlag", Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
					//预授权完成(扣款)后新返回的网关交易号
					returnMap.put("gatewayTradeNo", arr[9].split("=")[1]);
					
					//预授权完成(扣款)后新返回的请求时间
					returnMap.put("respTime", arr[12].split("=")[1]);
					
				}
				else{
					returnMap.put("successFlag", Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				}
			}
		}
		return returnMap;
   }

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
}


