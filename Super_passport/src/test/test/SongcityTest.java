package test;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.passport.utils.WebServiceConstant;

public class SongcityTest extends TestCase {
	private static String baseTemplateDir = "/com/lvmama/passport/songcity/template";
	
	private static String bookOrderUrl = WebServiceConstant.getProperties("songcity.bookorder.url");
	private static String cancelOrderUrl = WebServiceConstant.getProperties("songcity.cancelorder.url");
	private static String queryOrderUrl = WebServiceConstant.getProperties("songcity.queryorder.url");
	private static String loginUser = WebServiceConstant.getProperties("songcity.login.user");
	private static String loginPwd = WebServiceConstant.getProperties("songcity.login.pwd");

	//下单
	public void testBookOrder() throws Exception {
		String orderXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "testReq.xml", null);
		Map<String, String> data = new HashMap<String, String>();
		data.put("orderXml", orderXml);
		data.put("transTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		data.put("operator", loginUser);
		String sign = makeSign(data, digest(loginPwd));
		data.put("sign", sign);
/*		String resXml = HttpPostUtil.post(bookOrderUrl, data);
		System.out.println(resXml);
*/	}
	
	//取消
	public void testCancelOrder() throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data = new HashMap<String, String>();
		data.put("orderNo", "12122720415587");
		data.put("outOrderNo", "123456");
		data.put("transTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		data.put("operator", loginUser);
		String sign = makeSign(data, digest(loginPwd));
		data.put("sign", sign);
		/*String resXml = HttpPostUtil.post(cancelOrderUrl, data);
		System.out.println(resXml);*/
	}
	
	//查询
	public void testQueryOrder() throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data = new HashMap<String, String>();
		data.put("orderNo", "12122720411312");
		data.put("outOrderNo", "123456");
		data.put("transTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		data.put("operator", loginUser);
		String sign = makeSign(data, digest(loginPwd));
		data.put("sign", sign);
//		String resXml = HttpPostUtil.post(queryOrderUrl, data);
//		System.out.println(resXml);
	}
	
	private static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
	
	private static String digest(String src) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(src.getBytes("utf-8"));
			return bytesToHexString(digest.digest());
		} catch (Exception e) {
			return null;
		}
	}

	private static String makeSign(Map<String, String> params, String pass) {
		ArrayList<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
			if (key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("signType") || value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}
		return digest(prestr + pass);
	}
}
