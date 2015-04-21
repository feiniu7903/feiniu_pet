package com.lvmama.pet.sweb.perm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.OptionItem;

/**
 * 组织管理
 * @author Jordan.Zhao
 **/
@Results(value={
	@Result(name="index",location="/WEB-INF/pages/back/perm/perm_organization_index.jsp"),
	@Result(name="save_organization",type="redirect",location="index.do")
	
})
@Namespace(value="/perm_organization")
public class PermOrganizationAction extends BackBaseAction {
	private PermOrganizationService permOrganizationService;
	
	private String orgId;
	private String departmentName;
	private String parentOrgLevel;
	private String parentOrgId;
	
	@Action("index")
	public String index(){
//		List<PermOrganization> departmentsList = permOrganizationService.selectAllOrganization();
//		renderer.refreshTree(tree, departmentsList, null);
//		departmentsList.add(0, new PermOrganization());
		
		setRequestAttribute("firstOrgList",permOrganizationService.getOrganizationByLevel(1));
		
		return "index";
	}
	
	/**
	 * 保存组织
	 * @return
	 * @throws IOException 
	 */
	@Action("save_organization")
	public String saveOrganization() throws IOException{
		if(StringUtil.isEmptyString(orgId)){		//新增
			PermOrganization org = new PermOrganization();
			org.setDepartmentName(departmentName);
			if(StringUtil.isEmptyString(parentOrgId)){
				org.setParentOrgId(0L);
			}else{
				org.setParentOrgId(Long.parseLong(parentOrgId));
			}
			org.setPermLevel(Long.parseLong(parentOrgLevel) + 1);
			org.setCreateUser(0L);//TODO
			org.setCreateTime(new Date());
			org.setValid("Y");
			permOrganizationService.addOrganization(org);
			setRequestAttribute("firstOrgList",permOrganizationService.getOrganizationByLevel(1));
		}else{		//修改
			PermOrganization org = new PermOrganization();
			org.setOrgId(Long.parseLong(orgId));
			org.setDepartmentName(departmentName);
			if(StringUtil.isEmptyString(parentOrgId)){
				org.setParentOrgId(0L);
			}else{
				org.setParentOrgId(Long.parseLong(parentOrgId));
			}
			permOrganizationService.updateOrganization(org);
		}
		return "save_organization";
	}
	
	/**
	 * 删除组织
	 */
	@Action("delete_organization")
	public void deleteOrganization(){
		Long orgId = Long.parseLong(getRequestParameter("orgId"));
		Long n = permOrganizationService.getChildOrgCount(orgId);
		if(n > 0){		//存在子节点
			sendAjaxMsg("1");
			return;
		}
		n = permOrganizationService.getUserByOrgCount(orgId);
		if(n > 0){		//存在用户
			sendAjaxMsg("2");
			return;
		}
		permOrganizationService.deleteOrganization(orgId);
		sendAjaxMsg("success");
	}
	
	/**
	 * 获取所有组织列表，返回json
	 */
	@Action("get_all_org")
	public void getAllOrg(){
		List<PermOrganization> orgs = permOrganizationService.selectAllOrganization();
		if(orgs != null && orgs.size() > 0){
			List<OptionItem> items = new ArrayList<OptionItem>();
			for(PermOrganization o : orgs){
				OptionItem item = new OptionItem();
				item.setValue(o.getOrgId().toString());
				item.setLabel(o.getDepartmentName());
				items.add(item);
			}
			sendAjaxResultByJson(JSONArray.fromObject(items).toString());
		}
	}
	
	/**
	 * 获取组织列表，返回json
	 */
	@Action("get_org_list_by_level")
	public void getOrgListByLevel(){
		List<PermOrganization> orgs = permOrganizationService.getOrganizationByLevel(Integer.parseInt(getRequestParameter("level")));
		if(orgs != null && orgs.size() > 0){
			List<OptionItem> items = new ArrayList<OptionItem>();
			for(PermOrganization o : orgs){
				OptionItem item = new OptionItem();
				item.setValue(o.getOrgId().toString());
				item.setLabel(o.getDepartmentName());
				items.add(item);
			}
			sendAjaxResultByJson(JSONArray.fromObject(items).toString());
		}
	}
	/**
	 * 获取子组织列表，返回json
	 */
	@Action("get_child_org_list")
	public void getChildOrgList(){
		List<PermOrganization> orgs = permOrganizationService.getChildOrgList(Long.parseLong(getRequestParameter("orgId")));
		sendAjaxResultByJson(JSONArray.fromObject(orgs).toString());
	}

	public PermOrganizationService getPermOrganizationService() {
		return permOrganizationService;
	}

	public void setPermOrganizationService(
			PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getParentOrgLevel() {
		return parentOrgLevel;
	}

	public void setParentOrgLevel(String parentOrgLevel) {
		this.parentOrgLevel = parentOrgLevel;
	}

	public String getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
