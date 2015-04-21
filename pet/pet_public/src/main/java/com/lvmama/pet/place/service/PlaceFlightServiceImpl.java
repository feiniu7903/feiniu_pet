package com.lvmama.pet.place.service;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceAirline;
import com.lvmama.comm.pet.po.place.PlaceAirport;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.po.place.PlacePlaneModel;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.pet.place.dao.PlaceAirlineDAO;
import com.lvmama.pet.place.dao.PlaceAirportDAO;
import com.lvmama.pet.place.dao.PlaceDAO;
import com.lvmama.pet.place.dao.PlaceFlightDAO;
import com.lvmama.pet.place.dao.PlacePlaneModelDAO;
public class PlaceFlightServiceImpl  implements PlaceFlightService{

    private PlaceFlightDAO placeFlightDAO;
	private PlacePlaneModelDAO placePlaneModelDAO;
	private PlaceAirlineDAO placeAirlineDAO;
	private PlaceAirportDAO placeAirportDAO;
	private PlaceDAO placeDAO;
	
    //保存航班信息
	@Override
	public void saveFlight(PlaceFlight flight) {
		placeFlightDAO.insert(flight);
	}

	 //保存机型
	@Override
	public void savePlaneModel(PlacePlaneModel model) {
		placePlaneModelDAO.insert(model);
	}

	 //保存航空公司
	@Override
	public void saveAirline(PlaceAirline airline) {
		placeAirlineDAO.insert(airline);
	}
	

	@Override
	public PlaceFlight queryPlaceFlight(Long pk) {
		return placeFlightDAO.selectByPrimaryKey(pk);
	}
	
	@Override
	public PlaceFlight queryPlaceFlight(String flightNo) {
		return placeFlightDAO.selectByFlightNo(flightNo);
	}

	@Override
	public List<PlaceAirline> queryPlaceAirLineListByParam(
			Map<String, Object> param) {
		return placeAirlineDAO.queryPlaceAirLineList(param);
	}
	@Override
	public Long savePlaceAirport(PlaceAirport placeAirport) {
		return placeAirportDAO.insert(placeAirport);
	}

	@Override
	public void deletePlaceAirportByPlaceAirportId(Long placeAirportId) {
		placeAirportDAO.deleteByPrimaryKey(placeAirportId);
	}

	@Override
	public List<PlaceAirport> queryPlaceAirportListByParam(
			Map<String, Object> param) {
		return placeAirportDAO.queryPlaceAirportList(param);
	}

	@Override
	public Long countPlaceAirportListByParam(Map<String, Object> param) {
		return placeAirportDAO.countPlaceAirportList(param);
	}

	@Override
	public PlaceAirport getPlaceAirportByKey(Long placeAirportId) {
 		return  placeAirportDAO.getPlaceAirportByKey(placeAirportId);
	}

	@Override
	public Long countPlaceAirLinetListByParam(Map<String, Object> param) {
		return placeAirlineDAO.countPlaceAirLineList(param);
	}
	public int updatePlaceAirport(PlaceAirport placeAirport) {
		return placeAirportDAO.updateByPrimaryKey(placeAirport);
	}
	
	public void setPlaceAirportDAO(PlaceAirportDAO placeAirportDAO) {
		this.placeAirportDAO = placeAirportDAO;
	}

	@Override
	public PlaceFlight queryPlaceFlightDetail(Long pk) {
		PlaceFlight flight = queryPlaceFlight(pk);
		initOtherInfo(flight);
		return flight;
	}
	
	@Override
	public PlaceFlight queryPlaceFlightDetail(String flightNo) {
		PlaceFlight flight = queryPlaceFlight(flightNo);
		initOtherInfo(flight);
		return flight;
	}
	
	/**
	 * 初始化航班其他信息
	 * @param flight
	 */
	private void initOtherInfo(PlaceFlight flight) {
		if(flight!=null){
			if(flight.getStartAirportId()!=null){
				flight.setStartAirport(placeAirportDAO.getPlaceAirportByKey(flight.getStartAirportId()));
			}
			
			if(flight.getArriveAirportId()!=null){
				flight.setArriveAirport(placeAirportDAO.getPlaceAirportByKey(flight.getArriveAirportId()));
			}
			
			if(flight.getAirplaneId()!=null){
				flight.setAirplane(placePlaneModelDAO.selectByPrimaryKey(flight.getAirplaneId()));
			}
			
			if(flight.getAirlineId()!=null){
				flight.setAirline(placeAirlineDAO.selectByPrimaryKey(flight.getAirlineId()));
			}
			
			if(flight.getStartPlaceId()!=null){
				Place place=placeDAO.findByPlaceId(flight.getStartPlaceId());
				flight.setStartPlaceName(place.getName());
			}
			if(flight.getArrivePlaceId()!=null){
				Place place = placeDAO.findByPlaceId(flight.getArrivePlaceId());
				flight.setArrivePlaceName(place.getName());
			}
		}
	}
	

	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

	@Override
	public void updateByPrimaryKey(PlaceAirline airline) {
	 placeAirlineDAO.updateByPrimaryKey(airline);
	}

	@Override
	public PlaceAirline queryPlaceAirline(Long pk) {
		return placeAirlineDAO.selectByPrimaryKey(pk);
	}

	@Override
	public List<PlacePlaneModel> queryPlacePlaneModelListByParam(
			Map<String, Object> param) {
		return placePlaneModelDAO.queryPlacePlaneModelList(param);
	}

	@Override
	public Long countPlacePlaneModelListByParam(Map<String, Object> param) {
		return placePlaneModelDAO.countPlacePlaneModelList(param);
	}

	@Override
	public void updateByPrimaryKey(PlacePlaneModel model) {
		 placePlaneModelDAO.updateByPrimaryKey(model);
	}

	@Override
	public PlacePlaneModel queryPlacePlaneModel(Long pk) {
		return placePlaneModelDAO.selectByPrimaryKey(pk);
	}

	@Override
	public void updateByPrimaryKey(PlaceFlight flight) {
		 placeFlightDAO.updateByPrimaryKey(flight);
	}

	@Override
	public List<PlaceFlight> queryPlaceFlightListByParam(
			Map<String, Object> param) {
		return placeFlightDAO.queryPlaceFlightList(param);
	}

	@Override
	public Long countPlaceFlightListByParam(Map<String, Object> param) {
		return placeFlightDAO.countPlaceFlightList(param);
	}

	@Override
	public Long countPlaceFlightByflightNo(String flightNo) {
		return placeFlightDAO.countFlightByFlightNo(flightNo);
	}

	@Override
	public List<PlacePlaneModel> queryPlacePlaneModelList() {
		return placeFlightDAO.queryPlacePlaneModelList();
	}

	@Override
	public List<PlaceAirline> queryPlaceAirlineList() {
		return placeFlightDAO.queryPlaceAirlineList();
	}

	@Override
	public Place queryPlaceByPlaceId(long pk) {
		return placeDAO.findByPlaceId(pk);
	}

	@Override
	public Long countAirlineByCode(String airlineCode) {
		return placeAirlineDAO.countPlaceAirLineBycode(airlineCode);
	}

	@Override
	public Long countPlaneModelByCode(String planCode) {
		return placePlaneModelDAO.countPlaneModelBycode(planCode);
	}

	public void setPlaceAirlineDAO(PlaceAirlineDAO placeAirlineDAO) {
		this.placeAirlineDAO = placeAirlineDAO;
	}
	
	public void setPlaceFlightDAO(PlaceFlightDAO placeFlightDAO) {
		this.placeFlightDAO = placeFlightDAO;
	}
	
	public void setPlacePlaneModelDAO(PlacePlaneModelDAO placePlaneModelDAO) {
		this.placePlaneModelDAO = placePlaneModelDAO;
	}

	
}
