package com.lvmama.front.web.sms;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.com.ISmsHistoryService;
import com.lvmama.comm.pet.po.pub.ComSmsHistory;
import com.lvmama.front.web.BaseAction;
@Results({
	@Result(name = "image", location = "/WEB-INF/pages/sms/image.jsp")
})
public class SmsHistoryAction extends BaseAction {
	
	private static final long serialVersionUID = 0L;

	private ISmsHistoryService smsHistoryService;
	
	private ComSmsHistory smsHistory;
	
	private Long smsId;
	
	@Action("/sms/convertToImage")
	public String convertToImage() {
		if (smsId!=null){
			smsHistory = smsHistoryService.getSmsHistoryByKey(smsId);
			this.getRequest().setAttribute("codeImage", smsHistory.getCodeImage());	
		}
		return "image";
	}
	
	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public void setSmsHistoryService(ISmsHistoryService smsHistoryService) {
		this.smsHistoryService = smsHistoryService;
	}

	public ComSmsHistory getSmsHistory() {
		return smsHistory;
	}
}
