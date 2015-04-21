package com.lvmama.report.web.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Listheader;

import com.lvmama.report.po.ProductTransformBasicMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

/**
 * 产品类型转化分析
 * @author yangchen
 */
public class ListProductTransformAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 4626037556579247119L;
	/** 查询条件map集合 **/
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	/** 获取OrderTransformBasiceMV对象的列表集合 **/
	private List<ProductTransformBasicMV> analysisList = new ArrayList<ProductTransformBasicMV>();
	/** 预订总数 **/
	private Long sumProductQuantity = 0L;
	/** 后台支付总数 **/
	private Long sumPayedQuantity = 0L;
	/** 电话总数 **/
	private Long sumCallQuantity = 0L;
	/**
	 * 获取平均预订率
	 */
	private String avgProductConvert = "";
	/**
	 * 获取平均支付率
	 */
	private String avgPayedConvert = "";

	/** 业务对象 **/
	private ReportService reportService;
	/**
	 * 排序参数
	 */
	private Map<String, String> sortParam;
	/**
	 * 默认为人数降序排序
	 * @exception Exception e
	 */
	public void doBefore() throws Exception {
		sortParam = new HashMap<String, String>();
		sortParam.put("sortTypeName", "FEEDBACKTIME");
		sortParam.put("sortAscOrDesc", "desc");
	}

	/**
	 * 单击查询
	 */
	public void doQuery() {
		// 获取查询条件
		Map<String, Object> param = this.createMap();

		// 获取总条数
		initialPageInfoByMap(reportService.countProductTransformQuantity(param), param);

		// 获取ProductTransformBasicMV对象列表集合
		analysisList = reportService.queryProductTransformBasicMV(param,false);

		// 预订总数
		sumProductQuantity = reportService.sumProductQuantity(param);

		//支付总数
		sumPayedQuantity = reportService.sumPayedProductQuantity(param);

		// 电话总数
		sumCallQuantity = reportService.sumCallQuantity(param);
		//获取平均预订率
		avgProductConvert = getAvgConvert(sumCallQuantity, sumProductQuantity);
		//获取平均预订率
		avgPayedConvert = getAvgConvert(sumProductQuantity, sumPayedQuantity);

	}

	/**
	 * 导出数据
	 *
	 */
	public void doExport() {
		Map<String, Object> param = this.createMap();
		// 获取数据
		analysisList = reportService.queryProductTransformBasicMV(param,true);
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/productTransformAnalysis.xls");
	}

	/**
	 * 获取查询的参数
	 * @return map
	 */
	public Map<String, Object> createMap() {
		String sqlString = "";
		Map<String, Object> param = new HashMap<String, Object>();
		param.putAll(sortParam);

		if (searchConds.get("sTicket") != null) {
			if ((Boolean) searchConds.get("sTicket")) {
				if (!"".equals(sqlString)) {
					sqlString += " or (PRODUCT_TYPE ='打折门票')";
				} else {
					sqlString += "(PRODUCT_TYPE ='打折门票')";
				}
			}
		}
		if (searchConds.get("sHotel") != null) {
			if ((Boolean) searchConds.get("sHotel")) {
				if (!"".equals(sqlString)) {
					sqlString += " or (PRODUCT_TYPE='特色酒店')";
				} else {
					sqlString += "(PRODUCT_TYPE='特色酒店')";
				}
			}
		}

		if (searchConds.get("sGroup") != null) {
			if ((Boolean) searchConds.get("sGroup")) {
				if (!"".equals(sqlString)) {
					sqlString += " or PRODUCT_TYPE='周边游'";
				} else {
					sqlString += "PRODUCT_TYPE='周边游'";
				}
			}
		}

		if (searchConds.get("sGroupLong") != null) {
			if ((Boolean) searchConds.get("sGroupLong")) {
				if (!"".equals(sqlString)) {
					sqlString += " or PRODUCT_TYPE='国内游'";
				} else {
					sqlString += "PRODUCT_TYPE='国内游'";
				}
			}
		}

		if (searchConds.get("sGroupForeign") != null) {
			if ((Boolean) searchConds.get("sGroupForeign")) {
				if (!"".equals(sqlString)) {
					sqlString += " or PRODUCT_TYPE='出境游'";
				} else {
					sqlString += "PRODUCT_TYPE='出境游'";
				}
			}
		}

		if (searchConds.get("sFree") != null) {
			if ((Boolean) searchConds.get("sFree")) {
				if (!"".equals(sqlString)) {
					sqlString += " or PRODUCT_TYPE='自由行'";
				} else {
					sqlString += "PRODUCT_TYPE='自由行'";
				}
			}
		}
		if (null != searchConds.get("startDate")
				|| null != searchConds.get("endDate")) {
			param.put("startDate", searchConds.get("startDate"));
			param.put("endDate", searchConds.get("endDate"));
		}
		if (!"".equals(sqlString)) {
			param.put("sql", sqlString);
		}
		return param;
	}
	/**
	 * 点击人数,间数,套数进行降序升序的排序方法
	 * @param param 参数
	 */
	public void doSort(final Map<String, Object> param) {
		((Listheader) this.getComponent().getFellow("quantity"))
				.setLabel("下单量");
		((Listheader) this.getComponent().getFellow("payedQuantity"))
				.setLabel("支付量");
		((Listheader) this.getComponent().getFellow("callCount"))
		.setLabel("电话咨询总数");
		if ("Quantity".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow(
							"quantity")).setLabel("下单量△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow(
							"quantity")).setLabel("下单量▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "Quantity");
				((Listheader) this.getComponent().getFellow("quantity"))
						.setLabel("下单量▽");
			}
		}
		if ("PayedQuantity".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("payedQuantity"))
							.setLabel("支付量△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("payedQuantity"))
							.setLabel("支付量▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "PayedQuantity");
				((Listheader) this.getComponent().getFellow("payedQuantity"))
						.setLabel("支付量▽");
			}
		}
		if ("CALLS".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("callCount"))
							.setLabel("电话咨询总数△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("callCount"))
							.setLabel("电话咨询总数▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "CALLS");
				((Listheader) this.getComponent().getFellow("callCount"))
						.setLabel("电话咨询总数▽");
			}
		}
		refreshComponent("search");
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

	public List<ProductTransformBasicMV> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(final List<ProductTransformBasicMV> analysisList) {
		this.analysisList = analysisList;
	}

	public Long getSumProductQuantity() {
		return sumProductQuantity;
	}

	public void setSumProductQuantity(final Long sumProductQuantity) {
		this.sumProductQuantity = sumProductQuantity;
	}

	public Long getSumPayedQuantity() {
		return sumPayedQuantity;
	}

	public void setSumPayedQuantity(final Long sumPayedQuantity) {
		this.sumPayedQuantity = sumPayedQuantity;
	}

	public Long getSumCallQuantity() {
		return sumCallQuantity;
	}

	public void setSumCallQuantity(final Long sumCallQuantity) {
		this.sumCallQuantity = sumCallQuantity;
	}

	public String getAvgProductConvert() {
		return avgProductConvert;
	}

	public void setAvgProductConvert(final String avgProductConvert) {
		this.avgProductConvert = avgProductConvert;
	}

	public String getAvgPayedConvert() {
		return avgPayedConvert;
	}

	public void setAvgPayedConvert(final String avgPayedConvert) {
		this.avgPayedConvert = avgPayedConvert;
	}

	/**
	 * 获取统计的支付转化率
	 * @param count 支付
	 * @param orderQuantity 订单
	 * @return 支付转化率
	 */
	public String getAvgConvert(final Long count, final Long orderQuantity) {
		//获取统计转化率
		if (count != null || orderQuantity != null) {
			if (count == 0) {
				return "0.0%";
			} else {
				String str = (orderQuantity * 100.0 / count) + "";
				str = str.substring(0, str.indexOf(".") + 2);
				return str + "%";
			}
		}
		return "0.0%";
	}
}