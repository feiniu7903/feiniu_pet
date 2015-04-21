package com.lvmama.pet.refundment.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bocnet.common.security.PKCS7Tool;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.refundment.data.BOCRefundCallbackData;
import com.lvmama.pet.vo.PaymentErrorData;

public class BOCRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(BOCRefundServiceImpl.class);
	
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo bankReturnInfo = new BankReturnInfo();
		String requestURL = PaymentConstant.getInstance().getProperty("BOC_REFUND_URL");
		Map<String, String> paras = this.initRefundRequestParam(info);
		String responseStr = HttpsUtil.requestPostForm(requestURL, paras);
		LOG.info("responseStr: " + responseStr);
		BOCRefundCallbackData bocRefundCallbackData = BOCRefundCallbackData.initBOCRefundCallbackXML(responseStr);
		boolean isSuccess = bocRefundCallbackData.isSuccess();
		if(isSuccess){
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
		}
		else{
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
		}
		bankReturnInfo.setCode(bocRefundCallbackData.getHeader().getDealStatus());
		String code = bocRefundCallbackData.getHeader().getException();
		bankReturnInfo.setCodeInfo(PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.BOC.name(), code));
		return bankReturnInfo;
	}

	/**
	 * 初始化.
	 * @param info
	 * @return
	 */
	private Map<String, String> initRefundRequestParam(RefundmentToBankInfo info){
		//商户号
		String merchantNo = PaymentConstant.getInstance().getProperty("BOC_MERCHANT_NO");
		//商户退款交易流水号
		String mRefundSeq = SerialUtil.generate24ByteSerialAttaObjectId(info.getPaymentId());
		//币种
		String curCode = PaymentConstant.getInstance().getProperty("BOC_CUR_CODE");
		//退款金额 
		String refundAmount = String.valueOf(PriceUtil.trans2YuanStr(info.getRefundAmount()));
		//商户订单号
		String orderNo = info.getPaymentTradeNo();
		
		//商户签名数据
		String signData = merchantNo + "|" + mRefundSeq + "|" + curCode + "|" + refundAmount + "|" + orderNo;
		LOG.info("signData: " + signData);
		String signature = this.signature(signData);
		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("merchantNo", merchantNo);
		sPara.put("mRefundSeq", mRefundSeq);
		sPara.put("curCode", curCode);
		sPara.put("refundAmount", refundAmount);
		sPara.put("orderNo", orderNo);
		sPara.put("signData", signature);
		return sPara;
	}
	
	/**
	 * 构建签名.
	 * @param orig
	 * @return
	 */
	private String signature(String orig) {
		String signature = "";
		if(orig != null){
			byte[] data = orig.toString().getBytes();
			try {
				String keyStorePath = PaymentConstant.getInstance().getProperty("BOC_KEY_STORE_PATH");
				String keyStorePassword = PaymentConstant.getInstance().getProperty("BOC_KEY_STORE_PASSWORD");
				String keyPassword = PaymentConstant.getInstance().getProperty("BOC_KEY_PASSWORD");
				PKCS7Tool tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword, keyPassword);
				signature = tool.sign(data);
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				LOG.info("BOC REFUND POST DATA SIGNATURE ERROR GeneralSecurityException:" + e);
			} catch (IOException e) {
				e.printStackTrace();
				LOG.info("BOC REFUND POST DATA SIGNATURE ERROR IOException:" + e);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.info("BOC REFUND POST DATA SIGNATURE ERROR Exception:" + e);
			}
		}
		return signature;
	}

	public static void main(String[] args) {
		BOCRefundServiceImpl bocRefundServiceImpl = new BOCRefundServiceImpl();	
		//中行退款参数格式 (以管道符分隔)： 
		//驴妈妈在中行的商户号|商户退款交易流水号(驴妈妈系统自己生成流水号)|退款币种(001表示人民币 目前只支持人民币)|退款金额|原商户订单号（付钱时交易的流水号）
		String signData ="104310145110434"+"|"+"666931728"+"|"+"001"+"|"+"1.00"+"|"+"1307820156";
		//测试获取签名
		String signature = bocRefundServiceImpl.signature(signData.trim());
		System.out.println(signature);
		
//		String url = "https://ebspay.boc.cn/PGWPortal/RefundOrder.do";
//		Map<String, String> sPara = new HashMap<String, String>();
//		sPara.put("merchantNo", "104310145110434");
//		sPara.put("mRefundSeq", "666931727");
//		sPara.put("curCode", "001");
//		sPara.put("refundAmount", "1.00");
//		sPara.put("orderNo", "1307820156");
//		sPara.put("signData", signature);
//		
//		String response = HttpsUtil.requestPostForm(url, sPara);
//		System.out.println(response);
	}
}
