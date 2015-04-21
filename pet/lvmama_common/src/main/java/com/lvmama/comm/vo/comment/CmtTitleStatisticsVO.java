package com.lvmama.comm.vo.comment;


public class CmtTitleStatisticsVO implements java.io.Serializable{
 
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 1355166607310193584L;
	/**
	 * 景点Id
	 */
	private Long placeId;
	/**
	 * 点评数
	 */
	private Long commentCount = 0L;
	/**
	 * 平均分
	 */
	private Float avgScore = 0F;
	/**
	 * 0分点评数
	 */
	private Integer zeroScoreCount = 0;
	/**
	 * 1分点评数
	 */
	private Integer oneScoreCount = 0;
	/**
	 * 2分点评数
	 */
	private Integer twoScoreCount = 0;
	/**
	 * 3分点评数
	 */
	private Integer threeScoreCount = 0;
	/**
	 * 4分点评数
	 */
	private Integer fourScoreCount = 0;
	/**
	 * 5分点评数
	 */
	private Integer fiveScoreCount = 0;
	
	/**
	 * 产品ID
	 */
	private Long productId;
	
	/**
	 * 主题名称
	 */
	private String titleName;
	
	/**
	 * 景点类型
	 */
	private String stage;
	
	/**
	 * 产品类型
	 */
	private String productType;
	
	/**
	 * 四舍五入获取平均分
	 * @return int
	 */
	public int getIntegerOfAvgScore() {
		return Integer.parseInt(Math.round(this.getAvgScore()) + "");
	}

	/**
	 * 获取平均分的区间值
	 * @return float
	 */
	public String getRoundHalfUpOfAvgScore() {
		float avg = getAvgScore();
		String value = "0";
		if (avg == 0) {
			value = "0";
		} else if (avg > 0 && avg <= 0.5) {
			value = "05";
		} else if (avg > 0.5 && avg <= 1) {
			value = "1";
		} else if (avg > 1 && avg <= 1.5) {
			value = "15";
		} else if (avg > 1.5 && avg <= 2) {
			value = "2";
		} else if (avg > 2 && avg <= 2.5) {
			value = "25";
		} else if (avg > 2.5 && avg <= 3) {
			value = "3";
		} else if (avg > 3 && avg <= 3.5) {
			value = "35";
		} else if (avg > 3.5 && avg <= 4) {
			value = "4";
		} else if (avg > 4 && avg <= 4.5) {
			value = "45";
		} else if (avg > 4.5 && avg <= 5) {
			value = "5";
		}
		return value;
	}
	
	
	public Float getAvgScorePercent() {
		return avgScore * 20;
	}
	
	public String getAvgScoreStr() {
		return avgScore.toString();
	}
	
	
	
	/**
	 *  ----------------------  get and set property -------------------------------
	 */
	public void setPlaceId(final Long placeId) {
		this.placeId = placeId;
	}
	public void setCommentCount(final Long commentCount) {
		this.commentCount = commentCount;
	}
	public void setAvgScore(final Float avgScore) {
		this.avgScore = avgScore;
	}
	public void setZeroScoreCount(final Integer zeroScoreCount) {
		this.zeroScoreCount = zeroScoreCount;
	}
	public void setOneScoreCount(final Integer oneScoreCount) {
		this.oneScoreCount = oneScoreCount;
	}
	public void setTwoScoreCount(final Integer twoScoreCount) {
		this.twoScoreCount = twoScoreCount;
	}
	public void setThreeScoreCount(final Integer threeScoreCount) {
		this.threeScoreCount = threeScoreCount;
	}
	public void setFourScoreCount(final Integer fourScoreCount) {
		this.fourScoreCount = fourScoreCount;
	}
	public void setFiveScoreCount(final Integer fiveScoreCount) {
		this.fiveScoreCount = fiveScoreCount;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public Float getAvgScore() {
		return avgScore;
	}
	public Integer getZeroScoreCount() {
		return zeroScoreCount;
	}
	public Integer getOneScoreCount() {
		return oneScoreCount;
	}
	public Integer getTwoScoreCount() {
		return twoScoreCount;
	}
	public Integer getThreeScoreCount() {
		return threeScoreCount;
	}
	public Integer getFourScoreCount() {
		return fourScoreCount;
	}
	public Integer getFiveScoreCount() {
		return fiveScoreCount;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}
