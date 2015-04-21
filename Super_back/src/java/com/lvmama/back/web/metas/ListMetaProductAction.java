package com.lvmama.back.web.metas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductItemService;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.vo.Constant;

public class ListMetaProductAction extends BaseAction {
	private static final long serialVersionUID = 1954711511084187594L;
	private static final Log loger = LogFactory.getLog(ListMetaProductAction.class);
	private MetaProductService metaProductService;
	private PermRoleService permRoleService;
	private PermUserService permUserService;
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	private List<MetaProduct> productList = new ArrayList<MetaProduct>();
	private Integer totalRowCount;
	private List<Long> selectedMetaIds = new ArrayList<Long>();
	private Long manager_Id;
	private String allData;
	private int managerCount;
	Longbox managerId;
	Longbox supplierId;
	private List<PermOrganization> departmentsList;
	private Listbox departmentsListBox;
	private PermOrganizationService permOrganizationService;
	private SupplierService supplierService;
	
	@Override
	protected void doBefore() throws Exception {
		departmentsList = permOrganizationService.selectAllOrganization();
		departmentsList.add(0, new PermOrganization());
	}
	
	private ProdProductItemService productItemService;
	public int getManagerCount() {
		return managerCount;
	}

	public void setManagerCount(int managerCount) {
		this.managerCount = managerCount;
	}

	public void setAllData(String allData) {
		this.allData = allData;
	}

	Button search;
 
	public void doSearch() throws Exception {
		buildTypeSearchConds();
		
		if(departmentsListBox!=null){
			Listitem item = departmentsListBox.getSelectedItem();
			if (item != null) {
				Long departmentId = (Long) item.getValue();
				if(departmentId.equals(new Long(0))) {
					departmentId = null;
				}
				searchConds.put("orgId", departmentId);
			}	
		}
		
		if(StringUtils.isEmpty(allData) && isNotSeeAllData()) {//有的地方不需要判断数据权限
			searchConds.put("managerIds", permRoleService.getParamManagerIdsByRoleId(getSessionUser(), Constant.PERM_ROLE_META_MANAGER));
			loger.info(" managerIds is :" + permRoleService.getParamManagerIdsByRoleId(getSessionUser(), Constant.PERM_ROLE_META_MANAGER).toString());
			if (permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_CREATE) || permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_FIRST_AUDIT)) {
				searchConds.put("orgId", getSessionUserDepartmentId());
			}
			
			if(permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_META_MANAGER) && (permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_CREATE) || permRoleService.checkUserRole(getSessionUser().getUserId(),
					Constant.PERM_ROLE_FIRST_AUDIT))) {
				searchConds.put("twoRole", Boolean.TRUE);
			}
		}
		if(managerId != null) {
			searchConds.put("managerId", managerId.getValue());
		}
		
		if(supplierId != null) {
			searchConds.put("supplierId", supplierId.getValue());
		}
		
		totalRowCount = metaProductService.selectRowCount(searchConds);
		_totalRowCountLabel.setValue(totalRowCount.toString());
		_paging.setTotalSize(totalRowCount.intValue());

		searchConds.put("_startRow", _paging.getActivePage() * _paging.getPageSize() + 1);
		searchConds.put("_endRow", _paging.getActivePage() * _paging.getPageSize() + _paging.getPageSize());

		productList = metaProductService.findMetaProduct(searchConds);
		if(!productList.isEmpty()){
			List<Long> ids=new ArrayList<Long>();
			for(MetaProduct mp:productList){
				if(!ids.contains(mp.getSupplierId())){
					ids.add(mp.getSupplierId());
				}
			}
			
			Map<Long,SupSupplier> map=supplierService.getSupSupplierBySupplierId(ids);
			for(MetaProduct mp:productList){
				if(map.containsKey(mp.getSupplierId())){
					mp.setSupplier(map.get(mp.getSupplierId()));
				}
			}
		}
		productList = putRealManagerName();
		selectedMetaIds.clear();
	}

	/**
	 * 将产品经理的真实姓名放入列表中 
	 */
	private List<MetaProduct> putRealManagerName() {
		List<MetaProduct> result = new ArrayList<MetaProduct>();
		for (int i=0; i < productList.size(); i++) {
			MetaProduct metaProduct = productList.get(i);
			PermUser permUser = permUserService.getPermUserByUserId(metaProduct.getManagerId());
			if(permUser != null) {
				metaProduct.setManagerName(permUser.getRealName());
			}
			result.add(i, metaProduct);
		}
		return result;
	}
	
	/**
	 * 是否可以查看所有数据(true:不能看所有数据；false:可以看所有数据)
	 * @param hasAllDataRole
	 * @return
	 */
	private boolean isNotSeeAllData() {
		Boolean hasAllDataRole = permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_ALLDATA);
		Boolean hasAllMetaDataRole = permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_ALLDATA_META);

		return !getSessionUser().isAdministrator() && !hasAllMetaDataRole
				&& !hasAllDataRole;
	}

	private void buildTypeSearchConds() {
		List<String> productTypeList = new ArrayList<String>();
		List<String> subProductTypeList = new ArrayList<String>();

		String productType = null;
		if (searchConds.get("sTicket") != null && (Boolean) searchConds.get("sTicket")) {
			productType = Constant.PRODUCT_TYPE.TICKET.name();
			productTypeList.add(Constant.PRODUCT_TYPE.TICKET.name());

			if (null != searchConds.get("subTicket")) {
				subProductTypeList.add((String) searchConds.get("subTicket"));
			}
		}
		if (searchConds.get("sHotel") != null && (Boolean) searchConds.get("sHotel")) {
			productType = Constant.PRODUCT_TYPE.HOTEL.name();
			productTypeList.add(Constant.PRODUCT_TYPE.HOTEL.name());

			if (null != searchConds.get("subHotel")) {
				subProductTypeList.add((String) searchConds.get("subHotel"));
			}
		}
		if (searchConds.get("sRoute") != null && (Boolean) searchConds.get("sRoute")) {
			productType = Constant.PRODUCT_TYPE.ROUTE.name();
			productTypeList.add(Constant.PRODUCT_TYPE.ROUTE.name());

			if (null != searchConds.get("subRoute")) {
				subProductTypeList.add((String) searchConds.get("subRoute"));
			}
		}
		if (searchConds.get("sOther") != null && (Boolean) searchConds.get("sOther")) {
			productType = Constant.PRODUCT_TYPE.OTHER.name();
			productTypeList.add(Constant.PRODUCT_TYPE.OTHER.name());

			if (null != searchConds.get("subOther")) {
				subProductTypeList.add((String) searchConds.get("subOther"));
			}
		}
		searchConds.remove("productTypeList");
		searchConds.remove("productType");
		if (productTypeList.size() == 0) {
			return;
		} else if (productTypeList.size() == 1) {
			searchConds.put("productType", productType);
		} else {
			searchConds.put("productTypeList", productTypeList);
		}

		searchConds.remove("subProductTypeList");
		searchConds.remove("subProductType");
		if (subProductTypeList.size() == 0) {
			return;
		} else if (subProductTypeList.size() == 1) {
			searchConds.put("subProductType", subProductTypeList.get(0));
		} else {
			searchConds.put("subProductTypeList", subProductTypeList);
		}
	}

	/**
	 * 设置被选中的任務
	 * */
	public void onCheckboxEvent(Long metaId, boolean checked) {
		if (checked) {
			selectedMetaIds.add(metaId);
		} else {
			selectedMetaIds.remove(metaId);
		}
	}

	/**
	 * 修改产品经理窗口
	 * */
	public void doOpenDialog() throws Exception {
		if (selectedMetaIds.size() > 0) {
			HashMap params = new HashMap();
			params.put("selectedMetaIds", selectedMetaIds);
			params.put("managerCount",selectedMetaIds.size());
			showWindow("/metas/manager_user.zul", params);
		} else {
			alert("请选择需要修改的项！");
			return;
		}
	}
	
	public void doUpdate() {
		if(manager_Id == null) {
			alert("请选择产品经理！");
			return;
		}
		if(selectedMetaIds != null && selectedMetaIds.size() > 0) {
			HashMap params = new HashMap();
			params.put("managerId", manager_Id);
			
			// mod by zhangwengang 2013/4/1  批量修改采购产品经理时把部门也更新过去 start
			PermUser permUser = permUserService.getPermUserByUserId(manager_Id);
			if(permUser != null) {
				params.put("orgId", permUser.getDepartmentId());
			}
			// mod by zhangwengang 2013/4/1  批量修改采购产品经理时把部门也更新过去 end
			
			params.put("metaProductIds", selectedMetaIds);
			metaProductService.updateManager(params);
			alert("修改成功！");
			refreshParent("search");
			selectedMetaIds.clear();
			this.managerCount=0;
			closeWindow();
		}
	}
	
	/**
	 * 批量修改所属组织窗口
	 * @throws Exception 
	 * */
	public void doOpenDepDialog() throws Exception {
		if (selectedMetaIds.size() > 0) {
			HashMap params = new HashMap();
			params.put("selectedMetaIds", selectedMetaIds);
			params.put("managerCount",selectedMetaIds.size());
			showWindow("/products/saleUserManager/eidt_org.zul", params);
		} else {
			alert("请选择需要修改的项！");
			return;
		}
	}
	
	/**
	 * 复选框全选
	 */
	public void selectAllCheckbox(boolean isChecked) {
		selectedMetaIds.clear();// 清除刚才单选的全部数目
		for (int i = 0; i < this.productList.size(); i++) {
			MetaProduct product = this.productList.get(i);
			product.setChecked(isChecked);
			onCheckboxEvent(product.getMetaProductId(), isChecked);
		}
	}

	public void changeSubTicket(String value) {
		changeSubType("subTicket", value);
	}

	public void changeSubHotel(String value) {
		changeSubType("subHotel", value);
	}

	public void changeSubRoute(String value) {
		changeSubType("subRoute", value);
	}

	public void changeSubOther(String value) {
		changeSubType("subOther", value);
	}

	private void changeSubType(String name, String value) {
		if (StringUtils.isEmpty(value)) {
			searchConds.remove(name);
		} else {
			searchConds.put(name, value);
		}
	}

	/**
	 * 打开修改窗口
	 * 
	 */
	public void doEdit(Map params) throws Exception {
		String url = null;
		String productType = params.get("productType").toString();
		if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
			url = "/metas/edit_ticket.zul";
		}
		if (Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)) {
			url = "/metas/edit_hotel.zul";
		}
		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
			url = "/metas/edit_route.zul";
		}
		if (Constant.PRODUCT_TYPE.OTHER.name().equals(productType)) {
			url = "/metas/edit_other.zul";
		}
		if (url == null) {
			ZkMessage.showInfo("弹出页面链接为空");
			return;
		}
		showWindow(url, params);
	}

	/**
	 * Shi hui 删除记录
	 */
	public void doDelete(Map params) throws Exception {
		delQuest(params);
		this.refreshComponent("search");
	}

	public void delQuest(final Map params) {
		String productName = (String) params.get("productName");
		ZkMessage.showQuestion("您要删除 " + productName + ", 请确认。", new ZkMsgCallBack() {
			public void execute() {
				metaProductService.changeMetaProductValid(params,getOperatorName());
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});
	}

	/**
	 * 打开预览窗口
	 * 
	 */
	public void doPreview(Map params) throws Exception {
		String url = null;
		String productType = params.get("productType").toString();
		if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
			url = "/metas/view_index.zul";
		}
		if (Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)) {
			url = "/metas/view_index.zul";
		}
		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
			url = "/metas/view_index.zul";
		}
		if (Constant.PRODUCT_TYPE.OTHER.name().equals(productType)) {
			url = "/metas/view_index.zul";
		}
		if (url == null) {
			ZkMessage.showInfo("弹出页面链接为空");
			return;
		}
		showWindow(url, params);
	}

	/**
	 * @author lipengcheng 修改有效状态
	 */
	@SuppressWarnings("unchecked")
	public void updateValid(Map params) throws Exception {
		
		
		Long pid = Long.parseLong(String.valueOf(params.get("metaProductId")));
		if(productItemService.selectProdProductByMetaId(pid).size()!=0){
			alert("请删除此采购产品所关联销售产品");
		}else{
		
			String valid = params.get("valid").toString();

			params.put("metaProductId", pid);
			if (valid.equals("Y")) {
				valid = "N";
			} else {
				valid = "Y";
			}
			params.put("valid", valid);
			metaProductService.changeMetaProductValid(params,this.getOperatorName());
			if (valid.equals("Y")) {
				this.alert("状态已改为有效");
			} else {
				this.alert("状态已改为无效");
			}
			this.refreshComponent("search");
		}
	}

	public List<MetaProduct> getProductList() {
		return productList;
	}

	public Map getSearchConds() {
		return searchConds;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}

	public void setPermOrganizationService(PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}

	public Long getManager_Id() {
		return manager_Id;
	}

	public void setManager_Id(Long managerId) {
		manager_Id = managerId;
	}

	public void setSelectedMetaIds(List<Long> selectedMetaIds) {
		this.selectedMetaIds = selectedMetaIds;
	}

	public void setProductList(List<MetaProduct> productList) {
		this.productList = productList;
	}

	public ProdProductItemService getProductItemService() {
		return productItemService;
	}

	public void setProductItemService(ProdProductItemService productItemService) {
		this.productItemService = productItemService;
	}

	public List<PermOrganization> getDepartmentsList() {
		return departmentsList;
	}

}
