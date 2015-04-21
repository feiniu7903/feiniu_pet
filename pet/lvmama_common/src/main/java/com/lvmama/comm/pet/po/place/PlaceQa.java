package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlaceQa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long placeQaId;
	private Long placeId;
	private String question;
	private String answer;
	private Long seq;
	private Long flag;
	
	
	public Long getPlaceQaId() {
		return placeQaId;
	}
	public void setPlaceQaId(Long placeQaId) {
		this.placeQaId = placeQaId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public Long getFlag() {
		return flag;
	}
	public void setFlag(Long flag) {
		this.flag = flag;
	}
	
}
