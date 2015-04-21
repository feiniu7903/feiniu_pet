/**
 * 
 */
package com.lvmama.back.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.response.ItemcatsGetResponse;

/**
 * 淘宝开放平台API调用工具
 * 
 * @author zhangwengang
 * 
 */
public abstract class TaobaoUtils {
	// protected static String url =
	// "http://gw.api.tbsandbox.com/router/rest";// 沙箱环境调用地址
	protected static String url = "http://gw.api.taobao.com/router/rest"; // 正式环境
	// protected static String appkey = "21619437";
	// protected static String appSecret = "5d19b22f65dbc54b7ac290c204382ed0";
	// protected static String sessionkey =
	// "610192530a20b2cb9b128bb21d9f07ba01773c526551a9512694005"; //
	// 沙箱测试帐号sandbox_c_1授权后得到的sessionkey
	public static String map_url = "http://list.bendi.taobao.com/provider/itemLinkLs.do";
	public static String appkey = "21625323";
	public static String appSecret = "9a8c03550eed382b8bcf4bd3ac14745c";
	public static String sessionkey = "6100f24305abd15cb748f4024d4e5a2b1610c05ff3ee62f490728022";
	
	public static String username = "驴妈妈旅游专卖店";
	public static String userkey = "70CFC7DCE849C1C6841B0C44484A9427";

	/**
	 * 初始化
	 * 
	 * @param req
	 * @return
	 */
	public static TaobaoClient init() {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);// 实例化TopClient类
		return client;
	}

	public static void main(String[] args) {
		try {
			TaobaoClient client = TaobaoUtils.init();
			ItemcatsGetRequest req = new ItemcatsGetRequest();
			req.setFields("cid,parent_cid,name,is_parent");
			req.setParentCid(50011949L);
			ItemcatsGetResponse response;

			response = client.execute(req);

			List<ItemCat> list = response.getItemCats();
			for (ItemCat ic : list) {
				System.out.println(ic.getName());
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	public static String StringMD5(String input, String charset)
			throws UnsupportedEncodingException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = input.getBytes(charset);
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return ByteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String ByteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}

}
