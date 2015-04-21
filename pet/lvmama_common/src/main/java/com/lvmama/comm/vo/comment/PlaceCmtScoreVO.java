package com.lvmama.comm.vo.comment;

import java.io.Serializable;

public class PlaceCmtScoreVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4568490648173227514L;
	private String name;
	private String score;

	private boolean isMain;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	public boolean isMain() {
		return isMain;
	}
	
	
	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
}
