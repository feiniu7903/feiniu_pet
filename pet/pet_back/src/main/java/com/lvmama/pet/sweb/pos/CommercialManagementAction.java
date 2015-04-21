package com.lvmama.pet.sweb.pos;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pay.PayPosCommercial;
import com.lvmama.comm.pet.service.pay.PayPosCommercialService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;

/**
 * 交通银行POS机商户管理.
 * @author liwenzhan
 */
@ParentPackage("json-default")
@Results({
		@Result(name = "commercial_insert", location = "/WEB-INF/pages/back/pos/commercial/commercial_insert.jsp"),
		@Result(name = "commercial_query", location = "/WEB-INF/pages/back/pos/commercial/commercial_management.jsp"),
		@Result(name = "commercial_update", location = "/WEB-INF/pages/back/pos/commercial/commercial_update.jsp") })
public class CommercialManagementAction extends BackBaseAction {
	/**
	 * .
	 */
	private static final long serialVersionUID = 3169950199306772468L;
	/**
	 * 商户实体类.
	 */
	private PayPosCommercial payPosCommercial;
	/**
	 * 查询商户名称.
	 */
	private String searchCommercialName;
	/**
	 * 查询商户号.
	 */
	private String searchCommercialNo;
	/**
	 * 查询商户状态 .
	 */
	private String searchStatus;
	/**
	 * 供应商.
	 */
	private String searchSupplier;
	/**
	 * 商户ID.
	 */
	private Long commercialId;
	/**
	 * 商户号.
	 */
	private String commercialNo;
	/**
	 * 商户名称.
	 */
	private String commercialName;
	/**
	 * 商户备注.
	 */
	private String remark;
	/**
	 * 商户状态 .
	 */
	private String status;
	
	private String supplier;
	/**
	 * POS商户服务.
	 */
	private PayPosCommercialService payPosCommercialService;
	
	
	private Page<PayPosCommercial> payPosCommerciaPage;
	private Long perPageRecord=10L;
	
	/**
	 * 
	 */
	public CommercialManagementAction() {
		super();
	}
	
	
	/**
	 * 获得添加页.
	 * @return
	 */
	@Action("/pos/goCommercialInsert")
	public String initInsert() {
		return "commercial_insert";
	}

	/**
	 * 获得查询页.
	 * @return
	 */
	@Action("/pos/goCommercialQuery")
	public String initQueryList() {
		return "commercial_query";
	}

	/**
	 * 获得更新页.
	 * @return
	 */
	@Action("/pos/goCommercialUpdate")
	public String initUpdate() {
		payPosCommercial = payPosCommercialService.selectById(commercialId);
		return "commercial_update";
	}
	
	/**
	 * 获得查询列表.
	 * @return
	 */
	@Action("/pos/commercialQueryList")
	public String queryList() {
		Map<String,String> param=buildParameter();
		payPosCommerciaPage =payPosCommercialService.selectPayPosCommercialByParam(param,perPageRecord,this.page);
		payPosCommerciaPage.setUrl(WebUtils.getUrl(getRequest(),param));
		return "commercial_query";
	}
	/**
	 * 初始化查询参数.
	 * @return
	 */
	private Map<String,String> buildParameter() {
		Map<String,String> params=new HashMap<String, String>();
		if (this.commercialId!=null) {
			params.put("commercialId", commercialId.toString());
		}
		if (StringUtils.isNotEmpty(searchStatus)) {
			params.put("status",searchStatus.toString());
		}
		if (StringUtils.isNotEmpty(searchCommercialName)) {
			params.put("commercialName", searchCommercialName.trim());
		}
		if (StringUtils.isNotEmpty(searchCommercialNo)) {
			params.put("commercialNo", searchCommercialNo.trim());
		}
		if (StringUtils.isNotEmpty(searchSupplier)) {
			params.put("supplier", searchSupplier.trim());
		}
		return params;
	}
	/**
	 * 更改商户状态.
	 */
	@Action("/pos/modifyStatusCommercial")
	public void changeStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(commercialId==null||commercialId<1),"商户号不可以为空");
			Assert.hasLength(status,"状态不可以为空");
			
			PayPosCommercial cpc = this.payPosCommercialService.selectById(commercialId);
			if(cpc==null){
				throw new Exception("您修改的商户信息不存在!");
			}
			cpc.setStatus(status);
			payPosCommercialService.modifyStatus(cpc, this.getSessionUserName(), status);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 更改商户供应商.
	 */
	@Action("/pos/modifySupplier")
	public void changeSupplier(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(commercialId==null||commercialId<1),"商户号不可以为空");
			Assert.hasLength(supplier,"供应商不可以为空");
			
			PayPosCommercial cpc = this.payPosCommercialService.selectById(commercialId);
			PayPosCommercial oldCpc = this.payPosCommercialService.selectById(commercialId);
			if(cpc==null){
				throw new Exception("您修改的商户信息不存在!");
			}
			cpc.setSupplier(this.supplier);
			payPosCommercialService.modifySupplier(cpc,oldCpc, this.getSessionUserName(), supplier);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 新增商户.
	 */
	@Action("/pos/insertCommercial")
	public void insertById(){
		JSONResult result = new JSONResult();
		try {
			Assert.hasLength(commercialNo.trim(), "商户号不可以为空");
			Assert.hasLength(commercialName.trim(), "商户名称不可以为空");
			
			Map<String,Object> params=new HashMap<String, Object>();
			if (this.commercialNo!=null) {
				params.put("commercialNo", commercialNo);
			}
            if(payPosCommercialService.getSelectCount(params)>0){
            	throw new Exception("该商户号已经存在,请确定商务号");
            }		  
            payPosCommercial=new PayPosCommercial();
            payPosCommercial.setCommercialName(commercialName);
            payPosCommercial.setCommercialNo(commercialNo);
            payPosCommercial.setRemark(remark);
            payPosCommercial.setStatus(status);
            payPosCommercial.setSupplier(supplier);
            Long commercialId = payPosCommercialService.insert(payPosCommercial, this.getSessionUserName());
			result.put("commercialId", commercialId);
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 更新商户信息.
	 */
	@Action("/pos/updateCommercial")
	public void updateById(){
		JSONResult result = new JSONResult();
		try {
			Assert.hasLength(commercialNo.trim(), "商户号不可以为空");
			Assert.hasLength(commercialName.trim(), "商户名称不可以为空");
			PayPosCommercial oldPayPosCommercial = payPosCommercialService.selectById(commercialId);
			Map<String,Object> params=new HashMap<String, Object>();
			if (this.commercialNo!=null) {
				params.put("commercialNo", commercialNo);
			}
            if(payPosCommercialService.getSelectCount(params)>0 && !oldPayPosCommercial.getCommercialNo().equalsIgnoreCase(commercialNo)){
            	throw new Exception("该商户号已经存在,请确定商务号");
            }	
            payPosCommercial=payPosCommercialService.selectById(commercialId);
            payPosCommercial.setCommercialId(commercialId);
            payPosCommercial.setCommercialName(commercialName);
            payPosCommercial.setCommercialNo(commercialNo);
            payPosCommercial.setRemark(remark);
            payPosCommercial.setSupplier(supplier);
            payPosCommercialService.update(oldPayPosCommercial,payPosCommercial,this.getSessionUserName());
			result.put("CommercialId", commercialId);
 		}catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
 		result.output(getResponse());
	}
	
	
	
	public Long getCommercialId() {
		return commercialId;
	}

	public void setCommercialId(Long commercialId) {
		this.commercialId = commercialId;
	}


	public void setPayPosCommercialService(
			PayPosCommercialService payPosCommercialService) {
		this.payPosCommercialService = payPosCommercialService;
	}

	public Long getPerPageRecord() {
		return perPageRecord;
	}

	public void setPerPageRecord(Long perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

	public PayPosCommercial getPayPosCommercial() {
		return payPosCommercial;
	}


	public void setPayPosCommercial(PayPosCommercial payPosCommercial) {
		this.payPosCommercial = payPosCommercial;
	}


	public String getCommercialNo() {
		return commercialNo;
	}

	public void setCommercialNo(String commercialNo) {
		this.commercialNo = commercialNo;
	}

	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearchCommercialName() {
		return searchCommercialName;
	}

	public void setSearchCommercialName(String searchCommercialName) {
		this.searchCommercialName = searchCommercialName;
	}

	public String getSearchCommercialNo() {
		return searchCommercialNo;
	}

	public void setSearchCommercialNo(String searchCommercialNo) {
		this.searchCommercialNo = searchCommercialNo;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}


	public Page<PayPosCommercial> getPayPosCommerciaPage() {
		return payPosCommerciaPage;
	}


	public void setPayPosCommerciaPage(Page<PayPosCommercial> payPosCommerciaPage) {
		this.payPosCommerciaPage = payPosCommerciaPage;
	}


	public String getSearchSupplier() {
		return searchSupplier;
	}


	public void setSearchSupplier(String searchSupplier) {
		this.searchSupplier = searchSupplier;
	}


	public String getSupplier() {
		return supplier;
	}


	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
}
