package com.lvmama.report.web.moneyUser;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.zkoss.zul.Filedownload;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.service.MoneyUserDetailExportService;
import com.lvmama.report.utils.ZkMessage;
import com.lvmama.report.web.BaseAction;

public class MoneyUserDetailExportAction extends BaseAction {

	private Date visitStartDate;
	private Date visitEndDate;
	private Date moneyChangeStartDate;
	private Date moneyChangeEndDate;
	/**
	 * 导出报表类型 支付明细：PAY 退款明细：REFUND
	 */
	private String reportType;

	private MoneyUserDetailExportService moneyUserDetailExportService;

	/**
	 * 导出
	 */
	public void doExport() {
		if (reportType == null || reportType.trim().length() == 0) {
			ZkMessage.showError("请选择类型");
			return;
		}
		List list = null;
		String templatePath = "";
		String fileName = "";
		if ("PAY".equals(reportType)) { // 导出支付明细
			list = moneyUserDetailExportService.payExport(visitStartDate, visitEndDate, moneyChangeStartDate, moneyChangeEndDate);
			templatePath = "/WEB-INF/resources/template/moneyUserPayTemplate.xls";
			fileName = "money_user_pay_detail_" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
		} else if ("REFUND".equals(reportType)) { // 导出退款明细
			list = moneyUserDetailExportService.refundExport(visitStartDate, visitEndDate, moneyChangeStartDate, moneyChangeEndDate);
			templatePath = "/WEB-INF/resources/template/moneyUserRefundTemplate.xls";
			fileName = "money_user_refund_detail_" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
		} else {
			throw new RuntimeException("导出报表异常：报表类型不能为空");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("detailList", list);
		doExcel(map, templatePath, fileName);
	}
	
	/**
	 * 导出
	 * @param beans
	 * @param path
	 * @param fileName
	 */
	public void doExcel(Map beans,String path,String fileName){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/" + fileName + ".xls";
			
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destFileName);

			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("下载失败");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Date getVisitStartDate() {
		return visitStartDate;
	}

	public void setVisitStartDate(Date visitStartDate) {
		this.visitStartDate = visitStartDate;
	}

	public Date getVisitEndDate() {
		return visitEndDate;
	}

	public void setVisitEndDate(Date visitEndDate) {
		this.visitEndDate = visitEndDate;
	}

	public Date getMoneyChangeStartDate() {
		return moneyChangeStartDate;
	}

	public void setMoneyChangeStartDate(Date moneyChangeStartDate) {
		this.moneyChangeStartDate = moneyChangeStartDate;
	}

	public Date getMoneyChangeEndDate() {
		return moneyChangeEndDate;
	}

	public void setMoneyChangeEndDate(Date moneyChangeEndDate) {
		this.moneyChangeEndDate = moneyChangeEndDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public MoneyUserDetailExportService getMoneyUserDetailExportService() {
		return moneyUserDetailExportService;
	}

	public void setMoneyUserDetailExportService(MoneyUserDetailExportService moneyUserDetailExportService) {
		this.moneyUserDetailExportService = moneyUserDetailExportService;
	}
}
