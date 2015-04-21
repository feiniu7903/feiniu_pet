package com.lvmama.tnt.front.register;

import java.io.IOException;
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

import com.lvmama.tnt.comm.po.TntCertCode;
import com.lvmama.tnt.comm.service.TntCertCodeService;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.front.user.form.UserForm;
import com.lvmama.tnt.front.utils.RequstUtil;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.service.TntCompanyTypeService;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping(value = "/reg")
public class RegisterController extends BaseController {

	@Autowired
	private TntUserService tntUserService;

	@Autowired
	private TntCertCodeService tntCertCodeService;

	@Autowired
	private TntCompanyTypeService tntCompanyTypeService;

	@RequestMapping("/index")
	public String index() {
		return "reg/index";
	}

	@RequestMapping("/reg")
	public String reg_company(Model model) {
		model.addAttribute("userForm", new UserForm());
		model.addAttribute("companyTypes", query());
		return "reg/regCompany";
	}

	@RequestMapping("/regPerson")
	public String reg_person(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "reg/regPerson";
	}

	private List<TntCompanyType> query() {
		TntCompanyType tntCompanyType = new TntCompanyType();
		return tntCompanyTypeService.query(tntCompanyType);
	}

	@RequestMapping(value = "/regSave", method = { RequestMethod.POST })
	public String reg_save(HttpServletRequest req, UserForm userForm,
			Model model, HttpSession session) {
		ResultMessage result = userForm.validate();
		TntUser tntUser = userForm.toTntUser();
		if (result.isSuccess()) {
			result = regCheck(tntUser, session);
		}
		if (!RequstUtil.checkKaptchaCode(req, userForm.getImageCode(), true)) {
			result.setSuccess(false);
			result.setErrorText("验证码输入错误");
		}
		if (!result.isSuccess()) {
			model.addAttribute("userForm", userForm);
			model.addAttribute("result", result);
			if (userForm.isCompany()) {
				model.addAttribute("companyTypes", query());
				return "reg/regCompany";
			}
			return "reg/regPerson";
		}
		// 如果是个人分销商，设置个人分销商类型
		if (tntUser.getDetail().getCompanyTypeId() == null) {
			Long companyTypeId = tntCompanyTypeService.getPersonType();
			tntUser.getDetail().setCompanyTypeId(companyTypeId);
		}
		Long userId = tntUserService.insert(tntUser);
		session.setAttribute(TntConstant.SESSION_TNT_USER,
				tntUserService.findWithDetailByUserId(userId));
		return "redirect:/reg/reging";
	}

	@RequestMapping(value = "/userSave.do", method = { RequestMethod.POST })
	public void user_update(HttpServletRequest req, HttpServletResponse res,
			UserForm userForm, Model model, HttpSession session)
			throws IOException {
		ResultMessage result = userForm.validate();

		TntUser tntUser = userForm.toUpdateTntUser(getLoginUser(session));
		if (result.isSuccess()) {
			result = updateCheck(tntUser, session);
		}
		if (!RequstUtil.checkKaptchaCode(req, userForm.getImageCode(), true)) {
			result.setSuccess(false);
			result.setErrorText("验证码输入错误");
		}
		if (result.isSuccess()) {
			tntUser.setUserId(getLoginUserId(session));
			tntUser.getDetail().setUserDetailId(
					getLoginUser(session).getDetail().getUserDetailId());
			tntUserService.update(tntUser);
			session.setAttribute(TntConstant.SESSION_TNT_USER,
					tntUserService.findWithDetailByUserId(tntUser.getUserId()));
		}
		printRtn(req, res, result);
	}

	@RequestMapping(value = "/userAuditSave.do", method = { RequestMethod.POST })
	public String userAuditSave(HttpServletRequest req,
			HttpServletResponse res, UserForm userForm, Model model,
			HttpSession session) throws IOException {
		ResultMessage result = userForm.validate();

		TntUser tntUser = userForm.toUpdateTntUser(getLoginUser(session));
		if (result.isSuccess()) {
			result = updateCheck(tntUser, session);
		}
		if (!RequstUtil.checkKaptchaCode(req, userForm.getImageCode(), true)) {
			result.setSuccess(false);
			result.setErrorText("验证码输入错误");
		}
		if (result.isSuccess()) {
			tntUser.setUserId(getLoginUserId(session));
			tntUser.getDetail().setUserDetailId(
					getLoginUser(session).getDetail().getUserDetailId());
			if (com.lvmama.tnt.comm.vo.TntConstant.USER_INFO_STATUS.REREJECT
					.name().equalsIgnoreCase(
							tntUser.getDetail().getInfoStatus())) {
				tntUser.getDetail()
						.setFinalStatus(
								com.lvmama.tnt.comm.vo.TntConstant.USER_FINAL_STATUS.WAITING
										.name());
				tntUser.getDetail()
						.setInfoStatus(
								com.lvmama.tnt.comm.vo.TntConstant.USER_INFO_STATUS.REWAITING
										.name());
				if (tntUser != null && tntUser.getDetail() != null) {
					TntUserDetail detail = tntUser.getDetail();
					if (userForm.getEmail() != null
							&& !userForm.getEmail().equalsIgnoreCase(
									detail.getEmail())) {
						tntUser.getDetail()
								.setInfoStatus(
										com.lvmama.tnt.comm.vo.TntConstant.USER_INFO_STATUS.WAITING
												.name());
					}
				}
			} else {
				tntUser.getDetail()
						.setFinalStatus(
								com.lvmama.tnt.comm.vo.TntConstant.USER_FINAL_STATUS.WAITING
										.name());
				tntUser.getDetail()
						.setInfoStatus(
								com.lvmama.tnt.comm.vo.TntConstant.USER_INFO_STATUS.WAITING
										.name());
			}
			tntUserService.update(tntUser);
			session.setAttribute(TntConstant.SESSION_TNT_USER,
					tntUserService.findWithDetailByUserId(tntUser.getUserId()));
		}
		return "reg/auditActive";
	}

	@RequestMapping("/reging")
	public String reging(HttpSession session, Model model) {
		try {
			TntUser tntUser = (TntUser) session
					.getAttribute(TntConstant.SESSION_TNT_USER);
			if (TntConstant.USER_INFO_STATUS.WAITING.name().equalsIgnoreCase(
					tntUser.getDetail().getInfoStatus())) {
				return "/reg/regWaiting";
			}
			if (TntConstant.USER_INFO_STATUS.NEEDACTIVE.name()
					.equalsIgnoreCase(tntUser.getDetail().getInfoStatus())) {
				model.addAttribute("email", tntUser.getEmail());
				model.addAttribute("userId", tntUser.getUserId());
				return "/reg/regActive";
			}
			if (TntConstant.USER_INFO_STATUS.ACTIVED.name().equalsIgnoreCase(
					tntUser.getDetail().getInfoStatus())) {
				return "/reg/regSucceed";
			}
		} catch (Exception e) {
			return "redirect:/login/index";
		}

		return "redirect:/help/index";
	}

	@RequestMapping("/activeCode")
	public String regActive(HttpSession session, String emailCode, Model model) {
		if (StringUtils.isBlank(emailCode)) {
			model.addAttribute("result", "false");
			return "/reg/regSucceed";
		}
		TntCertCode userCertCode = tntCertCodeService.queryUserCertCode(
				USER_IDENTITY_TYPE.EMAIL, emailCode, true);
		if (null == userCertCode || null == userCertCode.getUserId()) {
			model.addAttribute("result", "false");
			return "reg/regSucceed";
		}

		TntUser tntUser = tntUserService.findWithDetailByUserId(userCertCode
				.getUserId());
		Long userId = tntUser.getUserId();
		if (null == tntUser || null == userId) {
			model.addAttribute("result", "false");
			return "reg/regSucceed";
		}
		boolean isActived = tntUserService.activeUser(userId);
		model.addAttribute("result", isActived + "");
		session.removeAttribute(TntConstant.SESSION_TNT_USER);
		return "/reg/regSucceed";
	}

	private ResultMessage regCheck(TntUser tntUser, HttpSession session) {
		ResultMessage result = new ResultMessage();
		TntUser nwewUser = null;
		TntUser user = tntUserService.findWithDetailByUserName(tntUser
				.getUserName());
		if (user != null && user.getUserId() != null) {
			result.setSuccess(false);
			result.setErrorText("该用户名已被使用，请更换其他用户名");
			return result;
		}
		nwewUser = new TntUser();
		nwewUser.getDetail().setMobileNumber(tntUser.getMobileNumber());
		user = tntUserService.findWithDetail(nwewUser);
		if (user != null && user.getUserId() != null) {
			result.setSuccess(false);
			result.setErrorText("该手机号已被使用，请更换其他手机号");
			return result;
		}

		nwewUser = new TntUser();
		nwewUser.getDetail().setEmail(tntUser.getEmail());
		user = tntUserService.findWithDetail(nwewUser);
		if (user != null && user.getUserId() != null) {
			result.setSuccess(false);
			result.setErrorText("该邮箱已被使用，请更换其他邮箱");
			return result;
		}
		return result;
	}

	private ResultMessage updateCheck(TntUser tntUser, HttpSession session) {
		TntUser sUser = getLoginUser(session);
		ResultMessage result = new ResultMessage();
		TntUser nwewUser = new TntUser();
		nwewUser.getDetail().setMobileNumber(tntUser.getMobileNumber());
		TntUser user = tntUserService.findWithDetail(nwewUser);
		if (user != null && !user.getUserId().equals(sUser.getUserId())) {
			result.setSuccess(false);
			result.setErrorText("该手机号已被使用，请更换其他手机号");
			return result;
		}

		nwewUser = new TntUser();
		nwewUser.getDetail().setEmail(tntUser.getEmail());
		user = tntUserService.findWithDetail(nwewUser);
		if (user != null && !user.getUserId().equals(sUser.getUserId())) {
			result.setSuccess(false);
			result.setErrorText("该邮箱已被使用，请更换其他邮箱");
			return result;
		}

		return result;
	}
}
