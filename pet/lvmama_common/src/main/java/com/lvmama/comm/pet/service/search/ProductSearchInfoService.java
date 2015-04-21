package com.lvmama.comm.pet.service.search;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductPlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.search.Shantou;
import com.lvmama.comm.pet.vo.ProductList;

public interface ProductSearchInfoService {
	/**
	 * 查询productSearchInfo
	 * @param param 查询条件
	 * @return 符合查询条件的productSearchInfo列表
	 * <p>根据查询条件来获得相关的productSearchInfo列表，当查询条件中含有标地的查询条件时，则先会从productProductPlace中获取相关标地信息，然后再查询。</p>
	 */
	List<ProductSearchInfo> queryProductSearchInfoByParam(Map<String, Object> param);
	
	/**
	 * 查询productSearchInfo的数量
	 * @param param 查询条件
	 * @return 数量
	 * <p>根据查询条件来获得相关的productSearchInfo列表，当查询条件中含有标地的查询条件时，则先会从productProductPlace中获取相关标地信息，然后再查询。</p>
	 */
	Long countProductSearchInfoByParam(Map<String, Object> param);

	/**
	 * 批量修改PLACE关联产品的SEQ
	 * 
	 * @param placePhoto
	 * @throws SQLException 
	 */
	void batchSaveProductSeq(String placeProductIds);
	/**
	 * 
	 * @param ProductSearchInfo
	 * @return
	 */
	List<ProductSearchInfo> getProductByFromPlaceIdAndDestId(Map<String,Object> param);
	/**
	 * 数量
	 * @param ProductSearchInfo
	 * @return
	 */
	long countProductByFromPlaceIdAndDestId(Map<String,Object> param);
	/**
	 * 查找酒店的自由行产品.
	 */
	List<ProductSearchInfo> selectFreenessProductsOfHotel(Long hotelPlaceId);
	/**
	 * 取一个酒店下面有多少产品，每个产品下面有哪些类别的房型；
	 * 
	 * @param placeId
	 * @param stage
	 * @param size
	 * @param channel
	 * @param reverse
	 * @return
	 */
	List<ProductSearchInfo> getProductHotelByPlaceIdAndType(long placeId,int size, String channel, boolean reverse);
	
	/**
	 * 查找标的下包含的产品
	 * 
	 * @author yuzhibing
	 * @param pageSize
	 *            每页显示数量
	 * @param currentPage
	 *            当前页
	 * @param placeId
	 *            placeId
	 * @param isTicket
	 *            isTicket
	 * @param stage
	 *            stage
	 * @return
	 */
	List<ProductSearchInfo> getProductByPlaceIdAndType(long pageSize, long currentPage, long placeId, String isTicket, String stage, String channel);
	/**
	 * 门票取的是类别产品的类型,各个类型混合在一起.
	 * 目前发现前台只用到门票和自由行；如果用到hotel和route等在这里添加
	 * 
	 * @param placeId
	 * @param size
	 * @param channel
	 * @return
	 */
	ProductList getIndexProductByPlaceIdAnd4TypeAndTicketBranch(long placeId, long size, String channel);
	
	/**
	 * @param placeId
	 * @param size
	 * @param channel
	 * @return
	 */
	ProductList getProductByPlaceIdAnd4Type(long placeId, long size, String channel);
	
	/**
	 * 根据当前产品查询用户可能喜欢的产品 取同地区下的同类型产品
	 * 
	 * @param productId
	 *            产品ID
	 * @param limitRows
	 *            显示记录条数
	 * @return
	 */
	List<ProductSearchInfo> getEnjoyProductList(Long productId, Long limitRows);
	
	
	/**
	 * 取容器产品
	 */
	List<ProductSearchInfo> getContainerProductList(String containerCode, Long fromPlaceId, String toPlaceId, String productType, String[] subProductType, int startRow, int endRow);
	/**
	 * 根据订单数量获取前多少条的产品列表
	 * @param fromPlaceId
	 * @param productType
	 * @param subProductType
	 * @param topLimit
	 * @return
	 */
	List<ProductSearchInfo> selectTopSalesList(Long fromPlaceId,String[] subProductType, int topLimit);
	/**
	 * 更新所有点评数量
	 */
	public void updateProductSearchInfoCmtNum();

	/**
	 * 闪投 产品数量

	 * @return
	 */
	Long countSelectShantouListByParam(Map<String, Object> parameters);

	/**
	 * 闪投 产品列表

	 * @return
	 */
	List<Shantou> selectShantouListByParam(Map<String, Object> param);
	
	/**
	 * 根据产品ID查询ProductSearchInfo
	 * @param productId
	 * @return
	 */
	ProductSearchInfo queryProductSearchInfoByProductId(Long productId);
	/**
	 * 查询类别索引表通过类别主键
	 * @return
	 */
	ProdBranchSearchInfo getProdBranchSearchInfoByBranchId(Long branchId);

	/**
	 *  通过placeid ，产品类型， 产品渠道获取 子产品信息
	 * @author nixianjun
	 * @param placeId
	 * @param isTicket
	 * @param channel
	 * @return
	 */
	List<ProdBranchSearchInfo> getProdBranchSearchInfoByParam(long placeId,
			String isTicket, String channel);
	/**
	 * 通过placeid,获取酒店房型介绍信息
	 * @param placeId
	 * @return
	 */
	List<PlaceHotelRoom> getPlaceHotelRoom(Long placeId);
	/**
	 * 通过placeid,获取酒店公告及一句话推荐
	 * @param placeId
	 * @return
	 */
	List<PlaceHotelNotice> getPlaceHotelNotice(Long placeId);
	/**
	 * 通过placeid,获取酒店的特色服务与玩法介绍
	 * @param placeId
	 * @return
	 */
	List<PlaceHotelOtherRecommend> getPlaceHotelRecommend(Long placeId);
	
	/**
	 * 通过PlaceId 与 销售渠道 获取 产品
	 * @param hotelPlaceId
	 * @param channel
	 * @return
	 */
	public List<ProductSearchInfo> selectProductsOfHotel(Long hotelPlaceId,String channel);
	/**
	 * 通过PlaceId 与 销售渠道 获取单房型
	 * @param placeId
	 * @param channel
	 * @param size
	 * @return
	 */
	public List<ProductSearchInfo> queryProductBranchByPlaceId(Long placeId,String channel,int size);

	/**
	 * 2345根据参数查询ProductPlaceSearchInfo（景点门票表）
	 * @param param  Map<String, Object> param
	 * @return
	 */
	
	List<ProductPlaceSearchInfo> queryPlaceProdctSearchInfoByParam(Map<String,Object> parameters);
	
	/**
	 * 2345景点产品总数量
	 * @return
	 */
	Long queryPlaceProdctSearchInfoByCount();
	

}
