/**
 * 
 */
package com.lvmama.jinjiang;

/**
 * @author chenkeke
 *
 */
public interface Response {

	/**
	 * 解析响应值
	 * @param responseJson
	 * @return 
	 */
	public <T extends Response>T parse(String responseJson);
	
	
	/**
	 * 响应状态
	 * @return
	 */
	public boolean isSuccess();
}
