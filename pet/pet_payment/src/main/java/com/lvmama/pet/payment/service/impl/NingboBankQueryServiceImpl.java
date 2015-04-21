
package com.lvmama.pet.payment.service.impl;

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
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.recon.utils.NingboReconUtil;
/**
 * 宁波银行的支付查询.
 * @author heyuxing
 *
 */
public class NingboBankQueryServiceImpl implements PaymentQueryService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(NingboBankQueryServiceImpl.class);
	
	private String reqCustomerId=PaymentConstant.getInstance().getProperty("NING_BO_BANK_REQ_CUSTOMER_ID");
	//证书验签参数
	private String dnHead =  PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_HEAD");
	private String dnTail = PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_TAIL");
	private String query_url=PaymentConstant.getInstance().getProperty("NING_BO_BANK_QUERY_URL");

	@Override
	public PaymentQueryReturnInfo paymentStateQuery(PayPayment info) {
		String xmlResponse = null;
		
		//封装数据
		try{
			String postData = NingboReconUtil.initQueryRequestParamsMap(reqCustomerId, "", info.getPaymentTradeNo(), dnHead, dnTail);
			//建立请求
			xmlResponse=HttpsUtil.requestPostData(query_url,postData,"text/html","GBK",600000,600000).getResponseString("GBK");
			System.out.println(xmlResponse);
			LOG.info("queryAlipayTradeByTradeNo(tradeNo="+info.getGatewayTradeNo()+", outTradeNo="+info.getPaymentTradeNo()+") result : \n"+xmlResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseInfo(xmlResponse);
	}
	
	private PaymentQueryReturnInfo responseInfo(String xmlResponse) {
		PaymentQueryReturnInfo paymentQueryReturnInfo = new PaymentQueryReturnInfo();
		
		try {
			if(StringUtils.isBlank(xmlResponse)) {
				paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
			}else if(!NingboReconUtil.checkSignature(xmlResponse)) {//验签
				paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
				LOG.info("Query2NingboBank Response checkSignature fail!\n" + xmlResponse);
			}else {
				LOG.info("Query2NingboBank Response: " + xmlResponse);
				Document requestDoc = DocumentHelper.parseText(xmlResponse);
				Element requestRoot = requestDoc.getRootElement();
				String result = requestRoot.elementText("ec");
				if("0000".equalsIgnoreCase(result)) {
					Element tradeElement = requestRoot.element("cd");
					if(tradeElement!=null && "T".equals(tradeElement.elementText("is_success"))) {
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.PAYED.getCode());
						paymentQueryReturnInfo.setCallbackInfo("");
						paymentQueryReturnInfo.setCallbackTime(DateUtil.getDateByStr(tradeElement.elementText("gmt_payment"), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
						paymentQueryReturnInfo.setGatewayTradeNo(tradeElement.elementText("trade_no"));
						paymentQueryReturnInfo.setRefundSerial(tradeElement.elementText("trade_no"));
						paymentQueryReturnInfo.setCodeInfo(tradeElement.elementText("trade_no"));	//网关交易号
					}else {
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL_PAY.getCode());
					}
				}else {
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.NO_PAYMENT.getCode());
				}
			}
		}catch(Exception e) {
			LOG.info("Query2NingboBank FAIL:" + e.getMessage());
			e.printStackTrace();
		}
		
		return paymentQueryReturnInfo;
	}
}
