package com.lvmama.market.service;

import com.lvmama.comm.bee.po.market.TaobaoProductDetail;
import com.lvmama.comm.bee.service.market.TaobaoProductDetailService;
import com.lvmama.market.dao.TaobaoProductDetailDAO;
/**
 * 上传聚划算商品详细信息
 * @author zhushuying
 *
 */
public class TaobaoProductDetailServiceImpl implements TaobaoProductDetailService {
	private TaobaoProductDetailDAO taobaoProductDetailDAO;

	@Override
	public TaobaoProductDetail selectDetailProductById(Long id) {
		return taobaoProductDetailDAO.selectDetailProdProductById(id);
	}

	@Override
	public Long addProductDetail(TaobaoProductDetail tpd) {
		return taobaoProductDetailDAO.addProductDetail(tpd);
	}

	public int updateProductDetail(TaobaoProductDetail tpd) {
		return taobaoProductDetailDAO.updateProductDetail(tpd);
	}
	
	public void setTaobaoProductDetailDAO(
			TaobaoProductDetailDAO taobaoProductDetailDAO) {
		this.taobaoProductDetailDAO = taobaoProductDetailDAO;
	}
}
