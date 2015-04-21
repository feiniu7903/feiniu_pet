package com.lvmama.comm.vst.service.search;

import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;

/**
 * 手机端PLACE相关搜索
 * 
 * @author hz
 * 
 */
@RemoteService("allSearchService")
public interface VstAllSearchService {

	@SuppressWarnings("rawtypes")
	public Map getAllSearch(Map<String, String> map);

}
