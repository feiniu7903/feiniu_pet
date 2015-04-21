/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.Pagination;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductControl;
import com.lvmama.comm.bee.po.meta.ProductControlRole;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductControlService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.meta.ProductControlRoleService;
import com.lvmama.comm.bee.vo.view.ProductControlCondition;
import com.lvmama.comm.bee.vo.view.ViewMetaProductControl;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author zuoxiaoshuai
 *
 */
@Results({
	@Result(name="toBtsWarningList",location="/WEB-INF/pages/back/meta/buyout_stock_warning_list.jsp"),
	@Result(name="toControlList",location="/WEB-INF/pages/back/meta/meta_product_control_list.jsp"),
	@Result(name="toControlEditor",location="/WEB-INF/pages/back/meta/control_editor.jsp"),
	@Result(name="toControlBatchEditor",location="/WEB-INF/pages/back/meta/control_batch_editor.jsp")
})
public class BuyOutStockWarningAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3472419146681383473L;
	
	private List<ViewMetaProductControl> viewControlList;
	
	private MetaProductBranchService metaProductBranchService;
	
	private MetaProductControlService metaProductControlService;
	
	private ProductControlRoleService productControlRoleService;
	
	private MetaProductService metaProductService;
	
	private WorkGroupService workGroupService;
	
	private List<WorkGroup> groupNameList;
	
	private Map<String,Object> searchConds = new HashMap<String, Object>();
	
	private List<MetaProductControl> controlList;
	
	private ProductControlCondition controlCondition;
	
	private MetaProductControl control;
	
	private List<MetaProductBranch> branchList;
	
	static Log LOG = LogFactory.getLog(BuyOutStockWarningAction.class);
	
	@Action("/meta/btsWarningList")
	public String toIndex() {
		groupNameList = workGroupService.queryWorkGroupName();
		return "toBtsWarningList";
	}
	
	@Action("/meta/searchBtsWarningList")
	public String toMetaProductList2() {
		searchConds = initParam();
		groupNameList = workGroupService.queryWorkGroupName();
		toResult(searchConds, true);
		searchConds.put("page", pagination.getPage());
		searchConds.put("perPageRecord", pagination.getPerPageRecord());
		getSession().setAttribute("META_GO_UPSTEP_URL", searchConds);
		
		return "toBtsWarningList";
	}
	
	private String toResult(Map<String,Object> searchConds,boolean autoReq) {
		Long totalRowCount = metaProductControlService.countViewControlByCondition(searchConds);
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
		viewControlList = metaProductControlService.getViewControlList(searchConds);
		pagination.setRecords(viewControlList);
		if(autoReq){
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		}else{
			pagination.setActionUrl(WebUtils.getUrl("/meta/searchBtsWarningList.do", true, searchConds));
		}
		return "toBtsWarningList";
	}
	
	private Map<String,Object> initParam() {
		if (controlCondition != null) {
			searchConds.put("workGroupId", controlCondition.getWorkGroupId());
			searchConds.put("applierName", getLikeValue(controlCondition.getApplierName()));
			searchConds.put("productId", controlCondition.getProductId());
			searchConds.put("productName", getLikeValue(controlCondition.getProductName()));
			
			searchConds.put("saleStartDate", controlCondition.getSaleStartDate());
			searchConds.put("saleEndDate", controlCondition.getSaleEndDate());
			searchConds.put("startDate", controlCondition.getStartDate());
			searchConds.put("endDate", controlCondition.getEndDate());
			
			searchConds.put("supplierId", controlCondition.getSupplierId());
			searchConds.put("supplierName", getLikeValue(controlCondition.getSupplierName()));
			searchConds.put("branchName", getLikeValue(controlCondition.getBranchName()));
			searchConds.put("controlType", controlCondition.getControlType());
		}
		searchConds.put("userId", getSessionUser().getUserId());
		ProductControlRole role = productControlRoleService.getControlRoleByUserId((Long) searchConds.get("userId"));
		if (role != null) {
			searchConds.put("roleArea", role.getRoleArea());
			if (Constant.PRODUCT_CONTROL_ROLE.ROLE_MANAGER.name().equals(role.getRoleArea())) {
				searchConds.put("managerList", role.getManagerIdList().replace("[", "").replace("]", ""));
			}
		}
		return searchConds;
	}
	
	private Map<String,Object> initControlParam() {
		if (controlCondition != null) {
			searchConds.put("branchId", controlCondition.getBranchId());
			searchConds.put("branchName", getLikeValue(controlCondition.getBranchName()));
			searchConds.put("productId", controlCondition.getProductId());
			searchConds.put("productName", getLikeValue(controlCondition.getProductName()));
			searchConds.put("supplierId", controlCondition.getSupplierId());
			searchConds.put("supplierName", getLikeValue(controlCondition.getSupplierName()));
			searchConds.put("controlType", StringUtils.trimToNull(controlCondition.getControlType()));
			searchConds.put("userId", getSessionUser().getUserId());
		}
		return searchConds;
	}

	private String getLikeValue(String str) {
		return StringUtils.isEmpty(str) ? null : "%" + StringUtils.trim(str) + "%";
	}
	
	@Action("/meta/controlList")
	public String toControlIndex() {
		return "toControlList";
	}
	
	@Action("/meta/editControl")
	public String editControl() {
		fillControl(control.getProductId());
		return "toControlEditor";
	}
	
	@Action("/meta/editBatchControl")
	public String editBatchControl() {
		fillControl(control.getProductId());
		return "toControlBatchEditor";
	}
	
	@Action("/meta/copyControl")
	public String copyControl() {
		control = metaProductControlService.getByPrimaryKey(control.getMetaProductControlId());
		control.setMetaProductControlId(null);
		control.setSaleQuantity(0L);
		return "toControlEditor";
	}
	
	@Action("/meta/modifyControl")
	public String modifyControl() {
		control = metaProductControlService.getByPrimaryKey(control.getMetaProductControlId());
		return "toControlEditor";
	}
	
	private void fillControl(Long productId) {
		MetaProduct product = metaProductService.getMetaProduct(productId);
		control.setProductName(product.getProductName());
		control.setControlType(product.getControlType());
		if (Constant.PRODUCT_CONTROL_TYPE.BRANCH_LEVEL.name().equals(product.getControlType())) {
			branchList = metaProductBranchService.getEbkMetaBranchByProductId(productId);
		}
	}
	
	@Action("/meta/searchControlList")
	public String toControlList(){
		searchConds = initControlParam();
		toControlResult(searchConds, true);
		searchConds.put("page", pagination.getPage());
		searchConds.put("perPageRecord", pagination.getPerPageRecord());
		getSession().setAttribute("META_GO_UPSTEP_URL", searchConds);
		return "toControlList";
	}
	
	@Action("/meta/exportViewList")
	public void exportViewList() {
		searchConds = initParam();
		List<ViewMetaProductControl> views = metaProductControlService.getReportViewControlList(searchConds);
		export(views);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void export(List<ViewMetaProductControl> views) {
		FileInputStream fin=null; 
		OutputStream os=null; 
		try 
		{ 
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/productControlTemplate.xls"); 
			Map beans = new HashMap(); 
			beans.put("views", views);
			beans.put("dateFormat", new SimpleDateFormat("yyyy-MM-dd"));
			XLSTransformer transformer = new XLSTransformer(); 
			File destFileName = new File(Constant.getTempDir() + "/excel"+new Date().getTime()+".xls"); 
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath()); 
			getResponse().setContentType("application/vnd.ms-excel"); 
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName()); 
			os=getResponse().getOutputStream(); 
			fin=new FileInputStream(destFileName); 
			IOUtils.copy(fin, os); 
			os.flush(); 
		}catch(Exception ex){ 
			LOG.error(ex);
		}finally{ 
			IOUtils.closeQuietly(fin); 
			IOUtils.closeQuietly(os); 
		} 

	}

	private void toControlResult(Map<String, Object> searchConds2, boolean autoReq) {
		Long totalRowCount = metaProductControlService.countProductControlByCondition(searchConds);
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
		controlList = metaProductControlService.getProductControlListByCondition(searchConds);
		pagination.setRecords(controlList);
		if(autoReq){
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		}else{
			pagination.setActionUrl(WebUtils.getUrl("/meta/searchControlList.do", true, searchConds));
		}
	}
	
	@Action("/meta/saveControl")
	public void saveControl() {
		JSONResult result=new JSONResult();
		result.put("opType", control.getMetaProductControlId() == null ? "NEW" : "MODIFY");
		try{
			metaProductControlService.saveControl(control);
		} catch(Exception ex){
			result.raise(ex);
		}
		result.outputJSON(getResponse());
	}
	
	@Action("/meta/saveBatchControl")
	public void saveBatchControl() {
		JSONResult result=new JSONResult();
		try{
			metaProductControlService.saveBatchControl(control);
		} catch(Exception ex){
			result.raise(ex);
		}
		result.outputJSON(getResponse());
	}
	
	@Action("/meta/deleteControlByProduct")
	public void deleteControlByProduct() {
		JSONResult result=new JSONResult();
		try{
			metaProductControlService.deleteControlByProduct(control);
		} catch(Exception ex){
			result.raise(ex);
		}
		result.outputJSON(getResponse());
	}
	
	@Action("/meta/deleteControl")
	public void deleteControl() {
		JSONResult result=new JSONResult();
		try{
			metaProductControlService.deleteControl(control);
		} catch(Exception ex){
			result.raise(ex);
		}
		result.outputJSON(getResponse());
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}

	public List<WorkGroup> getGroupNameList() {
		return groupNameList;
	}

	public void setGroupNameList(List<WorkGroup> groupNameList) {
		this.groupNameList = groupNameList;
	}

	public List<MetaProductControl> getControlList() {
		return controlList;
	}

	public void setControlList(List<MetaProductControl> controlList) {
		this.controlList = controlList;
	}

	public ProductControlCondition getControlCondition() {
		return controlCondition;
	}

	public void setControlCondition(ProductControlCondition controlCondition) {
		this.controlCondition = controlCondition;
	}

	public void setMetaProductControlService(
			MetaProductControlService metaProductControlService) {
		this.metaProductControlService = metaProductControlService;
	}

	public void setProductControlRoleService(
			ProductControlRoleService productControlRoleService) {
		this.productControlRoleService = productControlRoleService;
	}

	public MetaProductControl getControl() {
		return control;
	}

	public void setControl(MetaProductControl control) {
		this.control = control;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public List<MetaProductBranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<MetaProductBranch> branchList) {
		this.branchList = branchList;
	}

	public List<ViewMetaProductControl> getViewControlList() {
		return viewControlList;
	}

	public void setViewControlList(List<ViewMetaProductControl> viewControlList) {
		this.viewControlList = viewControlList;
	}
}
