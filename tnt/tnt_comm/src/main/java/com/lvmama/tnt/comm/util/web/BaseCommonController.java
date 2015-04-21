package com.lvmama.tnt.comm.util.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.comm.vo.ResultGod;

public class BaseCommonController {

	@Autowired
	private ComLogService comLogService;

	protected String getMethod() {
		return getRequest().getMethod();
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> getParameters() {
		return getRequest().getParameterMap();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected Object getRequestAttribute(String key) {
		return ServletActionContext.getRequest().getAttribute(key);
	}

	protected String getRequestParameter(String key) {
		return getRequest().getParameter(key);
	}

	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	protected Object getSession(final String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}

		return ServletUtil.getSession(HttpServletLocalThread.getRequest(),
				HttpServletLocalThread.getResponse(), key);
	}

	protected void putSession(final String key, final Object obj) {
		ServletUtil.putSession(HttpServletLocalThread.getRequest(),
				HttpServletLocalThread.getResponse(), key, obj);
	}

	protected boolean hasLocalServer() {
		String host = HttpServletLocalThread.getRequest().getLocalName();
		if (host.contains("127.0.0.1") || host.contains("")) {
			return true;
		}
		return false;
	}

	protected Cookie getCookie(final String key) {
		return ServletUtil.getCookie(HttpServletLocalThread.getRequest(), key);
	}

	/**
	 * 发送Ajax请求结果json
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendAjaxResultByJson(HttpServletResponse response, String json) {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
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
	public void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, final ResultGod<?> bean) {
		response.setContentType("text/json; charset=gb2312");
		try {
			if (request.getParameter("jsoncallback") == null) {
				response.getWriter().print(JSONObject.fromObject(bean));
			} else {
				response.getWriter().print(
						request.getParameter("jsoncallback") + "("
								+ JSONObject.fromObject(bean) + ")");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void insertComLog(HttpServletRequest request,
			HttpServletResponse response, String content, String logName,
			String logType, Long objectId, String objectType) {
		try {
			PermUser user = (PermUser) ServletUtil.getSession(request,
					response, com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			if (user != null) {
				ComLog comlog = new ComLog();
				comlog.setContent(content);
				comlog.setContentType(Constant.COM_LOG_CONTENT_TYPE.VARCHAR
						.name());
				comlog.setCreateTime(new Date());
				comlog.setLogName(logName);
				comlog.setLogType(logType);
				comlog.setObjectId(objectId);
				comlog.setObjectType(objectType);
				comlog.setOperatorName(user.getUserName());
				comLogService.addComLog(comlog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String getUserName(HttpServletRequest request,
			HttpServletResponse response) {
		PermUser user = (PermUser) ServletUtil.getSession(request, response,
				com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		return user != null ? user.getUserName() : null;
	}
}
