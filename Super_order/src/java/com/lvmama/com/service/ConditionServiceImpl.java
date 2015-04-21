package com.lvmama.com.service;

import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComConditionDAO;
import com.lvmama.comm.bee.service.com.ConditionService;
import com.lvmama.comm.pet.po.pub.ComCondition;
import com.lvmama.prd.logic.ProductLogic;

public class ConditionServiceImpl implements ConditionService {
	
	private ComConditionDAO comConditionDAO;
	private ProductLogic productLogic;
	
	public void addCondition(ComCondition condition) {
		comConditionDAO.insert(condition);
	}

	public void updateCondition(ComCondition condition) {
		comConditionDAO.updateByPrimaryKey(condition);
	}
	
	public List<ComCondition> getConditionByObjectId(Map params) {
		return comConditionDAO.selectConditionByObjectId(params);
	}
	
	public ComCondition getConditionByConditionId(Long conditionId) {
		return comConditionDAO.selectByPrimaryKey(conditionId);	
	}
	
	public void delCondition(ComCondition cond) {
		ComCondition cc=comConditionDAO.selectByPrimaryKey(cond.getConditionId());
		if(cc!=null){
			comConditionDAO.deleteByPrimaryKey(cond);
		}
	}

	public List<ComCondition> getMetaConditionByProduct(Long productId) {
		return productLogic.getMetaProductConditionsByProductId(productId);
	}


	public void setComConditionDAO(ComConditionDAO comConditionDAO) {
		this.comConditionDAO = comConditionDAO;
	}

	public void setProductLogic(ProductLogic productLogic) {
		this.productLogic = productLogic;
	}

}
