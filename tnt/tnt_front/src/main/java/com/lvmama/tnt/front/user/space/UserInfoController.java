package com.lvmama.tnt.front.user.space;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.front.user.form.UserForm;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntCompanyTypeService;
import com.lvmama.tnt.user.service.TntUserMaterialService;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping(value = "/user")
public class UserInfoController extends BaseController {

	@Autowired
	private TntUserService tntUserService;

	@Autowired
	TntUserMaterialService tntUserMaterialService;

	@Autowired
	private TntCashaccountService tntCashaccountService;
	@Autowired
	private TntCompanyTypeService tntCompanyTypeService;

	@RequestMapping(value = "/index.do")
	public String index(HttpSession session, Model model) {
		return "userspace/userinfo/baseInfo";
	}

	@RequestMapping(value = "/updatePasswordPage.do")
	public String updatePassword(HttpSession session, Model model, String ispay) {
		model.addAttribute("ispay", ispay);
		model.addAttribute("canShowpay", true);
		TntCashAccount cashCount = tntCashaccountService
				.getAccountByUserId(getLoginUserId(session));
		if (cashCount == null
				|| StringUtil.isEmptyString(cashCount.getPaymentPassword())) {
			model.addAttribute("canShowpay", false);
		}
		model.addAttribute("tntCashAccount", cashCount);
		return "userspace/userinfo/upPassword";
	}

	@RequestMapping(value = "/validOldPwd.do")
	public void validOldPwd(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model,
			String oldLoginPass) throws Exception {
		TntUser tntUser = getLoginUser(session);
		if (StringUtil.isEmptyString(oldLoginPass)
				|| !tntUser.getLoginPassword().equals(
						MD5.code(oldLoginPass, MD5.KEY_TNT_USER_PASSWORD))) {
			printRtn(request, response, new ResultMessage(false, "登录密码不正确"));
			return;
		}
		printRtn(request, response, new ResultMessage(true, ""));
		return;
	}

	@RequestMapping(value = "/savePassword.do", method = RequestMethod.POST)
	public String savePassword(HttpSession session, HttpServletRequest request) {
		String password = request.getParameter("loginPasswordTwo");
		if (StringUtils.isNotEmpty(password)) {
			TntUser tntUser = new TntUser(getLoginUserId(session));
			password = MD5.code(password, MD5.KEY_TNT_USER_PASSWORD);
			tntUser.setLoginPassword(password);
			tntUserService.updatePassword(tntUser);
			// session.setAttribute(TntConstant.SESSION_TNT_USER, null);
		}
		return "redirect:/login/index";
	}

	@RequestMapping(value = "/baseInfo.do")
	public String baseInfo(HttpSession session, Model model) {
		TntUser tntUser = tntUserService
				.findWithDetailByUserId(getLoginUserId(session));
		if (tntUser == null) {
			return "redirect:/login/index";
		}
		model.addAttribute("userForm", new UserForm(tntUser));
		if ("true".equalsIgnoreCase(tntUser.getIsCompany())) {
			model.addAttribute("companyTypes", query());
			return "userspace/userinfo/companyBaseInfo";
		}
		return "userspace/userinfo/personBaseInfo";
	}

	@RequestMapping("/auditInfo.do")
	public String auditInfo(HttpSession session,Model model) {
		TntUser tntUser = tntUserService
				.findWithDetailByUserId(getLoginUserId(session));
		model.addAttribute("userForm", new UserForm(tntUser));
		model.addAttribute("userDetail", tntUser.getDetail());
		if ("true".equalsIgnoreCase(tntUser.getIsCompany())) {
			model.addAttribute("companyTypes", query());
			return "reg/auditCompanyBaseInfo";
		}
		return "reg/auditPersonBaseInfo";
	}
	
	private List<TntCompanyType> query() {
		TntCompanyType tntCompanyType = new TntCompanyType();
		return tntCompanyTypeService.query(tntCompanyType);
	}
}
