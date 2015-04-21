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

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.sms.SmsConfig;
import com.lvmama.comm.pet.service.sms.SmsConfigService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant.SEND_MSG_CHANNEL;

/**
 * @author ready 短信渠道全局配置信息
 * */
@Results({ @Result(name = "index", location = "/WEB-INF/pages/back/sms/sms_config.jsp") })
@Namespace("/smsConfig")
public class SmsConfigAction extends BackBaseAction {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SmsConfigAction.class);

	private SmsConfig model;
	private Map<String, String> channelMap = null;
	private SmsConfigService smsConfigService;

	public void doBefore() {
		channelMap = new HashMap<String, String>();
		SEND_MSG_CHANNEL[] channel = SEND_MSG_CHANNEL.values();
		for (SEND_MSG_CHANNEL send_MSG_CHANNEL : channel) {
			channelMap.put(send_MSG_CHANNEL.getCode(),
					send_MSG_CHANNEL.getCnName());
		}
	}

	/**
	 * 跳转到短信渠道全局配置页面
	 * 
	 * @return
	 */
	@Action("index")
	public String index() {
		doBefore();
		this.model = this.smsConfigService.querySmsConfig();
		return "index";
	}

	/**
	 * 修改短信渠道全局配置信息
	 * 
	 * @throws Exception
	 */
	@Action("save")
	public void save() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (this.model != null
				&& StringUtil.isNotEmptyString(this.model.getDefaultChannel())) {
			resultMap.put("result", "1");
			resultMap.put("msg", "保存成功!");
			this.smsConfigService.update(this.model);
		} else {
			resultMap.put("result", "0");
			resultMap.put("msg", "参数有误!");
		}
		this.outputToClient(JSONSerializer.toJSON(resultMap).toString());
	}

	public SmsConfig getModel() {
		return model;
	}

	public void setModel(SmsConfig model) {
		this.model = model;
	}

	public Map<String, String> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, String> channelMap) {
		this.channelMap = channelMap;
	}

	public SmsConfigService getSmsConfigService() {
		return smsConfigService;
	}

	public void setSmsConfigService(SmsConfigService smsConfigService) {
		this.smsConfigService = smsConfigService;
	}

}
