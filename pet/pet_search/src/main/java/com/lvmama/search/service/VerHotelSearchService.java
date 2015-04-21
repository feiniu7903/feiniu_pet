package com.lvmama.search.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerHotelSearchVO;
import com.lvmama.comm.search.vo.VerPlaceBean;
import com.lvmama.comm.search.vo.VerPlaceTypeVO;
import com.lvmama.search.util.PageConfig;

/**
 * 酒店搜索
 * 
 * @author yanggan
 * 
 */
public interface VerHotelSearchService extends SearchService {
	
	public List<VerHotelBean> getProduct(String keyword,int size);

	public PageConfig<VerHotelBean> searchHotelName(String keyword) ;

	public List<VerPlaceBean> searchPlace(VerHotelSearchVO verHotelSearchVO);

	VerHotelSearchVO getDistrictId(VerHotelSearchVO verHotelSearchVO);

	public  List<VerPlaceTypeVO> getPlaceCatageory(HashMap hashMap);
	
	public VerPlaceBean getPlaceBean(String placeId);
	
	public List<VerPlaceBean> getPlaceChineseName(String placePinYin);
}