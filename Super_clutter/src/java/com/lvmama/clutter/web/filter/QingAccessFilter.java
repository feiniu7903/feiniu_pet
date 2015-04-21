/**
 * 登陆验证跳转
 * @author william liu
 * @modify courage,bing
 */
package com.lvmama.clutter.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.attribute.standard.Severity;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.lvmama.clutter.service.IBaiduActivityService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class QingAccessFilter implements Filter {

	//用户管理
	private static Logger log = Logger.getLogger(QingAccessFilter.class);
	private static String[] urls;
	private String baiduLoginUrl="/bd/baiduLogin.htm";
	private String loginOutUrl="http://login.lvmama.com/nsso/mobileAjax/logout.do";
	protected UserCooperationUserService userCooperationUserService;
	protected IBaiduActivityService baiduActivityService;
	public void destroy() {
	}
	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException{
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res=(HttpServletResponse) arg1;
		String nginx_req = req.getHeader("request");
		

		String reqUrl="";
		if(nginx_req!=null){
			reqUrl = req.getHeader("request").split(" ")[1];
		}else{
			reqUrl = req.getServletPath();
		}
		if(checkUrl(reqUrl)){
			if(ClientUtils.isQingHost(req)){
				String bdFrameWork = ServletUtil.getCookieValue(req, "bd_framework");
				//log.info("bd_framework2:"+bdFrameWork);
			   this.autoBaiduLogin(req, res,reqUrl);//处理百度自动登陆
			}
		}
		/**
		 * 拦截要百度登陆的url
		 */
		/*if (isInCheck(reqUrl)&&ClientUtils.isQingHost(req)) {
			log.debug("in .....");
			Map<String, String[]> paras = req.getParameterMap();
			if (paras != null) {
				Set<String> set = paras.keySet();
				int i=0;
				for (String key : set) {
					if (!key.equalsIgnoreCase("ticket")) {
						if (i==0) {
							reqUrl = reqUrl + "?" + key + "=" + paras.get(key)[0];
						}else{
							reqUrl = reqUrl + "&" + key + "=" + paras.get(key)[0];
						}
						i++;
					}
				}
			}
			String reqContextPath =  "http://"+req.getHeader("Host")+req.getContextPath();
			
			if (!isLogon(req, res)) {//未登陆
				res.sendRedirect(reqContextPath+baiduLoginUrl+"?service="+URLEncoder.encode(reqContextPath+reqUrl, "UTF-8"));
				return;
			} 	
		}*/
		//log.info("......bd_framework:...");
		long beginTime = System.currentTimeMillis();
		arg2.doFilter(arg0, arg1);
		long count = System.currentTimeMillis() - beginTime;
		if (log.isDebugEnabled()) {
			log.debug(req.getRequestURI()+" spent " + count + " ms.");
		}
	}
	
	private boolean isLogon(HttpServletRequest req, HttpServletResponse resp) {
		UserUser userUser = (UserUser) ServletUtil.getSession(req, resp, Constant.SESSION_FRONT_USER);
		if (userUser == null || (userUser != null && userUser.getUserId() == null)) {
			return false;
		}
		return true;
	}

	private boolean autoBaiduLogin(HttpServletRequest req, HttpServletResponse resp,String reqUrl) throws UnsupportedEncodingException, IOException{
		//log.info("bd_framework3:reqUrl="+reqUrl);
		String bd_token = ServletUtil.getCookieValue(req, "bd_token");
		String bd_uid = ServletUtil.getCookieValue(req, "bd_uid");
		String bd_timestamp = ServletUtil.getCookieValue(req, "bd_timestamp");
		//log.info("bd_token :"+bd_token);
		//log.info("bd_uid :"+bd_uid);
		//log.info("bd_timestamp :"+bd_timestamp);
		
		if(StringUtil.isNotEmptyString(bd_timestamp)){
			Long currentTimeMillis = System.currentTimeMillis();
			/**
			 * 时间戳如果相差10分钟 就忽略处理
			 */
			if((Long.valueOf(bd_timestamp)+60*10)*1000L<currentTimeMillis){
				return false;
			}
		}
		
		if(StringUtil.isNotEmptyString(bd_uid)&&!isSameBaiduAccount(req, resp)){
			/**
			 * 如果是不同的百度账号 先退出 后登陆
			 */
			String reqContextPath =  "http://"+req.getHeader("Host")+req.getContextPath();
			// resp.sendRedirect(loginOutUrl+"?service="+URLEncoder.encode(reqContextPath+reqUrl, "UTF-8"));
			
			 if(StringUtil.isNotEmptyString(bd_uid)){
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("cooperation", Constant.BAIDU_ACTIVITY_CHANNEL);
					parameters.put("cooperationUserAccount", bd_uid);
					List<UserCooperationUser> cooperationUseres = userCooperationUserService
							.getCooperationUsers(parameters);
					log.info("bd_uid query:"+bd_uid);
					if(cooperationUseres!=null&&!cooperationUseres.isEmpty()){
						return baiduActivityService.loginWithBaiduUid(bd_uid, ServletUtil.getLvSessionId(req, resp));
					}
				}
		}
		
		
		ServletUtil.addCookie(resp, "bd_uid", null);
		ServletUtil.addCookie(resp, "bd_timestamp", null);
		return false;
	}
	
	private boolean isBaiduLogin(HttpServletRequest req, HttpServletResponse resp) {
		UserUser userUser = (UserUser) ServletUtil.getSession(req, resp, Constant.SESSION_FRONT_USER);
		if(userUser!=null){
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", userUser.getId());
			List<UserCooperationUser> cooperationUseres = userCooperationUserService
					.getCooperationUsers(parameters);
			if(cooperationUseres!=null&&!cooperationUseres.isEmpty()){
				UserCooperationUser cu = cooperationUseres.get(0);
				return Constant.BAIDU_ACTIVITY_CHANNEL.equals(cu.getCooperation());
			}
		}
		return false;
	}
	
	private boolean isSameBaiduAccount(HttpServletRequest req, HttpServletResponse resp){
		String bd_uid = ServletUtil.getCookieValue(req, "bd_uid");
		UserUser userUser = (UserUser) ServletUtil.getSession(req, resp, Constant.SESSION_FRONT_USER);
		if(userUser!=null){
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", userUser.getId());
			List<UserCooperationUser> cooperationUseres = userCooperationUserService
					.getCooperationUsers(parameters);
			if(cooperationUseres!=null&&!cooperationUseres.isEmpty()){
				UserCooperationUser cu = cooperationUseres.get(0);
				return cu.getCooperationUserId().equals(bd_uid);
			}
		}
		return false;
	}
	
	private boolean isNullBdUid(HttpServletRequest req){
		String bd_uid = ServletUtil.getCookieValue(req, "bd_uid");
		return bd_uid==null;
	}

	/**
	 * 解析urlrewrite path.
	 * @param path.
	 * @return String 解析后的url.
	 */
	private boolean isInCheck(final String path) {
		if(checkUrl(path)){
			if (urls == null)
				return false;
			if (urls.length == 0)
				return false;
			if(path.lastIndexOf(".htm")>0||path.lastIndexOf(".do")>0){
				for (int i = 0; i < urls.length; i++) {
					if (path.matches(urls[i]+".*") ) {
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	/**
	 * 以下后缀的不需要过滤 
	 * @param str
	 * @return
	 */
	private boolean checkUrl(String str){
		String[] s = new String[]{".jpg",".js",".gif",".css"};
		for (int i = 0; i < s.length; i++) {
			if(str.toLowerCase().lastIndexOf(s[i])>0){
				return false;
			}
		}
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		String temp = arg0.getInitParameter("url");
		
		if (log.isDebugEnabled())
			log.debug("get init parameter");
		if (temp != null) {
			urls = temp.split(",");
		} 
	}

	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}

	public void setBaiduActivityService(IBaiduActivityService baiduActivityService) {
		this.baiduActivityService = baiduActivityService;
	}

}
