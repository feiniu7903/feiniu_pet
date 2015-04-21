package com.lvmama.search.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {
	private static Properties p = new Properties();
	static {
		try {
			InputStream inputStream = ConfigHelper.class.getClassLoader().getResourceAsStream("lvmama_search.properties");
			p.load(inputStream);
			inputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static String getString(String keyword) {
		return p.getProperty(keyword);
	}

	/**
	 * 根据路径返回配置文件
	 * 
	 * @param path
	 * @return
	 */
	public static final Properties getProperties(String path) {
		Properties properties = new Properties();
		try {
			InputStream inputStream = ConfigHelper.class.getClassLoader().getResourceAsStream(path);
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
