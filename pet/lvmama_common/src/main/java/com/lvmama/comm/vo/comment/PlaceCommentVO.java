package com.lvmama.comm.vo.comment;


public class PlaceCommentVO implements java.io.Serializable{
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -4668250380126845372L;
	/**
	 * 景区ID
	 */
	private Long placeId;
	/**
	 * 景区名称
	 */
	private String placeName;
	/**
	 * place的类型
	 */
	private String stage;
	/**
	 * 平均分
	 */
	private Float avgScore;
	/**
	 * 带逻辑的拼音URL
	 */
	private String pinYinUrl;
	/**
	 * 景点小图片
	 */
	private String placeMiddleImg;
	/**
	 * 景点点评数
	 */
	private Long countOfCmts;
	/**
	 * 景点小图片
	 */
	private String placSmallImg;
	/**
	 * 景点小图片
	 */
	private String placeLargeImg;
	
	/*
	 * avgScore 取百分比
	 * avgScorePercent = avgScore * 5 / 100
	 * */
	public Float getAvgScorePercent() {
		return avgScore * 20;
	}
	
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Float getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}
	public String getPinYinUrl() {
		return pinYinUrl;
	}
	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}
	public Long getCountOfCmts() {
		return countOfCmts;
	}
	public void setCountOfCmts(Long countOfCmts) {
		this.countOfCmts = countOfCmts;
	}

	public String getPlaceMiddleImg() {
		return placeMiddleImg;
	}

	public void setPlaceMiddleImg(String placeMiddleImg) {
		this.placeMiddleImg = placeMiddleImg;
	}

	public String getPlacSmallImg() {
		return placSmallImg;
	}

	public void setPlacSmallImg(String placSmallImg) {
		this.placSmallImg = placSmallImg;
	}

	public String getPlaceLargeImg() {
		return placeLargeImg;
	}

	public void setPlaceLargeImg(String placeLargeImg) {
		this.placeLargeImg = placeLargeImg;
	}
	
}
