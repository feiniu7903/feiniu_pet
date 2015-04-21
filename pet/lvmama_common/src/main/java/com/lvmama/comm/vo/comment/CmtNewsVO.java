package com.lvmama.comm.vo.comment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lvmama.comm.vo.Constant;

public class CmtNewsVO implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 7644216773523691266L;
	/**
	 * 标识
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 链接地址
	 */
	private String url;
	/**
	 * 图片地址
	 */
	private String pic;
	/**
	 * 图片链接地址
	 */
	private String picUrl;
	/**
	 * 简介
	 */
	private String summary;
	/**
	 * 创建日期
	 */
	private Date createdTime;
	/**
	 * 相关的place_Id
	 */
	private Long relatePlaceId;
	
	/**
	 * 该主题的待审核回复条数(表层没该数据,业务页面使用到)
	 */
	private Long replyCount;

	/**
	 * yyyy-MM-dd HH:mm 格式化时间
	 * 
	 * @return 时间
	 */
	public String getFormattedCreateTime() {
		if (null != createdTime) {
			return new SimpleDateFormat("yyyy-MM-dd").format(createdTime);
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("title", title).append("url", url).append("pic", pic)
				.append("picUrl", picUrl).append("summary", summary)
				.append("createdDate", createdTime).toString();
	}

	/*
	 * 获取图片绝对路径
	 */
	public String getAbsolutePictureUrl() {
		return Constant.getInstance().getPrefixPic() + this.pic;
	}

	/**
	 *  ----------------------  get and set property -------------------------------
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(final String pic) {
		this.pic = pic;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(final String picUrl) {
		this.picUrl = picUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(final Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Long replyCount) {
		this.replyCount = replyCount;
	}

	public void setRelatePlaceId(Long relatePlaceId) {
		this.relatePlaceId = relatePlaceId;
	}

	public Long getRelatePlaceId() {
		return relatePlaceId;
	}


}
