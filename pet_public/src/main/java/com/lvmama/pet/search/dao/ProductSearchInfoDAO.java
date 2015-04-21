package com.lvmama.pet.search.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductPlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.search.Shantou;
import com.lvmama.comm.utils.StringUtil;

public class ProductSearchInfoDAO extends BaseIbatisDAO {
	private static Logger LOG = Logger.getLogger(ProductSearchInfoDAO.class);

	/**
	 * 更新productSearchInfo
	 * @param productSearchInfo 更新后的productSearchInfo
	 */
	public void updateProductSearchInfo(final ProductSearchInfo productSearchInfo) {
		if(null != productSearchInfo && null != productSearchInfo.getProductId()) {			
			super.update("PRODUCT_SEARCH_INFO.updateProductSearchInfo", productSearchInfo);
		}
	}
	
	/**
	 * 更新点评总数
	 * <p>将进行全表更新,此API应该有定时任务触发</p>
	 */
	public void updateProductSearchInfoCmtNum() {
		super.update("PRODUCT_SEARCH_INFO.updateProductSearchInfoCmtNum");
	}
	
	/**
	 * 根据查询条件查询product_search_info表
	 * @param param 查询条件
	 * @return 符合条件的productSearchInfo列表
	 * <p>如果传入的条件为<code>null</code>或者为空，则直接返回<code>null</code>。
	 */
	@SuppressWarnings("unchecked")
	public List<ProductSearchInfo> queryProductSearchInfoByParam(final Map<String, Object> param) {
		if (null == param || param.isEmpty()) {
			return null;
		}
		String orderValue=(String)param.get("orderField");
		if(orderValue==null||"".equals(orderValue))
			param.put("orderField", "seq_asc");
		
		return (List<ProductSearchInfo>)super.queryForList("PRODUCT_SEARCH_INFO.queryProductSearchInfoByParam", param);
	}

	/**
	 * 根据查询条件对product_search_info计数
	 * @param param 查询条件
	 * @return 计数值
	 * <p>如果传入的条件为<code>null</code>或者为空，则直接返回0。
	 */
	public Long countProductSearchInfoByParam(final Map<String, Object> param) {
		if (null == param || param.isEmpty()) {
			return 0L;
		}
		return (Long) super.queryForObject("PRODUCT_SEARCH_INFO.countProductSearchInfoByParam", param);
	}
	
	/**
	 * 根据productId获取ProductSearchInfo
	 * @param productId
	 * @return
	 */
	public ProductSearchInfo queryProductSearchInfoByProductId(Long productId){
		return (ProductSearchInfo)super.queryForObject("PRODUCT_SEARCH_INFO.queryProductSearchInfoByProductId",productId);
	}

	
	/**
	 * 根据tagId查询符合此标签的产品ids,如果tagId为null则去所有的
	 * @param tagId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> queryProductIdListByTagId(Map<String, Object> param) {
		return super.queryForListForReport("PRODUCT_SEARCH_INFO.queryProductIdList", param);
	}
	

  	 
 	/**
 	 * @deprecated 这段代码不能算垃圾，但没任何的通用性。
      * 获取某个出发地下的某种类型的销售数据排行
      */
 	@SuppressWarnings("unchecked")
     public List<ProductSearchInfo> selectTopSalesList(Long fromPlaceId, String subProductType, int topLimit) {
 	     Map<String, Object> params = new HashMap<String, Object>();
	     params.put("fromPlaceId", fromPlaceId);
	     params.put("subProductType", subProductType);
	     params.put("topLimit", topLimit);
	     return super.queryForList("PRODUCT_SEARCH_INFO.selectTopSalesList", params);
     }
	/**
	 * @deprecated 这段代码不能算垃圾，但没任何的通用性。 
	 * 获取门票推荐数据(门票频道TOP10)
	 */
 	@SuppressWarnings("unchecked")
	public List<ProductSearchInfo> selectTicketProductTopData() {
		return super.queryForList("PRODUCT_SEARCH_INFO.selectTicketProductTopData");
	}
    /**
     * 获取门票或是酒店的类型列表
     * @param placeId
     * @param isTicket
     * @param channel
     * @return
     */
 	@SuppressWarnings("unchecked")
	public List<ProdBranchSearchInfo> getProductBranchByPlaceIdAndTicket(long placeId, String isTicket, String channel) {
 		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("placeId", placeId);
 		map.put("isTicket", isTicket);
 		if (!StringUtil.isEmptyString(channel) & "FRONTEND".equals(channel)) {
			 map.put("channelFront", "FRONTEND");

		} else if (!StringUtil.isEmptyString(channel) & "TUANGOU".equals(channel)) {
			 map.put("channelGroup", "TUANGOU");

		} else if (!StringUtil.isEmptyString(channel)) {
			 map.put("channel", channel);
		}
 		return super.queryForList("PRODUCT_SEARCH_INFO.getProductBranchByPlaceIdAndTicket",map);
 	}  	 
 	
 	
 	public ProdBranchSearchInfo getProdBranchSearchInfoByBranchId(Long branchId){
 		if(branchId==null){
 			throw new RuntimeException("branchId must not be null");
 		}
 		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("branchId", branchId);
 		return (ProdBranchSearchInfo) super.queryForObject("PRODUCT_SEARCH_INFO.getProductBranchPrimaryKey",map);
 	}
 	
 	

	/**
	 * 获得容器产品的数据
	 * 
	 * @param containerCode
	 * @param fromPlaceId
	 * @param toPlaceId
	 * @param productType
	 * @param subProductType
	 * @param startRow
	 * @param endRow
	 * @return
	 */
 	@SuppressWarnings("unchecked")
	public List<ProductSearchInfo> getContainerProductList(
			String containerCode, Long fromPlaceId, String toPlaceId,
			String productType, String subProductType, int startRow, int endRow) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerCode", containerCode);
		params.put("fromPlaceId", fromPlaceId);
		params.put("toPlaceId", toPlaceId);
		params.put("productType", productType);
		params.put("subProductType", subProductType);
		params.put("startRows", startRow);
		params.put("endRows", endRow);
		return super.queryForList(
				"PRODUCT_SEARCH_INFO.getContainerProductList", params);
	}
 	
 	/**
 	 * 查询所有的闪投产品信息
 	 */
 	public List<Shantou> queryProductShanTouList(Map<String, Object> param){
 		return super.queryForList("PRODUCT_SEARCH_INFO.getShantouListByParam", param);
 	}
 	
 	public Long queryCountByProductShantou(Map<String, Object> param){
 		return (Long)super.queryForObject("PRODUCT_SEARCH_INFO.countSelectShantouListByParam", param);
 	} 
 	
 	
 	/**
 	 * 查询景点门票
 	 * @param param  Map<String, Object> param
 	 * @return
 	 */
	public List<ProductPlaceSearchInfo> queryProductPlaceList(Map<String,Object> parameters){
 		return super.queryForList("PRODUCT_SEARCH_INFO.queryProductProductPlaceByParam",parameters);
 	}
	
	/**
	 * 查询景点门票总数量
	 * @return
	 */
	public Long queryProductPlaceCount(){
		return (Long) super.queryForObject("PRODUCT_SEARCH_INFO.queryProductPlaceCount");
	}
	
	

 	public List<PlaceHotelNotice> getPlaceHotelNotice(Long placeId) {
 		return super.queryForList("PRODUCT_SEARCH_INFO.queryPlaceHotelNoticeByPlaceId",placeId);
 	}
 	public List<PlaceHotelRoom> getPlaceHotelRoom(Long placeId) {
		return super.queryForList("PRODUCT_SEARCH_INFO.queryPlaceHotelRoomByPlaceId",placeId);
	}
 	public List<PlaceHotelOtherRecommend> getPlaceHotelRecommend(Long placeId) {
		return super.queryForList("PRODUCT_SEARCH_INFO.queryPlaceHoteRecommendByPlaceId",placeId);
	}
}
