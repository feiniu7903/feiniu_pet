package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientCmtLatitude implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3221258811800157888L;

	/**
	 * 维度ID
	 */
 	private String latitudeId;
 
	/**
	 * 评分(非常好，较好，好，一般，差)
	 */
 	private Integer score;
 	
	public String getLatitudeId() {
		return latitudeId;
	}
	public void setLatitudeId(String latitudeId) {
		this.latitudeId = latitudeId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
