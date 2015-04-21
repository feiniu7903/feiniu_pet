package com.lvmama.finance.group.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.op.OpGroupBudget;
import com.lvmama.comm.bee.po.op.OpOtherIncoming;
import com.lvmama.comm.bee.po.op.ProductOrderDetail;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.group.ibatis.po.FinGroupSettlement;
import com.lvmama.finance.group.ibatis.po.GroupSettlementInfo;
import com.lvmama.finance.group.ibatis.po.OrderInfoDetail;
import com.lvmama.finance.group.ibatis.po.TravelGroup;
import com.lvmama.finance.group.ibatis.vo.GroupBudgetFixed;
import com.lvmama.finance.group.ibatis.vo.GroupBudgetProd;

/**
 * 团列表Service
 * 
 * @author yanggan
 * 
 */
public interface GroupService {

	/**
	 * 查询团列表
	 * 
	 * @return
	 */
	Page<TravelGroup> searchList();

	/**
	 * 查询团预算信息
	 * 
	 * @param travelGroupCode
	 *            团号
	 * 
	 * @return 团预算信息
	 */
	OpGroupBudget searchBudget(String travelGroupCode);

	/**
	 * 查询团产品成本明细
	 * 
	 * @param travelGroupCode
	 *            团号
	 * @param type
	 *            类型
	 * @return
	 */
	List<GroupBudgetProd> searchBudgetProd(String travelGroupCode, String type);

	/**
	 * 查询团固定成本明细
	 * 
	 * @param travelGroupCode
	 *            团号
	 * @param type
	 *            类型
	 * @return
	 */
	List<GroupBudgetFixed> searchBudgetFixed(String travelGroupCode, String type);

	/**
	 * 根据团号查询团信息
	 * 
	 * @param groupCode
	 *            团号
	 * 
	 * @return
	 */
	TravelGroup searchGroup(String groupCode);

	/**
	 * 核算
	 * 
	 * @param groupCode
	 *            团号
	 */
	void check(String groupCode);

	/**
	 * 查询团单项结算
	 * 
	 * @return
	 */
	Page<FinGroupSettlement> searchSettlement();

	/**
	 * 线下打款
	 * 
	 * @param groupSettlementIds
	 *            团单项结算ID集合
	 * @param osp
	 *            打款信息
	 */
	String bankPay(SetSettlementPayment osp);

	/**
	 * 预存款打款
	 * 
	 * @param groupSettlementIds
	 *            团单项结算ID集合
	 * @param osp
	 *            打款信息
	 */
//	String advPay(Long[] groupSettlementIds, OrdSettlementPayment osp);

	/**
	 * 删除抵扣款
	 * 
	 * @param ids
	 *            团单项结算ID集合
	 */
	void deldk(String ids);
	
	GroupSettlementInfo searchGroupSettlement(Long id);
	
	/**
	 * 查询附加收入
	 * @param groupCode
	 * @return
	 */
	List<OpOtherIncoming> searchOtherIncoming(String groupCode);

	
	/**
	 * 产品成本查看订单明细
	 */
	Page<ProductOrderDetail> searchProductOrderDetails();
	
	Page<FinGroupSettlement> searchSettlementSumprice();
	
	Page<OrderInfoDetail> searchOrderInfoDetail(Map map);
	
	List<OrderInfoDetail> exportOrderInfoDetail(Map map);
	
	OrderInfoDetail searchSumprice(Map map);
	
	FinGroupSettlement searchSettlementById(Long groupSettlementId);
	
	List<OrderInfoDetail> exportOrderDetail();
	
	List<FinGroupSettlement> searchById(String[] groupSettlementIds);
	void confirmedGroupCost(final String groupCode);

}
