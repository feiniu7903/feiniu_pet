package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.response.IDSession;

public interface ILogin {
	/**
	 * 直接获取IDSession
	 * @return
	 */
	public String getIDSession();
	/**
	 * 获取IDSession
	 * @return
	 */
	public IDSession getIDSessionBySessionId(String sessionId);
}
