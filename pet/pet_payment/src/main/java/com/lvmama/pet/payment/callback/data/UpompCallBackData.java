package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.merPlus.PlusTools;

/**
 * 移动客户端回调数据.
 * @author fengyu
 * @see  com.lvmama.comm.vo.Constant
 * @see  com.lvmama.pet.vo.PaymentConstant
 * @see  com.merPlus.PlusTools
 */
public class UpompCallBackData implements CallbackData {
	private String xml;
	private Map<String, String> dataMap = new HashMap<String, String>();
	private Logger LOG = Logger.getLogger(this.getClass());

	/**
	 * 构造方法
	 * @param request
	 */
	public UpompCallBackData(String xml) {
		this.xml=xml;
		this.dataMap = PlusTools.parseXml(xml);
		LOG.info("xml:" + xml);
	}

	/**
	 * 检查签名
	 * @return 检查结果
	 */
	@Override
	public boolean checkSignature() {
		return PlusTools.checkSign(xml, PaymentConstant.getInstance().getProperty("UPOMP_CERT_PATH"));
	}

	/**
	 * 是否成功
	 * @return 结果
	 */
	@Override
	public boolean isSuccess() {
		String respCode=dataMap.get("cupsRespCode");
		boolean result=PaymentConstant.BYPAY_SUCCESS_KEY.equalsIgnoreCase(respCode) || "00".equalsIgnoreCase(respCode);
		if(result && checkSignature()){
			return true;
		}
		return false;
	}

	@Override
	public String getMessage() {
		return null;
	}

	/**
	 * 获取支付网关交易流水号
	 * @return
	 */
	@Override
	public String getGatewayTradeNo() {
		return dataMap.get("cupsQid");
	}

	@Override
	public String getCallbackInfo() {
		return dataMap.get("cupsRespDesc");
	}

	/**
	 * 获取支付金额
	 * @return
	 */
	@Override
	public long getPaymentAmount() {
		return Long.valueOf(dataMap.get("merchantOrderAmt"));
	}

	/**
	 * 获取支付网关（渠道）.
	 * @return String.
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.UPOMP.name();
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	/**
	 * 获取发出去的交易流水号(对帐的流水).
	 * @return String.
	 */
	@Override
	public String getPaymentTradeNo() {
		return dataMap.get("merchantOrderId");
	}

	/**
	 * 获取退款的流水号.
	 * @return String.
	 */
	@Override
	public String getRefundSerial() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 银行交易时间(目前预授权在用).
	 * @return
	 */
	@Override
	public Date getCallBackTime() {
		return new Date();
	}

}
