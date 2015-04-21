package com.lvmama.tnt.partner.hotel.service;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.prod.vo.ProductRequestVo;
import com.lvmama.vst.api.hotel.prod.vo.ProductVo;
import com.lvmama.vst.api.vo.PageVo;

/**
 * 酒店产品接口 当供应商入库或供应商直接调用使用
 * 
 * @author gaoyafeng
 * 
 */
public interface ProductService {
	/**
	 * 根据ID查询产品详情
	 * 
	 * @param prodId
	 *            产品ID
	 * @return 产品详情
	 */
	public ResponseVO<ProductVo> findProductDetail(
			RequestVO<Long> prodIdInfo);

	/**
	 * 根据分页参数查询产品信息分页列表
	 * 
	 * @param pageParam
	 *            产品分页对象
	 * @param productRequestVo
	 *            产品查询对象 （可为空）
	 * @return 产品分页列表
	 */
	public ResponseVO<PageVo<ProductVo>> findProductList(
			RequestVO<ProductRequestVo> productRequestVo,
			PageVo<ProductVo> pageParam);
}
