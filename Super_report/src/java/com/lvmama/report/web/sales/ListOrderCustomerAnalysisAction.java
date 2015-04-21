package com.lvmama.report.web.sales;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.service.perm.UserOrgAuditPermService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.report.po.OrderCustomerBasicMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

public class ListOrderCustomerAnalysisAction extends BaseAction{
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -388380415133787579L;
	/**
	 * 后台用户权限逻辑接口
	 */
	private UserOrgAuditPermService userOrgAuditPermServiceProxy = (UserOrgAuditPermService)SpringBeanProxy.getBean("userOrgAuditPermService");
	/**
	 * 报表逻辑接口
	 */
	private ReportService reportService;
	/**
	 * 数据记录列表
	 */
	private List<OrderCustomerBasicMV> analysisList = new ArrayList<OrderCustomerBasicMV>();
	/**
	 * 页面查询条件
	 */
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	
	private static final DecimalFormat profitDF = new DecimalFormat("0.000"); 
	private static final DecimalFormat financialDF = new DecimalFormat("###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###.00"); 
	/**
	 * /销售总额
	 */
	private String sunAmount = "0";
	/**
	 * 订单总数
	 */
	private Long countOfOrder = 0L;
	/**
	 * 毛利润
	 */
	private String sumProfit = "0";
	/**
	 * 毛利率
	 */
	private String sumProfitPer;
	
	/**
	 * 查询操作
	 * */
	public void doQuery() throws Exception{
		double tmp = 0;
		
		Map<String, Object> param = this.createMap();
//		param.put("managerIds", userOrgAuditPermServiceProxy.getParamManagerIdsByRoleId(getSessionUser(), Constant.PERM_ROLE_PROD_MANAGER));
		
		//获取订单总数
		countOfOrder = reportService.countOrderCustomerBasicMVByTime(param);
		initialPageInfoByMap(countOfOrder, param);
		
		//依据条件获取查询记录
		analysisList = reportService.queryOrderCustomerBasicMVByTime(param,false);
		
		//订单销售总额
		Long amountDouble = reportService.sumAmountOrderCustomerBasicMV(param);
		if(amountDouble != null){
			sunAmount = financialDF.format(amountDouble/100);
		} else {
			sunAmount = financialDF.format(0);
		}
		
		//订单产品总利润
		Long profitDouble =  reportService.sumProfitOrderCustomerBasicMV(param);
		if(profitDouble != null) {
			sumProfit = financialDF.format(profitDouble/100);
		} else {
			sumProfit = financialDF.format(0);
		}
		
		//订单总利率
		if(amountDouble != null && profitDouble != null){
			tmp =(profitDouble*100.0)/amountDouble;
			sumProfitPer = profitDF.format(tmp) + "%";
		} else {
			sumProfitPer = profitDF.format(0) + "%";
		}
	}
	
	/**
	 * 导出数据
	 * */
	public void doExport() throws Exception{
		Map<String, Object> param = this.createMap();
		//param.put("managerIds", userOrgAuditPermServiceProxy.getParamManagerIdsByRoleId(getSessionUser(), Constant.PERM_ROLE_PROD_MANAGER));
		
		analysisList = reportService.queryOrderCustomerBasicMVByTime(param,true);
		Map beans = new HashMap();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/orderCustomerAnalysis.xls");
	}
	
	/**
	 * 配置searchConds查询条件
	 * */
	public Map<String, Object> createMap(){
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("createStartDate", searchConds.get("createStartDate"));
		param.put("createEndDate", searchConds.get("createEndDate"));
		param.put("payStartDate", searchConds.get("payStartDate"));
		param.put("payEndDate", searchConds.get("payEndDate"));
		param.put("visitStartDate", searchConds.get("visitStartDate"));
		param.put("visitEndDate", searchConds.get("visitEndDate"));

		param.put("prodName", searchConds.get("prodName"));
		param.put("birthland", searchConds.get("birthland"));
		param.put("dest", searchConds.get("dest"));
		
		if (!StringUtils.isEmpty((String) searchConds.get("capitalName"))) {
			param.put("capitalName", searchConds.get("capitalName"));
		}
		if (!StringUtils.isEmpty((String) searchConds.get("prodProductId"))) {
			param.put("prodProductId", searchConds.get("prodProductId"));
		}
		if (!StringUtils.isEmpty((String) searchConds.get("realName"))) {
			param.put("realName", searchConds.get("realName"));
		}
		if (!StringUtils.isEmpty((String) searchConds.get("filialeName"))) {
			param.put("filialeName", searchConds.get("filialeName"));
		}
		if (!StringUtils.isEmpty((String) searchConds.get("orderId"))) {
			param.put("orderId", searchConds.get("orderId"));
		}
		
		//获取SUB_PRODUCT_TYPE 查询条件
		String sqlString = getSubProductTypeParam();
		if(!"".equals(sqlString)){
			param.put("sql", sqlString);
		}
		
		return param;
	}
	
	/**
	 * 获取SUB_PRODUCT_TYPE 查询条件
	 * */
	public String getSubProductTypeParam(){
		String sqlString = "";
		
		if (searchConds.get("sTicket")!=null) {
			if((Boolean)searchConds.get("sTicket")){
				if(!"".equals(sqlString)){
					sqlString+=" or (SUB_PRODUCT_TYPE='SINGLE' or SUB_PRODUCT_TYPE='SUIT' or SUB_PRODUCT_TYPE='UNION' or SUB_PRODUCT_TYPE='WHOLE')";
				}else{
					sqlString+="(SUB_PRODUCT_TYPE='SINGLE' or SUB_PRODUCT_TYPE='SUIT' or SUB_PRODUCT_TYPE='UNION' or SUB_PRODUCT_TYPE='WHOLE')";
				}
				
			}
		}
		if (searchConds.get("sHotel")!=null) {
			if((Boolean)searchConds.get("sHotel")){
				if(!"".equals(sqlString)){
					sqlString+=" or (SUB_PRODUCT_TYPE='SINGLE_ROOM' or SUB_PRODUCT_TYPE='HOTEL' or SUB_PRODUCT_TYPE='HOTEL_SUIT')";
				}else{
					sqlString+="(SUB_PRODUCT_TYPE='SINGLE_ROOM' or SUB_PRODUCT_TYPE='HOTEL' or SUB_PRODUCT_TYPE='HOTEL_SUIT')";
				}
			}
		}
		
		if (searchConds.get("sGroup")!=null) {
			if((Boolean)searchConds.get("sGroup")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='GROUP'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='GROUP'";
				}
			}
		}
		
		if (searchConds.get("sGroupLong")!=null) {
			if((Boolean)searchConds.get("sGroupLong")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='GROUP_LONG'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='GROUP_LONG'";
				}
			}
		}		
		
		if (searchConds.get("sGroupForeign")!=null) {
			if((Boolean)searchConds.get("sGroupForeign")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='GROUP_FOREIGN'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='GROUP_FOREIGN'";
				}
			}
		}
		
		if (searchConds.get("sFree")!=null) {
			if((Boolean)searchConds.get("sFree")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='FREENESS'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='FREENESS'";
				}
			}
		}
		
		if (searchConds.get("sFreeLong")!=null) {
			if((Boolean)searchConds.get("sFreeLong")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='FREENESS_LONG'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='FREENESS_LONG'";
				}
			}
		}		
		
		if (searchConds.get("sFreeForeign")!=null) {
			if((Boolean)searchConds.get("sFreeForeign")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='FREENESS_FOREIGN'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='FREENESS_FOREIGN'";
				}
			}
		}
		
		if (searchConds.get("sSelfhelpBus")!=null) {
			if((Boolean)searchConds.get("sSelfhelpBus")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='SELFHELP_BUS'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='SELFHELP_BUS'";
				}
			}
		}
		
		if (searchConds.get("sInsurance")!=null) {
			if((Boolean)searchConds.get("sInsurance")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='INSURANCE'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='INSURANCE'";
				}
			}
		}
		
		if (searchConds.get("sFangCha")!=null) {
			if((Boolean)searchConds.get("sFangCha")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='FANGCHA'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='FANGCHA'";
				}
			}
		}
		
		if (searchConds.get("sOther")!=null) {
			if((Boolean)searchConds.get("sOther")){
				if(!"".equals(sqlString)){
					sqlString+=" or SUB_PRODUCT_TYPE='OTHER'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='OTHER'";
				}
			}
		}
		
		return sqlString;
	}
	
	@SuppressWarnings("rawtypes")
	public List getAnalysisList() {
		return analysisList;
	}
	public void setAnalysisList(List AnalysisList) {
		this.analysisList = analysisList;
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

	public void setProfitPer(String profitPer) {
		this.sumProfitPer = profitPer;
	}

	public void setUserOrgAuditPermServiceProxy(UserOrgAuditPermService userOrgAuditPermServiceProxy) {
		this.userOrgAuditPermServiceProxy = userOrgAuditPermServiceProxy;
	}

	public String getSunAmount() {
		return sunAmount;
	}

	public Long getCountOfOrder() {
		return countOfOrder;
	}

	public String getSumProfit() {
		return sumProfit;
	}

	public String getSumProfitPer() {
		return sumProfitPer;
	}

}
