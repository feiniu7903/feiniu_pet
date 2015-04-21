package com.lvmama.finance.group.ibatis.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.op.ProductOrderDetail;
import com.lvmama.comm.pet.vo.FincConstant;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.group.ibatis.po.TravelGroup;

/**
 * 团信息 DAO
 * 
 * @author yanggan
 * 
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OpTravelGroupDAO extends PageDao {

	/**
	 * 查询团列表
	 * 
	 * @return
	 */
	public Page<TravelGroup> searchList() {
		Map map = FinanceContext.getPageSearchContext().getContext();
		String gs = (String) map.get("travelGroupStatus");
		if (!StringUtil.isEmptyString(gs)) {
			String[] gss = gs.split(",");
			map.put("travelGroupStatus", gss);
		} else {
			map.remove("travelGroupStatus");
		}
		String ss = (String) map.get("settlementStatus");
		if (!StringUtil.isEmptyString(ss)) {
			String[] sss = ss.split(",");
			map.put("settlementStatus", sss);
		} else {
			map.remove("settlementStatus");
		}
		String rt = (String) map.get("routeType");
		if (!StringUtil.isEmptyString(rt)) {
			String[] rtype = rt.split(",");
			map.put("routeType", rtype);
		} else {
			map.remove("routeType");
		}
		return queryForPageFin("OpTravelGroup.searchList", map);
	}

	/**
	 * 根据团号查询团信息
	 * 
	 * @param groupCode
	 *            团号
	 * @return
	 */
	public TravelGroup searchGroup(String groupCode) {
		return (TravelGroup) queryForObject("OpTravelGroup.searchGroup", groupCode);
	}

	/**
	 * 核算
	 * 
	 * @param groupCode
	 *            团号
	 * @param userName
	 *            操作人
	 */
	public void checkGroup(String groupCode, String userName) {
		Map map = new HashMap();
		map.put("status",Constant.TRAVEL_GROUP_STETTLEMENT_STATUS.CHECKED.name());
		map.put("user", userName);
		map.put("groupCode", groupCode);
		update("OpTravelGroup.checkGroup", map);
	}

	public void confirmedGroupCost(String groupCode){
		Map map = new HashMap();
		map.put("status", Constant.TRAVEL_GROUP_STETTLEMENT_STATUS.CONFIRMED.name());
		map.put("groupCode", groupCode);
		update("OpTravelGroup.confirmedGroupCost", map);
	}
	/**
	 * 修改订单的结算状态
	 * 
	 * @param groupCode
	 *            团号
	 */
	public void updateOrderSettlementStatus(String groupCode) {
		Map map = new HashMap();
		map.put("status", Constant.SETTLEMENT_STATUS.SETTLEMENTED.name());
		map.put("groupCode", groupCode);
		update("OpTravelGroup.updateOrderSettlementStatus", map);
	}

	/**
	 * 修改订单的结算状态
	 * 
	 * @param groupCode
	 *            团号
	 */
	public void updateOrderItemMetaSettlementStatus(String groupCode) {
		Map map = new HashMap();
		map.put("status", Constant.SETTLEMENT_STATUS.SETTLEMENTED.name());
		map.put("groupCode", groupCode);
		update("OpTravelGroup.updateOrderItemMetaSettlementStatus", map);
	}

	public Page<ProductOrderDetail> searchProductOrderDetails() {
		return queryForPageFin("OpTravelGroup.searchProductOrderDetails", FinanceContext.getPageSearchContext().getContext());
	}
}
