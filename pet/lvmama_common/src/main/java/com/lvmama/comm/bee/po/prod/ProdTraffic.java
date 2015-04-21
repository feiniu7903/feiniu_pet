package com.lvmama.comm.bee.po.prod;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.vo.Constant;

public class ProdTraffic extends ProdProduct{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7850285762917732233L;

	private Long prodTrafficId;

    private Long goFlightId;
    private PlaceFlight goFlight;
    
    private Long backFlightId;
    private PlaceFlight backFlight;
    private String direction;

    private Long days;
    private Long lineInfoId;
    
    

    public Long getProdTrafficId() {
        return prodTrafficId;
    }

    public void setProdTrafficId(Long prodTrafficId) {
        this.prodTrafficId = prodTrafficId;
    }
 
    public Long getGoFlightId() {
		return goFlightId;
	}

	public void setGoFlightId(Long goFlightId) {
		this.goFlightId = goFlightId;
	}

	public Long getBackFlightId() {
		return backFlightId;
	}

	public void setBackFlightId(Long backFlightId) {
		this.backFlightId = backFlightId;
	}

	public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public Long getDays() {
        return days==null?1:days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

	public PlaceFlight getGoFlight() {
		return goFlight;
	}

	public void setGoFlight(PlaceFlight goFlight) {
		this.goFlight = goFlight;
	}

	public PlaceFlight getBackFlight() {
		return backFlight;
	}

	public void setBackFlight(PlaceFlight backFlight) {
		this.backFlight = backFlight;
	}

	public boolean hasRound(){
		return Constant.TRAFFIC_DIRECTION.ROUND.name().equals(direction);
	}
	
	public Date getBackDate(Date date){
		return DateUtils.addDays(date, (int)(days-1));
	}

	public Long getLineInfoId() {
		return lineInfoId;
	}

	public void setLineInfoId(Long lineInfoId) {
		this.lineInfoId = lineInfoId;
	}
	
}