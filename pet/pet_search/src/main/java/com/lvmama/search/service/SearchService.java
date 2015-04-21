package com.lvmama.search.service;

import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;
/**
 * 搜索基础接口 
 * @author HZ
 *
 */
public interface SearchService {
		
	public PageConfig search(SearchVO sv, SORT... sorts);
	
}
