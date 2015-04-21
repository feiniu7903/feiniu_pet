package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.ord.TimePriceUtil;

public class LineInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 36388048922096954L;

	private Long lineInfoId;

    private Long startStationId;
    
    private LineStation startStation;

    private Long endStationId;
    private LineStation endStation;

    private String fullName;

    private String category;

    private Long startTime;

    private Long endTime;

    private Long takeDays;

    private String direct;
    
    private String startStationName;
    private String endStationName;
    private Long arrivalTime;
    
    public Long getLineInfoId() {
        return lineInfoId;
    }

    public void setLineInfoId(Long lineInfoId) {
        this.lineInfoId = lineInfoId;
    }

    public Long getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(Long startStationId) {
        this.startStationId = startStationId;
    }

    public Long getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(Long endStationId) {
        this.endStationId = endStationId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getTakeDays() {
        return takeDays;
    }

    public void setTakeDays(Long takeDays) {
        this.takeDays = takeDays;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct == null ? null : direct.trim();
    }

	public LineStation getStartStation() {
		return startStation;
	}

	public void setStartStation(LineStation startStation) {
		this.startStation = startStation;
	}

	public LineStation getEndStation() {
		return endStation;
	}

	public void setEndStation(LineStation endStation) {
		this.endStation = endStation;
	}
	
	public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getStartTimeStr() {
		if(getStartTime() != null){
			return TimePriceUtil.formatTime(getStartTime());
		}
		return null;
	}

	public String getEndTimeStr() {
		if(getEndTime() != null){
			return TimePriceUtil.formatTime(getEndTime());
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public String getRunSumTime() {
		if(getStartTimeStr()!=null && getEndTimeStr()!=null){
			String[] start = getStartTimeStr().split(":");
			String[] end = getEndTimeStr().split(":");
			Date date1 = new Date();
			Date date2 = new Date();
			date1.setDate(1);
			date1.setHours(Integer.parseInt(start[0]));
			date1.setMinutes(Integer.parseInt(start[1]));
			date2.setDate(date1.getDate()+getTakeDays().intValue());
			date2.setHours(Integer.parseInt(end[0]));
			date2.setMinutes(Integer.parseInt(end[1]));
			long tmp = (date2.getTime() - date1.getTime())/60/1000;
			long hour=0;
			if(tmp>=60){
				hour = tmp/60;
				tmp=tmp-hour*60;
			}
			StringBuffer sb = new StringBuffer();
			if(hour>0){
				sb.append(hour);
				sb.append("时");
			}
			if(tmp>0){
				sb.append(tmp);
				sb.append("分");
			}
			return sb.toString();
		}
		return "";
	}

	public long getTakeTime(){
		long time=endTime;
		if(takeDays>0){
			time +=2400*takeDays;
		}
		//System.out.println(time+"    "+startTime);
		long tmp = time-startTime;
		long mod=tmp%100;
		if(mod>60){
			tmp=tmp-mod+(mod-40);
		}
		return tmp;
	}

}