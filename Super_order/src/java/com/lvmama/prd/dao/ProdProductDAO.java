package com.lvmama.prd.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdHotSellSeq;
import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdOther;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ProdTicket;
import com.lvmama.comm.bee.po.prod.ProdTraffic;
import com.lvmama.comm.bee.vo.ProdRouteDate;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.vo.Constant;

public class ProdProductDAO extends BaseIbatisDAO {

	public int deleteByPrimaryKey(Long productId) {
		ProdProduct key = new ProdProduct();
		key.setProductId(productId);
		int rows = super.delete("PROD_PRODUCT.deleteByPrimaryKey", key);
		return rows;
	}

	public List<Long> selectAllProductId(Map<String,Object> params) {
		return super.queryForList("PROD_PRODUCT.selectAllProductId",params);
	}
	
	public Long getGroupProdIdCount() {
		Map<String, Long> params = new HashMap<String, Long>();
		return (Long)super.queryForObject("PROD_PRODUCT.selectGroupProdIdCount");
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getAllGroupProdIds(Long startRow, Long endRow) {
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("_startRow", startRow);
		params.put("_endRow", endRow);
		return super.queryForList("PROD_PRODUCT.selectAllGroupProdIds",params);
	}
	
	public Long selectAllProductIdCount(){
		return (Long)super.queryForObject("PROD_PRODUCT.selectAllProductIdCount");
	}
	
	public Long insert(ProdProduct record) {
		if(record.getProductId()==null){
			record.setProductId((Long)super.queryForObject("PROD_PRODUCT.selectProductKey"));
		}
        //审核状态不能为空，初始为 "产品待提交"
        if(record.getAuditingStatus()==null){
            record.setAuditingStatus(Constant.PRODUCT_AUDITING_STATUS.PRODUCTS_SUBMITTED.getCode());
        }
		Long id = (Long)super.insert("PROD_PRODUCT.insert", record);
		if (record instanceof ProdRoute) {
			super.insert("PROD_ROUTE.insert", record);
		} else if (record instanceof ProdOther) {
			super.insert("PROD_OTHER.insert", record);
		} else if (record instanceof ProdTicket) {
			super.insert("PROD_TICKET.insert", record);
		} else if (record instanceof ProdHotel) {
			super.insert("PROD_HOTEL.insert", record);
		}else if (record instanceof ProdTraffic){
			super.insert("PROD_TRAFFIC.insert", record);
		}
		updateSelfPackPaymentTarget(record);
		return id;
	}
	
	/**
	 * 
	 * @param record
	 */
	private void updateSelfPackPaymentTarget(final ProdProduct record){
		if(record.hasSelfPack()){
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("productId", record.getProductId());
			param.put("payToLvmama", "true");
			param.put("payToSupplier", "false");
			updatePaymentTarget(param);
		}
	}

	public ProdProduct selectByPrimaryKey(Long productId) {
		ProdProduct key = new ProdProduct();
		key.setProductId(productId);
		ProdProduct record = (ProdProduct) super.queryForObject("PROD_PRODUCT.selectByPrimaryKey", key);
		return record;
	}
	
	public ProdProduct selectProductByProdBranchId(Long prodBranchId) {
		ProdProduct record = (ProdProduct) super.queryForObject("PROD_PRODUCT.selectProductByProdBranchId", prodBranchId);
		return record;
	}

	public ProdProduct selectProductDetailByPrimaryKey(Long productId) {
		ProdProduct par = new ProdProduct();
		par.setProductId(productId);
		Object o = super.queryForObject("PROD_PRODUCT.selectByPrimaryKey",par);
		if(o!=null){
			String type=((ProdProduct)o).getProductType();
			ProdProduct key = new ProdProduct();
			key.setProductId(productId);
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(type)) {
				return (ProdRoute) super.queryForObject("PROD_PRODUCT.selectProdRouteByPrimaryKey", key);
			} else if (Constant.PRODUCT_TYPE.TRAFFIC.name().equals(type)) {
				return (ProdTraffic) super.queryForObject("PROD_PRODUCT.selectProdTrafficByPrimaryKey", key);
			}else if (Constant.PRODUCT_TYPE.HOTEL.name().equals(type)) {
				return (ProdHotel) super.queryForObject("PROD_PRODUCT.selectProdHotelByPrimaryKey", key);
			} else if (Constant.PRODUCT_TYPE.TICKET.name().equals(type)) {
				return (ProdTicket) super.queryForObject("PROD_PRODUCT.selectProdTicketByPrimaryKey", key);
			} else if (Constant.PRODUCT_TYPE.OTHER.name().equals(type)) {
				return (ProdOther) super.queryForObject("PROD_PRODUCT.selectProdOtherByPrimaryKey", key);
			}
		}
		return null;
	}
	
	public ProdProduct selectProductDetailByProductType(Long productId, String productType) {
		ProdProduct key = new ProdProduct();
		key.setProductId(productId);
		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
			return (ProdRoute) super.queryForObject("PROD_PRODUCT.selectProdRouteByPrimaryKey", key);
		} else if (Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)) {
			return (ProdTraffic) super.queryForObject("PROD_PRODUCT.selectProdTrafficByPrimaryKey", key);
		}else if (Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)) {
			return (ProdHotel) super.queryForObject("PROD_PRODUCT.selectProdHotelByPrimaryKey", key);
		} else if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
			return (ProdTicket) super.queryForObject("PROD_PRODUCT.selectProdTicketByPrimaryKey", key);
		} else if (Constant.PRODUCT_TYPE.OTHER.name().equals(productType)) {
			return (ProdOther) super.queryForObject("PROD_PRODUCT.selectProdOtherByPrimaryKey", key);
		}
		return null;
	}
	public ProdProduct selectProductDetailByProductType(Long productId, String productType,boolean WithoutValid) {
		ProdProduct key = new ProdProduct();
		key.setProductId(productId);
		if(WithoutValid){
			key.setValid("WithoutValid");
		}
		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
			return (ProdRoute) super.queryForObject("PROD_PRODUCT.selectProdRouteByPrimaryKey", key);
		} else if (Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)) {
			return (ProdTraffic) super.queryForObject("PROD_PRODUCT.selectProdTrafficByPrimaryKey", key);
		} else if (Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)) {
			return (ProdHotel) super.queryForObject("PROD_PRODUCT.selectProdHotelByPrimaryKey", key);
		} else if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
			return (ProdTicket) super.queryForObject("PROD_PRODUCT.selectProdTicketByPrimaryKey", key);
		} else if (Constant.PRODUCT_TYPE.OTHER.name().equals(productType)) {
			return (ProdOther) super.queryForObject("PROD_PRODUCT.selectProdOtherByPrimaryKey", key);
		}
		return null;
	}
	
	public ProdRoute selectProdRouteByPrimaryKey(Long id){
		ProdProduct key = new ProdProduct();
		key.setProductId(id);
		return (ProdRoute) super.queryForObject("PROD_PRODUCT.selectProdRouteByPrimaryKey", key);
	}
	
	public ProdTicket selectProdTicketByPrimaryKey(Long id){
		ProdProduct key = new ProdProduct();
		key.setProductId(id);
		return (ProdTicket) super.queryForObject("PROD_PRODUCT.selectProdTicketByPrimaryKey", key);
	}
	

	public ProdHotel selectProdHotelByPrimaryKey(Long id){
		ProdProduct key = new ProdProduct();
		key.setProductId(id);
		return (ProdHotel) super.queryForObject("PROD_PRODUCT.selectProdHotelByPrimaryKey", key);
	}
	 
	public int updateByPrimaryKey(ProdProduct record) {
		int rows = super.update("PROD_PRODUCT.updateByPrimaryKey", record);
		if (record instanceof ProdRoute) {
			super.update("PROD_ROUTE.updateByProductId", record);
		}else if(record instanceof ProdTraffic){
			super.update("PROD_TRAFFIC.updateByPrimaryKey", record);
		}else if (record instanceof ProdOther) {
			super.update("PROD_OTHER.updateByPrimaryKey", record);
		} else if (record instanceof ProdTicket) {
			super.update("PROD_TICKET.updateByPrimaryKey", record);
		} else if (record instanceof ProdHotel) {
			super.update("PROD_HOTEL.updateByPrimaryKey", record);
		}
		updateSelfPackPaymentTarget(record);
		return rows;
	}

	public List<ProdProduct> selectbyParam(Map<String, Object> param) {
		return super.queryForList("PROD_PRODUCT.selectbyParam", param);
	}
	
	public List<ProdProduct> selectBizCode(Map<String, Object> param) {
		return super.queryForList("PROD_PRODUCT.selectBizCode", param);
	}

	public List<ProdProduct> selectProductByParms(Map<String, Object> map) {
		return super.queryForList("PROD_PRODUCT.selectByNameTimeType", map);
	}

	public List<String> selectChannelByProductId(Long productId) {
		return super.queryForList("PROD_PRODUCT.selectChannelByProductId", productId);
	}

	public List<ProdProductChannel> getProductChannelByProductId(Long productId) {
		return super.queryForList("PROD_PRODUCT.getChannelByProductId", productId);
	}

	public int deleteChannelByProductId(Long productId) {
		return super.delete("PROD_PRODUCT.deleteChannelByProductId", productId);
	}

	public void insertChannel(ProdProductChannel channel) {
		super.insert("PROD_PRODUCT.insertChannel", channel);
	}
	@Deprecated
	public List<ProdProduct> getRelatProduct(Long productId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("productId", productId);
		return super.queryForList("PROD_PRODUCT.selectRelatProduct", map);
	}

	public List<ProdProduct> getProdProductByParamByRelation(ProdProduct record) {
		//TODO 该方法需要修改,现在关联的产品只有附加产品并且是关联到类别.
		return super.queryForList("PROD_PRODUCT.getProdProductByParamByRelation", record);
	}
	@Deprecated
	public List<ProdProduct> getProdProductByRelation(ProdProduct record) {
		return super.queryForList("PROD_PRODUCT.getProdProductByRelation", record);
	}
	
	public Integer selectByRelatProductIdAndProductId(Long relatProductId,Long productId){
		HashMap<String, Object> par=new HashMap<String, Object>();
		par.put("relatProductId", relatProductId);
		par.put("productId", productId);
		Integer count= (Integer) super.queryForObject("PROD_PRODUCT.selectByRelatProductIdAndProductId",par);
		return count;
 	}
	 
	public void updatePriceByProductId(ProdProduct product) {
		super.update("PROD_PRODUCT.updatePriceByProductId", product);
	}
	
	public void markIsValid(Map params) {
		super.update("PROD_PRODUCT.markIsValid", params);
	}
	
	public List<ProdProduct> getRouteListByParam(Map param){
		return super.queryForList("PROD_PRODUCT.selectRouteProductByParam",param);
	}
	public List<ProdProduct> selectSuggestInfoByPlacesName(String productName){
		return super.queryForList("PROD_PRODUCT.selectSuggestInfoByPlacesName",productName);
	}
	
	public void updatePaymentTarget(Map<String,Object> param){
		super.update("PROD_PRODUCT.updatePaymentTarget", param);
	}
	
	public Long selectBizCodeByProductId(Long productId){
		return (Long)super.queryForObject("PROD_PRODUCT.selectBizCodeByProductId",productId);
	}
	
	public void updateTimeById(Long productId,Date onlineTime,Date offlineTime){
		HashMap map=new HashMap();
		map.put("objectId", productId);
		map.put("onlineTime", onlineTime);
		map.put("offlineTime", offlineTime);
		super.update("PROD_PRODUCT.updateTimeById",map);
	}
	
	public Integer selectRowCount(Map searchConds){
		Integer count = 0;
		count = (Integer) super.queryForObject("PROD_PRODUCT.selectRowCount",searchConds);
		return count;
	}
	
	public ProdRoute getProdRouteById(Long productId) {
		return (ProdRoute) super.queryForObject("PROD_ROUTE.selectByProductId",productId);
	}
	
	public ProdOther getProdOtherById(Long productId){
		return (ProdOther) super.queryForObject("PROD_OTHER.selectByProductId",productId);
	}
	
	public ProdHotel getProdHotelById(Long productId) {
		return (ProdHotel) super.queryForObject("PROD_HOTEL.getProdHotelById",productId);
	}

	public List<ProdHotel> queryProdHotelByPlaceIdandParam(Map params){
		return super.queryForList("PROD_PRODUCT.selectHotelProductByPlaceIdAndParam", params);
	}

	public List<ProdProduct> queryProductByTagAndProductType(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.queryProductByTagAndProductType",params);
	}

	public Integer queryProductByTagAndProductTypeCount(
			Map<String, Object> params) {
		return  (Integer)super.queryForObject("PROD_PRODUCT.queryProductByTagAndProductTypeCount",params);
	}
	public HashMap getMinPriceByPlaceId(Long placeId, String stage,String channel) {
		Map params = new HashMap<String, Object>();
		params.put("placeId", placeId);
		params.put("stage", stage);
		params.put("channel", channel);
		return (HashMap) super.queryForObject("PROD_PRODUCT.getMinPriceByPlaceIdAndStage", params);
	}
	
	public Map queryOnlineProductByProductId(Map params) {
		return (Map)super.queryForObject("PROD_PRODUCT.queryOnlineProductByProductId", params);
	}
	public Long countOrderByProduct(Map params){
		 return (Long)super.queryForObject("PROD_PRODUCT.countOrderByProduct", params);
	}
	public List<ProdProduct> queryOnlineOtherProductByChannel(Map params){
		 return (List<ProdProduct>)super.queryForList("PROD_PRODUCT.queryOnlineOtherProductByChannel", params);
	}
	public Long countJoinUsersByProd(Map params){
	
		return (Long)super.queryForObject("PROD_PRODUCT.countJoinUsersByProd", params);
	}
	/**
	 * 查询团购产品参与用户
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryJoinUsersByProd(Map params){
		 return super.queryForList("PROD_PRODUCT.queryJoinUsersByProd", params);
	}
	public List<Map>  queryPrdTagByProductId(Map params){
		return super.queryForList("PROD_PRODUCT.queryPrdTagByProductId", params);
	}
	public List<Map> queryOnlineAndOffelineProductByChannel(Map params){
		return super.queryForList("PROD_PRODUCT.queryOnlineAndOffelineProductByChannel", params);
	}
	public Long countOnlineAndOffelineProductByChannel(Map params){
		return (Long)super.queryForObject("PROD_PRODUCT.countOnlineAndOffelineProductByChannel", params);
	}
	
	/**
	 * @author lipengcheng
	 * 控制销售产品是否可售
	 */
	public void markIsSellable(Map params){
	
		super.update("PROD_PRODUCT.markIsSellable",params);
		
	}


	
	public Long insertAssembly(ProdAssemblyPoint record) {
		Long id = (Long)super.insert("PROD_PRODUCT.insertAssembly", record);
		return id;
	}
	
	public List<ProdAssemblyPoint> selectAssembly(Long productId) {
		return super.queryForList("PROD_PRODUCT.selectAssembly", productId);
	}
	
	public void delAssembly(Long assemblyPointId) {
		super.delete("PROD_PRODUCT.delAssembly", assemblyPointId);
	}
	
	public ProdAssemblyPoint selectAssemblyById(Long assemblyPointId){
		ProdAssemblyPoint key = new ProdAssemblyPoint();
		key.setAssemblyPointId(assemblyPointId);
		ProdAssemblyPoint record = (ProdAssemblyPoint) super.queryForObject("PROD_PRODUCT.selectAssemblyByPrimaryKey", key);
		return record;
	}
	
	/**
	 * @author lipengcheng
	 * 查询销售产品分页
	 */
	public List<ProdProduct> selectManager(Map params){
	
		return super.queryForList("PROD_PRODUCT.selectManager",params);
		
	}
	/**
	 * @author lipengcheng
	 * 查询销售产品总数
	 */
	public Integer selectManagerCount(Map params){
	
		return (Integer)super.queryForObject("PROD_PRODUCT.selectManagerCount",params);
		
	}
	
	/**
	 * 批量修改销售产品经理
	 * @param params
	 */
	public void updateManager(HashMap params) {
		super.update("PROD_PRODUCT.updateManager",params);
	}
	
	/**
	 * 在批量修改销售产品经理，修改产品的组织ID
	 * @param params
	 */
	public void updateOrgId(HashMap params){
		super.update("PROD_PRODUCT.updateOrgId",params);
	}
	
	/**
	 * 批量修改销售产品id
	 * */
	public void updateOrgIds(Map<String, Object> params) {
		super.update("PROD_PRODUCT.updateOrgIds",params);
	}
	
	/**
	 * 更新一句话推荐信息
	 * @param prodProduct
	 * @return
	 */
	public void updateProdRecommendWord(ProdProduct prodProduct) {
		super.update("PROD_PRODUCT.updateProdRecommendWord",prodProduct);
	}
	/**
	 * 取出全部在线可售的产品
	 * 分页
	 * @param _endRow
	 * @param _startRow
	 * @return
	 */
	public List<ProdProduct> selectAllProductIdOnline(Map <String ,Object> params){
		return super.queryForList("PROD_PRODUCT.selectAllProductIdOnline",params);
	}
	
	/**
	 * 取出全部在线可售的产品的数量
	 * @param params
	 * @return
	 */
	public Long selectAllProductIdOnlineCount(){
		return (Long)super.queryForObject("PROD_PRODUCT.selectAllProductIdOnlineCount");
	}
	
	public Integer isExistProduct (Long productId){
		return (Integer)super.queryForObject("PROD_PRODUCT.isExistProduct",productId);
		 
	}
	
	/**
	 * @author shihui
	 * 门票产品列表查询
	 * */
	public List<ProdProduct> selectTicketProductListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectTicketProductListByParams", params);
	}
	
	public Long selectCountProductListByParams(Map<String, Object> params) {
		String productType = (String) params.get("productType");
		if(Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
			return (Long) super.queryForObject("PROD_PRODUCT.selectCountTicketProductListByParams", params);
		} else if(Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)) {
			return (Long) super.queryForObject("PROD_PRODUCT.selectTrainProductCountByParams", params);
		}else if(Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)) {
			return (Long) super.queryForObject("PROD_PRODUCT.selectCountHotelProductListByParams", params);
		} else {
			return (Long) super.queryForObject("PROD_PRODUCT.selectCountOtherProductListByParams", params);
		}
	}
	
	/**
	 * @author shihui
	 * 线路产品列表查询
	 * */
	public List<ProdRouteDate> selectRouteProductListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectRouteProductListByParams", params);
	}
	
	/**
	 * @author shihui
	 * 酒店产品列表查询
	 * */
	public List<ProdProduct> selectHotelProductListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectHotelProductListByParams", params);
	}
	
	/**
	 * 查询交通产品列表
	 * @param params
	 * @return
	 */
	public List<ProdProduct> selectTrainProductListByParams(Map<String,Object> params){
		return super.queryForList("PROD_PRODUCT.selectTrainProductListByParams",params);
	}
	
	/**
	 * 其他类型产品查询
	 * */
	public List<ProdProduct> selectOtherProductListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectOtherProductListByParams", params);
	}
	/**
	 * @author shihui
	 * 酒店产品类别列表信息查询
	 * */
	public List<ProdProductBranch> selectHotelBranchListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectHotelBranchListByParams", params);
	}
	
	/**
	 * 其他产品类型列表查询
	 * */
	public List<ProdProductBranch> selectOtherBranchListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectOtherBranchListByParams", params);
	}
	
	public void updateRoute(ProdRoute route){
		super.update("PROD_ROUTE.updateByProductId",route);
	}
	
	/**
	 * @author shihui
	 * 门票产品类别列表信息查询
	 * */
	public List<ProdProductBranch> selectTicketBranchListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectTicketBranchListByParams", params);
	}
	
	/**
	 * 线路搜索结果数量
	 * */
	public Long selectCountRouteListByParams(Map<String, Object> params) {
		return (Long) super.queryForObject("PROD_PRODUCT.selectCountRouteListByParams", params);
	}
	
	public List<ProdProduct> selectListByProdJourney(final Long prodJourneyId,Constant.PRODUCT_TYPE type){
		return super.queryForList("PROD_PRODUCT.select"+type.name()+"ListByProdJourney",prodJourneyId);
	}
	
	public List<Long> selectRouteProductIdsByParams(Map<String, Object> params){
		return super.queryForList("PROD_PRODUCT.selectRouteProductIdsByParams",params);
	}
	
	/**
	 * 通过productName like %productName% 出productId结果集
	 * @author ZHANG Nan
	 * @param productName productName
	 * @return productId List<Long>
	 */
	public List<Long> selectProductIdsByLikeProductName(String productName) {
		return super.queryForList("PROD_PRODUCT.selectProductIdsByLikeProductName",productName);
	}

	/**
	 * 查询团购上线产品
	 * 
	 * @param productIdStr
	 */
	@SuppressWarnings("unchecked")
	public List<ProdProduct> queryOnlineProductInProductIds(List<Long> productIdList) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productIdList", productIdList);
		return (List<ProdProduct>) super.queryForList("PROD_PRODUCT.queryOnlineProductInProductIds", param);
	}
	
	public Integer checkTimePriceByProductId(Long productId,String isSelfSign){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId",productId);
		param.put("isSelfSign",isSelfSign);
		return (Integer)super.queryForObject("PROD_PRODUCT.selectTimePriceByProductIdCount",param);
	}
	
	public List<ProdProduct> selectProductByMetaBranchId(Long metaBranchId){
		return super.queryForList("PROD_PRODUCT.selectProductByMetaBranchId",metaBranchId);
	}


	/**@author shihui
	 * 期票产品数量查询
	 * */
	public Long selectCountQiPiaoProductListByParams(Map<String, Object> params) {
		return (Long) super.queryForObject("PROD_PRODUCT.selectCountQiPiaoProductListByParams", params);
	}


	public List<ProdProduct> queryOnlineProductByParams(Map<String, Object> map) {
		return (List<ProdProduct>) super.queryForList("PROD_PRODUCT.queryOnlineProductInProductIds", map);
	}

	
	/**
	 * @author shihui
	 * 期票产品列表查询
	 * */
	public List<ProdProduct> selectQiPiaoProductListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectQiPiaoProductListByParams", params);
	}
	
	/**
	 * @author shihui
	 * 期票产品类别列表信息查询
	 * */
	public List<ProdProductBranch> selectQiPiaoBranchListByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT.selectQiPiaoBranchListByParams", params);
	}
	public ProdProduct getProdProductByLineInfoId(final Long lineInfoId){
		List<ProdTraffic> traffic = super.queryForList("PROD_TRAFFIC.getProdProductByLineInfoId",lineInfoId);
		if(traffic.isEmpty()){
			return null;
		}else{
			return selectProductDetailByPrimaryKey(traffic.get(0).getProductId());
		}
	}

	public ProdTraffic getTrainProduct(String fullName) {
		// TODO Auto-generated method stub
		ProdTraffic prodTraffic= (ProdTraffic)super.queryForObject("PROD_TRAFFIC.getTrainProduct", fullName);
		if(prodTraffic==null){
			return null;
		}
		return (ProdTraffic)selectProductDetailByProductType(prodTraffic.getProductId(),Constant.PRODUCT_TYPE.TRAFFIC.name());
	}
	
	public List<ProdProduct> selectProdToPlaceProduct(String productChannel,long placeId,int maxCount,long productId,String incodeProdId){
	    Map<String,Object> params = new HashMap<String, Object>();
	    params.put("productChannel", productChannel);
	    params.put("placeId",placeId);
	    params.put("productId",productId);
	    params.put("prodIds",incodeProdId);
	    params.put("_endRow", maxCount);
	    return super.queryForList("PROD_PRODUCT.selectProdToPlaceProduct", params);
	}

    public void updateAuditingStatus(Long productId, String auditingStatus) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("productId",productId);
        param.put("auditingStatus",auditingStatus);
        super.update("PROD_PRODUCT.updateAuditingStatus", param);

    }
    
    public void updateHasSensitiveWordByProductId(Long productId, String hasSensitiveWord) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("productId", productId);
    	params.put("hasSensitiveWord", hasSensitiveWord);
    	super.update("PROD_PRODUCT.updateHasSensitiveWordByProductId", params);
    }
    
    public void updateRefundByProductIds(Map<String, Object> params) {
    	super.update("PROD_PRODUCT.updateRefundByProductIds", params);
    }
    
    public ProdProduct selectProdWithToPlaceById(Long params){
		return (ProdProduct) super.queryForObject("PROD_PRODUCT.selectProdWithToPlaceById", params);
	}
    
    @SuppressWarnings("unchecked")
    public List<ProdProduct> queryHotSeqByProdTypeAndPlaceId(String prodPlaceIds,long orderCreateTime,String productType,String subProductType,String regionName,long endRow){
        Map<String, Object> params = new HashMap<String, Object>();
       
        params.put("prodPlaceIds", prodPlaceIds);//prodPlaceIds
        if(orderCreateTime!=0L){
            params.put("orderCreateTime", orderCreateTime);//orderCreateTime 倒计时天数 sysdate-orderCreateTime
        }
        params.put("productType", productType);//productType

        if(null !=subProductType && subProductType.length()>0){
           String[] tempPlaceId = subProductType.split(",");
           for (String groupType : tempPlaceId) {
               if(groupType=="GROUP" || groupType=="GROUP_LONG" || groupType=="GROUP_FOREIGN"){
                   params.put("groupType", "true");//groupType 排序中子类型中有跟团的则根据标的查询
               }
           } 
        }
        
        params.put("subProductType", subProductType);//subProductType
        params.put("regionName", regionName);//regionName 
        
        if(endRow==0){
            params.put("endRow", 10);
        }else{
            params.put("endRow", endRow);//endRow
        }
        return (List<ProdProduct>)super.queryForList("PROD_PRODUCT.queryHotSeqByProdTypeAndPlaceId",params);
    }
    
    public void insertProdHotSell(ProdHotSellSeq phss){
        super.insert("PROD_PRODUCT.insertProdHotSell",phss);
    }
    
    public void deleteProdHotSell(){
//        super.delete("PROD_PRODUCT.deleteProdHotSell");
    }
    
    @SuppressWarnings("unchecked")
    public List<ProdHotSellSeq> queryProdHotSell(String channel,String baseChannel){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("channel",channel);
        params.put("baseChannel",baseChannel);
        params.put("endRow",5L);//最多显示5条
        params.put("sellNotEmpty","true");//清除销量为零的数据
        return (List<ProdHotSellSeq>)super.queryForList("PROD_PRODUCT.queryProdHotSell",params);
    }
}