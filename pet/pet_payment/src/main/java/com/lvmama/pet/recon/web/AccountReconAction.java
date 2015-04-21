package com.lvmama.pet.recon.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.pay.FinReconBankStatement;
import com.lvmama.comm.pet.service.pay.FinReconBankStatementService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;

public class AccountReconAction extends BaseAction {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 3574883768616203128L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	// 支付宝对账接口
	private GatewayAccountService alipayAccountService;
	// 银联对账接口
	private GatewayAccountService unionPayAccountService;
	// 存款账户对账接口
	private GatewayAccountService cashAccountAccountService;
	// 奖金账户对账接口
	private GatewayAccountService cashBonusAccountService;
	// 宁波银行对账接口
	private GatewayAccountService ningboAccountService;
	// 银行源数据接口
	private FinReconBankStatementService finReconBankStatementService;
	// 无需勾兑的对账数据接口
	private GatewayAccountService noReconService;
	//杉德POS机的对账数据接口
	private GatewayAccountService sandPosAccountService;
	
	//财付通对账数据接口
	private GatewayAccountService tenpayAccountService;
		
	// 网关
	private String gateway;
	// 银行对账日期
	private String bankReconTime;
	// 对账状态
	private String reconStatus;
	// 银行商户订单号
	private String bankPaymentTradeNo;
	// 银行交易流水号
	private String bankGatewayTradeNo;
	// 交易类型
	private String transactionType;
	// 各网关接口MAP
	private static Map<String, GatewayAccountService> gatewayServiceImplMap = new HashMap<String, GatewayAccountService>();

	/**
	 * 根据日期+网关+对账状态 重新对账
	 * 
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	@Action("/pay/recon/accountRecon")
	public void accountReconByDay() throws IOException {
		log.info("gateway:" + gateway + ",bankReconTime:" + bankReconTime);
		if (StringUtils.isBlank(gateway) || StringUtils.isBlank(bankReconTime)) {
			log.error("The required parameters are not satisfied! gateway:"
					+ gateway + ",bankReconTime:" + bankReconTime);
			return;
		}
		Date date = DateUtil.toDate(bankReconTime, "yyyy-MM-dd");
		// 默认取前天所有数据(例:2013-03-24 00:00:00 ~ 2013-03-25 00:00:00)
		Date startDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date,
				0));
		Date endDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date,
				+1));

		GatewayAccountService gatewayAccountService = getServiceImpl(gateway);
		if (gatewayAccountService != null) {
			String result = gatewayAccountService.processAccount(startDate,
					endDate, reconStatus);
			log.info("gatewayAccountService.processAccount complete,gateway:"
					+ gateway + ",result:" + result);
			this.getResponse().getWriter().write("{result:'" + result + "'}");
		}
	}

	/**
	 * 单笔数据重新对账
	 * 
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	@Action("/pay/recon/accountReconSingle")
	public void accountReconBySingle() throws IOException {
		log.info("accountReconBySingle, Param:bankPaymentTradeNo:"
				+ bankPaymentTradeNo + ",bankGatewayTradeNo:"
				+ bankGatewayTradeNo + ",transactionType:" + transactionType
				+ ",gateway:" + gateway);
		FinReconBankStatement finReconBankStatement = finReconBankStatementService.selectFinReconBankStatement(bankPaymentTradeNo,bankGatewayTradeNo, transactionType, gatewayConversion(gateway));
		if (finReconBankStatement != null) {
			GatewayAccountService gatewayAccountService = getServiceImpl(gateway);
			if (gatewayAccountService != null) {
				gatewayAccountService.processRecon(finReconBankStatement, false);
				log.info("gatewayAccountService.processRecon complete! gateway:"+gateway);
			}
		} else {
			log.error("FinReconBankStatement is null!");
		}
	}

	private GatewayAccountService getServiceImpl(String gateway) {
		if (gatewayServiceImplMap.isEmpty()) {
			gatewayServiceImplMap.put(Constant.RECON_GW_TYPE.ALIPAY.name(),alipayAccountService);
			gatewayServiceImplMap.put(Constant.RECON_GW_TYPE.UNIONPAY.name(),unionPayAccountService);
			gatewayServiceImplMap.put(Constant.RECON_GW_TYPE.CASH_ACCOUNT.name(),cashAccountAccountService);
			gatewayServiceImplMap.put(Constant.RECON_GW_TYPE.CASH_BONUS.name(),cashBonusAccountService);
			gatewayServiceImplMap.put("NO_RECON", noReconService);
			gatewayServiceImplMap.put(Constant.RECON_GW_TYPE.SAND_POS.name(), sandPosAccountService);
			gatewayServiceImplMap.put(Constant.RECON_GW_TYPE.NING_BO_BANK.name(), ningboAccountService);
			gatewayServiceImplMap.put(Constant.RECON_GW_TYPE.TENPAY.name(), tenpayAccountService);
		}
		GatewayAccountService gatewayAccountService = gatewayServiceImplMap.get(gatewayConversion(gateway));
		if (gatewayAccountService == null) {
			log.error("GatewayAccountService is null!,gateway:" + gateway);
		}
		return gatewayAccountService;
	}
	
	
	private String gatewayConversion(String gateway){
		if(StringUtils.isNotBlank(gateway)){
			if(Constant.RECON_GW_TYPE.ALIPAY.name().equalsIgnoreCase(gateway)
				||Constant.RECON_GW_TYPE.ALIPAY_DIRECTPAY.name().equalsIgnoreCase(gateway)
				||Constant.RECON_GW_TYPE.ALIPAY_APP.name().equalsIgnoreCase(gateway)
				||Constant.RECON_GW_TYPE.ALIPAY_WAP.name().equalsIgnoreCase(gateway)
				||Constant.RECON_GW_TYPE.ALIPAY_BPTB.name().equalsIgnoreCase(gateway)
				||Constant.RECON_GW_TYPE.ALIPAY_BATCH.name().equalsIgnoreCase(gateway)){
				return Constant.RECON_GW_TYPE.ALIPAY.name();
			}
			else if(Constant.RECON_GW_TYPE.UNIONPAY.name().equalsIgnoreCase(gateway)
					||Constant.RECON_GW_TYPE.CHINAPAY_PRE.name().equalsIgnoreCase(gateway)){
				return Constant.RECON_GW_TYPE.UNIONPAY.name();
			}
		}
		return gateway;
	}

	public GatewayAccountService getAlipayAccountService() {
		return alipayAccountService;
	}

	public void setAlipayAccountService(
			GatewayAccountService alipayAccountService) {
		this.alipayAccountService = alipayAccountService;
	}

	public GatewayAccountService getUnionPayAccountService() {
		return unionPayAccountService;
	}

	public void setUnionPayAccountService(
			GatewayAccountService unionPayAccountService) {
		this.unionPayAccountService = unionPayAccountService;
	}

	public GatewayAccountService getCashAccountAccountService() {
		return cashAccountAccountService;
	}

	public void setCashAccountAccountService(
			GatewayAccountService cashAccountAccountService) {
		this.cashAccountAccountService = cashAccountAccountService;
	}

	public FinReconBankStatementService getFinReconBankStatementService() {
		return finReconBankStatementService;
	}

	public void setFinReconBankStatementService(
			FinReconBankStatementService finReconBankStatementService) {
		this.finReconBankStatementService = finReconBankStatementService;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getBankReconTime() {
		return bankReconTime;
	}

	public void setBankReconTime(String bankReconTime) {
		this.bankReconTime = bankReconTime;
	}

	public String getReconStatus() {
		return reconStatus;
	}

	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}

	public String getBankPaymentTradeNo() {
		return bankPaymentTradeNo;
	}

	public void setBankPaymentTradeNo(String bankPaymentTradeNo) {
		this.bankPaymentTradeNo = bankPaymentTradeNo;
	}

	public String getBankGatewayTradeNo() {
		return bankGatewayTradeNo;
	}

	public void setBankGatewayTradeNo(String bankGatewayTradeNo) {
		this.bankGatewayTradeNo = bankGatewayTradeNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public static Map<String, GatewayAccountService> getGatewayServiceImplMap() {
		return gatewayServiceImplMap;
	}

	public static void setGatewayServiceImplMap(
			Map<String, GatewayAccountService> gatewayServiceImplMap) {
		AccountReconAction.gatewayServiceImplMap = gatewayServiceImplMap;
	}

	public GatewayAccountService getCashBonusAccountService() {
		return cashBonusAccountService;
	}

	public void setCashBonusAccountService(
			GatewayAccountService cashBonusAccountService) {
		this.cashBonusAccountService = cashBonusAccountService;
	}

	public GatewayAccountService getNoReconService() {
		return noReconService;
	}

	public void setNoReconService(GatewayAccountService noReconService) {
		this.noReconService = noReconService;
	}

	public GatewayAccountService getSandPosAccountService() {
		return sandPosAccountService;
	}

	public void setSandPosAccountService(GatewayAccountService sandPosAccountService) {
		this.sandPosAccountService = sandPosAccountService;
	}

	public GatewayAccountService getNingboAccountService() {
		return ningboAccountService;
	}

	public void setNingboAccountService(GatewayAccountService ningboAccountService) {
		this.ningboAccountService = ningboAccountService;
	}

	public GatewayAccountService getTenpayAccountService() {
		return tenpayAccountService;
	}

	public void setTenpayAccountService(GatewayAccountService tenpayAccountService) {
		this.tenpayAccountService = tenpayAccountService;
	}
}
