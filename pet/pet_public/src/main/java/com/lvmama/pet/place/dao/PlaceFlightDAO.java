package com.lvmama.pet.place.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceAirline;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.po.place.PlacePlaneModel;

public class PlaceFlightDAO extends BaseIbatisDAO {

    public PlaceFlightDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long placeFlightId) {
        PlaceFlight key = new PlaceFlight();
        key.setPlaceFlightId(placeFlightId);
        int rows = super.delete("PLACE_FLIGHT.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(PlaceFlight record) {
        Object newKey = super.insert("PLACE_FLIGHT.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(PlaceFlight record) {
        Object newKey = super.insert("PLACE_FLIGHT.insertSelective", record);
        return (Long) newKey;
    }

    public PlaceFlight selectByPrimaryKey(Long placeFlightId) {
        PlaceFlight key = new PlaceFlight();
        key.setPlaceFlightId(placeFlightId);
        PlaceFlight record = (PlaceFlight) super.queryForObject("PLACE_FLIGHT.selectByPrimaryKey", key);
        return record;
    }
    
    public PlaceFlight selectByFlightNo(String flightNo) {
        PlaceFlight key = new PlaceFlight();
        key.setFlightNo(flightNo);
        PlaceFlight record = (PlaceFlight) super.queryForObject("PLACE_FLIGHT.selectByFlightNo", key);
        return record;
    }

    public int updateByPrimaryKeySelective(PlaceFlight record) {
        int rows = super.update("PLACE_FLIGHT.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(PlaceFlight record) {
        int rows = super.update("PLACE_FLIGHT.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<PlaceFlight> queryPlaceFlightList(Map<String,Object> param){
		List<PlaceFlight> placeFlightList=(List<PlaceFlight>)super.queryForList("PLACE_FLIGHT.queryPlaceFlightList",param);
		return placeFlightList;
	}

	public Long countPlaceFlightList(Map<String,Object> param){
		Long totalResultSize = (Long) super.queryForObject("PLACE_FLIGHT.countPlaceFlightList", param);
		return totalResultSize;
	}
	
	public Long countFlightByFlightNo(String flightNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flightNo", flightNo);
		Long totalResultSize = (Long) super.queryForObject("PLACE_FLIGHT.countPlaceFlightByFlightNo", param);
		return totalResultSize;
	}
	
    public List<PlaceAirline> queryPlaceAirlineList(){
		List<PlaceAirline> airlineList=(List<PlaceAirline>)super.queryForList("PLACE_FLIGHT.selectPlaceAirlineList");
		return airlineList;
	}
    
    public List<PlacePlaneModel> queryPlacePlaneModelList(){
		List<PlacePlaneModel> modelList=(List<PlacePlaneModel>)super.queryForList("PLACE_FLIGHT.selectPlacePlanceModelList");
		return modelList;
	}
}