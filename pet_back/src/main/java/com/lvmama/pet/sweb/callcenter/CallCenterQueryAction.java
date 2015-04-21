package com.lvmama.pet.sweb.callcenter;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.conn.ConnRecord;
import com.lvmama.comm.pet.service.conn.ConnRecordService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;

@ParentPackage("json-default")
@Results( {
	@Result(name = "queryList", location = "/WEB-INF/pages/back/conn/queryList.jsp")
})
/**
 * 来电弹屏-查询统计用户.
 * @author yuzhibing
 */
public class CallCenterQueryAction extends BaseAction {
	private final static String exportCallbackExcelURI="/report/exportCallback.action";
	
	private ConnRecordService connRecordService;
	private ConnRecord connRecord;
	private Page<ConnRecord> connRecordPage;
	private int page = 1;
	private String exportCallbackExcelUrl;
	@Action("/call/queryIndex")
	public String excuete() {
		return "queryList";
	}
	@Action("/call/query")
	public String query() {
		String beingTime = DateUtil.getFormatDate(this.connRecord.getFeedbackTime(), "yyyy-MM-dd HH:mm:ss");
		String endTime = DateUtil.getFormatDate(this.connRecord.getFeedbackTimeEnd(), "yyyy-MM-dd HH:mm:ss");
		exportCallbackExcelUrl =  exportCallbackExcelURI
				+"?param0=234&isCallBack="+(this.connRecord.getCallBack()!=null?this.connRecord.getCallBack():"")
				+"&feedbackTime="+(beingTime!=null?beingTime:"")
				+"&feedbackTimeEnd="+(endTime!=null?endTime:"");
		
		this.connRecordPage = connRecordService.queryConnRecordWithPage(connRecord, 10, page);
		connRecordPage.buildUrl(getRequest());
		return "queryList";
	}
	
	public ConnRecord getConnRecord() {
		return connRecord;
	}
	public void setConnRecord(ConnRecord connRecord) {
		this.connRecord = connRecord;
	}
	public void setConnRecordService(ConnRecordService connRecordService) {
		this.connRecordService = connRecordService;
	}
	public Page<ConnRecord> getConnRecordPage() {
		return connRecordPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getExportCallbackExcelUrl() {
		return exportCallbackExcelUrl;
	}
}
