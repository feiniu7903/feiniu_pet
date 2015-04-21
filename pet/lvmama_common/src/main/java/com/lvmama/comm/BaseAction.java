package com.lvmama.comm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.lvmama.comm.utils.ServletUtil;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -209605848524004148L;
	protected Logger log = Logger.getLogger(this.getClass());
	
	protected String ALIPAY_OPEN_PRIVATE_KEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO3kL7Bas7o4TGgcDvEXXzuw7cWeNLHKVt+d3ba9tcGE2HkAwYCcCFy+V5SgmeKu/WzcKlsQgqEuoxbbR2vbZQevgXTjp86KcJEiO5lJMxT/ddcXBWQ1b30YPpwWrEc4kCpD9AffrSwd2A+sb1KlgccZxsAIW+RW2qBkw9FiGxgFAgMBAAECgYBvjL+b93wthza03utWu/npGJb2QrA86j+AThtoFF9FJlEHtIYIE6KMMqkIxaJDPIETac3ms8A9aowJLLu55L/jSjkBPEbSsG0qsrWwJKMWjj9WyMR8b/7D8IxIzNJL01DHqcSV5mhruebS2AgjiGnJ6dQuntjoIUiLsL5LzybrgQJBAPvsh8Dd7tCTwHRccS6GtcHfjx6AA5qvZ1ouJZLirOhEEx3Nq7Fp99QTm5uTpnOcRKFkL2UycgCshbIHY5iOSaECQQDxvYhvr4AVTDUiyxTszfM31wheH8DzBn5cVs9S4zaWACSOazRx60BUxonAPtUZxOblVBursBLYIMA+rX8LR1vlAkEAzdaZpTCw7KOM4IaXw9g1sk8j9VvLlhwzai/Ca53igXT92aAu5SscX6AVyKx/mH7aZLQjNaBeHcbZBnmbGw9HgQJACCfPyYzPMmi+xiy5yP9tvC2J/7rshIZsjury1aYKumbI1PEy9MDuC64jCZRVFih5bbU3tcQb7qS0N6kcxyGiLQJBAN5Uo5cehY7gcL9Dw7YyI3iizh9lje0/1RMhEpiQXrnrBaxeKYLbKfUC4lsW8fNw9I0+WYbMWQlFW2uis3s2/r0=";
	protected String ALIPAY_OPEN_APPID = "2013081900000871";
	protected String ALIPAY_OPEN_URL="https://openapi.alipay.com/gateway.do";
	protected String ALIPAY_PARTNER="2088001842589142";
	
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
	public void putSession(String key, Object obj) {
		ServletUtil.putSession(getRequest(), getResponse(), key, obj);
	}
	
	protected Object getSession(String key) {
		return ServletUtil.getSession(getRequest(), getResponse(), key);
	}
	
	public void removeSession(String key) {
		ServletUtil.removeSession(getRequest(),getResponse(),key);
	}
	
	protected void saveMessage(String msg) {
		getRequest().setAttribute("messages", msg);
	}

	protected void errorMessage(String msg) {
		getRequest().setAttribute("errorMessages", msg);
	}
	
	protected String getCookieValue(String cookieName){
		Cookie cookie = ServletUtil.getCookie(getRequest(), cookieName);
		return cookie==null?null:cookie.getValue();
	}
	 
	public Cookie[] getCookies() {
		return ServletActionContext.getRequest().getCookies();
	}
	public void sendAjaxMsg(String msg){
		this.getResponse().setContentType("text/html;charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(msg);
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
	
	public Map<String, String> getPureParaPair() {
		log.info("getPureParaPair= "+ServletActionContext.getRequest().getQueryString());
		Map map = ServletActionContext.getRequest().getParameterMap();
		Map<String, String> result = new HashMap<String, String>();
		for (String key : (Set<String>)map.keySet()) {
			String[] values = (String[])map.get(key);
			result.put(key, values[0]);
			log.info(key + " = " + values[0]);
		}
		return result;
	}
	
	protected void setRequestAttribute(String key, Object value){
		getRequest().setAttribute(key, value);
	}
	/**
	 * 将文件写入附件
	 * 
	 * @author: ranlongfei 2012-12-17 下午8:34:33
	 * @param destFileName
	 * @param attachmentName
	 */
	public void writeAttachment(String destFileName, String attachmentName) {
		FileInputStream fin=null;
		OutputStream os=null;
		try{
			File f = new File(destFileName);
			if(attachmentName == null || "".equals(attachmentName)) {
				attachmentName = f.getName();
			}
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + attachmentName + ".xls");
			getResponse().setContentType("application/vnd.ms-excel");
			os=getResponse().getOutputStream();
			fin=new FileInputStream(f);
			IOUtils.copy(fin, os);
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}
	public String getReauestHostName(){
		return getRequest().getHeader("Host");
	}
}
