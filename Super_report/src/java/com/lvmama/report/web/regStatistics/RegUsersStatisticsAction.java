package com.lvmama.report.web.regStatistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;

import com.lvmama.report.service.RegUsersStatisticsService;
import com.lvmama.report.vo.TotalAnalysisVo;
import com.lvmama.report.vo.UserAnalysisVO;
import com.lvmama.report.web.BaseAction;

/**
 * 用户注册统计逻辑
 */
public class RegUsersStatisticsAction extends BaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = -4715414372235260130L;

	/**
	 * 注册统计逻辑类
	 */
	private RegUsersStatisticsService regUsersStatisticsService;
	/**
	 * userAnalysisVoList 注册状况记录列表
	 */
	private final List<UserAnalysisVO> userAnalysisVoList = new ArrayList<UserAnalysisVO>();
	/**
	 * userBehaviorVoList 用户重要行为记录列表
	 */
	private final List<UserAnalysisVO> userBehaviorVoList = new ArrayList<UserAnalysisVO>();
	/**
	 * totalStatisticsVoList 整体状况统计记录列表
	 */
	private List<TotalAnalysisVo> totalStatisticsVoList = new ArrayList<TotalAnalysisVo>();
	/**
	 * 前台查询条件
	 */
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	/**
	 * 后台数据查询条件
	 */
	private final Map<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * 搜索数据列表
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unchecked")
	public void search() throws IllegalArgumentException,
			IllegalAccessException, SecurityException, NoSuchFieldException {

		// userStatisticsVoList 用户数据分布情况(全部)记录列表
		List<UserAnalysisVO> userStatisticsVoList = new ArrayList<UserAnalysisVO>();
		// 设置后台parameters的查询条件
		buildSearchCons();

		// 判断List是否为空,如果不为空那么清空列表,防止重复数据
		if (!CollectionUtils.isEmpty(userAnalysisVoList)) {
			userAnalysisVoList.clear();
		}
		if (!CollectionUtils.isEmpty(userBehaviorVoList)) {
			userBehaviorVoList.clear();
		}

		// 抽取满足条件UserAnalysisVO的记录集合
		userStatisticsVoList = regUsersStatisticsService.getUserAnalysisVOList(parameters);
		// 获取满足条件的TotalAnalysisVo记录集合
		totalStatisticsVoList = regUsersStatisticsService.getTotalAnalysisTV(parameters);

		// 用户注册状况数据 (本周用户分为两部分一:用户注册,二:用户行为)
		for (int i = 0;  i < 17;  i++) {
			userAnalysisVoList.add(userStatisticsVoList.get(i));
		}
		// 用户行为数据插入用户行为的列表中
		for (int i = 17; i < userStatisticsVoList.size(); i++) {
			userBehaviorVoList.add(userStatisticsVoList.get(i));
		}
	}

	/**
	 * 数据导出操作
	 * 
	 * @throws Exception
	 *             e
	 */
	public void exportUsers() throws Exception {
		// 搜索展示数据
		search();
		Map beans = new HashMap();

		//整体注册用户统计
		beans.put("totalAnalysisTVList", totalStatisticsVoList);
		//本周用户注册用户统计(用户注册,用户行为)
		beans.put("userAnalysisVo", userAnalysisVoList);
		beans.put("userBehaviorVo", userBehaviorVoList);

		doExcel(beans, "/WEB-INF/resources/template/RegUsersStatisticsTemplate.xls");
	}

	/**
	 * 设置查询条件
	 */
	private void buildSearchCons() {
		if (null != searchConds.get("endDate")) {
			parameters.put("endDate", searchConds.get("endDate"));
		} else {
			parameters.put("endDate", new Date());
		}
	}

	/**
	 * 前台页面 时间的判断函数
	 */
	public void checkDate(String start, String dateException) {
		Datebox startBox = (Datebox) getComponent().getFellow(start);
		Label dateExceptionLabel = (Label) getComponent().getFellow(
				dateException);
		if ((startBox.getValue()).after(new Date())) {
			throw new WrongValueException(dateExceptionLabel, "开始时间不能晚于今天");
		}
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(final Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public void setRegUsersStatisticsService(
			final RegUsersStatisticsService regUsersStatisticsService) {
		this.regUsersStatisticsService = regUsersStatisticsService;
	}

	public List<UserAnalysisVO> getUserBehaviorVo() {
		return userBehaviorVoList;
	}

	public List<UserAnalysisVO> getUserAnalysisVoList() {
		return userAnalysisVoList;
	}

	public List<UserAnalysisVO> getUserBehaviorVoList() {
		return userBehaviorVoList;
	}

	public List<TotalAnalysisVo> getTotalStatisticsVoList() {
		return totalStatisticsVoList;
	}
}