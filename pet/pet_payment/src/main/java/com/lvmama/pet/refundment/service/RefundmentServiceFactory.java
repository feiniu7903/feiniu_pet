package com.lvmama.pet.refundment.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.refundment.service.impl.TenpayRefundServiceImpl;

public class RefundmentServiceFactory{
	private Log LOG = LogFactory.getLog(this.getClass());
	
	private static final Map<String,BankRefundmentService> serviceMap = new HashMap<String,BankRefundmentService>();

	/**
	 * 百付退款服务.
	 */
	private BankRefundmentService byPayRefundService;
	/**
	 * 银联在线预授权SERVICE.
	 */
	private BankRefundmentService unionpayPreRefundService;
	
	/**
	 * 银联
	 */
	private BankRefundmentService unionpayRefundService;
	
	/**
	 * 招行分期退款SERVICE.
	 */
	private BankRefundmentService cmbInstalmentRefundService;
	/**
	 * 支付宝退款SERVICE.
	 */
	private BankRefundmentService alipayRefundService;
	/**
	 * 中国银行分期退款SERVICE.
	 */
	private BankRefundmentService bocInstalmentRefundService;
	/**
	 * 招商银行直连退款SERVICE.
	 */
	private BankRefundmentService cmbRefundService;
	/**
	 * 交通银行退款SERVICE.
	 */
	private BankRefundmentService commRefundService;
	/**
	 * 中国银行直连退款SERVICE.
	 */
	private BankRefundmentService bocRefundService;
	
	/**
	 * 汇付天下退款SERVICE.
	 */
	private BankRefundmentService chinapnrRefundService;
	
	/**
	 * 百付-手机支付退款SERVICE
	 */
	private BankRefundmentService upompRefundService;
	
	/**
	 * 拉卡拉退款
	 */
	private BankRefundmentService lakalaRefundService;
	/**
	 * 现金账户退款
	 */
	private BankRefundmentService cashAccountRefundService;
	/**
	 * 通过支付宝打款到银行
	 */
	private BankRefundmentService alipayBptbRefundService;

	/**
	 * 通过支付宝打款到支付宝帐户
	 */
	private BankRefundmentService alipayBatchRefundService;
	/**
	 * 上海浦东发展银行
	 */
	private BankRefundmentService spdbRefundService;
	/**
	 * 储值卡退款
	 */
	private BankRefundmentService storedCardRefundService;
	/**
	 * 驴游天下卡退款
	 */
	private BankRefundmentService lvmamaCardRefundService;
	
	/**
	 * 中国移动退款
	 */
	private BankRefundmentService chinaMobileRefundService;
	
	/**
	 * 宁波银行退款
	 */
	private BankRefundmentService ningboBankRefundService;
	
	/**
	 * 工行退款
	 */
	private BankRefundmentService icbcRefundService;
	
	/**
	 * 工行分期退款
	 */
	private BankRefundmentService icbcInstalmentRefundService;
	
	
	/**
	 * 财付通退款
	 */
	private TenpayRefundServiceImpl tenpayRefundServiceImpl;
	
	/**
	 * 百度钱包退款服务.
	 */
	private BankRefundmentService baiduPayRefundService;
	
	/**
	 * @param info
	 * @return
	 */
	public BankReturnInfo refund(final RefundmentToBankInfo info){
		LOG.info("refund invoked: "+StringUtil.printParam(info));		
		BankRefundmentService refundService = getRefundmentService(info.getRefundGateway());
		if(refundService==null){
			BankReturnInfo bankReturnInfo=new BankReturnInfo();
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			bankReturnInfo.setCodeInfo("未获取到有效的退款网关,不允许进行退款操作!");
			return bankReturnInfo;
		}
		LOG.info("got service: " + refundService.getClass().getName());
		return  refundService.refund(info);
	}
	/**
	 * 根据支付网关，获取相应的退款服务.
	 * @param paymentGateway 支付网关.
	 * @return
	 */
	private BankRefundmentService getRefundmentService(String refundGateway){
		if(serviceMap.isEmpty()){
			serviceMap.put(Constant.PAYMENT_GATEWAY.TELBYPAY.name(), byPayRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name(), unionpayPreRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.CMB_INSTALMENT.name(), cmbInstalmentRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.CMB.name(), cmbRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ALIPAY.name(), alipayRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ALIPAY_DIRECTPAY.name(), alipayRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BOC_INSTALMENT.name(), bocInstalmentRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.COMM.name(), commRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.SPDB.name(), spdbRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BOC.name(), bocRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.CHINAPNR.name(), chinapnrRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.UPOMP.name(), upompRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.LAKALA.name(), lakalaRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.CASH_ACCOUNT.name(), cashAccountRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.CASH_BONUS.name(), cashAccountRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.STORED_CARD.name(), storedCardRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.LYTXK_STORED_CARD.name(), lvmamaCardRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ALIPAY_BPTB.name(), alipayBptbRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ALIPAY_BATCH.name(), alipayBatchRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.UNIONPAY.name(), unionpayRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.CHINA_MOBILE_PAY.name(), chinaMobileRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.NING_BO_BANK.name(), ningboBankRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ICBC.name(), icbcRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ICBC_INSTALMENT.name(), icbcInstalmentRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.TENPAY.name(), tenpayRefundServiceImpl);
			serviceMap.put(Constant.PAYMENT_GATEWAY.TENPAY_APP.name(), tenpayRefundServiceImpl);
			serviceMap.put(Constant.PAYMENT_GATEWAY.TENPAY_WAP.name(), tenpayRefundServiceImpl);
			serviceMap.put(Constant.PAYMENT_GATEWAY.WEIXIN_WEB.name(), tenpayRefundServiceImpl);
			serviceMap.put(Constant.PAYMENT_GATEWAY.WEIXIN_IOS.name(), tenpayRefundServiceImpl);
			serviceMap.put(Constant.PAYMENT_GATEWAY.WEIXIN_ANDROID.name(), tenpayRefundServiceImpl);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BAIDUPAY_WAP.name(), baiduPayRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BAIDUPAY_WAP_ACTIVITIES.name(), baiduPayRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BAIDUPAY_APP.name(), baiduPayRefundService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BAIDUPAY_APP_ACTIVITIES.name(), baiduPayRefundService);
		}
		BankRefundmentService refundService = serviceMap.get(refundGateway);
		return refundService;
	}
	
	
	public BankRefundmentService getByPayRefundService() {
		return byPayRefundService;
	}

	public void setByPayRefundService(BankRefundmentService byPayRefundService) {
		this.byPayRefundService = byPayRefundService;
	}

	public BankRefundmentService getUnionpayPreRefundService() {
		return unionpayPreRefundService;
	}
	public void setUnionpayPreRefundService(BankRefundmentService unionpayPreRefundService) {
		this.unionpayPreRefundService = unionpayPreRefundService;
	}
	public BankRefundmentService getCmbInstalmentRefundService() {
		return cmbInstalmentRefundService;
	}

	public void setCmbInstalmentRefundService(BankRefundmentService cmbInstalmentRefundService) {
		this.cmbInstalmentRefundService = cmbInstalmentRefundService;
	}

	public BankRefundmentService getAlipayRefundService() {
		return alipayRefundService;
	}

	public void setAlipayRefundService(BankRefundmentService alipayRefundService) {
		this.alipayRefundService = alipayRefundService;
	}

	public BankRefundmentService getBocInstalmentRefundService() {
		return bocInstalmentRefundService;
	}

	public void setBocInstalmentRefundService(BankRefundmentService bocInstalmentRefundService) {
		this.bocInstalmentRefundService = bocInstalmentRefundService;
	}

	public BankRefundmentService getCmbRefundService() {
		return cmbRefundService;
	}

	public void setCmbRefundService(BankRefundmentService cmbRefundService) {
		this.cmbRefundService = cmbRefundService;
	}

	public BankRefundmentService getCommRefundService() {
		return commRefundService;
	}

	public void setCommRefundService(BankRefundmentService commRefundService) {
		this.commRefundService = commRefundService;
	}

	public BankRefundmentService getBocRefundService() {
		return bocRefundService;
	}

	public void setBocRefundService(BankRefundmentService bocRefundService) {
		this.bocRefundService = bocRefundService;
	}

	public BankRefundmentService getChinapnrRefundService() {
		return chinapnrRefundService;
	}

	public void setChinapnrRefundService(BankRefundmentService chinapnrRefundService) {
		this.chinapnrRefundService = chinapnrRefundService;
	}

	public BankRefundmentService getUpompRefundService() {
		return upompRefundService;
	}

	public void setUpompRefundService(BankRefundmentService upompRefundService) {
		this.upompRefundService = upompRefundService;
	}

	public BankRefundmentService getLakalaRefundService() {
		return lakalaRefundService;
	}

	public void setLakalaRefundService(BankRefundmentService lakalaRefundService) {
		this.lakalaRefundService = lakalaRefundService;
	}

	public BankRefundmentService getCashAccountRefundService() {
		return cashAccountRefundService;
	}

	public void setCashAccountRefundService(BankRefundmentService cashAccountRefundService) {
		this.cashAccountRefundService = cashAccountRefundService;
	}

	public BankRefundmentService getAlipayBptbRefundService() {
		return alipayBptbRefundService;
	}

	public void setAlipayBptbRefundService(BankRefundmentService alipayBptbRefundService) {
		this.alipayBptbRefundService = alipayBptbRefundService;
	}

	public BankRefundmentService getAlipayBatchRefundService() {
		return alipayBatchRefundService;
	}

	public void setAlipayBatchRefundService(BankRefundmentService alipayBatchRefundService) {
		this.alipayBatchRefundService = alipayBatchRefundService;
	}

	public BankRefundmentService getStoredCardRefundService() {
		return storedCardRefundService;
	}

	public void setStoredCardRefundService(BankRefundmentService storedCardRefundService) {
		this.storedCardRefundService = storedCardRefundService;
	}

	public BankRefundmentService getLvmamaCardRefundService() {
		return lvmamaCardRefundService;
	}
	public void setLvmamaCardRefundService(
			BankRefundmentService lvmamaCardRefundService) {
		this.lvmamaCardRefundService = lvmamaCardRefundService;
	}
	public static Map<String, BankRefundmentService> getServicemap() {
		return serviceMap;
	}
	public void setUnionpayRefundService(BankRefundmentService unionpayRefundService) {
		this.unionpayRefundService = unionpayRefundService;
	}
	public BankRefundmentService getUnionpayRefundService() {
		return unionpayRefundService;
	}
	public BankRefundmentService getChinaMobileRefundService() {
		return chinaMobileRefundService;
	}
	public void setChinaMobileRefundService(BankRefundmentService chinaMobileRefundService) {
		this.chinaMobileRefundService = chinaMobileRefundService;
	}
	public BankRefundmentService getSpdbRefundService() {
		return spdbRefundService;
	}
	public void setSpdbRefundService(BankRefundmentService spdbRefundService) {
		this.spdbRefundService = spdbRefundService;
	}
	
	public BankRefundmentService getNingboBankRefundService() {
		return ningboBankRefundService;
	}
	public void setNingboBankRefundService(BankRefundmentService ningboBankRefundService) {
		this.ningboBankRefundService = ningboBankRefundService;
	}
	public BankRefundmentService getIcbcRefundService() {
		return icbcRefundService;
	}
	public void setIcbcRefundService(BankRefundmentService icbcRefundService) {
		this.icbcRefundService = icbcRefundService;
	}
	public BankRefundmentService getIcbcInstalmentRefundService() {
		return icbcInstalmentRefundService;
	}
	public void setIcbcInstalmentRefundService(BankRefundmentService icbcInstalmentRefundService) {
		this.icbcInstalmentRefundService = icbcInstalmentRefundService;
	}
	public TenpayRefundServiceImpl getTenpayRefundServiceImpl() {
		return tenpayRefundServiceImpl;
	}
	public void setTenpayRefundServiceImpl(TenpayRefundServiceImpl tenpayRefundServiceImpl) {
		this.tenpayRefundServiceImpl = tenpayRefundServiceImpl;
	}
	public BankRefundmentService getBaiduPayRefundService() {
		return baiduPayRefundService;
	}
	public void setBaiduPayRefundService(BankRefundmentService baiduPayRefundService) {
		this.baiduPayRefundService = baiduPayRefundService;
	}
	
}