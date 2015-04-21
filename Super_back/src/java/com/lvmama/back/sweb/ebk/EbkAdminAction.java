package com.lvmama.back.sweb.ebk;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.eplace.EbkPermission;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.po.eplace.EbkUserTarget;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.service.eplace.EbkPermissionService;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.bee.service.eplace.EbkUserTargetService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.ZTreeNode;


@Namespace("/ebk/admin")
@Results(value={@Result(name="admin_supplier_index",location="/WEB-INF/pages/back/ebk/admin_supplier.jsp"),
				@Result(name="search",location="/WEB-INF/pages/back/ebk/admin_index.jsp"),
				@Result(name="to_add_admin",location="/WEB-INF/pages/back/ebk/admin_add.jsp"),
				@Result(name="to_edit_admin",location="/WEB-INF/pages/back/ebk/admin_edit.jsp"),
				@Result(name="to_admin_product_eplace",location="/WEB-INF/pages/back/ebk/admin_product_eplace.jsp"),
				@Result(name="to_admin_product_ebooking",location="/WEB-INF/pages/back/ebk/admin_product_ebooking.jsp"),
				@Result(name="to_add_admin_product",location="/WEB-INF/pages/back/ebk/admin_product_add.jsp"),
				@Result(name="to_show_user_menu",location="/WEB-INF/pages/back/ebk/admin_user_menu.jsp"),
				@Result(name="back_to_admin_supplier",type="redirectAction",location="index.do",
						params={"supName","${supName}","lvmamaContactName","${lvmamaContactName}","page","${page}"}),
				@Result(name="performTargetList",location="/WEB-INF/pages/back/ebk/performTargetList.jsp"),
				@Result(name="searchMetaProduct",location="/WEB-INF/pages/back/ebk/metaProductList.jsp")
		})
public class EbkAdminAction extends BackBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4968675045395752968L;
	private final String PASSWORD = "111111";
	private SupplierService supplierService;
	private EbkUserService ebkUserService;
	private ComLogService petComLogService;
	private MetaProductBranchService metaProductBranchService;
	private EbkPermissionService ebkPermissionService;
	private EbkUserTargetService ebkUserTargetService;
	private PerformTargetService performTargetService;
	private MetaProductService metaProductService;
	
	private Page<EbkUser> ebkUserPage = new Page<EbkUser>();
	private Page<MetaProductBranch> ebkMetaBranchPage = new Page<MetaProductBranch>();
	private Page<SupSupplier> supPage = new Page<SupSupplier>();
	private List<SupPerformTarget> performTargetList;
	private List<EbkUserTarget> userTargetList;
	private Page<MetaProduct> metaProductPage = new Page<MetaProduct>();;
	
	private String supId;
	private String supName;
	private EbkUser admin;
	private String lvmamaContactName;
	private Long userTargetId;
	private Long supplierId;
	private Long userId;
	private Long targetId;
	
	
	@Action("index")
	public String index(){
		Map<String, Object> map = new HashMap<String, Object>();
		supName = URLDecoder.decode(URLDecoder.decode(supName==null?"":supName));
		map.put("supName", supName);
		lvmamaContactName = URLDecoder.decode(URLDecoder.decode(lvmamaContactName==null?"":lvmamaContactName));
		map.put("lvmamaContactName", lvmamaContactName);
		supPage.setTotalResultSize(ebkUserService.getEbkSupplierCount(map));
		supPage.buildUrl(getRequest());
		supPage.setCurrentPage(super.page);
		map.put("start", supPage.getStartRows());
		map.put("end", supPage.getEndRows());
		supPage.setItems(ebkUserService.getEbkSupplier(map));
		setRequestAttribute("supPage", supPage);
		putSession("adminSearchSupName", supName);
		putSession("adminSearchLvmamaContactName", lvmamaContactName);
		putSession("adminSearchPage", super.page);
		return "admin_supplier_index";
	}
	/**
	 * 查询供应商下的admin账号
	 * @return
	 * @throws Exception 
	 */
	@Action("admin_search")
	public String search() throws Exception{
		supName = URLDecoder.decode(URLDecoder.decode(supName));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", supId);
		ebkUserPage.setTotalResultSize(ebkUserService.getEbkUserCount(params));
		ebkUserPage.buildUrl(getRequest());
		ebkUserPage.setCurrentPage(super.page);
		params.put("start", ebkUserPage.getStartRows());
		params.put("end", ebkUserPage.getEndRows());
		ebkUserPage.setItems(ebkUserService.getEbkUser(params));
		setRequestAttribute("ebkUserPage", ebkUserPage);
		return "search";
	}
	/**
	 * suggest
	 * 查找供应商下拉列表
	 */
	@Action("get_supplier_list")
	public void getSupplier(){
		Map<String,Object> param = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(getRequestParameter("supName"))) {
			param.put("supplierName", getRequestParameter("supName"));
		}
		param.put("_endRow", 10);
		List<SupSupplier> list = supplierService.getSupSuppliers(param);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(SupSupplier ss:list){
				JSONObject obj=new JSONObject();
				obj.put("id", ss.getSupplierId());
				obj.put("text", ss.getSupplierName()+"("+ss.getSupplierId()+")");
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	@Action("back_to_admin_supplier")
	public String backToAdminSupplier(){
		supName = URLEncoder.encode(URLEncoder.encode((String)getSession("adminSearchSupName")));
		lvmamaContactName = URLEncoder.encode(URLEncoder.encode((String)getSession("adminSearchLvmamaContactName")));
		page = (Long)getSession("adminSearchPage");
		removeSession("adminSearchSupName");
		removeSession("adminSearchLvmamaContactName");
		removeSession("adminSearchPage");
		return "back_to_admin_supplier";
	}
	
	@Action("to_add_admin")
	public String toAddAdmin() throws Exception{
		supName=URLDecoder.decode(URLDecoder.decode(supName));
		return "to_add_admin";
	}
	/**
	 * 验证用户名是否存在
	 */
	@Action("is_exist_username")
	public void isExistUsername(){
		if(null == ebkUserService.getEbkUserByUserName(getRequestParameter("userName"))){
			sendAjaxMsg("true");
		}else{
			sendAjaxMsg("false");
		}
	}
	/**
	 * 保存admin账号
	 */
	@Action("save_admin")
	public void saveAdmin(){
		if(admin.getUserId() == null){		// new
			admin.setPassword(MD5.code(PASSWORD,MD5.KEY_EBK_USER_PASSWORD));
			admin.setCreateTime(new Date());
			admin.setIsAdmin("true");
			Long id = ebkUserService.insert(admin);
			petComLogService.insert("EBK_USER", null, id, getSessionUser().getUserName(), null, "创建用户", "创建用户", null);
		}else{	//update
			String logContent="";
			EbkUser oldUser = ebkUserService.getEbkUserById(admin.getUserId());
			logContent = logContent + "【原用户信息：" + oldUser.getAdminInfoStr() + "】";
			ebkUserService.updateEbkUserById(admin);
			EbkUser newUser = ebkUserService.getEbkUserById(admin.getUserId());
			logContent = logContent + "<br />【更新后用户信息：" + newUser.getAdminInfoStr() + "】<br />&nbsp;";
			petComLogService.insert("EBK_USER", null, admin.getUserId(), getSessionUser().getUserName(), null, "更新用户", logContent, null);
		}
		sendAjaxMsg("success");
	}
	/**
	 * 初始化密码
	 */
	@Action("init_password")
	public void initPassword(){
		ebkUserService.changePassword(Long.parseLong(getRequestParameter("adminId")),MD5.code(PASSWORD,MD5.KEY_EBK_USER_PASSWORD));
		petComLogService.insert("EBK_USER", null, Long.parseLong(getRequestParameter("adminId")), getSessionUser().getUserName(), null, "初始化密码", "初始化密码", null);
		sendAjaxMsg("success");
	}
	@Action("to_edit_admin")
	public String toEditAdmin(){
		admin = ebkUserService.getEbkUserById(Long.parseLong(getRequestParameter("userId")));
		SupSupplier supplier = supplierService.getSupplier(admin.getSupplierId());
		admin.setSupplierName(supplier.getSupplierName());
		return "to_edit_admin";
	}
		
	@Action("to_add_admin_product")
	public String toAddUserProduct(){
		setRequestAttribute("userId", getRequestParameter("userId"));
		setRequestAttribute("supplierId", getRequestParameter("supplierId"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", getRequestParameter("supplierId"));
		if(!StringUtil.isEmptyString(getRequestParameter("metaProductId"))){
			params.put("metaProductId", getRequestParameter("metaProductId"));
			setRequestAttribute("metaProductId", getRequestParameter("metaProductId"));
		}
		if(!StringUtil.isEmptyString(getRequestParameter("metaProductName"))){
			params.put("metaProductName", getRequestParameter("metaProductName"));
			setRequestAttribute("metaProductName", getRequestParameter("metaProductName"));
		}
		params.put("ebkUserId", getRequestParameter("userId"));
		params.put("isSearchEplaceProduct", "1");
		ebkMetaBranchPage.setTotalResultSize(metaProductBranchService.getEbkMetaBranchCount(params));
		ebkMetaBranchPage.buildUrl(getRequest());
		ebkMetaBranchPage.setCurrentPage(super.page);
		params.put("start", ebkMetaBranchPage.getStartRows());
		params.put("end", ebkMetaBranchPage.getEndRows());
		ebkMetaBranchPage.setItems(metaProductBranchService.getEbkMetaBranch(params));
		return "to_add_admin_product";
	}
	
	
	@Action("to_show_user_menu")
	public String showUserMenu(){
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
		setRequestAttribute("treeData",JSONArray.fromObject(nodeList).toString());
		return "to_show_user_menu";
	}
	
	@Action("bindingTarget")
	public void bindingTarget(){
		EbkUserTarget ebkUserTarget = new EbkUserTarget();
		ebkUserTarget.setUserId(userId);
		ebkUserTarget.setSupPerformTargetId(targetId);
		
		ebkUserTargetService.insert(ebkUserTarget);
		petComLogService.insert("EBK_USER", null, userId, getSessionUser().getUserName(), null, "E景通用户绑定到履行对象("+targetId+")", "E景通用户绑定到履行对象", null);
		sendAjaxMsg("success");
	}
	
	@Action("deleteBindingTarget")
	public void deleteBindingTarget(){
		EbkUserTarget ebkUserTarget = new EbkUserTarget();
		ebkUserTarget.setUserId(userId);
		ebkUserTarget.setSupPerformTargetId(targetId);
		if(userId!=null && targetId!=null){
			ebkUserTargetService.delete(ebkUserTarget);
			petComLogService.insert("EBK_USER", null, userId, getSessionUser().getUserName(), null, "删除E景通用户与履行对象("+targetId+")绑定关系", "删除E景通用户与履行对象绑定关系", null);
			sendAjaxMsg("success");
		}
	}
	
	@Action("performTargetList")
	public String performTargetList(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("supplierId", Long.valueOf(supplierId));
		performTargetList = performTargetService.findSupPerformTarget(params);
		
		Map<String,Object> userTargetParams = new HashMap<String,Object>();
		userTargetParams.put("userId", userId);
		userTargetList = ebkUserTargetService.getEbkUserTargetList(userTargetParams);
		return "performTargetList";
	}
	
	@Action("searchMetaProduct")
	public String searchMetaProduct(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("targetId", targetId);
		
		metaProductPage.setTotalResultSize(metaProductService.getMetaProductListByPerformTargetIdCount(params));
		metaProductPage.buildUrl(getRequest());
		metaProductPage.setCurrentPage(super.page);
		params.put("_startRow", metaProductPage.getStartRows());
		params.put("_endRow", metaProductPage.getEndRows());
		
		metaProductPage.setItems(metaProductService.getMetaProductListByPerformTargetId(params));
		
		return "searchMetaProduct";
	}
	
	@Action("canPrint")
	public void canPrint(){
		EbkUser user = ebkUserService.getEbkUserById(Long.parseLong(getRequestParameter("userId")));
		user.setCanPrint(getRequestParameter("print"));
		ebkUserService.updateEbkUserById(user);
		
		sendAjaxMsg("success");
	}
	
	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public String getSupId() {
		return supId;
	}

	public void setSupId(String supId) {
		this.supId = supId;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public EbkUserService getEbkUserService() {
		return ebkUserService;
	}

	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}

	public Page<EbkUser> getEbkUserPage() {
		return ebkUserPage;
	}

	public void setEbkUserPage(Page<EbkUser> ebkUserPage) {
		this.ebkUserPage = ebkUserPage;
	}

	public EbkUser getAdmin() {
		return admin;
	}

	public void setAdmin(EbkUser admin) {
		this.admin = admin;
	}

	public ComLogService getPetComLogService() {
		return petComLogService;
	}

	public void setPetComLogService(ComLogService petComLogService) {
		this.petComLogService = petComLogService;
	}

	public MetaProductBranchService getMetaProductBranchService() {
		return metaProductBranchService;
	}
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
	public Page<MetaProductBranch> getEbkMetaBranchPage() {
		return ebkMetaBranchPage;
	}

	public void setEbkMetaBranchPage(Page<MetaProductBranch> ebkMetaBranchPage) {
		this.ebkMetaBranchPage = ebkMetaBranchPage;
	}

	public String getLvmamaContactName() {
		return lvmamaContactName;
	}
	public void setLvmamaContactName(String lvmamaContactName) {
		this.lvmamaContactName = lvmamaContactName;
	}
	public Page<SupSupplier> getSupPage() {
		return supPage;
	}
	public void setSupPage(Page<SupSupplier> supPage) {
		this.supPage = supPage;
	}
	public EbkPermissionService getEbkPermissionService() {
		return ebkPermissionService;
	}
	public void setEbkPermissionService(EbkPermissionService ebkPermissionService) {
		this.ebkPermissionService = ebkPermissionService;
	}
	public void setEbkUserTargetService(EbkUserTargetService ebkUserTargetService) {
		this.ebkUserTargetService = ebkUserTargetService;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public Long getUserTargetId() {
		return userTargetId;
	}
	public void setUserTargetId(Long userTargetId) {
		this.userTargetId = userTargetId;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public List<SupPerformTarget> getPerformTargetList() {
		return performTargetList;
	}
	public void setPerformTargetList(List<SupPerformTarget> performTargetList) {
		this.performTargetList = performTargetList;
	}
	public List<EbkUserTarget> getUserTargetList() {
		return userTargetList;
	}
	public void setUserTargetList(List<EbkUserTarget> userTargetList) {
		this.userTargetList = userTargetList;
	}
	public MetaProductService getMetaProductService() {
		return metaProductService;
	}
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	public Page<MetaProduct> getMetaProductPage() {
		return metaProductPage;
	}
	public void setMetaProductPage(Page<MetaProduct> metaProductPage) {
		this.metaProductPage = metaProductPage;
	}

}
