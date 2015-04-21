package com.lvmama.front.web.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.front.web.BaseAction;

/**
 * 签证/签注发送邮件时调用该类,立即发送邮件
 */
public class VisaEmailAction extends BaseAction{
	
	private static final long serialVersionUID = 5897159225888468480L;
	private static final Log log = LogFactory.getLog(VisaEmailAction.class);
	private EmailClient emailClient;
	/**
	 * 签证材料远程服务
	 */
	private VisaApplicationDocumentService visaApplicationDocumentService;
	private String emailToAddress;//收件人地址
	private Long documentId;//签证材料文档id
	private String occupation;//人群
	private String cnVisaType;//签证类型
	
	@Action("/visa/visaDetails")
	public void visaDocumentDetails() throws IOException{
		List<VisaApplicationDocumentDetails> vList=new ArrayList<VisaApplicationDocumentDetails>();
		//文档详情
   	 	vList = visaApplicationDocumentService.queryDetailsByDocumentId(documentId);
	   	 if(vList != null &&vList.size()>0) {	
	   	 	//邮件
	   	 	EmailContent email = new EmailContent();
	   	 	//返回消息
	   	 	JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("message", "");
	   	 	try {
				//邮件头		
		   	 	email.setFromAddress("service@cs.lvmama.com");
				email.setFromName("驴妈妈旅游网");
				email.setSubject("驴妈妈旅游网");	
				email.setToAddress(emailToAddress);
				//通过vList得到发送内容
				String content = buildEmailContent(vList);
				email.setContentText(content);
				email.setCreateTime(new java.util.Date());
				emailClient.sendEmailDirect(email);
				json.put("success", true);
				log.info("签证/签证已发送到"+email.getToAddress());
	
			} catch (Exception e) {
				e.printStackTrace();
				log.error("发送到"+email.getToAddress());
			}
	   	 	getResponse().getWriter().print(json.toString());
	   	 }
	}
	
	/**
	 * 组装Email内容
	 */
	public String buildEmailContent(List<VisaApplicationDocumentDetails> vList) {
		StringBuilder content = new StringBuilder(100);
		content.append("<table width=\"800\" bgColor=\"#F2F2F2\" style=\"margin:0 auto;\">");
		content.append("<tr>");
		content.append("<td style=\"border-top:3px solid #D30085;text-align:center\">");
		content.append("<label style=\"font-weight:bold;color:#bd0059;font-size:16px;font-family:arial\">");
		content.append(cnVisaType+"-"+occupation);
		content.append("</label></td></tr>");
		content.append("<tr>");
		content.append("<td style=\"font-size:14px;color:#565656;text-align:left\">");
		content.append("<table style=\"background-color:#ffffff;border:1px solid #d8d8d8;margin:5px;width:800px\">");
		//循环取出标题和内容
		for(VisaApplicationDocumentDetails v:vList){
			content.append("<tr>");
			content.append("<td style=\"font-size:14px;color:#565656;border-bottom:1px dashed #ccc;padding: 10px 20px\"><b>");
			content.append(v.getTitle());
			content.append("</b></td>");
			content.append("<td style=\"font-size:14px;color:#565656;border-bottom:1px dashed #ccc;padding: 10px 20px\">");
			content.append(v.getContent());
			content.append("</td>");
			content.append("</tr>");
		}
		//循环结束
		content.append("</table>");
		content.append("<br/></td></tr></table>");
		content.append("<table width=\"800\" style=\"margin:10px auto 0 auto;font-family:arial\">");
		content.append("<tr><td style=\"color:#888888;margin-top:15px;margin-bottom:20px;text-align:center;font-size:12px;line-height:20px;\">");
		content.append("为了确保我们的信息不被当作垃圾邮件处理，请把驴妈妈 <a href=\"mailto:service@cs.lvmama.com\" style=\"color:#0066CC;\">service@cs.lvmama.com</a> 添加为您的联系人<br/>本邮件由系统自动发出，请勿直接回复，如有疑问，请拨打24小时客服服务热线：<b>1010-6060</b><br/>Copyright 2012 www.lvmama.com，上海景域文化传播有限公司版权所有");
		content.append("<td><tr></table>");
		return content.toString();
	}

	public void setVisaApplicationDocumentService(
			VisaApplicationDocumentService visaApplicationDocumentService) {
		this.visaApplicationDocumentService = visaApplicationDocumentService;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public EmailClient getEmailClient() {
		return emailClient;
	}

	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getCnVisaType() {
		return cnVisaType;
	}

	public void setCnVisaType(String cnVisaType) {
		this.cnVisaType = cnVisaType;
	}

	public String getEmailToAddress() {
		return emailToAddress;
	}

	public void setEmailToAddress(String emailToAddress) {
		this.emailToAddress = emailToAddress;
	}
	
	
}
