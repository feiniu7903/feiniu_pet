package com.lvmama.comm.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StackOverFlowUtil;

/**
 * 生成默认的LV_SESSION_ID
 * Servlet Filter implementation class DefaultFilter
 */
public class DefaultFilter implements Filter {
	
	private Log log = LogFactory.getLog(this.getClass());
    /**
     * Default constructor. 
     */
    public DefaultFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		//为了获取coremetrics404的部署
		String requestUrl = request.getRequestURL()+(request.getQueryString()==null?"":("?"+request.getQueryString()));
		request.setAttribute("coremetricsRequestUrl", requestUrl);
		//初始化lvsessionid，初始始化session
		request.getSession();
		ServletUtil.initLvSessionId(request, response);
		//如果出现错误，把出现的错误打印出来
		try{
			chain.doFilter(request, response);
		}catch (Exception ex) {
			StackOverFlowUtil.printErrorStack(request, response, ex);
		}catch(Throwable e) {
			log.error("Captured Throwable Exception URL: " + request.getRequestURL().toString());
			log.error(this.getClass(), e);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
