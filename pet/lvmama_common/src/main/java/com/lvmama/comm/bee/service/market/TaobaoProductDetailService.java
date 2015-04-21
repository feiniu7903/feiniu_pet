package com.lvmama.comm.bee.service.market;

import java.util.Map;

import com.lvmama.comm.bee.po.market.TaobaoProduct;
import com.lvmama.comm.bee.po.market.TaobaoProductDetail;
/**
 * 上传淘宝商品详细信息
 * @author zhushuying
 *
 */
public interface TaobaoProductDetailService {
	/**
	 * 根据上传淘宝商品接口id查询上传淘宝商品详细信息
	 * @param id
	 * @return
	 */
	public TaobaoProductDetail selectDetailProductById(Long id);
	
	/**
	 * 添加产品的详细信息
	 * @param tpd
	 */
	public Long addProductDetail(TaobaoProductDetail tpd);
	/**
	 * 修改信息
	 * @param tpd
	 * @return
	 */
	public int updateProductDetail(TaobaoProductDetail tpd);
}
