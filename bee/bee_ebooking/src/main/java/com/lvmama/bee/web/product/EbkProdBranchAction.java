package com.lvmama.bee.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;
@Results(value = {
		@Result(name = "SURROUNDING_GROUPebkProdBranch", location = "/WEB-INF/pages/ebooking/product/ebkGroupProdBranch.jsp"),
		@Result(name = "DOMESTIC_LONGebkProdBranch", location = "/WEB-INF/pages/ebooking/product/ebkLongProdBranch.jsp"),
		@Result(name = "ABROAD_PROXYebkProdBranch", location = "/WEB-INF/pages/ebooking/product/ebkProxyProdBranch.jsp"),
		@Result(name = "SURROUNDING_GROUPqueryEbkProdBranch", location = "/WEB-INF/pages/ebooking/product/subPage/queryGroupEbkProdBranch.jsp"),
		@Result(name = "DOMESTIC_LONGqueryEbkProdBranch", location = "/WEB-INF/pages/ebooking/product/subPage/queryLongEbkProdBranch.jsp"),
		@Result(name = "ABROAD_PROXYqueryEbkProdBranch", location = "/WEB-INF/pages/ebooking/product/subPage/queryProxyEbkProdBranch.jsp"),
		@Result(name = "setEbkVirtualBranchInit", location = "/WEB-INF/pages/ebooking/product/subPage/setEbkVirtualBranch.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProdBranch", location = "/WEB-INF/pages/ebooking/product/subPage/editGroupEbkProdBranch.jsp"),
		@Result(name = "DOMESTIC_LONGeditEbkProdBranch", location = "/WEB-INF/pages/ebooking/product/subPage/editLongEbkProdBranch.jsp"),
		@Result(name = "ABROAD_PROXYeditEbkProdBranch", location = "/WEB-INF/pages/ebooking/product/subPage/editProxyEbkProdBranch.jsp")
		})
public class EbkProdBranchAction extends BaseEbkProductAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1913835722075543350L;
	private EbkProdBranch ebkProdBranch;
	private Long prodBranchId;
	private String prodBranchType;
	private Map<String,String> branchTypeMap;
	private List<EbkProdBranch> ebkProdBranchList;
	private String virtualBranchIds;
	
	@Autowired
	private EbkProdBranchService ebkProdBranchService;
	@Autowired
	private EbkProdProductService ebkProdProductService;
	@Autowired
	private ComLogService comLogService;
	
	
	
	/**
	 * 
	 * @return
	 */
	@Action(value="/product/branch/ebkProdBranch")
	public String ebkProdBranch(){
		return queryEbkProductList("ebkProdBranch");
	}
	
	/**
	 * 根据EBK产品编号查询所有类型
	 * @return
	 */
	@Action(value="/product/branch/queryEbkProdBranch")
	public String queryEbkProdBranch(){
		return queryEbkProductList("queryEbkProdBranch");
	}
	
	/**
	 * 根据EBK产品类别编号查询类别
	 * @return
	 */
	@Action(value="/product/branch/editEbkProdBranch")
	public String editEbkProdBranch(){
		if(null!=prodBranchId){
			ebkProdBranch = ebkProdBranchService.queryForEbkProdBranchId(prodBranchId);
			if(null!=ebkProdBranch){
				ebkProdProductId = ebkProdBranch.getProdProductId();
			}
		}
		String result = isSupplierEbkProd();
		if(null!=result){
			return result;
		}
		initBranchType();
		return ebkProductViewType+"editEbkProdBranch";
	}
	/**
	 * 根据EBK产品查询需要共享库存的类别
	 * @return
	 */
	@Action(value="/product/branch/setEbkVirtualBranchInit")
	public String setEbkVirtualBranchInit(){
		if(null!=ebkProdProductId&&null!=prodBranchId){
			EbkProdBranch virtualBranch = ebkProdBranchService.queryForEbkProdBranchId(prodBranchId);
			String branchIds = virtualBranch.getVirtualBranchIds();
			List<String> virtualIds = new ArrayList<String>();
			if(StringUtils.isNotBlank(branchIds)){
				String ids[] = branchIds.split(",");
				for(String id : ids){
					virtualIds.add(id);
				}
			}
			EbkProdBranch branch = new EbkProdBranch();
			branch.setProdProductId(ebkProdProductId);
			List<EbkProdBranch> branchs  = ebkProdBranchService.query(branch);
			ebkProdBranchList = new ArrayList<EbkProdBranch>();
			if(null!=branchs&&branchs.size()>0){
				for(EbkProdBranch bch : branchs){
					if(!Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(bch.getBranchType())){
						if(virtualIds.contains(bch.getProdBranchId().toString())){
							bch.setInVirtualBranch(true);
						}
						ebkProdBranchList.add(bch);
					}
				}
			}
		}
		String result = isSupplierEbkProd();
		if(null!=result){
			return result;
		}
		return "setEbkVirtualBranchInit";
	}
	
	/**
	 * 删除产品类别
	 */
	@Action(value="/product/branch/saveEbkVirtualBranch")
	public void saveEbkVirtualBranch(){
		JSONObject json=new JSONObject();
		if(null==ebkProdProductId||null==prodBranchId){
			json.put("success", Boolean.FALSE);
			json.put("message", "-2");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		
		EbkProdBranch oldEbkProdBranch = ebkProdBranchService.queryForEbkProdBranchId(prodBranchId);
		oldEbkProdBranch.setVirtualBranchIds(virtualBranchIds);
		try{
			ebkProdBranchService.update(oldEbkProdBranch);
			comLogService.insert("EBK_PROD_BRANCH", ebkProdProductId, prodBranchId, super.getSessionUserName(), "deleteEbkProdBranch", "设置共享库存类别", "设置共享库存类别ID为"+virtualBranchIds, "EBK_PROD_PRODUCT");
			json.put("success", Boolean.TRUE);
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", "-500");
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	
	
	/**
	 *查询指定类型的类别
	 */
	@Action(value="/product/branch/searchEbkProdBranch")
	public void searchEbkProdBranch(){
		JSONObject json=new JSONObject();
		if(null==ebkProdProductId||null==prodBranchType){
			json.put("success", Boolean.FALSE);
			json.put("message", "-1");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		try{
			EbkProdBranch branch = new EbkProdBranch();
			branch.setProdProductId(ebkProdProductId);
			branch.setBranchType(prodBranchType);
			List<EbkProdBranch> currentList  = ebkProdBranchService.query(branch);
			if(currentList==null || (currentList!=null && currentList.isEmpty())){
				json.put("success", Boolean.TRUE);
			}else{
				json.put("success", Boolean.FALSE);
				json.put("message", "此类型类别已存在");
			}
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", "-500");
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	
	/**
	 *插入产品类别
	 */
	@Action(value="/product/branch/insertEbkProdBranch")
	public void insertEbkProdBranch(){
		JSONObject json=new JSONObject();
		if(null==ebkProdBranch){
			json.put("success", Boolean.FALSE);
			json.put("message", "-1");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		if(null==ebkProdProductId){
			ebkProdProductId = ebkProdBranch.getProdProductId();
		}else{
			ebkProdBranch.setProdProductId(ebkProdProductId);
		}
		if(!isSupplierEbkProductJson() || !isEditEbkEditStatusJson()){
			return;
		}
		try{
			EbkProdBranch branch = new EbkProdBranch();
			branch.setProdProductId(ebkProdProductId);
			List<EbkProdBranch> currentList  = ebkProdBranchService.query(branch);
			if(!Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(ebkProdBranch.getBranchType())){
				if(currentList==null || (currentList!=null && currentList.isEmpty())){
					ebkProdBranch.setDefaultBranch(Constant.TRUE_FALSE.TRUE.getCode());
				}else{
					if(currentList.size()==1&&Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(currentList.get(0).getBranchType())){
						ebkProdBranch.setDefaultBranch(Constant.TRUE_FALSE.TRUE.getCode());
					}
				}
			}
			ebkProdBranch =ebkProdBranchService.insert(ebkProdBranch);
			comLogService.insert("EBK_PROD_BRANCH", ebkProdProductId, ebkProdBranch.getProdBranchId(), super.getSessionUserName(), "insertEbkProdBranch", "新增产品类别", "新增"+ebkProdProduct.getMetaName()+" 产品类别 "+ebkProdBranch.getBranchName(), "EBK_PROD_PRODUCT");
			json.put("success", Boolean.TRUE);
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", "-500");
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	
	/**
	 *更新产品类别
	 */
	@Action(value="/product/branch/updateEbkProdBranch")
	public void updateEbkProdBranch(){
		JSONObject json=new JSONObject();
		if(null==ebkProdBranch){
			json.put("success", Boolean.FALSE);
			json.put("message", "-1");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		EbkProdBranch oldEbkProdBranch = ebkProdBranchService.queryForEbkProdBranchId(prodBranchId);
		if(null!=oldEbkProdBranch){
			ebkProdProductId = oldEbkProdBranch.getProdProductId();
			ebkProdBranch.setProdProductId(ebkProdProductId);
		}
		if(null==ebkProdProductId){
			ebkProdProductId = ebkProdBranch.getProdProductId();
		}else{
			ebkProdBranch.setProdProductId(ebkProdProductId);
		}
		if(!isSupplierEbkProductJson() || !isEditEbkEditStatusJson()){
			return;
		}

		oldEbkProdBranch.setEbkProductViewType(ebkProductViewType);
		try{
			ebkProdBranchService.update(ebkProdBranch);
			comLogService.insert("EBK_PROD_BRANCH", ebkProdProductId, prodBranchId, super.getSessionUserName(), "updateEbkProdBranch", "更新产品类别", "更新"+ebkProdProduct.getMetaName()+" 产品类别 "+ebkProdBranch.getBranchName()+": 以前信息 \r\n"+
					" 产品名称："+oldEbkProdBranch.getBranchName()+"\r\n "+
					" 产品类型："+oldEbkProdBranch.getBranchTypeCh()+"\r\n "+
					" 成人数："+oldEbkProdBranch.getAdultQuantity()+"\r\n "+
					" 儿童数："+oldEbkProdBranch.getChildQuantity(), "EBK_PROD_PRODUCT");
			json.put("success", Boolean.TRUE);
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", "-500");
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	@Action(value="/product/branch/updateEbkProdBranchFirst")
	public void updateEbkProdBranchFirst(){
		JSONObject json=new JSONObject();
		if(null==prodBranchId){
			json.put("success", Boolean.FALSE);
			json.put("message", "-2");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		EbkProdBranch oldEbkProdBranch = ebkProdBranchService.queryForEbkProdBranchId(prodBranchId);
		oldEbkProdBranch.setEbkProductViewType(ebkProductViewType);
		if(null!=oldEbkProdBranch){
			ebkProdProductId = oldEbkProdBranch.getProdProductId();
		}
		if(!isSupplierEbkProductJson() || !isEditEbkEditStatusJson()){
			return;
		}
		try{
			EbkProdBranch prodBranch = new EbkProdBranch();
			prodBranch.setProdProductId(ebkProdProductId);
			prodBranch.setDefaultBranch(Constant.TRUE_FALSE.FALSE.getCode());
			ebkProdBranchService.update(prodBranch);
			prodBranch.setProdProductId(null);
			prodBranch.setProdBranchId(prodBranchId);
			prodBranch.setDefaultBranch(Constant.TRUE_FALSE.TRUE.getCode());
			ebkProdBranchService.update(prodBranch);
			comLogService.insert("EBK_PROD_BRANCH", ebkProdProductId, prodBranchId, super.getSessionUserName(), "updateEbkProdBranchFirst", "设置产品主类别", "设置"+ebkProdProduct.getMetaName()+" 产品类别 "+oldEbkProdBranch.getBranchName()+"为主类别", "EBK_PROD_PRODUCT");
			json.put("success", Boolean.TRUE);
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", "-500");
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	
	/**
	 * 查询此类别是否设置过共享库存
	 */
	@Action(value="/product/branch/queryVirtualBranch")
	public void queryVirtualBranch(){
		JSONObject json=new JSONObject();
		if(null==prodBranchId||null==ebkProdProductId){
			json.put("success", Boolean.FALSE);
			json.put("message", "-2");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}

		EbkProdBranch ebkProdBranch = new EbkProdBranch();
		ebkProdBranch.setProdProductId(ebkProdProductId);
		List<EbkProdBranch> ebkProdBranchs = ebkProdBranchService.query(ebkProdBranch);
		if(null==ebkProdBranchs){
			json.put("success", Boolean.FALSE);
			json.put("message", "-500");
		}else{
			String virtualBranchIds = "";
			for(EbkProdBranch branch : ebkProdBranchs){
				if(Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(branch.getBranchType())){
					virtualBranchIds = branch.getVirtualBranchIds();
				}
			}
			if(StringUtils.isNotBlank(virtualBranchIds)&&virtualBranchIds.indexOf(prodBranchId.toString())!=-1){
				json.put("success", Boolean.FALSE);
				json.put("message", "false");
			}else{
				json.put("success", Boolean.TRUE);
			}
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	
	/**
	 * 删除产品类别
	 */
	@Action(value="/product/branch/deleteEbkProdBranch")
	public void deleteEbkProdBranch(){
		JSONObject json=new JSONObject();
		if(null==prodBranchId){
			json.put("success", Boolean.FALSE);
			json.put("message", "-2");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		
		EbkProdBranch oldEbkProdBranch = ebkProdBranchService.queryForEbkProdBranchId(prodBranchId);
		oldEbkProdBranch.setEbkProductViewType(ebkProductViewType);
		if(null!=oldEbkProdBranch){
			ebkProdProductId = oldEbkProdBranch.getProdProductId();
		}
		if(!isSupplierEbkProductJson() || !isEditEbkEditStatusJson()){
			return;
		}
		try{
			ebkProdBranchService.delete(prodBranchId);
			comLogService.insert("EBK_PROD_BRANCH", ebkProdProductId, prodBranchId, super.getSessionUserName(), "deleteEbkProdBranch", "删除产品类别", "删除"+ebkProdProduct.getMetaName()+" 产品类别 "+oldEbkProdBranch.getBranchName()+": 以前信息 \r\n"+
					" 产品名称："+oldEbkProdBranch.getBranchName()+"\r\n "+
					" 产品类型："+oldEbkProdBranch.getBranchTypeCh()+"\r\n "+
					" 成人数："+oldEbkProdBranch.getAdultQuantity()+"\r\n "+
					" 儿童数："+oldEbkProdBranch.getChildQuantity(), "EBK_PROD_PRODUCT");
			json.put("success", Boolean.TRUE);
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", "-500");
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	
	private String queryEbkProductList(final String resultSuccesssPage){
		String result = isSupplierEbkProd();
		if(null!=result){
			return result;
		}
		initBranchType();
		EbkProdBranch ebkProdBranch = new EbkProdBranch();
		ebkProdBranch.setProdProductId(ebkProdProductId);
		ebkProdBranchList = ebkProdBranchService.query(ebkProdBranch);
		return ebkProductViewType+resultSuccesssPage;
	}
	private void initBranchType(){
		branchTypeMap = new HashMap<String,String>();
		EbkProdProduct ebkProdProduct = ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		String productType = ebkProdProduct.getProductType();
		if(Constant.EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equalsIgnoreCase(productType) ||
		   Constant.EBK_PRODUCT_VIEW_TYPE.DOMESTIC_LONG.name().equalsIgnoreCase(productType)||
		   Constant.EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name().equalsIgnoreCase(productType)){
			Constant.ROUTE_BRANCH[] branchs = Constant.ROUTE_BRANCH.values();
			for(Constant.ROUTE_BRANCH branch:branchs){
				branchTypeMap.put(branch.name(), branch.getCnName());
			}
		}else if(Constant.EBK_PRODUCT_VIEW_TYPE.HOTEL.name().equalsIgnoreCase(productType)){
			Constant.HOTEL_BRANCH[] branchs = Constant.HOTEL_BRANCH.values();
			for(Constant.HOTEL_BRANCH branch:branchs){
				branchTypeMap.put(branch.name(), branch.getCnName());
			}
		}
	}
	public EbkProdBranch getEbkProdBranch() {
		return ebkProdBranch;
	}
	public void setEbkProdBranch(EbkProdBranch ebkProdBranch) {
		this.ebkProdBranch = ebkProdBranch;
	}
	public Long getProdBranchId() {
		return prodBranchId;
	}
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}
	public Map<String, String> getBranchTypeMap() {
		return branchTypeMap;
	}
	public void setBranchTypeMap(Map<String, String> branchTypeMap) {
		this.branchTypeMap = branchTypeMap;
	}
	public List<EbkProdBranch> getEbkProdBranchList() {
		return ebkProdBranchList;
	}
	public void setEbkProdBranchList(List<EbkProdBranch> ebkProdBranchList) {
		this.ebkProdBranchList = ebkProdBranchList;
	}
	public String getToShowEbkProduct() {
		return toShowEbkProduct;
	}
	public void setToShowEbkProduct(String toShowEbkProduct) {
		this.toShowEbkProduct = toShowEbkProduct;
	}
	public String getProdBranchType() {
		return prodBranchType;
	}

	public void setProdBranchType(String prodBranchType) {
		this.prodBranchType = prodBranchType;
	}

	public String getVirtualBranchIds() {
		return virtualBranchIds;
	}

	public void setVirtualBranchIds(String virtualBranchIds) {
		this.virtualBranchIds = virtualBranchIds;
	}
	
}
