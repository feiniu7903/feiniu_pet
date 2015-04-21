package com.lvmama.order.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.OrdFaxTask;
import com.lvmama.comm.bee.po.ord.OrdEplaceOrderQuantity;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.pass.PassPortDetail;
import com.lvmama.comm.bee.po.pass.PassPortSummary;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.InvoiceRelate;
import com.lvmama.comm.bee.vo.ord.OrderPersonCount;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.pet.po.pub.ComLog;

/**
 * 查询服务.
 * 
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdFaxSend
 * @see com.lvmama.ord.po.OrdFaxTask
 * @see com.lvmama.ord.po.OrdInvoice
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.po.OrdOrderItemProd
 * @see com.lvmama.ord.po.OrdPayment
 * @see com.lvmama.ord.po.OrdPerform
 * @see com.lvmama.ord.po.OrdSaleService
 * @see com.lvmama.ord.po.OrdSettlement
 * @see com.lvmama.ord.po.OrdSettlementQueue
 * @see com.lvmama.ord.po.SettlementItem
 * @see com.lvmama.ord.service.po.CompositeQuery
 * @see com.lvmama.ord.service.po.OrderAndComment
 */
public interface Query {
	/**
	 * 综合订单查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 指定查询条件的订单列表，如果指定查询条件没有对应订单，则返回元素数为0的订单列表，
	 * 使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code> <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdOrder> compositeQueryOrdOrder(CompositeQuery compositeQuery);

	/**
	 * 轻量级综合订单查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 为compositeQueryOrdOrder(final CompositeQuery compositeQuery)的轻量级实现方式，
	 * 其并不会填充详细的订单信息，只填充最基本的用户信息。
	 * </pre>
	 */
	List<OrdOrder> lightedCompositeQueryOrdOrder(CompositeQuery compositeQuery);

	/**
	 * 综合订单查询计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的订单总数
	 */
	Long compositeQueryOrdOrderCount(CompositeQuery compositeQuery);
	
	/**
	 * 综合订单查询订单的总金额信息
	 * @param compositeQuery
	 * 			综合查询
	 * @return 按条件查询总金额
	 */
	OrdOrderSum compositeQueryOrdOrderSum(CompositeQuery compositeQuery);

	/**
	 * 根据订单ID查询订单.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的订单，包含相关销售产品，相关采购产品，相关供应商等信息，如果指定ID的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrder queryOrdOrder(Long orderId);

	/**
	 * 根据通关码申请流水号查询订单ID.
	 * 
	 * @param serialNo
	 *            通关码申请流水号
	 * @return <pre>
	 * 指定通关码申请流水号的订单ID，如果指定通关码申请流水号的订单ID不存在则返回<code>null</code>
	 * </pre>
	 */
	Long queryOrdOrderIdBySerialNo(String serialNo);

	/**
	 * 根据订单ID查询{@link OrderAndComment}.
	 * 
	 * <pre>
	 * 返现使用
	 * </pre>
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link OrderAndComment}，
	 * 如果指定订单ID没有对应{@link OrderAndComment}，则返回<code>null</code>
	 * </pre>
	 */
	OrderAndComment queryOrderAndCommentByOrderId(Long orderId);

	
	/**
	 * 根据订单ID查询{@link OrderAndComment}.
	 * 
	 * <pre>
	 *  当前时间,该订单履行状态是否能返现,没状态时间限制.
	 * </pre>
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link OrderAndComment}，
	 * 如果指定订单ID没有对应{@link OrderAndComment}，则返回<code>null</code>
	 * </pre>
	 */
	List<OrderAndComment> selectCanRefundOrderByOrderId(final Long orderId);
	
	/**
	 * 查询{@link OrderAndComment}.
	 * 
	 * <pre>
	 * 返现使用(用户游玩后4月内点评互动，且订单履行时间在4个月内的)
	 * </pre>
	 * 
	 * @return <pre>
	 * 如果没有{@link OrderAndComment}， 则返回元素数为0的{@link OrderAndComment}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrderAndComment> queryOrderAndCommentOnPeriod(final Map<String, Object> map);

	
   /**
	* 根据订单ID查询订单是否待返现
	* @param orderId
	*            订单ID
	* @return {@link OrdOrder}
	*/
	OrderAndComment queryCanRefundOrderByOrderId(Long orderId);
	
	
	
	/**
	 * 获取可点评的订单产品信息
	 * @param userNo
	 * @return
	 */
	List<OrderAndComment> selectCanCommentOrderProductByUserNo(final String userNo);
	
	/**
	 * 获取可点评的订单产品信息,用于发站内信
	 * @return
	 */
	List<OrderAndComment> selectCanCommentOrderProductByDate();
	
	
	/**
	 * 根据采购产品订单子项ID获取订单ID.
	 * 
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * 指定采购产品订单子项ID的订单ID，如果指定采购产品订单子项ID的订单ID不存在则返回<code>null</code>
	 * </pre>
	 */
	Long queryOrdOrderId(Long ordOrderItemMetaId);

	/**
	 * 查询{@link OrdFaxTask}计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的{@link OrdFaxTask}总数
	 */
	Long queryOrdFaxTaskCount(CompositeQuery compositeQuery);

	/**
	 * 查询{@link OrdSaleService}.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 如果没有{@link OrdSaleService}， 则返回元素数为0的{@link OrdSaleService}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdSaleService> queryOrdSaleService(CompositeQuery compositeQuery);

	/**
	 * 查询{@link OrdSaleService}计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的{@link OrdSaleService}总数
	 */
	Long queryOrdSaleServiceCount(CompositeQuery compositeQuery);

	/**
	 * 查询 {@link OrdInvoice}.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 如果没有{@link OrdInvoice}， 则返回元素数为0的{@link OrdInvoice}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdInvoice> queryOrdInvoice(CompositeQuery compositeQuery);

	/**
	 * 查询 {@link OrdInvoice}计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的 {@link OrdInvoice}总数
	 */
	Long queryOrdInvoiceCount(CompositeQuery compositeQuery);
  
	/**
	 * 根据ID查询{@link OrdOrderItemProd}.
	 * 
	 * @param orderItemProdId
	 *            销售产品订单子项ID
	 * @return <pre>
	 * 指定ID的{@link OrdOrderItemProd}，
	 * 如果指定ID没有对应{@link OrdOrderItemProd}，则返回<code>null</code>
	 * </pre>
	 */
	OrdOrderItemProd queryOrdOrderItemProdById(Long orderItemProdId);
	
	/**
	 * 根据ID查询{@link OrdOrderItemMeta}.
	 * @param orderItemMetaId 采购产品订单子子项ID
	 * @return
	 */
	OrdOrderItemMeta queryOrdOrderItemMetaById(final Long orderItemMetaId);

	/**
	 * 根据订单号查询订单.
	 * 
	 * @param orderNo
	 *            订单号
	 * @return <pre>
	 * 指定订单号的订单，包含相关销售产品，相关采购产品，相关供应商等信息，如果指定订单号的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrder queryOrdOrderByOrderNo(String orderNo);

	/**
	 * 根据采购产品订单子项ID查询{@link OrdPerform}.
	 * 
	 * @param orderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * 指定采购产品订单子项ID的{@link OrdPerform}，
	 * 如果指定采购产品订单子项ID的{@link OrdPerform}不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdPerform queryOrdPerformByOrderItemMetaId(Long orderItemMetaId);

	/**
	 * 综合{@link OrdOrderItemMeta}查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 以{@link OrdOrder}形式包装{@link OrdOrderItemMeta}的列表，
	 * 每个{@link OrdOrder}包含一个{@link OrdOrderItemMeta},
	 * 使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdOrder> compositeQueryOrdOrderItemMeta(CompositeQuery compositeQuery);

	/**
	 * 查询 {@link OrdOrderItemMeta}计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的 {@link OrdOrderItemMeta}总数
	 */
	Long compositeQueryOrdOrderItemMetaCount(CompositeQuery compositeQuery);
   
	/**
	 * 综合{@link OrdSettlement}查询计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的{@link OrdSettlement}总数
	 */
	Long queryOrdSettlementCount(CompositeQuery compositeQuery);
	
	/**
	 * 根据订单ID查询{@link PayPayment}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link PayPayment}列表
	 */
	List<PayPayment> queryOrdPaymentByOrderId(Long orderId);
  
	/**
	 * 综合{@link OrdOrderItemMeta}查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * {@link OrdOrderItemMeta}的列表，使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdOrderItemMeta> compositeQueryOrdOrderItemMetaByMetaPerformRelate(
			CompositeQuery compositeQuery);

	/**
	 * 查询{@link OrdOrderAmountItem}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param oderAmountType
	 *            类型
	 * @return {@link OrdOrderAmountItem}
	 */
	List<OrdOrderAmountItem> queryOrdOrderAmountItem(Long orderId,
			String oderAmountType);

	/**
	 * 履行明细查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细列表
	 */
	List<PerformDetail> queryPerformDetail(CompositeQuery compositeQuery);

	/**
	 * 履行明细查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细查询计数
	 */
	Long queryPerformDetailCount(CompositeQuery compositeQuery);

	/**
	 * E景通订单查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	List<PerformDetail> queryPerformDetailForEplaceList(CompositeQuery compositeQuery);
	
	/**
	 * E景通订单查询(分页).
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	List<PerformDetail> queryPerformDetailForEplacePageList(CompositeQuery compositeQuery);

	/**
	 * E景通订单查询计数.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	Long queryPerformDetailForEplaceCount(CompositeQuery compositeQuery);
	
	/**
	 * E景通统计.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	List<String> queryPerformDetailForEplaceTongjiPageList(CompositeQuery compositeQuery);
	
	/**
	 * E景通统计计数.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	Long queryPerformDetailForEplaceTongjiCount(CompositeQuery compositeQuery);
	
	/**
	 * E景通统计门票数量和游玩人总数.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	List<OrdEplaceOrderQuantity> queryEbkOrderForEplaceTotalQuantity(CompositeQuery compositeQuery,boolean isTotal);
	
	/**
	 * 通关汇总查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关汇总列表
	 */
	List<PassPortSummary> queryPassPortSummary(CompositeQuery compositeQuery);

	/**
	 * 通关汇总查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关汇总查询计数
	 */
	Long queryPassPortSummaryCount(CompositeQuery compositeQuery);

	/**
	 * 通关明细查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关明细列表
	 */
	List<PassPortDetail> queryPassPortDetail(CompositeQuery compositeQuery);

	/**
	 * 通关明细查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关明细查询计数
	 */
	Long queryPassPortDetailCount(CompositeQuery compositeQuery);

	/**
	 * 根据订单ID查询 {@link OrdPerform}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdPerform}列表
	 */
	List<OrdPerform> queryOrdPerformByOrderId(Long orderId);

	/**
	 * 根据订单ID查询 {@link FincTransaction}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link FincTransaction}
	 */
	PayTransaction queryFincTransactionByOrderId(Long orderId);

	/**
	 * 查询提现记录计数.
	 * 
	 * @param compositeQuery
	 *            综合查询条件
	 * @return 查询提现记录计数
	 */
	Long queryMoneyDrawCount(CompositeQuery compositeQuery);

	/**
	 * queryComLog.
	 * 
	 * @param objectType
	 *            对象类型
	 * @param logType
	 *            日志类型
	 * @param objectId
	 *            对象ID
	 * @return List
	 */
	List<ComLog> queryComLog(String objectType, String logType, Long objectId);
	
	/**
	 * 统计订单人数.
	 * 
	 * @param productid
	 *            销售产品ID
	 * @param ordOrderVisitTimeStart
	 *            订单起始游玩时间（包括）
	 * @param ordOrderVisitTimeEnd
	 *            订单结束游玩时间（不包括）
	 * @param paymentStatus
	 *            订单支付状态
	 * @param orderStatus
	 *            订单状态
	 * @return {@link OrderPersonCount}
	 */
	OrderPersonCount queryOrderPersonCount(Long productid,
			Date ordOrderVisitTimeStart, Date ordOrderVisitTimeEnd,
			String paymentStatus, String orderStatus);
	

	/**
	 * 根据结算单号查询操作日志列表.
	 * 
	 * @param Long
	 *            settlementId 
	 * @return SettlementItem
	 */
	List<ComLog> fillComLogList(Long  settlementId);
	/**
	 * 根据查询条件计算查询到的SettlementItem列表的实际结算价总和.
	 * @param compositeQuery 查询条件.
	 * @return 实际结算价总和.
	 */
	float sumActualSettlementPriceYuan(CompositeQuery compositeQuery);
	/**
	 * 填充发票当中相关的内容;
	 * @param list 
	 * @param invoiceRelate 操作的条件
	 * @return
	 */
	public List<OrdInvoice> fillOrdInvoice(final List<OrdInvoice> list,InvoiceRelate invoiceRelate);
	/**
	 * 根据订单ID查询{@link OrdOrderItemMeta}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdOrderItemMeta}列表
	 */
	List<OrdOrderItemMeta> queryOrdOrderItemMetaByOrderId(Long orderId);
	
	/**
	 * 根据订单号查询订单子子项的集合
	 * @param orderId
	 * @return
	 */
	List<OrdOrderItemMeta> queryOrdOrderItemMetaList(Long orderId);
	/**
	 * 根据订单号查询订单子子项的集合
	 * @param orderId
	 * @return
	 */
	OrdOrder queryOrdOrderItemMetaListRefund(Long refundmentId, Long orderId);
	
	/**
	 * 根据订单号查询订单子子项的集合
	 * @param orderId
	 * @return
	 */
	List<OrdOrderItemMeta> queryOrderItemMetaList(Long orderId);

	/**
	 * 检验该用户是否已为游客下了相同的订单(酒店套房、目的地自由行)
	 * @author shihui
	 * */
	Long queryOrderByParams1(Map<String, Object> params);
	
	Long queryOrderLimitByUserId(Map<String, Object> params);
	Long queryOrderLimitByMobile(Map<String, Object> params);
	Long queryOrderLimitByCertNo(Map<String, Object> params);
	
	List<Long> queryOrderLimitByParam(Map<String,Object> params);
	/**
	 * 检验该用户是否已为游客下了相同的订单(酒店单房型)
	 * @author shihui
	 * */
	List<Map<String, Object>> queryOrderByParams2(Map<String, Object> params);
	
	/**
	 * 查询此订单的毛利润
	 * 
	 * @param orderId
	 * @return
	 */
	Long queryOrderProfitByOrderId(Long orderId);
	/**
	 * 订单统计
	 * @param params
	 * @return
	 */
	Long queryOrdOrderCount(Map<String, Object> params);
}
