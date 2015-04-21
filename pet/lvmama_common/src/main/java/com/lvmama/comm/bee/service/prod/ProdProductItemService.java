package com.lvmama.comm.bee.service.prod;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdProductItem;

public interface ProdProductItemService {
	
	/**
	 * 获取销售产品组成明细
	 * @param product
	 */
	List<ProdProductItem> getProductItems(Long productId);

	/**
	 * 添加一个销售产品子项
	 * @param productItem
	 */
	void addProductItem(ProdProductItem productItem,String operatorName);
	
	/**
	 * 删除一个销售产品子项
	 * @param productItemId
	 */
	void deleteProductItem(Long productId, Long productItemId,String productName,String operatorName);
	
	/**
	 * 
	 * @param productItem
	 */
	void updateProductItem(ProdProductItem productItem);
	
	/**
	 * 判断一个销售产品子项是否存在
	 * @param productId
	 * @param metaProductId
	 * @return
	 */
	boolean existsProductItem(Long productId,Long metaProductId);
	 
	/**
	 * 查询采购产品比销售产品时间价格表少的日期列表
	 * @param productItem
	 * @return
	 */
	List<Date> checkTimePrice(ProdProductItem productItem);
	
	/**
	 * 查找采购产品的销售产品
	 * 
	 * @param metaProductId
	 * @return
	 */
	public List<ProdProductItem> selectProdProductByMetaId(Long metaProductId);
}
