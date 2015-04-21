package com.lvmama.front.web.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.sms.SmsUtil;
import com.lvmama.comm.vo.SmsStatusReport;
import com.lvmama.front.web.BaseAction;

/**
 * 短信相关
 * 
 * @author ready
 * 
 */
public class SmsAction extends BaseAction {

	/**
	 * 接收亿美状态报告
	 * 
	 * @throws Exception
	 */
	@Action("/smsReport/emay")
	public void emaySmsReport() throws Exception {
		String reportXml = getRequestParameter("reportXml");
		try {
			logger.info("处理亿美短信状态报告数据:" + reportXml);
			if (reportXml != null && StringUtil.isNotEmptyString(reportXml)) {
				List<SmsStatusReport> reports = SmsUtil
						.parseEmayReportXml(reportXml);
				if (reports != null && reports.size() >= 1) {
					this.smsRemoteService.updateReport(reports);
				}
			}
		} catch (Exception e) {
			logger.info("处理亿美短信状态报告失败");
			logger.error(e.getMessage(), e);
			throw e;
		}
		this.getResponse().setContentType("text/plain; charset=utf-8");
		this.getResponse().getWriter().write("亿美短信状态报告处理成功!");
	}

	/**
	 * 接收点点客状态报告
	 * 
	 * @throws Exception
	 */
	@Action("/smsReport/dodoca")
	public void dodocaSmsReport() throws Exception {
		String msgid = this.getRequestParameter("msgid");
		String status = this.getRequestParameter("status");
		String reportTime = this.getRequestParameter("reportTime");
		if (msgid != null && StringUtil.isNotEmptyString(msgid.trim())
				&& status != null && StringUtil.isNotEmptyString(status.trim())) {
			try {
				logger.info("处理点点客短信状态报告数据:[msgid:" + msgid + ",status:"
						+ status + ",reportTime:" + reportTime + "]");
				Long serialId = Long.valueOf(msgid.trim());
				Integer result = "DELIVRD".equals(status.trim()) ? SmsContentLog.REPORT_FOR_SUCCESS
						: SmsContentLog.REPORT_FOR_ERROR;
				Date date = null;
				String fmString = "yyyyMMddHHmmss";
				if (reportTime != null
						&& reportTime.trim().length() == fmString.length()) {
					date = DateUtil.toDate(reportTime, fmString);
				}
				SmsStatusReport report = new SmsStatusReport(serialId, result,
						date, "");
				List<SmsStatusReport> reportStatusList = new ArrayList<SmsStatusReport>();
				reportStatusList.add(report);
				this.smsRemoteService.updateReport(reportStatusList);
			} catch (Exception e) {
				logger.info("处理点点客短信状态报告失败");
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		this.getResponse().setContentType("text/plain; charset=utf-8");
		this.getResponse().getWriter().write("点点客短信状态报告处理成功!");
	}

	private Logger logger = Logger.getLogger(SmsAction.class);
	private SmsRemoteService smsRemoteService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmsRemoteService getSmsRemoteService() {
		return smsRemoteService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public static void main(String[] args) throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><reportList><report><seqId>3456</seqId><reportStatus>0</reportStatus><mobile>15010359299</mobile><errorCode>deliver</errorCode><submitDate>20100109152333</submitDate><receiveDate>20100109152339</receiveDate></report><report><seqId>3456</seqId><reportStatus>0</reportStatus><mobile>15010359299</mobile><errorCode>deliver</errorCode><submitDate>20100109152333</submitDate><receiveDate>20100109152339</receiveDate></report><report><seqId>3456</seqId><reportStatus>0</reportStatus><mobile>15010359299</mobile><errorCode>deliver</errorCode><submitDate>20100109152333</submitDate><receiveDate>20100109152339</receiveDate></report></reportList>";
		List<SmsStatusReport> list = SmsUtil.parseEmayReportXml(xml);
		for (SmsStatusReport smsStatusReport : list) {
			System.out.println(JSONSerializer.toJSON(smsStatusReport));

		}
	}
}
