package com.lvmama.pet.recon.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.FinReconBankStatement;
import com.lvmama.comm.pet.service.pay.FinReconBankStatementService;
import com.lvmama.comm.pet.service.pay.FinReconResultService;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;

public class NoReconServiceImpl implements GatewayAccountService {

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	// 银行交易明细接口
	private FinReconBankStatementService finReconBankStatementService;
	// 对账结果接口
	private FinReconResultService finReconResultService;
	// 支付接口
	private PayPaymentService payPaymentService;
	// 退款接口
	private PayPaymentRefundmentService payPaymentRefundmentService;
	// 下载对账开始日期
	private Date startDate = null;
	// 下载对账结束日期
	private Date endDate = null;
	// 处理我方网关时包括的网关CODE
	private static String GATEWAY_IN = "'"
			+ Constant.RECON_GW_TYPE.CMB.name() + "','"
			+ Constant.RECON_GW_TYPE.CMB_INSTALMENT.name() + "','"
			+ Constant.RECON_GW_TYPE.COMM.name() + "','"
			+ Constant.RECON_GW_TYPE.CHINA_MOBILE_PAY.name() + "','"
			+ Constant.RECON_GW_TYPE.TELBYPAY.name() + "','"
			+ Constant.RECON_GW_TYPE.LAKALA.name() + "','"
			+ Constant.RECON_GW_TYPE.COMM_POS.name() + "','"
			+ Constant.RECON_GW_TYPE.COMM_POS_CASH.name() + "','"
			//+ Constant.RECON_GW_TYPE.SAND_POS.name() + "','"
			+ Constant.RECON_GW_TYPE.SAND_POS_CASH.name() + "','"
			+ Constant.RECON_GW_TYPE.UPOMP.name() + "','"
			+ Constant.RECON_GW_TYPE.SPDB.name() + "','"
			+ Constant.RECON_GW_TYPE.DAI_JIN_QUAN.name() +"','"
			+ Constant.RECON_GW_TYPE.STORED_CARD.name() +"','"
			+ Constant.RECON_GW_TYPE.ICBC.name() + "','"
			+ Constant.RECON_GW_TYPE.TENPAY.name() + "','"
			+ Constant.RECON_GW_TYPE.TENPAY_APP.name() + "','"
			+ Constant.RECON_GW_TYPE.TENPAY_WAP.name() + "','"
			+ Constant.RECON_GW_TYPE.CHINAPNR.name() + "','"
			+ Constant.RECON_GW_TYPE.ICBC_INSTALMENT.name() + "'";

	// 成功标志
	private static String SUCCESS = "SUCCESS";

	public static void main(String[] age ){
		System.out.println(GATEWAY_IN);
	}
	
	/**
	 * 定时任务自动处理无需勾兑的账务
	 */
	@Override
	public String processAccount() {
		Date date = new Date();
		// date=DateUtil.toDate("2013-03-21","yyyy-MM-dd");
		// 默认取前天所有数据(例:2013-03-24 00:00:00 ~ 2013-03-25 00:00:00)
		startDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -2));
		endDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -1));
		return processAccount(startDate, endDate);
	}

	private String processAccount(Date startDate, Date endDate) {
		return processAccount(startDate, endDate, "");
	}

	/**
	 * 根据日期处理存款账户账务
	 */
	@Override
	public String processAccount(Date startDate, Date endDate,
			String reconStatus) {
		this.startDate = startDate;
		this.endDate = endDate;
		// 对账
		log.info("start recon, reconStatus:" + reconStatus);
		return recon(reconStatus);
	}

	@Override
	public void processRecon(FinReconBankStatement finReconBankStatement,
			boolean processUnneedRecon) {
		// 现金账户默认勾兑成功,不需要重新对账功能
	}

	/**
	 * 对账功能
	 * 
	 * @author zhangwengang
	 * @param reconDate
	 *            对帐日期(对哪天的帐)
	 * @param reconStatus
	 *            重新对账时根据状态来删除结果表老数据
	 * @return 是否成功
	 */
	private String recon(String reconStatus) {
		// 网关+对账日期(对哪天的帐)删除老数据
		int rows = finReconResultService.deleteOldData(GATEWAY_IN, startDate,
				reconStatus);
		log.info("delete recon_result old data,rows:" + rows + ",GATEWAY_IN:"
				+ GATEWAY_IN + ",date:"
				+ DateUtil.formatDate(startDate, "yyyy-MM-dd")
				+ ",reconStatus:" + reconStatus);

		// 以当前网关+对账日期为条件,将 我方成功的交易数据插入到对账结果表中,默认为成功
		boolean result = finReconResultService
				.copyTransactionDataToReconResult(GATEWAY_IN, startDate,
						Constant.RECON_STATUS.SUCCESS.name());
		log.info("copyTransactionDataToReconResult complete, result:" + result);
		return result ? SUCCESS : "CASH_ACCOUNT_RECON_FAIL";
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public FinReconResultService getFinReconResultService() {
		return finReconResultService;
	}

	public void setFinReconResultService(
			FinReconResultService finReconResultService) {
		this.finReconResultService = finReconResultService;
	}

	public FinReconBankStatementService getFinReconBankStatementService() {
		return finReconBankStatementService;
	}

	public void setFinReconBankStatementService(
			FinReconBankStatementService finReconBankStatementService) {
		this.finReconBankStatementService = finReconBankStatementService;
	}

	public PayPaymentRefundmentService getPayPaymentRefundmentService() {
		return payPaymentRefundmentService;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

}
