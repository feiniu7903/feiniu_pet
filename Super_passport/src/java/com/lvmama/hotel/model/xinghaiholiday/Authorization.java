package com.lvmama.hotel.model.xinghaiholiday;

import java.security.NoSuchAlgorithmException;

import com.lvmama.comm.utils.MD5;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 授权信息
 */
public class Authorization {
	private static Authorization authorization = new Authorization();

	/** 客户接口ID号 */
	private String customerID = WebServiceConstant.getProperties("xinghaiholiday.customerID");
	/** 接口密钥 */
	private String customerKey = WebServiceConstant.getProperties("xinghaiholiday.customerKey");
	/** 订单操作员用户名 */
	private String contactUser = WebServiceConstant.getProperties("xinghaiholiday.contactUser");
	/** 订单操作员姓名 */
	private String contactName = WebServiceConstant.getProperties("xinghaiholiday.contactName");
	/** 1 为酒店预订 */
	private String bookType = WebServiceConstant.getProperties("xinghaiholiday.bookType");

	private Authorization() {
	}

	public static Authorization getInstance() {
		return authorization;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getContactUser() {
		return contactUser;
	}

	public String getContactName() {
		return contactName;
	}

	public String getBookType() {
		return bookType;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	/**
	 * 客户签名字符串SignStr为16 位字符串，由CustomerID & CustomerKey & CustomerID 连接后进行 MD5 16 位加密
	 */
	public String getSignStr() throws NoSuchAlgorithmException {
		return MD5.encode16(customerID + customerKey + customerID);
	}
}
