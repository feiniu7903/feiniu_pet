package com.lvmama.tnt.front.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RequstUtil {

	private static Log LOG = LogFactory.getLog(RequstUtil.class);
	public static boolean checkKaptchaCode(HttpServletRequest request, String verifycode, boolean needRemoveCode){
		String code = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if((verifycode).equalsIgnoreCase(code)){
			if(needRemoveCode){
				request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			}
			return true;
		}else{
			LOG.debug("submit verifyCode:" + verifycode + ",result is dismatch!" + ", system code:"
					+ (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
			return false;
		}
	}
}
