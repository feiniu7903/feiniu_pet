package com.lvmama.comm.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 点评功能的过滤词过滤类
 * @author Brian
 *
 */
public final class CommentFilterWordsUtil {
	private final static Log LOG = LogFactory.getLog(CommentFilterWordsUtil.class);
	private final static String PASSWORD_WORDS = "******";
	private List<String> fileterWords;
	private static CommentFilterWordsUtil instance;
	private static Object LOCK = new Object();
	public static CommentFilterWordsUtil getInstance()
	{
		if(instance == null){
			synchronized(LOCK) {
				if (instance==null) {
					instance=new CommentFilterWordsUtil();
				}
			}
		}
		return instance;
	}
	
	private CommentFilterWordsUtil() {
		URL url = getClass().getResource("/filterWords.txt");
		if (null == url) {
			LOG.debug("未发现点评需要的过滤词列表，初始化空的过滤词列表");
			fileterWords = new ArrayList<String>(0);
		} else {
			LOG.debug("初始化点评的过滤词列表");
			fileterWords = new ArrayList<String>();
			try {
				File file = new File(url.getFile());
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");   
				BufferedReader reader = new BufferedReader(isr); 
				
				String line = null;
				while ((line = reader.readLine()) != null) {
					LOG.debug("读取行:" + line);
					fileterWords.add(line);
				}
			} catch (Exception e) {
				LOG.error("读取配置文件错误!" + e.getMessage());
			}
		}
	}
	
	public String filter(final String message) {
		String m = null;
		if (!StringUtils.isEmpty(message)) {
			m = message;
			for (String key : fileterWords) {
				m = m.replaceAll(key, PASSWORD_WORDS);
			}
		}
		return m;
	}
	
	public static void main(String[] args) {
		String message = "谷成龙（先生）dfsdfds http：//www.52out.com";
		CommentFilterWordsUtil cfwu = new CommentFilterWordsUtil();
		System.out.println(cfwu.filter(message));
		System.out.println(message);
	}
}

