/**
 * 
 */
package com.lvmama.pet.comment.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.comment.CmtActivity;

/**
 * @author liuyi
 * 点评广告
 */
public class CmtActivityDAO extends BaseIbatisDAO {
	
	public List<CmtActivity> query() {
		return super.queryForList("CMT_ACTIVITY.query");
	}

	public void update(final CmtActivity cmtActivity) {
		super.update("CMT_ACTIVITY.update", cmtActivity);
	}

	
	public CmtActivity queryById(final Long id) {
		return (CmtActivity) super.queryForObject("CMT_ACTIVITY.queryById"
				, id);
	}
	
	
	public CmtActivity queryByActivitySubject(final String activitySubject) {
		return (CmtActivity) super.queryForObject("CMT_ACTIVITY.queryByActivitySubject"
				, activitySubject);
	}
}
