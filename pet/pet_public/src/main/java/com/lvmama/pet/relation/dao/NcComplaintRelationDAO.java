package com.lvmama.pet.relation.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.relation.NcComplaintRelation;

/**
 * 
 * @author zhushuying
 *
 */
public class NcComplaintRelationDAO extends BaseIbatisDAO {
	
	public Long addRelation(NcComplaintRelation complaintRelation) {
		return (Long) super.insert("NC_COMPLAINT_RELATION.insertRelation", complaintRelation);
	}
}
