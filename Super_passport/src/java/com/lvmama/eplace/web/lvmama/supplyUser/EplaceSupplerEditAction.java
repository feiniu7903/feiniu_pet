package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.EplaceMetaProduct;
import com.lvmama.comm.bee.po.pass.EplaceSupplier;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 增加.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class EplaceSupplerEditAction extends ZkBaseAction {
	/**
	 * 供应商查询.
	 */
	private EPlaceService eplaceService;
	private PerformTargetService performTargetService;
	/**
	 * 供应商查询.
	 */
	private SupplierService supplierService;
	/**
	 * 增加对像.
	 */
	private EplaceSupplier eplaceSupplier;
	/**
	 * 供应商对象.
	 */
	private SupSupplier supSupplier;
	/**
	 * 供应商对像编号.
	 */
	private String supplierId;
	private String chkCustomerVisible;
	private String ordChk;
	private List<EplaceMetaProduct> metaProductBranchList;
	private String eplaceSupplierId;
	private Long targetId;
	
	public void doBefore(){
		if(!StringUtil.isEmptyString(eplaceSupplierId)){
			eplaceSupplier=this.eplaceService.getEplaceSupplierByPk(Long.valueOf(eplaceSupplierId));
			chkCustomerVisible=eplaceSupplier.getCustomerVisible().indexOf("true")>-1?"true":"false";
			supSupplier=this.supplierService.getSupplier(Long.valueOf(supplierId));
			Map metaMap=new HashMap();
			metaMap.put("supplierId", supplierId);
			metaMap.put("eplaceSupplierId", eplaceSupplierId);
			metaMap.put("_endRow", 10000);
			metaProductBranchList=this.eplaceService.findEplaceMetaProductByMap(metaMap,null);
		}
	}
	public void addEplaceSupplier(){
		if(!StringUtil.isEmptyString(eplaceSupplierId)){
			if (metaProductBranchList.size() == 0) {
				ZkMessage.showInfo("采购产品列表为空，请先进行添加采购产品!");
				return;
			}
			if (!this.isCheckEd(metaProductBranchList)) {
				ZkMessage.showInfo("请选择一款采购产品进行添加!");
				return;
			}
			EplaceSupplier eplace=this.eplaceService.getEplaceSupplierByPk(Long.valueOf(eplaceSupplierId));
			eplace.setCustomerVisible(chkCustomerVisible);
			eplace.setCreateDate(new Date());
			eplace.setSupplierId(Long.valueOf(supplierId));
			eplace.setMobile(eplaceSupplier.getMobile());
			eplace.setProductManager(eplaceSupplier.getProductManager());
			eplace.setStatus(this.eplaceSupplier.getStatus());
			eplaceService.updateEplaceSupplier(eplace,metaProductBranchList);
			super.refreshParent("search");
			super.closeWindow();
		}
	}
	
	/**
	 * 通过履行对象查采购产品
	 */
	public void selectMetaProduct() {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("supplierId", supplierId);
		List<Long> ids=null;
		if (targetId != null) {
			param.put("targetId", targetId);
			ids = performTargetService.selectMetaRelationByParam(
					targetId, Constant.PRODUCT_BIZ_TYPE.BEE);
		}
		param.put("eplaceSupplierId", eplaceSupplierId);
		param.put("_endRow", 10000);
		metaProductBranchList = this.eplaceService.findEplaceMetaProductByMap(param,ids);
	}
	
	public boolean isCheckEd(List metaProductList){
		boolean isCheck=false;
		for (int i = 0; i < metaProductList.size(); i++) {
			EplaceMetaProduct e=(EplaceMetaProduct)metaProductList.get(i);
			if(e.isIschecked()){
				isCheck=true;
			}
		}
		return isCheck;
	}

	public EplaceSupplier getEplaceSupplier() {
		return eplaceSupplier;
	}

	public void setEplaceSupplier(EplaceSupplier eplaceSupplier) {
		this.eplaceSupplier = eplaceSupplier;
	}
	 
	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
	public SupplierService getSupplierService() {
		return supplierService;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public SupSupplier getSupSupplier() {
		return supSupplier;
	}
	public void setSupSupplier(SupSupplier supSupplier) {
		this.supSupplier = supSupplier;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getOrdChk() {
		return ordChk;
	}
	public void setOrdChk(String ordChk) {
		this.ordChk = ordChk;
	}
/*	public List getMetaProductList() {
		return metaProductList;
	}*/
	public String getEplaceSupplierId() {
		return eplaceSupplierId;
	}
	public void setEplaceSupplierId(String eplaceSupplierId) {
		this.eplaceSupplierId = eplaceSupplierId;
	}
	public String getChkCustomerVisible() {
		return chkCustomerVisible;
	}
	public void setChkCustomerVisible(String chkCustomerVisible) {
		this.chkCustomerVisible = chkCustomerVisible;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public List<EplaceMetaProduct> getMetaProductBranchList() {
		return metaProductBranchList;
	}
	public void setMetaProductBranchList(List<EplaceMetaProduct> metaProductBranchList) {
		this.metaProductBranchList = metaProductBranchList;
	}
	
}
