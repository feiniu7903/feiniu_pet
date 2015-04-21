package com.lvmama.report.web.sales;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.report.po.ProdProductBasicMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

/**
 * 
 * 目的地排行
 * 
 * @author luoyinqi
 *
 */
public class listTopAction extends BaseAction{

	private List<ProdProductBasicMV> topList = new ArrayList<ProdProductBasicMV>();
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	private ReportService reportService;
	private Long amount=0l;
	private Long saleDest=0l;
	private Long saleProd=0l;
	private Long perfit=0l;
	private String perfitPer;
	private Long Prod=0l;
	private Long dest=0l;
	private Long online=0l;
	
	public void doBefore() throws Exception{
		dest=reportService.sumDestProdProductBasicMV();
		Prod=reportService.sumProdProductBasicMV();
		online = reportService.sumProdProductOnlineBasicMV();
	}
	
	public void search() throws Exception{
		if (StringUtils.isEmpty((String)searchConds.get("filialeName"))) {
			searchConds.remove("filialeName");
		}
		double tmp=0;
		DecimalFormat df = new DecimalFormat("0.000"); 
		initialPageInfoByMap(reportService.countProdProductBasicMVByTime(searchConds), searchConds);
		topList = reportService.queryProdProductBasicMVByTime(searchConds,false);
		
		amount=reportService.sumAmontProdProductBasicMV(searchConds);
		if(amount!=null){
			amount=amount/100;
		}
		saleDest=reportService.sumDestAmontProdProductBasicMV(searchConds);
		saleProd=reportService.sumProdAmontProdProductBasicMV(searchConds);
		perfit=reportService.sumPerfitProdProductBasicMV(searchConds);
		if(perfit!=null){
			perfit=perfit/100;
		}
		if(amount!=null&&perfit!=null){
			tmp=(perfit*100.0)/amount;
		}
		perfitPer = df.format(tmp);
		
	}

	public void doExportSale() throws Exception{
		searchConds.put("_endRow", null);
		topList = reportService.queryProdProductBasicMVByTime(searchConds,true);
		Map beans = new HashMap();
		beans.put("excelList", topList);
		doExcel(beans, "/WEB-INF/resources/template/topListSale.xls");
	}
	
	public void doExportPlace() throws Exception{
		searchConds.put("_endRow", null);
		topList = reportService.queryProdProductBasicMVByTime(searchConds,true);
		Map beans = new HashMap();
		beans.put("excelList", topList);
		doExcel(beans, "/WEB-INF/resources/template/topListPlace.xls");
	}
	public List<ProdProductBasicMV> getTopList() {
		return topList;
	}

	public void setTopList(List<ProdProductBasicMV> topList) {
		this.topList = topList;
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
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getDest() {
		return dest;
	}

	public void setDest(Long dest) {
		this.dest = dest;
	}

	public Long getSaleDest() {
		return saleDest;
	}

	public void setSaleDest(Long saleDest) {
		this.saleDest = saleDest;
	}

	public Long getSaleProd() {
		return saleProd;
	}

	public void setSaleProd(Long saleProd) {
		this.saleProd = saleProd;
	}

	public Long getProd() {
		return Prod;
	}

	public void setProd(Long prod) {
		Prod = prod;
	}
	
	public Long getPerfit() {
		return perfit;
	}

	public void setPerfit(Long perfit) {
		this.perfit = perfit;
	}

	public String getPerfitPer() {
		return perfitPer;
	}

	public void setPerfitPer(String perfitPer) {
		this.perfitPer = perfitPer;
	}

	public Long getOnline() {
		return online;
	}

	public void setOnline(Long online) {
		this.online = online;
	}

}
