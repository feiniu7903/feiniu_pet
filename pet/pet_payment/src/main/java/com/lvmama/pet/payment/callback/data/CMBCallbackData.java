package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;

/**
 * 招商银行回调数据.
 * 
 * <pre>
 * * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 2011/06/27
 * @since Super 一期
 * @see com.lvmama.common.vo.Constant
 */
public class CMBCallbackData implements CallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(CMBCallbackData.class);
	/**
	 * 是否成功.
	 */
	private String Succeed;
	/**
	 * 商户号.
	 */
	private String coNo;
	/**
	 * 返回的定单流水号，10位数字，由商户系统生成.
	 */
	private String billNO;
	/**
	 * 支付日期.
	 */
	private String payDate;

	/**
	 * 金额， 以元为单位.
	 */
	private String amount;
	/**
	 * 支付回馈信息，4位分行号＋6位商户号＋8位银行接受交易的日期＋20位银行流水号.
	 */
	private String meg;
	/**
	 * 支付流水号.
	 */
	private String serial;

	/**
	 * 包装招商银行支付返回来的数据.
	 * 
	 * @param map
	 *            参数map
	 * @return CMBCallbackData 招商银行数据对象
	 */
	public CMBCallbackData(final Map<String, String> map) {
		this.setSucceed(map.get("Succeed"));
		this.setCoNo(map.get("CoNo"));
		this.setBillNO(map.get("BillNo"));
		this.setAmount(map.get("Amount"));
		this.setPayDate(map.get("Date"));
		this.setMeg(map.get("Msg"));
		LOG.info("map:"+map.toString());
		if (LOG.isInfoEnabled()) {
			LOG.info(this.getSucceed());
			LOG.info(this.getCoNo());
			LOG.info(this.getBillNO());
			LOG.info(this.getAmount());
			LOG.info(this.getPayDate());
			LOG.info(this.getMeg());
		}
	}

	/**
	 * 获取支付结果.
	 * 
	 * @return Y 成功 N 失败.
	 */
	public String getSucceed() {
		return Succeed;
	}

	/**
	 * 设置支付结果
	 * 
	 * @param succeed
	 *            Y 成功 N 失败.
	 */
	public void setSucceed(String succeed) {
		Succeed = succeed;
	}

	/**
	 * 获取商户号.
	 * 
	 * @return 商户号.
	 */
	public String getCoNo() {
		return coNo;
	}

	/**
	 * 设置商户号.
	 * 
	 * @param coNo
	 *            商户号.
	 */
	public void setCoNo(String coNo) {
		this.coNo = coNo;
	}

	/**
	 * 获取定单流水号.
	 * 
	 * @return 定单流水号
	 */
	public String getBillNO() {
		return billNO;
	}

	/**
	 * 设置定单流水号.
	 * 
	 * @param billNO
	 *            定单流水号.
	 */
	public void setBillNO(String billNO) {
		this.billNO = billNO;
	}

	/**
	 * 获取支付日期.
	 * 
	 * @return 支付日期.
	 */
	public String getPayDate() {
		return payDate;
	}

	/**
	 *  设置支付日期.
	 * 
	 * @param payDate
	 *            支付日期.
	 */
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	/**
	 * 获取支付金额.
	 * 
	 * @return 支付金额.
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 设置支付金额.
	 * 
	 * @param amount
	 *            支付金额.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 获取支付回馈信息.
	 * 
	 * @return 支付回馈信息.
	 */
	public String getMeg() {
		return meg;
	}

	/**
	 * 定单流水号.
	 * 
	 * @param meg
	 *            支付回馈信息.
	 */
	public void setMeg(String meg) {
		this.meg = meg;
	}

	/**
	 * 判断返回签名是否正确.
	 * 
	 * @return true 招商银行没有签名，故设置为true;
	 */
	@Override
	public boolean checkSignature() {
		return true;
	}

	/**
	 * 支付返回信息
	 * 
	 * @return 支付返回信息.
	 */
	@Override
	public String getCallbackInfo() {
		return this.meg;
	}

	/**
	 * 网关支付号
	 * 
	 * @return 网关支付号.
	 */
	@Override
	public String getGatewayTradeNo() {
		if(StringUtils.isNotBlank(meg) && meg.length()>=20){
			return this.meg.substring(this.meg.length() - 20);	
		}
		return "";
	}
	
	/**
	 * 返回信息.
	 * 
	 * @return 返回信息.
	 */
	@Override
	public String getMessage() {
		return null;
	}

	/**
	 * 获取支付金额.
	 * 
	 * @return 支付金额，以分为单位.
	 */
	@Override
	public long getPaymentAmount() {
		return Long.valueOf(this.amount) * 100;
	}

	/**
	 * 获取支付网关名称.
	 * 
	 * @return 支付网关名称.
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.CMB.name();
	}

	/**
	 * 是否支付成功.
	 * 
	 * @return Y代表支付成功，N代表支付不成功.
	 */
	@Override
	public boolean isSuccess() {
		return this.Succeed.contains("Y");
	}

	/**
	 * 设置订单流水号.
	 * 
	 * @return 订单流水号.
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	@Override
	public String getPaymentTradeNo() {
		return this.billNO;
	}

	@Override
	public String getRefundSerial() {
		return null;
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}
}
