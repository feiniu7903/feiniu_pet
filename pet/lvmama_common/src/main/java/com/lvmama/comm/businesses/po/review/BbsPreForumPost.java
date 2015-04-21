/**
 * 
 */
package com.lvmama.comm.businesses.po.review;

import java.io.Serializable;
import java.util.Date;

/**
 * 帖子内容
 * @author nixianjun
 *
 */
public class BbsPreForumPost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 34463456371L;

	private int pid;
	private int tid;
    private String subject;
    private String reviewstatus;
    private String message;
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
	 * @return 获取 message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return the url
	 */
	public String getUrl() {
		//return "http://bbs.lvmama.com/thread-"+tid+"-1-1.html";
		return "http://bbs.lvmama.com/forum.php?mod=redirect&goto=findpost&ptid="+tid+"&pid="+pid;
	}
	
	
	
}
