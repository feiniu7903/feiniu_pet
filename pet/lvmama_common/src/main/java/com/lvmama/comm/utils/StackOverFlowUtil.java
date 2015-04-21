package com.lvmama.comm.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StackOverFlowUtil {
	private static Log log = LogFactory.getLog(StackOverFlowUtil.class);

	public static String getExceptionStackInfo(Exception e) {
        try {
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            return sw.toString();  
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";  
        }  
    }


	public static void printErrorStack(HttpServletRequest req,HttpServletResponse res,Exception ex){
		StringBuffer sb = new StringBuffer("Captured Exception:");
		sb.append(ex.getMessage());
		sb.append("\r\n");
		sb.append("Request URL: ").append(req.getRequestURL().toString()).append("\r\n");
		sb.append("Request Referer: ").append(req.getHeader("referer")).append("\r\n");
		sb.append("Request IP: ").append(StackOverFlowUtil.getRemoteAddr(req)).append("\r\n");
		sb.append("Request Remote Host: ").append(req.getRemoteHost()).append("\r\n");
		sb.append("Request User-Agent: ").append(req.getHeader("User-Agent")).append("\r\n");
		sb.append("Request Parameters: \r\n {");
		Map paraMap =  req.getParameterMap();
		boolean isFirstPara = true;
		for (Iterator iter = paraMap.keySet().iterator(); iter.hasNext();) {
			String paraName = (String) iter.next();
			Object paraValue = paraMap.get(paraName);
			if(!isFirstPara)
				sb.append(",");
			isFirstPara=false;
			sb.append("\"");
			sb.append(paraName);
			sb.append("\":");
			if (paraValue!=null && (paraValue instanceof String[])) {
				String[] paraValues = (String[]) paraValue;
				if(paraValues.length>1)
					sb.append("[");
				for (int i = 0; i < paraValues.length; i++) {
					if(i>0){
						sb.append(",");
					}
					if(!paraValues[i].startsWith("{")){
						sb.append("\"");
					}
					sb.append(paraValues[i]);
					if(!paraValues[i].endsWith("}")){
						sb.append("\"");
					}
				}
				if(paraValues.length>1)
					sb.append("]");
			}
		}
		sb.append("}\r\n");
		sb.append(getExceptionStackInfo(ex));
		log.error(sb.toString());
	}
	
	/**
	 * 获取客户端IP地址.<br>
	 * 支持多级反向代理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 客户端真实IP地址
	 */
	private static String getRemoteAddr(final HttpServletRequest request) {
			return InternetProtocol.getRemoteAddr(request);
	}
	
	/**
	 * 远程地址是否有效.
	 * 
	 * @param remoteAddr
	 *            远程地址
	 * @return true代表远程地址有效，false代表远程地址无效
	 */
	private static boolean isEffective(final String remoteAddr) {
		boolean isEffective = false;
		if ((null != remoteAddr) && (!"".equals(remoteAddr.trim()))
				&& (!"unknown".equalsIgnoreCase(remoteAddr.trim()))) {
			isEffective = true;
		}
		return isEffective;
	}
}
