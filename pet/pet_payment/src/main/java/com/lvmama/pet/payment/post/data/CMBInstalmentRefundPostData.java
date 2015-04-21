package com.lvmama.pet.payment.post.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.CMBInstalmentTool;
import com.lvmama.pet.vo.CMBInstalmentLoginRequest;
import com.lvmama.pet.vo.CMBInstalmentLoginResponse;
import com.lvmama.pet.vo.CMBInstalmentRefundRequest;
//import com.lvmama.payment.utils.CMBInstalmentTool;

/**
 * 招行分期支付退款请求数据对象.
 * @author fengyu
 * @see com.bocnet.common.security.PKCS7Tool
 * @see com.lvmama.common.ord.po.OrdOrder
 * @see com.lvmama.common.ord.po.PayPayment
 * @see com.lvmama.pet.vo.PaymentConstant
 */
public class CMBInstalmentRefundPostData implements PostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(CMBInstalmentRefundPostData.class);

	private CMBInstalmentRefundRequest cmbInstalmentRefundRequest;

	public static String CMB_REFUND_CONNECTION;

	/**
	 * 退款请求地址.
	 */
	private final String refundUrl = PaymentConstant.getInstance().getProperty("CMB_INSTALMENT_DIRECTREQUEST_URL");

	/**
	 * 商户号.
	 */
	private final String cono = PaymentConstant.getInstance().getProperty("CMB_INSTALMENT_MCHMCHNBR");

	/**
	 * 登录操作员.
	 */
	private final String operatorID = PaymentConstant.getInstance().getProperty("CMB_INSTALMENT_OPERATOR_ID");

	/**
	 * 登录密码.
	 */
	private final String password = PaymentConstant.getInstance().getProperty("CMB_INSTALMENT_PASSWORD");

	/**
	 * 商务密钥.
	 */
	private final String requestPassword = PaymentConstant.getInstance().getProperty("CMB_INSTALMENT_REQUEST_PASSWORD");

//	private PayPayment payPayment;
//	private OrdRefundment ordRefundment;

	private RefundmentToBankInfo refundmentToBankInfo;

	/**
	 * 构造函数.
	 * @param order 订单.
	 * @param payPayment 支付记录.
	 * @param ordRefundment 退款单.
	 */
	public CMBInstalmentRefundPostData(RefundmentToBankInfo refundmentToBankInfo) {
		this.refundmentToBankInfo = refundmentToBankInfo;
	}

	/**
	 * 初始化退款的XML字符串.
	 * @return XML字符串.
	 */
	public String initRefundXML(){
		cmbInstalmentRefundRequest = new CMBInstalmentRefundRequest();
		if (CMB_REFUND_CONNECTION == null) {
			login();
		}

		//Body
		CMBInstalmentRefundRequest.Body body = new CMBInstalmentRefundRequest.Body();

		String crdBllNbr = this.refundmentToBankInfo.getGatewayTradeNo();
		String crdBllRef = this.refundmentToBankInfo.getRefundSerial();
		String rfdAmt = String.valueOf(PriceUtil.convertToYuan(this.refundmentToBankInfo.getRefundAmount()));

		body.setCrdBllNbr(crdBllNbr);
		body.setCrdBllRef(crdBllRef);
		body.setTrxGdsFlg("N");
		body.setTrxRfdAmt(rfdAmt);
		body.setTrxGdsNbr("12345678");
		body.setTrxGdsRfdCnt(1f);

		CMBInstalmentRefundRequest.Head head = new CMBInstalmentRefundRequest.Head(CMB_REFUND_CONNECTION, CMBInstalmentRefundRequest.COMMAND, "");
		cmbInstalmentRefundRequest.setHead(head);
		cmbInstalmentRefundRequest.setBody(body);
		CMBInstalmentTool.generateSignature(cmbInstalmentRefundRequest, requestPassword);

		return CMBInstalmentTool.toXML(cmbInstalmentRefundRequest);
	}

	public void login() {
		CMBInstalmentLoginRequest.Head head = new CMBInstalmentLoginRequest.Head(CMBInstalmentLoginRequest.COMMAND);
		CMBInstalmentLoginRequest.Body body = new CMBInstalmentLoginRequest.Body();
		body.setCono(cono);
		body.setOperatorID(operatorID);
		body.setPassword(password);

		CMBInstalmentLoginRequest loginRequest = new CMBInstalmentLoginRequest();
		loginRequest.setHead(head);
		loginRequest.setBody(body);

		String xmlRequest = CMBInstalmentTool.toXML(loginRequest);

		String xmlResponse = CMBInstalmentTool.sendURLRequest(refundUrl, xmlRequest);
		Map<String, Class> alias = new HashMap<String, Class>();
		alias.put(CMBInstalmentLoginResponse.ROOT_ELEMENT, CMBInstalmentLoginResponse.class);
		CMBInstalmentLoginResponse loginResponse = (CMBInstalmentLoginResponse) CMBInstalmentTool.fromXML(xmlResponse, alias);

		if (loginResponse != null) {
			CMB_REFUND_CONNECTION = loginResponse.getBody().getConnection();
		}
	}

	@Override
	public String getPaymentTradeNo() {
		return null;
	}

	@Override
	public String signature() {
		// TODO Auto-generated method stub
		return null;
	}
}