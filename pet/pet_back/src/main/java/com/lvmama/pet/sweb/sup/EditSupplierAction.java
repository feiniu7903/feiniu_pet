/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.LogObject;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.sweb.EditAction;

/**
 * 创建或修改一个供应商对象
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/sup/edit_supplier.jsp"),
	@Result(name="error_param",location="/WEB-INF/pages/back/sup/error_param.jsp")
})
public class EditSupplierAction extends BackBaseAction implements EditAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3610897908269412615L;
	
	
	private SupSupplier supplier;
	private SupplierService supplierService;
	private PlaceCityService placeCityService;
	private ComLogService comLogRemoteService;
	private List<ComCity> cityList=Collections.emptyList(); 
	private Long supplierId;
	private String province;
	private String parentSupplierName;
	
	@Override
	@Action("/sup/toAddSupplier")
	public String toAdd() {
		return INPUT;
	}

	@Override
	@Action("/sup/toEditSupplier")
	public String toEdit() {
		if(supplierId==null||supplierId<1){
			return "error_param";
		}
		supplier = supplierService.getSupplier(supplierId);
		ComCity cc = placeCityService.selectCityByPrimaryKey(supplier.getCityId());
		if(cc!=null){		
			cityList = placeCityService.getCityListByProvinceId(cc.getProvinceId()); 
			province = cc.getProvinceId();
		}		
		if(supplier.getParentId()!=null&&supplier.getParentSupplier()!=null){
			parentSupplierName=supplier.getParentSupplier().getSupplierName();
		}
		return INPUT;
	}

	@Override
	@Action("/sup/saveSupplier")
	public void save() {
		JSONResult result=new JSONResult();
		try{
			doValidate();
			boolean hasNew=false;					
			if(supplier.getSupplierId()!=null){				
				hasNew=true;
				SupSupplier supplierEntity=supplierService.getSupplier(supplier.getSupplierId());
				Assert.notNull(supplierEntity,"编辑的供应商不存在");
				supplierEntity=CopyUtil.copy(supplierEntity,supplier,getRequest().getParameterNames(),"supplier.");
				SupSupplier oldSupplier = supplierService.getSupplier(supplierEntity.getSupplierId());
				supplierService.updateSupplier(supplierEntity, getSessionUserNameAndCheck());
				LogObject.updateSupSupplierLogStr(supplierEntity, oldSupplier, this.getSessionUserNameAndCheck(),comLogRemoteService,placeCityService);
			}else{
				Long supplierId=supplierService.addSupplier(supplier, getSessionUserNameAndCheck());
				
				SupSupplier supp = this.supplierService.getSupplier(supplierId);
				LogObject.addSupplierLog(supp, getSessionUserNameAndCheck(), comLogRemoteService);
				
				result.put("supplierId", supplierId);
			}
			result.put("hasNew", hasNew);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	private void doValidate(){
		if(supplier.getSupplierId()==null){
			Assert.hasLength(supplier.getSupplierName(),"供应商名称不可以为空");
		}
		Assert.hasLength(supplier.getSupplierType(),"供应商类型不可以为空");
		Assert.hasLength(supplier.getCityId(),"所在省市必填");
		Assert.hasLength(supplier.getAddress(),"地址不可以为空");
		Assert.hasLength(supplier.getTelephone(),"供应商电话不可以为空");
		Assert.hasLength(supplier.getFax(),"传真不可以为空");
		Assert.notNull(supplier.getBosshead(),"我方负责人不可以为空");
		
		if(supplier.getSupplierId()==null){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("supplierNameEq", supplier.getSupplierName());			
			List<SupSupplier> list=supplierService.getSupSuppliers(param);
			if(!list.isEmpty()){
				throw new IllegalArgumentException("供应商名称名称重复");				
			}			
		}
	}

	public SupSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(SupSupplier supplier) {
		this.supplier = supplier;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	
	public Constant.SUPPLIER_TYPE[] getSupplierTypeList(){
		return Constant.SUPPLIER_TYPE.values();
	}
	
	public List<ComProvince> getProvinceList() {
		return placeCityService.getProvinceList();
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public List<ComCity> getCityList() {
		return cityList;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}	
	public String getProvince() {
		return province;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public String getParentSupplierName() {
		return parentSupplierName;
	}
	
	public Constant.SETTLEMENT_COMPANY[] getSettlementCompanyList() {
		return Constant.SETTLEMENT_COMPANY.values();
	}
}
