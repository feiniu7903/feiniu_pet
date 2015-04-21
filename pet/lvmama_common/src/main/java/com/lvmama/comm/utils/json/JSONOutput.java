/**
 * 
 */
package com.lvmama.comm.utils.json;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * json字符串输出. 
 * @author yangbin
 *
 */
public abstract class JSONOutput {
	
	private final static String JSON_CONTENT_TYPE="application/json;charset=UTF-8";

	/**
	 * 向response输出json信息
	 * @param <T>
	 * @param res
	 * @param obj
	 */
	public static <T> void writeJSON(HttpServletResponse res, T obj) {
		writeJSON(res, obj, JSON_CONTENT_TYPE);
	}
	/**
	 * 向response输出json信息
	 * @param <T>
	 * @param res
	 * @param obj
	 */
	public static <T> void writeJSON(HttpServletResponse res, T obj,String contentType) {
		writeJSON(res, obj, contentType,null);
	}
	
	public static<T> void writeJSON(HttpServletResponse res,T obj,String contentType,String callback){
		res.setContentType(contentType);
		res.setCharacterEncoding(ENCODING);
		try{
			StringBuffer sb=new StringBuffer();
			if(StringUtils.isNotEmpty(callback)){
				sb.append(callback);
				sb.append("(");
			}
			sb.append(obj.toString());
			if(StringUtils.isNotEmpty(callback)){
				sb.append(")");
			}
			res.getOutputStream().write(sb.toString().getBytes(ENCODING));
		}catch(Exception ex){
			logger.warn(ex);
		}
	}
	
	public static<T> void writeJSONP(HttpServletResponse res, T obj,String callback){
		writeJSON(res,obj,ENCODING,callback);
	}
	private static final Log logger=LogFactory.getLog(JSONOutput.class);
	private static final String ENCODING="UTF-8";
}
