package com.lvmama.operate.web.filter;

import java.io.IOException;

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

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

/**
 * Servlet Filter implementation class SessionUserFilter
 */
public class SessionUserFilter implements Filter {
     Log loger = LogFactory.getLog(this.getClass());
     
     public void init(FilterConfig fConfig) throws ServletException {
     }

     /**
      * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
      */
     public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
          HttpServletRequest request = (HttpServletRequest)req;
          HttpServletResponse res = (HttpServletResponse)response;
          PermUser permUser = (PermUser)ServletUtil.getSession(request, res, Constant.SESSION_BACK_USER);
          request.getSession().setAttribute(com.lvmama.operate.util.Constant.SESSION_USER,permUser);
          chain.doFilter(request, response);
     }

     public void destroy() {
     }
}
