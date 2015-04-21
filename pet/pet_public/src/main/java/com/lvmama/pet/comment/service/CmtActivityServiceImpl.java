/**
 * 
 */
package com.lvmama.pet.comment.service;

import java.util.List;

import com.lvmama.comm.pet.po.comment.CmtActivity;
import com.lvmama.comm.pet.service.comment.CmtActivityService;
import com.lvmama.pet.comment.dao.CmtActivityDAO;

/**
 * @author liuyi
 * 点评活动
 */
public class CmtActivityServiceImpl implements CmtActivityService {

	private CmtActivityDAO cmtActivityDAO;

	@Override
	public List<CmtActivity> query() {
		return cmtActivityDAO.query();
	}

	@Override
	public void update(final CmtActivity cmtActivity) {
		cmtActivityDAO.update(cmtActivity);
	}

	@Override
	public CmtActivity queryById(Long id) {
		return cmtActivityDAO.queryById(id);
	}

	@Override
	public CmtActivity queryByActivitySubject(String activitySubject){
		 return cmtActivityDAO.queryByActivitySubject(activitySubject);
	 }

	public CmtActivityDAO getCmtActivityDAO() {
		return cmtActivityDAO;
	}

	public void setCmtActivityDAO(CmtActivityDAO cmtActivityDAO) {
		this.cmtActivityDAO = cmtActivityDAO;
	}
	 

}
