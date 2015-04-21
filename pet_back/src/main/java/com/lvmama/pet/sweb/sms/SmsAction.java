package com.lvmama.pet.sweb.sms;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant.SEND_MSG_CHANNEL;

/**
 * @author likun 短信明细查询
 * */
@Results({
		@Result(name = "index", location = "/WEB-INF/pages/back/sms/sms_index.jsp"),
		@Result(name = "resend", location = "/WEB-INF/pages/back/sms/sms_resend.jsp"),
		@Result(name = "list", location = "/sms/index.do", type = "redirect") })
@Namespace("/sms")
public class SmsAction extends BackBaseAction {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SmsAction.class);

	// 短信发送渠道
	private Map<String, String> channelMap = null;
	// 状态
	private Map<String, String> smsStatusMap = null;

	public void doBefore() {
		// 获取短信发送通道
		channelMap = new LinkedHashMap<String, String>();
		SEND_MSG_CHANNEL[] channel = SEND_MSG_CHANNEL.values();
		for (SEND_MSG_CHANNEL send_MSG_CHANNEL : channel) {
			channelMap.put(send_MSG_CHANNEL.getCode(),
					send_MSG_CHANNEL.getCnName());
		}

		smsStatusMap = new LinkedHashMap<String, String>();
		//0:发送成功,1:发送失败,2:待发送,3:推送失败,4:已发送等待状态报告,
		//5:发送失败已重发,6:推送失败已重发,无:已发送等待状态报告
		smsStatusMap.put("0", "发送成功");
		smsStatusMap.put("1", "发送失败");
		smsStatusMap.put("2", "待发送");
		smsStatusMap.put("3", "推送失败");
		smsStatusMap.put("4", "已发送等待状态报告");
		smsStatusMap.put("5", "发送失败已重发");
		smsStatusMap.put("6", "推送失败已重发");
	}

	/**
	 * 短信查询列表页面
	 * 
	 * @return
	 */
	@Action("index")
	public String index() {
		doBefore();
		Map<String, Object> params = getParameterMap();
		// 取得数据总数量
		this.pageModel.setTotalResultSize(this.smsRemoteService
				.getSmsLogListCountByParams(params));
		// 初始化分页信息
		this.pageModel.buildUrl(getRequest());
		this.pageModel.setCurrentPage(this.page);
		params.put("skipResults", this.pageModel.getStartRows() - 1);
		params.put("maxResults", this.pageModel.getEndRows());
		this.pageModel.setItems(this.smsRemoteService
				.getSmsLogListByParams(params));
		return "index";
	}

	/**
	 * 短信重发
	 * 
	 * @throws Exception
	 */
	@Action("resend")
	public void resend() throws Exception {
		String result = "1";
		String id = this.getRequestParameter("id");
		String channel = this.getRequestParameter("channel");
		String tableName = this.getRequestParameter("tableName");
		if (StringUtil.isNotEmptyString(id)
				&& StringUtil.isNotEmptyString(tableName)) {
			Long userId = getSessionUser() != null ? getSessionUser()
					.getUserId() : null;
			Map<Object, Object> extendMap = new HashMap<Object, Object>();
			extendMap.put("userId", userId);
			this.smsRemoteService.reSendSms(Long.valueOf(id), channel,
					tableName, extendMap);
		}
		sendAjaxMsg(result);
	}

	/**
	 * 记录日志
	 * 
	 * @param msg
	 */
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

	private SmsRemoteService smsRemoteService;

	private Page<SmsContentLog> pageModel = new Page<SmsContentLog>();

	public Map<String, String> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, String> channelMap) {
		this.channelMap = channelMap;
	}

	public SmsRemoteService getSmsRemoteService() {
		return smsRemoteService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public Page<SmsContentLog> getPageModel() {
		return pageModel;
	}

	public void setPageModel(Page<SmsContentLog> pageModel) {
		this.pageModel = pageModel;
	}

	public Map<String, String> getSmsStatusMap() {
		return smsStatusMap;
	}

	public void setSmsStatusMap(Map<String, String> smsStatusMap) {
		this.smsStatusMap = smsStatusMap;
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
