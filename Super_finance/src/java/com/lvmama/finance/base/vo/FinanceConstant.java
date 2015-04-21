package com.lvmama.finance.base.vo;

import java.util.ResourceBundle;

/**
 * 从配置文件中加载出来的常量
 * 
 * @author ranlongfei 2012-7-2
 * @version
 */
public class FinanceConstant {
	private ResourceBundle resourceBundle;
	private static FinanceConstant instance;
	private long lastupdateTime;

	public synchronized static FinanceConstant getInstance() {
		if (instance == null) {
			instance = new FinanceConstant();
			instance.init();
		} else if (instance.isExpired()) {
			instance.init();
		}
		return instance;
	}

	/**
	 * 获得资源：ClassPath下
	 * 
	 * @author: ranlongfei 2012-7-2 下午12:09:03
	 */
	private void init() {
		resourceBundle = ResourceBundle.getBundle("finance");
		lastupdateTime = System.currentTimeMillis();
	}

	private boolean isExpired() {
		return (System.currentTimeMillis() - lastupdateTime) > 3600 * 1000;
	}

	public String getProperty(String key) {
		try {
			return String.valueOf(resourceBundle.getString(key));
		} catch (Exception e) {
			System.out.println(" key: " + key + " not found.");
		}
		return "";
	}

	public String getUTF8StringProperty(String key) {
		String result = null;
		String ss = resourceBundle.getString(key);
		try {
			result = new String(ss.getBytes(), "UTF-8");
		} catch (Exception e) {
		}

		return result;
	}

	public int getIntegerProperty(String key) {
		int num = 0;
		String ss = resourceBundle.getString(key);
		try {
			num = Integer.valueOf(ss).intValue();
		} catch (Exception e) {
		}

		return num;
	}

	public double getDoubleProperty(String key) {
		double num = 0.0;
		String ss = resourceBundle.getString(key);
		try {
			num = Double.valueOf(ss).doubleValue();
		} catch (Exception e) {
		}

		return num;
	}

	public boolean getBooleanProperty(String key) {
		return "true".equalsIgnoreCase(resourceBundle.getString(key));
	}
}
