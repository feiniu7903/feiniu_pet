package com.lvmama.pet.payment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PaymentQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;
import com.lvmama.pet.utils.TenpayUtil;
/**
 * @author zhangyong
 * @description 
 * 财付通手机app支付订单查询实现
 * @date 2014年6月11日 13:58:36
 *
 */
public class TenPayPhoneQueryServiceImpl implements PaymentQueryService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(TenPayPhoneQueryServiceImpl.class);
	@Override
	public PaymentQueryReturnInfo paymentStateQuery(PayPayment info) {
		
		Map<String,String> reqMap = new HashMap<String,String>();
		
		reqMap.put("ver", "2.0");//版本号
		reqMap.put("charset", "UTF-8");//编码
		reqMap.put("sign_key_index", "1");//密钥序号
		reqMap.put("sp_billno", info.getPaymentTradeNo());//商户订单号
		reqMap.put("transaction_id","");//财付通订单号
		reqMap.put("attach","");//附加信息 商家数据包，原样返回
		reqMap.put("type", info.getPaymentGateway());
		
		String response = TenpayUtil.getTenpayRes(reqMap);
		
		
		return responseInfo(response);
	}
	
	/**
	 * 解析返回的内容
	 * @param xmlResponse
	 * @return
	 */
	private PaymentQueryReturnInfo responseInfo(String xmlResponse) {
		PaymentQueryReturnInfo paymentQueryReturnInfo = new PaymentQueryReturnInfo();
		
		try {
			if(StringUtils.isBlank(xmlResponse)) {
				paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
			}else {
				LOG.info("Query2TenpayPhone  Response: " + xmlResponse);
				Document requestDoc = DocumentHelper.parseText(xmlResponse);
				Element requestRoot = requestDoc.getRootElement();
				String result = requestRoot.elementText("pay_result");
				String pay_info= requestRoot.elementText("pay_info");
				if("0".equalsIgnoreCase(result)&&StringUtil.isEmptyString(pay_info)) {//返回状态码，0表示成功，其它—失败 pay_info支付结果信息，支付成功时为空
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.PAYED.getCode());
						paymentQueryReturnInfo.setCallbackInfo("");
						paymentQueryReturnInfo.setCallbackTime(DateUtil.getDateByStr(requestRoot.elementText("time_end"), DateUtil.PATTERN_yyyyMMddHHmmss));
						paymentQueryReturnInfo.setGatewayTradeNo(requestRoot.elementText("transaction_id"));
						paymentQueryReturnInfo.setRefundSerial(requestRoot.elementText("transaction_id"));
						paymentQueryReturnInfo.setCodeInfo(requestRoot.elementText("transaction_id"));
				}else{
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
					paymentQueryReturnInfo.setCallbackInfo(requestRoot.elementText("pay_info"));
					LOG.info("Query2TenpayPhone  Response: tenpay not define this code "+result );	
				}
			}
		}catch(Exception e) {
			LOG.info("TenPay Phone Query FAIL:" + e.getMessage());
			e.printStackTrace();
		}
		
		return paymentQueryReturnInfo;
	}
	public static void main(String[] args) {
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("sign_type", "MD5");//加密算法
		reqMap.put("service_version", "1.0");//版本
		reqMap.put("input_charset", "UTF-8");//编码
		reqMap.put("sign_key_index", "1");//密钥序号
		reqMap.put("partner", "1216574701");//商户号
		reqMap.put("out_trade_no","1335100");//商户订单号
		reqMap.put("transaction_id","");//财付通订单号
		reqMap.put("use_spbill_no_flag", "");//通过商户订单号查询,不填值，根据财付通接口
	//	System.out.println(COMMUtil.getSignature(reqMap, tenpaykey));
		String res="";
		
		String sign = COMMUtil.getSignature(reqMap,"5977d7ae78e897aa2d34cec70ccf0215");//对参数签名
	
//		reqMap.put("partner", PaymentConstant.getInstance().getProperty("TENPAY_PARTNER"));//商户号
		reqMap.put("partner", "1216574701");//商户号
		reqMap.put("sign", sign);//签名
//		res = HttpsUtil.requestPostForm( PaymentConstant.getInstance().getProperty("TENPAY_QUERY_URL"), reqMap);
		res = HttpsUtil.requestPostForm( "https://gw.tenpay.com/gateway/normalorderquery.xml", reqMap);
		System.out.println(	res);
	}
}
