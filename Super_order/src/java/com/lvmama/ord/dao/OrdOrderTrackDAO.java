package com.lvmama.ord.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderTrack;

public class OrdOrderTrackDAO extends BaseIbatisDAO {

    public OrdOrderTrackDAO() {
        super();
    }
    
    public OrdOrderTrack getOrdertrackByPk(Long trackId) {
    	OrdOrderTrack o= (OrdOrderTrack)super.queryForObject("ORD_ORDER_TRACK.selByTrackId",trackId);
		return o;
	}

    
    public List<OrdOrderTrack> getOrdertrackByOrderId(Long orderId) {
		List<OrdOrderTrack> result = new ArrayList<OrdOrderTrack>();
		result = super.queryForList("ORD_ORDER_TRACK.selByOrderId",orderId);
		return result;
	}

  
    public Long saveOrdertrack(OrdOrderTrack obj) {
        Object newKey = super.insert("ORD_ORDER_TRACK.insert", obj);
        return (Long) newKey;
    }
    

    public Long updateOrdertrack(OrdOrderTrack ordTrack) {
        Object newKey = super.insert("ORD_ORDER_TRACK.update", ordTrack);
        return (Long) newKey;
    }
    
    public Long checkTrackIsMakeOrder(Map map) {
        Object newKey=null;
		try {
			newKey = super.queryForObject("ORD_ORDER_TRACK.selMakeOrder", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return (Long) newKey;
    }
}