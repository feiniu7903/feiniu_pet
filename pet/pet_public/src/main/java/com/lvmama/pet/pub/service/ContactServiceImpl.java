/**
 * 
 */
package com.lvmama.pet.pub.service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.utils.LogObject;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.pub.dao.ComContactDAO;
import com.lvmama.pet.pub.dao.ComContactRelationDAO;

/**
 * @author yangbin
 *
 */
public class ContactServiceImpl extends BaseService implements ContactService{

	private ComContactDAO comContactDAO;
	
	private ComContactRelationDAO comContactRelationDAO;

	@Override
	public Long addContact(ComContact contact,String operatorName) {
		Long contactId = comContactDAO.insert(contact);
		ComContactRelation ccr=new ComContactRelation();
		ccr.setContactId(contactId);
		ccr.setObjectId(contact.getSupplierId());
		ccr.setObjectType("SUP_SUPPLIER");
		comContactRelationDAO.insert(ccr);
		insertLog("COM_CONTACT", contact.getSupplierId(), contact.getContactId(), operatorName, 
				Constant.COM_LOG_SUPPLIER_EVENT.updateContact.name(), 
				"新增联系人", MessageFormat.format("创建了名称为[ {0} ]的联系人", (contact.getName()==null ?"":contact.getName())), "SUP_SUPPLIER");
		return contactId;	
	}
	@Override
	public ResultHandle updateContact(ComContact contact,String operatorName) {
		ResultHandle handle=new ResultHandle();
		ComContactRelation comContactRelation = new ComContactRelation();
		comContactRelation.setContactId(contact.getContactId());
		comContactRelation.setObjectId(contact.getSupplierId());
		comContactRelation.setObjectType("SUP_SUPPLIER");
		ComContactRelation ccr=comContactRelationDAO.selectByComContactRelation(comContactRelation);
		if (ccr == null) {
			handle.setMsg("联系人信息不存在");
			return handle;
		}
		
		ComContact entity=comContactDAO.selectByPrimaryKey(contact.getContactId());
		String content=LogObject.makeUpdateComContactLog(entity,contact);
		comContactDAO.updateByPrimaryKey(contact);
		insertLog("COM_CONTACT", contact.getSupplierId(), contact.getContactId(), operatorName, 
				Constant.COM_LOG_SUPPLIER_EVENT.updateContact.name(), 
				"修改联系人信息", content, "SUP_SUPPLIER");
		return handle;
	}

	@Override
	public List<ComContact> getContactByPersonTimeCompany(Map map) {
		if(!map.containsKey("supplierId")){
			return Collections.emptyList();
		}
		ComContactRelation ccr=new ComContactRelation();
		ccr.setObjectId((Long)map.get("supplierId"));
		ccr.setObjectType("SUP_SUPPLIER");
		return getContactByContractRelation(ccr);
	}
	@Override
	public ComContact getContactById(Long contactId) {
		ComContact con=comContactDAO.selectByPrimaryKey(contactId);
		return con;
	}
	@Override
	public void saveContactRelation(List<ComContact> list, Long objectId, String objectType) {
		ComContactRelation comContactRelation = new ComContactRelation();
		comContactRelation.setObjectId(objectId);
		comContactRelation.setObjectType(objectType);
		
		List<ComContact> oldList=getContactByContractRelation(comContactRelation);
		Map<Long,ComContact> map=new HashMap<Long, ComContact>();
		if(!oldList.isEmpty()){
			for(ComContact cc:oldList){
				map.put(cc.getContactId(), cc);
			}
		}
		for(int i=0;i<list.size();i++) {
			ComContact comContact = list.get(i);			
			if(!map.containsKey(comContact.getContactId())){
				comContactRelation.setContactId(comContact.getContactId());		
				comContactRelationDAO.insert(comContactRelation);
			}else{//如果存在就清除掉
				oldList.remove(map.get(comContact.getContactId()));
			}
		}
		//删除数据库当中的
		if(!oldList.isEmpty()){
			for(ComContact cc : oldList){
				deleteContactRelation(cc.getContactId(), objectId, objectType);
			}
		}
	}
	@Override
	public List<ComContact> getContactByContractRelation(ComContactRelation comContactRelation) {
		return comContactDAO.getContactByContractRelation(comContactRelation);
	}
	
	private void deleteContactRelation(Long contactId,Long objectId, String objectType) {
		ComContactRelation comContactRelation = new ComContactRelation();
		comContactRelation.setObjectId(objectId);
		comContactRelation.setContactId(contactId);
		comContactRelation.setObjectType(objectType);
		ComContactRelation relation = comContactRelationDAO.selectByComContactRelation(comContactRelation);
		comContactRelationDAO.deleteContactRelation(relation.getContactRelationId());
	}
	
	@Override
	public void deleteContactRelation(Long objectId, String objectType) {
		Assert.notNull(objectId);
		Assert.notNull(objectType);
		ComContactRelation comContactRelation = new ComContactRelation();
		comContactRelation.setObjectId(objectId);
		comContactRelation.setObjectType(objectType);
		comContactRelationDAO.deleteContactRelation(comContactRelation);
	}
	
	
	@Override
	public void deleteContact(Map params) {
		comContactDAO.markIsValid(params);
	}
	
	public void setComContactDAO(ComContactDAO comContactDAO) {
		this.comContactDAO = comContactDAO;
	}

	public void setComContactRelationDAO(ComContactRelationDAO comContactRelationDAO) {
		this.comContactRelationDAO = comContactRelationDAO;
	}
	@Override
	public ComContact getSupSettlementTragetContactByTargetId(Long targetId) {
		return this.comContactDAO.getSupSettlementTragetContactByTargetId(targetId);
	}
	 
}
