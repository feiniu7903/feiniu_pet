package com.lvmama.ord.dao;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.vo.ord.TrackLog;

public class TrackLogDAO extends BaseIbatisDAO {

    public TrackLogDAO() {
        super();
    }
    
    public List<TrackLog> getTrackLogByOrderId(Long tarckId) {
		List<TrackLog> result = new ArrayList<TrackLog>();
		result = super.queryForList("TRACK_LOG.selByTrackId",tarckId);
		return result;
	}

  
    public Long saveTrackLog(TrackLog obj) {
        Object newKey = super.insert("TRACK_LOG.insert", obj);
        return (Long) newKey;
    }

}