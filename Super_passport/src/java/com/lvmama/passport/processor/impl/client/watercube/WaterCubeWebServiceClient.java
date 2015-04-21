package com.lvmama.passport.processor.impl.client.watercube;

import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

public class WaterCubeWebServiceClient {
	/** WEBSERVICE方法名称 */
	private static final String METHOD = "getEleInterface";
	
	/**
	 * 提交订单请求
	 * @param requestXml
	 * @return
	 * @throws Exception
	 */
	public static String excute(String requestXml) throws Exception{
		Object [] ojbs = WebServiceClient.getClientInstance().execute(WebServiceConstant.getProperties("watercube.wsdl"), new Object[]{WebServiceConstant.getProperties("watercube.organization"),requestXml}, METHOD);
		return ojbs[0].toString();
	}

}
