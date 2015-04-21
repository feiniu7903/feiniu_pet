package com.lvmama.back.web.targets.bcertificatetarget;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;

public class DetailBCertificateTargetAction  extends BaseAction {
	
	private BCertificateTargetService bCertificateTargetService;
 	
	private SupplierService supplierService;
	private ContactService contactService;
	private Long targetId;	
	private boolean closeable=true;
		
	/** b端凭证对象 */
	private SupBCertificateTarget bcertificateTarget = new SupBCertificateTarget();
	
	private SupSupplier supplier;
	
	private List<ComContact> comContactRelationList = new ArrayList<ComContact>();
	
	protected void doBefore() throws Exception {
		if (targetId!=null) {
			bcertificateTarget = bCertificateTargetService.getBCertificateTargetByTargetId(targetId);
 			supplier = supplierService.getSupplier(bcertificateTarget.getSupplierId());
 			
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
			comContactRelation.setObjectType("SUP_B_CERTIFICATE_TARGET");
			comContactRelationList = contactService.getContactByContractRelation(comContactRelation);
		}
	}
	
	public SupBCertificateTarget getBcertificateTarget() {
		return bcertificateTarget;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
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
