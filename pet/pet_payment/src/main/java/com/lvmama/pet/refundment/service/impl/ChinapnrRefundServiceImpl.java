
package com.lvmama.pet.refundment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import chinapnr.SecureLink;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.vo.PaymentErrorData;
/**
 * 汇付天下的退款.
 * @author ZHANG Nan
 *
 */
public class ChinapnrRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(ChinapnrRefundServiceImpl.class);

	
	/**
	 * 汇付天下退款逻辑和报文.
	 * @param refundSerial
	 * @param amount
	 * @return
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo bankReturnInfo = new BankReturnInfo();
		Map<String, String> map = initRequestParamsMap(info);
		String response = HttpsUtil.requestPostForm(PaymentConstant.getInstance().getProperty("CHINAPNR_URL"), map);
		LOG.info("chinapnr refund response="+response);
		Map<String, String> resultMap=parseRefundData(response);
		bankReturnInfo.setSuccessFlag(isSuccess(resultMap));
		bankReturnInfo.setCode(resultMap.get("RespCode"));
		if(StringUtils.isNotBlank(resultMap.get("ErrMsg"))){
			bankReturnInfo.setCodeInfo(resultMap.get("ErrMsg"));
		}
		else{
			bankReturnInfo.setCodeInfo(PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.CHINAPNR.name(),resultMap.get("RespCode")));	
		}
		bankReturnInfo.setSerial(map.get("OrdId"));
		return bankReturnInfo;
	}
	private Map<String,String> initRequestParamsMap(RefundmentToBankInfo info){
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("Version", PaymentConstant.getInstance().getProperty("CHINAPNR_VERSION"));
		paramMap.put("CmdId",PaymentConstant.getInstance().getProperty("CHINAPNR_REFUNDMENT_TRANSTYPE"));
		paramMap.put("MerId", PaymentConstant.getInstance().getProperty("CHINAPNR_MERID"));
		String refundSerial=SerialUtil.generate15ByteSerial();
		paramMap.put("OrdId", refundSerial);
		paramMap.put("OldOrdId",info.getPaymentTradeNo());
		paramMap.put("RefAmt",PriceUtil.trans2YuanStr(info.getRefundAmount()));
		paramMap.put("DivDetails","");
		paramMap.put("BgRetUrl", PaymentConstant.getInstance().getProperty("CHINAPNR_REFUND_NOTIFY_URL"));
		
		// 构造签名数据体
		String chkdata = PaymentConstant.getInstance().getProperty("CHINAPNR_VERSION")
				+ PaymentConstant.getInstance().getProperty("CHINAPNR_REFUNDMENT_TRANSTYPE") + PaymentConstant.getInstance().getProperty("CHINAPNR_MERID")
				+ refundSerial + info.getPaymentTradeNo() + PriceUtil.trans2YuanStr(info.getRefundAmount()) + ""
				+ PaymentConstant.getInstance().getProperty("CHINAPNR_REFUND_NOTIFY_URL");
		SecureLink secureLink = new SecureLink();
		// 构造签名是否成功
		int signflag = secureLink.SignMsg(PaymentConstant.getInstance().getProperty("CHINAPNR_MERID"), PaymentConstant.getInstance().getProperty("CHINAPNR_MP_PATH"), chkdata);
		if (signflag != 0) {
			LOG.error("chinapnr refund generateSign fail!");
			return null;
		}
		paramMap.put("ChkValue", secureLink.getChkValue());
		return paramMap;
	}
	private Map<String,String> parseRefundData(String responseXml){
		Map<String,String> paramMap=new HashMap<String,String>();
		if(StringUtils.isNotBlank(responseXml)){
			String data[]=responseXml.split("\r\n");
			for(int i=0,j=data.length;i<j;i++){
				paramMap.put(data[i].split("=")[0], data[i].split("=")[1]);
			}
		}
		return paramMap;
	}
	private String isSuccess(Map<String, String> resultMap){
		//if("000000".equals(resultMap.get("RespCode")) && checkSign(resultMap)){
		//TODO 等待汇付天下解决乱码问题后再验签 
		if("000000".equals(resultMap.get("RespCode"))){
			return Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name();
		}
		else if("99".equals(resultMap.get("RespCode"))){
			return Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name();
		}
		return Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name();
	}
	private Boolean checkSign(Map<String, String> resultMap){
		String MerKeyFile = PaymentConstant.getInstance().getProperty("CHINAPNR_PG_PATH");
		String MerData = resultMap.get("CmdId") + resultMap.get("RespCode") + resultMap.get("OrdId") + resultMap.get("OldOrdId") + resultMap.get("ErrMsg");
		SecureLink sl = new SecureLink();
		int ret = sl.VeriSignMsg(MerKeyFile , MerData, resultMap.get("ChkValue"));
		LOG.info("chinapnr refund result verifySign ret="+ret);
		return ret==0;
	}
}
