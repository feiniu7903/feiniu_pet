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
public class GlArticle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 34563456371L;

	private int articleid;
	private int status;
    private String title;
    private String description;
    private String reviewstatus;
    private String content;
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
	 * @return the articleid
	 */
	public int getArticleid() {
		return articleid;
	}
	/**
	 * @param articleid the articleid to set
	 */
	public void setArticleid(int articleid) {
		this.articleid = articleid;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the url
	 */
	public String getUrl() {
		return "http://www.lvmama.com/guide/"+url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
