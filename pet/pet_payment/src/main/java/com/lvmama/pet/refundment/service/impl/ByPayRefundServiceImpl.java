package com.lvmama.pet.refundment.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.Dom4jUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.pay.ByPayUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;

public class ByPayRefundServiceImpl implements BankRefundmentService {

	/**
	 * LOG.
	 */
	private static final Logger log = Logger.getLogger(ByPayRefundServiceImpl.class);
	
	private String notify_url = PaymentConstant.getInstance().getProperty("BYPAY_TEL_NOTIFY_URL");

	private String return_url = PaymentConstant.getInstance().getProperty("BYPAY_TEL_RETURN_URL");

	private String merchantId = PaymentConstant.getInstance().getProperty("BYPAY_TEL_MERID");

	/**
	 * 百付退款逻辑.
	 * @param info
	 * @param isKey
	 * @return
	 */
	public BankReturnInfo refund(final RefundmentToBankInfo info) {
		return createXml(info);
	}
	
	/**
	 * 百付消费退款时候创建支付的XML文件.
	 * 
	 * @param orderId
	 * @param cupsQid
	 * @param amount
	 *            退款金额
	 * @return
	 */
	public BankReturnInfo createXml(final RefundmentToBankInfo info) {
		String serial = SerialUtil.generate24ByteSerialAttaObjectId(info.getPaymentId());
		String dateString = DateUtil.getFormatDate(new Date(),"yyyyMMddHHmmss");
		String refundType=PaymentConstant.getInstance().getProperty("BYPAY_TEL_TRANSTYPE_REFUND");
		String sendMess = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			    "<upbp application=\""+refundType+"\" version=\"1.0.0\" sendTime=\""+dateString+"\"   sendSeqId=\""+dateString+"\" >" +
			    "<merchantId>"+merchantId+"</merchantId>" +
				"<merchantOrderId>"+ serial +"</merchantOrderId>" +
				"<merchantOrderTime>"+dateString+"</merchantOrderTime>" +
				"<merchantOrderAmt>"+info.getRefundAmount()+"</merchantOrderAmt>" +
				"<merchantOrderCurrency>156</merchantOrderCurrency>" +
				"<cupsQid>"+info.getGatewayTradeNo()+"</cupsQid>"+
				"<backUrl>"+return_url+"</backUrl>" +
				"</upbp>";
		String xml = null;
		BankReturnInfo bankReturnInfo =new BankReturnInfo();
		bankReturnInfo.setSerial(serial);
		log.info("bypay refund sendMess=" + sendMess);
		try {
			xml = ByPayUtil.readContentFromPost(notify_url, sendMess);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("ByPay  conn  error, bypay refund callMess="+xml);
			if(StringUtils.isBlank(xml)){
				bankReturnInfo.setCodeInfo("百付服务器连接失败,请人工检查退款是否成功!");
				bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				return bankReturnInfo;	
			}
		}
		log.info("bypay refund callMess=" + xml);
		if (!StringUtil.isEmptyString(xml)) {
			Map<String, String> xmlString = Dom4jUtil.AnalyticXml(xml);
			bankReturnInfo.setCode(xmlString.get("respCode"));
			bankReturnInfo.setCodeInfo(xmlString.get("respDesc"));
			//百付-电话 同步成功表示 提交消费撤销或退货请求成功，并非退款成功。（退款是否成功需要靠退款异步回调的通知）
			if(PaymentConstant.BYPAY_SUCCESS_KEY.equalsIgnoreCase(xmlString.get("respCode"))){
				bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
			}
			else{
				bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			}
		}
		return bankReturnInfo;
	}
}
