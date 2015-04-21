package com.lvmama.pet.review.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.businesses.po.review.ReviewSendEmail;
import com.lvmama.comm.pet.service.review.ReviewSendEmailService;
import com.lvmama.pet.review.dao.ReviewSendEmailDAO;

 
public class ReviewSendEmailServiceImpl implements ReviewSendEmailService{
	@Autowired
    private ReviewSendEmailDAO reviewSendEmailDAO;

    @Override
    public void insert(ReviewSendEmail reviewSend) {
        reviewSendEmailDAO.insertReviewSendEmail(reviewSend);
    }

    @Override
    public List<ReviewSendEmail> query(Map<String,Object> param) {
        return reviewSendEmailDAO.queryReviewSendEmail(param);
    }

	@Override
	public long count() {
		 
		return reviewSendEmailDAO.count();
	}

	@Override
	public Integer exeScanningComment(Map map) {
 		return reviewSendEmailDAO.exeScanningComment(map);
	}

	@Override
	public Integer exeScanningCmtReply(Map map) {
 		return reviewSendEmailDAO.exeScanningCmtReply(map);
	}
    
}
