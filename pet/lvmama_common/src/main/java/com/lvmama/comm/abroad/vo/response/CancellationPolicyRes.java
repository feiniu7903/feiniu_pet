package com.lvmama.comm.abroad.vo.response;

import java.util.List;
public class CancellationPolicyRes extends ErrorRes{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8904865073880999867L;
	private List<CancellationPolicyDetail> details;
	/**
	 * 取消条款详情
	 * @return
	 */
	public List<CancellationPolicyDetail> getDetails() {
		return details;
	}
	public void setDetails(List<CancellationPolicyDetail> details) {
		this.details = details;
	}
}
