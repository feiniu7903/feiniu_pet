package com.lvmama.comm.pet.service.relation;

import com.lvmama.comm.pet.po.relation.NcComplaintRelation;

/**
 * 投诉主表、发短信、发邮件          关联
 * @author zhushuying
 *
 */
public interface NcComplaintRelationService {
	/**
     * 添加关系信息
     * @param complaintRelation
     * @return
     */
    public Long addRelation(NcComplaintRelation complaintRelation);
}
