/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.sup.SupSupplierAssess;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.json.JSONResult;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="index",location="/WEB-INF/pages/back/sup/supplier_assess.jsp")
})
public class SupplierAssessAction extends BackBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5348581596822769986L;
	private SupSupplierAssess supplierAssess;
	private SupplierService supplierService;
	private SupSupplier supplier;
	private List<SupSupplierAssess> supplierAssessList;
	private Long supplierId;
	private String type;
	@Action("/sup/assess/assessList")
	public String index(){
		supplier = supplierService.getSupplier(supplierId);		
		supplierAssessList = supplierService.selectSupplierAssessBySupplierId(supplierId);
		return "index";
	}


	@Action("/sup/assess/saveAssess")
	public void save() {
		JSONResult result=new JSONResult();
		try{
			Assert.hasText(type,"操作类型不可以为空");
			if(supplierAssess.getAssessPoints()==null||supplierAssess.getAssessPoints()<1){
				throw new IllegalArgumentException("分数不可以为空或小于1");
			}
			supplierAssess.changeSymbol(type);
			supplierAssess.setOperatorName(getSessionUserNameAndCheck());			
			supplierService.insertSupSupplierAssess(supplierAssess);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	public List<SupSupplierAssess> getSupplierAssessList() {
		return supplierAssessList;
	}

	public SupSupplierAssess getSupplierAssess() {
		return supplierAssess;
	}

	public void setSupplierAssess(SupSupplierAssess supplierAssess) {
		this.supplierAssess = supplierAssess;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public SupSupplier getSupplier() {
		return supplier;
	}


	public Long getSupplierId() {
		return supplierId;
	}


	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	
}
