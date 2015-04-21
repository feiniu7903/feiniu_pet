package com.lvmama.search.lucene.service.search;

import java.util.List;

import org.apache.lucene.search.Query;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 搜索基类
 * 
 * @author YangGan
 * 
 */

@SuppressWarnings("rawtypes")
public interface NewBaseSearchService {
	public List search(Query query, SORT... sorts);

	public List search(int max_search_size, Query query, SORT... sorts);

	public PageConfig search(int pageSize, int currentPage, Query query,
			SORT... sorts);
	/*
	 * 新增查询和原来的查询一样，就是带查询参数进入
	 */
	public PageConfig search(Integer pageSize, Integer page,
			Query query_2, SearchVO sv, SORT[] sorts);
	/*
	 * 新增查询和原来的查询一样，就是带查询参数进入
	 */
	public List search(int max_search_size, Query query, SearchVO sv,
			SORT... sorts);
	/*
	 * 新增查询和原来的查询一样，就是带查询参数进入
	 */
	public List search(Query query, SearchVO sv, SORT... sorts);

}
