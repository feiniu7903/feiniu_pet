
package com.lvmama.pet.payment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alipay.config.AlipayConfig;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PaymentQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.AlipayUtil;
/**
 * 支付宝的支付查询.
 * @author heyuxing
 *
 */
public class AlipayQueryServiceImpl implements PaymentQueryService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(AlipayQueryServiceImpl.class);

	@Override
	public PaymentQueryReturnInfo paymentStateQuery(PayPayment info) {
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "single_trade_query");
        sParaTemp.put("partner", PaymentConstant.getInstance().getProperty("ALIPAY_PARTNER"));
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("trade_no", "");
		sParaTemp.put("out_trade_no", info.getPaymentTradeNo());
		
		//建立请求
		String xmlResponse = AlipayUtil.sendPostInfo(sParaTemp, PaymentConstant.getInstance().getProperty("ALIPAY_URL"));
		LOG.info("queryAlipayTradeByTradeNo(tradeNo="+info.getGatewayTradeNo()+", outTradeNo="+info.getPaymentTradeNo()+") result : \n"+xmlResponse);
		return responseInfo(xmlResponse);
	}
	
	private PaymentQueryReturnInfo responseInfo(String xmlResponse) {
		PaymentQueryReturnInfo paymentQueryReturnInfo = new PaymentQueryReturnInfo();
		
		try {
			if(StringUtils.isBlank(xmlResponse)) {
				paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
			}else {
				LOG.info("Query2Alipay Response: " + xmlResponse);
				Document requestDoc = DocumentHelper.parseText(xmlResponse);
				Element requestRoot = requestDoc.getRootElement();
				String result = requestRoot.elementText("is_success");
				if("T".equalsIgnoreCase(result)) {
					Element tradeElement = requestRoot.element("response").element("trade");
					if(tradeElement!=null) {
						String tradeStatusElement = tradeElement.elementText("trade_status");
						if("TRADE_SUCCESS".equals(tradeStatusElement) || "WAIT_SELLER_SEND_GOODS".equals(tradeStatusElement) //支付成功；买家已付款，等待卖家发货；
								|| "WAIT_BUYER_CONFIRM_GOODS".equals(tradeStatusElement) || "TRADE_FINISHED".equals(tradeStatusElement) //卖家已发货，等待买家确认；交易成功结束；
								|| "WAIT_SYS_PAY_SELLER".equals(tradeStatusElement) || "TRADE_PENDING".equals(tradeStatusElement) //买家确认收货，等待支付宝打款给卖家；等待卖家收款；
								|| "BUYER_PRE_AUTH".equals(tradeStatusElement) ) {	//买家已付款（语音支付）；
							paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.PAYED.getCode());
							paymentQueryReturnInfo.setCallbackInfo("");
							paymentQueryReturnInfo.setCallbackTime(DateUtil.getDateByStr(tradeElement.elementText("gmt_payment"), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
							paymentQueryReturnInfo.setGatewayTradeNo(tradeElement.elementText("trade_no"));
							paymentQueryReturnInfo.setRefundSerial(tradeElement.elementText("trade_no"));
							paymentQueryReturnInfo.setCodeInfo(tradeElement.elementText("trade_no"));	//网关交易号
						}else if("TRADE_CLOSED".equals(tradeStatusElement) || "TRADE_REFUSE".equals(tradeStatusElement) 	//交易中途关闭（已结束，未成功完成）；立即支付交易拒绝；
								|| "TRADE_REFUSE_DEALING".equals(tradeStatusElement) || "TRADE_CANCEL".equals(tradeStatusElement)){	//立即支付交易拒绝中；立即支付交易取消；
							paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL_PAY.getCode());
						}else {
							paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.WAIT_PAY.getCode());
						}
					}else {
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.WAIT_PAY.getCode());
					}
				}else {
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.NO_PAYMENT.getCode());
				}
			}
		}catch(Exception e) {
			LOG.info("Alipay Query FAIL:" + e.getMessage());
			e.printStackTrace();
		}
		
		return paymentQueryReturnInfo;
	}
}
