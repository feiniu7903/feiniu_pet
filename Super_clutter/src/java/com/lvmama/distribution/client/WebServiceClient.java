package com.lvmama.distribution.client;

import java.net.URL;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
/**
 * webService 动态客户端
 * @author gaoxin
 *
 */
public class WebServiceClient {
	private static WebServiceClient instance;

	private WebServiceClient() {

	}

	public static WebServiceClient getClientInstance() {
		if (instance == null) {
			instance = new WebServiceClient();
		}
		return instance;
	}

	/**
	 * 动态访问WebServiec
	 * 
	 * @param reqeustXml
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public Object[] execute(String wsdl ,Object[] param, String methodName) throws Exception {
		Client client = new Client(new URL(wsdl));
	    client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");//设置发送的超时限制,单位是毫秒;
	    client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
	    client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
		Object[] results = client.invoke(methodName, param);
		client.close();
		return results;
	}
}
