package com.lvmama.op.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.op.FinGroupSettlement;
import com.lvmama.comm.bee.po.op.OpGroupBudget;
import com.lvmama.comm.bee.po.op.OpGroupBudgetFixed;
import com.lvmama.comm.bee.po.op.OpGroupBudgetProd;
import com.lvmama.comm.bee.po.op.OpGroupBudgetProdRefund;
import com.lvmama.comm.bee.po.op.OpOtherIncoming;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.op.ProductOrderDetail;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.OptionItem;

public class GroupBudgetDAO extends BaseIbatisDAO{
	
	/**
	 * 查询团列表结果集总数 
	 * @param param
	 * @return
	 */
	public Long getGroupListCount(Map<String, String> param){
		return (Long)super.queryForObject("GROUP_BUDGET.getGroupListCount", param);
	}
	
	/**
	 * 查询团列表分页结果集
	 * @param param
	 * @return
	 */
	public List<OpTravelGroup> getGroupList(Map<String, String> param){
		return (List<OpTravelGroup>)super.queryForList("GROUP_BUDGET.getGroupList", param);
	}
	/**
	 * 查询团列表，不带分页条件
	 */
	public List<OpTravelGroup> getGroupListForExport(Map<String, String> map){
		return (List<OpTravelGroup>)super.queryForList("GROUP_BUDGET.getGroupListForExport",map);
	}
	
	/**
	 * 查询团预算
	 */
	public OpGroupBudget getGroupBudgetByGroupCode(String travelGroupCode){
		return (OpGroupBudget)super.queryForObject("GROUP_BUDGET.getGroupBudgetByGroupCode",travelGroupCode);
	}
	/**
	 * 从团信息中查询团产品项
	 */
	public List<OpGroupBudgetProd> getGroupProductByGroupCode(String travelGroupCode){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("value", travelGroupCode);
		//保险产品子类型不参与团预算.
		params.put("subProductType", Constant.SUB_PRODUCT_TYPE.INSURANCE.name());
		List<OpGroupBudgetProd> prods = super.queryForList("GROUP_BUDGET.getGroupProductByGroupCode",params);
		//查询汇率
		for(OpGroupBudgetProd prod : prods){
			prod.setExchangeRate(getLatestExchangeRateByCurrency(prod.getCurrency()));
		}
		return prods;
	}
	/**
	 * 根据采购产品分支ID查询销售产品子类型
	 * @param branchId
	 * @return
	 */
	public String getSubProdTypeByBranchId(Long branchId){
		return (String)super.queryForObject("GROUP_BUDGET.getSubProdTypeByBranchId",branchId);
	}
	
	/**
	 * 查询团预算产品项
	 */
	public List<OpGroupBudgetProd> getGroupBudgetProductByGroupCode(String travelGroupCode, String groupProdType){
		Map map = new HashMap();
		map.put("travelGroupCode", travelGroupCode);
		map.put("type", groupProdType);
		List<OpGroupBudgetProd> prods = (List<OpGroupBudgetProd>)getGroupBudgetProductByParam(map);
		return prods;
	}
	
	/**
	 * 更新预算产品成本
	 * @param prod
	 */
	public void updateGroupBudgetProd(OpGroupBudgetProd prod){
		super.update("GROUP_BUDGET.updateGroupBudgetProd",prod);
	}
	/**
	 * 查询团预算固定成本项
	 */
	public List<OpGroupBudgetFixed> getGroupBudgetFixedByGroupCode(String travelGroupCode){
		Map map = new HashMap();
		map.put("travelGroupCode", travelGroupCode);
		map.put("type", "BUDGET");
		return getGroupBudgetFixedByParam(map);
	}
	
	/**
	 * 查询团预算产品项
	 */
	public List<OpGroupBudgetProd> getGroupFinalBudgetProductByGroupCode(String travelGroupCode){
		Map map = new HashMap();
		map.put("travelGroupCode", travelGroupCode);
		map.put("type", "COST");
		return getGroupBudgetProductByParam(map);
	}
	/**
	 * 查询团预算固定成本项
	 */
	public List<OpGroupBudgetFixed> getGroupFinalBudgetFixedByGroupCode(String travelGroupCode){
		Map map = new HashMap();
		map.put("travelGroupCode", travelGroupCode);
		map.put("type", "COST");
		return  getGroupBudgetFixedByParam(map);
	}
	
	/**
	 * 查询团预算产品项
	 */
	public List<OpGroupBudgetProd> getGroupBudgetProductByParam(Map paramerter){
		return (List<OpGroupBudgetProd>)super.queryForList("GROUP_BUDGET.getGroupBudgetProductByParam",paramerter);
	}
	/**
	 * 查询团预算固定成本项
	 */
	public List<OpGroupBudgetFixed> getGroupBudgetFixedByParam(Map paramerter){
		return (List<OpGroupBudgetFixed>)super.queryForList("GROUP_BUDGET.getGroupBudgetFixedByParam",paramerter);
	}
	
	/**
	 * 新增固定成本项
	 */
	public Long insertOpGroupBudgetFixed(OpGroupBudgetFixed item){
		return (Long)super.insert("GROUP_BUDGET.insertOpGroupBudgetFixed", item);
	}
	/**
	 * 更新团信息的结算状态
	 * @param groupCode
	 * @param status
	 */
	public void updateOpTravelGroupSettlementStatus(String groupCode,String status){
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("groupCode", groupCode);
		map.put("status", status);
		super.update("GROUP_BUDGET.updateOpTravelGroupSettlementStatus",map);
	}
	/**
	 * 更新固定成本项
	 */
	public void updateOpGroupBudgetFixed(OpGroupBudgetFixed item){
		super.update("GROUP_BUDGET.updateOpGroupBudgetFixed", item);
	}
	/**
	 * 更新产品成本项
	 */
	public void updateOpGroupBudgetProd(OpGroupBudgetProd item){
		super.update("GROUP_BUDGET.updateOpGroupBudgetProd", item);		
	}
	/**
	 * 删除固定成本项
	 */
	public void deleteOpGroupBudgetFixed(Long id){
		super.delete("GROUP_BUDGET.deleteOpGroupBudgetFixed", id);
	}
	
	/**
	 * 删除固定成本项
	 */
	public void deleteOpGroupBudgetFixedByParam(Map parameter){
		super.delete("GROUP_BUDGET.deleteOpGroupBudgetFixedByParam", parameter);
	}
	
	/**
	 * 删除固定成本项
	 */
	public void deleteOpGroupBudgetProdByParam(Map parameter){
		super.delete("GROUP_BUDGET.deleteOpGroupBudgetProdByParam", parameter);
	}
	
	/**
	 * 新增预算表
	 * @param budget
	 * @return
	 */
	public Long insertGroupBudget(OpGroupBudget budget){
		return (Long)super.insert("GROUP_BUDGET.insertGroupBudget",budget);
	}
	/**
	 * 更新预算表
	 * @param budget
	 * @return
	 */
	public void updateGroupBudget(OpGroupBudget budget){
		super.update("GROUP_BUDGET.updateGroupBudget",budget);
	}
	/**
	 * 生成产品明细
	 * @param groupCode
	 */
	public Long insertGroupBudgetProd(OpGroupBudgetProd prod){
		return (Long)super.insert("GROUP_BUDGET.insertGroupBudgetProd",prod);
	}
	
	/**
	 * 查询团信息
	 */
	public OpTravelGroup getOpTravelGroupByCode(String code){
		return (OpTravelGroup)super.queryForObject("GROUP_BUDGET.getOpTravelGroupByCode", code);
	}
	/**
	 * 更新预算产品结算状态
	 */
	public void	updateBudgetProdSettlementStatus(String groupCode,Long branchId,String status){
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("groupCode", groupCode);
		map.put("branchId", branchId);
		map.put("status", status);
		super.update("GROUP_BUDGET.updateBudgetProdSettlementStatus",map);
	}
	/**
	 * 更新产品成本结算状态
	 */
	public void updateBudgetProdSettlementStatus(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateBudgetProdSettlementStatusById",map);
	}
	/**
	 * 更新预算固定成本结算状态
	 */
	public void	updateBudgetFixedSettlementStatus(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateBudgetFixedSettlementStatus",map);
	}
	
	
	/**
	 * 查询固定成本项
	 */
	public OpGroupBudgetFixed getBudgetFixedByItemId(Long itemId){
		return (OpGroupBudgetFixed)super.queryForObject("GROUP_BUDGET.getBudgetFixedByItemId",itemId);
	}
	
	public List<OptionItem> getFixedOptions(){
		return (List<OptionItem>)super.queryForList("GROUP_BUDGET.getFixedOptions");
	}
	public List<OptionItem> getCurrencyOptions(){
		return (List<OptionItem>)super.queryForList("GROUP_BUDGET.getCurrencyOptions");
	}
	public List<OptionItem> getProductManagerForAutocomplete(String term){
		return (List<OptionItem>)super.queryForList("GROUP_BUDGET.getProductManagerForAutocomplete",term);
	}
	public List<OptionItem> getSupplierForAutocomplete(String term){
		return (List<OptionItem>)super.queryForList("GROUP_BUDGET.getSupplierForAutocomplete",term);
	}
	public List<OptionItem> getTargetForAutocomplete(Map<String, Object> map){
		return (List<OptionItem>)super.queryForList("GROUP_BUDGET.getTargetForAutocomplete",map);
	}
	/**
	 * 查询实际产品成本
	 */
	public List<OpGroupBudgetProd> getGroupBudgetProdListFromOrderByGroupCode(Map<String,Object> parameter){
		return super.queryForList("GROUP_BUDGET.selectGroupBudgetProdListFromOrderByParam",parameter);
	}
	/**
	 * 查询实际产品成本（有退款信息的订单）
	 */
	public List<OpGroupBudgetProdRefund> selectGroupBudgetProdRefundListFromOrderByParam(Map<String,Object> parameter){
		return super.queryForList("GROUP_BUDGET.selectGroupBudgetProdRefundListFromOrderByParam",parameter);
	}
	/**
	 * 查询其他成本总额
	 */
	public double getSumGroupBudgetFixedByGroupCode(Map<String,Object> parameter){
		return (Double) super.queryForObject("GROUP_BUDGET.selectSumGroupBudgetFixedByParam",parameter);
	}
	
	/**
	 * 查询其他收入
	 */
	public List<OpOtherIncoming> getOtherIncomingByGroupCode(Map<String,Object> parameter){
		return super.queryForList("GROUP_BUDGET.selectOtherIncomingListByParam",parameter);
	}
	
	/**
	 * 查询其他收入总额
	 */
	public double getSumOtherIncomingByGroupCode(Map<String,Object> parameter){
		return (Double) super.queryForObject("GROUP_BUDGET.selectSumOtherIncomingByParam",parameter);
	}
	/**
	 * 查询活动转让
	 * @param map
	 * @return
	 */
	public Double getOrderAllowanceSum(Map<String, Object> map){
		return (Double)super.queryForObject("GROUP_BUDGET.getOrderAllowanceSum",map);
	}
	/**
	 * 查询团订单中的保险总收入
	 * @param groupCode
	 * @return
	 */
	public Long getTotalInsuranceIncomingByGroupCode(String groupCode){
		return (Long)super.queryForObject("GROUP_BUDGET.getTotalInsuranceIncomingByGroupCode",groupCode);
	}
	
	/**
	 * 查询其他收入总额
	 */
	public Long getSumActuralSettlePriceByGroupCode(Map<String,Object> parameter){
		return (Long) super.queryForObject("GROUP_BUDGET.selectSumActuralSettlePriceByParam",parameter);
	}
	/**
	 * 根据团号查询退款金额合计
	 * @param groupCode 团号
	 * @return 退款金额合计
	 */
	public double getSubRefundAmountByGroupCode(String groupCode){
		return (Double) super.queryForObject("GROUP_BUDGET.getSubRefundAmountByGroupCode",groupCode);
	}
	/**
	 * 新增附加收入
	 * @param incoming
	 * @return
	 */
	public Long insertOtherIncoming(OpOtherIncoming incoming){
		return (Long)super.insert("GROUP_BUDGET.insertOtherIncoming",incoming);
	}
	
	/**
	 * 更新附加收入
	 */
	public void updateOtherIncoming(OpOtherIncoming incoming){
		super.update("GROUP_BUDGET.updateOtherIncoming",incoming);
	}
	/**
	 * 删除附加收入
	 * @param id
	 */
	public void deleteOtherIncoming(Long id){
		super.delete("GROUP_BUDGET.deleteOtherIncoming",id);
	}
	/**
	 * 查询附加收入
	 * @param map
	 * @return
	 */
	public List<OpOtherIncoming> getOtherIncoming(Map<String, Object> map){
		return (List<OpOtherIncoming>)super.queryForList("GROUP_BUDGET.getOtherIncoming",map);
	}
	/**
	 * 查询催款记录
	 * @param map
	 * @return
	 */
	public FinGroupSettlement getFinGroupSettlement(Map<String, Object> map){
		return (FinGroupSettlement)super.queryForObject("GROUP_BUDGET.getFinGroupSettlement",map);
	}
	/**
	 * 插入催款记录
	 * @param settlement
	 */
	public void insertRequirePay(FinGroupSettlement settlement){
		super.insert("GROUP_BUDGET.insertRequirePay",settlement);
	}

	/**
	 * 更新催款记录
	 */
	public void updateFinGroupSettlement(FinGroupSettlement settlement){
		super.update("GROUP_BUDGET.updateFinGroupSettlement",settlement);
	}
	
	/**
	 * 更新催款记录 的成本明细ID
	 * @param map
	 */
	public void updateFinGroupSettlementBudgetItemId(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateFinGroupSettlementBudgetItemId",map);
	}
	/**
	 * 更新延迟时间
	 * @return
	 */
	public void updateDelayTime(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateDelayTime",map);
	}
	
	/**
	 * 查询币种最新的汇率
	 * @param currency
	 * @return
	 */
	public Double getLatestExchangeRateByCurrency(String currency){
		return (Double)super.queryForObject("GROUP_BUDGET.getExchangeRateByCurrency",currency);
	}
	/**
	 * 查询产品订单总数
	 * @param map
	 * @return
	 */
	public long getProductOrderDetailsCount(Map<String, Object> map){
		return (Long)super.queryForObject("GROUP_BUDGET.getProductOrderDetailsCount",map);
	}
	/**
	 * 查询产品订单列表
	 * @param map
	 * @return
	 */
	public List<ProductOrderDetail> getProductOrderDetails(Map<String, Object> map){
		return (List<ProductOrderDetail>)super.queryForList("GROUP_BUDGET.getProductOrderDetails",map);
	}
	
	public List<ComLog> getLogs(Long groupId,String logType){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("logType", logType);
		return super.queryForList("GROUP_BUDGET.getLogs",map);
	}
	
	/**
	 * 更新预算成本明细的单项结算总额（人民币）
	 * @param itemId 明细项id (必须是实际成本表的ID)
	 * @param itemType 明细项类型。产品明细：PRODUCT;固定成本明细：FIXED
	 * @param subTotalCostsFc 单项总成本（外币） 单位：分
	 * @return
	 */
	public boolean updateBudgetItemSubtotalCosts(Long itemId, String itemType,Double subTotalCostsFc,Double defaultRate){
		Map map = new HashMap();
		map.put("itemId", itemId);
		map.put("type", itemType);
		List<OpGroupBudgetProd> prodList = super.queryForList("GROUP_BUDGET.searchSubTotalCosts",map);
		//已打款的外币总和
		Double payedsubTotalCostsFc =0d;
		//已打款的人民币总和
		Double payedsubTotalCosts =0d;
		Double rate = null;
		if(prodList == null || prodList.size() == 0){
			rate = defaultRate;
			payedsubTotalCostsFc = 0d;
			payedsubTotalCosts = 0d;
		}else{
			rate = prodList.get(0).getExchangeRate();
			for(OpGroupBudgetProd prod:prodList){
				Double r = prod.getExchangeRate();
				Double amount = prod.getSubtotalCostsFc();
				payedsubTotalCostsFc += amount;
				payedsubTotalCosts = payedsubTotalCosts+(amount*r);
			}
		}
		Double unPay = subTotalCostsFc - payedsubTotalCostsFc;
		if("PRODUCT".equals(itemType)){
			OpGroupBudgetProd prod = new OpGroupBudgetProd();
			prod.setItemId(itemId);
			prod.setSubtotalCosts(payedsubTotalCosts+unPay*rate);
			prod.setExchangeRate(rate);
			super.update("GROUP_BUDGET.updateProdSubTotalCosts",prod);
		}else{
			OpGroupBudgetFixed fixed = new OpGroupBudgetFixed();
			fixed.setItemId(itemId);
			fixed.setSubtotalCosts(payedsubTotalCosts+unPay*rate);
			fixed.setExchangeRate(rate);
			super.update("GROUP_BUDGET.updateFixedSubTotalCosts",fixed);
		}
		return true;
	}
	
	public Long getActOrderPersonNum(String travelCode){
		return (Long)super.queryForObject("GROUP_BUDGET.getActOrderPersonNum",travelCode);
	}
	
	/**
	 * 查询供应商预付款总额
	 */
	public Double getSupAdvanceAmount(Long supplierId,String currencyType){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("currencyType", currencyType);
		return (Double)super.queryForObject("GROUP_BUDGET.getSupAdvanceAmount",map);
	}
	/**
	 * 删除未打款的明细项
	 * @param travelGroupId
	 */
	public void deleteFinGroupSettlementByGroupId(Long travelGroupId){
		super.update("GROUP_BUDGET.deleteFinGroupSettlementByGroupId",travelGroupId);
	}
	/**
	 * 根据团id，把已打款的明细项生成抵扣款
	 * @param travelGroupId
	 */
	public void createMoneyForChargeByGroupId(Long travelGroupId){
		super.update("GROUP_BUDGET.createMoneyForChargeByGroupId",travelGroupId);
	}
	/**
	 * 查询团是否有订单存在
	 * @param groupCode
	 * @return
	 */
	public int hasOrderByGroupCode(String groupCode){
		Long c = (Long)super.queryForObject("GROUP_BUDGET.getOrderNumByGroupCode",groupCode);
		return c > 0? 1 : 0;
	}
	
	/**
	 * 查询团号
	 * @param map
	 * @return
	 */
	public List<String> getGroupCode(Map<String, Object> map){
		return (List<String>)super.queryForList("GROUP_BUDGET.getGroupCode",map);
	}
	/**
	 * 查询产品明细币种
	 * @param itemId
	 * @return
	 */
	public String getProductCurrencyByItemId(Long itemId){
		return (String)super.queryForObject("GROUP_BUDGET.getProductCurrencyByItemId", itemId);
	}
	/**
	 * 查询固定成本明细币种
	 * @param itemId
	 * @return
	 */
	public String getFixedCurrencyByItemId(Long itemId){
		return (String)super.queryForObject("GROUP_BUDGET.getFixedCurrencyByItemId", itemId);
	}
	
	/**
	 * 更新实际人数
	 */
	public void updateActPersons(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateActPersons", map);
	}
	/**
	 * 是否加入总成本
	 */
	public void updateIsInCostBudget(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateIsInCostBudget",map);
	}
	/**
	 * 是否加入总成本
	 */
	public void updateIsInCost(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateIsInCost",map);
	}
	/**
	 * 修改订单子子项的结算状态
	 */
	public void updateOrderItemMetaSettlementStatus(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateOrderItemMetaSettlementStatus",map);
	}
	/**
	 * 修改订单的结算状态
	 */
	public void updateOrderSettlementStatus(Map<String, Object> map){
		super.update("GROUP_BUDGET.updateOrderSettlementStatus",map);
	}
	
	/**
	 * 查询实际成本的采购产品明细
	 * @param metaBranchId 采购产品分类ID
	 * @param travelGroupCode 团号
	 * @return
	 */
	public OpGroupBudgetProd getCostGroupBudgetProdByBranchId(Long metaBranchId,String travelGroupCode){
		Map paramerter = new HashMap();
		paramerter.put("type", "COST");
		paramerter.put("prodBranchId", metaBranchId);
		paramerter.put("travelGroupCode", travelGroupCode);
		return (OpGroupBudgetProd)super.queryForObject("GROUP_BUDGET.getGroupBudgetProductByParam",paramerter);
	}
	/**
	 * 生成抵扣款
	 * @param settlement
	 */
	public void insertDeduction(FinGroupSettlement settlement){
		super.insert("GROUP_BUDGET.insertDeduction",settlement);
	}
	/**
	 * 根据团号查询实际收入
	 * @param travelCode
	 * @return
	 */
	public Double getTotalOrderActualPay(String travelCode) {
		return (Double) super.queryForObject("GROUP_BUDGET.getTotalOrderActualPay",travelCode);
	}
}
//TODO
