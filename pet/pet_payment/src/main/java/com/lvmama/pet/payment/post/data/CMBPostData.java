package com.lvmama.pet.payment.post.data;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 招商银行支付提交数据.
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 2011/06/29
 * @since Super一期
 * @see com.lvmama.pet.vo.PaymentConstant
 */
public class CMBPostData implements PostData {

	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(CMBPostData.class);

	/**
	 * 定单流水号，10位数字，是截取系统产生的支付流水号的4到14位，也就是yyddHHmmss.
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
	 * 支付流水号.
	 */
	private String merchantPara;
	
	/**
	 * 包装招商银行支付的数据.
	 * 
	 * @param ordOrder ordPayment
	 *            参数ordOrder   参数 ordPayment
	 * @return CMBPostData 招商银行数据对象.
	 */
	public CMBPostData(PayPayment payment) {
		this.setBillNO(payment.getPaymentTradeNo());
		this.setAmount(String.valueOf(PriceUtil.convertToYuan(payment.getAmount())));
		/** 日期格式转换. */
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd");
		this.setPayDate(dateFm.format(payment.getCreateTime()));
		this.setMerchantPara("上海驴妈妈旅游网"); //

		if (LOG.isInfoEnabled()) {
			LOG.info(this.getBillNO());
			LOG.info(this.getAmount());
			LOG.info(this.getCallBackUrl());
			LOG.info(this.getPayDate());
		}
	}
	/**
	 *  获取商户开户的分行号.
	 *  
	 * @return 商户开户的分行号.
	 */
	public String getBranchID() {
		return PaymentConstant.getInstance().getProperty("CMB_BRANCHID");
	}

	/**
	 * 获取商户号.
	 * 
	 * @return  商户号.
	 */
	public String getCoNo() {
		return PaymentConstant.getInstance().getProperty("CMB_CONO");
	}

	/**
	 * 获取定单流水号.
	 * 
	 * @return  定单流水号.
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
	 * 获取定单金额.
	 * 
	 * @return  定单金额.
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 设置定单金额.
	 * 
	 * @param amount
	 *           定单金额.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 获取回调地址.
	 * 
	 * @return   回调地址.
	 */
	public String getCallBackUrl() {
		return PaymentConstant.getInstance().getProperty("CMB_CALLBACK_URI");
	}
	/**
	 * 获取支付日期
	 * 
	 * @return  支付日期yyyyMMdd.
	 */
	public String getPayDate() {
		return payDate;
	}
	/**
	 * 设置支付日期.
	 * 
	 * @param payDate
	 *           支付日期yyyyMMdd.
	 */
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	/**
	 * 获取流水号
	 * 
	 * @return  支付流水号.
	 */
	public String getMerchantPara() {
		return merchantPara;
	}
	/**
	 * 设置流水号.
	 * 
	 * @param merchantPara
	 *           流水号.
	 */
	public void setMerchantPara(String merchantPara) {
		this.merchantPara = merchantPara;
	}
	/**
	 * 签名是否正确.
	 * 
	 * @return true 招商银行没有签名验证;
	 */
	@Override
	public String signature() {
		return null;
	}
	/**
	 * 获取对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getBillNO();
	}

}
