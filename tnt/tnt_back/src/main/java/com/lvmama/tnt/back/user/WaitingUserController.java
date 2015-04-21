package com.lvmama.tnt.back.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.tnt.comm.util.UserClient;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.recognizance.po.TntRecognizance;
import com.lvmama.tnt.recognizance.service.TntRecognizanceService;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;

@Controller
@RequestMapping("/user/waiting")
public class WaitingUserController extends AbstractUserController {

	@Autowired
	private TntRecognizanceService tntRecognizanceService;

	@Autowired
	private UserClient tntUserClient;

	protected void init(Model model) {
		model.addAttribute("infoStatusMap",
				TntConstant.USER_INFO_STATUS.toMap());
		model.addAttribute("materialStatusMap",
				TntConstant.USER_MATERIAL_STATUS.toMap());
		model.addAttribute("payTypeMap", getTntUserService().getPayTypeMap());
	}

	@Override
	protected String getStatus() {
		return TntConstant.USER_FINAL_STATUS.WAITING.getValue();
	}

	/** 分销商基本信息审核通过 **/
	@RequestMapping(method = RequestMethod.PUT)
	public String agree(TntUser t, Model model) {
		try {
			if (agree(t.getUserId())) {
				// 发送激活邮件
				t = getTntUserService().findWithDetailByUserId(t.getUserId());
				if (t != null
						&& t.getDetail() != null
						&& TntConstant.USER_INFO_STATUS.isNeedActivate(t
								.getDetail().getInfoStatus()))
					tntUserClient.sendAuthenticationCode(
							USER_IDENTITY_TYPE.EMAIL, t,
							TntConstant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE
									.name());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/waiting";
	}

	/** 分销商基本信息审核不通过 **/
	@RequestMapping(method = RequestMethod.DELETE)
	public String reject(TntUser t, Model model) {
		try {
			if (reject(t)) {
				t = getTntUserService().findWithDetailByUserId(t.getUserId());
				tntUserClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL,
						t,
						TntConstant.EMAIL_SSO_TEMPLATE.INFORMATION_UNQUALIFIED
								.name());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:waiting";
	}

	/** 显示终审界面 **/
	@RequestMapping(value = "/final/{userId}")
	protected String toFinalApprove(@PathVariable Long userId, Model model,
			HttpServletRequest request) {
		TntUser tntUser = getTntUserService().findWithDetailByUserId(userId);
		boolean materialed = false;
		boolean limitsed = false;
		if (tntUser != null) {
			TntUserDetail detail = tntUser.getDetail();
			materialed = TntConstant.USER_MATERIAL_STATUS.isAgreed(detail
					.getMaterialStatus());
			TntRecognizance tr = tntRecognizanceService.getByUserId(userId);
			limitsed = (tr != null && tr.getBalance() >= tr.getLimits())
					|| tr == null;

			model.addAttribute(detail);
			model.addAttribute("materialed", materialed);
			model.addAttribute("limitsed", limitsed);
		}
		return "/user/finalApprove";
	}

	/** 终审 **/
	@RequestMapping(value = "final", method = RequestMethod.POST)
	protected String finalApprove(TntUserDetail tntUserDetail, Model model,
			HttpServletRequest request) {
		Long userId = tntUserDetail.getUserId();
		String status = tntUserDetail.getFinalStatus();
		if ("1".equals(status))
			getTntUserService().finalAgree(userId);
		else
			getTntUserService().finalReject(userId,
					tntUserDetail.getFailReason());
		return "redirect:/user/" + getPage();
	}

	/** 设置支付方式 **/
	@RequestMapping(value = "/payType", method = RequestMethod.POST)
	protected String setPayType(TntUser tntUser) {
		TntUserDetail tntUserDetail = tntUser.getDetail();
		getTntUserService().setPayType(tntUserDetail.getUserId(),
				tntUserDetail.getPayType(), null);
		return "redirect:/user/waiting";
	}

	protected boolean agree(Long userId) {
		return getTntUserService().agree(userId);
	}

	protected boolean reject(TntUser t) {
		return getTntUserService().reject(t);
	}

	@Override
	protected String getPage() {
		return "waiting";
	}
}
