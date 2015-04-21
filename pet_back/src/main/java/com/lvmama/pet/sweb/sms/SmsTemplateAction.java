package com.lvmama.pet.sweb.sms;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant.SEND_MSG_CHANNEL;

/**
 * @author likun 短信模板管理
 * */
@Results({
		@Result(name = "petIndex", location = "/WEB-INF/pages/back/sms/sms_template_pet_index.jsp"),
		@Result(name = "petAddOrEdit", location = "/WEB-INF/pages/back/sms/sms_template_pet_save.jsp"),
		@Result(name = "petList", location = "/smsTemplate/petIndex.do", type = "redirect"),
		@Result(name = "superIndex", location = "/WEB-INF/pages/back/sms/sms_template_super_index.jsp"),
		@Result(name = "superAddOrEdit", location = "/WEB-INF/pages/back/sms/sms_template_super_save.jsp"),
		@Result(name = "superList", location = "/smsTemplate/superIndex.do", type = "redirect") })
@Namespace("/smsTemplate")
public class SmsTemplateAction extends BackBaseAction {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(SmsTemplateAction.class);

	private Map<String, String> channelMap = null;

	public void doBefore() {
		channelMap = new HashMap<String, String>();
		SEND_MSG_CHANNEL[] channel = SEND_MSG_CHANNEL.values();
		for (SEND_MSG_CHANNEL send_MSG_CHANNEL : channel) {
			channelMap.put(send_MSG_CHANNEL.getCode(),
					send_MSG_CHANNEL.getCnName());
		}

	}

	/**
	 * super短信模板页面
	 * 
	 * @return
	 */
	@Action("petIndex")
	public String petIndex() {
		doBefore();
		Map<String, Object> params = getParameterMap();
		// 取得数据总数量
		this.petTemplatePage
				.setTotalResultSize(this.comSmsTemplateRemoteService
						.getListCountByParams(params));
		// 初始化分页信息
		this.petTemplatePage.buildUrl(getRequest());
		this.petTemplatePage.setCurrentPage(this.page);
		params.put("skipResults", this.petTemplatePage.getStartRows() - 1);
		params.put("maxResults", this.petTemplatePage.getEndRows());
		this.petTemplatePage.setItems(this.comSmsTemplateRemoteService
				.getListByParams(params));
		return "petIndex";
	}

	/**
	 * 添加super短信模板
	 * 
	 * @return
	 */
	@Action("petAdd")
	public String petAdd() {
		doBefore();
		setRequestAttribute("operate", "add");
		return "petAddOrEdit";
	}

	/**
	 * 编辑pet短信模板
	 * 
	 * @return
	 */
	@Action("petEdit")
	public String petEdit() {
		doBefore();
		setRequestAttribute("operate", "edit");
		String templateId = getRequestParameter("templateId");
		if (StringUtil.isNotEmptyString(templateId)) {
			ComSmsTemplate model = this.comSmsTemplateRemoteService
					.selectByPrimaryKey(templateId);
			printLog("将要修改pet短信模板信息," + JSONSerializer.toJSON(this.template));
			setRequestAttribute("model", model);
		}
		return "petAddOrEdit";
	}

	@Action("petSave")
	public void petSave() throws Exception {
		String result = "1";
		if ("edit".equals(operate)) {
			printLog("修改pet短信模板信息," + JSONSerializer.toJSON(this.template));
			// 修改
			this.comSmsTemplateRemoteService.updateByPrimaryKey(this.template);
		} else {
			try {
				printLog("添加pet短信模板信息," + JSONSerializer.toJSON(this.template));
				this.comSmsTemplateRemoteService.insert(this.template);
			} catch (Exception e) {
				String mes = e.getMessage();
				if (StringUtil.isNotEmptyString(mes)
						&& mes.indexOf("COM_SMS_TEMPLATE_PK") != -1) {
					result = "短信模板ID已经存在，请换一个!";
				} else {
					throw e;
				}
			}
		}
		sendAjaxMsg(result);
	}

	/**
	 * 删除pet短信模板
	 * 
	 * @return
	 */
	@Action("petDelete")
	public String petDelete() {
		if (StringUtil.isNotEmptyString(templateId)) {
			this.comSmsTemplateRemoteService.deleteByPrimaryKey(templateId);
		}
		return "petList";
	}
	
	/**
	 * pet批量渠道
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Action("petEditBatchChannel")
	public String petEditBatchChannel() throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = getParameterMap();
		printLog("批量修改短信渠道," + JSONSerializer.toJSON(paramMap));
		this.comSmsTemplateRemoteService.updateBatchChannel(paramMap);
		resultMap.put("msg", "批量修改渠道成功!");
		this.outputToClient(JSONSerializer.toJSON(resultMap).toString());
		return null;
	}

	/**
	 * super短信模板页面
	 * 
	 * @return
	 */
	@Action("superIndex")
	public String superIndex() {
		doBefore();
		Map<String, Object> params = getParameterMap();
		// 取得数据总数量
		this.superTemplatePage
				.setTotalResultSize(this.superSmsTemplateRemoteService
						.getListCountByParams(params));
		// 初始化分页信息
		this.superTemplatePage.buildUrl(getRequest());
		this.superTemplatePage.setCurrentPage(this.page);
		params.put("skipResults", this.superTemplatePage.getStartRows() - 1);
		params.put("maxResults", this.superTemplatePage.getEndRows());
		this.superTemplatePage.setItems(this.superSmsTemplateRemoteService
				.getListByParams(params));
		return "superIndex";
	}

	/**
	 * 添加super短信模板
	 * 
	 * @return
	 */
	@Action("superAdd")
	public String superAdd() {
		doBefore();
		setRequestAttribute("operate", "add");
		return "superAddOrEdit";
	}

	/**
	 * 编辑super短信模板
	 * 
	 * @return
	 */
	@Action("superEdit")
	public String superEdit() {
		doBefore();
		setRequestAttribute("operate", "edit");
		String templateId = getRequestParameter("templateId");
		if (StringUtil.isNotEmptyString(templateId)) {
			ComSmsTemplate model = this.superSmsTemplateRemoteService
					.selectByPrimaryKey(templateId);
			printLog("将要修改Super短信模板信息," + JSONSerializer.toJSON(this.template));
			setRequestAttribute("model", model);
		}
		return "superAddOrEdit";
	}

	@Action("superSave")
	public void superSave() throws Exception {
		String result = "1";
		if ("edit".equals(operate)) {
			printLog("修改Super短信模板信息," + JSONSerializer.toJSON(this.template));
			// 修改
			this.template.setModifyProdChannelSms("1".equals(this
					.getRequestParameter("isModifyProdChannelSms")) ? true
					: false);
			this.superSmsTemplateRemoteService
					.updateByPrimaryKey(this.template);
		} else {
			try {
				printLog("添加Super短信模板信息,"
						+ JSONSerializer.toJSON(this.template));
				this.superSmsTemplateRemoteService.insert(this.template);
			} catch (Exception e) {
				String mes = e.getMessage();
				if (StringUtil.isNotEmptyString(mes)
						&& mes.indexOf("COM_SMS_TEMPLATE_PK") != -1) {
					result = "短信模板ID已经存在，请换一个!";
				} else {
					throw e;
				}
			}
		}
		sendAjaxMsg(result);
	}

	/**
	 * 删除super短信模板
	 * 
	 * @return
	 */
	@Action("superDelete")
	public String superDelete() {
		if (StringUtil.isNotEmptyString(templateId)) {
			this.superSmsTemplateRemoteService.deleteByPrimaryKey(templateId);
		}
		return "superList";
	}

	public void printLog(String msg) {
		if (getSessionUser() != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", getSessionUser().getUserId());
			map.put("userName", getSessionUser().getUserName());
			map.put("realName", getSessionUser().getRealName());
			msg = "操作人:" + JSONSerializer.toJSON(map).toString() + msg;
		}
		log.info(msg);
	}

	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 操作标志
	 */
	private String operate;

	/**
	 * 对象模板对象
	 */
	private ComSmsTemplate template;

	/**
	 * lvmama_pet短信模板service
	 */
	private ComSmsTemplateService comSmsTemplateRemoteService;

	/**
	 * lvmama_super短信模板service
	 */
	private ComSmsTemplateService superSmsTemplateRemoteService;

	private Page<ComSmsTemplate> petTemplatePage = new Page<ComSmsTemplate>();

	private Page<ComSmsTemplate> superTemplatePage = new Page<ComSmsTemplate>();

	public ComSmsTemplateService getComSmsTemplateRemoteService() {
		return comSmsTemplateRemoteService;
	}

	public void setComSmsTemplateRemoteService(
			ComSmsTemplateService comSmsTemplateRemoteService) {
		this.comSmsTemplateRemoteService = comSmsTemplateRemoteService;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Page<ComSmsTemplate> getPetTemplatePage() {
		return petTemplatePage;
	}

	public void setPetTemplatePage(Page<ComSmsTemplate> petTemplatePage) {
		this.petTemplatePage = petTemplatePage;
	}

	public Map<String, String> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, String> channelMap) {
		this.channelMap = channelMap;
	}

	public ComSmsTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ComSmsTemplate template) {
		this.template = template;
	}

	public Page<ComSmsTemplate> getSuperTemplatePage() {
		return superTemplatePage;
	}

	public void setSuperTemplatePage(Page<ComSmsTemplate> superTemplatePage) {
		this.superTemplatePage = superTemplatePage;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public ComSmsTemplateService getSuperSmsTemplateRemoteService() {
		return superSmsTemplateRemoteService;
	}

	public void setSuperSmsTemplateRemoteService(
			ComSmsTemplateService superSmsTemplateRemoteService) {
		this.superSmsTemplateRemoteService = superSmsTemplateRemoteService;
	}

	public static void main(String[] args) {
		/*
		 * Field[] fs = ComSmsTemplate.class.getDeclaredFields(); for (Field
		 * field : fs) { System.out.println(field.getName()); }
		 */

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext-pet-back-beans.xml");
		System.out.println(context.getBean("superSmsTemplateRemoteService"));
	}

}
