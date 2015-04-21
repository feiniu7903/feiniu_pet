package com.lvmama.pet.sweb.perm;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.perm.PermUserPermission;
import com.lvmama.comm.pet.service.perm.PermUserPermissionService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;

/**
 * 权限监控
 * @author Jordan.Zhao
 */
@Results(value={
		@Result(name="index",location="/WEB-INF/pages/back/perm/perm_permission_monitor_index.jsp")
	})
@Namespace(value="/perm_permission_monitor")
public class PermPermissionMonitorAction extends BackBaseAction{
	private Page<PermUserPermission> permUserPermissionPage = new Page<PermUserPermission>();
	private PermUserPermissionService permUserPermissionService;
	
	private Page<PermUser> permUserPage = new Page<PermUser>();
	private String permissionId;
	private String status;
	
	@Action("index")
	public String index(){
		return "index";
	}
	
	@Action("search")
	public String search(){
		if(StringUtil.isEmptyString(getRequestParameter("permissionId"))){
			return "index";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("permissionId", getRequestParameter("permissionId"));
		params.put("status", getRequestParameter("status"));
		permUserPage.setTotalResultSize(permUserPermissionService.getPermUserPermissionByPermCount(params));
		permUserPage.buildUrl(getRequest());
		permUserPage.setCurrentPage(super.page);
		params.put("skipResults", permUserPage.getStartRows());
		params.put("maxResults", permUserPage.getEndRows());
		permUserPage.setItems(permUserPermissionService.getPermUserPermissionByPerm(params));
		return "index";
	}
	
	/**
	 * 删除/激活 用户-权限关系
	 */
	@Action("deleteUserPermission")
	public void deleteUserPermission(){
		String upIds = getRequestParameter("upIds");
		String[] arr = upIds.split(",");
		for(String s : arr){
			String[] arr2 = s.split("-");
			Long userId = Long.parseLong(arr2[0]);
			Long permissionId = Long.parseLong(arr2[1]);
			String status = arr2[2];
			if("1".equals(status)){		//删除
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("permissionId", permissionId);
				permUserPermissionService.deletePermUserPermissionByParam(params);
			}else if ("0".equals(status)) {		//激活
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("permissionId", permissionId);
				params.put("type", "DISABLED");
				permUserPermissionService.deletePermUserPermission(params);
			}
		}
		sendAjaxMsg("success");
	}

	public Page<PermUserPermission> getPermUserPermissionPage() {
		return permUserPermissionPage;
	}

	public void setPermUserPermissionPage(
			Page<PermUserPermission> permUserPermissionPage) {
		this.permUserPermissionPage = permUserPermissionPage;
	}

	public PermUserPermissionService getPermUserPermissionService() {
		return permUserPermissionService;
	}

	public void setPermUserPermissionService(
			PermUserPermissionService permUserPermissionService) {
		this.permUserPermissionService = permUserPermissionService;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Page<PermUser> getPermUserPage() {
		return permUserPage;
	}

	public void setPermUserPage(Page<PermUser> permUserPage) {
		this.permUserPage = permUserPage;
	}
	
	
	
	
}
