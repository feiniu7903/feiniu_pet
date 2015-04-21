package com.lvmama.pet.sweb.perm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermRole;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.perm.PermUserPermission;
import com.lvmama.comm.pet.po.perm.PermUserRole;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.pet.service.perm.PermUserPermissionService;
import com.lvmama.comm.pet.service.perm.PermUserRoleService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.perm.PermissionService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;

/**
 * @author Jordan.Zhao
 * 用户管理
 * */
@Results(value={
	@Result(name="test",location="/WEB-INF/pages/back/perm/perm_user_test.jsp"),
	@Result(name="index",location="/WEB-INF/pages/back/perm/perm_user_index.jsp"),
	@Result(name="to_show",location="/WEB-INF/pages/back/perm/perm_user_show.jsp"),
	@Result(name="to_add",location="/WEB-INF/pages/back/perm/perm_user_add.jsp"),
	@Result(name="to_edit",location="/WEB-INF/pages/back/perm/perm_user_edit.jsp"),
	@Result(name="to_permission_manage",location="/WEB-INF/pages/back/perm/perm_user_permission_manage.jsp"),
	@Result(name="to_add_role",location="/WEB-INF/pages/back/perm/perm_user_permission_role.jsp"),
	@Result(name="to_add_permission",location="/WEB-INF/pages/back/perm/perm_user_permission_perm.jsp"),
	@Result(name="to_disable_permission",location="/WEB-INF/pages/back/perm/perm_user_permission_perm_disable.jsp"),
	@Result(name="to_change_password",location="/WEB-INF/pages/back/perm/perm_user_change_password.jsp")
})
@Namespace(value="/perm_user")
public class PermUserAction extends BackBaseAction {
	private static final Log logger=LogFactory.getLog(PermUserAction.class);
	private final String PASSWORD = "111111";
	private PermOrganizationService permOrganizationService;
	private PermUserService permUserService;
	private PermRoleService permRoleService;
	private PermissionService permissionService;
	private PermUserRoleService permUserRoleService;
	private PermUserPermissionService permUserPermissionService;
	private ComLogService comLogServiceRemote;
	private String search;
	
	private Page<PermUser> permUserPage = new Page<PermUser>();
	private Page<PermRole> permRolePage = new Page<PermRole>();
	private Page<PermPermission> permPermissionPage = new Page<PermPermission>();
	
	private List<PermOrganization> departmentsList;
	private String userName;
	private String realName;
	private String departmentId;
	private String valid;
	private PermUser permUser = new PermUser();
	
	/**
	 * 用户管理主页面
	 * @return
	 */
	@Action("index")
	public String index() {
		initDepartmentsList();
		return "index";
	}
	
	/**
	 * 查询用户列表
	 * @return
	 */
	@Action(value = "search")
	public String search() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(userName)){
			params.put("userName", userName);
		}
		if(!StringUtil.isEmptyString(realName)){
			params.put("realName", realName);
		}
		if(!"0".equals(departmentId)){
			params.put("departmentId", departmentId);
		}
		if(!StringUtil.isEmptyString(valid)){
			params.put("valid", valid);
		}
		//取得数据总数量
		permUserPage.setTotalResultSize(permUserService.selectUsersCountByParams(params));
		//初始化分页信息
		permUserPage.buildUrl(getRequest());
		permUserPage.setCurrentPage(super.page);
		params.put("skipResults", permUserPage.getStartRows() - 1);
		params.put("maxResults", permUserPage.getEndRows());
		permUserPage.setItems(permUserService.selectUsersByParams(params));
		initDepartmentsList();
		return "index";
	}
	/**
	 * 查看用户
	 * @return
	 */
	@Action(value="to_show")
	public String show(){
		Long userId = Long.valueOf(getRequestParameter("userId"));
		// 查询用户基本信息
		permUser = permUserService.getPermUserByUserId(userId);

		//查询用户权限
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		permPermissionPage.setTotalResultSize(permUserService.queryEnablePermissionsByUserIdPagingCount(params));
		permPermissionPage.buildUrl(getRequest());
		permPermissionPage.setCurrentPage(super.page);
		params.put("skipResults", permPermissionPage.getStartRows() - 1);
		params.put("maxResults", permPermissionPage.getEndRows());
		permPermissionPage.setItems(permUserService.queryEnablePermissionsByUserIdPaging(params));
		
		return "to_show";
	}
	/**
	 * 新增用户
	 * @return
	 */
	@Action(value="to_add")
	public String toAdd(){
		setRequestAttribute("userNum", permUserService.getNewPermUserNum());
		return "to_add";
	}
	/**
	 * 修改用户
	 * @return
	 */
	@Action(value="to_edit")
	public String toEdit(){
		Long userId = Long.valueOf(getRequestParameter("userId"));
		permUser.setValid("Y");
		permUser = permUserService.getPermUserByUserId(userId);
		
		//查询部门列表
		departmentsList = permOrganizationService.selectAllOrganization();
		
		for(PermOrganization org : departmentsList){
			if(org.getOrgId().equals(permUser.getDepartmentId())){
				setRequestAttribute("org", org);
				setRequestAttribute("orgList", permOrganizationService.getOrganizationByLevel(Integer.parseInt(String.valueOf(org.getPermLevel()))));
				break;
			}
		}
		return "to_edit";
	}
	
	/**
	 * 保存用户
	 * @return
	 */
	@Action("save")
	public String save(){
		boolean result = false;
		if (null != permUser.getUserId()) {				//更新
			String logContent = "";
			PermUser u = permUserService.getPermUserByUserId(permUser.getUserId());
			if(u == null){
				throw new RuntimeException("用户不存在");
			}
			logContent = logContent + "【原用户信息：" + u.getUserInfoStr() + "】";
			result = permUserService.updateUser(permUser);
			logContent = logContent + "<br />【更新后用户信息：" + permUser.getUserInfoStr() + "】";
			comLogServiceRemote.insert("PERM_USER", null, permUser.getUserId(), getSessionUser().getUserName(), "PERM_USER", "更新用户", logContent, null);
			return "index";
		} else {										//新增
			// 新增用户信息
			permUser.setPassword(PASSWORD);
			Long isrtId = permUserService.addUser(permUser);
			permUser.setUserId(isrtId);
			comLogServiceRemote.insert("PERM_USER", null, isrtId, getSessionUser().getUserName(), "PERM_USER", "新增用户", permUser.getUserInfoStr(), null);
			return "index";
		}
	}

	/**
	 * 初始化密码
	 */
	@Action(value="reset_password")
	public void resetPassword(){
		permUser=permUserService.getPermUserByUserId(Long.parseLong(getRequestParameter("userId")));
		permUser.setPassword(PASSWORD);
		boolean result = permUserService.initializePassword(permUser);
		comLogServiceRemote.insert("PERM_USER", null, Long.parseLong(getRequestParameter("userId")), 
				getSessionUser().getUserName(), "PERM_USER", "初始化密码", "初始化密码", null);
		sendAjaxMsg(String.valueOf(result));
	}
	
	/**
	 * 用户权限管理页面
	 * @return
	 */
	@Action("to_permission_manage")
	public String toPermissionManage(){
		Long userId = Long.parseLong(getRequestParameter("userId"));
		Map<String, Object> params = new HashMap<String, Object>();
		//查询用户角色列表
		params.put("userId", userId);
		permRolePage.setTotalResultSize(permRoleService.getRolesByParamsCount(params));
		params.put("skipResults", 0);
		params.put("maxResults", permRolePage.getTotalResultSize());
		permRolePage.setItems(permRoleService.getRolesByParams(params));
		
		//授权权限
		params.clear();
		params.put("userId", userId);
		params.put("type", "ENABLED");
		setRequestAttribute("permissionList", permUserPermissionService.getPermUserPermission(params));
		//禁止权限
		params.clear();
		params.put("userId", userId);
		params.put("type", "DISABLED");
		setRequestAttribute("disabledPermissionList", permUserPermissionService.getPermUserPermission(params));
		
		setRequestAttribute("userId", userId);
		setRequestAttribute("userName", permUserService.getPermUserByUserId(userId).getUserName());
		return "to_permission_manage";
	}
	/**
	 * 分配角色页面
	 * @return
	 */
	@Action("to_add_role")
	public String toAddRole(){
		setRequestAttribute("userId", getRequestParameter("userId"));
		setRequestAttribute("roleName", getRequestParameter("roleName"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleName", getRequestParameter("roleName"));
		permRolePage.setTotalResultSize(permRoleService.queryPermRoleByParamCount(params));
		params.put("userId", getRequestParameter("userId"));
		permRolePage.buildUrl(getRequest());
		permRolePage.setCurrentPage(super.page);
		params.remove("userId");
		params.put("skipResults", permRolePage.getStartRows() - 1);
		params.put("maxResults", permRolePage.getEndRows());
		permRolePage.setItems(permRoleService.queryPermRoleByParam(params));
		return "to_add_role";
	}
	/**
	 * 保存分配的角色
	 * @return
	 */
	@Action("add_role")
	public void addRole(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", getRequestParameter("roleId"));
		params.put("userId", getRequestParameter("userId"));
		params.put("maxResults", 10);
		params.put("skipResults", 0);
		List<PermUserRole> list = permUserRoleService.queryPermUserRoleByParam(params);
		if(list != null && list.size() > 0){
			sendAjaxMsg("1");		//已经拥有该角色
			return;
		}
		
		PermUserRole userRole = new PermUserRole();
		userRole.setRoleId(Long.parseLong(getRequestParameter("roleId")));
		userRole.setUserId(Long.parseLong(getRequestParameter("userId")));
		permUserRoleService.insertPermUserRole(userRole);
		comLogServiceRemote.insert("PERM_USER", null, Long.parseLong(getRequestParameter("userId")), 
				getSessionUser().getUserName(), "PERM_USER", "分配角色", "角色ID：" + getRequestParameter("roleId"), null);
		sendAjaxMsg("success");
	}
	/**
	 * 分配权限页面
	 * @return
	 */
	@Action("to_add_permission")
	public String toAddPermission(){
		setRequestAttribute("userId", getRequestParameter("userId"));
		setRequestAttribute("name", getRequestParameter("name"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", getRequestParameter("name"));
		permPermissionPage.setTotalResultSize(permissionService.queryPermPermissionByParamCount(params));
		params.put("userId", getRequestParameter("userId"));
		permPermissionPage.buildUrl(getRequest());
		permPermissionPage.setCurrentPage(super.page);
		params.remove("userId");
		params.put("skipResults", permPermissionPage.getStartRows());
		params.put("maxResults", permPermissionPage.getEndRows());
		permPermissionPage.setItems(permissionService.queryPermPermissionByParam(params));
		return "to_add_permission";
	}
	/**
	 * 保存分配的权限
	 * @return
	 */
	@Action("add_permission")
	public void addPermission(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", getRequestParameter("userId"));
		List<PermPermission> list = permissionService.queryPermPermissionFromRole(params);
		params.put("type", "ENABLED");
		List<PermPermission> list2 = permissionService.queryPermPermissionFromPerm(params);
		list.addAll(list2);
		if(list != null && list.size() > 0){
			for(PermPermission permission : list){
				if(permission.getPermissionId().toString().equals(getRequestParameter("permissionId"))){
					sendAjaxMsg("1");		//已经拥有该权限
					return;
				}
			}
		}
		
		PermUserPermission userPermission = new PermUserPermission();
		userPermission.setUserId(Long.parseLong(getRequestParameter("userId")));
		userPermission.setPermissionId(Long.parseLong(getRequestParameter("permissionId")));
		userPermission.setEnableDays(Long.parseLong(getRequestParameter("enableDays")));
		userPermission.setCreateTime(new Date());
		userPermission.setType("ENABLED");
		permUserPermissionService.insert(userPermission);
		comLogServiceRemote.insert("PERM_USER", null, Long.parseLong(getRequestParameter("userId")), 
				getSessionUser().getUserName(), "PERM_USER", "分配权限", "权限ID：" + getRequestParameter("permissionId"), null);
		sendAjaxMsg("success");
	}
	/**
	 * 禁用权限页面
	 * @return
	 */
	@Action("to_disable_permission")
	public String toDisablePermission(){
		setRequestAttribute("userId", getRequestParameter("userId"));
		setRequestAttribute("name", getRequestParameter("name"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", getRequestParameter("userId"));
		params.put("name", getRequestParameter("name"));
		permPermissionPage.setTotalResultSize(permissionService.queryEnablePermPermissionFromRoleCount(params));
		permPermissionPage.buildUrl(getRequest());
		permPermissionPage.setCurrentPage(super.page);
		params.put("skipResults", permPermissionPage.getStartRows());
		params.put("maxResults", permPermissionPage.getEndRows());
		permPermissionPage.setItems(permissionService.queryEnablePermPermissionFromRole(params));
		return "to_disable_permission";
	}
	/**
	 * 保存禁用的权限
	 * @return
	 */
	@Action("disable_permission")
	public void disablePermission(){
		PermUserPermission userPermission = new PermUserPermission();
		userPermission.setUserId(Long.parseLong(getRequestParameter("userId")));
		userPermission.setPermissionId(Long.parseLong(getRequestParameter("permissionId")));
		userPermission.setCreateTime(new Date());
		userPermission.setType("DISABLED");
		permUserPermissionService.insert(userPermission);
		comLogServiceRemote.insert("PERM_USER", null, Long.parseLong(getRequestParameter("userId")), 
				getSessionUser().getUserName(), "PERM_USER", "禁用权限", "权限ID：" + getRequestParameter("permissionId"), null);
		sendAjaxMsg("success");
	}
	/**
	 * 删除角色
	 * @return
	 */
	@Action("delete_role")
	public void deleteRole(){
		permUserRoleService.deleteUserRoleByPK(Long.valueOf(getRequestParameter("urId")));
		comLogServiceRemote.insert("PERM_USER", null, Long.parseLong(getRequestParameter("userId")), 
				getSessionUser().getUserName(), "PERM_USER", "删除角色", "角色ID：" + getRequestParameter("roleId"), null);
		sendAjaxMsg("success");
	}
	/**
	 * 删除权限
	 * @return
	 */
	@Action("delete_permission")
	public void deletePermission(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("upId", Long.parseLong(getRequestParameter("upId")));
		permUserPermissionService.deletePermUserPermission(params);
		comLogServiceRemote.insert("PERM_USER", null, Long.parseLong(getRequestParameter("userId")), 
				getSessionUser().getUserName(), "PERM_USER", "删除权限", "权限ID：" + getRequestParameter("permissionId"), null);
		sendAjaxMsg("success");
	}
	/**
	 * 激活禁用权限
	 * @return
	 */
	@Action("enable_permission")
	public void enablePermission(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("upId", Long.parseLong(getRequestParameter("upId")));
		permUserPermissionService.deletePermUserPermission(params);
		comLogServiceRemote.insert("PERM_USER", null, Long.parseLong(getRequestParameter("userId")), 
				getSessionUser().getUserName(), "PERM_USER", "删除禁用权限", "权限ID：" + getRequestParameter("permissionId"), null);
		sendAjaxMsg("success");
	}
	/**
	 * 搜索用户列表
	 */
	@Action("search_user")
	public void searchPermUsers(){
		if(logger.isDebugEnabled()){
			logger.debug("search str:::::::"+search);
		}
		List<PermUser> list=permUserService.findPermUser(search);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(PermUser user:list){
				JSONObject obj=new JSONObject();
				obj.put("id", user.getUserId());
				obj.put("text", user.getRealName());
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	@Action("to_change_password")
	public String toChangePassword(){
		return "to_change_password";
	}
	@Action("save_new_password")
	public void saveNewPassword(){
		String oldPassword = getRequestParameter("oldPassword");
		String newPassword = getRequestParameter("newPassword");
		if(!oldPassword.equals(getSessionUser().getPassword())){
			sendAjaxMsg("1");
			return;
		}
		PermUser user = getSessionUser();
		user.setPassword(newPassword);
		permUserService.updateUser(user);
		putSession(Constant.SESSION_BACK_USER, user);
		sendAjaxMsg("SUCCESS");
	}
	private void initDepartmentsList(){
		departmentsList = permOrganizationService.selectAllOrganization();
		departmentsList.add(0, new PermOrganization());
	}

	public Page<PermUser> getPermUserPage() {
		return permUserPage;
	}

	public void setPermUserPage(Page<PermUser> permUserPage) {
		this.permUserPage = permUserPage;
	}

	public PermUserService getPermUserService() {
		return permUserService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
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
 
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public PermOrganizationService getPermOrganizationService() {
		return permOrganizationService;
	}

	public void setPermOrganizationService(
			PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}

	public List<PermOrganization> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<PermOrganization> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public PermRoleService getPermRoleService() {
		return permRoleService;
	}

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public Page<PermRole> getPermRolePage() {
		return permRolePage;
	}

	public void setPermRolePage(Page<PermRole> permRolePage) {
		this.permRolePage = permRolePage;
	}

	public Page<PermPermission> getPermPermissionPage() {
		return permPermissionPage;
	}

	public void setPermPermissionPage(Page<PermPermission> permPermissionPage) {
		this.permPermissionPage = permPermissionPage;
	}

	public PermUser getPermUser() {
		return permUser;
	}

	public void setPermUser(PermUser permUser) {
		this.permUser = permUser;
	}

	public PermUserRoleService getPermUserRoleService() {
		return permUserRoleService;
	}

	public void setPermUserRoleService(PermUserRoleService permUserRoleService) {
		this.permUserRoleService = permUserRoleService;
	}

	public PermUserPermissionService getPermUserPermissionService() {
		return permUserPermissionService;
	}

	public void setPermUserPermissionService(
			PermUserPermissionService permUserPermissionService) {
		this.permUserPermissionService = permUserPermissionService;
	}

	public ComLogService getComLogServiceRemote() {
		return comLogServiceRemote;
	}

	public void setComLogServiceRemote(ComLogService comLogServiceRemote) {
		this.comLogServiceRemote = comLogServiceRemote;
	}
	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}
}