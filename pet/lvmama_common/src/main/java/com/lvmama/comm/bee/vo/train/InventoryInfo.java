/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangbin
 *
 */
public class InventoryInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -810494697303934665L;
	private String trainName;
	private List<SeatInventory> seatInventoies;
	public InventoryInfo(){
		this.seatInventoies = new ArrayList<SeatInventory>();
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public List<SeatInventory> getSeatInventoies() {
		return seatInventoies;
	}
	public void setSeatInventoies(List<SeatInventory> seatInventoies) {
		this.seatInventoies = seatInventoies;
	}
	
	
}
