/**
 * 
 */
package com.lvmama.front.web.ajax;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * @author liuyi
 *
 */
public class AjaxRtnCommentLatitude implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 570953327087023799L;
	
	/**
	 * 标识
	 */
	private Long cmtLatitudeId;
	/**
	 * 维度ID
	 */
	private String latitudeId;
	/**
	 * 评分(非常好，较好，好，一般，差)
	 */
	private Integer score;
	/**
	 * 点评ID
	 */
	private Long commentId;
	/**
	 * 维度名称
	 */
	private String latitudeName;
	
	
	public Long getCmtLatitudeId() {
		return cmtLatitudeId;
	}

	public void setCmtLatitudeId(Long cmtLatitudeId) {
		this.cmtLatitudeId = cmtLatitudeId;
	}

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

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getLatitudeName() {
		return latitudeName;
	}

	public void setLatitudeName(String latitudeName) {
		this.latitudeName = latitudeName;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
