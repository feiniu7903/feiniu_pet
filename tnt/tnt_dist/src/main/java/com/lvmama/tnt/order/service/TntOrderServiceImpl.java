package com.lvmama.tnt.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.cashaccount.mapper.TntCashPayMapper;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.order.mapper.TntOrderMapper;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.user.mapper.TntCompanyTypeMapper;
import com.lvmama.tnt.user.mapper.TntUserMapper;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUser;

@Repository("tntOrderService")
public class TntOrderServiceImpl implements TntOrderService {

	private final Log log = LogFactory.getLog(TntOrderServiceImpl.class);
	@Autowired
	public TntOrderMapper tntOrderMapper;

	@Autowired
	public TntCashPayMapper tntCashPayMapper;

	@Autowired
	private OrderCreateService orderCreateService;

	@Autowired
	private TntCashaccountService tntCashaccountService;

	@Autowired
	private TntUserMapper tntUserMapper;

	@Autowired
	private TntCompanyTypeMapper tntCompanyTypeMapper;

	@Autowired
	private OrderService orderServiceProxy;

	@Autowired
	private OrdRefundMentService ordRefundMentService;

	@Override
	public boolean cancelOrder(TntOrder tntOrder) {
		String opid = "B2B_SYSTEM";
		opid = Constant.getInstance().getDefaultRegisterUser();
		if (orderServiceProxy.cancelOrder(tntOrder.getOrderId(), "分销B2B平台取消订单",
				opid)) {
			tntOrder.setOrderStatus(TntConstant.ORDER_STATUS.CANCEL.name());
			return tntOrderMapper.updateStatus(tntOrder) > 0;
		}
		return false;
	}

	@Override
	public void insert(TntOrder entity) {
		tntOrderMapper.insert(entity);
	}

	@Override
	public List<TntOrder> findPage(Page<TntOrder> page) {
		return tntOrderMapper.findPage(page);
	}

	@Override
	public int count(TntOrder tntOrder) {
		return tntOrderMapper.count(tntOrder);
	}

	@Override
	public int update(TntOrder tntOrder) {
		return tntOrderMapper.update(tntOrder);
	}

	@Override
	public int delete(Long tntOrderId) {
		return tntOrderMapper.delete(tntOrderId);
	}

	@Override
	public TntOrder getById(Long tntOrderId) {
		return tntOrderMapper.getById(tntOrderId);
	}

	@Override
	public TntOrder getByOrderId(Long orderId) {
		return tntOrderMapper.getByOrderId(orderId);
	}

	@Override
	public TntOrder get(TntOrder t) {
		return tntOrderMapper.selectOne(t);
	}

	public TntOrder getDetailByOrderId(Long orderId) {
		TntOrder tntOrder = tntOrderMapper.getByOrderId(orderId);
		orderCreateService.buildOrder(tntOrder);
		orderCreateService.buildPriceOrder(tntOrder);
		return tntOrder;
	}

	@Override
	public TntOrder getWithItems(TntOrder t) {
		t = get(t);
		if (t != null) {
			t = buildItems(t);
		}
		return t;
	}

	@Override
	public List<TntOrder> queryWithItems(List<Long> orderIds, Long userId) {
		List<TntOrder> orders = null;
		if (orderIds != null && userId != null) {
			orders = new ArrayList<TntOrder>();
			for (Long orderId : orderIds) {
				TntOrder t = new TntOrder();
				t.setOrderId(orderId);
				t.setDistributorId(userId);
				t = getWithItems(t);
				if (t != null) {
					orders.add(t);
				}
			}
		}
		return orders;
	}

	@Override
	public TntOrder buildItems(TntOrder t) {
		String result = orderCreateService.buildOrder(t);
		return result == null ? t : null;
	}

	@Override
	public List<TntOrder> queryCanPay(List<Long> orderIds, Long userId) {
		List<TntOrder> orders = null;
		if (orderIds != null && userId != null) {
			orders = new ArrayList<TntOrder>();
			for (Long orderId : orderIds) {
				TntOrder t = new TntOrder();
				t.setOrderId(orderId);
				t.setDistributorId(userId);
				t = get(t);
				if (t != null) {
					orders.add(t);
				}
			}
			removeCanotPay(orders);
		}
		return orders;
	}

	@Override
	public String checkOrder(TntOrder order) {
		String error = null;
		if (order == null) {
			error = "订单不存在";
		} else {
			Long orderId = order.getOrderId();
			if (!TntConstant.ORDER_STATUS.isNormal(order.getOrderStatus())) {
				error = "订单"
						+ orderId
						+ "已"
						+ TntConstant.ORDER_STATUS.getCnName(order
								.getOrderStatus());
			} else if (!TntConstant.PAYMENT_STATUS.isUnPayed(order
					.getPaymentStatus())) {
				error = "订单"
						+ orderId
						+ TntConstant.PAYMENT_STATUS.getCnName(order
								.getPaymentStatus());
			} else if (!TntConstant.ORDER_APPROVE_STATUS.isVerified(order
					.getApproveStatus())) {
				error = "订单"
						+ orderId
						+ TntConstant.ORDER_APPROVE_STATUS.getCnName(order
								.getApproveStatus());
			}
		}
		return error;
	}

	@Override
	public List<TntOrder> removeCanotPay(List<TntOrder> list) {
		if (list != null && !list.isEmpty()) {
			Iterator<TntOrder> iter = list.listIterator();
			while (iter.hasNext()) {
				TntOrder t = iter.next();
				if (t == null
						|| !TntConstant.ORDER_STATUS.isNormal(t
								.getOrderStatus())
						|| !TntConstant.ORDER_APPROVE_STATUS.isVerified(t
								.getApproveStatus())
						|| !TntConstant.PAYMENT_STATUS.isUnPayed(t
								.getPaymentStatus())) {
					iter.remove();
				}
			}
		}
		return list;
	}

	@Override
	public Long sumAmount(List<TntOrder> orders) {
		Long sum = 0l;
		if (orders != null && !orders.isEmpty()) {
			for (TntOrder order : orders) {
				sum += order.getOrderAmount();
			}
		}
		return sum;
	}

	@Override
	public boolean changePayStatus(Long tntOrderId, String status) {
		if (tntOrderId != null && status != null) {
			TntOrder t = new TntOrder();
			t.setTntOrderId(tntOrderId);
			t.setPaymentStatus(status);
			t.setPaymentTime(new Date());
			return tntOrderMapper.updateStatus(t) > 0;
		}
		return false;
	}

	@Override
	public ResultGod<TntOrder> createOrder(TntBuyInfo buyInfo) {
		ResultGod<TntOrder> result = null;
		try {
			result = orderCreateService.createOrder(buyInfo);
			if (result.isSuccess()) {
				TntOrder order = result.getResult();
				if (order != null) {
					Long userId = order.getDistributorId();
					if (userId != null && order.getDistributorName() == null) {
						TntUser user = tntUserMapper
								.selectOneWithDetail(new TntUser(userId));
						if (user != null) {
							order.setDistributorName(user.getRealName());
							Long typeId = user.getDetail().getCompanyTypeId();
							if (typeId != null) {
								TntCompanyType type = tntCompanyTypeMapper
										.getById(typeId);
								if (type != null) {
									order.setChannelId(type.getChannelId());
								}
							}
						}
					}
					insert(order);
				}
			}
		} catch (Exception e) {
			result = new ResultGod<TntOrder>();
			result.setSuccess(false);
			result.setErrorText(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public void synOrder(Long orderId) {
		try {
			TntOrder tntOrder = this.getByOrderId(orderId);
			if (tntOrder == null) {
				log.info("synOrder 同步订单失败  分销系统中订单不存在   orderId= " + orderId);
				return;
			}
			OrdOrder ordOrder = orderServiceProxy
					.queryOrdOrderByOrderId(orderId);
			if (ordOrder == null) {
				log.info("synOrder 同步订单失败 订单不存在   orderId= " + orderId);
				return;
			}

			tntOrder.setApproveStatus(ordOrder.getApproveStatus());
			if (ordOrder.getContact() != null) {
				tntOrder.setContactMoblie(ordOrder.getContact().getMobile());
				tntOrder.setContactName(ordOrder.getContact().getName());
			}
			tntOrder.setOrderStatus(ordOrder.getOrderStatus());
			tntOrder.setPaymentStatus(ordOrder.getPaymentStatus());
			tntOrder.setPaymentTime(ordOrder.getPaymentTime());
			tntOrder.setPerformStatus(ordOrder.getPerformStatus());
			tntOrder.setResourceLackReason(ordOrder.getResourceLackReason());
			tntOrder.setVisitTime(ordOrder.getVisitTime());
			if (ordOrder.getMainProduct() != null) {
				tntOrder.setQuantity(""
						+ ordOrder.getMainProduct().getQuantity());
				tntOrder.setProductName(ordOrder.getMainProduct()
						.getProductName());
			}
			this.update(tntOrder);
		} catch (Exception e) {
			log.error("同步订单出错:" + e);
		}
	}

	public void synOrderRefund(Long refundmentId) {
		OrdRefundment refund = ordRefundMentService
				.findOrdRefundmentById(refundmentId);
		if (refund != null) {
			TntOrder tntOrder = this.getByOrderId(refund.getOrderId());
			if (tntOrder != null) {
				tntOrder.setRefundStatus(TntConstant.REFUND_STATUS.WAITING
						.name());
				tntOrder.setRefundAmount(refund.getAmount());
				this.update(tntOrder);
			}
		}
	}
}
