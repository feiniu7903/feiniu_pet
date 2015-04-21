package com.lvmama.report.web.cmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.zkoss.zul.Listheader;

import com.lvmama.report.po.CmtStatisticsModel;
import com.lvmama.report.service.CmtStatisticsService;
import com.lvmama.report.web.BaseAction;

public class CmtStatisticsAction extends BaseAction {
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -4547325149964434927L;
	/**
	 * 统计逻辑接口
	 */
	private CmtStatisticsService cmtStatisticsService;
	/**
	 * 前台查询条件
	 */
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	/**
	 * 记录列表
	 */
	private List<CmtStatisticsModel> list = new ArrayList<CmtStatisticsModel>();

	/**
	 * 表排序参数
	 */
	private Map<String, String> sortParam;

	/**
	 * 初始化排序
	 */
	public void doBefore() {
		sortParam = new HashMap<String, String>();
		sortParam.put("sortTypeName", "NUM"); //初始默认排序
		sortParam.put("sortAscOrDesc", "desc");
	}

	/**
	 * 搜索
	 */
	public void search() {
		//初始化查询条件
		initCondition();
		//分页
		initialPageInfoByMap(cmtStatisticsService.count(searchConds), searchConds);
		//抽取满足条件list的记录集合
		list = cmtStatisticsService.query(searchConds,false);
	}

	/**
	 * 数据导出操作
	 * @throws Exception  e
	 */
	public void export() throws Exception {

		//初始化查询条件
		initCondition();
		searchConds.remove("_startRow");
		searchConds.remove("_endRow");
		//抽取满足条件list的记录集合
		list = cmtStatisticsService.query(searchConds,true);

		//导出记录
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("excelList", list);
		String template="placeCmtStatisticsOfDayTemplate";
		if("user".equals(searchConds.get("statistics"))){
			template = "userCmtStatisticsTemplate";
		}
		doExcel(beans, "/WEB-INF/resources/template/"+template+".xls");
	}

	/**
	 * 初始化查询条件
	 */
	private void initCondition() {
		//页面查询条件判断
		if (searchConds.get("region") == null && searchConds.get("queryNum") != null) {
			alert("请补全查询条件: 点评数 ");
			return;
		}
		if (searchConds.get("region") != null && !("").equals(searchConds.get("region"))
				&& searchConds.get("queryNum") == null) {
			alert("请补全查询条件: 点评数 ");
			return;
		}

		//清空list
		if (!CollectionUtils.isEmpty(list)) {
			list.clear();
		}

		//添加表排序参数
		searchConds.putAll(sortParam);
	}

	/**
	 * 页面表格字段栏排序
	 * quantity,PAYED_QUANTITY ..对应排序的表字段
	 * @param param 页面传递变量(sortType)
	 * **/
	public void doSort(final Map<String, Object> param) {
		((Listheader) this.getComponent().getFellow("count")).setLabel("点评数");

		//从页面获取排序字段
		sortParam.put("sortTypeName", param.get("sortType").toString());

		if ("desc".equals(sortParam.get("orderBy"))) {
			sortParam.put("orderBy", "asc");
			((Listheader) this.getComponent().getFellow("count")).setLabel("点评数" + "△");
		} else {
			sortParam.put("orderBy", "desc");
			// 页面显示字段栏内容。如：点评数▽
			((Listheader) this.getComponent().getFellow("count")).setLabel("点评数" + "▽");
		}
		searchConds.put("orderBy", sortParam.get("orderBy"));
		//刷新页面查询按钮(id='searchBtn')
		refreshComponent("searchBtn");
	}
	/**
	 * 主题类型
	 * @param value 参数
	 * **/
	public void changeStageList(final String value) {
		searchConds.put("stage", value);
	}
	/**
	 * 点评数
	 * @param value 参数
	 * **/
	public void changeRegionList(final String value) {
		searchConds.put("region", value);
	}

	/**
	 * 点评状态
	 * @param value 参数
	 * **/
	public void changeCmtTypeList(final String value) {
		searchConds.put("cmtType", value);
	}

	/**
	 * 精华状态
	 * @param value 参数
	 * **/
	public void changeRecomendList(final String value) {
		searchConds.put("isBest", value);
	}

	/**
	 * 审核状态
	 * @param value 参数
	 * **/
	public void changeAuditList(final String value) {
		searchConds.put("isAudit", value);
	}

	public void setStatisticsType(final String value){
		searchConds.put("statistics", value);
	}
	public CmtStatisticsService getCmtStatisticsService() {
		return cmtStatisticsService;
	}

	public void setCmtStatisticsService(
			final CmtStatisticsService cmtStatisticsService) {
		this.cmtStatisticsService = cmtStatisticsService;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(final Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<CmtStatisticsModel> getList() {
		return list;
	}
	
	public void setList(final List<CmtStatisticsModel> list) {
		this.list = list;
	}

	public Map<String, String> getSortParam() {
		return sortParam;
	}

	public void setSortParam(final Map<String, String> sortParam) {
		this.sortParam = sortParam;
	}
}
