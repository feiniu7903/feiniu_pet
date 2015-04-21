package com.lvmama.back.web.condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.com.ConditionService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComCondition;

public class ListConditionAction extends BaseAction {
	
	private List<ComCondition> conditionList=new ArrayList<ComCondition>();
	
	protected ConditionService conditionService;
	
	protected Long objectId;
	protected String objectType;
	
	private ComCondition condition;
	
	public void doBefore() throws Exception {
		newCondition();
		refreshCondition();
	}
	
	private void refreshCondition() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objectId", objectId);
		params.put("objectType", objectType);
		conditionList=conditionService.getConditionByObjectId(params);
	}
	
	private void newCondition() {
		condition = new ComCondition();
		condition.setObjectId(objectId);
		condition.setObjectType(objectType);
	}
	
	public void save() {
		if (condition.getConditionId()==null) {
			conditionList.add(condition);
			conditionService.addCondition(condition);
		}else{
			if(conditionService.getConditionByConditionId(condition.getConditionId()) == null) {
				conditionList.add(condition);
				conditionService.addCondition(condition);
			}else{
				conditionList.remove(condition);
				conditionList.add(condition);
				conditionService.updateCondition(condition);
			}
		}
		newCondition();
		super.refreshComponent("refresh");
		super.refreshComponent("edit");
	}
	
	public void remove(Long conditionId) {
		ComCondition cond = new ComCondition();
		cond.setConditionId(conditionId);
		conditionList.remove(cond);
		conditionService.delCondition(cond);
		super.refreshComponent("refresh");
	}
	
	public void edit(Long conditionId) {
		this.condition = conditionService.getConditionByConditionId(conditionId);
		super.refreshComponent("edit");
	}
	
	public ComCondition getCondition() {
		return condition;
	}
	
	public List<ComCondition> getConditionList() {
		return conditionList;
	}
	
	public List<CodeItem> getConditionTypeList() {
		return CodeSet.getInstance().getCodeList("CONDITION_TYPE");
	}
	
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}
