package com.lvmama.pet.pub.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComContactRelation;

public class ComContactRelationDAO extends BaseIbatisDAO {

	public void insert(ComContactRelation record) {
		super.insert("COM_CONTACT_RELATION.insert", record);
	}

	public ComContactRelation selectByComContactRelation(ComContactRelation comContactRelation) {
		return (ComContactRelation)super.queryForObject("COM_CONTACT_RELATION.selectByComContactRelation",comContactRelation);
	}

	public void deleteContactRelation(long contactRelationId) {
		super.delete("COM_CONTACT_RELATION.deleteContactRelation",contactRelationId);
	}
	public void deleteContactRelation(ComContactRelation comContactRelation){
		super.delete("COM_CONTACT_RELATION.deleteAllContactRelation", comContactRelation);
	}
	
	public ComContactRelation selectByObjectId(Long objectId){
		return (ComContactRelation) super.queryForObject("COM_CONTACT_RELATION.selectByObjectId", objectId);
	}
	
	public ComContactRelation addContactRelation(Long objectId){
		return (ComContactRelation)super.insert("COM_CONTACT_RELATION.addContactRelation",objectId);
	}
}