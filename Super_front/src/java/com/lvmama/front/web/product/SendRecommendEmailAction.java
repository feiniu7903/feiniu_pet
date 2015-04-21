package com.lvmama.front.web.product;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.pub.ComEmailTemplate;
import com.lvmama.comm.pet.service.pub.ComEmailTemplateService;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.front.web.BaseAction;

/**
 * 发送推荐邮件给好友.<br/>
 * 通过消息机制，发送消息给 nsso来统一发送邮件.
 * 
 * @author sunruyi
 * @see org.apache.commons.logging.Log
 * @see org.apache.commons.logging.LogFactory
 * @see org.apache.struts2.convention.annotation.Action
 * @see com.lvmama.comm.utils.UtilityTool
 * @see com.lvmama.front.web.BaseAction
 * @see com.lvmama.util.StringUtil
 */
public class SendRecommendEmailAction extends BaseAction {

	/**
	 * 序列号.
	 */
	private static final long serialVersionUID = -7377927240800696119L;
	/**
	 * 日志.
	 */
	private final Log logger = LogFactory.getLog(ProductDetailAction.class);
	/**
	 * 推荐产品的邮件模板标识
	 */
	private static final String RECOMMEND_PRODOUT_MAIL_TEMPLATE = "RECOMMEND_PRODOUT_MAIL_TEMPLATE";

	/**
	 * 推荐人姓名所允许的最大长度.
	 */
	private final int maxReferrerNameLength = 16;
	/**
	 * 推荐人姓名.
	 */
	private String username;
	/**
	 * 推荐的产品ID.
	 */
	private String productId;
	/**
	 * 推荐的产品名称.
	 */
	private String productName;

	/**
	 * 邮箱地址所允许的最大长度.
	 */
	private final int maxEmailLength = 30;
	/**
	 * 填写的第一个邮箱地址.
	 */
	private String firstEmail;
	/**
	 * 填写的第二个邮箱地址.
	 */
	private String secondEmail;
	/**
	 * 填写的第三个邮箱地址.
	 */
	private String thirdEmail;
	/**
	 * 填写的第四个邮箱地址.
	 */
	private String fourthEmail;
	/**
	 * 邮件模板服务
	 */
	private ComEmailTemplateService comEmailTemplateService;
	/**
	 * 文件服务
	 */
	private FSClient fsClient;

	/**
	 * 推荐给好友发送邮件的消息.
	 */
	@Action("/product/recomment")
	public void recomment() {
		logger.info("username = " + username + " productId = " + productId + " productName = " + productName
				+ " firstEmail = " + firstEmail + " secondEmail= " + secondEmail + " thirdEmail= " + thirdEmail
				+ " fourthEmail= " + fourthEmail);
		
		// 判断推荐人是否为空、是否超过16个字符、productId是否为空、firstEmail是否为空、firstEmail是否符合email格式
		boolean flag = UtilityTool.isValid(username) && username.length() <= maxReferrerNameLength
				&& UtilityTool.isValid(productId) && UtilityTool.isValid(productName)
				&& UtilityTool.isValid(firstEmail) && isEmail(firstEmail);
		// flag = true;
		// 当flag为true时，发送消息
		if (flag) {
			try {	
				ComEmailTemplate emailTemplate = comEmailTemplateService.getComEmailTemplateByTemplateName(RECOMMEND_PRODOUT_MAIL_TEMPLATE);
				
				if (null == emailTemplate || null != emailTemplate.getContentTemplateFile()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Cann't find recommend product email template.");
					}
					return;
				}
				File file = ResourceUtil.getResourceFile(emailTemplate.getContentTemplateFile());
				byte[] fileData;
				if(file != null)
				{
					try {
						fileData = FileUtil.getBytesFromFile(file);
					} catch (Exception e2) {
						LOG.error("get mail content error "+e2);
						return;
					}
				}
				else
				{
					return;
				}
				
				String content = new String(fileData,"UTF-8");
				
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("userName", username);
				parameters.put("productId", productId);
				parameters.put("productName", productName);
				parameters.put("sendDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	
				EmailContent email = new EmailContent();
				email.setFromName("驴妈妈旅游网");
				StringBuilder sb = new StringBuilder();
				if (!StringUtils.isBlank(firstEmail)) {
					sb.append(firstEmail).append(",");
				}
				if (!StringUtils.isBlank(secondEmail)) {
					sb.append(secondEmail).append(",");
				}
				if (!StringUtils.isBlank(thirdEmail)) {
					sb.append(thirdEmail).append(",");
				}
				if (!StringUtils.isBlank(fourthEmail)) {
					sb.append(fourthEmail).append(",");
				}
				if (sb.length() > 0) {
					sb.setLength(sb.length() - 1);
				}
				email.setToAddress(sb.toString());
				try {
					email.setSubject(StringUtil.composeMessage(emailTemplate.getSubjectTemplate(), parameters));
					email.setContentText(StringUtil.composeMessage(content, parameters));
				} catch (Exception e) {
					LOG.error("replace email content error!");
				}
			} catch (Exception e) {
			}
		}
		sendAjaxMsg("true");
	}

	/**
	 * 初始化信息.
	 * 
	 * @param message
	 *            信息
	 */
	public List<String> initMessage() {
		List<String> recList = new ArrayList<String>();
		if (isEmail(firstEmail)) {
			recList.add(firstEmail);
		}
		if (isEmail(secondEmail)) {
			recList.add(secondEmail);
		}
		if (isEmail(thirdEmail)) {
			recList.add(thirdEmail);
		}
		if (isEmail(fourthEmail)) {
			recList.add(fourthEmail);
		}
		return recList;
	}

	/**
	 * 校验email格式. 
	 * 
	 * @param email
	 *            邮箱
	 * @return boolean 是否符合邮箱格式
	 */
	public boolean isEmail(final String email) {
		return !"@".equals(email) && StringUtil.validEmail(email) && email.length() <= maxEmailLength;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public String getFirstEmail() {
		return firstEmail;
	}

	public void setFirstEmail(final String firstEmail) {
		this.firstEmail = firstEmail;
	}

	public String getSecondEmail() {
		return secondEmail;
	}

	public void setSecondEmail(final String secondEmail) {
		this.secondEmail = secondEmail;
	}

	public String getThirdEmail() {
		return thirdEmail;
	}

	public void setThirdEmail(final String thirdEmail) {
		this.thirdEmail = thirdEmail;
	}

	public String getFourthEmail() {
		return fourthEmail;
	}

	public void setFourthEmail(final String fourthEmail) {
		this.fourthEmail = fourthEmail;
	}

	public void setComEmailTemplateService(
			ComEmailTemplateService comEmailTemplateService) {
		this.comEmailTemplateService = comEmailTemplateService;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public Log getLogger() {
		return logger;
	}
}
