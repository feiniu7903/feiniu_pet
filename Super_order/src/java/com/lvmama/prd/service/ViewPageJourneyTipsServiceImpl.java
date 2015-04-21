package com.lvmama.prd.service;

import java.util.List;

import com.lvmama.comm.bee.po.prod.ViewJourneyTips;
import com.lvmama.comm.bee.service.view.ViewPageJourneyTipsService;
import com.lvmama.prd.dao.ViewJourneyTipDAO;

/**行程小贴士业务层操作类
 * @author zhangxin
 *
 */
public class ViewPageJourneyTipsServiceImpl implements ViewPageJourneyTipsService {

	private ViewJourneyTipDAO viewJourneyTipDAO;

	public ViewJourneyTipDAO getViewJourneyTipDAO() {
		return viewJourneyTipDAO;
	}

	public void setViewJourneyTipDAO(ViewJourneyTipDAO viewJourneyTipDAO) {
		this.viewJourneyTipDAO = viewJourneyTipDAO;
	}

	@Override
	public void deleteViewJourneyTip(Long tipId) {
		viewJourneyTipDAO.deleteByPrimaryKey(tipId);
	}

	@Override
	public List<ViewJourneyTips> getViewJourneyTipsByJourneyId(Long journeyId) {
		return viewJourneyTipDAO.getViewJourneyTipsByJourneyId(journeyId);
	}

	@Override
	public Long insertViewJourney(ViewJourneyTips viewJourneyTips) {
		return viewJourneyTipDAO.insert(viewJourneyTips);
	}

	@Override
	public ViewJourneyTips loadViewJourneyTips(Long tipId) {
		return viewJourneyTipDAO.selectByPrimaryKey(tipId);
	}

	@Override
	public void updateViewJourneyTips(ViewJourneyTips viewJourneyTips) {
		viewJourneyTipDAO.updateViewJourneyTips(viewJourneyTips);
	}

}
