
package com.lvmama.pet.refundment.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.infosec.NetSignServer;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.StringDom4jUtil;
import com.lvmama.pet.vo.PaymentErrorData;
/**
 * 宁波银行-退款.
 * @author ZHANG Nan
 *
 */
public class NingboBankRefundServiceImpl implements BankRefundmentService {
	private static final Logger LOG = Logger.getLogger(NingboBankRefundServiceImpl.class);

	private String refund_url=PaymentConstant.getInstance().getProperty("NING_BO_BANK_REFUND_URL");
	private String reqServiceId="NCTR02Comm";
	private String reqFlowNo="";
	private String reqCustomerId=PaymentConstant.getInstance().getProperty("NING_BO_BANK_REQ_CUSTOMER_ID");
	private String reqChannelId="NC";
	private String reqDataView="XML";
	private String notify_url=PaymentConstant.getInstance().getProperty("NING_BO_BANK_REFUND_NOTIFY_URL");
	private String batch_num ="1";
	private String batch_no="";
	private String use_freeze_amount="N";
	//证书验签参数
	private String dnHead =  PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_HEAD");
	private String dnTail = PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_TAIL");
	
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo returnInfo =new BankReturnInfo();
		String out_order_no=SerialUtil.generate24ByteSerialAttaObjectId(info.getPaymentId());
	    try {
			String sendMessage= this.initRequestParamsMap(info,out_order_no);
			LOG.info("NingboBank Refund sendMessage="+sendMessage);
			String result=HttpsUtil.requestPostData(refund_url, sendMessage, "text/html", "GBK").getResponseString("GBK");
			LOG.info("NingboBank Refund result=" + result);
			if(StringUtils.isNotBlank(result)){
				Map<String, String> resultMap = StringDom4jUtil.parseNingBoBankRefundResult(result);
				if("0000".equals(resultMap.get("ec"))){
			    	if("T".equals(resultMap.get("is_success")) || "P".equals(resultMap.get("is_success"))){
			    		//对于宁波银行支付宝来说 退款同步返回的成功 表示申请退款成功
			    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());
			    	}
			    	else if("F".equals(resultMap.get("is_success"))){
			    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());	
			    	}
			    	else{
			    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());	
			    	}
			    	returnInfo.setCodeInfo(PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.NING_BO_BANK.name(), resultMap.get("error")));
				}
				else{
					returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
					returnInfo.setCodeInfo(resultMap.get("em"));	
				}
		    }
		    else{
		    	returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
		    	returnInfo.setCodeInfo(PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.NING_BO_BANK.name(), "UNKNOWN_ERROR"));
		    }
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			returnInfo.setCodeInfo(e.getMessage());
		}
		returnInfo.setSerial(out_order_no);
		return returnInfo;    
	}
	
	private String initRequestParamsMap(RefundmentToBankInfo info,String out_order_no){
		String reqTime=DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
		String refund_date=DateUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		String detail_data=info.getGatewayTradeNo()+"^"+PriceUtil.trans2YuanStr(info.getRefundAmount())+"^"+"lvmama_refund";
		String sd=signature(refund_date,detail_data,out_order_no);
		StringBuffer sendMessage=new StringBuffer();
		sendMessage.append("<?xml version=\"1.0\" encoding=\"gbk\" ?>");
		sendMessage.append("<root>");
			sendMessage.append("<reqServiceId>"+reqServiceId+"</reqServiceId>");
			sendMessage.append("<reqTime>"+reqTime+"</reqTime>");
			sendMessage.append("<reqFlowNo>"+reqFlowNo+"</reqFlowNo>");
			sendMessage.append("<reqCustomerId>"+reqCustomerId+"</reqCustomerId>");
			sendMessage.append("<reqChannelId>"+reqChannelId+"</reqChannelId>");
			sendMessage.append("<reqDataView>"+reqDataView+"</reqDataView>");
			sendMessage.append("<cd>");
				sendMessage.append("<notify_url>"+notify_url+"</notify_url>");
				sendMessage.append("<refund_date>"+refund_date+"</refund_date>");
				sendMessage.append("<batch_num>"+batch_num+"</batch_num>");
				sendMessage.append("<batch_no>"+batch_no+"</batch_no>");
				sendMessage.append("<detail_data>"+detail_data+"</detail_data>");
				sendMessage.append("<use_freeze_amount>"+use_freeze_amount+"</use_freeze_amount>");
				sendMessage.append("<out_order_no>"+out_order_no+"</out_order_no>");
			sendMessage.append("</cd>");
			sendMessage.append("<sd>"+sd+"</sd>");
		sendMessage.append("</root>");
	    return sendMessage.toString();
	}
	/**
	 * 退款签名
	 * @author ZHANG Nan
	 * @return
	 */
	private String signature(String refund_date,String detail_data,String out_order_no) {
		String sourceMsg=
		"<cd>" +
			"<notify_url>"+notify_url+"</notify_url>"+
			"<refund_date>"+refund_date+"</refund_date>"+
			"<batch_num>"+batch_num+"</batch_num>"+
			"<batch_no>"+batch_no+"</batch_no>"+
			"<detail_data>"+detail_data+"</detail_data>"+
			"<use_freeze_amount>"+use_freeze_amount+"</use_freeze_amount>"+
			"<out_order_no>"+out_order_no+"</out_order_no>" +
		"</cd>";
		LOG.info("refund signature SourceMsg="+sourceMsg);
		String signMsg="";
		try {
			String bankDN = dnHead + dnTail;
			NetSignServer nss = new NetSignServer();
			nss.NSSetPlainText(sourceMsg.getBytes("gbk"));
			byte bSignMsg[] = nss.NSDetachedSign(bankDN);  
			int i = nss.getLastErrnum();
			LOG.info("verifyCode...." +i);
			signMsg=new String(bSignMsg,"gbk");
			LOG.info("signMsg...." + signMsg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signMsg;
	}
}
