package com.lvmama.vst.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.vst.service.VstOrderRefundmentService;
import com.lvmama.order.dao.OrdOrderAmountApplyDAO;
import com.lvmama.order.dao.OrderRefundmentDAO;
import com.lvmama.order.service.OrderRefundmentService;

public class VstOrderRefundmentServiceImpl implements VstOrderRefundmentService {
	
	private OrderRefundmentService orderRefundmentService;
	
	private OrderRefundmentDAO orderRefundmentDAO;
	
	private OrdOrderAmountApplyDAO ordOrderAmountApplyDAO;

	@Override
	public List<OrdRefundment> findOrderRefundmentByOrderIdStatus(Long orderId,String status) {
		return orderRefundmentDAO.queryVstOrdRefundmentByOrderIdAndStatus(orderId,status,null);
	}

	@Override
	public List<OrdRefundMentItem> queryOrdRefundmentItemById(Long refundmentId) {
		return orderRefundmentDAO.queryOrdRefundmentItemById(refundmentId);
	}

	@Override
	public List<OrdRefundMentItem> findOrderRefundMentItemByOrderItemMetaId(Long orderItemMetaId) {
		return orderRefundmentDAO.findOrderRefundMentItemByOrderItemMetaId(orderItemMetaId);
	}

	@Override
	public List<OrdOrderAmountApply> selectByOrdOrderAmountApply(
			Map<String, Object> parameter) {
		return ordOrderAmountApplyDAO.selectByOrdOrderAmountApply(parameter);
	}

	@Override
	public OrdRefundment queryOrdRefundmentById(Long refundmentId) {
		return orderRefundmentDAO.queryOrdRefundmentById(refundmentId);
	}

	@Override
	public boolean updateOrdRefundmentStatusById(Long refundmentId,
			String status) {
		boolean stat = orderRefundmentDAO.updateOrdRefundmentStatusById(refundmentId, status);
		return stat;
	}

	@Override
	public boolean updateOrdRefundment(OrdRefundment ordRefundment) {
		return orderRefundmentDAO.updateOrdRefundment(ordRefundment);
	}

	public void setOrderRefundmentService(
			OrderRefundmentService orderRefundmentService) {
		this.orderRefundmentService = orderRefundmentService;
	}

	public void setOrderRefundmentDAO(OrderRefundmentDAO orderRefundmentDAO) {
		this.orderRefundmentDAO = orderRefundmentDAO;
	}

	public void setOrdOrderAmountApplyDAO(
			OrdOrderAmountApplyDAO ordOrderAmountApplyDAO) {
		this.ordOrderAmountApplyDAO = ordOrderAmountApplyDAO;
	}

	

}
