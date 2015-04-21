package com.client;

import java.security.NoSuchAlgorithmException;

import com.lvmama.comm.utils.MD5;

public class TestMd5 {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println(MD5.encode("partner=2088001842589142&service=mobile.merchant.paychannel"));
	}
}
