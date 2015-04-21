/**
 * 
 */
package com.lvmama.shholiday;

import java.util.Map;

/**
 * @author yangbin
 *
 */
public interface Request extends Handle{

	/**
	 * 设置TransactionName值
	 * @return
	 */
	public String getTransactionName();
	
	public Class<? extends Response> getResponseClazz();
	
	/**
	 * 请求的uri
	 * @return
	 */
	public String getRequestURI();
}
