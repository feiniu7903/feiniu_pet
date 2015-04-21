package com.client;

import java.security.NoSuchAlgorithmException;

import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;

public class TestEbk {
	public static void main(String[] args) throws NoSuchAlgorithmException {
//		String key = "6srGIiQLO8KSMF8IsFwgOaNgjMvetmBZAoPtSUxKWdoveBl1b3VEgUvJqDnz0uWvaO8KWMkxPi8sokEpjEi0fmFinYQv5l69kyMblFsrOL4z42rVRVCrzqal1KGgiRNZ";
////		String addCode="57622223";
////		String imei = "865854015116217";
////		String md5Str = MD5.encode("6srGIiQLO8KSMF8IsFwgOaNgjMvetmBZAoPtSUxKWdoveBl1b3VEgUvJqDnz0uWvaO8KWMkxPi8sokEpjEi0fmFinYQv5l69kyMblFsrOL4z42rVRVCrzqal1KGgiRNZ" + "425"
////				+ imei + addCode);
////		String requestUrl = String.format("http://api3g.lvmama.com//clutter/supplier/passInfo.do?userId=425&udid=%s&item=3073165_1&addCode=%s&signName=%s&performTime=2013-09-09 11:20:33",imei, addCode,md5Str);
////		System.out.println(requestUrl);
//		String signName = MD5.encode(key+String.format("164865854015417813%s", "19837588"));
//		String requestUrl = String.format("http://api3g.lvmama.com/clutter/supplier/orders.do?userId=164&addCode=%s&signName=%s&udid=%s", "19837588",signName,"865854015417813");
//		System.out.println(requestUrl);
		StringUtil.isPinyin("ADDFSss");
	}
}
