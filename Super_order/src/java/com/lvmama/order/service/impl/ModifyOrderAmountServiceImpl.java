package com.lvmama.order.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrdOrderAmountApplyDAO;
import com.lvmama.order.dao.OrderAmountItemDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.service.ModifyOrderAmountService;

/**
 * 订单修改SERVICE.
 * @author liwenzhan
 * @version  20111008
 * @see com.lvmama.ord.po.OrdOrder;
 * @see com.lvmama.ord.po.OrdOrderAmountApply;
 * @see com.lvmama.ord.po.OrdOrderAmountItem;
 * @see com.lvmama.vo.Constant;
 * @see com.lvmama.order.dao.OrdOrderAmountApplyDAO;
 * @see com.lvmama.order.dao.OrderDAO;
 * 
 */
public class ModifyOrderAmountServiceImpl implements ModifyOrderAmountService {
	/**
	 * 订单修改DAO.
	 */
	private OrdOrderAmountApplyDAO amountApplyDAO;
	/**
	 * 订单DAO.
	 */
	private OrderDAO orderDAO;
	/**
	 * 订单金额明细DAO.
	 */
	private OrderAmountItemDAO orderAmountItemDAO;

	/**
	 * 保存订单修改数据.
	 */
	public void saveModifyOrderAmountApply(OrdOrderAmountApply amountApply){
		amountApplyDAO.insertSelective(amountApply);
	}

	/**
	 * 根据修改申请的ID取相应的数据.
	 */
	public OrdOrderAmountApply selectByPrimaryKey(final Long ApplyId) {
		return amountApplyDAO.selectByPrimaryKey(ApplyId);
     }
	
	/**
	 * 更新修改订单申请.
	 */
	public int updateOrderModifyAmountApply(
			OrdOrderAmountApply ordOrderAmountApply) {
		return amountApplyDAO.updateByPrimaryKeySelective(ordOrderAmountApply);
	}
	
	/**
	 * 更新修改订单申请,同时修改订单金额 ,生成金额纪录OrdOrderAmountItem.
	 */
	@Override
	public int updateOrderModifyAmountApplyOrder(
			OrdOrderAmountApply ordOrderAmountApply) {
		OrdOrder ordOrder=orderDAO.selectByPrimaryKey(ordOrderAmountApply.getOrderId()); 
		//修改订单价格后,应支付的金额.
		Long countMoney=ordOrder.getOughtPay()-ordOrder.getActualPay()+ordOrderAmountApply.getAmount();
		if(Constant.ORDER_AMOUNT_APPLAY.PASS.name().equals(ordOrderAmountApply.getApplyStatus())&&countMoney>=0){
			// 由支付变为支付完成   修改订单价格后,应支付的金额=实际支付金额并且是部分支付的。 发消息通知团人数的操作. 
			if(countMoney == 0){
				ordOrder.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
				ordOrder.setOrderViewStatus(Constant.ORDER_VIEW_STATUS.PAYED.name());
				ordOrder.setPaymentFinishTime(new java.util.Date());
			}
			ordOrder.setOughtPay(ordOrder.getOughtPay()+ordOrderAmountApply.getAmount());
			orderDAO.updateByPrimaryKey(ordOrder);
			OrdOrderAmountItem amountItem = new OrdOrderAmountItem();
			amountItem.setItemAmount(ordOrderAmountApply.getAmount());
			amountItem.setOrderId(ordOrderAmountApply.getOrderId());
			amountItem.setItemName(ordOrderAmountApply.getApplyType());
			amountItem.setOderAmountType(Constant.ORDER_AMOUNT_TYPE.ORDER_MODIFY.name());
			orderAmountItemDAO.insert(amountItem);
		}
		return amountApplyDAO.updateByPrimaryKeySelective(ordOrderAmountApply);
	}
	
	/**
	 * 根据条件进行数据查询.
	 */
	@Override
	public List<OrdOrderAmountApply> queryOrderAmountApply(Map<String, Object> parameter) {
		return amountApplyDAO.selectByOrdOrderAmountApply(parameter);
	}
	/**
	 * 根据条件进行数据记录数查询.
	 */
	@Override
	public Long queryOrderAmountApplyCount(Map<String, Object> parameter) {
		return amountApplyDAO.selectByOrdOrderAmountApplyCount(parameter);
	}
	
	/**
	 * 设置订单修改DAO.
	 * @param amountApplyDAO
	 */
	public void setAmountApplyDAO(OrdOrderAmountApplyDAO amountApplyDAO) {
		this.amountApplyDAO = amountApplyDAO;
	}
	/**
	 * 获取订单修改DAO.
	 * @param amountApply
	 * @return
	 */
	public boolean modifyAmountApply(OrdOrderAmountApply amountApply){
		return true;
	}

	/**
	 * 设置订单DAO.
	 * @param orderDAO
	 */
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	/**
	 * 获取订单DAO.
	 * @return
	 */
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}
	/**
	 * setOrderAmountItemDAO.
	 * 
	 * @param orderAmountItemDAO
	 *            订单金额明细DAO
	 */
	public void setOrderAmountItemDAO(
			final OrderAmountItemDAO orderAmountItemDAO) {
		this.orderAmountItemDAO = orderAmountItemDAO;
	}
	
}
