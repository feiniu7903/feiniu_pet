package com.lvmama.pet.pub.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;

public class ComContactDAO extends BaseIbatisDAO{
	public int deleteByPrimaryKey(Long contactId) {
		ComContact key = new ComContact();
		key.setContactId(contactId);
		int rows = super.delete(
				"COM_CONTACT.deleteByPrimaryKey", key);
		return rows;
	}

	public Long insert(ComContact record) {
		return (Long)super.insert("COM_CONTACT.insert", record);
	}

	public ComContact selectByPrimaryKey(Long contactId) {
		ComContact key = new ComContact();
		key.setContactId(contactId);
		ComContact record = (ComContact) super
				.queryForObject("COM_CONTACT.selectByPrimaryKey",
						key);
		return record;
	}

	public int updateByPrimaryKey(ComContact record)
			 {
		int rows = super.update(
				"COM_CONTACT.updateByPrimaryKey", record);
		return rows;
	}

	public List<ComContact> getContactByContractRelation(ComContactRelation comContactRelation) {
		return super.queryForList("COM_CONTACT.getContactByContractRelation",comContactRelation);
	}
	public void markIsValid(Map params) {
		super.update("COM_CONTACT.markIsValid",params);
	}	
	public ComContact getSupSettlementTragetContactByTargetId(Long targetId) {
		return (ComContact) super.queryForObject("COM_CONTACT.serchSupSettlementTragetContactByTargetId",targetId);
	}	
}