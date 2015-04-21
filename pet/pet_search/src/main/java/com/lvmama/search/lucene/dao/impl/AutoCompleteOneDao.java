package com.lvmama.search.lucene.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.search.vo.AutoCompletePlaceDto;

@SuppressWarnings("unchecked")
@Repository
public class AutoCompleteOneDao extends BaseIbatisDAO {
	/**
	 * 一次查询所有栏目结果ALL
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getOneSearchAutoCompletePlace(Long fromPlaceId) {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AUTO_COMPLETE_ONE.getOneSearch_autoCompletePlace", fromPlaceId);
	}
	
	/**
	 * 目的地搜索自动补全
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getDestAutoCompletePlace() {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AUTO_COMPLETE_ONE.getDest_AutoCompletePlace");
	}

	/**
	 * 门票栏目
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getTicketAutoCompletePlace() {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AUTO_COMPLETE_ONE.getTicket_autoCompletePlace");
	}

	/**
	 * 跟团游
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getGroupAutoCompletePlace(Long fromPlaceId) {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AUTO_COMPLETE_ONE.getGroup_autoCompletePlace", fromPlaceId);
	}

	/**
	 * 自由行(机票+酒店)
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getFreeLongAutoCompletePlace(Long fromPlaceId) {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AUTO_COMPLETE_ONE.getFreeLong_autoCompletePlace", fromPlaceId);
	}
	
	/**
	 * 自由行(景点+酒店)
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getFreetourAutoCompletePlace() {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AUTO_COMPLETE_ONE.getFreetour_autoCompletePlace");
	}

	/**
	  * 酒店名称补全
	 * 
	 * @author wangwei
	 * @return
	 */
	public List<AutoCompletePlaceDto> getHotelAutoComplete() {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AUTO_COMPLETE_ONE.getHotel_AutoComplete");
	}


}