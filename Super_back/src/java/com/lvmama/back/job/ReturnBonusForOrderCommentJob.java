package com.lvmama.back.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * 订购并点评自动任务实现类.该Job从Super Cash中迁移过来
 *
 * @author Libo Wang
 * @version 2012-11-08
 * @since Super二期
 */
public class ReturnBonusForOrderCommentJob {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(ReturnBonusForOrderCommentJob.class);
	
	/**
	 * 返现服务.
	 */
	private transient CashAccountService cashAccountService;
	

	/**
	 * 订单服务代理.
	 */
	private transient OrderService orderServiceProxy;
	
	//引入hession接口, 填写配置
	private transient CmtCommentService cmtCommentService;
	/**
	 * SSO远程调用服务
	 */
	private UserUserProxy userUserProxy;
	
	/**
	 * 扫描满足条件的订购并点评.
	 */
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			
			
			LOG.debug("start scan");
			
			Map<String, Object> map = new HashMap<String, Object>();
			//获取到满足返现初步条件的点评记录
			int countNum = cmtCommentService.queryCountOfCommentAndOrderOnPeriod(
					new HashMap<String, Object>()).intValue();

			if(countNum > 0 ){
				//分批处理商数次(每次500笔)
				for (int i = 0; i < countNum ; i=i+500) {
					map.clear();
					map.put("_startRow", i+1);
					map.put("_endRow", i+ 500);
					doRefundOrderAndComment(map);
				}
			}
			
		}
	}
	
	private void doRefundOrderAndComment(Map<String, Object> paraMap) {
		
		//获取到同时满足订单和点评条件的记录
		List<OrderAndComment> canRefundOrderAndCommentList = new ArrayList<OrderAndComment>();
		
		List<OrderAndComment> cmtList = cmtCommentService.getCanRefundObjectMeetCommentCondition(paraMap);
		if(cmtList == null || cmtList.size() == 0)
		{
			LOG.info("redund cmt size null");
			return;
		}
		
		for(OrderAndComment orderAndComment : cmtList){
			final Map<String, Object> map = new Hashtable<String,Object>();
			map.put("isValid", "Y");
			map.put("isCashRefund", "false");
			map.put("commentCreateTime", orderAndComment.getCreateDate());
			map.put("orderId", orderAndComment.getOrderId());
			//获取该点评订单是否可返现
			final List<OrderAndComment> orderList = (List<OrderAndComment>) orderServiceProxy.queryOrderAndCommentOnPeriod(map);
			
			if (orderList == null || orderList.size() != 1) {
				continue; // 该点评对应订单不能返现，跳出
			} else {
				// 注入订单相关信息
				OrderAndComment tempOrderAndComment = orderList.get(0);
				orderAndComment.setOrderId(tempOrderAndComment.getOrderId());
				orderAndComment.setOrderVisitTime(tempOrderAndComment.getOrderVisitTime());
				orderAndComment.setCashRefund(tempOrderAndComment.getCashRefund());
				orderAndComment.setUserNo(tempOrderAndComment.getUserNo());
			}
			//获取用户信息  -------------------已经有值
			/*UserUser userUser = userUserProxy.getUserUserByUserNo(orderAndComment.getUserNo());
			orderAndComment.setUserId(userUser.getId());*/
			
			//放入待返现队列
			if(orderAndComment.getCashRefund() > 0){
				LOG.info("cash refund order: "+orderAndComment.getOrderId()+",cash amount: "+orderAndComment.getCashRefund()+" ,commentId:"+orderAndComment.getCommentId());
				canRefundOrderAndCommentList.add(orderAndComment);
				 
			}else if(orderAndComment.getCashRefund() == 0){
				CommonCmtCommentVO comment = cmtCommentService.getCmtCommentByKey(Long.parseLong(orderAndComment.getCommentId()));
				comment.setCashRefund(0L);	//订单返现为0, 点评不需要返现
				cmtCommentService.update(comment);
			}
		}
		if(canRefundOrderAndCommentList.size() > 0){
			Set<OrderAndComment> set = new java.util.TreeSet<OrderAndComment>();
			set.addAll(canRefundOrderAndCommentList);
			for (OrderAndComment e : set) {
				LOG.info("execute " + e.getOrderId());	
				try
				{
					//奖金账户返现
					cashAccountService.returnBonusForOrderComment(e);
					//订单返现
					orderServiceProxy.cashOrder(Long.parseLong(e.getOrderId()), e.getCashRefund());
					//点评返现
					cmtCommentService.updateExperienceComment(
							Long.valueOf(e.getOrderId()),
							Long.valueOf(e.getCommentId()),
									Long.valueOf(e.getCashRefund()));
				}
				catch(Exception ex)
				{
					LOG.error(ex,ex);
				}
			}
		}
	}
	
	
	/**
	 * 把32位user_id 转化为 Long 
	 * @param list
	 * @return
	 *//*
	private List<OrderAndComment> initUserId(List<OrderAndComment> list) {
		
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> userNoList = new ArrayList<String>();
			for (OrderAndComment oa : list) {
				userNoList.add(oa.getUserId());
			}
			if(userNoList.size() < 1000)
			{
				List<UserUser> userUserList = userUserProxy.getUsersListByUserNoList(userNoList);
				Map<String, String> map = new HashMap<String, String>();
				for (UserUser uu : userUserList) {
					map.put(uu.getUserId(), uu.getUserName());
				}
				for (OrderAndComment oa : list) {
					oa.setUserName(map.get(oa.getUserId()));
				}
			}
		}
		return list;
	}*/


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
}
