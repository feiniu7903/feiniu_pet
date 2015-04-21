/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.Pagination;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.meta.ProductControlRole;
import com.lvmama.comm.bee.service.meta.ProductControlRoleService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author zuoxiaoshuai
 *
 */
@Results({
	@Result(name="toControlRoleList",location="/WEB-INF/pages/back/meta/product_ct_role_list.jsp"),
	@Result(name="toControlRoleEditor",location="/WEB-INF/pages/back/meta/add_product_control_role.jsp")
})
public class ProductControlRoleAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProductControlRole> roleList;
	
	private ProductControlRoleService productControlRoleService;
	
	private WorkGroupService workGroupService;
	
	private Map<String,Object> searchConds = new HashMap<String, Object>();
	
	private ProductControlRole condition;
	
	private ProductControlRole role;
	
	private List<WorkGroup> groupNameList;
	
	private List<PermUser> userList;
	
	@Action("/meta/controlRoleIndex")
	public String toControlRoleIndex() {
		return "toControlRoleList";
	}
	
	@Action("/meta/toControlRoleList")
	public String searchControlRoleList() {
		searchConds = initParam();
		toResult(searchConds, true);
		searchConds.put("page", pagination.getPage());
		searchConds.put("perPageRecord", pagination.getPerPageRecord());
		getSession().setAttribute("META_GO_UPSTEP_URL", searchConds);
		return "toControlRoleList";
	}
	
	@Action("/meta/editControlRole")
	public String editControlRole() {
		groupNameList = workGroupService.queryWorkGroupName();
		if (role.getProductControlRoleId() != null) {
			role = productControlRoleService.getRoleByPrimaryKey(role);
			if (Constant.PRODUCT_CONTROL_ROLE.ROLE_MANAGER.name().equals(role.getRoleArea())) {
				userList = productControlRoleService.getUserListByRole(role);
			}
		}
		return "toControlRoleEditor";
	}
	
	@Action("/meta/saveControlRole")
	public void saveControlRole() {
		JSONResult result=new JSONResult();
		result.put("opType", role.getProductControlRoleId() == null ? "NEW" : "MODIFY");
		try{
			productControlRoleService.saveControlRole(role);
		} catch(Exception ex){
			result.raise(ex);
		}
		result.outputJSON(getResponse());
	}
	
	@Action("/meta/deleteControlRole")
	public void deleteControlRole() {
		JSONResult result=new JSONResult();
		try{
			productControlRoleService.deleteControlRole(role);
		} catch(Exception ex){
			result.raise(ex);
		}
		result.outputJSON(getResponse());
	}
	
	private String toResult(Map<String,Object> searchConds,boolean autoReq) {
		Long totalRowCount = productControlRoleService.countRoleByCondition(searchConds);
		if(autoReq){
			pagination = super.initPagination();
		}else{
			pagination = new Pagination();
			pagination.setPage((Integer)searchConds.get("page"));
			pagination.setPerPageRecord((Integer)searchConds.get("perPageRecord"));
		}
		pagination.setTotalRecords(totalRowCount);
		searchConds.put("_startRow", pagination.getFirstRow());
		searchConds.put("_endRow", pagination.getLastRow());
		roleList = productControlRoleService.getRoleListByCondition(searchConds);
		pagination.setRecords(roleList);
		if(autoReq){
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		}else{
			pagination.setActionUrl(WebUtils.getUrl("/meta/searchBtsWarningList.do", true, searchConds));
		}
		return "toBtsWarningList";
	}
	
	private Map<String,Object> initParam() {
		if (condition != null) {
			searchConds.put("userName", getLikeValue(condition.getUserName()));
		}
		return searchConds;
	}
	
	private String getLikeValue(String str) {
		return StringUtils.isEmpty(str) ? null : "%" + StringUtils.trim(str) + "%";
	}

	public void setProductControlRoleService(
			ProductControlRoleService productControlRoleService) {
		this.productControlRoleService = productControlRoleService;
	}

	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}

	public List<ProductControlRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<ProductControlRole> roleList) {
		this.roleList = roleList;
	}

	public ProductControlRole getCondition() {
		return condition;
	}

	public void setCondition(ProductControlRole condition) {
		this.condition = condition;
	}

	public ProductControlRole getRole() {
		return role;
	}

	public void setRole(ProductControlRole role) {
		this.role = role;
	}

	public List<WorkGroup> getGroupNameList() {
		return groupNameList;
	}

	public void setGroupNameList(List<WorkGroup> groupNameList) {
		this.groupNameList = groupNameList;
	}

	public List<PermUser> getUserList() {
		return userList;
	}

	public void setUserList(List<PermUser> userList) {
		this.userList = userList;
	}
}
