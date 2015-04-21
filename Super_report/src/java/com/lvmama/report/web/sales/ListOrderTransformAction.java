package com.lvmama.report.web.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Listheader;

import com.lvmama.report.po.OrderTransformBasiceMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

/**
 * 订单转化分析
 * @author yangchen
 */
public class ListOrderTransformAction extends BaseAction {

	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 4626037556579247119L;
	/** 查询条件map集合 **/
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	/** 获取OrderTransformBasiceMV对象的列表集合 **/
	private List<OrderTransformBasiceMV> analysisList = new ArrayList<OrderTransformBasiceMV>();
	/** 前台预订总数 **/
	private Long sumFOrderQuantity = 0L;
	/** 后台预订总数 **/
	private Long sumBOrderQuantity = 0L;
	/** 前台已支付总数 **/
	private Long sumFPayedQuantity = 0L;
	/** 后台支付总数 **/
	private Long sumBPayedQuantity = 0L;
	/**
	 * 获取后台的平均支付率
	 */
	private String avgBOrderConvert = "";
	/**
	 * 获取前台的平均支付率
	 */
	private String avgFOrderConvert = "";

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
		sortParam.put("sortTypeName", "FPAYEDQUANTITY");
		sortParam.put("sortAscOrDesc", "desc");
	}

	/**
	 * 单击查询
	 */
	public void doQuery() {
		// 获取查询条件
		Map<String, Object> param = this.createMap();

		// 获取总条数
		initialPageInfoByMap(reportService.countOrderTransformQuantity(param),
				param);

		// 获取OrderTransformBasiceMV对象列表集合
		analysisList = reportService.queryOrderTransformBasicMV(param,false);

		// 前台订单总数
		sumFOrderQuantity = reportService.sumFOrderQuantity(param);

		// 后台订单总数
		sumBOrderQuantity = reportService.sumBOrderQuantity(param);

		// 前台已支付总数
		sumFPayedQuantity = reportService.sumFPayedOrderQuantity(param);

		// 后台已支付总数
		sumBPayedQuantity = reportService.sumBPayedOrderQuantity(param);

		//前台支付率统计
		avgFOrderConvert = getAvgConvert(sumFPayedQuantity, sumFOrderQuantity);

		//后台支付率统计
		avgBOrderConvert = getAvgConvert(sumBPayedQuantity, sumBOrderQuantity);

	}

	/**
	 * 导出数据
	 * @throws Exception
	 */
	public void doExport() {
		Map<String, Object> param = this.createMap();
		// 获取数据
		analysisList = reportService.queryOrderTransformBasicMV(param,true);
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/orderTransformAnalysis.xls");
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

		if (null != searchConds.get("startDate")
				|| null != searchConds.get("endDate")) {
			param.put("startDate", searchConds.get("startDate"));
			param.put("endDate", searchConds.get("endDate"));
		}

		/** 判断条件是否为空,如果为空不存放与param中 **/
		if (null != searchConds.get("prodName")) {
			param.put("productName", searchConds.get("prodName").toString().trim());
		}

		/** 采购产品Id=""时 采购产品Id为空 **/
		if (null != searchConds.get("prodId")) {
		if (!"".equals(searchConds.get("prodId").toString().trim())) {
			param.put("productId", searchConds.get("prodId").toString().trim());
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
		((Listheader) this.getComponent().getFellow("fQuantity"))
				.setLabel("前台下单量");
		((Listheader) this.getComponent().getFellow("fPayedQuantity"))
				.setLabel("前台支付量");
		((Listheader) this.getComponent().getFellow("bQuantity"))
				.setLabel("后台下单量");
		((Listheader) this.getComponent().getFellow("bPayedQuantity"))
				.setLabel("后台支付量");
		if ("FQUANTITY".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow(
							"fQuantity")).setLabel("前台下单量△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow(
							"fQuantity")).setLabel("前台下单量▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "FQUANTITY");
				((Listheader) this.getComponent().getFellow("fQuantity"))
						.setLabel("前台下单量▽");
			}
		}
		if ("FPAYEDQUANTITY".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("fPayedQuantity"))
							.setLabel("前台支付量△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("fPayedQuantity"))
							.setLabel("前台支付量▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "FPAYEDQUANTITY");
				((Listheader) this.getComponent().getFellow("fPayedQuantity"))
						.setLabel("前台支付量▽");
			}
		}
		if ("BQUANTITY".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("bQuantity"))
							.setLabel("后台下单量△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("bQuantity"))
							.setLabel("后台下单量▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "BQUANTITY");
				((Listheader) this.getComponent().getFellow("bQuantity"))
						.setLabel("后台下单量▽");
			}
		}
		if ("BPAYEDQUANTITY".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("bPayedQuantity"))
							.setLabel("后台支付量△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("bPayedQuantity"))
							.setLabel("后台支付量▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "BPAYEDQUANTITY");
				((Listheader) this.getComponent().getFellow("bPayedQuantity"))
						.setLabel("后台支付量▽");
			}
		}
		refreshComponent("search");
	}

	public Long getSumFOrderQuantity() {
		return sumFOrderQuantity;
	}

	public void setSumFOrderQuantity(final Long sumFOrderQuantity) {
		this.sumFOrderQuantity = sumFOrderQuantity;
	}

	public Long getSumBOrderQuantity() {
		return sumBOrderQuantity;
	}

	public void setSumBOrderQuantity(final Long sumBOrderQuantity) {
		this.sumBOrderQuantity = sumBOrderQuantity;
	}

	public Long getSumFPayedQuantity() {
		return sumFPayedQuantity;
	}

	public void setSumFPayedQuantity(final Long sumFPayedQuantity) {
		this.sumFPayedQuantity = sumFPayedQuantity;
	}

	public Long getSumBPayedQuantity() {
		return sumBPayedQuantity;
	}

	public void setSumBPayedQuantity(final Long sumBPayedQuantity) {
		this.sumBPayedQuantity = sumBPayedQuantity;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(final Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<OrderTransformBasiceMV> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(final List<OrderTransformBasiceMV> analysisList) {
		this.analysisList = analysisList;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public String getAvgBOrderConvert() {
		return avgBOrderConvert;
	}

	public void setAvgBOrderConvert(final String avgBOrderConvert) {
		this.avgBOrderConvert = avgBOrderConvert;
	}

	public String getAvgFOrderConvert() {
		return avgFOrderConvert;
	}

	public void setAvgFOrderConvert(final String avgFOrderConvert) {
		this.avgFOrderConvert = avgFOrderConvert;
	}

	public void setReportService(final ReportService reportService) {
		this.reportService = reportService;
	}
	/**
	 * 获取统计的支付转化率
	 * @param payedQuantity 支付
	 * @param orderQuantity 订单
	 * @return 支付转化率
	 */
	public String getAvgConvert(final Long payedQuantity, final Long orderQuantity) {
		//获取统计转化率
		if (payedQuantity != null || orderQuantity != null) {
			//获取统计支付转化率
			if (orderQuantity == 0 || payedQuantity == null || orderQuantity == null) {
				return "0.0%";
			} else {
				String str = (payedQuantity * 100.0 / orderQuantity) + "";
				str = str.substring(0, str.indexOf(".") + 2);
				return str + "%";
			}
		}
		return "0.0%";
	}

}
