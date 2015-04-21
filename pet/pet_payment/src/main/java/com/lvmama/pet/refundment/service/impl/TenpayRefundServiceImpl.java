
package com.lvmama.pet.refundment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.COMMUtil;
import com.lvmama.pet.utils.StringDom4jUtil;
import com.lvmama.pet.utils.TenpayUtil;
/**
 *  财付通退款
 * @author zhangjie
 *
 */
public class TenpayRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(TenpayRefundServiceImpl.class);

	
	/**
	 * 财付通退款
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
			LOG.info("tenpay refund request=" + sParaTemp.toString());
			String date = TenpayUtil.getRefundStringDate(sParaTemp);
			
			result = TenpayUtil.cllTenpayHttpsRefund(date,info.getRefundGateway());
			
			LOG.info("tenpay refund result=" + result);

			String key;
			if(Constant.PAYMENT_GATEWAY.TENPAY.name().equalsIgnoreCase(info.getRefundGateway())){
				key = PaymentConstant.getInstance().getProperty("TENPAY_KEY");
			}else if(Constant.PAYMENT_GATEWAY.WEIXIN_WEB.name().equalsIgnoreCase(info.getRefundGateway())){
				key = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_KEY");
			}else if(Constant.PAYMENT_GATEWAY.WEIXIN_IOS.name().equalsIgnoreCase(info.getRefundGateway())){
				key = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_KEY");
			}else if(Constant.PAYMENT_GATEWAY.WEIXIN_ANDROID.name().equalsIgnoreCase(info.getRefundGateway())){
				key = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_KEY");
			}else{
				key = PaymentConstant.getInstance().getProperty("TENPAY_PHONE_KEY");
			}
			Map<String, String> resultMap=StringDom4jUtil.parseTenpayRefundResult(result);
			
			returnInfo.setSerial(resultMap.get("out_refund_no"));
			returnInfo.setCodeInfo(resultMap.get("retmsg"));
			
		    if(StringUtils.isNotBlank(result) && "0".equals(resultMap.get("retcode"))&&COMMUtil.getSignature(resultMap,key).equals(resultMap.get("sign"))){
		    	String resultState = resultMap.get("refund_status");
		    	if("4".equals(resultState)
		    			||"10".equals(resultState)
		    			||"8".equals(resultState)
		    			||"9".equals(resultState)
		    			||"11".equals(resultState)){
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());	
		    	}
		    	else if("3".equals(resultState)
		    			|| "5".equals(resultState)
		    			|| "6".equals(resultState)
		    			|| "7".equals(resultState)){
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
		    	}
		    	else if("1".equals(resultState)
		    			||"2".equals(resultState)
		    			){
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());
		    		returnInfo.setCodeInfo("退款处理中，请业务人员人工向财付通平台核实退款是否成功, 返回信息:"+resultMap.get("retmsg"));
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
		String partner;
		String op_user_id;
		String op_user_passwd;
		String key;
		if(Constant.PAYMENT_GATEWAY.TENPAY.name().equalsIgnoreCase(info.getRefundGateway())){
			partner = PaymentConstant.getInstance().getProperty("TENPAY_PARTNER");
			op_user_id = PaymentConstant.getInstance().getProperty("TENPAY_USER_ID");
			op_user_passwd = TenpayUtil.getMD5String(PaymentConstant.getInstance().getProperty("TENPAY_USER_PASSWD"));
			key = PaymentConstant.getInstance().getProperty("TENPAY_KEY");
		}else if(Constant.PAYMENT_GATEWAY.WEIXIN_WEB.name().equalsIgnoreCase(info.getRefundGateway())){
			partner = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_PARTNER");
			op_user_id = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_USER_ID");
			op_user_passwd = TenpayUtil.getMD5String(PaymentConstant.getInstance().getProperty("WEIXIN_WEB_USER_PASSWD"));
			key = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_KEY");
		}else if(Constant.PAYMENT_GATEWAY.WEIXIN_IOS.name().equalsIgnoreCase(info.getRefundGateway())){
			partner = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PARTNER");
			op_user_id = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_USER_ID");
			op_user_passwd = TenpayUtil.getMD5String(PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_USER_PASSWD"));
			key = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_KEY");
		}else if(Constant.PAYMENT_GATEWAY.WEIXIN_ANDROID.name().equalsIgnoreCase(info.getRefundGateway())){
			partner = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PARTNER");
			op_user_id = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_USER_ID");
			op_user_passwd = TenpayUtil.getMD5String(PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_USER_PASSWD"));
			key = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_KEY");
		}else{
			partner = PaymentConstant.getInstance().getProperty("TENPAY_PHONE_PARTNER");
			op_user_id = PaymentConstant.getInstance().getProperty("TENPAY_PHONE_USER_ID");
			op_user_passwd = TenpayUtil.getMD5String(PaymentConstant.getInstance().getProperty("TENPAY_PHONE_USER_PASSWD"));
			key = PaymentConstant.getInstance().getProperty("TENPAY_PHONE_KEY");
		}
		String out_refund_no = "";
		if(StringUtils.isNotBlank(info.getPayPaymentRefunfmentSerial())){
			out_refund_no = info.getPayPaymentRefunfmentSerial();
		}else{
			out_refund_no = SerialUtil.generate24ByteSerialAttaObjectId(info.getPayRefundmentId());
		}
		String out_trade_no = info.getPaymentTradeNo();
		String transaction_id = info.getGatewayTradeNo();
		String total_fee = info.getPayAmount().toString();
		String refund_fee = info.getRefundAmount().toString();
		
		
		resultMap.put("sign_type", "MD5");
		resultMap.put("input_charset", "UTF-8");
		resultMap.put("sign_key_index", "1");
		resultMap.put("service_version", "1.1");
		resultMap.put("partner", partner);
		resultMap.put("out_trade_no", out_trade_no);
		resultMap.put("transaction_id", transaction_id);
		resultMap.put("out_refund_no", out_refund_no);
		resultMap.put("total_fee", total_fee);
		resultMap.put("refund_fee", refund_fee);
		resultMap.put("op_user_id", op_user_id);
		resultMap.put("op_user_passwd", op_user_passwd);
		resultMap.put("recv_user_id", "");
		resultMap.put("reccv_user_name", "");
		resultMap.put("use_spbill_no_fla", "");
		
		resultMap.put("sign", COMMUtil.getSignature(resultMap,key));
		
	    return resultMap;
	}
	
}
