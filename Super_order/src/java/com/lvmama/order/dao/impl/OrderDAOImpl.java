package com.lvmama.order.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.order.dao.OrderDAO;

/**
 * 订单DAO实现类.
 *
 * <pre>
 * 封装订单数据CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.BaseIbatisDao
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.order.dao.OrderDAO
 */
public final class OrderDAOImpl extends BaseIbatisDAO implements OrderDAO {
	public Long insert(final OrdOrder record) {
		Object newKey = super
				.insert("ORDER.insert", record);
		return (Long) newKey;
	}
	
	//获取要自动废单的订单列表
	public List<OrdOrder> getToAutoCancelOrder() {
		return super.queryForList("ORDER.selectToAutoCancelOrder");
	} 

	/**
	 * 获取要自动审核通过的订单列表
	 * @return 需要自动审核通的订单
	 */
	public List<OrdOrder> getToAutoApproveOrder() {
		return super.queryForList("ORDER.selectToAutoApproveOrder");
	}

	/**
	 * 获取要自动结束的订单列表
	 * @return 需要自动审核通的订单
	 */
	public int autoFinishOrder() {
		return super.update("ORDER.updateOrderFinish");
	}

	/**
	 * 取订单的ID.
	 */
	public Long getOrderId(){
		Long orderId= (Long)super.queryForObject("ORDER.queryOrdOrderId");
		return orderId;
	}
	
	/*
	 * 通过订单编号取消订单
	 */
	public int cancelOrder(Long orderId,String cancelOperator,String cancelReason) {
		OrdOrder  order=new OrdOrder();
		order.setOrderId(orderId);
		order.setCancelOperator(cancelOperator);
		order.setCancelReason(cancelReason);
		int rows = super.update("ORDER.cancelOrderByOrderId", order);
		return rows;
	}
	
	public int updateByPrimaryKey(final OrdOrder record) {
		int rows = super.update("ORDER.updateByPrimaryKey",
				record);
		return rows;
	}

	public int updateByParamMap(final Map<String, String> params) {
		int rows = super.update("ORDER.updateByParamMap",
				params);
		return rows;
	}
	public int updateByParamMap2(final Map<String, Object> params) {
		int rows = super.update("ORDER.updateByParamMap",
				params);
		return rows;
	}
	

	public OrdOrder selectByPrimaryKey(final Long orderId) {
		OrdOrder key = new OrdOrder();
		key.setOrderId(orderId);
		OrdOrder record = (OrdOrder) super.queryForObject(
				"ORDER.selectByPrimaryKey", key);
		return record;
	}

	public OrdOrder selectByPrimaryKeyForUpdate(Long orderId) {
		OrdOrder key = new OrdOrder();
		key.setOrderId(orderId);
		OrdOrder record = (OrdOrder) super.queryForObject(
				"ORDER.selectByPrimaryKeyForUpdate", key);
		return record;
	}

	public List<OrdOrder> selectForAuditOrder(final Map<String, String> params) {
		return super.queryForList(
				"ORDER.selectForAuditOrder", params, 0, 1);
	}
	
	public OrdOrder selectForAuditOrderByOrderId(final Map<String, String> params) {
		return (OrdOrder)super.queryForObject("ORDER.selectForAuditOrderByOrderId", params);
	}

	/**
	 * 订单返现.
	 *
	 * @param orderId
	 *            订单ID
	 * @param commentId
	 *            点评ID
	 * @param cash
	 *            返现金额，以分为单位，100代表1元
	 * @return <pre>
	 * <code>true</code>代表订单返现成功，<code>false</code>
	 *         代表订单返现失败
	 * </pre>
	 */

	public boolean cashOrder(final Long orderId, final Long cash) {
		super.update("ORDER.updateOrderCash", orderId); 
		return true;
	}

	public float queryOrdersAmountByParams(Map<String, Object> params) {
		Object obj = super.queryForObject("ORDER.queryOrdersAmountByParams", params);
		return obj != null ? (Float)obj : 0;
	}
	
	/**
	 * /**
	 * 订单二次跟踪处理,取特定取消的订单.
	 *  <pre>
	 * 因资源审核未通过而取消的，可立即进行二次跟踪处理.
     * 超时未支付系统自动取消的，取消半个小时后可进行二次跟踪处理.
     * 客户线上自己取消的，取消半小时后可进行二次跟踪处理.
	 * </pre>
	 * @param RowNum 数量
	 * @return
	 */
	public List<OrdOrder> queryOrderNotTrack(Map<String, String> params) {
		return super.queryForList(
				"ORDER.queryOrderNotTrack",params);
	}
	
	/**
	 * 将订单修改为已履行
	 * @param orderId 订单标识
	 * @return 更新的数据行数
	 */
	@Override
	public int updateOrderPerformed(final Long orderId) {
		int rows = super.update("ORDER.updateOrderPerformed",
				orderId);
		return rows;
	}
	/**
	 * 查询历史订单ID
	 */
	public List<Long> getHistoryOrderId(Date startDate, Date endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return super.queryForList("ORDER.getHistoryOrderId",map);
	}
	
	@Override
	public boolean updateRefundedAmount(Long orderId, Long amount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("amount", amount);
		return super.update("ORDER.updateRefundedAmount", map) > 0;
	}
	
	@Override
	public boolean updateProdRefundedAmount(Long orderId, Long refundmentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("refundmentId", refundmentId);
		return super.update("ORDER.updateProdRefundedAmount", map) > 0;
	}

	@Override
	public List<OrdOrder> selectForPaymentOrderList() {
		return super.queryForList("ORDER.selectForPaymentOrderList");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrdOrder> selectForPaymentOrderListByCondition(Map<String,Object> params) {
		return super.queryForList("ORDER.selectForPaymentOrderListByCondition",params);
	}
	
	@Override
	public Long queryOrderProfitByOrderId(Long orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		return (Long) super.queryForObject("ORDER.queryOrderProfitByOrderId", param);
	}

	@Override
	public Page<OrdOrderPerformResourceVO> queryOrderPerformByPage(Page page,Map map) {
		Long countNum = (Long) this.queryForObject("ORDER.queryOrderPerformCount",map);
		if(countNum != null && countNum>0){
			page.setTotalResultSize(countNum);
			map.put("beginIndex", page.getStartRows());
			map.put("endIndex", page.getEndRows());
			List<OrdOrderPerformResourceVO> performVO = super.queryForList("ORDER.queryOrderPerform",map);
			page.setItems(performVO);
		}
		return page;
	}

	@Override
	public List<OrdOrderPerformResourceVO> queryOrderPerformByEBK(Map<String, Object> para) {
		return super.queryForList("ORDER.getOrdersByDeviceOrCode",para);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.dao.OrderDAO#queryOrderCashRefundByOrderId(java.lang.Long)
	 */
	@Override
	public Long queryOrderCashRefundByOrderId(Long orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		return (Long) super.queryForObject("ORDER.queryOrderCashRefundByOrderId", param);
	}
	
	
	@Override
	public Long queryOrderCashRefundByOrderIdForClient(Long orderId,Long extAmount) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("extAmount", extAmount);
		return (Long) super.queryForObject("ORDER.queryOrderCashRefundByOrderIdForClient", param);
	}
	
	@Override
	public boolean updateIsCashRefundByOrdId(OrdOrder order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", order.getOrderId());
		map.put("isCashRefund", order.getIsCashRefund());
		map.put("cashRefund",order.getCashRefund());
		return super.update("ORDER.updateIsCashRefundByOrderId",map)>0;
	}
	
	@Override
	public Long queryUserFirstOrderId(String userId) {
		Long orderId= (Long)super.queryForObject("ORDER.queryUserFirstOrderId",userId);
		return orderId;
	}
	
	@Override
	public Long queryUserOrderVisitTimeGreaterCounts(String userId){
		return (Long) super.queryForObject("ORDER.queryUserOrderVisitTimeGreaterCounts",userId);
	}
	
	@SuppressWarnings("unchecked")
    @Override
    public List<OrdOrder> queryOrderByThreeMonthsAgoWeek(Date time){
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("YYYYMMDD",sdf.format(time));
        return super.queryForListForReport("ORDER.queryOrderByThreeMonthsAgoWeek",map);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdOrder> queryOrderBySeckill(Map<String, Object> paramMap) {
		return super.queryForList("ORDER.queryOrderBySeckill",paramMap);
	}
}
