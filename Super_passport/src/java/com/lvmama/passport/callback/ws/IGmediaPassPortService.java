package com.lvmama.passport.callback.ws;
/**
 * 银河回调服务接口实现
 * @author chenlinjun
 *
 */
public interface IGmediaPassPortService {
	/**
	 * 回收请求
	 * @param reqXml
	 * @return
	 */
   String useCodeReq(String reqXml);
   /**
    * 修改人数
    * @param reqXml
    * @return
    */
   String proxyReq (String reqXml);
   /**
    * 验码
    * @param reqXml
    * @return
    */
   String validCodeReq (String reqXml);
	/**
	 * 同步凭证多条
	 * 二维码平台向合作伙伴发送同步凭证请求
	 * @param arg
	 * @return
	 */
	public String getVouchers(String reqXml);
	
	/**
	 * 同步打印内容
	 * 二维码平台向合作伙伴发送同步打印内容请求
	 * @param arg
	 * @return
	 */
	public String getPrintFormat (String arg);
	
	/**
	 * 同步凭证 单条
	 * 二维码平台向合作伙伴发送同步凭证请求。
	 * 
	 * @param reqXml
	 * @return
	 */
	public String getSingleVoucher (String reqXml);
}
