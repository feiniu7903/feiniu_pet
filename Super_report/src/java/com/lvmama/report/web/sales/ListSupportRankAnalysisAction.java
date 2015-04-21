package com.lvmama.report.web.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Listheader;

import com.lvmama.report.service.ReportService;
import com.lvmama.report.utils.ZkMessage;
import com.lvmama.report.vo.SupportRankAnalysisVo;
import com.lvmama.report.web.BaseAction;
/**
 * 用户排行分析Action
 * @author yangchen
 *
 */
public class ListSupportRankAnalysisAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 574844733316988175L;
	/** 导出的数据集 **/
	private List<SupportRankAnalysisVo> analysisList = new ArrayList<SupportRankAnalysisVo>();
	/** 查询条件map集合**/
	private  Map<String, Object> searchConds = new HashMap<String, Object>();
	/**reportService对象**/
	private ReportService reportService;

	/**
	 * 排序参数
	 */
	private Map<String, String> sortParam;

	/**订单总数**/
	private Long sumOrderQuantity = 0L;
	/**人数**/
	private Long sumPersonQuantity = 0L;
	/**房间数**/
	private Long sumRoomQuantity = 0L;
	/**套数**/
	private Long sumSuitQuantity = 0L;
	/**
	 * 默认为人数降序排序
	 * @exception Exception e
	 */
	public void doBefore() throws Exception {
		sortParam = new HashMap<String, String>();
		sortParam.put("sortTypeName", "PERSON_QUANTITY");
		sortParam.put("sortAscOrDesc", "desc");
	}


	/**
	 * 不能同时查询游玩时间和支付时间
	 * @return bool
	 */
	public boolean validateSearchCondsAboutDate() {
		if ((null != searchConds.get("startDate") || null != searchConds.get("endDate"))
		&& (null != searchConds.get("visitTimeStartDate") || null != searchConds.get("visitTimeEndDate"))) {
			return false;
		}
		return true;
	}

	/**
	 * 查询
	 */
	public void search() {
		// 同时查询游玩时间和订单支付完成的时间时,弹出警告框
		if (!validateSearchCondsAboutDate()) {
			ZkMessage.showError("游玩时间和订单支付完成时间只能选择其一!");
			return;
		}

		// 获取查询条件
		Map<String, Object> param = this.createMap();

		// 获取总条数
		initialPageInfoByMap(reportService.countSupportRankAnalysis(param),
				param);
		// 获取SupportAnalysisVo对象列表集合
		analysisList = reportService.querySupportRankAnalysis(param,false);

		// 订单总数
		sumOrderQuantity = reportService.sumOrderQuantity(param);
		// 人数总数
		sumPersonQuantity = reportService.sumPersonQuantity(param);
		// 房间数总数
		sumRoomQuantity = reportService.sumRoomQuantity(param);
		// 套数总数
		sumSuitQuantity = reportService.sumSuitQuantity(param);
	}

   /**
    * 导出操作
    */
	public void doExport() {
		if (!validateSearchCondsAboutDate()) {
			ZkMessage.showError("订单创建时间，订单游玩时间和订单支付完成时间只能选择其一!");
			return;
		}
		Map<String, Object> param = this.createMap();
		Map<String, List<SupportRankAnalysisVo>> beans = new HashMap<String, List<SupportRankAnalysisVo>>();
		beans.put("excelList", reportService.querySupportRankAnalysis(param,true));
		doExcel(beans, "/WEB-INF/resources/template/supportRankAnalysis.xls");
	}

	/**
	 * 获取查询的参数
	 * @return map
	 */
	public Map<String, Object> createMap() {
		// 初始化参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.putAll(sortParam);
		/** 支付时间 **/
		if (null != searchConds.get("startDate")
				|| null != searchConds.get("endDate")) {
			param.put("startDate", searchConds.get("startDate"));
			param.put("endDate", searchConds.get("endDate"));
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}

		/** 游玩时间 **/
		if (null != searchConds.get("visitTimeStartDate")
				|| null != searchConds.get("visitTimeEndDate")) {
			param.put("startDate", searchConds.get("visitTimeStartDate"));
			param.put("endDate", searchConds.get("visitTimeEndDate"));
			param.put("tableName", "META_PRODUCT_BASIC_VISIT_MV");
		}
		return param;
	}
	/**
	 * 点击人数,间数,套数进行降序升序的排序方法
	 * @param param 参数
	 */
	public void doSort(final Map<String, Object> param) {
		((Listheader) this.getComponent().getFellow("personQuantity"))
				.setLabel("人数");
		((Listheader) this.getComponent().getFellow("roomQuantity"))
				.setLabel("间数");
		((Listheader) this.getComponent().getFellow("suitQuantity"))
				.setLabel("套数");
		if ("PERSON_QUANTITY".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow(
							"personQuantity")).setLabel("人数△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow(
							"personQuantity")).setLabel("人数▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "PERSON_QUANTITY");
				((Listheader) this.getComponent().getFellow("personQuantity"))
						.setLabel("人数▽");
			}
		}
		if ("ROOM_QUANTITY".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("roomQuantity"))
							.setLabel("间数△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("roomQuantity"))
							.setLabel("间数▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "ROOM_QUANTITY");
				((Listheader) this.getComponent().getFellow("roomQuantity"))
						.setLabel("间数▽");
			}
		}
		if ("SUIT_QUANTITY".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("suitQuantity"))
							.setLabel("套数△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("suitQuantity"))
							.setLabel("套数▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "SUIT_QUANTITY");
				((Listheader) this.getComponent().getFellow("suitQuantity"))
						.setLabel("套数▽");
			}
		}
		refreshComponent("search");
	}

	public List<SupportRankAnalysisVo> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(final List<SupportRankAnalysisVo> analysisList) {
		this.analysisList = analysisList;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(final Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(final ReportService reportService) {
		this.reportService = reportService;
	}

	public Long getSumOrderQuantity() {
		return sumOrderQuantity;
	}

	public Long getSumPersonQuantity() {
		return sumPersonQuantity;
	}

	public Long getSumRoomQuantity() {
		return sumRoomQuantity;
	}

	public Long getSumSuitQuantity() {
		return sumSuitQuantity;
	}
	public Map<String, String> getSortParam() {
		return sortParam;
	}

	public void setSortParam(final Map<String, String> sortParam) {
		this.sortParam = sortParam;
	}

	public void setSumOrderQuantity(final Long sumOrderQuantity) {
		this.sumOrderQuantity = sumOrderQuantity;
	}
}
