/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.service;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.view.PaginationVO;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashBonusReturn;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.money.CashPay;
import com.lvmama.comm.pet.po.money.CashRecharge;
import com.lvmama.comm.pet.po.money.CashRefundment;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.pet.po.pub.ComBank;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.GeneralSequenceNo;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.CashAccountChangeLogVO;
import com.lvmama.comm.vo.CashAccountPayInfoVO;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_CASH_EVENT;
import com.lvmama.comm.vo.Constant.ComeFrom;
import com.lvmama.comm.vo.Constant.DRAW_MONEY_CHANNEL;
import com.lvmama.comm.vo.Constant.DRAW_MONEY_STATUS;
import com.lvmama.comm.vo.Constant.FINC_CASH_STATUS;
import com.lvmama.comm.vo.Constant.PAYMENT_SERIAL_STATUS;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.money.dao.CashAccountDAO;
import com.lvmama.pet.money.dao.CashBonusReturnDAO;
import com.lvmama.pet.money.dao.CashChangeDAO;
import com.lvmama.pet.money.dao.CashDrawDAO;
import com.lvmama.pet.money.dao.CashFreezeQueueDAO;
import com.lvmama.pet.money.dao.CashMoneyDrawDAO;
import com.lvmama.pet.money.dao.CashPayDAO;
import com.lvmama.pet.money.dao.CashRechargeDAO;
import com.lvmama.pet.money.dao.CashRefundmentDAO;
import com.lvmama.pet.pub.dao.ComBankDAO;
import com.lvmama.pet.pub.dao.ComLogDAO;
/**
 * CashAccount 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashAccountServiceImpl implements CashAccountService{
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(CashAccountServiceImpl.class);
	
	
	/**一年内使用奖金消费最多30次**/
	private static final Long BONUS_PAY_MAX_COUNT_OF_YEAR=30L;

	/**一个月内使用奖金消费最多10次**/
	private static final Long BONUS_PAY_MAX_COUNT_OF_MONTH=10L;
	
	
	@Autowired
	private ComLogDAO comLogDAO;
	@Autowired
	private CashAccountDAO cashAccountDAO;
	@Autowired
	private ComBankDAO comBankDAO;
	@Autowired
	private CashMoneyDrawDAO cashMoneyDrawDAO;
	@Autowired
	private CashDrawDAO cashDrawDAO;
	@Autowired
	private CashRefundmentDAO cashRefundmentDAO;
	@Autowired
	private CashFreezeQueueDAO cashFreezeQueueDAO;
	@Autowired
	private CashChangeDAO cashChangeDAO;
	@Autowired
	private CashRechargeDAO cashRechageDAO;
	@Autowired
	private ComSmsTemplateService comSmsTemplateService;
	@Autowired
	private SmsRemoteService smsRemoteService;
	@Autowired
	private PayPaymentService payPaymentService;
	@Autowired
	private CashRechargeDAO cashRechargeDAO;
	@Autowired
	private CashPayDAO cashPayDAO;
	@Autowired
	private PayPaymentRefundmentService payPaymentRefundmentService;
	
	@Autowired
	private CashBonusReturnDAO cashBonusReturnDAO;
	
	@Autowired
	protected UserUserProxy userUserProxy;
	
	
	@Override
	public Long insert(CashAccount cashAccount) {
		return cashAccountDAO.insert(cashAccount);
	}
	@Override
	public List<ComBank> getComBankList() {
		return comBankDAO.getComBankList();
	}
	
	@Override
	public boolean applyDraw2Bank(Long userId, String bankName,
			String bankAccount, String bankAccountName, String kaiHuHang,
			String province, String city, Long amount, boolean isCompensation,String flag,
			String operatorName,String isSuperback) {
		CashAccountVO moneyAccount = cashAccountDAO
				.queryMoneyAccount(userId);
		if (moneyAccount.getMaxDrawMoney() > 0) {
			CashMoneyDraw cashMoneyDraw = new CashMoneyDraw();
			cashMoneyDraw.setCashAccountId(moneyAccount.getCashAccountId());
			cashMoneyDraw.setBankName(bankName);
			cashMoneyDraw.setBankAccount(bankAccount);
			cashMoneyDraw.setBankAccountName(bankAccountName);
			cashMoneyDraw.setKaiHuHang(kaiHuHang);
			cashMoneyDraw.setCity(city);
			cashMoneyDraw.setProvince(province);
			cashMoneyDraw.setDrawAmount(amount);
			cashMoneyDraw.setDrawMoneyChannel(DRAW_MONEY_CHANNEL.BANK.name());
			cashMoneyDraw.setAuditStatus(DRAW_MONEY_STATUS.UNVERIFIED.name());
			cashMoneyDraw.setPayStatus(FINC_CASH_STATUS.UnApplyPayCash.name());
			cashMoneyDraw.setIsCompensation(String.valueOf(isCompensation));
			cashMoneyDraw.setFlag(flag);
			cashMoneyDraw.setCreateTime(new Date());
			Long moneyDrawId = cashMoneyDrawDAO.insert(cashMoneyDraw);

			insertLog("CASH_MONEY_DRAW", null, moneyDrawId, operatorName,
					COM_LOG_CASH_EVENT.drawMoneyApply.name(), "提现申请",null);

			cashFreezeQueueDAO.freezeDrawAmount(moneyAccount.getCashAccountId(), moneyDrawId, amount);
			if(StringUtils.isNotBlank(isSuperback)&& "true".equalsIgnoreCase(isSuperback)){
				return updateCashMoneyDrawAuditStatus(moneyDrawId, Constant.FINC_CASH_STATUS.VERIFIED.name(), "", operatorName);
			}
			
			return true;
		} else {
			LOG.info("用户可提现金额为" + moneyAccount.getMaxDrawMoney()
					+ " ,已经冻结,不能提现");
			return false;
		}
	}
	private void insertLog(String objectType, Long parentId, Long objectId, String operatorName,
			String logType, String logName, String content) {
		
		ComLog log = new ComLog();
		log.setParentId(parentId);
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		
		if (content != null)
			log.setContent(content);
		comLogDAO.insert(log);
	}
	@Override
	public boolean applyDraw2AliPay(Long userId, String aliPayAccount,
			String aliPayAccountName, Long amount, boolean isCompensation,
			String operatorName,String isSuperback) {
		CashAccount cashAccount=cashAccountDAO.getCashAccountByUserId(userId);
		CashMoneyDraw cashMoneyDraw = new CashMoneyDraw();
		cashMoneyDraw.setCashAccountId(cashAccount.getCashAccountId());
		cashMoneyDraw.setBankName("支付宝");
		cashMoneyDraw.setBankAccount(aliPayAccount);
		cashMoneyDraw.setBankAccountName(aliPayAccountName);
		cashMoneyDraw.setDrawAmount(amount);
		cashMoneyDraw.setDrawMoneyChannel(DRAW_MONEY_CHANNEL.ALIPAY.name());
		cashMoneyDraw.setAuditStatus(DRAW_MONEY_STATUS.UNVERIFIED.name());
		cashMoneyDraw.setPayStatus(FINC_CASH_STATUS.UnApplyPayCash.name());
		cashMoneyDraw.setIsCompensation(String.valueOf(isCompensation));
		cashMoneyDraw.setCreateTime(new Date());
		Long moneyDrawId = cashMoneyDrawDAO.insert(cashMoneyDraw);

		insertLog("CASH_MONEY_DRAW", null, moneyDrawId, operatorName,
				COM_LOG_CASH_EVENT.drawMoneyApply.name(), "提现申请",null);

		cashFreezeQueueDAO.freezeDrawAmount(cashAccount.getCashAccountId(), moneyDrawId, amount);
		if(StringUtils.isNotBlank(isSuperback)&& "true".equalsIgnoreCase(isSuperback)){
			return updateCashMoneyDrawAuditStatus(moneyDrawId, Constant.FINC_CASH_STATUS.VERIFIED.name(), "后台提现申请，自动审核通过", operatorName);
		}
		return true;
	}
	@Override
	public boolean updateCashMoneyDrawAuditStatus(Long moneyDrawId,
			String auditStatus, String memo, String operatorName) {
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moneyDrawId", moneyDrawId);
		paramMap.put("auditStatus", auditStatus);
		paramMap.put("memo", memo);
		int row = cashMoneyDrawDAO.updateByParamMap(paramMap);
		if (row == 1) {
			if (StringUtils.isNotBlank(memo)) {
				insertLog("CASH_MONEY_DRAW", null,moneyDrawId, operatorName,
						COM_LOG_CASH_EVENT.drawMoneyApprove.name(), "提现单审核",
						"set auditStatus =" + auditStatus + " " + memo);
			} else {
				insertLog("CASH_MONEY_DRAW", null,moneyDrawId, operatorName,
						COM_LOG_CASH_EVENT.drawMoneyApprove.name(), "提现单审核",
						"set auditStatus =" + auditStatus);
			}
			if (Constant.DRAW_MONEY_STATUS.REJECTED.name().equalsIgnoreCase(
					auditStatus))
				cashFreezeQueueDAO.unfreezeDrawAmount(moneyDrawId);

			return true;

		} else
			return false;
	}
	@Override
	public Long queryMoneyDrawCount(CompositeQuery compositeQuery) {
		return cashMoneyDrawDAO.queryMoneyDrawCount(compositeQuery.getMoneyDrawRelate()
				.getCashAccountId(),compositeQuery.getMoneyDrawRelate().getUserNo(),
				compositeQuery.getMoneyDrawRelate().getUserMobile(),compositeQuery.getMoneyDrawRelate().getBankAccountName(),
				compositeQuery.getMoneyDrawRelate().getFincCashStatus(), compositeQuery.getMoneyDrawRelate()
				.getCreateTimeStart(), compositeQuery.getMoneyDrawRelate()
				.getCreateTimeEnd());
	}
	@Override
	public List<CashMoneyDraw> queryMoneyDraw(CompositeQuery compositeQuery) {
		return cashMoneyDrawDAO.queryMoneyDraw(compositeQuery.getMoneyDrawRelate()
				.getCashAccountId(),compositeQuery.getMoneyDrawRelate().getUserNo(),
				compositeQuery.getMoneyDrawRelate().getUserMobile(),
				compositeQuery.getMoneyDrawRelate().getBankAccountName(),
				compositeQuery.getPageIndex().getBeginIndex(),
				compositeQuery.getPageIndex().getEndIndex(), compositeQuery.getMoneyDrawRelate().getFincCashStatus(),
				compositeQuery.getMoneyDrawRelate().getCreateTimeStart(),
				compositeQuery.getMoneyDrawRelate().getCreateTimeEnd());
	}
	@Override
	public CashAccountVO queryMoneyAccountByUserId(Long userId) {
		CashAccountVO moneyAccount = cashAccountDAO.queryMoneyAccount(userId);
		if (moneyAccount==null) {
			moneyAccount = new CashAccountVO(false);
		}
		return moneyAccount;
	}
	@Override
	public Long balance() {
		final Long balance = cashAccountDAO.balance();
		if (balance==null) {
			LOG.error("balance error");
			throw new RuntimeException("balance error");
		} else if (!balance.equals(0L)) {
			LOG.error("balance error: " + balance);
			throw new RuntimeException("balance error: " + balance);
		}
		return balance;
	}
	@Override
	public Long queryMoneyAccountChangeLogCount(CompositeQuery compositeQuery) {
		return cashChangeDAO.queryMoneyAccountChangeLogCount(compositeQuery
				.getMoneyAccountChangeLogRelate().getCashAccountId(),compositeQuery
				.getMoneyAccountChangeLogRelate().getUserNo(), compositeQuery
				.getMoneyAccountChangeLogRelate().getMoneyAccountChangeType(),
				compositeQuery.getPayFrom(),compositeQuery.getBonusRefundment());
	}
	@Override
	public List<CashAccountChangeLogVO> queryMoneyAccountChangeLog(
			CompositeQuery compositeQuery) {
		return cashChangeDAO.queryMoneyAccountChangeLog(compositeQuery
				.getMoneyAccountChangeLogRelate().getCashAccountId(),compositeQuery
				.getMoneyAccountChangeLogRelate().getUserNo(), compositeQuery
				.getMoneyAccountChangeLogRelate().getMoneyAccountChangeType(),
				compositeQuery.getPageIndex().getBeginIndex(), compositeQuery
						.getPageIndex().getEndIndex(), compositeQuery.getPayFrom(),compositeQuery.getBonusRefundment());
	}
	@SuppressWarnings("rawtypes")
	@Override
	public PaginationVO<Map<String, Object>> queryMoneyDrawHistory(PaginationVO<Map<String, Object>> paginationVO) {
		Map<String, Object> queryParamMap = buildFincMoneyHisParam(paginationVO);
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		paginationVO.setResultList(returnList);
		Long totalCount = (Long) this.cashMoneyDrawDAO
				.queryMoneyDrawHistoryCount(queryParamMap);
		paginationVO.setTotalRows(totalCount);
		if (totalCount > 0) {

			List result = this.cashMoneyDrawDAO
					.queryMoneyDrawHistory(queryParamMap);
			Iterator it = result.iterator();
			while (it.hasNext()) {
				Map<String, Object> resultRowMap = new HashMap<String, Object>();
				Map rowMap = (Map) it.next();
				CashMoneyDraw cashMoneyDraw = new CashMoneyDraw();

				cashMoneyDraw.setCashAccountId(new Long(rowMap.get("CASH_ACCOUNT_ID").toString()));
				cashMoneyDraw.setBankName((String) rowMap.get("BANK_NAME"));
				cashMoneyDraw.setBankAccount((String) rowMap
						.get("BANK_ACCOUNT"));
				cashMoneyDraw.setBankAccountName((String) rowMap
						.get("BANK_ACCOUNT_NAME"));
				cashMoneyDraw.setKaiHuHang((String) rowMap.get("KAI_HU_HANG"));
				cashMoneyDraw
						.setDrawAmount(rowMap.get("DRAW_AMOUNT") == null ? null
								: new Long(rowMap.get("DRAW_AMOUNT").toString()));
				cashMoneyDraw.setPayStatus((String) rowMap.get("PAY_STATUS"));
				cashMoneyDraw.setMoneyDrawId(new Long(rowMap.get(
						"MONEY_DRAW_ID").toString()));
				cashMoneyDraw.setCreateTime((Date) rowMap.get("APPLY_DATE"));
				resultRowMap.put("fincMoneyDraw", cashMoneyDraw);
				resultRowMap.put("sid", rowMap.get("SID") == null ? null
						: rowMap.get("SID").toString());
				resultRowMap.put("amount", rowMap.get("AMOUNT") == null ? null
						: rowMap.get("AMOUNT").toString());
				resultRowMap.put("failInfo", rowMap.get("FAIL_INFO"));
				resultRowMap.put("createTime", rowMap.get("CREATE_TIME"));
				resultRowMap.put("aliPayFile", rowMap.get("ALIPAY_FILE"));
				returnList.add(resultRowMap);

			}
		}

		return paginationVO;
	}
	
	private Map<String, Object> buildFincMoneyHisParam(PaginationVO<Map<String, Object>> paginationVO) {
		final Map<String, Object> map = new HashMap<String, Object>();
		Long cashAccountId = (Long) paginationVO.getQueryParamMap().get("cashAccountId");
		String userNo=(String) paginationVO.getQueryParamMap().get("userNo");
		if (cashAccountId!=null) {
			map.put("cashAccountId", cashAccountId);
		}
		if (StringUtils.isNotBlank(userNo)) {
			map.put("userNo", userNo);
		}

		if ((String) paginationVO.getQueryParamMap().get("status") != null) {
			map.put("status",
					(String) paginationVO.getQueryParamMap().get("status"));
		}
		map.put("createTimeStart",
				(Date) paginationVO.getQueryParamMap().get("createTimeStart"));
		map.put("createTimeEnd",
				(Date) paginationVO.getQueryParamMap().get("createTimeEnd"));
		map.put("beginIndex", paginationVO.getBeginIndex());
		map.put("endIndex", paginationVO.getEndIndex());
		return map;
	}
	@Override
	public PaginationVO<CashMoneyDraw> queryFincMoneyDraw(
			PaginationVO<CashMoneyDraw> paginationVO) {
		final Map<String, Object> map = buildFincMoneyQueryParam(paginationVO);

		Long totalRows = (Long) cashMoneyDrawDAO
				.queryMoneyDrawTasksCount(map);

		map.put("beginIndex", paginationVO.getBeginIndex());
		map.put("endIndex", paginationVO.getEndIndex());
		paginationVO.setTotalRows(totalRows);

		if (totalRows <= 0) {
			return paginationVO;
		}

		List<CashMoneyDraw> list = this.cashMoneyDrawDAO.queryMoneyDrawTasks(map);
		paginationVO.setResultList(list);
		return paginationVO;
	}
	
	private Map<String, Object> buildFincMoneyQueryParam(
			PaginationVO<CashMoneyDraw> paginationVO) {
		final Map<String, Object> map = new HashMap<String, Object>();
		Long cashAccountId = (Long) paginationVO.getQueryParamMap().get("cashAccountId");
		String userNo=(String) paginationVO.getQueryParamMap().get("userNo");
		if (cashAccountId!=null) {
			map.put("cashAccountId", cashAccountId);
		}
		if (userNo!=null) {
			map.put("userNo", userNo);
		}
		return map;
	}
	
	@Override
	public boolean setDoneToFincMoneyDrawPayStatus(Long moneyDrawId,
			String memo, String operatorName) {
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moneyDrawId", moneyDrawId);
		paramMap.put("payStatus", Constant.FINC_CASH_STATUS.Done.name());
		paramMap.put("memo", memo);
		int row = cashMoneyDrawDAO.updateByParamMap(paramMap);
		if (row == 1) {
			if (StringUtils.isNotBlank(memo)) {
				insertLog(
						"CASH_MONEY_DRAW",null,
						moneyDrawId,
						operatorName,
						COM_LOG_CASH_EVENT.drawMoneyDone.name(),
						"提现已处理完毕",
						"set payStatus ="
								+ Constant.FINC_CASH_STATUS.Done.name() + " "
								+ memo);
			} else {
				insertLog(
						"CASH_MONEY_DRAW",null,
						moneyDrawId,
						operatorName,
						COM_LOG_CASH_EVENT.drawMoneyDone.name(),
						"提现已处理完毕",
						"set payStatus ="
								+ Constant.FINC_CASH_STATUS.Done.name());
			}

			return true;

		} else
			return false;
	}
	@Override
	public boolean rejectedFincMoneyDrawPayStatus(Long moneyDrawId,
			String memo, String operatorName) {
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moneyDrawId", moneyDrawId);
		paramMap.put("payStatus",
				Constant.FINC_CASH_STATUS.UnApplyPayCashAndRejected.name());
		paramMap.put("memo", memo);
		int row = cashMoneyDrawDAO.updateByParamMap(paramMap);
		if (row == 1) {
			insertLog(
					"CASH_MONEY_DRAW",null,
					moneyDrawId,
					operatorName,
					COM_LOG_CASH_EVENT.drawMoneyRejected.name(),
					"提现拒绝",
					"payStatus ="+ Constant.FINC_CASH_STATUS.UnApplyPayCashAndRejected.name() + ",   拒绝原因:" + memo);
			cashFreezeQueueDAO.unfreezeDrawAmount(moneyDrawId);
			return true;

		} else
			return false;
	}
	@Override
	public boolean checkMobileNumber(Long userId) {
		CashAccount cashAccount=cashAccountDAO.getCashAccountByUserId(userId);
		if(cashAccount!=null && StringUtils.isNotBlank(cashAccount.getMobileNumber())){
			return true;
		}else{
			return false;
		}
		
	}
	@Override
	public CashAccount queryCashAccountByPk(Long id) {
		return cashAccountDAO.getCashAccountById(id);
	}
	@Override
	public CashAccount queryCashAccountByUserId(Long id) {
		return cashAccountDAO.getCashAccountByUserId(id);
	}
	@Override
	public CashAccount queryCashAccountByUserNo(String userNo) {
		return cashAccountDAO.getCashAccountByUserNo(userNo);
	}
	@Override
	public CashAccount queryOrCreateCashAccountByUserId(Long id) {
		CashAccount cashAccount=cashAccountDAO.getCashAccountByUserId(id);
		if(cashAccount==null){
			Long pk=cashAccountDAO.createMoneyAccount(id);
			LOG.info("Create a new cash account,Primary Key is: "+pk+", UserId is: "+id);
			cashAccount=cashAccountDAO.getCashAccountByUserId(id);
		}
		return cashAccount;
	}
	
	@Override
	public Long createCashAccountByUserId(Long id) {
		return cashAccountDAO.createMoneyAccount(id);
	}
	@Override
	public BankReturnInfo refund2CashAccount(RefundmentToBankInfo info) {
		
		cashAccountDAO.protect(""+info.getPayRefundmentId(), ComeFrom.REFUND.toString());
		
		//查询现金账户，如果不存在则创建账户
		CashAccount cashAccount=this.queryOrCreateCashAccountByUserId(info.getUserId());
		
		String codeInfo=null;
		Long cashRefundId = null;
		//判断支付网关，如果是奖金支付的就原路退回奖金账户
		if(Constant.PAYMENT_GATEWAY.CASH_BONUS.name().equals(info.getPaymentGateway())){
			
			//创建现金退款记录
			CashRefundment cashRefundment=new CashRefundment();
			cashRefundment.setAmount(info.getRefundAmount());
			cashRefundment.setOrderId(info.getOrderId());
			cashRefundment.setCashAccountId(cashAccount.getCashAccountId());
			cashRefundment.setCreateTime(new Date());	
			cashRefundment.setSerial(info.getPayPaymentRefunfmentSerial());
			cashRefundment.setRefundmentType(info.getRefundType());
			cashRefundment.setOrderRefundmentId(info.getObjectId());
			cashRefundment.setBounsRefundment("Y");
			cashRefundId = cashRefundmentDAO.insert(cashRefundment);
			//记录余额变更
			cashChangeDAO.balanceChange(cashAccount.getCashAccountId(), info.getRefundAmount(), ComeFrom.BONUS_REFUND.name(), cashRefundId.toString());
			
			CashPay cashPay=getCashPay(info.getPaymentId());
			if(CashPay.BonusFrom.OLD.name().equals(cashPay.getBonusFrom())){//老账户
				cashAccountDAO.updateBonusBalance(cashAccount.getCashAccountId(), info.getRefundAmount());
			}else if(CashPay.BonusFrom.NEW.name().equals(cashPay.getBonusFrom())){
				cashAccountDAO.updateNewBonusBalance(cashAccount.getCashAccountId(), info.getRefundAmount());
			}
			
			if (LOG.isDebugEnabled()) {
				LOG.debug("bonus refund successful");
				LOG.debug("bonus refundmentAmount: " + info.getRefundAmount());
				LOG.debug("payRefundmentId: " + info.getPayRefundmentId());
			}
			
			//发送奖金退款短信
			sendCashAccountSms(cashAccount.getMobileNumber(),info.getRefundAmount(),Constant.SMS_TEMPLATE.CASH_BONUS_REFUNDMENT.name());
			
			codeInfo="奖金账户退款成功";
			
		}else{
			//创建现金退款记录
			CashRefundment cashRefundment=new CashRefundment();
			cashRefundment.setAmount(info.getRefundAmount());
			cashRefundment.setOrderId(info.getOrderId());
			cashRefundment.setCashAccountId(cashAccount.getCashAccountId());
			cashRefundment.setCreateTime(new Date());	
			cashRefundment.setSerial(info.getPayPaymentRefunfmentSerial());
			cashRefundment.setRefundmentType(info.getRefundType());
			cashRefundment.setOrderRefundmentId(info.getObjectId());
			cashRefundment.setBounsRefundment("N");

			cashRefundId = cashRefundmentDAO.insert(cashRefundment);
			
			//记录余额变更.
			cashChangeDAO.balanceChange(cashAccount.getCashAccountId(), info.getRefundAmount(), ComeFrom.REFUND.toString(), cashRefundId.toString());
			
			//操作订单退款余额.
			cashAccountDAO.updateRefundBalance(cashAccount.getCashAccountId(), info.getRefundAmount());
			if (LOG.isDebugEnabled()) {
				LOG.debug("refund successful");
				LOG.debug("refundmentAmount: " + info.getRefundAmount());
				LOG.debug("payRefundmentId: " + info.getPayRefundmentId());
			}
			
			//发送现金退款短信
			sendCashAccountRefundSms(cashAccount.getMobileNumber(), info.getRefundAmount());
			
			codeInfo="现金账户退款成功";
		}
		
		BankReturnInfo bankReturnInfo = new BankReturnInfo();
		bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
		bankReturnInfo.setCodeInfo(codeInfo);
		bankReturnInfo.setSerial(SerialUtil.generate10ByteSerial()+ cashRefundId+"");
		return bankReturnInfo;
	}
	
	private CashPay getCashPay(Long paymentId){
		//更新奖金余额，判断奖金支付来源，返回新旧账户
		PayPayment pay=payPaymentService.selectByPaymentId(paymentId);
		if(null!=pay.getOriObjectId()){//支付转移
			//找原始支付记录
			PayPayment payment=getOrgPayPayment(String.valueOf(pay.getOriObjectId()),pay.getPaymentTradeNo(),0);
			return cashPayDAO.findCashPayByOutTradeNo(payment.getSerial());
		}else{
			return cashPayDAO.findCashPayByOutTradeNo(pay.getSerial());
		}
	}
	
	/**
	 * 找原始支付记录
	 * @param objectId 订单号
	 * @param paymentTradeNo 支付交易号
	 * @param count 递归次数
	 * @return 原始支付记录
	 */
	private PayPayment getOrgPayPayment(String objectId,String paymentTradeNo,Integer count){
		PayPayment pay=payPaymentService.selectByPaymentTradeNoAndObjectId(paymentTradeNo, objectId);
		if(null!=pay&&null!=pay.getOriObjectId()){//找上一笔支付记录
			if(count>=1000){//防止由于数据问题造成的死循环
				return null;
			}
			count++;
			return getOrgPayPayment(String.valueOf(pay.getOriObjectId()),pay.getPaymentTradeNo(),count);
		}
		return pay;
	}
	
	@Override
	public boolean initPaymentPassword(Long userId, String inputPassword) {
		try {
			CashAccount cashAccount = cashAccountDAO.getCashAccountByUserId(userId);
			if(StringUtils.isNotBlank(inputPassword) && !"null".equals(inputPassword)){
				String newInputPasswordMD5 = new MD5().code(inputPassword);
				cashAccountDAO.updatePaymentPassword(userId, newInputPasswordMD5);
				this.insertLog("CASH_ACCOUNT", null, cashAccount.getCashAccountId(), ""+userId,
						COM_LOG_CASH_EVENT.cashAccountChange.name(), "找回支付密码",null);
				return true;
			} else if("null".equals(inputPassword)){//当找回密码时的验证码输入正确时，会将用户的支付密码设置为null
				cashAccountDAO.updatePaymentPassword(userId, null);
				this.insertLog("CASH_ACCOUNT", null, cashAccount.getCashAccountId(), ""+userId,
						COM_LOG_CASH_EVENT.cashAccountChange.name(), "找回支付密码",null);
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean changePaymentPassword(Long userId, String oldInputPassword,
			String newInputPassword) {
		String oldInputPasswordMD5 = "";
		String newInputPasswordMD5 = "";
		CashAccount cashAccount = cashAccountDAO.getCashAccountByUserId(userId);
		String oldPassword = cashAccount.getPaymentPassword();
		try {
			newInputPasswordMD5 = new MD5().code(newInputPassword);
			oldInputPasswordMD5 = new MD5().code(oldInputPassword);
			if(LOG.isDebugEnabled()){
				LOG.debug("The oldPassword id DB is：" + oldPassword);
				LOG.debug("The oldInputPasswordMD5 user input is: " + oldInputPasswordMD5);
			}
			//初始支付密码为null或用户输入的原始密码与数据库中的原始密码相同时为true
			if(oldInputPasswordMD5.equals(oldPassword)){
				cashAccountDAO.updatePaymentPassword(userId, newInputPasswordMD5);
				this.insertLog("CASH_ACCOUNT", null, cashAccount.getCashAccountId(), ""+userId,
						COM_LOG_CASH_EVENT.cashAccountChange.name(), "修改支付密码",null);
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean validateMoneyAccountPaymentPassword(Long userId,
			String password) {
		CashAccount cashAccount = cashAccountDAO.getCashAccountByUserId(userId);
		try {
			password = new MD5().code(password);
			return password.equals(cashAccount.getPaymentPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean withDrawMoney(CashMoneyDraw cashMoneyDraw, CashDraw cashDraw) {
		cashDrawDAO.insert(cashDraw);
		cashMoneyDrawDAO.updateByPrimaryKey(cashMoneyDraw);
		return true;
	}
	@Override
	public boolean successPaymentCallback(PayPayment payment) {
//		final OrdOrder order = orderDAO
//				.selectByPrimaryKey(payment.getObjectId());
//		if (!isValid(order)) {
//			LOG.error("paymentCallback failed: Can not find OrdOrder with the orderId = "
//					+ payment.getObjectId());
//			return false;
//		}
//		// 回调时 修改ord_payment 和 ord_order 的支付类型.
//		transactionLogic.paymentSuccess(payment);
		return true;
	}
	@Override
	public CashRecharge findCashRechargeBySerial(String serial) {
		return cashRechageDAO.findCashRechargeBySerial(serial);
	}
	
	@Override
	public CashRecharge findCashRechargeById(Long cashRechargeId) {
		return cashRechageDAO.findCashRechargeByPrimaryKey(cashRechargeId);
	}
	public boolean rechargeToCashAccount(Long cashRechargeId,Long rechargeAmount){
		CashRecharge cashRecharge = cashRechageDAO.findCashRechargeByPrimaryKey(cashRechargeId);
		if (cashRecharge == null){
			LOG.error("cashRecharge id: " + cashRechargeId);
		}
		if(cashAccountDAO ==null){
			LOG.error("cashAccountDAO is null: ");
		}
		if (!PAYMENT_SERIAL_STATUS.SUCCESS.name().equals(cashRecharge.getStatus())) {
			CashAccount cashAccount=cashAccountDAO.getCashAccountById(cashRecharge.getCashAccountId());
			cashRecharge.setStatus(PAYMENT_SERIAL_STATUS.SUCCESS.name());
			cashRecharge.setCallbackTime(new Date());
			cashRechageDAO.updateByPrimaryKey(cashRecharge);
			cashAccountDAO.protect(cashRechargeId.toString(),
					ComeFrom.RECHARGE.toString());
			cashChangeDAO.balanceChange(cashAccount.getCashAccountId(), rechargeAmount,
					ComeFrom.RECHARGE.toString(), cashRechargeId.toString());
			cashAccountDAO.updateReChargeBalance(cashAccount.getCashAccountId(), rechargeAmount);
			if (LOG.isDebugEnabled()) {
				LOG.debug("recharge successful");
				LOG.debug("cashRechargeId: " + cashRechargeId);
				LOG.debug("reChargeAmount: " + rechargeAmount);
			}
			this.sendCashAccountRechargeSms(cashAccount.getMobileNumber(),rechargeAmount);
			return true;
		}
		return false;
	}
	
	/**
	 * 对于从IVR接收到用户填写的密码进行MD5加密.
	 * @param inputPassword 加密前的密码.
	 * @return md5Password 加密后的密码.
 	 */
	private String makeMD5Password(String inputPassword) {
		String md5Password = "";
		try {
			md5Password = new MD5().code(inputPassword);
		} catch (Exception e) {
			md5Password = "";
		}
		return md5Password;
	}
	/**
	 * 存款账户全额或部分支付.
	 * @param cashAccountPayInfo 存款账户支付信息
	 * @param orderId 订单号
	 * @param cashAccountVO 存款账户
	 * @return
	 */
	private boolean moneyAccountTotalOrdPartPay(
			CashAccountPayInfoVO cashAccountPayInfo, Long orderId,
			CashAccountVO cashAccountVO) {
		boolean flag=false;
		// 存款账户全额支付
		/*if (cashAccountVO.getMaxPayMoney() >= cashAccountPayInfo.getPaytotalFen()) {
			flag = this.totalPay(orderId, cashAccountPayInfo.getCsno());
			if (flag) {
				orderMessageProducer.sendMsg(MessageFactory.newOrderPaymentMessage(orderId));
			}
		} else {// 存款账户部分支付
			flag = this.partPay(orderId, cashAccountVO.getMaxPayMoney(),cashAccountPayInfo.getCsno());
			if (flag) {
				orderMessageProducer.sendMsg(MessageFactory.newOrderPartpayPaymentMessage(orderId));
			}
		}*/
		return flag;
	}
	/**
	 * 添加存款账户支付错误记录.
	 * @param objectId 订单号
	 * @param actualPay 实际支付
	 * @param callbackInfo 错误信息
	 */
	private void insertFailedMoneyAccountPayment(Long objectId,Long actualPay,String callbackInfo) {
		//TODO 解耦
//		final Date date = new Date();
//		PayPayment payment = new PayPayment();
//		payment.setObjectId(objectId);
//		payment.setPaymentGateway(PAYMENT_TYPE_ONLINE.CASH_ACCOUNT.name());
//		payment.setAmount(actualPay);
//		payment.setStatus(PAYMENT_SERIAL_STATUS.FAIL.name());
//		payment.geneSerialNo();
//		payment.setCallbackInfo(callbackInfo);
//		payment.setCreateTime(date);
//		payment.setCallbackTime(date);
//		payPaymentService.savePayment(payment);
	}
	/**
	 * orderCashPay.
	 * 
	 * @param order
	 *            order
	 * @param payAmount
	 *            payAmount
	 * @param operatorName
	 *            operatorName
	 * @param serial
	 *            支付流水号
	 * @return boolean
	 */
	private boolean orderCashPay(final Long cashAccountId,final Long orderId,final Long payAmount,
			final String operatorName, final String serial) {
//		Date date = new Date();
//		Long payedAmount = payPaymentService.sumPayedPayPaymentAmountByObjectId(orderId);
//		if (payAmount > (oughtPay - payedAmount)) {
//			LOG.error("orderCashPay failed: The payAmount = " + payAmount
//					+ " is larger than (oughtPay = " + oughtPay
//					+ " subtract payedAmount = " + payedAmount + ")");
//			return false;
//		}
////		PayPayment payment = new PayPayment();
////		payment.setObjectId(orderId);
////		payment.setPaymentGateway(PAYMENT_TYPE_ONLINE.CASH_ACCOUNT.name());
////		payment.setAmount(payAmount);
////		payment.setStatus(PAYMENT_SERIAL_STATUS.SUCCESS.name());
////		payment.geneSerialNo();
////		payment.setCreateTime(date);
////		date = new Date();
////		payment.setCallbackTime(date);
////		payPaymentService.savePayment(payment);
//
//		CashPay cashPay = new CashPay();
//		cashPay.setAmount(payAmount);
//		cashPay.setCreateTime(date);
//		cashPay.setSerial(serial);
//		cashPay.setCashAccountId(cashAccountId);
//		cashPay.setStatus(FINC_CASH_STATUS.PayCashSuccess.name());
//		cashPay.setOrderId(orderId);
//		Long cashPayId = cashPayDAO.insert(cashPay);
//
////		PayTransaction payTransaction = new PayTransaction();
////		payTransaction.setSerial(fincCashPay.getSerial());
////		payTransaction.setAmount(payAmount);
////		payTransaction.setObjectId(orderId);
////		payTransaction.setObjectType("ORD_ORDER");
////		payTransaction.setPayee("LVMAMA");
////		payTransaction.setPayer(userId);
////		payTransaction.setPaymentGateway(PAYMENT_TYPE_ONLINE.CASH_ACCOUNT
////				.name());
////		payTransaction.setPaymentType("ONLINE");
////		payTransaction.setTransactionType(TRANSCATION_TYPE.PAYMENT.name());
////		payTransaction.setTransTime(date);
////		payTransaction.setCreateTime(date);
////		payPaymentService.savePayTransaction(payTransaction);
//
//		Long actualPay = order.getActualPay();
//		order.setActualPay(actualPay + payAmount);
//		if ((actualPay + payAmount) >= order.getOughtPay()) {
//			order.setPaymentStatus(PAYMENT_STATUS.PAYED.name());
//			order.setOrderViewStatus(Constant.PAYMENT_STATUS.PAYED.name());
//		} else {
//			order.setPaymentStatus(Constant.PAYMENT_STATUS.PARTPAY.name());
//			order.setOrderViewStatus(Constant.PAYMENT_STATUS.PARTPAY.name());
//		}
//		orderDAO.updateByPrimaryKey(order);
//
//		moneyAccountService.payFromMoneyAccount(userId, cashPayId, payAmount);
//
//		orderMessageProducer.sendMsg(MessageFactory
//				.newCashAccountPayMessage(cashPayId));

		return true;
	}
	/**
	 * 支付.
	 * <pre>
	 * 先使用现金账户充值余额支付，现金账户充值余额不足时再使用现金账户订单退款余额支付
	 * 支付金额不足时抛出运行期异常
	 * </pre>
	 * @param userId 用户ID
	 * @param cashPayId 支付记录ID
	 * @param payAmount  支付金额，以分为单位
	 * @return <code>true</code>代表支付成功，<code>false</code> 代表支付失败
	 */
	private boolean payFromMoneyAccount(final Long userId,final Long cashPayId, final Long payAmount) {
		final CashAccountVO cashAccountVO = this.queryMoneyAccountByUserId(userId);
		if (cashAccountVO==null || cashAccountVO.getMaxPayMoney()<=0) {
			LOG.error("not enough max pay money: userId is " + userId
					+ " max pay money is 0 pay is " + payAmount);
			throw new RuntimeException(
					"not enough max pay money: userId is " + userId
							+ " max pay money is 0 pay is " + payAmount);
		} else if (cashAccountVO.getMaxPayMoney() < payAmount) {
			LOG.error("not enough max pay money: userId is " + userId
					+ " max pay money is " + cashAccountVO.getMaxPayMoney()
					+ " pay is " + payAmount);
			throw new RuntimeException(
					"not enough max pay money: userId is " + userId
							+ " max pay money is "
							+ cashAccountVO.getMaxPayMoney() + " pay is "
							+ payAmount);
		} else {
			final Long rechargeBalance = cashAccountVO.getRechargeBalance();
			if (payAmount > rechargeBalance) {
				cashChangeDAO.balanceChange(cashAccountVO.getCashAccountId(), rechargeBalance
						- payAmount, ComeFrom.REFUND_BALANCE_PAY.toString(),
						cashPayId.toString());
				cashAccountDAO.updateRefundBalance(cashAccountVO.getCashAccountId(), rechargeBalance
						- payAmount);
				if (rechargeBalance > 0) {
					cashChangeDAO.balanceChange(cashAccountVO.getCashAccountId(), -rechargeBalance,
							ComeFrom.RECHARGE_BALANCE_PAY.toString(),
							cashPayId.toString());
					cashAccountDAO.updateReChargeBalance(cashAccountVO.getCashAccountId(),
							-rechargeBalance);
				}
				checkBalancebyUserId(userId);
				if (LOG.isDebugEnabled()) {
					LOG.debug("pay successful");
					LOG.debug("userId: " + userId);
					LOG.debug("pay: " + rechargeBalance);
					LOG.debug("pay: " + (payAmount - rechargeBalance));
				}
			} else {
				cashChangeDAO.balanceChange(cashAccountVO.getCashAccountId(), -payAmount,
						ComeFrom.RECHARGE_BALANCE_PAY.toString(),
						cashPayId.toString());
				cashAccountDAO.updateReChargeBalance(cashAccountVO.getCashAccountId(), -payAmount);
				checkBalancebyUserId(userId);
				if (LOG.isDebugEnabled()) {
					LOG.debug("pay successful");
					LOG.debug("userId: " + userId);
					LOG.debug("payAmount: " + payAmount);
				}
			}
		}
		return true;
	}
	
	/**
	 * 从奖金扣钱
	 * @param userId 用户ID
	 * @param cashPayId 支付业务ID
	 * @param payAmount 支付金额
	 * @return
	 */
	private boolean payFromBonusAccount(final Long userId,final Long cashPayId, final Long payAmount) {
		
		final CashAccountVO cashAccountVO = this.queryMoneyAccountByUserId(userId);
		
		if (null==cashAccountVO|| cashAccountVO.getBonusBalance()<=0) {
			
			LOG.error("not enough bonus: userId is " + userId+ " max bonus is 0 but pay is " + payAmount);
			
			throw new RuntimeException("not enough bonus: userId is " + userId+ " max bonus is 0 but pay is " + payAmount);
		
		} else if (cashAccountVO.getBonusBalance() < payAmount) {
			
			LOG.error("not enough bonus : userId is " + userId+ " max bonus is " + cashAccountVO.getBonusBalance()+ " but pay is " + payAmount);
			
			throw new RuntimeException("not enough bonus : userId is " + userId+ "  max bonus is  "+ cashAccountVO.getBonusBalance() + " but pay is "+ payAmount);
		
		} else {
			
			//奖金账户变更
			cashChangeDAO.balanceChange(cashAccountVO.getCashAccountId(), -payAmount,ComeFrom.BONUS_BALANCE_PAY.toString(),cashPayId.toString());
			
			//更新奖金余额
			cashAccountDAO.updateBonusBalance(cashAccountVO.getCashAccountId(), -payAmount);
			
			//校验奖金余额非负数
			final CashAccountVO checkCashAccount= this.queryMoneyAccountByUserId(userId);
			if (checkCashAccount.getBonusBalance() < 0) {
				LOG.error("bonus balance will be negative: " + userId);
				throw new RuntimeException("bonus balance will be negative: "+ userId);
			}
			
			if (LOG.isDebugEnabled()) {
				LOG.debug("bonus pay successful");
				LOG.debug("userId: " + userId);
				LOG.debug("payAmount: " + payAmount);
			}
		}
		return true;
	}
	
	/**
	 * 从新奖金扣钱
	 * @param userId 用户ID
	 * @param cashPayId 支付业务ID
	 * @param payAmount 支付金额
	 * @return
	 */
	private boolean payFromNewBonusAccount(final Long userId,final Long cashPayId, final Long payAmount) {
		
		final CashAccountVO cashAccountVO = this.queryMoneyAccountByUserId(userId);
		
		if (null==cashAccountVO|| cashAccountVO.getNewBonusBalance()<=0) {
			
			LOG.error("not enough new bonus: userId is " + userId+ " max bonus is 0 but pay is " + payAmount);
			
			throw new RuntimeException("not enough new bonus: userId is " + userId+ " max bonus is 0 but pay is " + payAmount);
		
		} else if (cashAccountVO.getNewBonusBalance() < payAmount) {
			
			LOG.error("not enough new bonus : userId is " + userId+ " max bonus is " + cashAccountVO.getBonusBalance()+ " but pay is " + payAmount);
			
			throw new RuntimeException("not enough new bonus : userId is " + userId+ "  max bonus is  "+ cashAccountVO.getBonusBalance() + " but pay is "+ payAmount);
		
		} else {
			
			//奖金账户变更
			cashChangeDAO.balanceChange(cashAccountVO.getCashAccountId(), -payAmount,ComeFrom.BONUS_BALANCE_PAY.toString(),cashPayId.toString());
			
			//更新奖金余额
			cashAccountDAO.updateNewBonusBalance(cashAccountVO.getCashAccountId(), -payAmount);
			
			//校验奖金余额非负数
			final CashAccountVO checkCashAccount= this.queryMoneyAccountByUserId(userId);
			if (checkCashAccount.getNewBonusBalance() < 0) {
				LOG.error("new bonus balance will be negative: " + userId);
				throw new RuntimeException("new bonus balance will be negative: "+ userId);
			}
			
			if (LOG.isDebugEnabled()) {
				LOG.debug("bonus pay successful");
				LOG.debug("userId: " + userId);
				LOG.debug("payAmount: " + payAmount);
			}
		}
		return true;
	}
	
	
	/**
	 * 根据用户ID对账.
	 * <pre>
	 * 确保现金账户余额不能为负值
	 * 充值余额或订单退款余额将为负时抛出运行期异常
	 * </pre>
	 * @param userId  用户ID
	 */
	private void checkBalancebyUserId(final Long userId) {
		final CashAccountVO cashAccountVO = this.queryMoneyAccountByUserId(userId);
		if (cashAccountVO.getRechargeBalance() < 0
				|| cashAccountVO.getRefundBalance() < 0) {
			LOG.error("balance will be negativ: " + userId);
			throw new RuntimeException("balance will be negativ: "
					+ userId);
		}
	}
	@Override
	public CashAccountVO queryMoneyAccountByUserNo(String userNo) {
		return cashAccountDAO.queryMoneyAccount(userNo);
	}
	@Override
	public boolean updateCashRecharge(CashRecharge cashRecharge) {
		return (cashRechargeDAO.updateByPrimaryKey(cashRecharge) == 1) ? true
				: false;
	}
	@Override
	public Long insertCashRecharge(CashRecharge cashRecharge) {
		return cashRechargeDAO.insert(cashRecharge);
	}

	@Override
	public boolean pay0Yuan(Long orderId) {
//		OrdOrder ordOrder = orderDAO.selectByPrimaryKey(orderId);
//		if(ordOrder==null){
//			LOG.error("0元支付失败orderId:"+orderId+"订单存在");
//			return false;
//		}
//		if (ordOrder.isPayToLvmama()
//				&& !ordOrder.isPaymentSucc()
//				&& ordOrder.getOughtPayYuan() == 0
//				&& checkPaySuccByNeedEContract(ordOrder)) {
//				LOG.info("pay0yuan orderId:"+ordOrder.getOrderId());
////				PayPayment payment = new PayPayment();
////				payment.setObjectId(ordOrder.getOrderId());
////				payment.setPaymentGateway(PAYMENT_TYPE_ONLINE.PAY_0_YUAN.name());
////				payment.setAmount(0L);
////				payment.setStatus(PAYMENT_SERIAL_STATUS.SUCCESS.name());
////				payment.geneSerialNo();
////				payment.setCreateTime(new Date());
////				payment.setCallbackTime(new Date());
////				payPaymentService.savePayment(payment);
////				this.successPaymentCallback(payment);
//				return true;
//		}
		return false;
	}

	@Override
	public boolean bindMobileNumber(Long userId, String mobileNumber,
			boolean updateFlag) {
		boolean flag = false;
		CashAccount cashAccount=this.queryOrCreateCashAccountByUserId(userId);
		if (updateFlag) {
			cashAccountDAO.updateMobileNumber(userId, mobileNumber);
			this.insertLog("CASH_ACCOUNT", null, cashAccount.getCashAccountId(), ""+userId,
					COM_LOG_CASH_EVENT.cashAccountChange.name(), "更新绑定手机号码","老的手机号码："+ cashAccount.getMobileNumber() + ", 新的手机号码：" + mobileNumber);
			flag = true;
		} else {
			if (StringUtils.isBlank(cashAccount.getMobileNumber())) {
				cashAccountDAO.updateMobileNumber(userId, mobileNumber);
				this.insertLog("CASH_ACCOUNT", null, cashAccount.getCashAccountId(), ""+userId,
						COM_LOG_CASH_EVENT.cashAccountChange.name(), "绑定手机号码","老的手机号码："+ cashAccount.getMobileNumber() + ", 新的手机号码：" + mobileNumber);
				flag = true;
			}
		}
		return flag;
	}
	@Override
	public Long insertCashMoneyDraw(CashMoneyDraw cashMoneyDraw) {
		return cashMoneyDrawDAO.insert(cashMoneyDraw);
	}
	@Override
	public CashMoneyDraw queryCashMoneyDraw(Long moneyDrawId) {
		return cashMoneyDrawDAO.selectByPrimaryKey(moneyDrawId);
	}
	@Override
	public boolean updateCashDrawByPrimaryKey(CashDraw cashDraw) {
		int row = cashDrawDAO.updateByPrimaryKey(cashDraw);
		if(row == 1)
			return true;
		else
			return false;
	}
	@Override
	public CashDraw findCashDrawByAlipay2bankFile(String alipay2bankFile) {
		return cashDrawDAO.findCashDrawByAlipay2bankFile(alipay2bankFile);
	}
	@Override
	public CashDraw findCashDrawBySerial(String serial) {
		return cashDrawDAO.findCashDrawBySerial(serial);
	}

	/**
	  * 提现.
	  * 
	  * <pre>
	  * 内部调用{@link #unfreezeDrawAmount(String, Long, Long)}
	  * 可提现金额不足时抛出运行期异常
	  * </pre>
	  * 
	  * @param userId
	  *            用户ID
	  * @param cashDrawId
	  *            提现记录ID
	  * @param drawAmount
	  *            提现金额，以分为单位
	  * @return <code>true</code>代表提现成功，<code>false</code>代表提现失败
	  */
	public boolean unfreezeAndDraw(final Long userId,
			final Long cashDrawId, final Long drawAmount) {
		final CashAccountVO moneyAccount = cashAccountDAO.queryMoneyAccount(userId);
		if (moneyAccount.getMaxDrawMoney() < drawAmount) {
			LOG.error("not enough max draw money: userId is " + userId
					+ " max draw money is " + moneyAccount.getMaxDrawMoney()
					+ " draw is " + drawAmount);
			throw new RuntimeException("not enough max draw money: userId is "
					+ userId + " max draw money is "
					+ moneyAccount.getMaxDrawMoney() + " draw is " + drawAmount);
		} else {
			// moneyAccountDAO.lock(userId);
			cashAccountDAO.protect(cashDrawId.toString(),
					ComeFrom.DRAW.toString());
			cashChangeDAO.balanceChange(moneyAccount.getCashAccountId(), -drawAmount,
					ComeFrom.DRAW.toString(), cashDrawId.toString());
			cashAccountDAO.updateRefundBalance(moneyAccount.getCashAccountId(), -drawAmount);
			checkBalancebyUserId(userId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("draw successful");
				LOG.debug("userId: " + userId);
				LOG.debug("cashDrawId: " + cashDrawId);
				LOG.debug("drawAmount: " + drawAmount);
			}
		}
		return true;
	}
	
	@Override
	public CashDraw findCashDrawByMoneyDrawId(Long moneyDrawId) {
		return cashDrawDAO.findCashDrawByMoneyDrawId(moneyDrawId);
	}
	
	private void sendCashAccountSms(String mobileNumber,Long amount,String smsTemplate) {
		// 短信发送到单独与现金账户绑定的手机号码
		if (mobileNumber != null) {
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("cash", PriceUtil.convertToYuan(amount));
			String smsContent = comSmsTemplateService.getSmsContent(smsTemplate, data);
			try{
				smsRemoteService.sendSms(smsContent, mobileNumber);	
			}catch(Exception e){
				LOG.error(this.getClass(), e);
			}
		}
	}
	public void sendCashAccountRechargeSms(String mobileNumber,Long rechargeAmount) {
		sendCashAccountSms(mobileNumber,rechargeAmount,Constant.SMS_TEMPLATE.CASH_ACCOUNT_RECHARGE.name());
	}
	
	private void sendCashAccountPaySms(String mobileNumber,Long actualPayAmount,Long actualBonusPay) {
		if (StringUtils.isNotBlank(mobileNumber)) {
			if(actualBonusPay>0&&actualPayAmount>0){//同时使用了奖金和现金
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("cash", PriceUtil.convertToYuan(actualPayAmount));
				data.put("bonus", PriceUtil.convertToYuan(actualBonusPay));
				String smsContent = comSmsTemplateService.getSmsContent(Constant.SMS_TEMPLATE.CASH_ACCOUNT_AND_BONUS_PAY.name(), data);
				try{
					smsRemoteService.sendSms(smsContent, mobileNumber);	
				}catch(Exception e){
					LOG.error(this.getClass(), e);
				}
			}else if(actualPayAmount>0&&actualBonusPay==0){//只使用现金
				sendCashAccountSms(mobileNumber,actualPayAmount,Constant.SMS_TEMPLATE.CASH_ACCOUNT_PAY.name());
			}else if(actualBonusPay>0&&actualPayAmount==0){//只使用了奖金
				sendCashAccountSms(mobileNumber,actualBonusPay,Constant.SMS_TEMPLATE.CASH_BONUS_PAY.name());
			}
		}
	}
	
	public void sendCashAccountDrawSms(String mobileNumber,Long drawAmount) {
		sendCashAccountSms(mobileNumber,drawAmount,Constant.SMS_TEMPLATE.CASH_ACCOUNT_DRAWMONEY.name());
	}
	
	public void sendCashAccountRefundSms(String mobileNumber,Long refundAmount) {
		sendCashAccountSms(mobileNumber,refundAmount,Constant.SMS_TEMPLATE.CASH_ACCOUNT_REFUNDMENT.name());
	}
	
	@Override
	public CashPay payFromMoneyAccount(Long orderId, Long userId,
			Long payAmount, String serial) {
		CashAccount cashAccount=cashAccountDAO.getCashAccountByUserId(userId);
		CashPay cashPay = new CashPay();
		cashPay.setAmount(payAmount);
		cashPay.setCreateTime(new Date());
		cashPay.setSerial(serial);
		cashPay.setOutTradeNo(serial);
		cashPay.setCashAccountId(cashAccount.getCashAccountId());
		cashPay.setStatus(FINC_CASH_STATUS.PayCashSuccess.name());
		cashPay.setOrderId(orderId);
		cashPay.setPayFrom(CashPay.PayFrom.MONEY);
		Long cashPayId = cashPayDAO.insert(cashPay);
		this.payFromMoneyAccount(userId, cashPayId, payAmount);
		return cashPay;
	}
	
	/**
	 * 使用奖金支付
	 * @param orderId 订单ID
	 * @param userId 用户ID
	 * @param payAmount 支付金额 
	 * @param serial 支付交易号
	 * @return
	 */
	private CashPay payFromBonusAccount(Long orderId, Long userId,Long payAmount, String serial){
		CashAccount cashAccount=cashAccountDAO.getCashAccountByUserId(userId);
		CashPay cashPay = new CashPay();
		cashPay.setAmount(payAmount);
		cashPay.setCreateTime(new Date());
		cashPay.setSerial(serial);
		cashPay.setOutTradeNo(serial);
		cashPay.setCashAccountId(cashAccount.getCashAccountId());
		cashPay.setStatus(FINC_CASH_STATUS.PayCashSuccess.name());
		cashPay.setOrderId(orderId);
		cashPay.setPayFrom(CashPay.PayFrom.BONUS);
		cashPay.setBonusFrom(CashPay.BonusFrom.OLD.name());
		Long cashPayId = cashPayDAO.insert(cashPay);
		this.payFromBonusAccount(userId, cashPayId, payAmount);
		return cashPay;
	}
	
	/**
	 * 使用新奖金支付
	 * @param orderId 订单ID
	 * @param userId 用户ID
	 * @param payAmount 支付金额 
	 * @param serial 支付交易号
	 * @return
	 */
	private CashPay payFromNewBonusAccount(Long orderId, Long userId,Long payAmount, String serial){
		CashAccount cashAccount=cashAccountDAO.getCashAccountByUserId(userId);
		CashPay cashPay = new CashPay();
		cashPay.setAmount(payAmount);
		cashPay.setCreateTime(new Date());
		cashPay.setSerial(serial);
		cashPay.setOutTradeNo(serial);
		cashPay.setCashAccountId(cashAccount.getCashAccountId());
		cashPay.setStatus(FINC_CASH_STATUS.PayCashSuccess.name());
		cashPay.setOrderId(orderId);
		cashPay.setPayFrom(CashPay.PayFrom.BONUS);
		cashPay.setBonusFrom(CashPay.BonusFrom.NEW.name());
		Long cashPayId = cashPayDAO.insert(cashPay);
		this.payFromNewBonusAccount(userId, cashPayId, payAmount);
		return cashPay;
	}
	
	
	@Override
	public Long payFromBonus(Long userId,Long orderId,String bizType,Long payAmount) {
		
		cashAccountDAO.protect(String.valueOf(orderId),Constant.ComeFrom.BONUS_BALANCE_PAY.toString()); 
		
		CashAccountVO cashAccount=this.queryMoneyAccountByUserId(userId);
		
		Long actualBonusPay=0L;//奖金实际支付金额
		
		Long bonusBalance=cashAccount.getBonusBalance();//奖金余额
		
		if(payAmount>0&&bonusBalance>0){//使用奖金支付
			if(bonusBalance>payAmount){
				actualBonusPay=payAmount;
			}else{
				actualBonusPay=bonusBalance;
			}
			if(payAmount<actualBonusPay) {
				actualBonusPay=payAmount;
			}
			//保存奖金支付记录
			PayPayment bonusPayment = new PayPayment();
			bonusPayment.setBizType(bizType);
			bonusPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			bonusPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			bonusPayment.setObjectId(orderId);
			bonusPayment.setPaymentGateway(Constant.PAYMENT_GATEWAY.CASH_BONUS.name());
			bonusPayment.setAmount(actualBonusPay);
			bonusPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			bonusPayment.setSerial(bonusPayment.geneSerialNo());
			bonusPayment.setCreateTime(new Date());
			bonusPayment.setCallbackTime(new Date());
			bonusPayment.setPaymentTradeNo(bonusPayment.getSerial());
			bonusPayment.setGatewayTradeNo(bonusPayment.getSerial());
			Long bonusPaymentId = payPaymentService.savePayment(bonusPayment);
			if(null!=bonusPaymentId){
				if (LOG.isDebugEnabled()) {
					LOG.debug("Use bonus pay , userId is "+userId+", orderId is "+orderId+" , actual pay is "+actualBonusPay);
				}
				//账户余额支付
				this.payFromBonusAccount(orderId,userId,actualBonusPay,bonusPayment.getSerial());
			}
			//发送支付短信
			sendCashAccountSms(cashAccount.getMobileNumber(),actualBonusPay,Constant.SMS_TEMPLATE.CASH_BONUS_PAY.name());
			return bonusPaymentId;
		}
		return 0L;
	}
	
	
	
	@Override
	public List<Long> payFromBonus(Long userId,Long orderId,String bizType,Long oldPayAmount,Long newPayAmount) {
		
		cashAccountDAO.protect(String.valueOf(orderId),Constant.ComeFrom.BONUS_BALANCE_PAY.toString()); 
		
		CashAccountVO cashAccount=this.queryMoneyAccountByUserId(userId);
		
		List<Long> paymentIdList=new ArrayList<Long>();
		
		//老奖金扣减
		Long actualBonusPay=0L;//奖金实际支付金额
		Long bonusBalance=cashAccount.getBonusBalance();//奖金余额
		if(oldPayAmount>0&&bonusBalance>0){//使用奖金支付
			if(bonusBalance>oldPayAmount){
				actualBonusPay=oldPayAmount;
			}else{
				actualBonusPay=bonusBalance;
			}
			if(oldPayAmount<actualBonusPay) {
				actualBonusPay=oldPayAmount;
			}
			//保存奖金支付记录
			PayPayment bonusPayment = new PayPayment();
			bonusPayment.setBizType(bizType);
			bonusPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			bonusPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			bonusPayment.setObjectId(orderId);
			bonusPayment.setPaymentGateway(Constant.PAYMENT_GATEWAY.CASH_BONUS.name());
			bonusPayment.setAmount(actualBonusPay);
			bonusPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			bonusPayment.setSerial(bonusPayment.geneSerialNo());
			bonusPayment.setCreateTime(new Date());
			bonusPayment.setCallbackTime(new Date());
			bonusPayment.setPaymentTradeNo(bonusPayment.getSerial());
			bonusPayment.setGatewayTradeNo(bonusPayment.getSerial());
			Long bonusPaymentId = payPaymentService.savePayment(bonusPayment);
			if(null!=bonusPaymentId){
				if (LOG.isDebugEnabled()) {
					LOG.debug("Use bonus pay , userId is "+userId+", orderId is "+orderId+" , actual pay is "+actualBonusPay);
				}
				paymentIdList.add(bonusPaymentId);
				//账户余额支付
				this.payFromBonusAccount(orderId,userId,actualBonusPay,bonusPayment.getSerial());
			}
		}
		
		//新奖金扣减
		Long newActualBonusPay=0L;//新奖金实际支付金额
		Long newBonusBalance=cashAccount.getNewBonusBalance();//新奖金余额
		if(newPayAmount>0&&newBonusBalance>0){
			if(newBonusBalance>newPayAmount){
				newActualBonusPay=newPayAmount;
			}else{
				newActualBonusPay=newBonusBalance;
			}
			if(newPayAmount<newActualBonusPay) {
				newActualBonusPay=newPayAmount;
			}
			//保存奖金支付记录
			PayPayment newBonusPayment = new PayPayment();
			newBonusPayment.setBizType(bizType);
			newBonusPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			newBonusPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			newBonusPayment.setObjectId(orderId);
			newBonusPayment.setPaymentGateway(Constant.PAYMENT_GATEWAY.CASH_BONUS.name());
			newBonusPayment.setAmount(newActualBonusPay);
			newBonusPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			newBonusPayment.setSerial(newBonusPayment.geneSerialNo());
			newBonusPayment.setCreateTime(new Date());
			newBonusPayment.setCallbackTime(new Date());
			newBonusPayment.setPaymentTradeNo(newBonusPayment.getSerial());
			newBonusPayment.setGatewayTradeNo(newBonusPayment.getSerial());
			Long newBonusPaymentId = payPaymentService.savePayment(newBonusPayment);
			if(null!=newBonusPaymentId){
				if (LOG.isDebugEnabled()) {
					LOG.debug("Use new bonus pay , userId is "+userId+", orderId is "+orderId+" , actual pay is "+newActualBonusPay);
				}
				paymentIdList.add(newBonusPaymentId);
				//账户余额支付
				this.payFromNewBonusAccount(orderId,userId,newActualBonusPay,newBonusPayment.getSerial());
			}
		}
		
		//发送支付短信
		sendCashAccountSms(cashAccount.getMobileNumber(),actualBonusPay+newActualBonusPay,Constant.SMS_TEMPLATE.CASH_BONUS_PAY.name());
		
		return paymentIdList;
	}
	
	
	
	@Override
	public List<Long> payFromCashAccount(Long userId,Long orderId,String bizType,Long payAmount,Long bonusPay) {
		cashAccountDAO.protect(orderId+"_"+DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"),Constant.ComeFrom.PAY.toString()); 

		List<Long> paymentList=new ArrayList<Long>();
		
		CashAccountVO cashAccount=this.queryMoneyAccountByUserId(userId);
		
		Long actualBonusPay=0L;//奖金实际支付金额
		
		Long actualPayAmount=0L;//现金账户实际支付金额
		
		Long bonusBalance=cashAccount.getBonusBalance();//奖金余额
		
		if(payAmount>0&&bonusPay>0&&bonusBalance>0){//使用奖金支付
			if(bonusBalance>bonusPay){
				actualBonusPay=bonusPay;
			}else{
				actualBonusPay=bonusBalance;
			}
			if(payAmount<actualBonusPay) {
				actualBonusPay=payAmount;
			}
			
			//保存奖金支付记录
			PayPayment bonusPayment = new PayPayment();
			bonusPayment.setBizType(bizType);
			bonusPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			bonusPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			bonusPayment.setObjectId(orderId);
			bonusPayment.setPaymentGateway(Constant.PAYMENT_GATEWAY.CASH_BONUS.name());
			bonusPayment.setAmount(actualBonusPay);
			bonusPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			bonusPayment.setSerial(bonusPayment.geneSerialNo());
			bonusPayment.setCreateTime(new Date());
			bonusPayment.setCallbackTime(new Date());
			bonusPayment.setPaymentTradeNo(bonusPayment.getSerial());
			bonusPayment.setGatewayTradeNo(bonusPayment.getSerial());
			Long bonusPaymentId = payPaymentService.savePayment(bonusPayment);
			if(null!=bonusPaymentId){
				if (LOG.isDebugEnabled()) {
					LOG.debug("Use bonus pay , userId is "+userId+", orderId is "+orderId+" , actual pay is "+actualBonusPay);
				}
				//账户余额支付
				this.payFromBonusAccount(orderId,userId,actualBonusPay,bonusPayment.getSerial());
				paymentList.add(bonusPaymentId);
			}
		}
		
		//重新计算剩余还需要支付金额
		payAmount=payAmount-actualBonusPay;
		
		if(payAmount>0&&cashAccount.getMaxPayMoney()>0){//使用账户余额支付
			if(cashAccount.getMaxPayMoney()>payAmount){
				actualPayAmount = payAmount;
			}else{
				actualPayAmount = cashAccount.getMaxPayMoney();
			}
			PayPayment payment = new PayPayment();
			payment.setBizType(bizType);
			payment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			payment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payment.setObjectId(orderId);
			payment.setPaymentGateway(Constant.PAYMENT_GATEWAY.CASH_ACCOUNT.name());
			payment.setAmount(actualPayAmount);
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payment.setSerial(payment.geneSerialNo());
			payment.setCreateTime(new Date());
			payment.setCallbackTime(new Date());
			payment.setPaymentTradeNo(payment.getSerial());
			payment.setGatewayTradeNo(payment.getSerial());
			Long paymentId = payPaymentService.savePayment(payment);
			if(null != paymentId){
				if (LOG.isDebugEnabled()) {
					LOG.debug("Use money pay , userId is "+userId+", orderId is "+orderId+" , actual pay is "+actualPayAmount);
				}
				//账户余额支付
				this.payFromMoneyAccount(orderId,userId,actualPayAmount,payment.getSerial());
				paymentList.add(paymentId);
			}
		}
		
		//发送支付短信
		sendCashAccountPaySms(cashAccount.getMobileNumber(), actualPayAmount,actualBonusPay);
		
		return paymentList;
	}
	
	
	
	@Override
	public void returnBonusForOrderComment(OrderAndComment orderAndComment){
		Long returnAmount=orderAndComment.getCashRefund();
		Long userId=orderAndComment.getUserId();
		String businessId=orderAndComment.getOrderId();
		String bonusFrom=Constant.BonusOperation.ORDER_AND_COMMENT.name();
		//产生业务唯一标示:来源+点评id+订单id
		String protectId=bonusFrom+orderAndComment.getCommentId()+orderAndComment.getOrderId();
		//奖金返现
		retrunBonus(returnAmount, userId, businessId, bonusFrom, protectId,ComeFrom.BONUS_RETURN.toString());
	}

	
	@Override
	public void returnOrderManualAdjust(OrderAndComment orderAndComment){
		Long returnAmount = orderAndComment.getCashRefund();
		Long userId = orderAndComment.getUserId();
		String businessId=orderAndComment.getOrderId();
		String bonusFrom = Constant.BonusOperation.ORDER_MANUAL_ADJUST.name();
		String protectId=bonusFrom+orderAndComment.getCommentId()+orderAndComment.getOrderId()+ System.currentTimeMillis();
		retrunBonus(returnAmount,userId,businessId,bonusFrom,protectId,ComeFrom.BONUS_MANUAL_ADJUST_RETURN.toString());
	}
	

	@Override
	public void returnBonusForPCActivity(Long returnAmount, Long userId,String activityId) {
		String bonusFrom=Constant.BonusOperation.ACTIVITY.name();
		//产生业务唯一标示:来源+活动id+用户id+时间
		String protectId=bonusFrom+activityId+userId+System.currentTimeMillis();
		//奖金返现
		retrunBonus(returnAmount, userId, activityId, bonusFrom, protectId,ComeFrom.ACTIVITY.name());
	}

	/**
	 * 奖金返现
	 * @param returnAmount 返现金额(分)
	 * @param userId 用户id
	 * @param businessId 业务id（如订单ID）
	 * @param bonusFrom 返现来源 @see com.lvmama.comm.vo.Constant.BonusOperation
	 * @param protectId 奖金返现唯一标示
	 * @param comeFrom 奖金返现类型 @see com.lvmama.comm.vo.Constant.ComeFrom
	 */
	private void retrunBonus(Long returnAmount, Long userId, String businessId,String bonusFrom, String protectId,String comeFrom) {
	    	cashAccountDAO.protect(protectId,comeFrom);
		
		if(returnAmount<=0){
			LOG.error("return bonus must be greater than zero");
			throw new RuntimeException("return bonus must be greater than zero"); 
		}
		
		CashAccount cashAccount=this.queryOrCreateCashAccountByUserId(userId);
		
		//记录奖金返现记录
		CashBonusReturn cashBonusReturn=new CashBonusReturn();
		cashBonusReturn.setBusinessId(businessId);
		cashBonusReturn.setBonus(returnAmount);
		cashBonusReturn.setCashAccountId(cashAccount.getCashAccountId());
		cashBonusReturn.setComeFrom(bonusFrom);
		cashBonusReturn.setCreateDate(new Date());
		cashBonusReturnDAO.insert(cashBonusReturn);
		
		//奖金账户变更
		cashChangeDAO.balanceChange(cashAccount.getCashAccountId(), returnAmount,comeFrom,String.valueOf(cashBonusReturn.getReturnId()));
		
		//更新奖金余额
		cashAccountDAO.updateNewBonusBalance(cashAccount.getCashAccountId(), returnAmount);
		LOG.info("update new bonus balance,account id:"+cashAccount.getCashAccountId()+",return amount:"+returnAmount);
		
		//校验奖金余额非负数
		final CashAccountVO checkCashAccount= this.queryMoneyAccountByUserId(userId);
		if (checkCashAccount.getNewBonusBalance() < 0) {
			LOG.error("bonus balance will be negative: " + userId);
			throw new RuntimeException("bonus balance will be negative: "+ userId);
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("userId: " + userId+",return bonus amount: " + returnAmount);
		}
	}
	/**
	 * 分页查询返现信息
	 */
	public List<CashBonusReturn> queryBonusReturn(final Long userId,final Integer beginIndex, final Integer endIndex){
		return cashBonusReturnDAO.queryBonusReturn(userId, beginIndex, endIndex);
	}
	/**
	 * 查询返现信息的数量
	 */
	public Long getBonusReturnCount(final Long userId){
		return cashBonusReturnDAO.getBonusReturnCount(userId);
	}
	
	@Override
	public CashMoneyDraw getFincMoneyDrawByPK(Long moneyDrawId) {
		return cashMoneyDrawDAO.selectByPrimaryKey(moneyDrawId);
	}
	@Override
	public void updateCashMoneyDrawPayStatusByPK(CashMoneyDraw cashMoneyDraw,
			String status,
			String operatorName) {
		cashMoneyDraw.setPayStatus(status);
		cashMoneyDrawDAO.updateByPrimaryKey(cashMoneyDraw);
		insertLog("CASH_MONEY_DRAW", cashMoneyDraw.getCashAccountId(),
				cashMoneyDraw.getCashAccountId(), operatorName, "changeStatus",
				"更改提现记录", "更改payStatus = "+cashMoneyDraw.getPayStatusName());
	}
	@Override
	public boolean callbackForDrawMoneyHandle(String serialNo, String tradeNo,
			boolean isSuccess, String payStatus, String memo) {
		LOG.info("callbackForDrawMoneyHandle:serialNo="+serialNo+", tradeNo="+tradeNo+", isSuccess="+isSuccess
				+", payStatus="+payStatus+", memo="+memo);
		boolean retStatus=false;
		if(!Constant.FINC_CASH_STATUS.PayCashFailedByUnfreeze.name().equals(payStatus)){
			retStatus=callbackForDrawMoney(serialNo, tradeNo, isSuccess, payStatus, memo);
		}
		CashDraw cashDraw = findCashDrawBySerial(serialNo);
		CashMoneyDraw cashMoneyDraw = queryCashMoneyDraw(cashDraw.getMoneyDrawId());
		if(retStatus){
			//记录打款成功日志
			insertLog("CASH_MONEY_DRAW", cashMoneyDraw.getCashAccountId(), cashMoneyDraw.getMoneyDrawId(), "SYSTEM",
					COM_LOG_CASH_EVENT.drawMoneyDone.name(), COM_LOG_CASH_EVENT.drawMoneyDone.name(),  "打款成功");
		}else{
			if(Constant.FINC_CASH_STATUS.PayCashFailedByUnfreeze.name().equals(payStatus)){
				//记录批次余额不足
				insertLog("CASH_MONEY_DRAW", cashMoneyDraw.getCashAccountId(), cashMoneyDraw.getMoneyDrawId(), "SYSTEM",
						COM_LOG_CASH_EVENT.drawMoneyDone.name(), COM_LOG_CASH_EVENT.drawMoneyDone.name(),  "余额不足");
			}else{
				//记录打款失败
				insertLog("CASH_MONEY_DRAW", cashMoneyDraw.getCashAccountId(), cashMoneyDraw.getMoneyDrawId(), "SYSTEM",
						COM_LOG_CASH_EVENT.drawMoneyDone.name(), COM_LOG_CASH_EVENT.drawMoneyDone.name(),  "打款失败");
			}
		}
		return retStatus;
	}
	@Override
	public boolean manualHandle(boolean manualHandleFlag, Long cashMoneyDrawId,
			String userName) {
		LOG.info("manualHandle:manualHandleFlag="+manualHandleFlag+", cashMoneyDrawId="+cashMoneyDrawId+", userName="+userName);
		CashDraw cashDraw = initCashDraw(cashMoneyDrawId,userName);
		CashMoneyDraw cashMoneyDraw = cashMoneyDrawDAO.selectByPrimaryKey(cashMoneyDrawId);
		if(checkPayStatus(cashMoneyDraw.getPayStatus(), cashMoneyDrawId)){
			if(manualHandleFlag){
				callbackForDrawMoney(cashDraw.getSerial(), cashDraw.getGatewayTradeNo(), true, Constant.FINC_CASH_STATUS.PayCashSuccess.name(), null);
				//记录打款成功日志
				insertLog("CASH_MONEY_DRAW", cashMoneyDraw.getCashAccountId(), cashMoneyDraw.getMoneyDrawId(),userName,
						COM_LOG_CASH_EVENT.drawMoneyDone.name(), "手工处理成功",  "打款成功");
			}else{
				callbackForDrawMoney(cashDraw.getSerial(), cashDraw.getGatewayTradeNo(), false, Constant.FINC_CASH_STATUS.PayCashFailed.toString(), "");
				//记录打款失败
				insertLog("CASH_MONEY_DRAW", cashMoneyDraw.getCashAccountId(), cashMoneyDraw.getMoneyDrawId(), userName,
						COM_LOG_CASH_EVENT.drawMoneyDone.name(), "手工处理失败",  "打款失败");
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 初始化cashDraw
	 * <br>cashMoneyDraw状态为"打款申请已提交"且cashDraw未生成，生成一个新的cashDraw
	 * @param cashMoneyDrawId
	 * @param userName
	 * @param flag
	 * @return
	 */
	private CashDraw initCashDraw(Long cashMoneyDrawId,String userName){
		CashMoneyDraw cashMoneyDraw=cashMoneyDrawDAO.selectByPrimaryKey(cashMoneyDrawId);
		CashDraw cashDraw = this.findCashDrawByMoneyDrawId(cashMoneyDrawId);
		if(Constant.FINC_CASH_STATUS.ApplyPayCash.name().equals(cashMoneyDraw.getPayStatus()) && cashDraw==null){
			cashDraw = new CashDraw();
			cashDraw.setAmount(cashMoneyDraw.getDrawAmount());
			cashDraw.setCreateTime( new Date() );
			cashDraw.setMoneyDrawId(cashMoneyDraw.getMoneyDrawId());
			cashDraw.setOperatorName(userName);
			cashDraw.setSerial(GeneralSequenceNo.generateSerialNo());

			cashDraw.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
			cashMoneyDraw.setPayStatus(Constant.FINC_CASH_STATUS.ApplyPayCashRejected.name());
			cashDrawDAO.insert(cashDraw);
			cashMoneyDrawDAO.updateByPrimaryKey(cashMoneyDraw);
			
			List<PayPaymentRefundment> refundList =  payPaymentRefundmentService.selectPayRefundmentListByObjectIdAndObjectTypeAndBizType(
					cashMoneyDrawId, Constant.PAYMENT_OBJECT_TYPE.CASH_MONEY_DRAW.name(), Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name());
			if(refundList!=null && refundList.size()>0){
				PayPaymentRefundment payRefundment=refundList.get(0);
				payRefundment.setStatus(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());	
				payRefundment.setCallbackTime(new Date());
				payRefundment.setSerial(cashDraw.getSerial());
				payPaymentRefundmentService.updatePyamentRefundmentAndPayPayPayment(payRefundment, null);
			}
		}
		return cashDraw;
	}
	/**
	 * cashMoneyDraw状态检查（ApplyPayCashSuccess或ApplyPayCashRejected返回true）
	 * @param payStatus
	 * @param moneyDrawId
	 * @return
	 */
	private boolean checkPayStatus(final String payStatus,
			final Long moneyDrawId) {
		boolean flag = true;
		if (!payStatus.equals(FINC_CASH_STATUS.ApplyPayCashSuccess.toString()) 
				&&!payStatus.equals(FINC_CASH_STATUS.ApplyPayCashRejected.toString())) {
				LOG.error("the cashMoneyDraw " + moneyDrawId
						+ " has been processed");
			flag = false;
		}
		return flag;
	}
	private boolean callbackForDrawMoney(String serialNo,String tradeNo,boolean isSuccess,String payStatus, String memo) {
		Date now=new Date();
		PayPaymentRefundment refundment=this.payPaymentRefundmentService.selectPaymentRefundmentBySerial(serialNo);
		CashDraw cashDraw = this.findCashDrawBySerial(serialNo);
		CashMoneyDraw cashMoneyDraw = this.queryCashMoneyDraw(cashDraw.getMoneyDrawId());
		CashAccount cashAccount = this.queryCashAccountByPk(cashMoneyDraw.getCashAccountId());
		cashDraw.setGatewayTradeNo(tradeNo);
		cashDraw.setCallbackTime(now);
		if(isSuccess){
			cashDraw.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			cashMoneyDraw.setPayStatus(Constant.FINC_CASH_STATUS.PayCashSuccess.name());
			
			cashMoneyDrawDAO.updateByPrimaryKey(cashMoneyDraw);
			cashDrawDAO.updateByPrimaryKey(cashDraw);
			
			PayTransaction payTransaction = new PayTransaction();
			payTransaction.setSerial(cashDraw.getSerial());
			payTransaction.setAmount(cashDraw.getAmount());
			payTransaction.setGatewayTradeNo(cashDraw.getGatewayTradeNo());
			payTransaction.setMemo(cashDraw.getCallbackInfo());
			payTransaction.setObjectId(cashDraw.getCashDrawId());
			payTransaction.setObjectType("FINC_CASH_DRAW");
			payTransaction.setPayee(cashMoneyDraw.getBankAccountName());
			payTransaction.setPayer("LVMAMA");
			payTransaction.setPaymentGateway(Constant.PAYMENT_GATEWAY.ALIPAY.name());
			payTransaction.setPaymentType("ONLINE");
			payTransaction.setTransactionType(Constant.TRANSCATION_TYPE.DRAWCASH.name());
			payTransaction.setTransTime(cashDraw.getTransTime());
			payTransaction.setCreateTime(now);
			
			payPaymentService.savePayTransaction(payTransaction);
			cashFreezeQueueDAO.unfreezeDrawAmount(cashMoneyDraw.getMoneyDrawId());
			unfreezeAndDraw(cashAccount.getUserId(), cashDraw.getCashDrawId(), cashDraw.getAmount());
			sendCashAccountDrawSms(cashAccount.getMobileNumber(), cashMoneyDraw.getDrawAmount());
			
			refundment.setStatus(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
			this.payPaymentRefundmentService.updatePyamentRefundment(refundment);
			return true;
			
		}else{
			cashMoneyDraw.setPayStatus(payStatus);
			cashDraw.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
			cashDraw.setCallbackInfo(cashDraw.getCallbackInfo()==null?memo:cashDraw.getCallbackInfo()+memo);
			cashMoneyDrawDAO.updateByPrimaryKey(cashMoneyDraw);
			cashDrawDAO.updateByPrimaryKey(cashDraw);
			
			cashFreezeQueueDAO.unfreezeDrawAmount(cashMoneyDraw.getMoneyDrawId());
			refundment.setStatus(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			refundment.setCallbackInfo(memo);
			this.payPaymentRefundmentService.updatePyamentRefundment(refundment);
			return false;
		}
	}
	/**
	 * 根据businessId和comeFrom查询记录数.
	 * @param businessId
	 * @param comeFrom
	 * @return
	 */
	public Long selectProtectCount(String businessId,String comeFrom) {
		return cashAccountDAO.selectProtectCount(businessId, comeFrom);
	}
	
	@Override
	public Long queryMoneyDrowCountByBankAccount(String bankAccountName) {
		return cashMoneyDrawDAO.queryMoneyDrowCountByBankAccount(bankAccountName);
	}
	
	@Override
	public void updateLastPayValidateTime(Long userId,Date lastPayValidateTime) {
		cashAccountDAO.updateLastPayValidateTime(userId, lastPayValidateTime);
	}
	
	@Override
	public void changeCashAccountValidByParams(Long userId, String valid, String memo, String operatorName) {
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("userId", userId);
		map.put("valid", valid);
		map.put("memo", memo);
		cashAccountDAO.changeCashAccountValidByParams(map);
		CashAccount ca = cashAccountDAO.getCashAccountByUserId(userId);
		if(ca != null) {
			ComLog log = new ComLog();
			log.setObjectType("CASH_ACCOUNT");
			log.setObjectId(ca.getCashAccountId());
			log.setOperatorName(operatorName);
			log.setLogType("CHANGE_CASH_ACCOUNT_VALID");
			log.setLogName("CHANGE_CASH_ACCOUNT_VALID");
			comLogDAO.insert(log);
		}
	}
	
	/**
	 * 手机是否唯一
	 *
	 * @param mobile 手机号
	 * @return 唯一手机返回真，否则返回假
	 */
	public boolean isMobileRegistrable(final String mobile) {
		if (!StringUtil.validMobileNumber(mobile)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(mobile + "未能通过正则表达式检验，将返回false");
			}
			return false;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobileNumber", mobile);
		List<CashAccount> list = cashAccountDAO.queryCashAccountByParam(params);
		if(!list.isEmpty() && list.size() > 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public void returnBonus4ElongOrder(MobileHotelOrder mobileHotelOrder,final Long userId) {
		Long returnAmount=mobileHotelOrder.getRefundAmount();
		String businessId=mobileHotelOrder.getOrderId()+"";
		String bonusFrom=Constant.BonusOperation.ELONG_ORDER_REFUND.name();
		//产生业务唯一标示:来源+驴途订单（自己生产的order_id）id+艺龙订单id(艺龙生产的订单id)
		String protectId=bonusFrom + mobileHotelOrder.getLvHotelOrderId() + mobileHotelOrder.getOrderId();
		//奖金返现
		retrunBonus(returnAmount, userId, businessId, bonusFrom, protectId,ComeFrom.ELONG_ORDER_BONUS_RETURN.toString());
	}

/**
	 * 客户端活动奖金返现 . 
	 * @param returnAmount  
	 * @param userNo
	 * @param businessId businessId  不能重复
	 */
	public boolean returnBonus4ClientActivity(Long returnAmount, final String userNo, String businessId){
		boolean b = false;
		String bonusFrom=Constant.BonusOperation.CLIENT_ACTIVITY_REFUND.name();
		//产生业务唯一标示:来源+businessId+userNo；
		String protectId=bonusFrom + businessId;
		try{
			UserUser u = userUserProxy.getUserUserByUserNo(userNo);//userNo;
			 if(null != u && null != u.getId()) {
				 Long userId = u.getId();
				 //奖金返现
				 retrunBonus(returnAmount, userId, businessId, bonusFrom, protectId,ComeFrom.CLIENT_ACTIVITY_BONUS_RETURN.toString());
				 b = true;
			 }
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		 
		 return b;
	}
	/**
	 * 获取用户某个时间区间使用成功使用奖金支付订单次数
	 * @param userId 用户id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 奖金支付订单次数
	 */
	private Long getBonusPayCount(Long userId,Date startTime,Date endTime){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("payFrom", CashPay.PayFrom.BONUS.name());
		paramMap.put("startTime", DateUtil.formatDate(startTime, DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
		paramMap.put("endTime", DateUtil.formatDate(endTime, DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
		return cashPayDAO.getCashPayCount(paramMap);
	}
	
	@Override
	public boolean canUseBonusPay(Long userId) {
		
		//支付次数
		Long counts=0L;
		
		//一年以内支付次数校验
		Calendar calYear = Calendar.getInstance();
		calYear.setTime(new Date());
		calYear.add(Calendar.YEAR, -1);
		counts=getBonusPayCount(userId,calYear.getTime(),new Date());
		if(counts>=BONUS_PAY_MAX_COUNT_OF_YEAR){
			return false;
		}
		
		//一个月以内支付次数校验
		Calendar calMonth = Calendar.getInstance();
		calMonth.setTime(new Date());
		calMonth.add(Calendar.MONTH, -1);
		counts=getBonusPayCount(userId,calMonth.getTime(),new Date());
		if(counts>=BONUS_PAY_MAX_COUNT_OF_MONTH){
			return false;
		}
		
		return true;
	}
	
}
