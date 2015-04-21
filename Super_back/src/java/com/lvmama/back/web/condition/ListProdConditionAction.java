package com.lvmama.back.web.condition;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComCondition;

public class ListProdConditionAction extends ListConditionAction {
	
	private List<ComCondition> metaConditionList=new ArrayList<ComCondition>();
	
	public void doBefore() throws Exception {
		super.doBefore();
		if(objectType.equals("PROD_PRODUCT")){
			metaConditionList = conditionService.getMetaConditionByProduct(objectId);
		}
	}

	public List<ComCondition> getMetaConditionList() {
		return metaConditionList;
	}

}
