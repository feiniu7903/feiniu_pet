package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.pass.EplaceSupplier;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
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
public class EplaceSupplerAddAction extends ZkBaseAction {
	/**
	 * 供应商查询.
	 */
	private EPlaceService eplaceService;
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
	private boolean chkCustomerVisible;
	private String ordChk;
	private List ordItemsIds;
	private List metaProductList;
	private List<MetaProductBranch> metaProductBranchList;
	private String productId;
	private List<SupPerformTarget> performTargetList;
	private Long targetId;
	private PerformTargetService performTargetService;

	public void doBefore() {
		if (!StringUtil.isEmptyString(supplierId)) {
			ordItemsIds= new ArrayList();
			eplaceSupplier = new EplaceSupplier();
			Map map = new HashMap();
			map.put("supplierId", supplierId);
			supSupplier = this.supplierService.getSupplier(Long
					.valueOf(supplierId));
			Map metaMap = new HashMap();
			metaMap.put("supplierId", supplierId);
			metaMap.put("_endRow", 10000);
//			metaProductList = this.ePlaceService.findMetaProduct(metaMap);
			metaProductBranchList = eplaceService.selectMetaProductBranchBySupplierId(Long.valueOf(supplierId));
		}
		if (!StringUtil.isEmptyString(productId)) {
			performTargetList = performTargetService.findSuperSupPerformTargetByMetaProductId(Long.valueOf(productId));
		}
	}

	public void addEplaceSupplier() {
		if (!StringUtil.isEmptyString(supplierId)) {
			if (metaProductBranchList.size() == 0) {
				ZkMessage.showInfo("采购产品列表为空，请先进行添加采购产品!");
				return;
			}
			if (ordItemsIds.size() == 0) {
				ZkMessage.showInfo("请选择一款采购产品进行添加!");
				return;
			}
			EplaceSupplier eplace = new EplaceSupplier();
			eplace.setCustomerVisible(chkCustomerVisible + "");
			eplace.setCreateDate(new Date());
			eplace.setSupplierId(Long.valueOf(supplierId));
			eplace.setMobile(eplaceSupplier.getMobile());
			eplace.setProductManager(eplaceSupplier.getProductManager());
			eplace.setStatus("OPEN");
			eplaceService.addEplaceSupplier(eplace, ordItemsIds);
			super.refreshParent("search");
			super.closeWindow();
		}
	}
	
	/**
	 * 通过履行对象ID查采购产品
	 * 逻辑：如果没有输入履行对象就查询此供应商全部绑定的采购产品
	 */
	public void selectMetaProduct() {	
		List<Long> metaProductIds=null;
		if (targetId != null) {
			metaProductIds=performTargetService.selectMetaRelationByParam(targetId, Constant.PRODUCT_BIZ_TYPE.BEE);			
		}else{
			long supplierIdNum=NumberUtils.toLong(supplierId);
			if(supplierIdNum>0L){
				metaProductIds = performTargetService.selectMetaRelationBySupplierId(supplierIdNum,Constant.PRODUCT_BIZ_TYPE.BEE);
			}
		}
		
		if(CollectionUtils.isNotEmpty(metaProductIds)){
			metaProductList = this.eplaceService.selectMetaProductByIds(metaProductIds);
		}
	}
	
	public void chkVlaue(Long value) {
		if (ordItemsIds.indexOf(value) == -1) {
			ordItemsIds.add(value);
		} else {
			ordItemsIds.remove(ordItemsIds.indexOf(value));
		}
	}
	
	public void chkIsChekced(boolean checked) {
		this.chkCustomerVisible=checked;
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

	public void setChkCustomerVisible(boolean chkCustomerVisible) {
		this.chkCustomerVisible = chkCustomerVisible;
	}

	public boolean isChkCustomerVisible() {
		return chkCustomerVisible;
	}

	public String getOrdChk() {
		return ordChk;
	}

	public void setOrdChk(String ordChk) {
		this.ordChk = ordChk;
	}

	public List getOrdItemsIds() {
		return ordItemsIds;
	}

	public void setOrdItemsIds(List ordItemsIds) {
		this.ordItemsIds = ordItemsIds;
	}

	public List getMetaProductList() {
		return metaProductList;
	}

	public void setMetaProductList(List metaProductList) {
		this.metaProductList = metaProductList;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List getPerformTargetList() {
		return performTargetList;
	}

	public void setPerformTargetList(List performTargetList) {
		this.performTargetList = performTargetList;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public List<MetaProductBranch> getMetaProductBranchList() {
		return metaProductBranchList;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
	
}
