/**
 * 
 */
package com.lvmama.front.web.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 点评参数检测拦截器
 * @author liuyi
 *
 */

public class CommentParamCheckInterceptor extends AbstractInterceptor {

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		Map<String, Object> param = actionContext.getParameters();
		Iterator<Entry<String, Object>> paramIterator = param.entrySet().iterator();
        Pattern positivePattern = Pattern.compile("^[1-9]+\\d*$");   
		while(paramIterator.hasNext()){
			Entry<String, Object> paramEntry = paramIterator.next();
			String key = paramEntry.getKey();
			Object value = paramEntry.getValue();
			if("placeId".equalsIgnoreCase(key) && StringUtils.isNotEmpty(((String[])value)[0])){
				Matcher m = positivePattern.matcher(((String[])value)[0]);  
				boolean matchFlag = m.matches();
				if(!matchFlag){
					return "paramError";
				}
			}
			
			if("productId".equalsIgnoreCase(key) && StringUtils.isNotEmpty(((String[])value)[0])){
				Matcher m = positivePattern.matcher(((String[])value)[0]);  
				boolean matchFlag = m.matches();
				if(!matchFlag){
					return "paramError";
				}
			}
			
			if("orderId".equalsIgnoreCase(key) && StringUtils.isNotEmpty(((String[])value)[0])){
				Matcher m = positivePattern.matcher(((String[])value)[0]);  
				boolean matchFlag = m.matches();
				if(!matchFlag){
					return "paramError";
				}
			}
			
			if("commentId".equalsIgnoreCase(key) && StringUtils.isNotEmpty(((String[])value)[0])){
				Matcher m = positivePattern.matcher(((String[])value)[0]);  
				boolean matchFlag = m.matches();
				if(!matchFlag){
					return "paramError";
				}
			}
		}
		return invocation.invoke();
	}

}
