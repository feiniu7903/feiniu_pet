package com.lvmama.back.sweb;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.web.util.WebUtils;

import com.lvmama.back.utils.Pagination;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.JsonMsg;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.UploadUtil;
import com.lvmama.comm.vo.Constant;
import com.opensymphony.xwork2.ActionSupport;

@Results({
	@Result(name = "login", type="redirect", location = "login.do")
	})
public abstract class BaseAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9197369313409325055L;
	
	protected Log LOG = LogFactory.getLog(this.getClass());
	
	private Constant constant = Constant.getInstance();
	/**
	 * Pagination
	 */
	protected Pagination pagination;
	
	private static final int BUFFER_SIZE = 16 * 1024;
	
	public String getBasePath() {
		String basePath = this.getRequest().getContextPath() + "/";
		return basePath;
	}
	
	public String getOperatorId() {
		return this.getSessionUser().getUserId().toString();
	}
	
	private Long getOperatorLongId() {
		return this.getSessionUser().getUserId();
	}
	
	/**
	 * 把指定的文件上传到专用的静态文件服务器上，返回URL
	 * @param file 
	 * @return
	 */
	public String postToRemote(File f, String filename) {
		return UploadUtil.uploadFile(f, filename);
	}
	
	public JsonMsg setJsonMsg(String key,String value){
		JsonMsg jm = new JsonMsg();
		jm.setKey(key);
		jm.setValue(value);
		return jm;
	}
	public String getUserId(){
		return (String)getSession().getAttribute(Constant.SESSION_NAME_TYPE.SESSION_USER_ID.name());
	}
	
	public String getBookerUserId(){
		if(getUserId()!=null && !getUserId().equals("") && getUserId().length()==32){
			return this.getUserId();
		}else {
			return Constant.DEFUALT_BOOKER_USER_ID;
		}
	}
	
	/**
	 * 初始化当前页和Pagination对象
	 */
	protected Pagination initPagination() {
		// request url method
		String currentPage = getRequest().getParameter("page");
		String perPageRecord = getRequest().getParameter("perPageRecord");
		int page = 1;
		if (currentPage != null && !"".equals(currentPage)) {
			page = Integer.parseInt(currentPage);
		}
		// 构建新的Pagination对象
		pagination = new Pagination();
		int iperPageRecord = pagination.getPerPageRecord();
		if (perPageRecord != null && !"".equals(perPageRecord)) {
			iperPageRecord = Integer.parseInt(perPageRecord);
		}
		if(iperPageRecord!=pagination.getPerPageRecord()){
			pagination.setPerPageRecord(iperPageRecord);
			pagination.setPage(page);
		}else{
			pagination.setPage(page);	
		}				
		return pagination;
	}

	
	/**
	 * 发送Ajax请求结果
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendAjaxResult(String json) {

		this.getResponse().setContentType("text/html;charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送Ajax请求结果json
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendAjaxResultByJson(String json) {
		this.getResponse().setContentType("application/json;charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendAjaxMsg(String msg){
		try {
			this.getResponse().getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMethod() {
		return ServletActionContext.getRequest().getMethod();
	}
	
	public boolean isLogined() {
		PermUser user = (PermUser)this.getSessionUser();
		if (user!=null && user.getPermUserPass()){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 获取当前登录用户
	 * @return
	 */
	protected PermUser getSessionUser(){
		PermUser user = (PermUser)this.getSession(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		return user;
	}
	
	/**
	 * 获得session中的DepartmentId
	 * 
	 * @return
	 */
	public Long getSessionUserDepartmentId() {
		return getSessionUser().getDepartmentId();
	}
	
	public String getOperatorName() {
		return getSessionUser().getUserName();
	}
	
	/**
	 * 取当前登录的用户名称.带检测是否登录的功能
	 * @return
	 */
	protected String getOperatorNameAndCheck(){
		if(isLogined()){
			return getOperatorName();
		}
		throw new UserAnonymousException();
	}
	
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	protected void putSession(String key, Object obj) {
		ServletUtil.putSession(getRequest(), getResponse(), key, obj);
	}
	
	protected Object getSession(String key) {
		return ServletUtil.getSession(getRequest(), getResponse(), key);
	}
	
	protected void removeSession(String key){
		ServletUtil.removeSession(getRequest(), getResponse(), key);
	}
	
	private String getCookieValue(final String cookieName){
		Cookie cookie=WebUtils.getCookie(getRequest(), cookieName);
		if(cookie==null){
			return null;
		}
		
		return cookie.getValue();
	}
	
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}

	public Cookie[] getCookies() {
		return ServletActionContext.getRequest().getCookies();
	}
	
	protected void saveMessage(String msg) {
		ServletActionContext.getRequest().setAttribute("messages", msg);
	}

	protected void errorMessage(String msg) {
		ServletActionContext.getRequest().setAttribute("errorMessages", msg);
	}

	public Constant getConstant() {
		return constant;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
