package com.lvmama.comm.pet.service.place;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceAirline;
import com.lvmama.comm.pet.po.place.PlaceAirport;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.po.place.PlacePlaneModel;
/**
 * 机票相关的place服务接口
 * @author yangbin
 *
 */
public interface PlaceFlightService {

	/**
	 * 保存航班信息
	 * @param flight
	 * @return
	 */
	void saveFlight(PlaceFlight flight);
	
	/**
	 * 保存机型
	 * @param model
	 * @return
	 */
	void savePlaneModel(PlacePlaneModel model);
	
	/**
	 * 保存航空公司
	 * @param airline
	 * @return
	 */
	void saveAirline(PlaceAirline airline);
	
	 
	 List<PlaceAirline> queryPlaceAirLineListByParam(Map<String,Object> param);
	 
	 Long countPlaceAirLinetListByParam(Map<String,Object> param);

	 
	 /**
	  * 查询航班	 
	  * @param pk
	  * @return
	  */
	 PlaceFlight queryPlaceFlight(final Long pk);
	 
	 /**
	  * 根据航班号查询航班
	  * @param flightNo 航班号
	  * @return
	  */
	 PlaceFlight queryPlaceFlight(final String flightNo);
	 
	 void updateByPrimaryKey(PlaceFlight flight);
	 
	 List<PlaceFlight> queryPlaceFlightListByParam(Map<String,Object> param);
	 
	 Long countPlaceFlightListByParam(Map<String,Object> param);
	 
	 Long countPlaceFlightByflightNo(String flightNo);
	 
	 void updateByPrimaryKey(PlaceAirline airline);
	 
	 PlaceAirline queryPlaceAirline(final Long pk);
	 
	 Long countAirlineByCode(String airlineCode);
	 
	 Long countPlaneModelByCode(String planCode);
    
	 /**
	  * 保存机场
	  */
	 Long savePlaceAirport(PlaceAirport placeAirport);
	 
	 List<PlacePlaneModel> queryPlacePlaneModelListByParam(Map<String,Object> param);
	 
	 Long countPlacePlaneModelListByParam(Map<String,Object> param);
	 
	 void updateByPrimaryKey(PlacePlaneModel airline);
	 
	 PlacePlaneModel queryPlacePlaneModel(final Long pk);
	 
	 List<PlacePlaneModel> queryPlacePlaneModelList();
	 
	 List<PlaceAirline> queryPlaceAirlineList();

	 void deletePlaceAirportByPlaceAirportId(Long placeAirportId);
	 
	 PlaceAirport getPlaceAirportByKey(Long placeAirportId);
	
	 List<PlaceAirport> queryPlaceAirportListByParam(Map<String,Object> param);
	 
	 Long countPlaceAirportListByParam(Map<String,Object> param);

	 int updatePlaceAirport(PlaceAirport placeAirport);
	 
	 /**
	  * 查询航班详细信息
	  * @param pk
	  * @return
	  */
	 PlaceFlight queryPlaceFlightDetail(final Long pk);
	 
	 /**
	  * 根据航班号查询航班详细信息
	  * @param flightNo 航班号
	  * @return
	  */
	 PlaceFlight queryPlaceFlightDetail(final String flightNo);
	 
	 Place queryPlaceByPlaceId(final long pk);
	 
}
