package com.lvmama.finance.group.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.group.ibatis.po.FinGroupSettlement;
import com.lvmama.finance.group.ibatis.po.GroupSettlementInfo;
import com.lvmama.finance.group.ibatis.po.OrderInfoDetail;

/**
 * 团结算DAO
 * 
 * @author yanggan
 * 
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FinGroupSettlementDAO extends PageDao {

	private Map settlementMap = new HashMap();
	/**
	 * 生成抵扣款信息
	 * 
	 * @param fgsList
	 */
	public void insertDeduction(List<FinGroupSettlement> fgsList) {
		this.insertList("FinGroupSettlement.insertDeduction", fgsList);
	}

	
	public Page<FinGroupSettlement> searchSettlementSumprice() {
		settlementMap = FinanceContext.getPageSearchContext().getContext();
		String rt = (String) settlementMap.get("routeType");
		if (!StringUtil.isEmptyString(rt)) {
			String[] rtype = rt.split(",");
			settlementMap.put("routeType", rtype);
		} else {
			settlementMap.remove("routeType");
		}
		
		return queryForPageFin("FinGroupSettlement.searchSettlementSumprice", settlementMap);
	}
	/**
	 * 查询团单项结算
	 * 
	 * @return
	 */
	public Page<FinGroupSettlement> searchSettlement() {
		
		return queryForPageFin("FinGroupSettlement.searchSettlement", settlementMap);
	}

	/**
	 * 根据ID查询单项结算信息
	 * @param id
	 * @return
	 */
	public List<FinGroupSettlement> searchSettlementByIds(Long[] groupSettlementIds) {
		return   queryForList("FinGroupSettlement.searchSettlementByIds",groupSettlementIds);
	}

	/**
	 * 打款
	 * @param groupSettlementIds
	 * @param exchangeRate
	 * @param amount
	 * @param remark
	 * @param status
	 */
	public void pay(Long[] groupSettlementIds, Double exchangeRate, Double amount, String remark, String status) {
		Map map = new HashMap();
		map.put("groupSettlementIds",groupSettlementIds);
		map.put("exchangeRate",exchangeRate);
		if(amount != null){
			map.put("amount",amount);
		}
		map.put("remark",remark);
		map.put("status",status);
		update("FinGroupSettlement.pay",map);
	}

	/**
	 * 删除抵扣款
	 * @param idList
	 */
	public void deldk(String[] idList) {
		this.update("FinGroupSettlement.deldk",idList);
	}

	/**
	 * 修改抵扣款金额
	 * @param fgs
	 */
	public void updateDkAmount(FinGroupSettlement fgs) {
		this.update("FinGroupSettlement.updateDkAmount",fgs);
	}
	
	
	/**
	 * 根据团单项结算ID查询已支付的金额汇总信息
	 * @param idList
	 * @return
	 */
	public List<FinGroupSettlement> searchPayedAmount(Long[] idList){
		return this.queryForList("FinGroupSettlement.searchPayedAmount",idList);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public GroupSettlementInfo searchGroupSettlement(Long settlementId) {
		return (GroupSettlementInfo)this.queryForObject("FinGroupSettlement.searchGroupSettlement", settlementId);
	}
	
	public Page<OrderInfoDetail> searchOrderInfoDetail(Map map) {
		return queryForPageFin("FinGroupSettlement.searchOrderInfoDetail", map);
	}
	
	public List<OrderInfoDetail> exportOrderInfoDetail(Map map) {
		return queryForListForReport("FinGroupSettlement.exportOrderInfoDetail", map);
	}

	public OrderInfoDetail searchSumPrice(Map map){
		return (OrderInfoDetail) queryForObject("FinGroupSettlement.searchOrderDetailSumprice", map);
	}
	
	public FinGroupSettlement searchSettlementById(Long groupSettlementId) {
		return (FinGroupSettlement)queryForObject("FinGroupSettlement.searchSettlementById", groupSettlementId);
	}
	
	public List<OrderInfoDetail> exportOrderDetail() {
		Map map = FinanceContext.getPageSearchContext().getContext();
		String rt = (String) map.get("routeType");
		if (!StringUtil.isEmptyString(rt)) {
			String[] rtype = rt.split(",");
			map.put("routeType", rtype);
		} else {
			map.remove("routeType");
		}
		return queryForListForReport("FinGroupSettlement.exportOrderDetail", map);
	}


	/**
	 * 根据订单子子项ID查询退款信息
	 * @param orderItemMetaId 订单子子项ID
	 */ 
	public List<OrdRefundMentItem> searchRefundmentDetail(Long orderItemMetaId) {
		return queryForList("FinGroupSettlement.queryOrdRefundmentItemByOrdItemMetaId",orderItemMetaId);
	}
	
	public List<FinGroupSettlement> searchById(String[] groupSettlementIds) {
		return this.queryForList("FinGroupSettlement.searchById", groupSettlementIds);
	}
}
