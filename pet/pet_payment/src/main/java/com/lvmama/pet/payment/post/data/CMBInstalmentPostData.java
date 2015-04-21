package com.lvmama.pet.payment.post.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.CMBInstalmentTool;
import com.lvmama.pet.vo.CDPRequest_Pay;

/**
 * 招商银行分期付款交易表单.
 * @author fengyu
 * @see  com.lvmama.comm.utils.PriceUtil
 * @see  com.lvmama.pet.utils.CMBInstalmentTool
 * @see  com.lvmama.pet.vo.CDPRequest_Pay
 * @see  com.lvmama.pet.vo.PaymentConstant
 */
public class CMBInstalmentPostData implements PostData {

	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory
			.getLog(CMBInstalmentPostData.class);

	private CDPRequest_Pay request_Pay = new CDPRequest_Pay();

	/**
	 * Request
	 */
	private String requestXML;

	/**
	 * 商户接收订单支付结果服务器通知的URL.
	 */
	private String notifyurl = PaymentConstant.getInstance().getProperty(
			"CMB_INSTALMENT_NOTIFY_URL");

	/**
	 * 商户号.
	 */
	private final String mchmchnbr = PaymentConstant.getInstance().getProperty(
			"CMB_INSTALMENT_MCHMCHNBR");
	/**
	 * 版本号.
	 */
	private final String version = PaymentConstant.getInstance().getProperty(
			"CMB_INSTALMENT_VERSION");

	/**
	 * 测试版本标志.
	 */
	private final String testFlag = PaymentConstant.getInstance().getProperty(
			"CMB_INSTALMENT_REQUEST_TESTFLAG");

	/**
	 * 币种.
	 */
	private final String trxtrxccy = PaymentConstant.getInstance().getProperty(
			"CMB_INSTALMENT_TRXTRXCCY");
	/**
	 * 付款页面隐藏分期相关文字.
	 */
	private final String uihideped = PaymentConstant.getInstance().getProperty(
			"CMB_INSTALMENT_REQUEST_UIHIDEPED");
	/**
	 * 商户提供的通知URL.
	 */
	private final String notifyURL = PaymentConstant.getInstance().getProperty(
			"CMB_INSTALMENT_NOTIFY_URL");
	/**
	 * 商户提供的通知参数.
	 */
	private final String notifyParamName = PaymentConstant.getInstance()
			.getProperty("CMB_INSTALMENT_NOTIFY_PARAM_NAME");
	/**
	 * 商户密钥.
	 */
	private final String requestPassword = PaymentConstant.getInstance()
			.getProperty("CMB_INSTALMENT_REQUEST_PASSWORD");

	/**
	 * 订单号.
	 */
	private String orderid;

	/**
	 * 订单日期：格式yyyymmdd.
	 */
	private String date;
	/**
	 * 订单发生时间：格式hhmiss.
	 */
	private String timestamp;

	/**
	 * 构造方法
	 * @param orderId
	 * @param bizType
	 * @param amount
	 * @param paramMap
	 */
	public CMBInstalmentPostData(PayPayment payment, String instalmentNum) {
		LOG.info("Creating CMBInstalmentPostData ...... ");
		orderid = payment.getPaymentTradeNo();
		date = this.formatDate(payment.getCreateTime());
		timestamp = this.formatTimestamp(payment.getCreateTime());
		
		CDPRequest_Pay.Head head = new CDPRequest_Pay.Head(version, testFlag);
		request_Pay.setHead(head);

		CDPRequest_Pay.Body body = new CDPRequest_Pay.Body();
		body.setMchMchNbr(mchmchnbr);
		body.setMchBllNbr(orderid);
		body.setMchBllDat(date);
		body.setMchBllTim(timestamp);
		body.setTrxTrxCcy(trxtrxccy);
		body.setTrxBllAmt(PriceUtil.convertToYuan(payment.getAmount()));
		body.setTrxPedCnt(Integer.valueOf(instalmentNum));
		body.setUIHidePed(uihideped);

		
		body.setMchUsrIdn(orderid);

		body.setMchNtfUrl(notifyURL);
		body.setMchNtfPam(notifyParamName);
		body.setTrxGdsGrp("12345678");
		body.setTrxGdsNam("test");
		body.setTrxPstAdr("test");
		body.setTrxPstTel("test");
		
		request_Pay.setHead(head);
		request_Pay.setBody(body);
		
		//进行数字签名
		request_Pay.setSignature(signature());
		this.requestXML = CMBInstalmentTool.toXML(request_Pay);

		
		
		LOG.info("Finished creating CMBInstalmentPostData ...... ");
	}

	/**
	 * 进行数字签名
	 * @return 数字签名
	 */
	@Override
	public String signature() {
		LOG.info("requestPassword=" + requestPassword);
		String signature = CMBInstalmentTool.generateSignature(request_Pay,requestPassword);
		LOG.info("signature=" + signature);
		return signature;
	}

	/**
	 * 格式化订单日期.
	 * @param creatTime
	 * @return 订单日期.
	 */
	private String formatDate(Date creatTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(creatTime);
	}

	/**
	 * 格式化订单发生时间.
	 * @param creatTime
	 * @return 订单发生时间.
	 */
	private String formatTimestamp(Date creatTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("kkmmss");
		return sdf.format(creatTime);
	}

	/**
	 * 获取商户接收订单支付结果服务器通知的URL.
	 * @return 商户接收订单支付结果服务器通知的URL.
	 */
	public String getNotifyurl() {
		return notifyurl;
	}

	/**
	 * 获取订单号.
	 * @return 订单号
	 */
	public String getOrderid() {
		return orderid;
	}

	public String getRequestXML() {
		return requestXML;
	}

	public void setRequestXML(String requestXML) {
		this.requestXML = requestXML;
	}

	/**
	 * 获取支付对账流水号.
	 * @return
	 */	
	@Override
	public String getPaymentTradeNo() {
		return this.getOrderid();
	}

}
