package com.lvmama.back.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.vo.Constant;

public class SaleUserManagerAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private List<ProdProduct> productList = new ArrayList<ProdProduct>();

	private List<PermOrganization> departmentsList;
	
	private ProdProductService prodProductService;
	
	private PermOrganizationService permOrganizationService;
	
	private Listbox departmentsListBox;
	
	@SuppressWarnings("rawtypes")
	private Map searchConds = new HashMap();
	
	private Integer totalRowCount;
	
	private List<Long> selectedProductIds = new ArrayList<Long>();
	private Long manager_Id;
	Longbox managerId;
	Longbox placeId;
	private Longbox productId;
	private int managerCount;
	
	private PermUserService permUserService;
	

	public int getManagerCount() {
		return managerCount;
	}

	public void setManagerCount(int managerCount) {
		this.managerCount = managerCount;
	}
/**
 * @author lipengcheng
 * @throws Exception
 */
	@SuppressWarnings("unchecked")
	public void forwardSearch() throws Exception {
		Listitem item = departmentsListBox.getSelectedItem();
		if (item != null) {
			Long departmentId = (Long) item.getValue();
			if(departmentId.equals(new Long(0))) {
				departmentId = null;
			}
			searchConds.put("orgId", departmentId);
		}
		
		if (productList != null) {
			productList.clear();
		}
		buildTypeSearchConds();

		if (managerId != null) {
			searchConds.put("managerId", managerId.getValue());
		}

		if (placeId != null) {
			searchConds.put("placeId", placeId.getValue());
		}
		
		if(productId != null){
			searchConds.put("productId", productId.getValue());
		}
		totalRowCount = prodProductService.selectManagerCount(searchConds);
		// 调用父类分页方法
		initialPageInfoByMap(totalRowCount.longValue(), searchConds);
		productList = prodProductService.selectManager(searchConds);
		selectedProductIds.clear();
	}


	
	@Override
	protected void doBefore() throws Exception {
		departmentsList = permOrganizationService.selectAllOrganization();
		departmentsList.add(0, new PermOrganization());
	}

	/**
	 * 设置被选中的任務
	 * */
	public void onCheckboxEvent(Long productId, boolean checked) {
		if (checked) {
			selectedProductIds.add(productId);
		} else {
			selectedProductIds.remove(productId);
		}
	}
	
	/**
	 * 修改产品经理窗口
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doOpenDialog() throws Exception {
		if (selectedProductIds.size() > 0) {
			HashMap params = new HashMap();
			params.put("selectedProductIds", selectedProductIds);
			params.put("managerCount",selectedProductIds.size());
			showWindow("/products/saleUserManager/manager_user.zul", params);
		} else {
			alert("请选择需要修改的项！");
			return;
		}
	}
	
	/**
	 * 批量修改所属组织窗口
	 * @throws Exception 
	 * */
	public void doOpenDepDialog() throws Exception {
		if (selectedProductIds.size() > 0) {
			HashMap params = new HashMap();
			params.put("selectedProductIds", selectedProductIds);
			params.put("managerCount",selectedProductIds.size());
			showWindow("/products/saleUserManager/eidt_org.zul", params);
		} else {
			alert("请选择需要修改的项！");
			return;
		}
	}
	
	/**
	 *  批量修改销售产品经理
	 *  产品如果没有组织ID，就将新产品经理的组织ID赋给它
	 */
	public void doUpdate() {
		if(manager_Id == null) {
			alert("请选择产品经理！");
			return;
		}
		if(selectedProductIds!= null && selectedProductIds.size() > 0) {
			
			HashMap<String,Object> params = new HashMap<String,Object>();
			
			params.put("managerId", manager_Id);
			
			//为得到产品经理的部门ID而进行查询
			PermUser permUser = permUserService.getPermUserByParams(params);
			
			params.put("orgId", permUser.getDepartmentId());
			params.put("productIds", selectedProductIds);
			prodProductService.updateManager(params);
			alert("修改成功！");
			selectedProductIds.clear();
			refreshParent("search");
			this.managerCount=0;
			closeWindow();
		}
	}
	
	/**
	 * 复选框全选
	 */
	public void selectAllCheckbox(boolean isChecked){
		
		selectedProductIds.clear();//清除刚才单选的全部数目
		for (int i = 0; i < this.productList.size(); i++) {
			ProdProduct product=this.productList.get(i);
			product.setChecked(isChecked);
			onCheckboxEvent(product.getProductId(), isChecked);
		}
	}

	/**
	 * 选择商品类型
	 */
	@SuppressWarnings("unchecked")
	private void buildTypeSearchConds() {
		List<String> productTypeList = new ArrayList<String>();
		List<String> subProductTypeList = new ArrayList<String>();
		
		if (null != searchConds.get("filialeName") && "".equalsIgnoreCase((String)searchConds.get("filialeName"))) {
			searchConds.remove("");
		}
		@SuppressWarnings("unused")
		String productType = null;
		if (searchConds.get("sTicket")!=null && (Boolean)searchConds.get("sTicket")){
			productType = Constant.PRODUCT_TYPE.TICKET.name();
			productTypeList.add(Constant.PRODUCT_TYPE.TICKET.name());
			
			if (null != searchConds.get("subTicket")) {
				subProductTypeList.add((String) searchConds.get("subTicket"));
			}
		}
		if (searchConds.get("sHotel")!=null && (Boolean)searchConds.get("sHotel")){
			productType = Constant.PRODUCT_TYPE.HOTEL.name();
			productTypeList.add(Constant.PRODUCT_TYPE.HOTEL.name());
			
			if (null != searchConds.get("subHotel")) {
				subProductTypeList.add((String) searchConds.get("subHotel"));
			}			
		}
		if (searchConds.get("sRoute")!=null && (Boolean)searchConds.get("sRoute")){
			productType = Constant.PRODUCT_TYPE.ROUTE.name();
			productTypeList.add(Constant.PRODUCT_TYPE.ROUTE.name());
			
			if (null != searchConds.get("subRoute")) {
				subProductTypeList.add((String) searchConds.get("subRoute"));
			}			
		}
		if (searchConds.get("sOther")!=null && (Boolean)searchConds.get("sOther")){
			productType = Constant.PRODUCT_TYPE.OTHER.name();
			productTypeList.add(Constant.PRODUCT_TYPE.OTHER.name());
			
			if (null != searchConds.get("subOther")) {
				subProductTypeList.add((String) searchConds.get("subOther"));
			}
		}
		searchConds.remove("productTypeList");
		searchConds.remove("productType");
		if (productTypeList.size()==0) {
			return;
		}
//		else if(productTypeList.size()==1) {
//			searchConds.put("productType", productType);
//			System.out.println("111111111111"+productType);
//		}
		else{
			searchConds.put("productTypeList", productTypeList);
		}
		
		searchConds.remove("subProductTypeList");
		searchConds.remove("subProductType");
		if (subProductTypeList.size()==0) {
			return;
		}
//		else if(subProductTypeList.size()==1) {
//			searchConds.put("subProductType", subProductTypeList.get(0));
//		}
		else{
			searchConds.put("subProductTypeList", subProductTypeList);
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
	
	@SuppressWarnings("unchecked")
	private void changeSubType(String name, String value) {
		if (StringUtils.isEmpty(value)) {
			searchConds.remove(name);
		} else {
			searchConds.put(name, value);
		}
	}	

	@SuppressWarnings("rawtypes")
	public Map getSearchConds() {
		return searchConds;
	}

	public List<ProdProduct> getProductList() {
		return productList;
	}

	public void setSelectedProductIds(List<Long> selectedProductIds) {
		this.selectedProductIds = selectedProductIds;
	}

	public Long getManager_Id() {
		return manager_Id;
	}

	public void setManager_Id(Long managerId) {
		manager_Id = managerId;
	}

	public void setProductList(List<ProdProduct> productList) {
		this.productList = productList;
	}

	public Longbox getProductId() {
		return productId;
	}

	public void setProductId(Longbox productId) {
		this.productId = productId;
	}

	public PermUserService getPermUserService() {
		return permUserService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setPermOrganizationService(PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}

	public List<PermOrganization> getDepartmentsList() {
		return departmentsList;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	
	
}
