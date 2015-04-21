package com.lvmama.back.sweb.prod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.service.com.ConditionService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComCondition;
import com.lvmama.comm.utils.json.JSONResult;


@Results({
	@Result(name="input",location="/WEB-INF/pages/back/prod/prod_condition.jsp"),
	@Result(name="prod_condition_list",location="/WEB-INF/pages/back/prod/prod_condition_list.jsp")
})
public class ProdConditionAction extends ProductAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6196631614565381292L;
	private List<ComCondition> metaConditionList;
	private List<ComCondition> conditionList;
	protected ConditionService conditionService;
	private Long objectId;
	private String objectType;
	private ComCondition condition;
	private boolean all=true;
	@Override
	@Action(value="/prod/editProdCondition")
	public String goEdit() {
		if(all){
			metaConditionList = conditionService.getMetaConditionByProduct(objectId);			
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objectId", objectId);
		params.put("objectType", objectType);
		conditionList=conditionService.getConditionByObjectId(params);
		if(all){
			return goAfter();
		}else{
			return "prod_condition_list";
		}
	}
	@Action("/prod/deleteCondition")
	public void deleteCondition(){
		JSONResult result=new JSONResult(getResponse());		
		Assert.notNull(condition);
		conditionService.delCondition(condition);		
		result.output();
	}

	@Override
	@Action("/prod/saveCondition")
	public void save() {
		JSONResult result=new JSONResult(getResponse());
		
		if(StringUtils.isEmpty(condition.getConditionType())){
			result.raise("提示类型不可以为空").output();
			return;
		}
		if(condition.getBeginTime()==null){
			result.raise("开始时间不可以为空").output();
			return;
		}
		if(condition.getEndTime()==null){
			result.raise("结束时间不可以为空").output();
			return;
		}
		if(StringUtils.isEmpty(condition.getContent())){
			result.raise("内容不可以为空").output();
			return;
		}
		if(condition.getBeginTime().after(condition.getEndTime())){
			result.raise("有效时错误").output();
			return;
		}
		boolean hasNew=true;//新增加的标记
		if(condition.getConditionId()!=null){
			if(conditionService.getConditionByConditionId(condition.getConditionId())!=null){
				conditionService.updateCondition(condition);
				hasNew=false;
			}
		}
		
		if(hasNew){
			conditionService.addCondition(condition);
		}
		result.put("hasNew", hasNew);
		
		result.output();
	}

	/**
	 * @return the metaConditionList
	 */
	public List<ComCondition> getMetaConditionList() {
		return metaConditionList;
	}

	/**
	 * @param conditionService the conditionService to set
	 */
	public void setConditionService(ConditionService conditionService) {
		this.conditionService = conditionService;
	}

	/**
	 * @return the conditionList
	 */
	public List<ComCondition> getConditionList() {
		return conditionList;
	}

	public List<CodeItem> getConditionTypeList() {
		return CodeSet.getInstance().getCodeListAndBlank("CONDITION_TYPE");
	}

	/**
	 * @return the objectType
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the condition
	 */
	public ComCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(ComCondition condition) {
		this.condition = condition;
	}
	/**
	 * @param all the all to set
	 */
	public void setAll(boolean all) {
		this.all = all;
	}
	
	
}
