package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.dao.OrderPerformDAO;
import com.lvmama.order.dao.QueryDAO;

public class OrderPerformServiceImpl implements OrderPerformService {
	
	private static Logger logger = Logger.getLogger(OrderPerformServiceImpl.class);

	private OrderDAO orderDAO;
	
	private OrderPerformDAO orderPerformDAO;
	
	private OrderItemProdDAO orderItemProdDAO;
	
	private OrderItemMetaDAO orderItemMetaDAO;
	private QueryDAO queryDAO;
	/**
	 * 新增履行信息.
	 *
	 * @param performTargetId
	 *            履行对象ID
	 * @param objectId
	 *            订单ID或订单子子项ID
	 * @param objectType
	 *            objectId指向的类型
	 * @param adultQuantity
	 *            该采购产品的成人数量
	 * @param childQuantity
	 *            该采购产品的儿童数量
	 *
	 * @return 订单号
	 */
	@Override
	public Long insertOrdPerform(Long performTargetId, Long objectId, String objectType, Long adultQuantity, Long childQuantity,String memo) {
		String performStatus = Constant.ORDER_PERFORM_STATUS.PERFORMED.name();
		Long orderId = insertPerform(performTargetId, objectId, objectType, adultQuantity, childQuantity, memo, performStatus);
		return orderId;
	}
	/**
	 * 新增履行信息.
	 * @author: ranlongfei 2013-2-28 下午4:37:15
	 * @param performTargetId
	 * @param objectId
	 * @param objectType
	 * @param adultQuantity
	 * @param childQuantity
	 * @param memo
	 * @param performStatus
	 * @return
	 */
	private Long insertPerform(Long performTargetId, Long objectId, String objectType, Long adultQuantity, Long childQuantity, String memo, String performStatus) {
		Long orderId = null;
		if("ORD_ORDER".equalsIgnoreCase(objectType)) {
			List<OrdOrderItemMeta> itemMetaList = orderItemMetaDAO.selectByPerformTargetIdAndOrderId(performTargetId, objectId);
			for (OrdOrderItemMeta ordOrderItemMeta : itemMetaList) {
				OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(ordOrderItemMeta.getOrderItemId());
				if(!"true".equalsIgnoreCase(itemProd.getAdditional())){
					ordOrderItemMeta.setPerformStatus(performStatus);
					orderItemMetaDAO.updateByPrimaryKey(ordOrderItemMeta);
				}
			}
			orderId = objectId;
		} else {
			OrdOrderItemMeta ordOrderItemMeta = orderItemMetaDAO.selectByPrimaryKey(objectId);
			OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(ordOrderItemMeta.getOrderItemId());
			if(!"true".equalsIgnoreCase(itemProd.getAdditional())){
				ordOrderItemMeta.setPerformStatus(performStatus);
				orderItemMetaDAO.updateByPrimaryKey(ordOrderItemMeta);
			}
			orderId = ordOrderItemMeta.getOrderId();
		}
		List<OrdPerform> list = orderPerformDAO.selectByObjectIdAndObjectType(objectId, objectType);
		if (list.size()>0) {
			logger.warn("insertOrdPerform Warning: OrdPerform is exist with objectId = " + objectId + " and objectType = " + objectType);
			for (OrdPerform ordPerform : list) {
				ordPerform.setAdultQuantity(adultQuantity);
				ordPerform.setChildQuantity(childQuantity);
				ordPerform.setMemo(memo);
				logger.error(adultQuantity);
				logger.error(childQuantity);
				orderPerformDAO.update(ordPerform);
			}
		}else{
			OrdPerform ordPerform = new OrdPerform();
			ordPerform.setPerformTargetId(performTargetId);
			ordPerform.setObjectId(objectId);
			ordPerform.setObjectType(objectType);
			ordPerform.setAdultQuantity(adultQuantity);
			ordPerform.setChildQuantity(childQuantity);
			ordPerform.setMemo(memo);
			orderPerformDAO.insert(ordPerform);
		}
		return orderId;
	}
	@ Override
	public boolean autoPerform(Long orderItemMetaId, Long performTargetId) {
		OrdOrderItemMeta ordOrderItemMeta = orderItemMetaDAO.selectByPrimaryKey(orderItemMetaId);
		OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(ordOrderItemMeta.getOrderItemId());
		if(!"true".equalsIgnoreCase(itemProd.getAdditional())){
			insertPerform(performTargetId , orderItemMetaId, "ORD_ORDER_ITEM_META", ordOrderItemMeta.getTotalAdultQuantity(), ordOrderItemMeta.getTotalChildQuantity(), "自动履行", Constant.ORDER_PERFORM_STATUS.AUTOPERFORMED.name());
		}
		return checkAllPerformed(ordOrderItemMeta.getOrderId());
	}

	@ Override
	public boolean checkAllPerformed(Long orderId) {
		if (orderPerformDAO.isAllPerformed(orderId)) {
//			OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
//			order.setPerformStatus(Constant.ORDER_PERFORM_STATUS.PERFORMED.name());
//			orderDAO.updateByPrimaryKey(order);
			orderDAO.updateOrderPerformed(orderId);
			return true;
		}
		return false;
	}
	
	public List<PerformDetail> getOrderPerformDetail(List<Long> orderItemMetaIds){
		return orderPerformDAO.getOrderPerformDetail(orderItemMetaIds);
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setOrderPerformDAO(OrderPerformDAO orderPerformDAO) {
		this.orderPerformDAO = orderPerformDAO;
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}
	@Override
	public Page<OrdOrderPerformResourceVO> queryOrderPerformByPage(Long pageSize,Long currentPage,List<Long> metaBranchIds,Map<String, Object> para) {
		//未履行的订单
		Page<OrdOrderPerformResourceVO> page = Page.page(pageSize, currentPage);
		page = orderDAO.queryOrderPerformByPage(page,para);
		//根据订单号取到，已订单号和targetId分组
		OrdOrderPerformResourceVO order = null;
		for (int i = 0; null!=  page.getItems() && i < page.getItems().size(); i++) {
			order = page.getItems().get(i);
			order.setOrdOrderItemMetaList(orderItemMetaDAO.selectByPerformByMetaBranchIdAndOrderId(order.getOrderId(), metaBranchIds));
			order.setOrdPersonList(this.queryDAO.queryOrdPersonByOrdOrderId(order.getOrderId()));
		}
		return page;
	}

	public void setQueryDAO(QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}
	
	@Override
	public List<OrdOrderPerformResourceVO> queryOrderPerformByEBK(Map<String, Object> para) {
		List<OrdOrderPerformResourceVO> orderPerformResources = orderDAO.queryOrderPerformByEBK(para);
		for(OrdOrderPerformResourceVO vo : orderPerformResources){
			para.put("orderId", vo.getOrderId());
			vo.setOrdOrderItemMetaList(orderItemMetaDAO.selectOrdOrderItemMetasByEBK(para));
			vo.setOrdPersonList(queryDAO.queryOrdPersonByOrdOrderId(vo.getOrderId()));
		}
		
		return orderPerformResources;
	}
	
	
	@Override
	public boolean isItemMetasNotInOrder(Long[] orderItemMetaId, Long orderId) {
		List<Long> orderItemMetaIdsList = new ArrayList<Long>();
		for(Long id : orderItemMetaId){
			orderItemMetaIdsList.add(id);
		}
		Long length = orderItemMetaDAO.selectForPerformed(orderItemMetaIdsList,orderId);
		return length.intValue() != orderItemMetaId.length;
	}
	@Override
	public ResultHandle perform(Long orderId, Long targetId, Long[] quantity, Long[] orderItemMetaId, String remark, Long ebkUserId, Date performTime) {
		throw new RuntimeException("not implements.");
	}
	@Override
	public ResultHandle perform(String addCode, String udid, Long[] quantity, Long[] orderItemMetaId, String remark, Long ebkUserId, Date performTime) {
		throw new RuntimeException("not implements.");
	}
	
}
