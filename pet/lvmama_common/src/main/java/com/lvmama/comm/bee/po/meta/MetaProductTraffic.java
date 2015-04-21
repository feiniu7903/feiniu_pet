package com.lvmama.comm.bee.po.meta;

import com.lvmama.comm.vo.Constant;

public class MetaProductTraffic extends MetaProduct{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8758100391649757065L;

	private Long metaTrafficId;

    private String direction = Constant.TRAFFIC_DIRECTION.SINGLE.name();

    private Long days = 1l;

    private Long goFlight;
    
    private String goFlightName;

    private Long backFlight;
    
    private String backFlightName;
    
    private Long lineInfoId;

    public Long getMetaTrafficId() {
        return metaTrafficId;
    }

    public void setMetaTrafficId(Long metaTrafficId) {
        this.metaTrafficId = metaTrafficId;
    }
    public String getDirection() {
        return direction;
    }
    
    public String getZhDirection(){
    	return Constant.TRAFFIC_DIRECTION.getCnName(direction);
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public Long getGoFlight() {
        return goFlight;
    }

    public void setGoFlight(Long goFlight) {
        this.goFlight = goFlight;
    }

    public Long getBackFlight() {
        return backFlight;
    }

    public void setBackFlight(Long backFlight) {
        this.backFlight = backFlight;
    }

	public String getGoFlightName() {
		return goFlightName;
	}

	public void setGoFlightName(String goFlightName) {
		this.goFlightName = goFlightName;
	}

	public String getBackFlightName() {
		return backFlightName;
	}

	public void setBackFlightName(String backFlightName) {
		this.backFlightName = backFlightName;
	}

	public Long getLineInfoId() {
		return lineInfoId;
	}

	public void setLineInfoId(Long lineInfoId) {
		this.lineInfoId = lineInfoId;
	}

	
}