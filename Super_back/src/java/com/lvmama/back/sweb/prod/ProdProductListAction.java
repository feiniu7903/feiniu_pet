package com.lvmama.back.sweb.prod;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.Pagination;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.prod.BounsReturnScale;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.BounsReturnScaleService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yuzhibing
 *
 */
@Results({
	@Result(name = "toProductList", location = "/WEB-INF/pages/back/prod/product_list.jsp"),
	@Result(name = "toVisaProductList", location = "/WEB-INF/pages/back/prod/visa_product_list.jsp"),
	@Result(name = "toProductListAction",location="/prod/toProductList.do?productType=${productType}",type="redirect"),
	@Result(name = "toVisaProductListAction",location="/prod/toVisaProductList.do?productType=${productType}",type="redirect")
	})
public class ProdProductListAction extends BaseAction{
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1219012267482045364L;

	private ProdProductService prodProductService;
	private PermRoleService permRoleService;
	private BounsReturnScaleService bounsReturnScaleService;
	private String productName;
	private String onLine;
	private String bizcode;
	private String productId;
	private Long placeId;
	private String prodBranchId;
	private String onlineStatus;
	private String filialeName;
	private String subProductType;
	private String placeName;
	private String productType;
	private String selfPack;
	private String visaType;
	private String city;
	private String country;
    private String salesChannels;
    private String searchAuditingStatus;
    private String freeBackable;
	//签证
    private boolean isVisaProduct=false;

	/**当前页**/
	private Integer currentPage = 1;
	private String isAperiodic; 
	
	@Action("/prod/toProductList")
	public String toProductList(){
		return "toProductList";
	}
    
	//签证列表
	@Action("/prod/toVisaProductList")
	public String toVisaProductList(){
		productType=Constant.PRODUCT_TYPE.OTHER.name();
		return "toVisaProductList";
	}
	
	@Action("/prod/queryProductList")
	public String queryProductList(){
		Map<String,Object> searchConds=initParam();
		String success=toResult(searchConds, true);
		
		searchConds.put("placeName", placeName);
		searchConds.put("page", pagination.getPage());
		searchConds.put("perPageRecord", pagination.getPerPageRecord());
		getSession().setAttribute("PROD_GO_UPSTEP_URL", searchConds);
		
		return success;
	}
	
	@SuppressWarnings("unchecked")
	@Action("/prod/goBackProductList")
	public String queryProductList2(){		
		Map<String,Object> searchConds=(Map<String,Object>)getSession().getAttribute("PROD_GO_UPSTEP_URL");
		if(searchConds==null||!searchConds.containsKey("productType")||!searchConds.get("productType").equals(productType)){
			if(StringUtils.equals(subProductType,"VISA")){
				return "toVisaProductListAction";
			}else{
			    return "toProductListAction";
			}
		}
		
		for(String key:searchConds.keySet()){
			Field field=ReflectionUtils.findField(this.getClass(), key);
			if(field!=null){
				try{
					field.set(this, searchConds.get(key));
				}catch(Exception ex){
				}
			}
		}
		
				
		return toResult(searchConds, false);
	}
	
	private String toResult(Map<String,Object> searchConds,boolean autoReq){
		Integer totalRowCount = prodProductService.selectRowCount(searchConds);
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
		List<ProdProduct> productList = prodProductService.selectProductByParms(searchConds);
		List<BounsReturnScale> scaleConfList = bounsReturnScaleService.getAll();
		Map<String, Double> scaleMap = createScaleMap(scaleConfList);
		for (ProdProduct pro : productList) {
			if ("Y".equalsIgnoreCase(pro.getIsRefundable())) {
				String key = pro.getProductType() + "_" + pro.getSubProductType();
				Double scale = scaleMap.get(key);
				if (scale != null) {
					if (pro.getBounsScaleDouble() * 100 > scale) {
						pro.setIsMoreThanConf("Y");
					}
				}
			}
		}
		pagination.setRecords(productList);
		if(autoReq){
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		}else{
			if(searchConds.containsKey("sProductName")){
				productName=(String)searchConds.get("sProductName");			
				searchConds.put("productName", productName);
			}
			pagination.setActionUrl(WebUtils.getUrl("/prod/queryProductList.do", true, searchConds));
		}
		if(StringUtils.equals(subProductType,"VISA")){
			return "toVisaProductList";
		}else{
		   return "toProductList";
		}
	}
	
	
	private Map<String, Double> createScaleMap(
			List<BounsReturnScale> scaleConfList) {
		Map<String, Double> map = new HashMap<String, Double>();
		for (BounsReturnScale scale : scaleConfList) {
			map.put(scale.getProductType() + "_" + scale.getProductSubType(), new Double(scale.getScale()));
		}
		return map;
	}

	private Map<String,Object> initParam() {
		Map<String,Object> searchConds = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(productName)){
			searchConds.put("sProductName", productName);
		}
		
		if (StringUtils.isNotEmpty(this.bizcode)) {
 			searchConds.put("bizcode",bizcode );
		} 
		
		if(StringUtils.isNotEmpty(this.productId)){
			if(NumberUtils.toLong(productId)>0){
				searchConds.put("productId", productId);
			}
		}
		if(StringUtils.isNotEmpty(prodBranchId)){
			if(NumberUtils.toLong(prodBranchId.trim())>0){
				searchConds.put("prodBranchId", prodBranchId.trim());
			}
		}
		if (StringUtils.isNotEmpty(this.filialeName)) {
 			searchConds.put("filialeName",filialeName );
		} 
		if (StringUtils.isNotEmpty(this.onlineStatus)) {
 			searchConds.put("onlineStatus",onlineStatus );
		} 
		if (StringUtils.isNotEmpty(this.onLine)) {
 			searchConds.put("onLine",onLine );
		}
		if (StringUtils.isNotEmpty(this.subProductType)) {
			if(StringUtils.equals(this.subProductType,"VISA")){
				this.isVisaProduct=true;
			}
 			searchConds.put("subProductType",subProductType );
		} 
		if (StringUtils.isNotEmpty(this.productType)) {
 			searchConds.put("productType",productType );
		} 
		if (placeId!=null && placeId > 0) {
 			searchConds.put("placeId",placeId);
		} 
		if(StringUtils.equals(Constant.PRODUCT_TYPE.ROUTE.name(), productType)&&StringUtils.isNotEmpty(selfPack)){
			searchConds.put("selfPack", selfPack);
		}
		if (isProdNotSeeAllData()) {
			searchConds.put("managerIds", permRoleService.getParamManagerIdsByRoleId(getSessionUser(), Constant.PERM_ROLE_PROD_MANAGER));
			if (permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_CREATE)||permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_FIRST_AUDIT)) {
				searchConds.put("orgId", getSessionUserDepartmentId());
			}
			if(permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_PROD_MANAGER) && (permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_CREATE) || permRoleService.checkUserRole(getSessionUser().getUserId(),
					Constant.PERM_ROLE_FIRST_AUDIT))) {
				searchConds.put("twoRole", Boolean.TRUE);
			}
		}
		if (StringUtils.isNotEmpty(this.visaType)) {
 			searchConds.put("visaType",visaType);
		}
		if (StringUtils.isNotEmpty(this.country)) {
 			searchConds.put("country",country);
		}
		if (StringUtils.isNotEmpty(this.city)) {
 			searchConds.put("city",city);
		}
 		if (StringUtils.isNotEmpty(this.searchAuditingStatus)) {
 			searchConds.put("searchAuditingStatus",searchAuditingStatus);
		}
 		searchConds.put("isVisaProduct",isVisaProduct);
 		searchConds.put("freeBackable", StringUtils.trimToNull(freeBackable));
 		//是否为期票
		if(StringUtils.isNotEmpty(isAperiodic)){
			searchConds.put("isAperiodic", isAperiodic);
		}
        if(StringUtils.isNotEmpty(salesChannels)){
            searchConds.put("salesChannels", salesChannels);
            if(salesChannels.equals("true")){
                searchConds.put("isOfficial", true);
            }
            if(salesChannels.equals("false")){
                searchConds.put("notOfficial", true);
            }

		}

		return searchConds;
	}

	 
	/**
	 * 是否可以查看所有数据(true:不能看所有数据；false:可以看所有数据)
	 * 
	 * @param hasAllDataRole
	 * @return
	 */
	private boolean isProdNotSeeAllData() {
		Boolean hasAllDataRole = permRoleService.checkUserRole(getSessionUser().getUserId(), Constant.PERM_ROLE_ALLDATA);
		
		return !getSessionUser().isAdministrator()&&!hasAllDataRole;
	}


	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	 

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName.trim();
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId.trim();
	}

	public List<CodeItem> getFilialeNameList() {
		return CodeSet.getInstance().getCodeListAndBlank(Constant.CODE_TYPE.FILIALE_NAME.name());
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	public List<CodeItem> getSubProductTypeList() {
		return ProductUtil.getSubProductTypeList(productType,true);
	}

	/**
	 * @return the selfPack
	 */
	public String getSelfPack() {
		return selfPack;
	}

	/**
	 * @param selfPack the selfPack to set
	 */
	public void setSelfPack(String selfPack) {
		this.selfPack = selfPack;
	}
	
	

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}

	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	/**
	 * @return the prodBranchId
	 */
	public String getProdBranchId() {
		return prodBranchId;
	}

	/**
	 * @param prodBranchId the prodBranchId to set
	 */
	public void setProdBranchId(String prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isVisaProduct() {
		return isVisaProduct;
	}

	public void setVisaProduct(boolean isVisaProduct) {
		this.isVisaProduct = isVisaProduct;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

    public String getSalesChannels() {
        return salesChannels;
    }

    public void setSalesChannels(String salesChannels) {
        this.salesChannels = salesChannels;
    }

    public String getSearchAuditingStatus() {
        return searchAuditingStatus;
    }

    public void setSearchAuditingStatus(String searchAuditingStatus) {
        this.searchAuditingStatus = searchAuditingStatus;
    }

	public String getFreeBackable() {
		return freeBackable;
	}

	public void setFreeBackable(String freeBackable) {
		this.freeBackable = freeBackable;
	}

	public void setBounsReturnScaleService(
			BounsReturnScaleService bounsReturnScaleService) {
		this.bounsReturnScaleService = bounsReturnScaleService;
	}
}
