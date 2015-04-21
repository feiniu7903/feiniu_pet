/**
 * 
 */
package com.lvmama.comm.businesses.po.review;

import java.io.Serializable;
import java.util.Date;

/**
 * 攻略内容
 * @author nixianjun
 *
 */
public class GlComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 34563456371L;

	private int commentid;
	private String content;
	private int status;
    private String reviewstatus;
    private String url;
    private Date createdate;
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the reviewstatus
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the commentid
	 */
	public int getCommentid() {
		return commentid;
	}
	/**
	 * @param commentid the commentid to set
	 */
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
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
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl(){
		return "http://www.lvmama.com/guide/"+url;
	}
}
