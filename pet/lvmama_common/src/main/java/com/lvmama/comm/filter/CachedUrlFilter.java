package com.lvmama.comm.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CachedUrlFilter implements Filter {

	@Override
	public void destroy() {

	}

	/**
	 * 1. 修改客户端缓存策略，对配置文件中的URL设置HTTP HEADER
	 * 2. 配置文件为 config\\url-cache.xml,这个文件包含的是需要增加cache的URL列表
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (CachedUriConstant.getInstance().contains(req.getRequestURI())) {
			//设置客户端缓存时间为1天
			res.setHeader("Cache-Control", "max-age=86400");
			
			SimpleDateFormat dateformat=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'",Locale.US);
			dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
			Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
			
			res.setHeader("Last-Modified", dateformat.format(cal.getTime()));
			res.setHeader("Date", dateformat.format(cal.getTime()));
			
			//设置过期时间为1天
			cal.add(Calendar.HOUR, 24);
			res.setHeader("Expires", dateformat.format(cal.getTime()));
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	

}
