package com.lvmama.back.web.utils.insurance.sinosig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class SinosigPolicy {
	protected static final String USERNAME = "FORLMMYG187C57108E7D9AEA647901EAAAB";
	protected static final String PASSWORD = "FORLMMYG187C57108E7D9AEA647901EAAAB";
	protected static final String SERVER = "http://219.143.230.134:7002/ifp/SyncInterface";
	
	public String md5(final String content) {
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
	
	public String request(final String content, final boolean isSurrender) {
		String sbb = "interfaceFlag=LMMYG" + "&data=" + content + "&sign="
				+ md5(("cclvmmforygbxsgslii" + content)).toLowerCase();
		if (isSurrender) {
			sbb += "&functionFlag=" + "SURRENDER";
		} else {
			sbb += "&functionFlag=" + "INSURE";
		}
		StringBuilder outtext = new StringBuilder();
		
		try {
			URL url = new URL(SERVER);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setConnectTimeout(30000);
			//con.setRequestMethod("POST");
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
		
}
