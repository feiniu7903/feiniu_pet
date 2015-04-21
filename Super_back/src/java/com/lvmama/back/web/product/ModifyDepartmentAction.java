package com.lvmama.back.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

import com.lvmama.back.web.BaseAction;
import com.lvmama.back.web.permission.treeUtil.MyTreeRenderer;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;

/**
 * @author shihui
 * 
 *         批量修改所在部门
 * */
public class ModifyDepartmentAction extends BaseAction {

	private static final long serialVersionUID = -281516666949756966L;

	private MyTreeRenderer renderer = new MyTreeRenderer();

	private ProdProductService prodProductService;

	private PermOrganizationService permOrganizationService;

	private List<PermOrganization> departmentsList;

	private List<Long> selectedProductIds = new ArrayList<Long>();
	
	private MetaProductService metaProductService;

	private int managerCount;

	private Tree tree;

	private Long orgId;
	
	private List<Long> selectedMetaIds = new ArrayList<Long>();
	
	@Override
	protected void doAfter() throws Exception {
		departmentsList = permOrganizationService.selectAllOrganization();
		renderer.refreshTree(tree, departmentsList, null);
		departmentsList.add(0, new PermOrganization());
	}

	/**
	 * 记录选择的部门
	 * */
	public void selectDepartment() {
		Treeitem treeItem = tree.getSelectedItem();
		orgId = (Long) treeItem.getAttribute("id");
		String name = treeItem.getAttribute("name").toString();
		alert("您选择了 " + name + " ！");
	}

	public void doUpdate() {
		if(orgId == null) {
			alert("请选择部门!");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		if (selectedProductIds != null && selectedProductIds.size() > 0) {
			params.put("productIds", selectedProductIds);
			prodProductService.updateOrgIds(params);
			selectedProductIds.clear();
		}
		if(selectedMetaIds != null & selectedMetaIds.size() > 0) {
			params.put("metaProductIds", selectedMetaIds);
			metaProductService.updateOrgIds(params);
			selectedMetaIds.clear();
		}
		alert("修改成功！");
		refreshParent("search");
		this.managerCount = 0;
		closeWindow();
	}

	public List<PermOrganization> getDepartmentsList() {
		return departmentsList;
	}

	public void setPermOrganizationService(PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}

	public int getManagerCount() {
		return managerCount;
	}

	public void setManagerCount(int managerCount) {
		this.managerCount = managerCount;
	}

	public void setSelectedProductIds(List<Long> selectedProductIds) {
		this.selectedProductIds = selectedProductIds;
	}
 
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setSelectedMetaIds(List<Long> selectedMetaIds) {
		this.selectedMetaIds = selectedMetaIds;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

}
