package com.lvmama.tnt.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.front.utils.RequstUtil;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
public class LoginController extends BaseController {
	private static final Log LOG = LogFactory.getLog(LoginController.class);

	@Autowired
	private TntUserService tntUserService;

	@RequestMapping(value = "login/index")
	public String index(@RequestParam(required = false) String service,
			Model model) {
		/**
		 * 增加未登录跳转
		 * 
		 * hupeipei
		 */
		if (service != null)
			model.addAttribute("service", service);
		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res,
			HttpSession session) {
		session.removeAttribute(TntConstant.SESSION_TNT_USER);
		return "redirect:index";
	}

	private ResultMessage login(HttpServletRequest req, TntUser user,
			String imageCode) {
		ResultMessage result = new ResultMessage();
		String error = null;
		if (user == null) {
			error = "用户为空";
		} else {
			if (!RequstUtil.checkKaptchaCode(req, imageCode, true)) {
				error = "验证码错误";
			} else {
				TntUser tntUser = tntUserService.findWithDetailByUserName(user
						.getUserName());
				if (tntUser == null
						|| StringUtil.isEmptyString(user.getLoginPassword())
						|| !tntUser.getLoginPassword().equals(
								MD5.code(user.getLoginPassword(),
										MD5.KEY_TNT_USER_PASSWORD))) {
					error = "用户名或密码错误";
				} else if (tntUser.getDetail() != null
						&& TntConstant.USER_FINAL_STATUS.isNotDoing(tntUser
								.getDetail().getFinalStatus())) {
					String finalStatus = tntUser.getDetail().getFinalStatus();
					if (TntConstant.USER_FINAL_STATUS.isReject(finalStatus)) {
						error = "驴妈妈拒绝与您合作！";
					} else if (TntConstant.USER_FINAL_STATUS.isEnd(finalStatus)) {
						error = "驴妈妈已经终止与您合作！";
					}
				} else {
					req.getSession().setAttribute(TntConstant.SESSION_TNT_USER,
							tntUser);
				}
			}
		}
		result.setSuccess(error == null);
		result.setErrorText(error);
		return result;
	}

	@RequestMapping(value = "/login")
	public String login(Model model, TntUser user, HttpServletRequest req,
			HttpServletResponse res, String imageCode) throws Exception {
		ResultMessage result = login(req, user, imageCode);
		if (!result.isSuccess()) {
			model.addAttribute("loginError", result.getErrorText());
			return "login";
		} else {
			String service = req.getParameter("service");
			if (service != null && !service.isEmpty()) {
				res.sendRedirect(service);
				return null;
			}
			return "redirect:/index";
		}
	}

	@RequestMapping(value = "/ajaxLogin")
	public void ajaxLogin(TntUser user, HttpServletRequest req,
			HttpServletResponse res, String imageCode) throws Exception {
		ResultMessage result = login(req, user, imageCode);
		printRtn(req, res, result);
	}

	@RequestMapping(value = "/checkLogin")
	public void isLogin(HttpServletResponse response, HttpSession session) {
		boolean isLogin = getLoginUser(session) != null;
		sendAjaxResultByJson(response, Boolean.toString(isLogin));
	}
	
	//http://localhost/autoLogin?userId=1601&_=1401861101740
	//@RequestMapping(value = "/autoLogin")
	public String autologin(HttpServletRequest req, TntUser user) {
		TntUser tntUser = tntUserService.findWithDetailByUserId(user.getUserId());
		req.getSession().setAttribute(TntConstant.SESSION_TNT_USER,tntUser);
		return "redirect:/user/index.do";
	}
}
