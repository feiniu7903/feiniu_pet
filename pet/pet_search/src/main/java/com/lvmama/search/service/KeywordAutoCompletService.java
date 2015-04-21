package com.lvmama.search.service;

import java.util.List;

import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;

/**
 * 下拉补全逻辑
 * 
 * @author HZ
 * 
 */
public interface KeywordAutoCompletService {
	/**
	 * 酒店自动补全
	 * @param keyword
	 * @return
	 */
	public List<AutoCompletePlaceObject> hotelAutoComplete(String keyword);
	
	public List<AutoCompletePlaceObject> getKeywordAutoComplet(String fromChannel,String fromDestId, String keyword);
	
	public List<AutoCompletePlaceObject> destAutoComplete(String keyword) ;
	
	public List<AutoCompleteVerHotel> verHotelAutoComplete(String keyword) ;
	
}
