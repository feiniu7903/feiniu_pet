package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;
import java.util.List;
public class RecalcRoomPriceReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5248060192772513205L;
	private List<RecalcRoomPriceReqRoom> rooms;
	public List<RecalcRoomPriceReqRoom> getRooms() {
		return rooms;
	}
	/**
	 * 房间信息.必需1间或以上
	 * @param rooms
	 */
	public void setRooms(List<RecalcRoomPriceReqRoom> rooms) {
		this.rooms = rooms;
	}
}
