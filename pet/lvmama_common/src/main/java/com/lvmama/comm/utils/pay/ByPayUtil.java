package com.lvmama.comm.utils.pay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lvmama.comm.utils.HttpsUtil;

public class ByPayUtil {

	
	 /**
	  * 交互取返回的信息.
	  * @param POST_URL
	  * @param postData
	  * @return
	  * @throws IOException
	  */
	public static String readContentFromPost(String POST_URL,String postData) throws IOException{
//       // Post请求的url，与get不同的是不需要带参数
//       URL postUrl = new URL(POST_URL);
//       // 打开连接
//       HttpURLConnection connection = (HttpURLConnection) postUrl
//               .openConnection();
//       connection.setDoOutput(true);
//       connection.setDoInput(true);
//       connection.setRequestMethod("POST");
//       connection.setUseCaches(false);
//       connection.setInstanceFollowRedirects(true);
//       connection.setConnectTimeout(8000);
//       connection.setReadTimeout(60000);
//       // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
//       // 进行编码
//       connection.setRequestProperty("Content-Type",
//               "application/octet-stream"); // text/xml;charset=UTF-8
//       
//       connection.connect();
//       DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//
//       out.write(postData.getBytes("utf-8"));
//       out.flush();
//       out.close(); // flush and close
//       BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
//   	   String result = "";
//       String line;
//		while ((line = reader.readLine()) != null) {
//			result += line;
//		}
//       reader.close();
//       connection.disconnect();
//       return result;
       return HttpsUtil.requestPostData(POST_URL, postData, "application/octet-stream", "utf-8",HttpsUtil.CONNECTION_TIMEOUT,HttpsUtil.SO_TIMEOUT_60S).getResponseString("utf-8");
   }
	
}
