package com.lvmama.shholiday.service;

import java.util.Date;
import java.util.Map;

import com.lvmama.comm.pet.vo.Page;
import com.lvmama.shholiday.response.ProductDetailResponse;
import com.lvmama.shholiday.vo.product.ProductInfo;
/**
 * 上航假期产品接口
 * @author yanzhirong
 *
 */
public interface ShHolidayProductService {
	/**
	 * 根据前台action的查询条件获得结果集
	 * @return
	 */
	public Page<ProductInfo> selectProductInfoByCondition(Long currentPage,Long pageSize,Map<String,Object> paramMap);
	
	/**
	 * action 的采购产品详情页面展示
	 */
	public ProductDetailResponse selectProductInfoById(Long productId);
	
	/**
	 * action 入库，针对从未入库过的供应商产品进行入库操作 已入库的更新操作
	 * @throws Exception 
	 */
	public void saveMetaProductForUnStocked(String productId);
	
	/**
	 * 上下线指定的产品
	 */
	public void onOfflineProduct(ProductInfo productInfo) throws Exception;
	
	/**
	 * 更新所有产品的时间价格库存
	 */
	public void updateAllProductTimePrices(Date startDate, Date endDate) throws Exception;
	
	/**
	 * 更新指定采购产品的时间价格库存
	 */
	public void updateProductTimePrice(String productId,String productType, Date startDate, Date endDate) throws Exception;

	/**
	 * 更新所有产品基本信息，价格
	 * @param startDate
	 * @param endDate
	 */
	public void updateAllProductInfo(Date startDate, Date endDate);

	/**
	 * 通知更新产品
	 * @param productId
	 */
	public void productNotify(String productId);
	
	
}
