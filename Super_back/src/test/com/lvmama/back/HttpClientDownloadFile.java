package com.lvmama.back;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.lvmama.comm.vo.Constant;


public class HttpClientDownloadFile {
	
	private final static String REMOTE_FILE_URL = "http://pic.lvmama.com/pics/";
	
	private final static int BUFFER = 1024;

	public static void main(String[] args) {

	   HttpClient client = new HttpClient();
	   String path = "super/2013/12/DUEHJ.docx";
	   GetMethod httpGet = new GetMethod(REMOTE_FILE_URL+path);  
		try {
			
			client.executeMethod(httpGet);
			InputStream in = httpGet.getResponseBodyAsStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
		   
		    byte[] b = new byte[BUFFER];
		    int len = 0;
			while((len=in.read(b))!= -1){
				bos.write(b, 0, len);
			}			
			byte[] byteArrays =  bos.toByteArray();
			in.close();
			bos.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpGet.releaseConnection();
		}
       	System.out.println("download, success!!");
       }
}
