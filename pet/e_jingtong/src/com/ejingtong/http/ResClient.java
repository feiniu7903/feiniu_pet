package com.ejingtong.http;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ejingtong.common.Constans;
import com.ejingtong.help.Tools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ResClient {
	
	private static String LOGIN_URL = "supplier/login.do";//登录接口地址
	private static  String GET_ORDER_URL = "supplier/orders.do"; //获取订单信息 接口地址
	private static  String PASS_URL = "supplier/passInfo.do";
	private static  String PUSH_SUCCESS_CALLBACK_URL = "callback.do";
	private static  String UPLOAD_LOG_URL = "log/uploadLog.do";

	private static AsyncHttpClient client;
	
	public static void post(Context context,RequestParams params,String url,
			AsyncHttpResponseHandler responseHandler){
		
		client = new AsyncHttpClient();

		if(Constans.userInfo != null && Constans.userInfo.getUserId() != null){
			params.put("userId", Constans.userInfo.getUserId());
		}
		
		params.put("udid", Constans.IMEI);
		Log.i("", url + "?" + params);
		client.post(url, params, responseHandler);
	}
	
	public static void get(Context context,RequestParams params,String url,
			AsyncHttpResponseHandler responseHandler){
		
		if(params == null){
			params = new RequestParams();
		}
		
		client = new AsyncHttpClient();
		if(Constans.userInfo != null && Constans.userInfo.getUserId() != null){
			params.put("userId", Constans.userInfo.getUserId());
		}
		params.put("udid", Constans.IMEI);
		Log.i("", url + "?" + params);
		client.get(url, params, responseHandler);
	}

	//登录
	public static void login(Context context, RequestParams params, AsyncHttpResponseHandler handler){
		post(context, params, Constans.ADDR_LOGIC +  LOGIN_URL, handler);
	}
	
	//获取订单详情
	public static void getOrder(Context context,String addCode, RequestParams params, AsyncHttpResponseHandler handler){
		String md5Str = Tools.MD5(Constans.key +Constans.userInfo.getUserId() +  Constans.IMEI + addCode);
		params.put("signName", md5Str);
		post(context, params, Constans.ADDR_LOGIC + GET_ORDER_URL, handler);
	}
	
	//通关
	public static void pass(Context context,String addCode, RequestParams params, AsyncHttpResponseHandler handler){
		String md5Str = Tools.MD5(Constans.key +Constans.userInfo.getUserId() +  Constans.IMEI + addCode);
		params.put("signName", md5Str);
		post(context, params, Constans.ADDR_LOGIC + PASS_URL, handler);
	}
	
	//拉取订单成功后的回调
	public static void pushCackback(Context context, RequestParams params, AsyncHttpResponseHandler handler){
		
		post(context, params, Constans.ADDR_PUSH_BACK + PUSH_SUCCESS_CALLBACK_URL, handler);
	}
	
	
	/**
	 * 文件上传
	 * @param pathFile 上传文件的路径
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String uploadFile(String pathFile, Map<String, String> params) throws ClientProtocolException, IOException, JSONException {
		   HttpClient httpclient = new DefaultHttpClient();
		   //设置通信协议版本
		   httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		   HttpPost httppost = new HttpPost(Constans.ADDR_PUSH_BACK + UPLOAD_LOG_URL);
		   File file = new File(pathFile);

		   System.out.println("file size:" + file.length());
		   
		   MultipartEntity mpEntity = new MultipartEntity(); //文件传输
		   ContentBody cbFile = new FileBody(file);
		   mpEntity.addPart("file", cbFile); // <input type="file" name="userfile" />  对应的
		   
		   if(null != params){
			   for(String key : params.keySet()){
				   mpEntity.addPart(key, new StringBody(params.get(key)));
			   }
		   }
		   
		   httppost.setEntity(mpEntity);
		   System.out.println("executing request " + httppost.getRequestLine());
		   
		   HttpResponse response = httpclient.execute(httppost);
		   HttpEntity resEntity = response.getEntity();

		   System.out.println(response.getStatusLine());//通信Ok
		   String json="";
		   String path="";
		   if (resEntity != null) {
		     //System.out.println(EntityUtils.toString(resEntity,"utf-8"));
		     json=EntityUtils.toString(resEntity,"utf-8");
		     resEntity.consumeContent();
		     
		   }
		   
		   httpclient.getConnectionManager().shutdown();
		   System.out.println("上传后的返回数据:" + json);
		   return path;
		 }

}
