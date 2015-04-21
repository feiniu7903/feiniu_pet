package com.lvmama.comm.vo.train.product;

public class TicketBookInfo {
	/**
	 * 车次，例如G102
	 */
	private String train_id;
	/**
	 * 车型，例如101（高铁）
	 */
	private int train_type;
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
	 * 经停车站数
	 */
	private int park_station;
	/**
	 * 商务座票价
	 */
	private float seat_busi;
	/**
	 * 商务座库存状态
	 */
	private int book_seat_busi;
	/**
	 * 特等座票价
	 */
	private float seat_class0;
	/**
	 * 特等座库存状态
	 */
	private int book_seat_class0;
	/**
	 * 一等座票价
	 */
	private float seat_class1;
	/**
	 * 一等座库存状态
	 */
	private int book_seat_class1;
	/**
	 * 二等座票价
	 */
	private float seat_class2;
	/**
	 * 二等座库存状态
	 */
	private int book_seat_class2;
	/**
	 * 软座票价
	 */
	private float seat_soft;
	/**
	 * 软座库存状态
	 */
	private int book_seat_soft;
	/**
	 * 硬座票价
	 */
	private float seat_hard;
	/**
	 * 硬座库存状态
	 */
	private int book_seat_hard;
	/**
	 * 无座票价
	 */
	private float seat_none;
	/**
	 * 无座库存状态
	 */
	private int book_seat_none;
	/**
	 * 高级软卧上铺票价
	 */
	private float bed_senior_top;
	/**
	 * 高级软卧下铺票价
	 */
	private float bed_senior_bot;
	/**
	 * 高级软卧库存状态
	 */
	private int book_bed_senior;
	/**
	 * 软卧上铺票价
	 */
	private float bed_soft_top;
	/**
	 * 软卧下铺票价
	 */
	private float bed_soft_bot;
	/**
	 * 软卧库存状态
	 */
	private int book_bed_soft;
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
	/**
	 * 硬卧库存状态
	 */
	private int book_bed_hard;
	public String getTrain_id() {
		return train_id;
	}
	public void setTrain_id(String train_id) {
		this.train_id = train_id;
	}
	public int getTrain_type() {
		return train_type;
	}
	public void setTrain_type(int train_type) {
		this.train_type = train_type;
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
	public int getPark_station() {
		return park_station;
	}
	public void setPark_station(int park_station) {
		this.park_station = park_station;
	}
	public float getSeat_busi() {
		return seat_busi;
	}
	public void setSeat_busi(float seat_busi) {
		this.seat_busi = seat_busi;
	}
	public int getBook_seat_busi() {
		return book_seat_busi;
	}
	public void setBook_seat_busi(int book_seat_busi) {
		this.book_seat_busi = book_seat_busi;
	}
	public float getSeat_class0() {
		return seat_class0;
	}
	public void setSeat_class0(float seat_class0) {
		this.seat_class0 = seat_class0;
	}
	public int getBook_seat_class0() {
		return book_seat_class0;
	}
	public void setBook_seat_class0(int book_seat_class0) {
		this.book_seat_class0 = book_seat_class0;
	}
	public float getSeat_class1() {
		return seat_class1;
	}
	public void setSeat_class1(float seat_class1) {
		this.seat_class1 = seat_class1;
	}
	public int getBook_seat_class1() {
		return book_seat_class1;
	}
	public void setBook_seat_class1(int book_seat_class1) {
		this.book_seat_class1 = book_seat_class1;
	}
	public float getSeat_class2() {
		return seat_class2;
	}
	public void setSeat_class2(float seat_class2) {
		this.seat_class2 = seat_class2;
	}
	public int getBook_seat_class2() {
		return book_seat_class2;
	}
	public void setBook_seat_class2(int book_seat_class2) {
		this.book_seat_class2 = book_seat_class2;
	}
	public float getSeat_soft() {
		return seat_soft;
	}
	public void setSeat_soft(float seat_soft) {
		this.seat_soft = seat_soft;
	}
	public int getBook_seat_soft() {
		return book_seat_soft;
	}
	public void setBook_seat_soft(int book_seat_soft) {
		this.book_seat_soft = book_seat_soft;
	}
	public float getSeat_hard() {
		return seat_hard;
	}
	public void setSeat_hard(float seat_hard) {
		this.seat_hard = seat_hard;
	}
	public int getBook_seat_hard() {
		return book_seat_hard;
	}
	public void setBook_seat_hard(int book_seat_hard) {
		this.book_seat_hard = book_seat_hard;
	}
	public float getSeat_none() {
		return seat_none;
	}
	public void setSeat_none(float seat_none) {
		this.seat_none = seat_none;
	}
	public int getBook_seat_none() {
		return book_seat_none;
	}
	public void setBook_seat_none(int book_seat_none) {
		this.book_seat_none = book_seat_none;
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
	public int getBook_bed_senior() {
		return book_bed_senior;
	}
	public void setBook_bed_senior(int book_bed_senior) {
		this.book_bed_senior = book_bed_senior;
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
	public int getBook_bed_soft() {
		return book_bed_soft;
	}
	public void setBook_bed_soft(int book_bed_soft) {
		this.book_bed_soft = book_bed_soft;
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
	public int getBook_bed_hard() {
		return book_bed_hard;
	}
	public void setBook_bed_hard(int book_bed_hard) {
		this.book_bed_hard = book_bed_hard;
	}
	
	@Override
	public String toString(){
		return "train_id:" + train_id + "|train_type:" + train_type
				+ "|depart_station:" + depart_station + "|arrive_station:" + arrive_station
				+ "|depart_date:" + depart_date + "|depart_time:" + depart_time
				+ "|arrive_time:" + arrive_time + "|cost_time:" + cost_time
				+ "|park_station:" + park_station 
				+ "|seat_busi:" + seat_busi + "|book_seat_busi:" + book_seat_busi
				+ "|seat_class0:" + seat_class0 + "|book_seat_class0:" + book_seat_class0
				+ "|seat_class1:" + seat_class1 + "|book_seat_class1:" + book_seat_class1
				+ "|seat_class2:" + seat_class2 + "|book_seat_class2:" + book_seat_class2
				+ "|seat_soft:" + seat_soft + "|book_seat_soft:" + book_seat_soft
				+ "|seat_hard:" + seat_hard + "|book_seat_hard:" + book_seat_hard
				+ "|seat_none:" + seat_none + "|book_seat_none:" + book_seat_none
				+ "|bed_senior_top:" + bed_senior_top + "|bed_senior_bot:" + bed_senior_bot
				+ "|book_bed_senior:" + book_bed_senior + "|bed_soft_top:" + bed_soft_top
				+ "|bed_soft_bot:" + bed_soft_bot + "|book_bed_soft:" + book_bed_soft
				+ "|bed_hard_top:" + bed_hard_top + "|bed_hard_mid:" + bed_hard_mid
				+ "|bed_hard_bot:" + bed_hard_bot + "|book_bed_hard:" + book_bed_hard;
	}
}
