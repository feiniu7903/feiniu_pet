package com.lvmama.market.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.market.TaobaoProduct;
import com.lvmama.comm.bee.service.market.TaobaoProductService;
import com.lvmama.market.dao.TaobaoProductDAO;

public class TaobaoProductServiceImpl implements TaobaoProductService {
	private TaobaoProductDAO taobaoProductDAO;

	public Integer selectRowCount(Map<String, Object> searchConds) {
		return taobaoProductDAO.selectRowCount(searchConds);
	}

	public List<TaobaoProduct> selectProductByParms(Map<String, Object> map) {
		return taobaoProductDAO.selectProductByParms(map);
	}

	@Override
	public TaobaoProduct selectProductById(Long interfaceId) {
		return taobaoProductDAO.selectProdProductById(interfaceId);
	}

	@Override
	public void addProduct(TaobaoProduct tp) {
		taobaoProductDAO.insert(tp);	
	}
	
	@Override
	public int updateProduct(Map<String, Object> map) {
		return taobaoProductDAO.updateByParam(map);
	}
	
	public void setTaobaoProductDAO(TaobaoProductDAO taobaoProductDAO) {
		this.taobaoProductDAO = taobaoProductDAO;
	}
	
}
