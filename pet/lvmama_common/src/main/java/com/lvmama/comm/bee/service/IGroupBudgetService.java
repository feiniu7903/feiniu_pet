package com.lvmama.comm.bee.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.op.OpGroupBudget;
import com.lvmama.comm.bee.po.op.OpGroupBudgetFixed;
import com.lvmama.comm.bee.po.op.OpGroupBudgetProd;
import com.lvmama.comm.bee.po.op.OpOtherIncoming;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.OptionItem;

public interface IGroupBudgetService {
	public void log(String type,String name,Long groupId, String content, String userName);
	public Long getGroupListCount(Map<String, String> param);
	public List<OpTravelGroup> getGroupList(Map<String, String> param);
	public List<OpTravelGroup> getGroupListForExport(Map<String, String> map);
	
	public OpGroupBudget getGroupBudgetByGroupCode(String travelGroupCode);
	public List<OpGroupBudgetProd> getGroupProductByGroupCode(String travelGroupCode);

	public List<OpGroupBudgetProd> getGroupBudgetProductByGroupCode(String travelGroupCode, String groupProdType);
	public List<OpGroupBudgetFixed> getGroupBudgetFixedByGroupCode(String travelGroupCode);
	
	public List<OptionItem> getFixedOptions();
	public List<OptionItem> getCurrencyOptions();
	public List<OptionItem> getProductManagerForAutocomplete(String term);
	public List<OptionItem> getSupplierForAutocomplete(String term);
	public List<OptionItem> getTargetForAutocomplete(Map<String, Object> map);
	public Long insertOpGroupBudgetFixed(OpGroupBudgetFixed item);
	public void updateOpGroupBudgetFixed(OpGroupBudgetFixed item);
	public void deleteOpGroupBudgetFixed(Long id);	
	public Long saveGroupBudget(OpGroupBudget budget);	
	public OpGroupBudget countFinalBudgetByTravelCode(String travelCode);
	public OpTravelGroup getOpTravelGroupByCode(String code);
	
	public void updateBudgetProdSettlementStatus(Map<String, Object> map);
	public void	updateBudgetFixedSettlementStatus(Map<String, Object> map);
	public void updateDelayTime(Map<String, Object> map);
	
	public OpGroupBudgetFixed getBudgetFixedByItemId(Long itemId);
	public List<ComLog> getLogs(Long groupId,String logType);
	
	
	public List<OpOtherIncoming> getGroupIncomingByGroupCode(String travelGroupCode);
	public List<OpGroupBudgetProd> getGroupFinalBudgetProductByGroupCode(String travelGroupCode);
	public List<OpGroupBudgetFixed> getGroupFinalBudgetFixedByGroupCode(String travelGroupCode);
	
	public Long saveOtherIncoming(OpOtherIncoming incoming);
	public void deleteOtherIncoming(Long id);
	
	public List<OpOtherIncoming> getOtherIncoming(Map<String, Object> map);
	public Page getProductOrderDetailsByPage(Page page,Map<String, Object> map);
	
	public Double getSupAdvanceAmount(Long supplierId, String currencyType);
	public Double getReqPayAmount(Long itemId,String type);
	
	public void cancelTravelGroupHandler(Long travelGroupId);
	
	public int hasOrderByGroupCode(String groupCode);
	
	public List<String> getGroupCode(Map<String, Object> map);
	
	public void autoCreateFinalBudget(String groupCode);
	
	public String getCurrencyByItemId(Long itemId, String currency);
	
	public void updateActPersons(Map<String, Object> map);
	
	public void updateIsInCostBudget(Map<String, Object> map);
	public void updateIsInCost(Map<String, Object> map);
	
	public Long insertGroupBudgetProd(OpGroupBudgetProd prod);
	public void updateToCosted(String paraGroupTravelCode, String paraStatus);
}
