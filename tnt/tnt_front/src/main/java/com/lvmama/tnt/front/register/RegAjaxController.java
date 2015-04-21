package com.lvmama.tnt.front.register;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping(value = "/regAjax")
public class RegAjaxController {

	@Autowired
	private TntUserService tntUserService;

	@RequestMapping("/checkUserName")
	public void checkUserName(String userName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ResultMessage result = new ResultMessage();
		if (StringUtil.isEmptyString(userName)) {
			result.setSuccess(false);
			result.setErrorText("请输入用户名");
		}
		if (result.isSuccess()) {
			TntUser user = tntUserService.findWithDetailByUserName(userName);
			if (user != null && user.getUserId() != null) {
				result.setSuccess(false);
				result.setErrorText("该用户名已被使用，请更换其他用户名");
			}
		}
		printRtn(request, response, result);
	}

	@RequestMapping("/checkEmail")
	public void checkEmail(String email, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ResultMessage result = new ResultMessage();
		if (StringUtil.isEmptyString(email)) {
			result.setSuccess(false);
			result.setErrorText("请输入邮箱");
		}
		if (result.isSuccess()) {
			TntUser tntUser = new TntUser();
			tntUser.getDetail().setEmail(email);
			TntUser user = tntUserService.findWithDetail(tntUser);
			if (user != null && user.getUserId() != null) {
				result.setSuccess(false);
				result.setErrorText("该邮箱已被使用，请更换其他邮箱");
			}
		}
		printRtn(request, response, result);
	}

	@RequestMapping("/checkMobile")
	public void checkMobile(String mobile, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ResultMessage result = new ResultMessage();
		if (StringUtil.isEmptyString(mobile)) {
			result.setSuccess(false);
			result.setErrorText("请输入手机号");
		}
		if (result.isSuccess()) {
			TntUser tntUser = new TntUser();
			tntUser.getDetail().setMobileNumber(mobile);
			TntUser user = tntUserService.findWithDetail(tntUser);
			if (user != null && user.getUserId() != null) {
				result.setSuccess(false);
				result.setErrorText("该手机号已被使用，请更换其他手机号");
			}
		}
		printRtn(request, response, result);
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
	private void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, final ResultMessage bean)
			throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().print(JSONObject.fromObject(bean));
		} else {
			ServletActionContext
					.getResponse()
					.getWriter()
					.print(ServletActionContext.getRequest().getParameter(
							"jsoncallback")
							+ "(" + JSONObject.fromObject(bean) + ")");
		}
	}

}
