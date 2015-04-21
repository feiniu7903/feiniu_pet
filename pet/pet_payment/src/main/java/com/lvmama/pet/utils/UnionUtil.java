package com.lvmama.pet.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lvmama.comm.utils.HttpsUtil;

public class UnionUtil {
	
	/**
	 * 加密方式
	 * @param str
	 * @param signType
	 * @param charset
	 * @return
	 */
	public static String md5(String str,String signType,String charset) {
		if (str == null) {
			return null;
		}
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(signType);
			messageDigest.reset();
			messageDigest.update(str.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
			return str;
		} catch (UnsupportedEncodingException e) {
			return str;
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
	
	
	//组装预授权完成请求包
	public final static String[] reqSuccVo = new String[]{
		"acqCode",
		"backEndUrl",
		"charset",
		"commodityDiscount",
		"commodityName",
		"commodityQuantity",
		"commodityUnitPrice",
		"commodityUrl",
		"customerIp",
		"customerName",
		"defaultBankNumber",
		"defaultPayType",
		"frontEndUrl",
		"merAbbr",
		"merCode",
		"merId",
		"merReserved",
		"orderAmount",
		"orderCurrency",
		"orderNumber",
		"orderTime",
		"origQid",
//		"signMethod",
//		"signature",
		"transTimeout",
		"transType",
		"transferFee",
		"version"
	};

	//组装预授权查询请求包
	public final static String[] reqQueryVo = new String[]{
		"version",
		"charset",
		"transType",
		"merId",
		"orderNumber",
		"orderTime",
		"merReserved"
	};

	//组装支付查询成功交易返回信息包
	public final static String[] reqQuerySuccessVo = new String[]{
		"qid",
		"respTime",
		"respMsg"
	};

	
	/**
	 * 向银联发送查询请求
	 * @param transType
	 * @param orderNumber
	 * @param orderTime
	 * @return
	 */
	public static String getUnionPaySyncRes(String key,String requestUrl,String[] valueVo) {
		Map<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < reqSuccVo.length; i++) {
			map.put(reqSuccVo[i], valueVo[i]);
		}
		map.put("signature", signMap(map,key));
		map.put("signMethod", "MD5");
		return HttpsUtil.requestPostForm(requestUrl, map);
	}
	
	/**
	 * 向银联发送查询请求
	 * @param transType
	 * @param orderNumber
	 * @param orderTime
	 * @return
	 */
	public static String getUnionPayQueryRes(String key,String requestUrl,String[] valueVo) {
		Map<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < reqQueryVo.length; i++) {
			map.put(reqQueryVo[i], valueVo[i]);
		}
		map.put("signature", signMap(map,key));
		map.put("signMethod", "MD5");
		return HttpsUtil.requestPostForm(requestUrl, map);
	}
	
     /**
  	 * 生成加密钥
  	 * 
  	 * @param map
  	 * @param secretKey
  	 *            商城密钥
  	 * @return
  	 */
  	private static String signMap(Map<String, String> map,String key) {
  		String keyString="";
		String strBeforeMd5 = joinMapValue(map, '&')+md5(key, "MD5", "UTF-8");
		keyString = md5(strBeforeMd5, "MD5", "UTF-8");
   		return keyString;
  	}
     
  	/**
	 * 包装请求数据 .
	 * @param map
	 * @param connector
	 * @return
	 */
	private static  String joinMapValue(Map<String, String> map, char connector) {
		StringBuffer b = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			b.append(entry.getKey());
			b.append('=');
			if (entry.getValue() != null) {
				b.append(entry.getValue());
			}
			b.append(connector);
		}
		return b.toString();
	}
	
	/**
	 * 银行返回信息包装.
	 * @param str
	 * @return
	 */
	public static String[] getResArr(String str) {
		String regex = "(.*?cupReserved\\=)(\\{[^}]+\\})(.*)";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);

		String reserved = "";
		if (matcher.find()) {
			reserved = matcher.group(2);
		}

		String result = str.replaceFirst(regex, "$1$3");
		String[] resArr = result.split("&");
		for (int i = 0; i < resArr.length; i++) {
			if ("cupReserved=".equals(resArr[i])) {
				resArr[i] += reserved;
			}
		}
		return resArr;
	}
	

	//验证签名 
	public static boolean checkSecurity(String key,String[] arr){
		//验证签名
		int checkedRes = checkSecurityString(key,arr);
		if(checkedRes==1){
			return true;
		}else if(checkedRes == 0){
			return false;
		}else if(checkedRes == 2){
			return false;
		}
		return false;
	}
	
	

	/**
	 * 商户的业务逻辑
	 * @param arr
	 * @return
	 */
	public static String getRespCode(String[] arr) {
		// 以下是商户业务处理
		String result = "";
		for (int i = 0; i < arr.length; i++) {
			String[] resultArr = arr[i].split("=");
			// 处理商户业务逻辑
			if (resultArr.length >= 2 && "respCode".equals(resultArr[0])) {
				result = arr[i].substring(resultArr[0].length() + 1);
				break;
			}
		}
		return result;
	}
	
	/**
	 *获取查询结果中支付状态
	 * @param arr
	 * @return
	 */
	public static String getQueryResultCode(String[] arr) {
		// 以下是商户业务处理
		String result = "";
		for (int i = 0; i < arr.length; i++) {
			String[] resultArr = arr[i].split("=");
			// 处理商户业务逻辑
			if (resultArr.length >= 2 && "queryResult".equals(resultArr[0])) {
				result = arr[i].substring(resultArr[0].length() + 1);
				break;
			}
		}
		return result;
	}


	/**
	 *获取查询结果中支付成功状态相关参数
	 * @param arr
	 * @return
	 */
	public static Map<String, String> getQuerySuccessAllCode(String[] arr) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < arr.length; i++) {
			String[] resultArr = arr[i].split("=");
			if (resultArr.length >= 2 && "qid".equals(resultArr[0])) {
				map.put(reqQuerySuccessVo[0], arr[i].substring(resultArr[0].length() + 1));
				break;
			}
			if (resultArr.length >= 2 && "respTime".equals(resultArr[0])) {
				map.put(reqQuerySuccessVo[1], arr[i].substring(resultArr[0].length() + 1));
				break;
			}
			if (resultArr.length >= 2 && "respMsg".equals(resultArr[0])) {
				map.put(reqQuerySuccessVo[2], arr[i].substring(resultArr[0].length() + 1));
				break;
			}
		}
		return map;
	}
	
	/**
	 * 查询验证签名
	 * 
	 * @param valueVo
	 * @return 0:验证失败 1验证成功 2没有签名信息（报文格式不对）
	 */
	private static int checkSecurityString(String key,String[] valueVo) {
		Map<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < valueVo.length; i++) {
			String[] keyValue = valueVo[i].split("=");
			map.put(keyValue[0], keyValue.length >= 2 ? valueVo[i].substring(keyValue[0].length() + 1) : "");
		}
		if ("".equals(map.get("signature"))) {
			return 2;
		}
		String signature = map.get("signature");
		boolean isValid = false;
		if ("MD5".equalsIgnoreCase(map.get("signMethod"))) {
			map.remove("signature");
			map.remove("signMethod");
			String returnStringMd5 = md5(joinMapValue(map, '&') + md5(key,"MD5", "UTF-8"), "MD5", "UTF-8");
			isValid = signature.equals(returnStringMd5);
		} 

		return (isValid ? 1 : 0);
	}
 }
