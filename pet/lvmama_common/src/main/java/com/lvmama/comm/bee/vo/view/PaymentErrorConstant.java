package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;
import java.util.ResourceBundle;

public class PaymentErrorConstant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8282597045466852929L;
	private static ResourceBundle resourceBundle;
	private static PaymentErrorConstant instance;
	public void init() {
		resourceBundle = ResourceBundle.getBundle("payment_error");
	}

	public static PaymentErrorConstant getInstance() {
		if (instance == null) {
			instance = new PaymentErrorConstant();
			instance.init();
		}
		return instance;
	}
	public String getProperty(String key){
		return String.valueOf(resourceBundle.getString(key));
	}
}
