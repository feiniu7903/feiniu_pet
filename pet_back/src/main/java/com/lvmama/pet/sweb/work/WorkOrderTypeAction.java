package com.lvmama.pet.sweb.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.work.WorkDepartment;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.service.work.WorkDepartmentService;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.OptionItem;

@Namespace("/work/order_type")
@Results(value = {
		@Result(name = "list", location = "/WEB-INF/pages/back/work/work_order_type_list.jsp"),
		@Result(name = "add", location = "/WEB-INF/pages/back/work/work_order_type_add.jsp"),
		@Result(name = "edit", location = "/WEB-INF/pages/back/work/work_order_type_edit.jsp") })
public class WorkOrderTypeAction extends BackBaseAction {
	private static final long serialVersionUID = 1L;
	private WorkOrderTypeService workOrderTypeService;
	private WorkDepartmentService workDepartmentService;
	private WorkGroupService workGroupService;
	private String typeName;
	private String typeCode;
	private String departmentId;
	private String departmentId1;
	private String creatorComplete;
	private String limitCompleteTime;
	private String limitTime;
	private String limitReceiver;
	private String content;
	private String system;
	private String urlTemplate;
	private String receiverEditable;
	private String workGroupId;
	private List<WorkOrderType> workOrderTypeList;
	private List<WorkDepartment> departmentList;
	private List<WorkGroup> workGroupList;
	private List<WorkGroup> sendGroupList;
	private String workOrderTypeId;
	private String paramOrderId;
	private String paramProductId;
	private String paramUserName;
	private String sendGroupId;
	private String useAgent;
	private List<String> paramRequire;
	private String sysDistribute;
	
	@Action("list")
	public String list() {
		Map<String, Object> params = new HashMap<String, Object>();
		typeName = getRequestParameter("typeName");
		system = getRequestParameter("system");
		if (!StringUtil.isEmptyString(typeName)) {
			params.put("typeName", typeName);
		}
		if (!StringUtil.isEmptyString(system)) {
			params.put("system", system);
		}
		Long totalSize = workOrderTypeService.getWorkOrderTypeCount(params);
		Page<WorkOrderType> workOrderTypePage = new Page<WorkOrderType>();
		workOrderTypePage.setTotalResultSize(totalSize);
		workOrderTypePage.setCurrentPage(super.page);
		params.put("start", workOrderTypePage.getStartRows());
		params.put("end", workOrderTypePage.getEndRows());
		workOrderTypePage.buildUrl(getRequest());

		workOrderTypePage.setItems(workOrderTypeService
				.getWorkOrderTypePage(params));
		setRequestAttribute("workOrderTypePage", workOrderTypePage);
		return "list";
	}

	@Action("add")
	public String add() {
		initDepartmentsList();
		initWorkGroupsList();
		return "add";
	}

	@Action("save_workOrderType")
	public String saveWorkOrderType() {
	     WorkOrderType obj=new WorkOrderType();
	     obj.setTypeName(typeName);
	     obj.setTypeCode(typeCode);
	     if(StringUtils.isNotBlank(sendGroupId))
	     {
	    	 obj.setSendGroupId(Long.valueOf(sendGroupId));
	     }
	     obj.setCreatorDepartmentId(Long.valueOf(departmentId));
	     obj.setCreatorComplete(creatorComplete);
	     if(StringUtils.isNotBlank(system) && system.equals("true")){
	    	 obj.setLimitCompleteTime("true");
	     }else{
	    	 obj.setLimitCompleteTime("false");
	     }
	     if(!StringUtil.isEmptyString(limitTime)){
	    	 obj.setLimitTime(Long.valueOf(limitTime));
	     }
	     if(StringUtils.isNotBlank(useAgent) && useAgent.equals("true")){
	    	 obj.setUseAgent("true");
	     }else{
	    	 obj.setUseAgent("false");
	     }
	     obj.setLimitReceiver(limitReceiver);
	     obj.setContent(content);
	     obj.setSystem(system);
	     obj.setUrlTemplate(urlTemplate);
	     obj.setReceiverEditable(receiverEditable==null?"false":receiverEditable);
	     obj.setReceiverGroupId(Long.valueOf(workGroupId));
	     obj.setCreateTime(new Date());
	     if(CollectionUtils.isNotEmpty(paramRequire)){
	    	 if(paramRequire.contains("order_id")){
	    		 obj.setParamOrderId("true");
	    	 }else{
	    		 obj.setParamOrderId("false");
	    	 }
	    	 if(paramRequire.contains("product_id")){
	    		 obj.setParamProductId("true");
		     }else{
		    	 obj.setParamProductId("false");
	    	 }
	    	 if(paramRequire.contains("mobile_number")){
	    		 obj.setParamUserName("true");
		     }else{
		    	 obj.setParamUserName("false");
	    	 }
	     }
	     if(StringUtils.isNotBlank(sysDistribute) && sysDistribute.equals("true")){
	    	 obj.setSysDistribute("true");
	     }else{
	    	 obj.setSysDistribute("false");
	     }
	     workOrderTypeService.insert(obj);
	     return add();
	 /*	if(size>0){
			sendAjaxMsg("SUCCESS");
		}else{
			sendAjaxMsg("FAILED");
		}*/
	}
	@Action("edit")
	public String edit(){
		this.getRequest().setAttribute("workOrderTypeId", workOrderTypeId);
		WorkOrderType workOrderType=workOrderTypeService.getWorkOrderTypeById(Long.valueOf(workOrderTypeId));
		setRequestAttribute("workOrderType", workOrderType);
		departmentList = workDepartmentService.getAllWorkDepartment();
		departmentId=workOrderType.getCreatorDepartmentId().toString();	//发起部门
		departmentId1 = workGroupService.getWorkGroupById(workOrderType.getReceiverGroupId()).getWorkDepartmentId().toString();//接收部门
		workGroupId=workOrderType.getReceiverGroupId().toString();//接收组织
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workDepartmentId", departmentId1);
		workGroupList=workGroupService.getWorkGroupWithDepartment(params);
		params.put("workDepartmentId", departmentId);
		sendGroupList=workGroupService.getWorkGroupWithDepartment(params);
		sendGroupId=(workOrderType.getSendGroupId()==null?null:workOrderType.getSendGroupId().toString());
		return "edit";
	}
	@Action("edit_workOrderType")
	public void editWorkOrderType() {
	     WorkOrderType obj=workOrderTypeService.getWorkOrderTypeById(Long.valueOf(workOrderTypeId));
	     obj.setTypeName(typeName);
	     if(StringUtils.isNotBlank(sendGroupId))
	     {
	    	 obj.setSendGroupId(Long.valueOf(sendGroupId));
	     }else{
	    	 obj.setSendGroupId(null);
	     }
	     obj.setCreatorDepartmentId(Long.valueOf(departmentId));
	     obj.setCreatorComplete(creatorComplete);
	     if(StringUtils.isNotBlank(system) && system.equals("true")){
	    	 obj.setLimitCompleteTime("true");
	     }else{
	    	 obj.setLimitCompleteTime("false");
	     }
	     if(!StringUtil.isEmptyString(limitTime)){
	    	 obj.setLimitTime(Long.valueOf(limitTime));
	     }else{
	    	 obj.setLimitTime(null);
	     }
	     obj.setLimitReceiver(limitReceiver);
	     obj.setContent(content);
	     obj.setSystem(system);
	     if(StringUtils.isNotBlank(useAgent) && useAgent.equals("true")){
	    	 obj.setUseAgent("true");
	     }else{
	    	 obj.setUseAgent("false");
	     }
	     obj.setUrlTemplate(urlTemplate);
	     if(CollectionUtils.isNotEmpty(paramRequire)){
	    	 if(paramRequire.contains("order_id")){
	    		 obj.setParamOrderId("true");
	    	 }else{
	    		 obj.setParamOrderId("false");
	    	 }
	    	 if(paramRequire.contains("product_id")){
	    		 obj.setParamProductId("true");
		     }else{
		    	 obj.setParamProductId("false");
	    	 }
	    	 if(paramRequire.contains("mobile_number")){
	    		 obj.setParamUserName("true");
		     }else{
		    	 obj.setParamUserName("false");
	    	 }
	     }else{
	    	 obj.setParamOrderId("false");
	    	 obj.setParamProductId("false");
	    	 obj.setParamUserName("false");
	     }
	     obj.setReceiverEditable(receiverEditable==null?"false":receiverEditable);
	     obj.setReceiverGroupId(Long.valueOf(workGroupId));
	     if(StringUtils.isNotBlank(sysDistribute) && sysDistribute.equals("true")){
	    	 obj.setSysDistribute("true");
	     }else{
	    	 obj.setSysDistribute("false");
	     }
	     int size=workOrderTypeService.update(obj);
	 	if(size>0){
			sendAjaxMsg("SUCCESS");
		}else{
			sendAjaxMsg("FAILED");
		}
	}
	private void initDepartmentsList(){
		departmentList = workDepartmentService.getAllWorkDepartment();
		//departmentList.add(0, new WorkDepartment());
	}
	private void initWorkGroupsList(){
		if(departmentList!=null&&departmentList.size()>0){
			Long firstDepartmentId=departmentList.get(0).getWorkDepartmentId();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("workDepartmentId", firstDepartmentId);
			params.put("valid", "true");
			workGroupList=workGroupService.getWorkGroupWithDepartment(params);
			sendGroupList=workGroupService.getWorkGroupWithDepartment(params);
		}
	}
	@Action("changeGroupList")
	public void changeGroup(){
		   departmentId1=getRequestParameter("departmentId1");
		   Map<String, Object> params = new HashMap<String, Object>();
		   if (!StringUtil.isEmptyString(departmentId1)) {
			   params.put("workDepartmentId", departmentId1);
			   params.put("valid", "true");
			   workGroupList=workGroupService.getWorkGroupWithDepartment(params);
			}
			if(workGroupList != null && workGroupList.size() > 0){
				List<OptionItem> items = new ArrayList<OptionItem>();
				OptionItem all = new OptionItem();
				all.setValue("");
				all.setLabel("--请选择--");
				items.add(all);
				for(WorkGroup o : workGroupList){
					OptionItem item = new OptionItem();
					item.setValue(o.getWorkGroupId()+"");
					item.setLabel(o.getGroupName());
					items.add(item);
				}
				sendAjaxResultByJson(JSONArray.fromObject(items).toString());
			}
	}
	
	@Action("check_type_name")
	public void checkTypeName(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeNameCheck", getRequestParameter("typeName").trim());
		map.put("start",1);
		map.put("end", 1);
		List<WorkOrderType> typeList = workOrderTypeService.getWorkOrderTypePage(map);
		if(typeList!=null && typeList.size() > 0){
			sendAjaxMsg("false");
			return;
		}
		sendAjaxMsg("true");
	}
	@Action("check_type_code")
	public void checkTypeCode(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeCode", getRequestParameter("typeCode").trim());
		map.put("start",1);
		map.put("end", 1);
		List<WorkOrderType> typeList = workOrderTypeService.getWorkOrderTypePage(map);
		if(typeList!=null && typeList.size() > 0){
			sendAjaxMsg("false");
			return;
		}
		sendAjaxMsg("true");
	}
	@Action("check_type_name_edit")
	public void checkTypeNameEdit(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeNameCheck", getRequestParameter("typeName").trim());
		map.put("start",1);
		map.put("end", 1);
		List<WorkOrderType> typeList = workOrderTypeService.getWorkOrderTypePage(map);
		if(typeList!=null && typeList.size() > 0 && !typeList.get(0).getWorkOrderTypeId().equals(Long.parseLong(getRequestParameter("typeId")))){
			sendAjaxMsg("false");
			return;
		}
		sendAjaxMsg("true");
	}
	public WorkOrderTypeService getWorkOrderTypeService() {
		return workOrderTypeService;
	}

	public void setWorkOrderTypeService(
			WorkOrderTypeService workOrderTypeService) {
		this.workOrderTypeService = workOrderTypeService;
	}
	
	
	public WorkDepartmentService getWorkDepartmentService() {
		return workDepartmentService;
	}

	public void setWorkDepartmentService(WorkDepartmentService workDepartmentService) {
		this.workDepartmentService = workDepartmentService;
	}

	
	public WorkGroupService getWorkGroupService() {
		return workGroupService;
	}

	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public List<WorkOrderType> getWorkOrderTypeList() {
		return workOrderTypeList;
	}

	public void setWorkOrderTypeList(List<WorkOrderType> workOrderTypeList) {
		this.workOrderTypeList = workOrderTypeList;
	}

	public List<WorkDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<WorkDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public List<WorkGroup> getWorkGroupList() {
		return workGroupList;
	}

	public void setWorkGroupList(List<WorkGroup> workGroupList) {
		this.workGroupList = workGroupList;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getCreatorComplete() {
		return creatorComplete;
	}

	public void setCreatorComplete(String creatorComplete) {
		this.creatorComplete = creatorComplete;
	}

	public String getLimitCompleteTime() {
		return limitCompleteTime;
	}

	public void setLimitCompleteTime(String limitCompleteTime) {
		this.limitCompleteTime = limitCompleteTime;
	}

	public String getLimitReceiver() {
		return limitReceiver;
	}

	public void setLimitReceiver(String limitReceiver) {
		this.limitReceiver = limitReceiver;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrlTemplate() {
		return urlTemplate;
	}
	public void setUrlTemplate(String urlTemplate) {
		this.urlTemplate = urlTemplate;
	}

	public String getReceiverEditable() {
		return receiverEditable;
	}
	public void setReceiverEditable(String receiverEditable) {
		this.receiverEditable = receiverEditable;
	}

	public String getWorkGroupId() {
		return workGroupId;
	}
	public void setWorkGroupId(String workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	public String getWorkOrderTypeId() {
		return workOrderTypeId;
	}

	public void setWorkOrderTypeId(String workOrderTypeId) {
		this.workOrderTypeId = workOrderTypeId;
	}

	public String getDepartmentId1() {
		return departmentId1;
	}

	public void setDepartmentId1(String departmentId1) {
		this.departmentId1 = departmentId1;
	}

	public String getParamOrderId() {
		return paramOrderId;
	}

	public void setParamOrderId(String paramOrderId) {
		this.paramOrderId = paramOrderId;
	}

	public String getParamProductId() {
		return paramProductId;
	}

	public void setParamProductId(String paramProductId) {
		this.paramProductId = paramProductId;
	}

	public String getParamUserName() {
		return paramUserName;
	}

	public void setParamUserName(String paramUserName) {
		this.paramUserName = paramUserName;
	}

	public String getSendGroupId() {
		return sendGroupId;
	}

	public void setSendGroupId(String sendWorkGroupId) {
		this.sendGroupId = sendWorkGroupId;
	}

	public List<WorkGroup> getSendGroupList() {
		return sendGroupList;
	}

	public String getUseAgent() {
		return useAgent;
	}

	public void setUseAgent(String useAgent) {
		this.useAgent = useAgent;
	}

	public List<String> getParamRequire() {
		return paramRequire;
	}

	public void setParamRequire(List<String> paramRequire) {
		this.paramRequire = paramRequire;
	}

	public String getSysDistribute() {
		return sysDistribute;
	}

	public void setSysDistribute(String sysDistribute) {
		this.sysDistribute = sysDistribute;
	}
}
