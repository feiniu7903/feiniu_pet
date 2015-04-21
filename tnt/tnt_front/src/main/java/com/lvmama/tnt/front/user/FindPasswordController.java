package com.lvmama.tnt.front.user;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.comm.utils.Configuration;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.comm.po.TntCertCode;
import com.lvmama.tnt.comm.service.TntCertCodeService;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntUserService;

/**
 * 找回密码统一入口
 * 
 * @author gaoxin
 * 
 */
@Controller
@RequestMapping(value = "/findpass")
public class FindPasswordController extends BaseController {
	private static final Log log = LogFactory
			.getLog(FindPasswordController.class);

	@Autowired
	private TntUserService tntUserService;
	/**
	 * super 用户校验码服务
	 */
	@Autowired
	private TntCertCodeService tntCertCodeService;

	/**
	 * 找回密码首页
	 * 
	 * @return 首页标识
	 */
	@RequestMapping(value = "/index")
	public String index() {
		return "/findPassword/index";
	}

	/**
	 * 找回密码方式：手机或邮箱
	 * 
	 * @return 返回手机或邮箱找回密码首页
	 */
	@RequestMapping(value = "/findType")
	public String findType(Model model, String email, String mobile) {
		if (!(StringUtils.isNotEmpty(email) || StringUtils.isNotEmpty(mobile))) {
			log.error("Can't find password for empty mobile or email");
			return "/findPassword/index";
		}
		model.addAttribute("mobile", mobile);
		model.addAttribute("email", email);
		return "/findPassword/findPassByType";
	}

	/**
	 * 填写新密码
	 * 
	 * @return 设置新密码页面标识
	 */
	@RequestMapping(value = "/fillNewPass")
	public String toFillNewPass(Model model, String mobile, String email,
			String recallId) {
		if (StringUtil.validMobileNumber(mobile)) {
			return "/findPassword/fillNewPass";
		}

		TntCertCode emailCode = tntCertCodeService.queryUserCertCode(
				USER_IDENTITY_TYPE.EMAIL,
				StringUtils.isNotEmpty(recallId) ? recallId : "0", false);
		email = (emailCode != null) ? emailCode.getIdentityTarget() : "";
		if (StringUtils.isNotEmpty(email)) {
			model.addAttribute("email", email);
			model.addAttribute("recallId", recallId);
			return "/findPassword/fillNewPass";
		}

		if (log.isDebugEnabled()) {
			log.debug("Can't find password for empty mobile or email");
		}

		return "/error";
	}

	/**
	 * 保存新密码
	 * 
	 * @return 保存新密码后跳转页面
	 */
	@RequestMapping(value = "/saveNewPass", method = { RequestMethod.POST })
	public String saveNewPass(Model model, String password, String mobile,
			String email, String authenticationCode, String recallId) {
		if (!(StringUtil.validMobileNumber(mobile) || StringUtil
				.validEmail(email))) {
			log.debug("Can't find password for empty mobile or email");
			return "index";
		}
		TntUser users = tntUserService.queryUserByMobileOrEmail(mobile, email);
		if (null == users) {
			log.debug("Cann't find user");
			return "/error";
		}

		boolean checkCodeResult = false;
		if (!StringUtils.isEmpty(mobile)) {
			checkCodeResult = tntCertCodeService.validateAuthenticationCode(
					USER_IDENTITY_TYPE.MOBILE, authenticationCode, mobile);
		} else if (!StringUtils.isEmpty(email)) {
			String code = StringUtils.isNotEmpty(recallId) ? recallId : "0";
			checkCodeResult = tntCertCodeService.validateAuthenticationCode(
					USER_IDENTITY_TYPE.EMAIL, code, email);
			model.addAttribute("email", email);
		}

		if (checkCodeResult) {
			// 保存用户新密码
			password = MD5.code(password, MD5.KEY_TNT_USER_PASSWORD);
			users.setLoginPassword(password);
			tntUserService.updatePassword(users);
			return "/findPassword/success";
		} else {
			log.error("find password authenticate fail");
			return "/error";
		}
	}

	/**
	 * 发送重置密码邮件成功
	 * 
	 * @return 重置密码邮件成功页面
	 */
	@RequestMapping(value = "/sendResetEmailSucc")
	public String sendResetEmailSucc(Model model, String email) {
		if (StringUtil.validEmail(email)) {
			String userMailHost = handleUserMailHost(email);
			model.addAttribute("userMailHost", userMailHost);
			model.addAttribute("email", email);
			return "/findPassword/resetEmailSucc";
		}
		return "/error";
	}

	/**
	 * 根据用户邮箱，获得邮箱主站地址
	 */
	private String handleUserMailHost(String email) {
		String value = "";
		Configuration configuration = Configuration.getConfiguration();
		Properties properties = configuration.getConfig("/mailWWW.properties");
		value = properties.getProperty(email.substring(email.indexOf("@")));
		String userMailHost = value == null ? "" :value.trim();
		if (StringUtil.isEmptyString(userMailHost)) {
			userMailHost = "http://www."
					+ email.substring(email.indexOf("@") + 1);
		}
		return userMailHost;
	}

}
