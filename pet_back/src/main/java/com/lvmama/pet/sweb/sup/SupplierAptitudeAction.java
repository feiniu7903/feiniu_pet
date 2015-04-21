/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.sup.SupSupplierAptitude;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 供应商资质审核相关
 * @author yangbin
 *
 */
@Results({
	@Result(name="index",location="/WEB-INF/pages/back/sup/supplier_aptitude.jsp")
})
public class SupplierAptitudeAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5066388282273603516L;
	private Long supplierId;
	private SupplierService supplierService;
	private SupSupplierAptitude supplierAptitude;
	
	@Action("/sup/aptitude/aptitudeList")
	public String index(){
		supplierAptitude = supplierService.getSupplierAptitudeBySupplierId(supplierId);
		if(supplierAptitude==null){
			supplierAptitude = new SupSupplierAptitude();
			supplierAptitude.setSupplierId(supplierId);
		}
		return "index";
	}	
	
	@Action("/sup/aptitude/saveAptitude")
	public void save() {
		JSONResult result=new JSONResult();
		try{			
			if(supplierAptitude.getSupplierAptitudeId()!=null){
				SupSupplierAptitude oldEntity=supplierService.getSupplierAptitudeByPrimaryKey(supplierAptitude.getSupplierAptitudeId());
				Assert.notNull(oldEntity,"更改的资质信息不存在");
				oldEntity=CopyUtil.copy(oldEntity, supplierAptitude, getRequest().getParameterNames(),"supplierAptitude.");
				supplierService.insertSupSupplierAptitude(oldEntity,getSessionUserNameAndCheck());
			}else{
				supplierService.insertSupSupplierAptitude(supplierAptitude,getSessionUserNameAndCheck());
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}	

	public SupSupplierAptitude getSupplierAptitude() {
		return supplierAptitude;
	}
	public void setSupplierAptitude(SupSupplierAptitude supplierAptitude) {
		this.supplierAptitude = supplierAptitude;
	}
	
	public Constant.SUP_APTITUDE_TYPE[] getSupplierAdtitudeTypeList(){
		return Constant.SUP_APTITUDE_TYPE.values();
	}
	
	public Constant.SUP_APTITUDE_STATUS_TYPE[] getValidTypeList(){
		return Constant.SUP_APTITUDE_STATUS_TYPE.values();
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}	
}
