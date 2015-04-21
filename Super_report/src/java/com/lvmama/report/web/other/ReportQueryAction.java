package com.lvmama.report.web.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.zkoss.zul.Filedownload;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.report.service.RptQueryService;
import com.lvmama.report.utils.ExcelExportUtil;
import com.lvmama.report.web.BaseAction;

public class ReportQueryAction extends BaseAction{
	
	private static final Log log = LogFactory.getLog(ReportQueryAction.class);
	private List<Map> dataList; 
	
	RptQueryService rptQueryService;
	
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOptions=new HashMap<String, Object>();
	private Integer totalRowCount;

	/**
	 * 界面上点击查询按钮
	 * @throws Exception
	 */
	public void doSearch() throws Exception{
		totalRowCount=rptQueryService.count(queryOptions);
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		//_paging.setTotalSize(totalRowCount.intValue());
	}
	
	public void doExport() throws IOException {
		Resource resource = new ClassPathResource("order_report.sql");
		ExcelExportUtil util = new ExcelExportUtil(super.getSessionUserName(), buildQueryStatement(), resource.getFile().getAbsolutePath());
		File resultFile = util.create();
		if (resultFile!=null) {
			log.info("export report to :" + resultFile.getAbsolutePath());
			FileInputStream fis = new FileInputStream(resultFile.getAbsolutePath());
			byte[] data =  new byte[(int)resultFile.length()];
			fis.read(data);
			fis.close();
			resultFile.delete();
			String theTime = String.valueOf(System.currentTimeMillis());
			Filedownload.save(data, "text/csv", "report_"+super.getSessionUserName()+ "_" +theTime+".csv");
		}
	}
	
	private String buildQueryStatement() {
		String queryStatement = "where 1=1 ";
		if (queryOptions.get("orderId")!=null && !queryOptions.get("orderId").toString().equals("")) {
			queryStatement += "and ORDER_ID='"+queryOptions.get("orderId").toString()+"' ";
		}

		if (queryOptions.get("visitTimeB")!=null) {
			String time= DateUtil.getDateTime("yyyy-MM-dd", (Date)queryOptions.get("visitTimeB"));
			queryStatement += "and VISIT_TIME>=to_date('"+time+"','yyyy-MM-dd') ";
		}
		if (queryOptions.get("visitTimeE")!=null && !queryOptions.get("visitTimeE").toString().equals("")) {
			String time= DateUtil.getDateTime("yyyy-MM-dd", (Date)queryOptions.get("visitTimeE"));
			queryStatement += "and VISIT_TIME<to_date('"+time+"','yyyy-MM-dd')+1 ";
		}
		
		if (queryOptions.get("orderStatus")!=null && !queryOptions.get("orderStatus").toString().equals("")) {
			queryStatement += "and ORDER_STATUS='"+queryOptions.get("orderStatus").toString()+"' ";
		}
		
		if (queryOptions.get("createTimeB")!=null && !queryOptions.get("createTimeB").toString().equals("")) {
			String time= DateUtil.getDateTime("yyyy-MM-dd", (Date)queryOptions.get("createTimeB"));
			queryStatement += "and CREATE_TIME>=to_date('"+time+"','yyyy-MM-dd') ";
		}
		if (queryOptions.get("createTimeE")!=null && !queryOptions.get("createTimeE").toString().equals("")) {
			String time= DateUtil.getDateTime("yyyy-MM-dd", (Date)queryOptions.get("createTimeE"));
			queryStatement += "and CREATE_TIME<to_date('"+time+"','yyyy-MM-dd')+1 ";
		}
		
		if (queryOptions.get("payTimeB")!=null && !queryOptions.get("payTimeB").toString().equals("")) {
			String time= DateUtil.getDateTime("yyyy-MM-dd", (Date)queryOptions.get("payTimeB"));
			queryStatement += "and PAYMENT_TIME>=to_date('"+time+"','yyyy-MM-dd') ";
		}
		if (queryOptions.get("payTimeE")!=null && !queryOptions.get("payTimeE").toString().equals("")) {
			String time= DateUtil.getDateTime("yyyy-MM-dd", (Date)queryOptions.get("payTimeE"));
			queryStatement += "and PAYMENT_TIME<to_date('"+time+"','yyyy-MM-dd')+1 ";
		}
		
		if (queryOptions.get("paymentStatus")!=null && !queryOptions.get("paymentStatus").toString().equals("")) {
			queryStatement += "and PAYMENT_STATUS='"+queryOptions.get("paymentStatus").toString()+"' ";
		}
		
		if (queryOptions.get("paymentTarget")!=null && !queryOptions.get("paymentTarget").toString().equals("")) {
			queryStatement += "and PAYMENT_TARGET='"+queryOptions.get("paymentTarget").toString()+"' ";
		}
		if (queryOptions.get("metaProductName")!=null && !queryOptions.get("metaProductName").toString().equals("")) {
			queryStatement += "and meta_product_name like '%"+queryOptions.get("metaProductName").toString()+"%' ";
		}
		if (queryOptions.get("productName")!=null && !queryOptions.get("productName").toString().equals("")) {
			queryStatement += "and product_name like '%"+queryOptions.get("productName").toString()+"%' ";
		}
		if (queryOptions.get("productId")!=null && !queryOptions.get("productId").toString().equals("")) {
			queryStatement += "and product_id = "+queryOptions.get("productId").toString()+" ";
		}
		if (queryOptions.get("metaProductId")!=null && !queryOptions.get("metaProductId").toString().equals("")) {
			queryStatement += "and meta_product_id = "+queryOptions.get("metaProductId").toString()+" ";
		}
		if (queryOptions.get("supplierId")!=null && !queryOptions.get("supplierId").toString().equals("")) {
			queryStatement += "and supplier_id = "+queryOptions.get("supplierId").toString()+" ";
		}
		if (queryOptions.get("travelGroupCode")!=null && !queryOptions.get("travelGroupCode").toString().equals("")) {
			queryStatement += "and travel_group_code like '%"+queryOptions.get("travelGroupCode").toString()+"' ";
		}
		if (queryOptions.get("filialeName")!=null && !queryOptions.get("filialeName").toString().equals("")) {
			queryStatement += "and filiale_name = '"+queryOptions.get("filialeName").toString()+"' ";
		}
		if (queryOptions.get("channelCode")!=null && !queryOptions.get("channelCode").toString().equals("")) {
			queryStatement += "and channel = '"+queryOptions.get("channelCode").toString()+"' ";
		}
		
		if (queryOptions.get("physical")!=null && queryOptions.get("physical").toString().equals("true")){
			queryStatement +="and physical = 'true'";
		}
		
		queryStatement+=";";
		return queryStatement;
	}
	
	public void selectPhysical(Boolean v){
		if(v!=null&&v){
			queryOptions.put("physical", "true");
		}else if(queryOptions.containsKey("physical")){
			queryOptions.remove("physical");
		}
	}
	
	public List<Map> getDataList() {
		return dataList;
	}

	public Map<String, Object> getQueryOptions() {
		return queryOptions;
	}

	public void setRptQueryService(RptQueryService rptQueryService) {
		this.rptQueryService = rptQueryService;
	}

}
