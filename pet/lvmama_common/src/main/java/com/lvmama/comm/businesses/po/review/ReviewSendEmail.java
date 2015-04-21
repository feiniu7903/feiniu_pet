package com.lvmama.comm.businesses.po.review;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class ReviewSendEmail implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private long rseId;
    private String keyWordStartDate;
    private String keyWordEndDate;
    private String contentStartDate;
    private String contentEndDate;
    private String reviewChannel;
    private String reviewStatus;
    private Date rseDate;
    private Integer count;


	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}


	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}


	public long getRseId() {
        return rseId;
    }


    public void setRseId(long rseId) {
        this.rseId = rseId;
    }


    public String getRseContent() {
        String content = "";
        if (!StringUtil.isEmptyString(keyWordStartDate)) {
            content += "关键字时间范围:" + keyWordStartDate;
        }
        if (!StringUtil.isEmptyString(keyWordEndDate)) {
            content += "至" + keyWordEndDate + "<br />";
        }
        if (!StringUtil.isEmptyString(contentStartDate)) {
            content += "内容创建时间范围:" + contentStartDate;
        }
        if (!StringUtil.isEmptyString(contentEndDate)) {
            content += "至" + contentEndDate + "<br />";
        }
        if (!StringUtil.isEmptyString(reviewChannel)) {
        	String fromName="";
        	if("1".equals(reviewChannel)){
        		fromName="论坛帖子和主题列表";
        	}else if("2".equals(reviewChannel)){
        		fromName="点评及评论";
        	}else if("3".equals(reviewChannel)){
        		fromName="攻略及评论";
        	}else if("4".equals(reviewChannel)){
        		fromName="资讯及评论";
        	}
            content += "内容来源:" + fromName + "<br />";
        }
        if (!StringUtil.isEmptyString(reviewStatus)) {
            content += "安全等级:" + Constant.REVIEW_STATUS.getCnNameByCode(reviewStatus) + "<br />";
        }
        if (null!=count) {
            content += "扫描命中数量:" + count+ "<br />";
        }
        return content;
    }



    public Date getRseDate() {
        return rseDate;
    }


    /**
	 * @return the keyWordStartDate
	 */
	public String getKeyWordStartDate() {
		return keyWordStartDate;
	}


	/**
	 * @param keyWordStartDate the keyWordStartDate to set
	 */
	public void setKeyWordStartDate(String keyWordStartDate) {
		this.keyWordStartDate = keyWordStartDate;
	}


	/**
	 * @return the keyWordEndDate
	 */
	public String getKeyWordEndDate() {
		return keyWordEndDate;
	}


	/**
	 * @param keyWordEndDate the keyWordEndDate to set
	 */
	public void setKeyWordEndDate(String keyWordEndDate) {
		this.keyWordEndDate = keyWordEndDate;
	}


	/**
	 * @return the contentStartDate
	 */
	public String getContentStartDate() {
		return contentStartDate;
	}


	/**
	 * @param contentStartDate the contentStartDate to set
	 */
	public void setContentStartDate(String contentStartDate) {
		this.contentStartDate = contentStartDate;
	}


	/**
	 * @return the contentEndDate
	 */
	public String getContentEndDate() {
		return contentEndDate;
	}


	/**
	 * @param contentEndDate the contentEndDate to set
	 */
	public void setContentEndDate(String contentEndDate) {
		this.contentEndDate = contentEndDate;
	}


	/**
	 * @return the reviewChannel
	 */
	public String getReviewChannel() {
		return reviewChannel;
	}


	/**
	 * @param reviewChannel the reviewChannel to set
	 */
	public void setReviewChannel(String reviewChannel) {
		this.reviewChannel = reviewChannel;
	}


	/**
	 * @return the reviewStatus
	 */
	public String getReviewStatus() {
		return reviewStatus;
	}


	/**
	 * @param reviewStatus the reviewStatus to set
	 */
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}


	public void setRseDate(Date rseDate) {
        this.rseDate = rseDate;
    }


    public String getRseDateToString() {
        return DateUtil.getFormatDate(rseDate, "yyyy-MM-dd HH:mm:ss");
    }
}
