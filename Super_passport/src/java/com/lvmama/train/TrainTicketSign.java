package com.lvmama.train;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 *  
 * @author chenkeke
 *
 */
public class TrainTicketSign {

	private static final String HMAC_SHA1 = "HmacSHA1";
	private final String CHARSET = "utf8";
	/**
	 * 获取签名
	 * @param key 商户密钥
	 * @param method HTTP请求方式
	 * @param url 请求URI路径
	 * @param paramsMap 请求参数
	 * @return
	 * @throws Exception
	 */
	public String sign(String key,String method,String url,Map<String, String> paramsMap) throws Exception{
		StringBuffer sign =new StringBuffer();
		//字符串排序拼接start
		List<String> paramsKeyList = new ArrayList<String>();
		paramsKeyList.addAll(paramsMap.keySet());
		Collections.sort(paramsKeyList);
		StringBuffer params = new StringBuffer();
		for (String paramsKey : paramsKeyList) {
			if(params.length()>0)
				params.append("&");
			params.append(paramsKey);
			params.append("=");
			params.append(paramsMap.get(paramsKey)==null?"":paramsMap.get(paramsKey));
		}
		System.out.println(params.toString());
		//字符串排序拼接end
		//转换后字符串拼接start
		sign.append(method);
		sign.append("&");
		sign.append(this.urlEncode(url));
		sign.append("&");
		sign.append(this.urlEncode(params.toString()));
		//转换后字符串拼接end
		//加密并且转成base64字符
		System.out.println(sign.toString());
		String base64sign = Base64.encodeBase64String(this.getSignature(sign.toString(), key+"&"));
		System.out.println(base64sign);
		//return this.urlEncode(base64sign);
		return base64sign.trim();
	}
	/**
	 * 获取签名
	 * @param key
	 * @param method
	 * @param url
	 * @param getParams
	 * @param postParams
	 * @return
	 * @throws Exception
	 */
	public String sign(String key,String method,String url,String getParams,String postParams) throws Exception{
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.putAll(this.params2Map(getParams));
		paramsMap.putAll(this.params2Map(postParams));
		return this.sign(key, method, url, paramsMap);
		
	}
	private String urlEncode(String url) throws UnsupportedEncodingException{
		//特殊字符转16进制
		url = URLEncoder.encode(url,CHARSET).replaceAll("\\+", "%20");
		url = this.specialChar2hex(url);
		return  url;
	}
	/**
	 * get post String参数转MAP
	 * @param params
	 * @return
	 */
	private Map<String, String> params2Map(String params){
		Map<String, String> paramsMap = new HashMap<String, String>();
		if(params!=null && !"".equals(params.trim())){
			for (int i = 0; i < params.split("&").length; i++) {
				String keyValue = params.split("&")[i];
				String paramsKey = keyValue.split("=")[0];
				String paramsValue ="";
				if(keyValue.split("=").length>1){
					paramsValue = keyValue.substring(keyValue.indexOf("=")+1);
				}
				paramsMap.put(paramsKey, paramsValue);	
			}
		}
		return paramsMap;
	} 
	/**
	 * 生成签名数据
	 * 
	 * @param data
	 *            待加密的数据
	 * @param key
	 *            加密使用的key
	 * @throws Exception
	 */
	private byte[] getSignature(String data, String key) throws Exception {
		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		return mac.doFinal(data.getBytes());
	}

    /**
     *字节码转换成16进制字符串
     *@param byte[]b输入要转换的字节码
     *@return String返回转换后的16进制字符串
     */
	private String byte2hex (byte []b )
    {
         String hs ="";
         String stmp ="";
         for (int n =0 ;n <b.length ;n ++)
         {
             stmp =(java.lang.Integer.toHexString (b [n ]&0XFF ));
             if (stmp.length ()==1 )hs =hs +"0"+stmp ;
             else hs =hs +stmp ;
         }
         return hs.toUpperCase ();
    }
    /**
     * 查找是否有除 数字、英文、[-]、[_]、[.]、[%] 的特殊字符，如有就替换为%加16进制如：=替换为%3D
     * @param speChar
     * @return
     */
    private String specialChar2hex(String speChar){    	
    	String regex = "^[0-9A-Za-z-_.%]+$";
		Map<String, String> strMap= new HashMap<String, String>();
		strMap.put(speChar, speChar);
		while (strMap.size()>0) {
			int count=0;
			Object[] key = strMap.keySet().toArray();
			for (int i = 0; i < key.length; i++) {
				String string = (String)key[i];
				if(!string.matches(regex)){
					String tmp1 = string.substring(0,string.length()/2);
					String tmp2 = string.substring(string.length()/2);
					if(!tmp1.matches(regex)){
						strMap.put(tmp1, tmp1);
					}
					if(!tmp2.matches(regex)){
						strMap.put(tmp2, tmp2);
					}
					strMap.remove(string);
					if(string.length()>1){
						count++;
					}else if(string.length()==1) {
						speChar = speChar.replaceAll("\\"+string, "%"+this.byte2hex(string.getBytes()));
					}
				}		
				strMap.remove(string);
			}
			if(count==0){
				break;
			}
		}
		return speChar;
    }
	public static void main(String[] args) throws Exception {
	
		TrainTicketSign trainTicketSign = new TrainTicketSign();
		// System.out.println(trainTicketSign.urlEncode("depart_station=上海虹桥*"));
		System.out
				.println(trainTicketSign
						.sign("6ecd6d6d326eb22b9ead5fa986d6d9bf",
								"POST",
								"/v1/order/ticket_order_create",
								"merchant_id=600001&user_ip=101.102.103.104&depart_station=上海虹桥&arrive_station=北京南&depart_date=2013-09-12&train_id=G102",
								"json_param=[{\"passenger_name\":\"孙悟空\",\"ticket_class\":204,\"ticket_type\":301,\"ticket_price\":553,\"id_type\":401,\"id_num\":\"320123191201234567\",\"phone_num\":\"13812345678\"},{\"passenger_name\":\"孙尚香\",\"ticket_class\":203,\"ticket_type\":301,\"ticket_price\":933,\"id_type\":401,\"id_num\":\"410881198209211314\",\"phone_num\":\"18612345678\"}]"));
		
		String str1="POST&%2Fv1%2Forder%2Fticket_order_create&arrive_station%3D%E5%8C%97%E4%BA%AC%E5%8D%97%26depart_date%3D2013-09-12%26depart_station%3D%E4%B8%8A%E6%B5%B7%E8%99%B9%E6%A1%A5%26json_param%3D%5B%7B%22passenger_name%22%3A%22%E5%AD%99%E6%82%9F%E7%A9%BA%22%2C%22ticket_class%22%3A204%2C%22ticket_type%22%3A301%2C%22ticket_price%22%3A553%2C%22id_type%22%3A401%2C%22id_num%22%3A%22320123191201234567%22%2C%22phone_num%22%3A%2213812345678%22%7D%2C%7B%22passenger_name%22%3A%22%E5%AD%99%E5%B0%9A%E9%A6%99%22%2C%22ticket_class%22%3A203%2C%22ticket_type%22%3A301%2C%22ticket_price%22%3A933%2C%22id_type%22%3A401%2C%22id_num%22%3A%22410881198209211314%22%2C%22phone_num%22%3A%2218612345678%22%7D%5D%26merchant_id%3D600001%26train_id%3DG102%26user_ip%3D101.102.103.104";
		String str ="POST&%2Fv1%2Forder%2Fticket_order_create&arrive_station%3D%E5%8C%97%E4%BA%AC%E5%8D%97%26depart_date%3D2013-09-12%26depart_station%3D%E4%B8%8A%E6%B5%B7%E8%99%B9%E6%A1%A5%26json_param%3D%5B%7B%22passenger_name%22%3A%22%E5%AD%99%E6%82%9F%E7%A9%BA%22%2C%22ticket_class%22%3A204%2C%22ticket_type%22%3A301%2C%22ticket_price%22%3A553%2C%22id_type%22%3A401%2C%22id_num%22%3A%22320123191201234567%22%2C%22phone_num%22%3A%2213812345678%22%7D%2C%7B%22passenger_name%22%3A%22%E5%AD%99%E5%B0%9A%E9%A6%99%22%2C%22ticket_class%22%3A203%2C%22ticket_type%22%3A301%2C%22ticket_price%22%3A933%2C%22id_type%22%3A401%2C%22id_num%22%3A%22410881198209211314%22%2C%22phone_num%22%3A%2218612345678%22%7D%5D%26merchant_id%3D600001%26train_id%3DG102%26user_ip%3D101.102.103.104";
		System.out.println(str1.equals(str));

	}
}
