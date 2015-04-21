package com.lvmama.comm.utils.ord;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

public final  class DaZhongPolicyUtils {
	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static ArrayList<String> createSession(HttpClient httpClient) {
		HttpPost httpPost = new HttpPost("http://dz.e-dicc.com.cn/ah/session");
		ArrayList<String> cookies = new ArrayList<String>();
		try {
			// Execute HTTP request
			StringEntity reqEntity = new StringEntity(
					"{\"username\":\"lvmama01\",\"password\":\"123456\"}");
			reqEntity.setContentType("application/json");
			httpPost.setEntity(reqEntity);
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();

			HeaderIterator hi = response.headerIterator("Set-cookie");
			while (hi.hasNext()) {
				cookies.add(hi.nextHeader().getValue());
			}

			if (resEntity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(resEntity.getContent()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					cookies.add(line);
				}
			}
			//EntityUtils.consume(resEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cookies;
	}
	
	public static String postPolicies(HttpClient httpClient, ArrayList<String> cookies, byte[] policyJosnData) {
		HttpPost httpPost = new HttpPost("http://dz.e-dicc.com.cn/ah/policies");
		try {
			ByteArrayEntity reqEntity = new ByteArrayEntity(policyJosnData);
			reqEntity.setContentType("application/json");
			httpPost.setEntity(reqEntity);

			Iterator<String> si = cookies.iterator();
			while (si.hasNext()) {
				httpPost.addHeader("Cookie", si.next());
			}

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();

			StringBuffer sb = new StringBuffer();
			if (resEntity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(resEntity.getContent()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}
			return sb.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String cancelPolicy(HttpClient httpClient, ArrayList<String> cookies, String policyId) {
		HttpDelete httpDelete = new HttpDelete(
				"http://dz.e-dicc.com.cn/ah/policies/" + policyId);
		try {
			// Execute HTTP request
			Iterator<String> si = cookies.iterator();
			while (si.hasNext()) {
				httpDelete.addHeader("Cookie", si.next());
			}	
			HttpResponse response = httpClient.execute(httpDelete);
			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(resEntity.getContent()));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					cookies.add(line);
					sb.append(line);
				}
				return sb.length() == 0 ? null : sb.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	public static byte[] downloadPolicy(HttpClient httpClient, ArrayList<String> cookies, String policyId) {
		HttpGet httpGet = new HttpGet(
				"http://dz.e-dicc.com.cn/ah/pdf_printer/policies/" + policyId);
		InputStream is=null;
		ByteArrayOutputStream bytestream=null;
		try {
			Iterator<String> si = cookies.iterator();
			while (si.hasNext()) {
				httpGet.addHeader("Cookie", si.next());
			}
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				is = resEntity.getContent();
				bytestream = new ByteArrayOutputStream();
				int ch;
				while ((ch = is.read()) != -1) {
					bytestream.write(ch);
				}
				byte policydata[] = bytestream.toByteArray();
				bytestream.close();
				is.close();
				return policydata;
			}else{
				System.out.println("没有找到内容");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(bytestream);
			IOUtils.closeQuietly(is);
		}
		return null;
	}
	
	public static void deleteSession(HttpClient httpClient,
			ArrayList<String> cookies) {
		HttpDelete httpDelete = new HttpDelete(	"http://dz.e-dicc.com.cn/ah/session");
		try {
			httpClient.execute(httpDelete);

			Iterator<String> si = cookies.iterator();
			while (si.hasNext()) {
				httpDelete.addHeader("Cookie", si.next());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
