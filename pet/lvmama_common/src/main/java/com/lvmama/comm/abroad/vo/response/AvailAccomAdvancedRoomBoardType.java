package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
public class AvailAccomAdvancedRoomBoardType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1799698603944422942L;
	private AvailAccomAdvancedRoomBoardTypeAdults Adults;
	private AvailAccomAdvancedRoomBoardTypeChildren Children;
	public AvailAccomAdvancedRoomBoardTypeAdults getAdults() {
		return Adults;
	}
	public void setAdults(AvailAccomAdvancedRoomBoardTypeAdults adults) {
		Adults = adults;
	}
	public AvailAccomAdvancedRoomBoardTypeChildren getChildren() {
		return Children;
	}
	public void setChildren(AvailAccomAdvancedRoomBoardTypeChildren children) {
		Children = children;
	}
}
