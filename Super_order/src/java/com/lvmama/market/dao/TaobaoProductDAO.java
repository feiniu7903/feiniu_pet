package com.lvmama.market.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.market.TaobaoProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;

public class TaobaoProductDAO extends BaseIbatisDAO {

	public Integer selectRowCount(Map<String, Object> searchConds) {
		Integer count = 0;
		count = (Integer) super.queryForObject("TAOBAO_PRODUCT.selectRowCount",
				searchConds);
		return count;
	}

	public List<TaobaoProduct> selectProductByParms(Map<String, Object> map) {
		List<TaobaoProduct> rstList = super.queryForList(
				"TAOBAO_PRODUCT.selectByParam", map);
		for (TaobaoProduct tp : rstList) {
			Long productId = tp.getProductId();
			if (null != productId) {
				map.clear();
				map.put("productId", productId);
				ProdProduct pp = (ProdProduct) super.queryForObject(
						"TAOBAO_PRODUCT.getProdProductById", map);
				if (null != pp) {
					tp.setProdProduct(pp);
				}
			}
		}
		return rstList;
	}

	public TaobaoProduct selectProdProductById(Long interfaceId) {
		TaobaoProduct prod = (TaobaoProduct) super.queryForObject(
				"TAOBAO_PRODUCT.getProductByInterfaceId", interfaceId);
		return prod;
	}

	public void insert(TaobaoProduct tp) {
		super.insert("TAOBAO_PRODUCT.insert", tp);
	}
	
	public int updateByParam(Map<String, Object> map) {
		return super.update("TAOBAO_PRODUCT.updateByParam", map);
	}
}