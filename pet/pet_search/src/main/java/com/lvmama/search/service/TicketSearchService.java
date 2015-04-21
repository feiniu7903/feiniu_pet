package com.lvmama.search.service;

import java.util.Map;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.search.util.PageConfig;


/**
 * 门票搜索
 * 
 * @author YangGan
 *
 */
public interface TicketSearchService extends SearchService{
	public PageConfig<Map<String, Object>> getTicketProducts(PageConfig<PlaceBean> placePageConfig ,SearchVO sv )  ;

}
