package com.lvmama.pet.review.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.ReviewSendEmail;

public class ReviewSendEmailDAO extends BaseIbatisDAO{
    

    /**
     * 根据条件查询关键词
     * @param map
     * @return
     */
    public List<ReviewSendEmail> queryReviewSendEmail(Map<String,Object> param) {
        return super.queryForList("REVIEWSENDEMAIL.queryReviewSendEmail",param);
    }


    /**
     * 提交一个请求自动添加一条发送记录
     * @param reviewSend
     */
    public void insertReviewSendEmail(ReviewSendEmail reviewSend) {
        super.insert("REVIEWSENDEMAIL.insertReviewSendEmail",reviewSend);
    }


	public long count() {
		return (Long) super.queryForObject("REVIEWSENDEMAIL.count");
	}


	public Integer exeScanningComment(Map map) {
		 super.update("REVIEWCOMMENTPRODUCE.exeScanningComment",map);
		 return (Integer)map.get("count");
	}


	public Integer exeScanningCmtReply(Map map) {
		 super.update("REVIEWCOMMENTPRODUCE.exeScanningCmtReply",map);
		 return (Integer)map.get("count");
	}
}
