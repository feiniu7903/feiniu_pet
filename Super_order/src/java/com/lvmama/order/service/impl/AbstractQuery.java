package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdEplaceOrderQuantity;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProdTime;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.po.ord.OrdOrderTrack;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.pass.PassPortDetail;
import com.lvmama.comm.bee.po.pass.PassPortSummary;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.InvoiceRelate;
import com.lvmama.comm.bee.vo.ord.OrderPersonCount;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.bee.vo.ord.TrackLog;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ord.dao.TrackLogDAO;
import com.lvmama.order.dao.QueryDAO;
import com.lvmama.order.service.Query;
import com.lvmama.order.service.builder.Director;
import com.lvmama.order.service.builder.SQLBuilder;

/**
 * 查询服务抽象类.
 *   
 * <pre></pre>
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.UtilityTool#isValid(Object)
 * @see com.lvmama.com.po.UserUser
 * @see com.lvmama.ord.po.FinishSettlementItem
 * @see com.lvmama.ord.po.OrdFaxSend
 * @see com.lvmama.ord.po.OrdFaxTask
 * @see com.lvmama.ord.po.OrdInvoice
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.ord.po.OrdOrderItemProd
 * @see com.lvmama.ord.po.OrdPayment
 * @see com.lvmama.ord.po.OrdPerform
 * @see com.lvmama.ord.po.OrdPerson
 * @see com.lvmama.ord.po.OrdSaleService
 * @see com.lvmama.ord.po.OrdSettlement
 * @see com.lvmama.ord.po.OrdSettlementQueue
 * @see com.lvmama.ord.po.OrdSubSettlement
 * @see com.lvmama.ord.po.FincTransaction
 * @see com.lvmama.ord.po.SettlementItem
 * @see com.lvmama.ord.service.po.CompositeQuery
 * @see com.lvmama.ord.service.po.OrderAndComment
 * @see com.lvmama.vo.Constant.ORD_PERSON_TYPE
 * @see com.lvmama.order.dao.QueryDAO
 * @see com.lvmama.order.service.Query
 * @see com.lvmama.order.service.builder.Director
 * @see com.lvmama.order.service.builder.SQLBuilder
 */
public abstract class AbstractQuery extends AbstractBuilderFactory implements Query {
	
	private PayPaymentService payPaymentService; 
	/**
	 * queryDAO.
	 */
	private transient QueryDAO queryDAO;
	/**
	 * 
	 */
	private TrackLogDAO trackLogDAO;
	/**
	 * comLogDAO.
	 */
	private ComLogDAO comLogDAO;

	/**
	 * getComLogDAO.
	 * 
	 * @return
	 */
	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	/**
	 * setComLogDAO.
	 * 
	 * @param comLogDAO
	 */
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	/**
	 * setQueryDAO.
	 * 
	 * @param queryDAO
	 *            {@link QueryDAO}
	 */
	public final void setQueryDAO(final QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

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
	@Override
	public List<OrdOrder> compositeQueryOrdOrder(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getSQLBuilder();
		director.setOrdOrderBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		final String sql = sqlBuilder.buildCompleteSQLStatement();
		List<OrdOrder> list = queryDAO.queryList(OrdOrder.class, sql);
		if (!list.isEmpty()) {
			list = fill2OrdOrder(list, sql);
		}
		return list;
	}
	
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
	@Override
	public List<OrdOrder> lightedCompositeQueryOrdOrder(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getSQLBuilder();
		director.setOrdOrderBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		final String sql = sqlBuilder.buildCompleteSQLStatement();
		List<OrdOrder> list = queryDAO.queryList(OrdOrder.class, sql);
		return list;
	}	
	
	

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.Query#compositeQueryOrdOrderSum(com.lvmama.ord.service.po.CompositeQuery)
	 */
	@Override
	public OrdOrderSum compositeQueryOrdOrderSum(final CompositeQuery compositeQuery) {
		final Director director=getDirector();
		final SQLBuilder sqlBuilder = getSQLBuilderSum();
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		final String sql=sqlBuilder.buildCompleteSQLStatement();
		return queryDAO.queryOrdOrderPaySum(sql);
	}

	/**
	 * getOrdOrderItemProdMap.
	 * 
	 * @param ordOrderItemProdList
	 *            销售产品订单子项列表
	 * @return Map<订单ID, 销售产品订单子项列表>
	 */
	private Map<Long, List<OrdOrderItemProd>> getOrdOrderItemProdMap(
			final List<OrdOrderItemProd> ordOrderItemProdList) {
		final Map<Long, List<OrdOrderItemProd>> map = new Hashtable<Long, List<OrdOrderItemProd>>();
		for (OrdOrderItemProd item : ordOrderItemProdList) {
			final List<OrdOrderItemProd> list;
			if (map.containsKey(item.getOrderId())) {
				list = map.get(item.getOrderId());
			} else {
				list = new ArrayList<OrdOrderItemProd>();
			}
			list.add(item);
			map.put(item.getOrderId(), list);
		}
		return map;
	}

	/**
	 * getOrdOrderItemMetaMap.
	 * 
	 * @param ordOrderItemMetaList
	 *            采购产品订单子项列表
	 * @return Map<订单ID, 采购产品订单子项列表>
	 */
	private Map<Long, List<OrdOrderItemMeta>> getOrdOrderItemMetaMap(
			final List<OrdOrderItemMeta> ordOrderItemMetaList) {
		final Map<Long, List<OrdOrderItemMeta>> map = new Hashtable<Long, List<OrdOrderItemMeta>>();
		for (OrdOrderItemMeta item : ordOrderItemMetaList) {
			final List<OrdOrderItemMeta> list;
			if (map.containsKey(item.getOrderId())) {
				list = map.get(item.getOrderId());
			} else {
				list = new ArrayList<OrdOrderItemMeta>();
			}
			list.add(item);
			map.put(item.getOrderId(), list);
		}
		return map;
	}

	/**
	 * fill2OrdOrderItemProd.
	 * 
	 * @param ordOrderItemProdList
	 *            销售产品订单子项列表
	 * @param ordOrderItemMetaList
	 *            采购产品订单子项列表
	 * @return 销售产品订单子项列表
	 */
	private List<OrdOrderItemProd> fill2OrdOrderItemProd(
			final List<OrdOrderItemProd> ordOrderItemProdList,
			final List<OrdOrderItemMeta> ordOrderItemMetaList) {
		final Map<Long, List<OrdOrderItemMeta>> map = new Hashtable<Long, List<OrdOrderItemMeta>>();
		for (OrdOrderItemMeta item : ordOrderItemMetaList) {
			final List<OrdOrderItemMeta> list;
			if (map.containsKey(item.getOrderItemId())) {
				list = map.get(item.getOrderItemId());
			} else {
				list = new ArrayList<OrdOrderItemMeta>();
			}
			list.add(item);
			map.put(item.getOrderItemId(), list);
		}
		for (OrdOrderItemProd item : ordOrderItemProdList) {
			if (map.containsKey(item.getOrderItemProdId())) {
				item.setOrdOrderItemMetas(map.get(item.getOrderItemProdId()));
			}
		}
		return ordOrderItemProdList;
	}

	/**
	 * getOrdPersonMap.
	 * 
	 * @param ordPersonList
	 *            人员列表
	 * @return Map<订单ID, 人员列表>
	 */
	private Map<Long, List<OrdPerson>> getOrdPersonMap(
			final List<OrdPerson> ordPersonList) {
		final Map<Long, List<OrdPerson>> map = new Hashtable<Long, List<OrdPerson>>();
		for (OrdPerson item : ordPersonList) {
			final List<OrdPerson> list;
			if (map.containsKey(item.getObjectId())) {
				list = map.get(item.getObjectId());
			} else {
				list = new ArrayList<OrdPerson>();
			}
			list.add(item);
			map.put(item.getObjectId(), list);
		}
		return map;
	}

	/**
	 * 整体是Order,Invoice,Relation三个表之间的关联，把relation放到invoice当中，并且把invoice放到每个关联的订单当中.
	 * getOrdInvoiceMap.
	 * 
	 * @param ordInvoiceList
	 *            发票列表
	 * @return Map<订单ID, 发票列表>
	 */
	private Map<Long, List<OrdInvoice>> getOrdInvoiceMap(
			List<OrdInvoice> ordInvoiceList,
			List<OrdInvoiceRelation> relationList) {
		Map<Long, List<OrdInvoice>> map = new HashMap<Long, List<OrdInvoice>>();
		if(CollectionUtils.isNotEmpty(ordInvoiceList)&&CollectionUtils.isNotEmpty(relationList)){//为空就直接跳过
			//先绑定发票与关联的订单列表
			Map<Long,List<OrdInvoiceRelation>> relationMap=new HashMap<Long, List<OrdInvoiceRelation>>();
			Map<Long,List<OrdInvoiceRelation>> ordRelationMap=new HashMap<Long, List<OrdInvoiceRelation>>();
			for(OrdInvoiceRelation r:relationList){
				List<OrdInvoiceRelation> list,list2;
				if(relationMap.containsKey(r.getInvoiceId())){
					list=relationMap.get(r.getInvoiceId());				
				}else{
					list=new ArrayList<OrdInvoiceRelation>();
				}
				list.add(r);
				relationMap.put(r.getInvoiceId(), list);
				
				
				if(ordRelationMap.containsKey(r.getOrderId())){
					list2=ordRelationMap.get(r.getOrderId());
				}else{
					list2=new ArrayList<OrdInvoiceRelation>();
				}
				list2.add(r);
				ordRelationMap.put(r.getOrderId(), list2);
			}
			Map<Long,OrdInvoice> invoiceMap=new HashMap<Long, OrdInvoice>();
			if(MapUtils.isNotEmpty(relationMap)){
				for(OrdInvoice invoice:ordInvoiceList){
					invoice.setInvoiceRelationList(relationMap.get(invoice.getInvoiceId()));
					invoiceMap.put(invoice.getInvoiceId(), invoice);
				}
			}
			
			
			if(MapUtils.isNotEmpty(ordRelationMap)){			
				for(Long orderId:ordRelationMap.keySet()){
					List<OrdInvoiceRelation> list=ordRelationMap.get(orderId);
					List<OrdInvoice> invoices=new ArrayList<OrdInvoice>();
					for(OrdInvoiceRelation r:list){
						invoices.add(invoiceMap.get(r.getInvoiceId()));
					}
					map.put(orderId, invoices);
				}
			}
		}
		return map;
	}

	/**
	 * getFincTransactionMap.
	 * 
	 * @param fincTransactionList
	 *            交易列表
	 * @return Map<订单ID, 交易列表>
	 */
	private Map<Long, PayTransaction> getPayTransactionMap(
			final List<PayTransaction> payTransactionList) {
		final Map<Long, PayTransaction> map = new Hashtable<Long, PayTransaction>();
		for (PayTransaction item : payTransactionList) {
			map.put(item.getObjectId(), item);
		}
		return map;
	}

	/**
	 * getOrdOrderRouteMap.
	 * 
	 * @param orderRouteList
	 *            线路列表
	 * @return Map<订单ID, 订单线路>
	 */
	private Map<Long, OrdOrderRoute> getOrdOrderRouteMap(
			final List<OrdOrderRoute> orderRouteList) {
		final Map<Long, OrdOrderRoute> map = new Hashtable<Long, OrdOrderRoute>();
		for (OrdOrderRoute item : orderRouteList) {
			map.put(item.getOrderId(), item);
		}
		return map;
	}

	public TrackLogDAO getTrackLogDAO() {
		return trackLogDAO;
	}

	public void setTrackLogDAO(TrackLogDAO trackLogDAO) {
		this.trackLogDAO = trackLogDAO;
	}
	
	/**
	 * 订单二次处理跟踪.
	 * @param ordOrderTrackList
	 * @return
	 */
	private Map<Long, OrdOrderTrack> getOrdOrderTrackMap(
			final List<OrdOrderTrack> ordOrderTrackList) {
		final Map<Long, OrdOrderTrack> map = new Hashtable<Long, OrdOrderTrack>();
		for (OrdOrderTrack item : ordOrderTrackList) {
			List<TrackLog> list=trackLogDAO.getTrackLogByOrderId(item.getOrdTrackId());
			if(list!=null&&list.size()>0){
				TrackLog trackLog=list.get(0);
				item.setTrackLogStatus(trackLog.getZhTrackStatus());
			}
			map.put(item.getOrderId(), item);
		}
		return map;
	}
	
	
	/**
	 * 向订单里添加相关对象.
	 * 
	 * @param orderList
	 *            订单列表
	 * @param ordOrderItemProdMap
	 *            销售产品订单子项映射
	 * @param ordOrderItemMetaMap
	 *            采购产品订单子项映射
	 * @param ordPersonMap
	 *            人员映射
	 * @param usrUsersMap
	 *            用户映射
	 * @param ordInvoiceMap
	 *            发票映射
	 * @param fincTransactionMap
	 *            交易映射
	 * @param ordOrderRouteMap
	 *            线路映射
	 * @return 订单列表
	 */
	private List<OrdOrder> fill2OrdOrder(final List<OrdOrder> orderList,
			final Map<Long, List<OrdOrderItemProd>> ordOrderItemProdMap,
			final Map<Long, List<OrdOrderItemMeta>> ordOrderItemMetaMap,
			final Map<Long, List<OrdPerson>> ordPersonMap,
			final Map<Long, List<OrdInvoice>> ordInvoiceMap,
			final Map<Long, PayTransaction> fincTransactionMap,
			final Map<Long, OrdOrderRoute> ordOrderRouteMap,
			final Map<Long, OrdOrderTrack> ordOrderTrackMap
	          ) {
		for (OrdOrder order : orderList) {
			if (ordOrderItemProdMap.containsKey(order.getOrderId())) {
				order.setOrdOrderItemProds(ordOrderItemProdMap.get(order
						.getOrderId()));
				order.removeMainInItemProds();
			}
			if (ordOrderItemMetaMap.containsKey(order.getOrderId())) {
				order.setAllOrdOrderItemMetas(ordOrderItemMetaMap.get(order
						.getOrderId()));
			}
			if (ordOrderRouteMap.containsKey(order.getOrderId())) {
				order.setOrderRoute(ordOrderRouteMap.get(order.getOrderId()));
			}
			if (ordOrderTrackMap.containsKey(order.getOrderId())) {
				order.setOrderTrack(ordOrderTrackMap.get(order.getOrderId()));
			}
			if (ordPersonMap.containsKey(order.getOrderId())) {
				order.setPersonList(ordPersonMap.get(order.getOrderId()));
				for (OrdPerson person : order.getPersonList()) {
					if (Constant.ORD_PERSON_TYPE.CONTACT.name().equals(
							person.getPersonType())) {
						order.setContact(person);
					}
					if (Constant.ORD_PERSON_TYPE.ADDRESS.name().equals(
							person.getPersonType())) {
						order.setAddress(person);
					}
					if (Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(
							person.getPersonType())) {
						order.getTravellerList().add(person);
					}
				}
				//没有游玩时取联系人
				if(order.getTravellerList().isEmpty()){
					order.getTravellerList().add(order.getContact());
				}
			}
		
			if (ordInvoiceMap.containsKey(order.getOrderId())) {
				order.setInvoiceList(ordInvoiceMap.get(order.getOrderId()));
			}
			
			// TODO 订单支付交易不是唯一的，如何办？
			// if (fincTransactionMap.containsKey(order.getOrderId())) {
			// order.setPay(fincTransactionMap.get(order.getOrderId()));
			// }
		}
		return orderList;
	}
 
	/**
	 * 向订单里添加相关对象.
	 * 
	 * @param list
	 *            订单列表
	 * @param orderSql
	 *            查询 {@link OrdOrder}的SQL
	 * @return 订单列表
	 */
	private List<OrdOrder> fill2OrdOrder(final List<OrdOrder> list,
			final String orderSql) {
		List<OrdOrderItemProd> ordOrderItemProdList = queryDAO
				.queryOrderRelate(OrdOrderItemProd.class, orderSql);
		List<OrdOrderItemProdTime> ordOrderItemProdTimeList = queryDAO
				.queryOrderRelate(OrdOrderItemProdTime.class, orderSql);
		ordOrderItemProdList = this.fill2OrderItemProdTimeList(
				ordOrderItemProdList, ordOrderItemProdTimeList);
		List<OrdOrderItemMeta> ordOrderItemMetaList = queryDAO
				.queryOrderRelate(OrdOrderItemMeta.class, orderSql);
		List<OrdOrderItemMetaTime> ordOrderItemMetaTimeList = queryDAO
				.queryOrderRelate(OrdOrderItemMetaTime.class, orderSql);
		ordOrderItemMetaList = this.fill2OrderItemMeatTimeList(
				ordOrderItemMetaList, ordOrderItemMetaTimeList);
		ordOrderItemProdList = fill2OrdOrderItemProd(ordOrderItemProdList,
				ordOrderItemMetaList);
		//ordOrderItemProdList = fillShortName(ordOrderItemProdList);
		final List<OrdPerson> ordPersonList = queryDAO.queryOrderRelate(
				OrdPerson.class, orderSql);
		final List<OrdInvoice> ordInvoiceList = queryDAO.queryOrderRelate(
				OrdInvoice.class, orderSql);
		final List<OrdInvoiceRelation> ordInvoiceRelationList = queryDAO
				.queryOrderRelate(OrdInvoiceRelation.class, orderSql);

		final List<PayTransaction> payTransactionList = queryDAO
				.queryOrderRelate(PayTransaction.class, orderSql);
		//团附加表 
		final List<OrdOrderRoute> orderRouteList = queryDAO.queryOrderRelate(
				OrdOrderRoute.class, orderSql);
		//订单二次处理.
		final List<OrdOrderTrack> orderTrackList=queryDAO.queryOrderRelate(
				OrdOrderTrack.class, orderSql);
		
		final List<PayPayment> orderPreList = queryDAO.queryOrderRelate(
				PayPayment.class, orderSql);
		return fill2OrdOrder(list,
				getOrdOrderItemProdMap(ordOrderItemProdList),
				getOrdOrderItemMetaMap(ordOrderItemMetaList),
				getOrdPersonMap(ordPersonList),
				getOrdInvoiceMap(ordInvoiceList,ordInvoiceRelationList),
				getPayTransactionMap(payTransactionList),
				getOrdOrderRouteMap(orderRouteList),
				getOrdOrderTrackMap(orderTrackList));
	}
	
	private List<OrdOrderItemMeta> fill2OrderItemMeatTimeList(
			List<OrdOrderItemMeta> ordOrderItemMetaList,
			List<OrdOrderItemMetaTime> ordOrderItemMetaTimeList) {
		final Map<Long, List<OrdOrderItemMetaTime>> map = new Hashtable<Long, List<OrdOrderItemMetaTime>>();
		for (OrdOrderItemMetaTime item : ordOrderItemMetaTimeList) {
			final List<OrdOrderItemMetaTime> list;
			if (map.containsKey(item.getOrderItemMetaId())) {
				list = map.get(item.getOrderItemMetaId());
			} else {
				list = new ArrayList<OrdOrderItemMetaTime>();
			}
			list.add(item);
			map.put(item.getOrderItemMetaId(), list);
		}
		for (OrdOrderItemMeta item : ordOrderItemMetaList) {
			if (map.containsKey(item.getOrderItemMetaId())) {
				item.setAllOrdOrderItemMetaTime(map.get(item
						.getOrderItemMetaId()));
			}
		}
		return ordOrderItemMetaList;
	}

	private List<OrdOrderItemProd> fill2OrderItemProdTimeList(
			List<OrdOrderItemProd> ordOrderItemProdList,
			List<OrdOrderItemProdTime> ordOrderItemProdTimeList) {
		final Map<Long, List<OrdOrderItemProdTime>> map = new Hashtable<Long, List<OrdOrderItemProdTime>>();
		for (OrdOrderItemProdTime item : ordOrderItemProdTimeList) {
			final List<OrdOrderItemProdTime> list;
			if (map.containsKey(item.getOrderItemProdId())) {
				list = map.get(item.getOrderItemProdId());
			} else {
				list = new ArrayList<OrdOrderItemProdTime>();
			}
			list.add(item);
			map.put(item.getOrderItemProdId(), list);
		}
		for (OrdOrderItemProd item : ordOrderItemProdList) {
			if (map.containsKey(item.getOrderItemProdId())) {
				item.setAllOrdOrderItemProdTime(map.get(item
						.getOrderItemProdId()));
			}
		}
		return ordOrderItemProdList;
	}

	/**
	 * 向订单里添加相关对象.
	 * 
	 * @param list
	 *            订单列表
	 * @param orderSql
	 *            查询 {@link OrdOrder}的SQL
	 * @param ordOrderItemMetaList
	 *            ordOrderItemMetaList
	 * @return 订单列表
	 */
	private List<OrdOrder> fill2OrdOrderWithNotOrdOrderItemMeta(
			final List<OrdOrder> list, final String orderSql,
			final List<OrdOrderItemMeta> ordOrderItemMetaList) {
		List<OrdOrderItemProd> ordOrderItemProdList = queryDAO
				.queryOrderRelate(OrdOrderItemProd.class, orderSql);
		ordOrderItemProdList = fill2OrdOrderItemProd(ordOrderItemProdList,
				ordOrderItemMetaList);
		//ordOrderItemProdList = fillShortName(ordOrderItemProdList);
		final List<OrdPerson> ordPersonList = queryDAO.queryOrderRelate(
				OrdPerson.class, orderSql);
		final List<OrdInvoice> ordInvoiceList = queryDAO.queryOrderRelate(
				OrdInvoice.class, orderSql);
		final List<OrdInvoiceRelation> ordInvoiceRelationList = queryDAO
				.queryOrderRelate(OrdInvoiceRelation.class, orderSql);
		final List<PayTransaction> payTransactionList = queryDAO
				.queryOrderRelate(PayTransaction.class, orderSql);
		final List<PayPayment> orderPreList = queryDAO.queryOrderRelate(
				PayPayment.class, orderSql);
		return fill2OrdOrder(list,
				getOrdOrderItemProdMap(ordOrderItemProdList),
				new Hashtable<Long, List<OrdOrderItemMeta>>(),
				getOrdPersonMap(ordPersonList),
				getOrdInvoiceMap(ordInvoiceList,ordInvoiceRelationList),
				getPayTransactionMap(payTransactionList),
				new Hashtable<Long, OrdOrderRoute>(),
				new Hashtable<Long, OrdOrderTrack>()
				);
	}

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
	@Override
	public Long compositeQueryOrdOrderCount(final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getSQLBuilderCount();
		director.setOrdOrderBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryCount(OrdOrder.class,
				sqlBuilder.buildCompleteSQLStatement());
	}

	/**
	 * 根据订单ID查询订单.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的订单，包含相关销售产品，相关采购产品，相关供应商等信息，如果指定ID的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrder queryOrdOrder(final Long orderId) {
		OrdOrder order = queryDAO.queryOrdOrder(orderId);
		
		
		if (UtilityTool.isValid(order)) {
			List<OrdOrder> list = new ArrayList<OrdOrder>();
			list.add(order);
			list = fill2OrdOrder(list, getSQL(order.getOrderId()));
			if (!list.isEmpty()) {
				order = list.get(0);
				
				//填充发票内容信息  发票配送地址也要加载
				List<OrdInvoice> invoiceList=order.getInvoiceList();
				InvoiceRelate invoiceRelate = new CompositeQuery.InvoiceRelate();
				invoiceRelate.setFindAddress(true);
				fillOrdInvoice(invoiceList,invoiceRelate);
				order.setInvoiceList(invoiceList);
				
				List<OrdOrderTraffic> trafficList = queryDAO.queryOrderTrafficList(orderId);
				List<OrdOrderTrafficTicketInfo> ticketInfoList = queryDAO.queryOrderTrafficTicketInfo(orderId);
				fillTraffic(order,trafficList,ticketInfoList);
				order.setOrderTrafficList(trafficList);
			}
		}
		return order;
	}
	
	private void fillTraffic(OrdOrder order,List<OrdOrderTraffic> trafficList,
			List<OrdOrderTrafficTicketInfo> ticketInfoList) {
		if(trafficList.isEmpty()){
			return;
		}
		if(ticketInfoList.isEmpty()){
			return;
		}
		Map<Long,OrdPerson> personMap = new HashMap<Long, OrdPerson>();
		for(OrdPerson person:order.getTravellerList()){
			personMap.put(person.getPersonId(), person);
		}
		Map<Long,List<OrdOrderTrafficTicketInfo>> map = new HashMap<Long, List<OrdOrderTrafficTicketInfo>>();
		for(OrdOrderTrafficTicketInfo info:ticketInfoList){
			List<OrdOrderTrafficTicketInfo> list=null;
			if(map.containsKey(info.getOrderTrafficId())){
				list = map.get(info.getOrderTrafficId());
			}else{
				list = new ArrayList<OrdOrderTrafficTicketInfo>();
			}
			if(info.getOrdPersonId()!=null){
				info.setPerson(personMap.get(info.getOrdPersonId()));
			}
			list.add(info);
			map.put(info.getOrderTrafficId(), list);
		}
		for(OrdOrderTraffic traffic:trafficList){
			traffic.setOrderTrafficTicketInfoList(map.get(traffic.getOrderTrafficId()));
		}
	}

	/**
	 * order.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return SQL
	 */
	private String getSQL(final Long orderId) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getSQLBuilder();
		final CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getOrderIdentity().setOrderId(orderId);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return sqlBuilder.buildCompleteSQLStatement();
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
		return queryDAO.queryOrdOrderIdBySerialNo(serialNo);
	}
 

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
	@Override
	public List<OrderAndComment> selectCanRefundOrderByOrderId(final Long orderId){
		return queryDAO.selectCanRefundOrderByOrderId(orderId);
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
	public List<OrderAndComment> queryOrderAndCommentOnPeriod(final Map<String, Object> map ) {
		return queryDAO.queryOrderAndCommentOnPeriod(map);
	}
	
   /**
	* 根据订单ID查询订单是否待返现
	* @param orderId
	*            订单ID
	* @return {@link OrdOrder}
	*/
	public OrderAndComment queryCanRefundOrderByOrderId(Long orderId)
	{
		return queryDAO.queryCanRefundOrderByOrderIdOnPeriod(orderId);
	}
	
	
	/**
	 * 获取可点评的订单产品信息
	 * @param userNo
	 * @return
	 */
	@Override
	public List<OrderAndComment> selectCanCommentOrderProductByUserNo(final String userNo){
		return queryDAO.selectCanCommentOrderProductByUserNo(userNo);
	}
	
	/**
	 * 获取可点评的订单产品信息,用于写站内信
	 * @return
	 */
	@Override
	public List<OrderAndComment> selectCanCommentOrderProductByDate(){
		return queryDAO.selectCanCommentOrderProductByDate();
	}
	

	/**
	 * 根据采购产品订单子项ID获取订单ID.
	 * 
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * 指定采购产品订单子项ID的订单ID，如果指定采购产品订单子项ID的订单ID不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public Long queryOrdOrderId(final Long ordOrderItemMetaId) {
		return queryDAO.queryOrdOrderId(ordOrderItemMetaId);
	}

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
	@Override
	public List<OrdSaleService> queryOrdSaleService(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getSaleServiceSQLBuilder();
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return fillOrdSaleService(queryDAO.queryList(
				OrdSaleService.class, sqlBuilder.buildCompleteSQLStatement()));
	}

	/**
	 * 填充投诉列表.
	 * 
	 * @param list
	 *            投诉列表
	 * @return 投诉列表
	 */
	private List<OrdSaleService> fillOrdSaleService(
			final List<OrdSaleService> list) {
		for (OrdSaleService item : list) {
			if (UtilityTool.isValid(item.getOrderId())) {
				item.setOrdOrder(queryOrdOrder(item.getOrderId()));
			}
		}
		return list;
	}

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
	@Override
	public Long queryOrdSaleServiceCount(final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getSaleServiceSQLBuilderCount();
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		System.out.println("queryOrdSaleServiceCount sql: "+sqlBuilder.buildCompleteSQLStatement());
		return queryDAO.queryCount(OrdSaleService.class,
				sqlBuilder.buildCompleteSQLStatement());
	}

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
	@Override
	public List<OrdInvoice> queryOrdInvoice(final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getInvoiceSQLBuilder();
		director.setInvoiceBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		String sql = sqlBuilder.buildCompleteSQLStatement();
		List<OrdInvoice> list = fillOrdInvoice(queryDAO.queryList(OrdInvoice.class,
				sql),compositeQuery.getInvoiceRelate());
		
		if(compositeQuery.getInvoiceRelate().isFindOrder() && CollectionUtils.isNotEmpty(list)){
			sql = "select DISTINCT ORD_ORDER.* from ORD_ORDER,ORD_INVOICE_RELATION WHERE ORD_ORDER.ORDER_ID=ORD_INVOICE_RELATION.ORDER_ID AND INVOICE_ID IN(SELECT INVOICE_ID FROM ("+sql+"))";
			
			List<OrdOrder> orderList = queryDAO.queryList(OrdOrder.class, sql);
			if (!orderList.isEmpty()) {
				orderList = fill2OrdOrder(orderList,sql);
				list = fillOrdInvoice2(orderList, list);
			}
		}		
		return list;
	}
	
	private List<OrdInvoice> fillOrdInvoice2(List<OrdOrder> orderList,List<OrdInvoice> invoiceList){
		Map<Long,OrdOrder> orderMap=new HashMap<Long, OrdOrder>();
		for(OrdOrder order:orderList){
			orderMap.put(order.getOrderId(), order);
		}
		
		for(OrdInvoice invoice:invoiceList){
			invoice.setOrderList(getOrderListByInvoiceRelation(orderMap, invoice.getInvoiceRelationList()));
		}
		
		return invoiceList;
	}
	private List<OrdOrder> getOrderListByInvoiceRelation(Map<Long,OrdOrder> orderMap,List<OrdInvoiceRelation> relationList){
		if(CollectionUtils.isEmpty(relationList)){
			return Collections.emptyList();
		}
		List<OrdOrder> orderList=new ArrayList<OrdOrder>();
		for(OrdInvoiceRelation r:relationList){
			OrdOrder order = orderMap.get(r.getOrderId());
			if(order!=null){
				orderList.add(order);
			}
		}
		return orderList;
	}

	/**
	 * 填充发票列表.
	 * 
	 * @param list
	 *            发票列表
	 * @return 发票列表
	 */
	public List<OrdInvoice> fillOrdInvoice(List<OrdInvoice> list,InvoiceRelate invoiceRelate) {
		if(CollectionUtils.isNotEmpty(list)){
			for (OrdInvoice item : list) {
				if (invoiceRelate.isFindOrderRelation()) {
					List<OrdInvoiceRelation> relations = queryDAO
							.queryInvoiceRelationByInvoiceId(item
									.getInvoiceId());
					item.setInvoiceRelationList(relations);
				}
				if (invoiceRelate.isFindAddress()) {
					item.setDeliveryAddress(queryDAO.queryPerson(item
							.getInvoiceId(), Constant.OBJECT_TYPE.ORD_INVOICE));
				}
			}
		}
		return list;
	}

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
	@Override
	public Long queryOrdInvoiceCount(final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getInvoiceSQLBuilderCount();
		director.setInvoiceBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryCount(OrdInvoice.class,
				sqlBuilder.buildCompleteSQLStatement());
	}

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
	@Override
	public OrdOrderItemProd queryOrdOrderItemProdById(final Long orderItemProdId) {		
		return queryDAO.queryOrdOrderItemProdById(orderItemProdId);
	}
	
	

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.Query#queryOrdOrderItemMetaById(java.lang.Long)
	 */
	@Override
	public OrdOrderItemMeta queryOrdOrderItemMetaById(Long orderItemMetaId) {
		return queryDAO.queryOrdOrderItemMetaById(orderItemMetaId);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getDirector()
	 */
	@Override
	public Director getDirector() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getFinishedSettlementItemSQLBuilder()
	 */
	@Override
	public SQLBuilder getFinishedSettlementItemSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getFinishedSettlementItemSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getFinishedSettlementItemSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getInvoiceSQLBuilder()
	 */
	@Override
	public SQLBuilder getInvoiceSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getInvoiceSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getInvoiceSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getOrdOrderItemMetaSQLBuilder()
	 */
	@Override
	public SQLBuilder getOrdOrderItemMetaSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getOrdOrderItemMetaSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getOrdOrderItemMetaSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getOrdSettlementSQLBuilder()
	 */
	@Override
	public SQLBuilder getOrdSettlementSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getOrdSettlementSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getOrdSettlementSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getPassPortDetailSQLBuilder()
	 */
	@Override
	public SQLBuilder getPassPortDetailSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getPassPortDetailSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getPassPortDetailSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getPassPortSummarySQLBuilder()
	 */
	@Override
	public SQLBuilder getPassPortSummarySQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getPassPortSummarySQLBuilderCount()
	 */
	@Override
	public SQLBuilder getPassPortSummarySQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getPerformDetailSQLBuilder()
	 */
	@Override
	public SQLBuilder getPerformDetailSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getPerformDetailSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getPerformDetailSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSaleServiceSQLBuilder()
	 */
	@Override
	public SQLBuilder getSaleServiceSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSaleServiceSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getSaleServiceSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSettlementItemSQLBuilder()
	 */
	@Override
	public SQLBuilder getSettlementItemSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSettlementItemSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getSettlementItemSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSettlementQueueSQLBuilder()
	 */
	@Override
	public SQLBuilder getSettlementQueueSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSettlementQueueSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getSettlementQueueSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSQLBuilder()
	 */
	@Override
	public SQLBuilder getSQLBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSQLBuilderCount()
	 */
	@Override
	public SQLBuilder getSQLBuilderCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.impl.AbstractBuilderFactory#getSQLBuilderSum()
	 */
	@Override
	public SQLBuilder getSQLBuilderSum() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据订单号查询订单.
	 * 
	 * @param orderNo
	 *            订单号
	 * @return <pre>
	 * 指定订单号的订单，包含相关销售产品，相关采购产品，相关供应商等信息，如果指定订单号的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrder queryOrdOrderByOrderNo(final String orderNo) {
		OrdOrder order = queryDAO.queryOrdOrderByOrderNo(orderNo);
		if (UtilityTool.isValid(order)) {
			List<OrdOrder> list = new ArrayList<OrdOrder>();
			list.add(order);
			list = fill2OrdOrder(list, getSQL(order.getOrderId()));
			if (!list.isEmpty()) {
				order = list.get(0);
			}
		}
		return order;
	}
	/**
	 * 查询此订单的毛利润
	 * 
	 * @param orderId
	 * @return
	 */
	public Long queryOrderProfitByOrderId(Long orderId) {
		return queryDAO.queryOrderProfitByOrderId(orderId);
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
		return queryDAO.queryOrdPerformByOrderItemMetaId(orderItemMetaId);
	}

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
	@Override
	public List<OrdOrder> compositeQueryOrdOrderItemMeta(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getOrdOrderItemMetaSQLBuilder();
		director.setOrdOrderItemMetaBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		final List<OrdOrderItemMeta> ordOrderItemMetaList = queryDAO.queryList(
				OrdOrderItemMeta.class, sqlBuilder.buildCompleteSQLStatement());
		List<OrdOrder> orderList = fillOrderList(ordOrderItemMetaList);
		if (!orderList.isEmpty()) {
			orderList = fill2OrdOrderWithNotOrdOrderItemMeta(orderList,
					sqlBuilder.buildOrderItemMetaIdInStatement(),
					ordOrderItemMetaList);
		}
		return orderList;
	}

	/**
	 * 填充订单列表.
	 * 
	 * @param ordOrderItemMetaList
	 *            采购产品订单子项
	 * @return 订单列表
	 */
	private List<OrdOrder> fillOrderList(
			final List<OrdOrderItemMeta> ordOrderItemMetaList) {
		final List<OrdOrder> orderList = new ArrayList<OrdOrder>();
		for (OrdOrderItemMeta item : ordOrderItemMetaList) {
			final OrdOrder order = queryDAO.queryOrdOrder(item.getOrderId());
			if (UtilityTool.isValid(order)) {
				final List<OrdOrderItemMeta> list = new ArrayList<OrdOrderItemMeta>();
				list.add(item);
				order.setAllOrdOrderItemMetas(list);
				orderList.add(order);
			}
		}
		return orderList;
	}

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
	@Override
	public Long compositeQueryOrdOrderItemMetaCount(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getOrdOrderItemMetaSQLBuilderCount();
		director.setOrdOrderItemMetaBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryCount(OrdOrderItemMeta.class,
				sqlBuilder.buildCompleteSQLStatement());
	}

	/**
	 * 根据订单ID查询{@link OrdPayment}.
	 * 
	 * @param objectId
	 *            订单ID
	 * @return {@link OrdPayment}列表
	 */
	@Override
	public List<PayPayment> queryOrdPaymentByOrderId(final Long objectId) {
		return payPaymentService.selectByObjectIdAndBizType(objectId, com.lvmama.comm.vo.Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
	}
	 
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
	@Override
	public List<OrdOrderItemMeta> compositeQueryOrdOrderItemMetaByMetaPerformRelate(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getOrdOrderItemMetaSQLBuilder();
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryList(OrdOrderItemMeta.class,
				sqlBuilder.buildCompleteSQLStatement());
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
		return queryDAO.queryOrdOrderAmountItem(orderId, oderAmountType);
	}

	/**
	 * 履行明细查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细列表
	 */
	@Override
	public List<PerformDetail> queryPerformDetail(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getPerformDetailSQLBuilder();
		director.setPerformDetailBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryList(PerformDetail.class,
				sqlBuilder.buildCompleteSQLStatement());
	}

	/**
	 * 履行明细查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细查询计数
	 */
	@Override
	public Long queryPerformDetailCount(final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getPerformDetailSQLBuilderCount();
		director.setPerformDetailBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryCount(PerformDetail.class,
				sqlBuilder.buildCompleteSQLStatement());
	}

	/**
	 * E景通订单查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	@Override
	public List<PerformDetail> queryPerformDetailForEplaceList(CompositeQuery compositeQuery){
		Map<String,Object> params=createQueryPerformDetailForEplace(compositeQuery);
		return queryDAO.queryPerformDetailForEplaceList( params);
	}	
	/**
	 * E景通订单查询(分页).
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	@Override
	public List<PerformDetail> queryPerformDetailForEplacePageList(CompositeQuery compositeQuery){
		Map<String,Object> params=createQueryPerformDetailForEplace(compositeQuery);
		return queryDAO.queryList(PerformDetail.class,"ORDER.queryPerformDetailForEPlacePageListSQL", params);
	}
	
	/**
	 * E景通订单查询计数.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	@Override
	public Long queryPerformDetailForEplaceCount(CompositeQuery compositeQuery){
		Map<String,Object> params=createQueryPerformDetailForEplace(compositeQuery);
		return queryDAO.queryCount("ORDER.queryPerformDetailForEPlaceCountSQL", params);
	}
	/**
	 * E景通统计各类型门票数量.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	@Override
	public List<OrdEplaceOrderQuantity> queryEbkOrderForEplaceTotalQuantity(CompositeQuery compositeQuery,boolean isTotal){
		Map<String,Object> params=createQueryPerformDetailForEplace(compositeQuery);
		if(isTotal){
			return queryDAO.queryList(OrdEplaceOrderQuantity.class,"ORDER.queryEbkOrderForEplaceTotalQuantity", params);
		}else{
			return queryDAO.queryList(OrdEplaceOrderQuantity.class,"ORDER.queryEbkOrderForEplaceDailyTotalQuantity", params);
		}
	}
	@Override
	public List<String> queryPerformDetailForEplaceTongjiPageList(CompositeQuery compositeQuery){
		Map<String,Object> params=createQueryPerformDetailForEplace(compositeQuery);
		return queryDAO.queryList(String.class,"ORDER.queryPerformDetailForEPlaceTongjiPageListSQL", params);
	}
	@Override
	public Long queryPerformDetailForEplaceTongjiCount(CompositeQuery compositeQuery){
		Map<String,Object> params=createQueryPerformDetailForEplace(compositeQuery);
		return queryDAO.queryCount("ORDER.queryPerformDetailForEPlaceTongjiCountSQL", params);
	}
	
	private Map<String,Object> createQueryPerformDetailForEplace(CompositeQuery compositeQuery){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("contactName", compositeQuery.getPerformDetailRelate().getContactName());
		map.put("contactMobile", compositeQuery.getPerformDetailRelate().getContactMobile());
		map.put("orderStatus", compositeQuery.getPerformDetailRelate().getOrderStatus());
		map.put("paymentTarget", compositeQuery.getPerformDetailRelate().getPaymentTarget());
		map.put("metaBrandIds", compositeQuery.getPerformDetailRelate().getBranchIds());
		map.put("visitTimeStart", compositeQuery.getPerformDetailRelate().getVisitTimeStart());
		map.put("visitTimeEnd", compositeQuery.getPerformDetailRelate().getVisitTimeEnd());
		map.put("orderId", compositeQuery.getPerformDetailRelate().getOrderId());
		map.put("performStatus", compositeQuery.getPerformDetailRelate().getPerformStatus());
		map.put("performStatus", compositeQuery.getPerformDetailRelate().getPerformStatus());
		map.put("orderItemMetaId", compositeQuery.getPerformDetailRelate().getOrderItemMetaId());
		map.put("passwordCertificate", compositeQuery.getPerformDetailRelate().getPasswordCertificate());
		map.put("isAperiodic", compositeQuery.getPerformDetailRelate().getIsAperiodic());
		map.put("useStatus", compositeQuery.getPerformDetailRelate().getUseStatus());
		map.put("beginIndex", compositeQuery.getPageIndex().getBeginIndex());
		map.put("endIndex", compositeQuery.getPageIndex().getEndIndex());
		
		map.put("createTimeStart", compositeQuery.getOrderTimeRange().getCreateTimeStart());
		map.put("createTimeEnd", compositeQuery.getOrderTimeRange().getCreateTimeEnd());
		return map;
	}
	/**
	 * 通关汇总查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关汇总列表
	 */
	@Override
	public List<PassPortSummary> queryPassPortSummary(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getPassPortSummarySQLBuilder();
		director.setPassPortSummaryBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryList(PassPortSummary.class,
				sqlBuilder.buildCompleteSQLStatement());
	}

	/**
	 * 通关汇总查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关汇总查询计数
	 */
	@Override
	public Long queryPassPortSummaryCount(final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getPassPortSummarySQLBuilderCount();
		director.setPassPortSummaryBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryCount(PassPortSummary.class,
				sqlBuilder.buildPassPortSummarySQLBuilderCount());
	}

	/**
	 * 通关明细查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关明细列表
	 */
	@Override
	public List<PassPortDetail> queryPassPortDetail(
			final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getPassPortDetailSQLBuilder();
		director.setPassPortDetailBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryList(PassPortDetail.class,
				sqlBuilder.buildCompleteSQLStatement());
	}

	/**
	 * 通关明细查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关明细查询计数
	 */
	@Override
	public Long queryPassPortDetailCount(final CompositeQuery compositeQuery) {
		final Director director = getDirector();
		final SQLBuilder sqlBuilder = getPassPortDetailSQLBuilderCount();
		director.setPassPortDetailBusiness(true);
		director.setCompositeQuery(compositeQuery);
		director.order(sqlBuilder);
		return queryDAO.queryCount(PassPortDetail.class,
				sqlBuilder.buildCompleteSQLStatement());
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
		return queryDAO.queryOrdPerformByOrderId(orderId);
	}

	/**
	 * 根据订单ID查询 {@link FincTransaction}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link FincTransaction}
	 */
	@Override
	public PayTransaction queryFincTransactionByOrderId(final Long orderId) {
		List<PayTransaction> list = payPaymentService.selectPayTransactionByObjectId(Long.valueOf(orderId));
		if(!list.isEmpty() && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询提现记录计数.
	 * 
	 * @param compositeQuery
	 *            综合查询条件
	 * @return 查询提现记录计数
	 */
	@Override
	public Long queryMoneyDrawCount(final CompositeQuery compositeQuery) {
		String status = null;
		if (UtilityTool.isValid(compositeQuery.getMoneyDrawRelate().getFincCashStatus())) {
			status = compositeQuery.getMoneyDrawRelate().getFincCashStatus()
					.toString();
		}
		return queryDAO.queryMoneyDrawCount(compositeQuery.getMoneyDrawRelate()
				.getUserId(), status, compositeQuery.getMoneyDrawRelate()
				.getCreateTimeStart(), compositeQuery.getMoneyDrawRelate()
				.getCreateTimeEnd());
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
		return queryDAO.queryOrderPersonCount(productid,
				ordOrderVisitTimeStart, ordOrderVisitTimeEnd, paymentStatus,
				orderStatus);
	}
 
	@Override
	public List<OrdOrderItemMeta> queryOrdOrderItemMetaByOrderId(Long orderId){
		return this.queryDAO.queryOrdOrderItemMetaByOrderId(orderId);
	}
	
	/**
	 * 根据订单号查询订单子子项集合
	 */
	public OrdOrder queryOrdOrderItemMetaListRefund(Long refundmentId, Long orderId){
		OrdOrder ordOrder = this.queryOrdOrder(orderId);
		List<OrdOrderItemMeta> ordOrderItemMetaList = queryDAO.queryOrderItemMetaListRefund(refundmentId,orderId);
		ordOrder.setAllOrdOrderItemMetas(ordOrderItemMetaList);
		return ordOrder;
	}
	
	/**
	 * 根据订单号查询订单子子项集合
	 */
	public List<OrdOrderItemMeta> queryOrderItemMetaList(Long orderId){
		String orderIdInsql = getSQL(orderId);
		List<OrdOrderItemMeta> ordOrderItemMetaList = queryDAO.queryOrdOrderItemMetaList(orderIdInsql);
		return ordOrderItemMetaList;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	/**
	 * 检验该用户是否已为游客下了相同的订单(酒店套房、目的地自由行)
	 * @author shihui
	 * */
	@Override
	public Long queryOrderByParams1(Map<String, Object> params) {
		return queryDAO.queryOrderByParams1(params);
	}
	
	/**
	 * 检验该用户是否已为游客下了相同的订单(酒店单房型)
	 * @author shihui
	 * */
	@Override
	public List<Map<String, Object>> queryOrderByParams2(Map<String, Object> params) {
		return queryDAO.queryOrderByParams2(params);
	}
	@Override
	public Long queryOrderLimitByUserId(Map<String, Object> params){
		return queryDAO.queryOrderLimitByUserId(params);
	}
	@Override
	public Long queryOrderLimitByMobile(Map<String, Object> params){
		return queryDAO.queryOrderLimitByMobile(params);
	}
	
	@Override
	public Long queryOrderLimitByCertNo(Map<String, Object> params){
		return queryDAO.queryOrderLimitByCertNo(params);
	}
	
	@Override
	public List<Long> queryOrderLimitByParam(Map<String,Object> params){
		return queryDAO.queryOrderLimitByParam(params);
	}

	@Override
	public Long queryOrdOrderCount(Map<String, Object> params){
		return queryDAO.queryOrdOrderCount(params);
	}
}
