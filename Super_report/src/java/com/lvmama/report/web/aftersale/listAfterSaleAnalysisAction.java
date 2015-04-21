package com.lvmama.report.web.aftersale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.report.po.OrderSaleServiceBasicMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

public class listAfterSaleAnalysisAction extends BaseAction{
	
	private List<OrderSaleServiceBasicMV> analysisList = new ArrayList<OrderSaleServiceBasicMV>();
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	private ReportService reportService;
	private Long orderSeviceNum;
	private Long refundAmount;
	private Long compensationAmount;
	
	public void doBefore() throws Exception{
	}
	
	public void doQuery() throws Exception{
		Map<String, Object> param = this.createMap();
		initialPageInfoByMap(reportService.countOrderSaleServiceBasicMV(param), param);
		analysisList = reportService.queryOrderSaleServiceBasicMV(param,false);
		
		orderSeviceNum = reportService.countOrderSaleServiceBasicMV(param);
		refundAmount = reportService.sumRefundAmountOrderSaleServiceBasicMV(param);
		if(refundAmount!=null){
			refundAmount=refundAmount/100;
		}
		compensationAmount = reportService.sumCompensationAmountOrderSaleServiceBasicMV(param);
		if(compensationAmount!=null){
			compensationAmount=compensationAmount/100;
		}
	}
	
	public void doExport() throws Exception{
		Map<String, Object> param = this.createMap();
		analysisList = reportService.queryOrderSaleServiceBasicMV(param,true);
		Map beans = new HashMap();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/saleServiceAnalysis.xls");
	}
	
	public Map createMap(){
		String sqlString = "";
		Map<String, Object> param = new HashMap<String, Object>();
		if (searchConds.get("sTicket")!=null) {
			if((Boolean)searchConds.get("sTicket")){
				if((Boolean)searchConds.get("sTicket")){
					if(!"".equals(sqlString)){
						sqlString+="or (SUB_PRODUCT_TYPE='SINGLE' or SUB_PRODUCT_TYPE='SUIT' or SUB_PRODUCT_TYPE='UNION' or SUB_PRODUCT_TYPE='WHOLE')";
					}else{
						sqlString+="(SUB_PRODUCT_TYPE='SINGLE' or SUB_PRODUCT_TYPE='SUIT' or SUB_PRODUCT_TYPE='UNION' or SUB_PRODUCT_TYPE='WHOLE')";
					}
					
				}
				
			}
		}
		if (searchConds.get("sHotel")!=null) {
			if((Boolean)searchConds.get("sHotel")){
				if(!"".equals(sqlString)){
					sqlString+="or SUB_PRODUCT_TYPE='HOTEL'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='HOTEL'";
				}
			}
		}
		if (searchConds.get("sGroupForeign")!=null) {
			if((Boolean)searchConds.get("sGroupForeign")){
				if(!"".equals(sqlString)){
					sqlString+="or SUB_PRODUCT_TYPE='GROUP_FOREIGN'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='GROUP_FOREIGN'";
				}
			}
		}
		if (searchConds.get("sFreeForeign")!=null) {
			if((Boolean)searchConds.get("sFreeForeign")){
				if(!"".equals(sqlString)){
					sqlString+="or SUB_PRODUCT_TYPE='FREENESS_FOREIGN'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='FREENESS_FOREIGN'";
				}
			}
		}
		
		if (searchConds.get("sGroup")!=null) {

			if((Boolean)searchConds.get("sGroup")){
				if(!"".equals(sqlString)){
					sqlString+="or SUB_PRODUCT_TYPE='GROUP'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='GROUP'";
				}
			}
		}
		if (searchConds.get("sFree")!=null) {
			if((Boolean)searchConds.get("sFree")){
				if(!"".equals(sqlString)){
					sqlString+="or SUB_PRODUCT_TYPE='FREENESS'";
				}else{
					sqlString+="SUB_PRODUCT_TYPE='FREENESS'";
				}
			}
		}
		
		param.put("startDate", searchConds.get("startDate"));
		param.put("endDate", searchConds.get("endDate"));
		param.put("prodName", searchConds.get("prodName"));
		if(!"".equals(sqlString)){
			param.put("sql", sqlString);
			System.out.println(sqlString);
		}
		
		return param;
	}
	public List<OrderSaleServiceBasicMV> getAnalysisList() {
		return analysisList;
	}
	public void setAnalysisList(List<OrderSaleServiceBasicMV> AnalysisList) {
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

	public Long getOrderSeviceNum() {
		return orderSeviceNum;
	}

	public void setOrderSeviceNum(Long orderSeviceNum) {
		this.orderSeviceNum = orderSeviceNum;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getCompensationAmount() {
		return compensationAmount;
	}

	public void setCompensationAmount(Long compensationAmount) {
		this.compensationAmount = compensationAmount;
	}

}
