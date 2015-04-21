package com.lvmama.comm.vo.train.product;

import com.lvmama.comm.bee.vo.train.InventoryInfo;
import com.lvmama.comm.bee.vo.train.SeatInventory;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.RspVo;

public class TicketInventoryRspVo extends RspVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8795462136L;
	/**
	 * 商务座库存状态
	 */
	private int book_seat_busi=-2;
	/**
	 * 特等座库存状态
	 */
	private int book_seat_class0=-2;
	/**
	 * 一等座库存状态
	 */
	private int book_seat_class1=-2;
	/**
	 * 二等座库存状态
	 */
	private int book_seat_class2=-2;
	/**
	 * 软座库存状态
	 */
	private int book_seat_soft=-2;
	/**
	 * 硬座库存状态
	 */
	private int book_seat_hard=-2;
	/**
	 * 无座库存状态
	 */
	private int book_seat_none=-2;
	/**
	 * 高级软卧存状态
	 */
	private int book_bed_senior=-2;
	/**
	 * 软卧库存状态
	 */
	private int book_bed_soft=-2;
	/**
	 * 硬卧库存状态
	 */
	private int book_bed_hard=-2;
	
	/**
	 * 整理
	 * @param lineName
	 * @return
	 */
	public InventoryInfo arrange(String lineName) {
		InventoryInfo inventory = new InventoryInfo();
		inventory.setTrainName(lineName);
		
		if (book_seat_busi != -2)
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_201
							.getAttr1(), book_seat_busi));
		if (book_seat_class0 != -2)
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_202
							.getAttr1(), book_seat_class0));
		if (book_seat_class1 != -2)
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_203
							.getAttr1(), book_seat_class1));
		if (book_seat_class2 != -2)
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_204
							.getAttr1(), book_seat_class2));
		if (book_seat_soft != -2){
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_215
							.getAttr1(), book_seat_soft));
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_209
							.getAttr1(), book_seat_soft));
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_210
							.getAttr1(), book_seat_soft));
		}
		if (book_seat_hard != -2)
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_216
							.getAttr1(), book_seat_hard));
		if (book_seat_none != -2)
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_217
							.getAttr1(), book_seat_none));
		if (book_bed_senior != -2)
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_205
							.getAttr1(), book_bed_senior));
		if (book_bed_soft != -2)
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_208
							.getAttr1(), book_bed_soft));
		if (book_bed_hard != -2){
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_211
							.getAttr1(), book_bed_hard));
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_212
							.getAttr1(), book_bed_hard));
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_213
							.getAttr1(), book_bed_hard));
			inventory.getSeatInventoies().add(
					new SeatInventory(Constant.TRAIN_SEAT_CATALOG.SC_214
							.getAttr1(), book_bed_hard));
		}
		return inventory;
	}
	public int getBook_seat_busi() {
		return book_seat_busi;
	}
	public void setBook_seat_busi(int book_seat_busi) {
		this.book_seat_busi = book_seat_busi;
	}
	public int getBook_seat_class0() {
		return book_seat_class0;
	}
	public void setBook_seat_class0(int book_seat_class0) {
		this.book_seat_class0 = book_seat_class0;
	}
	public int getBook_seat_class1() {
		return book_seat_class1;
	}
	public void setBook_seat_class1(int book_seat_class1) {
		this.book_seat_class1 = book_seat_class1;
	}
	public int getBook_seat_class2() {
		return book_seat_class2;
	}
	public void setBook_seat_class2(int book_seat_class2) {
		this.book_seat_class2 = book_seat_class2;
	}
	public int getBook_seat_soft() {
		return book_seat_soft;
	}
	public void setBook_seat_soft(int book_seat_soft) {
		this.book_seat_soft = book_seat_soft;
	}
	public int getBook_seat_hard() {
		return book_seat_hard;
	}
	public void setBook_seat_hard(int book_seat_hard) {
		this.book_seat_hard = book_seat_hard;
	}
	public int getBook_seat_none() {
		return book_seat_none;
	}
	public void setBook_seat_none(int book_seat_none) {
		this.book_seat_none = book_seat_none;
	}
	public int getBook_bed_senior() {
		return book_bed_senior;
	}
	public void setBook_bed_senior(int book_bed_senior) {
		this.book_bed_senior = book_bed_senior;
	}
	public int getBook_bed_soft() {
		return book_bed_soft;
	}
	public void setBook_bed_soft(int book_bed_soft) {
		this.book_bed_soft = book_bed_soft;
	}
	public int getBook_bed_hard() {
		return book_bed_hard;
	}
	public void setBook_bed_hard(int book_bed_hard) {
		this.book_bed_hard = book_bed_hard;
	}
	
	@Override
	public String toString(){
		return "book_seat_busi:" + book_seat_busi + "|book_seat_class0:" + book_seat_class0
				+ "|book_seat_class1:" + book_seat_class1 + "|book_seat_class2:" + book_seat_class2
				+ "|book_seat_soft:" + book_seat_soft + "|book_seat_hard:" + book_seat_hard
				+ "|book_seat_none:" + book_seat_none + "|book_bed_senior:" + book_bed_senior
				+ "|book_bed_soft:" + book_bed_soft + "|book_bed_hard:" + book_bed_hard;
	}
}
