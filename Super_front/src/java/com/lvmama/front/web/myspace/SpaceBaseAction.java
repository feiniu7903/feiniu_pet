package com.lvmama.front.web.myspace;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;
import com.lvmama.front.web.ajax.AjaxRtnBean;
/**
 * @author shangzhengyuan
 * @description 我的驴妈妈改版基类Action
 * @time 20120824
 */
@Results({
	@Result(name = "login", location = "http://login.lvmama.com/nsso/login?service=${url}",type="redirect"),
	@Result(name=com.opensymphony.xwork2.Action.ERROR,params={"status", "404"},type="dispatcher"),
	@Result(name = "contactCustomService", location = "/WEB-INF/pages/myspace/sub/personalInformation/contactCustomService.ftl", type = "freemarker")
})
@SuppressWarnings("serial")
public class SpaceBaseAction extends BaseAction {
	public static final String LOGIN = "login";
	
	private List<Map<String,Object>> navList = new ArrayList<Map<String,Object>>();
	private boolean contentFooter = false;
	public List<Map<String,Object>> getNavList(){
		return navList;
	}
	public void sendAjaxMsg(final AjaxRtnBean bean){
		ServletActionContext.getResponse().setContentType("text/json; charset=UTF-8");
		try{
			if (ServletActionContext.getRequest().getParameter("jsoncallback") == null) {
				ServletActionContext.getResponse().getWriter().print(JSONObject.fromObject(bean));
			} else {
				ServletActionContext.getResponse().getWriter().print(ServletActionContext.getRequest().getParameter("jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
			}
		}catch(IOException e){
			LOG.warn("我的驴妈妈AJAX方法出错：\r\n"+e);
		}
	}
	public String getUrl(){
		return "http://www.lvmama.com"+getRequest().getRequestURI();
	}
	
	/**
	 * 记录用户修改用户信息的行为（修改密码/用户名/手机号/EMAIL）
	 * @param user
	 * @param action
	 * @param memo
	 */
	protected void collectModifyUserInfoAction(UserUser user,  String action, String memo)
	{
		UserActionCollectionService userActionCollectionService = getUserActionCollectionService();
		if (null != userActionCollectionService) {
			userActionCollectionService.save(user.getId(), InternetProtocol.getRemoteAddr(getRequest()),InternetProtocol.getRemotePort(getRequest()) ,action, memo);
		}
	}
	
	
	protected void syncBBS(UserUser user) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("update BBS account");
			}		
			String bbsUrl = Constant.getInstance().getBBSUrl();
			if(StringUtils.isNotEmpty(bbsUrl))
			{
				LOG.info("sync bbs:"+user.getUserId());
				StringBuffer sb = new StringBuffer(bbsUrl+"/api/sync.php?action=update");
				sb.append("&username=").append(URLEncoder.encode(user.getUserName(), "utf-8"))
					.append("&password=").append(user.getRealPass()).append("&user_id=").append(user.getUserId())
					.append("&ip=").append("");
				if (LOG.isDebugEnabled()) {
					LOG.debug("submit data：" + sb.toString());
				}
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
				GetMethod getMethod = new GetMethod(sb.toString());
				httpClient.executeMethod(getMethod);
			}
			else
			{
				LOG.error("bbs url is null");
			}

		} catch (Exception ioe) {
			LOG.error(ioe.getMessage());
		}
	}
	
	
	/**
	 * 打印调试信息
	 * @param message
	 * @param log
	 */
	protected void debug(final String message, final Log log) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}
	public boolean getContentFooter() {
		return contentFooter;
	}
	public void setContentFooter(boolean contentFooter) {
		this.contentFooter = contentFooter;
	}
	
	/**
	 * 跳转人工处理页面
	 * @return
	 */
	@Action(value="/myspace/userinfo/contactCustomService")
	public String returnContactCustomServicePage()
	{
		return "contactCustomService";
	}

	/**
	 * 获取用户信息收集服务
	 * @return
	 */
	private UserActionCollectionService getUserActionCollectionService() {
		return (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
	}
}
