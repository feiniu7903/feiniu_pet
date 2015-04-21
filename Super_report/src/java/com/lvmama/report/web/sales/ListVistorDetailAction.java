package com.lvmama.report.web.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.report.po.VistorDetailBasicMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

/**
 * 游客信息Action
 * @author yangchen
 */
public class ListVistorDetailAction extends BaseAction {

	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 832317991239553741L;
	/** 查询条件map集合 **/
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	/** 获取VistorDetailBasicMV对象的列表集合 **/
	private List<VistorDetailBasicMV> analysisList = new ArrayList<VistorDetailBasicMV>();
	/** 业务对象 **/
	private ReportService reportService;

	/**
	 * 单击查询
	 */
	public void doQuery() {
		// 获取查询条件
		Map<String, Object> param = this.createMap();

		// 获取总条数
		Long countVistor = reportService.countVistorDetailBasicMV(param);
		initialPageInfoByMap(countVistor,
				param);

		// 获取VistorDetailBasicMV对象列表集合
		if(countVistor > 0) {
			analysisList = reportService.queryVistorDetailBasicMV(param,false);
		} else {
			analysisList = new ArrayList<VistorDetailBasicMV>();
		}
	}

	/**
	 * 导出数据
	 */
	public void doExport() {
		Map<String, Object> param = this.createMap();
		// 获取数据
		analysisList = reportService.queryVistorDetailBasicMV(param,true);
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/vistorDetail.xls");
	}

	/**
	 * 配置searchConds查询条件
	 * @return 查询条件
	 * */
	public Map<String, Object> createMap() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("payStartDate", searchConds.get("payStartDate"));
		param.put("payEndDate", searchConds.get("payEndDate"));
		param.put("visitStartDate", searchConds.get("visitStartDate"));
		param.put("visitEndDate", searchConds.get("visitEndDate"));
		if (!StringUtils.isEmpty((String) searchConds.get("orderId"))) {
			if (!"".equals(searchConds.get("orderId").toString().trim())) {
			param.put("orderId", searchConds.get("orderId").toString().trim());
			}
		}
		if (!StringUtils.isEmpty((String) searchConds.get("prodProductIds"))) {
			String ids = searchConds.get("prodProductIds").toString().replace(" ", "");
			if (!"".equals(searchConds.get("prodProductIds").toString().replace(" ", ""))) {
				param.put("prodProductIds", ids.split(","));
			}
		}
		if (!StringUtils.isEmpty((String) searchConds.get("prodProductId"))) {
			if (!"".equals(searchConds.get("prodProductId").toString().trim())) {
				param.put("prodProductId", searchConds.get("prodProductId")
						.toString().trim());
			}
		}
		if (!StringUtils.isEmpty((String) searchConds.get("prodName"))) {
			if (!"".equals(searchConds.get("prodName").toString().trim())) {
			param.put("prodName", searchConds.get("prodName").toString().trim());
			}
		}
		if (!StringUtils.isEmpty((String) searchConds.get("realName"))) {
			if (!"".equals(searchConds.get("realName").toString().trim())) {
			param.put("realName", searchConds.get("realName").toString().trim());
			}
		}
		if (!StringUtils.isEmpty((String) searchConds.get("filialeName"))) {
			if (!"".equals(searchConds.get("filialeName").toString().trim())) {
			param.put("filialeName", searchConds.get("filialeName").toString()
					.trim());
			}
		}
		// 获取SUB_PRODUCT_TYPE 查询条件
		String sqlString = getSubProductTypeParam();
		if (!"".equals(sqlString)) {
			param.put("sql", sqlString);
		}
		return param;
	}

	/**
	 * 获取SUB_PRODUCT_TYPE 查询条件
	 * @return 查询类型
	 * */
	public String getSubProductTypeParam() {
		String sqlString = "";

		if (searchConds.get("sTicket") != null) {
			if ((Boolean) searchConds.get("sTicket")) {
				if (!"".equals(sqlString)) {
					sqlString += " or (PRODUCT_TYPE ='TICKET')";
				} else {
					sqlString += "(PRODUCT_TYPE ='TICKET')";
				}

			}
		}
		if (searchConds.get("sHotel") != null) {
			if ((Boolean) searchConds.get("sHotel")) {
				if (!"".equals(sqlString)) {
					sqlString += " or (PRODUCT_TYPE='HOTEL')";
				} else {
					sqlString += "(PRODUCT_TYPE='HOTEL')";
				}
			}
		}

		if (searchConds.get("sGroup") != null) {
			if ((Boolean) searchConds.get("sGroup")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='GROUP'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='GROUP'";
				}
			}
		}

		if (searchConds.get("sGroupLong") != null) {
			if ((Boolean) searchConds.get("sGroupLong")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='GROUP_LONG'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='GROUP_LONG'";
				}
			}
		}

		if (searchConds.get("sGroupForeign") != null) {
			if ((Boolean) searchConds.get("sGroupForeign")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='GROUP_FOREIGN'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='GROUP_FOREIGN'";
				}
			}
		}

		if (searchConds.get("sFree") != null) {
			if ((Boolean) searchConds.get("sFree")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='FREENESS'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='FREENESS'";
				}
			}
		}

		if (searchConds.get("sFreeLong") != null) {
			if ((Boolean) searchConds.get("sFreeLong")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='FREENESS_LONG'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='FREENESS_LONG'";
				}
			}
		}

		if (searchConds.get("sFreeForeign") != null) {
			if ((Boolean) searchConds.get("sFreeForeign")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='FREENESS_FOREIGN'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='FREENESS_FOREIGN'";
				}
			}
		}

		if (searchConds.get("sSelfhelpBus") != null) {
			if ((Boolean) searchConds.get("sSelfhelpBus")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='SELFHELP_BUS'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='SELFHELP_BUS'";
				}
			}
		}

		if (searchConds.get("sInsurance") != null) {
			if ((Boolean) searchConds.get("sInsurance")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='INSURANCE'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='INSURANCE'";
				}
			}
		}

		if (searchConds.get("sFangCha") != null) {
			if ((Boolean) searchConds.get("sFangCha")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='FANGCHA'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='FANGCHA'";
				}
			}
		}

		if (searchConds.get("sOther") != null) {
			if ((Boolean) searchConds.get("sOther")) {
				if (!"".equals(sqlString)) {
					sqlString += " or SUB_PRODUCT_TYPE='OTHER'";
				} else {
					sqlString += "SUB_PRODUCT_TYPE='OTHER'";
				}
			}
		}
		return sqlString;
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

	public List<VistorDetailBasicMV> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(final List<VistorDetailBasicMV> analysisList) {
		this.analysisList = analysisList;
	}

	public void setReportService(final ReportService reportService) {
		this.reportService = reportService;
	}

}
