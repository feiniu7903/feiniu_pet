package com.lvmama.pet.payment.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PaymentQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.merPlus.OrdersBean;
import com.merPlus.PlusTools;
/**
 * 银联手机支付订单查询(针对 消费 类型为01的)
 * @author zhangyong
 *
 */
public class UpompQueryServiceImpl implements PaymentQueryService {
	
	private static Logger logger = Logger.getLogger(UpompQueryServiceImpl.class);
	private String keyPassWord = PaymentConstant.getInstance().getProperty(
			"UPOMP_KEY_PASSWORD");
	private String transType = PaymentConstant.getInstance().getProperty("UNIONPAY_TRANSTYPE_PAY");
	private String merchantId = PaymentConstant.getInstance().getProperty(
			"UPOMP_MERCHANT_ID");
	private String privateCertPath = PaymentConstant.getInstance().getProperty(
			"UPOMP_PFX_PATH");
	private String publicCertPath = PaymentConstant.getInstance().getProperty(
			"UPOMP_PUBLIC_CERT_PATH");
	@Override
	public PaymentQueryReturnInfo paymentStateQuery(PayPayment info) {
//		info.setCreateTime(DateUtil.getDateByStr("20140202121212", DateUtil.PATTERN_yyyyMMddHHmmss));
			OrdersBean  bean = null;
			try {
			 bean =	PlusTools.selectXML(transType, merchantId, info.getPaymentTradeNo(),  DateUtil.formatDate(info.getCreateTime(), DateUtil.PATTERN_yyyyMMddHHmmss), getPriOrPubKey("publicCert"), getPriOrPubKey("privateKey"));
			} catch (Exception e) {
				logger.error("upomquery is fail:" +e.getMessage());
				e.printStackTrace();
			}
			
		
		return responseInfo(bean);
	}

	
	private PaymentQueryReturnInfo responseInfo(OrdersBean bean){
		PaymentQueryReturnInfo info = new PaymentQueryReturnInfo();
		if(bean == null){
			info.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.name());
			info.setCallbackInfo("查询返回为空");
			info.setCodeInfo("查询返回为空");
			return info;
		}
		/**
		 * 订单查询的有效属性为：transType、merchantId、
		 * merchantOrderId、merchantOrderTime、sign、
		 * queryResult、settleDate、setlAmt、setlCurrency、
		 * converRate、cupsQid、cupsTraceNum、cupsTraceTime、
		 * cupsRespCode、cupsRespDesc。
		 */
		if("0000".equals(bean.getCupsRespCode())){
			if("0".equals(bean.getQueryResult())){
				info.setCallbackInfo("");
				info.setCodeInfo("支付成功");
				info.setCode(Constant.PAYMENT_QUERY_STATUS.PAYED.name());
				info.setGatewayTradeNo(bean.getCupsQid());
				info.setRefundSerial(bean.getCupsQid());
			}else if("1".equals(bean.getQueryResult())){
				info.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL_PAY.name());
				info.setCodeInfo("支付失败");
			}else if("2".equals(bean.getQueryResult())){
				info.setCode(Constant.PAYMENT_QUERY_STATUS.WAIT_PAY.name());
				info.setCodeInfo("订单处理中。。");
			}else if("3".equals(bean.getQueryResult())){
				info.setCode(Constant.PAYMENT_QUERY_STATUS.NO_PAYMENT.name());
				info.setCodeInfo("无此订单");
			}else{
				info.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.name());
				info.setCodeInfo("报文格式错误（返回空字符串）");
			}
		}else{
			info.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.name());
			info.setCodeInfo(bean.getRespDesc());
		}
		
		return info;
	}
	
	private Map<String,String> getKeyStoreMap(){
		return 	PlusTools.getCertKey(privateCertPath, keyPassWord, publicCertPath);
	}
	
	private String getPriOrPubKey(String keyType){
		return getKeyStoreMap().get(keyType);
	}
}
