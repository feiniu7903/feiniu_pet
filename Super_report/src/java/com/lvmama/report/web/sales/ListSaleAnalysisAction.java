package com.lvmama.report.web.sales;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Listheader;

import com.lvmama.comm.pet.service.perm.UserOrgAuditPermService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.utils.ZkMessage;
import com.lvmama.report.web.BaseAction;

public class ListSaleAnalysisAction extends BaseAction{
	private static final long serialVersionUID = -7418572575073908772L;
	private static final DecimalFormat profitDF = new DecimalFormat("0.000"); 
	private static final DecimalFormat financialDF = new DecimalFormat("###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###.00"); 
	private List analysisList = new ArrayList();
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	private ReportService reportService;
	private UserOrgAuditPermService userOrgAuditPermServiceProxy = (UserOrgAuditPermService)SpringBeanProxy.getBean("userOrgAuditPermService");
	private String amount="0";
	private String profit="0";
	private String profitPer;
	/**
	 *  订单总数
	 */
	private Long sumOrderQuantity = 0L;
	/**
	 * 销量总数
	 */
	private Long sumAmountQuantity = 0L;
	/**
	 * 排序参数
	 */
	private Map<String, String> sortParam;
	
	
	public void doBefore() throws Exception {
		sortParam = new HashMap<String, String>();
		sortParam.put("sortTypeName", "quantity");
		sortParam.put("sortAscOrDesc", "desc");
	}
	
	public boolean validateSearchCondsAboutDate() {
		int i = 0;
		i += (null != searchConds.get("startDate") || null != searchConds.get("endDate")) ? 1 : 0;
		i += (null != searchConds.get("createdTimeStartDate") || null != searchConds.get("createdTimeEndDate")) ? 1 : 0;
		i += (null != searchConds.get("visitTimeStartDate") || null != searchConds.get("visitTimeEndDate")) ? 1 : 0;
		return i < 2;
	}
	
	public void doQuery() throws Exception{
		double tmp = 0;
		
		if ( !validateSearchCondsAboutDate() ) {
			ZkMessage.showError("订单创建时间，订单游玩时间和订单支付完成时间只能选择其一!");
			return;
		}
		
		Map<String, Object> param = this.createMap();
		//param.put("managerIds", userOrgAuditPermServiceProxy.getParamManagerIdsByRoleId(getSessionUser(), Constant.PERM_ROLE_PROD_MANAGER));
		
		initialPageInfoByMap(reportService.countProdProductBasicMVByTime(param), param);
		analysisList = reportService.queryProdProductBasicMVByTime(param,false);
		Long amountDouble=reportService.sumAmontProdProductBasicMV(param);
		if(amountDouble!=null){
			amount=financialDF.format(amountDouble/100);
		} else {
			amount=financialDF.format(0);
		}
		Long profitDouble=reportService.sumPerfitProdProductBasicMV(param);
		if(profitDouble!=null){
			profit=financialDF.format(profitDouble/100);
		} else {
			profit=financialDF.format(0);
		}
		if(amountDouble!=null&&profitDouble!=null){
			tmp=(profitDouble*100.0)/amountDouble;
			profitPer=profitDF.format(tmp)+"%";
		} else {
			profitPer=profitDF.format(0)+"%";
		}
		sumOrderQuantity = reportService.sumOrderQuantityProdProductBasicMV(param);
		sumAmountQuantity = reportService.sumAmountQuantityProdProductBasicMV(param);
	}
	
	public void doExport() throws Exception{
		if ( !validateSearchCondsAboutDate() ) {
			ZkMessage.showError("订单创建时间，订单游玩时间和订单支付完成时间只能选择其一!");
			return;
		}
		
		
		Map<String, Object> param = this.createMap();
		
		analysisList = reportService.queryProdProductBasicMVByTime(param,true);
		Map beans = new HashMap();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/saleAnalysis.xls");
	}
	
	public void doSort(Map<String,Object> param) {
		((Listheader) this.getComponent().getFellow("quantity")).setLabel("销量");
		((Listheader) this.getComponent().getFellow("order_quantity")).setLabel("订单数");
		((Listheader) this.getComponent().getFellow("amout")).setLabel("销量总金额");
		((Listheader) this.getComponent().getFellow("profit")).setLabel("毛利润");
		((Listheader) this.getComponent().getFellow("profitPer")).setLabel("毛利率");
		if ("quantity".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("quantity")).setLabel("销量△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("quantity")).setLabel("销量▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "quantity");
				((Listheader) this.getComponent().getFellow("quantity")).setLabel("销量▽");
			}		
		}
		if ("order_quantity".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("order_quantity")).setLabel("订单数△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("order_quantity")).setLabel("订单数▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "order_quantity");
				((Listheader) this.getComponent().getFellow("order_quantity")).setLabel("订单数▽");
			}		
		}
		if ("amount".equals(param.get("sortType"))) {
			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("amout")).setLabel("销量总金额△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("amout")).setLabel("销量总金额▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "amount");
				((Listheader) this.getComponent().getFellow("amout")).setLabel("销量总金额▽");
			}		
		}
		if ("profit".equals(param.get("sortType"))) {
			if ("profit".equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("profit")).setLabel("毛利润△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("profit")).setLabel("毛利润▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "profit");
				((Listheader) this.getComponent().getFellow("profit")).setLabel("毛利润▽");
			}		
		}
		if ("profitPer".equals(param.get("sortType"))) {
			if ("profit*100.0/amount".equals(sortParam.get("sortTypeName"))) {
				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
					sortParam.put("sortAscOrDesc", "asc");
					((Listheader) this.getComponent().getFellow("profitPer")).setLabel("毛利率△");
				} else {
					sortParam.put("sortAscOrDesc", "desc");
					((Listheader) this.getComponent().getFellow("profitPer")).setLabel("毛利率▽");
				}
			} else {
				sortParam.put("sortAscOrDesc", "desc");
				sortParam.put("sortTypeName", "profit*100.0/amount");
				((Listheader) this.getComponent().getFellow("profitPer")).setLabel("毛利率▽");
			}		
		}		
		refreshComponent("search");
	}
	
	public Map<String, Object> createMap(){
		String sqlString = "";
		Map<String, Object> param = new HashMap<String, Object>();
		param.putAll(sortParam);
		param.put("managerIds", userOrgAuditPermServiceProxy.getParamManagerIdsByRoleId(getSessionUser(), Constant.PERM_ROLE_PROD_MANAGER));
		
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
		
		if (null != searchConds.get("startDate") || null != searchConds.get("startDate")) {
			param.put("startDate", searchConds.get("startDate"));
			param.put("endDate", searchConds.get("endDate"));
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		
		if (null != searchConds.get("createdTimeStartDate") || null != searchConds.get("createdTimeStartDate")) {
			param.put("startDate", searchConds.get("createdTimeStartDate"));
			param.put("endDate", searchConds.get("createdTimeEndDate"));
			param.put("tableName", "PROD_PRODUCT_BASIC_CREATE_MV");
		}
		
		if (null != searchConds.get("visitTimeStartDate") || null != searchConds.get("visitTimeStartDate")) {
			param.put("startDate", searchConds.get("visitTimeStartDate"));
			param.put("endDate", searchConds.get("visitTimeEndDate"));
			param.put("tableName", "PROD_PRODUCT_BASIC_VISIT_MV");
		}

		param.put("prodName", searchConds.get("prodName"));
		param.put("birthland", searchConds.get("birthland"));
		param.put("dest", searchConds.get("dest"));
		param.put("prod_product_id", searchConds.get("prod_product_id"));
		if (!StringUtils.isEmpty((String) searchConds.get("realName"))) {
			param.put("realName", searchConds.get("realName"));
		}
		if (!StringUtils.isEmpty((String) searchConds.get("filialeName"))) {
			param.put("filialeName", searchConds.get("filialeName"));
		}
		if(!"".equals(sqlString)){
			param.put("sql", sqlString);
		}
		
		return param;
	}
	
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

	public String getAmount() {
		return amount;
	}

	public String getProfit() {
		return profit;
	}

	public String getProfitPer() {
		return profitPer;
	}

	public void setProfitPer(String profitPer) {
		this.profitPer = profitPer;
	}

	public void setUserOrgAuditPermServiceProxy(UserOrgAuditPermService userOrgAuditPermServiceProxy) {
		this.userOrgAuditPermServiceProxy = userOrgAuditPermServiceProxy;
	}

	public Long getSumOrderQuantity() {
		return sumOrderQuantity;
	}

	public Long getSumAmountQuantity() {
		return sumAmountQuantity;
	}

}
