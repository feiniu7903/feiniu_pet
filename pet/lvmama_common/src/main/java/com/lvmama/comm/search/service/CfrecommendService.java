package com.lvmama.comm.search.service;

import java.util.List;

import com.lvmama.comm.hessian.RemoteService;

@RemoteService("cfrecommendService")
public interface CfrecommendService {
	public List<String> recommend(String userID,int num);
	
}
 