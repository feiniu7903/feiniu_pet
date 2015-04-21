package com.lvmama.pet.payment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PaymentQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;
/**
 * 百度支付订单查询
 * @author zhangyong
 *
 */
public class BaiduQueryServiceImpl implements PaymentQueryService {

	public static Logger logger = Logger.getLogger(BaiduQueryServiceImpl.class);

	@Override
	public PaymentQueryReturnInfo paymentStateQuery(PayPayment info) {

		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("service_code", "11");// 目前必须为11
		reqMap.put("order_no", info.getPaymentTradeNo());
		reqMap.put("output_type", "1");// 1 XML
		reqMap.put("output_charset", "1");// 1 GBK
		reqMap.put("version", "2");// 接口的版本号 必须为2
		reqMap.put("sign_method", "1");// 1 MD5 2 SHA-1
		reqMap.put("type", info.getPaymentGateway());//这个是支付路径标志，发送请求时，需要将该字段删除

		return responseInfo(getResponse(reqMap));
	}

	/**
	 * 解析返回的内容
	 * 
	 * @param xmlResponse
	 * @return
	 */
	private PaymentQueryReturnInfo responseInfo(String xmlResponse) {
		
		PaymentQueryReturnInfo paymentQueryReturnInfo = new PaymentQueryReturnInfo();
		try {
			if(StringUtils.isEmpty(xmlResponse)){
				paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
			}else{
				if(logger.isInfoEnabled()){
				logger.info("Query2Baidu Response: " + xmlResponse);
				}
				Document requestDoc = DocumentHelper.parseText(xmlResponse);
				Element requestRoot = requestDoc.getRootElement();
				String status  = requestRoot.elementText("query_status");
				paymentQueryReturnInfo.setCallbackInfo(status);
				if("0".equals(status)){
					String pay_status = requestRoot.elementText("pay_result");
					if("2".equalsIgnoreCase(pay_status)){//支付结果代码 1 支付成功  2 等待支付    3	退款成功
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
						logger.info("query success: pay status is not payed" );
					}else {
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.PAYED.getCode());
						paymentQueryReturnInfo.setCallbackTime(DateUtil.getDateByStr(requestRoot.elementText("pay_time"),DateUtil.PATTERN_yyyyMMddHHmmss));
						paymentQueryReturnInfo.setCallbackInfo("");
						paymentQueryReturnInfo.setGatewayTradeNo(requestRoot.elementText("bfb_order_no"));
						paymentQueryReturnInfo.setRefundSerial(requestRoot.elementText("bfb_order_no"));
						paymentQueryReturnInfo.setCodeInfo(requestRoot.elementText("bfb_order_no"));
					}
				}else if("1002".equals(status)){
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
					logger.info("query failed :  1002");
				}else if("5801".equals(status)){
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
					logger.info("query failed : 5801");
				}else if("5802".equals(status)){
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
					logger.info("query failed : 5802");
				}else if("5803".equals(status)){
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
					logger.info("query failed : 5803");
				}else if("5804".equals(status)){
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
					logger.info("query failed : 5804");
				}else if("5806".equals(status)){
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
					logger.info("query failed : 5806");
				}
				
			}
		} catch (Exception e) {
			logger.error("BAIDU QUERY FAIL:"+e.getMessage());
			e.printStackTrace();
		}
		return paymentQueryReturnInfo;
	}
	/**
	 * 发送请求到百度查询支付结果
	 * 
	 * @param reqMap请求参数
	 * @return
	 */
	private  String getResponse(Map<String, String> reqMap) {
		String res = "";
		String priKey = "";
		String gateway = reqMap.get("type");
		if (Constant.PAYMENT_GATEWAY.BAIDUPAY_APP.name().equals(gateway)
				|| Constant.PAYMENT_GATEWAY.BAIDUPAY_WAP.name().equals(gateway)) {
			reqMap.put(
					"sp_no",
					PaymentConstant.getInstance().getProperty(
							"BAIDUAPPPAY_SP_NO"));// 服务编号
			priKey = PaymentConstant.getInstance().getProperty(
					"BAIDUAPPPAY_KEY");
		} else {
			reqMap.put(
					"sp_no",
					PaymentConstant.getInstance().getProperty(
							"BAIDUWAPPAY_ACTIVITIES_SP_NO"));// 服务编号
			priKey = PaymentConstant.getInstance().getProperty(
					"BAIDUWAPPAY_ACTIVITIES_KEY");
		}
		reqMap.remove("type");
		String sign = COMMUtil.getGBKSignature(reqMap, priKey);// 对参数签名
		reqMap.put("sign", sign);// 签名
		Map<String, Object> sendMap = new HashMap<String, Object>();
		sendMap.putAll(reqMap);
		StringBuilder urlBuilder = new StringBuilder(PaymentConstant.getInstance().getProperty(
				"BAIDUQUERY_BY_ORDER_NO_URL")); 
		urlBuilder.append("?").append(HttpsUtil.mapToUrl(sendMap)); 
		res = HttpsUtil.requestGet(urlBuilder.toString(),"GBK",null);
		return res;
	}
	
}
