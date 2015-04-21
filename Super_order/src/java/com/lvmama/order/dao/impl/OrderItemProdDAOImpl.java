package com.lvmama.order.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.order.dao.OrderItemProdDAO;

/**
 * 销售产品订单子项DAO实现类.
 *
 * <pre>
 * 封装销售产品订单子项DCRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.BaseIbatisDao
 * @see com.lvmama.ord.po.OrdOrderItemProd
 * @see com.lvmama.order.dao.OrderItemProdDAO
 */
public final class OrderItemProdDAOImpl extends BaseIbatisDAO implements
		OrderItemProdDAO {
	public Long calcOrderAmount(final Long orderId) {
		return (Long) super.queryForObject(
				"ORDER_ITEM_PROD.calcAmount", orderId);
	}

	public Long insert(final OrdOrderItemProd record) {
		Object newKey = super.insert(
				"ORDER_ITEM_PROD.insert", record);
		return (Long) newKey;
	}
	public void updateByPrimaryKey(final OrdOrderItemProd record) {
		 super.update(
				"ORDER_ITEM_PROD.updateByPrimaryKey", record);
	}
	
	public List<OrdOrderItemProd> queryByOrderIdAndProductId(final Long orderId, final Long productId) {
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("orderId", orderId);
		params.put("productId", productId);
		return (List<OrdOrderItemProd>) super.
				queryForList("ORDER_ITEM_PROD.queryOrdOrderItemProdByOrderIdAndProductId", params);
	}

	/**
	 * 通过订单号加载订单明细列表
	 * @param orderId
	 * @return
	 */
	public List<OrdOrderItemProd> selectByOrderId(Long orderId){
		return (List<OrdOrderItemProd>)super.queryForList("ORDER_ITEM_PROD.queryOrdOrderItemProdByOrderId", orderId);
	}
	
	/**
	 * 通过主键获得订单子项
	 * @param orderItemProdId
	 * @return
	 */
    public OrdOrderItemProd selectByPrimaryKey(Long orderItemProdId) {
        OrdOrderItemProd record = (OrdOrderItemProd) super.queryForObject("ORDER_ITEM_PROD.selectByPrimaryKey", orderItemProdId);
        return record;
    }
    
    /**
     * 判断销售产品是否为 超级自由行 的 主销售产品
     */
    public boolean isSuperFreeMainProd(Long orderItemProductId){
    	Long c = (Long)super.queryForObject("ORDER_ITEM_PROD.getSuperFreeMainProdCount",orderItemProductId);
    	return c==null?false : c > 0?true:false;
    }

	@Override
	public void updateOrderItemProdPaidAmount(long orderId) {
		 super.update("ORDER_ITEM_PROD.updateOrderItemProdPaidAmount", orderId);
	}

	@Override
	public void clearVisitTime(Long orderId) {
		super.update("ORDER_ITEM_PROD.clearVisitTime", orderId);
	}
	@Override 
	public Long getOrderCountByProductIds(String[] productIds,Date startTime,Date endTime) { 
		Map<String,Object> param = new HashMap<String, Object>(); 
		if(productIds!=null&&productIds.length>0){ 
			param.put("productIds", productIds); 
			param.put("validBeginTime", DateUtil.formatDate(startTime, "yyyy-MM-dd"));
			param.put("validEndTime", DateUtil.formatDate(endTime, "yyyy-MM-dd"));
			return (Long)super.queryForObject("ORDER_ITEM_PROD.getOrderCountByProductIds",param); 
		}else{ 
			return 0L; 
		} 
	}
}
