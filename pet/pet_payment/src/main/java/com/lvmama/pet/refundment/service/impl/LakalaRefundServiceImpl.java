package com.lvmama.pet.refundment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;

/**
 * 拉卡拉退款服务
 * @author shengshenghua
 *
 */
public class LakalaRefundServiceImpl implements BankRefundmentService{
	
	private static final Logger LOG = Logger.getLogger(LakalaRefundServiceImpl.class);
	
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo returninfo = new BankReturnInfo();
		String ver = "1.1";
		String merId = PaymentConstant.getInstance().getProperty("LAKALA_MER_ID");
		String macType = "MD5";
		String bill_num = info.getPaymentTradeNo();
		String refound_amount = PriceUtil.moneyConvertStr(info.getRefundAmount());
		String partner_bill_no = SerialUtil.generate15ByteSerial();
		String refound_desc = "";
		String sign = generateSign(bill_num, macType, merId, partner_bill_no, refound_amount, refound_desc, ver);
		Map<String, String> sParams = new HashMap<String, String>();
        sParams.put("ver", ver);
        sParams.put("merId", merId);
        sParams.put("macType", macType);
        sParams.put("bill_num", bill_num);
        sParams.put("refound_amount", refound_amount);
	    sParams.put("partner_bill_no", partner_bill_no);
	    sParams.put("refound_desc", refound_desc);
	    sParams.put("sign", sign);
		
	    Map<String, String> rpsParams = new HashMap<String, String>();
    	String reqUrl = PaymentConstant.getInstance().getProperty("LAKALA_REFUND_GATEWAY");
    	String result = HttpsUtil.requestPostForm(reqUrl, sParams);
    	LOG.info("resp: " + result);
    	String[] keyValues = result.split("&");
    	for (String str : keyValues) {
			String[] pair = str.split("=");
			rpsParams.put(pair[0], pair[1]);
		}
	    String ret_code = rpsParams.get("ret_code");
	    boolean isSucc = ret_code != null && "000".equals(ret_code);
	    returninfo.setCode(ret_code);
	    returninfo.setCodeInfo(rpsParams.get("ret_msg"));
	    returninfo.setSerial(rpsParams.get("partner_bill_no"));
	    if(isSucc){
	    	returninfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());
	    } else{
	    	returninfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
	    }
	    return returninfo;
	}

	
	/**
	 * 生成签名
	 * @param bill_num
	 * @param macType
	 * @param merId
	 * @param partner_bill_no
	 * @param refound_amount
	 * @param refound_desc
	 * @param ver
	 * @return
	 */
	private String generateSign(String bill_num, String macType,
			String merId, String partner_bill_no, String refound_amount,
			String refound_desc, String ver) {
		final StringBuilder data = new StringBuilder("bill_num=")
				.append(bill_num)
				.append("&").append("macType=").append(macType)
				.append("&").append("merId=").append(merId)
				.append("&").append("partner_bill_no=").append(partner_bill_no)
				.append("&").append("refound_amount=").append(refound_amount)
				.append("&").append("refound_desc=").append(refound_desc)
				.append("&").append("ver=").append(ver);
		return UtilityTool.messageEncrypt(
				data.toString() + PaymentConstant.getInstance().getProperty("LAKALA_MACKEY"),
				"MD5","UTF-8");
	}
}
