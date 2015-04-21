package com.lvmama.pet.payment.post.data;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;


/**
 * 交通银行支付提交数据.
 * 
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 2011/06/29
 * @since Super一期
 */
public class COMMPostData implements PostData { 

	/**
	 * 消息版本号 Y interfaceVersion 7 固定为 1.0.0.0.
	 */
	private final String interfaceVersion = PaymentConstant.getInstance().getProperty("COMM_INTERFACEVERSION");
	/**
	 * 商户客户号（商户编号） Y merID 15 商户客户号是银行生成的15 位编号.
	 */
	private final String merID = PaymentConstant.getInstance().getProperty("COMM_MERID");
	/**
	 * 订单号 Y orderid 20 商户应保证定单号+定单日期的唯一性.
	 */
	private String orderId = "";
	/**
	 * 商户订单日期 Y orderDate 8 yyyyMMdd.
	 */
	private String orderDate = "";
	/**
	 * 商户订单时间 N orderTime 8 HHmmss.
	 */
	private String orderTime = "";
	/**
	 * 交易类别 Y tranType 1 0 B2C.
	 */
	private static final String tranType = "0";
	/**
	 * 订单金额 Y amount 17 单位:元 并带两位小数15 位整数+2 位小数.
	 */
	private String amount = "";
	/**
	 * 订单币种 Y curType 3 人民币 CNY.
	 */
	private static final String curType = "CNY";
	/**
	 * 订单内容 N orderContent 100 商家填写的其他订单信息，在个人客户页面显示.
	 */
	private String orderContent = "";
	/**
	 * 商家备注 N orderMono 100 不在个人客户页面显示的备注，但可在商户管理页面上显示.
	 */
	private String orderMono = "";
	/**
	 * 物流配送标志 N phdFlag 1 0 非物流 1 物流配送.
	 */
	private String phdFlag = "";
	/**
	 * 通知方式 Y notifyType 1 0 不通知 1 通知 2 转页面.
	 */
	private static final String notifyType = "1";
	/**
	 * 主动通知 URL N merURL 100 为空则不发通知.
	 */
	private String merURL = PaymentConstant.getInstance().getProperty("COMM_ASYNC_URL");
	/**
	 * 取货 URL N goodsURL 100 为空则不显示按钮，不自动跳转.
	 */
	private String goodsURL = PaymentConstant.getInstance().getProperty("COMM_CALLBACK_URI");
	/**
	 * 自动跳转时间 N jumpSeconds 2 等待n 秒后自动跳转取货URL；若不填写则表示不自动跳转.
	 */
	private static final String jumpSeconds = "5";
	/**
	 * 商户批次号 N payBatchNo 10 商家可填入自己的批次号，对账使用.
	 */
	private String payBatchNo = "";
	/**
	 * 代理商家名称 N proxyMerName 30 二级商户编号/或证件号码.
	 */
	private String proxyMerName = "";
	/**
	 * 代理商家证件类型 N proxyMerType 4 代理商家证件类型.
	 */
	private String proxyMerType = "";
	/**
	 * 代理商家证件号码 N proxyMerCredentials 20 代理商家证件号码.
	 */
	private String proxyMerCredentials = "";
	/**
	 * 渠道编号 Y netType 1 固定填0:（html 渠道）.
	 */
	private static final String netType = "0";
	/**
	 * 商家签名 Y merSignMsg 2000 商家签名 detech 方式签名.
	 */
	private String merSignMsg = "";

	/**
	 * 包装交通银行支付的数据.
	 * 
	 * @param ordOrder ordPayment
	 *            参数ordOrder   参数 ordPayment
	 * @return CMBPostData 交通银行数据对象.
	 */
	
	public COMMPostData(PayPayment payment) {
		/**日期格式转换*/
		String payTime = DateUtil.formatDate(payment.getCreateTime(), "yyyyMMddHHmmss");
		this.orderId = payment.getPaymentTradeNo();
		/** 0到8是截取日期     8到14是截取时间*/
		this.setOrderDate(payTime.substring(0, 8));
		this.setOrderTime(payTime.substring(8, 14));
		this.setAmount(String.valueOf(PriceUtil.convertToYuan(payment.getAmount())));
		
		
		String isSign = generateRequestData();
		this.setMerSignMsg(COMMUtil.verify(isSign));
	}
	
	/**
	 * 对数据的包装.
	 */
	private String generateRequestData() {
		String sourceMsg = this.getInterfaceVersion() + "|"
				+ this.getMerID() + "|" + this.getOrderId()
				+ "|" + this.getOrderDate() + "|"
				+ this.getOrderTime() + "|"
				+ this.getTranType() + "|" + this.getAmount()
				+ "|" + this.getCurType() + "|"
				+ this.getOrderContent() + "|"
				+ this.getOrderMono() + "|" + this.getPhdFlag()
				+ "|" + this.getNotifyType() + "|"
				+ this.getMerURL() + "|" + this.getGoodsURL()
				+ "|" + this.getJumpSeconds() + "|"
				+ this.getPayBatchNo() + "|"
				+ this.getProxyMerName() + "|"
				+ this.getProxyMerType() + "|"
				+ this.getProxyMerCredentials() + "|"
				+ this.getNetType();
		return sourceMsg;
	}
	/**
	 * 签名是否正确.
	 * 
	 * @return  null;
	 */
	@Override
	public String signature() {
		return null;
	}
	/**
	 * 获取消息版本号.
	 * 
	 * @return 消息版本号.
	 */
	public String getInterfaceVersion() {
		return interfaceVersion;
	}
	/**
	 * 获取商户客户号.
	 * 
	 * @return 商户客户号.
	 */
	public String getMerID() {
		return merID;
	}
	/**
	 * 获取订单号.
	 * 
	 * @return 订单号.
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置订单号.
	 * 
	 * @param orderId
	 *           订单号.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * 获取商户订单日期.
	 * 
	 * @return 商户订单日期.
	 */
	public String getOrderDate() {
		return orderDate;
	}
	/**
	 * 设置商户订单日期.
	 * 
	 * @param orderDate
	 *          商户订单日期.
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	/**
	 * 获取商户订单时间.
	 * 
	 * @return 商户订单时间.
	 */
	public String getOrderTime() {
		return orderTime;
	}
	/**
	 * 设置商户订单时间.
	 * 
	 * @param orderTime
	 *          商户订单时间.
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	/**
	 * 获取交易类别.
	 * 
	 * @return 交易类别.
	 */
	public String getTranType() {
		return tranType;
	}
	/**
	 * 获取订单金额.
	 * 
	 * @return 订单金额.
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 设置订单金额.
	 * 
	 * @param amount
	 *          订单金额.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * 获取订单币种.
	 *
	 * @return 订单币种.
	 */
	public String getCurType() {
		return curType;
	}
	/**
	 * 获取订单内容.
	 *
	 * @return 订单内容.
	 */
	public String getOrderContent() {
		return orderContent;
	}
	/**
	 * 设置订单内容.
	 * 
	 * @param orderContent
	 *          订单内容.
	 */
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}
	/**
	 * 获取商家备注.
	 *
	 * @return 商家备注.
	 */
	public String getOrderMono() {
		return orderMono;
	}
	/**
	 * 设置商家备注.
	 * 
	 * @param orderMono
	 *          商家备注.
	 */
	public void setOrderMono(String orderMono) {
		this.orderMono = orderMono;
	}
	/**
	 * 获取物流配送标志.
	 *
	 * @return 物流配送标志.
	 */
	public String getPhdFlag() {
		return phdFlag;
	}
	/**
	 * 设置配送标志.
	 * 
	 * @param phdFlag
	 *         配送标志.
	 */
	public void setPhdFlag(String phdFlag) {
		this.phdFlag = phdFlag;
	}
	/**
	 * 获取通知方式.
	 *
	 * @return 通知方式.
	 */
	public String getNotifyType() {
		return notifyType;
	}
	/**
	 * 获取主动通知 URL.
	 *
	 * @return 主动通知 URL.
	 */
	public String getMerURL() {
		return merURL;
	}
	/**
	 * 设置主动通知 URL..
	 * 
	 * @param merURL
	 *        主动通知 URL..
	 */
	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}
	/**
	 * 获取取货 URL.
	 *
	 * @return 取货 URL.
	 */
	public String getGoodsURL() {
		return  goodsURL;
	}
	/**
	 * 获取自动跳转时间.
	 *
	 * @return 自动跳转时间.
	 */
	public String getJumpSeconds() {
		return jumpSeconds;
	}
	/**
	 * 获取商户批次号.
	 *
	 * @return  商户批次号.
	 */
	public String getPayBatchNo() {
		return payBatchNo;
	}
	/**
	 * 设置商户批次号.
	 * 
	 * @param payBatchNo
	 *        商户批次号.
	 */
	public void setPayBatchNo(String payBatchNo) {
		this.payBatchNo = payBatchNo;
	}
	/**
	 * 获取代理商家证件.
	 *
	 * @return  代理商家证件.
	 */
	public String getProxyMerName() {
		return proxyMerName;
	}
	/**
	 * 设置代理商家证件.
	 * 
	 * @param proxyMerName
	 *        代理商家证件.
	 */
	public void setProxyMerName(String proxyMerName) {
		this.proxyMerName = proxyMerName;
	}
	/**
	 * 获取代理商家证件类型.
	 *
	 * @return  代理商家证件类型.
	 */
	public String getProxyMerType() {
		return proxyMerType;
	}
	/**
	 * 设置代理商家证件类型.
	 * 
	 * @param proxyMerType
	 *        代理商家证件类型.
	 */
	public void setProxyMerType(String proxyMerType) {
		this.proxyMerType = proxyMerType;
	}
	/**
	 * 获取代理商家证件号码.
	 *
	 * @return  代理商家证件号码.
	 */
	public String getProxyMerCredentials() {
		return proxyMerCredentials;
	}
	/**
	 * 设置代理商家证件号码.
	 * 
	 * @param proxyMerCredentials
	 *        代理商家证件号码.
	 */
	public void setProxyMerCredentials(String proxyMerCredentials) {
		this.proxyMerCredentials = proxyMerCredentials;
	}
	/**
	 * 获取渠道编号.
	 *
	 * @return  渠道编号.
	 */
	public String getNetType() {
		return netType;
	}
	/**
	 * 获取商家签名.
	 *
	 * @return  商家签名.
	 */
	public String getMerSignMsg() {
		return merSignMsg;
	}
	/**
	 * 设置商家签名.
	 * 
	 * @param merSignMsg
	 *       商家签名.
	 */
	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}
	@Override
	public String getPaymentTradeNo() {
		return this.getOrderId();
	}

}