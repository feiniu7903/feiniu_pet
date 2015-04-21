package com.lvmama.comm.bee.service.market;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.market.TaobaoProduct;

public interface TaobaoProductService {

	public Integer selectRowCount(Map<String, Object> searchConds);

	public List<TaobaoProduct> selectProductByParms(Map<String, Object> map);
	
	public TaobaoProduct selectProductById(Long interfaceId);
	
	public void addProduct(TaobaoProduct tp);
	
	public int updateProduct(Map<String, Object> map);
}
