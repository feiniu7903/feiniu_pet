package com.lvmama.comm.vst.service.search;

import java.util.List;

import com.lvmama.comm.hessian.RemoteService;

@RemoteService("cfrecommendService")
public interface VstCfrecommendService {
	public List<String> recommend(String userID, int num);

}
