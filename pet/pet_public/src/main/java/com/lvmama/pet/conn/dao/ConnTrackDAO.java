package com.lvmama.pet.conn.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.conn.ConnTrack;

public class ConnTrackDAO extends BaseIbatisDAO {

    public ConnTrackDAO() {
        super();
    }
    
    public ConnTrack getOrdertrackByPk(Long trackId) {
    	ConnTrack o= (ConnTrack)super.queryForObject("CONN_TRACK.selByTrackId",trackId);
		return o;
	}

    
    public List<ConnTrack> getOrdertrackByOrderId(Long orderId) {
		List<ConnTrack> result = new ArrayList<ConnTrack>();
		result = super.queryForList("CONN_TRACK.selByOrderId",orderId);
		return result;
	}

  
    public Long saveOrdertrack(ConnTrack obj) {
        Object newKey = super.insert("CONN_TRACK.insert", obj);
        return (Long) newKey;
    }
    

    public Long updateOrdertrack(ConnTrack connTrack) {
        Object newKey = super.insert("CONN_TRACK.update", connTrack);
        return (Long) newKey;
    }
    
    public Long checkTrackIsMakeOrder(Map map) {
        Object newKey=null;
		try {
			newKey = super.queryForObject("CONN_TRACK.selMakeOrder", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return (Long) newKey;
    }
}