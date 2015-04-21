package com.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;

import com.lvmama.comm.utils.HttpsUtil;

public class TestPost {
	public static void main(String[] args) throws HttpException, IOException {
		String d = HttpsUtil.requestGet("https://192.168.0.94/clutter/hotel/create.do");
		System.out.println(d);
	}
}
