package com.lvmama.comm.vo.comment;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 小驴说事实体类
 * @author yuzhizeng
 */
public class CmtSpecialSubjectVO implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 3553240029659537377L;
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
	 * 图片链接地址:点击图片后跳转的地址
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
	 * 第几期
	 */
	private String versionNum;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("title", title)
				.append("url", url).append("pic", pic).append("picUrl", picUrl)
				.append("summary", summary).append("createdTime", createdTime).append("versionNum", versionNum)
				.toString();
	}

	/*
	 * 获取图片绝对路径
	 */
	public String getAbsolutePictureUrl() {
		return Constant.getInstance().getPrefixPic() + this.pic;
	}

	public String getStrCreatedTime() {
		return DateUtil.getFormatDate(createdTime, "yyyy-MM-dd");
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

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
}
