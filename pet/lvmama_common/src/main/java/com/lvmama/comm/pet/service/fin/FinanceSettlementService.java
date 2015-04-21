package com.lvmama.comm.pet.service.fin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;
@RemoteService("financeSettlementService")
public interface FinanceSettlementService {
	public Page<SetSettlementItem> searchItemListPage(Map<String, Object> map);
	public List<SetSettlementItem> searchItemList(Map<String, Object> map) ;
	
	public Page<SetSettlement> searchSettleListPage(Map<String,Object> map);
	public List<SetSettlement> searchSettleList(Map<String,Object> map);
	
	public Map<String, Object> toPay(SetSettlement settlement, Long advanceDepositPayAmount, Long deductionPayAmount, Long bankPayAmount, String bankName, Date operatetime, String serial, String operatorName);
	public int addOrder(Long settlementId, List<Long> settlementItemIds, String operator);
	public int removeSettlementItem(Long settlementId, List<Long> settlementItemIds, String operator);
	public Long searchSumprice(Map<String,Object> map);
	public Map<String, Object>  settle(SetSettlement settlement, String memo, String operatorName,VstSuppSupplierVo sup) ;
	public void updateSettlementPrice(List<SetSettlementItem> setSettlementItems,Long actualSettlementPrice, String operatorName, String messageType);
	
	SetSettlement getSetSettlementById(Long settlementId);
	
	/**
	 * 获取结算子项信息
	 * @param setSettlementItemId 结算子项id
	 * @return 结算子项信息
	 */
	SetSettlementItem getSetSettlementItem(Long setSettlementItemId);
}
