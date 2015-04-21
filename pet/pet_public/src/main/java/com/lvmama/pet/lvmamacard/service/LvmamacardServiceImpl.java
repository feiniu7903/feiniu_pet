package com.lvmama.pet.lvmamacard.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardIn;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardOut;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardOutDetails;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.lvmamacard.LvmamaCardStatistics;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.lvmamacard.DESUtils;
import com.lvmama.comm.utils.lvmamacard.LvmamaCardUtils;
import com.lvmama.comm.utils.lvmamacard.RandomNumberGeneratorUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ComeFrom;
import com.lvmama.comm.vo.Constant.STORED_CARD_ENUM;
import com.lvmama.pet.lvmamacard.dao.InStoregeDAO;
import com.lvmama.pet.lvmamacard.dao.LvmamaStoredCardDao;
import com.lvmama.pet.lvmamacard.dao.OutStoregeDAO;
import com.lvmama.pet.lvmamacard.dao.OutStoregeDetailsDAO;
import com.lvmama.pet.money.dao.CashAccountDAO;
import com.lvmama.pet.money.dao.StoredCardUsageDAO;

public class LvmamacardServiceImpl implements LvmamacardService {

	private static final Logger LOG = Logger.getLogger(LvmamacardServiceImpl.class);
	
	@Autowired
	private PayPaymentService payPaymentService;
	@Autowired
	private ComLogService comLogService;
	
	@Autowired
	private InStoregeDAO inStoregeDAO;
	@Autowired
	private LvmamaStoredCardDao lvmamaStoredCardDao;
	@Autowired
	private OutStoregeDAO outStoregeDAO;
	@Autowired
	private OutStoregeDetailsDAO outStoregeDetailsDAO;
	@Autowired
	private StoredCardUsageDAO storedCardUsageDAO;
	@Autowired
	protected CashAccountDAO cashAccountDAO;	
	
	@Override
	public long countByParamForInStorege(Map param) {
		return (Long) inStoregeDAO.countByParamForInStorege(param);
	}

	@Override
	public List<StoredCardIn> queryByParamForInStorege(Map param) {
		return (List<StoredCardIn>) inStoregeDAO.queryByParamForInStorege(param);
	}

	@Override
	public String getIncodeForInStorege(Integer amount) {
		return inStoregeDAO.getIncodeForInStorege(amount);
	}

	@Override
	public void insertStoredCardInForInStorege(StoredCardIn storedCardIn) {
		inStoregeDAO.insertStoredCardInForInStorege(storedCardIn);
	}

	@Override
	public void batchinsertLvmamaStoredCardForLvmamaStoredCard(
			List<LvmamaStoredCard> list) {
		lvmamaStoredCardDao.batchinsertLvmamaStoredCardForLvmamaStoredCard(list);

	}

	@Override
	public String getLastCardNoByAmountForLvmamaStoredCard(Integer amount3) {
		return lvmamaStoredCardDao
				.getLastCardNoByAmountForLvmamaStoredCard(amount3);
	}

	@Override
	public void insertLvmamaStoredCard(LvmamaStoredCard lvmamastoredcard) {
		lvmamaStoredCardDao.inserinsertLvmamaStoredCardt(lvmamastoredcard);
	}
 
	/**
	 * 创建卡
	 */
	@Override
	public void insertStoredCardInAndbatchInsertStoredCard(Integer amount,
			Integer count,Date validDate) {
		// 批次号
		String batchCode = generalIncode(amount);
		// 增加卡
		StoredCardIn cardFanwei = generalBatchCard(amount, count, batchCode,validDate);

		StoredCardIn storedCardIn = new StoredCardIn();
		storedCardIn.setAmount(amount);
		storedCardIn.setInCount(count);
		storedCardIn.setInStatus(Integer.valueOf(Constant.CARD_IN_STATUS.one
				.getCode()));
		storedCardIn.setInCode(batchCode);
		storedCardIn.setCardNoBegin(cardFanwei.getCardNoBegin());
		storedCardIn.setCardNoEnd(cardFanwei.getCardNoEnd());
		this.insertStoredCardInForInStorege(storedCardIn);
	}

	/**
	 * 批量增加卡
	 * 
	 * @param amount2
	 * @param count2
	 * @param batchCode
	 * @return
	 * @author nixianjun 2013-11-26
	 */
	private StoredCardIn generalBatchCard(Integer amount2, Integer count2,
			String batchCode,Date validDate) {
		List<LvmamaStoredCard> list = new ArrayList<LvmamaStoredCard>();
		StoredCardIn cardFanwei2 = new StoredCardIn();
		// 最新的序号
		Integer lastNo = generalCardLastNo(amount2);
		DecimalFormat decimalformat = new DecimalFormat("00000000");
		List<String> passwordList = RandomNumberGeneratorUtils.getbatchPasswordList(count2);
		for (int i = 1; i <= count2; i++) {
			String cardCode = Constant.CARD_AMOUNT.getCardPreCode(amount2.toString()) + decimalformat.format(lastNo + i);
			LvmamaStoredCard lvmamastoredcard = new LvmamaStoredCard();
			lvmamastoredcard.setType(Integer.valueOf(Constant.CARD_TYPE.newed.getCode()));
			lvmamastoredcard.setAmount(amount2 * 100L);
			lvmamastoredcard.setBalance(amount2 * 100L);
			lvmamastoredcard.setCardBatchNo(batchCode);
			//lvmamastoredcard.setActiveStatus(Constant.STORED_CARD_ENUM.UNACTIVE.name());
			lvmamastoredcard.setStatus(Constant.CARD_STATUS.INITIALIZATION.name());
			lvmamastoredcard.setStockStatus(Constant.STORED_CARD_ENUM.NO_STOCK.name());
			lvmamastoredcard.setCardNo(cardCode);
			if(validDate!=null){
				lvmamastoredcard.setOverTime(validDate);
			}
			// 设置密码
			String password = passwordList.get(0);
			passwordList.remove(0);
			lvmamastoredcard.setPassword(DESUtils.getInstance().getEncString(
					password));
			if (i == 1) {
				cardFanwei2.setCardNoBegin(lvmamastoredcard.getCardNo());
			}
			if (i == count2) {
				cardFanwei2.setCardNoEnd(lvmamastoredcard.getCardNo());
			}
			list.add(lvmamastoredcard);
		}
		this.batchinsertLvmamaStoredCardForLvmamaStoredCard(list);

		return cardFanwei2;
	}

	/**
	 * 通过面值类型获取最新的card序列号
	 * 
	 * @param amount3
	 * @return
	 * @author nixianjun 2013-11-25
	 */
	private Integer generalCardLastNo(Integer amount3) {
		String code = this.getLastCardNoByAmountForLvmamaStoredCard(amount3);
		Integer serNo;
		if (code != null) {
			serNo = Integer.valueOf(code.substring(4, code.length()));
		} else {
			serNo = 0;
		}
		return serNo;
	}

	/**
	 * 获取入库号 role(比如 面值对应代号+8位数字)
	 * 
	 * @return
	 * @author nixianjun 2013-11-25
	 * @param amount2
	 */
	private String generalIncode(Integer amount2) {
		String code = this.getIncodeForInStorege(amount2);
		DecimalFormat decimalformat = new DecimalFormat("00000000");
		if (code != null) {
			code = Constant.CARD_AMOUNT.getCode(amount2.toString())+ decimalformat.format(Integer.valueOf(code.substring(1,code.length())) + 1);
		} else {
			code = Constant.CARD_AMOUNT.getCode(amount2.toString())+ "00000001";
		}
		return code;
	}
	@Override
	public long countByParamForLvmamaStoredCard(Map param) {
		return lvmamaStoredCardDao.countByParamForLvmamaStoredCard(param);
	}
	
	@Override
	public List<LvmamaStoredCard> queryByParamForLvmamaStoredCard(Map param) {
		return lvmamaStoredCardDao.queryByParamForLvmamaStoredCard(param);
	}
	

	/* 出库 */

	@Override
	public List<StoredCardOut> queryByParamForOutStorege(Map<String, Object> param) {
		return this.outStoregeDAO.queryByParamForOutStorege(param);
	}

	@Override
	public void insertStoredCardOutForOutStorege(StoredCardOut storedCardOut) {
		this.outStoregeDAO.insertStoredCardOutForOutStorege(storedCardOut);
	}

	@Override
	public void updateStoredCardOutForOutStorege(StoredCardOut storedCardOut) {
		this.outStoregeDAO.updateStoredCardOutForOutStorege(storedCardOut);
	}

	@Override
	public Long countByParamForOutStorege(Map<String, Object> param) {
		return this.outStoregeDAO.countByParamForOutStorege(param);
	}

	@Override
	public List<StoredCardOutDetails> queryByParamForOutStoregeDetails(
			Map<String, Object> param) {
		return this.outStoregeDetailsDAO.queryByParamForOutStoregeDetails(param);
	}

	@Override
	public String getOutCodeForOutStorege() {
		return this.outStoregeDAO.getIncodeForOutStorege();
	}

	@Override
	public void insertOutStoregeDetails(List<StoredCardOutDetails> list) {
		this.outStoregeDetailsDAO.insertOutStoregeDetails(list);
	}

	@Override
	public void updateOutStoregeDetails(StoredCardOutDetails details) {
		this.outStoregeDetailsDAO.updateOutStoregeDetails(details);
	}

	@Override
	public void deleteOutStoregeDetails(String outCode) {
		this.outStoregeDetailsDAO.deleteOutStoregeDetails(outCode);
	}

	public OutStoregeDetailsDAO getOutStoregeDetailsDAO() {
		return outStoregeDetailsDAO;
	}

	public void setOutStoregeDetailsDAO(OutStoregeDetailsDAO outStoregeDetailsDAO) {
		this.outStoregeDetailsDAO = outStoregeDetailsDAO;
	}

 
	/*					出库					*/
	
	public OutStoregeDAO getOutStoregeDAO() {
		return outStoregeDAO;
	}

	@Override
	public LvmamaStoredCard getOneStoreCardByCardNo(String cardNo) {
 		return this.lvmamaStoredCardDao.getOneStoreCardByCardNo(cardNo);
	}
 

	public void setOutStoregeDAO(OutStoregeDAO outStoregeDAO) {
		this.outStoregeDAO = outStoregeDAO;
	}

	@Override
	public void batchCancelLvmamaStoredCardByArray(String[] cardNoArray,String status) {
		Map map=new HashMap<String, Object>();
		map.put("cardNoArray", cardNoArray);
		map.put("status", status);
		updateByParamForLvmamaStoredCard(map);
	}
	@Override
	public void updateForInStorage(Map param){
		this.inStoregeDAO.updateByMap(param);
	}

	@Override
	public void cancelLvmamaStoredCardByInCode(String inCode, String status,String inStatus) {
		Map param=new HashMap<String, Object>();
		param.put("inCode", inCode);
		param.put("inStatus", Integer.valueOf(inStatus));
		this.inStoregeDAO.updateByMap(param);
		Map map=new HashMap<String, Object>();
		map.put("cardBatchNo", inCode);
		map.put("status", status);
		updateByParamForLvmamaStoredCard(map);
	}

	/**
	 * 
	 */
	@Override
	public void passLvmamaStoredCardByInCode(String inCode, String stockstatus,
			String inStatus) {
		Map param=new HashMap<String, Object>();
		param.put("inCode", inCode);
		param.put("inStatus", Integer.valueOf(inStatus));
		param.put("inDate", new Date());
		this.inStoregeDAO.updateByMap(param);
		Map map=new HashMap<String, Object>();
		map.put("cardBatchNo", inCode);
		map.put("stockStatus", stockstatus);
		map.put("intoTime", new Date());
		updateByParamForLvmamaStoredCard(map);
	}
	@Override
	public void updateByParamForLvmamaStoredCard(Map map){
		this.lvmamaStoredCardDao.updateByParam(map);
	}

	@Override
	public void deleteOutStorege(StoredCardOut storedCardOut) {
		this.outStoregeDAO.deleteStoredCardOutForOutStorege(storedCardOut);
	}

	@Override
	public void updateOutStoredCard(Map<String, Object> param) {
		this.lvmamaStoredCardDao.updateOutStoredCard(param);
	}

	@Override
	public List<LvmamaStoredCard> queryOutStoredBeginNoAndEndNo(
			Map<String, Object> param) {
		return this.lvmamaStoredCardDao.queryOutStoredBeginNoAndEndNo(param);
	}

	@Override
	public long countByParamForInStoreAndOutStore(Map param) {
 		return this.inStoregeDAO.countByParamForInStoreAndOutStore(param);
	}

	@Override
	public List<LvmamaCardStatistics> queryByParamForInStoreAndOutStore(Map param) {
 		return this.inStoregeDAO.queryByParamForInStoreAndOutStore(param);
	}

	@Override
	public LvmamaCardStatistics getLvmamaCardStatisticsByInCode(String inCode) {
 		return this.inStoregeDAO.getLvmamaCardStatisticsByInCode(inCode);
	}

	@Override
	public List<LvmamaStoredCard> queryOutStoredCardStatusByOutCode(Map<String, Object> param) {
		return  this.lvmamaStoredCardDao.queryOutStoredCardStatusByOutCode(param);
	}

	@Override
	public Long getCardCountByParamForInStorege(Map param) {
 		return this.inStoregeDAO.getCardCountByParamForInStorege(param);
	}
	
	
	
	/**
	 * 使用新储值卡 支付订单.
	 *
	 * @param lvmamaStoredCard
	 *            储值卡对象
	 * @param orderId
	 *            订单ID
	 * @param payAmount
	 *            订单金额
	 * @param operatorId
	 *            操作人
	 * @author zhangjie 2013-12-10
	 */
	@Override
	public Long payFromStoredCard(LvmamaStoredCard lvmamaStoredCard,Long orderId, String bizType, Long payAmount, Long operatorId) {
		
		// 订单应付.
		long actualPayAmount = payAmount;

		if (lvmamaStoredCard.getBalance() < payAmount) {
			actualPayAmount = lvmamaStoredCard.getBalance();
		}
		
		PayPayment payment = new PayPayment();
		payment.setBizType(bizType);
		payment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
		payment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
		payment.setObjectId(orderId);
		payment.setPaymentGateway(Constant.PAYMENT_GATEWAY.LYTXK_STORED_CARD.name());
		payment.setAmount(actualPayAmount);
		payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
		payment.setCallbackTime(new Date());
		payment.setSerial(payment.geneSerialNo());
		payment.setCreateTime(new Date());
		payment.setPaymentTradeNo(payment.getSerial());
		Long paymentId = payPaymentService.savePayment(payment);

		//更新储值卡余额
		lvmamaStoredCard.setBalance(lvmamaStoredCard.getBalance() - actualPayAmount);
		lvmamaStoredCard.setStatus(Constant.CARD_STATUS.USED.name());
		lvmamaStoredCardDao.updateByStoredCard(lvmamaStoredCard);

		// 保存储值卡消费记录.
		StoredCardUsage usage = new StoredCardUsage();
		usage.setAmount(actualPayAmount);
		usage.setCardId(lvmamaStoredCard.getStoredCardId());
		usage.setCreateTime(new Date());
		usage.setOperator(operatorId+"");
		usage.setOrderId(orderId);
		usage.setSerial(payment.getSerial());
		usage.setUsageType(Constant.STORED_CARD_ENUM.STORED_PAY.name());
		storedCardUsageDAO.insert(usage);
		
		return paymentId;
	}
 
	/**
	 * 有储值卡支付的退款处理
	 * @param userId  用户ID
	 * @param refundmentEventId  退款记录ID
	 * @param refundmentAmount  退款金额，以分为单位
	 * @param orderId 订单ID
	 * @param ordPaymentList 订单支付记录
	 * @return <code>true</code>代表退款成功，<code>false</code>代表退款失败
	 * @author zhangjie 2013-12-11
	 */
	@Override
	public BankReturnInfo refund2CardAccount(Long payRefundmentId,Long paymentId,Long refundAmount,String serialNo,String operatorId) {
		BankReturnInfo bankReturnInfo = new BankReturnInfo();
		// 判断储值卡有没有退过款.
		Long protectCount = cashAccountDAO.selectProtectCount(payRefundmentId.toString(),ComeFrom.LYTXK_STORED_CARD_REFUND.toString());
		if (protectCount == 0L) {
			cashAccountDAO.protect(payRefundmentId.toString(), ComeFrom.LYTXK_STORED_CARD_REFUND.toString());
			refundCardAmount(operatorId, paymentId, refundAmount, serialNo, new Date());
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
			bankReturnInfo.setCodeInfo("退款成功");
		}else{
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			bankReturnInfo.setCodeInfo("不允许重复退款!");
		}
		return bankReturnInfo;
	}
	
	/**
	 * 储值卡退款.
	 * @param userId
	 * @param orderId
	 * @param refundAmount
	 * @param paymentAmount
	 * @param serialNo
	 * @return
	 * @author zhangjie 2013-12-11
	 */
	private void refundCardAmount(String userId,Long paymentId,
			Long paymentAmount,String serialNo,Date tranDate) {
		//1.储值卡消费记录
		StoredCardUsage usage = storedCardUsageDAO.queryBySerial(serialNo);
		//2.根据储值卡的消费记录取相应的储值卡的信息.
		LvmamaStoredCard card = lvmamaStoredCardDao.queryStoredCardById(usage.getCardId());
		PayPayment payment = this.payPaymentService.selectByPaymentId(paymentId);
		Long orderId = null;
		if (payment!=null){
			orderId = payment.getObjectId();
		}
		
		//3.判断退款金额     增加一条相应储值卡的退费记录.
		StoredCardUsage cardUsage = new StoredCardUsage();
		cardUsage.setAmount(paymentAmount);
		cardUsage.setCardId(card.getStoredCardId());
		cardUsage.setCreateTime(tranDate);
		cardUsage.setOperator(userId);
		cardUsage.setOrderId(orderId);
		cardUsage.setSerial(serialNo);
		cardUsage.setUsageType(STORED_CARD_ENUM.STORED_REFUND.name());
		storedCardUsageDAO.insert(cardUsage);
		//4.修改储值卡的余额.和过期时间
		if(card.getOverTime()!=null){
			if(DateUtil.isCompareTime(card.getOverTime(),new Date())){
				card.setOverTime(DateUtil.mergeDateTimeAddMonth(new Date(),1));
			}else{
				card.setOverTime(DateUtil.mergeDateTimeAddMonth(card.getOverTime(),1));
			}
		}
		Long balance = card.getBalance();
		card.setBalance(balance + paymentAmount); 		
		lvmamaStoredCardDao.updateByStoredCard(card);
	}

	@Override
	public List<LvmamaStoredCard> getOverTimeStoredCard(Date date) {
 		return lvmamaStoredCardDao.getOverTimeStoredCard(date);
	}

	@Override
	public void doDeplay(LvmamaStoredCard lvmamaCard, String userName) {
		Map map = new HashMap();
		Date overDate;
		map.put("cardNo", lvmamaCard.getCardNo());
		map.put("balance", lvmamaCard.getBalance() - LvmamaCardUtils.FIFTY);
		if (lvmamaCard.getStatus().equals(
				Constant.CARD_STATUS.FINISHED.getCode())) {
			if (lvmamaCard.getAmount().longValue() != lvmamaCard.getBalance().longValue()) {
				map.put("status", Constant.CARD_STATUS.USED.getCode());
			} else {
				map.put("status", Constant.CARD_STATUS.NOTUSED.getCode());
			}
			overDate = DateUtil.mergeDateTimeAddYear(new Date(), 1);
			map.put("overTime", overDate);
		} else {
			overDate = DateUtil.mergeDateTimeAddYear(lvmamaCard.getOverTime(),
					1);
			map.put("overTime", overDate);
		}
		this.updateByParamForLvmamaStoredCard(map);
		 comLogService.insert(
				LvmamaCardUtils.STORED_CARD,
				null,
				lvmamaCard.getStoredCardId(),
				userName,
				Constant.COM_LOG_OBJECT_TYPE.STOREDCARD.getCode(),
				"卡延期",
				"卡号：" + lvmamaCard.getCardNo() +"老的过期日期:"+DateUtil.formatDate(lvmamaCard.getOverTime(), "yyyy-MM-dd")+ "新的过期日期："
						+ DateUtil.formatDate(overDate, "yyyy-MM-dd"), null);		
	}
	
	@Override
	public StoredCardOut queryOutStoregeSum(Map<String, Object> param){
		return this.outStoregeDAO.queryOutStoregeSum(param);
	}
	
	@Override
	public List<StoredCardOut> queryOutStoregeExcel(Map<String,Object> param){
		return this.outStoregeDAO.queryOutStoregeExcel(param);
	}

	@Override
	public void doUnFrozen(LvmamaStoredCard lvmamaCard, String userName) {
		Map map = new HashMap();
		map.put("cardNo", lvmamaCard.getCardNo());
		if ( lvmamaCard.getAmount().longValue()!=lvmamaCard.getBalance().longValue()) {
			map.put("status", Constant.CARD_STATUS.USED.getCode());
		} else {
			map.put("status", Constant.CARD_STATUS.NOTUSED.getCode());
		}
		this.updateByParamForLvmamaStoredCard(map);
		this.comLogService.insert(LvmamaCardUtils.STORED_CARD, null,lvmamaCard.getStoredCardId(), userName, Constant.COM_LOG_OBJECT_TYPE.STOREDCARD.getCode(), "卡解冻", "卡解冻，卡号："+lvmamaCard.getCardNo(), null);

	}
	
	@Override
	public List<LvmamaStoredCard> queryUsedLvmamaStoredCardByUserId(Map param) {
		return lvmamaStoredCardDao.queryUsedLvmamaStoredCardByUserId(param);
	}
}
