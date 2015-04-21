
package com.lvmama.pet.refundment.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.COMMUtil;
import com.lvmama.pet.utils.StringDom4jUtil;
/**
 *  百度钱包退款
 * @author zhangjie
 *
 */
public class BaidupayRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(BaidupayRefundServiceImpl.class);

	
	/**
	 * 百度钱包退款
	 * @param refundSerial
	 * @param info
	 * @return
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		String result = "";
		BankReturnInfo returnInfo =new BankReturnInfo();
	    try {
			Map<String, String> sParaTemp = this.initRequestParamsMap(info);
			LOG.info("baidu refund request=" + sParaTemp.toString());
			String date = getRefundRequestDate(sParaTemp);
			
			result = HttpsUtil.requestGet(PaymentConstant.getInstance().getProperty("BAIDUPAY_REFUND_URL")+"?"+date);
			
			LOG.info("baidupay refund result=" + result);

			Map<String, String> resultMap=StringDom4jUtil.parseBaidupayRefundResult(result);
			
			returnInfo.setSerial(resultMap.get("sp_refund_no"));
			returnInfo.setCodeInfo(resultMap.get("ret_detail"));
			
			if(StringUtils.isNotBlank(result)){
		    	if("1".equals(resultMap.get("ret_code"))){
		    		//百度钱包退款申请返回1为接受退款申请，退款成功与否在return_url回调
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());	
		    		returnInfo.setCodeInfo("退款处理中，百度钱包会异步回调是否退款成功, 返回信息:"+resultMap.get("ret_detail"));
		    	}
		    	else{
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());	
		    	}
		    }
		    else{
		    	returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
		    }
		} catch (Exception e) {
			e.printStackTrace();
    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
    		returnInfo.setCodeInfo(e.getMessage());
		}
       return returnInfo;    
	}
	
	private Map<String,String> initRequestParamsMap(RefundmentToBankInfo info){
		Map<String, String> resultMap = new HashMap<String, String>();
		String return_url = PaymentConstant.getInstance().getProperty("BAIDUPAY_REFUND_NOTIFY_URL");
		String sp_no;
		String key;
		if(Constant.PAYMENT_GATEWAY.BAIDUPAY_APP.name().equalsIgnoreCase(info.getPaymentGateway())||Constant.PAYMENT_GATEWAY.BAIDUPAY_WAP.name().equalsIgnoreCase(info.getPaymentGateway())){
			sp_no = PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_SP_NO");
			key = PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_KEY");
		}else{
			sp_no = PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_ACTIVITIES_SP_NO");
			key = PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_ACTIVITIES_KEY");
		}
		
		String order_no = info.getPaymentTradeNo();
		String cashback_amount = info.getRefundAmount().toString();
		String cashback_time = this.formatDate(new Date(), "yyyyMMddHHmmssSSS");
		String sp_refund_no;
		if(StringUtils.isNotBlank(info.getPayPaymentRefunfmentSerial())){
			sp_refund_no = info.getPayPaymentRefunfmentSerial();
		}else{
			sp_refund_no = SerialUtil.generate20ByteSerialAttaObjectId(info.getPayRefundmentId());
		}
		
		resultMap.put("service_code", "2");
		resultMap.put("input_charset", "1");
		resultMap.put("sign_method", "1");
		resultMap.put("output_type", "1");
		resultMap.put("output_charset", "1");
		resultMap.put("return_url", return_url);
		resultMap.put("return_method", "2");
		resultMap.put("version", "2");
		resultMap.put("sp_no", sp_no);
		resultMap.put("order_no", order_no);
		resultMap.put("cashback_amount", cashback_amount);
		resultMap.put("cashback_time", cashback_time);
		resultMap.put("currency", "1");
		resultMap.put("sp_refund_no", sp_refund_no);
		resultMap.put("sign", COMMUtil.getGBKSignature(resultMap,key));
		
	    return resultMap;
	}

	private String formatDate(Date date,String pattern){
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	 /**
	 * 生成退款请求数据
	 * @param resultMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getRefundRequestDate(Map<String, String> sParaTemp) throws UnsupportedEncodingException {
		List<String> keys = new ArrayList<String>(sParaTemp.keySet());
       StringBuffer prestr = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = sParaTemp.get(key);
			if("return_url".equals(key)){
				value = URLEncoder.encode(value,"GBK");
			}
			if (first) {
				prestr.append(key + "=" + value);
				first = false;
			} else {
				prestr.append("&" + key + "=" + value);
			}
		}
		return prestr.toString();
	}
	
}
