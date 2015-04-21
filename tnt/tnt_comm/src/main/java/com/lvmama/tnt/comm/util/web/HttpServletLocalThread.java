/**
 * 
 */
package com.lvmama.tnt.comm.util.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lancey
 *
 */
public class HttpServletLocalThread{
	
	static class HttpContext{
		private HttpServletRequest request;
		private HttpServletResponse response;
		public HttpContext(HttpServletRequest request,
				HttpServletResponse response) {
			super();
			this.request = request;
			this.response = response;
		}
		public HttpServletRequest getRequest() {
			return request;
		}
		public HttpServletResponse getResponse() {
			return response;
		}
		
		
	}

	static ThreadLocal<HttpContext> threadLocal = new ThreadLocal<HttpServletLocalThread.HttpContext>();
	
	public static void set(HttpServletRequest req,HttpServletResponse res){
		threadLocal.set(new HttpContext(req,res));
	}
	
	static HttpServletRequest getRequest(){
		return threadLocal.get().getRequest();
	}
	
	static HttpServletResponse getResponse(){
		return threadLocal.get().getResponse();
	}
	
	public static void clean(){
		threadLocal.remove();
	}
}
