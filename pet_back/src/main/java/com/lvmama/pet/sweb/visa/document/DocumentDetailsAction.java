package com.lvmama.pet.sweb.visa.document;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;

public class DocumentDetailsAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 3218553890267256549L;
	/**
	 * 签证材料远程服务
	 */
	private VisaApplicationDocumentService visaApplicationDocumentService;
	
	private VisaApplicationDocumentDetails details;
	
	@Action("/visa/details/add")
	public void add() throws IOException {
		JSONObject json = new JSONObject();
		if (null != details 
				&& null != details.getDocumentId()
				&& StringUtils.isNotBlank(details.getTitle())
				&& StringUtils.isNotBlank(details.getContent())) {
			visaApplicationDocumentService.insert(details, getSessionUserNameAndCheck());
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		getResponse().getWriter().print(json.toString());
	}
	
	@Action("/visa/details/update")
	public void update() throws IOException {
		JSONObject json = new JSONObject();
		if (null != details 
				&& null != details.getDetailsId()
				&& null != details.getDocumentId()
				&& StringUtils.isNotBlank(details.getTitle())
				&& StringUtils.isNotBlank(details.getContent())) {
			visaApplicationDocumentService.update(details, getSessionUserNameAndCheck());
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		getResponse().getWriter().print(json.toString());
	}
	
	@Action("/visa/details/del")
	public void del() throws IOException {
		JSONObject json = new JSONObject();
		if (null != details 
				&& null != details.getDetailsId()) {
			visaApplicationDocumentService.deleteDetails(details.getDetailsId(), getSessionUserNameAndCheck());
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		getResponse().getWriter().print(json.toString());		
	}

	public void setDetails(VisaApplicationDocumentDetails details) {
		this.details = details;
	}

	public VisaApplicationDocumentDetails getDetails() {
		return details;
	}

	public void setVisaApplicationDocumentService(
			VisaApplicationDocumentService visaApplicationDocumentService) {
		this.visaApplicationDocumentService = visaApplicationDocumentService;
	}
}
