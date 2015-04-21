package com.lvmama.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaPrice;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdSettlementPriceRecord;
import com.lvmama.comm.bee.service.ord.ModifySettlementPriceService;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_CHANGE_RESULT;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_REASON;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.dao.impl.ModifySettlementPriceDAO;

/**
 * 修改结算价Service实现类
 * @author zhangwenjun
 *
 */
public class ModifySettlementPriceServiceImpl extends OrderServiceImpl implements ModifySettlementPriceService {
	private ModifySettlementPriceDAO modifySettlementPriceDAO;
	private SetSettlementItemService setSettlementItemService;

	private OrderItemProdDAO orderItemProdDAO;
	private OrderItemMetaDAO orderItemMetaDAO;
	public SetSettlementItemService getSetSettlementItemService() {
		return setSettlementItemService;
	}

	public void setSetSettlementItemService(
			SetSettlementItemService setSettlementItemService) {
		this.setSettlementItemService = setSettlementItemService;
	}

	public ModifySettlementPriceDAO getModifySettlementPriceDAO() {
		return modifySettlementPriceDAO;
	}

	public void setModifySettlementPriceDAO(
			ModifySettlementPriceDAO modifySettlementPriceDAO) {
		this.modifySettlementPriceDAO = modifySettlementPriceDAO;
	}

	@Override
	public Integer selectRowCount(Map<String, Object> searchConds) {
		return modifySettlementPriceDAO.selectRowCount(searchConds);
	}

	@Override
	public List<OrdOrderItemMetaPrice> selectByParms(Map<String, Object> map) {
		return modifySettlementPriceDAO.selectByParms(map);
	}

	@Override
	public Integer queryHistoryRecordCount(Map<String, Object> searchConds) {
		return modifySettlementPriceDAO.queryHistoryRecordCount(searchConds);
	}

	@Override
	public List<OrdOrderItemMetaPrice> queryHistoryRecordList(
			Map<String, Object> map) {
		return modifySettlementPriceDAO.queryHistoryRecordList(map);
	}

	@Override
	public List<OrdOrderItemMetaPrice> exportHistoryRecordList(Map<String, Object> map) {
		return modifySettlementPriceDAO.exportHistoryRecordList(map);
	}
	@Override
	public boolean updateSettlementPrice(Long orderItemMetaId,
			ORD_SETTLEMENT_PRICE_CHANGE_TYPE changeType,
			ORD_SETTLEMENT_PRICE_REASON reason, String remark,
			Long settlementPrice, String creator){
		return this.updateSettlementPrice(orderItemMetaId, changeType, reason, remark, settlementPrice, creator, false);
	}
	@Override
	public boolean updateSettlementPrice(Long orderItemMetaId, Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE changeType, ORD_SETTLEMENT_PRICE_REASON reason, String remark, Long settlementPrice, String creator,boolean updateFlag) {
		final OrdSettlementPriceRecord ordSettlementPriceRecord = new OrdSettlementPriceRecord();
		ordSettlementPriceRecord.setOrderItemMetaId(orderItemMetaId);
		ordSettlementPriceRecord.setChangeType(changeType.name());
		ordSettlementPriceRecord.setReason(reason.name());
		ordSettlementPriceRecord.setRemark(remark);
		// 修改后的金额
		ordSettlementPriceRecord.setUpdatePrice(settlementPrice);
		ordSettlementPriceRecord.setIfSettlementPayment(setSettlementItemService.searchSettlementPayByOrderItemMetaId(orderItemMetaId));
		ordSettlementPriceRecord.setCreator(creator);
		/**
		 * 根据订单子子项 id 查询订单子子项信息
		 */
		OrdOrderItemMeta orderItemMeta = modifySettlementPriceDAO.selectByOrderItemMetaId(ordSettlementPriceRecord.getOrderItemMetaId());
		OrdOrderItemProd itemProd = this.selectItemByOrderItemProdId(orderItemMeta.getOrderItemId());
		OrdOrderItemMetaPrice ordOrderItemMetaPrice = new OrdOrderItemMetaPrice();
		// 用来比较修改的结算价是否大于销售价
		boolean comparePriceRes = false;
		// 记录日志，区分结算单价和结算总价
		String logTitle;
		// 修改结算单价
		if(ordSettlementPriceRecord.getChangeType().equals(ORD_SETTLEMENT_PRICE_CHANGE_TYPE.UNIT_PRICE.toString())){
			logTitle = "修改结算单价";
			if(settlementPrice * orderItemMeta.getProductQuantity() > itemProd.getPrice()){
				comparePriceRes = true;
			}
			ordSettlementPriceRecord.setbActualSettlementPrice(orderItemMeta.getActualSettlementPrice());
			ordSettlementPriceRecord.setActualSettlementPrice(ordSettlementPriceRecord.getUpdatePrice());
			ordSettlementPriceRecord.setbTotalSettlementPrice(orderItemMeta.getTotalSettlementPrice());
			ordSettlementPriceRecord.setTotalSettlementPrice(ordSettlementPriceRecord.getUpdatePrice() * orderItemMeta.getQuantity() * orderItemMeta.getProductQuantity());
			ordOrderItemMetaPrice.setActualSettlementPrice(ordSettlementPriceRecord.getActualSettlementPrice());
			if(ordSettlementPriceRecord.getUpdatePrice() > orderItemMeta.getActualSettlementPrice()){
				ordSettlementPriceRecord.setChangeResult(ORD_SETTLEMENT_PRICE_CHANGE_RESULT.UP.toString());
			}else if (ordSettlementPriceRecord.getUpdatePrice() < orderItemMeta.getActualSettlementPrice()){
				ordSettlementPriceRecord.setChangeResult(ORD_SETTLEMENT_PRICE_CHANGE_RESULT.DOWN.toString());
			}
		}else{// 修改结算总价
			logTitle = "修改结算总价";
			if(settlementPrice  > itemProd.getPrice() * itemProd.getQuantity()){
				comparePriceRes = true;
			}
			ordSettlementPriceRecord.setbTotalSettlementPrice(orderItemMeta.getTotalSettlementPrice());
			ordSettlementPriceRecord.setTotalSettlementPrice(ordSettlementPriceRecord.getUpdatePrice());
			ordOrderItemMetaPrice.setTotalSettlementPrice(ordSettlementPriceRecord.getTotalSettlementPrice());
			ordSettlementPriceRecord.setbActualSettlementPrice(orderItemMeta.getActualSettlementPrice());
			ordSettlementPriceRecord.setActualSettlementPrice(Math.round(Double.longBitsToDouble(ordSettlementPriceRecord.getUpdatePrice())/Double.longBitsToDouble((orderItemMeta.getQuantity() * orderItemMeta.getProductQuantity()))));
			if(ordSettlementPriceRecord.getUpdatePrice() > orderItemMeta.getTotalSettlementPrice()){
				ordSettlementPriceRecord.setChangeResult(ORD_SETTLEMENT_PRICE_CHANGE_RESULT.UP.toString());
			}else if (ordSettlementPriceRecord.getUpdatePrice() < orderItemMeta.getTotalSettlementPrice()){
				ordSettlementPriceRecord.setChangeResult(ORD_SETTLEMENT_PRICE_CHANGE_RESULT.DOWN.toString());
			}
		}

		ordOrderItemMetaPrice.setOrderItemMetaId(ordSettlementPriceRecord.getOrderItemMetaId());
		ordOrderItemMetaPrice.setChangeType(ordSettlementPriceRecord.getChangeType());
		
		boolean flag = true;
		boolean result = true;
		/**
		 * 调用接口判断是否已经结算打款
		 */
		if(!updateFlag  && ( ordSettlementPriceRecord.getIfSettlementPayment() || comparePriceRes ) ){// 已经结算打款
			ordSettlementPriceRecord.setStatus(Constant.ORD_SETTLEMENT_PRICE_RECORD_STATUS.UNVERIFIED.toString());
			ordSettlementPriceRecord.setSettlementPay(1);
			result = false;
		}else{// 未结算打款
			ordSettlementPriceRecord.setStatus(Constant.ORD_SETTLEMENT_PRICE_RECORD_STATUS.VERIFIED.toString());
			ordSettlementPriceRecord.setSettlementPay(0);
			// 执行修改结算价
			flag = modifySettlementPriceDAO.updateSettlementPrice(ordOrderItemMetaPrice);

			// 修改结算价成功之后在日志表里添加记录 
			insertLog(orderItemMetaId, "ORD_ORDER_ITEM_META",orderItemMeta.getOrderId(), "ORD_ORDER", creator,
					logTitle, Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
					"修改为：" + PriceUtil.convertToYuan(settlementPrice));
		}
		
		// 添加修改记录
		if(flag){
			modifySettlementPriceDAO.insertOrdSettlementPriceRecord(ordSettlementPriceRecord);
		}
		
		return result;
	}
	
	@Override
	public boolean insertSettlementRecord(OrdSettlementPriceRecord ordSettlementPriceRecord) {
		try{
			// 添加修改记录
			modifySettlementPriceDAO.insertOrdSettlementPriceRecord(ordSettlementPriceRecord);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public OrdOrderItemMetaPrice selectByPrimaryKey(Long ordOrderItemMetaId) {
		return modifySettlementPriceDAO.selectByPrimaryKey(ordOrderItemMetaId);
	}

	@Override
	public Integer queryVerifyListCount(Map<String, Object> searchConds) {
		return modifySettlementPriceDAO.queryVerifyListCount(searchConds);
	}

	@Override
	public List<OrdOrderItemMetaPrice> queryVerifyList(
			Map<String, Object> map) {
		return modifySettlementPriceDAO.queryVerifyList(map);
	}

	@Override
	public boolean doVerify(Map<String, Object> map) {
		try{
			modifySettlementPriceDAO.doVerify(map);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public OrdSettlementPriceRecord queryHistoryRecordById(Long recordId) {
		return modifySettlementPriceDAO.queryHistoryRecordById(recordId);
	}

	@Override
	public boolean updateHistoryRecordById(Map<String, Object> map) {
		try{
			modifySettlementPriceDAO.updateHistoryRecordById(map);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean ifExistRefundment(Long orderItemMetaId) {
		Integer result = modifySettlementPriceDAO.queryRefundmentByMetaId(orderItemMetaId);
		if(result > 0){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public boolean updateSettlementPriceForVedify(OrdOrderItemMetaPrice ordOrderItemMetaPrice) {
		try{
			// 修改结算价
			modifySettlementPriceDAO.updateSettlementPrice(ordOrderItemMetaPrice);

			// 修改结算价成功之后在日志表里添加记录 
			insertLog(ordOrderItemMetaPrice.getOrderItemMetaId(), "ORD_ORDER_ITEM_META",ordOrderItemMetaPrice.getOrderId(), "ORD_ORDER", ordOrderItemMetaPrice.getOperatorName(),
					"修改结算价", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
					"结算单价：" + ordOrderItemMetaPrice.getActualSettlementPriceYuan() + ",结算总价：" + ordOrderItemMetaPrice.getTotalSettlementPriceYuan());
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public OrdOrderItemMeta selectByOrderItemMetaId(Long orderItemMetaId){
		OrdOrderItemMeta ordOrderItemMeta = modifySettlementPriceDAO.selectByOrderItemMetaId(orderItemMetaId);
		return ordOrderItemMeta;
	}
	
	@Override
	public OrdOrderItemProd selectItemByOrderItemProdId(Long orderItemProdId){
		return orderItemProdDAO.selectByPrimaryKey(orderItemProdId);
	}
	@Override
	public boolean searchUnverifiedRecord(Long orderItemMetaId) {
		Integer result = modifySettlementPriceDAO.searchUnverifiedRecord(orderItemMetaId);
		if(result > 0){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public boolean updateOrderItemMetaVirtual(Long orderItemMetaId,String virtual){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("virtual", virtual);
		params.put("orderItemMetaId", orderItemMetaId);
		Integer result = orderItemMetaDAO.updateByParamMap(params);
		return result>0;
	}
	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}
	
}
