package com.lvmama.order.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.OrderPersonCount;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.OBJECT_TYPE;
import com.lvmama.order.dao.QueryDAO;
import com.lvmama.order.dao.SelectInSQL;

/**
 * 查询DAO实现类.
 * 
 * <pre></pre>
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.UtilityTool#isValid(Object)
 * @see com.lvmama.BaseIbatisDao
 * @see com.lvmama.com.po.ComContact
 * @see com.lvmama.com.po.UserUser
 * @see com.lvmama.finc.po.FincTransaction
 * @see com.lvmama.ord.po.OrdInvoice
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.ord.po.OrdOrderItemProd
 * @see com.lvmama.ord.po.OrdPayment
 * @see com.lvmama.ord.po.OrdPerform
 * @see com.lvmama.ord.po.OrdPerson
 * @see com.lvmama.ord.po.OrdSubSettlement
 * @see com.lvmama.ord.service.po.OrderAndComment
 * @see com.lvmama.prd.po.MetaProduct
 * @see com.lvmama.prd.po.ProdProduct
 * @see com.lvmama.order.dao.QueryDAO
 * @see com.lvmama.order.dao.SelectInSQL
 */
public final class QueryDAOImpl extends BaseIbatisDAO implements QueryDAO,
		SelectInSQL {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(QueryDAOImpl.class);

	/**
	 * 根据订单ID查询{@link OrdOrder}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link OrdOrder}，如果指定ID的{@link OrdOrder}不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrder queryOrdOrder(final Long orderId) {
		return (OrdOrder) super.queryForObject(
				"ORDER.queryOrdOrder", orderId);
	}

	/**
	 * 根据订单ID查询{@link OrdPerson}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdPerson}列表
	 */
	@Override
	public List<OrdPerson> queryOrdPersonByOrdOrderId(final Long orderId) {
		final Map<String, String> map = new Hashtable<String, String>();
		map.put("objectType", "ORD_ORDER");
		map.put("orderId", orderId.toString());
		return super.queryForList(
				"ORDER_PERSON.queryOrdPersonByOrdOrderId", map);
	}

	/**
	 * 根据通关码申请流水号查询订单ID.
	 * 
	 * @param serialNo
	 *            通关码申请流水号
	 * @return <pre>
	 * 指定通关码申请流水号的订单ID，如果指定通关码申请流水号的订单ID不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public Long queryOrdOrderIdBySerialNo(final String serialNo) {
		return (Long) super.queryForObject(
				"ORDER.queryOrdOrderIdBySerialNo", serialNo);
	}

	/**
	 * 根据订单ID查询{@link OrdOrderItemMeta}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdOrderItemMeta}列表
	 */
	@Override
	public List<OrdOrderItemMeta> queryOrdOrderItemMetaByOrderId(
			final Long orderId) {
		return super.queryForList(
				"ORDER_ITEM_META.queryOrdOrderItemMetaByOrderId", orderId);
	}
 
   /**
	* 根据订单ID查询订单是否待返现(履行和游玩时间都在3月内)PERFORM_TIME
	* @param orderId
	*            订单ID
	* @return {@link OrdOrder}
	*/
	public OrderAndComment queryCanRefundOrderByOrderIdOnPeriod(final Long orderId)
	{
		OrderAndComment order = null;
		final Map<String, String> map = new Hashtable<String, String>();
		map.put("orderId", orderId.toString());
		map.put("isCashRefund", "false");
		final List<OrderAndComment> list = super.queryForList("ORDER.queryCanRefundOrderList", map);
        if (!list.isEmpty()) {
        	 order = list.get(0);
        }
        else
        {
        	order = null;
        }
        return order;
	}
	
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
	@Override
	public List<OrderAndComment> selectCanRefundOrderByOrderId(
			final Long orderId) {
		List<OrderAndComment> result = new ArrayList<OrderAndComment>();
		final Map<String, String> map = new Hashtable<String, String>();
		map.put("isCashRefund", "false");
		map.put("orderId", orderId.toString());
		result = super.queryForList(
				"ORDER.selectCanRefundOrderByOrderId", map);

		return result;
	}

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
	@Override
	public List<OrderAndComment> queryOrderAndCommentOnPeriod(final Map<String, Object> map) {
		List<OrderAndComment> result = new ArrayList<OrderAndComment>();	
		result = super.queryForList(
				"ORDER.queryOrderAndCommentOnPeriod", map);
		return result;
	}
	
	
	
	/**
	 * 获取可点评的订单产品信息
	 * @param userNo
	 * @return
	 */
	public List<OrderAndComment> selectCanCommentOrderProductByUserNo(final String userNo){
		 List<OrderAndComment>  result = super.queryForList("ORDER.selectCanCommentOrderProductByUserNo", userNo);
		 return result;
	}
	
	/**
	 * 获取可点评的订单产品信息,用于发站内信
	 * @return
	 */
	public List<OrderAndComment> selectCanCommentOrderProductByDate(){
		 @SuppressWarnings("unchecked")
		List<OrderAndComment>  result = super.queryForList("ORDER.selectCanCommentOrderProductByDate");
		 return result;
	}
	

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
	@Override
	public Long queryOrdOrderId(final Long ordOrderItemMetaId) {
		return (Long) super.queryForObject(
				"ORDER_ITEM_META.queryOrdOrderId", ordOrderItemMetaId);
	}

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
	@Override
	public OrdPerform queryOrdPerformByOrderItemMetaId(
			final Long orderItemMetaId) {
		final Map<String, String> map = new Hashtable<String, String>();
		map.put("objectId", orderItemMetaId.toString());
		map.put("objectType", "ORD_ORDER_ITEM_META");
		return (OrdPerform) super.queryForObject(
				"ORDER_PERFORM.selectByObjectIdAndObjectType", map);
	}

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
	@Override
	public OrdOrderItemProd queryOrdOrderItemProdById(final Long id) {
		return (OrdOrderItemProd) super.queryForObject(
				"ORDER_ITEM_PROD.selectByPrimaryKey", id);
	}

	/**
	 * 根据订单号查询{@link OrdOrder}.
	 * 
	 * @param orderNo
	 *            订单号
	 * @return <pre>
	 * 指定订单号的{@link OrdOrder}，如果指定订单号的{@link OrdOrder}不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrder queryOrdOrderByOrderNo(final String orderNo) {
		return (OrdOrder) super.queryForObject(
				"ORDER.queryOrdOrderByOrderNo", orderNo);
	}

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
	@Override
	public <T> List<T> queryOrderRelate(final Class<T> clazz,
			final String orderIdInsql) {
		final String completeSQL = getCompleteSQL(clazz, orderIdInsql);
		List<T> list;
		if (UtilityTool.isValid(completeSQL)) {
			list = queryList(clazz, completeSQL);
		} else {
			list = new ArrayList<T>();
		}
		return list;
	}

	/**
	 * getCompleteSQL.
	 * 
	 * @param <T>
	 *            泛型
	 * @param clazz
	 *            {@link OrdOrder}相关对象类型
	 * @param orderIdInsql
	 *            查询 {@link OrdOrder}的SQL
	 * @return CompleteSQL
	 */
	private <T> String getCompleteSQL(final Class<T> clazz,
			final String orderIdInsql) {
		String completeSQL = null;
		if (clazz.getSimpleName().equals("OrdOrderItemProd")) {
			completeSQL = new StringBuilder().append(ORD_ORDER_ITEM_PROD)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdOrderItemMeta")) {
			completeSQL = new StringBuilder().append(ORD_ORDER_ITEM_META)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdPerson")) {
			completeSQL = new StringBuilder().append(ORD_PERSON)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("UsrUsers")) {
			completeSQL = new StringBuilder().append(USR_USERS)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdInvoice")) {
			completeSQL = new StringBuilder().append(ORD_INVOICE)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdOrder")) {
			completeSQL = new StringBuilder().append(ORD_ORDER)
					.append(orderIdInsql).append(END).toString();
		}  else if (clazz.getSimpleName().equals("OrdOrderItemMetaTime")) {
			completeSQL = new StringBuilder().append(ORD_ORDER_ITEM_META_TIME)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdOrderItemProdTime")) {
			completeSQL = new StringBuilder().append(ORD_ORDER_ITEM_PROD_TIME)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdOrderRoute")) {
			completeSQL = new StringBuilder().append(ORD_ORDER_ROUTE)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdOrderTrack")) {
			completeSQL = new StringBuilder().append(ORD_ORDER_TRACK)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdInvoiceRelation")) {
			completeSQL = new StringBuffer().append(ORD_INVOICE_RELATION)
					.append(orderIdInsql).append(END).toString();
		} else if (clazz.getSimpleName().equals("OrdOrderPre")) {
			completeSQL = new StringBuffer().append(ORD_ORDER_PRE)
			.append(orderIdInsql).append(END).toString();
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(completeSQL);
		}
		return completeSQL;
	}
  
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
	@Override
	public <T> Long queryCount(final Class<T> clazz, final String completeSQL) {
		final String sqlMap = getSqlMap1(clazz);
		Long count;
		if (UtilityTool.isValid(sqlMap)) {
			count = (Long) super.queryForObject(sqlMap,
					completeSQL);
		} else {
			count = Long.parseLong("0");
		}
		return count;
	}

	/**
	 * getSqlMap1.
	 * 
	 * @param <T>
	 *            泛型
	 * @param clazz
	 *            {@link OrdOrder}相关对象类型
	 * @return
	 */
	private <T> String getSqlMap1(final Class<T> clazz) {
		String sqlMap = null;
		if (clazz.getSimpleName().equals("FinishSettlementItem")) {
			sqlMap = "ORDER_ITEM_META.queryFinishSettlementItemCount";
		} else if (clazz.getSimpleName().equals("OrdOrderItemMeta")) {
			sqlMap = "ORDER_ITEM_META.queryOrdOrderItemMetaCount";
		} else if (clazz.getSimpleName().equals("OrdSettlement")) {
			sqlMap = "ORD_SETTLEMENT.queryOrdSettlementCount";
		} else if (clazz.getSimpleName().equals("SettlementItem")) {
			sqlMap = "ORDER_ITEM_META.querySettlementItemCount";
		} else if (clazz.getSimpleName().equals("OrdInvoice")) {
			sqlMap = "ORDER_INVOICE.queryOrdInvoiceCount";
		} else if (clazz.getSimpleName().equals("OrdSettlementQueue")) {
			sqlMap = "ORD_SETTLEMENT_QUEUE.queryOrdSettlementQueueCount";
		} else if (clazz.getSimpleName().equals("OrdSaleService")) {
			sqlMap = "ORD_SALE_SERVICE.queryOrdSaleServiceCount";
		} else if (clazz.getSimpleName().equals("OrdOrder")) {
			sqlMap = "ORDER.queryOrdOrderListCount";
		} else if (clazz.getSimpleName().equals("UsrUsers")) {
			sqlMap = "ORDER.queryUsrUsers";
		} else if (clazz.getSimpleName().equals("FincTransaction")) {
			sqlMap = "FINC_TRANSACTION.queryFincTransaction";
		} else if (clazz.getSimpleName().equals("PerformDetail")) {
			sqlMap = "ORDER.queryPerformDetailCount";
		} else if (clazz.getSimpleName().equals("PassPortDetail")) {
			sqlMap = "ORDER.queryPassPortDetailCount";
		} else if (clazz.getSimpleName().equals("PassPortSummary")) {
			sqlMap = "ORDER.queryPassPortSummaryCount";
		}
		return sqlMap;
	}

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
	@Override
	public <T> List<T> queryList(final Class<T> clazz, final String completeSQL) {
		final String sqlMap = getSqlMap2(clazz);
		List<T> list;
		if (UtilityTool.isValid(sqlMap)) {
			list = super.queryForListForReport(sqlMap, completeSQL);
		} else {
			list = new ArrayList<T>();
		}
		return list;
	}

	/**
	 * getSqlMap2.
	 * 
	 * @param <T>
	 *            泛型
	 * @param clazz
	 *            {@link OrdOrder}相关对象类型
	 * @return
	 */
	private <T> String getSqlMap2(final Class<T> clazz) {
		String sqlMap = null;
		if (clazz.getSimpleName().equals("OrdSettlement")) {
			sqlMap = "ORD_SETTLEMENT.queryOrdSettlementList";
		} else if (clazz.getSimpleName().equals("OrdOrderItemMeta")) {
			sqlMap = "ORDER_ITEM_META.queryOrdOrderItemMetaList1";
		} else if (clazz.getSimpleName().equals("OrdInvoice")) {
			sqlMap = "ORDER_INVOICE.queryOrdInvoiceList";
		} else if (clazz.getSimpleName().equals("OrdSettlementQueue")) {
			sqlMap = "ORD_SETTLEMENT_QUEUE.queryOrdSettlementQueueList";
		} else if (clazz.getSimpleName().equals("OrdSaleService")) {
			sqlMap = "ORD_SALE_SERVICE.queryOrdSaleServiceList";
		} else if (clazz.getSimpleName().equals("OrdOrder")) {
			sqlMap = "ORDER.queryOrdOrderList";
		} else if (clazz.getSimpleName().equals("OrdOrderItemProd")) {
			sqlMap = "ORDER_ITEM_PROD.queryOrdOrderItemProd";
		} else if (clazz.getSimpleName().equals("OrdPerson")) {
			sqlMap = "ORDER_PERSON.queryOrdPerson";
		} else if (clazz.getSimpleName().equals("UsrUsers")) {
			sqlMap = "ORDER.queryUsrUsers";
		} else if (clazz.getSimpleName().equals("FincTransaction")) {
			sqlMap = "FINC_TRANSACTION.queryFincTransaction";
		} else if (clazz.getSimpleName().equals("PerformDetail")) {
			sqlMap = "ORDER.queryPerformDetail";
		} else if (clazz.getSimpleName().equals("PassPortDetail")) {
			sqlMap = "ORDER.queryPassPortDetail";
		} else if (clazz.getSimpleName().equals("PassPortSummary")) {
			sqlMap = "ORDER.queryPassPortSummary";
		} else if (clazz.getSimpleName().equals("OrdOrderItemProdTime")) {
			sqlMap = "ORDER.queryOrdOrderItemProdTime";
		} else if (clazz.getSimpleName().equals("OrdOrderItemMetaTime")) {
			sqlMap = "ORDER.queryOrdOrderItemMetaTime";
		} else if (clazz.getSimpleName().equals("OrdOrderRoute")) {
			sqlMap = "ORDER.queryOrdOrderRoute";
		} else if (clazz.getSimpleName().equals("OrdOrderTrack")) {
			sqlMap = "ORDER.queryOrdOrderTrack";
		} else if (clazz.getSimpleName().equals("OrdInvoiceRelation")) {
			sqlMap = "ORDER_INVOICE_RELATION.queryRelations";
		} else if (clazz.getSimpleName().equals("OrdOrderPre")) {
			sqlMap = "ORD_ORDER_PRE.queryOrdOrderPre";
		}
		return sqlMap;
	}

	/**
	 * 查询{@link OrdOrderAmountItem}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param oderAmountType
	 *            类型
	 * @return {@link OrdOrderAmountItem}
	 */
	@Override
	public List<OrdOrderAmountItem> queryOrdOrderAmountItem(final Long orderId,
			final String oderAmountType) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		map.put("orderId", orderId);
		if (oderAmountType != null && !oderAmountType.equalsIgnoreCase("ALL")) {
			map.put("oderAmountType", oderAmountType);
		}
		return super.queryForList(
				"ORDER_AMOUNT_ITEM.queryOrdOrderAmountItem", map);
	}

	/**
	 * 根据订单ID查询 {@link OrdPerform}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdPerform}列表
	 */
	@Override
	public List<OrdPerform> queryOrdPerformByOrderId(final Long orderId) {
		final Map<String, String> map = new Hashtable<String, String>();
		map.put("objectId", orderId.toString());
		map.put("objectType", "ORD_ORDER");
		return super.queryForList(
				"ORDER_PERFORM.selectByObjectIdAndObjectType", map);
	}

	/**
	 * 查询提现记录计数.
	 * 
	 * @param userId
	 *            用户ID
	 * @return 查询提现记录计数
	 */
	@Override
	public Long queryMoneyDrawCount(final String userId, final String status,
			final Date createTimeStart, final Date createTimeEnd) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		if (UtilityTool.isValid(userId)) {
			map.put("userId", userId);
		}
		if (UtilityTool.isValid(status)) {
			map.put("status", status);
		}
		if (UtilityTool.isValid(createTimeStart)) {
			map.put("createTimeStart", createTimeStart);
		}
		if (UtilityTool.isValid(createTimeEnd)) {
			map.put("createTimeEnd", createTimeEnd);
		}
		return (Long) super.queryForObject(
				"FINC_MONEY_DRAW.queryMoneyDrawCount", map);
	}

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
	@Override
	public OrderPersonCount queryOrderPersonCount(final Long productid,
			final Date ordOrderVisitTimeStart, final Date ordOrderVisitTimeEnd,
			final String paymentStatus, final String orderStatus) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		if (UtilityTool.isValid(productid)) {
			map.put("productid", productid);
		}
		if (UtilityTool.isValid(ordOrderVisitTimeStart)) {
			map.put("ordOrderVisitTimeStart",
					UtilityTool.formatDate(ordOrderVisitTimeStart));
		}
		if (UtilityTool.isValid(ordOrderVisitTimeEnd)) {
			map.put("ordOrderVisitTimeEnd", UtilityTool.formatDate(ordOrderVisitTimeEnd));
		}
		if (UtilityTool.isValid(paymentStatus)) {
			map.put("paymentStatus", paymentStatus.toString());
		}
		if (UtilityTool.isValid(orderStatus)) {
			map.put("orderStatus", orderStatus.toString());
		}
		return (OrderPersonCount) super.queryForObject(
				"ORDER.queryOrderPersonCount", map);
	}

	@Override
	public OrdOrderSum queryOrdOrderPaySum(String completeSQL) {
		return (OrdOrderSum) super.queryForObject(
				"ORDER.queryOrdOrderPaySum", completeSQL);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdInvoiceRelation> queryInvoiceRelationByInvoiceId(
			Long invoiceId) {
		return super.queryForList(
				"ORDER_INVOICE_RELATION.selectByInvoiceId", invoiceId);	
	}
	
	public List<OrdOrder> queryOrderByInvoiceSQL(String completeSQL){
		return super.queryForList("ORDER.selectByInvoiceSQL",completeSQL);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrdPerson queryPerson(Long objectId, OBJECT_TYPE objectType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectType", objectType.name());
		map.put("objectId", objectId);
		map.put("personType", Constant.ORD_PERSON_TYPE.ADDRESS.name());
		List<OrdPerson> list = super.queryForList(
				"ORDER_PERSON.queryOrdPersonByParams", map);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}

	}

	@Override
	public OrdOrderItemMeta queryOrdOrderItemMetaById(Long orderItemMetaId) {
		OrdOrderItemMeta key = new OrdOrderItemMeta();
		key.setOrderItemMetaId(orderItemMetaId);
		OrdOrderItemMeta orderItemMeta = (OrdOrderItemMeta) super
				.queryForObject("ORDER_ITEM_META.selectByPrimaryKey", key);
		return orderItemMeta;
	}
	 
	/**
	 * 查询订单子子项列表.
	 * @return 列表
	 */
	@Override
	public List<OrdOrderItemMeta> queryOrderItemMetaListRefund(Long refundmentId, Long orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("refundmentId", refundmentId);
		param.put("orderId", orderId);
		List<OrdOrderItemMeta> list = super.queryForList("ORDER_ITEM_META.queryRefundmentOrdOrderItemMetaList", param);
		return list;
	}
	
	/**
	 * 查询订单子子项列表.
	 * @return 列表
	 */
	@Override
	public List<OrdOrderItemMeta> queryOrdOrderItemMetaList(String orderIdInsql) {
		String completeSQL = new StringBuilder().append(ORD_ORDER_ITEM_META3)
				.append(orderIdInsql).append(END).append(PRODUCT_STATUS).toString();
		List<OrdOrderItemMeta> list = super.queryForList("ORDER_ITEM_META.queryOrdOrderItemMetaAndStatusList", completeSQL);
		return list;
	}

	@Override
	public <T> List<T> queryList(Class<T> clazz, String sqlStatement,
			Map<String, Object> params) {
		List<T> list = super.queryForList(sqlStatement, params);
		return list;
	}

	@Override
	public Long queryCount(String sqlStatement,	Map<String, Object> params) {
		return (Long) super.queryForObject(sqlStatement,params);
	}

	@Override
	public Long queryOrderByParams1(Map<String, Object> params) {
		Long count= (Long) super.queryForObject("ORDER.queryOrderByParams1", params);
		return count;
	}

	@Override
	public List<Map<String, Object>> queryOrderByParams2(Map<String, Object> params) {
		return super.queryForList("ORDER.queryOrderByParams2", params);
	}

	@Override
	public Long queryOrderProfitByOrderId(Long orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("canPayByBonus", "Y");
		return (Long) super.queryForObject("ORDER.queryOrderProfitByOrderId", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerformDetail> queryPerformDetailForEplaceList(
			Map<String, Object> params) {
		return super.queryForListForReport("ORDER.queryPerformDetailForEPlaceListSQL", params) ;
	
	}
	
	@Override
	public Long queryOrderLimitByUserId(Map<String, Object> params) {
		return (Long) super.queryForObject("ORDER.queryOrderLimitByUserId",params);
	}

	@Override
	public Long queryOrderLimitByMobile(Map<String, Object> params) {
		return (Long) super.queryForObject("ORDER.queryOrderLimitByMobile",params);
	}
	
	@Override
	public Long queryOrderLimitByCertNo(Map<String, Object> params) {
		return (Long) super.queryForObject("ORDER.queryOrderLimitByCertNo",params);
	}

	@Override
	public List<Long> queryOrderLimitByParam(Map<String, Object> params) {
		return super.queryForList("ORDER.checkTrainOrderLimit",params);
	}

	@Override
	public List<OrdOrderTraffic> queryOrderTrafficList(Long orderId) {
		return super.queryForList("ORD_ORDER_TRAFFIC.queryOrderTrafficListByOrderId",orderId);
	}

	@Override
	public List<OrdOrderTrafficTicketInfo> queryOrderTrafficTicketInfo(
			Long orderId) {
		return super.queryForList("ORD_ORDER_TRAFFIC_TICKET_INFO.queryOrderTrafficTicketInfoByOrderId",orderId);
	}

	@Override
	public Long queryOrdOrderCount(Map<String, Object> params){
		return (Long) super.queryForObject("ORDER.queryOrdOrderCount",params);
	}
}
