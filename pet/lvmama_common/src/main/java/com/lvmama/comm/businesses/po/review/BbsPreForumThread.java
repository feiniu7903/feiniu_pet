/**
 * 
 */
package com.lvmama.comm.businesses.po.review;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nixianjun
 *
 */
public class BbsPreForumThread implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 34463456371L;

	private int pid;
	private int tid;
	private int first;
	private String subject;
 	private String reviewstatus;
 	private String url;
 	private Date createdate;
 	/**
	 * @return the createdate
	 */
	public Date getCreatedate() {
		return createdate;
	}
	/**
	 * @param createdate the createdate to set
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	/**
	 * @return 获取 pid
	 */
	public int getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}
	/**
	 * @return 获取 tid
	 */
	public int getTid() {
		return tid;
	}
	/**
	 * @param tid the tid to set
	 */
	public void setTid(int tid) {
		this.tid = tid;
	}
	/**
	 * @return 获取 first
	 */
	public int getFirst() {
		return first;
	}
	/**
	 * @param first the first to set
	 */
	public void setFirst(int first) {
		this.first = first;
	}
	/**
	 * @return 获取 subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return 获取 reviewstatus
	 */
	public String getReviewstatus() {
		return reviewstatus;
	}
	/**
	 * @param reviewstatus the reviewstatus to set
	 */
	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return "http://bbs.lvmama.com/thread-"+tid+"-1-1.html";
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
