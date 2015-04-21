package com.lvmama.sso.web.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import net.sf.json.JSONObject;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.vo.AjaxRtnBaseBean;
import com.lvmama.sso.web.BaseLoginAction;

/**
 * 批量注册
 *
 */
public class BatchRegisterAjaxAction extends BaseLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3003985092168177281L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(BatchRegisterAjaxAction.class);
    /**
     * 查询内容
     */
	private String queryContent;
	/**
	 * 域
	 */
	private String field;
	/**
	 * 分隔符
	 */
	private String separator;
	/**
	 * 合法性
	 */
	private String validate = "Y";
	/**
	 * sms内容
	 */
	private String smsContent;
	/**
	 * 邮件内容
	 */
	private String mailContent;
    /**
     * SSO系统的消息生成者
     */
	private SSOMessageProducer ssoMessageProducer;
	/**
	 * 用户服务接口
	 */
	private UserUserProxy userUserProxy;

	/**
     * 批量注册
     * @throws IOException IOException
     */
	@Action("/batch/request")
	public void register() throws IOException {
		getResponse().setContentType("text/html; charset=gb2312");
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(false, "未有更多的信息");

		LOG.info("接收到请求:queryContent=" + queryContent + ";field=" + field + ";separator=" + separator
				+ ";validate=" + validate + ";smsContent=" + smsContent + ";mailContent=" + mailContent);

		if (StringUtils.isEmpty(queryContent)) {
			rtn.setErrorText("需要注册的内容不能为空");
			getResponse().getWriter().print(JSONObject.fromObject(rtn));
			return;
		}
		if (StringUtils.isEmpty(field)) {
			rtn.setErrorText("注册的类型(手机/邮箱地址)不能为空");
			getResponse().getWriter().print(JSONObject.fromObject(rtn));
			return;
		}
		if (StringUtils.isEmpty(separator)) {
			rtn.setErrorText("分隔符(回车/冒号/逗号/空格)不能为空");
			getResponse().getWriter().print(JSONObject.fromObject(rtn));
			return;
		}

		if (field.equals("mobile_number")) {
			registerBatchMobileNumber(splitContent(queryContent, separator));
			rtn.setSuccess(true);
			rtn.setErrorText("上述用户信息已自动注册");
			getResponse().getWriter().print(JSONObject.fromObject(rtn));
			return;
		}

		if (field.equals("email")) {
			registerBatchEmail(splitContent(queryContent, separator));
			rtn.setSuccess(true);
			rtn.setErrorText("上述用户信息已自动注册");
			getResponse().getWriter().print(JSONObject.fromObject(rtn));
			return;
		}
	}

	/**
	 * 手机批量注册
	 * @param mobiles 手机列表
	 */
	private void registerBatchMobileNumber(final List<String> mobiles) {
		UserUser users = null;
		for (String mobile : mobiles) {
			if (userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile)) {
				users = userUserProxy.genDefaultUser();
				users.setMobileNumber(mobile);
				users.setIsMobileChecked(validate);
				users.setGroupId(Constant.USER_CHANNEL.GP_TECH.name());
				users.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
				users.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
				users = userUserProxy.register(users);
				SSOMessage message = new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.INNER_BATCH, users.getId());
				if (StringUtils.isNotEmpty(smsContent)) {
					message.putAttribute("SMS.Content", smsContent);
				}
				ssoMessageProducer.sendMsg(message);
				LOG.info(mobile + "register successfully, the identification is" + users.getUserId());
			} else {
				LOG.info(mobile + "has a user!");
			}
		}
	}

	/**
	 * 邮箱批量注册
	 * @param emails 邮箱列表
	 */
	private void registerBatchEmail(final List<String> emails) {
		UserUser users = null;
		for (String email : emails) {
			if (userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, email)) {
				users = userUserProxy.genDefaultUser();
				users.setEmail(email);
				users.setIsEmailChecked(validate);
				users.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
				users.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
				users = userUserProxy.register(users);
				SSOMessage message = new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.INNER_BATCH, users.getId());
				if (StringUtils.isNotEmpty(mailContent)) {
					message.putAttribute("MAIL.Content", mailContent);
				}
				ssoMessageProducer.sendMsg(message);
				LOG.info(email + "batch register successfully, the identification is" + users.getUserId());
			} else {
				LOG.info(email + "has a user!");
			}
		}
	}

	/**
	 * 分隔内容
	 * @param content 需要分隔的内容
	 * @param separator1  分隔符
	 * @return 分隔后的内容列表
	 */
	private List<String> splitContent(final String content, final String separator1) {
		List<String> values = new ArrayList<String>();
		if (null != content) {
			String[] result = content.split(separator1);
			for (String r : result) {
				values.add(r.trim());
			}
		}
		return values;
	}

	//setter and getter
	public String getQueryContent() {
		return queryContent;
	}

	public void setQueryContent(final String queryContent) {
		this.queryContent = queryContent;
	}

	public String getField() {
		return field;
	}

	public void setField(final String field) {
		this.field = field;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(final String separator) {
		this.separator = separator;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(final String validate) {
		this.validate = validate;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(final String smsContent) {
		this.smsContent = smsContent;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(final String mailContent) {
		this.mailContent = mailContent;
	}

	public SSOMessageProducer getSsoMessageProducer() {
		return ssoMessageProducer;
	}

	public void setSsoMessageProducer(final SSOMessageProducer ssoMessageProducer) {
		this.ssoMessageProducer = ssoMessageProducer;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

}
