package com.lvmama.pet.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 中行分期退款请求主机校验. 实现用于主机名验证的基接口。
 * <p>
 *   在握手期间，如果 URL的主机名和服务器的标识主机名不匹配，</br>
 * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。</br>
 * 为防止对方更换IP的情况，故return true;
 * </p>
 * @author sunruyi
 */
public class BOCInstalmentHostnameVerifier implements HostnameVerifier {
	@Override
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}
