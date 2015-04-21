package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;
import java.util.List;

public class CancellationPolicyMergeReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8705123822243538036L;
	private List<CancellationPolicyMergeRoom> rooms;
	public List<CancellationPolicyMergeRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<CancellationPolicyMergeRoom> rooms) {
		this.rooms = rooms;
	}
}
