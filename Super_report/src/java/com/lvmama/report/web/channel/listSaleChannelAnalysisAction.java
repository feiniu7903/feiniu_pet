package com.lvmama.report.web.channel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

public class listSaleChannelAnalysisAction extends BaseAction{
	
	private List analysisList = new ArrayList();
	private List<CodeItem> channelList = new ArrayList<CodeItem>();
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	private ReportService reportService;
	private String amount;
	private Long profit;
	private String profitPer;
	
	public void doBefore() throws Exception{
		this.channelList.add(new CodeItem("","全部"));
		this.channelList.add(new CodeItem("TAOBAL","淘宝"));
		this.channelList.add(new CodeItem("OTHER","其它分销"));
		this.channelList.add(new CodeItem("SERVICESTATION","服务站"));
		this.channelList.add(new CodeItem("BACKEND","驴妈妈后台"));
		this.channelList.add(new CodeItem("FRONTEND","驴妈妈前台"));
		this.channelList.add(new CodeItem("TENCENT","腾讯电子钱包"));
		this.channelList.add(new CodeItem("WAP","WAP"));
		this.channelList.add(new CodeItem("CTRIP","携程"));
		

	}
	
	public void doQuery() throws Exception{
		double tmp = 0;
		double tmpAmount = 0;
		DecimalFormat df = new DecimalFormat("0.000"); 
		
		Map<String, Object> param = this.createMap();
		initialPageInfoByMap(reportService.countChannelBasicMV(param,false), param);
		analysisList = reportService.queryChannelBasicMV(param,true);
		
		Long flag = reportService.sumAmountChannelBasicMV(param);
		if(flag!=null){
			tmpAmount=flag/100;
			amount=df.format(tmpAmount);
		}else{
			amount="0";
		}
		profit = reportService.sumProfitChannelBasicMV(param);
		if(tmpAmount!=0 && profit!=null){
			tmp = profit/tmpAmount;
		}
		profitPer = df.format(tmp)+"%";
		
		if(profit!=null){
			profit=profit/100;
		}
	}
	
	public void doExport() throws Exception{
		Map<String, Object> param = this.createMap();
		analysisList = reportService.queryChannelBasicMV(param,true);
		Map beans = new HashMap();
		beans.put("excelList", analysisList);
		doExcel(beans, "/WEB-INF/resources/template/saleChannelAnalysis.xls");
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
		param.put("channel", searchConds.get("channel"));
		if(!"".equals(sqlString)){
			param.put("sql", sqlString);
		}
		
		return param;
		
	}
	
	public void changeChannelStatus(String channel){
		if("".equals(channel)){
			this.searchConds.remove("channel");
		}else{
			searchConds.put("channel", channel);
		}
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
	public List<CodeItem> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
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

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getProfit() {
		return profit;
	}

	public void setProfit(Long profit) {
		this.profit = profit;
	}

	public String getProfitPer() {
		return profitPer;
	}

	public void setProfitPer(String profitPer) {
		this.profitPer = profitPer;
	}
}
