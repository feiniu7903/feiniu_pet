package com.lvmama.report.web.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.report.po.UserRegisterBasic;
import com.lvmama.report.po.UserRegisterChannelMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.service.UserRegisterChannelService;
import com.lvmama.report.web.BaseAction;

public class ListRegAnalysisAction extends BaseAction {
	private static final long serialVersionUID = -8852005824658686747L;

	private List<UserRegisterBasic> analysisList = new ArrayList<UserRegisterBasic>();

	private List<UserRegisterChannelMV> allChannel = new ArrayList<UserRegisterChannelMV>();
	private List<UserRegisterChannelMV> firstLevelChannel = new ArrayList<UserRegisterChannelMV>();
	private List<UserRegisterChannelMV> secondLevelChannel = new ArrayList<UserRegisterChannelMV>();
	private List<UserRegisterChannelMV> thirdLevelChannel = new ArrayList<UserRegisterChannelMV>();
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	private Long newUser; // 新增用户总数

	private Long selectedFirstLevelChannelId;
	private Long selectedSecondLevelChannelId;
	private Long selectedThirdLevelChannelId;

	private ReportService reportService;
	private UserRegisterChannelService userRegisterChannelService;

	@Override
	public void doBefore() {
		allChannel = userRegisterChannelService.getUserRegisterChannelMV();

		refreshFirstLevelChannel();
		refreshSecondLevelChannel(null);
		refreshThirdLevelChannel(null);
	}

	public void doQuery() throws Exception {
		searchConds.remove("channelId");
		searchConds.remove("channelIdList");
		searchConds.put("channelId", generateChannelId());

		newUser = reportService.sumUserUpdate(searchConds);

		initialPageInfoByMap(reportService.countUserRegisterBasic(searchConds),
				searchConds);
		analysisList = reportService.queryUserRegisterBasic(searchConds,false);
	}

	public void doExport() throws Exception {
		searchConds.remove("channelId");
		searchConds.remove("channelIdList");
		searchConds.remove("_startRow"); 
		searchConds.remove("_endRow");
		searchConds.put("channelId", generateChannelId());

		analysisList = reportService.queryUserRegisterBasic(searchConds,true);
		Map beans = new HashMap();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/regAnalysis.xls");
	}

	public void changeFirstLevelChannel(Long channelId) {
		refreshSecondLevelChannel(channelId);
		refreshThirdLevelChannel(null);
		selectedFirstLevelChannelId = channelId;
	}

	public void changeSecondLevelChannel(Long channelId) {
		refreshThirdLevelChannel(channelId);
		selectedSecondLevelChannelId = channelId;
	}

	public void changeThirdLevelChannel(Long channelId) {
		selectedThirdLevelChannelId = channelId;
	}

	private Long generateChannelId() {
		if (null != selectedThirdLevelChannelId) {
			return selectedThirdLevelChannelId;
		} else {
			if (null != selectedSecondLevelChannelId) {
				return selectedSecondLevelChannelId;
			} else {
				if (null != selectedFirstLevelChannelId) {
					return selectedFirstLevelChannelId;
				} else {
					return null;
				}
			}
		}
	}
	/**
	 * @deprecated
	 * @return
	 */
	private List<String> generateChannelList() {
		if (null == selectedThirdLevelChannelId && null == selectedSecondLevelChannelId && null == selectedFirstLevelChannelId) {
			return null;
		}
		List<String> _channelList = new ArrayList<String>();
		if (null != this.selectedThirdLevelChannelId) {
			for (UserRegisterChannelMV mv : allChannel) {
				if (mv.getChannelId() != null
						&& mv.getChannelId()
								.equals(selectedThirdLevelChannelId)) {
					_channelList.add(mv.getChannelCode());
				}
			}
		} else {
			if (null != this.selectedSecondLevelChannelId) {
				for (UserRegisterChannelMV mv : allChannel) {
					if (mv.getFatherId() != null
							&& mv.getFatherId().equals(
									this.selectedSecondLevelChannelId)) {
						_channelList.add(mv.getChannelCode());
					}
				}
			} else {
				if (null != this.selectedFirstLevelChannelId) {
					List<Long> allSelectedSecondChannelId = new ArrayList<Long>();
					for (UserRegisterChannelMV mv : allChannel) {
						if (mv.getFatherId() != null
								&& mv.getFatherId().equals(
										this.selectedFirstLevelChannelId)) {
							allSelectedSecondChannelId.add(mv.getChannelId());
						}
					}
					for (UserRegisterChannelMV mv : allChannel) {
						if (mv.getFatherId() != null
								&& allSelectedSecondChannelId.contains(mv
										.getFatherId())) {
							allSelectedSecondChannelId.add(mv.getChannelId());
						}
					}
				}
			}
		}
		if (_channelList.isEmpty()) {
			_channelList.add("_ttttt");
		}
		return _channelList;
	}

	private void refreshFirstLevelChannel() {
		firstLevelChannel.clear();
		firstLevelChannel.add(new UserRegisterChannelMV(null, "----请选择----",
				null));
		for (UserRegisterChannelMV mv : allChannel) {
			if (mv.getLayer() != null && mv.getLayer().equals(1L)) {
				firstLevelChannel.add(mv);
			}
		}
		selectedFirstLevelChannelId = null;
	}

	private void refreshSecondLevelChannel(Long fatherId) {
		secondLevelChannel.clear();
		secondLevelChannel.add(new UserRegisterChannelMV(null, "----请选择----",
				null));
		for (UserRegisterChannelMV mv : allChannel) {
			if (mv.getFatherId() != null && mv.getFatherId().equals(fatherId)) {
				secondLevelChannel.add(mv);
			}
		}
		selectedSecondLevelChannelId = null;
	}

	private void refreshThirdLevelChannel(Long fatherId) {
		thirdLevelChannel.clear();
		thirdLevelChannel.add(new UserRegisterChannelMV(null, "----请选择----",
				null));
		for (UserRegisterChannelMV mv : allChannel) {
			if (mv.getFatherId() != null && mv.getFatherId().equals(fatherId)) {
				thirdLevelChannel.add(mv);
			}
		}
		selectedThirdLevelChannelId = null;
	}

	// setter and getter
	public List<UserRegisterBasic> getAnalysisList() {
		return analysisList;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public Long getNewUser() {
		return newUser;
	}

	public void setNewUser(Long newUser) {
		this.newUser = newUser;
	}

	public void setUserRegisterChannelService(
			UserRegisterChannelService userRegisterChannelService) {
		this.userRegisterChannelService = userRegisterChannelService;
	}

	public List<UserRegisterChannelMV> getFirstLevelChannel() {
		return firstLevelChannel;
	}

	public List<UserRegisterChannelMV> getSecondLevelChannel() {
		return secondLevelChannel;
	}

	public List<UserRegisterChannelMV> getThirdLevelChannel() {
		return thirdLevelChannel;
	}
}
