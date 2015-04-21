package com.lvmama.ord.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderTrack;
import com.lvmama.comm.bee.service.IOrdertrackService;
import com.lvmama.comm.bee.vo.ord.TrackLog;
import com.lvmama.ord.dao.OrdOrderTrackDAO;
import com.lvmama.ord.dao.TrackLogDAO;

public class OrderTrackServiceImpl implements IOrdertrackService {
	private OrdOrderTrackDAO ordOrderTrackDAO;
	private TrackLogDAO trackLogDAO;
	
	public OrdOrderTrack getOrdertrackByPk(Long trackId){
		return this.ordOrderTrackDAO.getOrdertrackByPk(trackId);
	}
	@Override
	public List<OrdOrderTrack> getOrdertrackByOrderId(Long orderId) {
		return ordOrderTrackDAO.getOrdertrackByOrderId(orderId);
	}

	@Override
	public void saveOrdertrack(OrdOrderTrack o) {
		ordOrderTrackDAO.saveOrdertrack(o);
	}
	@Override
	public List<TrackLog> getTrackLogByTrackId(Long trackId) {
		return trackLogDAO.getTrackLogByOrderId(trackId);
	}

	@Override
	public void saveTrackLog(TrackLog obj) {
		trackLogDAO.saveTrackLog(obj);
	}
	@Override
	public Long updateOrdertrack(OrdOrderTrack ordTrack) {
		return this.ordOrderTrackDAO.updateOrdertrack(ordTrack);
	}
	public Long checkTrackIsMakeOrder(Map map) {
		return this.ordOrderTrackDAO.checkTrackIsMakeOrder(map);
	}
	
	public OrdOrderTrackDAO getOrdOrderTrackDAO() {
		return ordOrderTrackDAO;
	}

	public void setOrdOrderTrackDAO(OrdOrderTrackDAO ordOrderTrackDAO) {
		this.ordOrderTrackDAO = ordOrderTrackDAO;
	}

	public TrackLogDAO getTrackLogDAO() {
		return trackLogDAO;
	}

	public void setTrackLogDAO(TrackLogDAO trackLogDAO) {
		this.trackLogDAO = trackLogDAO;
	}
	
}
