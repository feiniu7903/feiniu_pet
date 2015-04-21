package com.lvmama.comm.utils.freemarker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class HttpIncludeTemplateDirectiveModel implements TemplateDirectiveModel {
	@Override
	public void execute(Environment env, Map paramsMap, TemplateModel[] loopVars,  
            TemplateDirectiveBody body) throws TemplateException, IOException { 
		 // 获取传递进来的参数  
        Iterator it = paramsMap.entrySet().iterator();  
		String value="";
		String charset="";
		int timeout=0; // 超时时间
		while (it.hasNext()) {  
        	Entry entry = (Entry) it.next();  
        	String paramName = entry.getKey().toString();  
            TemplateModel paramValue = (TemplateModel) entry.getValue();  
            if("value".equalsIgnoreCase(paramName)){
            	value = paramValue.toString();
            }else if("charset".equalsIgnoreCase(paramName)){
            	charset = paramValue.toString();
            }else if("timeout".equalsIgnoreCase(paramName)){
            	timeout = Integer.parseInt(paramValue.toString());
            }
        }
		if (timeout <= 0) {
			timeout = 3000;
		}
		if("".equals(charset)){
			charset = "utf-8";
		}
		String regex ="(\\=([\\u4e00-\\u9fa5]+))";
		Pattern p = Pattern.compile(regex);
		Matcher m=p.matcher(value);
		String a ;
		while(m.find()){
			value = value.replaceAll(m.group(2), URLEncoder.encode(m.group(2),"UTF-8"));
		}
		String webContext = GetWebContent(value, charset, timeout);
		Writer out = env.getOut();
        out.write(webContext);
	}
	
	/*
	 * 取得指定URL的Web内容
	 */
	public static String GetWebContent(final String theURL, final String charset, final int timeout){
		String sTotalString = "";
		URL l_url = null;
		HttpURLConnection l_connection = null;
		java.io.InputStream l_urlStream = null;
		BufferedReader l_reader = null;
		try {
			l_url = new URL(theURL);
			l_connection = (HttpURLConnection) l_url.openConnection();
			l_connection.setConnectTimeout(timeout);
			// 加入取内容超时处理
			l_connection.setReadTimeout(timeout);
			l_connection.connect();
			l_urlStream = l_connection.getInputStream();
			l_reader = new BufferedReader(new InputStreamReader(l_urlStream, charset));
			int buffer_size = 1024;
			char[] buffer = new char[buffer_size];
			StringBuffer sb = new StringBuffer();
			int readcount = 0;
			while ((readcount = l_reader.read(buffer, 0, buffer_size)) > 0) {
				sb.append(buffer, 0, readcount);
			}
			sTotalString = sb.toString();
			l_reader.close();
			l_urlStream.close();
			l_connection.disconnect();
		} catch (Exception e) {
			System.out.println("error: exception in WebUtil: " + e.toString() + ":" + theURL);
		} finally {
			if (l_reader != null) {
				try {
					l_reader.close();
				} catch (Exception e) {
				}
			}
			if (l_urlStream != null) {
				try {
					l_urlStream.close();
				} catch (Exception e) {
				}
			}
			if (l_connection != null) {
				try {
					l_connection.disconnect();
				} catch (Exception e) {
				}
			}
		}

		if (sTotalString == null) {
			sTotalString = "";
		}
		return sTotalString;
	}
}
