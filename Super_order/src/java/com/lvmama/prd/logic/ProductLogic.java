package com.lvmama.prd.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComConditionDAO;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductItem;
import com.lvmama.comm.pet.po.pub.ComCondition;
import com.lvmama.prd.dao.ProdProductItemDAO;

public class ProductLogic {

	private ProdProductItemDAO prodProductItemDAO;
	private ComConditionDAO comConditionDAO;
	

	public List<ComCondition> getAllConditionsByProduct(List<ProdProduct> products) {
		List<ComCondition> conditionList = new ArrayList<ComCondition>();
		for(int i=0;i<products.size();i++) {
			Long productId = products.get(i).getProductId();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("objectId", productId);
			params.put("objectType", "PROD_PRODUCT");
			conditionList.addAll(comConditionDAO.selectConditionByObjectId(params));
			conditionList.addAll(getMetaProductConditionsByProductId(productId));
		}
		return conditionList;
	}
	
	public List<ComCondition> getMetaProductConditionsByProductId(Long productId) {
		List<ComCondition> conditionList = new ArrayList<ComCondition>();
		List<ProdProductItem> list = prodProductItemDAO.selectProductItems(productId);
		for(int i=0;i<list.size();i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("objectId", list.get(i).getMetaProductId());
			params.put("objectType", "META_PRODUCT");
			conditionList.addAll(comConditionDAO.selectConditionByObjectId(params));
		}
		return conditionList;
	}
	

	public void setProdProductItemDAO(ProdProductItemDAO prodProductItemDAO) {
		this.prodProductItemDAO = prodProductItemDAO;
	}

	public void setComConditionDAO(ComConditionDAO comConditionDAO) {
		this.comConditionDAO = comConditionDAO;
	}	
}
