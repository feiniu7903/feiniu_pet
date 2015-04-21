package com.lvmama.comm.search.vo;


public class AutoCompletePlaceHotel extends AutoCompletePlaceObject {
	private static final long serialVersionUID = 1L;
	/** 经纬度 */
	protected Double longitude;
	/** 经纬度 */
	protected Double latitude;
	
	protected String destParentStr;
	
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getDestParentStr() {
		return destParentStr;
	}
	public void setDestParentStr(String destParentStr) {
		this.destParentStr = destParentStr;
	}
	public String getMatchword() {
		return matchword;
	}
	public void setMatchword(String matchword) {
		this.matchword = matchword;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public String getMatchSEQ() {
		return matchSEQ;
	}
	public void setMatchSEQ(String matchSEQ) {
		this.matchSEQ = matchSEQ;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
	
}
