package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.DownOrderReq;
import com.lvmama.comm.abroad.vo.response.DownOrderRes;

public interface IDownOrder {
	public DownOrderRes downOrder(DownOrderReq req,String sessionId);

}
