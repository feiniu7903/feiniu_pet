package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.ord.TimePriceUtil;

public class LineStops implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4089288717411813893L;

	private Long lineStopsId;

	private Long lineInfoId;

	private Long stationId;

	private Long stopStep;

	private Long arrivalTime;

	private Long departureTime;

	private Long takeTime;

	private Long lineStopVersionId;

	private String stationName;
	
	private String stationPinyin;

	public Long getLineStopsId() {
		return lineStopsId;
	}

	public void setLineStopsId(Long lineStopsId) {
		this.lineStopsId = lineStopsId;
	}

	public Long getLineInfoId() {
		return lineInfoId;
	}

	public void setLineInfoId(Long lineInfoId) {
		this.lineInfoId = lineInfoId;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Long getStopStep() {
		return stopStep;
	}

	public void setStopStep(Long stopStep) {
		this.stopStep = stopStep;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Long departureTime) {
		this.departureTime = departureTime;
	}

	public Long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Long takeTime) {
		this.takeTime = takeTime;
	}

	public Long getLineStopVersionId() {
		return lineStopVersionId;
	}

	public void setLineStopVersionId(Long lineStopVersionId) {
		this.lineStopVersionId = lineStopVersionId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationPinyin() {
		return stationPinyin;
	}

	public void setStationPinyin(String stationPinyin) {
		this.stationPinyin = stationPinyin;
	}

	public String getDepartureTimeStr() {
		if (getDepartureTime() != null && getDepartureTime() != -1) {
			return TimePriceUtil.formatTime(getDepartureTime());
		} else if (getDepartureTime() == -1) {
			return "-";
		}
		return null;
	}

	public String getArrivalTimeStr() {
		if (getArrivalTime() != null && getArrivalTime() != -1) {
			return TimePriceUtil.formatTime(getArrivalTime());
		} else if (getArrivalTime() == -1) {
			return "-";
		}
		return null;
	}

	public String getRunStopTime() {
		if (takeTime != null && takeTime != 0) {
			return TimePriceUtil.formatSec(takeTime);
		}
		return "-";
	}

	@SuppressWarnings("deprecation")
	public String getStopTime() {
		if (getDepartureTimeStr() != null && getArrivalTimeStr() != null
				&& (!getDepartureTimeStr().equals("-"))
				&& (!getArrivalTimeStr().equals("-"))) {
			String[] start = getArrivalTimeStr().split(":");
			String[] end = getDepartureTimeStr().split(":");
			Date date1 = new Date();
			Date date2 = new Date();
			int day = 0;
			if (departureTime < arrivalTime) {
				day = 1;
			}
			date1.setDate(1);
			date1.setHours(Integer.parseInt(start[0]));
			date1.setMinutes(Integer.parseInt(start[1]));
			date2.setDate(date1.getDate() + day);
			date2.setHours(Integer.parseInt(end[0]));
			date2.setMinutes(Integer.parseInt(end[1]));
			long tmp = (date2.getTime() - date1.getTime()) / 60 / 1000;
			long hour = 0;
			if (tmp >= 60) {
				hour = tmp / 60;
				tmp = tmp - hour * 60;
			}
			StringBuffer sb = new StringBuffer();
			if (tmp > 0) {
				sb.append(tmp);
				sb.append("分钟");
			}
			return sb.toString();
		}
		return "-";
	}
}