package com.lvmama.back.sweb.distribution;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.utils.MD5;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionProductCategory;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.distribution.DistributorIp;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "searchListDistributor", location = "/WEB-INF/pages/back/distribution/distributor/searchListDistributor.jsp"),
	@Result(name = "saveOrUpdateDistributor", location = "/WEB-INF/pages/back/distribution/distributor/saveOrUpdateDistributor.jsp"),
	@Result(name = "searchDistributor", location = "/WEB-INF/pages/back/distribution/distributor/saveOrUpdateDistributor.jsp"),
	@Result(name = "searchListDistributorIP", location = "/WEB-INF/pages/back/distribution/distributor/searchListDistributorIp.jsp"),
	@Result(name = "saveOrUpdateDistributorIP", location = "/WEB-INF/pages/back/distribution/distributor/saveOrUpdateDistributorIp.jsp"),
	@Result(name = "searchDistributorIP", location = "/WEB-INF/pages/back/distribution/distributor/saveOrUpdateDistributorIp.jsp"),
	@Result(name = "editDistributionPro", location = "/WEB-INF/pages/back/distribution/distributor/editDistributionPro.jsp"),
	@Result(name = "searchDistributionProdCategory", location = "/WEB-INF/pages/back/distribution/distributor/editDistributionProCategory.jsp")
})
@ParentPackage("json-default")
public class DistributorManagementAction extends BackBaseAction{

	private static final long serialVersionUID = -1177388763818982952L;
	
	
	/**分销商名称*/
	private String distributorName;
	
	/**分销商ID*/
	private Long distributorInfoId;
	
	/**ip地址*/
	private String ip;
	
	/**分销商*/
	private DistributorInfo distributor = new DistributorInfo();
	
	
	/** 分销商返点*/
	private DistributionProductCategory distributionProductCategory = new DistributionProductCategory();
	
	/** 分销返佣点列表*/
	private List<DistributionProductCategory> distributionProductCategoryList;
	
	/** 分销商分页 */
	private Page<DistributorInfo> distributorPage = new Page<DistributorInfo>();
	
	/** 分销商ip列表*/
	private List<DistributorIp> listDistributorIp;
	
	
	/**分销商ip的主键id*/
	private Long distributorIpId;
	
	/**分销返佣点的主键id*/
	private Long distributionProductCategoryId;
	
	
	/**分销商ip*/
	private DistributorIp distributorIp;
	
	/** 关于分销的服务*/
	private DistributionService distributionService;
	
	/**	 查询分销商列表 */
	@Action("/distribution/searchListDistributor")
	public String searchListDistributor(){
		Map<String, Object> params = new HashMap<String, Object>();
		/*分销商名称*/
		if (!StringUtil.isEmptyString(this.distributorName)) {
			params.put("distributorName", this.distributorName.trim());
		}
		/*分销商id*/
		if(null != distributorInfoId){
			params.put("distributorInfoId", distributorInfoId.toString().trim());
		}
		distributorPage.setTotalResultSize(distributionService.selectDistributorByParamsCount(params));
		distributorPage.buildUrl(getRequest());
		distributorPage.setCurrentPage(super.page);
		params.put("start", distributorPage.getStartRows());
		params.put("end", distributorPage.getEndRows());
		if(distributorPage.getTotalResultSize()>0){
			distributorPage.setItems(distributionService.selectDistributorByParams(params));
		}
		
		return "searchListDistributor";
	}
	
	@Action("/distribution/searchDistributor")
	public String searchDistributor(){
		distributor= this.distributionService.selectByDistributorId(distributorInfoId);
		return "searchDistributor";
	}
	
	/**
	 * 新增或修改分销商
	 */
	@Action("/distribution/saveOrUpdateDistributor")
	public void saveOrUpdateDistributor(){
		ResultHandle result = new ResultHandle();
		checkTrueOrFalseDomainDistributor(distributor);
		if(this.distributor.getDistributorInfoId() == null){
			this.getKey(distributor);
			this.distributionService.insertDistributorInfo(distributor);
		}else{
			this.distributionService.updateDistributorInfo(distributor);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	/**
	 * 查询分销商ip列表 
	 */
	@Action("/distribution/searchListDistributorIP")
	public String searchListDistributorIP(){
		listDistributorIp = distributionService.getDistributorIpByDistributorInfoId(distributorInfoId);
		return "searchListDistributorIP";
	}
	
	/**
	 * 
	 */
	@Action("/distribution/searchDistributorIP")
	public String searchDistributorIP(){
		distributorIp = distributionService.getDistributorIpByDistributorIpId(distributorIpId);
		return "searchDistributorIP";
	}
	
	/**
	 *  新增或修改分销商跳转页面
	 */
	@Action("/distribution/saveOrUpdateDistributorIP")
	public void saveOrUpdateDistributorIP(){
		ResultHandle result = new ResultHandle();
		if(distributorIp.getDistributorIpId()==null){//新增
			this.distributionService.insertDistributorIp(distributorIp);
		}else{//修改
			this.distributionService.updateDistributorIpByDistributorIpId(distributorIp);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	
	
	/**
	 * 删除分销商ip, ajax返回json格式数据
	 */
	@Action("/distribution/deleteDistributorIP")
	public void deleteDistributorIP(){
		JSONResult result=new JSONResult();
		try{
			this.distributionService.deleteDistributorIpByDistributorIpId(distributorIpId);
			result.put("result", "1");
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 修改分销产品设置 
	 */
	@Action("/distribution/editDistributionPro")
	public String editDistributionPro(){
		
		return "editDistributionPro";
	}
	
	
	/**
	 * 查询分销商下的分销返点列表 
	 */
	@Action("/distribution/searchDistributionProdCategory")
	public String searchDistributionProdCategory(){
		distributionProductCategoryList = this.distributionService.selectDistributionProductCategory(distributorInfoId);
		return "searchDistributionProdCategory";
	}
	
	/**
	 * 删除分销返佣点
	 */
	@Action("/distribution/deleteDistributionProdCategory")
	public void deleteDistributionProdCategory(){
		JSONResult result=new JSONResult();
		try{
			this.distributionService.deleteDistributorProductCategory(this.distributionProductCategoryId);
			result.put("result","1");
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 插入一个分销返佣点
	 */
	@Action("/distribution/insertDistributionProdCategory")
	public void insertDistributionProdCategory(){
		ResultHandle result = new ResultHandle();
		Map<String,Object> params = new HashMap<String,Object>();
		if(null != this.distributionProductCategory.getDistributorInfoId()){
			params.put("distributorInfoId", distributionProductCategory.getDistributorInfoId());
		}
		if(!StringUtil.isEmptyString(this.distributionProductCategory.getPayOnline())){
			params.put("payOnline", distributionProductCategory.getPayOnline());
		}
		if(!StringUtil.isEmptyString(this.distributionProductCategory.getSubProductType())){
			params.put("subProductType", distributionProductCategory.getSubProductType());
		}
		if(!StringUtil.isEmptyString(this.distributionProductCategory.getProductType())){
			params.put("productType", distributionProductCategory.getProductType());
		}
		Long count = this.distributionService.selectPistributionProductCategoryConditionByCount(params);
		
		//产品类型为门票的插入数据
		if(count == 0){
			this.distributionService.insertDistributionProductCategory(distributionProductCategory);
		}else{
			result.setMsg("已经插入过同样的数据，不许重复添加");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	
	/**
	 * 分销商规则密钥
	 * @param distributor
	 */
	public void getKey(DistributorInfo distributor){
		try {
			String key = MD5.codeByUTF8(distributor.getDistributorCode()+distributor.getDistributorName()+String.valueOf(new Date().getTime())+"LVMAMA");
			distributor.setDistributorKey(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查boolean字段的传值 
	 */
	private void checkTrueOrFalseDomainDistributor(DistributorInfo distributor){
		if(distributor.getIsAddNewprod().equals("on")){
			distributor.setIsAddNewprod("true");
		}else{
			distributor.setIsAddNewprod("false");
		}
		if(distributor.getIsPushUpdate().equals("on")){
			distributor.setIsPushUpdate("true");
		}else{
			distributor.setIsPushUpdate("false");
		}
		if(distributor.getIsRegisterUser().equals("on")){
			distributor.setIsRegisterUser("true");
		}else{
			distributor.setIsRegisterUser("false");
		}
	}
	

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	

	public Long getDistributorInfoId() {
		return distributorInfoId;
	}

	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}

	public Page<DistributorInfo> getDistributorPage() {
		return distributorPage;
	}

	public void setDistributorPage(Page<DistributorInfo> distributorPage) {
		this.distributorPage = distributorPage;
	}

	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public DistributorInfo getDistributor() {
		return distributor;
	}

	public void setDistributor(DistributorInfo distributor) {
		this.distributor = distributor;
	}

	public List<DistributorIp> getListDistributorIp() {
		return listDistributorIp;
	}

	public void setListDistributorIp(List<DistributorIp> listDistributorIp) {
		this.listDistributorIp = listDistributorIp;
	}

	public Long getDistributorIpId() {
		return distributorIpId;
	}

	public void setDistributorIpId(Long distributorIpId) {
		this.distributorIpId = distributorIpId;
	}

	public DistributorIp getDistributorIp() {
		return distributorIp;
	}

	public void setDistributorIp(DistributorIp distributorIp) {
		this.distributorIp = distributorIp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public DistributionProductCategory getDistributionProductCategory() {
		return distributionProductCategory;
	}

	public void setDistributionProductCategory(
			DistributionProductCategory distributionProductCategory) {
		this.distributionProductCategory = distributionProductCategory;
	}

	public Long getDistributionProductCategoryId() {
		return distributionProductCategoryId;
	}

	public void setDistributionProductCategoryId(Long distributionProductCategoryId) {
		this.distributionProductCategoryId = distributionProductCategoryId;
	}

	public List<DistributionProductCategory> getDistributionProductCategoryList() {
		return distributionProductCategoryList;
	}

	public void setDistributionProductCategoryList(
			List<DistributionProductCategory> distributionProductCategoryList) {
		this.distributionProductCategoryList = distributionProductCategoryList;
	}

	
}
