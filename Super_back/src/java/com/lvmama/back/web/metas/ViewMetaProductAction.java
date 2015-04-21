package com.lvmama.back.web.metas;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.vo.Constant;

public class ViewMetaProductAction extends BaseAction {

	private MetaProductService metaProductService;
	private ContactService contactService;
	private Long metaProductId;
	private Long metaBranchId;
	private MetaProductBranchService metaProductBranchService;
	private MetaProductBranch metaProductBranch;
	private String productType;
	private List<ComContact> contactRelationList;
	private SupplierService supplierService;
	private SupContractService supContractService;
	private MetaProduct metaProduct;
	
		
	protected void doBefore() throws Exception {
		metaProduct = metaProductService.getMetaProduct(metaProductId, productType);
		if(metaProduct.getSupplierId()!=null){
			SupSupplier ss=supplierService.getSupplier(metaProduct.getSupplierId());
			if(ss!=null){
				metaProduct.setSupplier(ss);
				metaProduct.setSupplierName(ss.getSupplierName());
			}
		}
		if(metaProduct.getContractId()!=null){
			SupContract sc=supContractService.getContract(metaProduct.getContractId());
			if(sc!=null){
				metaProduct.setContractNo(sc.getContractNo());
			}
		}
		if(metaBranchId != null && metaBranchId != 0L) {
			metaProductBranch = metaProductBranchService.getMetaBranch(metaBranchId);
		}else{
			List<MetaProductBranch> metaProductBranchList = metaProductBranchService.selectBranchListByProductId(metaProduct.getMetaProductId());
			if(metaProductBranchList != null && metaProductBranchList.size()>0){
				metaProductBranch = metaProductBranchList.get(0);				
			}
		}
	 
		ComContactRelation cc = new ComContactRelation();
		cc.setObjectId(metaProduct.getMetaProductId());
		cc.setObjectType(Constant.CONTACT_TYPE.META_PRODUCT.name());
		contactRelationList = this.contactService.getContactByContractRelation(cc);
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}

	public MetaProduct getMetaProduct() {
		return metaProduct;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public MetaProductBranch getMetaProductBranch() {
		return metaProductBranch;
	}

	public void setMetaProductBranch(MetaProductBranch metaProductBranch) {
		this.metaProductBranch = metaProductBranch;
	}

	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public List<ComContact> getContactRelationList() {
		return contactRelationList;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
}
