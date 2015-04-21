package com.lvmama.comm.pet.po.comment;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.lvmama.comm.vo.Constant;

/**
 * @author yuzhizeng
 * 点评维度统计
 */
public class CmtLatitudeStatistics implements Serializable{
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -7845367649250260269L;

	/**
	 * 景点Id
	 */
	private Long placeId;
	
	/**
	 * 产品ID
	 */
	private Long productId;
	
	/**
	 * 平均分
	 */
	private Float avgScore = 0.00F;
	
	/**
	 * 维度ID
	 */
	private String latitudeId;
	
	/**
	 * 维度名称
	 */
	private String latitudeName;
	
	/**
	 * 最大分
	 */
	private int maxScore = Constant.LATITUDE_GRADE_SCORE.PERFECT.getScore();//default max score is perfect score

	/**
	 * commentCount点评总数
	 */
	private Long commentCount;
	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	/**
	 * @return the placeId
	 */
	public Long getPlaceId() {
		return placeId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param avgScore the avgScore to set
	 */
	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	/**
	 * @return the avgScore
	 */
	public Float getAvgScore() {
 		return (float)(Math.round(avgScore*100))/100;
	}
	/**
	 * 平均值保留两位小数
	 * @return
	 */
	public String getAvgScoreForTwoDP(){
		     DecimalFormat df = new DecimalFormat("0.00");//这里是设定小数位数
		     String e = df.format(avgScore);
		     return e;
	}

	/**
	 * @param latitudeId the latitudeId to set
	 */
	public void setLatitudeId(String latitudeId) {
		this.latitudeId = latitudeId;
	}

	/**
	 * @return the latitudeId
	 */
	public String getLatitudeId() {
		return latitudeId;
	}

	/**
	 * @param latitudeName the latitudeName to set
	 */
	public void setLatitudeName(String latitudeName) {
		this.latitudeName = latitudeName;
	}

	/**
	 * @return the latitudeName
	 */
	public String getLatitudeName() {
		return latitudeName;
	}

	/**
	 * @param maxScore the maxScore to set
	 */
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	/**
	 * @return the maxScore
	 */
	public int getMaxScore() {
		return maxScore;
	}

	/**
	 * @return the commentCount
	 */
	public Long getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	
}
