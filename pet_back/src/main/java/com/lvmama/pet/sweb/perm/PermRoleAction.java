package com.lvmama.pet.sweb.perm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermRole;
import com.lvmama.comm.pet.po.perm.PermRolePermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.perm.PermUserRole;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.perm.PermRolePermissionService;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.pet.service.perm.PermUserRoleService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.perm.PermissionService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.OptionItem;

/**
 * @author Jordan.Zhao
 * 角色设置
 * */
@Results(value={
	@Result(name="index",location="/WEB-INF/pages/back/perm/perm_role_index.jsp"),
	@Result(name="to_add",location="/WEB-INF/pages/back/perm/perm_role_add.jsp"),
	@Result(name="to_edit",location="/WEB-INF/pages/back/perm/perm_role_edit.jsp"),
	@Result(name="to_binding_perm",location="/WEB-INF/pages/back/perm/perm_role_binding_perm.jsp"),
	@Result(name="to_binding_user",location="/WEB-INF/pages/back/perm/perm_role_binding_user.jsp"),
	@Result(name="perm_child_element",location="/WEB-INF/pages/back/perm/perm_child_element.jsp")
})
@Namespace(value="/perm_role")
public class PermRoleAction extends BackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PermRoleService permRoleService;
	private PermRolePermissionService permRolePermissionService;
	private PermissionService permissionService;
	private PermUserRoleService permUserRoleService;
	private PermOrganizationService permOrganizationService;
	private PermUserService permUserService;
	private Page<PermRole> permRolePage = new Page<PermRole>();
	private PermRole permRole = new PermRole();
	private String roleName;
	private String roleLabel;
	private List<PermOrganization> departmentsList;
	private Page<PermRolePermission> permRolePermissionPage = new Page<PermRolePermission>();
	private Page<PermPermission> permPermissionPage = new Page<PermPermission>();
	private String ids;
	private Page<PermUserRole> permUserRolePage = new Page<PermUserRole>();
	private Page<PermUser> permUserPage = new Page<PermUser>();
	private ComLogService comLogServiceRemote;
	private Long roleId;
	private String perm_permName;
	private String perm_parentName;
	private String name;
	private String type;
	private String category;
	private String parentID;
	private String parentName;
	private String departmentName;
	private String userName;
	private String realName;
	private String dep_userName;
	private String dep_realName;
	private String dep_departmentName;
	private String dep_position;
	private Long sysPage;//系统权限相关的分页
	List<PermPermission> gategoryList;
	
	@Action("index")
	public String index(){
		return "index";
	}
	
	/**
	 * 查询角色列表
	 * @return
	 */
	@Action("search")
	public String search(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleName", roleName);
		params.put("roleLabel", roleLabel);
		Long totalResultSize = permRoleService.queryPermRoleByParamCount(params);
		permRolePage.setTotalResultSize(totalResultSize);
		permRolePage.setCurrentPage(this.page);
		//初始化分页信息
		permRolePage.buildUrl(getRequest());
		params.put("skipResults", permRolePage.getStartRows() - 1);
		params.put("maxResults", permRolePage.getEndRows());
		permRolePage.setItems(permRoleService.queryPermRoleByParam(params));
		return "index";
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
				item.setValue(o.getDepartmentName());
				item.setLabel(o.getDepartmentName());
				items.add(item);
			}
			sendAjaxResultByJson(JSONArray.fromObject(items).toString());
		}
	}
	
	/**
	 * 新增角色页面
	 * @return
	 */
	@Action("to_add")
	public String toAdd(){
		
		return "to_add";
	}
	
	/**
	 * 保存角色
	 * @return
	 */
	@Action("save")
	public String save(){
		permRole.setValid("Y");
		permRole.setCreateTime(new Date());
		if(permRole.getRoleName() !=null && !permRole.getRoleName().equals("")) {
			// 根据roleId判断是新增还是修改
			if(permRole.getRoleId() == null){	
				// 新增
				Long roleId = permRoleService.insertPermRole(permRole);
				// 保存日志信息getSessionUser().getUserName()
				comLogServiceRemote.insert("PERM_ROLE", null, roleId, "admin", "PERM_ROLE", "新增角色", "新增角色：" + permRole.getRoleName(), null);
				
				return "to_add";
			}else{		
				// 修改
				permRoleService.udpatePermRole(permRole);
				// 保存日志信息
				comLogServiceRemote.insert("PERM_ROLE", null, permRole.getRoleId(), "admin", "PERM_ROLE", "修改角色", "修改角色：" + permRole.getRoleName(), null);
				
				return "to_edit";
			}
		}
		return "to_add";
	}
	
	/**
	 * 编辑角色
	 * @return
	 */
	@Action("to_edit")
	public String toEdit(){
		permRole=this.permRoleService.getPermRoleByPK(Long.parseLong(getRequestParameter("roleId")));
		
		//查询部门列表
		departmentsList = permOrganizationService.selectAllOrganization();
		
		for(PermOrganization org : departmentsList){
			if(org.getDepartmentName().equals(permRole.getRoleLabel())){
				setRequestAttribute("org", org);
				setRequestAttribute("orgList", permOrganizationService.getOrganizationByLevel(Integer.parseInt(String.valueOf(org.getPermLevel()))));
				break;
			}
		}
		
		return "to_edit";
	} 
	
	/**
	 * 绑定权限，管理页面
	 * @return
	 */
	@Action("to_binding_perm")
	public String toBindingPerm(){

		gategoryList = this.permissionService.queryPermPermissionByPermLevel();
		for (int i = 0; i < gategoryList.size(); i++) {
			PermPermission p=(PermPermission)gategoryList.get(i);
			if(!StringUtil.isEmptyString(category)&&category.equals(p.getCategory())){
				p.setSelected(true);
			}
		}
		
		roleId = Long.parseLong(getRequestParameter("roleId"));
		
		/**
		 *  已绑定的角色权限列表
		 */
		this.initRolePerm(roleId);
		
		/**
		 *  系统权限列表
		 */
		this.initSystemPerm(roleId);
		
		return "to_binding_perm";
	}

	/**
	 *  获取角色权限
	 * @param roleId
	 */
	private void initRolePerm(Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		if(null != getRequestParameter("perm_permName")){
			params.put("perm_permName", getRequestParameter("perm_permName"));
		}
		if(null != getRequestParameter("perm_parentName")){
			params.put("perm_parentName", getRequestParameter("perm_parentName"));
		}
		permRolePermissionPage.setTotalResultSize(permRolePermissionService.queryPermRolePermissionPermRoleByParamCount(params));
		//初始化分页信息
		permRolePermissionPage.buildUrl(getRequest());
		permRolePermissionPage.setCurrentPage(super.page);
		params.put("skipResults", permRolePermissionPage.getStartRows());
		params.put("maxResults", permRolePermissionPage.getEndRows());
		permRolePermissionPage.setItems(permRolePermissionService.queryPermRolePermissionPermRoleByParam(params));
	}

	/**
	 *  系统权限列表
	 */
	private void initSystemPerm(Long roleId) {
		Map<String, Object> serachMap = new HashMap<String, Object>();
		serachMap.put("roleId", roleId);
		if(null != getRequestParameter("name")){
			serachMap.put("name", getRequestParameter("name"));
		}
		if(null != getRequestParameter("type")){
			serachMap.put("type", getRequestParameter("type"));
		}
		if(null != getRequestParameter("category")){
			serachMap.put("category", getRequestParameter("category"));
		}
		if(null != getRequestParameter("parentID")){
			serachMap.put("parentId", getRequestParameter("parentID"));
		}
		if(null != getRequestParameter("parentName")){
			serachMap.put("parentName", getRequestParameter("parentName"));
		}
		permPermissionPage.setCurrentPageParamName("sysPage");
		permPermissionPage.setTotalResultSize(permissionService.queryPermPermissionByParamCount(serachMap));
		//初始化分页信息
		permPermissionPage.buildUrl(getRequest());
		permPermissionPage.setCurrentPage(sysPage==null?1:sysPage);
		serachMap.put("skipResults", permPermissionPage.getStartRows());
		serachMap.put("maxResults", permPermissionPage.getEndRows());
		permPermissionPage.setItems(permissionService.queryPermPermissionByParam(serachMap));
	}
	
	/**
	 * 删除权限绑定
	 * @return
	 */
	@Action("delete_role_permission")
	public void deleteRolePermission(){
		String[] idStr = ids.split(",");
		for (int i = 0; i < idStr.length; i++) {
			this.permRolePermissionService.deletePermRolePermission(Long.parseLong(idStr[i]));
			// 保存日志信息
			comLogServiceRemote.insert("PERM_ROLE", null, Long.parseLong(getRequestParameter("roleId")), "admin", "PERM_ROLE", 
					"删除权限绑定", "RPID：" + idStr[i], null);
		}
		
		sendAjaxMsg("success");
	}
	
	/**
	 * 绑定权限，保存
	 * @return
	 */
	@Action("save_role_permission")
	public void saveRolePermission(){
		try {
			Long roleId = Long.parseLong(getRequestParameter("roleId"));
			String[] idsArr = ids.split(",");
			for(int i=0; i<idsArr.length; i++){
				Long permissionId = Long.parseLong(idsArr[i]);
				// 执行绑定
				this.permRolePermissionService.insertPermRolePermissionByList(permissionId, roleId);
				// 保存日志信息
				comLogServiceRemote.insert("PERM_ROLE", null, roleId, "admin", "PERM_ROLE", 
						"绑定权限", "权限ID：" + permissionId, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sendAjaxMsg("success");
	}
	
	/**
	 * 绑定用户，管理页面
	 * @return
	 */
	@Action("to_binding_user")
	public String toUserManage(){
		Long roleId = Long.parseLong(getRequestParameter("roleId"));
		
		/**
		 * 绑定的用户列表
		 */
		initBindingUser(roleId);

		/**
		 * 部门用户列表
		 */
		initDepartmentUser(roleId);
		
		return "to_binding_user";
	}

	/**
	 * 绑定的用户列表
	 * @param roleId
	 */
	private void initBindingUser(Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		if(!StringUtil.isEmptyString(getRequestParameter("departmentName"))){
			params.put("departmentRoleName", getRequestParameter("departmentName"));
		}
		if(!StringUtil.isEmptyString(getRequestParameter("userName"))){
			params.put("userRoleName", getRequestParameter("userName"));
		}
		if(!StringUtil.isEmptyString(getRequestParameter("realName"))){
			params.put("realRoleName", getRequestParameter("realName"));
		}
		permUserRolePage.setTotalResultSize(permUserRoleService.queryPermUserRoleByParamCount(params));
		permUserRolePage.setCurrentPageParamName("permUserRolePage.page");
		//初始化分页信息
		permUserRolePage.buildUrl(getRequest());
		params.put("skipResults", permUserRolePage.getStartRows());
		params.put("maxResults", permUserRolePage.getEndRows());
		permUserRolePage.setItems(permUserRoleService.queryPermUserRoleByParam(params));
	}

	/**
	 * 部门用户列表
	 */
	private void initDepartmentUser(Long roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		if(!StringUtil.isEmptyString(getRequestParameter("dep_userName"))){
			map.put("userName", getRequestParameter("dep_userName"));
		}
		if(!StringUtil.isEmptyString(getRequestParameter("dep_realName"))){
			map.put("realName", getRequestParameter("dep_realName"));
		}
		if(!StringUtil.isEmptyString(getRequestParameter("dep_departmentName"))){
			map.put("departmentName", getRequestParameter("dep_departmentName"));
		}
		if(!StringUtil.isEmptyString(getRequestParameter("dep_position"))){
			map.put("position", getRequestParameter("dep_position"));
		}
		permUserPage.setTotalResultSize(permUserService.queryPermUserByParamCount(map));
		permUserPage.setCurrentPageParamName("permUserPage.page");
		//初始化分页信息
		permUserPage.buildUrl(getRequest());
		map.put("skipResults",permUserPage.getStartRows());
		map.put("maxResults", permUserPage.getEndRows());
		permUserPage.setItems(permUserService.queryPermUserByParam(map));
	}
	
	/**
	 * 删除用户绑定
	 * @return
	 */
	@Action("to_delete_user_binding")
	public void deleteUser(){
		String[] idStr = ids.split(",");
		for (int i = 0; i < idStr.length; i++) {
			permUserRoleService.deleteUserRoleByPK(Long.parseLong(idStr[i]));
			// 保存日志信息
			comLogServiceRemote.insert("PERM_ROLE", null, Long.parseLong(getRequestParameter("roleId")), "admin", "PERM_ROLE", 
					"删除用户绑定", "URID：" + idStr[i], null);
		}
		
		sendAjaxMsg("success");
	}
	
	/**
	 * 绑定用户，保存
	 * @return
	 */
	@Action("binding_role_user")
	public void saveRoleUser(){
		try {
			Long roleId = Long.parseLong(getRequestParameter("roleId")); 
			String[] idStr = ids.split(",");
			for (int i = 0; i < idStr.length; i++) {
				Long userId = Long.parseLong(idStr[i]);
				// 执行绑定
				this.permUserRoleService.insertPermUserRoleByList(userId, roleId);
				// 保存日志信息
				comLogServiceRemote.insert("PERM_ROLE", null, roleId, "admin", "PERM_ROLE", 
						"绑定用户", "用户ID：" + userId, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sendAjaxMsg("success");
	}
	
	/**
	 * 查看子元素
	 */
	@Action("to_show_element")
	public String showElement(){
		Long permission = Long.parseLong(getRequestParameter("permissionId"));
		Long roleId = Long.parseLong(getRequestParameter("roleId"));
		Map<String, Object> serachMap = new HashMap<String, Object>();
		serachMap.put("roleId", roleId);
		serachMap.put("parentId", permission);
		permPermissionPage.setTotalResultSize(permissionService.queryPermPermissionByParamCount(serachMap));
		//初始化分页信息
		permPermissionPage.buildUrl(getRequest());
		permPermissionPage.setCurrentPage(this.page);
		serachMap.put("skipResults", permPermissionPage.getStartRows());
		serachMap.put("maxResults", permPermissionPage.getEndRows());
		permPermissionPage.setItems(permissionService.queryPermPermissionByParam(serachMap));
		
		return "perm_child_element";
	}

	public PermRoleService getPermRoleService() {
		return permRoleService;
	}

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}
	
	public Page<PermRole> getPermRolePage() {
		return permRolePage;
	}

	public void setPermRolePage(Page<PermRole> permRolePage) {
		this.permRolePage = permRolePage;
	}
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleLabel() {
		return roleLabel;
	}

	public void setRoleLabel(String roleLabel) {
		this.roleLabel = roleLabel;
	}
	public PermRole getPermRole() {
		return permRole;
	}

	public void setPermRole(PermRole permRole) {
		this.permRole = permRole;
	}
	public List<PermOrganization> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<PermOrganization> departmentsList) {
		this.departmentsList = departmentsList;
	}
	
	public PermOrganizationService getPermOrganizationService() {
		return permOrganizationService;
	}

	public void setPermOrganizationService(
			PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}
	
	public Page<PermRolePermission> getPermRolePermissionPage() {
		return permRolePermissionPage;
	}

	public void setPermRolePermissionPage(
			Page<PermRolePermission> permRolePermissionPage) {
		this.permRolePermissionPage = permRolePermissionPage;
	}
	public PermRolePermissionService getPermRolePermissionService() {
		return permRolePermissionService;
	}

	public void setPermRolePermissionService(
			PermRolePermissionService permRolePermissionService) {
		this.permRolePermissionService = permRolePermissionService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	public Page<PermPermission> getPermPermissionPage() {
		return permPermissionPage;
	}

	public void setPermPermissionPage(Page<PermPermission> permPermissionPage) {
		this.permPermissionPage = permPermissionPage;
	}
	public String getPerm_permName() {
		return perm_permName;
	}

	public void setPerm_permName(String perm_permName) {
		this.perm_permName = perm_permName;
	}

	public String getPerm_parentName() {
		return perm_parentName;
	}

	public void setPerm_parentName(String perm_parentName) {
		this.perm_parentName = perm_parentName;
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public PermUserRoleService getPermUserRoleService() {
		return permUserRoleService;
	}

	public void setPermUserRoleService(PermUserRoleService permUserRoleService) {
		this.permUserRoleService = permUserRoleService;
	}

	public PermUserService getPermUserService() {
		return permUserService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public Page<PermUserRole> getPermUserRolePage() {
		return permUserRolePage;
	}

	public void setPermUserRolePage(Page<PermUserRole> permUserRolePage) {
		this.permUserRolePage = permUserRolePage;
	}
	
	public Page<PermUser> getPermUserPage() {
		return permUserPage;
	}

	public void setPermUserPage(Page<PermUser> permUserPage) {
		this.permUserPage = permUserPage;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDep_userName() {
		return dep_userName;
	}

	public void setDep_userName(String dep_userName) {
		this.dep_userName = dep_userName;
	}

	public String getDep_realName() {
		return dep_realName;
	}

	public void setDep_realName(String dep_realName) {
		this.dep_realName = dep_realName;
	}

	public String getDep_departmentName() {
		return dep_departmentName;
	}

	public void setDep_departmentName(String dep_departmentName) {
		this.dep_departmentName = dep_departmentName;
	}

	public String getDep_position() {
		return dep_position;
	}

	public void setDep_position(String dep_position) {
		this.dep_position = dep_position;
	}
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<PermPermission> getGategoryList() {
		return gategoryList;
	}

	public void setGategoryList(List<PermPermission> gategoryList) {
		this.gategoryList = gategoryList;
	}

	public ComLogService getComLogServiceRemote() {
		return comLogServiceRemote;
	}

	public void setComLogServiceRemote(ComLogService comLogServiceRemote) {
		this.comLogServiceRemote = comLogServiceRemote;
	}

	public void setSysPage(Long sysPage) {
		this.sysPage = sysPage;
	}
	
}
