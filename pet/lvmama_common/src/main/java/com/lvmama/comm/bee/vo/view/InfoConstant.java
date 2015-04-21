package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;
import java.util.ResourceBundle;

public class InfoConstant implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -357423692626420189L;
	private static ResourceBundle resourceBundle;
	private static InfoConstant instance;
	public void init() {
		resourceBundle = ResourceBundle.getBundle("info");
	}

	public static InfoConstant getInstance() {
		if (instance == null) {
			instance = new InfoConstant();
			instance.init();
		}
		return instance;
	}
	public static String getProperty(String key){
		return String.valueOf(resourceBundle.getString(key));
	}
}
