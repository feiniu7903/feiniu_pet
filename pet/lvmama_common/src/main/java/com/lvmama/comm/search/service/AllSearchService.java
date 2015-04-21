package com.lvmama.comm.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.TreeBean;


/**
 * 手机端PLACE相关搜索
 * 
 * @author hz
 * 
 */
@RemoteService("allSearchService")
public interface AllSearchService {
	
	
	public Map  getAllSearch(Map<String,String> map);
	

}
