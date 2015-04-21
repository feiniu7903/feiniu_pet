/**
 * 
 */
package com.lvmama.pet.pub.service;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComUserFeedback;
import com.lvmama.comm.pet.service.pub.ComUserFeedbackService;
import com.lvmama.pet.pub.dao.ComUserFeedbackDAO;

/**
 * @author yangbin
 *
 */
public class ComUserFeedbackServiceImpl implements ComUserFeedbackService {
	
	private ComUserFeedbackDAO comUserFeedbackDAO;

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.pub.ComUserFeedbackService#queryFeedBackByParams(com.lvmama.comm.pet.po.pub.ComUserFeedback, int, int)
	 */
	@Override
	public List<ComUserFeedback> queryFeedBackByParams(
			ComUserFeedback feedBack, int beignIndex, int endIndex) {
		return comUserFeedbackDAO.queryFeedBackByParams(feedBack,beignIndex,endIndex);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.pub.ComUserFeedbackService#queryFeedBackCountByParams(com.lvmama.comm.pet.po.pub.ComUserFeedback)
	 */
	@Override
	public Long queryFeedBackCountByParams(ComUserFeedback feedBack) {
		return comUserFeedbackDAO.queryFeedBackCountByParams(feedBack);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.pub.ComUserFeedbackService#getFeedBackTypes()
	 */
	@Override
	public List<String> getFeedBackTypes() {
		return comUserFeedbackDAO.getFeedBackTypes();
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.pub.ComUserFeedbackService#getFeedBackStateIds()
	 */
	@Override
	public List<String> getFeedBackStateIds() {
		return comUserFeedbackDAO.getFeedBackStateIds();
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.pub.ComUserFeedbackService#update(com.lvmama.comm.pet.po.pub.ComUserFeedback)
	 */
	@Override
	public void update(ComUserFeedback feedBack) {
		comUserFeedbackDAO.updateByPrimaryKey(feedBack);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.pub.ComUserFeedbackService#getFeedBackByKey(java.lang.String)
	 */
	@Override
	public ComUserFeedback getFeedBackByKey(Long userFeedbackId) {
		return comUserFeedbackDAO.selectByPrimaryKey(userFeedbackId);
	}

	@Override
	public void saveUserFeedBack(ComUserFeedback feedBack) {
		this.comUserFeedbackDAO.insert(feedBack);
	}

	public void setComUserFeedbackDAO(ComUserFeedbackDAO comUserFeedbackDAO) {
		this.comUserFeedbackDAO = comUserFeedbackDAO;
	}
}
