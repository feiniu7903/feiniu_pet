package com.lvmama.bee.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.service.ebooking.EbkAnnouncementService;
import com.lvmama.comm.bee.service.eplace.EbkPermissionService;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.pic.ValidateCodeGenerator;
import com.lvmama.comm.utils.pic.ValidateCodeGenerator.ValidateCode;
import com.lvmama.comm.vo.Constant;

@Results(value={
		@Result(name="login",location="/WEB-INF/pages/login.jsp"),
		@Result(name="adminlogin",location="/WEB-INF/pages/admin/adminlogin.jsp"),
		@Result(name="adminindex",location="admin/supplierList.do",type="redirect"),
		@Result(name="index",location="/WEB-INF/pages/index.jsp"),
		@Result(name="to_login",type="redirect", location="login.do"),
		@Result(name="to_change_pwd",type="redirect", 
		location="/ebk_user/to_change_pwd.do?password_short=true")
	})
public class LoginAction extends EbkBaseAction{
	private static final long serialVersionUID = 1L;
	
	private EbkUserService ebkUserService;
	private EbkPermissionService ebkPermissionService;
	private SupplierService supplierService;
	private EbkAnnouncementService ebkAnnouncementService;
	private EbkUser user;
	private String validateCode;
	
	@Action("/login")
	public String login(){
		if(user == null){
			getSystemAnnouncement();
			setRequestAttribute("loginError", null);
			return "login";
		}
		EbkUser u = ebkUserService.getEbkUserByUserName(user.getUserName());
		EbkUser parent = null;
		if(u != null && "false".equals(u.getIsAdmin())){
			parent = ebkUserService.getEbkUserById(u.getParentUserId());
		}
		String isTestingEnvironment  = Constant.getInstance().getProperty("isTestingEnvironment");
		if(u == null 
				|| "false".equals(u.getValid())
				|| StringUtil.isEmptyString(user.getPassword())
				|| !u.getPassword().equals(MD5.code(user.getPassword(), MD5.KEY_EBK_USER_PASSWORD))
				|| ("false".equals(u.getIsAdmin()) && (parent == null || "false".equals(parent.getValid()))))
		{
			setRequestAttribute("loginError", "用户名或密码错误");
			getSystemAnnouncement();
			return "login";
		}else if(!"true".equals(isTestingEnvironment) && (StringUtil.isEmptyString((String)getSession(Constant.SESSION_EBOOKING_VALIDATE_CODE))
				|| !getSession(Constant.SESSION_EBOOKING_VALIDATE_CODE).equals(validateCode))){
			setRequestAttribute("loginError", "验证码错误");
			getSystemAnnouncement();
			return "login";
		}else{
			removeSession(Constant.SESSION_EBOOKING_VALIDATE_CODE);
			if("false".equals(u.getIsAdmin())){
				EbkUser admin = ebkUserService.getEbkUserById(u.getParentUserId());
//				u.setBizType(admin.getBizType());
				u.setLvmamaContactName(admin.getLvmamaContactName());
				u.setLvmamaContactPhone(admin.getLvmamaContactPhone());
				u.setSupplierId(admin.getSupplierId());
			}
			SupSupplier supplier = supplierService.getSupplier(u.getSupplierId());
			u.setSupplierName(supplier.getSupplierName());
			putSession(Constant.SESSION_EBOOKING_USER, u);
			//加载页面元素权限和产品权限
			putSession(Constant.Session_EBOOKING_USER_PERMISSION_LIST, ebkPermissionService.getEbkPermissionByUserId(u.getUserId()));

			if("true".equals(u.getIsAdmin())){
				putSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST, ebkUserService.getEbkUserMetaBranchIds(u.getUserId()));
			}else {
				putSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST, ebkUserService.getEbkUserMetaBranchIds(u.getParentUserId()));
			}
			if(u.getPassword().equals(MD5.code("111111", MD5.KEY_EBK_USER_PASSWORD))){
				return "to_change_pwd";
			}
			return "index";
		}
	}
	
	/**
	 * 用于lvmama管理员登录，以查询、校验各供应商的订单信息
	 * 这里使用了虚拟账户登录（指定的但不存在于数据库中），然后根据所先把的供应商不同，然后使用各供应商的管理员账户登录
	 * @return
	 */
	@Action("/adminlogin")
	public String adminlogin() {
		
		String adminUserName = Constant.getInstance().getProperty("ebooking.lvmama.admin.username");
		String password = Constant.getInstance().getProperty("ebooking.lvmama.admin.password");
		
		String sid = getRequest().getParameter("sid");
		if(StringUtil.isNotEmptyString(sid)){
			EbkUser lvmamaAdmin = getLvmamaSessionUser();
			if(lvmamaAdmin == null){
				return "adminlogin"; 
			}
			if(lvmamaAdmin.getUserName().equals(adminUserName) && lvmamaAdmin.getPassword().equals(password)){
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("supplierId", sid);
				
				List<EbkUser> ebkuserList = ebkUserService.getEbkUser(params);
				EbkUser loginuser = null; 
				for(EbkUser ebkuser : ebkuserList){
					if("true".equals(ebkuser.getIsAdmin())){
						loginuser = ebkuser;
						break;
					}
				}
				if(loginuser == null){
					for(EbkUser ebkuser : ebkuserList){
						if(ebkuser.getParentUserId() != null ){
							loginuser = ebkUserService.getEbkUserById(ebkuser.getParentUserId());
							break;
						}
					}
				}
				if(loginuser==null){
					return "adminindex";
				}
				
				EbkUser u = loginuser;
				
				removeSession(Constant.SESSION_EBOOKING_VALIDATE_CODE);
				if("false".equals(u.getIsAdmin())){
					EbkUser admin = ebkUserService.getEbkUserById(u.getParentUserId());
					u.setLvmamaContactName(admin.getLvmamaContactName());
					u.setLvmamaContactPhone(admin.getLvmamaContactPhone());
					u.setSupplierId(admin.getSupplierId());
				}
				SupSupplier supplier = supplierService.getSupplier(u.getSupplierId());
				u.setSupplierName(supplier.getSupplierName());
				putSession(Constant.SESSION_EBOOKING_USER, u);
				//加载页面元素权限和产品权限
				putSession(Constant.Session_EBOOKING_USER_PERMISSION_LIST, ebkPermissionService.getEbkPermissionByUserId(u.getUserId()));

				if("true".equals(u.getIsAdmin())){
					putSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST, ebkUserService.getEbkUserMetaBranchIds(u.getUserId()));
				}else {
					putSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST, ebkUserService.getEbkUserMetaBranchIds(u.getParentUserId()));
				}
				return "index";
			
			}else{
				return "adminlogin"; 
			}
		}
		
		if (user == null) {
			getSystemAnnouncement();
			setRequestAttribute("loginError", null);
			return "adminlogin";
		}
		
		if (StringUtil.isEmptyString((String) getSession(Constant.SESSION_EBOOKING_VALIDATE_CODE)) || 
				!getSession(Constant.SESSION_EBOOKING_VALIDATE_CODE).equals(validateCode)) {
			setRequestAttribute("loginError", "验证码错误");
			getSystemAnnouncement();
			return "adminlogin";
		}else if (!(user.getUserName().equals(adminUserName) && user.getPassword().equals(password))) {
			setRequestAttribute("loginError", "用户名或密码错误");
			getSystemAnnouncement();
			return "adminlogin";
		} else {
			removeSession(Constant.SESSION_EBOOKING_VALIDATE_CODE);
			putSession(Constant.SESSION_EBOOKING_USER_LVMAMA, user);
			putSession(Constant.SESSION_EBOOKING_USER, user);
			return "adminindex";
		}
	}
	
	
	
	@Action("/login/validate_code")
	public void validateCode(){
		try {
			getResponse().setHeader("Pragma","No-cache");
			getResponse().setHeader("Cache-Control","no-cache");
			getResponse().setDateHeader("Expires", 0);
			ValidateCode vc = ValidateCodeGenerator.generateNumberValidateCode();
			putSession(Constant.SESSION_EBOOKING_VALIDATE_CODE,vc.getCode());
			ImageIO.write(vc.getImage(), "JPEG",getResponse().getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
			getResponse().flushBuffer();
		}
		catch(IllegalStateException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Action("/loginOut")
	public String loginOut(){
		removeSession(Constant.SESSION_EBOOKING_USER);
		removeSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST);
		return "to_login";
	}
	@Action("/index")
	public String index(){
		if(isLogined()) {
			return "index";
		} else {
			return "to_login";
		}
	}
	
	/**
	 * 获取最近三条系统公告
	 */
	private void getSystemAnnouncement(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", new Date());
		params.put("orderByBeginDateDesc", "true");
		params.put("start", 1);
		params.put("end", 5);
		setRequestAttribute("announceList", ebkAnnouncementService.findEbkAnnouncementListByMap(params));
	}

	public EbkUserService getEbkUserService() {
		return ebkUserService;
	}

	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}

	public EbkUser getUser() {
		return user;
	}

	public void setUser(EbkUser user) {
		this.user = user;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public EbkPermissionService getEbkPermissionService() {
		return ebkPermissionService;
	}

	public void setEbkPermissionService(EbkPermissionService ebkPermissionService) {
		this.ebkPermissionService = ebkPermissionService;
	}

	public EbkAnnouncementService getEbkAnnouncementService() {
		return ebkAnnouncementService;
	}

	public void setEbkAnnouncementService(
			EbkAnnouncementService ebkAnnouncementService) {
		this.ebkAnnouncementService = ebkAnnouncementService;
	}


}
