package com.lvmama.bee.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.eplace.EbkPermission;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.po.eplace.EbkUserPermission;
import com.lvmama.comm.bee.service.eplace.EbkPermissionService;
import com.lvmama.comm.bee.service.eplace.EbkUserPermissionService;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ZTreeNode;

@Namespace(value="/ebk_user")
@Results(value={
                @Result(name="to_change_pwd",location="/WEB-INF/pages/user/user_change_pwd.jsp"),
                @Result(name="change_pwd_success",location="/WEB-INF/pages/user/user_change_pwd_success.jsp"),
                @Result(name="index",location="/WEB-INF/pages/user/user_index.jsp"),
                @Result(name="to_add_user",location="/WEB-INF/pages/user/user_add.jsp"),
                @Result(name="save_user_success",type="redirect",location="to_add_user.do"),
                @Result(name="to_update_user",location="/WEB-INF/pages/user/user_update.jsp"),
                @Result(name="update_user_success",type="redirect",location="index.do")
          })
public class EbkUserAction extends EbkBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6402494476212503294L;

	private final String PASSWORD = "111111";
	
	private EbkUserService ebkUserService;
	private EbkPermissionService ebkPermissionService;
	private EbkUserPermissionService ebkUserPermissionService;
	private ComLogService petComLogService;
	private String oldPwd;
	private String newPwd1;
	private String newPwd2;
	
	private EbkUser user;
	private String permIds;
	private String password_short;
	private Long userId;
	private Page<EbkUser> ebkUserPage = new Page<EbkUser>();
	
	@Action("to_change_pwd")
	public String toChangePwd(){
		return "to_change_pwd";
	}
	/**
	 * 修改密码
	 * @return
	 */
	@Action("change_pwd")
	public void changePwd(){
		Map<String, Object> param = new HashMap<String, Object>();
		EbkUser user = getSessionUser();
		//校验原密码是否正确
		if(!StringUtil.isEmptyString(oldPwd)&&!user.getPassword().equals(MD5.code(oldPwd, MD5.KEY_EBK_USER_PASSWORD))){
			param.put("error", true);
			param.put("errorMessage", "原密码不正确!");
			this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
			return ;
		}
		if(!StringUtil.isEmptyString(newPwd1)){
			//校验新密码长度是否和原密码相同
			if(newPwd1.equals(oldPwd)){
				param.put("error", true);
				param.put("errorMessage", "新密码不能和原密码相同，请修改!");
				this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
				return ;
			};
			//校验新密码长度是否小于6位
			int length=newPwd1.length();
			if(length<6){
				param.put("error", true);
				param.put("errorMessage", "新密码长度不能小于6位!");
				this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
				return ;
			};
			//校验新密码是否全部由相同字符组成
			boolean hasAllSameChar = true;
			for(int i=1;i<length;i++){
				if(newPwd1.charAt(0)!=newPwd1.charAt(i)){
					hasAllSameChar = false;
					break;
				}
			}
			if(hasAllSameChar) {
				param.put("error", true);
				param.put("errorMessage", "新密码不能由全部相同的字符组成!");
				this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
				return ;
			}
		}
		//校验确认密码和新密码是否一致
		if(!StringUtil.isEmptyString(newPwd1)&&! StringUtil.isEmptyString(newPwd2) && !newPwd1.equals(newPwd2)){
			param.put("error", true);
			param.put("errorMessage", "确认密码和新密码和不一致,请修改!");
			this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
			return;
		}
		ebkUserService.changePassword(user.getUserId(), MD5.code(newPwd1, MD5.KEY_EBK_USER_PASSWORD));
		user.setPassword(MD5.code(newPwd1, MD5.KEY_EBK_USER_PASSWORD));
		putSession(Constant.SESSION_EBOOKING_USER,user);
		petComLogService.insert("EBK_USER", null, user.getUserId(), getSessionUser().getUserName(), null, "修改密码", "修改密码", null);
		param.put("success", true);
		this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
	}
	@Action("change_pwd_success")
	public String changePasswordSuccess(){
		return "change_pwd_success";
	}
	
	@Action("index")
	public String index(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentUserId", getSessionUser().getUserId());
		params.put("isAdmin", "false");
		if(!"all".equals(getRequestParameter("valid"))){
			String valid = getRequestParameter("valid");
			params.put("valid", valid);
		}
		ebkUserPage.setTotalResultSize(ebkUserService.getEbkUserCount(params));
		ebkUserPage.buildUrl(getRequest());
		ebkUserPage.setCurrentPage(super.page);
		params.put("start", ebkUserPage.getStartRows());
		params.put("end", ebkUserPage.getEndRows());
		ebkUserPage.setItems(ebkUserService.getEbkUser(params));
		setRequestAttribute("valid", getRequestParameter("valid"));
		return "index";
	}
	
	@Action("to_add_user")
	public String toAddUser(){
		return "to_add_user";
	}
	
	/**
	 * 加载权限树
	 */
	@Action("get_permission_tree")
	public void getPermissionTree(){
		List<EbkPermission> permissions = ebkPermissionService.getEbkPermissionByBizType(null);
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		for(EbkPermission perm : permissions){
			// 不加载用户管理权限
			if(perm.getPermissionId() == 1) {
				continue;
			}
			ZTreeNode node = new ZTreeNode();
			node.setId(perm.getPermissionId().toString());
			node.setpId(perm.getParentId().toString());
			node.setName(perm.getName());
			nodeList.add(node);
		}
		sendAjaxResultByJson(JSONArray.fromObject(nodeList).toString());
	}
	
	/**
	 * 保存用户
	 * @return
	 */
	@Action("save_user")
	public void saveUser(){
		user.setCreateTime(new Date());
		user.setIsAdmin("false");
		user.setParentUserId(getSessionUser().getUserId());
		user.setPassword(MD5.code(user.getPassword(), MD5.KEY_EBK_USER_PASSWORD));
		user.setSupplierId(getSessionUser().getSupplierId());
		Long insertUserId = ebkUserService.insert(user);
		if(!StringUtil.isEmptyString(permIds)){
			String[] ids = permIds.split(",");
			for(String id : ids){
				EbkUserPermission up = new EbkUserPermission();
				up.setUserId(insertUserId);
				up.setPermissionId(Long.parseLong(id));
				ebkUserPermissionService.insert(up);
			}
		}
		petComLogService.insert("EBK_USER", null, insertUserId, getSessionUser().getUserName(), null, 
				"创建用户", "[用户基本信息：" + user.getUserInfoStr() + "][用户权限ID：" + permIds + "]", null);
		sendAjaxMsg("SUCCESS");
	}
	
	/**
	 * 用户名是否存在
	 */
	@Action("is_exist_username")
	public void isExistUsername(){
		if(null == ebkUserService.getEbkUserByUserName(getRequestParameter("userName"))){
			sendAjaxMsg("true");
		}else{
			sendAjaxMsg("false");
		}
	}
	
	@Action("to_update_user")
	public String toUpdateUser(){
		user = ebkUserService.getEbkUserById(Long.parseLong(getRequestParameter("userId")));
		return "to_update_user";
	}
	/**
	 * 加载用户的权限树
	 */
	@Action("get_user_permission_tree")
	public void getUserPermissionTree(){
		List<EbkPermission> permList = ebkPermissionService.getEbkPermissionByUserId(Long.parseLong(getRequestParameter("userId")));
		List<EbkPermission> allPermList = ebkPermissionService.getEbkPermissionByBizType(null);
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		for(EbkPermission perm : allPermList){
			ZTreeNode node = new ZTreeNode();
			node.setId(perm.getPermissionId().toString());
			node.setpId(perm.getParentId().toString());
			node.setName(perm.getName());
			if(permList != null){
				for(EbkPermission p : permList){
					if(perm.getPermissionId().equals(p.getPermissionId())){
						node.setChecked("true");
						break;
					}
				}
			}
			nodeList.add(node);
		}
		sendAjaxResultByJson(JSONArray.fromObject(nodeList).toString());
	}
	/**
	 * 初始化密码
	 */
	@Action("init_password")
	public void initPassword(){
		ResultHandle result = new ResultHandle();
		EbkUser u = ebkUserService.getEbkUserById(userId);
		if(u == null) {
			result.setMsg("用户不存在");
		} else if(!this.getSessionUser().getUserId().equals(u.getParentUserId())) {
			result.setMsg("用户不存在");
		} else {
			ebkUserService.changePassword(userId,MD5.code(PASSWORD,MD5.KEY_EBK_USER_PASSWORD));
			petComLogService.insert("EBK_USER", null, userId, getSessionUser().getUserName(), null, "初始化密码", "初始化密码", null);
		}
		sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	/**
	 * 修改用户
	 * @return
	 */
	@Action("update_user")
	public String updateUser(){
		ebkUserService.updateUser(user);
		String[] arr = permIds.split(",");
		List<Long> permIdList = new ArrayList<Long>();
		if(arr != null){
			for(String s : arr){
				permIdList.add(Long.parseLong(s));
			}
		}
		ebkUserPermissionService.update(user.getUserId(),permIdList);
		petComLogService.insert("EBK_USER", null, user.getUserId(), getSessionUser().getUserName(), null, 
				"创建用户", "[用户基本信息：" + user.getUserInfoStr() + "][用户权限ID：" + permIds + "]", null);
		return "update_user_success";
	}
	
	public EbkUserService getEbkUserService() {
		return ebkUserService;
	}
	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd1() {
		return newPwd1;
	}
	public void setNewPwd1(String newPwd1) {
		this.newPwd1 = newPwd1;
	}
	public String getNewPwd2() {
		return newPwd2;
	}
	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2;
	}
	public Page<EbkUser> getEbkUserPage() {
		return ebkUserPage;
	}
	public void setEbkUserPage(Page<EbkUser> ebkUserPage) {
		this.ebkUserPage = ebkUserPage;
	}
	public EbkUser getUser() {
		return user;
	}
	public void setUser(EbkUser user) {
		this.user = user;
	}
	public EbkPermissionService getEbkPermissionService() {
		return ebkPermissionService;
	}
	public void setEbkPermissionService(EbkPermissionService ebkPermissionService) {
		this.ebkPermissionService = ebkPermissionService;
	}
	public String getPermIds() {
		return permIds;
	}
	public void setPermIds(String permIds) {
		this.permIds = permIds;
	}
	public EbkUserPermissionService getEbkUserPermissionService() {
		return ebkUserPermissionService;
	}
	public void setEbkUserPermissionService(
			EbkUserPermissionService ebkUserPermissionService) {
		this.ebkUserPermissionService = ebkUserPermissionService;
	}
	public ComLogService getPetComLogService() {
		return petComLogService;
	}
	public void setPetComLogService(ComLogService petComLogService) {
		this.petComLogService = petComLogService;
	}
	public String getPassword_short() {
		return password_short;
	}
	public void setPassword_short(String password_short) {
		this.password_short = password_short;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
