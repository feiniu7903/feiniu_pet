/**
 * 
 */
package com.lvmama.shholiday;

/**
 * @author yangbin
 *
 */
public interface Response {

	/**
	 * 解析响应值
	 * @param responseXml
	 */
	public void parse(String responseXml);
	
	
	/**
	 * 响应状态
	 * @return
	 */
	public boolean isSuccess();
}
