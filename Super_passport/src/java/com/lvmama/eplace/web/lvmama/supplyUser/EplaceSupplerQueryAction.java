package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.EplaceSupplier;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.passport.utils.ZkMessage;
import com.lvmama.passport.utils.ZkMsgCallBack;

/**
 * 供应商查询.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class EplaceSupplerQueryAction extends ZkBaseAction {
	/**
	 * 供应商查询.
	 */
	private EPlaceService eplaceService;
	private SupplierService supplierService;
	/**
	 * 综合查询Map.
	 */
	private Map serachMap = new HashMap();
	/**
	 * 供应商集合.
	 */
	private List<EplaceSupplier> eplaceSupplierList;
	private String status;
	/**
	 * 用户综合查询.
	 */
	public void search() {
		if (serachMap.get("supplierName") != null
				&& !StringUtil.isEmptyString(serachMap.get("supplierName")
						.toString())) {
			serachMap.put("supplierName", serachMap.get("supplierName")
					.toString());
		}
		 
		Map map = initialPageInfoByMap(supplierService.selectRowCount(serachMap), serachMap);
		
		int skipResults = 0;
		int maxResults = 20;
		if (map.get("_startRow") != null) {
			skipResults = Integer.parseInt(map.get("_startRow").toString());
		}
		if (map.get("_endRow") != null) {
			maxResults = Integer.parseInt(map.get("_endRow").toString());
		}
		serachMap.put("_startRow", skipResults);
		serachMap.put("_endRow", maxResults);
		List<SupSupplier> supplierList = supplierService.getSupSuppliers(map);
		eplaceSupplierList = eplaceService.findEplaceSupplierByMap(supplierList);
	}

	public void delEplaceSupplier(final Long eplaceSupplierId)
			throws InterruptedException {
		ZkMessage.showQuestion("您确认要关闭此条信息吗", new ZkMsgCallBack() {
			public void execute() {
				EplaceSupplier eplacesupplier = eplaceService.getEplaceSupplierByPk(eplaceSupplierId);
				eplacesupplier.setStatus("CLOSE");
				eplaceService.updateEplaceSupplier(eplacesupplier, null);
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});

	}

	public Map getSerachMap() {
		return serachMap;
	}

	public void setSerachMap(Map serachMap) {
		this.serachMap = serachMap;
	}

	public List<EplaceSupplier> getEplaceSupplierList() {
		return eplaceSupplierList;
	}

	public void setEplaceSupplierList(List<EplaceSupplier> eplaceSupplierList) {
		this.eplaceSupplierList = eplaceSupplierList;
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

}
