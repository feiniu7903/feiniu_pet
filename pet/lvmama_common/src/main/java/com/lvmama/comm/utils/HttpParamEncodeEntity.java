/**
 * 
 */
package com.lvmama.comm.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.util.Assert;

/**
 * @author yangbin
 *
 */
public class HttpParamEncodeEntity extends StringEntity{

	public HttpParamEncodeEntity(List<NameValuePair> formParams, String charset) {
		super(ParamEncodeUtil.format(formParams,charset), ContentType.create(URLEncodedUtils.CONTENT_TYPE, charset));
	}
	
	
	private static class ParamEncodeUtil{
		
		static String format(List<NameValuePair> formParams,String requestCharacter){
			StringBuffer sb =new StringBuffer();
			Assert.notEmpty(formParams);
			for(NameValuePair nv:formParams){
				if(sb.length()>0){
					sb.append("&");
				}
				sb.append(encodeString(nv.getName(),requestCharacter));
				sb.append("=");
				sb.append(encodeString(nv.getValue(),requestCharacter));
			}
			
			return  sb.toString();
		}
		
		private static String encodeString(String str,String requestCharacter){
			try {
				return URLEncoder.encode(str, requestCharacter);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}
		
	}

}
