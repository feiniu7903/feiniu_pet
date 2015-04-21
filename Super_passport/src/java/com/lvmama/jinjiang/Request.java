/**
 * 
 */
package com.lvmama.jinjiang;

/**
 * @author chenkeke
 *
 */
public interface Request{
	/**
	 * 设置TransactionName值
	 * @return
	 */
	public String getTransactionName();
	/**
	 * 设置TransactionNameMethod值
	 * @return
	 */
	public String getTransactionMethod();
	
	public Class<? extends Response> getResponseClazz();
	
	/**
	 * 请求的uri
	 * @return
	 */
	public String getRequestURI();
	/**
	 * 渠道编码
	 * @return
	 */
	public String getChannelCode() ;
	/**
	 * 时间戳
	 * @return
	 */
	public String getTimestamp();
	/**
	 * 密文
	 * @return
	 */
	public String getCiphertext();
	
	/**
	 * 设置密文
	 * @return
	 */
	public void setCiphertext(String ciphertext);
	
	/**
	 * 设置请求的uri
	 * @param requestURI
	 */
	public void setRequestURI(String requestURI);
	
	/**
	 * 设置时间戳
	 * @param timestamp
	 */
	public void setTimestamp(String timestamp);

	/**
	 * 设置渠道编码
	 * @param channelCode
	 */
	public void setChannelCode(String channelCode);
}
