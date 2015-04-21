/**
 * 
 */
/**
 * @author yangbin
 *
 */
package com.lvmama.shholiday.notify;

enum PRODUCT_NOTIFY_TYPE{
	/** 产品基本信息 */
	PRODUCTINFO,
	/**产品图片信息修改 */
	IMAGES,
	/**产品价格信息 */
	TEAMPRODUCTPRICES,
	/**产品每日行程安排信息 */
	DAYTRIPINFOS,
	/**产品每日行程安排信息明细  */
	DAYTRIPDETAILS,
	/**产品提示 */
	PRODUCTNOTICES
}


enum NOTIFY_ERROR_CODE{
	Err80001("80001","文件地址错误");
	
	private String code;
	private String desc;
	NOTIFY_ERROR_CODE(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	
	
}