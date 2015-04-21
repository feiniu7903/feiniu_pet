package com.lvmama.comm.abroad.vo.response;

import java.util.List;

public class RecalcRoomPriceRes extends ErrorRes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5158471334768558414L;

	private RecalcRoomPriceReservation Reservation;
	
	private List<RecalcRoomPriceRoom> rooms;

	/**
	 * 预订信息
	 * @return
	 */
	public RecalcRoomPriceReservation getReservation() {
		return Reservation;
	}

	public void setReservation(RecalcRoomPriceReservation reservation) {
		Reservation = reservation;
	}
	/**
	 * 房间信息
	 * @return
	 */
	public List<RecalcRoomPriceRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<RecalcRoomPriceRoom> rooms) {
		this.rooms = rooms;
	}
}
