package com.lvmama.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.OrderPersonCount;
import com.lvmama.comm.vo.Constant;


/**
 * 查询扩展DAO接口.
 *
 * <pre>
 * 订单服务内部调用
 * </pre>
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.com.po.ComContact
 * @see com.lvmama.com.po.UserUser
 * @see com.lvmama.ord.po.OrdInvoice
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.ord.po.OrdOrderItemProd
 * @see com.lvmama.ord.po.OrdPayment
 * @see com.lvmama.ord.po.OrdPerform
 * @see com.lvmama.ord.po.OrdPerson
 * @see com.lvmama.ord.po.OrdSubSettlement
 * @see com.lvmama.ord.po.FincTransaction
 * @see com.lvmama.ord.service.po.OrderAndComment
 * @see com.lvmama.prd.po.MetaProduct
 * @see com.lvmama.prd.po.ProdProduct
 */
public interface QueryExtendDAO {
	/**
	 * 根据订单ID查询{@link OrdOrderItemMeta}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdOrderItemMeta}列表
	 */
	List<OrdOrderItemMeta> queryOrdOrderItemMetaByOrderId(Long orderId);
 
	/**
	 * 根据订单ID查询{@link OrdPerson}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdPerson}列表
	 */
	List<OrdPerson> queryOrdPersonByOrdOrderId(Long orderId);
  
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
	 * 根据发票ID查询{@link OrdInvoiceRelation}
	 * @param invoiceId
	 * @return
	 */
	List<OrdInvoiceRelation> queryInvoiceRelationByInvoiceId(Long invoiceId);
	
	List<OrdOrder> queryOrderByInvoiceSQL(String completeSQL);
	
	/**
	 * 查询ordPerson对象,取第一条
	 * @param objectId
	 * @param objectType
	 * @return
	 */
	OrdPerson queryPerson(Long objectId,Constant.OBJECT_TYPE objectType);
 
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
	OrderAndComment queryCanRefundOrderByOrderIdOnPeriod(Long orderId);
	
	
	/**
	 * 根据订单ID查询{@link OrderAndComment}.
	 * 
	 * <pre>
	 *  当前时间,该订单履行状态是否能返现,没状态时间限制.
	 * </pre>
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link OrderAndComment}，
	 * 如果指定订单ID没有对应{@link OrderAndComment}，则返回<code>null</code>
	 * </pre>
	 */
	List<OrderAndComment> selectCanRefundOrderByOrderId(final Long orderId);
	
	
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
	 * 指定采购产品订单子项ID的订单ID，
	 * 如果指定采购产品订单子项ID的订单ID不存在则返回<code>null</code>
	 * </pre>
	 */
	Long queryOrdOrderId(Long ordOrderItemMetaId);

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
	 * 根据销售产品订单子项ID查询{@link OrdOrderItemProd}.
	 *
	 * @param id
	 *            销售产品订单子项ID
	 * @return <pre>
	 * 指定销售产品订单子项ID的{@link OrdOrderItemProd}，
	 * 如果指定销售产品订单子项ID的{@link OrdOrderItemProd}不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrderItemProd queryOrdOrderItemProdById(Long id);

	/**
	 * 根据查询 {@link OrdOrder}的SQL查询 {@link OrdOrder}相关对象.
	 *
	 * @param <T>
	 *            泛型
	 * @param clazz
	 *            {@link OrdOrder}相关对象类型
	 * @param orderIdInsql
	 *            查询 {@link OrdOrder}的SQL
	 * @return {@link OrdOrder}相关对象列表
	 */
	<T> List<T> queryOrderRelate(Class<T> clazz, String orderIdInsql);
   
	/**
	 * 查询数量.
	 *
	 * @param <T>
	 *            泛型
	 * @param clazz
	 *            对象类型
	 * @param completeSQL
	 *            完整SQL语句
	 * @return 数量
	 */
	<T> Long queryCount(Class<T> clazz, String completeSQL);

	/**
	 * 查询列表.
	 *
	 * @param <T>
	 *            泛型
	 * @param clazz
	 *            对象类型
	 * @param completeSQL
	 *            完整SQL语句
	 * @return 列表
	 */
	<T> List<T> queryList(Class<T> clazz, String completeSQL);

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
	 * 根据订单ID查询 {@link OrdPerform}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdPerform}列表
	 */
	List<OrdPerform> queryOrdPerformByOrderId(Long orderId);

	/**
	 * 查询提现记录计数.
	 *
	 * @param userId
	 *            用户ID
	 * @param status
	 *            状态
	 * @param createTimeStart
	 *            起始创建时间（包括）
	 * @param createTimeEnd
	 *            结束创建时间（包括）
	 * @return 查询提现记录计数
	 */
	Long queryMoneyDrawCount(String userId, String status,
			Date createTimeStart, Date createTimeEnd);

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
	
	<T> List<T> queryList(Class<T> clazz,String sqlStatement, Map<String,Object> params);
	
	Long queryCount(String sqlStatement, Map<String,Object> params);
}
