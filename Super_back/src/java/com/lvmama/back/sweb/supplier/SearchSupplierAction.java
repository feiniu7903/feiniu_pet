/**
 * 
 */
package com.lvmama.back.sweb.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.json.JSONOutput;

/**
 * @author yangbin
 *
 */
public class SearchSupplierAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9026705456141680837L;
	private String search;
	private SupplierService supplierService;
	/**
	 * 查找供应商
	 */
	@Action("/supplier/searchSupplier")
	public void search(){
		Map<String,Object> param = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(search)) {
			param.put("supplierName", search);
		}
		param.put("_endRow", 10);
		List<SupSupplier> list = supplierService.getSupSuppliers(param);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(SupSupplier ss:list){
				JSONObject obj=new JSONObject();
				obj.put("id", ss.getSupplierId());
				obj.put("text", ss.getSupplierName()+"("+ss.getSupplierId()+")");
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * @param supplierService the supplierService to set
	 */
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	
	
}
