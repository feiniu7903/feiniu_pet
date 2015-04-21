package com.lvmama.search.lucene.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.search.vo.AutoCompletePlaceDto;
import com.lvmama.comm.search.vo.CitySubject;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;

/**
 * 客户端自动补全数据查询
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class AutoCompleteDao extends BaseIbatisDAO {


	/**
	 * 手机栏目下拉提示
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getClientAutoCompletePlace() {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AutoComplete.getClient_autoCompletePlace");
	}

	/**
	 * 手机栏目下拉提示/根据STAGE=1,2,3筛选
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getClientAutoCompletePlaceByStage(Long stage) {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AutoComplete.getClient_autoCompletePlaceByStage", stage);
	}

	/**
	 * 手机栏目地标下拉提示
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getClientPlaceAutoComplete(ClientPlaceSearchVO searchVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stage", searchVo.getStage());
		map.put("channel", searchVo.getChannel());		
		List<String> productTypeLt = searchVo.getProductType();
		List<String> productTypes = new ArrayList<String>();
		if (productTypeLt != null && !productTypeLt.isEmpty()) {
			for (int i = 0; i < productTypeLt.size(); i++) {
				if(productTypeLt.get(i).equals("TICKET")){
					productTypes.add("TICKET_NUM");
				} else if(productTypeLt.get(i).equals("HOTEL")){
					productTypes.add("HOTEL_NUM");
				} else if(productTypeLt.get(i).equals("ROUTE")){
					productTypes.add("ROUTE_NUM");
					productTypes.add("FREENESS_NUM");
				}
			}
		}
		if(!productTypes.isEmpty()){
			map.put("productType", productTypes);	
		}
		
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AutoComplete.getClient_getPlaceAutoComplete", map);
	}

	/**
	 * 手机栏目线路下拉提示
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getClientRouteAutoComplete(ClientRouteSearchVO searchVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromDest", searchVo.getFromDest());
		map.put("toDest", searchVo.getToDest());
		map.put("productType", searchVo.getProductType());
		map.put("subProductType", searchVo.getSubProductType());
		map.put("channel", searchVo.getChannel());
		System.out.println(map.toString());
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AutoComplete.getClient_getRouteAutoComplete", map);
	}

	/**
	 * 手机栏目获得所有景点主题
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getClientSubjectAll() {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AutoComplete.getClient_subjectAll");
	}

	/**
	 * 手机栏目获得制定城市的景点主题
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getClientSubjectByCity(Long cityId) {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AutoComplete.getClient_subjectByCity", cityId);
	}

	/**
	 * 手机栏目获得所以路线的主题/标红主题
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getClientRouteSubjectAll() {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AutoComplete.getClient_routeSubjectAll");
	};

	/**
	 * 手机栏目获得制定城市下路线的主题/标红主题
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompletePlaceDto> getClientRouteSubjectByCity(Long cityId) {
		return (List<AutoCompletePlaceDto>) this.queryForListForReport("AutoComplete.getClient_routeSubjectByCity", cityId);
	};

	public Long countAllCityAllSubjects(Map<String,Object> params){
		return (Long) this.queryForObject("AutoComplete.countAllCityAllSubjects", params);
	}
	
	public List<CitySubject> selectAllCityAllSubjects(Map<String,Object> params){
		return (List<CitySubject>) this.queryForListForReport("AutoComplete.selectAllCityAllSubjects", params);
	}


}