package com.lvmama.pet.fin.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinDeduction;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementChange;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.service.fin.FinanceSettlementService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_OBJECT_TYPE;
import com.lvmama.comm.vo.Constant.COM_LOG_SETTLEMENT_EVENT;
import com.lvmama.comm.vo.Constant.EVENT_TYPE;
import com.lvmama.comm.vo.Constant.FIN_DEDUCTION_OBJECT_TYPE;
import com.lvmama.comm.vo.Constant.SET_SETTLEMENT_ITEM_STATUS;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinanceAdvanceDepositDAO;
import com.lvmama.pet.fin.dao.FinanceDeductionDAO;
import com.lvmama.pet.fin.dao.FinanceSettlementDAO;
import com.lvmama.pet.fin.dao.FinanceSupplierMoneyDAO;
import com.lvmama.pet.fin.dao.SetSettlementChangeDAO;
import com.lvmama.pet.fin.dao.SetSettlementDAO;
import com.lvmama.pet.fin.dao.SetSettlementItemDAO;
import com.lvmama.pet.fin.dao.SetSettlementPaymentDAO;

@HessianService("financeSettlementService")
@Service("financeSettlementService")
public class FinanceSettlementServiceImpl  extends BaseService implements FinanceSettlementService{
	private static final Logger LOG = Logger.getLogger(FinanceSettlementServiceImpl.class);

	@Autowired
	FinanceSettlementDAO financeSettlementDAO;
	@Autowired
	SetSettlementItemDAO setSettlementItemDAO;
	@Autowired
	SetSettlementDAO setSettlementDAO;
	@Autowired
	SetSettlementChangeDAO setSettlementChangeDAO;
	@Autowired
	FinanceSupplierMoneyDAO financeSupplierMoneyDAO;
	@Autowired
	SetSettlementPaymentDAO setSettlementPaymentDAO;
	@Autowired
	FinanceAdvanceDepositDAO financeAdvanceDepositDAO;
	@Autowired
	FinanceDeductionDAO financeDeductionDAO;
	
	@Override
	public Page<SetSettlementItem> searchItemListPage(Map<String, Object> map) {
		return financeSettlementDAO.searchItemListPage(map);
	}

	@Override
	public List<SetSettlementItem> searchItemList(Map<String, Object> map) {
		return financeSettlementDAO.searchItemList(map);
	}

	@Override
	public Page<SetSettlement> searchSettleListPage(Map<String, Object> map) {
		return financeSettlementDAO.searchSettleListPage(map);
	}

	@Override
	public List<SetSettlement> searchSettleList(Map<String, Object> map) {
		return financeSettlementDAO.searchSettleList(map);
	}

	@Override
	public Map<String, Object> toPay(SetSettlement settlement,
			Long advanceDepositPayAmount, Long deductionPayAmount,
			Long bankPayAmount, String bankName, Date operatetime,
			String serial, String operatorName) {
		FinSupplierMoney supplierMoney = financeSupplierMoneyDAO.searchBySupplierId(settlement.getSupplierId());
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
				payment.setSupplierId(String.valueOf(settlement.getSupplierId()));
				payment.setTargetId(settlement.getTargetId());
				payment.setRate(1d);
				payment.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
				setSettlementPaymentDAO.insertPayment(payment);
				FinAdvanceDeposit fa = new FinAdvanceDeposit();
				fa.setBank(settlement.getSettlementId().toString());
				fa.setCreator(operatorName);
				fa.setSupplierId(settlement.getSupplierId());
				fa.setType(Constant.FIN_ADVANCE_DEPOSIT_TYPE.PAYMENT.name());
				fa.setDirection(Constant.DERECTION_TYPE.CREDIT.name());
				fa.setAmount(advanceDepositPayAmount);
				fa.setOperatetime(operatetime);
				fa.setRemark("结算单打款使用预存款");
				fa.setCurrency("CNY");
				fa.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
				financeAdvanceDepositDAO.insertFinadvanceDeposit(fa);
			}
			// 如果使用抵扣款，则更新结算单的应结算金额（应结算金额减去抵扣款金额）
			if (deductionPayAmount > 0) {
				settlement.setDeductionAmount(settlement.getDeductionAmount() + deductionPayAmount);
				// 增加抵扣款流水记录
				FinDeduction fd = new FinDeduction();
				fd.setObjectType(FIN_DEDUCTION_OBJECT_TYPE.SET_SETTLEMENT.name());
				fd.setObjectId(settlement.getSettlementId().toString());
				fd.setCreator(operatorName);
				fd.setSupplierId(settlement.getSupplierId());
				fd.setType(Constant.FIN_DEDUCTION_TYPE.PAYMENT.name());
				fd.setDirection(Constant.DERECTION_TYPE.CREDIT.name());
				fd.setAmount(deductionPayAmount);
				fd.setRemark("结算单打款使用抵扣款");
				fd.setCurrency("CNY");
				fd.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
				financeDeductionDAO.insertFinDeduction(fd);
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
				payment.setSupplierId(String.valueOf(settlement.getSupplierId()));
				payment.setTargetId(settlement.getTargetId());
				payment.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
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
				financeSupplierMoneyDAO.minusSupplierMoney(settlement.getSupplierId(), deductionPayAmount, advanceDepositPayAmount, null);
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

	@Override
	public int addOrder(Long settlementId, List<Long> settlementItemIds,
			String operatorName) {
		SetSettlement settlement =financeSettlementDAO.searchSetSettlementById(settlementId);
		if(settlement.getStatus().equals(Constant.SETTLEMENT_STATUS.SETTLEMENTED.name())){
			return -1;
		}
		List<SetSettlementItem> items = this.financeSettlementDAO.searchItemsByItemIds(settlementItemIds);
		
		Long settlementAmount=0L;
		for(SetSettlementItem item:items){
			settlementAmount+= item.getTotalSettlementPrice();
		}
		Long oldAmount = settlement.getSettlementAmount();
		settlement.setSettlementAmount(settlementAmount + oldAmount);
		// 更新结算单的结算金额
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settlementId", settlement.getSettlementId());
		map.put("settlementAmount", settlement.getSettlementAmount());
		
		if(Constant.SET_SETTLEMENT_STATUS.PAYED.name().equals(settlement.getStatus())
				&& !settlement.isFullPayed()){
			map.put("status", Constant.SET_SETTLEMENT_STATUS.PARTPAY.name());
		}
		this.setSettlementDAO.updateSettlement(map);
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("ids", settlementItemIds);
		updateMap.put("settlementId", settlement.getSettlementId());
		updateMap.put("settlementStatus",  Constant.SETTLEMENT_STATUS.SETTLEMENTING.name());
		updateMap.put("joinSettlementTime",  new Date());
		// 更新结算队列项的结算状态为结算中、所属结算单ID、加入结算单的时间
		setSettlementItemDAO.updateSettlementItems(map);
		super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlement.getSettlementId(), operatorName, COM_LOG_SETTLEMENT_EVENT.MERGE_SETTLEMENT.name(), COM_LOG_SETTLEMENT_EVENT.MERGE_SETTLEMENT.getCnName(), "合并结算单，结算金额从 " + PriceUtil.convertToYuan(oldAmount) + " 更新为 " + PriceUtil.convertToYuan(settlement.getSettlementAmount()), null);
		return 1;
		
	}

	@Override
	public Long searchSumprice(Map<String, Object> map) {
		return financeSettlementDAO.searchSumprice(map);
	}

	@Override
	public int removeSettlementItem(Long settlementId,
			List<Long> settlementItemIds, String operatorName) {
		SetSettlement settlement = financeSettlementDAO.searchSetSettlementById(settlementId);
		if (settlement.getStatus().equals(Constant.SET_SETTLEMENT_STATUS.SETTLEMENTED)) {
			return 0;
		}
		if (settlement.getPayedAmount() != null && settlement.getPayedAmount() > 0 && !settlement.isFullPayed()) {
			return -1;
		}
		// 已打款的结算单删除时，要记录删除记录
		if (settlement.getPayedAmount() != null && settlement.getPayedAmount() > 0) {
			setSettlementChangeDAO.insertBatchDel(settlementItemIds, operatorName);
		}
		StringBuffer log_content = new StringBuffer("删除订单结算项[");
		String ids_str = StringUtils.join(settlementItemIds, ",");
		log_content.append(ids_str);
		for (Long id : settlementItemIds) {
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, id, operatorName, COM_LOG_SETTLEMENT_EVENT.REMOVE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.REMOVE_SETTLEMENT_ITEM.getCnName(), "从结算单["+settlementId+"]中移除", null);
		}
		//把订单结算项从结算单中移除
		setSettlementItemDAO.removeSettlementItem(settlementItemIds, Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name());
		//更新结算单的应结算金额
		setSettlementDAO.updateSettlementSettlementAmount(settlementId);
		
		SetSettlement settlement_new =financeSettlementDAO.searchSetSettlementById(settlementId);
		Map<String,Object> updateMap = new HashMap<String, Object>();
		updateMap.put("settlementId", settlement_new.getSettlementId());
		if(Constant.SET_SETTLEMENT_STATUS.PARTPAY.name().equals(settlement_new.getStatus()) && settlement_new.isFullPayed()){
			updateMap.put("status", Constant.SET_SETTLEMENT_STATUS.PAYED.name());
			this.setSettlementDAO.updateSettlement(updateMap);
		}
		log_content.append("]<br/>结算单的应结金额从：");
		log_content.append(settlement.getSettlementAmountYuan())
		.append("修改为：")
		.append(settlement_new.getSettlementAmountYuan());
		super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlementId, operatorName, COM_LOG_SETTLEMENT_EVENT.REMOVE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.REMOVE_SETTLEMENT_ITEM.getCnName(), log_content.toString(), null);
		return 1;
	}
	
	@Override
	public SetSettlement getSetSettlementById(Long settlementId) {
		return financeSettlementDAO.searchSetSettlementById(settlementId);
	}
	
	@Override
	public Map<String, Object>  settle(SetSettlement settlement, String memo, String operatorName,VstSuppSupplierVo sup) {
		Map<String, Object> res = new HashMap<String, Object>();
		Long deductionAmount = settlement.getDeductionAmount();
		Long payedAmount = settlement.getPayedAmount();
		deductionAmount = deductionAmount == null ? 0 : deductionAmount;
		payedAmount = payedAmount == null ? 0 : payedAmount;
		long diffAmount =  payedAmount + deductionAmount - settlement.getSettlementAmount();
		// 打款金额，应结算金额存在差额产生抵扣款
		if (diffAmount > 0) {
			
			Long supplierId = sup.getSupplierId();
			settlement.setSupplierName(sup.getSupplierName());
			int updateRow = financeSupplierMoneyDAO.addDeduction(diffAmount, supplierId);
			if (updateRow == 0) {
				financeSupplierMoneyDAO.insertDeduction(diffAmount, supplierId, "CNY");
			}
			FinDeduction fd = new FinDeduction();
			fd.setObjectType(FIN_DEDUCTION_OBJECT_TYPE.SET_SETTLEMENT.name());
			fd.setObjectId(settlement.getSettlementId().toString());
			fd.setCreator(operatorName);
			fd.setSupplierId(supplierId);
			fd.setType(Constant.FIN_DEDUCTION_TYPE.DEPOSIT.name());
			// 当类型为存入时 借贷方向为：借
			fd.setDirection(Constant.DERECTION_TYPE.DEBIT.name());
			fd.setAmount(diffAmount);
			fd.setRemark("结算生成抵扣款");
			financeDeductionDAO.insertFinDeduction(fd);
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
		setSettlementItemDAO.updateItemSettlementStatusBySettlementId(settlement.getSettlementId(), Constant.SET_SETTLEMENT_STATUS.SETTLEMENTED.name());
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
	 * 修改结算价
	 * 
	 * @param setSettlementItems
	 *            订单结算项
	 * @param operatorName
	 *            操作人
	 * @param messageType
	 *            触发的消息类型            
	 */
	public void updateSettlementPrice(List<SetSettlementItem> setSettlementItems,Long settlementPrice, String operatorName, String messageType){
		LOG.debug("updateSettlementPrice begin,setSettlementItems size :" + setSettlementItems.size());
		for (SetSettlementItem setSettlementItem : setSettlementItems) {
			SetSettlementItem existsItem = this.financeSettlementDAO.searchItemsByItemId(setSettlementItem.getSettlementItemId());
			if (EVENT_TYPE.ORDER_MODIFY_SETTLEMENT_PRICE.name().equals(messageType)) {
				setSettlementItem.setActualSettlementPrice(settlementPrice);
			} else {
				setSettlementItem.setTotalSettlementPrice(settlementPrice);
			}
			if (existsItem != null) {// 订单结算项存在，更新
				Long itemId = existsItem.getSettlementItemId();
				setSettlementItem.setSettlementItemId(itemId);
				Integer updateRow = null;
				if (existsItem.getSettlementId() == null) {// 未生成结算单
					if(SET_SETTLEMENT_ITEM_STATUS.CANCEL.name().equals(existsItem.getStatus()) && setSettlementItem.getTotalSettlementPrice() > 0l){
						setSettlementItem.setStatus(SET_SETTLEMENT_ITEM_STATUS.NORMAL.name());
					}
					updateRow = this.setSettlementItemDAO.updateSettlementItem(setSettlementItem);
				} else {// 已生成结算单
					String type = "" , log = "";
					if (EVENT_TYPE.ORDER_MODIFY_SETTLEMENT_PRICE.name().equals(messageType)) {
						type = "single";
						log = "产品经理修改结算单价";
					} else {
						type = "total";
						log = "产品经理修改结算总价";
					}
					if(!StringUtil.isEmptyString(setSettlementItem.getUpdateRemark())){
						log = setSettlementItem.getUpdateRemark();
					}
					updateRow = this.modifySettlementPrice(itemId, settlementPrice, existsItem.getSettlementId(), log, type, operatorName);
				}
				if (updateRow != null && updateRow > 0) {
					String logContent = "";
					if(EVENT_TYPE.ORDER_MODIFY_SETTLEMENT_PRICE.name().equals(messageType)){
						logContent = "修改结算单价,结算单价从:" + existsItem.getActualSettlementPriceYuan() + "更新为:" + setSettlementItem.getActualSettlementPriceYuan()+"<br/>结算总价从:" + existsItem.getTotalSettlementPriceYuan() + "更新为:" + setSettlementItem.getTotalSettlementPriceYuan();
					} else if(EVENT_TYPE.ORDER_MODIFY_TOTAL_SETTLEMENT_PRICE.name().equals(messageType)){
						logContent = "修改结算总价,结算总价从:" + existsItem.getTotalSettlementPriceYuan() + "更新为:" + setSettlementItem.getTotalSettlementPriceYuan();
					}
					super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, itemId, operatorName, COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), logContent, null);
				}
			}
		}
	}
	/**
	 * 修改结算总价
	 * 
	 * @param settlementItemId
	 *            结算项ID
	 * @param settlementId
	 *            结算单ID
	 * @param settlementPrice
	 *            结算价
	 * @param remark
	 *            备注
	 * @param type
	 *            total 结算总价 single 结算单价
	 * @param operatorName
	 *            操作人
	 */
	public Integer modifySettlementPrice(Long settlementItemId,Long settlementPrice, Long settlementId,String remark,String type, String operatorName){
		SetSettlement settlement = financeSettlementDAO.searchSetSettlementById(settlementId);
		if(Constant.SET_SETTLEMENT_STATUS.SETTLEMENTED.name().equals(settlement.getStatus())){
			return -1;
		}
		SetSettlementItem settlementItem = financeSettlementDAO.searchItemsByItemId(settlementItemId);
		if("total".equals(type) || "single".equals(type) ){
			
			SetSettlementItem settlementItem_new = new SetSettlementItem();
			settlementItem_new.setSettlementItemId(settlementItemId);
			settlementItem_new.setUpdateRemark(remark);
			SetSettlementChange change = new SetSettlementChange();
			if("total".equals(type)){
				settlementItem_new.setTotalSettlementPrice(settlementPrice);
				settlementItem_new.setActualSettlementPrice(Math.round(Double.longBitsToDouble(settlementPrice)/Double.longBitsToDouble((settlementItem.getQuantity() * settlementItem.getProductQuantity()))));
				change.setChangetype(Constant.SET_SETTLEMENT_CHANGE_TYPE.MODIFY_TOTAL_PRICE.name());
			}else{
				change.setChangetype(Constant.SET_SETTLEMENT_CHANGE_TYPE.MODIFY.name());
				settlementItem_new.setActualSettlementPrice(settlementPrice);
				settlementItem_new.setTotalSettlementPrice(settlementItem_new.getActualSettlementPrice() * settlementItem.getQuantity() * settlementItem.getProductQuantity());
			}
			if(SET_SETTLEMENT_ITEM_STATUS.CANCEL.name().equals(settlementItem.getStatus()) && settlementItem_new.getTotalSettlementPrice() > 0l){
				settlementItem_new.setStatus(SET_SETTLEMENT_ITEM_STATUS.NORMAL.name());
			}
			//修改结算价
			setSettlementItemDAO.updateSettlementItem(settlementItem_new);
			change.setSettlementItemId(settlementItemId);
			change.setSettlementItemId(settlementItem.getSettlementItemId());
			change.setOrderItemMetaId(settlementItem.getOrderItemMetaId());
			change.setAmountBeforeChange(settlementItem.getTotalSettlementPrice());
			change.setAmountAfterChange(settlementItem_new.getTotalSettlementPrice());
			change.setRemark(remark);
			change.setCreator(operatorName);
			change.setSettlementId(settlementId);
			if(settlement.getPayedAmount()!=null && settlement.getPayedAmount()>0){
				setSettlementChangeDAO.insert(change);
			}
			//更新结算单的结算价
			setSettlementDAO.updateSettlementSettlementAmount(settlementId);
			SetSettlement settlement_new = setSettlementDAO.searchSettlementBySettlementId(settlementId);
			Map<String,Object> updateMap = new HashMap<String, Object>();
			updateMap.put("settlementId", settlement_new.getSettlementId());
			if(Constant.SET_SETTLEMENT_STATUS.PARTPAY.name().equals(settlement_new.getStatus()) && settlement_new.isFullPayed()){
				updateMap.put("status", Constant.SET_SETTLEMENT_STATUS.PAYED.name());
				this.setSettlementDAO.updateSettlement(updateMap);
			}
			if(Constant.SET_SETTLEMENT_STATUS.PAYED.name().equals(settlement_new.getStatus()) && !settlement_new.isFullPayed()){
				updateMap.put("status", Constant.SET_SETTLEMENT_STATUS.PARTPAY.name());
				this.setSettlementDAO.updateSettlement(updateMap);
			}
			StringBuffer log_content = new StringBuffer("");
			log_content.append("结算项[").append(settlementItem.getSettlementItemId());
			if("single".equals(type)){
				log_content.append("]修改结算单价,结算单价从:")
							.append(settlementItem.getActualSettlementPriceYuan()).append("更新为:")
							.append(settlementItem_new.getActualSettlementPriceYuan()).append("<br/>结算总价从:")
							.append(settlementItem.getTotalSettlementPriceYuan())
							.append("更新为：").append(settlementItem_new.getTotalSettlementPriceYuan());
			} else{
				log_content.append("]修改结算总价,结算总价从:")
				.append(settlementItem.getTotalSettlementPriceYuan())
				.append("更新为：").append(settlementItem_new.getTotalSettlementPriceYuan())
				.append("<br/>修改结算单价,结算单价从:")
				.append(settlementItem.getActualSettlementPriceYuan()).append("更新为:")
				.append(settlementItem_new.getActualSettlementPriceYuan());
			}
			log_content.append("<br/>结算单的应结金额从：");
			log_content.append(settlement.getSettlementAmountYuan())
			.append("更新为：")
			.append(settlement_new.getSettlementAmountYuan());
			super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlementId, operatorName, COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT.name(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT.getCnName(), log_content.toString(), null);
			return 1;
		}else{
			return 0;
		}
		
	}

	@Override
	public SetSettlementItem getSetSettlementItem(Long setSettlementItemId) {
		return financeSettlementDAO.searchItemsByItemId(setSettlementItemId);
	}
}
