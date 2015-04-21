package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.StringUtil;

/**
 * 供应商查询.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class SupplierAction extends ZkBaseAction {
	/**
	 * 综合查询Map.
	 */
	private Map serachMap = new HashMap();
	/**
	 * 供应商集合.
	 */
	private List<SupSupplier> supSupplierList;
	/**
	 * 供应商查询.
	 */
	private SupplierService supplierService;

	/**
	 * 用户综合查询.
	 */
	public void search() {
		if(serachMap.get("supplierName")!=null&&!StringUtil.isEmptyString(serachMap.get("supplierName").toString())){
			serachMap.put("supplierName",serachMap.get("supplierName").toString());
		}
		Map map=initialPageInfoByMap(supplierService.selectRowCount(serachMap),serachMap);
		int skipResults=0;
		int maxResults=20;
		if(map.get("_startRow")!=null){
			skipResults=Integer.parseInt(map.get("_startRow").toString());
		}
		if(map.get("_endRow")!=null){
			maxResults=Integer.parseInt(map.get("_endRow").toString());
		}
		serachMap.put("_startRow",skipResults);
		serachMap.put("_endRow",maxResults);
		supSupplierList = supplierService.getSupSuppliers(serachMap);
	}

	public Map getSerachMap() {
		return serachMap;
	}

	public void setSerachMap(Map serachMap) {
		this.serachMap = serachMap;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public List<SupSupplier> getSupSupplierList() {
		return supSupplierList;
	}

	public void setSupSupplierList(List<SupSupplier> supSupplierList) {
		this.supSupplierList = supSupplierList;
	}
}
