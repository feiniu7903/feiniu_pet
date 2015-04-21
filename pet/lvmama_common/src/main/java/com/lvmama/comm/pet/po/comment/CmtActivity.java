/**
 * 
 */
package com.lvmama.comm.pet.po.comment;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;
/**
 * @author liuyi
 * 点评广告对象
 */
public class CmtActivity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9172754377076688731L;
	/**
	 * 广告ID
	 */
	private Long activityId;
	/**
	 * 广告主题,广告宣传位置
	 */
	private String activitySubject;
	/**
	 * 链接
	 */
	private String url;
	/**
	 * 图片
	 */
	private String pic;
	/**
	 * 图片链接
	 */
	private String picUrl;
	/**
	 * 广告标题
	 */
	private String title;
	/**
	 * 广告内容
	 */
	private String content;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(final Long activityId) {
		this.activityId = activityId;
	}

	public String getActivitySubject() {
		return activitySubject;
	}

	public void setActivitySubject(final String activitySubject) {
		this.activitySubject = activitySubject;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	/**
	 * 获取图片绝对路径
	 */
	public String getAbsolutePictureUrl() {
		return Constant.getInstance().getPrefixPic() + this.pic;
	}
}
