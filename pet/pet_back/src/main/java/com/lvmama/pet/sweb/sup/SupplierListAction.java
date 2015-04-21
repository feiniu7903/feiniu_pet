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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
/**
 * 供应商列表页
 * @author yangbin
 *
 */
@Results({
	@Result(name="supplier_list",location="/WEB-INF/pages/back/sup/supplier_list.jsp"),
	@Result(name="relate_supplier",location="/WEB-INF/pages/back/contract/relate_supplier.jsp")
})
public class SupplierListAction extends BackBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4780667014323041782L;
	private PlaceCityService placeCityService;
	private SupplierService supplierService;
	
	private String supplierName;
	private Long supplierId;
	private String contractNo;
	private String provinceId;
	private String cityId;
	
	private List<ComCity> cityList = Collections.emptyList();
	
	@Action("/sup/index")
	public String index(){		
		return "supplier_list";
	}
	
	@Action("/sup/relateSupplier")
	public String relateSupplier(){		
		return "relate_supplier";
	}
	
	@Action("/sup/supplierList")
	public String supplierList(){
		pagination=initPage();
		pagination.setPageSize(20);
		Map<String,Object> param=buildParam();
		pagination.setTotalResultSize(supplierService.selectRowCount(param));
		if(pagination.getTotalResultSize()>0){
			param.put("_startRow", pagination.getStartRows());
			param.put("_endRow", pagination.getEndRows());
			List<SupSupplier> list=supplierService.getSupSuppliers(param);
			for(SupSupplier ss:list){
				if(ss.getParentId()!=null){
					ss.setParentSupplier(supplierService.getSupplier(ss.getParentId()));
				}
			}
			pagination.setItems(list);
		}
		pagination.buildUrl(getRequest());
		
		if(StringUtils.isNotEmpty(provinceId)){
			cityList = placeCityService.getCityListByProvinceId(provinceId);
		}
		
		return "supplier_list";
	}
	
	@Action("/sup/relateSupplierSearch")
	public void relateSupplierSearch(){
		Map<String,Object> param=buildParam();
		List<SupSupplier> list = supplierService.getSupSuppliers(param);
		JSONResult result=new JSONResult();
		JSONArray array = new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(SupSupplier ss : list){
				JSONObject obj=new JSONObject();
				obj.put("supplierId", ss.getSupplierId());
				obj.put("supplierName", ss.getSupplierName());
				obj.put("createTime", DateUtil.formatDate(ss.getCreateTime(), "yyyy-MM-dd"));
				obj.put("cityName", ss.getComCity().getCityName());
				array.add(obj);
			}
		}
		result.put("supplierList", array);
		result.output(getResponse());
	}
		
	private Map<String,Object> buildParam(){
		Map<String,Object> map=new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(supplierName)){
			map.put("supplierName", supplierName);			
		}
		if(supplierId!=null){
			map.put("supplierId", supplierId);
		}
		
		if(StringUtils.isNotEmpty(provinceId)){
			map.put("provinceId", provinceId);			
		}
		
		if(StringUtils.isNotEmpty(cityId)){
			map.put("cityId", cityId);
		}
		
		if(StringUtils.isNotEmpty(contractNo)){
			map.put("contractNo", contractNo);
		}
		
		return map;
	}
	
	/**
	 * 省份列表
	 * @return
	 */
	public List<ComProvince> getProvinceList(){
		return placeCityService.getProvinceList();
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		if(StringUtils.isNotEmpty(supplierId)){
			this.supplierId = NumberUtils.toLong(supplierId);
		}
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public List<ComCity> getCityList() {
		return cityList;
	}
}
