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

import com.lvmama.comm.utils.XssHTMLUtil;

/**
 * 过滤掉HTML标签
 */
public class XssFilter implements Filter {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * Default constructor.
	 */
	public XssFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		//白名单过滤
		if(!XssWhiteListConstant.getInstance().contains(request.getRequestURI())){
			// 过滤掉一些可能引起攻击的代码
			Map<String, Object> para = request.getParameterMap();
			XssHTMLUtil xssHTMLUtil = new XssHTMLUtil();
			for (Iterator<String> iter = para.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				Object obj = para.get(key);
				if (obj != null && (obj instanceof String[])) {
					String[] paraValues = (String[]) obj;
					for (int i = 0; i < paraValues.length; i++) {
						paraValues[i] = xssHTMLUtil.filter(paraValues[i]);
					}
				}
			}
		}else{
			log.debug("don't filter "+request.getRequestURI());
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
