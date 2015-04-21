package com.lvmama.back.sweb.meta;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.ReflectionUtils;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.Pagination;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "toMetaProductList", location = "/WEB-INF/pages/back/meta/meta_product_list.jsp"),
	@Result(name = "toMetaProductListAction",location="/meta/metaProductList.do?productType=${productType}",type="redirect"),
	@Result(name = "relate_meta_products", location = "/WEB-INF/pages/back/meta/relate_meta_products.jsp")
	})
public class MetaProductListAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8295640123365942075L;
	private static final Log loger = LogFactory.getLog(MetaProductListAction.class);
	private MetaProductService metaProductService;
	private PermRoleService permRoleService;
	private SupplierService supplierService;
	private List<MetaProduct> metaProductList;
	private String productType;
	private String productName;
	private String metaProductId;
	private String bizCode;
	private String supplierId;
	private String supplierName;
	private String subProductType;
	Map<String,Object> searchConds=new HashMap<String, Object>();
	private Long targetId;
	private String isAperiodic; 
	private String filialeName;
	
	@Action("/meta/metaProductList")
	public String toMetaProductList(){		
		return "toMetaProductList";
	}

	@Action("/meta/metaProductList2")
	public String toMetaProductList2(){
		searchConds=initParam();
		
		String success=toResult(searchConds, true);
		searchConds.put("supplierName", supplierName);
		searchConds.put("page", pagination.getPage());
		searchConds.put("perPageRecord", pagination.getPerPageRecord());
		getSession().setAttribute("META_GO_UPSTEP_URL", searchConds);
		
		return success;
	}
	
	@SuppressWarnings("unchecked")
	@Action("/meta/goBackMetaProuctList")
	public String toBackMetaProuctList(){
		searchConds = (Map<String,Object>)getSession().getAttribute("META_GO_UPSTEP_URL");
		if(searchConds==null || !searchConds.containsKey("productType")||!searchConds.get("productType").equals(productType)){
			return "toMetaProductListAction";
		}
		for(String key:searchConds.keySet()){
			Field field=ReflectionUtils.findField(this.getClass(), key);
			if(field!=null){
				try{
					if(key.equals("metaProductId")){
						field.set(this, searchConds.get(key).toString());
					}else{
						field.set(this, searchConds.get(key));						
					}
				}catch(Exception ex){	
				}
			}
		}
		return toResult(searchConds, false);
	}
	
	
	private String toResult(Map<String,Object> searchConds,boolean autoReq){
		Integer totalRowCount=metaProductService.selectRowCount(searchConds);
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
		metaProductList = metaProductService.findMetaProduct(searchConds);
		if(!metaProductList.isEmpty()){
			for(MetaProduct mp:metaProductList){
				if(mp.getSupplierId()!=null){
					SupSupplier ss=supplierService.getSupplier(mp.getSupplierId());
					if(ss!=null){
						mp.setSupplier(ss);
						mp.setSupplierName(ss.getSupplierName());
					}
				}
			}
		}
		pagination.setRecords(metaProductList);
		if(autoReq){
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		}else{
			pagination.setActionUrl(WebUtils.getUrl("/meta/metaProductList2.do", true, searchConds));
		}
		return "toMetaProductList";
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
	private Map<String,Object> initParam() {
		if(StringUtils.isNotEmpty(productName)){
			searchConds.put("productName", productName);
		}
		searchConds.put("productType", productType);
		if (StringUtils.isNotEmpty(this.bizCode)) {
			searchConds.put("bizCode",bizCode);
		} 
		if(StringUtils.isNotEmpty(this.metaProductId)){			
			searchConds.put("metaProductId", org.apache.commons.lang3.math.NumberUtils.toLong(metaProductId));
		}
		if (StringUtils.isNotEmpty(this.supplierId)) {
			searchConds.put("supplierId",supplierId);
		} 
		if(StringUtils.isNotEmpty(subProductType)){
			searchConds.put("subProductTypeList", new String[]{subProductType});
			searchConds.put("subProductType",subProductType );
		}
		if(isNotSeeAllData()) {//有的地方不需要判断数据权限
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
		//是否为期票
		if(StringUtils.isNotEmpty(isAperiodic)){
			searchConds.put("isAperiodic", isAperiodic);
		}
		if(StringUtils.isNotEmpty(filialeName)){
			searchConds.put("filialeName", filialeName);
		}
		return searchConds;
	}
	
	@Action("/meta/showRelateMetaProductList")
	public String showRelateMetaProductList() {
		if(targetId != null && targetId > 1){
			initPagination();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("targetId", targetId);
			pagination.setTotalRecords(metaProductService.getMetaProductListByTargetIdCount(params));
			params.put("_startRow",pagination.getFirstRow()-1);
			params.put("_endRow",pagination.getLastRow());
			metaProductList = metaProductService.getMetaProductListByTargetId(params);
		}
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		return "relate_meta_products";
	}
	
	public List<MetaProduct> getMetaProductList() {
		return metaProductList;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName.trim();
	}
	public String getMetaProductId() {
		return metaProductId;
	}
	public void setMetaProductId(String metaProductId) {
		this.metaProductId = metaProductId.trim();
	}
	
	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getSupplierId() {
		return supplierId;
	}
	public Map<String, Object> getSearchConds() {
		return searchConds;
	}
	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}
	public void setMetaProductList(List<MetaProduct> metaProductList) {
		this.metaProductList = metaProductList;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getProductType() {
		return productType;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<CodeItem> getSubProductTypeList() {
		if (StringUtils.isNotEmpty(productType)) {
			if (Constant.PRODUCT_TYPE.OTHER.name().equals(productType)) {
				return ProductUtil.getOtherSubTypeList(true);
			}else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)){
				return ProductUtil.getMetaRouteSubTypeList();
			}
		}
		return Collections.emptyList();

	}

	/**
	 * @return the subProductType
	 */
	public String getSubProductType() {
		return subProductType;
	}

	/**
	 * @param subProductType the subProductType to set
	 */
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
	
	
}
