package com.lvmama.ord.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdRefundmentItemProd;
import com.lvmama.comm.pet.po.sup.SupSupplier;

public class OrdRefundMentDAO extends BaseIbatisDAO {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(OrdRefundMentDAO.class);
	
	public Long findOrdRefundByParamCountSaleFinish(Map param){
		Long count = 0l;
		count = (Long) super.queryForObject(
				"ORD_REFUNDMENT.selectByParamCountSaleFinish",param);
		return count;
	}
	public List<OrdRefundment> findOrdRefundByParamSaleFinish(Map param,int skipResults,int maxResults){
		List<OrdRefundment> list = super.queryForList(
				"ORD_REFUNDMENT.selectByParamSaleFinish", param,skipResults,maxResults);
		return list;
	}
	
	public Long insert(OrdRefundment record) {
		Object newKey = super.insert(
				"ORD_REFUNDMENT.insert", record);
		return (Long) newKey;
	}
	/**
	 * 根据退款单ID取退款单信息.
	 * @param ordRefundId
	 * @return
	 */
	public OrdRefundment findOrdRefundmentById(Long refundmentId){
		return (OrdRefundment)super.queryForObject("ORD_REFUNDMENT.selectById",refundmentId);
	}
	
	
	public void updateOrdRefundmentByPK(OrdRefundment ordrefundment) {
		super.update("ORD_REFUNDMENT.updateByPrimaryKey",
				ordrefundment);
	}
	public Long findOrdRefundByParamCount(Map<String,Object> param){
		Long count = 0l;
		try{
			count = (Long) super.queryForObject(
					"ORD_REFUNDMENT.selectByParamCount",param);
		}catch(Exception e){
			String paramStr = "LOG DEBUG INFO === findOrdRefundByParamCount SQLException ";
			for (Map.Entry<String,Object> entry : param.entrySet()) {
				paramStr = paramStr + "[" +entry.getKey() + ":" + entry.getValue() + "],";
			}
			LOG.error(paramStr);
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	public Long findVstOrdRefundByParamCount(Map<String,Object> param){
		Long count = 0l;
		try{
			count = (Long) super.queryForObject(
					"ORD_REFUNDMENT.selectVstOrdByParamCount",param);
		}catch(Exception e){
			String paramStr = "LOG DEBUG INFO === findOrdRefundByParamCount SQLException ";
			for (Map.Entry<String,Object> entry : param.entrySet()) {
				paramStr = paramStr + "[" +entry.getKey() + ":" + entry.getValue() + "],";
			}
			LOG.error(paramStr);
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	
	public List<OrdRefundment> findOrdRefundByParam(Map param,int skipResults,int maxResults) {
		if(param == null){
			param = new HashMap<String, Object>();
		}
		param.put("skipResults", skipResults);
		param.put("maxResults", maxResults);
		List<OrdRefundment> list = super.queryForList("ORD_REFUNDMENT.selectByParam",param);
		return list;
	}
	
	public List<OrdRefundment> findVstOrdRefundByParam(Map param,int skipResults,int maxResults) {
		if(param == null){
			param = new HashMap<String, Object>();
		}
		param.put("skipResults", skipResults);
		param.put("maxResults", maxResults);
		List<OrdRefundment> list = super.queryForList("ORD_REFUNDMENT.selectVstOrdByParam",param);
		return list;
	}
	
	public java.math.BigDecimal findOrdfundByParamSumAmount(Map param) {
		java.math.BigDecimal sum = null;
		sum = (java.math.BigDecimal) super.queryForObject("ORD_REFUNDMENT.selectByParamSumAmount",param);
		return sum;
	}
	
	public List<OrdRefundment> queryRefundment(Map param) {
		List<OrdRefundment> list = super.queryForList("ORD_REFUNDMENT.queryRefundment",param);
		return list;
	}
	public List<OrdRefundment> queryVstRefundment(Map param) {
		List<OrdRefundment> list = super.queryForList("ORD_REFUNDMENT.queryVstRefundment",param);
		return list;
	}
	
	public Long queryRefundmentCount(Map param) {
		Long result = (Long)super.queryForObject("ORD_REFUNDMENT.queryRefundmentCount",param);
		return result;
	}
	public Long queryVstRefundmentCount(Map param) {
		Long result = (Long)super.queryForObject("ORD_REFUNDMENT.queryVstRefundmentCount",param);
		return result;
	}
	
	public List<OrdOrderItemMeta> queryOrdOrderItemMetaList(Long refundMentId) {
		List<OrdOrderItemMeta> list = super.queryForList("ORD_REFUNDMENT.queryOrdOrderItemMetaList", refundMentId);
		return list;
	}
	
	public List<OrdRefundMentItem> queryOrdRefundmentItemsByRefundmentId(Long refundMentId) {
		List<OrdRefundMentItem> list = super.queryForList("ORD_REFUNDMENT.queryOrdRefundmentItemsByRefundmentId", refundMentId);
		return list;
	}
	/**
	 * 根据供应商id查询供应商信息.
	 * @param supplierId
	 * @return
	 */
	public SupSupplier querySupplierById(Long supplierId){
		return (SupSupplier)super.queryForObject("ORD_REFUNDMENT.selectSupplierById",supplierId);
	}
	
	public boolean updateRefundStatus(Long refundmentId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("refundmentId", refundmentId);
		map.put("status", status);
		int res = super.update("ORD_REFUNDMENT.updateRefundStatus", map);
		if(res == 1)
			return true;
		else
			return false;
	}
	
	public boolean insertOrdRefundmentItem(Map map) {
		try{
			super.insert("ORD_REFUNDMENT.insertRefundmentItem", map);
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}

		return true;
	}
	
	public boolean updateOrdRefundmentItem(Map map) {
		try{
			super.update("ORD_REFUNDMENT.updateRefundmentItem", map);
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}

		return true;
	}
	
	public List<OrdRefundment> queryRefundmentList(Map param) {
		List<OrdRefundment> list = super.queryForList("ORD_REFUNDMENT.queryRefundmentList",param);
		return list;
	}
	public List<OrdRefundment> queryVstRefundmentList(Map param) {
		List<OrdRefundment> list = super.queryForList("ORD_REFUNDMENT.queryVstRefundmentList",param);
		return list;
	}
	
	public Long queryRefundmentListCount(Map param) {
		Long result = (Long)super.queryForObject("ORD_REFUNDMENT.queryRefundmentListCount",param);
		return result;
	}
	public Long queryVstRefundmentListCount(Map param) {
		Long result = (Long)super.queryForObject("ORD_REFUNDMENT.queryVstRefundmentListCount",param);
		return result;
	}
	
	public boolean updateOrderStatus(Long orderId, String status) {
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("status", status);
		int res = super.update("ORD_REFUNDMENT.updateOrderStatus", map);
		if(res == 1)
			return true;
		else
			return false;
	}
	
	public List<OrdRefundment> queryRefundmentByOrderId(Map param) {
		return super.queryForList("ORD_REFUNDMENT.queryRefundmentByOrderId",param);
	}
	
	public List<OrdOrderItemProd> queryProds(Long orderId) {
		return super.queryForList("ORD_REFUNDMENT.queryProdsByOrderId",orderId);
	}
	
	public List<String> queryManagerNameList(Long orderId) {
		return this.queryForList("ORD_REFUNDMENT.queryManagerNameList", orderId);
	}
	
	public List<OrdRefundMentItem> queryRefundMentItem(final Long orderItemMetaId){
		Assert.notNull(orderItemMetaId);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderItemMetaId", orderItemMetaId);
		return queryForList("ORD_REFUNDMENT_ITEM.select",map);
	}
	
	public boolean insertOrdRefundmentItemProd(OrdRefundmentItemProd ordRefundmentItemProd) {
		try {
			super.insert("ORD_REFUNDMENT.insertRefundmentItemProd", ordRefundmentItemProd);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	public Long queryRefundmentItemProdCount(final Long refundmentId) {
		return (Long)super.queryForObject("ORD_REFUNDMENT.queryRefundmentItemProdCount", refundmentId);
	}
}