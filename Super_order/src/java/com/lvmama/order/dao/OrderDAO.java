package com.lvmama.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.pet.vo.Page;

/**
 * 订单DAO接口.
 *
 * <pre>
 * 封装订单数据CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrder
 */
public interface OrderDAO {
	Long insert(OrdOrder record);

	int updateByPrimaryKey(OrdOrder record);

	/**
	 * 更新OrdOrder不为空的属性值.
	 * <br/>此方法已过时,替代方法:updateByParamMap2().
	 * @param params
	 * @return
	 */
	@Deprecated
	int updateByParamMap(Map<String, String> params);
	/**
	 * 更新OrdOrder不为空的属性值.
	 * <br/>此方法替代方法:updateByParamMap().
	 * @param params
	 * @return
	 */
	int updateByParamMap2(Map<String,Object> params);

	/**
	 * 取订单的ID.
	 */
	Long getOrderId();

	OrdOrder selectByPrimaryKey(Long orderId);
	OrdOrder selectByPrimaryKeyForUpdate(Long orderId);

	List<OrdOrder> selectForAuditOrder(Map<String, String> params);
	
	OrdOrder selectForAuditOrderByOrderId(Map<String, String> params);
	
	/**
	 * 获取需自动审核通过的订单
	 * @return
	 */
	List<OrdOrder> getToAutoApproveOrder();
	
	/**
	 * 获取需自动废单的列表
	 * @return
	 */
	List<OrdOrder> getToAutoCancelOrder();

	/**
	 * 自动结束订单列表
	 * @return 影响的行数
	 */
	int autoFinishOrder();

	/**
	 * 取消一个订单
	 * @param orderId
	 * @param cancelOperator
	 * @param cancelReason
	 * @return
	 */
	int cancelOrder(Long orderId,String cancelOperator,String cancelReason);
	
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
	boolean cashOrder(Long orderId, Long cash);
	
	/**
	 * 查询订单总金额.
	 *
	 * @param params
	 *            用户ID，订单状态
	 * @return 总金额
	 * 
	 */
	float queryOrdersAmountByParams(Map<String, Object> params);
	
	/**
	 * 订单二次跟踪处理,取特定取消的订单.
	 *  <pre>
	 * 因资源审核未通过而取消的，可立即进行二次跟踪处理.
     * 超时未支付系统自动取消的，取消半个小时后可进行二次跟踪处理.
     * 客户线上自己取消的，取消半小时后可进行二次跟踪处理.
	 * </pre>
	 * @return
	 */
	List<OrdOrder> queryOrderNotTrack(Map<String, String> params);
	
	/**
	 * 将订单修改为已履行
	 * @param orderId 订单标识
	 * @return 更新的数据行数
	 */
	int updateOrderPerformed(final Long orderId);
	
	/**
	 * 查询历史订单ID
	 */
	public List<Long> getHistoryOrderId(Date startDate, Date endDate);
	
	/**
	 * 更新订单记录中的成功退款金额
	 * @param orderId 订单标识
	 * @param amount 本次成功退款的金额
	 * @return 是否更新成功
	 * <p>更新ord_order表中的refunded_amount和refund_exists，refunded_amount将会被更新成已有的refunded_amount加上<code>amount</code>后的值</p>
	 */
	boolean updateRefundedAmount(Long orderId, Long amount);
	
	boolean updateProdRefundedAmount(Long orderId, Long refundmentId);
	
	List<OrdOrder> selectForPaymentOrderList();
	
	/**
	 * 根据条件查询订单
	 * @return
	 * @author zhushuying
	 */
	List<OrdOrder> selectForPaymentOrderListByCondition(Map<String,Object> params);
	
	/**
	 * 查询订单的毛利润
	 * 
	 * @param orderId
	 * @return
	 */
	Long queryOrderProfitByOrderId(Long orderId);
	
	Page<OrdOrderPerformResourceVO> queryOrderPerformByPage(Page page,Map<String, Object> para);
	
	List<OrdOrderPerformResourceVO> queryOrderPerformByEBK(Map<String, Object> para);
	
	/**
	 * 查询订单点评返现的总额
	 * 
	 * @param orderId
	 * @return
	 */
	Long queryOrderCashRefundByOrderId(Long orderId);
	
	
	
	/**
	 * 查询订单点评返现的总额
	 * 
	 * @param orderId 订单id
	* @param extAmount 每一个订单子项多返现金额，以分为单位
	 * @return
	 */
	Long queryOrderCashRefundByOrderIdForClient(Long orderId,Long extAmount);
	
	/**
	 * 更改返现状态
	 * @param order
	 * @return
	 */
	boolean updateIsCashRefundByOrdId(OrdOrder order);

	/**
	 * 查询用户第一笔订单ID
	 * @param userId 用户ID
	 * @return
	 */
	public Long queryUserFirstOrderId(String userId);
	
	/**
	 * 查询用户游玩时间晚于当前时间减去7天的订单数量
	 * @param userId 用户ID
	 * @return
	 */
	public Long queryUserOrderVisitTimeGreaterCounts(String userId);
	
	/**
     * 查询三个月的前一周的所有订单
     * @param param
     * @return
     */
    public List<OrdOrder> queryOrderByThreeMonthsAgoWeek(Date time);
    /**
     * 查询秒杀
     */
    public List<OrdOrder> queryOrderBySeckill(Map<String, Object> paramMap);
}
