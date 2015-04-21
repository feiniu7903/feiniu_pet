package com.lvmama.search.service.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.service.ClientHotelSearchService;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;
import com.lvmama.comm.search.vo.AutoCompleteVerHotelCity;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerHotelSearchVO;
import com.lvmama.comm.search.vo.VerPlaceBean;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.query.ClientHotelQueryUtil;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteOneService;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.service.VerHotelSearchService;
import com.lvmama.search.util.SORT;

/**
 * 酒店搜索接口
 * @author ltwangwei
 *
 */
@HessianService("clientHotelSearchService")
@Service("clientHotelSearchService")
public class ClientHotelSearchServiceImpl implements ClientHotelSearchService {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	protected NewBaseSearchService newVerHotelSearchService;
	
	@Autowired
	protected AutoCompleteOneService autoCompleteOneService;
	
	@Autowired 
	protected VerHotelSearchService verHotelSearchService;

	/**
	 * 酒店数据查询
	 */
	@SuppressWarnings("unchecked")
	public Page<VerHotelBean> search(VerHotelSearchVO verHotelSearchVO, int sort) {
		logger.info("before invoke hotel search...");
		
		ArrayList<VerHotelBean> verList = new ArrayList<VerHotelBean>();
		
		//有经纬度的
		if(StringUtil.isNotEmptyString(verHotelSearchVO.getLongitude())  &&
				StringUtil.isNotEmptyString(verHotelSearchVO.getLatitude()) &&
				!verHotelSearchVO.getLongitude().equals("0.0")&&
				!verHotelSearchVO.getLatitude().equals("0.0") &&
				!"1".equals(verHotelSearchVO.getRanktype())){
			
			verHotelSearchVO.setRanktype("2");
		}else{
			//搜索条件没有经纬度
			verHotelSearchVO.setRanktype("1");
		}
		
		Query query = ClientHotelQueryUtil.getVerHotelQuery(verHotelSearchVO);
		
		verList = (ArrayList<VerHotelBean>) newVerHotelSearchService.search(query, verHotelSearchVO, SORT.getSort(sort));
		
		Page<VerHotelBean> result = new Page<VerHotelBean>(verList.size(), verHotelSearchVO.getPageSize(), verHotelSearchVO.getPage());
		
		result.setAllItems(verList);
		
		return result;
	}

	/**
	 * 酒店城市,酒店名称,周边景点 三合一自动补全
	 * @param keyword
	 * @return
	 */
	public List<AutoCompleteVerHotel> keyWordAutoComplete(String keyword){
		
		List<AutoCompleteVerHotel> list =  autoCompleteOneService.getAutoCompleteVerHotelListMatched(SearchConstants.HOTEL_MERGE_COMPLETE, null, keyword, null, false);
		
		if(list != null && list.size() == 0 && StringUtil.isNotEmptyString(keyword)){
			
			keyword = keyword.substring(0, keyword.length()-1);
			
			return this.keyWordAutoComplete(keyword);
		}
		
		List<AutoCompleteVerHotel> list_district = new ArrayList<AutoCompleteVerHotel>();
		
		List<AutoCompleteVerHotel> list_landmark = new ArrayList<AutoCompleteVerHotel>();
		
		List<AutoCompleteVerHotel> list_hotel = new ArrayList<AutoCompleteVerHotel>();
		
		List<AutoCompleteVerHotel> return_list = new ArrayList<AutoCompleteVerHotel>();
		
		int maxSzie = list.size() > 10 ? 10 : list.size();
		
		for (int i = 0; i < maxSzie; i++) {
			
			AutoCompleteVerHotel ap = list.get(i);
			
			if("1".equals(ap.getAutocompleteMark())){
				
				list_district.add(ap);
			}else if("2".equals(ap.getAutocompleteMark())){
				
				list_landmark.add(ap);
			}else if("3".equals(ap.getAutocompleteMark())){
				
				list_hotel.add(ap);
			}
		}
		
		return_list.addAll(list_district);
		
		return_list.addAll(list_landmark);
		
		return_list.addAll(list_hotel);
		
		return return_list;
	}
	

	/**
	 * 获取城市列表
	 * @param keyword
	 * @return
	 */
	public List<AutoCompleteVerHotelCity> getCitiesByKeyword(String keyword){
		
		List<AutoCompleteVerHotelCity> list =  autoCompleteOneService.getAutoCompleteVerHotelCitiesMatched(SearchConstants.HOTEL_MERGE_COMPLETE, null, keyword, null, false);
		
		if(list != null && list.size() == 0 && StringUtil.isNotEmptyString(keyword)){
			
			keyword = keyword.substring(0, keyword.length()-1);
			
			return this.getCitiesByKeyword(keyword);
		}
		
		return list;
	}

	/**
	 * 获取地标、商圈、景区
	 * @param verHotelSearchVO
	 * @return
	 */
	public List<VerPlaceBean> getVerPlaceList(VerHotelSearchVO verHotelSearchVO) {
		
		return verHotelSearchService.searchPlace(verHotelSearchVO);
	}
	
	
	
}























