package com.lvmama.pet.sweb.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.OptionItem;

@Namespace("/work/group/user")
@Results(value={
		@Result(name="list",location="/WEB-INF/pages/back/work/work_group_user_list.jsp"),
		@Result(name="to_add",location="/WEB-INF/pages/back/work/work_group_user_add.jsp")
})
public class WorkGroupUserAction extends BackBaseAction{
	private WorkGroupService workGroupService;
	private WorkGroupUserService workGroupUserService;
	private PermOrganizationService permOrganizationService;
	private PermUserService permUserService;
	
	private String departmentId;
	private String departmentId2;
	private String departmentId3;
	private String realName;
	private String userName;
	private List<PermOrganization> departmentList;
	private List<PermOrganization> departmentList2;
	private List<PermOrganization> departmentList3;
	@Action("list")
	public String list(){
		Long id = Long.parseLong(getRequestParameter("workGroupId"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workGroupId", id);
		map.put("start", 1);
		map.put("end", 1);
		setRequestAttribute("group", workGroupService.getWorkGroupPage(map).get(0));
		Page<WorkGroupUser> userPage = new Page<WorkGroupUser>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workGroupId", id);
		params.put("valid", "true");
		userPage.setTotalResultSize(workGroupUserService.getWorkGroupUserPageCount(params));
		userPage.buildUrl(getRequest());
		userPage.setCurrentPage(super.page);
		params.put("start", userPage.getStartRows());
		params.put("end", userPage.getEndRows());
		userPage.setItems(workGroupUserService.getWorkGroupUserPage(params));
		setRequestAttribute("userPage", userPage);
		setRequestAttribute("workGroupId", id);
		return "list";
	}
	
	@Action("to_add")
	public String toAdd(){
		Page<WorkGroupUser> userPage = new Page<WorkGroupUser>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workGroupId", getRequestParameter("workGroupId"));
		if(!StringUtil.isEmptyString(userName)){
			params.put("userName", userName);
		}
		if(!StringUtil.isEmptyString(realName)){
			params.put("realName", realName);
		}
		if(!"0".equals(departmentId)){
			params.put("departmentId", departmentId);
		}
		if(StringUtils.isNotBlank(departmentId2)){
			params.put("orgId", departmentId2);
		}
		if(StringUtils.isNotBlank(departmentId3)){
			params.put("orgId", departmentId3);
		}
		userPage.setTotalResultSize(workGroupUserService.getPermUserPageCount(params));
		userPage.buildUrl(getRequest());
		userPage.setCurrentPage(super.page);
		params.put("start", userPage.getStartRows());
		params.put("end", userPage.getEndRows());
		userPage.setItems(workGroupUserService.getPermUserPage(params));
		setRequestAttribute("userPage", userPage);
		setRequestAttribute("workGroupId", getRequestParameter("workGroupId"));
		initDepartmentsList();
		return "to_add";
	}
	
	@Action("save_group_user")
	public void saveGroupUser(){
		String[] arr = getRequestParameter("ids").split(",");
		Long[] ids = new Long[arr.length]; 
		for(int i=0;i<arr.length;i++){
			ids[i] = Long.parseLong(arr[i]);
		}
		workGroupUserService.insertGroupUser(Long.parseLong(getRequestParameter("workGroupId")),ids);
		sendAjaxMsg("SUCCESS");
	}
	
	@Action("delete_group_user")
	public void deleteGroupUser(){
		String[] arr = getRequestParameter("ids").split(",");
		Long[] ids = new Long[arr.length]; 
		for(int i=0;i<arr.length;i++){
			ids[i] = Long.parseLong(arr[i]);
		}
		workGroupUserService.updateGroupUserValid(ids,"false");
		sendAjaxMsg("SUCCESS");
	}
	
	@Action("set_leader")
	public void setLeader(){
		workGroupUserService.setLeader(Long.parseLong(getRequestParameter("workGroupId")),Long.parseLong(getRequestParameter("permUserId")),Boolean.parseBoolean(getRequestParameter("flag")));
		sendAjaxMsg("SUCCESS");
	}
	@Action("getChildOrgList")
	public void getChildOrgList(){
		List<OptionItem> items = new ArrayList<OptionItem>();
		if(departmentId!=null && !departmentId.equals("0")){
			departmentList = permOrganizationService.getChildOrgList(Long.parseLong(departmentId));
			OptionItem all = new OptionItem();
			all.setValue("");
			all.setLabel("--请选择--");
			items.add(all);
			for(PermOrganization po:departmentList){
				OptionItem item = new OptionItem();
				item.setValue(po.getOrgId()+"");
				item.setLabel(po.getDepartmentName());
				items.add(item);
			}
		}
		sendAjaxResultByJson(JSONArray.fromObject(items).toString());
	}
	private void initDepartmentsList(){
		departmentList = permOrganizationService.selectAllOrganization();
		PermOrganization temp1=new PermOrganization();
		temp1.setDepartmentName("--请选择--");
		departmentList.add(0, temp1);
		
		if(departmentId!=null && !departmentId.equals("0")){
			departmentList2 = permOrganizationService.getChildOrgList(Long.parseLong(departmentId));
			if(departmentList2!=null){
				PermOrganization temp=new PermOrganization();
				temp.setDepartmentName("--请选择--");
				departmentList2.add(0,temp);
			}
		}else{
			departmentList2=new ArrayList<PermOrganization>();
		}
		
		if(StringUtils.isNotBlank(departmentId2)){
			departmentList3 = permOrganizationService.getChildOrgList(Long.parseLong(departmentId2));
			if(departmentList3!=null){
				PermOrganization temp=new PermOrganization();
				temp.setDepartmentName("--请选择--");
				departmentList3.add(0,temp);
			}
		}else{
			departmentList3=new ArrayList<PermOrganization>();
		}
	}

	public WorkGroupService getWorkGroupService() {
		return workGroupService;
	}

	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}

	public WorkGroupUserService getWorkGroupUserService() {
		return workGroupUserService;
	}

	public void setWorkGroupUserService(WorkGroupUserService workGroupUserService) {
		this.workGroupUserService = workGroupUserService;
	}

	public PermOrganizationService getPermOrganizationService() {
		return permOrganizationService;
	}

	public void setPermOrganizationService(
			PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}

	public PermUserService getPermUserService() {
		return permUserService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<PermOrganization> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<PermOrganization> departmentList) {
		this.departmentList = departmentList;
	}

	public String getDepartmentId2() {
		return departmentId2;
	}

	public void setDepartmentId2(String departmentId2) {
		this.departmentId2 = departmentId2;
	}

	public String getDepartmentId3() {
		return departmentId3;
	}

	public void setDepartmentId3(String departmentId3) {
		this.departmentId3 = departmentId3;
	}

	public List<PermOrganization> getDepartmentList2() {
		return departmentList2;
	}

	public void setDepartmentList2(List<PermOrganization> departmentList2) {
		this.departmentList2 = departmentList2;
	}

	public List<PermOrganization> getDepartmentList3() {
		return departmentList3;
	}

	public void setDepartmentList3(List<PermOrganization> departmentList3) {
		this.departmentList3 = departmentList3;
	}
	
}
