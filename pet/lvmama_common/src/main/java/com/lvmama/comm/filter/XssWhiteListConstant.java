package com.lvmama.comm.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.lvmama.comm.utils.ResourceUtil;

public class XssWhiteListConstant {
	
	private static XssWhiteListConstant instance;
	
	private Set<String> uriSet = new HashSet<String>();;
	private static String xssWhiteListFilePath = "/WEB-INF/resources/xss-white-list.txt";

	public synchronized static XssWhiteListConstant getInstance() {
		if (instance == null) {
			instance = new XssWhiteListConstant();
			instance.init();
		}
		return instance;
	}
	
	/**
	 * 初始化需要增加缓存的URL列表
	 */
	private void init() {
		try {
			File resource = ResourceUtil.getResourceFile(xssWhiteListFilePath);
			BufferedReader br = new BufferedReader(new FileReader(resource));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				uriSet.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断传进来的uri是否在禁止缓存的uri列表里面
	 * 
	 * @param uri
	 * @return URI列表中是否包含指定URI
	 */
	public boolean contains(String uri) {
		for (String line : uriSet) {
			if (uri.matches(line)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		XssWhiteListConstant constant = XssWhiteListConstant.getInstance();
		System.out.println(constant.contains("/tuiguang/QQCbRedirect"));
	}
	
}
