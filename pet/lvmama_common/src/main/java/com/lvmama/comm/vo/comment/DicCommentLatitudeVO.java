package com.lvmama.comm.vo.comment;

import java.io.Serializable;

/**
 * 点评纬度字典表实体类 VO 公共 MODEL
 * @author dengcheng
 *
 */
public class DicCommentLatitudeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1992348348422159926L;
	/**
	 * 标识
	 */
	private String latitudeId;
	/**
	 * 主题
	 */
	private String subject;
	/**
	 * 名字
	 */
	private String name;

	public String getLatitudeId() {
		return latitudeId;
	}
	public void setLatitudeId(final String latitudeId) {
		this.latitudeId = latitudeId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}

}
