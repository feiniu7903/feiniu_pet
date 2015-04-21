package com.lvmama.comm.bee.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderTrack;
import com.lvmama.comm.bee.vo.ord.TrackLog;

/**
 * 保存订单二次跟踪处理(领取订单).
 * 
 * @author huangli
 * 
 */
public interface IOrdertrackService {
	
	public Long checkTrackIsMakeOrder(Map map);
	
	public Long updateOrdertrack(OrdOrderTrack ordTrack);
	
	public OrdOrderTrack getOrdertrackByPk(Long trackId);
	
	public void saveOrdertrack(OrdOrderTrack o);

	public List<OrdOrderTrack> getOrdertrackByOrderId(Long orderId);
	
	public void saveTrackLog(TrackLog obj);

	public List<TrackLog> getTrackLogByTrackId(Long trackId);

}
