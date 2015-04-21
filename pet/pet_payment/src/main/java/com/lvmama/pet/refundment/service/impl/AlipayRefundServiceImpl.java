
package com.lvmama.pet.refundment.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alipay.config.AlipayConfig;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.AlipayUtil;
/**
 * 支付宝的退款.
 * @author liwenzhan
 *
 */
public class AlipayRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(AlipayRefundServiceImpl.class);

	
	/**
	 * 支付宝退款逻辑和报文.
	 * @param refundSerial
	 * @param amount
	 * @return
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		String result = "";
		BankReturnInfo returnInfo =new BankReturnInfo();
	    try {
			Map<String, String> sParaTemp = this.initRequestParamsMap(info);
			LOG.info("sParaTemp="+sParaTemp.toString());
			result = AlipayUtil.sendPostInfo(sParaTemp, PaymentConstant.getInstance().getProperty("ALIPAY_REFUND_GATEWAY_URL"));
			LOG.info("ALIPAY REFUND RESULT=" + result);
			returnInfo.setSerial(sParaTemp.get("batch_no"));
		    returnInfo.setCodeInfo(result);
		    
		    if(StringUtils.isNotBlank(result)){
		    	if(result.contains("<is_success>T</is_success>")){
		    		//对于支付宝来说 退款同步返回的成功 表示申请退款成功
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());	
		    	}
		    	else if(result.contains("<is_success>F</is_success>")){
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());	
		    	}
		    	else if(result.contains("<is_success>P</is_success>")){
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());	
		    	}
		    }
		    else{
		    	returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    if(!(result != null && result.contains("<is_success>T</is_success>"))){
	    	LOG.info("refundment failed: " + result);
	    } 
       return returnInfo;    
	}
	
	private Map<String,String> initRequestParamsMap(RefundmentToBankInfo info){
		Map<String, String> resultMap = new HashMap<String, String>();
		Date now = new Date();
		String refundAmount = PriceUtil.trans2YuanStr(info.getRefundAmount());
		//退款批次号
		String batch_no = this.formatDate(now, "yyyyMMddHHmmssSSS");
		//退款请求时间
		String refund_date = this.formatDate(now, "yyyy-MM-dd HH:mm:ss");
		//退款总笔数
		String batch_num = "1";
		//单笔数据集
		String detail_data = "";
		//退款原因(退款原因记录成批次号 给后面对账时能拿到退款时上送的批次号做准备,
		//此字段值不可更改 否则会导致后面支付宝对账无法获取到pay_payment_refundment的serial
		String refundReason = Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.LVMAMA_REFUNDMENT.name()+batch_no;
		detail_data = info.getGatewayTradeNo() + "^" + refundAmount + "^" + refundReason;
		String ALIPAY_PARTNER = PaymentConstant.getInstance().getProperty("ALIPAY_PARTNER");
		String ALIPAY_REFUND_NOTIFY_URL = PaymentConstant.getInstance().getProperty("ALIPAY_REFUND_NOTIFY_URL");
		
		resultMap.put("batch_no", batch_no);
		resultMap.put("refund_date", refund_date);
		resultMap.put("batch_num", batch_num);
		resultMap.put("detail_data", detail_data);
		resultMap.put("service", "refund_fastpay_by_platform_nopwd");
		resultMap.put("partner", ALIPAY_PARTNER);
		resultMap.put("sign_type", "MD5");
		resultMap.put("notify_url", ALIPAY_REFUND_NOTIFY_URL);
		resultMap.put("_input_charset", AlipayConfig.input_charset);
	    return resultMap;
	}
	
	private String formatDate(Date date,String pattern){
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	public static void main(String[] args) {
		AlipayRefundServiceImpl alipay = new AlipayRefundServiceImpl();
		RefundmentToBankInfo info = new RefundmentToBankInfo();
		info.setRefundSerial("2012082115117463");
		info.setRefundAmount(100l);
		BankReturnInfo returnInfo = alipay.refund(info);
		System.out.println(returnInfo.getCode());
	}

	
}
