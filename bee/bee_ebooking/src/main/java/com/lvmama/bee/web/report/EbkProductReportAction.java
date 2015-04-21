package com.lvmama.bee.web.report;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.service.ebooking.EbkProductReportService;
import com.lvmama.comm.bee.vo.report.EbkProductVisitorReportVo;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ExcelUtils;

@Results(value={
		@Result(name="onSaleProductList",location="/WEB-INF/pages/report/product/onSaleProductList.jsp"),
		@Result(name="productSalesList",location="/WEB-INF/pages/report/product/productSalesList.jsp"),
		@Result(name="productVisitorList",location="/WEB-INF/pages/report/product/productVisitorList.jsp")
	})
public class EbkProductReportAction extends EbkBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4324207484402778323L;
	
	private String prodProductName;
	private String subProductType;
	private String metaProductName;
	private String placeName;
	private String onlineTimeStart;
	private String onlineTimeEnd;
	private String offlineTimeStart;
	private String offlineTimeEnd;
	private String prodProductId;
	private String manager;
	private String visitTimeStart;
	private String visitTimeEnd;
	private Long   orderId;
	private String payStatus;

	private Page<EbkProductVisitorReportVo> reportPage = new Page<EbkProductVisitorReportVo>();
	private EbkProductReportService ebkProductReportService;
	
	private Map<String, Object> initParam() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		if(!StringUtils.isEmpty(onlineTimeEnd)) {
			params.put("onlineTimeEnd", onlineTimeEnd+" 23:59:59");
		}
		if(!StringUtils.isEmpty(offlineTimeEnd)) {
			params.put("offlineTimeEnd", offlineTimeEnd+" 23:59:59");
		}
		if(StringUtils.isEmpty(visitTimeEnd) && StringUtils.isEmpty(visitTimeStart)) {
			visitTimeEnd = DateUtil.getFormatDate(new Date(), "yyyy-MM-dd");
			visitTimeStart = DateUtil.getFormatDate(new Date(), "yyyy-MM-dd");
		}
		if(!StringUtils.isEmpty(visitTimeEnd)) {
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		try {
			if(prodProductName != null)
				this.prodProductName = new String(java.net.URLDecoder.decode(prodProductName, "utf-8").getBytes("iso-8859-1"),"utf-8");
			if(metaProductName != null)
				this.metaProductName = new String(java.net.URLDecoder.decode(metaProductName, "utf-8").getBytes("iso-8859-1"),"utf-8");
			if(placeName != null)
				this.placeName = new String(java.net.URLDecoder.decode(placeName, "utf-8").getBytes("iso-8859-1"),"utf-8");
			if(manager != null)
				this.manager = new String(java.net.URLDecoder.decode(manager, "utf-8").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		if(prodProductId != null) {
			List<String> ids = new ArrayList<String>();
			for(String id : prodProductId.split("\\D+")) {
				if(!StringUtils.isEmpty(id)) {
					ids.add(id);
				}
			}
			if(ids.size() > 0) params.put("prodProductIds",ids);
		}
		params.put("prodProductName",prodProductName);
		params.put("subProductType",subProductType);
		params.put("metaProductName",metaProductName);
		params.put("placeName",placeName);
		params.put("onlineTimeStart",onlineTimeStart);
		params.put("offlineTimeStart",offlineTimeStart);
		params.put("manager",manager);
		params.put("visitTimeStart",visitTimeStart);
		params.put("orderId",orderId);
		params.put("payStatus",payStatus);

		return params;
	}
	private void initPageParam(Map<String, Object> params) {
		reportPage.setCurrentPage(page);
		params.put("start", reportPage.getStartRows());
		params.put("end", reportPage.getEndRows());
	}
	@Action("/report/product/downOnSaleProductList")
	public void downOnSaleProductList() {
		Map<String, Object> params = initParam();
		// 分页
		params.put("start", 1);
		params.put("end", 65535);
		reportPage.setItems(ebkProductReportService.findOnSaleProductList(params));
		try {
			String fileName = ExcelUtils.writeXlsFile(reportPage.getItems(), "/WEB-INF/resources/template/onSaleProductTemplate.xls");
			this.writeAttachment(fileName, "LvmamaProduct_"+this.getCurrentSupplierId()+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/report/product/onSaleProductList")
	public String onSaleProductList() {
		Map<String, Object> params = initParam();
		int count = ebkProductReportService.findOnSaleProductCount(params);
		reportPage.setTotalResultSize(count);
		if(count > 0) {
			reportPage.setItems(ebkProductReportService.findOnSaleProductList(params));		
		}
		reportPage.buildUrl(getRequest());
		
		return "onSaleProductList";
	}
	
	/**
	 * 短线收客表
	 * 
	 */
	@Action("/report/product/downProductSalesList")
	public void downProductSalesList() {
		Map<String, Object> params = initParam();
		// 分页
		params.put("start", 1);
		params.put("end", 65535);
		reportPage.setItems(ebkProductReportService.findProductSalesList(params));
		try {
			String fileName = ExcelUtils.writeXlsFile(reportPage.getItems(), "/WEB-INF/resources/template/productSalesTemplate.xls");
			this.writeAttachment(fileName, "LvmamaSales_"+this.getCurrentSupplierId()+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/report/product/productSalesList")
	public String productSalesList() {
		Map<String, Object> params = initParam();
		int count = ebkProductReportService.findProductSalesCount(params);
		reportPage.setTotalResultSize(count);
		if(count > 0) {
			reportPage.setItems(ebkProductReportService.findProductSalesList(params));		
		}
		reportPage.buildUrl(getRequest());
		
		return "productSalesList";
	}
	/**
	 * 出团游客表
	 * 
	 */
	@Action("/report/product/downProductVisitorList")
	public void downProductVisitorList() {
		Map<String, Object> params = initParam();
		// 分页
		params.put("start", 1);
		params.put("end", 65535);
		reportPage.setItems(ebkProductReportService.findProductVisitorList(params));
		try {
			String fileName = ExcelUtils.writeXlsFile(reportPage.getItems(), "/WEB-INF/resources/template/productVisitorTemplate.xls");
			this.writeAttachment(fileName, "LvmamaVisitor_"+this.getCurrentSupplierId()+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/report/product/productVisitorList")
	public String productVisitorList() {
		Map<String, Object> params = initParam();
		int count = ebkProductReportService.findProductVisitorCount(params);
		reportPage.setTotalResultSize(count);
		if(count > 0) {
			this.initPageParam(params);
			reportPage.setItems(ebkProductReportService.findProductVisitorList(params));		
		}
		reportPage.buildUrl(getRequest());
		
		return "productVisitorList";
	}
	public String getProdProductName() {
		return prodProductName;
	}
	public void setProdProductName(String prodProductName) {
		this.prodProductName = prodProductName;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public String getMetaProductName() {
		return metaProductName;
	}
	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getOnlineTimeStart() {
		return onlineTimeStart;
	}
	public void setOnlineTimeStart(String onlineTimeStart) {
		this.onlineTimeStart = onlineTimeStart;
	}
	public String getOnlineTimeEnd() {
		return onlineTimeEnd;
	}
	public void setOnlineTimeEnd(String onlineTimeEnd) {
		this.onlineTimeEnd = onlineTimeEnd;
	}
	public String getProdProductId() {
		return prodProductId;
	}
	public void setProdProductId(String prodProductId) {
		this.prodProductId = prodProductId;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getVisitTimeStart() {
		return visitTimeStart;
	}
	public void setVisitTimeStart(String visitTimeStart) {
		this.visitTimeStart = visitTimeStart;
	}
	public String getVisitTimeEnd() {
		return visitTimeEnd;
	}
	public void setVisitTimeEnd(String visitTimeEnd) {
		this.visitTimeEnd = visitTimeEnd;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public Page<EbkProductVisitorReportVo> getReportPage() {
		return reportPage;
	}
	public void setReportPage(Page<EbkProductVisitorReportVo> reportPage) {
		this.reportPage = reportPage;
	}
	public EbkProductReportService getEbkProductReportService() {
		return ebkProductReportService;
	}
	public void setEbkProductReportService(EbkProductReportService ebkProductReportService) {
		this.ebkProductReportService = ebkProductReportService;
	}
	public String getOfflineTimeStart() {
		return offlineTimeStart;
	}
	public void setOfflineTimeStart(String offlineTimeStart) {
		this.offlineTimeStart = offlineTimeStart;
	}
	public String getOfflineTimeEnd() {
		return offlineTimeEnd;
	}
	public void setOfflineTimeEnd(String offlineTimeEnd) {
		this.offlineTimeEnd = offlineTimeEnd;
	}
}
