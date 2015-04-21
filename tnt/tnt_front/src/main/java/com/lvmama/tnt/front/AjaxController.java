package com.lvmama.tnt.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.comm.utils.Configuration;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.po.TntCertCode;
import com.lvmama.tnt.comm.util.UserClient;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.front.utils.RequstUtil;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntUserService;

/**
 * 邮箱，手机校验ajax
 * 
 * @author gaoxin
 */
@Controller
@RequestMapping(value = "/ajax")
public class AjaxController {

	private static final Log LOG = LogFactory.getLog(AjaxController.class);

	@Autowired
	private UserClient tntUserClient;

	@Autowired
	private TntUserService tntUserService;
	@Autowired
	private TntCashaccountService tntCashaccountService;

	/**
	 * 唯一性检查
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/checkUniqueField")
	public void checkUniqueField(HttpServletRequest request, HttpServletResponse response, String userName, String mobile, String email) throws Exception {
		TntUser user = (TntUser) request.getSession().getAttribute(TntConstant.SESSION_TNT_USER);
		if (null != mobile) {
			if (null != user && mobile.equals(user.getMobileNumber())) {
				printRtn(request, response, new ResultMessage(true, ""));

				return;
			}
			if (!tntUserService.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile)) {
				printRtn(request, response, new ResultMessage(false, "该手机号已被使用，请更换其他手机号"));
				return;
			}
		}
		if (null != email) {
			if (null != user && email.equals(user.getEmail())) {
				printRtn(request, response, new ResultMessage(true, ""));
				return;
			}
			if (!tntUserService.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, email)) {
				printRtn(request, response, new ResultMessage(false, "该邮箱已被使用，请更换其他邮箱"));
				return;
			}
		}
		if (null != userName) {
			if (!tntUserService.isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME, userName)) {
				printRtn(request, response, new ResultMessage(false, "该用户名已被使用，请更换其他用户名"));
				return;
			}
		}
		printRtn(request, response, new ResultMessage(true, ""));
	}

	/**
	 * 支付手机唯一性检查
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/checkPayMobileUnique.do")
	public void checkPayMobileUnique(Model model, HttpServletRequest request, HttpServletResponse response, String mobile, String oldmobile) throws Exception {
		if (null != mobile) {
			if (oldmobile != null && mobile.equals(oldmobile)) {
				printRtn(request, response, new ResultMessage(false, "手机号已绑定当前账户！"));
				return;
			} else if (!tntCashaccountService.isMobileUnique(mobile)) {
				printRtn(request, response, new ResultMessage(false, "该手机号已被其他账户绑定，请更换其他手机号"));
				return;
			} else {
				printRtn(request, response, new ResultMessage(true, ""));
				return;
			}
		}
		printRtn(request, response, new ResultMessage(true, ""));
		return;
	}

	/**
	 * 找回密码发送邮件，验证输入的验证码
	 * 
	 * @throws IOException
	 *             IOException
	 */
	@RequestMapping(value = "/findpass/validateVerifycode")
	public void validateVerifycode(Model model, HttpServletRequest request, HttpServletResponse response, String mobile, String email, String validateCode) throws IOException {
		ResultMessage rtn = new ResultMessage(true, null);
		if (StringUtil.isEmptyString(validateCode) && !RequstUtil.checkKaptchaCode(request, validateCode, true)) {
			rtn.setSuccess(false);
			rtn.setErrorText("验证码出错");
		}
		model.addAttribute("mobile", mobile);
		model.addAttribute("email", email);
		model.addAttribute("validateCode", validateCode);
		printRtn(request, response, rtn);
	}

	/**
	 * 发送验证码 如果发送目标是手机的话，那么验证码会以短信的形式发送；如果发送目标是邮箱的话，那么
	 * 验证码则以电子邮箱的形式发送。但发送的前提是发送目标必须在Users表中存在相关信息。且 验证码的有效期为30分钟。
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/sendAuthenticationCode")
	public void sendAuthenticationCode(HttpServletRequest request, HttpServletResponse response, String validateCode, String validateType, String mobile, String email, Long userId) throws Exception {
		ResultMessage rtn = new ResultMessage(false, "不是有效的手机或者邮箱，无法成功发送验证码");
		if (null != validateCode) {
			rtn.setErrorText("验证码出错");
			printRtn(request, response, rtn);
			return;
		}

		if (StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
			rtn.setSuccess(false);
			rtn.setErrorText("目标为空，无法发送");
			printRtn(request, response, rtn);
			return;
		}
		// 优先发送短信验证码
		if (StringUtil.validMobileNumber(mobile)) {
			String key = "sendAuthenticationCode" + mobile;
			// 对于手机注册，生成验证码的时候账号还没插入数据库，所以只能自己构造一个USER对象
			TntUser user = new TntUser();
			TntUser loginUser = (TntUser) request.getSession().getAttribute(TntConstant.SESSION_TNT_USER);
			if (loginUser != null) {
				user.setUserId(loginUser.getUserId());
			}
			user.setMobileNumber(mobile);
			validateSmsSendCount(request, key, user, Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_AUTHENTICATION_CODE, rtn);
		} else {
			// 其次发送邮件验证码
			if (StringUtil.validEmail(email)) {
				if (null == userId) {
					rtn.setErrorText("无法找到有效的用户信息, 发送邮件验证码失败");
					printRtn(request, response, rtn);
					return;
				}
				TntUser users = tntUserService.findWithDetailByUserId(userId);
				if (null == users) {
					rtn.setErrorText("无法找到相关用户, 发送邮件验证码失败");
					printRtn(request, response, rtn);
					return;
				}
				users.setEmail(email);

				if (StringUtils.isNotEmpty(validateType)) {
					if (validateType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name())) {
						// 激活邮箱
						tntUserClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name());
					} 
				} else {
					tntUserClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name());
				}
				// 返回登录url add by taiqichao
				rtn.setResult(getMailHost(email));
				rtn.setSuccess(true);
				rtn.setErrorText("");
			}
		}
		printRtn(request, response, rtn);
	}

	/**
	 * 系统校验验证码
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/validateAuthenticationCode")
	public void validateAuthenticationCode(HttpServletRequest request, HttpServletResponse response, String mobile, String email, String authenticationCode) throws Exception {

		USER_IDENTITY_TYPE certCodeType = USER_IDENTITY_TYPE.MOBILE;
		String identityTarget = null;
		if (null != mobile) {
			certCodeType = USER_IDENTITY_TYPE.MOBILE;
			identityTarget = mobile;
		} else if (null != email) {
			if (null != email) {
				certCodeType = USER_IDENTITY_TYPE.EMAIL;
				identityTarget = email;
			}
		}

		// 激活码不存在
		if (StringUtils.isEmpty(authenticationCode)) {
			printRtn(request, response, new ResultMessage(false, "无效的验证码"));
			return;
		}

		// 找不到激活记录或者激活记录对应的对象不符
		TntCertCode certCode = tntUserClient.queryUserCertCode(certCodeType, authenticationCode, false);
		if (null == certCode || !identityTarget.equals(certCode.getIdentityTarget())) {
			printRtn(request, response, new ResultMessage(false, "无效的验证码"));
			return;
		}

		printRtn(request, response, new ResultMessage(true, ""));
	}

	/**
	 * 找回密码发送信息：手机发送验证码短信，邮箱发送重置密码邮件
	 * 
	 * @throws IOException
	 *             IOException
	 */
	@RequestMapping(value = "/findpass/sendMessage", method = { RequestMethod.POST })
	public void sendMessage(HttpServletRequest request, HttpServletResponse response, String mobile, String email, String validateCode) throws IOException {
		ResultMessage rtn = new ResultMessage(false, "不是有效的手机或者邮箱");
		if (StringUtil.isEmptyString(mobile) && StringUtil.isEmptyString(email)) {
			rtn.setErrorText("目标为空，无法发送");
			printRtn(request, response, rtn);
			return;
		}
		/**
		 * 找回密码的手机或邮箱是否存在
		 */
		TntUser users = tntUserService.queryUserByMobileOrEmail(mobile, email);
		if (null == users) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("user is null");
			}
			rtn.setErrorText("无法找到所需的用户信息");
			printRtn(request, response, rtn);
			return;
		}
		/**
		 * 若是手机：发送手机验证码
		 */
		if (StringUtil.validMobileNumber(mobile)) {
			String key = "findPhoneMessage" + mobile;
			validateSmsSendCount(request, key, users, SMS_SSO_TEMPLATE.SMS_MOBILE_RESET_PASSWORD, rtn);
			printRtn(request, response, rtn);
		}
		/**
		 * 若是邮箱：发送重置密码邮件
		 */
		if (StringUtil.validEmail(email)) {
			if (null != validateCode && !RequstUtil.checkKaptchaCode(request, validateCode, true)) {
				rtn.setErrorText("验证码出错");
				printRtn(request, response, rtn);
				return;
			}
			tntUserClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_RESET_PASSWORD.name());
			rtn.setSuccess(true);
			rtn.setErrorText("");
			printRtn(request, response, rtn);
		}
	}

	/**
	 * 重新发送邮箱注册的激活邮件
	 * 
	 * @throws IOException
	 *             IOException
	 */
	@RequestMapping(value = "/resentRegisterEmail")
	public void resentRegisterEmail(HttpServletRequest request, HttpServletResponse response, Long userId) throws IOException {
		TntUser user = tntUserService.findWithDetailByUserId(userId);
		if (user != null) {
			tntUserClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, user, Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name());
			printRtn(request, response, new ResultMessage(true, "邮件已发送"));
		} else {
			printRtn(request, response, new ResultMessage(false, "找不到用户标识"));
		}
	}

	private String getMailHost(String email) {
		String value = "";
		Configuration configuration = Configuration.getConfiguration();
		Properties properties = configuration.getConfig("/mailWWW.properties");
		value = properties.getProperty(email.substring(email.indexOf("@")));
		String userMailHost = value.trim();
		if (StringUtil.isEmptyString(userMailHost)) {
			userMailHost = "http://www." + email.substring(email.indexOf("@") + 1);
		}
		return userMailHost;
	}

	/**
	 * 
	 * @param key
	 *            缓存key
	 * @param users
	 *            发送的相关用户
	 * @param sst
	 *            短信类型
	 * @return -1 获取验证码失败 0.验证次数不通过 1.验证次数通过
	 */
	private void validateSmsSendCount(HttpServletRequest request, String key, TntUser users, SMS_SSO_TEMPLATE sst, ResultMessage rtn) {
		Boolean time_flag = (Boolean) MemcachedUtil.getInstance().get(key + "Time");
		if (time_flag == null) {
			MemcachedUtil.getInstance().set(key + "Time", 60, true);
		} else {
			rtn.setSuccess(false);
			rtn.setErrorText("waiting");
			return;
		}
		String ip = InternetProtocol.getRemoteAddr(request);
		String ip_key = "ipLimit" + ip;
		if (LOG.isDebugEnabled()) {
			LOG.debug("validate sms send count, the ip is " + ip);
		}
		Integer value_ip = (Integer) MemcachedUtil.getInstance().get(ip_key);
		if (value_ip == null) {
			value_ip = 0;
		}
		if (value_ip >= 5) {// 同一个IP地址半小时内发送5次
			if (LOG.isDebugEnabled()) {
				LOG.debug("validate sms send count ,the ip" + ip + " verified failed");
			}
			rtn.setSuccess(false);
			rtn.setErrorText("ipLimit");
			return;
		}
		Integer value = (Integer) MemcachedUtil.getInstance().get(key);// 同一个手机号发送次数限制的key
		if (value == null) {
			value = 0;
		}
		if (value < 5) {
			String code = tntUserClient.sendAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, users, sst.name());
			if (StringUtils.isBlank(code)) {
				rtn.setErrorText("发送短信验证码失败");
			} else {
				MemcachedUtil.getInstance().set(ip_key, 1800, value_ip + 1);
				Calendar currentTime = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				c2.set(Calendar.HOUR_OF_DAY, 24);
				c2.set(Calendar.MINUTE, 00);
				c2.set(Calendar.SECOND, 00);
				c2.set(Calendar.MILLISECOND, 000);
				Long cachedTime = (c2.getTimeInMillis() - currentTime.getTimeInMillis()) / 1000;
				MemcachedUtil.getInstance().set(key, cachedTime.intValue(), value + 1);
				rtn.setSuccess(true);
				rtn.setErrorText("");
				if (LOG.isDebugEnabled()) {
					LOG.debug("validate sms send count ,the mobile " + users.getMobileNumber() + " verified pass");
				}
			}
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("validate sms send count ,the mobile " + users.getMobileNumber() + " verified failed");
			}
			rtn.setSuccess(false);
			rtn.setErrorText("phoneWarning");
		}
	}

	/**
	 * 输出返回码
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param bean
	 *            Ajax返回的基本Bean
	 * @throws IOException
	 *             IOException
	 */
	private void printRtn(final HttpServletRequest request, final HttpServletResponse response, final ResultMessage bean) throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().print(JSONObject.fromObject(bean));
		} else {
			response.getWriter().print(request.getParameter("jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
		}
	}

	/**
	 * 输出值
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param bean
	 *            Ajax返回的基本Bean
	 * @throws IOException
	 *             IOException
	 */
	@SuppressWarnings("unused")
	private void printRtn(final HttpServletRequest request, final HttpServletResponse response, String json) throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().write(json);
		} else {

			response.getWriter().write(request.getParameter("jsoncallback") + "(" + json + ")");
		}
	}

	@RequestMapping(value = "/testRemote")
	public void testRemote(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.print(false);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 唯一性检查
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/checkUnique")
	public void checkUnique(HttpServletRequest request,HttpServletResponse response,String userName,String mobile,String email,String imageCode) throws Exception {
		TntUser user = (TntUser) request.getSession().getAttribute(TntConstant.SESSION_TNT_USER);
		if (null != mobile) {
			if (null != user && mobile.equals(user.getMobileNumber())) {
				printUnique(request , response, new ResultMessage(true, ""));

				return;
			}
			if (!tntUserService.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile)) {
				printUnique(request , response, new ResultMessage(false, "该手机号已被使用，请更换其他手机号"));
				return;
			}
		}
		if (null != email) {
			if (null != user && email.equals(user.getEmail())) {
				printUnique(request, response, new ResultMessage(true, ""));
				return;
			}
			if (!tntUserService.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL,
					email)) {
				printUnique(request, response, new ResultMessage(false,
						"该邮箱已被使用，请更换其他邮箱"));
				return;
			}
		}
		if (null != userName) {
			if (!tntUserService.isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME,
					userName)) {
				printUnique(request, response, new ResultMessage(false,
						"该用户名已被使用，请更换其他用户名"));
				return;
			}
		}
		if (null != imageCode) {
			if (!RequstUtil.checkKaptchaCode(request, imageCode, false)) {
				printUnique(request, response, new ResultMessage(false, ""));
				return;
			}
		}
		printUnique(request, response, new ResultMessage(true, ""));
	}

	private void printUnique(final HttpServletRequest request,
			final HttpServletResponse response, final ResultMessage bean)
			throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if(bean.isSuccess()){
			response.getWriter().print("true");
		}else{
			response.getWriter().print("false");
		}
	}
}
