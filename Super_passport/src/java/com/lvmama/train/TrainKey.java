/**
 * 
 */
package com.lvmama.train;

import java.io.Serializable;

import com.lvmama.comm.pet.po.search.ProdTrainCache;

/**
 * @author lancey
 *
 */
public class TrainKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2388794448575837870L;
	/**
	 * 车次，例如G102
	 */
	private String train_id;
	/**
	 * 出发车站名，中文
	 */
	private String depart_station;
	/**
	 * 到达车站名，中文
	 */
	private String arrive_station;
	
	/**
	 * 车位
	 */
	private String berth;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(train_id);
		sb.append("_");
		sb.append(depart_station);
		sb.append("_");
		sb.append(arrive_station);
		sb.append("_");
		sb.append(berth);
		return sb.toString();
	}

	private TrainKey(String train_id, String depart_station,
			String arrive_station, String berth) {
		super();
		this.train_id = train_id;
		this.depart_station = depart_station;
		this.arrive_station = arrive_station;
		this.berth = berth;
	}
	
	
	public static TrainKey newTrainKey(final com.lvmama.comm.vo.train.product.Station2StationInfo ss,String berth){
		TrainKey tk = new TrainKey(ss.getTrain_id(), ss.getDepart_station(), ss.getArrive_station(), berth);
		return tk;
	}
	
	public static TrainKey newTrainKey(final ProdTrainCache cache){
		TrainKey tk = new TrainKey(cache.getLineName(), cache.getDepartureStationName(), cache.getArrivalStationName(), cache.getSeatType());
		return tk;
	}
}
