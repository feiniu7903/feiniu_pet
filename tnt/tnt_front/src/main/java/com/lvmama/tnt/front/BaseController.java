package com.lvmama.tnt.front;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lvmama.tnt.comm.util.web.BaseCommonController;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntUser;

public class BaseController extends BaseCommonController {

	/**
	 * 提取前台登录用户ID
	 * 
	 * @return
	 */
	public Long getLoginUserId(HttpSession session) {
		TntUser user = getLoginUser(session);
		return user == null ? null : user.getUserId();
		// return 1461l;
	}

	protected boolean isLogin(HttpSession session) {
		return null != getLoginUser(session);
	}

	/**
	 * 取登录信息
	 * 
	 * @return
	 */
	public TntUser getLoginUser(HttpSession session) {
		TntUser user = (TntUser) session
				.getAttribute(TntConstant.SESSION_TNT_USER);
		return user;
	}

	/**
	 * 输出返回码
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param object
	 *            Ajax返回的对象
	 * @throws IOException
	 *             IOException
	 */
	protected void printRtn(final Object object, HttpServletRequest req,
			HttpServletResponse Resp) throws IOException {
		String json = null;
		Resp.setContentType("text/json; charset=utf-8");
		if (null == object) {
			return;
		} else {
			if (object instanceof java.util.Collection) {
				json = JSONArray.fromObject(object).toString();
			} else {
				json = JSONObject.fromObject(object).toString();
			}
		}
		Resp.getWriter().print(
				req.getParameter("jsoncallback") + "(" + json + ")");
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
	public void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, final ResultMessage bean)
			throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().print(JSONObject.fromObject(bean));
		} else {
			response.getWriter().print(
					request.getParameter("jsoncallback") + "("
							+ JSONObject.fromObject(bean) + ")");
		}
	}

	public String getBasePath(HttpServletRequest req) throws IOException {
		return req.getScheme() + "://" + req.getServerName()
				+ req.getContextPath();
	}

	protected void outputJsonMsg(final HttpServletRequest request,
			final HttpServletResponse response, String jsonStr)
			throws IOException {
		String callback = request.getParameter("callback");
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().println(callback + "(" + jsonStr + ")");
	}
}
