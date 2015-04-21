package com.lvmama.search.util;

import com.lvmama.comm.utils.HttpsUtil;

public class SearchHttpsClient {
	private static HttpsUtil httpsUtil=null;
	public static HttpsUtil getSingle(){
		if(httpsUtil==null){
			httpsUtil =  new HttpsUtil();
		}
		return httpsUtil;
	}
	
}
