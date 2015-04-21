package com.lvmama.comm.pet.po.comment;

public class CommentStatistics {

	/**
	 * 景区ID
	 */
	private Long placeId;
	/**
	 * 景区名字
	 */
	private String name;
	/**
	 * 点评总数
	 */
	private int commentCount;

	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(final Long placeId) {
		this.placeId = placeId;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(final int commentCount) {
		this.commentCount = commentCount;
	}
}
