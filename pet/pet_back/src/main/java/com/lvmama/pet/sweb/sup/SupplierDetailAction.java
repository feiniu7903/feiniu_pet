/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.sup.SupSupplierAptitude;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="success",location="/WEB-INF/pages/back/sup/supplier_detail.jsp")
})
public class SupplierDetailAction extends BackBaseAction {

	private SupplierService supplierService;
	private Long supplierId;
	private SupSupplier supplier;
	private List<SupPerformTarget> performTargetList=Collections.emptyList();
	private List<SupSettlementTarget> settlementTargetList=Collections.emptyList();
	private List<SupBCertificateTarget> bcertificateTargetList=Collections.emptyList();
	private List<ComContact> contactList=Collections.emptyList();
	private SupSupplierAptitude supplierAptitude;
	private ComProvince comProvince;
	private ComCity comCity;
	private List<SupContract> contractList=Collections.emptyList();
	
	private SettlementTargetService settlementTargetService;
	private PerformTargetService performTargetService;
	private BCertificateTargetService bCertificateTargetService;
	private ContactService contactService;
	private PlaceCityService placeCityService;
	private SupContractService supContractService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2382938361023846303L;
	
	@Action("/sup/detail")
	public String detail(){
		supplier=supplierService.getSupplier(supplierId);
		if(supplier!=null){
			comCity=placeCityService.selectCityByPrimaryKey(supplier.getCityId());
			if(comCity!=null){
				comProvince = placeCityService.selectByPrimaryKey(comCity.getProvinceId());
			}
		}
		
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		
		contactList = contactService.getContactByPersonTimeCompany(params);
		
		
		
		settlementTargetList = settlementTargetService.findSupSettlementTarget(params);
				
		performTargetList = performTargetService.findSupPerformTarget(params);
		
		bcertificateTargetList = bCertificateTargetService.findBCertificateTarget(params);		
		
		fillTargetContact();
		
		supplierAptitude = supplierService.getSupplierAptitudeBySupplierId(supplierId);
		
		params.put("contractAudit", Constant.CONTRACT_AUDIT.PASS.name());
		Page<SupContract> page = supContractService.getSupContractByParam(params, 5L, 1L);
		if(page.getTotalResultSize()>0L){
			contractList=page.getItems();
			
			for(SupContract sc:contractList){
				sc.setFsList(supContractService.getSupContractFsByContractId(sc.getContractId()));
			}
		}
		return SUCCESS;
	}
	
	private void fillTargetContact(){
		for(SupSettlementTarget target:settlementTargetList){
			target.setContactList(getContactInfo(target.getTargetId(), "SUP_SETTLEMENT_TARGET"));
		}
		
		for(SupPerformTarget target:performTargetList){
			target.setContactList(getContactInfo(target.getTargetId(),"SUP_PERFORM_TARGET"));
		}
		
		for(SupBCertificateTarget target:bcertificateTargetList){
			target.setContactList(getContactInfo(target.getTargetId(), "SUP_B_CERTIFICATE_TARGET"));
		}
	}
	private List<ComContact> getContactInfo(Long objectId,String objectType){
		ComContactRelation relation=new ComContactRelation();
		relation.setObjectId(objectId);
		relation.setObjectType(objectType);
		return contactService.getContactByContractRelation(relation);
	}
	
	@Action("/sup/relateSupplierDetail")
	public void relateSupplierDetail(){
		supplier = supplierService.getSupplier(supplierId);
		JSONResult result=new JSONResult();
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"parentSupplier", "childSupplierList"});
		JSONObject object = JSONObject.fromObject(supplier, config);
		ComCity city = placeCityService.selectCityByPrimaryKey(supplier.getCityId());
		object.put("cityName", placeCityService.selectByPrimaryKey(city.getProvinceId()).getProvinceName()+city.getCityName()+"市");
		
		if(supplier.getParentId() != null && supplier.getParentId() > 0) {
			object.put("supSupplierName", supplier.getParentSupplier().getSupplierName());
		}
		
		if(supplier.getForegiftsAlert() != null) {
			object.put("foregiftsAlert", DateUtil.formatDate(supplier.getForegiftsAlert(), "yyyy-MM-dd"));
		}
		
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		List<SupSettlementTarget> settlementTargetList = settlementTargetService.findSupSettlementTarget(params);
		
		JSONArray array = new JSONArray();
		for (SupSettlementTarget target : settlementTargetList) {
			JSONObject obj = new JSONObject();
			obj.put("targetId", target.getTargetId());
			obj.put("targetName", target.getName());
			array.add(obj);
		}
		result.put("targets", array);
		result.put("supplier", object);
		result.output(getResponse());
	}


	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}


	public SupSupplier getSupplier() {
		return supplier;
	}


	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public List<SupPerformTarget> getPerformTargetList() {
		return performTargetList;
	}

	public List<SupSettlementTarget> getSettlementTargetList() {
		return settlementTargetList;
	}

	public List<SupBCertificateTarget> getBcertificateTargetList() {
		return bcertificateTargetList;
	}

	public List<ComContact> getContactList() {
		return contactList;
	}

	public SupSupplierAptitude getSupplierAptitude() {
		return supplierAptitude;
	}

	public List<SupContract> getContractList() {
		return contractList;
	}

	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public ComProvince getComProvince() {
		return comProvince;
	}

	public ComCity getComCity() {
		return comCity;
	}

	public Constant.SUP_APTITUDE_TYPE[] getSupplierAdtitudeTypeList(){
		return Constant.SUP_APTITUDE_TYPE.values();
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}
}
