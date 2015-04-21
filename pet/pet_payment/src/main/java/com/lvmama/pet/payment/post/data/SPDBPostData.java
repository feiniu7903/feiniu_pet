package com.lvmama.pet.payment.post.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.SPDBUtil;

/**
 * 上海浦东发展银行支付提交数据.
 * 
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 张振华
 * @see com.lvmama.pet.vo.PaymentConstant
 */
public class SPDBPostData implements PostData,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1343333664963673514L;
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(SPDBPostData.class);
	/**
	 * 交易缩写(交易类型)
	 */
	private String transName;
	/**
	 * 交易缩写(交易类型)
	 */
	private String tranAbbr;
	/**
	 * 企业客户号
	 */
	private String masterID;
	/**
	 * 商户日期时间
	 */
	private String mercDtTm;
	/**
	 * 订单号
	 */
	private String termSsn;
	/**
	 * 原交易清算日期
	 */
	private String oSttDate;
	/**
	 * 原网关流水
	 */
	private String oAcqSsn;
	/**
	 * 交易金额
	 */
	private String tranAmt;
	/**
	 * 交易备注1
	 */
	private String remark1;
	/**
	 * 交易备注2
	 */
	private String remark2;

	/**
	 * 包装上海浦东发展银行支付的数据.
	 * 
	 * @param ordOrder
	 *            ordPayment 参数ordOrder 参数 ordPayment
	 * @return CMBPostData 上海浦东发展银行数据对象.
	 */
	public SPDBPostData(PayPayment payment) {
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddhhMMss");
		this.setTransName("IPER");
		this.setTranAbbr("IPER");
		this.setTermSsn(payment.getPaymentTradeNo());
		this.setTranAmt(String.valueOf(PriceUtil.convertToYuan(payment.getAmount())));
		this.setMercDtTm(dateFm.format(payment.getCreateTime()));
		if (LOG.isInfoEnabled()) {
			LOG.info(this.getTermSsn());
			LOG.info(this.getTranAmt());
			LOG.info(this.getMercUrl());
			LOG.info(this.getMercDtTm());
		}
	}
	

	/**
	 * 获取回调地址.
	 * 
	 * @return 回调地址.
	 */
	public String getMercUrl() {
		return PaymentConstant.getInstance().getProperty("SPDB_CALLBACK_URI");
	}

	/**
	 * 获取支付日期
	 * 
	 * @return 支付日期yyyyMMdd.
	 */
	public String getMercDtTm() {
		return mercDtTm;
	}

	/**
	 * 设置支付日期.
	 * 
	 * @param payDate
	 *            支付日期yyyyMMdd.
	 */
	public void setMercDtTm(String mercDtTm) {
		this.mercDtTm = mercDtTm;
	}

	/**
	 * 签名是否正确.
	 */
	@Override
	public String signature() {
		return this.getBankSignature();
	}
	
	/**
	 * 获得签名
	 * 
	 * @return
	 */
	public String getBankSignature() {
		return SPDBUtil.getSignature(this.getPlain());
	}
	
	public String getPlain() {
		StringBuffer bs = new StringBuffer();
			bs.append("TranAbbr="+this.getTranAbbr());
			bs.append("|MercDtTm="+this.getMercDtTm());
			bs.append("|TermSsn="+this.getTermSsn());
			bs.append("|OsttDate=|OacqSsn=");
			bs.append("|MercCode="+this.getMercCode());
			bs.append("|TermCode="+this.getTermCode());
			bs.append("|TranAmt="+this.getTranAmt());
			bs.append("|Remark1="+this.getRemark1());
			bs.append("|Remark2="+this.getRemark2());
			bs.append("|MercUrl="+this.getMercUrl());
			return bs.toString();
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
	 * @return the masterID
	 */
	public String getMasterID() {
		return masterID;
	}

	/**
	 * @param masterID
	 *            the masterID to set
	 */
	public void setMasterID(String masterID) {
		this.masterID = masterID;
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
	 * @return the oSttDate
	 */
	public String getoSttDate() {
		return oSttDate;
	}

	/**
	 * @param oSttDate
	 *            the oSttDate to set
	 */
	public void setoSttDate(String oSttDate) {
		this.oSttDate = oSttDate;
	}

	/**
	 * @return the oAcqSsn
	 */
	public String getoAcqSsn() {
		return oAcqSsn;
	}

	/**
	 * @param oAcqSsn
	 *            the oAcqSsn to set
	 */
	public void setoAcqSsn(String oAcqSsn) {
		this.oAcqSsn = oAcqSsn;
	}

	/**
	 * @return the mercCode
	 */
	public String getMercCode() {
		return PaymentConstant.getInstance().getProperty("SPDB_MERCCODE");
	}

	/**
	 * @return the termCode
	 */
	public String getTermCode() {
		return PaymentConstant.getInstance().getProperty("SPDB_TERMCODE");
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
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}

	/**
	 * @param remark1
	 *            the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}

	/**
	 * @param remark2
	 *            the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取对账流水号.
	 * 
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getTermSsn();
	}

	/**
	 * @return the transName
	 */
	public String getTransName() {
		return transName;
	}

	/**
	 * @param transName
	 *            the transName to set
	 */
	public void setTransName(String transName) {
		this.transName = transName;
	}

}
