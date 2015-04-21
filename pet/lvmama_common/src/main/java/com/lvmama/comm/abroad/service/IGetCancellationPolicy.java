package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.CancellationPolicyMergeReq;
import com.lvmama.comm.abroad.vo.request.CancellationPolicyReq;
import com.lvmama.comm.abroad.vo.response.CancellationPolicyRes;

public interface IGetCancellationPolicy {
	/**
	 *  查询预订订单取消规则
	 * @param getCancellationPolicyReq
	 * @return
	 */
	public CancellationPolicyRes queryCancellationPolicy( CancellationPolicyReq CancellationPolicyReq,String sessionId);
	/**
	 * 查询预订订单取消规则（支持查询多个房间）
	 * @param cancellationPolicyMergeReq
	 * @param sessionId
	 * @return
	 */
	public CancellationPolicyRes queryCancellationMergePolicy(CancellationPolicyMergeReq cancellationPolicyMergeReq,String sessionId);
}
