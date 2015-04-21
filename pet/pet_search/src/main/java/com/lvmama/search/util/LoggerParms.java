package com.lvmama.search.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lvmama.comm.utils.InternetProtocol;

public class LoggerParms {

	public static String getIpAddr(HttpServletRequest request) {
		if(request==null){
			return null;
		}
		return InternetProtocol.getRemoteAddr(request);
	}
	
	public static String getNowTime() {
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		java.util.Date date = new java.util.Date();
		return format.format(date);

	}
	
	public static String getSessionId(HttpSession session) {
		if(session!=null){
			return session.getId();
		}
		return null;
		
	}
}
