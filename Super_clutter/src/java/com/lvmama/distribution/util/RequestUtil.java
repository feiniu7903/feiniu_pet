package com.lvmama.distribution.util;

import javax.servlet.http.HttpServletRequest;

import com.lvmama.comm.utils.InternetProtocol;

public class RequestUtil {

public static String getIpAddr(HttpServletRequest request) {
	return InternetProtocol.getRemoteAddr(request);
}

}