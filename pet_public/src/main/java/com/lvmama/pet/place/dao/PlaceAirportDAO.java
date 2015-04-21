package com.lvmama.pet.place.dao;

import java.util.List;
import java.util.Map;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceAirport;

public class PlaceAirportDAO extends BaseIbatisDAO {

    public PlaceAirportDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long placeAirportId) {
        PlaceAirport placeAirport = new PlaceAirport();
        placeAirport.setPlaceAirportId(placeAirportId);
        int rows = super.delete("PLACE_AIRPORT.deleteByPrimaryKey", placeAirport);
        return rows;
    }

    public Long insert(PlaceAirport record) {
        return (Long) super.insert("PLACE_AIRPORT.insert", record);
    }

	@SuppressWarnings("unchecked")
	public List<PlaceAirport> queryPlaceAirportList(Map<String,Object> param){
		List<PlaceAirport> placeAirportList=(List<PlaceAirport>)super.queryForList("PLACE_AIRPORT.queryPlaceAirportList",param);
		return placeAirportList;
	}

	public Long countPlaceAirportList(Map<String,Object> param){
		Long totalResultSize = (Long) super.queryForObject("PLACE_AIRPORT.countPlaceAirportList", param);
		return totalResultSize;
	}

	public PlaceAirport getPlaceAirportByKey(Long placeAirportId) {
		PlaceAirport key = new PlaceAirport();
		key.setPlaceAirportId(placeAirportId);
		PlaceAirport record = (PlaceAirport) super.queryForObject(
				"PLACE_AIRPORT.selectByPrimaryKey", key);
		return record;
	}
    public int updateByPrimaryKey(PlaceAirport record) {
        int rows = super.update("PLACE_AIRPORT.updateByPrimaryKey", record);
        return rows;
    }
}