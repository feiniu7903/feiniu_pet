package com.lvmama.pet.job.quartz;

import java.util.List;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.vo.Constant;

public class CommentLetterJob implements Runnable {

	private static final Logger LOG = Logger.getLogger(CommentLetterJob.class);
	private OrderService orderServiceProxy;

	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			LOG.info("CommentLetterJob Begin:===>");
			//获取可点评的订单信息
			List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByDate();
			if(canCommentOrderProductList!=null &&canCommentOrderProductList.size()>0){
				for(OrderAndComment orderAndComment:canCommentOrderProductList){
					commentLetter(orderAndComment);
				}
			}
			LOG.info("CommentLetterJob End");
		}
	}
	
	/**
	 * 站内信,邀请写点评
	 * @param orderAndComment
	 */
	private void commentLetter(OrderAndComment orderAndComment){
		String subject ="您有待点评的订单（发点评可返奖金）";
		String message ="亲爱的 "+orderAndComment.getUserName()+"：<br/>"+
						"<p>期待你将此次< "+orderAndComment.getProductName()+" >的体验与感受告诉我们和其他驴友，审核通过后将获赠相应奖金，同时获赠100积分。</p>" +
						"<p>详情请点击:"+
						"<a target='_blank' href='http://www.lvmama.com/comment/writeComment/fillComment.do?productId="+orderAndComment.getProductId()+"&orderId="+orderAndComment.getOrderId()+"&productType="+orderAndComment.getProductType()+"' class='cmtclick_now'>"+
						"立即点评</a></p><br/>";
		String type ="COMMENT";
		CommentUtil.synchLetter(subject,message,type,orderAndComment.getUserNo());
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}