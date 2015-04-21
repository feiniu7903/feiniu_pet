package com.lvmama.pet.payment.post.data;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocnet.common.security.PKCS7Tool;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.BocUtil;

/**
 * 中国银行(直连)网上支付.
 * @author sunruyi
 */
public class BOCPostData implements PostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(BOCPostData.class);
	/**
	 * 证书库路径.
	 */
	private final String keyStorePath = PaymentConstant.getInstance().getProperty("BOC_KEY_STORE_PATH");
	/**
	 * 证书库口令.
	 */
	private final String keyStorePassword = PaymentConstant.getInstance().getProperty("BOC_KEY_STORE_PASSWORD");
	/**
	 * 签名私钥口令，一般与证书库口令相同.
	 */
	private final String keyPassword = PaymentConstant.getInstance().getProperty("BOC_KEY_PASSWORD");
	/**
	 * 商户号.
	 */
	private final String merchantNo = PaymentConstant.getInstance().getProperty("BOC_MERCHANT_NO");
	/**
	 * 支付类型.
	 */
	private final String payType = PaymentConstant.getInstance().getProperty("BOC_PAY_TYPE");
	/**
	 * 订单币种.
	 */
	private final String curCode = PaymentConstant.getInstance().getProperty("BOC_CUR_CODE");
	/**
	 * 用于接收中行通知的URL.
	 */
	private final String orderUrl = PaymentConstant.getInstance().getProperty("BOC_ORDER_URL");
	/**
	 * 订单号.
	 */
	private String orderNo;
	/**
	 * 订单金额.
	 */
	private String orderAmount;
	/**
	 * 订单时间.
	 */
	private String orderTime;
	/**
	 * 订单说明.
	 */
	private String orderNote;
	/**
	 * 商户签名数据.
	 */
	private String signData;
	/**
	 * 包装中国银行支付的数据.
	 * @param ordOrder ordPayment
	 *            参数ordOrder   参数 ordPayment
	 * @return CMBPostData 中国银行数据对象.
	 */
	public BOCPostData(PayPayment payment){
		this.orderNo = payment.getPaymentTradeNo();
		orderAmount = BocUtil.formatOrderAmount(payment.getAmount());
		orderTime = BocUtil.formatOrderTime(payment.getCreateTime());
		orderNote = "在驴妈妈旅游网订购的产品";
		
		signData = this.signature();
		
		LOG.info("keyStorePath=" + keyStorePath);
		LOG.info("keyStorePassword=" + keyStorePassword);
		LOG.info("keyPassword=" + keyPassword);
		LOG.info("merchantNo=" + merchantNo);
		LOG.info("payType=" + payType);
		LOG.info("curCode=" + curCode);
		LOG.info("orderUrl=" + orderUrl);
		LOG.info("orderNo=" + orderNo);
		LOG.info("orderAmount=" + orderAmount);
		LOG.info("orderTime=" + orderTime);
		LOG.info("orderNote=" + orderNote);
		LOG.info("signData=" + signData);
	}
	
	/**
	 * 获取签名.
	 */
	@Override
	public String signature() {
		String signature = null;
		StringBuilder signBuilder = new StringBuilder();
		signBuilder.append(orderNo).append("|").append(orderTime).append("|")
				.append(curCode).append("|").append(orderAmount).append("|")
				.append(merchantNo);
		byte[] data = signBuilder.toString().getBytes();
		try {
			PKCS7Tool tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword, keyPassword);
			signature = tool.sign(data);
		} catch (GeneralSecurityException e) {
			LOG.info("BOC POST DATA SIGNATURE ERROR GeneralSecurityException:" + e);
		} catch (IOException e) {
			LOG.info("BOC POST DATA SIGNATURE ERROR IOException:" + e);
		} catch (Exception e) {
			LOG.info("BOC POST DATA SIGNATURE ERROR Exception:" + e);
		}
		return signature;
	}
	
	/**
	 * 获取商户号.
	 * @return 商户号.
	 */
	public String getMerchantNo() {
		return merchantNo;
	}
	/**
	 * 获取支付类型.
	 * @return 支付类型.
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * 获取订单币种.
	 * @return 订单币种.
	 */
	public String getCurCode() {
		return curCode;
	}
	/**
	 * 获取通知中行回调的URL.
	 * @return 通知中行回调的URL.
	 */
	public String getOrderUrl() {
		return orderUrl;
	}
	/**
	 * 获取订单号.
	 * @return 订单号.
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * 获取订单金额.
	 * @return 订单金额.
	 */
	public String getOrderAmount() {
		return orderAmount;
	}
	/**
	 * 获取订单创建时间.
	 * @return 订单创建时间.
	 */
	public String getOrderTime() {
		return orderTime;
	}
	/**
	 * 获取订单说明.
	 * @return 订单说明.
	 */
	public String getOrderNote() {
		return orderNote;
	}
	/**
	 * 获取签名数据.
	 * @return 签名数据.
	 */
	public String getSignData() {
		return signData;
	}
	/**
	 * 获取对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getOrderNo();
	}

}

