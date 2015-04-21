package com.lvmama.comm.pet.service.review;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.businesses.po.review.ReviewSendEmail;

public interface ReviewSendEmailService {
    /**
     * 提交请求时 自动添加一条操作记录
     * @param reviewSend
     */
    public void insert(ReviewSendEmail reviewSend);

    /**
     * 仅显示最近操作的前五条
     * @return 
     */
    public List<ReviewSendEmail> query(Map<String,Object> param);

	public long count();

	public Integer exeScanningComment(Map map);

	public Integer exeScanningCmtReply(Map map);
}
