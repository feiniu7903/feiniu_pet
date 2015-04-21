package com.lvmama.pet.sweb.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdContainerProduct;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;
import com.lvmama.comm.pet.service.pub.ComLogService;
@Results({
    @Result(name = "container", location = "/WEB-INF/pages/back/container/container_list.jsp")
})
public class ContainerAction extends BackBaseAction {
	private ProdContainerProductService prodContainerProductService;
	private List<ProdContainer> prodContainerList;
	private List<ProdContainerProduct> prodContainerProductList;
	private String containerCode;
	private String fromPlace;
	private String toPlace;
	private String toParentPlace;
	private String subProductType;
	private String productId;
	private String productName;
	private String sortType;
	private String recommendSeq;
	private String containerProductId;
	private String oldRecommendSeq;
	private String isValid;
	private ComLogService comLogRemoteService;
	
	@Action("/container/container")
	public String container() throws Exception {
		pagination=initPage();
		pagination.setPageSize(15);
		Map<String,Object> param=builderParam();
		pagination.setTotalResultSize(prodContainerProductService.getContainerProductListCount(param));
		if(pagination.getTotalResultSize()>0){
			param.put("startRow", pagination.getStartRows());
			param.put("endRow", pagination.getEndRows());
			prodContainerProductList=prodContainerProductService.getContainerProductList(param);
		}
		prodContainerList=prodContainerProductService.getContainerNameCodePairs();
		pagination.buildUrl(getRequest());
		return "container";
	}
	@Action("/container/updateRecommendSeq")
	public String updateRecommendSeq() throws Exception{
		try{
			
			prodContainerProductService.updateRecommendSeq(new Long(containerProductId),Integer.parseInt(recommendSeq),Integer.parseInt(oldRecommendSeq),this.getSessionUserName());
			this.outputToClient("success");
		}catch(Exception ex){
			ex.printStackTrace();
			this.outputToClient("error");
		}
		return null;
	}
	@Action("/container/showOrHide")
	public String showOrHide() throws Exception{
		try{
			prodContainerProductService.containerProductShowOrHide(new Long(containerProductId),isValid,this.getSessionUserName());
			this.outputToClient("success");
		}catch(Exception ex){
			ex.printStackTrace();
			this.outputToClient("error");
		}
		return null;
	}
	protected Map<String, Object> builderParam() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerCode", containerCode);
		params.put("fromPlaceId", fromPlace);
		params.put("toPlaceId", toPlace);
		if(toParentPlace!=null&&!"".equals(toParentPlace)){
			params.put("toPlaceId", toParentPlace);
		}
		params.put("subProductType", subProductType);
		params.put("productId", productId);
		params.put("productName", productName);
		params.put("sortType", sortType);
		return params;
	}
	@Action("/container/getFromPlaceList")
	public String getFromPlaceList() throws Exception{
		StringBuffer sb=new StringBuffer("");
		try{
			List<ProdContainer> fromPlaceList = prodContainerProductService.getFromPlacesByContainerCode(this.containerCode);
			if(fromPlaceList!=null&&fromPlaceList.size()>0){
				sb.append("{data:[");
				boolean b=false;
				for(ProdContainer prodContainer:fromPlaceList){
					if(StringUtils.isNotBlank(prodContainer.getFromPlaceName())){
						b=true;
						sb.append("{fromPlaceName:\""+prodContainer.getFromPlaceName()+"\",fromPlaceId:\""+prodContainer.getFromPlaceId()+"\"},");
					}
				}
				String result="";
				if(b){
					result=sb.toString().substring(0,sb.toString().length()-1)+"]}";
				}else{
					result=sb.toString()+"]}";
				}
				this.outputToClient(result);
			}else{
				sb.append("{data:[]}");
				this.outputToClient(sb.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			sb.append("{data:[]}");
			this.outputToClient(sb.toString());
		}
		return null;
	}
	@Action("/container/getProductTypeList")
	public String getProductTypeList() throws Exception{
		StringBuffer sb=new StringBuffer("");
		if(StringUtils.isNotBlank(containerCode)){
			sb.append("{data:[");
			if(containerCode.equals("GNY_RECOMMEND")){
				sb.append("{name:\"全部\",value=\"\"},");
				sb.append("{name:\"长途跟团游\",value=\"GROUP_LONG\"},");
				sb.append("{name:\"长途自由行\",value=\"FREENESS_LONG\"}");
			}else if(containerCode.equals("ZBY_RECOMMEND")){
				sb.append("{name:\"全部\",value=\"\"},");
				sb.append("{name:\"短途跟团游\",value=\"GROUP\"},");
				sb.append("{name:\"自助巴士班\",value=\"SELFHELP_BUS\"}");
			}else if(containerCode.equals("CJY_RECOMMEND")){
				sb.append("{name:\"全部\",value=\"\"},");
				sb.append("{name:\"出境跟团游\",value=\"GROUP_FOREIGN\"},");
				sb.append("{name:\"出境自由行\",value=\"FREENESS_FOREIGN\"}");
			}else{
				sb.append("{name:\"全部\",value=\"\"}");
			}
			sb.append("]}");
		}else{
			sb.append("{data:[]}");
		}
		this.outputToClient(sb.toString());
		return null;
	}
	@Action("/container/getToPlaceList")
	public String getToPlaceList() throws Exception{
		StringBuffer sb=new StringBuffer("");
		try{
			List<ProdContainer> toPlaceList=new ArrayList<ProdContainer>();
			if (prodContainerProductService.isFromPlaceEmpty(this.containerCode)) {
				toPlaceList = prodContainerProductService.getToPlacesByContainerCodeAndDestId(this.containerCode, "3548");
			} else if(fromPlace!=null) {
				toPlaceList = prodContainerProductService.getToPlacesByContainerCodeAndFromPlaceId(this.containerCode, fromPlace);
			}
			if(toPlaceList!=null&&toPlaceList.size()>0){
				sb.append("{data:[");
				boolean b=false;
				for(ProdContainer prodContainer:toPlaceList){
					if(StringUtils.isNotBlank(prodContainer.getToPlaceName())){
						b=true;
						sb.append("{toPlaceName:\""+prodContainer.getToPlaceName()+"\",toPlaceId:\""+prodContainer.getToPlaceId()+"\"},");
					}
				}
				String result="";
				if(b){
					result=sb.toString().substring(0,sb.toString().length()-1)+"]}";
				}else{
					result=sb.toString()+"]}";
				}
				this.outputToClient(result);
			}else{
				sb.append("{data:[]}");
				this.outputToClient(sb.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			sb.append("{data:[]}");
			this.outputToClient(sb.toString());
		}
		return null;
	}
	
	@Action("/container/getToParentPlaceList")
	public String getToParentPlaceList() throws Exception{
		StringBuffer sb=new StringBuffer("");
		try{
			List<ProdContainer> toPlaceList=new ArrayList<ProdContainer>();
			if(toParentPlace!=null&&!"".equals(toParentPlace)){
				toPlaceList = prodContainerProductService.getToPlacesByContainerCodeAndDestId(this.containerCode, toParentPlace);
			}
			if(toPlaceList!=null&&toPlaceList.size()>0){
				sb.append("{data:[");
				boolean b=false;
				for(ProdContainer prodContainer:toPlaceList){
					if(StringUtils.isNotBlank(prodContainer.getToPlaceName())){
						b=true;
						sb.append("{toPlaceName:\""+prodContainer.getToPlaceName()+"\",toPlaceId:\""+prodContainer.getToPlaceId()+"\"},");
					}
				}
				String result="";
				if(b){
					result=sb.toString().substring(0,sb.toString().length()-1)+"]}";
				}else{
					result=sb.toString()+"]}";
				}
				this.outputToClient(result);
			}else{
				sb.append("{data:[]}");
				this.outputToClient(sb.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			sb.append("{data:[]}");
			this.outputToClient(sb.toString());
		}
		return null;
	}
	
	@Action("/container/getContainerOprationLog")
	public String getContainerOprationLog() throws Exception{
		StringBuffer sb=new StringBuffer("");
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("objectType", "CONTAINER_PRODUCT");
			param.put("objectId", containerProductId);
			param.put("skipResults",0);
			param.put("maxResults",Integer.MAX_VALUE);
			List<ComLog> logList=comLogRemoteService.queryByObjectIdMap(param);
			if(logList!=null&&logList.size()>0){
				sb.append("{data:[");
				boolean b=false;
				for(ComLog comLog:logList){
					b=true;
					sb.append("{createTime:\""+comLog.getCreateTime().toLocaleString()+"\",oprName:\""+comLog.getOperatorName()+"-"+comLog.getLogName()+"\",content:\""+comLog.getContent()+"\"},");
				}
				String result="";
				if(b){
					result=sb.toString().substring(0,sb.toString().length()-1)+"]}";
				}else{
					result=sb.toString()+"]}";
				}
				this.outputToClient(result);
			}else{
				sb.append("{data:[]}");
				this.outputToClient(sb.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			sb.append("{data:[]}");
			this.outputToClient(sb.toString());
		}
		return null;
	}
	public ProdContainerProductService getProdContainerProductService() {
		return prodContainerProductService;
	}
	public void setProdContainerProductService(
			ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}
	public List<ProdContainer> getProdContainerList() {
		return prodContainerList;
	}
	public void setProdContainerList(List<ProdContainer> prodContainerList) {
		this.prodContainerList = prodContainerList;
	}
	public String getContainerCode() {
		return containerCode;
	}
	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public List<ProdContainerProduct> getProdContainerProductList() {
		return prodContainerProductList;
	}
	public void setProdContainerProductList(
			List<ProdContainerProduct> prodContainerProductList) {
		this.prodContainerProductList = prodContainerProductList;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public String getRecommendSeq() {
		return recommendSeq;
	}
	public void setRecommendSeq(String recommendSeq) {
		this.recommendSeq = recommendSeq;
	}
	public String getContainerProductId() {
		return containerProductId;
	}
	public void setContainerProductId(String containerProductId) {
		this.containerProductId = containerProductId;
	}
	public String getOldRecommendSeq() {
		return oldRecommendSeq;
	}
	public void setOldRecommendSeq(String oldRecommendSeq) {
		this.oldRecommendSeq = oldRecommendSeq;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}
	public String getToParentPlace() {
		return toParentPlace;
	}
	public void setToParentPlace(String toParentPlace) {
		this.toParentPlace = toParentPlace;
	}
	
}
