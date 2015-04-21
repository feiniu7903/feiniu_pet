package com.lvmama.comm.vo.train.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.vo.train.SeatInfo;
import com.lvmama.comm.vo.Constant;

public class Station2StationInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8495621354L;
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
	 * 出发日期
	 */
	private String depart_date;
	/**
	 * 出发时间
	 */
	private String depart_time;
	/**
	 * 到达时间
	 */
	private String arrive_time;
	/**
	 * 旅行时间
	 */
	private String cost_time;
	/**
	 * 1-新增票价信息；2-更新票价信息；3-删除票价信息
	 */
	private int status;
	/**
	 * 商务座票价
	 */
	private float seat_busi;
	/**
	 * 特等座票价
	 */
	private float seat_class0;
	/**
	 * 一等座票价
	 */
	private float seat_class1;
	/**
	 * 二等座票价
	 */
	private float seat_class2;
	/**
	 * 软座票价
	 */
	private float seat_soft;
	/**
	 * 硬座票价
	 */
	private float seat_hard;
	/**
	 * 无座票价
	 */
	private float seat_none;
	/**
	 * 高级软卧上铺票价
	 */
	private float bed_senior_top;
	/**
	 * 高级软卧下铺票价
	 */
	private float bed_senior_bot;
	/**
	 * 软卧上铺票价
	 */
	private float bed_soft_top;
	/**
	 * 软卧下铺票价
	 */
	private float bed_soft_bot;
	/**
	 * 硬卧上铺票价
	 */
	private float bed_hard_top;
	/**
	 * 硬卧中铺票价
	 */
	private float bed_hard_mid;
	/**
	 * 硬卧下铺票价
	 */
	private float bed_hard_bot;
	private List<SeatInfo> seats = null;
	
	/**
	 * 整理票价信息
	 */
	public void arrange(){
		if(seats == null) seats = new ArrayList<SeatInfo>();
		
		//目前只需导入硬座、一等座、二等座、硬卧
//		if(seat_busi != 0)  seats.add(new SeatInfo(Math.round(seat_busi * 100), Constant.TRAIN_SEAT_CATALOG.SC_201.getAttr1()));
//		if(seat_class0 != 0)  seats.add(new SeatInfo(Math.round(seat_class0 * 100), Constant.TRAIN_SEAT_CATALOG.SC_202.getAttr1()));
		if(seat_class1 != 0)  seats.add(new SeatInfo(Math.round(seat_class1 * 100), Constant.TRAIN_SEAT_CATALOG.SC_203.getAttr1()));
		if(seat_class2 != 0)  seats.add(new SeatInfo(Math.round(seat_class2 * 100), Constant.TRAIN_SEAT_CATALOG.SC_204.getAttr1()));
//		if(seat_soft != 0)  seats.add(new SeatInfo(Math.round(seat_soft * 100), Constant.TRAIN_SEAT_CATALOG.SC_215.getAttr1()));
		if(seat_hard != 0)  seats.add(new SeatInfo(Math.round(seat_hard * 100), Constant.TRAIN_SEAT_CATALOG.SC_216.getAttr1()));
//		if(seat_none != 0)  seats.add(new SeatInfo(Math.round(seat_none * 100), Constant.TRAIN_SEAT_CATALOG.SC_217.getAttr1()));
//		if(bed_senior_top != 0)  seats.add(new SeatInfo(Math.round(bed_senior_top * 100), Constant.TRAIN_SEAT_CATALOG.SC_206.getAttr1()));
//		if(bed_senior_bot != 0)  seats.add(new SeatInfo(Math.round(bed_senior_bot * 100), Constant.TRAIN_SEAT_CATALOG.SC_207.getAttr1()));
//		if(bed_soft_top != 0)  seats.add(new SeatInfo(Math.round(bed_soft_top * 100), Constant.TRAIN_SEAT_CATALOG.SC_209.getAttr1()));
//		if(bed_soft_bot != 0)  seats.add(new SeatInfo(Math.round(bed_soft_bot * 100), Constant.TRAIN_SEAT_CATALOG.SC_210.getAttr1()));
		if(bed_hard_top != 0)  seats.add(new SeatInfo(Math.round(bed_hard_top * 100), Constant.TRAIN_SEAT_CATALOG.SC_212.getAttr1()));
		if(bed_hard_mid != 0)  seats.add(new SeatInfo(Math.round(bed_hard_mid * 100), Constant.TRAIN_SEAT_CATALOG.SC_213.getAttr1()));
		if(bed_hard_bot != 0)  seats.add(new SeatInfo(Math.round(bed_hard_bot * 100), Constant.TRAIN_SEAT_CATALOG.SC_214.getAttr1()));
	}
	public String getTrain_id() {
		return train_id;
	}
	public void setTrain_id(String train_id) {
		this.train_id = train_id;
	}
	public String getDepart_station() {
		return depart_station;
	}
	public void setDepart_station(String depart_station) {
		this.depart_station = depart_station;
	}
	public String getArrive_station() {
		return arrive_station;
	}
	public void setArrive_station(String arrive_station) {
		this.arrive_station = arrive_station;
	}
	public String getDepart_date() {
		return depart_date;
	}
	public void setDepart_date(String depart_date) {
		this.depart_date = depart_date;
	}
	public String getDepart_time() {
		return depart_time;
	}
	public void setDepart_time(String depart_time) {
		this.depart_time = depart_time;
	}
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getCost_time() {
		return cost_time;
	}
	public void setCost_time(String cost_time) {
		this.cost_time = cost_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getSeat_busi() {
		return seat_busi;
	}
	public void setSeat_busi(float seat_busi) {
		this.seat_busi = seat_busi;
	}
	public float getSeat_class0() {
		return seat_class0;
	}
	public void setSeat_class0(float seat_class0) {
		this.seat_class0 = seat_class0;
	}
	public float getSeat_class1() {
		return seat_class1;
	}
	public void setSeat_class1(float seat_class1) {
		this.seat_class1 = seat_class1;
	}
	public float getSeat_class2() {
		return seat_class2;
	}
	public void setSeat_class2(float seat_class2) {
		this.seat_class2 = seat_class2;
	}
	public float getSeat_soft() {
		return seat_soft;
	}
	public void setSeat_soft(float seat_soft) {
		this.seat_soft = seat_soft;
	}
	public float getSeat_hard() {
		return seat_hard;
	}
	public void setSeat_hard(float seat_hard) {
		this.seat_hard = seat_hard;
	}
	public float getSeat_none() {
		return seat_none;
	}
	public void setSeat_none(float seat_none) {
		this.seat_none = seat_none;
	}
	public float getBed_senior_top() {
		return bed_senior_top;
	}
	public void setBed_senior_top(float bed_senior_top) {
		this.bed_senior_top = bed_senior_top;
	}
	public float getBed_senior_bot() {
		return bed_senior_bot;
	}
	public void setBed_senior_bot(float bed_senior_bot) {
		this.bed_senior_bot = bed_senior_bot;
	}
	public float getBed_soft_top() {
		return bed_soft_top;
	}
	public void setBed_soft_top(float bed_soft_top) {
		this.bed_soft_top = bed_soft_top;
	}
	public float getBed_soft_bot() {
		return bed_soft_bot;
	}
	public void setBed_soft_bot(float bed_soft_bot) {
		this.bed_soft_bot = bed_soft_bot;
	}
	public float getBed_hard_top() {
		return bed_hard_top;
	}
	public void setBed_hard_top(float bed_hard_top) {
		this.bed_hard_top = bed_hard_top;
	}
	public float getBed_hard_mid() {
		return bed_hard_mid;
	}
	public void setBed_hard_mid(float bed_hard_mid) {
		this.bed_hard_mid = bed_hard_mid;
	}
	public float getBed_hard_bot() {
		return bed_hard_bot;
	}
	public void setBed_hard_bot(float bed_hard_bot) {
		this.bed_hard_bot = bed_hard_bot;
	}
	public List<SeatInfo> getSeats() {
		return seats;
	}
	public void setSeats(List<SeatInfo> seats) {
		this.seats = seats;
	}
	
	@Override
	public String toString(){
		return "train_id:" + train_id + "|depart_station:" + depart_station
				+ "|arrive_station:" + arrive_station + "|depart_date:" + depart_date
				+ "|depart_time:" + depart_time + "|arrive_time:" + arrive_time
				+ "|cost_time:" + cost_time
				+ "|status:" + status + "|seat_busi:" + seat_busi
				+ "|seat_class0:" + seat_class0 + "|seat_class1:" + seat_class1
				+ "|seat_soft:" + seat_soft + "|seat_hard:" + seat_hard
				+ "|seat_none:" + seat_none + "|bed_senior_top:" + bed_senior_top
				+ "|bed_senior_bot:" + bed_senior_bot + "|bed_soft_top:" + bed_soft_top
				+ "|bed_soft_bot:" + bed_soft_bot + "|bed_hard_top:" + bed_hard_top
				+ "|bed_hard_mid:" + bed_hard_mid + "|bed_hard_bot:" + bed_hard_bot;
	}
}
