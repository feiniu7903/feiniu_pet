/**
 * 
 */
package com.lvmama.comm.vo.train;

import org.apache.commons.collections.Predicate;

import com.lvmama.comm.bee.po.prod.LineStops;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.train.product.LineStopsStationInfo;

/**
 * @author lancey
 *
 */
public class LineStopsComparator implements Predicate{
	
	private Long stationId;
	private LineStopsStationInfo info;
	
	

	public LineStopsComparator(Long stationId, LineStopsStationInfo info) {
		super();
		this.stationId = stationId;
		this.info = info;
	}

	@Override
	public boolean evaluate(Object arg0) {
		LineStops stop=(LineStops)arg0;
		if(!stop.getStationId().equals(stationId)){
			return false;
		}
		if(!stop.getDepartureTime().equals(TimePriceUtil.getLongTime(info.getDepart_time()))){
			//System.out.println("111111111111111111");
			return false;
		}
		if(!stop.getArrivalTime().equals(TimePriceUtil.getLongTime(info.getArrive_time()))){
			//System.out.println("2222222222222222222222222");
			return false;
		}
		if(!stop.getStopStep().equals((long)info.getStation_no())){
			//System.out.println("33333333333333333333333333");
			return false;
		}
		return true;
	}

}
