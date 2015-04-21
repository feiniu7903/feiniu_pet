package com.lvmama.search.service;

import java.util.List;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.search.util.PageConfig;

/**
 * 酒店搜索
 * 
 * @author yanggan
 * 
 */
public interface HotelSearchService extends SearchService {
	
	public List<ProductBean> getProduct(String keyword,int size);

	public PageConfig<PlaceBean> searchHotelName(String keyword) ;
}