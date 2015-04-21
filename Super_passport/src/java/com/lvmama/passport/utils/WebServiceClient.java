package com.lvmama.passport.utils;

import java.net.URL;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
/**
 * webService 动态客户端
 * @author chenlinjun
 *
 */
public class WebServiceClient {
	private static WebServiceClient instance;
	public static String NEED_LONG_TIME = "NEED_LONG_TIME";
	private WebServiceClient() {

	}

	public static WebServiceClient getClientInstance() {
		if (instance == null) {
			instance = new WebServiceClient();
		}
		return instance;
	}

	public void initXfireClient(Object var) {
//		HttpClientParams params = new HttpClientParams();
//		params.setParameter(HttpClientParams.SO_TIMEOUT, 30000);
//		params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, 30000L);
//		params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);         
//		params.setConnectionManagerTimeout(30000L);
		//使用HttpClientParams的话，下面的 client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");可以不用
		//上面的和下面的等价
		
		Client client = Client.getInstance(var);
		//client.setUrl(url);
		//client.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params);
		client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");//设置发送的超时限制,单位是毫秒;
		client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
		client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
	}
	public void initXfireClient(Object var,String channel) {
		HttpClientParams params = new HttpClientParams();
		if(NEED_LONG_TIME.equals(channel)){
			params.setParameter(HttpClientParams.SO_TIMEOUT, 180000);
			params.setConnectionManagerTimeout(60000L);
		}else {
			params.setParameter(HttpClientParams.SO_TIMEOUT, 30000);
			params.setConnectionManagerTimeout(30000L);
		}
		params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);         
		
		Client client = Client.getInstance(var);
		//client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");
		client.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS,params); 
	}
	/**
	 * 动态访问WebServiec
	 * 
	 * @param reqeustXml
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public Object[] execute(String wsdl, Object[] param, String methodName) throws Exception {
		Client client = new Client(new URL(wsdl));
		HttpClientParams params = new HttpClientParams();
		params.setParameter(HttpClientParams.SO_TIMEOUT, 180000);
		params.setConnectionManagerTimeout(60000L);
		client.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS,params); 
	    client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");//设置发送的超时限制,单位是毫秒;
	    client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
	    client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
		Object[] results = client.invoke(methodName, param);
		client.close();
		return results;
	}
	
	public static String call(String wsdl, Object[] param, String methodName) throws Exception {
		Client client = new Client(new URL(wsdl));
	    client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "180000");//设置发送的超时限制,单位是毫秒;
	    client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
	    client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
		Object[] results = client.invoke(methodName, param);
		client.close();
		return (String)results[0];
	}
}
