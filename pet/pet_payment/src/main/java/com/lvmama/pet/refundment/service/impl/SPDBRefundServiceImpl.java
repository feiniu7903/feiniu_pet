
package com.lvmama.pet.refundment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.SPDBUtil;
import com.lvmama.pet.utils.StringDom4jUtil;
import com.lvmama.pet.vo.PaymentErrorData;
/**
 * 上海浦东发展银行的退款.
 * @author zhangzhenhua
 *
 */
public class SPDBRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(SPDBRefundServiceImpl.class);
	private String notify_url = PaymentConstant.getInstance().getProperty("SPDB_REFUND_NOTIFY_URL");

	
	/**
	 * 上海浦东发展银行退款逻辑和报文.
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
			LOG.info("SPDB refund post params:" + sParaTemp.toString());
			result = HttpsUtil.requestPostForm(notify_url, sParaTemp, "GBK","UTF-8");
			LOG.info("SPDB REFUND RESULT=" + result);
			Map<String, String> resultMap = StringDom4jUtil.parseSPDBRefundResult(result);
			if(resultMap!=null) {
				Map<String, String> plainMap = SPDBUtil.getPlainMap(resultMap.get("Plain"));
				String respCode = plainMap.get("RespCode");
				String codeInfo = "";
				if(StringUtils.isEmpty(respCode)) {
					respCode = resultMap.get("ErrorCode");
					codeInfo = resultMap.get("ErrorMsg");
				} else {
					codeInfo = PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.SPDB.name(), respCode);
				}
				returnInfo.setSerial(plainMap.get("TermSsn"));//对方订单号
				returnInfo.setCode(respCode);
			    returnInfo.setCodeInfo(codeInfo);
			    
			    if("00".equals(respCode)&&SPDBUtil.checkSignature(resultMap.get("Signature"),resultMap.get("Plain"))){
		    		returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());	
			    } else {
			    	returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			    }
			} else{
		    	returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
		    }
			LOG.info("returnInfo:"+StringUtil.printParam(returnInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("SPDB REFUND eturnInfo.getSuccessFlag###=" + returnInfo.getSuccessFlag());
       return returnInfo;    
	}
	
	private Map<String,String> initRequestParamsMap(RefundmentToBankInfo info){
		Map<String, String> resultMap = new HashMap<String, String>();
		StringBuffer plain = new StringBuffer();
		plain.append("TranAbbr=IPSR");// 交易类型
		plain.append("|MercDtTm="+DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));// 交易时间（退款请求时间）
		plain.append("|TermSsn="+SerialUtil.generate12ByteSerial());// 订单号,不能重复,随机生成流水12位
		plain.append("|OSttDate="+DateUtil.formatDate(info.getCallbackTime(), "yyyyMMdd"));// 原交易的清算日期
		plain.append("|OAcqSsn="+info.getGatewayTradeNo());// 原网关支付流水
		plain.append("|MercCode="+PaymentConstant.getInstance().getProperty("SPDB_MERCCODE"));// 商户号
		plain.append("|TermCode=00000000");// 终端号，没有时为零
		plain.append("|TranAmt="+PriceUtil.trans2YuanStr(info.getRefundAmount()));// 交易金额元为单位
		resultMap.put("transName", "IPSR");
		resultMap.put("Plain", plain.toString());
		resultMap.put("Signature", SPDBUtil.getSignature(plain.toString()));//去生成签名
	    return resultMap;
	}
	
	public static void main(String[] args) {
		System.out.println(SerialUtil.generate15ByteSerial());;
	}
	
}