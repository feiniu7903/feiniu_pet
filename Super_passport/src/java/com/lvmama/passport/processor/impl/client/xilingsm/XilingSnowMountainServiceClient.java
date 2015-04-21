package com.lvmama.passport.processor.impl.client.xilingsm;

import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import com.lvmama.passport.utils.WebServiceConstant;

/**
 * Xiling WebService client
 * @author zuoxiaoshuai
 *
 */
public class XilingSnowMountainServiceClient {

	/**
	 * client instance
	 */
	private static XilingSnowMountainServiceClient instance;

	/**
	 * privatization constructor
	 */
	private XilingSnowMountainServiceClient() {
	}

	/**
	 * get client instance
	 * @return
	 */
	public static XilingSnowMountainServiceClient getInstance() {
		  if (instance==null) {
			  instance = new XilingSnowMountainServiceClient();
		  }
		  return instance;
	}

	/**
	 * call WebServiec
	 * @param reqeustXml
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public String execute(String reqeustXml, String methodName)
			throws Exception {
		String result = "";
		//get url by properties
		URL url = new URL(WebServiceConstant.getProperties("xilingsm.wsdl"));
		//open connection
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setReadTimeout(30000);//set timeout
        httpConnection.connect();
        Client client = new Client(httpConnection.getInputStream(), null);
        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");
        client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
        client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
        Object[] results = client.invoke(methodName, new Object[] { reqeustXml });
		result = (String) results[0];
		//close http connection
		httpConnection.disconnect();
		//close client
		client.close();
		return result;
	}
}
