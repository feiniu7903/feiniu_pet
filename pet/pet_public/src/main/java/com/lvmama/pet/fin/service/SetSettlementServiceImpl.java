package com.lvmama.pet.fin.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinDeduction;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementChange;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.service.fin.SetSettlementService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_OBJECT_TYPE;
import com.lvmama.comm.vo.Constant.COM_LOG_SETTLEMENT_EVENT;
import com.lvmama.comm.vo.Constant.FIN_DEDUCTION_OBJECT_TYPE;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinAdvanceDepositDAO;
import com.lvmama.pet.fin.dao.FinDeductionDAO;
import com.lvmama.pet.fin.dao.FinSupplierMoneyDAO;
import com.lvmama.pet.fin.dao.SetSettlementChangeDAO;
import com.lvmama.pet.fin.dao.SetSettlementDAO;
import com.lvmama.pet.fin.dao.SetSettlementItemDAO;
import com.lvmama.pet.fin.dao.SetSettlementPaymentDAO;

/**
 * 结算单Service实现类
 * 
 * @author yanggan
 * @version 结算重构 12/01/2012
 * 
 */
@HessianService("setSettlementService")
@Service("setSettlementService")
public class SetSettlementServiceImpl extends BaseService implements SetSettlementService {

	protected transient final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SetSettlementDAO setSettlementDAO;
	@Autowired
	private SetSettlementItemDAO setSettlementItemDAO;
	@Autowired
	private SetSettlementPaymentDAO setSettlementPaymentDAO;
	@Autowired
	private SetSettlementChangeDAO setSettlementChangeDAO;
	@Autowired
	private FinSupplierMoneyDAO finSupplierMoneyDAO;
	@Autowired
	private FinDeductionDAO finDeductionDAO;
	@Autowired
	private FinAdvanceDepositDAO finAdvanceDepositDAO;

	/**
	 * 查询结算单
	 * 
	 * @param searchParams
	 *            查询参数
	 * @return 分页数据
	 */
	@Override
	public Page<SetSettlement> searchList(Map<String, Object> searchParams) {
		return setSettlementDAO.searchList(searchParams);
	}
	
	/**
	 * 根据查询条件查询结算单ID
	 * 
	 * @param searchParameter
	 *            查询条件
	 * @return
	 */
	@Override
	public List<Long> searchSettlementIds(Map<String, Object> searchParameter){
		return setSettlementDAO.searchSettlementIds(searchParameter);
	}

	/**
	 * 根据结算单ID查询结算单信息
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	@Override
	public SetSettlement searchBySettlementId(Long id) {
		return setSettlementDAO.searchSettlementBySettlementId(id);
	}

	/**
	 * 根据结算单ID查询结算单信息（包含原始对象信息）
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	@Override
	public SetSettlement searchInitalSettlement(Long id) {
		return setSettlementDAO.searchInitalSettlementBySettlementId(id);
	}

	/**
	 * 修改固话结算对象信息
	 * 
	 * @param settlement
	 *            结算单信息
	 */
	@Override
	public void updateInitalInfo(SetSettlement settlement) {
		setSettlementDAO.updateInitalInfo(settlement);
	}

	/**
	 * 结算单打款
	 * 
	 * @param settlementId
	 *            结算单号
	 * @param advanceDepositPayAmount
	 *            预存款打款金额
	 * @param deductionPayAmount
	 *            抵扣款打款金额
	 * @param bankPayAmount
	 *            银行打款金额
	 * @param bankName
	 *            银行名称
	 * @param operatetime
	 *            打款时间
	 * @param serial
	 *            流水号
	 * @return
	 */
	public Map<String, Object> toPay(Long settlementId, Long advanceDepositPayAmount, Long deductionPayAmount, Long bankPayAmount, String bankName, Date operatetime, String serial, String operatorName) {
		SetSettlement settlement = this.setSettlementDAO.searchSettlementBySettlementId(settlementId);
		FinSupplierMoney supplierMoney = finSupplierMoneyDAO.searchBySupplierId(settlement.getSupplierId());
		if (supplierMoney == null) {
			supplierMoney = new FinSupplierMoney();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		long deductionAmount = supplierMoney.getDeductionAmount() == null ? 0 : supplierMoney.getDeductionAmount();
		long advanceDepositAmount = supplierMoney.getAdvanceDepositAmount() == null ? 0 : supplierMoney.getAdvanceDepositAmount();
		if (deductionPayAmount > deductionAmount) {
			res.put("res", -1);
			return res;
		} else if (advanceDepositPayAmount > advanceDepositAmount) {
			res.put("res", -2);
			return res;
		} else {
			if (advanceDepositPayAmount > 0) {
				// 新增预存款打款记录
				SetSettlementPayment payment = new SetSettlementPayment();
				payment.setSettlementId(settlement.getSettlementId());
				payment.setTargetId(settlement.getTargetId());
				payment.setPaytype(Constant.SET_SETTLEMENT_PAYMENT_PAYTYPE.ADVANCE_DEPOSIT.name());
				payment.setAmount(advanceDepositPayAmount);
				payment.setCreator(operatorName);
				payment.setOperatetime(operatetime);
				payment.setBank("预存款");
				payment.setCreatetime(new Date());
				payment.setCurrency("CNY");
				payment.setRate(1d);
				setSettlementPaymentDAO.insertPayment(payment);
				FinAdvanceDeposit fa = new FinAdvanceDeposit();
				fa.setBank(settlement.getSettlementId().toString());
				fa.setCreator(operatorName);
				fa.setSupplierId(settlement.getSupplierId());
				fa.setType(Constant.FIN_ADVANCE_DEPOSIT_TYPE.PAYMENT.name());
				fa.setSupplierId(settlement.getSupplierId());
				fa.setDirection(Constant.DERECTION_TYPE.CREDIT.name());
				fa.setAmount(advanceDepositPayAmount);
				fa.setOperatetime(operatetime);
				fa.setRemark("结算单打款使用预存款");
				fa.setCurrency("CNY");
				finAdvanceDepositDAO.insertFinadvanceDeposit(fa);
			}
			// 如果使用抵扣款，则更新结算单的应结算金额（应结算金额减去抵扣款金额）
			if (deductionPayAmount > 0) {
				settlement.setDeductionAmount(settlement.getDeductionAmount() + deductionPayAmount);
				// 增加抵扣款流水记录
				FinDeduction fd = new FinDeduction();
				fd.setObjectType(FIN_DEDUCTION_OBJECT_TYPE.SET_SETTLEMENT.name());
				fd.setObjectId(settlementId.toString());
				fd.setCreator(operatorName);
				fd.setSupplierId(settlement.getSupplierId());
				fd.setType(Constant.FIN_DEDUCTION_TYPE.PAYMENT.name());
				fd.setDirection(Constant.DERECTION_TYPE.CREDIT.name());
				fd.setAmount(deductionPayAmount);
				fd.setRemark("结算单打款使用抵扣款");
				fd.setCurrency("CNY");
				finDeductionDAO.insertFinDeduction(fd);
			}
			if (bankPayAmount > 0) {
				// 新增银行打款记录
				SetSettlementPayment payment = new SetSettlementPayment();
				payment.setSettlementId(settlement.getSettlementId());
				payment.setTargetId(settlement.getTargetId());
				payment.setPaytype(Constant.SET_SETTLEMENT_PAYMENT_PAYTYPE.CASH.name());
				payment.setAmount(bankPayAmount);
				payment.setCreator(operatorName);
				payment.setBank(bankName);
				payment.setSerial(serial);
				payment.setOperatetime(operatetime);
				payment.setCreatetime(new Date());
				payment.setCurrency("CNY");
				payment.setRate(1d);
				setSettlementPaymentDAO.insertPayment(payment);
			}
			Long payedAmount = settlement.getPayedAmount();
			payedAmount = payedAmount == null ? 0 : payedAmount;
			Long amount = bankPayAmount + advanceDepositPayAmount;
			settlement.setPayedAmount(payedAmount + amount);
			//结算单的抵扣款使用金额
			Long da = settlement.getDeductionAmount() == null ? 0 : settlement.getDeductionAmount();
			if (settlement.getPayedAmount() + da >= settlement.getSettlementAmount()) {//打款金额+抵扣款使用金额大于等于应结算金额
				settlement.setStatus(Constant.SET_SETTLEMENT_STATUS.PAYED.name());
			} else {
				settlement.setStatus(Constant.SET_SETTLEMENT_STATUS.PARTPAY.name());
			}
			Map<String, Object> updateMap = new HashMap<String, Object>();
			// 更新结算单的结算金额、结算状态、打款金额
			updateMap.put("status", settlement.getStatus());
			updateMap.put("payedAmount", settlement.getPayedAmount());
			updateMap.put("deductionAmount", settlement.getDeductionAmount());
			updateMap.put("settlementId", settlement.getSettlementId());
			setSettlementDAO.updateSettlement(updateMap);
			if (deductionPayAmount > 0 || advanceDepositPayAmount > 0) {
				// 更新供应商的金额数据
				finSupplierMoneyDAO.minusSupplierMoney(settlement.getSupplierId(), deductionPayAmount, advanceDepositPayAmount, null);
			}
			res.put("res", 1);
			res.put("settlement", settlement);
			StringBuffer log_content = new StringBuffer("");
			DecimalFormat df1 = new DecimalFormat(",###.00"); 
			if(amount > 0){
				if (advanceDepositPayAmount > 0) {
					log_content.append("预存款使用金额：").append(df1.format(PriceUtil.convertToYuan(advanceDepositPayAmount)));
				}
				if (bankPayAmount > 0) {
					log_content.append(" 银行打款金额：").append(df1.format(PriceUtil.convertToYuan(bankPayAmount)));
				}
				log_content.append(" 合计：").append(df1.format(PriceUtil.convertToYuan(amount))).append(" <br/>");
			}
			if (deductionPayAmount > 0) {
				log_content.append("抵扣款使用金额：").append(df1.format(PriceUtil.convertToYuan(deductionPayAmount)));
			}
			super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlement.getSettlementId(), operatorName, COM_LOG_SETTLEMENT_EVENT.SETTLEMENT_PAY.name(), COM_LOG_SETTLEMENT_EVENT.SETTLEMENT_PAY.getCnName(), log_content.toString(), null);
			return res;
		}
	}

	/**
	 * 结算单结算
	 * 
	 * @param settlementId
	 *            结算单号
	 * @param memo
	 *            备注
	 * @param operatorName
	 *            操作人
	 */
	@Override
	public Map<String, Object>  settle(long settlementId, String memo, String operatorName) {
		Map<String, Object> res = new HashMap<String, Object>();
		SetSettlement settlement = this.setSettlementDAO.searchSettlementBySettlementId(settlementId);
		Long deductionAmount = settlement.getDeductionAmount();
		Long payedAmount = settlement.getPayedAmount();
		deductionAmount = deductionAmount == null ? 0 : deductionAmount;
		payedAmount = payedAmount == null ? 0 : payedAmount;
		long diffAmount =  payedAmount + deductionAmount - settlement.getSettlementAmount();
		// 打款金额，应结算金额存在差额产生抵扣款
		if (diffAmount > 0) {
			int updateRow = finSupplierMoneyDAO.addDeduction(diffAmount, settlement.getSupplierId());
			if (updateRow == 0) {
				finSupplierMoneyDAO.insertDeduction(diffAmount, settlement.getSupplierId(), "CNY");
			}
			FinDeduction fd = new FinDeduction();
			fd.setObjectType(FIN_DEDUCTION_OBJECT_TYPE.SET_SETTLEMENT.name());
			fd.setObjectId(settlement.getSettlementId().toString());
			fd.setCreator(operatorName);
			fd.setSupplierId(settlement.getSupplierId());
			fd.setType(Constant.FIN_DEDUCTION_TYPE.DEPOSIT.name());
			// 当类型为存入时 借贷方向为：借
			fd.setDirection(Constant.DERECTION_TYPE.DEBIT.name());
			fd.setAmount(diffAmount);
			fd.setRemark("结算生成抵扣款");
			finDeductionDAO.insertFinDeduction(fd);
			DecimalFormat df1 = new DecimalFormat(",###.00"); 
			res.put("deductionAmount", df1.format(PriceUtil.convertToYuan(diffAmount)));
		}

		settlement.setStatus(Constant.SET_SETTLEMENT_STATUS.SETTLEMENTED.name());
		settlement.setSettlementTime(new Date());
		// 更新结算单的结算时间、结算状态
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("status", settlement.getStatus());
		updateMap.put("settlementId", settlement.getSettlementId());
		updateMap.put("settlementTime", settlement.getSettlementTime());
		if (!StringUtil.isEmptyString(memo)) {
			updateMap.put("memo", memo);
		}
		setSettlementDAO.updateSettlement(updateMap);
		//修改结算子单项的结算状态为已结算
		setSettlementItemDAO.updateItemSettlementStatusBySettlementId(settlementId, Constant.SET_SETTLEMENT_STATUS.SETTLEMENTED.name());
		StringBuffer log_content = new StringBuffer("结算单结算");
		if (diffAmount > 0) {
			log_content.append(",生成了抵扣款：").append(PriceUtil.convertToYuan(diffAmount));
		}
		super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlement.getSettlementId(), operatorName, COM_LOG_SETTLEMENT_EVENT.SETTLEMENTED.name(), COM_LOG_SETTLEMENT_EVENT.SETTLEMENTED.getCnName(), log_content.toString(), null);
		res.put("settlement", settlement);
		res.put("res", 1);
		return res;
	}

	/**
	 * 查询change的流水记录
	 * 
	 * @param settlementId
	 *            结算单号
	 * @return
	 */
	public Page<SetSettlementChange> searchChangeRecord(Map<String,Object> searchParams){
		return setSettlementChangeDAO.searchBySettlementId(searchParams);
	}
	

}
