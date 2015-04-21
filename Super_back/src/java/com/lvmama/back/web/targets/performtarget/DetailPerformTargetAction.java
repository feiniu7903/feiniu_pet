package com.lvmama.back.web.targets.performtarget;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;

public class DetailPerformTargetAction extends BaseAction {

	private PerformTargetService performTargetService;
 	private SupplierService supplierService;
 	private ContactService contactService;
	private SupPerformTarget performTarget; 
	private SupSupplier supplier;
	private List<ComContact> comContactRelationList;
	
	
	private Long targetId;
	private boolean closeable=true;
	protected void doBefore() throws Exception {
		if (targetId!=null) {
			performTarget = performTargetService.getSupPerformTarget(targetId);
			supplier = supplierService.getSupplier(performTarget.getSupplierId());
			
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
			comContactRelation.setObjectType("SUP_PERFORM_TARGET");
			comContactRelationList = contactService.getContactByContractRelation(comContactRelation);
		}
	}
	
	public SupPerformTarget getPerformTarget() {
		return performTarget;
	}

	public SupSupplier getSupplier() {
		return supplier;
	}

	public List<ComContact> getComContactRelationList() {
		return comContactRelationList;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return the closeable
	 */
	public boolean isCloseable() {
		return closeable;
	}

	/**
	 * @param closeable the closeable to set
	 */
	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}
	
}
