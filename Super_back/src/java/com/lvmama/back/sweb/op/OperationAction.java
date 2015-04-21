package com.lvmama.back.sweb.op;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.service.IGroupBudgetService;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;

/**
 * 计调处理功能页面
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public final class OperationAction extends BaseAction{

	private IOpTravelGroupService opTravelGroupService;
	private TopicMessageProducer orderMessageProducer;
	private IGroupBudgetService groupBudgetService;
	private Long travelGroupId;
	private long count;
	private String status;
	private String memo;

	/**
	 * 变更团的计划人数
	 */
	@Action("/op/change/initial_group_num")
	public void changeInitialGroupNum()
	{
		JSONResult result=new JSONResult();
		try
		{
			
			doBefore();
			Assert.isTrue(count>=-1, "人数不可以少于-1值");			
		
			opTravelGroupService.updateGroupInitialNum(travelGroupId, count,getOperatorName());
			
			OpTravelGroup group=opTravelGroupService.getOpTravelGroup(travelGroupId);
			long remain=group.getRemain();//OpTravelGroupUtil.remain(group);
			result.put("remain", remain);
		}catch(Exception ex)
		{
			result.raise(new JSONResultException(ex.getMessage()));
		}
		
		result.output(getResponse());
	}
	
	/**
	 * 变更团的状态
	 */
	@Action("/op/change/change_status")
	public void changeStatus(){
		JSONResult result=new JSONResult();
		try{
			doBefore();
			opTravelGroupService.changeStatus(travelGroupId,status,memo,getOperatorName());
			orderMessageProducer.sendMsg(MessageFactory.newTravelGroupStatus(travelGroupId,status));
			if(Constant.TRAVEL_GROUP_STATUS.CONFIRM.name().equals(status)){
				OpTravelGroup group=opTravelGroupService.getOpTravelGroup(travelGroupId);
				result.put("makeTime", DateUtil.getFormatDate(group.getMakeTime(), "yy-MM-dd HH:mm"));
			}
			//取消团，如果已做预算，并打款，则生成抵扣款
			if(Constant.TRAVEL_GROUP_STATUS.CANCEL.name().equals(status)){
				groupBudgetService.cancelTravelGroupHandler(travelGroupId);
			}
		}catch(Exception ex)
		{
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 发送可发出团通知书消息
	 */
	@Action("/op/change/change_groupword_abled")
	public void change_groupword_abled(){
		JSONResult result = new JSONResult();
		try{
			opTravelGroupService.changeGroupwordAble(travelGroupId,getOperatorName());
			orderMessageProducer.sendMsg(MessageFactory.newTravelGroupWordAble(travelGroupId));
		}catch(Exception ex){
			result.raise(new JSONResultException(ex));
		}
		
		result.output(getResponse());
	}
	
	
	/**
	 * 对团号做检查
	 */
	private void doBefore()
	{
		Assert.isTrue(!(travelGroupId==null||travelGroupId<1),"团号不存在");
	}

	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
	}


	public void setTravelGroupId(Long travelGroupId) {
		this.travelGroupId = travelGroupId;
	}


	public void setCount(long count) {
		this.count = count;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public IGroupBudgetService getGroupBudgetService() {
		return groupBudgetService;
	}

	public void setGroupBudgetService(IGroupBudgetService groupBudgetService) {
		this.groupBudgetService = groupBudgetService;
	}
	
}
