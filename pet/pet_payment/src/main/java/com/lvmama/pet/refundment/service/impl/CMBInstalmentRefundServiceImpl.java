package com.lvmama.pet.refundment.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.payment.post.data.CMBInstalmentRefundPostData;
import com.lvmama.pet.refundment.data.CMBInstalmentRefundCallbackData;
import com.lvmama.pet.utils.CMBInstalmentTool;

/**
 * 招行分期退款类.
 * 
 * @author fengyu
 * @see com.lvmama.comm.pet.po.pay.BankReturnInfo
 * @see com.lvmama.comm.pet.service.pay.BankRefundmentService
 * @see com.lvmama.comm.vo.Constant
 * @see com.lvmama.comm.vo.PaymentConstant
 * @see com.lvmama.comm.vo.RefundmentToBankInfo
 * @see com.lvmama.pet.payment.post.data.CMBInstalmentRefundPostData
 * @see com.lvmama.pet.refundment.data.CMBInstalmentRefundCallbackData
 * @see com.lvmama.pet.utils.CMBInstalmentTool
 */
public class CMBInstalmentRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory
			.getLog(CMBInstalmentRefundServiceImpl.class);
	/**
	 * 招行分期退款请求对象.
	 */
	private CMBInstalmentRefundPostData cmbInstalmentRefundData;
	/**
	 * 退款请求的XML.
	 */
	private String xmlRefundRequest;

	/**
	 * 退款请求地址.
	 */
	private final String refundUrl = PaymentConstant.getInstance().getProperty(
			"CMB_INSTALMENT_DIRECTREQUEST_URL");

	/**
	 * 无参构造函数.
	 */
	public CMBInstalmentRefundServiceImpl() {

	}

	/**
	 * 构造函数.
	 */
	public CMBInstalmentRefundServiceImpl(String xmlRequest) {
		this.xmlRefundRequest = xmlRequest;
	}

//	/**
//	 * 构造函数.
//	 * 
//	 * @param order
//	 *            订单.
//	 * @param payPayment
//	 *            支付记录.
//	 * @param ordRefundment
//	 *            退款单.
//	 */
//	public CMBInstalmentRefundServiceImpl(
//			RefundmentToBankInfo refundmentToBankInfo) {
//		cmbInstalmentRefundData = new CMBInstalmentRefundPostData(
//				refundmentToBankInfo);
//		xmlRefundRequest = cmbInstalmentRefundData.initRefundXML();
//		LOG.debug("initRefund xmlRefundRequest:\n" + xmlRefundRequest);
//	}

	/**
	 * 初始化退款数据.
	 * 
	 * @param order
	 *            订单.
	 * @param payPayment
	 *            支付记录.
	 * @param ordRefundment
	 *            退款单.
	 */
	public void initRefund(RefundmentToBankInfo refundmentToBankInfo) {
		cmbInstalmentRefundData = new CMBInstalmentRefundPostData(
				refundmentToBankInfo);
		xmlRefundRequest = cmbInstalmentRefundData.initRefundXML();
		LOG.debug("initRefund xmlRefundRequest:\n" + xmlRefundRequest);
	}

	/**
	 * 退款.
	 * 
	 * @return 是否退款成功.
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo refundmentToBankInfo) {
		BankReturnInfo bankReturnInfo = new BankReturnInfo();
		boolean isSuccess = false;
		try {
			initRefund(refundmentToBankInfo);
			CMBInstalmentRefundCallbackData cmbInstalmentRefundResult;
			String xmlResponse = CMBInstalmentTool.sendURLRequest(refundUrl,xmlRefundRequest);
			if (StringUtils.isNotBlank(xmlResponse)) {
				cmbInstalmentRefundResult = CMBInstalmentRefundCallbackData.initCMBInstalmentRefundResult(xmlResponse);
				isSuccess = cmbInstalmentRefundResult.isRefundSuccess();
				//如果退款请求不成功并且登录已经失效 默认再进行一次退款请求操作
				if (!isSuccess && Constant.CMB_INSTALMENT_DIRECT_ERRCODE.R.name().equals(cmbInstalmentRefundResult.getCode())) {
					cmbInstalmentRefundData.login();
					xmlRefundRequest = cmbInstalmentRefundData.initRefundXML();
					xmlResponse = CMBInstalmentTool.sendURLRequest(refundUrl, xmlRefundRequest);
					cmbInstalmentRefundResult = CMBInstalmentRefundCallbackData.initCMBInstalmentRefundResult(xmlResponse);
					isSuccess = cmbInstalmentRefundResult.isRefundSuccess();
				}
				if(isSuccess){
					bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
				}
				else{
					bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				}
				bankReturnInfo.setCode(cmbInstalmentRefundResult.getCode());
				bankReturnInfo.setCodeInfo(cmbInstalmentRefundResult.getErrMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankReturnInfo;
	}
}
