package com.lvmama.report.web.sales;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.report.po.MetaProductBasicMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.utils.ZkMessage;
import com.lvmama.report.web.BaseAction;


/**
 * 供应商分析
 * @author yangchen
 *
 */
public class ListSupportAnalysisAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -2703393760827303061L;
	/** 格式化数字**/
	private  final DecimalFormat financialDF =
		new DecimalFormat("###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###.00");
	/** 获取SupportAnalysisVo对象列表集合**/
	private List<MetaProductBasicMV> analysisList = new ArrayList<MetaProductBasicMV>();
	/**查询条件map集合**/
	private  Map<String, Object> searchConds = new HashMap<String, Object>();
	/**业务对象**/
	private ReportService reportService;
	/**销售总额**/
	private String amount = "0";
	/**订单量**/
	private Long sumOrderQuantity = 0L;
	/**销量**/
	private Long sumAmountQuantity = 0L;

	/**
	 * 不能同时查询游玩时间和支付时间
	 * @return bool
	 */
	public boolean validateSearchCondsAboutDate() {
		if ((null != searchConds.get("startDate") || null != searchConds
				.get("endDate"))
				&& (null != searchConds.get("visitTimeStartDate") || null != searchConds
						.get("visitTimeEndDate"))) {
			return false;
		}
		return true;
	}

	/**
	 * 查询
	 */
	public void doQuery() {
		// 同时查询游玩时间和订单支付完成的时间时,弹出警告框
		if (!validateSearchCondsAboutDate()) {
			ZkMessage.showError("游玩时间和订单支付完成时间只能选择其一!");
			return;
		}

		// 获取查询条件
		Map<String, Object> param = this.createMap();

		// 获取总条数
		initialPageInfoByMap(reportService.countMetaProductBasicMV(param),
				param);

		// 获取SupportAnalysisVo对象列表集合
		analysisList = reportService.queryMetaProductBasicMV(param,false);

		// 获取总销售额
		Long amountDouble = reportService.sumAmountMetaProductBasicMV(param);
		if (amountDouble != null) {
			amount = financialDF.format(amountDouble);
		} else {
			amount = financialDF.format(0);
		}

		// 订单总数
		sumOrderQuantity = reportService
				.sumOrderQuantityMetaProductBasicMV(param);

		// 销量总数
		sumAmountQuantity = reportService
				.sumAmountQuantityMetaProductBasicMV(param);
	}

	/**
	 * 导出数据
	 */
	public void doExport() {
		if (!validateSearchCondsAboutDate()) {
			ZkMessage.showError("订单创建时间，订单游玩时间和订单支付完成时间只能选择其一!");
			return;
		}
		Map<String, Object> param = this.createMap();
		analysisList = reportService.queryMetaProductBasicMV(param,true);
		Map<String, List<MetaProductBasicMV>> beans = new  HashMap<String, List<MetaProductBasicMV>>();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/supportAnalysis.xls");
	}

	/**
	 * 获取查询的参数
	 * @return map
	 */
	public Map<String, Object> createMap() {
		String sqlString = "";
		// 初始化参数
		Map<String, Object> param = new HashMap<String, Object>();

		// 添加表查询参数$sql$
		// searchConds.get("sTicket") 页面checkbox选项值
		/** 门票 **/
		if (searchConds.get("sTicket") != null
				&& (Boolean) searchConds.get("sTicket")) {
			if (!"".equals(sqlString)) {
				sqlString += " or "; // sqlString已经有SQL语句
			}
			//当勾选门票,产品类型为门票
			sqlString += "((c.product_type='TICKET')";
				// 条件不为全部
				if (!"".equalsIgnoreCase((String) searchConds.get("subTicket"))
						&& null != searchConds.get("subTicket")) {
					// 具体选项
					sqlString = setSqlString(sqlString,
							(String) searchConds.get("subTicket"));
			}
				sqlString += ")";
		}
		/** 酒店 **/
		if (searchConds.get("sHotel") != null
				&& (Boolean) searchConds.get("sHotel")) {
			if (!"".equals(sqlString)) {
				sqlString += " or "; // sqlString已经有SQL语句
			}
			//当勾选酒店时,产品类型为酒店
			sqlString += "((c.product_type='HOTEL')";
						if (!"".equalsIgnoreCase((String) searchConds.get("subHotel"))
								&& null != searchConds.get("subHotel")) {
					sqlString = setSqlString(sqlString,
							(String) searchConds.get("subHotel"));
			}
			sqlString += ")";
		}
		/** 线路 **/
		if (searchConds.get("sLines") != null
				&& (Boolean) searchConds.get("sLines")) {
			if (!"".equals(sqlString)) {
				sqlString += " or ";
			}
			//当勾选线路时,产品类型为线路
			sqlString += "((c.product_type='ROUTE')";
				if (!"".equalsIgnoreCase((String) searchConds.get("subLines"))
						&& null != searchConds.get("subLines")) {
					sqlString = setSqlString(sqlString,
							(String) searchConds.get("subLines"));
				}
				sqlString += ")";
		}

		/** 其他 **/
		if (searchConds.get("sOther") != null
				&& (Boolean) searchConds.get("sOther")) {
			if (!"".equals(sqlString)) {
				sqlString += " or ";
			}
			//当勾选其他时时,产品类型为其他
			sqlString += "((c.product_type='OTHER')";
				if (!"".equalsIgnoreCase((String) searchConds.get("subOther"))
						&& null != searchConds.get("subOther")) {
					sqlString = setSqlString(sqlString,
							(String) searchConds.get("subOther"));
				}
				sqlString += ")";
		}

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
		/** 采购产品Id=""时 采购产品Id为空 **/
		if (null != searchConds.get("metaProductId")) {
		if (!"".equals(searchConds.get("metaProductId").toString().trim())) {
			param.put("meta_product_id", searchConds.get("metaProductId").toString().trim());
		}
	}

		/** 判断条件是否为空,如果为空不存放与param中 **/
		if (null != searchConds.get("supplierName")) {
			param.put("supplierName", searchConds.get("supplierName").toString().trim());
		}
		if (null != searchConds.get("metaProductName")) {
			param.put("metaProdName", searchConds.get("metaProductName").toString().trim());
		}
		if (!"".equals(sqlString)) {
			param.put("sql", sqlString);
		}
		return param;
	}

	/**
	 * 设置sql语句
	 * @param sqlString  sql语句
	 * @param subStr    查询的数据
	 * @return sql
	 */
	public String setSqlString(final String sqlString, final String subStr) {
		String sqlStr = "";
		if (!"".equals(sqlString)) {
			sqlStr = sqlString + " and   (c.META_PRODUCT_TYPE='" + subStr + "')";
		} else {
			sqlStr = sqlString + " (c.META_PRODUCT_TYPE='" + subStr + "')";
		}
		return sqlStr;
	}

	/** 点击下拉列表的事件(门票)
	 * @param value v
	 **/
	public void changeSubTicket(final String value) {
		searchConds.put("subTicket", value);
	}
	/** 点击下拉列表的事件(酒店)
	 * @param value v
	 **/
	public void changeSubHotel(final String value) {
		searchConds.put("subHotel", value);
	}
	/** 点击下拉列表的事件(其他)
	 * @param value v
	 **/
	public void changeSubOther(final String value) {
		searchConds.put("subOther", value);
	}
	/** 点击下拉列表的事件(线路)
	 * @param value v
	 **/
	public void changeLines(final String value) {
		searchConds.put("subLines", value);
	}


	public List<MetaProductBasicMV> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(final List<MetaProductBasicMV> analysisList) {
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

	public String getAmount() {
		return amount;
	}

	public Long getSumOrderQuantity() {
		return sumOrderQuantity;
	}

	public Long getSumAmountQuantity() {
		return sumAmountQuantity;
	}

}
