package com.lvmama.pet.fin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinDeduction;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementChange;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.service.fin.FinanceSupplierMoneyService;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_OBJECT_TYPE;
import com.lvmama.comm.vo.Constant.COM_LOG_SETTLEMENT_EVENT;
import com.lvmama.comm.vo.Constant.EVENT_TYPE;
import com.lvmama.comm.vo.Constant.FIN_DEDUCTION_OBJECT_TYPE;
import com.lvmama.comm.vo.Constant.SET_SETTLEMENT_ITEM_STATUS;
import com.lvmama.comm.vst.service.SettlementService;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinDeductionDAO;
import com.lvmama.pet.fin.dao.FinSupplierMoneyDAO;
import com.lvmama.pet.fin.dao.FinanceSettlementDAO;
import com.lvmama.pet.fin.dao.SetSettlementChangeDAO;
import com.lvmama.pet.fin.dao.SetSettlementDAO;
import com.lvmama.pet.fin.dao.SetSettlementItemDAO;
@HessianService("settlementService")
@Service("settlementService")
public class SettlementServiceImpl extends BaseService implements SettlementService  {
	
	private final static Logger LOG = Logger.getLogger(SettlementServiceImpl.class);
	
	private final static String NEW_SUPPLIER_BUSINESS="NEW_SUPPLIER_BUSINESS";
	
	@Autowired
	private SetSettlementItemDAO setSettlementItemDAO;
	@Autowired
	private SetSettlementDAO setSettlementDAO;
	@Autowired
	private SetSettlementItemService setSettlementItemService;
	@Autowired
	private FinanceSettlementDAO financeSettlementDAO;
	@Autowired
	private FinDeductionDAO finDeductionDAO;
	@Autowired
	private FinSupplierMoneyDAO finSupplierMoneyDAO;
	@Autowired
	private SetSettlementChangeDAO setSettlementChangeDAO;
	@Autowired
	private FinanceSupplierMoneyService financeSupplierMoneyService;
	
	@Override
	public void insertSettlementItem(SetSettlementItem setSettlementItem,
			String businessName) {
		LOG.debug(StringUtil.printParam(setSettlementItem));
		Assert.notNull(businessName,"businessName can't be empty!");
		setSettlementItem.setBusinessName(businessName);
		setSettlementItemDAO.insertSettlementItem(setSettlementItem);
		super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, setSettlementItem.getSettlementItemId(), "SYSTEM", COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.getCnName(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.getCnName(), null);
	}

	@Override
	public void batchInsertSettlementItem(
			List<SetSettlementItem> setSettlementItems, String businessName) {
		Assert.notNull(businessName,"businessName can't be empty!");
		for(SetSettlementItem item:setSettlementItems){
			item.setBusinessName(businessName);
			LOG.debug(StringUtil.printParam(item));
			setSettlementItemDAO.insertSettlementItem(item);
			Long itemId = item.getSettlementItemId();
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, itemId, "SYSTEM", COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.getCnName(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.getCnName(), null);
		}
	}

	@Override
	public int updateSettlementItem(SetSettlementItem setSettlementItem,
			String businessName) {
		Assert.notNull(businessName,"businessName can't be empty!");
		LOG.debug(StringUtil.printParam(setSettlementItem));
		int result = setSettlementItemDAO.updateSettlementItem(setSettlementItem);
		if(result>0){
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, setSettlementItem.getSettlementItemId(), "SYSTEM", COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), null);
		}
		return result;
	}

	@Override
	public void batchUpdateSettlementItem(
			List<SetSettlementItem> setSettlementItems, String businessName) {
		Assert.notNull(businessName,"businessName can't be empty!");
		for(SetSettlementItem item:setSettlementItems){
			item.setBusinessName(businessName);
			LOG.debug(StringUtil.printParam(item));
			int result = setSettlementItemDAO.updateSettlementItem(item);
			if(result>0){
				super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, item.getSettlementItemId(), "SYSTEM", COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), null);
			}
		}
	}

	@Override
	public int orderCancelSetSettlementItem(final Long orderId,final String businessName) {
		LOG.debug("orderId="+orderId+" ,businessName="+businessName);
		Assert.notNull(orderId,"orderId can't be empty!");
		Assert.notNull(businessName,"businessName can't be empty!");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("businessName", businessName);
		List<SetSettlementItem> list = setSettlementItemDAO.searchItem(map);
		if(null==list || (null!=list && list.isEmpty())){
			return 0;
		}
		Integer result = 0;
		String logContent = "订单取消后更新订单结算项";
		for(SetSettlementItem item:list){
			item.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			item.setStatus(Constant.SET_SETTLEMENT_ITEM_STATUS.CANCEL.name());
			result +=setSettlementItemDAO.updateSettlementItem(item);
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, item.getSettlementItemId(), "SYSTEM", Constant.COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.name(), Constant.COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), logContent, null);
		}
		return result;
	}

	@Override
	public int orderRefundmentUpdateSetSettlement(List<SetSettlementItem> items,final String businessName) {
		Assert.notNull(items,"items can't be empty!");
		Assert.isTrue(items.isEmpty(), "items can't be empty!");
		Assert.notNull(businessName,"businessName can't be empty!");
		Integer result = 0;
		String logContent = "";
		LOG.debug("begin businessName"+businessName);
		for(SetSettlementItem item:items){
			LOG.debug(StringUtil.printParam(item));
			SetSettlementItem oldItem = setSettlementItemDAO.searchItemBySettlementItemId(item.getSettlementItemId());
			if(item.getTotalSettlementPrice()!=0){
				item.setStatus(Constant.SET_SETTLEMENT_ITEM_STATUS.NORMAL.name());
			}
			result +=setSettlementItemDAO.updateSettlementItem(item);
			setSettlementItemService.updateSettlement(oldItem, item);
			logContent = "退款成功后更新订单结算项,结算价从:" + oldItem.getTotalSettlementPriceYuan() + "更新为:" + item.getTotalSettlementPriceYuan();
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, item.getSettlementItemId(), "SYSTEM", Constant.COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.name(), Constant.COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), logContent, null);
		}
		return result;
	}

	@Override
	public int updateSupSupplierSettleMoney(Long supplierId, Long depositMoney,
			Date depositTime) {
		try{
			LOG.debug("supplierId="+supplierId+",depositMoney="+depositMoney+",depositTime="+depositTime);
			FinSupplierMoney finSupplierMoney = new FinSupplierMoney();
			finSupplierMoney.setSupplierId(supplierId);
			finSupplierMoney.setAdvanceDepositAlert(depositMoney);
			finSupplierMoney.setDepositAlert(depositTime);
			finSupplierMoney.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
			financeSupplierMoneyService.updateSupplierMoney(finSupplierMoney);
			super.insertLog("FIN_SUPPLIER_MONEY", supplierId, supplierId, "SYSTEM", "saveFinanceSupplierMoney", "saveFinanceSupplierMoney", "供应商修改预存款操作：预存款="+depositMoney+",预警时间="+DateUtil.getDateTime("yyyy-MM-dd", depositTime)+",系统名称=新供应商系统", null);
		return 1;
		}catch(Exception e){
			LOG.error(e.getMessage());
			return -1;
		}
	}
	
	@Override
	public List<SetSettlementItem> findSetSettlementItemByParams(final Long orderId,final Long orderItemId,final String businessName ){
		if(StringUtil.isEmptyString(businessName) || (null==orderId && null!=orderItemId)){
			return null;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		params.put("orderItemMetaId", orderItemId);
		params.put("businessName", businessName);
		params.put("excludeSettlementId", Boolean.TRUE);
		return financeSettlementDAO.searchItemList(params);
	}

	@Override
	public void saveOrUpdateSetSettlementItem(List<SetSettlementItem> setSettlementItems) {
		for (SetSettlementItem setSettlementItem : setSettlementItems) {
			saveSettlementItem(setSettlementItem,NEW_SUPPLIER_BUSINESS);
		}
	}

	@Override
	public void saveSettlementItem(SetSettlementItem setSettlementItem,String businessName) {
		List<SetSettlementItem> items=this.findSetSettlementItemByParams(setSettlementItem.getOrderId(), setSettlementItem.getOrderItemMetaId(), businessName);
		if(null!=items&&items.size()>0){//存在更新
			this.updateSettlementItem(setSettlementItem, businessName);
		}else{//新增
			this.insertSettlementItem(setSettlementItem, businessName);
		}
	}
	
	/**
	 * 获取新系统结算子项
	 * @param orderItemMetaId 订单子项
	 * @return
	 */
	public SetSettlementItem getSetSettlementItemVst(Long orderItemMetaId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderItemMetaId", orderItemMetaId);
		params.put("businessName", NEW_SUPPLIER_BUSINESS);
		params.put("excludeSettlementId", Boolean.TRUE);
		List<SetSettlementItem> itemList=financeSettlementDAO.searchItemList(params);
		if(null!=itemList&&itemList.size()>0){
			return itemList.get(0);
		}
		return null;
	}
	
	
	/**
	 * 新增或更新订单结算项
	 * 
	 * @param setSettlementItems
	 *            订单结算项
	 * @param messageType
	 *            触发的消息类型
	 */
	@Override
	public void insertOrUpdateSettlementItem(List<SetSettlementItem> setSettlementItems, EVENT_TYPE messageType) {
		for (SetSettlementItem setSettlementItem : setSettlementItems) {
			SetSettlementItem existsItem = getSetSettlementItemVst(setSettlementItem.getOrderItemMetaId());
			if (existsItem == null) {// 订单结算项不存在，新增
				setSettlementItem.setStatus(Constant.SET_SETTLEMENT_ITEM_STATUS.NORMAL.name());
				setSettlementItem.setSettlementStatus(Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name());
				this.setSettlementItemDAO.insertSettlementItem(setSettlementItem);
				Long itemId = setSettlementItem.getSettlementItemId();
				super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, itemId, "SYSTEM", COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.getCnName(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.getCnName(), null);
			} else {// 订单结算项存在，更新
				Long itemId = existsItem.getSettlementItemId();
				setSettlementItem.setSettlementItemId(itemId);
				if(EVENT_TYPE.ORDER_RESTORE.name().equals(messageType.name())
						&& SET_SETTLEMENT_ITEM_STATUS.CANCEL.name().equals(existsItem.getStatus())){
					//如果是恢复订单且订单结算项的状态为取消，则更新订单结算项的状态为正常
					setSettlementItem.setStatus(SET_SETTLEMENT_ITEM_STATUS.NORMAL.name());
				}
				Integer updateRow = this.setSettlementItemDAO.updateSettlementItem(setSettlementItem);
				if (updateRow != null && updateRow > 0) {
					String logContent = "";
					if (EVENT_TYPE.ORDER_CANCEL.name().equals(messageType.name())) {// 取消订单
						logContent = "订单取消后更新订单结算项";
					} else if (EVENT_TYPE.ORDER_RESTORE.name().equals(messageType.name())) {// 恢复订单
						logContent = "订单恢复后更新订单结算项";
					} else if (EVENT_TYPE.ORDER_REFUNDED.name().equals(messageType.name())) {// 退款成功
						//更新结算单
						updateSettlement(existsItem, setSettlementItem);
						logContent = "退款成功后更新订单结算项,结算价从:" + existsItem.getTotalSettlementPriceYuan() + "更新为:" + setSettlementItem.getTotalSettlementPriceYuan();
					} else if (EVENT_TYPE.PASSCODE_APPLY_SUCCESS.name().equals(messageType.name())) {// 订单申码成功
						logContent = "订单申码成功后更新订单结算项";
					} else {
						logContent = COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName();
					}
					super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, itemId, "SYSTEM", COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), logContent, null);
				}
			}
		}
	}
	
	public void updateSettlement(SetSettlementItem existsItem, SetSettlementItem newItem) {
		if (existsItem.getSettlementId() != null) {
			SetSettlement settlement = setSettlementDAO.searchSettlementBySettlementId(existsItem.getSettlementId());
			if (Constant.SET_SETTLEMENT_STATUS.SETTLEMENTED.name().equals(settlement.getStatus())) {// 结算单已结算，生成抵扣款
				Long amount = existsItem.getTotalSettlementPrice() - newItem.getTotalSettlementPrice();
				if (amount > 0) {
					int updateRow = finSupplierMoneyDAO.addDeduction(amount, existsItem.getSupplierId());
					if (updateRow == 0) {
						finSupplierMoneyDAO.insertDeduction(amount, existsItem.getSupplierId(), "CNY");
					}
					FinDeduction fd = new FinDeduction();
					fd.setObjectType(FIN_DEDUCTION_OBJECT_TYPE.SET_SETTLEMENT.name());
					fd.setObjectId(existsItem.getSettlementId().toString());
					fd.setCreator("SYSTEM");
					fd.setSupplierId(existsItem.getSupplierId());
					fd.setType(Constant.FIN_DEDUCTION_TYPE.DEPOSIT.name());
					// 当类型为存入时 借贷方向为：借
					fd.setDirection(Constant.DERECTION_TYPE.DEBIT.name());
					fd.setAmount(amount);
					fd.setRemark("退款生成抵扣款");
					finDeductionDAO.insertFinDeduction(fd);
				}
			} else if (Constant.SET_SETTLEMENT_STATUS.PAYED.name().equals(settlement.getStatus())) {// 结算单已打款，生成change记录
				SetSettlementChange change = new SetSettlementChange();
				change.setChangetype(Constant.SET_SETTLEMENT_CHANGE_TYPE.MODIFY_TOTAL_PRICE.name());
				change.setSettlementItemId(existsItem.getSettlementItemId());
				change.setOrderItemMetaId(existsItem.getOrderItemMetaId());
				change.setAmountBeforeChange(existsItem.getTotalSettlementPrice());
				change.setAmountAfterChange(newItem.getTotalSettlementPrice());
				change.setCreator("SYSTEM");
				change.setSettlementId(existsItem.getSettlementId());
				setSettlementChangeDAO.insert(change);
			}
			// 更新结算单的应结算金额
			setSettlementDAO.updateSettlementSettlementAmount(existsItem.getSettlementId());
			SetSettlement settlement_new = setSettlementDAO.searchSettlementBySettlementId(existsItem.getSettlementId());
			String logContent = "订单结算项["+existsItem.getSettlementItemId()+"]退款成功<br/>结算单应结金额从：" + settlement.getSettlementAmountYuan() + "修改为：" + settlement_new.getSettlementAmountYuan();
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, existsItem.getSettlementId(), "SYSTEM", COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT.name(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT.getCnName(), logContent, null);
		}
	}

	@Override
	public boolean searchSettlementPayByOrderItemMetaId(Long orderItemMetaId,String businessName) {
		Assert.notNull(businessName,"businessName can't be empty!");
		Long payedAmount=setSettlementItemDAO.getSettlementPayAmount(orderItemMetaId, businessName);
		if(payedAmount != null && payedAmount>0){
			return true;
		}else{
			return false;
		}
	}
	
	
}
