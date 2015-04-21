package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderPersonService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderItemMetaAperiodicDAO;

public class OrderItemMetaAperiodicServiceImpl extends OrderServiceImpl
		implements OrderItemMetaAperiodicService {

	private OrderItemMetaAperiodicDAO orderItemMetaAperiodicDAO;
	private OrderService orderServiceProxy;
	private OrderPersonService orderPersonService;

	@Override
	public ResultHandle updateStatusByPrimaryKey(
			final OrdOrderItemMetaAperiodic ordAperiodic) {

		ResultHandle result = new ResultHandle();
		int i = orderItemMetaAperiodicDAO
				.updateStatusByPrimaryKey(ordAperiodic);
		if (i <= 0) {
			result.setMsg("密码券修改失败!");
			return result;
		}
		return result;
	}

	/**
	 * 不定期订单激活状态修改
	 * 
	 * @author: zhangjie
	 * @param orderId
	 *            订单号
	 * @param visitPerName
	 *            客人姓名
	 * @param mobile
	 *            客人电话号码
	 * @param visitTimeStart
	 *            入住时间
	 * @param passwordUseStatus
	 *            激活状态
	 * @param userId
	 *            操作人ID
	 * @return
	 */
	@Override
	public ResultHandle updateAperiodicOrderUseStatus(final Long orderId,
			String visitPerName, String mobile, String visitTimeStart,
			String passwordUseStatus, String userId, List<Long> orderItemMetaIds) {
		ResultHandle result = new ResultHandle();
		if (orderItemMetaIds == null || orderItemMetaIds.isEmpty()) {
			result.setMsg("发生异常,操作失败！");
			return result;
		}
		Date currDate = new Date();
		// 需确认游玩人是否修改
		for (Long itemMetaId : orderItemMetaIds) {
			OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicDAO
					.selectOrderAperiodicByOrderItemId(itemMetaId);
			if (aperiodic == null) {
				result.setMsg("找不到该密码券对应的订单，操作失败！");
				return result;
			}
			aperiodic.setActivationStatus(passwordUseStatus);
			if (Constant.APERIODIC_ACTIVATION_STATUS.ACTIVATED.name()
					.equalsIgnoreCase(passwordUseStatus)) {
				aperiodic.setUsedTime(currDate);
			} else {
				aperiodic.setUsedTime(null);
			}
			result = this.updateStatusByPrimaryKey(aperiodic);
		}
		if (result.isSuccess()) {
			// 修改游玩时间
			result = orderServiceProxy.updateOrderVisitTime(orderId,
					visitTimeStart, userId, orderItemMetaIds);
		}
		return result;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public OrderPersonService getOrderPersonService() {
		return orderPersonService;
	}

	public void setOrderPersonService(OrderPersonService orderPersonService) {
		this.orderPersonService = orderPersonService;
	}

	public OrderItemMetaAperiodicDAO getOrderItemMetaAperiodicDAO() {
		return orderItemMetaAperiodicDAO;
	}

	public void setOrderItemMetaAperiodicDAO(
			OrderItemMetaAperiodicDAO orderItemMetaAperiodicDAO) {
		this.orderItemMetaAperiodicDAO = orderItemMetaAperiodicDAO;
	}

	@Override
	public boolean isOrderActivated(Long orderId) {
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (order != null) {
			List<OrdOrderItemMeta> metaList = order.getAllOrdOrderItemMetas();
			for (OrdOrderItemMeta ordOrderItemMeta : metaList) {
				if (ordOrderItemMeta.isHotelProductType()
						|| ordOrderItemMeta.isRouteProductType()) {
					OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicDAO
							.selectOrderAperiodicByOrderItemId(ordOrderItemMeta
									.getOrderItemMetaId());
					if (aperiodic != null) {
						if (Constant.APERIODIC_ACTIVATION_STATUS.ACTIVATED
								.name().equalsIgnoreCase(
										aperiodic.getActivationStatus())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public OrdOrderItemMetaAperiodic selectOrderAperiodicByOrderItemMetaId(
			Long orderItemMetaId) {
		return orderItemMetaAperiodicDAO
				.selectOrderAperiodicByOrderItemId(orderItemMetaId);
	}

	@Override
	public OrdOrderItemMetaAperiodic selectFirstOrderAperiodicByOrderId(Long orderId) {
		List<OrdOrderItemMetaAperiodic> list = orderItemMetaAperiodicDAO.selectOrderAperiodicByOrderId(orderId);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> checkPasswordCertificate(Long orderId,
			Long supplierId, String passwordCertificate, String visitTime) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (ordOrder == null) {
			resultMap.put("message", "订单异常");
			return resultMap;
		}
		List<OrdOrderItemMeta> itemMetas = ordOrder.getAllOrdOrderItemMetas();
		if (itemMetas == null || itemMetas.isEmpty()) {
			resultMap.put("message", "订单异常");
			return resultMap;
		}
		Date visitDate = DateUtil.toDate(visitTime, "yyyy-MM-dd");
		boolean flag = true, canPass = true;
		StringBuffer message = new StringBuffer();
		for (OrdOrderItemMeta itemMeta : itemMetas) {
			if (itemMeta.getSupplierId().longValue() != supplierId.longValue()) {
				continue;
			}
			Long itemMetaId = itemMeta.getOrderItemMetaId();
			OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicDAO
					.selectOrderAperiodicByOrderItemId(itemMetaId);
			if (aperiodic == null) {
				resultMap.put("message", "验证异常");
				return resultMap;
			}
			if (StringUtils.isNotEmpty(passwordCertificate) && !aperiodic.getPasswordCertificate().equalsIgnoreCase(
					passwordCertificate)) {
				resultMap.put("message", "密码券错误,校验失败");
				return resultMap;
			}
			message.append(itemMeta.getZhBranchName()+":"+DateUtil.formatDate(aperiodic.getValidBeginTime(), "yyyy-MM-dd")+" 至 "+DateUtil.formatDate(aperiodic.getValidEndTime(), "yyyy-MM-dd"));
			if(StringUtils.isNotEmpty(aperiodic.getInvalidDateMemo())) {
				message.append("("+aperiodic.getInvalidDateMemo()+")\n");
			}
			if(visitDate.before(aperiodic.getValidBeginTime())){
				flag = false;
				message.append("未到有效期\n\n");
			}else if(visitDate.after(aperiodic.getValidEndTime())){
				flag = false;
				message.append("过了有效期\n\n");
			} else {
				if(!aperiodic.validateInvalidDate(visitTime)) {
					message.append("今日不可游玩\n\n");
					canPass = false;
				}
			}
		}
		if(!flag || !canPass) {
			if(canPass) {
				if(StringUtils.isNotEmpty(passwordCertificate)) {
					message.append("是否确定通关？");
				} else {
					message.append("是否确定激活？");
				}
			}
			resultMap.put("message", message.toString());
		}
		resultMap.put("canPass", canPass);
		return resultMap;
	}

	@Override
	public Map<Long, String> getAperiodicStatusByOrderId(Long orderId) {
		Map<Long, String> map = new HashMap<Long, String>();
		List<OrdOrderItemMetaAperiodic> list = orderItemMetaAperiodicDAO.selectOrderAperiodicByOrderId(orderId);
		for (OrdOrderItemMetaAperiodic aperiodic : list) {
			map.put(aperiodic.getOrderItemMetaId(), aperiodic.getZhActivationStatus());
		}
		return map;
	}

}
