package com.lvmama.pet.place.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlacePlaneModel;
public class PlacePlaneModelDAO extends BaseIbatisDAO {

    public PlacePlaneModelDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long placeModelId) {
        PlacePlaneModel key = new PlacePlaneModel();
        key.setPlaceModelId(placeModelId);
        int rows = super.delete("PLACE_PLANE_MODEL.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(PlacePlaneModel record) {
        Object newKey = super.insert("PLACE_PLANE_MODEL.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(PlacePlaneModel record) {
        Object newKey = super.insert("PLACE_PLANE_MODEL.insertSelective", record);
        return (Long) newKey;
    }

    public PlacePlaneModel selectByPrimaryKey(Long placeModelId) {
        PlacePlaneModel key = new PlacePlaneModel();
        key.setPlaceModelId(placeModelId);
        PlacePlaneModel record = (PlacePlaneModel) super.queryForObject("PLACE_PLANE_MODEL.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(PlacePlaneModel record) {
        int rows = super.update("PLACE_PLANE_MODEL.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(PlacePlaneModel record) {
        int rows = super.update("PLACE_PLANE_MODEL.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<PlacePlaneModel> queryPlacePlaneModelList(Map<String,Object> param){
		List<PlacePlaneModel> placeAirLineList=(List<PlacePlaneModel>)super.queryForList("PLACE_PLANE_MODEL.queryPlacePlaneModelList",param);
		return placeAirLineList;
	}

	public Long countPlacePlaneModelList(Map<String,Object> param){
		Long totalResultSize = (Long) super.queryForObject("PLACE_PLANE_MODEL.countPlacePlaneModelList", param);
		return totalResultSize;
	}
	
	public Long countPlaneModelBycode(String planeCode){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("planeCode", planeCode);
		Long totalResultSize = (Long) super.queryForObject("PLACE_PLANE_MODEL.checkPlaneModelByplaneCode", param);
		return totalResultSize;
	}
}