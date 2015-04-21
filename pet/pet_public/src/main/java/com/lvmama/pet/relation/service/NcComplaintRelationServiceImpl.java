package com.lvmama.pet.relation.service;

import com.lvmama.comm.pet.po.relation.NcComplaintRelation;
import com.lvmama.comm.pet.service.relation.NcComplaintRelationService;
import com.lvmama.pet.relation.dao.NcComplaintRelationDAO;
/**
 * 投诉主表、发短信、发邮件          关联
 * @author zhushuying
 *
 */
public class NcComplaintRelationServiceImpl implements NcComplaintRelationService {
	private NcComplaintRelationDAO complaintRelationDAO;

	@Override
	public Long addRelation(NcComplaintRelation complaintRelation) {
		return complaintRelationDAO.addRelation(complaintRelation);
	}

	public NcComplaintRelationDAO getComplaintRelationDAO() {
		return complaintRelationDAO;
	}

	public void setComplaintRelationDAO(NcComplaintRelationDAO complaintRelationDAO) {
		this.complaintRelationDAO = complaintRelationDAO;
	}

}
