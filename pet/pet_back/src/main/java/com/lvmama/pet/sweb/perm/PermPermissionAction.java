package com.lvmama.pet.sweb.perm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.service.perm.PermissionService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;

/**
 * 权限管理
 * @author yanggan
 **/
@ParentPackage("json-default")
@Results(value={
	@Result(name="index",location="/WEB-INF/pages/back/perm/perm_permission_index.jsp"),
	@Result(name="to_add_top_menu",location="/WEB-INF/pages/back/perm/perm_permission_to_add_top_menu.jsp"),
	@Result(name="to_edit",location="/WEB-INF/pages/back/perm/perm_permission_to_edit.jsp"),
	@Result(name="to_add_child_menu",location="/WEB-INF/pages/back/perm/perm_permission_to_add_child_menu.jsp"),
	@Result(name="child_list",location="/WEB-INF/pages/back/perm/perm_permission_child_list.jsp"),
	
	@Result(name="success",type="json",params={"root","result"})
})
@Namespace(value="/perm_permission")
public class PermPermissionAction extends BackBaseAction {
	private PermissionService permissionService;
	private Page<PermPermission> permPermissionPage = new Page<PermPermission>();
	private PermPermission permPermission;
	List<PermPermission> gategoryList;
	/*查询条件-权限名称*/
	private String name;
	/*查询条件-级别*/
	private String permLevel;
	/*查询条件-类型称*/
	private String type;
	/*查询条件-类别*/
	private String category;
	/*查询条件-父类ID*/
	private String parentId;
	/*查询条件-是否删除*/
	private String valid;
	/*权限ID*/
	private Long permissionId;
	
	private String result;
	/**
	 * 进入权限管理页面
	 * @return
	 */
	@Action("index")
	public String index(){
		gategoryList = this.permissionService.queryPermPermissionByPermLevel();
		for (int i = 0; i < gategoryList.size(); i++) {
			PermPermission p=(PermPermission)gategoryList.get(i);
			if(!StringUtil.isEmptyString(category)&&category.equals(p.getCategory())){
				p.setSelected(true);
			}
		}
		return "index";
	}
	
	/**
	 * 查询权限
	 * @return
	 */
	@Action("search_permission")
	public String searchPermission(){
		Map<String , Object> params = new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(name)){
			params.put("name", name);
		}
		if(!StringUtil.isEmptyString(permLevel)){
			params.put("permLevel", permLevel);
		}
		if(!StringUtil.isEmptyString(type)){
			params.put("type", type);
		}
		if(!StringUtil.isEmptyString(category)){
			params.put("category", category);
		}
		if(!StringUtil.isEmptyString(parentId)){
			params.put("parentId", parentId);
		}
		if(!StringUtil.isEmptyString(valid)){
			params.put("valid", "N");
		}
		permPermissionPage.setTotalResultSize(permissionService.queryPermPermissionByParamCount(params));
		permPermissionPage.buildUrl(getRequest());
		permPermissionPage.setCurrentPage(super.page);
		params.put("skipResults", permPermissionPage.getStartRows());
		params.put("maxResults", permPermissionPage.getEndRows());
		permPermissionPage.setItems(permissionService.queryPermPermissionByParam(params));
		return index();
	}
	/**
	 * 逻辑删除指定的权限. 
	 * @param permissionId
	 */
	@Action("delete")
	public String delPermPermission(){
		try {
			PermPermission perm=this.permissionService.getPermPermissionByPK(permissionId);
			perm.setValid("N");
			this.permissionService.udpatePermission(perm);
			result = "success";
		} catch (Exception e) {
			result = "fail";
			log.debug("faild delPermPermission By permissionId:"+permissionId+" "+e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 修改权限页面
	 * @return
	 */
	@Action("to_edit")
	public String toEdit(){
		permPermission=this.permissionService.getPermPermissionByPK(permissionId);
		category = this.permPermission.getCategory();
		this.index();
		return "to_edit";
	}
	@Action("edit")
	public String edit(){
		try {
			this.permissionService.udpatePermission(permPermission);
			result = "success";
		} catch (Exception e) {
			result = "fail";
			log.debug("faild updatePermPermission :"+permPermission.getPermissionId()+" "+e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 新增一级菜单页面
	 * @return
	 */
	@Action("to_add_top_menu")
	public String toAddTopMenu(){
		this.index();
		return "to_add_top_menu";
	}
	/**
	 * 保存一级菜单
	 * @return
	 */
	@Action("save_top_menu")
	public String saveTopMenu(){
		if(permPermission.getEmbed() == null){
			permPermission.setEmbed("N");
		}
		permPermission.setValid("Y");
		permPermission.setPermLevel("1");
		permPermission.setType("URL");
		if (this.permissionService.insertPermission(permPermission) != null) {
			result = "success";
		}else{
			result = "fail";
		}
		return "success";
	}
	
	/**
	 * 新增子菜单页面
	 * @return
	 */
	@Action("to_add_child_menu")
	public String toAddChildMenu(){
		permPermission=this.permissionService.getPermPermissionByPK(permissionId);
		this.index();
		return "to_add_child_menu";
	}
	/**
	 * 保存子菜单
	 * @return
	 */
	@Action("save_child_menu")
	public String saveChildMenu(){
		if("1".equals(this.permPermission.getPermLevel())){
			permPermission.setEmbed("N");
		}else{
			permPermission.setEmbed("Y");
		}
		permPermission.setType("URL");
		permPermission.setValid("Y");
		if (this.permissionService.insertPermission(permPermission) != null) {
			result = "success";
		}else{
			result = "fail";
		}
		return SUCCESS;
	}
	/**
	 * 查询子菜单
	 * @return
	 */
	@Action("view_child_list")
	public String viewChildList(){
		Map<String,Object> serachPermMap=new HashMap<String,Object>();
		serachPermMap.put("permissionId", permissionId);
		serachPermMap.put("type",type);
		Long totalRecords =permissionService.selectSecondItemsByPermissionIdCount(serachPermMap);
		permPermissionPage.setTotalResultSize(totalRecords);
		permPermissionPage.buildUrl(getRequest());
		permPermissionPage.setCurrentPage(super.page);
		serachPermMap.put("skipResults", permPermissionPage.getStartRows());
		serachPermMap.put("maxResults", permPermissionPage.getEndRows());
		List<PermPermission> permPermissionList = permissionService.selectSecondItemsByPermissionId(serachPermMap);	
		permPermissionPage.setItems(permPermissionList);
		return "child_list";
	}
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public List<PermPermission> getGategoryList() {
		return gategoryList;
	}

	public Page<PermPermission> getPermPermissionPage() {
		return permPermissionPage;
	}

	public PermPermission getPermPermission() {
		return permPermission;
	}

	public void setPermPermission(PermPermission permPermission) {
		this.permPermission = permPermission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermLevel() {
		return permLevel;
	}

	public void setPermLevel(String permLevel) {
		this.permLevel = permLevel;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getResult() {
		return result;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	
}
