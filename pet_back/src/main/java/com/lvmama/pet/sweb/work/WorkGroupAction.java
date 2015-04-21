package com.lvmama.pet.sweb.work;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.work.WorkDepartment;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.service.work.WorkDepartmentService;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;

@Namespace("/work/group")
@Results(value={
		@Result(name="list",location="/WEB-INF/pages/back/work/work_group_list.jsp"),
		@Result(name="add",location="/WEB-INF/pages/back/work/work_group_add.jsp"),
		@Result(name="edit",location="/WEB-INF/pages/back/work/work_group_edit.jsp")
})
public class WorkGroupAction extends BackBaseAction{
	private static final long serialVersionUID = 1L;
	private WorkGroupService workGroupService;
	private WorkDepartmentService workDepartmentService;
	private String departmentId;
	private String groupName;
	private String groupDesc;
	private String valid;
	private String groupType;
	private String workGroupId;
	private String permUserName;
	private List<WorkDepartment> departmentList;
	@Action("list")
	public String list(){
		Map<String, Object> params = new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(departmentId)){
			Long id=Long.valueOf(departmentId);
			params.put("workDepartmentId", id);
		}
		if(!StringUtil.isEmptyString(groupName)){
			params.put("groupName", groupName);
		}
		params.put("permUserName", permUserName);
		Long totalSize=workGroupService.getWorkGroupPageCount(params);
		Page<WorkGroup> workGroupPage = new Page<WorkGroup>();
		workGroupPage.setTotalResultSize(totalSize);
		workGroupPage.setCurrentPage(super.page);
		params.put("start", workGroupPage.getStartRows());
		params.put("end", workGroupPage.getEndRows());
		workGroupPage.buildUrl(getRequest());
		
		workGroupPage.setItems(workGroupService.getWorkGroupPage(params));
		setRequestAttribute("workGroupPage", workGroupPage);
		initDepartmentsList();
		return "list";
	}
	@Action("add")
	public String add(){
		initDepartmentsList();
		return "add";
	}
	@Action("save_group")
	public void saveGroup(){
		Map<String, Object> params = new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(departmentId)){
			Long id=Long.valueOf(departmentId);
			params.put("workDepartmentId", id);
		}
		List<WorkGroup> groups=workGroupService.queryWorkGroupByParam(params);
		for(WorkGroup group:groups){
			 if(group.getGroupName().equalsIgnoreCase(groupName)){
				 sendAjaxMsg("EXIST");
				 return;
			 }
		}
		WorkGroup workGroup=new WorkGroup();
		workGroup.setWorkDepartmentId(Long.valueOf(departmentId));
		workGroup.setGroupName(groupName);
		workGroup.setMemo(groupDesc);
		workGroup.setValid("true");
		workGroup.setGroupType(groupType);
		workGroup.setCreateTime(new Date());
		Long size=workGroupService.insert(workGroup);
		if(size>0){
			sendAjaxMsg("SUCCESS");
		}else{
			sendAjaxMsg("FAILED");
		}
	}
	@Action("edit")
	public String edit(){
		workGroupId=getRequestParameter("workGroupId");
		WorkGroup workGroup=workGroupService.getWorkGroupById(Long.valueOf(workGroupId));
		this.getRequest().setAttribute("workGroupId", workGroupId);
		setRequestAttribute("workGroup", workGroup);
		departmentId=workGroup.getWorkDepartmentId().toString();
		initDepartmentsList();
		return "edit";
	}
	@Action("edit_group")
	public void editGroup(){
		WorkGroup workGroup=new WorkGroup();
		workGroup.setWorkGroupId(Long.valueOf(workGroupId));
		workGroup.setWorkDepartmentId(Long.valueOf(departmentId));
		workGroup.setGroupName(groupName);
		workGroup.setMemo(groupDesc);
		workGroup.setValid(valid);
		workGroup.setGroupType(groupType);
		Map<String, Object> params = new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(departmentId)){
			Long id=Long.valueOf(departmentId);
			params.put("workDepartmentId", id);
		}
		List<WorkGroup> groups=workGroupService.queryWorkGroupByParam(params);
		for(int i=0;i<groups.size();i++){
			if(groups.get(i).getWorkGroupId().equals(Long.valueOf(workGroupId))){
				groups.remove(i);
				continue;
			}
			if(groups.get(i).getGroupName().equalsIgnoreCase(groupName)){
				 sendAjaxMsg("EXIST");
				 return;
			 }
		}
		int res=workGroupService.updateWorkGroup(workGroup);
		if(res>0){
			sendAjaxMsg("SUCCESS");
		}else{
			sendAjaxMsg("FAILED");
		}
	}
	private void initDepartmentsList(){
		departmentList = workDepartmentService.getAllWorkDepartment();
	}

	public List<WorkDepartment> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<WorkDepartment> departmentList) {
		this.departmentList = departmentList;
	}
	public WorkGroupService getWorkGroupService() {
		return workGroupService;
	}
	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}
	
	public WorkDepartmentService getWorkDepartmentService() {
		return workDepartmentService;
	}

	public void setWorkDepartmentService(WorkDepartmentService workDepartmentService) {
		this.workDepartmentService = workDepartmentService;
	}

	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getWorkGroupId() {
		return workGroupId;
	}
	public void setWorkGroupId(String workGroupId) {
		this.workGroupId = workGroupId;
	}
	public String getPermUserName() {
		return permUserName;
	}
	public void setPermUserName(String permUserName) {
		this.permUserName = permUserName;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	
}
