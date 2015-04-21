package com.lvmama.ord.service.impl;

import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.service.GroupAdviceNoteService;
import com.lvmama.ord.dao.OrdGroupAdviceNoteDAO;

public class GroupAdviceNoteServiceImpl implements GroupAdviceNoteService{
	private OrdGroupAdviceNoteDAO ordGroupAdviceNoteDAO;
	/**
	 * 根据订单号查询销售产品
	 */
	public OrdOrderItemProd getOrderItemProdByOrderId(Long orderId){
		return ordGroupAdviceNoteDAO.getOrderItemProdByOrderId(orderId);
	}
	/**
	 * 更据订单号查询出团通知书状态
	 */
	public OrdOrderRoute getOrderRouteByOrderId(Long orderId){
		return ordGroupAdviceNoteDAO.getOrderRouteByOrderId(orderId);
	}
	
	/**
	 * 更新出团通知书状态
	 */
	public void updateOrderGroupWordStatus(Long orderId, String status){
		ordGroupAdviceNoteDAO.updateOrderGroupWordStatus(orderId,status);
	}
	
	public OrdGroupAdviceNoteDAO getOrdGroupAdviceNoteDAO() {
		return ordGroupAdviceNoteDAO;
	}
	public void setOrdGroupAdviceNoteDAO(OrdGroupAdviceNoteDAO ordGroupAdviceNoteDAO) {
		this.ordGroupAdviceNoteDAO = ordGroupAdviceNoteDAO;
	}
	
	
}
