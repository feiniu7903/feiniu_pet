package com.lvmama.passport.processor.impl.client.gmedia;

import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 动态访问WebServiec
 * @author chenlinjun
 *
 */
public class GmediaServiceClient {
	private static GmediaServiceClient instance;
	private GmediaServiceClient(){
		
	}
	public static GmediaServiceClient getClientInstance(){
		  if(instance==null){
			  instance=new GmediaServiceClient();
		  }
		  return instance;
	}
/**
 * 动态访问WebServiec
 * @param reqeustXml
 * @param methodName
 * @return
 * @throws Exception
 */
	public String execute(String reqeustXml, String methodName)
			throws Exception {
			String result = "";
		 	URL url = new URL(WebServiceConstant.getProperties("gmedia"));
	        HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	        httpConnection.setReadTimeout(30000);//设置http连接的读超时,单位是毫秒
	        httpConnection.connect();
	        Client client = new Client(httpConnection.getInputStream(), null);
	        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");//设置发送的超时限制,单位是毫秒;
	        client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
	        client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
	        Object[] results = client.invoke(methodName,
				new Object[] { reqeustXml });
		result = (String) results[0];
		httpConnection.disconnect();
		client.close();
		return result;
	}
}
