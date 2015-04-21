package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.SPDBUtil;
import com.lvmama.pet.vo.PaymentErrorData;

/**
 * 上海浦东发展银行回调数据.
 * 
 * <pre>
 * * 详情请参考接口文档
 * </pre>
 * 
 * @author 张振华
 * @date 2013/003/27
 * @see com.lvmama.common.vo.Constant
 */
public class SPDBCallbackData implements CallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(SPDBCallbackData.class);
	/**
	 * 交易名称
	 */
	private String transName;
	/**
	 * 交易请求明文
	 */
	private String plain;
	/**
	 * 交易缩写
	 */
	private String tranAbbr;
	/**
	 * 网关流水
	 */
	private String acqSsn;
	/**
	 * 商户日期时间
	 */
	private String mercDtTm;
	/**
	 * 订单号
	 */
	private String termSsn;
	/**
	 * 响应码
	 */
	private String respCode;
	/**
	 * 终端号
	 */
	private String termCode;
	/**
	 * 商户号
	 */
	private String mercCode;
	/**
	 * 交易金额
	 */
	private String tranAmt;
	/**
	 * 清算日期
	 */
	private String settDate;

	/**
	 * 错误code
	 * 
	 */
	private String errorCode;
	/**
	 * 错误信息
	 * 
	 */
	private String errorMsg;
	

	private Map<String, String> paraMap;	
	
	/**
	 * 返回的签名
	 * 
	 */
	private String signature;

	/**
	 * 包装上海浦东发银行支付返回来的数据.
	 * 
	 * @param map
	 *            参数map
	 * @return CMBCallbackData 上海浦东发银行数据对象
	 */
	public SPDBCallbackData(final Map<String, String> mapParam) {
		if (mapParam!=null) {
			paraMap=mapParam;
			this.setTransName(paraMap.get("transName"));
			this.setPlain(paraMap.get("Plain"));
			this.setSignature(paraMap.get("Signature"));
			this.setErrorCode(paraMap.get("ErrorCode"));
			this.setErrorMsg(paraMap.get("ErrorMsg"));
			Map<String, String> map = SPDBUtil.getPlainMap(this.getPlain());
			if(map!=null) {
				this.setTranAbbr(map.get("TranAbbr"));
				this.setAcqSsn(map.get("AcqSsn"));
				this.setMercDtTm(map.get("MercDtTm"));
				this.setTermSsn(map.get("TermSsn"));
				this.setRespCode(map.get("RespCode"));
				this.setTermCode(map.get("TermCode"));
				this.setMercCode(map.get("MercCode"));
				this.setTranAmt(map.get("TranAmt"));
				this.setSettDate(map.get("SettDate"));
				if (LOG.isInfoEnabled()) {
					LOG.info("SPDBCallback CallbackData:"+paraMap.toString());
				}
			}
		}
	}

	/**
	 * 判断返回签名是否正确.
	 * 
	 * @return true 招商银行没有签名，故设置为true;
	 */
	@Override
	public boolean checkSignature() {
		return SPDBUtil.checkSignature(this.getSignature(),this.getPlain());
	}
	
	/**
	 * 支付返回信息
	 * 
	 * @return 支付返回信息.
	 */
	@Override
	public String getCallbackInfo() {
		if(StringUtils.isNotEmpty(this.getRespCode())) {
			return PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.SPDB.name(), this.getRespCode());
		} else {
			return this.getErrorCode()+":"+this.getErrorMsg();
		}
	}

	/**
	 * 网关支付号
	 * 
	 * @return 网关支付号.
	 */
	@Override
	public String getGatewayTradeNo() {
		return this.acqSsn;
	}

	/**
	 * 返回信息.
	 * 
	 * @return 返回信息.
	 */
	@Override
	public String getMessage() {
		return this.getCallbackInfo();
	}

	/**
	 * 获取支付金额.
	 * 
	 * @return 支付金额，以分为单位.
	 */
	@Override
	public long getPaymentAmount() {
		return Long.valueOf(this.tranAmt) * 100;
	}

	/**
	 * 获取支付网关名称.
	 * 
	 * @return 支付网关名称.
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.SPDB.name();
	}

	/**
	 * 是否支付成功.
	 * 
	 * @return 00代表支付成功
	 */
	@Override
	public boolean isSuccess() {
		return "00".equals(this.respCode)&&this.checkSignature();
	}

	@Override
	public String getRefundSerial() {
		return null;
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}

	@Override
	public String getPaymentTradeNo() {
		return this.termSsn;
	}

	/**
	 * @return the tranAbbr
	 */
	public String getTranAbbr() {
		return tranAbbr;
	}

	/**
	 * @param tranAbbr
	 *            the tranAbbr to set
	 */
	public void setTranAbbr(String tranAbbr) {
		this.tranAbbr = tranAbbr;
	}

	/**
	 * @return the acqSsn
	 */
	public String getAcqSsn() {
		return acqSsn;
	}

	/**
	 * @param acqSsn
	 *            the acqSsn to set
	 */
	public void setAcqSsn(String acqSsn) {
		this.acqSsn = acqSsn;
	}

	/**
	 * @return the mercDtTm
	 */
	public String getMercDtTm() {
		return mercDtTm;
	}

	/**
	 * @param mercDtTm
	 *            the mercDtTm to set
	 */
	public void setMercDtTm(String mercDtTm) {
		this.mercDtTm = mercDtTm;
	}

	/**
	 * @return the termSsn
	 */
	public String getTermSsn() {
		return termSsn;
	}

	/**
	 * @param termSsn
	 *            the termSsn to set
	 */
	public void setTermSsn(String termSsn) {
		this.termSsn = termSsn;
	}

	/**
	 * @return the respCode
	 */
	public String getRespCode() {
		return respCode;
	}

	/**
	 * @param respCode
	 *            the respCode to set
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	/**
	 * @return the termCode
	 */
	public String getTermCode() {
		return termCode;
	}

	/**
	 * @param termCode
	 *            the termCode to set
	 */
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	/**
	 * @return the mercCode
	 */
	public String getMercCode() {
		return mercCode;
	}

	/**
	 * @param mercCode
	 *            the mercCode to set
	 */
	public void setMercCode(String mercCode) {
		this.mercCode = mercCode;
	}

	/**
	 * @return the tranAmt
	 */
	public String getTranAmt() {
		return tranAmt;
	}

	/**
	 * @param tranAmt
	 *            the tranAmt to set
	 */
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}

	/**
	 * @return the settDate
	 */
	public String getSettDate() {
		return settDate;
	}

	/**
	 * @param settDate
	 *            the settDate to set
	 */
	public void setSettDate(String settDate) {
		this.settDate = settDate;
	}

	/**
	 * @return the paraMap
	 */
	public Map<String, String> getParaMap() {
		return paraMap;
	}

	/**
	 * @param paraMap the paraMap to set
	 */
	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @return the transName
	 */
	public String getTransName() {
		return transName;
	}

	/**
	 * @param transName the transName to set
	 */
	public void setTransName(String transName) {
		this.transName = transName;
	}

	/**
	 * @return the plain
	 */
	public String getPlain() {
		return plain;
	}

	/**
	 * @param plain the plain to set
	 */
	public void setPlain(String plain) {
		this.plain = plain;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	

}
