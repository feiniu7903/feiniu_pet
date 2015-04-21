/**
 * 
 */
package com.lvmama.comm.pet.po.comment;

import java.io.Serializable;

/**
 * @author liuyi
 *
 */
public class DicCommentLatitude  implements Serializable{
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 3055160797935817332L;
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
	
	/**
	 * 更新点评主题时，旧的维度标识
	 */
	private String oldLatitudeId;
	/**
	 * 更新点评主题时，新的维度标识
	 */
	private String newLatitudeId;

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
	public String getOldLatitudeId() {
		return oldLatitudeId;
	}
	public void setOldLatitudeId(String oldLatitudeId) {
		this.oldLatitudeId = oldLatitudeId;
	}
	public String getNewLatitudeId() {
		return newLatitudeId;
	}
	public void setNewLatitudeId(String newLatitudeId) {
		this.newLatitudeId = newLatitudeId;
	}

}
