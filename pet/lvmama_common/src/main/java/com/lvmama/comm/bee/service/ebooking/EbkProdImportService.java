package com.lvmama.comm.bee.service.ebooking;

import com.lvmama.comm.vo.Constant.EBK_PRODUCT_VIEW_TYPE;

/**
 * Super产品数据导入ebk系统
 * 
 * @author taiqichao
 * 
 */
public interface EbkProdImportService {

	
	/**
	 * 批量从Super产品库导入ebk系统,支持新增和修改
	 * @param supplierId 供应商ID
	 * @param ebkProductViewType 产品大类
	 * @throws Exception
	 */
	public void importProductsByProductType(Long supplierId,EBK_PRODUCT_VIEW_TYPE ebkProductViewType) throws Exception;
	
	/**
	 * 同步单个产品
	 * 
	 * @param ebkProductId
	 *            ebk产品id
	 */
	public void importProduct(Long ebkProductId) throws Exception;
	
	/**
	 * 单个从Super产品库导入ebk系统
	 * @param metaProductId 采购产品id
	 * @throws Exception
	 */
	public void importMetaProduct(Long metaProductId) throws Exception;
	
	/**
	 * 单个从Super产品库导入ebk系统
	 * @param prodProductId 销售产品id
	 * @throws Exception
	 */
	public void importProdProduct(Long prodProductId) throws Exception;

}
