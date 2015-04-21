package com.lvmama.pet.fin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinDeduction;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementChange;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_OBJECT_TYPE;
import com.lvmama.comm.vo.Constant.COM_LOG_SETTLEMENT_EVENT;
import com.lvmama.comm.vo.Constant.EVENT_TYPE;
import com.lvmama.comm.vo.Constant.FIN_DEDUCTION_OBJECT_TYPE;
import com.lvmama.comm.vo.Constant.SETTLEMENT_TYPE;
import com.lvmama.comm.vo.Constant.SET_SETTLEMENT_ITEM_STATUS;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinDeductionDAO;
import com.lvmama.pet.fin.dao.FinSupplierMoneyDAO;
import com.lvmama.pet.fin.dao.SetSettlementChangeDAO;
import com.lvmama.pet.fin.dao.SetSettlementDAO;
import com.lvmama.pet.fin.dao.SetSettlementItemDAO;
import com.lvmama.pet.sup.dao.SupSettlementTargetDAO;
import com.lvmama.pet.sup.dao.SupSupplierDAO;

/**
 * 订单结算项Service实现类
 * 
 * @author yanggan
 * @version 结算重构 12/01/2012
 * 
 */
@HessianService("setSettlementItemService")
@Service("setSettlementItemService")
public class SetSettlementItemServiceImpl extends BaseService implements SetSettlementItemService {
	protected transient final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SetSettlementItemDAO setSettlementItemDAO;
	@Autowired
	private SetSettlementDAO setSettlementDAO;
	@Autowired
	private FinSupplierMoneyDAO finSupplierMoneyDAO;
	@Autowired
	private SupSettlementTargetDAO supSettlementTargetDAO;
	@Autowired
	private SupSupplierDAO supSupplierDAO;
	@Autowired
	private SetSettlementChangeDAO setSettlementChangeDAO;
	@Autowired
	private FinDeductionDAO finDeductionDAO;

	@Override
	public Page<SetSettlementItem> searchItemList(Map<String, Object> map){
		return setSettlementItemDAO.searchItemList(map);
	}
	/**
	 * 查询订单结算项
	 * 
	 * @param map
	 *            查询参数
	 * @return 分页数据
	 */
	@Override
	public Page<SetSettlementItem> searchList(Map<String, Object> searchParams) {
		return setSettlementItemDAO.searchList(searchParams);
	}

	/**
	 * 不结算
	 * 
	 * @param settlementItemIds
	 *            订单子子项ID
	 * @param operatorName
	 *            操作人员
	 */
	@Override
	public void noSettle(List<Long> settlementItemIds, String operatorName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", settlementItemIds);
		map.put("status", Constant.SET_SETTLEMENT_ITEM_STATUS.NOSETTLEMENT.name());
		map.put("settlementStatus",  Constant.SETTLEMENT_STATUS.NOSETTLEMENT.name());
		// 更新结算队列项状态为不结算 、结算状态为不结算
		setSettlementItemDAO.updateSettlementItems(map);
		for (Long id : settlementItemIds) {
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, id, operatorName, COM_LOG_SETTLEMENT_EVENT.NO_SETTLE.name(), COM_LOG_SETTLEMENT_EVENT.NO_SETTLE.getCnName(), "更新订单结算项为不结算", null);
		}
	}

	/**
	 * 根据订单号查询订单结算项
	 * 
	 * @param orderId
	 *            订单号
	 * @return 订单结算项
	 */
	@Override
	public List<SetSettlementItem> searchSettlementItemByOrderId(Long orderId) {
		return this.setSettlementItemDAO.searchSettlementItemByOrderId(orderId);
	}

	/**
	 * 根据选择的订单结算项生成结算单
	 * 
	 * @param settlementItemIds
	 *            订单结算项ID
	 * @param settlementType
	 *            结算类型（ORDER OR GROUP）
	 * @param operatorName
	 *            操作人员
	 * @return key=newSettlement 创建的新结算的ID;key=mergeSettlement 合并的旧结算单的ID
	 */
	@Override
	public Map<String, Object> settle(List<Long> settlementItemIds, Constant.SETTLEMENT_TYPE settlementType, String operatorName) {
		List<SetSettlementItem> items = this.setSettlementItemDAO.searchItemsByItemIds(settlementItemIds);// 根据订单结算项ID查询订单结算项
		return this.settleBase(items, settlementType, operatorName,null);
	}

	/**
	 * 根据查询条件生成结算单
	 * 
	 * @param searchParams
	 *            查询条件
	 * @param settlementType
	 *            结算类型（ORDER OR GROUP）
	 * @param operatorName
	 *            操作人员
	 * @return key=newSettlement 创建的新结算的ID;key=mergeSettlement
	 *         合并的旧结算单的ID;key=orderItemMetaIds已生成/合并结算单的订单子子项ID
	 */
	public Map<String, Object> settleAll(Map<String, Object> searchParams, SETTLEMENT_TYPE settlementType, String operatorName) {
		List<SetSettlementItem> items = this.setSettlementItemDAO.searchItem(searchParams);
		Map<String, Object> resMap = this.settleBase(items, settlementType, operatorName, null);
		List<Long> orderItemMetaIds = new ArrayList<Long>();
		for (SetSettlementItem item : items) {
			orderItemMetaIds.add(item.getOrderItemMetaId());
		}
		resMap.put("orderItemMetaIds", orderItemMetaIds);
		return resMap;
	}
	/**
	 * 只用于新系统对接
	 * @param targetItemMap
	 * @param settlementType
	 * @param operatorName
	 * @param settlementId
	 * @param target
	 * @return
	 */
	public Map<String,Object> settleByTarget(Map<String,List<SetSettlementItem>> targetItemMap, Constant.SETTLEMENT_TYPE settlementType, String operatorName,Long settlementId,Map<String,SupSettlementTarget> targetMap){
		List<Long> mergeList = new ArrayList<Long>();// 合并的结算单号
		List<Long> newList = new ArrayList<Long>();// 创建的结算单号
		SetSettlement settlement = null;
		if(settlementId != null){
			/*
			 * 向某个指定的结算单进行增加订单结算项 
			 */
			settlement = this.setSettlementDAO.searchSettlementBySettlementId(settlementId);
		}
		/*
		 * 使用构造完成的[结算对象-订单结算项]Map 合并/创建结算单
		 */
		for (Map.Entry<String, List<SetSettlementItem>> targetItem : targetItemMap.entrySet()) {
			Long settlementAmount = 0l;
			List<Long> itemIdList = new ArrayList<Long>();
			for (SetSettlementItem item : targetItem.getValue()) {
				settlementAmount += item.getTotalSettlementPrice();
				itemIdList.add(item.getSettlementItemId());
			}
			Long targetId = null;
			String filialeName = null;
			String businessName = null;
				String[] strs = targetItem.getKey().split(",");
				targetId = Long.parseLong(strs[0]);
				filialeName = strs[1];
				businessName = strs[2];
			if(settlementId == null){
				/*
				 * 当结算对象下面存在未打款的结算单则进行合并动作，反之进行创建动作 根据结算对象ID、结算类型（ORDER[订单结算] OR
				 * GROUP[团代售订单节结算])、结算状态（未结算）查询结算单 如果返回结果不为null，代表存在，反之代表不存在
				 */
				settlement = this.setSettlementDAO.searchSettlementByTargetIdFilialeName(targetId, filialeName , settlementType, Constant.SET_SETTLEMENT_STATUS.UNPAY,businessName);
			}
			if (settlement != null ) {// 存在未打款的结算单，合并到旧的结算单中或强制合并的标记为true
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
				mergeList.add(settlement.getSettlementId());
				super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlement.getSettlementId(), operatorName, COM_LOG_SETTLEMENT_EVENT.MERGE_SETTLEMENT.name(), COM_LOG_SETTLEMENT_EVENT.MERGE_SETTLEMENT.getCnName(), "合并结算单，结算金额从 " + PriceUtil.convertToYuan(oldAmount) + " 更新为 " + PriceUtil.convertToYuan(settlement.getSettlementAmount()), null);
			} else {
				SupSettlementTarget target = targetMap.get(targetItem.getKey());
				settlement = new SetSettlement();
				settlement.setSettlementAmount(settlementAmount);
				settlement.setTargetId(target.getTargetId());
				settlement.setStatus(Constant.SET_SETTLEMENT_STATUS.UNPAY.name());
				settlement.setOperatorName(operatorName);
				settlement.setPayedAmount(null);
				settlement.setTargetName(target.getName());
				settlement.setSettlementPeriod(target.getSettlementPeriod());
				settlement.setAdvancedDays(target.getAdvancedDays());
				settlement.setBankAccountName(target.getBankAccountName());
				settlement.setBankAccount(target.getBankAccount());
				settlement.setBankName(target.getBankName());
				settlement.setAlipayName(target.getAlipayName());
				settlement.setAlipayAccount(target.getAlipayAccount());
				settlement.setTargetType(target.getType());
				settlement.setBankLines(target.getBankLines());
				settlement.setSettlementType(settlementType.name());
				settlement.setFilialeName(filialeName);
				settlement.setBusinessName(businessName);
				settlement.setCompanyId(target.getSupplier().getCompanyId());
				settlement.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
				this.setSettlementDAO.insertSettlement(settlement);
				newList.add(settlement.getSettlementId());
				super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlement.getSettlementId(), operatorName, COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT.name(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT.getCnName(), "生成结算单", null);
				
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", itemIdList);
			map.put("settlementId", settlement.getSettlementId());
			map.put("settlementStatus",  Constant.SETTLEMENT_STATUS.SETTLEMENTING.name());
			map.put("joinSettlementTime",  new Date());
			// 更新结算队列项的结算状态为结算中、所属结算单ID、加入结算单的时间
			setSettlementItemDAO.updateSettlementItems(map);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("newSettlement", newList);
		resMap.put("mergeSettlement", mergeList);
		return resMap;
	}
	private Map<String, Object> settleBase(List<SetSettlementItem> items, Constant.SETTLEMENT_TYPE settlementType, String operatorName,Long settlementId) {
		List<Long> mergeList = new ArrayList<Long>();// 合并的结算单号
		List<Long> newList = new ArrayList<Long>();// 创建的结算单号
		/*
		 * 由于订单结算的结算单的维度是每一个结算对象是一个结算单 所以构造一个Map用于存放结算对象ID与结算项对应关系
		 */
		Map<String, List<SetSettlementItem>> targetItemMap = new HashMap<String, List<SetSettlementItem>>();
		for (SetSettlementItem item : items) {
			String businessName = item.getBusinessName();
			if(StringUtil.isEmptyString(businessName)){
				businessName = Constant.SET_SETTLEMENT_BUSINESS_NAME.SUPER_ORDER_BUSINESS.name();
			}
			if(Constant.SET_SETTLEMENT_ITEM_STATUS.NORMAL.name().equals(item.getStatus())){
				final String LOCK_KEY = "ADD_SETTELENT_ITEM_KEY_"+Long.toString(item.getSettlementItemId());
				if(SynchronizedLock.isOnDoingMemCached(LOCK_KEY)){
					continue;
				}
				String key = item.getTargetId().toString();
				//if(settlementType.name().equals(SETTLEMENT_TYPE.GROUP.name())){
					key = key + "," +item.getFilialeName()+","+businessName;
				//}
				List<SetSettlementItem> itemList = targetItemMap.get(key);
				if (itemList == null) {
					itemList = new ArrayList<SetSettlementItem>();
				}
				itemList.add(item);
				targetItemMap.put(key, itemList);
			}
		}
		SetSettlement settlement = null;
		if(settlementId != null){
			/*
			 * 向某个指定的结算单进行增加订单结算项 
			 */
			settlement = this.setSettlementDAO.searchSettlementBySettlementId(settlementId);
		}
		/*
		 * 使用构造完成的[结算对象-订单结算项]Map 合并/创建结算单
		 */
		for (Map.Entry<String, List<SetSettlementItem>> targetItem : targetItemMap.entrySet()) {
			Long settlementAmount = 0l;
			List<Long> itemIdList = new ArrayList<Long>();
			for (SetSettlementItem item : targetItem.getValue()) {
				settlementAmount += item.getTotalSettlementPrice();
				itemIdList.add(item.getSettlementItemId());
			}
			Long targetId = null;
			String filialeName = null;
			String businessName = null;
			//if(settlementType.name().equals(SETTLEMENT_TYPE.ORDER.name())){
			//	targetId =  Long.parseLong(targetItem.getKey());
			//}else{
				String[] strs = targetItem.getKey().split(",");
				targetId = Long.parseLong(strs[0]);
				filialeName = strs[1];
				businessName = strs[2];
			//}
			if(settlementId == null){
				/*
				 * 当结算对象下面存在未打款的结算单则进行合并动作，反之进行创建动作 根据结算对象ID、结算类型（ORDER[订单结算] OR
				 * GROUP[团代售订单节结算])、结算状态（未结算）查询结算单 如果返回结果不为null，代表存在，反之代表不存在
				 */
				settlement = this.setSettlementDAO.searchSettlementByTargetIdFilialeName(targetId, filialeName , settlementType, Constant.SET_SETTLEMENT_STATUS.UNPAY,businessName);
			}
			if (settlement != null ) {// 存在未打款的结算单，合并到旧的结算单中或强制合并的标记为true
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
				mergeList.add(settlement.getSettlementId());
				super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlement.getSettlementId(), operatorName, COM_LOG_SETTLEMENT_EVENT.MERGE_SETTLEMENT.name(), COM_LOG_SETTLEMENT_EVENT.MERGE_SETTLEMENT.getCnName(), "合并结算单，结算金额从 " + PriceUtil.convertToYuan(oldAmount) + " 更新为 " + PriceUtil.convertToYuan(settlement.getSettlementAmount()), null);
			} else {// 没有未打款的结算单，生成新的结算单
				SupSettlementTarget target = supSettlementTargetDAO.getSettlementTargetById(targetId);
				settlement = new SetSettlement();
				settlement.setSettlementAmount(settlementAmount);
				settlement.setTargetId(target.getTargetId());
				settlement.setStatus(Constant.SET_SETTLEMENT_STATUS.UNPAY.name());
				settlement.setOperatorName(operatorName);
				settlement.setPayedAmount(null);
				settlement.setTargetName(target.getName());
				settlement.setSettlementPeriod(target.getSettlementPeriod());
				settlement.setAdvancedDays(target.getAdvancedDays());
				settlement.setBankAccountName(target.getBankAccountName());
				settlement.setBankAccount(target.getBankAccount());
				settlement.setBankName(target.getBankName());
				settlement.setAlipayName(target.getAlipayName());
				settlement.setAlipayAccount(target.getAlipayAccount());
				settlement.setTargetType(target.getType());
				settlement.setBankLines(target.getBankLines());
				settlement.setSettlementType(settlementType.name());
				settlement.setFilialeName(filialeName);
				settlement.setBusinessName(businessName);
				SupSupplier supplier = supSupplierDAO.selectByPrimaryKey(target.getSupplierId());
				settlement.setCompanyId(supplier.getCompanyId());
				this.setSettlementDAO.insertSettlement(settlement);
				newList.add(settlement.getSettlementId());
				super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlement.getSettlementId(), operatorName, COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT.name(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT.getCnName(), "生成结算单", null);
				
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", itemIdList);
			map.put("settlementId", settlement.getSettlementId());
			map.put("settlementStatus",  Constant.SETTLEMENT_STATUS.SETTLEMENTING.name());
			map.put("joinSettlementTime",  new Date());
			// 更新结算队列项的结算状态为结算中、所属结算单ID、加入结算单的时间
			setSettlementItemDAO.updateSettlementItems(map);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("newSettlement", newList);
		resMap.put("mergeSettlement", mergeList);
		return resMap;
	}

	/**
	 * 新增或更新订单结算项
	 * 
	 * @param setSettlementItems
	 *            订单结算项
	 * @param messageType
	 *            触发的消息类型
	 * @return 新增时返回订单结算项的ID，更新时返回更新影响的行数
	 */
	@Override
	public void insertOrUpdateSettlementItem(List<SetSettlementItem> setSettlementItems, Constant.EVENT_TYPE messageType) {
		logger.debug("insertOrUpdateSettlementItem begin,setSettlementItems size :" + setSettlementItems.size() + " messageType:" + messageType);
		for (SetSettlementItem setSettlementItem : setSettlementItems) {
			SetSettlementItem existsItem = this.setSettlementItemDAO.searchItemByOrderItemMetaId(setSettlementItem.getOrderItemMetaId());
			logger.debug("orderItemMetaId:" + setSettlementItem.getOrderItemMetaId());
			if(StringUtils.isBlank(setSettlementItem.getMetaFilialeName())){//如果采购主体为空取销售主体
				setSettlementItem.setMetaFilialeName(setSettlementItem.getFilialeName());
			}
			if (existsItem == null) {// 订单结算项不存在，新增
				logger.debug("SetSettlementItem not exists");
				setSettlementItem.setStatus(Constant.SET_SETTLEMENT_ITEM_STATUS.NORMAL.name());
				setSettlementItem.setSettlementStatus(Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name());
				this.setSettlementItemDAO.insertSettlementItem(setSettlementItem);
				Long itemId = setSettlementItem.getSettlementItemId();
				super.insertLog(Constant.COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, itemId, "SYSTEM", COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.getCnName(), COM_LOG_SETTLEMENT_EVENT.CREATE_SETTLEMENT_ITEM.getCnName(), null);
			} else {// 订单结算项存在，更新
				Long itemId = existsItem.getSettlementItemId();
				logger.debug("SetSettlementItem exists ,itemId:" + itemId);
				setSettlementItem.setSettlementItemId(itemId);
				if(Constant.EVENT_TYPE.ORDER_RESTORE.name().equals(messageType.name()) 
						&& Constant.SET_SETTLEMENT_ITEM_STATUS.CANCEL.name().equals(existsItem.getStatus())){
					//如果是恢复订单且订单结算项的状态为取消，则更新订单结算项的状态为正常
					setSettlementItem.setStatus(Constant.SET_SETTLEMENT_ITEM_STATUS.NORMAL.name());
				}
				Integer updateRow = this.setSettlementItemDAO.updateSettlementItem(setSettlementItem);
				logger.debug("updateRow:" + updateRow);
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
	public void updateSettlementPrice(List<SetSettlementItem> setSettlementItems, String operatorName, Constant.EVENT_TYPE messageType){
		logger.debug("updateSettlementPrice begin,setSettlementItems size :" + setSettlementItems.size());
		for (SetSettlementItem setSettlementItem : setSettlementItems) {
			SetSettlementItem existsItem = this.setSettlementItemDAO.searchItemByOrderItemMetaId(setSettlementItem.getOrderItemMetaId());
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
					Long settlementPrice = null;
					if (EVENT_TYPE.ORDER_MODIFY_SETTLEMENT_PRICE.name().equals(messageType.name())) {
						type = "single";
						log = "产品经理修改结算单价";
						settlementPrice = setSettlementItem.getActualSettlementPrice();
					} else {
						type = "total";
						log = "产品经理修改结算总价";
						settlementPrice = setSettlementItem.getTotalSettlementPrice();
					}
					if(!StringUtil.isEmptyString(setSettlementItem.getUpdateRemark())){
						log = setSettlementItem.getUpdateRemark();
					}
					updateRow = this.modifySettlementPrice(itemId, settlementPrice, existsItem.getSettlementId(), log, type, operatorName);
				}
				if (updateRow != null && updateRow > 0) {
					String logContent = "";
					if(EVENT_TYPE.ORDER_MODIFY_SETTLEMENT_PRICE.name().equals(messageType.name())){
						logContent = "修改结算单价,结算单价从:" + existsItem.getActualSettlementPriceYuan() + "更新为:" + setSettlementItem.getActualSettlementPriceYuan()+"<br/>结算总价从:" + existsItem.getTotalSettlementPriceYuan() + "更新为:" + setSettlementItem.getTotalSettlementPriceYuan();
					} else if(EVENT_TYPE.ORDER_MODIFY_TOTAL_SETTLEMENT_PRICE.name().equals(messageType.name())){
						logContent = "修改结算总价,结算总价从:" + existsItem.getTotalSettlementPriceYuan() + "更新为:" + setSettlementItem.getTotalSettlementPriceYuan();
					}
					super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT_ITEM.name(), null, itemId, operatorName, COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.name(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT_ITEM.getCnName(), logContent, null);
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

	/**
	 * 根据结算单号查询订单子子项项ID
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @return
	 */
	@Override
	public List<Long> searchOrderItemMetaIdsBySettlementId(long settlementId) {
		return setSettlementItemDAO.searchOrderItemMetaIdsBySettlementId(settlementId);
	}
	

	/**
	 * 根据订单结算项ID查询订单子子项ID
	 * 
	 * @param settlementItemIds
	 *            订单结算项ID
	 * @return
	 */
	@Override
	public List<Long> searchOrderItemMetaIdsBySettlementItemId(List<Long> settlementItemIds){
		return setSettlementItemDAO.searchOrderItemMetaIdsBySettlementItemId(settlementItemIds);
	}

	/**
	 * 查询导出的Excel数据
	 * 
	 * @param settlementIds
	 *            结算单号
	 * @return
	 */
	@Override
	public List<SetSettlementItem> searchItemExcelData1(List<Long> settlementIds) {
		return setSettlementItemDAO.searchItemExcelData1(settlementIds);
	}

	/**
	 * 查询结算单的订单明细
	 * 
	 * @param searchParameter
	 *            查询参数
	 * @return 分页数据
	 */
	@Override
	public Page<SetSettlementItem> searchItemDetailList(Map<String, Object> searchParameter) {
		return setSettlementItemDAO.searchItemDetailList(searchParameter);
	}

	/**
	 * 查询结算总价
	 * 
	 * @param searchParameter
	 *            查询参数
	 * @return
	 */
	@Override
	public Long searchSumprice(Map<String, Object> searchParameter) {
		return setSettlementItemDAO.searchSumprice(searchParameter);
	}

	/**
	 * 导出结算单的订单明细
	 * 
	 * @param searchParameter
	 *            查询参数
	 */
	@Override
	public List<SetSettlementItem> searchItemExcelData2(Map<String, Object> searchParameter) {
		return setSettlementItemDAO.searchItemExcelData2(searchParameter);
	}

	/**
	 * 从结算单中移除订单结算项
	 * 
	 * @param settlementId
	 *            结算单
	 * @param settlementItemIds
	 *            订单结算项
	 * @param operatorName
	 *            操作人
	 */
	@Override
	public int removeSettlementItem(long settlementId, List<Long> settlementItemIds, String operatorName) {
		SetSettlement settlement = setSettlementDAO.searchSettlementBySettlementId(settlementId);
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
		
		SetSettlement settlement_new = setSettlementDAO.searchSettlementBySettlementId(settlementId);
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

	/**
	 * 根据订单号查询订单结算项
	 * 
	 * @param orderId
	 *            订单号
	 * @param targetId
	 *            结算对象ID
	 * @param 结算单类型
	 *            ORDER OR GROUP        
	 * @return
	 */
	public List<SetSettlementItem> searchListByOrderId(Long orderId, Long targetId,String settlementType){
		return setSettlementItemDAO.searchListByOrderId(orderId,targetId,settlementType);
	}
	
	/**
	 * 增加订单结算项到结算单中
	 * 
	 * @param settlementId
	 *            结算单
	 * @param settlementItemIds
	 *            订单结算项
	 * @param operatorName
	 *            操作人
	 */
	public Integer addOrder(long settlementId, List<Long> settlementItemIds, String operatorName){
		SetSettlement settlement = this.setSettlementDAO.searchSettlementBySettlementId(settlementId);
		if(settlement.getStatus().equals(Constant.SETTLEMENT_STATUS.SETTLEMENTED.name())){
			return -1;
		}
		List<SetSettlementItem> items = this.setSettlementItemDAO.searchItemsByItemIds(settlementItemIds);
		this.settleBase(items, Constant.SETTLEMENT_TYPE.ORDER, operatorName,settlementId);
		return 1;
		
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
		SetSettlement settlement = setSettlementDAO.searchSettlementBySettlementId(settlementId);
		if(Constant.SET_SETTLEMENT_STATUS.SETTLEMENTED.name().equals(settlement.getStatus())){
			return -1;
		}
		SetSettlementItem settlementItem = setSettlementItemDAO.searchItemBySettlementItemId(settlementItemId);
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
	/**
	 * 批量修改结算价
	 * 
	 * @param metaProductId
	 *            采购产品ID
	 * @param settlementId
	 *            结算单ID
	 * @param settlementPrice
	 *            结算价
	 * @param remark
	 *            备注
	 * @param operatorName
	 *            操作人
	 */
	public Integer batchModifySettlementPrice(Long metaProductId,Long settlementPrice,Long settlementId,String remark, String operatorName){
		SetSettlement settlement = setSettlementDAO.searchSettlementBySettlementId(settlementId);
		if(Constant.SET_SETTLEMENT_STATUS.SETTLEMENTED.name().equals(settlement.getStatus())){
			return -1;
		}
		//增加修改记录
		setSettlementChangeDAO.insertBatchModify(metaProductId, settlementId, settlementPrice,remark, operatorName);
		//批量修改结算价
		setSettlementItemDAO.updateSettlementPriceByMetaProductId(metaProductId,settlementId,settlementPrice);
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
		log_content.append("批量修改采购产品[").append(metaProductId);
		log_content.append("]的结算价为：").append(PriceUtil.convertToYuan(settlementPrice));
		log_content.append("<br/>结算单的应结金额从：");
		log_content.append(settlement.getSettlementAmountYuan())
		.append("修改为：")
		.append(settlement_new.getSettlementAmountYuan());
		super.insertLog(COM_LOG_OBJECT_TYPE.SET_SETTLEMENT.name(), null, settlementId, operatorName, COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT.name(), COM_LOG_SETTLEMENT_EVENT.UPDATE_SETTLEMENT.getCnName(), log_content.toString(), null);
		return 1;
	}
	
	/**
	 * 根据订单ID更新结算项中的订单结算总额
	 * @param orderId
	 * @param countSettleAmount
	 * @return
	 */
	public int updateSettlementItem(final Long orderId,final Long countSettleAmount){
		SetSettlementItem countSet = new SetSettlementItem();
		countSet.setOrderId(orderId);
		countSet.setCountSettleAmount(countSettleAmount);
		return setSettlementItemDAO.updateSettlementItem(countSet);
	}
	/**
	 * 根据订单子子项ID查询是否已经进行结算打款
	 * @param orderItemMetaId 订单子子项ID
	 * @return true 已经结算打款  false 未结算打款
	 */
	public boolean searchSettlementPayByOrderItemMetaId(Long orderItemMetaId){
		Long payedAmount = this.setSettlementItemDAO.searchSettlementPayByOrderItemMetaId(orderItemMetaId);
		if(payedAmount != null && payedAmount>0){
			return true;
		}else{
			return false;
		}
	}
}
