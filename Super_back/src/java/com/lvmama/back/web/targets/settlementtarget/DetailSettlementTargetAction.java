package com.lvmama.back.web.targets.settlementtarget;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;

public class DetailSettlementTargetAction extends BaseAction{
	
	private SettlementTargetService settlementTargetService;
	private SupplierService supplierService;
	private ContactService contactService;
	private List<ComContact> comContactRelationList;
	private SupSettlementTarget settlementTarget;
	private SupSupplier supplier;
	private Long targetId;
	private boolean closeable=true;
	public void doBefore() throws Exception {
		if(targetId!=null){
			settlementTarget = settlementTargetService.getSettlementTargetById(targetId);
			supplier = supplierService.getSupplier(settlementTarget.getSupplierId());
			
			loadContactList();
		}
	} 
	
	/**
	 * 加载联系人列表
	 * @param targetId
	 */
	public void loadContactList(){
		if(targetId!=null){
			ComContactRelation comContactRelation = new ComContactRelation();
			comContactRelation.setObjectId(targetId);
			comContactRelation.setObjectType("SUP_SETTLEMENT_TARGET");
			comContactRelationList = contactService.getContactByContractRelation(comContactRelation);
		}
	}
	
	public SupSupplier getSupplier() {
		return supplier;
	}
	public SupSettlementTarget getSettlementTarget() {
		return settlementTarget;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public List<ComContact> getComContactRelationList() {
		return comContactRelationList;
	}
	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}
	 
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	/**
	 * @param closeable the closeable to set
	 */
	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}

	/**
	 * @return the closeable
	 */
	public boolean isCloseable() {
		return closeable;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}
	
}
