/**
 * 
 */
package com.lvmama.comm.businesses.po.review;

import java.io.Serializable;
import java.util.Date;

/**
 * 资讯点评
 * @author nixianjun
 *
 */
public class PhpcmsContent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 34563456371L;

	private int contentid;
	private String content;
	private int status;
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
	 * @return the contentid
	 */
	public int getContentid() {
		return contentid;
	}
	/**
	 * @param contentid the contentid to set
	 */
	public void setContentid(int contentid) {
		this.contentid = contentid;
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
	 * @return the url
	 */
	public String getUrl() {
		return "http://www.lvmama.com/info/"+url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
