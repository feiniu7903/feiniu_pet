package com.lvmama.back.web.utils.insurance.taipin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;



public class TaiPinPensionPolicy {
	protected static final String SERVER = "http://116.228.52.139/eservice/gp/servlet/PublicGpXMLOnlineServlet";
	protected static final String DLBH = "D0100200";

	public String request(final String content) {
		StringBuilder sb = new StringBuilder();
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(30000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(30000);
			PostMethod getMethod = new PostMethod(SERVER);
			getMethod.addParameter("dlbh", DLBH);
			getMethod.addParameter("xmlstr", content);
			getMethod.addParameter(
					"md5",
					md5(content + "i93b9k3c85c4713c362994f6e32qu2lm"));
			
			httpClient.executeMethod(getMethod);
			InputStream inputStream = getMethod.getResponseBodyAsStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "GBK");
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferReader.readLine()) != null) {
				sb.append(line.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public String request2(final String content) {
		String md5 = md5(content + "i93b9k3c85c4713c362994f6e32qu2lm");
		String sbb = "dlbh=" + "D0100200" + "&xmlstr=" + content + "&md5="
				+ md5;
		StringBuilder outtext = new StringBuilder();
		
		try {
			URL url = new URL(SERVER);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setConnectTimeout(30000);
			OutputStream outs = con.getOutputStream();
			outs.write(sbb.toString().getBytes("GBK"));
			InputStream in = con.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,
					"GBK"));

			String line = "";
			while ((line = read.readLine()) != null) {
				outtext.append(line);
			}
			read.close();
			in.close();
			outs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outtext.toString();
	}
	

	private String md5(final String content) {
		try {
			MessageDigest alga;
			String myinfo = content;
			alga = MessageDigest.getInstance("MD5");
			alga.update(myinfo.getBytes("GBK"));
			byte[] digesta = alga.digest();
			String hs = "";
			String stmp = "";
			for (int n = 0; n < digesta.length; n++) {
				stmp = (java.lang.Integer.toHexString(digesta[n] & 0XFF));
				if (stmp.length() == 1)
					hs = hs + "0" + stmp;
				else
					hs = hs + stmp;
			}
			return hs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		String t = "周晓斌230bfa3c85c4713c718994f6e32e12lm";
		System.out.println(new TaiPinPensionPolicy().md5(t));	
	}
}
