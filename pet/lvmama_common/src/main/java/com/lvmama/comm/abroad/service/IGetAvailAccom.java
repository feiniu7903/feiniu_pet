package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.AvailAccomAdvancedReq;
import com.lvmama.comm.abroad.vo.response.AvailAccomAdvancedRes;


public interface IGetAvailAccom{
	/**
	 * 酒店房间明细查询（支持排序、分页）
	 * @param availAccomAdvancedReq
	 * @param startResult
	 * @param maxResults
	 * @param sortStr
	 * @return
	 */
	public AvailAccomAdvancedRes queryAvailAccomAdvancedList(
			AvailAccomAdvancedReq availAccomAdvancedReq,String sessionId,int startResult,int maxResults);
}
