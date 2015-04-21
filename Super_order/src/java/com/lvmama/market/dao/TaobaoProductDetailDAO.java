package com.lvmama.market.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.market.TaobaoProductDetail;
/**
 * 
 * @author zhushuying
 *
 */
public class TaobaoProductDetailDAO extends BaseIbatisDAO {
	
	public TaobaoProductDetail selectDetailProdProductById(Long productId) {
		TaobaoProductDetail detailProd = (TaobaoProductDetail) super.queryForObject("TAOBAO_PRODUCT_DETAIL.getDetailProdProductById", productId);
		return detailProd;
	}
	
	public Long addProductDetail(TaobaoProductDetail tpd) {
		return (Long) super.insert("TAOBAO_PRODUCT_DETAIL.insert", tpd);
	}
	
	public int updateProductDetail(TaobaoProductDetail tpd) {
		return super.update("TAOBAO_PRODUCT_DETAIL.updateDetailUpdateById", tpd);
	}
}